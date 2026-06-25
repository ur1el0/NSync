# NSync Demo Guide

This guide explains how to present NSync from the early static prototype stage to the current full-stack build. It also includes a Jetpack Compose component reference and an API walkthrough using `backend/tests.http` and the Django REST Framework browsable API.

## 1. App Introduction

NSync is a gamified personal knowledge and memory app.

The app helps users:

- Capture important ideas as notes.
- Create flashcards from those notes.
- Review flashcards in quiz-style sessions.
- Track progress through XP, levels, streaks, accuracy, and mastery.

The simple product flow is:

```text
Capture knowledge -> Create review cards -> Review -> Earn XP -> Track mastery
```

The current version is built with:

- Kotlin and Jetpack Compose for the Android app.
- Django REST Framework for the backend API.
- PostgreSQL in Docker for persistent storage.
- JWT authentication for user accounts.
- Retrofit, OkHttp, and DataStore for Android API/session handling.

## 2. Before: The 50% Static Prototype

At the 50% stage, NSync was mainly a visual and navigation prototype.

What already existed:

- Login and Register screens.
- Dashboard screen.
- Knowledge Base screen.
- Review Cards screen.
- Review Session screen.
- Session Complete screen.
- Mastery screen.
- Profile screen.
- Bottom navigation.
- Static sample data inside the Android project.

What this meant:

- The app could show the intended UI.
- The user could move between screens.
- The design matched the prototype more closely over time.
- But the app was not yet a real account-based system.

Main limitation:

```text
The UI existed, but the data was mostly static.
```

For example:

- Notes came from sample data.
- Flashcards were not fully persisted.
- User progress was mostly mocked.
- Login/register screens did not yet create real authenticated users.
- Review sessions did not fully update backend progress.

## 3. Now: Current Full-Stack Build

The project has moved from a static prototype to a working full-stack app.

Current major changes:

- Django backend was added.
- PostgreSQL database was added through Docker Compose.
- JWT authentication was added.
- Android login/register now connect to the backend.
- Notes are created, loaded, edited, and deleted through the API.
- Flashcards are connected to notes.
- Review sessions can contain multiple cards.
- Review completion updates XP, accuracy, streak, and level.
- Dashboard, Profile, and Mastery now use progress-related data.
- Settings and local review reminders were added.
- Documentation was expanded.

Current app architecture:

```text
Compose Screen
  -> ViewModel
  -> Repository
  -> Retrofit API
  -> AuthInterceptor
  -> Django REST API
  -> PostgreSQL
```

## 4. Demo Setup Checklist

Before demoing, run the backend and mobile app.

### Backend

From the repository root:

```bash
cd backend
source .venv/bin/activate
docker compose up -d db
python manage.py migrate
python manage.py runserver
```

Backend URL:

```text
http://127.0.0.1:8000/
```

API base URL:

```text
http://127.0.0.1:8000/api/
```

### Android Emulator

If using an emulator, reverse the Django port:

```bash
$ANDROID_HOME/platform-tools/adb reverse tcp:8000 tcp:8000
```

Then run the Android app from Android Studio.

## 5. Demo Flow

Use this sequence when presenting the app.

### Step 1: Introduce NSync

Say:

> NSync is a personal knowledge and memory app. It lets users save notes, turn them into flashcards, review those cards, and track learning progress through XP, streaks, accuracy, and mastery.

Show:

- Login screen.
- App name and visual style.

Explain:

- The original version focused on Compose UI and prototype matching.
- The current version connects the UI to a real Django backend.

### Step 2: Register or Log In

Show:

- Register screen.
- Login screen.

Explain:

- The app now uses real authentication.
- Register and login call the Django API.
- The backend returns JWT access and refresh tokens.
- The Android app stores the session locally using DataStore.
- The app restores the session when reopened.

Related files:

- `LoginScreen.kt`
- `RegisterScreen.kt`
- `AuthViewModel.kt`
- `AuthRepository.kt`
- `AuthSessionStore.kt`
- `AuthInterceptor.kt`
- `ApiService.kt`

