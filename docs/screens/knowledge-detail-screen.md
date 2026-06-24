# Knowledge Detail Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/knowledge/KnowledgeDetailScreen.kt`
**Route:** `Routes.KNOWLEDGE_DETAIL` with `noteId`

## Purpose and Data Flow

`KnowledgeDetailScreen` loads one note in `LaunchedEffect(noteId)` through `KnowledgeDetailViewModel.loadNote`. It exposes the note's content, mastery summary, review-card count, and note actions. `deleteNote` sets state that triggers `onDeleted`.

## Functions and Imports

- `KnowledgeDetailScreen`: the single composable; reads `KnowledgeDetailUiState` and conditionally emits `LazyListScope.item` content.
- `LaunchedEffect`: reloads for a different route argument and reacts to successful deletion.
- `MainScreenScaffold`, `ProgressSummaryCard`, and `PrimaryScreenButton`: common app layout and cards/actions.
- Material `OutlinedButton`, Compose `Row`, and theme styles: edit/delete controls and content formatting.

## Navigation

- Edit opens `editNote(noteId)`.
- Add Review Card opens `newFlashcard(noteId)`.
- Start Review opens `REVIEW_SESSION`.
- Delete returns to `KNOWLEDGE_BASE`; back returns without deleting.
