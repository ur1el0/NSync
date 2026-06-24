# NSync Project Context

## Purpose

NSync is a gamified personal knowledge app. A signed-in user creates notes, attaches review cards to those notes, completes review sessions, and sees progress through XP, levels, streaks, accuracy, and mastery.

```text
Authenticate -> Capture a note -> Add review cards -> Review -> Persist outcomes -> Track progress
```

## Current Implementation

The repository contains a working Android client and a Django REST API.

- The Android app is written in Kotlin with Jetpack Compose.
- Django REST Framework exposes the API.
- PostgreSQL is supplied through Docker Compose.
- JWT access and refresh tokens authenticate the Android client.
- API data is scoped to the authenticated user.
- Notes and flashcards support create, read, update, and delete workflows.
- Review completion updates user progress and flashcard mastery.
- Settings persist local review preferences and can schedule Android reminders.

## Architecture

```text
Android Compose screens
  -> ViewModels
  -> Repositories
  -> Retrofit + AuthInterceptor
  -> Django REST API
  -> PostgreSQL
```

The Android client stores its session in DataStore Preferences. `AuthInterceptor` attaches the access token to protected requests. `AuthRepository` refreshes an expired session once, then clears the local session when re-authentication is required.

## Product Boundaries

Included now:

- Manual note and flashcard creation
- Note-linked review sessions
- Per-user progress and review results
- Authentication and logout
- Local settings and reminder scheduling

Not implemented:

- AI/Ollama card generation
- OCR or document import
- Offline-first synchronization
- Advanced spaced-repetition scheduling
- Password reset/change API flow
- Cloud push notifications

## Documentation

- [Repository README](readme.md): setup and development procedure.
- [Documentation index](docs/README.md): current technical documentation.
- [Android architecture](docs/android-architecture.md): client layers and request flow.
- [Backend API](docs/backend-api.md): models, endpoints, and ownership rules.
- [Function reference](docs/function-reference.md): source-file functions, classes, and imports.
- [Screen documentation](docs/screens/README.md): navigation and UI behavior for every Compose screen.
