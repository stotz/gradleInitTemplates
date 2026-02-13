package {{ group }}

import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage

/**
 * Main entry point for {{ app_name | default(project_name) }}
 *
 * Generated: {{ now().strftime('%Y-%m-%d %H:%M:%S') }}
 */
fun main(args: Array<String>) {
    // Check for command-line argument
    when {
        args.contains("--app") -> {
            println("Launching App (simple starter)...")
            Application.launch(App::class.java)
        }
        args.contains("--advanced") -> {
            println("Launching AdvancedDemo (feature showcase)...")
            Application.launch(AdvancedDemo::class.java)
        }
        else -> {
            // Interactive selection
            Application.launch(LauncherApp::class.java)
        }
    }
}

/**
 * Launcher application that lets user choose which demo to run
 */
class LauncherApp : Application() {
    override fun start(stage: Stage) {
        stage.title = "{{ app_name | default(project_name) }} - Select Demo"
        
        val content = VBox(20.0).apply {
            padding = Insets(40.0)
            alignment = Pos.CENTER
            
            children.addAll(
                Label("{{ app_name | default(project_name) }}").apply {
                    style = "-fx-font-size: 24px; -fx-font-weight: bold;"
                },
                
                Label("Select which application to launch:").apply {
                    style = "-fx-font-size: 14px; -fx-text-fill: gray;"
                },
                
                VBox(10.0).apply {
                    alignment = Pos.CENTER
                    
                    children.addAll(
                        Button("App - Simple Starter").apply {
                            style = "-fx-font-size: 14px; -fx-padding: 15 30;"
                            prefWidth = 300.0
                            
                            setOnAction {
                                stage.close()
                                launchApp()
                            }
                        },
                        
                        Label("Clean, professional starter with essential features").apply {
                            style = "-fx-font-size: 11px; -fx-text-fill: gray;"
                        }
                    )
                },
                
                VBox(10.0).apply {
                    alignment = Pos.CENTER
                    
                    children.addAll(
                        Button("AdvancedDemo - Feature Showcase").apply {
                            style = "-fx-font-size: 14px; -fx-padding: 15 30;"
                            prefWidth = 300.0
                            
                            setOnAction {
                                stage.close()
                                launchAdvancedDemo()
                            }
                        },
                        
                        Label("Comprehensive UI library demonstrations (5 tabs)").apply {
                            style = "-fx-font-size: 11px; -fx-text-fill: gray;"
                        }
                    )
                },
                
                Label("Tip: Use --app or --advanced flags to skip this screen").apply {
                    style = "-fx-font-size: 10px; -fx-text-fill: gray; -fx-font-style: italic;"
                    padding = Insets(20.0, 0.0, 0.0, 0.0)
                }
            )
        }
        
        stage.scene = Scene(content, 500.0, 400.0)
        stage.show()
    }
    
    private fun launchApp() {
        Platform.runLater {
            val newStage = Stage()
            App().start(newStage)
        }
    }
    
    private fun launchAdvancedDemo() {
        Platform.runLater {
            val newStage = Stage()
            AdvancedDemo().start(newStage)
        }
    }
}
