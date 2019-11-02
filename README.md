# Learnification

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
