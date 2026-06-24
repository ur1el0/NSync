from django.urls import path, include
from rest_framework.routers import DefaultRouter
from rest_framework_simplejwt.views import TokenRefreshView

from . import api_views

router = DefaultRouter()

router.register("notes", api_views.NoteViewSet, basename="notes")
router.register("flashcards", api_views.FlashcardViewSet, basename="flashcards")

urlpatterns = [
    path("health/", api_views.health_check, name="api-health"),
    path("auth/register/", api_views.register, name="api-register"),
    path("auth/login/", api_views.login, name="api-login"),
    path("auth/refresh/", TokenRefreshView.as_view(), name="api-token-refresh"),
    path("auth/logout/", api_views.logout, name="api-logout"),
    path("auth/me/", api_views.me, name="api-me"),
    path("progress/", api_views.progress_detail, name="api-progress"),
    path("review/complete/", api_views.complete_review, name="api-review-complete"),
    path("", include(router.urls)),
]
