#!/bin/bash

echo "=========================================="
echo "Simple Firebase App - Setup Script"
echo "=========================================="
echo ""

# Check if google-services.json exists
if [ ! -f "app/google-services.json" ]; then
    echo "⚠️  WARNING: google-services.json not found!"
    echo "Please download it from Firebase Console and place it in app/google-services.json"
    echo ""
fi

# Initialize git repository if not already initialized
if [ ! -d ".git" ]; then
    echo "📦 Initializing Git repository..."
    git init
    git add .
    git commit -m "Initial commit: Simple Firebase App"
    echo "✅ Git repository initialized"
else
    echo "✅ Git repository already exists"
fi

echo ""
echo "=========================================="
echo "Next Steps:"
echo "=========================================="
echo "1. Add google-services.json from Firebase Console"
echo "2. Create a GitHub repository"
echo "3. Push to GitHub: git remote add origin <your-repo-url> && git push -u origin main"
echo "4. Set up Codemagic CI/CD"
echo "5. Configure Codespaces for cloud development"
echo ""
echo "To build the app locally:"
echo "  ./gradlew assembleDebug"
echo ""
echo "=========================================="
