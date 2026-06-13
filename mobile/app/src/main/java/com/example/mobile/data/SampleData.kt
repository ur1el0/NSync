package com.example.mobile.data

object SampleData {
    val userProfile = UserProfile(
        name = "Roosc",
        email = "zanoroosc@gmail.com",
        learningGoal = "Build durable knowledge",
        level = 5,
        totalXp = 2450,
        xpToNextLevel = 550,
        streakDays = 12,
        accuracyPercent = 92
    )

    val knowledgeItems = listOf(
        KnowledgeItem(
            id = 1,
            title = "Building a Simple Monthly Budget",
            collection = "Personal Finance",
            source = "Money management notes",
            context = "Money management notes",
            summary = "A monthly budget helps organize income, expenses, savings, and spending priorities.",
            fullNote = """
                A monthly budget helps organize income, expenses, savings, and spending priorities. It gives a clear view of where money goes and helps prevent unnecessary overspending.

                Management Skills

                A simple budget can divide money into needs, wants, savings, and debt payments. This makes financial decisions easier because each peso has a purpose.

                Reviewing a budget regularly helps identify spending habits, prepare for upcoming expenses, and adjust goals when income or priorities change.
            """.trimIndent(),
            reviewCardCount = 6,
            updatedLabel = "Updated yesterday",
            masteryPercent = 80,
            xpEarned = 120
        ),
        KnowledgeItem(
            id = 2,
            title = "Django Rest Framework",
            collection = "Programming",
            source = "Django Programming",
            context = "Backend reference",
            summary = "Django REST Framework helps build API endpoints for web and mobile applications.",
            fullNote = """
                Django REST Framework is a toolkit for building Web APIs using Django. It provides serializers, viewsets, routers, authentication helpers, and browsable API tools.

                Serializers convert Django model instances into JSON-friendly data. Views and viewsets handle incoming requests and return API responses.

                DRF is useful for connecting a Django backend to a mobile app because the Android client can request and send structured data through API endpoints.
            """.trimIndent(),
            reviewCardCount = 5,
            updatedLabel = "Updated today",
            masteryPercent = 72,
            xpEarned = 85
        ),
        KnowledgeItem(
            id = 3,
            title = "Common Interview Questions",
            collection = "Interview Prep",
            source = "Personal career notes",
            context = "Job preparation",
            summary = "Common interview questions help prepare clear answers about skills, projects, and work habits.",
            fullNote = """
                Interview preparation helps organize answers before the actual interview. Strong answers are specific, honest, and connected to real project experience.

                Technical interview questions often test core concepts, problem solving, and communication. Behavioral questions focus on teamwork, responsibility, conflict, and learning from mistakes.

                A good answer explains the situation, action, and result without sounding memorized.
            """.trimIndent(),
            reviewCardCount = 8,
            updatedLabel = "Updated this week",
            masteryPercent = 64,
            xpEarned = 64
        )
    )

    val reviewCards = listOf(
        ReviewCard(
            id = 1,
            knowledgeItemId = 1,
            collection = "Personal Finance",
            question = "What is the purpose of a monthly budget?",
            answer = "A monthly budget helps track income, expenses, savings, and spending so money is managed intentionally.",
            difficulty = "Easy",
            masteryLabel = "Bronze Mastery",
            masteryPercent = 52,
            updatedLabel = "Edited today"
        ),
        ReviewCard(
            id = 2,
            knowledgeItemId = 2,
            collection = "Programming",
            question = "What does Django REST Framework help you build?",
            answer = "Django REST Framework helps build API endpoints that let web or mobile clients send and receive structured data.",
            difficulty = "Medium",
            masteryLabel = "Gold Mastery",
            masteryPercent = 80,
            updatedLabel = "Edited yesterday"
        ),
        ReviewCard(
            id = 3,
            knowledgeItemId = 3,
            collection = "Interview Prep",
            question = "What makes a strong interview answer?",
            answer = "A strong interview answer is specific, honest, and connected to real project experience.",
            difficulty = "Easy",
            masteryLabel = "Silver Mastery",
            masteryPercent = 72,
            updatedLabel = "Edited this week"
        ),
        ReviewCard(
            id = 4,
            knowledgeItemId = 2,
            collection = "Programming",
            question = "What is the purpose of a Django model?",
            answer = "A Django model defines the structure and behavior of data stored in the database.",
            difficulty = "Medium",
            masteryLabel = "Silver Mastery",
            masteryPercent = 72,
            updatedLabel = "Edited today"
        ),
        ReviewCard(
            id = 5,
            knowledgeItemId = 3,
            collection = "Interview Prep",
            question = "What is the difference between authentication and authorization?",
            answer = "Authentication verifies identity, while authorization determines what actions or resources a user can access.",
            difficulty = "Medium",
            masteryLabel = "Bronze Mastery",
            masteryPercent = 60,
            updatedLabel = "Edited this week"
        )
    )

    val collectionMastery = listOf(
        CollectionMastery(
            collection = "Personal Finance",
            context = "Money management",
            masteryPercent = 88
        ),
        CollectionMastery(
            collection = "Programming",
            context = "Backend development",
            masteryPercent = 72
        ),
        CollectionMastery(
            collection = "Interview Prep",
            context = "Career preparation",
            masteryPercent = 64
        ),
        CollectionMastery(
            collection = "Writing Skills",
            context = "Communication",
            masteryPercent = 58
        )
    )

    val recentAttempts = listOf(
        RecentAttempt(
            title = "Finance Review Session",
            timeLabel = "2 hours ago",
            score = "9/10",
            rank = "Gold"
        ),
        RecentAttempt(
            title = "Django API Review",
            timeLabel = "Yesterday",
            score = "7/10",
            rank = "Silver"
        ),
        RecentAttempt(
            title = "Interview Prep Sprint",
            timeLabel = "3 days ago",
            score = "80%",
            rank = "Review"
        )
    )

    val sessionSummary = ReviewSessionSummary(
        score = "4/5",
        accuracyPercent = 80,
        xpEarned = 125,
        streakDays = 13,
        levelLabel = "Level 5",
        xpToNextLevel = 675
    )

    val reviewSessionCard = reviewCards.first()

    val dashboardRecentKnowledge = knowledgeItems.first().copy(
        reviewCardCount = 8,
        xpEarned = 64
    )

    val detailKnowledge = knowledgeItems.first().copy(
        collection = "Finance",
        reviewCardCount = 12,
        masteryPercent = 80,
        xpEarned = 120
    )

    val draftReviewCardQuestion = "What is the purpose of a monthly budget?"

    val draftReviewCardAnswer =
        "A monthly budget helps track income, expenses, savings, and spending so money is managed intentionally."

    val collections = listOf(
        "All",
        "Personal Finance",
        "Programming",
        "Interview Prep",
        "Writing Skills"
    )
}
