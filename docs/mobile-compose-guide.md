# NSync Jetpack Compose Guide

This guide explains the Compose and Android UI APIs used by the current NSync client. The complete project-defined function list is in [function-reference.md](function-reference.md).

## Core Compose Imports

| Import family | Used for |
| --- | --- |
| `androidx.compose.runtime.*` | `@Composable`, `remember`, `mutableStateOf`, `LaunchedEffect`, and observable UI state. |
| `androidx.compose.foundation.layout.*` | `Column`, `Row`, `Box`, `Spacer`, sizing, padding, and arrangement. |
| `androidx.compose.foundation.lazy.*` | Scrollable, efficient lists through `LazyColumn`, `item`, and `items`. |
| `androidx.compose.material3.*` | Material controls such as `Scaffold`, `Card`, `Button`, `OutlinedButton`, `Text`, `Icon`, `Switch`, and `DropdownMenu`. |
| `androidx.compose.ui.Modifier` | Chains layout, drawing, click, and size behavior onto a composable. |
| `androidx.compose.ui.res.painterResource` | Loads an Android drawable into `Icon` or `Image`. |
| `androidx.lifecycle.viewmodel.compose.viewModel` | Retrieves a lifecycle-aware ViewModel inside a composable. |

## Important Compose Functions

### `@Composable`

Marks a function that emits UI. Compose may call it again when state read by that function changes. It is used for screens and reusable components such as `MainScreenScaffold`, `AuthTextField`, and `ReviewSessionListItem`.

### State: `remember`, `mutableStateOf`, and `rememberSaveable`

- `remember { mutableStateOf(...) }` holds local UI state while the composable remains in the composition. Login/Register field text and Review Session answer visibility use this pattern.
- `rememberSaveable` also restores simple values after activity recreation. Settings uses it for selection and toggle state.
- ViewModel state is preferred for API-backed data that must outlive a temporary recomposition.

### Side effects: `LaunchedEffect` and `DisposableEffect`

- `LaunchedEffect(key)` runs a coroutine when a key changes. Detail screens load their ID, review screens load a session, and navigation restores the auth session this way.
- `DisposableEffect` attaches lifecycle observers and removes them when the composable leaves. Dashboard/Profile/Review Cards use it to refresh data on `ON_RESUME`.

### Layout: `Column`, `Row`, `Box`, and `LazyColumn`

- `Column` stacks children vertically.
- `Row` places children horizontally; `Modifier.weight` divides available row width.
- `Box` overlays or centers children with `contentAlignment`.
- `LazyColumn` renders a scrolling sequence through `item {}` and `items(list) {}`. It is used for the main scaffold and larger screen content.

### Material 3: `Scaffold`, cards, fields, and actions

- `Scaffold` allocates a page shell with a `bottomBar` and returns `innerPadding` that content must apply. `MainScreenScaffold` and `SessionCompleteScreen` use it.
- `Card` creates a surfaced container; `CardDefaults.cardColors` sets its background.
- `Button` is a filled primary action. `OutlinedButton` is a secondary action. `ButtonDefaults` controls colors/elevation.
- `OutlinedTextField` provides Material text input; `AuthTextField` configures it for NSync's visual design and password transformation.
- `Switch`, `DropdownMenu`, and `DropdownMenuItem` implement Settings controls.
- `LinearProgressIndicator` displays XP or mastery fraction. Inputs must be clamped to `0f..1f`.

### Navigation Compose

`rememberNavController` creates a controller. `NavHost` declares route-to-composable mappings. `composable` can read typed arguments declared by `navArgument` and `NavType`. `navigate`, `popBackStack`, `popUpTo`, `launchSingleTop`, `saveState`, and `restoreState` control transitions and back-stack state.

## Styling and Resources

`ui/theme/Color.kt`, `Type.kt`, `Theme.kt`, and `ScreenStyles.kt` define the app's colors, Inter font family, Material theme, text styles, and shared card border. Screen code imports these values instead of copying global style decisions.

`R.drawable.*` values are generated Android resources. `painterResource(R.drawable.name)` loads vector or bitmap drawables. Android manifest declarations are required for `INTERNET`, notification receiver registration, and cleartext development traffic.

## ViewModel Boundary

A composable should render `uiState` and call a ViewModel action from click callbacks. A ViewModel owns coroutines and repository calls. The repository owns Retrofit calls and DTO-to-UI mapping. This keeps composables deterministic and makes API errors/loading states explicit.
