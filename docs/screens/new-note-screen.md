# New/Edit Note Screen

**Source:** `mobile/app/src/main/java/com/example/mobile/ui/screens/knowledge/NewNoteScreen.kt`
**Routes:** `Routes.NEW_NOTE`, `Routes.EDIT_NOTE` with `noteId`

## Purpose and Data Flow

The same screen creates and edits notes. Without `noteId`, Save calls `NewNoteViewModel.createNote`. With `noteId`, `LaunchedEffect` loads the note and Save calls `updateNote`. A saved flag invokes `onSaveClick`, returning to the Knowledge Base.

## Functions

- `NewNoteScreen`: owns editable title, collection/tag, source/context, and content state; selects create versus edit behavior.
- `NewNoteLabel`: compact form label composable.
- `NewNoteField`: reusable styled text field wrapper for note inputs.

## Important Imports

- `LazyColumn`, `Column`, `Row`, `Box`: editor layout.
- Material `Scaffold`, `OutlinedTextField`, `Button`, `OutlinedButton`, and `Card`: form structure/actions.
- `LaunchedEffect`, `remember`, `mutableStateOf`: loaded values and form state.
- `NewNoteViewModel`, `BottomNavBar`, and NSync theme values: API action, navigation, styling.

## Navigation

Back/Cancel pop the route. A successful save pops back to `KNOWLEDGE_BASE`. Bottom navigation is also available from this form screen.
