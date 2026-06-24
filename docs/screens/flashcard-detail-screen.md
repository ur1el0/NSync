# Flashcard Detail Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/review/FlashcardDetailScreen.kt`
**Route:** `Routes.FLASHCARD_DETAIL` with `cardId`

## Purpose and Data Flow

`FlashcardDetailScreen` loads one card through `FlashcardDetailViewModel.loadCard(cardId)`. It displays the question, answer, difficulty, and mastery. Deletion is handled by `deleteCard`; once state reports success, the screen calls `onDeleted`.

## Functions and Imports

- `FlashcardDetailScreen`: the single composable, with `LaunchedEffect` for loading/deletion state.
- `MainScreenScaffold`, `ProgressSummaryCard`, `PrimaryScreenButton`: shared layout and progress/action UI.
- Material `OutlinedButton`, Compose `Row`, and theme colors/styles: edit/delete actions.

## Navigation

- Edit opens `editFlashcard(cardId)`.
- Start Note Session opens `reviewSessionForNote(card.knowledgeItemId)`.
- Delete returns to `REVIEW_CARDS`; back pops the detail route.
