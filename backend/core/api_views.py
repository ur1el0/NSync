from datetime import timedelta

from django.utils import timezone
from rest_framework import status, viewsets
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny, IsAuthenticated
from rest_framework.response import Response
from rest_framework_simplejwt.exceptions import TokenError
from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework.exceptions import PermissionDenied

from .models import Note, Flashcard, QuizAttempt, UserProgress
from .serializers import (
    NoteSerializer, 
    FlashcardSerializer, 
    QuizAttemptSerializer,
    UserProgressSerializer,
    RegisterSerializer,
    LoginSerializer,
    AuthenticatedUserSerializer,
)


def build_auth_response(user):
    refresh = RefreshToken.for_user(user)

    return {
        "refresh": str(refresh),
        "access": str(refresh.access_token),
        "user": AuthenticatedUserSerializer(user).data,
    }


@api_view(['GET'])
@permission_classes([AllowAny])
def health_check(request):
    return Response({"status":"ok"})

class NoteViewSet(viewsets.ModelViewSet):
    queryset = Note.objects.order_by("-updated_at")
    serializer_class = NoteSerializer
    permission_classes = [IsAuthenticated]

    def get_queryset(self):
        return Note.objects.filter(owner=self.request.user).order_by("-updated_at")

    def perform_create(self, serializer):
        serializer.save(owner=self.request.user)

class FlashcardViewSet(viewsets.ModelViewSet):
    queryset = Flashcard.objects.all()
    serializer_class = FlashcardSerializer
    permission_classes = [IsAuthenticated]

    def get_queryset(self):
        queryset = Flashcard.objects.filter(
            connected_note__owner=self.request.user
        ).order_by("id")
        note_id = self.request.query_params.get("note")

        if note_id:
            queryset = queryset.filter(connected_note_id=note_id)

        return queryset

    def perform_create(self, serializer):
        connected_note = serializer.validated_data["connected_note"]

        if connected_note.owner != self.request.user:
            raise PermissionDenied(
                "You do not have permission to add flashcards to this note."
            )

        serializer.save()

@api_view(["GET"])
@permission_classes([IsAuthenticated])
def progress_detail(request):
    progress, created = UserProgress.objects.get_or_create(user=request.user)
    serializer = UserProgressSerializer(progress)
    return Response(serializer.data)


@api_view(["POST"])
@permission_classes([IsAuthenticated])
def complete_review(request):
    answers = request.data.get("answers")

    if isinstance(answers, list):
        flashcard_ids = [answer.get("flashcard_id") for answer in answers]

        if len(flashcard_ids) != len(set(flashcard_ids)):
            return Response(
                {"detail": "Duplicate flashcard answers are not allowed."},
                status=status.HTTP_400_BAD_REQUEST,
            )

        owned_flashcards = Flashcard.objects.filter(
            id__in=flashcard_ids,
            connected_note__owner=request.user,
        )
        flashcards_by_id = {flashcard.id: flashcard for flashcard in owned_flashcards}

        if len(flashcards_by_id) != len(set(flashcard_ids)):
            return Response(
                {"detail": "One or more flashcards do not belong to this user."},
                status=status.HTTP_403_FORBIDDEN,
            )

        score = 0
        for answer in answers:
            flashcard = flashcards_by_id.get(answer.get("flashcard_id"))
            recalled = bool(answer.get("recalled", False))

            if recalled:
                score += 1
                flashcard.mastery_level = min(3, flashcard.mastery_level + 1)
            else:
                flashcard.mastery_level = max(0, flashcard.mastery_level - 1)

            flashcard.save(update_fields=["mastery_level"])

        total_questions = len(answers)
        xp_earned = total_questions * 25
    else:
        score = int(request.data.get("score", 0))
        total_questions = int(request.data.get("total_questions", 0))
        xp_earned = int(request.data.get("xp_earned", 0))

    attempt = QuizAttempt.objects.create(
        user=request.user,
        score=score,
        total_questions=total_questions,
        xp_earned=xp_earned,
    )

    progress, created = UserProgress.objects.get_or_create(user=request.user)

    progress.total_xp += xp_earned
    progress.total_reviews += total_questions
    progress.correct_reviews += score

    today = timezone.localdate()
    if progress.last_reviewed_on == today:
        pass
    elif progress.last_reviewed_on == today - timedelta(days=1):
        progress.streak += 1
    else:
        progress.streak = 1
    progress.last_reviewed_on = today

    if progress.total_reviews > 0:
        progress.accuracy = progress.correct_reviews / progress.total_reviews * 100
    else:
        progress.accuracy = 0


    progress.level = calculate_level(progress.total_xp)
    progress.save()
    
    return Response(
            {
            "attempt":QuizAttemptSerializer(attempt).data,
            "progress": UserProgressSerializer(progress).data,
        },
        status=status.HTTP_201_CREATED,
    )

def calculate_level(total_xp):
    if total_xp >= 1000:
        return 5
    if total_xp >= 500:
        return 4
    if total_xp >= 250:
        return 3
    if total_xp >= 100:
        return 2
    return 1


@api_view(["POST"])
@permission_classes([AllowAny])
def register(request):
    input_serializer = RegisterSerializer(data=request.data)
    input_serializer.is_valid(raise_exception=True)
    user = input_serializer.save()

    return Response(build_auth_response(user), status=status.HTTP_201_CREATED)

@api_view(["POST"])
@permission_classes([AllowAny])
def login(request):
    serializer = LoginSerializer(data=request.data)
    serializer.is_valid(raise_exception=True)

    user = serializer.validated_data["user"]
    return Response(build_auth_response(user), status=status.HTTP_200_OK)

@api_view(["POST"])
@permission_classes([IsAuthenticated])
def logout(request):
    try:
        refresh_token = request.data.get("refresh")

        if not refresh_token:
            return Response(
                {"detail": "Refresh token is required in the request body."},
                status=status.HTTP_400_BAD_REQUEST
            )

        token = RefreshToken(refresh_token)
        token.blacklist()

        return Response(status=status.HTTP_204_NO_CONTENT)
    except TokenError:
        return Response(
            {"detail": "Invalid or expired refresh token."},
            status=status.HTTP_400_BAD_REQUEST
        )

@api_view(["GET"])
@permission_classes([IsAuthenticated])
def me(request):
    serializer = AuthenticatedUserSerializer(request.user)
    return Response(serializer.data)
