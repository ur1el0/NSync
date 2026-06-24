# NSync Function Reference

This reference lists every project-defined Kotlin callable in the current Android source tree. Standard library property accessors and generated Android resource members are intentionally excluded. For Compose/Material import explanations, see [mobile-compose-guide.md](mobile-compose-guide.md).

## Data, Session, and Networking

| File | Imports / dependencies | Classes and functions | Responsibility |
| --- | --- | --- | --- |
| `data/Models.kt` | No framework imports | `UserProfile`, `KnowledgeItem`, `ReviewCard`, `CollectionMastery`, `RecentAttempt`, `ReviewSessionSummary`, `LoginRequest`, `RegisterRequest` | UI-domain data shapes. Some legacy types remain for presentation/sample compatibility. |
| `data/local/AuthSession.kt` | No framework imports | `AuthSession` | Holds access/refresh tokens and authenticated user identity in one value. |
| `data/local/AuthSessionStore.kt` | `Context`, DataStore Preferences, Kotlin `Flow` | `AuthSessionStore`; `saveSession`; `updateTokens`; `clearSession` | Reads/writes the local session. Exposes access-token, refresh-token, complete-session, and signed-in flows. |
| `data/remote/dto/AuthDtos.kt` | Gson `SerializedName` | `RegisterRequestDto`, `LoginRequestDto`, `AuthenticatedUserDto`, `AuthResponseDto`, `RefreshRequestDto`, `RefreshResponseDto` | JSON request/response contracts for authentication. |
| `data/remote/dto/NoteDtos.kt` | Gson `SerializedName` | `NoteDto`, `CreateNoteRequestDto`, `UpdateNoteRequestDto` | JSON contracts for notes. |
| `data/remote/dto/FlashcardDtos.kt` | Gson `SerializedName` | `FlashcardDto`, `CreateFlashcardRequestDto`, `UpdateFlashcardRequestDto` | JSON contracts for flashcards and their connected note metadata. |
| `data/remote/dto/ReviewDtos.kt` | Gson `SerializedName` | `ReviewCompleteRequestDto`, `ReviewAnswerDto`, `QuizAttemptDto`, `UserProgressDto`, `ReviewCompleteResponseDto` | JSON contracts for a completed review and progress result. |
| `data/remote/api/ApiService.kt` | Retrofit `GET`, `POST`, `PUT`, `DELETE`, `Body`, `Path`, `Response`; all DTOs | `getNotes`, `getNoteById`, `createNote`, `updateNote`, `deleteNote`, `getFlashcards`, `getFlashcardById`, `createFlashcard`, `updateFlashcard`, `deleteFlashcard`, `completeReview`, `getProgress`, `register`, `login`, `refreshToken`, `logout`, `getAuthenticatedUser` | Declares HTTP method, route, body, and result type for every backend endpoint. All methods are suspend functions and must run from a coroutine. |
| `data/remote/interceptor/AuthInterceptor.kt` | OkHttp `Interceptor`/`Response`; Flow `first`; `runBlocking`; `AuthSessionStore` | `AuthInterceptor` and `intercept` | Adds a Bearer access token to protected requests. Skips public auth/health endpoints. |
| `data/remote/RetrofitClient.kt` | Retrofit, Gson converter, OkHttp, `Context`, session/interceptor types | `RetrofitClient`; `initialize` | Initializes one session store, OkHttp client, Retrofit instance, and `ApiService`. Must run before repository construction. |
| `data/repository/AuthRepository.kt` | Auth DTOs, `ApiService`, `AuthSessionStore`, Retrofit `HttpException` | `AuthRepository`; `register`; `login`; `refreshSession`; `logout`; `AuthResponseDto.toSession`; `verifySession`; `saveVerifiedSession` | Owns authentication workflow. Saves tokens after login/register, refreshes an expired session once, verifies `/auth/me/`, and clears invalid sessions. |
| `data/repository/NSyncRepository.kt` | API service, note/flashcard/review DTOs, UI models | `NSyncRepository`; `getNotes`; `getNoteById`; `createNote`; `updateNote`; `deleteNote`; `getKnowledgeItemById`; `getKnowledgeItems`; `NoteDto.toKnowledgeItem`; `getReviewCards`; `createFlashcard`; `getReviewCardById`; `updateFlashcard`; `deleteFlashcard`; `completeReview`; `FlashcardDto.toReviewCard`; `masteryLabel`; `masteryPercent`; `getProgress` | Calls learning-data endpoints and translates network DTOs into models used by the UI. |

## Navigation and App Entry

