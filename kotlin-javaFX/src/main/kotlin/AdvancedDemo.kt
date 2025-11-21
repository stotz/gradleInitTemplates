package {{ group }}

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import org.controlsfx.control.Notifications
import org.controlsfx.control.PopOver
import org.controlsfx.control.Rating
import org.controlsfx.control.textfield.CustomTextField
import org.controlsfx.control.ToggleSwitch
import com.dlsc.formsfx.model.structure.Field
import com.dlsc.formsfx.model.structure.Form
import com.dlsc.formsfx.model.structure.Section
import com.dlsc.formsfx.view.renderer.FormRenderer
import net.synedra.validatorfx.Validator
import kotlin.math.hypot

/**
 * Advanced JavaFX Demo for {{ app_name }}
 * 
 * Comprehensive demonstration of UI libraries:
 * - Ikonli: Icon fonts (FontAwesome 5)
 * - FormsFX: Declarative form creation with various field types
 * - ValidatorFX: Input validation with visual feedback
 * - ControlsFX: Enhanced controls (Notifications, PopOver, Rating, ToggleSwitch, CustomTextField)
 * - JavaFX Canvas: Vector graphics with hit-testing
 * - TableView: Data display
 * - TreeView: Hierarchical data
 * 
 * To use this demo instead of the simple starter:
 * In Main.kt, change: Application.launch(App::class.java)
 * To: Application.launch(AdvancedDemo::class.java)
 */
class AdvancedDemo : Application() {

    override fun start(stage: Stage) {
        stage.title = "{{ app_name }} - Advanced Demo"
        
        val tabPane = createTabPane(stage)
        stage.scene = Scene(tabPane, 1200.0, 750.0)
        stage.show()
    }

    private fun createTabPane(stage: Stage): TabPane {
        return TabPane().apply {
            tabs.addAll(
                createFormsTab(stage),
                createValidationTab(stage),
                createControlsFXTab(stage),
                createCanvasTab(stage),
                createDataTab(stage)
            )
        }
    }

    // ==============================================
    // TAB 1: FormsFX - Declarative Forms
    // ==============================================
    private fun createFormsTab(stage: Stage): Tab {
        return Tab("FormsFX").apply {
            isClosable = false

            val nameField = Field.ofStringType("")
                .label("Full Name")
                .placeholder("Enter your name")
                .required(true)

            val ageField = Field.ofIntegerType(25)
                .label("Age")
                .placeholder("18-100")
                .required(true)

            val emailField = Field.ofStringType("")
                .label("Email")
                .placeholder("user@example.com")
                .required(true)

            val countryField = Field.ofSingleSelectionType(
                listOf("Switzerland", "Germany", "Austria", "France", "Italy"),
                0
            )
                .label("Country")
                .required(true)

            val subscribeField = Field.ofBooleanType(false)
                .label("Subscribe to newsletter")

            val commentsField = Field.ofStringType("")
                .label("Comments")
                .placeholder("Your comments here...")
                .multiline(true)

            val form = Form.of(
                Section.of(nameField, ageField)
                    .title("Personal Information"),
                Section.of(emailField, countryField, subscribeField)
                    .title("Contact Details"),
                Section.of(commentsField)
                    .title("Additional Information")
            )

            val formRenderer = FormRenderer(form).apply {
                prefWidth = 500.0
            }

            val submitButton = Button("Submit Form", FontIcon(FontAwesomeSolid.CHECK)).apply {
                style = "-fx-font-size: 14px; -fx-padding: 10 20;"
                setOnAction {
                    if (form.isValid) {
                        showNotification(stage, "Success", "Form data:\n" +
                                "Name: ${nameField.value}\n" +
                                "Age: ${ageField.value}\n" +
                                "Email: ${emailField.value}\n" +
                                "Country: ${countryField.selection}\n" +
                                "Subscribe: ${subscribeField.value}")
                    } else {
                        showNotification(stage, "Error", "Please complete all required fields")
                    }
                }
            }

            val resetButton = Button("Reset", FontIcon(FontAwesomeSolid.UNDO)).apply {
                style = "-fx-font-size: 14px; -fx-padding: 10 20;"
                setOnAction { form.reset() }
            }

            content = VBox(15.0).apply {
                padding = Insets(20.0)
                children.addAll(
                    Label("FormsFX: Declarative Form Creation").apply {
                        style = "-fx-font-size: 18px; -fx-font-weight: bold;"
                    },
                    Label("Features: Required fields, validation, multi-line text, dropdowns, checkboxes").apply {
                        style = "-fx-font-size: 12px; -fx-text-fill: gray;"
                    },
                    Separator(),
                    formRenderer,
                    HBox(10.0, submitButton, resetButton).apply {
                        alignment = Pos.CENTER_LEFT
                    }
                )
            }
        }
    }

