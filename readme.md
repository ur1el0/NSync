# NSync

NSync is a personal knowledge and memory app for capturing ideas, turning them into review cards, and tracking learning progress. The Android client uses Jetpack Compose, while the Django REST API persists per-user notes, flashcards, review outcomes, and progress in PostgreSQL.

Detailed implementation documentation is available in [docs/README.md](docs/README.md).

```text
Capture knowledge -> Create review cards -> Review -> Earn XP -> Track mastery
```

## Features

- JWT authentication: register, login, token refresh, logout, and restored sessions.
- Per-user knowledge base with create, read, update, and delete note flows.
- Flashcards connected to a source note and organized into review sessions by note and collection.
- Multi-card review sessions with answer reveal, recall ratings, XP, accuracy, streak, and mastery updates.
- Dashboard, profile, and mastery views backed by user progress data.
- Settings for review goal, reminder time, difficulty, streak reminders, and notifications.
- Bottom navigation and dedicated screens for authentication, knowledge, review, progress, profile, and settings.

## Tech Stack

### Android client

- Kotlin and Jetpack Compose with Material 3
- Navigation Compose
- ViewModel and Compose UI state
- Retrofit, OkHttp, and Gson for HTTP requests
- DataStore Preferences for local JWT session storage
- Android notification scheduling for review reminders

### Backend

- Python, Django, and Django REST Framework
- PostgreSQL running in Docker Compose
- Simple JWT for token authentication
- `django-cors-headers` and environment-based Django configuration

## Project Structure

```text
NSync/
  backend/
    NSync/                 Django configuration
    core/                  Models, serializers, API views, tests, migrations
    docker-compose.yml     PostgreSQL service
    requirements.txt

  mobile/
    app/src/main/java/com/example/mobile/
      data/                DTOs, Retrofit, repositories, DataStore session
      navigation/          Routes and navigation graph
      notifications/       Review reminder scheduling
      ui/
        components/        Reusable Compose UI
        screens/           Auth, dashboard, knowledge, review, profile, settings
        state/             Screen UI state classes
        theme/             Colors, typography, and shared styles
        viewmodel/         Screen logic and API coordination

  nsync-frames.pdf         UI prototype reference
```

## API Overview

The backend exposes the following API routes under `/api/`:

| Area | Routes |
| --- | --- |
| Authentication | `auth/register/`, `auth/login/`, `auth/refresh/`, `auth/logout/`, `auth/me/` |
| Notes | `notes/` and `notes/{id}/` |
| Flashcards | `flashcards/` and `flashcards/{id}/` |
| Learning progress | `progress/`, `review/complete/` |
| Health check | `health/` |

Authenticated API resources are scoped to the signed-in user. A token from `auth/login/` or `auth/register/` is required for protected routes.

## Local Development Procedure

### 1. Configure and run the backend

From the repository root:

```bash
cd backend
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
cp .env.example .env
```

Set a unique `DJANGO_SECRET_KEY` and matching PostgreSQL values in `backend/.env`. Do not commit `.env`.

Start PostgreSQL, apply migrations, run tests, and start Django:

```bash
docker compose up -d db
python manage.py migrate
python manage.py test core
python manage.py runserver
```

The API runs at `http://127.0.0.1:8000/` by default.

### 2. Connect an Android emulator

The Android app uses `http://127.0.0.1:8000/` as its development API base URL. Reverse the emulator port so that this address reaches the host machine's Django server:

```bash
$ANDROID_HOME/platform-tools/adb reverse tcp:8000 tcp:8000
```

Confirm the emulator is visible first with:

```bash
$ANDROID_HOME/platform-tools/adb devices
```

For a physical device, replace the development base URL with the computer's reachable LAN IP address instead of using `adb reverse`.

### 3. Build and run the Android app

```bash
cd mobile
./gradlew :app:compileDebugKotlin
```

Run the `app` configuration from Android Studio on the connected emulator or device.

### 4. Verify the core workflow

1. Register a new account or log in with an existing account.
2. Create a note in the Knowledge Base.
3. Add flashcards associated with that note.
4. Open the related review session and complete its cards.
5. Confirm XP, accuracy, streak, and mastery update on Dashboard, Profile, and Mastery.
6. Log out, then log in again to confirm that the account's data remains scoped to that user.

## Development Checks

Run these checks before opening a pull request:

```bash
cd backend
python manage.py test core

cd ../mobile
./gradlew :app:compileDebugKotlin
```

## Security Notes

- Keep `backend/.env` local. Commit only `.env.example` with placeholders.
- JWT access and refresh tokens are stored locally by the Android client for session restoration.
- Protected API routes require authentication and return only data owned by the current user.
