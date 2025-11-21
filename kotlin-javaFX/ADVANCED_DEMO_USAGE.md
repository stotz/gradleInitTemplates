# Using AdvancedDemo.kt

## Overview

The kotlin-javaFX template includes TWO demo applications:

1. **App.kt** - Simple, clean starter application
2. **AdvancedDemo.kt** - Comprehensive UI library showcase

## Interactive Selection (Default)

By default, when you run the application, you get a **selection screen**:

```
┌─────────────────────────────────────┐
│     {{ app_name }}                  │
│                                     │
│  Select which application to launch:│
│                                     │
│  ┌───────────────────────────────┐ │
│  │   App - Simple Starter        │ │
│  └───────────────────────────────┘ │
│  Clean, professional starter        │
│                                     │
│  ┌───────────────────────────────┐ │
│  │   AdvancedDemo - Features     │ │
│  └───────────────────────────────┘ │
│  Comprehensive UI demonstrations    │
│                                     │
│  Tip: Use --app or --advanced      │
└─────────────────────────────────────┘
```

Just run:
```bash
./gradlew run
```

Then click the button for the demo you want!

## Command-Line Selection

Skip the selection screen with command-line flags:

### Launch App (Simple Starter)
```bash
./gradlew run --args="--app"
```

### Launch AdvancedDemo (Feature Showcase)
```bash
./gradlew run --args="--advanced"
```

## How It Works

The `Main.kt` file includes a `LauncherApp` that shows the selection screen:

```kotlin
fun main(args: Array<String>) {
    when {
        args.contains("--app") -> Application.launch(App::class.java)
        args.contains("--advanced") -> Application.launch(AdvancedDemo::class.java)
        else -> Application.launch(LauncherApp::class.java)  // Interactive selection
    }
}
```

## What's the Difference?

### App.kt (Default)
- ✅ Clean, professional starter
- ✅ Essential features only
- ✅ Easy to customize
- ✅ Perfect starting point
- ✅ Single-window layout

**Features:**
- Menu bar (File, Edit, Help)
- Toolbar with icons
- Click counter button
- Clickable colored labels
- Canvas with geometric shapes
- Event log with timestamps
- Status bar

### AdvancedDemo.kt (Optional)
- ✅ Comprehensive showcase
- ✅ 5 tabs with different features
- ✅ All UI libraries demonstrated
- ✅ Learning resource
- ✅ Copy/paste examples

**Features (5 Tabs):**

#### Tab 1: FormsFX
- Multi-section forms
- Various field types (String, Integer, Boolean, Dropdown, Multiline)
- Required field validation
- Submit & Reset buttons

#### Tab 2: ValidatorFX
- Real-time validation
- Email regex validation
- Password strength validation
- Password matching
- Age range validation
- URL validation with warnings
- PopOver error display

#### Tab 3: ControlsFX
- Rating control (5-star)
- ToggleSwitch with status
- CustomTextField with search icon & clear button
- 4 notification types (Info, Warning, Error, Success)

#### Tab 4: Canvas
- Multiple shapes (Circle, Rectangle, Triangle, Line, Arc)
- Click detection for each shape
- Event logging
- Background grid

#### Tab 5: Data Display
- TableView with person data
- TreeView with hierarchical structure
- Sample data

## When to Use Which?

### Use App.kt (Default) when:
- ✅ Starting a new project
- ✅ Want simple, clean foundation
- ✅ Building a specific application
- ✅ Need quick customization
- ✅ Don't need all features

### Use AdvancedDemo.kt when:
- ✅ Learning JavaFX and UI libraries
- ✅ Need examples for specific features
- ✅ Exploring what's possible
- ✅ Want to copy/paste patterns
- ✅ Showcasing capabilities

## Combining Both

You can also keep both and switch between them:

### Option 1: Command-line argument

```kotlin
class Main {
    fun run(args: Array<String>) {
        val appClass = if (args.contains("--advanced")) {
            AdvancedDemo::class.java
        } else {
            App::class.java
        }
        javafx.application.Application.launch(appClass)
    }
}

fun main(args: Array<String>) {
    Main().run(args)
}
```

Run with:
```bash
./gradlew run --args="--advanced"
```

### Option 2: Menu item

Add to App.kt menu:
```kotlin
MenuItem("Show Advanced Demo").apply {
    setOnAction {
        // Launch AdvancedDemo in new window
        val newStage = Stage()
        AdvancedDemo().start(newStage)
    }
}
```

## Customization

### From App.kt
1. Replace `createContent()` method
2. Keep menu, toolbar, status bar structure
3. Build your UI in the center area

### From AdvancedDemo.kt
1. Copy specific tab content
2. Paste into your App.kt
3. Adapt to your needs

## Examples

### Example 1: Add FormsFX to App.kt

Copy from AdvancedDemo.kt Tab 1 → Paste into App.kt center area

### Example 2: Add Canvas to App.kt

Copy from AdvancedDemo.kt Tab 4 → Paste into App.kt panel

### Example 3: Add Validation to App.kt

Copy ValidatorFX setup from Tab 2 → Apply to your form fields

## File Locations

```
src/main/kotlin/
├── Main.kt              (Entry point - choose which app to launch)
├── App.kt               (Default: Simple starter)
└── AdvancedDemo.kt      (Optional: Feature showcase)
```

## Dependencies

Both applications use the same dependencies from `build.gradle.kts`:
- JavaFX 25
- Ikonli 12.4.0
- ControlsFX 11.2.2
- FormsFX 11.6.0
- ValidatorFX 0.6.3

No changes needed to switch between them.

## Summary

| Aspect | App.kt | AdvancedDemo.kt |
|--------|--------|-----------------|
| Default | ✅ Yes | ❌ No |
| Complexity | Simple | Complex |
| Purpose | Starting point | Learning resource |
| Customization | Easy | Reference only |
| Features | Essential | Comprehensive |
| Layout | Single window | 5 tabs |
| Best for | Production | Education |

**Recommendation:** 
- Start with **App.kt** for your project
- Use **AdvancedDemo.kt** as reference when you need specific features
- Don't feel obligated to use AdvancedDemo - it's purely optional!
