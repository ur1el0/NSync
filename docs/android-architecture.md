# Android Architecture

## Entry and Initialization

`MainActivity` is the Android entry point. Its important imports are `ComponentActivity`, `setContent`, `enableEdgeToEdge`, `RetrofitClient`, `AppNavigation`, and `MobileTheme`.

`onCreate` enables edge-to-edge rendering, calls `RetrofitClient.initialize(applicationContext)`, and renders:

```text
MobileTheme -> AppNavigation -> current route composable
```

Calling `initialize` before Compose is important: the Retrofit singleton needs an application context to construct `AuthSessionStore` and `AuthInterceptor`.

## Navigation

`navigation/Routes.kt` contains route constants, route-builder functions, and `bottomNavRoutes`. Its project import is `R` for bottom-navigation drawable IDs.

`navigation/AppNavigation.kt` imports Navigation Compose (`rememberNavController`, `NavHost`, `composable`, `navArgument`, and `NavType`), all screen composables, the Retrofit session store, and the authentication ViewModel/factory.

`AppNavigation()`:

1. Creates the navigation controller.
2. Constructs `AuthRepository` and `AuthViewModel` once with `remember` and `viewModel`.
3. Calls `restoreSession()` in `LaunchedEffect(Unit)`.
4. Redirects authenticated users away from Login/Register, and unauthenticated users away from protected routes.
5. Declares every route and translates screen callbacks into `navigate`, `popBackStack`, or route-builder calls.

Arguments are passed through `navArgument` for note ID, flashcard ID, and session summary values. The route string is an identifier, not a data store; each detail screen reloads its entity from the API using the ID.

## State and ViewModel Layer

The `ui/state/` files contain immutable data classes. They hold loading flags, errors, current models, and action flags such as `deleted` or `saved`. They are plain Kotlin types and have no Android dependency.

The `ui/viewmodel/` files import `ViewModel`, `viewModelScope`, Compose `mutableStateOf`, repository classes, and `kotlinx.coroutines.launch`.

ViewModels start a coroutine in `viewModelScope`, call the repository, and assign a new UI-state value. Compose observes the state read by each screen and recomposes automatically. Screens do not call Retrofit directly.

## Remote Data Layer

`ApiService` is a Retrofit interface. Important imports are Retrofit annotations (`GET`, `POST`, `PUT`, `DELETE`, `Body`, `Path`) and `Response`. Each suspend method maps one endpoint to a DTO type.

`RetrofitClient` imports Retrofit, Gson, OkHttp, `AuthInterceptor`, and `AuthSessionStore`. It creates one authenticated `OkHttpClient` and one Retrofit `ApiService`. The development base URL is `http://127.0.0.1:8000/`; Android emulator access requires `adb reverse tcp:8000 tcp:8000`.

DTO files use `@SerializedName` from Gson when JSON names differ from Kotlin names. `NSyncRepository` maps note and flashcard DTOs into UI models such as `KnowledgeItem` and `ReviewCard`, so screen code remains independent of HTTP field names.

## Authentication Flow

`AuthSession` is the in-memory representation of access token, refresh token, and authenticated-user fields. `AuthSessionStore` imports DataStore Preferences and Kotlin Flow. It exposes flows for tokens/session and provides `saveSession`, `updateTokens`, and `clearSession`.

`AuthInterceptor` imports OkHttp `Interceptor` and `Response`, plus `Flow.first` and `runBlocking`. On OkHttp's worker thread it reads the access token and appends `Authorization: Bearer <token>` to protected requests. It deliberately excludes health, register, login, and refresh paths.

`AuthRepository` imports the auth DTOs, session store, API service, and `HttpException`. It registers/logs in, stores sessions, refreshes tokens, calls logout, and verifies a restored session through `/api/auth/me/`. A 401 causes one refresh-and-retry attempt; failure clears local tokens.

## Compose UI Layer

Screen files import Compose foundation layouts (`Column`, `Row`, `Box`, `LazyColumn`), Material 3 controls (`Button`, `Card`, `Text`, fields), lifecycle helpers where needed, feature ViewModels, and shared components/theme values.

Reusable UI is under `ui/components/`:

- `MainScreenScaffold`: Material 3 `Scaffold`, shared top copy, `LazyColumn`, and `BottomNavBar`.
- `AuthTextField`: styled email/password field.
- `KnowledgeListCard`, `ReviewSessionListItem`, and `ReviewCardListItem`: list presentation components.
- `PrimaryScreenButton`, `SummaryMetric`, `ProgressSummaryCard`, and `CenteredCard`: shared controls/cards.
- `SettingsComponents`: section, row, dropdown, toggle, and divider components.

`ui/theme/` centralizes colors, Inter typography, Material 3 theme configuration, text styles, and standard card borders.

## Local Notifications

`SettingsPreferences` uses Android `SharedPreferences` for settings values. `ReviewReminderScheduler` imports `AlarmManager`, `PendingIntent`, `Intent`, and `Calendar` to schedule/cancel a daily alarm. `ReviewReminderReceiver` imports Android notification APIs and creates the channel before displaying the review reminder. Android 13+ notification permission is requested by `SettingsScreen`.
