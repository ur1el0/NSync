from rest_framework import serializers
from .models import User, Note, Flashcard, QuizAttempt, UserProgress, UserProfile
from django.contrib.auth import authenticate, get_user_model


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = [
            'id',
            'name',
            'email',
        ]


class NoteSerializer(serializers.ModelSerializer):
    class Meta: 
        model = Note
        fields = [
            'id',
            'title',
            'content',
            'tag',
            'created_at',
            'updated_at',
        ]


class FlashcardSerializer(serializers.ModelSerializer):
    note_title = serializers.CharField(source='connected_note.title', read_only=True)
    note_tag = serializers.CharField(source='connected_note.tag', read_only=True)

    class Meta:
        model = Flashcard
        fields = [
            'id',
            'connected_note',
            'question',
            'answer',
            'note_title',
            'note_tag',
            'difficulty',
            'mastery_level',
        ]
        

class QuizAttemptSerializer(serializers.ModelSerializer):
    class Meta:
        model = QuizAttempt
        fields = [
            'id',
            'score',
            'total_questions',
            'xp_earned',
            'date_taken',
        ]

class UserProgressSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProgress
        fields = [
            'id',
            'total_xp',
            'level',
            'streak',
            'total_reviews',
            'correct_reviews',
            'accuracy',
        ]

class AuthenticatedUserSerializer(serializers.ModelSerializer):
    display_name = serializers.CharField(
        source="profile.display_name",
        read_only=True
    )

    class Meta:
        model = get_user_model()
        fields = [
            'id',
            'email',
            'display_name',
        ]

class RegisterSerializer(serializers.Serializer):
    display_name = serializers.CharField(max_length=200)
    email = serializers.EmailField()
    password = serializers.CharField(
        write_only=True,
        trim_whitespace=False,
    )

    def validate_email(self, value):
        email = value.strip().lower()
        user_model = get_user_model()

        if user_model.objects.filter(username=email).exists():
            raise serializers.ValidationError(
                "An account with this email already exists."
            )
        return email

    def create(self, validated_data):
        user_model = get_user_model()
        email = validated_data["email"]

        user = user_model.objects.create_user(
            username=email,
            email=email,
            password=validated_data["password"]
        )

        UserProfile.objects.create(
            user=user,
            display_name=validated_data["display_name"]
        )

        return user

class LoginSerializer(serializers.Serializer):
    email = serializers.EmailField()
    password = serializers.CharField(
        write_only=True,
        trim_whitespace=False,
    )

    def validate(self, attrs):
        email = attrs["email"].strip().lower()
        user = authenticate(
            username=email,
            password=attrs["password"]
        )

        if user is None or not user.is_active:
            raise serializers.ValidationError(
                "Invalid email or password."
            )

        attrs["user"] = user
        return attrs
