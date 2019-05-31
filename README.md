# Wedding-plan

## Description
App designed to plan wedding, send and receive notifications via FCM, used firebase storage to upload images and view them.

<div style="display: flex">
<img src="https://serving.photos.photobox.com/6751358829caa806727be139d8bae8f2c7c8cc5b5393c77c7185d7d8a929599b7cd5dde6.jpg" width="250" height="450" >
<img src="https://serving.photos.photobox.com/36185198c53708fa3b23889afa77966d6d6d0e7dd0950f07ca154cfef4c0bd2f753d24bd.jpg" width="250" height="450">
<img src="https://serving.photos.photobox.com/1418785637bcf67640b1f40673be6fc20afe898d75189d289b67831b969db2e384c1991b.jpg" width="250" height="450">
<img src="https://serving.photos.photobox.com/538560501e6b07eddb25a6b40c3c5c116182f17aa38d4ca49948bb0ae1f87e2f2fc8b2e5.jpg" width="250" height="450">
</div>

#### Common Project Requirements
- [x] App conforms to common standards found in the Android Nanodegree General Project Guidelines

- [x] App is written solely in the Java Programming Language

- [x] App utilizes stable release versions of all libraries, Gradle, and Android Studio.

#### Core Platform Development
- [x] App integrates a third-party library.

- [x] App validates all input from servers and users. If data does not exist or is in the wrong format, the app logs this fact and does not crash.

- [x] App includes support for accessibility. That includes content descriptions, navigation using a D-pad, and, if applicable, non-audio versions of audio cues.

- [x] App keeps all strings in a strings.xml file and enables RTL layout switching on all layouts.

- [x] App provides a widget to provide relevant information to the user on the home screen.

#### Google Play Services
- [x] App integrates two or more Google services. Google service integrations can be a part of Google Play Services or Firebase.

- [x] Each service imported in the build.gradle is used in the app.

- [x] If Location is used, the app customizes the user’s experience by using the device's location.

- [x] If Admob is used, the app displays test ads. If Admob was not used, student meets specifications.

- [x] If Analytics is used, the app creates only one analytics instance. If Analytics was not used, student meets specifications.

- [x] If Maps is used, the map provides relevant information to the user. If Maps was not used, student meets specifications.

- [x] If Identity is used, the user’s identity influences some portion of the app. If Identity was not used, student meets specifications.

#### Material Design
- [x] App theme extends AppCompat.

- [x] App uses an app bar and associated toolbars.

- [x] App uses standard and simple transitions between activities.

#### Building
- [x] App builds from a clean repository checkout with no additional configuration.

- [x] App builds and deploys using the installRelease Gradle task.

- [x] App is equipped with a signing configuration, and the keystore and passwords are included in the repository. Keystore is referred to by a relative path.

- [x] All app dependencies are managed by Gradle.

#### Data Persistence
- [x] App stores data locally either by implementing a ContentProvider OR using Firebase Realtime Database OR using Room. No third party frameworks nor Persistence Libraries may be used.

*Must implement at least one of the three:*
- [x] If it regularly pulls or sends data to/from a web service or API, app updates data in its cache at regular intervals using a SyncAdapter or JobDispatcher.
OR
- [x] If it needs to pull or send data to/from a web service or API only once, or on a per request basis (such as a search application), app uses an IntentService to do so.
OR
- [x] It it performs short duration, on-demand requests(such as search), app uses an AsyncTask.

- [x] If Content provider is used, the app uses a Loader to move its data to its views.

- [x] If Room is used then LiveData and ViewModel are used when required and no unnecessary calls to the database are made.
