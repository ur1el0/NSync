# NSync Kotlin Work Plan

This is the local coding guide for the 50% Android version of NSync using Kotlin and Jetpack Compose.

NSync is now a generalized personal knowledge and memory app, not only a student app. The mobile code should match the finished prototype direction:

```text
Capture knowledge -> Create review cards -> Review -> Earn XP -> Track mastery
```

## 50% Coding Goal

Build a working static UI version with navigation and sample data.

Implemented in 50% version:

- Login / create account UI
- Dashboard
- Knowledge Base
- Knowledge Detail
- Review Cards
- Review Session
- Mastery / Progress
- User Profile
- Bottom navigation
- Static sample data

Not implemented yet:

- Real authentication
- Database saving
- Django API connection
- OCR
- AI-generated review cards
- Real editable forms
- Persistent quiz scoring

## Screen Names

Use these app labels in the UI:

```text
Dashboard
Knowledge Base
Knowledge Detail
Review Cards
Review Session
Session Complete
Mastery
User Profile
Settings
```

Bottom navigation can stay short:

```text
Dashboard
Notes
Cards
Progress
Profile
```

Avoid calling the generalized feature "Flashcards" everywhere. In screen titles, prefer `Review Cards`.

## Recommended File Structure

```text
mobile/app/src/main/java/com/example/mobile/
|
|-- MainActivity.kt
|
|-- data/
|   |-- Models.kt
|   |-- SampleData.kt
|
|-- navigation/
|   |-- AppNavigation.kt
|   |-- Routes.kt
|
|-- ui/
|   |-- components/
|   |   |-- BottomNavBar.kt
|   |   |-- KnowledgeCard.kt
|   |   |-- ReviewCardItem.kt
|   |   |-- StatCard.kt
|   |   |-- MasteryCard.kt
|   |   |-- PrimaryActionButton.kt
|   |
|   |-- screens/
|   |   |-- AuthScreen.kt
|   |   |-- RegisterScreen.kt
|   |   |-- DashboardScreen.kt
|   |   |-- KnowledgeBaseScreen.kt
|   |   |-- KnowledgeDetailScreen.kt
|   |   |-- ReviewCardsScreen.kt
|   |   |-- ReviewSessionScreen.kt
|   |   |-- SessionCompleteScreen.kt
|   |   |-- MasteryScreen.kt
|   |   |-- ProfileScreen.kt
|   |   |-- SettingsScreen.kt
|   |
|   |-- theme/
|       |-- Color.kt
|       |-- Theme.kt
|       |-- Type.kt
```

## Android Studio File Type

Create most files as `Kotlin File`, not class.

Use `Kotlin File` for:

- Screen files
- Component files
- Data files
- Navigation files

Use `Kotlin Class` only for `MainActivity.kt`, which already exists.

## Package Names

Each file should start with the package matching its folder:

```kotlin
package com.example.mobile.data
package com.example.mobile.navigation
package com.example.mobile.ui.components
package com.example.mobile.ui.screens
package com.example.mobile.ui.theme
```

## Build Order

### 1. Models.kt

Create the data shapes first.

Suggested data classes:

```kotlin
data class KnowledgeItem(
    val id: Int,
    val title: String,
    val collection: String,
    val source: String,
    val context: String,
    val summary: String,
    val fullNote: String,
    val reviewCardCount: Int,
    val updatedLabel: String,
    val masteryPercent: Int,
    val xpEarned: Int
)

data class ReviewCard(
    val id: Int,
    val collection: String,
    val question: String,
    val answer: String,
    val difficulty: String,
    val masteryLabel: String,
    val masteryPercent: Int,
    val updatedLabel: String
)

data class CollectionMastery(
    val collection: String,
    val context: String,
    val masteryPercent: Int
)

data class RecentAttempt(
    val title: String,
    val timeLabel: String,
    val score: String,
    val rank: String
)
```

### 2. SampleData.kt

