# NSync Screen Documentation

Each page below documents its current route, composables, data dependency, important imports, and navigation behavior. The complete callable list is in [../function-reference.md](../function-reference.md).

| Screen | Kotlin file | Route | Documentation |
| --- | --- | --- | --- |
| Login | `ui/screens/auth/LoginScreen.kt` | `LOGIN` | [login-screen.md](login-screen.md) |
| Register | `ui/screens/auth/RegisterScreen.kt` | `REGISTER` | [register-screen.md](register-screen.md) |
| Dashboard | `ui/screens/dashboard/DashboardScreen.kt` | `DASHBOARD` | [dashboard-screen.md](dashboard-screen.md) |
| Knowledge Base | `ui/screens/knowledge/KnowledgeBaseScreen.kt` | `KNOWLEDGE_BASE` | [knowledge-base-screen.md](knowledge-base-screen.md) |
| Knowledge Detail | `ui/screens/knowledge/KnowledgeDetailScreen.kt` | `KNOWLEDGE_DETAIL` | [knowledge-detail-screen.md](knowledge-detail-screen.md) |
| New/Edit Note | `ui/screens/knowledge/NewNoteScreen.kt` | `NEW_NOTE`, `EDIT_NOTE` | [new-note-screen.md](new-note-screen.md) |
| Review Sessions | `ui/screens/review/ReviewCardsScreen.kt` | `REVIEW_CARDS` | [review-cards-screen.md](review-cards-screen.md) |
| Flashcard Detail | `ui/screens/review/FlashcardDetailScreen.kt` | `FLASHCARD_DETAIL` | [flashcard-detail-screen.md](flashcard-detail-screen.md) |
| New/Edit Flashcard | `ui/screens/review/NewFlashcardScreen.kt` | `NEW_FLASHCARD`, `EDIT_FLASHCARD` | [new-flashcard-screen.md](new-flashcard-screen.md) |
| Review Session | `ui/screens/review/ReviewSessionScreen.kt` | `REVIEW_SESSION`, `REVIEW_SESSION_NOTE` | [review-session-screen.md](review-session-screen.md) |
| Session Complete | `ui/screens/review/SessionCompleteScreen.kt` | `SESSION_COMPLETE` | [session-complete-screen.md](session-complete-screen.md) |
| Mastery | `ui/screens/progress/MasteryScreen.kt` | `MASTERY` | [mastery-screen.md](mastery-screen.md) |
| Profile | `ui/screens/profile/ProfileScreen.kt` | `PROFILE` | [profile-screen.md](profile-screen.md) |
| Settings | `ui/screens/settings/SettingsScreen.kt` | `SETTINGS` | [settings-screen.md](settings-screen.md) |

`AppNavigation.kt` owns all route registrations. `MainScreenScaffold.kt` supplies the shared header, scroll container, and bottom navigation for tab-based screens.
