
Welcome to the Android Technical Learning project! This repository is a personal space dedicated to exploring new Android development features and testing various third-party libraries. The goal is to continuously update and expand knowledge about the latest trends and technologies in Android development.

## Clean architecture with 3 main modules
- Data (for database, API and preferences code)
- Domain (for business logic and models)
- AndroidApp (for UI logic, with MVVM)

## Tests
- [Mockk](https://mockk.io/) library
- Unit tests[JUnit4](https://junit.org/junit4/)
- Mockito
- Robolectric

## Other useful features
- This version brings [Modularization](https://developer.android.com/topic/modularization)
- Version Management (with [Version catalog](https://docs.gradle.org/current/userguide/platforms.html))
- Dependency injection (with [Koin](https://insert-koin.io/docs/reference/koin-android/get-instances))
- Network calls (with [Retrofit](https://square.github.io/retrofit/))
- Reactive programming (with [Kotlin Flows](https://kotlinlang.org/docs/reference/coroutines/flow.html))
- Android architecture components to share ViewModels during configuration changes
- Google [Material Design](https://material.io/blog/android-material-theme-color) library
- Declarative UI (with [Jetpack Compose](https://developer.android.com/jetpack/compose))
    - Compose Navigation (with [Hilt Support](https://developer.android.com/jetpack/compose/libraries#hilt-navigation) and Assisted Inject Example)
- Code style checking[Ktlint](https://github.com/pinterest/ktlint)
- Image loading[glide](https://github.com/bumptech/glide)
- Edge To Edge Configuration

# Getting started
To start exploring and contributing to this project, clone the repository and open it in Android Studio:
```shell
  git clone git@github.com:nguyenhuuduc458/android-technical-learning.git
```
Make sure to update the Gradle dependencies and build the project:
```shell
  /gradlew build
```

# Notes
- Android Template contains `.github/workflows` for ktlint check, unit testing and build apk in release mode.