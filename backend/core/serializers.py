from rest_framework import serializers
from .models import User, Note, Flashcard, QuizAttempt, UserProgress

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