### Step 3: Show Dashboard

Show:

- Dashboard screen after login.

Explain:

- Dashboard shows the user's level, XP, streak, accuracy, and recent knowledge.
- Earlier, these values were static.
- Now the app can load progress data from the backend.

Related files:

- `DashboardScreen.kt`
- `DashboardViewModel.kt`
- `DashboardUiState.kt`
- `NSyncRepository.kt`

### Step 4: Open Knowledge Base

Show:

- Knowledge Base tab.
- Existing notes if available.
- Empty state if there are no notes.

Explain:

- Notes are now loaded from `/api/notes/`.
- Each user only sees their own notes.
- This proves the app is no longer just using static sample data.

Related files:

- `KnowledgeBaseScreen.kt`
- `KnowledgeBaseViewModel.kt`
- `KnowledgeBaseUiState.kt`
- `NoteDtos.kt`

### Step 5: Create a New Note

Show:

- Tap New Note.
- Fill title, collection/tag/source/context/content.
- Save the note.

Explain:

- The Android app sends a POST request to the backend.
- Django saves the note with the current user as owner.
- The note appears in the Knowledge Base after saving.

Related files:

- `NewNoteScreen.kt`
- `NewNoteViewModel.kt`
- `CreateNoteRequestDto` inside `NoteDtos.kt`
- `NoteViewSet` in `backend/core/api_views.py`

### Step 6: Open Note Details

Show:

- Tap a note.
- Show note details.
- Show edit/delete options.
- Show add review card option.

Explain:

- The note detail screen loads one specific note by ID.
- Edit and delete actions also call the API.
- This is the CRUD part of the app.

Related files:

- `KnowledgeDetailScreen.kt`
- `KnowledgeDetailViewModel.kt`
- `KnowledgeDetailUiState.kt`

### Step 7: Add Flashcards

Show:

- Add a flashcard connected to the note.
- Enter a question and answer.
- Save it.

Explain:

- Flashcards are connected to a source note.
- The backend rejects creating a flashcard in another user's note.
- This keeps learning data private per account.

Related files:

- `NewFlashcardScreen.kt`
- `NewFlashcardViewModel.kt`
- `FlashcardDtos.kt`
- `FlashcardViewSet` in `backend/core/api_views.py`

### Step 8: Show Review Cards / Sessions

Show:

- Review Cards tab.
- Related cards grouped into review sessions.

Explain:

- The early version treated review cards more like static cards.
- The current version groups cards into sessions based on related data such as note, tag, title, or collection.
- The session contains the cards; the list screen is used to choose which session to review.

Related files:

- `ReviewCardsScreen.kt`
- `ReviewCardsViewModel.kt`
- `ReviewSessionListItem.kt`
- `ReviewCardListItem.kt`

### Step 9: Complete Review Session

Show:

- Start a session.
- Reveal the answer.
- Rate recall.
- Move through multiple cards.
- Complete the session.

Explain:

- The session uses multiple cards.
- The app tracks the score, total questions, and XP earned.
- On completion, the app sends the result to `/api/review/complete/`.

Related files:

- `ReviewSessionScreen.kt`
- `ReviewSessionViewModel.kt`
- `ReviewDtos.kt`

### Step 10: Show Session Complete

Show:

- Session Complete screen.
- Score.
- Accuracy.
- XP earned.
- Streak.
- Level progress.

Explain:

- This page summarizes the review.
- The backend updates the user's progress.
- Progress can then be shown in Dashboard, Profile, and Mastery.

Related files:

- `SessionCompleteScreen.kt`
- `SessionCompleteViewModel.kt`
- `SessionCompleteUiState.kt`

### Step 11: Show Mastery and Profile

Show:

- Mastery tab.
- Profile tab.

Explain:

- Mastery summarizes learning performance by source note or collection.
- Profile shows user progress and account-related information.
- These screens moved from static prototype displays toward API-connected progress data.

Related files:

- `MasteryScreen.kt`
- `MasteryViewModel.kt`
- `MasteryUiState.kt`
- `ProfileScreen.kt`
- `ProfileViewModel.kt`
- `ProfileUiState.kt`

### Step 12: Show Settings

Show:

- Settings screen.
- Daily review goal.
- Reminder time.
- Difficulty.
- Streak reminders.
- Notifications.

Explain:

- Settings are local app preferences.
- Reminder settings can schedule Android notifications.
- Forgot password/change password are still planned items.

Related files:

- `SettingsScreen.kt`
- `SettingsPreferences.kt`
- `SettingsComponents.kt`
- `ReviewReminderScheduler.kt`
- `ReviewReminderReceiver.kt`

## 6. Recent Changes Summary

Use this as a quick explanation of what happened after the 50% version.

### UI and structure

- Split screens into feature folders.
- Moved reusable UI into components.
- Added Inter font.
- Matched more screens to the prototype.
- Added Settings screen.
- Improved Login, Profile, Review Cards, and Session Complete layouts.

### Backend

- Added Django REST Framework API.
- Added PostgreSQL through Docker Compose.
- Added models, serializers, API views, and routes.
- Added migrations.
- Added tests.

### Authentication

- Added JWT register/login/refresh/logout/me endpoints.
- Added Android session storage.
- Added auth-aware navigation.
- Added token interceptor.
- Added session restore.

### Knowledge

- Added API-connected notes.
- Added create, read, update, and delete.
- Scoped notes by authenticated user.

### Flashcards and review

- Added API-connected flashcards.
- Connected flashcards to notes.
- Added multi-card review sessions.
- Added review completion.
- Added recall outcomes.

### Progress

- Added XP updates.
- Added accuracy.
- Added streak tracking.
- Added level calculation.
- Added mastery aggregation.

## 7. Jetpack Compose Functions and Components Used

This section lists the main Jetpack Compose functions/classes used in the app and where they appear.

### `@Composable`

What it does:

- Marks a function as a Compose UI function.
- Allows the function to emit UI.

Where used:

- All screen files, including `LoginScreen`, `DashboardScreen`, `KnowledgeBaseScreen`, `ReviewSessionScreen`, `ProfileScreen`, and `SettingsScreen`.
- Shared components such as `BottomNavBar`, `AuthTextField`, `MainScreenScaffold`, and `SettingsComponents`.

### `MaterialTheme`

What it does:

- Provides app-wide Material styling such as colors and typography.

Where used:

- `Theme.kt`

### `Scaffold`

What it does:

- Provides a standard Material layout structure with slots such as bottom bar and content.

Where used:

- `MainScreenScaffold.kt`
- `DashboardScreen.kt`
- `NewNoteScreen.kt`
- Other main screens through shared layout wrappers.

### `NavHost`

What it does:

- Defines the navigation graph for Compose Navigation.

Where used:

- `AppNavigation.kt`

### `composable`

What it does:

- Registers one route/screen inside the navigation graph.

Where used:

- `AppNavigation.kt`

### `rememberNavController`

What it does:

- Creates and remembers the navigation controller used to move between screens.

Where used:

- `AppNavigation.kt`

### `navArgument`

What it does:

- Defines route parameters, such as note IDs and flashcard IDs.

Where used:

- `AppNavigation.kt`
- Used for note detail, edit note, new flashcard, edit flashcard, review by note, and session complete routes.

### `LaunchedEffect`

What it does:

- Runs side effects tied to the Compose lifecycle.
- Useful for loading data when a screen appears or reacting to state changes.

Where used:

- `AppNavigation.kt` for session restore and auth redirects.
- Detail/edit screens for loading data by ID.
- Settings for loading local preferences.
- Review screens for loading session data.

### `DisposableEffect`

What it does:

- Runs setup and cleanup logic when a composable enters/leaves composition.

Where used:

- `MasteryScreen.kt`

### `remember`

What it does:

- Stores a value across recompositions.

Where used:

- Form fields, local UI state, repositories/factories in navigation, dropdown state, and screen interactions.

### `rememberSaveable`

