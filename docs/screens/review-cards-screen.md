# Review Cards Screen

Kotlin file: `mobile/app/src/main/java/com/example/mobile/ui/screens/review/ReviewCardsScreen.kt`  
Route: `Routes.REVIEW_CARDS`

## Purpose

`ReviewCardsScreen` shows the available review cards and gives the user a button to start a review session.

## Functions

- `ReviewCardsScreen(onStartReviewClick, onRouteClick)` is the main composable. It reads `SampleData.reviewCards` and displays the cards as a list.

## Navigation

- Opened from bottom navigation or the dashboard add action.
- Start Review calls `onStartReviewClick`, which navigates to `Routes.REVIEW_SESSION`.
- Bottom navigation calls `onRouteClick`.

## Imports And Compose Pieces

- `Column`, `Text`, and `Spacer` build the page.
- Static sample data is shown directly because there is no database layer yet.

## Shared Components And Styling

- Uses `MainScreenScaffold` for the shared layout and bottom navigation.
- Uses `ReviewCardListItem` for each card.
- Uses `PrimaryScreenButton` for Start Review.
- Uses shared screen text styles from `ScreenStyles.kt`.