Use generalized sample data from the finished prototype.

Collections:

```text
Personal Finance
Programming
Interview Prep
Writing Skills
Networking
```

Sample knowledge items:

```text
Building a Simple Monthly Budget
Collection: Personal Finance
Source: Money management notes
Context: Training
Review cards: 6

Django Rest Framework
Collection: Programming
Source: Django Programming
Context: Backend reference
Review cards: 5

Common Interview Questions
Collection: Interview Prep
Source: Personal career notes
Context: Job preparation
Review cards: 8
```

Sample review cards:

```text
Question: What is the purpose of a monthly budget?
Answer: A monthly budget helps track income, expenses, savings, and spending so money is managed intentionally.
Collection: Personal Finance
Difficulty: Easy

Question: What is a VLAN?
Answer: A VLAN is a logical network segment that separates devices within the same physical network.
Collection: Networking
Difficulty: Hard

Question: What is a thesis statement?
Answer: A thesis statement clearly presents the main argument or purpose of a written work.
Collection: Writing Skills
Difficulty: Easy

Question: What is the purpose of a Django model?
Answer: A Django model defines the structure and behavior of data stored in the database.
Collection: Programming
Difficulty: Medium
```

### 3. Routes.kt

Define route constants.

```kotlin
object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val DASHBOARD = "dashboard"
    const val KNOWLEDGE_BASE = "knowledge_base"
    const val KNOWLEDGE_DETAIL = "knowledge_detail"
    const val REVIEW_CARDS = "review_cards"
    const val REVIEW_SESSION = "review_session"
    const val SESSION_COMPLETE = "session_complete"
    const val MASTERY = "mastery"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
}
```

### 4. AppNavigation.kt

Set up navigation between screens.

Start destination:

```text
login
```

Basic flow:

```text
Login -> Dashboard
Register -> Dashboard
Dashboard -> Review Session
Dashboard -> Knowledge Base
Knowledge Base -> Knowledge Detail
Knowledge Detail -> Review Session
Review Cards -> Review Session
Review Session -> Session Complete
Session Complete -> Dashboard
Profile -> Settings
```

### 5. MainActivity.kt

