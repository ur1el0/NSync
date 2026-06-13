# Review Session Screen

Kotlin file: `mobile/app/src/main/java/com/example/mobile/ui/screens/review/ReviewSessionScreen.kt`  
Route: `Routes.REVIEW_SESSION`

## Purpose

`ReviewSessionScreen` shows the active quiz/review card flow. It displays a question first, then reveals the answer when the user presses Show Answer.

## Functions

- `ReviewSessionScreen(onCompleteClick)` is the main composable. It keeps `showAnswer` state and toggles between the question and answer views.

## Navigation

- Opened from Dashboard, Knowledge Detail, or Review Cards.
- The finish action calls `onCompleteClick`, which navigates to `Routes.SESSION_COMPLETE`.
- This screen intentionally does not show bottom navigation so the review session stays focused.

## Imports And Compose Pieces

- `remember` and `mutableStateOf` control whether the answer is visible.
- `Card`, `LinearProgressIndicator`, `Button`, `TextButton`, `IconButton`, `Column`, and `Row` build the review UI.
- `painterResource` loads the back arrow, XP icon, and profile/logo image.

## Shared Components And Styling

- Uses `SampleData.draftReviewCardQuestion` and `SampleData.draftReviewCardAnswer`.
- Uses theme colors and Inter typography directly in the screen.
- Current behavior is prototype-level: it reviews one sample card and then allows completion.
