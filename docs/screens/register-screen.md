# Register Screen

Kotlin file: `mobile/app/src/main/java/com/example/mobile/ui/screens/auth/RegisterScreen.kt`  
Route: `Routes.REGISTER`

## Purpose

`RegisterScreen` provides the create account UI. It collects name, email, and password using the same visual language as the login screen.

## Functions

- `RegisterScreen(onRegisterClick, onLoginClick)` is the main composable. It stores form text locally and calls `onRegisterClick(name, email, password)` when the create account button is pressed.
- `RegisterLabel(text)` draws the small label above each input.

## Navigation

- Opened from Login through `Routes.REGISTER`.
- Register action navigates to `Routes.DASHBOARD`.
- Login link calls `onLoginClick`, which returns to the previous auth screen.

## Imports And Compose Pieces

- `remember` and `mutableStateOf` hold form state.
- `Column`, `Box`, `Text`, `Spacer`, and `Button` build the page.
- `Modifier` controls padding, fill width, size, and click behavior.

## Shared Components And Styling

- Uses `AuthTextField` for consistent input styling.
- Uses `NSyncBlue`, `NSyncCardWhite`, `InterFontFamily`, and local register text styles.
- The screen is self-contained because the auth layout differs from the main app scaffold.
