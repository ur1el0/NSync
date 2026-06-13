# Mastery Screen

Kotlin file: `mobile/app/src/main/java/com/example/mobile/ui/screens/progress/MasteryScreen.kt`  
Route: `Routes.MASTERY`

## Purpose

`MasteryScreen` shows progress by collection so the user can see what areas are strong or need more review.

## Functions

- `MasteryScreen(onRouteClick)` is the main composable. It reads `SampleData.collectionMastery` and renders progress cards for each collection.

## Navigation

- Opened from bottom navigation through `Routes.MASTERY`.
- Bottom navigation calls `onRouteClick`.

## Imports And Compose Pieces

- `Column`, `Text`, and `Spacer` build the page.
- Static sample collection mastery is used until real scoring exists.

## Shared Components And Styling

- Uses `MainScreenScaffold` for the shared app layout.
- Uses `ProgressSummaryCard` for each collection.
- Uses `SummaryMetric` for top-level progress values.
- Uses shared screen styles from `ScreenStyles.kt`.
