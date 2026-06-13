# NSync Screen Documentation

This folder documents each Jetpack Compose screen in the Android app.

Use these files when explaining the code, preparing a submission, or finding where a UI behavior is implemented.

## Screen Index

| Screen | Kotlin file | Route |
| --- | --- | --- |
| Login | `ui/screens/auth/LoginScreen.kt` | `Routes.LOGIN` |
| Register | `ui/screens/auth/RegisterScreen.kt` | `Routes.REGISTER` |
| Dashboard | `ui/screens/dashboard/DashboardScreen.kt` | `Routes.DASHBOARD` |
| Knowledge Base | `ui/screens/knowledge/KnowledgeBaseScreen.kt` | `Routes.KNOWLEDGE_BASE` |
| Knowledge Detail | `ui/screens/knowledge/KnowledgeDetailScreen.kt` | `Routes.KNOWLEDGE_DETAIL` |
| New Note | `ui/screens/knowledge/NewNoteScreen.kt` | `Routes.NEW_NOTE` |
| Review Cards | `ui/screens/review/ReviewCardsScreen.kt` | `Routes.REVIEW_CARDS` |
| Review Session | `ui/screens/review/ReviewSessionScreen.kt` | `Routes.REVIEW_SESSION` |
| Session Complete | `ui/screens/review/SessionCompleteScreen.kt` | `Routes.SESSION_COMPLETE` |
| Mastery | `ui/screens/progress/MasteryScreen.kt` | `Routes.MASTERY` |
| Profile | `ui/screens/profile/ProfileScreen.kt` | `Routes.PROFILE` |

## Shared Files

- `navigation/AppNavigation.kt` owns the `NavHost` and connects screen callbacks to routes.
- `navigation/Routes.kt` stores route constants and bottom navigation items.
- `data/Models.kt` defines the data classes used by screens.
- `data/SampleData.kt` provides the current static prototype data.
- `ui/components/` contains reusable UI parts such as auth fields, cards, buttons, bottom navigation, and the main scaffold.
- `ui/theme/` contains colors, typography, app theme, and shared text styles.
