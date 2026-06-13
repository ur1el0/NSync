# NSync Mobile Compose Guide

This guide explains how the Android Jetpack Compose UI is structured, what each screen does, where it is linked in navigation, where styling lives, and what the main Compose functions/imports mean.

For shorter page-by-page references, see `docs/screens/README.md`.

## Entry Point

### `MainActivity.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/MainActivity.kt
```

Purpose:

- Starts the Android app.
- Calls `setContent { ... }` to render Compose UI.
- Wraps the app in `MobileTheme`.
- Loads `AppNavigation()`.

Important imports:

- `androidx.activity.ComponentActivity`: base Android activity.
- `androidx.activity.compose.setContent`: lets an Activity render Compose content.
- `androidx.activity.enableEdgeToEdge`: lets UI draw behind system bars.
- `com.example.mobile.navigation.AppNavigation`: app navigation graph.
- `com.example.mobile.ui.theme.MobileTheme`: app theme wrapper.

Flow:

```text
MainActivity -> MobileTheme -> AppNavigation -> screen routes
```

## Navigation

### `Routes.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/navigation/Routes.kt
```

Purpose:

- Defines string route names used by Jetpack Navigation.
- Defines bottom navigation items through `BottomNavRoute`.

Routes:

```text
LOGIN
REGISTER
DASHBOARD
KNOWLEDGE_BASE
KNOWLEDGE_DETAIL
NEW_NOTE
REVIEW_CARDS
REVIEW_SESSION
SESSION_COMPLETE
MASTERY
PROFILE
SETTINGS
```

`BottomNavRoute` contains:

- `label`: text shown in the bottom nav.
- `route`: destination route.
- `iconRes`: drawable icon resource.

### `AppNavigation.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/navigation/AppNavigation.kt
```

Purpose:

- Owns the app `NavHost`.
- Maps each route to a screen.
- Passes click callbacks into screens.

Important imports:

- `androidx.navigation.compose.rememberNavController`: creates the navigation controller.
- `androidx.navigation.compose.NavHost`: container for route destinations.
- `androidx.navigation.compose.composable`: declares one route/screen destination.

Important Compose/navigation functions:

- `rememberNavController()`: remembers the nav controller across recompositions.
- `NavHost(navController, startDestination)`: defines the app navigation graph.
- `composable(Routes.X) { ... }`: renders a screen for one route.
- `navController.navigate(route)`: moves to another screen.
- `navController.popBackStack()`: goes back.
- `popUpTo(...)`: removes destinations from the back stack.
- `launchSingleTop = true`: avoids duplicate copies of the same screen.
- `restoreState = true` and `saveState = true`: preserve bottom-nav screen state.

Screen links:

```text
LOGIN -> LoginScreen
REGISTER -> RegisterScreen
DASHBOARD -> DashboardScreen
KNOWLEDGE_BASE -> KnowledgeBaseScreen
NEW_NOTE -> NewNoteScreen
KNOWLEDGE_DETAIL -> KnowledgeDetailScreen
REVIEW_CARDS -> ReviewCardsScreen
REVIEW_SESSION -> ReviewSessionScreen
SESSION_COMPLETE -> SessionCompleteScreen
MASTERY -> MasteryScreen
PROFILE -> ProfileScreen
```

Important route behavior:

- Login/register navigate to Dashboard and remove auth screens from the back stack.
- Dashboard `Start Review` navigates to Review Session.
- Dashboard recent knowledge navigates to Knowledge Detail.
- Knowledge Base `+ New Note` navigates to New Note.
- Review Session completes into Session Complete.
- Profile logout navigates back to Login and clears the stack.

## Data Layer

### `Models.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/data/Models.kt
```

Purpose:

- Defines static data shapes used by the UI.

Models:

- `UserProfile`: profile, level, XP, streak, accuracy.
- `KnowledgeItem`: a note/knowledge entry.
- `ReviewCard`: a flashcard/review card linked by `knowledgeItemId`.
- `CollectionMastery`: progress by collection.
- `RecentAttempt`: previous quiz/review attempt.
- `ReviewSessionSummary`: session result data.

### `SampleData.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/data/SampleData.kt
```

Purpose:

- Provides static sample data before database/API integration.
- Feeds all current screens.

Important values:

- `userProfile`: dashboard/profile user.
- `knowledgeItems`: Knowledge Base list.
- `reviewCards`: Review Cards list.
- `collectionMastery`: Mastery screen list.
- `sessionSummary`: Session Complete data.
- `reviewSessionCard`: current card used by Review Session.
- `draftReviewCardQuestion` and `draftReviewCardAnswer`: draft review content.

## Shared Components

### `BottomNavBar.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/ui/components/BottomNavBar.kt
```

Purpose:

- Reusable bottom navigation component.
- Reads `bottomNavRoutes` from `Routes.kt`.
- Highlights the active route through `currentRoute`.
- Calls `onRouteClick(route)` when a tab is tapped.

Important imports:

- `NavigationBar`: Material 3 bottom navigation container.
- `NavigationBarItem`: one tab in the nav bar.
- `Icon`: renders drawable icons.
- `painterResource`: loads drawable resources.

Important Compose functions:

- `NavigationBar { ... }`: bottom nav wrapper.
- `NavigationBarItem(...)`: bottom tab.
- `Icon(painter = painterResource(id = ...))`: displays vector drawable icon.
- `Text(...)`: displays label text.

### `MainScreenScaffold.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/ui/components/MainScreenScaffold.kt
```

Purpose:

- Shared layout wrapper for main tab screens.
- Provides:
  - background color,
  - title/subtitle header,
  - bottom nav,
  - scrollable content area.

Used by:

- `KnowledgeBaseScreen`
- `KnowledgeDetailScreen`
- `ReviewCardsScreen`
- `MasteryScreen`
- `ProfileScreen`

Important imports:

- `Scaffold`: Material layout structure with slots like `bottomBar`.
- `LazyColumn`: scrollable vertical list.
- `LazyListScope`: lets caller provide `item {}` and `items(...)` content.
- `Spacer`: empty spacing.

Important Compose functions:

- `Scaffold(...)`: page frame. In this app, it supplies the bottom navigation.
- `LazyColumn(...)`: scrollable vertical container.
- `item { ... }`: one block inside a `LazyColumn`.
- `content()`: inserts caller-provided list content.

### `AuthTextField.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/ui/components/AuthTextField.kt
```

Purpose:

- Shared text field for Login and Register screens.
- Supports optional leading icon.
- Supports password visual hiding.

Important imports:

- `OutlinedTextField`: Material text field with border.
- `PasswordVisualTransformation`: hides password characters.
- `VisualTransformation.None`: normal visible text.
- `OutlinedTextFieldDefaults.colors`: custom field colors.

Important Compose functions:

- `OutlinedTextField(...)`: input field.
- `leadingIcon = { Icon(...) }`: optional icon inside the field.
- `singleLine = true`: restricts input to one line.
- `visualTransformation`: switches between password and normal text display.

### `ScreenButtons.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/ui/components/ScreenButtons.kt
```

Purpose:

- Contains `PrimaryScreenButton`, the shared blue rounded button used across main screens.

Important imports:

- `Button`: Material button.
- `ButtonDefaults.buttonColors`: custom button background color.
- `RoundedCornerShape`: rounded pill shape.

### `ScreenCards.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/ui/components/ScreenCards.kt
```

Purpose:

- Shared card components that are not tied to one feature.

Functions:

- `SummaryMetric`: small metric card for values like score, accuracy, streak.
- `ProgressSummaryCard`: card with title/subtitle and progress bar.
- `CenteredCard`: generic card that centers its content.

Important imports:

- `Card`: Material card container.
- `LinearProgressIndicator`: progress bar.
- `Box`: simple layout container that can align one child.
- `Column` and `Row`: vertical and horizontal layouts.

## Styling

### `Color.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/ui/theme/Color.kt
```

Purpose:

- Defines app brand colors.

Important values:

- `NSyncBlue`
- `NSyncLightBackground`
- `NSyncCardWhite`
- `NSyncMutedText`
- `NSyncRed`
- `NSyncBorder`

### `Type.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/ui/theme/Type.kt
```

Purpose:

- Defines the `InterFontFamily`.
- Sets app typography.

Important imports:

- `Font`: loads font resource from `res/font`.
- `FontFamily`: groups fonts into one family.
- `FontWeight`: maps weights like Normal, Bold, ExtraBold.

Important resources:

```text
mobile/app/src/main/res/font/inter.ttf
```

### `ScreenStyles.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/ui/theme/ScreenStyles.kt
```

Purpose:

- Stores shared screen text styles and common borders.

Important values:

- `ScreenTitle`
- `ScreenCardBorder`
- `ScreenLogoStyle`
- `ScreenHeroStyle`
- `ScreenSectionStyle`
- `ScreenBodyStyle`
- `ScreenSmallStyle`
- `ScreenSmallBoldStyle`
- `ScreenButtonStyle`

### `Theme.kt`

Path:

```text
mobile/app/src/main/java/com/example/mobile/ui/theme/Theme.kt
```

Purpose:

- Applies Material 3 theme.
- Sets color scheme and typography.

Important Compose function:

- `MaterialTheme(...)`: makes theme colors and typography available to composables.

## Screen Files

### Auth Screens

#### `LoginScreen.kt`

Path:

```text
ui/screens/auth/LoginScreen.kt
```

Purpose:

- Login page.
- Collects email and password.
- Shows NSync logo.
- Links to Register.

Linked in:

```text
Routes.LOGIN -> LoginScreen
```

Callbacks:

- `onLoginClick(email, password)`: navigates to Dashboard.
- `onRegisterClick()`: navigates to Register.

Important local state:

- `email`
- `password`

Important imports:

- `remember`, `mutableStateOf`: hold typed field values.
- `Image`, `painterResource`: show app logo.
- `Card`, `Button`, `Text`: Material UI.
- `AuthTextField`: shared input component.

#### `RegisterScreen.kt`

Path:

```text
ui/screens/auth/RegisterScreen.kt
```

Purpose:

- Create account page.
- Collects name, email, and password.
- Links back to Login.

Linked in:

```text
Routes.REGISTER -> RegisterScreen
```

Callbacks:

- `onRegisterClick(name, email, password)`: navigates to Dashboard.
- `onLoginClick()`: goes back to Login.

## Dashboard

### `DashboardScreen.kt`

Path:

```text
ui/screens/dashboard/DashboardScreen.kt
```

Purpose:

- Main home screen after login.
- Shows:
  - top nav,
  - greeting,
  - level progress,
  - streak and accuracy,
  - start review action,
  - recent knowledge card,
  - bottom nav.

Linked in:

```text
Routes.DASHBOARD -> DashboardScreen
```

Callbacks:

- `onStartReviewClick`: goes to Review Session.
- `onKnowledgeClick`: goes to Knowledge Detail.
- `onAddClick`: goes to Review Cards.
- `onRouteClick`: bottom nav route handler.

Important child functions:

- `DashboardTopNav`
- `DashboardGreeting`
- `LevelProgressCard`
- `DashboardStatsRow`
- `StatCard`
- `DashboardActions`
- `RecentKnowledgeSection`
- `InfoPill`

Important imports:

- `Scaffold`: page shell with bottom nav.
- `LazyColumn`: scrollable dashboard content.
- `LinearProgressIndicator`: XP progress bar.
- `Image` and `painterResource`: logo/profile icon.
- `Icon`: dashboard stats icons.

Styling:

- Dashboard-specific styles are local constants at the bottom of `DashboardScreen.kt`.
- Shared colors come from `ui/theme/Color.kt`.

## Knowledge Screens

### `KnowledgeBaseScreen.kt`

Path:

```text
ui/screens/knowledge/KnowledgeBaseScreen.kt
```

Purpose:

- Lists knowledge items from `SampleData.knowledgeItems`.
- Provides `+ New Note` button.

Linked in:

```text
Routes.KNOWLEDGE_BASE -> KnowledgeBaseScreen
```

Callbacks:

- `onKnowledgeClick(item)`: opens Knowledge Detail.
- `onNewNoteClick()`: opens New Note.
- `onRouteClick(route)`: bottom nav.

Important imports:

- `items`: displays a list inside `LazyColumn`.
- `KnowledgeListCard`: card for each knowledge item.
- `PrimaryScreenButton`: New Note button.
- `MainScreenScaffold`: shared page wrapper.

### `KnowledgeListCard.kt`

Current path:

```text
ui/components/KnowledgeListCard.kt
```

Purpose:

- Displays one `KnowledgeItem`.
- Shows collection, title, source, review card count, and update label.

Note:

- This is knowledge-specific. It can be moved later to:

```text
ui/screens/knowledge/components/KnowledgeListCard.kt
```

### `KnowledgeDetailScreen.kt`

Path:

```text
ui/screens/knowledge/KnowledgeDetailScreen.kt
```

Purpose:

- Shows detail for `SampleData.detailKnowledge`.
- Shows mastery progress.
- Shows full note text.
- Has Start Review button.

Linked in:

```text
Routes.KNOWLEDGE_DETAIL -> KnowledgeDetailScreen
```

Callbacks:

- `onStartReviewClick()`: opens Review Session.
- `onRouteClick(route)`: bottom nav.

### `NewNoteScreen.kt`

Path:

```text
ui/screens/knowledge/NewNoteScreen.kt
```

Purpose:

- Static New Note form page.
- Lets user type title, collection, source, context, and note body.
- Currently stores data only in local Compose state. It does not save to database yet.

Linked in:

```text
Routes.NEW_NOTE -> NewNoteScreen
```

Callbacks:

- `onBackClick()`: goes back.
- `onSaveClick()`: returns to Knowledge Base.
- `onRouteClick(route)`: bottom nav.

Important local state:

- `title`
- `collection`
- `source`
- `context`
- `content`

Important imports:

- `OutlinedTextField`: input fields.
- `remember`, `mutableStateOf`: local form state.
- `Scaffold`: page shell.
- `BottomNavBar`: bottom nav.
- `OutlinedButton` and `Button`: Cancel and Save Note actions.

## Review Screens

### `ReviewCardsScreen.kt`

Path:

```text
ui/screens/review/ReviewCardsScreen.kt
```

Purpose:

- Lists review cards from `SampleData.reviewCards`.
- Has Start Review button.

Linked in:

```text
Routes.REVIEW_CARDS -> ReviewCardsScreen
```

Callbacks:

- `onStartReviewClick()`: opens Review Session.
- `onRouteClick(route)`: bottom nav.

### `ReviewCardListItem.kt`

Current path:

```text
ui/components/ReviewCardListItem.kt
```

Purpose:

- Displays one `ReviewCard`.
- Shows mastery label, question, collection, difficulty, and mastery progress.

Note:

- This is review-specific. It can be moved later to:

```text
ui/screens/review/components/ReviewCardListItem.kt
```

### `ReviewSessionScreen.kt`

Path:

```text
ui/screens/review/ReviewSessionScreen.kt
```

Purpose:

- Shows one review question.
- `Show Answer` toggles answer mode.
- Answer mode displays `SampleData.draftReviewCardAnswer`.
- `Finish review` navigates to Session Complete.

Linked in:

```text
Routes.REVIEW_SESSION -> ReviewSessionScreen
```

Callback:

- `onCompleteClick()`: opens Session Complete.

Important local state:

- `showAnswer`: controls whether the question or answer is visible.

Important imports:

- `remember`, `mutableStateOf`: answer visibility state.
- `LazyColumn`: vertical screen layout.
- `CenteredCard`: shared centered card.
- `PrimaryScreenButton`: Show/Hide Answer button.

### `SessionCompleteScreen.kt`

Path:

```text
ui/screens/review/SessionCompleteScreen.kt
```

Purpose:

- Shows review result summary.
- Shows score, accuracy, XP, streak, and progress.
- Lets user return to Dashboard.

Linked in:

```text
Routes.SESSION_COMPLETE -> SessionCompleteScreen
```

Callback:

- `onDashboardClick()`: navigates to Dashboard.

## Progress Screen

### `MasteryScreen.kt`

Path:

```text
ui/screens/progress/MasteryScreen.kt
```

Purpose:

- Shows static progress/mastery overview.
- Shows total XP and level.
- Lists collection mastery using `SampleData.collectionMastery`.

Linked in:

```text
Routes.MASTERY -> MasteryScreen
```

Callback:

- `onRouteClick(route)`: bottom nav.

## Profile Screen

### `ProfileScreen.kt`

Path:

```text
ui/screens/profile/ProfileScreen.kt
```

Purpose:

- Shows user profile info from `SampleData.userProfile`.
- Shows level, streak, and accuracy.
- Reuses dashboard icons:
  - `R.drawable.ic_wind`
  - `R.drawable.ic_target`
- Has Logout button.

Linked in:

```text
Routes.PROFILE -> ProfileScreen
```

Callbacks:

- `onRouteClick(route)`: bottom nav.
- `onLogoutClick()`: goes back to Login and clears the stack.

## Common Jetpack Compose Concepts Used

### `@Composable`

Marks a function as UI that Compose can render.

Example:

```kotlin
@Composable
fun DashboardScreen(...) { ... }
```

### `Modifier`

Chains layout and drawing instructions.

Common uses:

- `fillMaxSize()`: fill all available space.
- `fillMaxWidth()`: fill parent width.
- `height(52.dp)`: set fixed height.
- `padding(...)`: add spacing.
- `background(...)`: draw background.
- `clip(...)`: clip to shape.
- `clickable { ... }`: make element clickable.

### `Column`

Vertical layout.

Used when children should stack top to bottom.

### `Row`

Horizontal layout.

Used for buttons side by side, stats row, top bars.

### `Box`

Simple container that can align content.

Used for centered icons, profile avatar, card content.

### `LazyColumn`

Scrollable vertical list.

Difference from `Column`:

- `Column` renders all children immediately.
- `LazyColumn` renders list items lazily and supports `item {}` / `items(...)`.

### `Scaffold`

Material page structure.

This app uses it mainly for:

- screen background,
- `bottomBar`.

### `Card`

Material container with background, shape, and border.

Used for:

- dashboard cards,
- knowledge cards,
- review cards,
- profile card.

### `Text`

Displays text.

Usually receives:

- `text`,
- `color`,
- `style`.

### `Button` and `OutlinedButton`

Clickable Material buttons.

Used for:

- Login,
- Create Account,
- Start Review,
- Save Note,
- Logout.

### `OutlinedTextField`

Text input field with outlined style.

Used for:

- auth forms,
- New Note fields.

### `Image`

Displays bitmap/vector images.

Uses:

```kotlin
painterResource(id = R.drawable.nsync_logo)
```

### `Icon`

Displays icons, usually vector drawables.

Used in:

- bottom nav,
- dashboard/profile stat cards,
- auth input fields.

### `remember` and `mutableStateOf`

Stores local state in a composable.

Example:

```kotlin
var email by remember { mutableStateOf("") }
```

When the state changes, Compose redraws the affected UI.

### `painterResource`

Loads drawable resources.

Example:

```kotlin
painterResource(id = R.drawable.ic_grid)
```

Requires:

```kotlin
import androidx.compose.ui.res.painterResource
import com.example.mobile.R
```

Avoid wrong imports like:

```kotlin
import androidx.compose.ui.input.key.Key.Companion.R
```

That breaks `R.drawable`.

## Current Architecture Summary

```text
MainActivity
  -> MobileTheme
    -> AppNavigation
      -> Auth screens
      -> Dashboard
      -> Knowledge screens
      -> Review screens
      -> Mastery
      -> Profile
```

Shared code:

```text
ui/components/
  AuthTextField
  BottomNavBar
  MainScreenScaffold
  ScreenButtons
  ScreenCards

ui/theme/
  Color
  Type
  Theme
  ScreenStyles
```

Static data:

```text
data/
  Models
  SampleData
```

Navigation:

```text
navigation/
  Routes
  AppNavigation
```

## What Is Still Static

These screens currently use local/sample data only:

- Knowledge Base
- Knowledge Detail
- New Note
- Review Cards
- Review Session
- Session Complete
- Mastery
- Profile

The next step for real app behavior is to replace `SampleData` with ViewModels, local persistence, or API data.
