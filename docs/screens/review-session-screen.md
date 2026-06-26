# Review Session Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/review/ReviewSessionScreen.kt`
**Routes:** `Routes.REVIEW_SESSION`, `Routes.REVIEW_SESSION_NOTE` with optional `noteId`

## Purpose and Data Flow

`ReviewSessionScreen` loads either all cards or cards for one note through `ReviewSessionViewModel.loadSession`. It first shows a question, reveals the answer locally, then records `Review again` or `Got it`. Each rating becomes a `ReviewAnswerDto`. When the last card is rated, the ViewModel sends the complete list to the API and exposes `ReviewSessionResult`.

## Functions and Imports

- `ReviewSessionScreen`: controls local `showAnswer`, observes `completedResult`, and calls `onCompleteClick(score, totalQuestions, xpEarned)`.
- `LaunchedEffect(noteId, cardId)`: loads the matching session.
- `LaunchedEffect(currentIndex)`: hides the answer for the next card.
- `CenteredCard` and `PrimaryScreenButton`: shared card and reveal action.
- Material `Button`/`OutlinedButton`, lazy layout, and `ReviewSessionViewModel`: answer controls, scrolling, and backend flow.

## Navigation

The screen has no bottom navigation so the review stays focused. Note-scoped sessions complete into `sessionComplete(score, totalQuestions, xpEarned, noteId)` so Review Again stays inside the same set.
