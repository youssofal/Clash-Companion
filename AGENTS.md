# Clash Companion — Agent Instructions

## What This Project Is
Android overlay app for voice-controlled Clash Royale gameplay. Solo 48h build for Supercell AI Hackathon.
Full plan: `docs/clash-companion-final-plan-v2.md`

## Project Structure
```
app/src/main/
├── java/com/yoyostudios/clashcompanion/
│   ├── ClashCompanionApp.kt              # Application class + Compose theme
│   ├── MainActivity.kt                    # Compose setup UI, permissions, deck loading
│   ├── accessibility/
│   │   └── ClashCompanionAccessibilityService.kt  # Tap injection (safeTap)
│   ├── capture/
│   │   └── ScreenCaptureService.kt        # MediaProjection frames
│   ├── speech/
│   │   └── SpeechService.kt              # sherpa-onnx STT pipeline
│   ├── detection/
│   │   ├── HandDetector.kt               # pHash card-in-hand
│   │   └── ArenaDetector.kt              # Roboflow API polling
│   ├── command/
│   │   ├── CommandRouter.kt              # Five-tier routing
│   │   ├── CommandParser.kt              # Fuzzy match + aliases
│   │   └── CommandQueue.kt              # Queue + Conditional buffers
│   ├── strategy/
│   │   ├── OpusCoach.kt                  # Pre-match deck analysis (Claude Opus)
│   │   └── GeminiPlayer.kt              # Real-time tactical decisions (Gemini 3 Flash)
│   ├── deck/
│   │   └── DeckManager.kt               # Share link parse + cr-api-data
│   ├── overlay/
│   │   └── OverlayManager.kt            # WindowManager + ComposeView overlay HUD
│   ├── api/
│   │   ├── AnthropicClient.kt           # Claude API wrapper (Opus)
│   │   ├── GeminiClient.kt             # Gemini API wrapper (Flash — strategy + vision)
│   │   └── RoboflowClient.kt           # Roboflow API wrapper
│   ├── ui/
│   │   └── theme/
│   │       ├── Theme.kt                  # ClashCompanionTheme (Material 3)
│   │       ├── Color.kt                  # Design system color palette
│   │       ├── Type.kt                   # Typography scale
│   │       └── Spacing.kt               # Spacing & dimension tokens
│   └── util/
│       ├── Coordinates.kt               # Hardcoded 1080x2340 positions
│       └── CardAliases.kt               # Alias dictionary
├── res/
│   ├── font/                             # Custom typefaces
│   ├── drawable/                          # Vector drawables, app icon layers
│   └── xml/
│       └── accessibility_service_config.xml
└── assets/
    ├── moonshine-base/                   # ONNX model files
    ├── silero-vad/                        # VAD model
    └── card-data.json                    # Bundled cr-api-data
```

## Build & Deploy
- Build: `./gradlew assembleDebug`
- Install: `adb install -r app/build/outputs/apk/debug/app-debug.apk`
- Logs: `adb logcat -s ClashCompanion`

## Key Patterns
- ALL taps go through safeTap() in AccessibilityService
- ALL coordinates are 1080x2340 hardcoded
- ALL HTTP calls use OkHttp with coroutines on Dispatchers.IO
- ALL JSON parsing uses Gson
- ALL UI is Jetpack Compose with Material 3 — no new XML layouts
- ALL visible elements must be polished, branded, and animated — design is a first-class deliverable

## Git Commit Standards

Judges review commit history. Every commit must be professional and tell a story.

### Format (Conventional Commits)
```
feat(M#): Short imperative description of milestone deliverable

- Bullet: specific component built and what it does
- Bullet: key technical decision or pattern used
- Bullet: each significant file/feature added
- ...list ALL significant additions

Tested: How verified (device, commands, results)
Device: Samsung Galaxy A35, Android 16, 1080x2340
```

### Rules
- **ONE milestone = ONE commit.** Never squash milestones.
- **Conventional type prefixes:** `feat`, `fix`, `refactor`, `docs`, `chore`
- **Imperative mood** in subject ("Add X" not "Added X")
- **Commit immediately** after milestone passes testing
- **Never commit** secrets, build artifacts, IDE files, or WIP code
