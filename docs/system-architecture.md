# NSync System Architecture

This document explains the full NSync system: Android client, Django backend, PostgreSQL database, authentication, API flow, and local development setup.

## 1. System Overview

NSync is a full-stack personal knowledge and memory app.

High-level flow:

```text
User
  -> Android app
  -> Retrofit HTTP client
  -> Django REST API
  -> PostgreSQL database
```

Product flow:

```text
Register/Login
  -> Create notes
  -> Add flashcards
  -> Review cards
  -> Save review result
  -> Update XP, level, streak, accuracy, and mastery
```

## 2. Architecture Diagram

```text
+-----------------------------+
|        Android Client       |
|-----------------------------|
| Jetpack Compose Screens     |
| ViewModels                  |
| UI State classes            |
| Repositories                |
| Retrofit + OkHttp           |
| AuthInterceptor             |
| DataStore AuthSessionStore  |
+--------------+--------------+
               |
               | HTTP / JSON
               | Authorization: Bearer <access>
               v
+--------------+--------------+
|        Django REST API      |
|-----------------------------|
| API URLs / Routers          |
| API Views / ViewSets        |
| Serializers                 |
| Models                      |
| Simple JWT Auth             |
| Ownership filtering         |
+--------------+--------------+
               |
               | Django ORM
               v
+--------------+--------------+
|          PostgreSQL         |
|-----------------------------|
| Users                       |
| UserProfile                 |
| Notes                       |
| Flashcards                  |
| QuizAttempt                 |
| UserProgress                |
+-----------------------------+
```

## 3. Repository Structure

```text
NSync/
  backend/
    NSync/
      settings.py
      urls.py
      asgi.py
      wsgi.py
    core/
      models.py
      serializers.py
      api_views.py
      api_urls.py
      tests.py
      migrations/
    docker-compose.yml
    requirements.txt
    tests.http

  mobile/
    app/src/main/java/com/example/mobile/
      data/
        local/
        remote/
        repository/
        Models.kt
        SampleData.kt
      navigation/
      notifications/
      ui/
        components/
        screens/
        state/
        theme/
        viewmodel/
```

## 4. Android Client Architecture

The Android app follows a layered structure.

```text
Compose Screen
  -> ViewModel
  -> Repository
  -> ApiService
  -> RetrofitClient
  -> Django API
```

### 4.1 UI Layer

Location:

```text
mobile/app/src/main/java/com/example/mobile/ui/
```

Main folders:

- `screens/`: full app screens.
- `components/`: reusable UI pieces.
- `state/`: screen state data classes.
- `theme/`: colors, typography, shared styling.
- `viewmodel/`: screen logic and API coordination.

Screen folders:

- `auth`: login and register.
- `dashboard`: progress overview.
- `knowledge`: notes and note detail.
- `review`: flashcards, review sessions, session completion.
- `progress`: mastery screen.
- `profile`: user profile.
- `settings`: settings and local preferences.

### 4.2 Compose Screens

Screens are responsible for displaying state and calling callbacks.

Examples:

- `LoginScreen.kt`
- `RegisterScreen.kt`
- `DashboardScreen.kt`
- `KnowledgeBaseScreen.kt`
- `NewNoteScreen.kt`
- `ReviewCardsScreen.kt`
- `ReviewSessionScreen.kt`
- `SessionCompleteScreen.kt`
- `ProfileScreen.kt`
- `SettingsScreen.kt`

Screens should not directly own backend logic. They call ViewModel functions or receive callbacks from navigation.

### 4.3 ViewModel Layer

Location:

```text
mobile/app/src/main/java/com/example/mobile/ui/viewmodel/
```

Purpose:

- Load data.
- Call repositories.
- Hold UI state.
- Handle screen actions.
- Convert API results into screen state.

Examples:

- `AuthViewModel`
- `DashboardViewModel`
- `KnowledgeBaseViewModel`
- `NewNoteViewModel`
- `KnowledgeDetailViewModel`
- `ReviewCardsViewModel`
- `ReviewSessionViewModel`
- `SessionCompleteViewModel`
- `MasteryViewModel`
- `ProfileViewModel`

ViewModels use Compose state:

```text
mutableStateOf(...)
```

