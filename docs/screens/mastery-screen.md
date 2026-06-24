# Mastery Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/progress/MasteryScreen.kt`
**Route:** `Routes.MASTERY`

## Purpose and Data Flow

`MasteryViewModel.loadMastery` gets progress and review cards, then derives collection-level mastery data. The screen displays loading/error states, top metrics, and a `ProgressSummaryCard` for each collection.

## Functions and Imports

- `MasteryScreen(onRouteClick, viewModel)`: the single composable.
- `LaunchedEffect(Unit)`: starts mastery loading once per screen entry.
- `MainScreenScaffold`, `SummaryMetric`, and `ProgressSummaryCard`: shared page shell and presentation.
- `MasteryViewModel`, Compose `Text`, and theme styles: data and UI dependencies.

## Navigation

The screen is a bottom-navigation destination. `onRouteClick` is passed to `MainScreenScaffold`.
