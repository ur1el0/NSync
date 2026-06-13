# Knowledge Base Screen

Kotlin file: `mobile/app/src/main/java/com/example/mobile/ui/screens/knowledge/KnowledgeBaseScreen.kt`  
Route: `Routes.KNOWLEDGE_BASE`

## Purpose

`KnowledgeBaseScreen` lists saved knowledge items and provides the New Note entry point.

## Functions

- `KnowledgeBaseScreen(onKnowledgeClick, onNewNoteClick, onRouteClick)` is the main composable. It reads `SampleData.knowledgeItems`, displays each item, and shows the bottom New Note action.

## Navigation

- Bottom navigation opens this screen through `Routes.KNOWLEDGE_BASE`.
- Tapping a knowledge item calls `onKnowledgeClick`, which navigates to `Routes.KNOWLEDGE_DETAIL`.
- The New Note button calls `onNewNoteClick`, which navigates to `Routes.NEW_NOTE`.
- Bottom navigation calls `onRouteClick`.

## Imports And Compose Pieces

- `Column`, `Spacer`, and `Text` build the screen structure.
- `LazyColumn` is not used because the prototype list is small and static.

## Shared Components And Styling

- Uses `MainScreenScaffold` for the shared main layout and bottom nav.
- Uses `KnowledgeListCard` for each knowledge item row.
- Uses `PrimaryScreenButton` for the New Note button.
- Uses `ScreenHeroStyle`, `ScreenBodyStyle`, and `ScreenSectionStyle` from `ScreenStyles.kt`.
