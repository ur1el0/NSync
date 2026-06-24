# Settings Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/settings/SettingsScreen.kt`
**Route:** `Routes.SETTINGS`

## Purpose and Data Flow

Settings stores device-local learning preferences in `SettingsPreferences` and schedules/cancels the Android reminder alarm. These preferences are not currently backend user-profile fields.

## Functions

- `SettingsScreen(onBackClick, onProfileClick)`: reads local preferences, owns displayed selection/toggle state, requests Android 13+ notification permission, persists changes, and controls reminder scheduling.
- `SettingsTopBar(onBackClick)`: back action and NSync heading.

`SettingsComponents.kt` supplies `SettingsSection`, `SettingsRow`, `SettingsDropdownRow`, `SettingsToggleRow`, and `SettingsDivider` so rows are not duplicated inside the screen.

## Important Imports

- Android `Manifest`, `Build`, `PackageManager`, `ContextCompat`, and Activity Result APIs: permission checking/requesting.
- Compose `rememberSaveable`, `LaunchedEffect`, Material `Icon`/`Text`, and layout APIs: state and presentation.
- `ReviewReminderScheduler` and `SettingsPreferences`: reminder and local persistence behavior.

## Navigation

Back returns to Profile. The Profile row also returns to `PROFILE`. This settings screen does not use the main bottom navigation scaffold.
