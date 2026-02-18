# Simple Firebase App

A basic Android app to test the development workflow with GitHub, Codemagic, Codespaces, and Firebase.

## Features

- Simple counter that increments when you tap a button
- Firebase Analytics integration to track user interactions
- Firebase Firestore integration to save counter values
- Clean Material Design UI

## Setup Instructions

### 1. Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or use an existing one
3. Add an Android app to your Firebase project:
   - Package name: `com.testapp.simplefirebaseapp`
   - Download the `google-services.json` file
   - Place it in `app/google-services.json`

4. Enable Firebase Analytics and Firestore in your Firebase Console

### 2. Local Development

```bash
# Clone the repository
git clone <your-repo-url>
cd SimpleFirebaseApp

# Build the project
./gradlew build

# Run on connected device or emulator
./gradlew installDebug
```

### 3. GitHub Setup

1. Create a new GitHub repository
2. Push this code to GitHub:

```bash
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```

### 4. Codespaces Setup

1. In your GitHub repository, click "Code" → "Codespaces" → "Create codespace on main"
2. Codespaces will automatically set up a development environment
3. Install Android SDK if needed in the codespace

### 5. Codemagic Setup

1. Sign in to [Codemagic](https://codemagic.io/)
2. Connect your GitHub repository
3. Create a `codemagic.yaml` configuration file (see below)
4. Configure build triggers and environment variables

#### Sample codemagic.yaml

```yaml
workflows:
  android-workflow:
    name: Android Workflow
    max_build_duration: 60
    environment:
      groups:
        - firebase
      vars:
        PACKAGE_NAME: "com.testapp.simplefirebaseapp"
    scripts:
      - name: Set up local.properties
        script: |
          echo "sdk.dir=$ANDROID_SDK_ROOT" > "$CM_BUILD_DIR/local.properties"
      - name: Build Android app
        script: |
          ./gradlew assembleDebug
    artifacts:
      - app/build/outputs/**/*.apk
    publishing:
      firebase:
        firebase_service_account: $FIREBASE_SERVICE_ACCOUNT
        android:
          app_id: $FIREBASE_APP_ID
```

### 6. Environment Variables

Set these in Codemagic:

- `FIREBASE_SERVICE_ACCOUNT`: Your Firebase service account JSON
- `FIREBASE_APP_ID`: Your Firebase Android app ID

## Project Structure

```
SimpleFirebaseApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/testapp/simplefirebaseapp/
│   │       │   └── MainActivity.java
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml
│   │       │   └── values/
│   │       │       ├── strings.xml
│   │       │       ├── colors.xml
│   │       │       └── themes.xml
│   │       └── AndroidManifest.xml
│   ├── build.gradle
│   └── google-services.json (you need to add this)
├── build.gradle
├── settings.gradle
└── README.md
```

## Testing the Workflow

1. **Local Development**: Build and run the app locally
2. **GitHub**: Push changes to trigger version control
3. **Codespaces**: Use cloud-based development environment
4. **Codemagic**: Automated builds on push
5. **Firebase**: View analytics and Firestore data in Firebase Console

## Next Steps

- Add more Firebase features (Authentication, Cloud Functions, etc.)
- Set up automated testing
- Configure release builds with signing
- Add deployment to Google Play Store
