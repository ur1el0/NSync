# New/Edit Flashcard Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/review/NewFlashcardScreen.kt`
**Routes:** `Routes.NEW_FLASHCARD` with `noteId`, `Routes.EDIT_FLASHCARD` with `cardId`

## Purpose and Data Flow

This form creates a card for a selected note or edits an existing card. `NewFlashcardViewModel.saveFlashcard` posts a new question/answer/difficulty. In edit mode, `loadCard` populates fields and `updateFlashcard` sends the update.

## Functions and Imports

- `NewFlashcardScreen`: owns temporary field values and chooses create/update behavior from nullable `noteId` and `cardId`.
- `LaunchedEffect(cardId)`: loads the card in edit mode.
- `LaunchedEffect(viewModel.loadedCard)` and `LaunchedEffect(viewModel.saved)`: copy loaded fields into local state and close after save.
- Material `OutlinedTextField`, `Button`, and `OutlinedButton`; Compose `Column`/`Row`; `NewFlashcardViewModel`; and theme styles form the screen's direct dependencies.

## Navigation

Cancel/back pops the route. Successful create/update invokes `onSaved`, also popping the route.
