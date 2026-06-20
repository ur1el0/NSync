from datetime import date, timedelta
from unittest.mock import patch

from rest_framework.test import APITestCase

from .models import UserProgress


class CompleteReviewTests(APITestCase):
    endpoint = "/api/review/complete/"

    def complete_review(self):
        return self.client.post(
            self.endpoint,
            {"score": 2, "total_questions": 3, "xp_earned": 75},
            format="json",
        )

    @patch("core.api_views.timezone.localdate")
    def test_first_review_starts_streak(self, mock_localdate):
        mock_localdate.return_value = date(2026, 6, 20)

        response = self.complete_review()

        self.assertEqual(response.status_code, 201)
        progress = UserProgress.objects.get(id=1)
        self.assertEqual(progress.streak, 1)
        self.assertEqual(progress.last_reviewed_on, date(2026, 6, 20))

    @patch("core.api_views.timezone.localdate")
    def test_same_day_review_does_not_increment_streak(self, mock_localdate):
        today = date(2026, 6, 20)
        mock_localdate.return_value = today
        UserProgress.objects.create(id=1, streak=4, last_reviewed_on=today)

        self.complete_review()

        self.assertEqual(UserProgress.objects.get(id=1).streak, 4)

    @patch("core.api_views.timezone.localdate")
    def test_consecutive_day_review_increments_streak(self, mock_localdate):
        today = date(2026, 6, 20)
        mock_localdate.return_value = today
        UserProgress.objects.create(id=1, streak=4, last_reviewed_on=today - timedelta(days=1))

        self.complete_review()

        self.assertEqual(UserProgress.objects.get(id=1).streak, 5)

    @patch("core.api_views.timezone.localdate")
    def test_missed_day_resets_streak(self, mock_localdate):
        today = date(2026, 6, 20)
        mock_localdate.return_value = today
        UserProgress.objects.create(id=1, streak=4, last_reviewed_on=today - timedelta(days=2))

        self.complete_review()

        self.assertEqual(UserProgress.objects.get(id=1).streak, 1)
