# Profile Screen

Kotlin file: `mobile/app/src/main/java/com/example/mobile/ui/screens/profile/ProfileScreen.kt`  
Route: `Routes.PROFILE`

## Purpose

`ProfileScreen` shows the user's profile, study stats, and a logout button.

## Functions

- `ProfileScreen(onRouteClick, onLogoutClick)` is the main composable. It reads `SampleData.userProfile`, shows user details, and wires Logout to the navigation callback.
- `ProfileStatCard(iconRes, iconTint, value, label)` draws a reusable stat card for streak and accuracy.

## Navigation

- Opened from bottom navigation through `Routes.PROFILE`.
- Logout calls `onLogoutClick`, which navigates to `Routes.LOGIN` and clears the app stack.
- Bottom navigation calls `onRouteClick`.

## Imports And Compose Pieces

- `Card`, `Image`, `Icon`, `Button`, `Column`, `Row`, `Text`, and `Spacer` build the profile layout.
- `painterResource` loads the profile image and the two stat icons reused from Dashboard.

## Shared Components And Styling

- Uses `MainScreenScaffold` for the shared app frame.
- Reuses dashboard-style stat icons: `ic_wind` and `ic_target`.
- Uses theme colors, Inter typography, and local profile layout values.
