from django.urls import path, include
from rest_framework.routers import DefaultRouter
from . import api_views

router = DefaultRouter()

router.register("notes", api_views.NoteViewSet, basename="notes")
router.register("flashcards", api_views.NoteViewSet, basename="flashcards")

urlpatterns = [
    path("health/", api_views.health_check, name="api-health"),
    path("progress/", api_views.progress_detail, name="api-progress"),
    path("review/complete", api_views.complete_review, name="api-review-complete"),
    path("", include(router.urls)),
]