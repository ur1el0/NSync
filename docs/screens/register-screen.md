# Register Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/auth/RegisterScreen.kt`
**Route:** `Routes.REGISTER`

## Purpose and Data Flow

`RegisterScreen` collects display name, email, and password. It dispatches the values to `AuthViewModel.register`, which calls `AuthRepository.register`, stores returned JWT tokens, and lets `AppNavigation` redirect to Dashboard.

## Functions

- `RegisterScreen(onRegisterClick, onLoginClick, isLoading, errorMessage)`: owns local field state, prevents blank/loading submission, and renders API validation errors.
- `RegisterLabel(text)`: renders the compact label used above each input.

## Important Imports

- Compose runtime state: temporary form values.
- Material 3 `Button`, `Card`, `Text`: form UI.
- `AuthTextField`: shared input styling and password support.
- Theme imports: Inter font, NSync colors, and local `TextStyle` values.

## Navigation

- Existing-account action invokes `onLoginClick`, which returns to Login.
- A successful registration produces an authenticated session and triggers the same Dashboard redirect as Login.
