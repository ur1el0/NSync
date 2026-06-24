# Login Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/auth/LoginScreen.kt`
**Route:** `Routes.LOGIN`

## Purpose and Data Flow

`LoginScreen` collects an email and password, then sends them to `AuthViewModel.login` through `onLoginClick`. The ViewModel calls `AuthRepository.login`, saves the JWT session, and `AppNavigation` redirects the authenticated user to Dashboard.

## Functions

- `LoginScreen(onLoginClick, onRegisterClick, isLoading, errorMessage)`: owns temporary email/password field state, disables invalid or loading submissions, renders backend errors, and opens Register.
- `AppLogo()`: renders the rounded NSync logo surface using `Image` and `painterResource`.

## Important Imports

- Compose `remember` and `mutableStateOf`: form-only state.
- Foundation `Column`, `Box`, `Spacer`, `Image`, and modifiers: layout/logo.
- Material 3 `Button`, `Card`, and `Text`: controls and form surface.
- `AuthTextField`: shared styled email/password input.
- `InterFontFamily`, `NSyncBlue`, and `NSyncCardWhite`: local visual style.

## Navigation

- Create account calls `onRegisterClick`, which navigates to `REGISTER`.
- Successful login is handled by auth-session state in `AppNavigation`, which clears the auth route and opens `DASHBOARD`.