Keep it simple. It should only load theme and navigation.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileTheme {
                AppNavigation()
            }
        }
    }
}
```

### 6. BottomNavBar.kt

Reusable bottom navigation for main screens.

Tabs:

```text
Dashboard
Notes
Cards
Progress
Profile
```

Route mapping:

```text
Dashboard -> Routes.DASHBOARD
Notes -> Routes.KNOWLEDGE_BASE
Cards -> Routes.REVIEW_CARDS
Progress -> Routes.MASTERY
Profile -> Routes.PROFILE
```

Important: Rename `BottomNarBar.kt` to `BottomNavBar.kt` if it still has the typo.

### 7. DashboardScreen.kt

Use `Scaffold` here.

Show:

- NSync title
- Greeting: `Hello, Roosc!`
- Level 5
- XP progress
- Day streak
- Accuracy
- Start Review button
- Recent Knowledge section
- One or two `KnowledgeCard` components
- Bottom navigation

### 8. KnowledgeBaseScreen.kt

This replaces `NotesScreen`.

Show:

- Header: `Knowledge Base`
- Subtitle: `Capture, search, and organize important ideas.`
- Knowledge cards from sample data
- Floating add button
- Bottom navigation

No real search/filter needed yet.

### 9. KnowledgeDetailScreen.kt

Show:

- Title
- Collection chip
- Source
- Context
- Mastery percent
- Start Review button
- Full note text
- Review cards section
- XP progress card
- Bottom navigation

Static content is okay.

### 10. ReviewCardsScreen.kt

Show:

- Header: `Review Cards`
- Review sprint card
- List of review cards
- Difficulty
- Mastery label
- Mastery percent
- Bottom navigation

Use `Cards` or `Review` in the bottom nav label to avoid long text.

### 11. ReviewSessionScreen.kt

Show one static question and answer behavior.

Minimum:

- Card 1 of 5
- XP badge
- Question
- Show Answer button
- Skip button

If you have time, add local UI state:

```kotlin
var showAnswer by remember { mutableStateOf(false) }
```

When `showAnswer == true`, display the answer and buttons:

```text
Wrong
Correct
```

Correct can navigate to `SessionCompleteScreen`.

### 12. SessionCompleteScreen.kt

Show:

- Score: 4/5
- Accuracy: 80%
- XP earned: +125 XP
- 13 day streak
- Level progress
- Review Again button
- Back to Dashboard button

### 13. MasteryScreen.kt

This replaces or updates `ProgressScreen`.

Show:

- Total XP
- Level
- Streak
- Accuracy
- Collection Mastery
- Recent Attempts
- Bottom navigation

Add at least 3 mastery cards:

```text
Personal Finance - 88%
Programming - 72%
Interview Prep - 64%
```

### 14. ProfileScreen.kt

Show:

- User Profile
- Name: Roosc
- Learning Goal: Build durable knowledge
- Day streak
- XP progress
- Settings button
- Logout button
- Bottom navigation

### 15. SettingsScreen.kt

Optional but useful because it exists in the prototype.

Show static settings:

- Email address
- Daily review goal
- Reminder time
- Review difficulty
- Streak reminders
- Notifications
- Appearance
- Log out

## Reusable Components

Create these only when needed. Do not overbuild early.

### KnowledgeCard.kt

Used in Dashboard and Knowledge Base.

Props:

```text
title
collection
source
reviewCardCount
updatedLabel
onClick
```

### ReviewCardItem.kt

Used in Review Cards and Knowledge Detail.

Props:

```text
question
collection
difficulty
masteryLabel
masteryPercent
```

### StatCard.kt

Used in Dashboard, Mastery, Profile.

Props:

```text
label
value
optional icon
```

### MasteryCard.kt

Used in Mastery.

Props:

```text
collection
context
masteryPercent
```

### PrimaryActionButton.kt

Reusable big blue button.

Props:

```text
text
onClick
```

## Compose Components To Use

Use these to satisfy the requirement for Compose UI components:

```text
Scaffold
TopAppBar or custom top row
NavigationBar
NavigationBarItem
FloatingActionButton
Card
OutlinedTextField
FilterChip or AssistChip
LinearProgressIndicator
LazyColumn
Button
OutlinedButton
IconButton
```

## Minimum Working Version

If time is short, finish these first:

```text
MainActivity.kt
Models.kt
SampleData.kt
Routes.kt
AppNavigation.kt
BottomNavBar.kt
DashboardScreen.kt
KnowledgeBaseScreen.kt
ReviewCardsScreen.kt
ReviewSessionScreen.kt
MasteryScreen.kt
ProfileScreen.kt
```

Then add:

```text
KnowledgeDetailScreen.kt
SessionCompleteScreen.kt
RegisterScreen.kt
SettingsScreen.kt
```

## PDF Submission Notes

In the final PDF, include:

- Brief app description
- Prototype screenshots
- Actual Android app screenshots
- Complete Kotlin source code by file
- Short purpose of each Kotlin file
- Midterm learning reflection

For the 50% functionality explanation:

```text
The Android version implements the main NSync interface using Jetpack Compose. It includes login/register UI, dashboard, knowledge base, review cards, review session, mastery tracking, profile, bottom navigation, and static sample data. Real authentication, persistent storage, Django API connection, OCR, and AI card generation are planned for future development.
```

## React Mental Model

```text
MainActivity.kt          = main.jsx
AppNavigation.kt         = App.jsx / React Router
Routes.kt                = route constants
ui/screens/              = pages/
ui/components/           = components/
data/Models.kt           = TypeScript types/interfaces
data/SampleData.kt       = mockData.js
ui/theme/                = theme/styles
```

`@Composable` functions are like React components.
