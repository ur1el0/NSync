from .models import User, Note, Flashcard, QuizAttempt, UserProgress
from rest_framework import serializers

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = [
            'id',
            'name',
            'email'
            'password'
        ]

        def __str__(self):
            return self.name
        

class NoteSerializer(serializers.ModelSerializer):
    class Meta: 
        model = Note
        fields = [
            'title',
            'content'
        ]

        def __str__(self):
            return self.title
        
class FlashcardSerializer(serializers.ModelSerializer):
    class Meta:
        model = Flashcard
        fields = [
            'connected_note',
            'question',
            'answer',
        ]

class QuizAttemptSerializer(serializers.ModelSerializer):
    class Meta:
        model = QuizAttempt
        fields = [
            'score',
            'total_questions',
            'xp_earned',
        ]

class UserProgressSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProgress
        fields = [
            'total_xp',
            'level',
            'streak',
            'total_reviews',
            'correct_reviews',
            'accuracy',
        ]