# Deployment Guide

This guide walks you through testing the complete development workflow with all platforms.

## Part 1: Firebase Setup

### Step 1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Add project" or select existing project
3. Enter project name (e.g., "simple-firebase-app-test")
4. Enable Google Analytics (optional)
5. Click "Create project"

### Step 2: Add Android App

1. In Firebase Console, click "Add app" → Android icon
2. Enter package name: `com.testapp.simplefirebaseapp`
3. App nickname: "Simple Firebase App"
4. Download `google-services.json`
5. Place file in `app/google-services.json`

### Step 3: Enable Firebase Services

**Firestore Database:**
1. Go to "Firestore Database" in left menu
2. Click "Create database"
3. Choose "Start in test mode" (for testing)
4. Select location
5. Click "Enable"

**Analytics:**
- Should be enabled by default
- No additional setup needed

## Part 2: GitHub Setup

### Step 1: Create GitHub Repository

1. Go to [GitHub](https://github.com)
2. Click "New repository"
3. Name: `simple-firebase-app`
4. Choose public or private
5. Do NOT initialize with README (we have one)
6. Click "Create repository"

### Step 2: Push Code to GitHub

```bash
cd SimpleFirebaseApp
git init
git add .
git commit -m "Initial commit: Simple Firebase App"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/simple-firebase-app.git
git push -u origin main
```

## Part 3: Codespaces Setup

### Step 1: Create Codespace

1. Go to your GitHub repository
2. Click "Code" → "Codespaces" tab
3. Click "Create codespace on main"
4. Wait for environment to build (5-10 minutes first time)

### Step 2: Test in Codespace

Once your Codespace is ready:

```bash
# The post-create script should run automatically
# If not, run it manually:
bash .devcontainer/post-create.sh

# Build the project
./gradlew assembleDebug

# Check build output
ls -la app/build/outputs/apk/debug/
```

## Part 4: Codemagic Setup

### Step 1: Sign Up / Sign In

1. Go to [Codemagic](https://codemagic.io/)
2. Sign in with GitHub account
3. Authorize Codemagic to access your repositories

### Step 2: Add Application

1. Click "Add application"
2. Select your GitHub repository
3. Choose "Android" as project type
4. Click "Finish"

### Step 3: Configure Environment Variables

1. Go to "Team settings" → "Environment variables"
2. Add variable groups:

**Firebase Credentials Group:**
```
Group name: firebase_credentials
Variables:
- FIREBASE_SERVICE_ACCOUNT: <your-firebase-service-account-json>
- FIREBASE_APP_ID: <your-firebase-android-app-id>
```

**Keystore (for signed builds):**
```
Group name: keystore_reference
Variables:
- CM_KEYSTORE: <upload your keystore file>
- CM_KEYSTORE_PASSWORD: <your keystore password>
- CM_KEY_ALIAS: <your key alias>
- CM_KEY_PASSWORD: <your key password>
```

### Step 4: Configure Workflow

The `codemagic.yaml` file is already in your repository. Codemagic will use it automatically.

To trigger a build:
1. Make a change to your code
2. Push to GitHub
3. Codemagic will automatically start building

Or manually:
1. Go to Codemagic dashboard
2. Select your app
3. Click "Start new build"

## Part 5: Testing the Complete Workflow

### Workflow Test Checklist

- [ ] **Local Development**
  - Clone repository locally
  - Add `google-services.json`
  - Build with `./gradlew assembleDebug`
  - Verify APK created

- [ ] **GitHub Integration**
  - Make a code change
  - Commit and push to GitHub
  - Verify code appears in repository

- [ ] **Codespaces Development**
  - Create Codespace
  - Verify Android SDK installed
  - Build project in Codespace
  - Verify successful build

- [ ] **Codemagic CI/CD**
  - Push code to trigger build
  - Monitor build in Codemagic dashboard
  - Verify build artifacts (APK/AAB)
  - Check build logs for errors

- [ ] **Firebase Integration**
  - Install APK on device/emulator
  - Tap "Increment Counter" button
  - Tap "Save to Firestore" button
  - Check Firebase Console:
    - Analytics: Events logged
    - Firestore: Counter data saved

## Part 6: Common Issues & Solutions

### Issue: Build fails with "google-services.json not found"
**Solution:** Ensure `google-services.json` is in `app/` directory and not in `.gitignore`

### Issue: Codemagic build fails
**Solution:** 
- Check environment variables are set correctly
- Verify `codemagic.yaml` syntax
- Check build logs for specific errors

### Issue: Firebase not working in app
**Solution:**
- Verify package name matches in `google-services.json`
- Check Firebase services are enabled in console
- Verify internet permission in AndroidManifest.xml

### Issue: Codespace build fails
**Solution:**
- Run post-create script manually
- Check Java version: `java -version`
- Verify Android SDK path: `echo $ANDROID_HOME`

## Part 7: Next Steps

After successful testing:

1. **Add automated tests**
   - Unit tests with JUnit
   - Integration tests
   - UI tests with Espresso

2. **Configure release builds**
   - Generate signing keystore
   - Configure ProGuard/R8
   - Set up version management

3. **Deploy to Firebase App Distribution**
   - Configure in `codemagic.yaml`
   - Add tester groups
   - Automate distribution

4. **Deploy to Google Play**
   - Create Google Play Console account
   - Generate service account credentials
   - Configure Play Store deployment in Codemagic

## Resources

- [Firebase Documentation](https://firebase.google.com/docs)
- [Codemagic Documentation](https://docs.codemagic.io/)
- [GitHub Codespaces Documentation](https://docs.github.com/en/codespaces)
- [Android Developers Guide](https://developer.android.com/)
