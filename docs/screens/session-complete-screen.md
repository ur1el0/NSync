# Session Complete Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/review/SessionCompleteScreen.kt`
**Route:** `Routes.SESSION_COMPLETE` with score, totalQuestions, and xpEarned

## Purpose and Data Flow

The route carries the immediate session result. `SessionCompleteViewModel.loadProgress` then reloads persisted backend progress so the screen can display current streak, level, and accuracy rather than only local values.

## Functions

- `SessionCompleteScreen`: root composable; loads progress and renders summary/actions.
- `SessionCompleteTopBar`: dashboard back affordance and NSync branding.
- `SessionMetricCard`: score/accuracy metric presentation.
- `XpEarnedCard`: XP result card.
- `StreakCard`: streak and visual day-progress presentation.
- `LevelProgressSection`: calculates level range and renders a progress indicator.

## Important Imports

- Material `Scaffold`, `Card`, `Button`, `OutlinedButton`, `LinearProgressIndicator` and Compose scroll/layout APIs build the result layout.
- `BottomNavBar`, `SessionCompleteViewModel`, route constants, and theme imports provide navigation, data, and style.

## Navigation

- Review Again opens a new `REVIEW_SESSION`.
- Back to Dashboard clears/replaces the review path with `DASHBOARD`.
- Bottom navigation is rendered in the screen scaffold.