    // ==============================================
    // TAB 2: ValidatorFX - Input Validation
    // ==============================================
    private fun createValidationTab(stage: Stage): Tab {
        return Tab("ValidatorFX").apply {
            isClosable = false

            val emailInput = TextField().apply {
                promptText = "Enter email (required)"
                prefColumnCount = 30
            }

            val passwordInput = PasswordField().apply {
                promptText = "Password (min 8 chars, must contain number)"
                prefColumnCount = 30
            }

            val confirmPasswordInput = PasswordField().apply {
                promptText = "Confirm password"
                prefColumnCount = 30
            }

            val ageInput = TextField().apply {
                promptText = "Age (18-100)"
                prefColumnCount = 10
            }

            val urlInput = TextField().apply {
                promptText = "Website URL (optional)"
                prefColumnCount = 30
            }

            val validator = Validator()

            // Email validation
            validator.createCheck()
                .dependsOn("email", emailInput.textProperty())
                .withMethod { ctx ->
                    val email = ctx.get<String>("email")
                    if (email.isBlank()) {
                        ctx.error("Email is required")
                    } else if (!email.matches(Regex("""^[\w.+-]+@[\w.-]+\.[A-Za-z]{2,}$"""))) {
                        ctx.error("Invalid email format")
                    }
                }
                .decorates(emailInput)
                .immediate()

            // Password validation
            validator.createCheck()
                .dependsOn("password", passwordInput.textProperty())
                .withMethod { ctx ->
                    val password = ctx.get<String>("password")
                    when {
                        password.length < 8 -> ctx.error("Password must be at least 8 characters")
                        !password.any { it.isDigit() } -> ctx.error("Password must contain at least one number")
                    }
                }
                .decorates(passwordInput)
                .immediate()

            // Confirm password validation
            validator.createCheck()
                .dependsOn("password", passwordInput.textProperty())
                .dependsOn("confirmPassword", confirmPasswordInput.textProperty())
                .withMethod { ctx ->
                    val password = ctx.get<String>("password")
                    val confirm = ctx.get<String>("confirmPassword")
                    if (confirm.isNotEmpty() && password != confirm) {
                        ctx.error("Passwords do not match")
                    }
                }
                .decorates(confirmPasswordInput)
                .immediate()

            // Age validation
            validator.createCheck()
                .dependsOn("age", ageInput.textProperty())
                .withMethod { ctx ->
                    val ageText = ctx.get<String>("age")
                    if (ageText.isNotBlank()) {
                        val age = ageText.toIntOrNull()
                        when {
                            age == null -> ctx.error("Age must be a number")
                            age < 18 -> ctx.error("Must be at least 18 years old")
                            age > 100 -> ctx.error("Age must be less than 100")
                        }
                    }
                }
                .decorates(ageInput)
                .immediate()

            // URL validation (optional)
            validator.createCheck()
                .dependsOn("url", urlInput.textProperty())
                .withMethod { ctx ->
                    val url = ctx.get<String>("url")
                    if (url.isNotBlank() && !url.matches(Regex("""^https?://.*"""))) {
                        ctx.warn("URL should start with http:// or https://")
                    }
                }
                .decorates(urlInput)
                .immediate()

            val validateButton = Button("Validate All", FontIcon(FontAwesomeSolid.CHECK_CIRCLE)).apply {
                style = "-fx-font-size: 14px; -fx-padding: 10 20;"
            }

            val popOver = PopOver()

            validateButton.setOnAction {
                if (validator.containsErrors()) {
                    val errors = mutableListOf<String>()
                    if (emailInput.styleClass.contains("error")) errors.add("Email invalid")
                    if (passwordInput.styleClass.contains("error")) errors.add("Password invalid")
                    if (confirmPasswordInput.styleClass.contains("error")) errors.add("Passwords don't match")
                    if (ageInput.styleClass.contains("error")) errors.add("Age invalid")

                    popOver.contentNode = VBox(8.0).apply {
                        padding = Insets(15.0)
                        children.add(Label("Validation Errors:").apply {
                            style = "-fx-font-weight: bold; -fx-font-size: 13px;"
                        })
                        errors.forEach { error ->
                            children.add(Label("- $error").apply {
                                graphic = FontIcon(FontAwesomeSolid.TIMES_CIRCLE).apply {
                                    iconColor = Color.RED
                                }
                            })
                        }
                    }
                    popOver.show(validateButton)
                } else {
                    if (popOver.isShowing) popOver.hide()
                    showNotification(stage, "Success", "All validations passed!")
                }
            }

            content = VBox(15.0).apply {
                padding = Insets(20.0)
                children.addAll(
                    Label("ValidatorFX: Input Validation").apply {
                        style = "-fx-font-size: 18px; -fx-font-weight: bold;"
                    },
                    Label("Features: Real-time validation, visual feedback, error/warning messages, PopOver").apply {
                        style = "-fx-font-size: 12px; -fx-text-fill: gray;"
                    },
                    Separator(),
                    GridPane().apply {
                        hgap = 10.0
                        vgap = 10.0
                        padding = Insets(10.0)

                        add(Label("Email:"), 0, 0)
                        add(emailInput, 1, 0)

                        add(Label("Password:"), 0, 1)
                        add(passwordInput, 1, 1)

                        add(Label("Confirm:"), 0, 2)
                        add(confirmPasswordInput, 1, 2)

                        add(Label("Age:"), 0, 3)
                        add(ageInput, 1, 3)

                        add(Label("Website:"), 0, 4)
                        add(urlInput, 1, 4)
                    },
                    validateButton
                )
            }
        }
    }