What it does:

- Stores UI state across recomposition and configuration changes when possible.

Where used:

- `SettingsScreen.kt` for settings-related local state.

### `mutableStateOf`

What it does:

- Creates observable Compose state.
- When the value changes, Compose recomposes affected UI.

Where used:

- ViewModels for UI state.
- Form screens such as Login, Register, New Note, and Review Session.

### `mutableIntStateOf`

What it does:

- Optimized Compose state holder for `Int`.

Where used:

- `SettingsScreen.kt`

### `getValue` / `setValue`

What it does:

- Kotlin property delegates used with Compose state.

Where used:

- ViewModels and screen-local state.

### `Column`

What it does:

- Places children vertically.

Where used:

- Almost every screen: Login, Dashboard, New Note, Review Session, Profile, Settings.

### `Row`

What it does:

- Places children horizontally.

Where used:

- Dashboard stat rows, top bars, settings rows, review controls, profile sections.

### `Box`

What it does:

- Allows stacking children or aligning content inside one container.

Where used:

- Login logo/image layout.
- Dashboard avatar/icon containers.
- New Note and shared UI layouts.

### `LazyColumn`

What it does:

- Efficient vertical scrolling list.

Where used:

- Dashboard.
- Knowledge Base.
- Review Cards.
- Review Session.
- Main shared screen scaffold.

### `items`

What it does:

- Adds list items inside a `LazyColumn`.

Where used:

- Knowledge list, review session/card lists, mastery lists.

### `Spacer`

What it does:

- Adds empty space between UI elements.

Where used:

- Most screens for vertical and horizontal spacing.

### `Text`

What it does:

- Displays text.

Where used:

- All screens and most components.

### `Button`

What it does:

- Displays a filled action button.

Where used:

- Login.
- Register.
- New Note.
- Review Session.
- Session Complete.
- Review session list items.

### `OutlinedButton`

What it does:

- Displays a secondary outlined button.

Where used:

- New Note cancel action.
- Review Session secondary actions.
- Session Complete back-to-dashboard action.
- Review session list items.

### `Icon`

What it does:

- Displays a vector/drawable icon.

Where used:

- Bottom navigation.
- Dashboard stats.
- Settings rows.
- Auth text fields.

### `Image`

What it does:

- Displays bitmap/vector image resources.

Where used:

- Login branding/logo image.
- Dashboard user image/avatar.

### `Card`

What it does:

- Displays grouped content in a Material container.

Where used:

- Dashboard stat cards.
- Login form card.
- Knowledge list cards.
- Review card/session cards.
- Settings sections.

### `Surface`

What it does:

- Provides a Material container with color, shape, and elevation behavior.

Where used:

- Dashboard icon/avatar styling and related UI containers.

### `OutlinedTextField`

What it does:

- Displays an editable text input with an outline.

Where used:

- `AuthTextField.kt`
- Login/Register input fields.
- New Note form fields.
- New Flashcard form fields.

### `DropdownMenu`

What it does:

- Displays a popup menu of selectable options.

Where used:

- `SettingsComponents.kt`

### `DropdownMenuItem`

What it does:

- Displays one option inside a dropdown.

Where used:

- `SettingsComponents.kt`

### `Switch`

What it does:

- Displays an on/off toggle.

Where used:

- Settings toggles for reminders and notifications.

### `HorizontalDivider`

What it does:

- Draws a horizontal line between sections or rows.

Where used:

- `SettingsComponents.kt`

### `LinearProgressIndicator`

What it does:

- Displays progress as a horizontal bar.

Where used:

- Dashboard level progress.
- Session Complete level progress.
- Mastery/progress-related UI.

### `Modifier`

What it does:

- Applies layout, styling, click behavior, size, padding, and drawing behavior to Compose UI.

Where used:

- Everywhere in Compose UI files.

Common modifiers used:

- `fillMaxSize`
- `fillMaxWidth`
- `padding`
- `height`
- `width`
- `size`
- `background`
- `clickable`
- `clip`
- `border`
- `shadow`
- `offset`