This lets Compose recompose when state changes.

### 4.4 UI State Layer

Location:

```text
mobile/app/src/main/java/com/example/mobile/ui/state/
```

Purpose:

- Store screen data in one predictable object.
- Represent loading, error, and success state.

Examples:

- `AuthUiState`
- `DashboardUiState`
- `KnowledgeBaseUiState`
- `KnowledgeDetailUiState`
- `SessionCompleteUiState`
- `ProfileUiState`

Example responsibilities:

- `DashboardUiState`: display name, email, progress, recent knowledge.
- `ProfileUiState`: display name, email, learning goal, progress.
- `KnowledgeBaseUiState`: notes list, loading state, error state.

### 4.5 Repository Layer

Location:

```text
mobile/app/src/main/java/com/example/mobile/data/repository/
```

Repositories isolate API logic from ViewModels.

Main repositories:

- `AuthRepository`
- `NSyncRepository`

`AuthRepository` handles:

- register
- login
- token refresh
- logout
- session verification
- saving/clearing session data

`NSyncRepository` handles:

- notes CRUD
- flashcards CRUD
- review completion
- progress loading
- DTO-to-domain mapping

### 4.6 Remote API Layer

Location:

```text
mobile/app/src/main/java/com/example/mobile/data/remote/
```

Files:

- `RetrofitClient.kt`
- `api/ApiService.kt`
- `dto/`
- `interceptor/AuthInterceptor.kt`

`ApiService` defines backend HTTP routes:

- `GET /api/notes/`
- `POST /api/notes/`
- `GET /api/flashcards/`
- `POST /api/flashcards/`
- `POST /api/review/complete/`
- `GET /api/progress/`
- `POST /api/auth/register/`
- `POST /api/auth/login/`
- `POST /api/auth/refresh/`
- `POST /api/auth/logout/`
- `GET /api/auth/me/`

`RetrofitClient` builds:

- Retrofit instance.
- OkHttp client.
- `AuthInterceptor`.
- `AuthSessionStore`.

### 4.7 Local Session Layer

Location:

```text
mobile/app/src/main/java/com/example/mobile/data/local/
```

Files:

- `AuthSession.kt`
- `AuthSessionStore.kt`

`AuthSession` stores:

- access token
- refresh token
- user ID
- display name
- email

`AuthSessionStore` uses DataStore Preferences to persist the session locally.

This allows:

- session restore after app restart
- bearer token access for requests
- current user display on Dashboard/Profile/Settings

## 5. Authentication Architecture

Authentication uses JWT.

### 5.1 Register/Login Flow

```text
LoginScreen/RegisterScreen
  -> AuthViewModel
  -> AuthRepository
  -> ApiService
  -> Django auth endpoint
  -> JWT response
  -> AuthSessionStore.saveSession()
  -> AppNavigation redirects to Dashboard
```

The backend returns:

```json
{
  "refresh": "...",
  "access": "...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "display_name": "User"
  }
}
```

### 5.2 Authenticated Request Flow

```text
Repository calls ApiService
  -> Retrofit sends request
  -> AuthInterceptor reads access token
  -> Adds Authorization header
  -> Django validates JWT
  -> API returns user-owned data
```

Header:

```http
Authorization: Bearer <access-token>
```

### 5.3 Session Restore Flow

```text
AppNavigation starts
  -> AuthViewModel.restoreSession()
  -> AuthRepository.verifySession()
  -> GET /api/auth/me/
  -> valid session: continue to app
  -> invalid session: clear local session and go to Login
```

### 5.4 Token Refresh Flow

If `/api/auth/me/` returns `401`:

```text
AuthRepository.refreshSession()
  -> POST /api/auth/refresh/
  -> save new tokens
  -> retry /api/auth/me/
```

If refresh fails:

```text
clear session -> return to Login
```

## 6. Navigation Architecture

Location:

```text
mobile/app/src/main/java/com/example/mobile/navigation/
```

Files:

- `AppNavigation.kt`
- `Routes.kt`

`AppNavigation` owns:

- `NavHost`
- route definitions
- route arguments
- auth session restore
- redirects between login/register and protected screens
- bottom navigation behavior

Main route groups:

