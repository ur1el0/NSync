# New Note Screen

Kotlin file: `mobile/app/src/main/java/com/example/mobile/ui/screens/knowledge/NewNoteScreen.kt`  
Route: `Routes.NEW_NOTE`

## Purpose

`NewNoteScreen` is the prototype note creation page. It lets the user type a title, collection, source, context, and note body, then save or cancel.

## Functions

- `NewNoteScreen(onBackClick, onSaveClick, onRouteClick)` is the main composable. It owns temporary form state for title, collection, source, context, and content.
- `NewNoteLabel(text)` draws labels for the compact form fields.
- `NewNoteField(value, onValueChange, placeholder, modifier)` draws reusable text input boxes.

## Navigation

- Opened from Knowledge Base through `Routes.NEW_NOTE`.
- Back and Cancel call `onBackClick`.
- Save calls `onSaveClick`, which currently returns to `Routes.KNOWLEDGE_BASE`.
- Bottom navigation calls `onRouteClick`.

## Imports And Compose Pieces

- `remember` and `mutableStateOf` hold unsaved form input.
- `BasicTextField` is used for custom prototype-like fields.
- `IconButton`, `TextButton`, `Button`, `Column`, `Row`, and `Box` build the editor layout.

## Shared Components And Styling

- Uses `MainScreenScaffold` for the main app frame.
- Uses `NSyncBlue`, `InterFontFamily`, and local styles such as `NewNoteTopActionStyle`, `NewNoteTinyStyle`, and `NewNoteFieldStyle`.
- Text field content is styled black through `NewNoteFieldStyle`.
- Data is not persisted yet; this is still static prototype behavior.