    // ==============================================
    // TAB 3: ControlsFX - Enhanced Controls
    // ==============================================
    private fun createControlsFXTab(stage: Stage): Tab {
        return Tab("ControlsFX").apply {
            isClosable = false

            // Rating control
            val rating = Rating(5, 3).apply {
                isUpdateOnHover = true
            }
            val ratingLabel = Label("Rating: 3.0")
            rating.ratingProperty().addListener { _, _, newValue ->
                ratingLabel.text = "Rating: %.1f".format(newValue.toDouble())
            }

            // Toggle Switch
            val toggleSwitch = ToggleSwitch("Enable Feature").apply {
                isSelected = false
            }
            val toggleLabel = Label("Status: OFF")
            toggleSwitch.selectedProperty().addListener { _, _, newValue ->
                toggleLabel.text = "Status: ${if (newValue) "ON" else "OFF"}"
                toggleLabel.style = if (newValue) 
                    "-fx-text-fill: green; -fx-font-weight: bold;" 
                else 
                    "-fx-text-fill: red;"
            }

            // Custom TextField with clear button
            val searchField = CustomTextField().apply {
                val textField = this
                promptText = "Search..."
                left = FontIcon(FontAwesomeSolid.SEARCH)
                right = Button().apply {
                    graphic = FontIcon(FontAwesomeSolid.TIMES)
                    style = "-fx-background-color: transparent;"
                    setOnAction { textField.clear() }
                }
            }

            // Notifications with different types
            val infoButton = Button("Show Info", FontIcon(FontAwesomeSolid.INFO_CIRCLE)).apply {
                setOnAction {
                    Notifications.create()
                        .title("Information")
                        .text("This is an information notification")
                        .owner(stage)
                        .showInformation()
                }
            }

            val warningButton = Button("Show Warning", FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE)).apply {
                setOnAction {
                    Notifications.create()
                        .title("Warning")
                        .text("This is a warning notification")
                        .owner(stage)
                        .showWarning()
                }
            }

            val errorButton = Button("Show Error", FontIcon(FontAwesomeSolid.TIMES_CIRCLE)).apply {
                setOnAction {
                    Notifications.create()
                        .title("Error")
                        .text("This is an error notification")
                        .owner(stage)
                        .showError()
                }
            }

            val confirmButton = Button("Show Confirm", FontIcon(FontAwesomeSolid.CHECK_CIRCLE)).apply {
                setOnAction {
                    Notifications.create()
                        .title("Success")
                        .text("Operation completed successfully")
                        .owner(stage)
                        .showConfirm()
                }
            }

            content = VBox(20.0).apply {
                padding = Insets(20.0)
                children.addAll(
                    Label("ControlsFX: Enhanced Controls").apply {
                        style = "-fx-font-size: 18px; -fx-font-weight: bold;"
                    },
                    Label("Features: Rating, ToggleSwitch, CustomTextField, Various Notifications").apply {
                        style = "-fx-font-size: 12px; -fx-text-fill: gray;"
                    },
                    Separator(),
                    
                    VBox(10.0).apply {
                        children.addAll(
                            Label("Rating Control:").apply {
                                style = "-fx-font-weight: bold;"
                            },
                            HBox(10.0, rating, ratingLabel).apply {
                                alignment = Pos.CENTER_LEFT
                            }
                        )
                    },
                    
                    Separator(),
                    
                    VBox(10.0).apply {
                        children.addAll(
                            Label("Toggle Switch:").apply {
                                style = "-fx-font-weight: bold;"
                            },
                            HBox(10.0, toggleSwitch, toggleLabel).apply {
                                alignment = Pos.CENTER_LEFT
                            }
                        )
                    },
                    
                    Separator(),
                    
                    VBox(10.0).apply {
                        children.addAll(
                            Label("Custom TextField:").apply {
                                style = "-fx-font-weight: bold;"
                            },
                            searchField
                        )
                    },
                    
                    Separator(),
                    
                    VBox(10.0).apply {
                        children.addAll(
                            Label("Notifications:").apply {
                                style = "-fx-font-weight: bold;"
                            },
                            FlowPane(10.0, 10.0).apply {
                                children.addAll(infoButton, warningButton, errorButton, confirmButton)
                            }
                        )
                    }
                )
            }
        }
    }

    // ==============================================
    // TAB 4: Canvas - Vector Graphics
    // ==============================================
    private fun createCanvasTab(stage: Stage): Tab {
        return Tab("Canvas").apply {
            isClosable = false

            val canvas = Canvas(600.0, 500.0)
            val g = canvas.graphicsContext2D

            // Background
            g.fill = Color.web("#f8f9fa")
            g.fillRect(0.0, 0.0, 600.0, 500.0)

            // Draw various shapes
            val shapes = listOf(
                "Circle" to Pair(100.0, 100.0),
                "Rectangle" to Pair(250.0, 100.0),
                "Triangle" to Pair(450.0, 100.0),
                "Line" to Pair(100.0, 300.0),
                "Arc" to Pair(350.0, 300.0)
            )

            // Circle
            g.fill = Color.BLUE
            g.fillOval(70.0, 70.0, 60.0, 60.0)
            g.fill = Color.BLACK
            g.fillText("Circle", 75.0, 145.0)

            // Rectangle
            g.fill = Color.RED
            g.fillRect(220.0, 70.0, 80.0, 60.0)
            g.fill = Color.BLACK
            g.fillText("Rectangle", 235.0, 145.0)

            // Triangle
            g.fill = Color.GREEN
            g.fillPolygon(
                doubleArrayOf(450.0, 420.0, 480.0),
                doubleArrayOf(70.0, 130.0, 130.0),
                3
            )
            g.fill = Color.BLACK
            g.fillText("Triangle", 430.0, 145.0)

            // Line
            g.stroke = Color.PURPLE
            g.lineWidth = 4.0
            g.strokeLine(70.0, 280.0, 180.0, 280.0)
            g.fill = Color.BLACK
            g.fillText("Line", 95.0, 300.0)

            // Arc
            g.stroke = Color.ORANGE
            g.lineWidth = 3.0
            g.strokeArc(320.0, 250.0, 80.0, 80.0, 0.0, 180.0, javafx.scene.shape.ArcType.OPEN)
            g.fill = Color.BLACK
            g.fillText("Arc", 345.0, 345.0)

            val eventLog = TextArea().apply {
                isEditable = false
                prefHeight = 150.0
                promptText = "Click on shapes..."
            }

            canvas.setOnMouseClicked { e ->
                val msg = when {
                    hypot(e.x - 100.0, e.y - 100.0) <= 30.0 -> "Circle clicked"
                    e.x in 220.0..300.0 && e.y in 70.0..130.0 -> "Rectangle clicked"
                    isPointInTriangle(e.x, e.y, 450.0, 70.0, 420.0, 130.0, 480.0, 130.0) -> "Triangle clicked"
                    e.y in 276.0..284.0 && e.x in 70.0..180.0 -> "Line clicked"
                    hypot(e.x - 360.0, e.y - 290.0) in 37.0..43.0 && e.y < 290.0 -> "Arc clicked"
                    else -> "Background clicked at (%.0f, %.0f)".format(e.x, e.y)
                }
                eventLog.appendText("$msg\n")
                showNotification(stage, "Canvas", msg)
            }

            content = VBox(15.0).apply {
                padding = Insets(20.0)
                children.addAll(
                    Label("JavaFX Canvas: Vector Graphics").apply {
                        style = "-fx-font-size: 18px; -fx-font-weight: bold;"
                    },
                    Label("Features: Shapes, lines, arcs, click detection, event handling").apply {
                        style = "-fx-font-size: 12px; -fx-text-fill: gray;"
                    },
                    Separator(),
                    canvas,
                    Label("Event Log:").apply {
                        style = "-fx-font-weight: bold;"
                    },
                    eventLog
                )
            }
        }
    }

    // ==============================================
    // TAB 5: Data Display
    // ==============================================
    private fun createDataTab(stage: Stage): Tab {
        return Tab("Data Display").apply {
            isClosable = false

            // TableView
            data class Person(val name: String, val age: Int, val email: String)
            
            val tableView = TableView<Person>().apply {
                items.addAll(
                    Person("Alice Smith", 28, "alice@example.com"),
                    Person("Bob Johnson", 35, "bob@example.com"),
                    Person("Charlie Brown", 42, "charlie@example.com"),
                    Person("Diana Wilson", 31, "diana@example.com")
                )

                columns.add(TableColumn<Person, String>("Name").apply {
                    setCellValueFactory { javafx.beans.property.SimpleStringProperty(it.value.name) }
                    prefWidth = 150.0
                })
                columns.add(TableColumn<Person, Int>("Age").apply {
                    setCellValueFactory { javafx.beans.property.SimpleIntegerProperty(it.value.age).asObject() }
                    prefWidth = 80.0
                })
                columns.add(TableColumn<Person, String>("Email").apply {
                    setCellValueFactory { javafx.beans.property.SimpleStringProperty(it.value.email) }
                    prefWidth = 200.0
                })
            }

            // TreeView
            val rootItem = TreeItem("Root")
            val child1 = TreeItem("Child 1")
            child1.children.addAll(TreeItem("Grandchild 1.1"), TreeItem("Grandchild 1.2"))
            val child2 = TreeItem("Child 2")
            child2.children.addAll(TreeItem("Grandchild 2.1"), TreeItem("Grandchild 2.2"))
            rootItem.children.addAll(child1, child2)
            rootItem.isExpanded = true

            val treeView = TreeView(rootItem).apply {
                prefHeight = 200.0
            }

            content = VBox(15.0).apply {
                padding = Insets(20.0)
                children.addAll(
                    Label("JavaFX Data Display").apply {
                        style = "-fx-font-size: 18px; -fx-font-weight: bold;"
                    },
                    Label("Features: TableView with columns, TreeView with hierarchy").apply {
                        style = "-fx-font-size: 12px; -fx-text-fill: gray;"
                    },
                    Separator(),
                    Label("TableView:").apply {
                        style = "-fx-font-weight: bold;"
                    },
                    tableView,
                    Separator(),
                    Label("TreeView:").apply {
                        style = "-fx-font-weight: bold;"
                    },
                    treeView
                )
            }
        }
    }

    // ==============================================
    // HELPER METHODS
    // ==============================================

    private fun showNotification(stage: Stage, title: String, message: String) {
        Notifications.create()
            .title(title)
            .text(message)
            .owner(stage)
            .showInformation()
    }

    private fun isPointInTriangle(
        px: Double, py: Double,
        x1: Double, y1: Double,
        x2: Double, y2: Double,
        x3: Double, y3: Double
    ): Boolean {
        val d1 = sign(px, py, x1, y1, x2, y2)
        val d2 = sign(px, py, x2, y2, x3, y3)
        val d3 = sign(px, py, x3, y3, x1, y1)

        val hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0)
        val hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0)

        return !(hasNeg && hasPos)
    }

    private fun sign(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Double {
        return (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3)
    }
}
