from django.db import models

# Create your models here.
    
class User(models.Model):
    name = models.CharField(max_length=200)
    email = models.EmailField(blank=True)

    def __str__(self):
        return self.name

class Note(models.Model):
    title = models.CharField(max_length=200)
    content = models.TextField()
    tag = models.CharField(max_length=100, blank=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.title

class Flashcard(models.Model):
    connected_note = models.ForeignKey(Note, on_delete=models.CASCADE)
    question = models.TextField()
    answer = models.TextField()
    difficulty = models.CharField(max_length=50, blank=True)
    mastery_level = models.IntegerField(min=0, max=3)

    def __str__(self):
        return self.question

class QuizAttempt(models.Model):
    score = models.IntegerField(default=0)
    total_questions = models.PositiveIntegerField(default=0)
    xp_earned = models.PositiveIntegerField(default=0)
    date_taken = models.DateTimeField(auto_now_add=True)

class UserProgress(models.Model):
    total_xp = models.PositiveIntegerField(default=0)
    level = models.PositiveIntegerField(default=1)
    streak = models.PositiveIntegerField(default=0)
    total_reviews = models.PositiveIntegerField(default=0)
    correct_reviews = models.PositiveIntegerField(default=0)
    accuracy = models.FloatField(default=0.0)

