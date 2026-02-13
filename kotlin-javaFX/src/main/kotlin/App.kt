package {{ group }}

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Stage
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import org.controlsfx.control.Notifications
import kotlin.math.abs
import kotlin.math.hypot

/**
 * JavaFX Application for {{ app_name | default(project_name) }}
 *
 * This starter application demonstrates:
 * - Menu bar with standard File/Edit/Help menus
 * - Toolbar with icon buttons
 * - Interactive buttons and labels
 * - Canvas with click detection (circles, lines, text)
 * - Event logging
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
    private lateinit var eventLog: TextArea
    private var clickCount = 0

    override fun start(stage: Stage) {
        stage.title = "{{ app_name | default(project_name) }}"
        stage.scene = Scene(createMainLayout(stage), 1000.0, 700.0)
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
                            setOnAction { 
                                logEvent("Menu: New")
                                showNotification(stage, "New", "Create new file") 
                            }
                        },
                        MenuItem("Open...").apply {
                            accelerator = javafx.scene.input.KeyCombination.keyCombination("Ctrl+O")
                            setOnAction { 
                                logEvent("Menu: Open")
                                showNotification(stage, "Open", "Open file dialog") 
                            }
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
                            setOnAction { 
                                logEvent("Menu: Cut")
                                showNotification(stage, "Cut", "Cut to clipboard") 
                            }
                        },
                        MenuItem("Copy").apply {
                            accelerator = javafx.scene.input.KeyCombination.keyCombination("Ctrl+C")
                            setOnAction { 
                                logEvent("Menu: Copy")
                                showNotification(stage, "Copy", "Copy to clipboard") 
                            }
                        },
                        MenuItem("Paste").apply {
                            accelerator = javafx.scene.input.KeyCombination.keyCombination("Ctrl+V")
                            setOnAction { 
                                logEvent("Menu: Paste")
                                showNotification(stage, "Paste", "Paste from clipboard") 
                            }
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
                                    headerText = "{{ app_name | default(project_name) }}"
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
                    setOnAction { 
                        logEvent("Toolbar: New")
                        showNotification(stage, "New", "Create new file") 
                    }
                },
                Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.FOLDER_OPEN)
                    tooltip = Tooltip("Open")
                    setOnAction { 
                        logEvent("Toolbar: Open")
                        showNotification(stage, "Open", "Open file") 
                    }
                },
                Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.SAVE)
                    tooltip = Tooltip("Save")
                    setOnAction { 
                        logEvent("Toolbar: Save")
                        showNotification(stage, "Save", "Save file") 
                    }
                },
                Separator(),
                Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.CUT)
                    tooltip = Tooltip("Cut")
                    setOnAction { 
                        logEvent("Toolbar: Cut")
                        showNotification(stage, "Cut", "Cut selection") 
                    }
                },
                Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.COPY)
                    tooltip = Tooltip("Copy")
                    setOnAction { 
                        logEvent("Toolbar: Copy")
                        showNotification(stage, "Copy", "Copy selection") 
                    }
                },
                Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.PASTE)
                    tooltip = Tooltip("Paste")
                    setOnAction { 
                        logEvent("Toolbar: Paste")
                        showNotification(stage, "Paste", "Paste clipboard") 
                    }
                }
            )
        }
    }

    // ==============================================
    // CONTENT AREA - Main application content
    // DEMO: Remove this and add your own UI
    // ==============================================
    private fun createContent(stage: Stage): HBox {
        return HBox(15.0).apply {
            padding = Insets(15.0)

            children.addAll(
                createLeftPanel(stage),
                createCanvasPanel(stage),
                createEventLogPanel()
            )
        }
    }

    private fun createLeftPanel(stage: Stage): VBox {
        return VBox(15.0).apply {
            padding = Insets(10.0)
            prefWidth = 250.0
            style = "-fx-background-color: white; -fx-border-color: #d0d0d0; -fx-border-radius: 5; -fx-background-radius: 5;"

            // Title
            children.add(Label("{{ app_name | default(project_name) }}").apply {
                style = "-fx-font-size: 20px; -fx-font-weight: bold;"
            })

            children.add(Label("JavaFX Application Starter").apply {
                style = "-fx-font-size: 12px; -fx-text-fill: gray;"
            })

            children.add(Separator())

            // Demo: Interactive button
            children.add(Label("DEMO: Click Counter").apply {
                style = "-fx-font-size: 14px; -fx-font-weight: bold;"
            })

            children.add(Button("Click Me!").apply {
                graphic = FontIcon(FontAwesomeSolid.MOUSE_POINTER)
                style = "-fx-font-size: 13px; -fx-padding: 8 16;"
                maxWidth = Double.MAX_VALUE

                setOnAction {
                    clickCount++
                    logEvent("Button clicked ($clickCount)")
                    updateStatus("Button clicked $clickCount time(s)")
                    showNotification(stage, "Click!", "Button was clicked ($clickCount)")
                }

                setOnMouseEntered {
                    style = "-fx-font-size: 13px; -fx-padding: 8 16; -fx-background-color: #3498db; -fx-text-fill: white;"
                    updateStatus("Hover: Click Me button")
                }

                setOnMouseExited {
                    style = "-fx-font-size: 13px; -fx-padding: 8 16;"
                    updateStatus("Ready")
                }
            })

            children.add(Separator())

            // Demo: Interactive labels
            children.add(Label("DEMO: Clickable Labels").apply {
                style = "-fx-font-size: 14px; -fx-font-weight: bold;"
            })

            children.add(FlowPane(8.0, 8.0).apply {
                children.addAll(
                    createClickableLabel("Red", "#e74c3c", stage),
                    createClickableLabel("Green", "#2ecc71", stage),
                    createClickableLabel("Blue", "#3498db", stage),
                    createClickableLabel("Yellow", "#f39c12", stage)
                )
            })

            children.add(Separator())

            // Instructions
            children.add(Label("To customize:").apply {
                style = "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: gray;"
            })

            val instructionLabels = listOf(
                Label("1. Remove demo content in createContent()"),
                Label("2. Keep menu bar, toolbar, and status bar structure"),
                Label("3. Build your own UI in the center area"),
                Label("4. Use FontAwesome icons via Ikonli"),
                Label("5. Use Notifications for toast messages")
            )
            instructionLabels.forEach { label ->
                label.style = "-fx-font-size: 10px; -fx-text-fill: gray;"
                label.isWrapText = true
                children.add(label)
            }
        }
    }

    private fun createCanvasPanel(stage: Stage): VBox {
        return VBox(10.0).apply {
            padding = Insets(10.0)
            style = "-fx-background-color: white; -fx-border-color: #d0d0d0; -fx-border-radius: 5; -fx-background-radius: 5;"

            children.add(Label("DEMO: Canvas Click Detection").apply {
                style = "-fx-font-size: 14px; -fx-font-weight: bold;"
            })

            children.add(Separator())

            val canvas = Canvas(400.0, 500.0)
            val g = canvas.graphicsContext2D

            // Background
            g.fill = Color.web("#f8f9fa")
            g.fillRect(0.0, 0.0, 400.0, 500.0)

            // Red circle - click on outline
            val redCx = 100.0
            val redCy = 100.0
            val redR = 50.0
            g.stroke = Color.RED
            g.lineWidth = 3.0
            g.strokeOval(redCx - redR, redCy - redR, 2 * redR, 2 * redR)
            g.fill = Color.BLACK
            g.fillText("Red Outline", redCx - 30, redCy - 65)

            // Blue circle - click on filled area
            val blueCx = 300.0
            val blueCy = 100.0
            val blueR = 50.0
            g.fill = Color.BLUE
            g.fillOval(blueCx - blueR, blueCy - blueR, 2 * blueR, 2 * blueR)
            g.fill = Color.BLACK
            g.fillText("Blue Filled", blueCx - 30, blueCy - 65)

            // Green diagonal line
            val lineX1 = 50.0
            val lineY1 = 200.0
            val lineX2 = 350.0
            val lineY2 = 300.0
            g.stroke = Color.GREEN
            g.lineWidth = 4.0
            g.strokeLine(lineX1, lineY1, lineX2, lineY2)
            g.fill = Color.BLACK
            g.fillText("Green Line", 180.0, 240.0)

            // Letter A
            val letterAX = 100.0
            val letterAY = 400.0
            g.fill = Color.BLACK
            g.font = Font.font("Arial", FontWeight.BOLD, 60.0)
            g.fillText("A", letterAX, letterAY)
            g.font = Font.font("Arial", 12.0)
            g.fillText("Click A", letterAX + 5, letterAY + 20)

            // Letter B
            val letterBX = 250.0
            val letterBY = 400.0
            g.fill = Color.BLACK
            g.font = Font.font("Arial", FontWeight.BOLD, 60.0)
            g.fillText("B", letterBX, letterBY)
            g.font = Font.font("Arial", 12.0)
            g.fillText("Click B", letterBX + 5, letterBY + 20)

            // Click handling
            canvas.setOnMouseClicked { e ->
                val clickMsg = StringBuilder()

                // Check red circle outline (distance from center approximately equals radius)
                val redDist = hypot(e.x - redCx, e.y - redCy)
                if (abs(redDist - redR) < 6.0) {
                    clickMsg.append("Red Circle Outline clicked")
                }
                // Check blue circle filled area
                else if (hypot(e.x - blueCx, e.y - blueCy) <= blueR) {
                    clickMsg.append("Blue Circle Area clicked")
                }
                // Check green line
                else if (isPointNearLine(e.x, e.y, lineX1, lineY1, lineX2, lineY2, 6.0)) {
                    clickMsg.append("Green Line clicked")
                }
                // Check letter A (approximate bounding box)
                else if (e.x >= letterAX && e.x <= letterAX + 40 && e.y >= letterAY - 50 && e.y <= letterAY) {
                    clickMsg.append("Letter A clicked")
                }
                // Check letter B (approximate bounding box)
                else if (e.x >= letterBX && e.x <= letterBX + 40 && e.y >= letterBY - 50 && e.y <= letterBY) {
                    clickMsg.append("Letter B clicked")
                }
                else {
                    clickMsg.append("Canvas background clicked")
                }

                clickMsg.append(" at (%.0f, %.0f)".format(e.x, e.y))
                logEvent(clickMsg.toString())
                updateStatus(clickMsg.toString())
                showNotification(stage, "Canvas Click", clickMsg.toString())
            }

            children.add(canvas)
        }
    }

    private fun createEventLogPanel(): VBox {
        return VBox(10.0).apply {
            padding = Insets(10.0)
            prefWidth = 250.0
            style = "-fx-background-color: white; -fx-border-color: #d0d0d0; -fx-border-radius: 5; -fx-background-radius: 5;"

            children.add(Label("Event Log").apply {
                style = "-fx-font-size: 14px; -fx-font-weight: bold;"
            })

            children.add(Separator())

            eventLog = TextArea().apply {
                isEditable = false
                isWrapText = true
                prefRowCount = 30
                style = "-fx-font-family: 'Courier New'; -fx-font-size: 10px;"
            }

            children.add(eventLog)

            children.add(Button("Clear Log").apply {
                graphic = FontIcon(FontAwesomeSolid.TRASH)
                maxWidth = Double.MAX_VALUE
                setOnAction {
                    eventLog.clear()
                    updateStatus("Event log cleared")
                }
            })
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
            style = "-fx-font-size: 12px; -fx-padding: 8 16; -fx-background-color: $color; " +
                    "-fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;"
            minWidth = 70.0
            alignment = Pos.CENTER

            setOnMouseClicked {
                logEvent("Label clicked: $text")
                showNotification(stage, "Clicked", "You clicked: $text")
                updateStatus("Clicked: $text")
            }

            setOnMouseEntered {
                style = "-fx-font-size: 12px; -fx-padding: 8 16; -fx-background-color: $color; " +
                        "-fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand; -fx-opacity: 0.8;"
                updateStatus("Hover: $text label")
            }

            setOnMouseExited {
                style = "-fx-font-size: 12px; -fx-padding: 8 16; -fx-background-color: $color; " +
                        "-fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;"
                updateStatus("Ready")
            }
        }
    }

    private fun updateStatus(message: String) {
        statusLabel.text = message
    }

    private fun logEvent(message: String) {
        val timestamp = java.time.LocalTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")
        )
        eventLog.appendText("[$timestamp] $message\n")
        eventLog.scrollTop = Double.MAX_VALUE
    }

    private fun showNotification(stage: Stage, title: String, message: String) {
        Notifications.create()
            .title(title)
            .text(message)
            .owner(stage)
            .showInformation()
    }

    private fun isPointNearLine(
        px: Double, py: Double,
        x1: Double, y1: Double,
        x2: Double, y2: Double,
        threshold: Double
    ): Boolean {
        val lineLength = hypot(x2 - x1, y2 - y1)
        val distance = abs((y2 - y1) * px - (x2 - x1) * py + x2 * y1 - y2 * x1) / lineLength
        
        // Check if point is within threshold distance to line
        if (distance > threshold) return false
        
        // Check if point is within line segment bounds
        val dotProduct = ((px - x1) * (x2 - x1) + (py - y1) * (y2 - y1)) / (lineLength * lineLength)
        return dotProduct in 0.0..1.0
    }
}
