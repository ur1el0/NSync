# Profile Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/profile/ProfileScreen.kt`
**Route:** `Routes.PROFILE`

## Purpose and Data Flow

Profile combines static presentation fields currently sourced from `SampleData.userProfile` with live progress from `ProfileViewModel.loadProfile`. The UI constrains its cards/actions to a centered readable width and displays level, XP, review count, streak, and accuracy.

## Functions and Imports

- `ProfileScreen(onRouteClick, onSettingsClick, onLogoutClick, profileViewModel)`: loads progress on lifecycle resume and renders profile/actions.
- `ProfileStatCard`: reusable centered streak or accuracy card.
- Lifecycle `DisposableEffect`, `LifecycleEventObserver`, and `LocalLifecycleOwner`: refresh progress at `ON_RESUME`.
- `MainScreenScaffold`, Material cards/buttons, `SampleData`, `ProfileViewModel`, and theme imports: layout, action, identity placeholder, data, and visual design.

## Navigation

- Settings opens `SETTINGS`.
- Logout calls `AuthViewModel.logout` through `AppNavigation`; navigation then redirects to Login.
- Bottom navigation remains available through the scaffold.