- Auth:
  - Login
  - Register
- Main:
  - Dashboard
  - Knowledge Base
  - Flashcards
  - Mastery
  - Profile
  - Settings
- Detail/edit:
  - Knowledge detail
  - New/edit note
  - New/edit flashcard
  - Flashcard detail
  - Review session
  - Session complete

## 7. Backend Architecture

The backend uses Django REST Framework.

```text
URL route
  -> API view / ViewSet
  -> Serializer
  -> Model
  -> PostgreSQL
```

### 7.1 Backend Project Files

Location:

```text
backend/
```

Important files:

- `NSync/settings.py`: Django settings and installed apps.
- `NSync/urls.py`: project URL routing.
- `core/models.py`: database models.
- `core/serializers.py`: API input/output serializers.
- `core/api_views.py`: REST API logic.
- `core/api_urls.py`: API route definitions.
- `core/tests.py`: backend tests.
- `docker-compose.yml`: PostgreSQL service.
- `tests.http`: manual API testing.

### 7.2 Backend API Routes

Base URL:

```text
http://127.0.0.1:8000/api/
```

Routes:

```text
GET    /api/health/
POST   /api/auth/register/
POST   /api/auth/login/
POST   /api/auth/refresh/
POST   /api/auth/logout/
GET    /api/auth/me/
GET    /api/notes/
POST   /api/notes/
GET    /api/notes/{id}/
PUT    /api/notes/{id}/
DELETE /api/notes/{id}/
GET    /api/flashcards/
POST   /api/flashcards/
GET    /api/flashcards/{id}/
PUT    /api/flashcards/{id}/
DELETE /api/flashcards/{id}/
GET    /api/progress/
POST   /api/review/complete/
```

### 7.3 API Views and ViewSets

Location:

```text
backend/core/api_views.py
```

Main handlers:

- `health_check`: confirms API is running.
- `register`: creates a user and returns JWT tokens.
- `login`: authenticates a user and returns JWT tokens.
- `logout`: blacklists refresh token.
- `me`: returns current authenticated user.
- `NoteViewSet`: CRUD for notes.
- `FlashcardViewSet`: CRUD for flashcards.
- `progress_detail`: returns user progress.
- `complete_review`: saves review result and updates progress.

## 8. Database Architecture

Location:

```text
backend/core/models.py
```

### 8.1 Main Models

```text
UserProfile
  -> one-to-one Django auth user
  -> stores display_name

Note
  -> owner: Django auth user
  -> title
  -> content
  -> tag
  -> created_at
  -> updated_at

Flashcard
  -> connected_note: Note
  -> question
  -> answer
  -> difficulty
  -> mastery_level

QuizAttempt
  -> user: Django auth user
  -> score
  -> total_questions
  -> xp_earned
  -> date_taken

UserProgress
  -> user: Django auth user
  -> total_xp
  -> level
  -> streak
  -> last_reviewed_on
  -> total_reviews
  -> correct_reviews
  -> accuracy
```

### 8.2 Ownership Rules

Notes belong to the signed-in user:

```text
Note.owner = request.user
```

Flashcards belong indirectly through notes:

```text
Flashcard.connected_note.owner = request.user
```

Progress belongs to the signed-in user:

```text
UserProgress.user = request.user
```

Quiz attempts belong to the signed-in user:

```text
QuizAttempt.user = request.user
```

This prevents one user from reading or changing another user's learning data.

## 9. Data Flow Examples

### 9.1 Create Note

```text
NewNoteScreen
  -> NewNoteViewModel.createNote()
  -> NSyncRepository.createNote()
  -> ApiService.createNote()
  -> POST /api/notes/
  -> NoteViewSet.perform_create()
  -> Note(owner=request.user)
  -> PostgreSQL
  -> NoteDto response
  -> UI returns to Knowledge Base
```

### 9.2 Add Flashcard

```text
NewFlashcardScreen
  -> NewFlashcardViewModel.saveFlashcard()
  -> NSyncRepository.createFlashcard()
  -> ApiService.createFlashcard()
  -> POST /api/flashcards/
  -> FlashcardViewSet.perform_create()
  -> check connected note owner
  -> save flashcard
  -> FlashcardDto response
```