| File | Imports / dependencies | Classes and functions | Responsibility |
| --- | --- | --- | --- |
| `MainActivity.kt` | Android `ComponentActivity`, `setContent`, `enableEdgeToEdge`; `RetrofitClient`; `MobileTheme`; `AppNavigation` | `MainActivity.onCreate` | Initializes networking and starts Compose. |
| `navigation/Routes.kt` | `R` | `Routes`; `knowledgeDetail`; `editNote`; `newFlashcard`; `flashcardDetail`; `editFlashcard`; `reviewSessionForNote`; `sessionComplete`; `BottomNavRoute`; `bottomNavRoutes` | Central route names, argument route builders, and bottom navigation definitions. |
| `navigation/AppNavigation.kt` | Navigation Compose, lifecycle `viewModel`, Compose effects/remember, all screen composables, auth repository/factory | `AppNavigation` | Builds the `NavHost`, restores auth, redirects by session state, and maps screen callbacks to routes. |

## Notifications and Settings Storage

| File | Imports / dependencies | Classes and functions | Responsibility |
| --- | --- | --- | --- |
| `notifications/ReviewReminderScheduler.kt` | `AlarmManager`, `PendingIntent`, `Intent`, `Calendar`, `SettingsPreferences` | `ReviewReminderScheduler`; `schedule`; `cancel`; `reminderCalendar`; `pendingIntent` | Schedules/cancels the daily notification alarm from saved reminder time. |
| `notifications/ReviewReminderReceiver.kt` | `BroadcastReceiver`, Android notification APIs, `MainActivity`, `SettingsPreferences` | `ReviewReminderReceiver.onReceive`; `createChannel` | Receives the alarm, checks notification preference/permission, creates a channel, and posts the reminder. |
| `ui/screens/settings/SettingsPreferences.kt` | `Context`, `SharedPreferences` | `SettingsPreferences`; `from` | Defines preference keys and opens the settings preference file. |

## UI State and ViewModels

All ViewModel files import `ViewModel`, `viewModelScope`, Compose state delegates, `launch`, a repository, and their feature state/model type. The UI state data classes import only models they need.

| File | Classes and functions | Responsibility |
| --- | --- | --- |
| `ui/state/AuthUiState.kt` | `AuthUiState` | Auth loading, session, error, and restore-complete flags. |
| `ui/state/DashboardUiState.kt` | `DashboardUiState` | Dashboard progress, recent knowledge, loading, and error. |
| `ui/state/KnowledgeBaseUiState.kt` | `KnowledgeBaseUiState` | Note list, loading, and error. |
| `ui/state/KnowledgeDetailUiState.kt` | `KnowledgeDetailUiState` | One note plus load/delete state. |
| `ui/state/FlashcardDetailUiState.kt` | `FlashcardDetailUiState` | One card plus load/delete state. |
| `ui/state/MasteryUiState.kt` | `MasteryUiState` | Progress and derived mastery list state. |
| `ui/state/ProfileUiState.kt` | `ProfileUiState` | Profile progress, loading, and error. |
| `ui/state/SessionCompleteUiState.kt` | `SessionCompleteUiState` | Updated progress for completion screen. |
| `ui/viewmodel/AuthViewModel.kt` | `AuthViewModel`; `login`; `register`; `logout`; `restoreSession`; `clearError` | Runs auth repository actions and updates `AuthUiState`. |
| `ui/viewmodel/AuthViewModelFactory.kt` | `AuthViewModelFactory` | Supplies the repository constructor dependency to `AuthViewModel`. |
| `ui/viewmodel/DashboardViewModel.kt` | `DashboardViewModel`; `loadDashboard` | Loads progress and recent notes for Dashboard. |
| `ui/viewmodel/KnowledgeBaseViewModel.kt` | `KnowledgeBaseViewModel`; `loadNotes` | Loads the Knowledge Base list. |
| `ui/viewmodel/KnowledgeDetailViewModel.kt` | `KnowledgeDetailViewModel`; `loadNote`; `deleteNote` | Loads or deletes one note. |
| `ui/viewmodel/NewNoteViewModel.kt` | `NewNoteViewModel`; `createNote`; `loadNote`; `updateNote` | Creates a note or loads/updates it in edit mode. |
| `ui/viewmodel/ReviewCardsViewModel.kt` | `ReviewCardsViewModel`; `loadCards` | Loads cards, later grouped by the screen into note sessions. |
| `ui/viewmodel/FlashcardDetailViewModel.kt` | `FlashcardDetailViewModel`; `loadCard`; `deleteCard` | Loads or deletes one card. |
| `ui/viewmodel/NewFlashcardViewModel.kt` | `NewFlashcardViewModel`; `saveFlashcard`; `loadCard`; `updateFlashcard` | Creates a flashcard or loads/updates it in edit mode. |
| `ui/viewmodel/ReviewSessionViewModel.kt` | `ReviewSessionResult`; `ReviewSessionViewModel`; `loadSession`; `nextCard`; `recordAnswer`; `consumeCompletedResult`; `completeSession` | Loads a note/card session, records recall choices, completes the backend review, and exposes navigation result values. |
| `ui/viewmodel/SessionCompleteViewModel.kt` | `SessionCompleteViewModel`; `loadProgress` | Loads fresh progress for the completion screen. |
| `ui/viewmodel/MasteryViewModel.kt` | `MasteryViewModel`; `loadMastery` | Loads progress/cards and derives collection mastery. |
| `ui/viewmodel/ProfileViewModel.kt` | `ProfileViewModel`; `loadProfile` | Loads profile progress. |

