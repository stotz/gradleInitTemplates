package {{ group }}

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.stage.Stage
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import org.controlsfx.control.Notifications

/**
 * JavaFX Application for {{ app_name }}
 *
 * This starter application demonstrates:
 * - Menu bar with standard File/Edit/Help menus
 * - Toolbar with icon buttons
 * - Interactive buttons and labels
 * - Event handling (click, hover)
 * - Status bar
 * - Toast notifications (ControlsFX)
 *
 * To customize:
 * 1. Remove demo content in createContent()
 * 2. Keep the BorderPane layout structure
 * 3. Build your own UI in the center area
 */
class App : Application() {

    private lateinit var statusLabel: Label
    private var clickCount = 0

    override fun start(stage: Stage) {
        stage.title = "{{ app_name }}"
        stage.scene = Scene(createMainLayout(stage), 800.0, 600.0)
        stage.show()
    }

    private fun createMainLayout(stage: Stage): BorderPane {
        return BorderPane().apply {
            top = VBox().apply {
                children.addAll(
                    createMenuBar(stage),
                    createToolbar(stage)
                )
            }
            center = createContent(stage)
            bottom = createStatusBar()
        }
    }

    // ==============================================
    // MENU BAR - Standard application menus
    // ==============================================
    private fun createMenuBar(stage: Stage): MenuBar {
        return MenuBar().apply {
            menus.addAll(
                // File menu
                Menu("File").apply {
                    items.addAll(
                        MenuItem("New").apply {
                            accelerator = javafx.scene.input.KeyCombination.keyCombination("Ctrl+N")
                            setOnAction { showNotification(stage, "New", "Create new file") }
                        },
                        MenuItem("Open...").apply {
                            accelerator = javafx.scene.input.KeyCombination.keyCombination("Ctrl+O")
                            setOnAction { showNotification(stage, "Open", "Open file dialog") }
                        },
                        SeparatorMenuItem(),
                        MenuItem("Exit").apply {
                            setOnAction { stage.close() }
                        }
                    )
                },
                // Edit menu
                Menu("Edit").apply {
                    items.addAll(
                        MenuItem("Cut").apply {
                            accelerator = javafx.scene.input.KeyCombination.keyCombination("Ctrl+X")
                            setOnAction { showNotification(stage, "Cut", "Cut to clipboard") }
                        },
                        MenuItem("Copy").apply {
                            accelerator = javafx.scene.input.KeyCombination.keyCombination("Ctrl+C")
                            setOnAction { showNotification(stage, "Copy", "Copy to clipboard") }
                        },
                        MenuItem("Paste").apply {
                            accelerator = javafx.scene.input.KeyCombination.keyCombination("Ctrl+V")
                            setOnAction { showNotification(stage, "Paste", "Paste from clipboard") }
                        }
                    )
                },
                // Help menu
                Menu("Help").apply {
                    items.addAll(
                        MenuItem("About").apply {
                            setOnAction {
                                Alert(Alert.AlertType.INFORMATION).apply {
                                    title = "About"
                                    headerText = "{{ app_name }}"
                                    contentText = "Version {{ version }}\nBuilt with Kotlin {{ kotlin_version }}\n\n{{ vendor }}"
                                }.showAndWait()
                            }
                        }
                    )
                }
            )
        }
    }

