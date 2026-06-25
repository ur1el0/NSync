# NSync Progress Flow: From 50% Prototype to Current Build

This file is a simple reference for explaining what changed after the 50% version of NSync. Use it to build a presentation flow, demo script, or project progress timeline.

## Starting Point: 50% Prototype

At the 50% stage, NSync was mainly a Jetpack Compose prototype.

What existed:

- Login and register screens based on the prototype design.
- Dashboard UI with XP, level, streak, accuracy, and recent knowledge sections.
- Knowledge base screens.
- Review card and review session screens.
- Session complete screen.
- Profile and mastery screens.
- Bottom navigation.
- Static sample data in the Android app.
- Screen files split into clearer folders.
- Shared UI components for repeated layouts.

Main limitation:

- The app looked like the product idea, but most data was still local/static and not tied to real users or a backend.

## Phase 1: Code Organization and UI Cleanup

The first major improvement was cleaning up the Android project structure.

Changes made:

- Split large screen files into independent screen files.
- Moved screens into feature folders:
  - `auth`
  - `dashboard`
  - `knowledge`
  - `review`
  - `progress`
  - `profile`
  - `settings`
- Moved reusable UI into `ui/components`.
- Added shared layout wrappers and styling files.
- Standardized the app typography around Inter.
- Added reusable components such as:
  - `MainScreenScaffold`
  - `BottomNavBar`
  - `AuthTextField`
  - `KnowledgeListCard`
  - `ReviewCardListItem`
  - `ReviewSessionListItem`
  - `SettingsComponents`

Why it mattered:

- The project became easier to read.
- Each screen became easier to maintain.
- Shared UI stopped being duplicated across multiple pages.

## Phase 2: Backend Foundation

After the prototype, the project moved from static sample data toward a real backend.

Changes made:

- Added a Django backend.
- Added Django REST Framework.
- Added backend models for learning data.
- Added serializers for API input/output.
- Added API views and API routes.
- Added backend tests.
- Added `requirements.txt`.
- Added `.env.example`.
- Added Docker Compose for PostgreSQL.

Backend areas added:

- Notes
- Flashcards
- Review completion
- User progress
- Health check

Why it mattered:

- NSync stopped being only a frontend prototype.
- The app gained persistent data.
- Android could start loading real notes, flashcards, and progress from an API.

## Phase 3: PostgreSQL and Environment Configuration

The backend database moved to PostgreSQL using Docker.

Changes made:

- Added `docker-compose.yml` for the database.
- Added environment-based Django settings.
- Added `.env` support for local secrets.
- Added `.env.example` for safe configuration sharing.
- Configured PostgreSQL connection values:
  - database name
  - database user
  - password
  - host
  - port
- Ran migrations against PostgreSQL.

Why it mattered:

- The project now uses a real production-style database instead of relying only on SQLite/local test data.
- Database setup became repeatable.
- Secrets and local configuration are kept out of Git.

## Phase 4: JWT Authentication

Authentication was added to both backend and mobile.

Backend changes:

- Added JWT authentication using Simple JWT.
- Added auth endpoints:
  - register
  - login
  - refresh token
  - logout
  - current user
- Added protected API routes.
- Added per-user ownership checks.

Mobile changes:

- Added auth DTOs.
- Added `AuthSession` and `AuthSessionStore`.
- Stored access and refresh tokens with DataStore Preferences.
- Added `AuthRepository`.
- Added `AuthInterceptor` for attaching access tokens to API requests.
- Added token refresh handling.
- Added `AuthViewModel`.
- Added `AuthUiState`.
- Added `AuthViewModelFactory`.
- Connected Login and Register screens to real auth state.
- Updated navigation so protected screens require an authenticated session.

Why it mattered:

- Users can now create accounts and sign in.
- API data belongs to the signed-in user.
- The app can restore sessions after reopening.
- Expired access tokens can be refreshed instead of immediately logging the user out.

## Phase 5: API-Connected Knowledge Base

The knowledge base moved from static data to backend data.

Changes made:

- Added Retrofit API functions for notes.
- Added note DTOs.
- Added note repository functions.
- Added `KnowledgeBaseViewModel`.
- Added `KnowledgeBaseUiState`.
- Connected `KnowledgeBaseScreen` to live API data.
- Added create note flow.
- Added note detail flow.
- Added update note flow.
- Added delete note flow.

Why it mattered:

- Notes are now saved to the backend.
- Notes remain available after app restart.
- Notes are scoped to the signed-in account.

## Phase 6: Flashcards and Review Sessions

Flashcards became real backend-connected learning objects.

Changes made:

- Added flashcard DTOs.
- Added Retrofit API functions for flashcards.
- Added repository functions for flashcards.
- Added `ReviewCardsViewModel`.
- Added `NewFlashcardViewModel`.
- Added `FlashcardDetailViewModel`.
- Added flashcard create flow.
- Added flashcard detail/edit flow.
- Grouped flashcards into review sessions by related fields such as:
  - source note
  - title
  - tag
  - collection/category
- Added support for multiple cards inside a review session.

Why it mattered:

- Review is no longer a single static card.
- Flashcards now belong to actual notes.
- A review session can contain several related cards.

## Phase 7: Review Completion and Progress Tracking

Review sessions started affecting user progress.

Changes made:

