# Review Sessions Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/review/ReviewCardsScreen.kt`
**Route:** `Routes.REVIEW_CARDS`

## Purpose and Data Flow

This page presents sessions, not an ungrouped card list. `ReviewCardsViewModel.loadCards` fetches the user's cards, then the screen groups them by connected note ID, source-note title, and collection. Each group becomes one `ReviewSessionListItem`.

## Functions and Imports

- `ReviewCardsScreen(onStartSessionClick, onAddCardClick, onRouteClick, viewModel)`: loads cards, renders loading/error/empty states, groups cards, and emits session cards.
- `DisposableEffect`, `LifecycleEventObserver`, and `LocalLifecycleOwner`: reload data when the page resumes.
- `MainScreenScaffold`: supplies page shell and bottom navigation.
- `ReviewSessionListItem`: renders session title/tag/count plus Start Review and Add Card actions.

## Navigation

- Start Review opens `reviewSessionForNote(noteId)` and therefore reviews every card for that note.
- Add Card opens `newFlashcard(noteId)`.
- Bottom navigation is delegated through `onRouteClick`.
