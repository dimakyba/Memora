### Memora: An application for learning information by spaced repetition

## Annotation:

The purpose of this work is to develop a mobile application for learning information by spaced repetition, which allows users to create decks of cards containing text and images, import and export them, set up repetition times, and receive push notifications to optimize the memorization process. The application implements an interval repetition algorithm to improve learning efficiency.

stack:
+ Android Studio
+ Kotlin
+ Jetpack Compose


TO-DO:
- [ ] class hierarchy
- [ ] main menu screen
- [ ] deck creation button
- [ ] create deck
- [ ] deck display after clicking
- [ ] card creation: text, picture, difficulty
- [ ] display the card in the deck
- [ ] view card when clicked
- [ ] mark as viewed
- [ ] popup menu when you press three dots view edit info delete
- [ ] customize push notifications
- [ ] implement 2 interval counting algorithms for two difficulties: difficult: frequent and fast. easy: infrequent and slow.

## dev log:

### 03.01.25

init the project, made git branches

### 04.01.25 
1) created design prototype: https://www.figma.com/design/BUK3l9sfjKn4nrAt1FZQty/Memora?node-id=601-10&p=f
2) using Jetpack Compose created top bar and Add Button

### 02.03.2025
let's start from the beginning

architecture:
    + MVVM pattern
    + notifications:
        + NotificationManager.kt
        + NotificationScheduler.kt
    + data (Room (DB), JSON, Repositories)
        local (db):
            + CardEntity.kt
            + DeckEntity.kt
            + CardDao.kt
            + DeckDao.kt
            + AppDatabase.kt
        repository: 
            + CardRepository.kt
            + DeckRepository.kt
        json:
            + JsonStorage.kt (import and export as json)
    + domain (business logic (models, algorithms, use-cases))
        models: (pure models without dependencies)
            + Card.kt
            + Deck.kt
            + Content.kt
        algorithms:
            + RepetitionAlgorithm.kt
            + FixedIntervalAlgorithm.kt
            + AdaptiveAlgorithm.kt
        usecases:
            + GetCardsForTodayUseCase.kt
            + UpdateCardReviewUseCase.kt
            + ScheduleNextReviewUseCase.kt  
            + GetDecksUseCase.kt  
            + GetCardsByDeckUseCase.kt
            + UpdateCardProgressUseCase.kt
            + DeckProgressUseCase.kt
    + presentation (UI, ViewModel)
        screens:
            settings:
                SettingsScreen.kt
                SettingsViewModel.kt
                AboutScreen.kt
            decklist:
                DeckListScreen.kt
                DeckListViewModel.kt
            card:
                CardScreen.kt
                CardViewModel.kt
        components:
            CardItem.kt
            DeckItem.kt
            ReviewProgressBar.kt
    + di (dependency injection (Dagger Hilt))
        + AppModule.kt (general dependencies)
        + DataModule.kt (storage and repositories)
        + DomainModule.kt (use-cases and algorithms)
        + PresentationModule.kt (screens and ui components)
        + NotificationModule.kt

described our models with data classes
