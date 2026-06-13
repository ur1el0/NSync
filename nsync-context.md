# NSync Project Context

This file captures the planning context for NSync from the ideation conversation so the project direction stays clear while building.

## Project Name

**NSync**

NSync is a gamified note-taking and flashcard review app for students. The app helps users write study notes, manually turn those notes into flashcards, review them in quiz-style sessions, and track progress using XP, levels, streaks, accuracy, and mastery.

## Core Idea

NSync combines the personal flashcard review idea of Anki with the game-like quiz feeling of Kahoot, but for a solo study workflow.

The central loop is:

```text
Create note -> create flashcard -> review or quiz -> gain XP -> track mastery
```

A student writes normal notes, converts important ideas into question-and-answer flashcards, reviews those cards, and receives progress feedback.

## Current Direction

The project started from a copied/simple Django NoteTaker project. Instead of continuing as a plain CRUD note app, NSync should evolve into a Django backend that later connects to a Kotlin Android app.

The current recommended approach is:

```text
Django first -> API second -> Kotlin Android later
```

The first priority is to make the Django data model and backend logic work before building the Android client.

## Current Android Prototype State

The Android app now has a Jetpack Compose static UI prototype in `mobile/`. This prototype is ahead of the original backend-first plan and is useful for the 50% mobile submission.

Current mobile implementation:

- Login and Register auth UI
- Dashboard with top nav, user logo, level progress, stats, review action, and recent knowledge
- Knowledge Base with a New Note button
- Knowledge Detail page
- New Note page with static form state
- Review Cards list
- Review Session with Show Answer / Hide Answer state
- Session Complete result page
- Mastery / Progress page
- Profile page with reused dashboard stat icons and Logout
- Bottom navigation for Dashboard, Knowledge, Flashcards, Mastery, and Profile
- Static prototype data in `SampleData.kt`

Current Android structure:

```text
mobile/app/src/main/java/com/example/mobile/
  MainActivity.kt
  data/
    Models.kt
    SampleData.kt
  navigation/
    AppNavigation.kt
    Routes.kt
  ui/
    components/
      AuthTextField.kt
      BottomNavBar.kt
      KnowledgeListCard.kt
      MainScreenScaffold.kt
      ReviewCardListItem.kt
      ScreenButtons.kt
      ScreenCards.kt
    screens/
      auth/
        LoginScreen.kt
        RegisterScreen.kt
      dashboard/
        DashboardScreen.kt
      knowledge/
        KnowledgeBaseScreen.kt
        KnowledgeDetailScreen.kt
        NewNoteScreen.kt
      profile/
        ProfileScreen.kt
      progress/
        MasteryScreen.kt
      review/
        ReviewCardsScreen.kt
        ReviewSessionScreen.kt
        SessionCompleteScreen.kt
    theme/
      Color.kt
      ScreenStyles.kt
      Theme.kt
      Type.kt
```

Detailed screen explanations are stored in `docs/screens/`.

## Architecture Plan

Recommended final architecture:

```text
Kotlin Android app -> Django REST API -> PostgreSQL
```

Local development can start with SQLite because Django provides it by default. PostgreSQL is recommended later for deployment and portfolio-quality backend work.

## Folder Plan

For now, keep the Django project layout as-is:

```text
NSync/
  manage.py
  NSync/
  core/
  readme.md
```

Do not move files into a `backend/` folder yet. The current layout is already normal for a Django-only project.

Later, when the Kotlin Android app is created, the structure can become:

```text
NSync/
  backend/
    manage.py
    NSync/
    core/
  mobile/
    app/
    build.gradle
```

The move to `backend/` and `mobile/` should happen after the Django backend is working and the Android project is ready to be added.

## V1 Scope

NSync v1 should stay focused on the core study loop.

### Included In V1

- Notes list
- Create note
- Edit note
- Delete note
- Note detail
- Flashcards linked to notes
- Manual flashcard creation
- Flashcard review mode
- Simple quiz mode
- Score result
- XP earned
- Level
- Streak
- Mastery level per flashcard
- Basic progress dashboard

### Excluded From V1

The following should not be built in v1:

- Authentication
- Leaderboard
- AI flashcard generation
- Offline sync
- Multiplayer/live quiz rooms
- Complex spaced repetition
- Push notifications

These are better saved for v2.

## V2 Ideas

Possible future features:

- User authentication
- AI note-to-flashcard generation
- Leaderboard
- Achievements and badges
- Offline mode with Room
- Cloud sync
- Spaced repetition scheduling
- Push notifications
- Better analytics
- Firebase for auth or notifications if needed, but not as the main database

## Database Decision

PostgreSQL is the better long-term database choice for NSync because the data is relational:

```text
Note -> many Flashcards
QuizAttempt -> quiz result history
UserProgress -> accumulated progress stats
```

Recommended path:

```text
v1 local development: SQLite
v1/v2 deployment: PostgreSQL
Firebase: optional later for auth/push notifications, not main DB
```

Firebase was considered, but because NSync uses Django, PostgreSQL fits the backend much more naturally.

## Main Backend Objects

NSync v1 needs four main backend concepts.