## Shared Compose Components

These files import Compose layout/foundation primitives, Material 3 controls, `Modifier`, resource loaders where icons are used, and NSync theme values. Every listed function is annotated with `@Composable`.

| File | Functions | Responsibility |
| --- | --- | --- |
| `ui/components/AuthTextField.kt` | `AuthTextField` | Styled email/password `OutlinedTextField`, including icon and password visual transformation. |
| `ui/components/BottomNavBar.kt` | `BottomNavBar` | Renders `bottomNavRoutes` through Material 3 `NavigationBar` and dispatches a selected route. |
| `ui/components/MainScreenScaffold.kt` | `MainScreenScaffold` | Shared `Scaffold`, heading, scrollable list area, and bottom navigation. |
| `ui/components/KnowledgeListCard.kt` | `KnowledgeListCard` | Displays one tappable API-backed note summary. |
| `ui/components/ReviewCardListItem.kt` | `ReviewCardListItem` | Displays a card-level summary; retained for card-specific presentation. |
| `ui/components/ReviewSessionListItem.kt` | `ReviewSessionListItem` | Displays a note-grouped session with Start Review and Add Card actions. |
| `ui/components/ScreenButtons.kt` | `PrimaryScreenButton` | Standard full-width primary action button. |
| `ui/components/ScreenCards.kt` | `SummaryMetric`; `ProgressSummaryCard`; `CenteredCard` | Reusable metrics, progress card, and centered card container. |
| `ui/components/SettingsComponents.kt` | `SettingsSection`; `SettingsRow`; `SettingsDropdownRow`; `SettingsToggleRow`; `SettingsDivider` | Reusable Settings section and control rows. |

## Screen Composables and Helpers

Screen files import Compose layout/Material components, a feature ViewModel via `viewModel` where data is remote, navigation routes, shared components, and theme styles. The per-screen behavior and navigation are documented in [screens/README.md](screens/README.md).

| File | Functions |
| --- | --- |
| `ui/screens/auth/LoginScreen.kt` | `LoginScreen`, `AppLogo` |
| `ui/screens/auth/RegisterScreen.kt` | `RegisterScreen`, `RegisterLabel` |
| `ui/screens/dashboard/DashboardScreen.kt` | `DashboardScreen`, `DashboardTopNav`, `DashboardGreeting`, `LevelProgressCard`, `DashboardStatsRow`, `StatCard`, `DashboardActions`, `RecentKnowledgeSection`, `InfoPill`, `levelStartXp`, `nextLevelXp`, `formatNumber` |
| `ui/screens/knowledge/KnowledgeBaseScreen.kt` | `KnowledgeBaseScreen` |
| `ui/screens/knowledge/KnowledgeDetailScreen.kt` | `KnowledgeDetailScreen` |
| `ui/screens/knowledge/NewNoteScreen.kt` | `NewNoteScreen`, `NewNoteLabel`, `NewNoteField` |
| `ui/screens/review/ReviewCardsScreen.kt` | `ReviewCardsScreen` |
| `ui/screens/review/FlashcardDetailScreen.kt` | `FlashcardDetailScreen` |
| `ui/screens/review/NewFlashcardScreen.kt` | `NewFlashcardScreen` |
| `ui/screens/review/ReviewSessionScreen.kt` | `ReviewSessionScreen` |
| `ui/screens/review/SessionCompleteScreen.kt` | `SessionCompleteScreen`, `SessionCompleteTopBar`, `SessionMetricCard`, `XpEarnedCard`, `StreakCard`, `LevelProgressSection` |
| `ui/screens/progress/MasteryScreen.kt` | `MasteryScreen` |
| `ui/screens/profile/ProfileScreen.kt` | `ProfileScreen`, `ProfileStatCard` |
| `ui/screens/settings/SettingsScreen.kt` | `SettingsScreen`, `SettingsTopBar` |

## Theme

| File | Imports / values | Responsibility |
| --- | --- | --- |
| `ui/theme/Color.kt` | Compose `Color` values | NSync palette. |
| `ui/theme/Type.kt` | `Typography`, `TextStyle`, `FontFamily`, `FontWeight`, `sp`, `R.font.inter` | Inter family and base Material typography. |
| `ui/theme/Theme.kt` | Material 3 `MaterialTheme`, color scheme, Compose system theme APIs | Applies NSync colors and typography through `MobileTheme`. |
| `ui/theme/ScreenStyles.kt` | `BorderStroke`, `Color`, `TextStyle`, `FontWeight`, `dp`, `sp` | Shared text styles, title color, and card border. |
