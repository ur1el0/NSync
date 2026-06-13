# Dashboard Screen

Kotlin file: `mobile/app/src/main/java/com/example/mobile/ui/screens/dashboard/DashboardScreen.kt`  
Route: `Routes.DASHBOARD`

## Purpose

`DashboardScreen` is the main home page after login. It shows the NSync top navigation, greeting, level progress, streak and accuracy stats, review actions, and the most recent knowledge item.

## Functions

- `DashboardScreen(onStartReviewClick, onKnowledgeClick, onAddClick, onRouteClick)` is the main composable. It reads `SampleData.userProfile` and `SampleData.dashboardRecentKnowledge`, lays out the dashboard, and wires buttons to navigation callbacks.
- `DashboardTopNav()` draws the top row with the NSync wordmark and user logo.
- `DashboardGreeting(name)` displays the personalized greeting and subtitle.
- `LevelProgressCard(user)` shows level, XP, target level, and a progress bar.
- `DashboardStatsRow(streakDays, accuracyPercent)` places the two stat cards side by side.
- `StatCard(iconRes, iconTint, value, label)` is the reusable small metric card.
- `DashboardActions(onStartReviewClick, onAddClick)` draws Start Review and add buttons.
- `RecentKnowledgeSection(item, onKnowledgeClick)` displays the latest knowledge item card.
- `InfoPill(text)` renders small chips such as card count and XP.
- `formatNumber(value)` formats XP numbers with commas.

## Navigation

- Start Review navigates to `Routes.REVIEW_SESSION`.
- The recent knowledge card navigates to `Routes.KNOWLEDGE_DETAIL`.
- The add button navigates to `Routes.REVIEW_CARDS`.
- Bottom navigation calls `onRouteClick`, which is handled by `AppNavigation`.

## Imports And Compose Pieces

- `Scaffold` is provided indirectly through `MainScreenScaffold`, which gives the page its shared background and bottom nav.
- `Card`, `Icon`, `Image`, `LinearProgressIndicator`, `Row`, `Column`, and `Text` build the dashboard sections.
- `painterResource` loads dashboard icons and the user logo.

## Shared Components And Styling

- Uses `MainScreenScaffold` for the main page wrapper and bottom navigation.
- Uses `BottomNavBar` through the scaffold.
- Uses `SampleData` for static profile and recent knowledge data.
- Uses theme colors and local dashboard styles such as `DashboardHeadlineStyle`, `DashboardActionStyle`, and `DashboardKnowledgeTitleStyle`.
