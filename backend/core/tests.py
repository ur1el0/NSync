from datetime import date
from unittest.mock import patch

from django.contrib.auth import get_user_model
from rest_framework import status
from rest_framework.test import APITestCase

from .models import Flashcard, Note, UserProfile, UserProgress


class AuthenticationTests(APITestCase):
    def test_register_creates_user_profile_and_tokens(self):
        response = self.client.post(
            "/api/auth/register/",
            {
                "display_name": "Roosc",
                "email": "roosc@example.com",
                "password": "SecurePassword123",
            },
            format="json",
        )

        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertIn("access", response.data)
        self.assertIn("refresh", response.data)
        self.assertEqual(response.data["user"]["email"], "roosc@example.com")

        user = get_user_model().objects.get(email="roosc@example.com")
        self.assertTrue(UserProfile.objects.filter(user=user, display_name="Roosc").exists())

    def test_login_rejects_invalid_credentials(self):
        user_model = get_user_model()
        user_model.objects.create_user(
            username="roosc@example.com",
            email="roosc@example.com",
            password="SecurePassword123",
        )

        response = self.client.post(
            "/api/auth/login/",
            {"email": "roosc@example.com", "password": "wrong-password"},
            format="json",
        )

        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)


class LearningDataOwnershipTests(APITestCase):
    def setUp(self):
        user_model = get_user_model()
        self.user_a = user_model.objects.create_user(
            username="user-a@example.com",
            email="user-a@example.com",
            password="SecurePassword123",
        )
        self.user_b = user_model.objects.create_user(
            username="user-b@example.com",
            email="user-b@example.com",
            password="SecurePassword123",
        )
        self.user_a_note = Note.objects.create(
            owner=self.user_a,
            title="User A note",
            content="Private knowledge.",
            tag="Testing",
        )

    def test_notes_require_authentication(self):
        response = self.client.get("/api/notes/")

        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)

    def test_user_cannot_list_or_retrieve_another_users_note(self):
        self.client.force_authenticate(self.user_b)

        list_response = self.client.get("/api/notes/")
        detail_response = self.client.get(f"/api/notes/{self.user_a_note.id}/")

        self.assertEqual(list_response.status_code, status.HTTP_200_OK)
        self.assertEqual(list_response.data, [])
        self.assertEqual(detail_response.status_code, status.HTTP_404_NOT_FOUND)

    def test_user_cannot_create_flashcard_for_another_users_note(self):
        self.client.force_authenticate(self.user_b)

        response = self.client.post(
            "/api/flashcards/",
            {
                "connected_note": self.user_a_note.id,
                "question": "Can User B add this?",
                "answer": "No.",
            },
            format="json",
        )

        self.assertEqual(response.status_code, status.HTTP_403_FORBIDDEN)
        self.assertFalse(Flashcard.objects.exists())

    @patch("core.api_views.timezone.localdate", return_value=date(2026, 6, 23))
    def test_review_progress_is_separate_for_each_user(self, _mock_localdate):
        self.client.force_authenticate(self.user_a)
        user_a_response = self.client.post(
            "/api/review/complete/",
            {"score": 2, "total_questions": 3, "xp_earned": 75},
            format="json",
        )

        self.client.force_authenticate(self.user_b)
        user_b_response = self.client.post(
            "/api/review/complete/",
            {"score": 1, "total_questions": 2, "xp_earned": 25},
            format="json",
        )

        self.assertEqual(user_a_response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(user_b_response.status_code, status.HTTP_201_CREATED)

        user_a_progress = UserProgress.objects.get(user=self.user_a)
        user_b_progress = UserProgress.objects.get(user=self.user_b)
        self.assertEqual(user_a_progress.total_xp, 75)
        self.assertEqual(user_b_progress.total_xp, 25)
        self.assertEqual(user_a_progress.streak, 1)
        self.assertEqual(user_b_progress.streak, 1)
