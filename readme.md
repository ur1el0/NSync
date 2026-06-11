# NSync

NSync is a gamified personal knowledge and memory app. It helps users capture important ideas, organize them into a knowledge base, create review cards, review through quiz-style sessions, and track mastery using XP, levels, streaks, accuracy, and progress.

The app is designed for students, self-learners, professionals, certification reviewers, interview preparation, work training, and other learning goals.

```text
Capture knowledge -> Create review cards -> Review -> Earn XP -> Track mastery
```

## Current Scope

This repository contains a Django backend and a Kotlin Android mobile app.

The current mobile goal is a 50% Jetpack Compose implementation that matches the finished prototype direction. It focuses on static sample data, navigation, and the main user interface.

Implemented or planned for the 50% mobile version:

- Login / create account UI
- Dashboard
- Knowledge Base
- Knowledge Detail
- Review Cards
- Review Session
- Session Complete
- Mastery / Progress
- User Profile
- Bottom navigation
- Static sample data

Not included yet:

- Real authentication
- Persistent database saving in mobile
- Django API connection
- OCR
- AI-generated review cards
- Full editable forms
- Persistent quiz scoring

## Prototype

The finished prototype frames are stored in:

```text
nsync-frames.pdf
```

The prototype presents NSync as a generalized knowledge review app, not only a student flashcard app.

## Project Structure

```text
NSync/
  backend/
    manage.py
    NSync/
    core/

  mobile/
    app/
      src/main/java/com/example/mobile/
        MainActivity.kt
        data/
        navigation/
        ui/
          components/
          screens/
          theme/

  nsync-context.md
  nsync-mobile-plan.md
  nsync-frames.pdf
  readme.md
```

## Android Mobile Plan

The Android app uses Kotlin and Jetpack Compose.

Recommended Kotlin structure:

```text
mobile/app/src/main/java/com/example/mobile/
|
|-- MainActivity.kt
|
|-- data/
|   |-- Models.kt
|   |-- SampleData.kt
|
|-- navigation/
|   |-- AppNavigation.kt
|   |-- Routes.kt
|
|-- ui/
|   |-- components/
|   |   |-- BottomNavBar.kt
|   |   |-- KnowledgeCard.kt
|   |   |-- ReviewCardItem.kt
|   |   |-- StatCard.kt
|   |   |-- MasteryCard.kt
|   |   |-- PrimaryActionButton.kt
|   |
|   |-- screens/
|   |   |-- AuthScreen.kt
|   |   |-- RegisterScreen.kt
|   |   |-- DashboardScreen.kt
|   |   |-- KnowledgeBaseScreen.kt
|   |   |-- KnowledgeDetailScreen.kt
|   |   |-- ReviewCardsScreen.kt
|   |   |-- ReviewSessionScreen.kt
|   |   |-- SessionCompleteScreen.kt
|   |   |-- MasteryScreen.kt
|   |   |-- ProfileScreen.kt
|   |   |-- SettingsScreen.kt
|   |
|   |-- theme/
|       |-- Color.kt
|       |-- Theme.kt
|       |-- Type.kt
```

Build order for the mobile app:

1. `Models.kt`
2. `SampleData.kt`
3. `Routes.kt`
4. `AppNavigation.kt`
5. `MainActivity.kt`
6. `BottomNavBar.kt`
7. `DashboardScreen.kt`
8. `KnowledgeBaseScreen.kt`
9. `ReviewCardsScreen.kt`
10. `ReviewSessionScreen.kt`
11. `MasteryScreen.kt`
12. `ProfileScreen.kt`

Then add:

- `KnowledgeDetailScreen.kt`
- `SessionCompleteScreen.kt`
- `RegisterScreen.kt`
- `SettingsScreen.kt`

## Backend

Run Django commands from the `backend/` directory:

```bash
cd backend
python manage.py runserver
```

The Django backend is planned for API and data persistence later. The current mobile phase does not require backend integration yet.

## Android Build

Run Android Gradle commands from the `mobile/` directory:

```bash
cd mobile
./gradlew :app:compileDebugKotlin
```

## Submission Notes

The final PDF submission should include:

- Brief app description
- App prototype
- Screenshots of the actual 50% Android interface/functionality
- Complete source code organized by Kotlin file
- Short purpose of each Kotlin file
- Midterm learning reflection

Suggested 50% functionality description:

```text
The Android version implements the main NSync interface using Jetpack Compose. It includes login/register UI, dashboard, knowledge base, review cards, review session, mastery tracking, profile, bottom navigation, and static sample data. Real authentication, persistent storage, Django API connection, OCR, and AI card generation are planned for future development.
```
