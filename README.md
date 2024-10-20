# Learnification

## How to build this on your machine

1. Download the latest Android Studio. The defaults seem to work fine for me.
2. Open Android Studio and set the Gradle JDK to version 11.
3. (Re-)run the IDE sync and confirm that it all works okay.
4. Browse the code, have a go at running the app in an emulator (or even on a real device) etc.

## Tests

I've only ever run the instrumentation and UI tests on a real device, so I'm not sure if they work on an emulator. 

## How to create a new release

1. Update the `versionName` and `versionCode` in `app/build.gradle`
2. In Android Studio, go to Build->Generate Signed Bundle/APK. Generate a bundle, and as the
keystore, use `release-keystore.jks`. This should generate a file `app/release/app-release.aab`.
3. Go to the play console, and go to release management. Go to the "Learnification" app.
4. Under "Release management" go to "App releases". Under the "Production track" go to "manage".
Press "Create release".
5. Upload your app bundle, the `.aab` file, as the bundle. Enter the release notes. Enter the
release name, which should be the same as the versionCode you updated earlier. Save, review and
roll-out the release.