    // ==============================================
    // TOOLBAR - Icon buttons for common actions
    // ==============================================
    private fun createToolbar(stage: Stage): ToolBar {
        return ToolBar().apply {
            items.addAll(
                Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.FILE)
                    tooltip = Tooltip("New")
                    setOnAction { showNotification(stage, "New", "Create new file") }
                },
                Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.FOLDER_OPEN)
                    tooltip = Tooltip("Open")
                    setOnAction { showNotification(stage, "Open", "Open file") }
                },
                Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.SAVE)
                    tooltip = Tooltip("Save")
                    setOnAction { showNotification(stage, "Save", "Save file") }
                },
                Separator(),
                Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.CUT)
                    tooltip = Tooltip("Cut")
                    setOnAction { showNotification(stage, "Cut", "Cut selection") }
                },
                Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.COPY)
                    tooltip = Tooltip("Copy")
                    setOnAction { showNotification(stage, "Copy", "Copy selection") }
                },
                Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.PASTE)
                    tooltip = Tooltip("Paste")
                    setOnAction { showNotification(stage, "Paste", "Paste clipboard") }
                }
            )
        }
    }

    // ==============================================
    // CONTENT AREA - Main application content
    // DEMO: Remove this and add your own UI
    // ==============================================
    private fun createContent(stage: Stage): VBox {
        return VBox(20.0).apply {
            padding = Insets(20.0)
            alignment = Pos.TOP_CENTER

            children.addAll(
                // Title
                Label("{{ app_name }}").apply {
                    style = "-fx-font-size: 24px; -fx-font-weight: bold;"
                },

                // Subtitle
                Label("JavaFX Application Starter").apply {
                    style = "-fx-font-size: 14px; -fx-text-fill: gray;"
                },

                Separator(),

                // Demo: Interactive button
                Label("DEMO: Click Counter").apply {
                    style = "-fx-font-size: 16px; -fx-font-weight: bold;"
                },

                Button("Click Me!").apply {
                    graphic = FontIcon(FontAwesomeSolid.MOUSE_POINTER)
                    style = "-fx-font-size: 14px; -fx-padding: 10 20;"

                    // Click event
                    setOnAction {
                        clickCount++
                        updateStatus("Button clicked $clickCount time(s)")
                        showNotification(stage, "Click!", "Button was clicked ($clickCount)")
                    }

                    // Hover events
                    setOnMouseEntered {
                        style = "-fx-font-size: 14px; -fx-padding: 10 20; -fx-background-color: #3498db; -fx-text-fill: white;"
                        updateStatus("Hover: Click Me button")
                    }

                    setOnMouseExited {
                        style = "-fx-font-size: 14px; -fx-padding: 10 20;"
                        updateStatus("Ready")
                    }
                },

                Separator(),

                // Demo: Interactive labels
                Label("DEMO: Clickable Labels").apply {
                    style = "-fx-font-size: 16px; -fx-font-weight: bold;"
                },

                HBox(10.0).apply {
                    alignment = Pos.CENTER

                    children.addAll(
                        createClickableLabel("Red", "#e74c3c", stage),
                        createClickableLabel("Green", "#2ecc71", stage),
                        createClickableLabel("Blue", "#3498db", stage),
                        createClickableLabel("Yellow", "#f39c12", stage)
                    )
                },

                Separator(),

                // Instructions
                Label("To customize this application:").apply {
                    style = "-fx-font-size: 12px; -fx-text-fill: gray;"
                },
                VBox(5.0).apply {
                    alignment = Pos.TOP_LEFT
                    padding = Insets(10.0, 0.0, 0.0, 40.0)
                    children.addAll(
                        Label("1. Remove demo content in createContent()"),
                        Label("2. Keep menu bar, toolbar, and status bar structure"),
                        Label("3. Build your own UI in the center area"),
                        Label("4. Use FontAwesome icons via Ikonli"),
                        Label("5. Use Notifications for toast messages")
                    ).forEach { it.style = "-fx-font-size: 11px; -fx-text-fill: gray;" }
                }
            )
        }
    }

    // ==============================================
    // STATUS BAR - Bottom status information
    // ==============================================
    private fun createStatusBar(): HBox {
        return HBox().apply {
            padding = Insets(5.0, 10.0, 5.0, 10.0)
            style = "-fx-background-color: #f0f0f0;"

            statusLabel = Label("Ready").apply {
                style = "-fx-font-size: 11px;"
            }

            children.add(statusLabel)
        }
    }

    // ==============================================
    // HELPER METHODS
    // ==============================================

    private fun createClickableLabel(text: String, color: String, stage: Stage): Label {
        return Label(text).apply {
            style = "-fx-font-size: 14px; -fx-padding: 10 20; -fx-background-color: $color; " +
                    "-fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;"

            setOnMouseClicked {
                showNotification(stage, "Clicked", "You clicked: $text")
                updateStatus("Clicked: $text")
            }

            setOnMouseEntered {
                style = "-fx-font-size: 14px; -fx-padding: 10 20; -fx-background-color: $color; " +
                        "-fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand; -fx-opacity: 0.8;"
                updateStatus("Hover: $text label")
            }

            setOnMouseExited {
                style = "-fx-font-size: 14px; -fx-padding: 10 20; -fx-background-color: $color; " +
                        "-fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;"
                updateStatus("Ready")
            }
        }
    }

    private fun updateStatus(message: String) {
        statusLabel.text = message
    }

    private fun showNotification(stage: Stage, title: String, message: String) {
        Notifications.create()
            .title(title)
            .text(message)
            .owner(stage)
            .showInformation()
    }
}