### `painterResource`

What it does:

- Loads drawable resources for icons/images.

Where used:

- Login image.
- Bottom navigation icons.
- Dashboard/profile/settings icons.

### `ContentScale`

What it does:

- Controls how an image fills its bounds.

Where used:

- Login and dashboard image rendering.

### `TextStyle`, `FontWeight`, `sp`, `dp`

What they do:

- `TextStyle` defines text appearance.
- `FontWeight` controls font thickness.
- `sp` is used for font sizes.
- `dp` is used for layout sizes and spacing.

Where used:

- Theme files.
- Screen typography.
- Reusable component styling.

## 8. API Walkthrough Using `tests.http`

Use `backend/tests.http` to demo the backend with VS Code REST Client or a similar HTTP client.

Base variable:

```http
@baseUrl = http://127.0.0.1:8000/api
```

### Step 1: Health Check

Request:

```http
GET {{baseUrl}}/health/
```

Expected result:

```json
{
  "status": "ok"
}
```

Purpose:

- Confirms the Django server is running.
- Does not require authentication.

Backend function:

- `health_check` in `backend/core/api_views.py`

### Step 2: Register User A

Request:

```http
POST {{baseUrl}}/auth/register/
Content-Type: application/json

{
  "display_name": "User A",
  "email": "user-a@example.com",
  "password": "SecurePassword123"
}
```

Expected result:

- `201 Created`
- Response includes:
  - `access`
  - `refresh`
  - `user`

What to do after:

- Copy the `access` token into `@userAAccessToken`.
- Copy the `refresh` token into `@userARefreshToken`.

Backend function:

- `register`

Serializer:

- `RegisterSerializer`

### Step 3: Register User B

Request:

```http
POST {{baseUrl}}/auth/register/
Content-Type: application/json

{
  "display_name": "User B",
  "email": "user-b@example.com",
  "password": "SecurePassword123"
}
```

Expected result:

- `201 Created`
- Response includes User B tokens.

What to do after:

- Copy User B's access token into `@userBAccessToken`.

Purpose:

- User B is used to prove that User A's data is private.

### Step 4: User A Creates a Note

Request:

```http
POST {{baseUrl}}/notes/
Authorization: Bearer {{userAAccessToken}}
Content-Type: application/json

{
  "title": "User A private note",
  "content": "Only User A should access this.",
  "tag": "Testing"
}
```

Expected result:

- `201 Created`
- Response includes the created note.

What to do after:

- Copy the created note `id` into `@userANoteId`.

Backend class:

- `NoteViewSet`

Important backend behavior:

- `perform_create()` saves `owner=request.user`.

### Step 5: User B Lists Notes

Request:

```http
GET {{baseUrl}}/notes/
Authorization: Bearer {{userBAccessToken}}
```

Expected result:

- User A's note should not appear.

Purpose:

- Demonstrates per-user note scoping.

Backend behavior:

- `NoteViewSet.get_queryset()` filters notes by `owner=self.request.user`.

### Step 6: User B Tries to Read User A's Note

Request:

```http
GET {{baseUrl}}/notes/{{userANoteId}}/
Authorization: Bearer {{userBAccessToken}}
```

Expected result:

- `404 Not Found`

Purpose:

- Confirms users cannot access another user's note by guessing the ID.

### Step 7: User B Tries to Create a Flashcard in User A's Note

Request:

```http
POST {{baseUrl}}/flashcards/
Authorization: Bearer {{userBAccessToken}}
Content-Type: application/json

{
  "connected_note": {{userANoteId}},
  "question": "Should this work?",
  "answer": "No."
}
```

Expected result:

- `403 Forbidden`

Purpose:

- Confirms users cannot attach flashcards to notes they do not own.

Backend class:

- `FlashcardViewSet`

Important backend behavior:

- `perform_create()` checks whether `connected_note.owner == request.user`.

### Step 8: User A Completes a Review

Request:

```http
POST {{baseUrl}}/review/complete/
Authorization: Bearer {{userAAccessToken}}
Content-Type: application/json

{
  "score": 2,
  "total_questions": 3,
  "xp_earned": 75
}
```

Expected result:

- `201 Created`
- Response includes:
  - `attempt`
  - `progress`

Purpose:

- Creates a quiz attempt.
- Updates User A's progress.

Backend function:

- `complete_review`

Backend updates:

- `total_xp`
- `total_reviews`
- `correct_reviews`
- `accuracy`
- `streak`
- `last_reviewed_on`
- `level`

### Step 9: User B Completes a Review

Request:

```http
POST {{baseUrl}}/review/complete/
Authorization: Bearer {{userBAccessToken}}
Content-Type: application/json

{
  "score": 1,
  "total_questions": 2,
  "xp_earned": 25
}
```

Expected result:

- User B gets separate progress from User A.

Purpose:

- Confirms progress is scoped by authenticated user.

### Step 10: Check User A Progress

Request:

```http
GET {{baseUrl}}/progress/
Authorization: Bearer {{userAAccessToken}}
```

Expected result:

- User A's XP, review count, accuracy, streak, and level.

Backend function:

- `progress_detail`

### Step 11: Check User B Progress

Request:

```http
GET {{baseUrl}}/progress/
Authorization: Bearer {{userBAccessToken}}
```

Expected result:

- User B's separate XP, review count, accuracy, streak, and level.

Purpose:

- Demonstrates that progress is not shared across users.

### Step 12: User A Logs Out

Request:

```http
POST {{baseUrl}}/auth/logout/
Authorization: Bearer {{userAAccessToken}}
Content-Type: application/json

{
  "refresh": "{{userARefreshToken}}"
}
```

Expected result:

- `204 No Content`

Purpose:

- Blacklists the refresh token.
- Ends the session on the backend side.

Backend function:

- `logout`

## 9. API Walkthrough Using DRF Browsable API

You can also demo the API in the browser.

Start Django:

```bash
cd backend
source .venv/bin/activate
python manage.py runserver
```

Open:

```text
http://127.0.0.1:8000/api/
```

Useful pages:

- `http://127.0.0.1:8000/api/health/`
- `http://127.0.0.1:8000/api/notes/`
- `http://127.0.0.1:8000/api/flashcards/`
- `http://127.0.0.1:8000/api/progress/`

Important note:

- Protected routes require authentication.
- In REST Client, authentication is passed with:

```http
Authorization: Bearer <access-token>
```

For the DRF browsable API, the easiest demo path is:

1. Use `tests.http` to register or log in.
2. Copy the access token.
3. Use an API client for protected requests.
4. Use the browser mainly to show the route exists and the DRF interface.

## 10. Demo Talking Points

Use these points when explaining the current version.

### Static to dynamic

Before:

- Screens showed prototype data.
- Sample data was hardcoded in the Android app.

Now:

- Notes, flashcards, and progress come from the backend.
- Data persists in PostgreSQL.
- Data is tied to the authenticated user.

### UI to full-stack

Before:

- The project mostly demonstrated Compose UI and navigation.

Now:

- The Android app talks to Django through Retrofit.
- JWT tokens secure protected requests.
- Review results update backend progress.

### Prototype to product flow

Before:

- Screens existed separately.

Now:

- The screens form a connected learning workflow:

```text
Login -> Dashboard -> Notes -> Flashcards -> Review -> Progress
```

## 11. Known Remaining Work

These are honest limitations to mention if asked:

- Forgot password is not fully implemented yet.
- Change password is not fully implemented yet.
- Some visual polish is still needed.
- Some profile/settings values may still be local or static.
- Android automated testing can be improved.
- Production deployment configuration is not finished.
- AI/Ollama card generation is intentionally not implemented yet.

## 12. Suggested Closing

Say:

> The main improvement from the 50% version is that NSync is no longer just a static Compose prototype. It now has real authentication, persistent user-owned data, note and flashcard CRUD, review sessions, progress updates, and a Django/PostgreSQL backend supporting the Android app.
