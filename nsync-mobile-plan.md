# NSync Mobile Development Plan

This document records the current Android implementation and the next technical priorities. It replaces the earlier static prototype plan.

## Implemented

1. Compose navigation with auth, knowledge, review, progress, profile, and settings routes.
2. JWT registration, login, refresh, logout, and session restoration.
3. Retrofit repositories for notes, flashcards, review completion, and progress.
4. Note CRUD and flashcard CRUD screens.
5. Multi-card review sessions grouped by the connected note.
6. Progress, mastery, profile, dashboard, local settings, and review reminders.

## Current Development Procedure

1. Start PostgreSQL and Django from `backend/`.
2. Run `adb reverse tcp:8000 tcp:8000` for an Android emulator.
3. Build or run `mobile/app`.
4. Register or log in.
5. Exercise note, flashcard, review, and progress flows against the backend.
6. Run backend tests and Kotlin compilation before committing.

Detailed commands are in [readme.md](readme.md).

## Next Priorities

1. Replace remaining legacy presentation placeholders with authenticated API data where they still affect real screens.
2. Add automated Android unit and UI tests for repositories, ViewModels, and navigation.
3. Improve error messages and loading/retry states across all API-backed screens.
4. Add backend support for password reset/change and connect it to Settings.
5. Add stronger token storage and a production configuration path.
6. Refine visual design after functional flows are stable.

## Source Map

```text
mobile/app/src/main/java/com/example/mobile/
  data/          DTOs, Retrofit, repositories, DataStore
  navigation/    route constants and NavHost
  notifications/ reminders and notification receiver
  ui/components/ reusable Compose UI
  ui/screens/    feature-specific Compose screens
  ui/state/      immutable screen state data classes
  ui/theme/      colors, typography, and shared styles
  ui/viewmodel/  screen actions and asynchronous state
```

See [docs/android-architecture.md](docs/android-architecture.md) and [docs/function-reference.md](docs/function-reference.md) for the detailed implementation reference.
