# Login Screen

Kotlin file: `mobile/app/src/main/java/com/example/mobile/ui/screens/auth/LoginScreen.kt`  
Route: `Routes.LOGIN`

## Purpose

`LoginScreen` is the first app screen. It matches the prototype login design with the NSync logo, app title, subtitle, email/password inputs, a primary login button, and a create account link.

## Functions

- `LoginScreen(onLoginClick, onRegisterClick)` is the main composable. It stores the email and password text with `remember` and calls `onLoginClick(email, password)` when Login is pressed.
- `AppLogo()` draws the rounded logo container and displays the app drawable inside it.

## Navigation

- Login is the start destination in `AppNavigation`.
- Successful login navigates to `Routes.DASHBOARD` and removes the login screen from the back stack.
- The create account text calls `onRegisterClick`, which navigates to `Routes.REGISTER`.

## Imports And Compose Pieces

- `@Composable` marks the functions that draw UI.
- `remember` and `mutableStateOf` keep temporary form state while the screen is visible.
- `Column`, `Box`, `Image`, `Text`, and `Spacer` build the layout.
- `Button` creates the primary Login action.
- `painterResource` loads the image from `res/drawable`.

## Shared Components And Styling

- Uses `AuthTextField` for the email and password fields.
- Uses `InterFontFamily`, `NSyncBlue`, and `NSyncCardWhite` from the theme.
- Uses local `TextStyle` values such as `LoginLabelStyle`, `LoginFooterStyle`, and `LoginCreateAccountStyle` for prototype-specific text.
