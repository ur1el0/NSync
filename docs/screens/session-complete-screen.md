# Session Complete Screen

Kotlin file: `mobile/app/src/main/java/com/example/mobile/ui/screens/review/SessionCompleteScreen.kt`  
Route: `Routes.SESSION_COMPLETE`

## Purpose

`SessionCompleteScreen` confirms that a review session finished and summarizes the result.

## Functions

- `SessionCompleteScreen(onDashboardClick)` is the main composable. It reads `SampleData.sessionSummary` and displays XP, cards reviewed, accuracy, and mastery progress.

## Navigation

- Opened from `ReviewSessionScreen` through `Routes.SESSION_COMPLETE`.
- The dashboard button calls `onDashboardClick`, which navigates back to `Routes.DASHBOARD`.

## Imports And Compose Pieces

- `Column`, `Text`, and `Spacer` build the result layout.
- The page is not wrapped in bottom navigation because it is part of the review flow.

## Shared Components And Styling

- Uses `SummaryMetric` for compact result values.
- Uses `ProgressSummaryCard` for the mastery summary.
- Uses `PrimaryScreenButton` for the dashboard action.
- Uses `ScreenHeroStyle`, `ScreenBodyStyle`, and shared screen styles.
