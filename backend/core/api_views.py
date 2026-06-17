from rest_framework import status, viewsets
from rest_framework.response import Response
from rest_framework.decorators import api_view
from .models import User, Note, Flashcard, QuizAttempt, UserProgress
from .serializers import (
    UserSerializer, 
    NoteSerializer, 
    FlashcardSerializer, 
    QuizAttemptSerializer,
    UserProgressSerializer
)

@api_view(['GET'])
def health_check(request):
    return Response({"status":"ok"})

class NoteViewSet(viewsets.ModelViewSet):
    queryset = Note.objects.order_by("-updated_at")
    serializer_class = NoteSerializer


class FlashcardViewset(viewsets.ModelViewSet):
    serializer_class = FlashcardSerializer

    def get_queryset(self):
        queryset = Flashcard.objects.order_by("id")
        note_id = self.request.query_params.get("note")

        if note_id:
            queryset = queryset.filter(connected_note_id=note_id)

        return queryset
    

@api_view(["GET"])
def progress_detail(request):
    progress, created = UserProgress.objects.get_or_create(id=1)
    serializer = UserProgressSerializer(progress)
    return Response(serializer.data)


@api_view(["POST"])
def complete_review(request):
    score = int(request.data.get("score", 0))
    total_questions = int(request.data.get("total_questions", 0))
    xp_earned = int(request.data.get("xp_earned", 0))

    attempt = QuizAttempt.objects.create(
        score=score,
        total_questions=total_questions,
        xp_earned=xp_earned,
    )

    progress, created = UserProgress.objects.get_or_create(id=1)

    progress.total_xp += xp_earned
    progress.total_reviews += total_questions
    progress.correct_reviews += score

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
    if total_xp >= 500:
        return 2
    return 1