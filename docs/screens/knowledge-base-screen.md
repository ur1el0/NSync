# Knowledge Base Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/knowledge/KnowledgeBaseScreen.kt`
**Route:** `Routes.KNOWLEDGE_BASE`

## Purpose and Data Flow

`KnowledgeBaseViewModel` loads notes through `NSyncRepository.getKnowledgeItems`. `KnowledgeBaseScreen` renders the result, a loading/error/empty state, and one `KnowledgeListCard` for each API-backed note.

## Functions and Imports

- `KnowledgeBaseScreen(onKnowledgeClick, onNewNoteClick, onRouteClick, viewModel)`: the only screen composable.
- `items` from `foundation.lazy` creates lazy list entries inside `MainScreenScaffold`'s `LazyColumn` scope.
- `KnowledgeListCard` renders each note; `PrimaryScreenButton` renders `+ New Note`.
- `viewModel()` supplies the lifecycle-aware `KnowledgeBaseViewModel`.

## Navigation

- Tapping a note opens `knowledgeDetail(item.id)`.
- `+ New Note` opens `NEW_NOTE`.
- Bottom navigation is delegated through `onRouteClick`.
