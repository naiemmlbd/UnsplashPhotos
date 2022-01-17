# UnsplashPhotos
--------------
## _An android app that displays a list of photos as a gallery._

<html>
<body>
<p align="center"><img src="https://github.com/jhnaiem/UnsplashPhotos/blob/develop/Mockup/ezgif.com-gif-maker.gif" width="150" height="250"/></p>
</body>
</html>
## Feature List

- Get a list of photos from “https://api.unsplash.com/”
- A user can infinitely scroll on the gallery screen
- A user can tap on a photo to see a full-screen photo
- The app’s flavor/scheme can be changed easily so that changing from development API to      production API becomes trivial
- Cache images
- Cache API response
- Allow saving photos in JPEG format to the local gallery
- Allow sharing the photo

## The architectural pattern used in the app
In this app, I have adopted Clean architecture with MVVM.
According to clean architecture, I have divided the code into three layers:
1. Presentation/UI layer
2. Domain layer
3. Data Layer
<html>
<body>
<p align="center"><img src="https://github.com/jhnaiem/UnsplashPhotos/blob/develop/Mockup/1_uQYJsu2agzmjp9s_eEieeA.png" width="3=200" height="180"/></p>
</body>
</html>
**1. Domain layer**
This is the center of Clean Architecture. It establishes communication between the data and the Presentation layer. This layer contains a Domain model and repository( interface). We can see, this layer is in the middle of the clean architecture and the Data layer, and the Presentation layer only knows the Domain layer. Here, Outer layers( Data, UI ) depend on inner layers, but the inner layer doesn't depend on any, even it doesn't know about the data layer and the UI layer. Also, there are no implementation details in the inner layer, only business logic.

**2. Data layer**
This layer contains the Repository implementation, UseCase’s implementations, and Data source. Data sources get the required data by communicating with the server or local DB. With the help of repository implementation, I have managed the data from the Data source and mapped it to the domain model using a mapper. Then the repository implementation provides domain objects to the interactors. So, here I have introduced a repository pattern. The repository pattern helps to hide the data source of the application.

**3. UI layer**
The main responsibilities of this layer are to get triggered to request data and, after getting the data showing them to the user.
In the UnspalshPhotos app, this layer includes an activity that is the main activity which is the entry point to the app, two fragments, and two ViewModel. As I have followed MVVM and which is a technique to manage the UI layer. MVVM separated the presentation layer into three components, and they are,
1. Model
2. View
3. ViewModel

Model:
This component is the output of the UseCase of the Domain layer. It is basically the Domain model (Photo) which we get from UseCase after mapping happens in the data layer.

View:
This is the UI part of the app, which includes two fragments (). Here in MVVM, the view is decoupled from the View Model. We use the observer pattern for decoupling. It was obtained by LiveData previously, but now we use Kotlin Flow.

ViewModel:
The ViewModel is the main point of MVVM. I have established a connection between the Presentation layer and the Domain layer by putting useCase in ViewModel(put corresponding use case in the constructor). This component handles the data passed from the UseCases and sends them to the view and vice versa.

##UML class diagram
<html>
<body>
<p align="center"><img src="https://github.com/jhnaiem/UnsplashPhotos/blob/develop/Mockup/UML.jpeg" width="300" height="250"/></p>
</body>
</html>

Libraries Used
--------------
* [Foundation][0] - Components for core system capabilities, Kotlin extensions and support for multidex and automated testing.
    * [AppCompat][1] - Degrade gracefully on older versions of Android.
    * [Android KTX][2] - Write more concise, idiomatic Kotlin code.
      Dependency Injection
    * [Retrofit][42] - A type-safe HTTP client for Android and Java.

*  [Architecture][10] - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.
    * [Data Binding][11] - Declaratively bind observable data to UI elements.
    * [Lifecycles][12] - Create a UI that automatically responds to lifecycle events.
    * [Kotlin Flows][13] - Kotlin Flow can handle streams of data asynchronously which is being executed sequentially.
    * [ViewModel][17] - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.

* [UI][30] - Details on why and how to use UI Components in your apps - together or separate
    * [Fragment][34] - A basic unit of composable UI.
    * [Animations & Transitions][31] - Move widgets and transition between screens.
    * [Layout][35] - Lay out widgets using different algorithms.
    * [Paging 3][40] - The Paging library helps you load and display pages of data from a larger dataset from local storage or over network.
* Third party and miscellaneous libraries
    * [Kotlin Coroutines][91] for managing background threads with simplified code and reducing needs for callbacks
    * [Glide][90] for image loading
    * [Hilt][92]: Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.

[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[4]: https://developer.android.com/training/testing/
[10]: https://developer.android.com/jetpack/arch/
[11]: https://developer.android.com/topic/libraries/data-binding/
[12]: https://developer.android.com/topic/libraries/architecture/lifecycle
[13]: https://developer.android.com/kotlin/flow
[14]: https://developer.android.com/topic/libraries/architecture/navigation/
[16]: https://developer.android.com/topic/libraries/architecture/room
[17]: https://developer.android.com/topic/libraries/architecture/viewmodel
[18]: https://developer.android.com/topic/libraries/architecture/workmanager
[30]: https://developer.android.com/guide/topics/ui
[31]: https://developer.android.com/training/animation/
[34]: https://developer.android.com/guide/components/fragments
[35]: https://developer.android.com/guide/topics/ui/declaring-layout
[91]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[90]: https://bumptech.github.io/glide/
[92]: https://developer.android.com/training/dependency-injection/hilt-android
[93]: https://developer.android.com/training/dependency-injection
[40]: https://developer.android.com/topic/libraries/architecture/paging/v3-migration
[41]: https://developer.android.com/training/dependency-injection/hilt-android
[42]: https://square.github.io/retrofit/
