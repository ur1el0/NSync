# Knowledge Detail Screen

Kotlin file: `mobile/app/src/main/java/com/example/mobile/ui/screens/knowledge/KnowledgeDetailScreen.kt`  
Route: `Routes.KNOWLEDGE_DETAIL`

## Purpose

`KnowledgeDetailScreen` shows one selected knowledge item, including its collection, source, context, note content, mastery, XP, and review card count.

## Functions

- `KnowledgeDetailScreen(onStartReviewClick, onRouteClick)` is the main composable. It currently uses `SampleData.detailKnowledge` as the displayed item.

## Navigation

- Opened from Dashboard or Knowledge Base.
- Start Review calls `onStartReviewClick`, which navigates to `Routes.REVIEW_SESSION`.
- Bottom navigation calls `onRouteClick`.

## Imports And Compose Pieces

- `Column`, `Row`, `Text`, and `Spacer` arrange the detail content.
- The screen uses normal Compose layout modifiers for padding, spacing, and full-width layout.

## Shared Components And Styling

- Uses `MainScreenScaffold` for the app wrapper.
- Uses `ProgressSummaryCard` for mastery and XP summary.
- Uses `PrimaryScreenButton` for the Start Review button.
- Uses `ScreenHeroStyle`, `ScreenBodyStyle`, `ScreenSmallStyle`, and related shared text styles.