- Added review completion API request/response DTOs.
- Added quiz attempt data.
- Added review outcome persistence.
- Added recall rating handling.
- Added `ReviewSessionViewModel`.
- Added `SessionCompleteViewModel`.
- Added `SessionCompleteUiState`.
- Updated session complete screen with:
  - score
  - accuracy
  - XP earned
  - streak
  - level progress
- Backend now updates progress after review completion.
- Backend tracks daily review streaks.
- Backend updates card mastery from review outcomes.

Why it mattered:

- Reviews now have consequences in the app.
- Completing cards updates XP, accuracy, streak, and mastery.
- The session complete page reflects real review results.

## Phase 8: Dashboard, Mastery, and Profile Became Data-Driven

The progress-related screens moved closer to live data.

Dashboard changes:

- Added `DashboardViewModel`.
- Added `DashboardUiState`.
- Loaded progress from the API.
- Updated dashboard stats from backend progress.

Mastery changes:

- Added `MasteryViewModel`.
- Added `MasteryUiState`.
- Aggregated mastery by collection/source note.
- Displayed mastery based on actual review/flashcard data.

Profile changes:

- Added `ProfileViewModel`.
- Added `ProfileUiState`.
- Connected profile progress numbers to app data.
- Centered and cleaned up profile screen layout.

Why it mattered:

- Progress screens became connected to the user's learning activity.
- The dashboard and mastery pages now reflect actual usage instead of only prototype values.

## Phase 9: Settings and Reminder Scheduling

Settings were added as a separate app area.

Changes made:

- Added `SettingsScreen`.
- Added `SettingsPreferences`.
- Added reusable settings components.
- Added dropdown-style settings controls.
- Added notification-related settings components.
- Added local review preference storage.
- Added Android review reminder scheduling:
  - `ReviewReminderScheduler`
  - `ReviewReminderReceiver`

Settings areas:

- Daily review goal
- Reminder time
- Review difficulty
- Streak reminders
- Notifications
- Appearance placeholder

Why it mattered:

- The app gained user preference controls.
- Review reminders support the habit-building goal of the app.

## Phase 10: Navigation and Session Protection

Navigation was updated to respect authentication.

Changes made:

- Added auth-aware navigation state.
- Login and register routes are available when logged out.
- Main app routes are protected when logged in.
- Logout clears the local session.
- App startup checks whether a stored session is still valid.
- Bottom navigation links were corrected.

Why it mattered:

- Users cannot access protected app data without signing in.
- The app behaves more like a real account-based product.

## Phase 11: Documentation Updates

Documentation was updated after the app became full-stack.

Changes made:

- Updated root `readme.md`.
- Updated `nsync-context.md`.
- Updated `nsync-mobile-plan.md`.
- Added documentation index in `docs/README.md`.
- Added Android architecture documentation.
- Added backend API documentation.
- Added function reference documentation.
- Added per-screen documentation files.
- Removed outdated 50% wording from current docs.

Why it mattered:

- The project now has setup instructions.
- The architecture is documented.
- Each screen and major function has an explanation.
- The project is easier to present and maintain.

## Current App Flow

Current user flow:

```text
Register/Login
  -> Dashboard
  -> Create Note
  -> Add Flashcards
  -> Start Review Session
  -> Reveal Answers
  -> Rate Recall
  -> Complete Session
  -> Update XP, Streak, Accuracy, and Mastery
  -> View Progress in Dashboard/Profile/Mastery
```

Current technical flow:

```text
Compose Screen
  -> ViewModel
  -> Repository
  -> Retrofit API
  -> AuthInterceptor adds JWT token
  -> Django REST API
  -> PostgreSQL
```

## Biggest Changes Since 50%

The most important changes are:

1. The app moved from static sample data to backend-connected data.
2. Authentication was added with JWT tokens.
3. User data became scoped to each authenticated account.
4. Notes became persistent CRUD objects.
5. Flashcards became connected to notes.
6. Review sessions now support multiple cards.
7. Review outcomes now update XP, accuracy, streak, and mastery.
8. PostgreSQL and Docker were added.
9. Settings and reminder scheduling were added.
10. The project documentation was expanded.

## What Is Still Not Fully Done

Remaining work:

- Forgot password screen and backend flow.
- Change password flow.
- Final visual polish for some screens.
- Replace remaining static profile/settings values with API-backed user data.
- Add stronger Android UI/integration tests.
- Prepare production deployment settings.
- Optional future AI/Ollama flashcard generation.

## Simple Presentation Flow

Use this order if you want to explain the project progress:

1. Start with the 50% prototype.
2. Explain the problem: the app looked right, but data was static.
3. Show the backend addition.
4. Show authentication and user-owned data.
5. Show notes CRUD.
6. Show flashcards connected to notes.
7. Show multi-card review sessions.
8. Show review completion updating progress.
9. Show dashboard, mastery, and profile using progress data.
10. Show settings and reminders.
11. End with what is left to finish.

## Short Summary

From the 50% version to now, NSync changed from a mostly static Jetpack Compose prototype into a working full-stack app. It now has Django REST APIs, PostgreSQL persistence, JWT authentication, protected user-owned data, note and flashcard CRUD, multi-card review sessions, review outcome tracking, progress updates, settings, reminders, and expanded documentation.