### Note

A study note written by the user.

Should store:

- title
- content
- tag or subject
- created date
- updated date

Logic:

```text
One note can have many flashcards.
```

### Flashcard

A question-and-answer card created from a note.

Should store:

- connected note
- question
- answer
- difficulty
- mastery level
- created date

Logic:

```text
One flashcard belongs to one note.
```

Recommended relationship name:

```text
note
```

Instead of:

```text
connected_note
```

This makes the model relationship easier to read:

```text
flashcard.note
note.flashcards
```

### QuizAttempt

A record of one quiz or review session.

Should store:

- score
- total questions
- XP earned
- date taken

Important point:

```text
xp_earned should be one number, not an array.
```

It represents the total XP earned from one quiz session.

Example:

```text
score: 4
total_questions: 5
xp_earned: 67
```

The XP rules live in app logic, not as an array in the database.

### UserProgress

The current progress state for the app/user.

Should store:

- total XP
- level
- streak
- total reviews
- correct reviews
- accuracy

For v1 with no auth, this can represent the single local/test user's progress.

## About User/Auth

NSync v1 should not focus on authentication.

The copied NoteTaker project had a simple custom `User` model, but for NSync v1, the best direction is:

```text
Ignore or remove custom User for now.
Use a single local/test progress record.
Add real auth in v2.
```

When auth is added later, prefer Django's built-in auth user instead of a homemade `User` model.

## Flashcard Mastery Logic

Mastery should track how well the user knows a flashcard.

Recommended levels:

```text
0 = New
1 = Learning
2 = Familiar
3 = Mastered
```

Logic:

```text
Correct answer -> mastery increases
Wrong answer -> mastery decreases
```

Boundaries:

```text
Mastery cannot go below 0.
Mastery cannot go above 3.
```

Important Django note:

```text
models.IntegerField(min=1, max=4)
```

is not valid Django model field logic. Django does not use `min` and `max` arguments that way for model fields.

The limit should be handled with validators, choices, or quiz/review logic.

## Difficulty Logic

Flashcard difficulty is user-facing or manually assigned.

Suggested values:

```text
easy
medium
hard
```

Difficulty is different from mastery:

```text
Difficulty = how hard the card is intended to be
Mastery = how well the user currently knows it
```

## XP Logic

Do not store XP rules as an array in the model.

Store only the final XP result for a quiz attempt:

```text
QuizAttempt.xp_earned = total XP from one quiz
UserProgress.total_xp = accumulated XP overall
```

Suggested XP rules:

```text
Correct answer: +10 XP
Wrong answer: +2 XP
Complete quiz: +25 XP
Daily review: +50 XP
```

Example calculation:

```text
4 correct answers = 40 XP
1 wrong answer = 2 XP
completion bonus = 25 XP

total XP earned = 67 XP
```

XP rules should live in the quiz/review logic, not directly as model fields.

## Level Logic

Suggested level thresholds:

```text
Level 1: 0 XP
Level 2: 100 XP
Level 3: 250 XP
Level 4: 500 XP
Level 5: 1000 XP
```

After each quiz, the app should:

```text
Add XP earned to total XP
Check which level matches the new total XP
Update progress level
```

## Accuracy Logic

Accuracy can be calculated from:

```text
correct_reviews / total_reviews * 100
```

For example:

```text
correct_reviews = 8
total_reviews = 10
accuracy = 80%
```

If total reviews is zero, accuracy should be zero to avoid division by zero.

## Streak Logic

Streak should be simple in v1.

Basic idea:

```text
If user reviewed today -> keep or increase streak
If user missed a day -> reset streak
```

But streak can be implemented after XP, level, and mastery work.

Recommended priority:

```text
XP first
Level second
Mastery third
Streak last
```

## Django File Responsibilities

### models.py

Use this file to define what data exists.

Responsible for:

- Note
- Flashcard
- QuizAttempt
- UserProgress
- Relationships between models

Questions answered here:

```text
What data should exist?
How are notes connected to flashcards?
What does a quiz attempt save?
What does progress track?
```

### admin.py

Use this file to register models so they can be tested in Django admin.

This helps check:

```text
Can I create notes?
Can I create flashcards?
Can I inspect quiz attempts?
Can I edit progress?
```

### forms.py

Use this only if building temporary Django HTML pages.

Possible forms:

- Note form
- Flashcard form

Later, once Kotlin and DRF are used, forms become less important.

### views.py

Use this for page logic or app actions.

Responsible for logic like:

- show notes list
- create note
- create flashcard
- start review
- submit answer
- calculate XP
- update mastery
- update progress
- show result

### urls.py

Use this to connect routes to views.

Example route ideas:

```text
/notes/
/notes/create/
/notes/<id>/
/flashcards/create/
/review/
/progress/
```

### serializers.py

Add this when starting Django REST Framework.

Serializers convert model data into JSON and validate incoming API data.

### api_views.py

Optional but recommended when using DRF.

This keeps API logic separate from HTML page views.

Suggested separation:

```text
views.py = HTML views
api_views.py = JSON API views for Kotlin
```

## DRF Plan