### 9.3 Complete Review

```text
ReviewSessionScreen
  -> ReviewSessionViewModel records answers
  -> NSyncRepository.completeReview()
  -> POST /api/review/complete/
  -> complete_review()
  -> create QuizAttempt
  -> update UserProgress
  -> return attempt + progress
  -> SessionCompleteScreen displays result
```

### 9.4 Load Dashboard

```text
DashboardScreen
  -> DashboardViewModel.loadDashboard()
  -> read AuthSessionStore for displayName/email
  -> NSyncRepository.getProgress()
  -> GET /api/progress/
  -> NSyncRepository.getKnowledgeItems()
  -> GET /api/notes/
  -> render greeting, level, streak, accuracy, recent knowledge
```

## 10. Settings and Notifications

Settings are local Android preferences.

Location:

```text
mobile/app/src/main/java/com/example/mobile/ui/screens/settings/
```

Files:

- `SettingsScreen.kt`
- `SettingsPreferences.kt`

Notification files:

```text
mobile/app/src/main/java/com/example/mobile/notifications/
```

Files:

- `ReviewReminderScheduler.kt`
- `ReviewReminderReceiver.kt`

Settings store:

- daily review goal
- reminder time
- review difficulty
- streak reminders
- notifications enabled

Reminder flow:

```text
SettingsScreen
  -> user changes notification/reminder settings
  -> SettingsPreferences saves values
  -> ReviewReminderScheduler schedules/cancels reminder
  -> ReviewReminderReceiver receives alarm
  -> Android notification appears
```

## 11. Local Development Architecture

### 11.1 Backend

```bash
cd backend
source .venv/bin/activate
docker compose up -d db
python manage.py migrate
python manage.py runserver
```

Backend runs at:

```text
http://127.0.0.1:8000/
```

### 11.2 PostgreSQL

Docker exposes PostgreSQL on the host port configured in:

```text
backend/docker-compose.yml
backend/.env
```

Example:

```yaml
ports:
  - "5435:5432"
```

```env
DB_PORT=5435
```

The container uses port `5432` internally. The host machine uses `5435`.

### 11.3 Android Emulator

The Android app uses:

```text
http://127.0.0.1:8000/
```

For emulator access to the host Django server:

```bash
$ANDROID_HOME/platform-tools/adb reverse tcp:8000 tcp:8000
```

Then run the app from Android Studio.

## 12. Testing and Demo Tools

### 12.1 Backend Tests

```bash
cd backend
python manage.py test core
```

### 12.2 Android Compile Check

```bash
cd mobile
./gradlew :app:compileDebugKotlin
```

### 12.3 Manual API Tests

Use:

```text
backend/tests.http
```

This file demonstrates:

- health check
- register user A
- register user B
- create note
- confirm user ownership
- reject cross-user access
- complete review
- check progress
- logout

### 12.4 DRF Browsable API

Open:

```text
http://127.0.0.1:8000/api/
```

Useful endpoints:

```text
/api/health/
/api/notes/
/api/flashcards/
/api/progress/
```

Protected routes require a valid JWT access token.

## 13. Current Boundaries

Implemented:

- Android Compose UI.
- Authenticated full-stack flow.
- JWT session handling.
- User-owned notes.
- User-owned flashcards through note ownership.
- Review completion.
- Progress updates.
- Settings and local reminders.
- PostgreSQL local database.

Not fully implemented:

- Forgot password flow.
- Change password flow.
- Production deployment.
- Advanced spaced repetition.
- AI/Ollama flashcard generation.
- Full Android UI/integration test coverage.

## 14. Architecture Summary

NSync uses a straightforward client-server architecture.

The Android app owns the user interface and local session handling. ViewModels manage UI state, repositories perform data operations, Retrofit sends API requests, and OkHttp attaches JWT tokens.

The Django backend owns authentication, authorization, business rules, and persistence. Django REST Framework exposes JSON endpoints, serializers shape API data, models define the database, and PostgreSQL stores user-owned learning data.

The most important rule in the architecture is user ownership:

```text
Every note, flashcard, quiz attempt, and progress record must resolve back to the authenticated user.
```

That rule is what turns NSync from a static prototype into a real account-based learning app.
