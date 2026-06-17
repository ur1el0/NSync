from django.contrib import admin
from .models import User, Note, Flashcard, QuizAttempt, UserProgress

# Register your models here.

admin.site.register(User)
admin.site.register(Note)
admin.site.register(Flashcard)
admin.site.register(QuizAttempt)
admin.site.register(UserProgress)