DRF means Django REST Framework.

It should be added after the models are clear.

DRF is the bridge between:

```text
Django database <-> Kotlin Android app
```

Without DRF, Django mainly returns HTML pages.

With DRF, Django returns JSON.

Example JSON note:

```json
{
  "id": 1,
  "title": "Django Basics",
  "content": "Django is a Python web framework",
  "tag": "Backend"
}
```

DRF will allow:

```text
Kotlin asks for notes -> Django sends JSON notes
Kotlin creates note -> Django saves it
Kotlin creates flashcard -> Django saves it
Kotlin submits quiz result -> Django updates XP/progress
```

Recommended DRF build order:

```text
1. models.py
2. migrations
3. admin.py
4. serializers.py
5. API views
6. API urls
7. test API
8. Kotlin Retrofit connection
```

Suggested first API endpoints:

```text
GET    /api/notes/
POST   /api/notes/
GET    /api/notes/<id>/
PUT    /api/notes/<id>/
DELETE /api/notes/<id>/
```

Then add:

```text
GET    /api/flashcards/
POST   /api/flashcards/
PUT    /api/flashcards/<id>/review/
POST   /api/quiz/submit/
GET    /api/progress/
```

## Kotlin Android Plan

The Kotlin Android app will eventually be the main user-facing product.

Recommended Android stack:

- Kotlin
- Jetpack Compose
- Retrofit
- ViewModel
- Coroutines
- Room later for offline support

Possible Android package structure:

```text
app/
  data/
    api/
      ApiService.kt
    models/
      Note.kt
      Flashcard.kt
      UserProgress.kt
    repository/
      StudyRepository.kt
  ui/
    dashboard/
    notes/
    flashcards/
    quiz/
    profile/
  viewmodel/
    NoteViewModel.kt
    QuizViewModel.kt
    ProgressViewModel.kt
  MainActivity.kt
```

Planned screens:

- Splash/login placeholder, later optional
- Dashboard
- Notes list
- Create note
- Note detail
- Flashcard editor
- Flashcard review mode
- Quiz mode
- Result screen
- Profile/progress

For v1, auth screens can be skipped or just treated as a placeholder.

## UI/Design Prompt Context

A Stitch prompt was created for designing NSync screens.

Design direction:

- Mobile app UI for NSync
- Gamified note-taking and flashcard review app
- Student productivity plus light gamification
- Focused, useful, slightly playful, not childish
- Kotlin Android / Jetpack Compose feel
- Bottom navigation tabs:
  - Dashboard
  - Notes
  - Review
  - Progress

Important screens for design:

1. Dashboard
2. Notes list
3. Create note
4. Note detail
5. Flashcard review
6. Quiz question
7. Quiz result
8. Progress/profile

Suggested dashboard content:

- Welcome back
- Level 3
- 240 XP
- 5 day streak
- 18 cards reviewed
- 82% accuracy
- Continue Review button
- Recent notes section

Sample note:

```text
Title: Django Basics
Content preview: Django is a Python web framework used for building web applications quickly.
Tags: Django, Backend
```

Sample flashcard:

```text
Question: What is Django?
Answer: A Python web framework used for building web applications quickly.
```

## Build Order

Recommended development order:

```text
1. Finalize Django models
2. Make migrations
3. Migrate database
4. Register models in admin
5. Test creating notes and flashcards in admin
6. Build simple Django views or go straight to DRF
7. Add serializers
8. Add API views
9. Add API URLs
10. Test API with Postman, Thunder Client, or curl
11. Build Kotlin UI with dummy data
12. Connect Kotlin to Django with Retrofit
13. Add XP, level, mastery, streak updates
14. Polish mobile UI
```

## Immediate Next Checkpoints

### Checkpoint 1

```text
I can create and view notes.
```

### Checkpoint 2

```text
I can create flashcards connected to notes.
```

### Checkpoint 3

```text
I can review flashcards.
```

### Checkpoint 4

```text
The app calculates score and XP.
```

### Checkpoint 5

```text
The dashboard shows progress.
```

### Checkpoint 6

```text
The backend is ready to become an API for Kotlin.
```

## Current Model Review Notes

The current model file already has the right main objects:

- User
- Note
- Flashcard
- QuizAttempt
- UserProgress

Current strengths:

- `Note` has title, content, tag, created_at, updated_at.
- `QuizAttempt` has score, total_questions, xp_earned, date_taken.
- `UserProgress` has total_xp, level, streak, total_reviews, correct_reviews, accuracy.

Recommended adjustments:

- Do not focus on custom `User` for v1.
- Prefer `note` over `connected_note` for the Flashcard relationship name.
- Mastery should start at 0 and use 0-3, not 1-4.
- Do not use `min` and `max` arguments directly in `IntegerField`.
- XP rules should be calculated in logic, while `xp_earned` stores the final result.

## Project Description

NSync is a Kotlin and Django-based gamified note-taking app that helps students turn their notes into flashcards and quiz reviews. V1 focuses on the core solo study loop: creating notes, making flashcards, answering review questions, and tracking progress through XP, levels, streaks, accuracy, and mastery.
