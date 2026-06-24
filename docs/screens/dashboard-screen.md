# Dashboard Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/dashboard/DashboardScreen.kt`
**Route:** `Routes.DASHBOARD`

## Purpose and Data Flow

Dashboard is the authenticated home view. `DashboardViewModel.loadDashboard` loads user progress and recent knowledge through `NSyncRepository`; a lifecycle observer reloads it when the screen resumes.

## Functions

- `DashboardScreen`: renders loading/error/data states and connects review, knowledge, add, and bottom-nav actions.
- `DashboardTopNav`, `DashboardGreeting`: header sections.
- `LevelProgressCard`, `DashboardStatsRow`, `StatCard`: progress, streak, and accuracy presentation.
- `DashboardActions`, `RecentKnowledgeSection`, `InfoPill`: action and latest-note UI.
- `levelStartXp`, `nextLevelXp`, `formatNumber`: pure helpers for XP range/progress text.

## Important Imports

- Lifecycle `DisposableEffect` and `LifecycleEventObserver`: refresh on `ON_RESUME`.
- Compose layout/Material cards/progress controls: dashboard structure.
- `DashboardViewModel`, `UserProgressDto`, and shared screen components/styles: data and visual reuse.

## Navigation

- Start Review and add actions open `REVIEW_CARDS`.
- A recent note opens `knowledgeDetail(noteId)`.
- Bottom navigation is passed to `MainScreenScaffold` through `onRouteClick`.
