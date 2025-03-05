package edu.ntnu.idi.bidata.view;

import edu.ntnu.idi.bidata.utils.ThemeManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class IssueBoardView extends BaseView {

  private VBox userSidebar;
  private ToggleButton householdTasksButton;
  private ToggleButton myTasksButton;
  private Button addTaskButton;
  private ToggleButton lightModeButton;
  private ToggleButton darkModeButton;
  private VBox issueBoardContainer;
  private VBox columnsContainer;
  private VBox todoColumn;
  private VBox doneColumn;

  @Override
  protected void createView() {
    userSidebar = createIssueboardSidebar();
    issueBoardContainer = createIssueBoardContainer();

    StackPane contentPane = new StackPane(issueBoardContainer);
    contentPane.setAlignment(Pos.CENTER);
    HBox.setHgrow(contentPane, Priority.ALWAYS);

    HBox mainLayout = new HBox(userSidebar, contentPane);
    mainLayout.setSpacing(0);
    mainLayout.setPadding(new Insets(0));
    mainLayout.setAlignment(Pos.TOP_LEFT);

    view = mainLayout; // Ensure this is set

    // Add the scene listener after view is initialized
    view.sceneProperty().addListener((obs, oldScene, newScene) -> {
      if (newScene != null) {
        newScene.widthProperty().addListener((obs2, oldWidth, newWidth) -> {
          if (newWidth.doubleValue() < 1100) { // Adjust the threshold as needed
            // Switch to vertical layout
            columnsContainer.getChildren().clear();
            columnsContainer.getChildren().addAll(todoColumn, doneColumn);
          } else {
            // Switch to horizontal layout
            columnsContainer.getChildren().clear();
            HBox horizontalLayout = new HBox(20, todoColumn, doneColumn);
            horizontalLayout.setAlignment(Pos.TOP_CENTER);
            columnsContainer.getChildren().add(horizontalLayout);
          }
        });
      }
    });
  }

  private VBox createIssueboardSidebar() {
    VBox sidebar = new VBox(20);
    sidebar.setPadding(new Insets(20));
    sidebar.setAlignment(Pos.TOP_LEFT);
    sidebar.setPrefWidth(200);
    sidebar.getStyleClass().add("sidebar");

    Label userLabel = new Label("Issueboard");
    userLabel.setFont(new Font("Arial", 16));
    userLabel.setStyle("-fx-font-weight: bold;");
    userLabel.getStyleClass().add("text");

    HBox userTitle = new HBox(5, userLabel);
    userTitle.setAlignment(Pos.CENTER_LEFT);

    ToggleGroup navToggleGroup = new ToggleGroup();

    householdTasksButton = new ToggleButton("Household");
    householdTasksButton.setToggleGroup(navToggleGroup);
    householdTasksButton.setSelected(true);
    householdTasksButton.setMaxWidth(Double.MAX_VALUE);
    householdTasksButton.getStyleClass().add("pill-toggle");

    myTasksButton = new ToggleButton("My Tasks");
    myTasksButton.setToggleGroup(navToggleGroup);
    myTasksButton.setMaxWidth(Double.MAX_VALUE);
    myTasksButton.getStyleClass().add("pill-toggle");

    // Add event listeners to the toggle buttons
    householdTasksButton.setOnAction(event -> showHouseholdTasks());
    myTasksButton.setOnAction(event -> showMyTasks());

    VBox navigation = new VBox(10, householdTasksButton, myTasksButton);
    HBox lineNav = new HBox(10, createLineGraphic(), navigation);
    lineNav.setAlignment(Pos.TOP_LEFT);

    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);

    ToggleGroup themeToggleGroup = new ToggleGroup();
    lightModeButton = new ToggleButton("    Light");
    lightModeButton.setToggleGroup(themeToggleGroup);
    lightModeButton.setMaxWidth(Double.MAX_VALUE);
    lightModeButton.getStyleClass().addAll("themeSwitcherButtons", "lightButton");

    darkModeButton = new ToggleButton("    Dark");
    darkModeButton.setToggleGroup(themeToggleGroup);
    darkModeButton.setMaxWidth(Double.MAX_VALUE);
    darkModeButton.getStyleClass().addAll("themeSwitcherButtons", "darkButton");

    HBox themeSwitcher = new HBox(10, lightModeButton, darkModeButton);
    themeSwitcher.setAlignment(Pos.CENTER);
    themeSwitcher.getStyleClass().add("themeSwitcher");

    sidebar.getChildren().addAll(userTitle, lineNav, spacer, themeSwitcher);

    setupThemeToggleBehavior();

    return sidebar;
  }

  private void setupThemeToggleBehavior() {
    if (ThemeManager.isDarkMode()) {
      darkModeButton.setSelected(true);
    } else {
      lightModeButton.setSelected(true);
    }
    ThemeManager.darkModeProperty().addListener((obs, oldVal, newVal) -> {
      darkModeButton.setSelected(newVal);
      lightModeButton.setSelected(!newVal);
    });
    lightModeButton.setOnAction(e -> {
      Scene scene = lightModeButton.getScene();
      if (scene != null) {
        ThemeManager.setLightTheme(scene);
      }
    });
    darkModeButton.setOnAction(e -> {
      Scene scene = darkModeButton.getScene();
      if (scene != null) {
        ThemeManager.setDarkTheme(scene);
      }
    });
  }

  /**
   * Creates the vertical + horizontal lines graphic.
   */
  private Pane createLineGraphic() {
    Pane linesPane = new Pane();
    linesPane.setPrefSize(16, 63);
    Line verticalLine = new Line(1, 0, 1, 58);
    verticalLine.getStyleClass().add("sidebarLines");
    verticalLine.setStrokeWidth(2);
    Line horizontal1 = new Line(2, 18, 16, 18);
    horizontal1.getStyleClass().add("sidebarLines");
    horizontal1.setStrokeWidth(2);
    Line horizontal2 = new Line(2, 58, 16, 58);
    horizontal2.getStyleClass().add("sidebarLines");
    horizontal2.setStrokeWidth(2);
    linesPane.getChildren().addAll(verticalLine, horizontal1, horizontal2);
    return linesPane;
  }

  /**
   * Creates the Issue Board container with kanban-style columns.
   */
  private VBox createIssueBoardContainer() {
    VBox container = new VBox(20);
    container.setAlignment(Pos.TOP_CENTER);
    container.setPadding(new Insets(20));
    container.getStyleClass().add("container");

    // Header with title, view toggles, and action buttons
    HBox header = createBoardHeader();

    // Main board content
    columnsContainer = createBoardColumns();
    ScrollPane scrollPane = new ScrollPane(columnsContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");
    scrollPane.setPadding(new Insets(0));
    VBox.setVgrow(scrollPane, Priority.ALWAYS);
    container.getChildren().addAll(header, scrollPane);
    return container;
  }

  /**
   * Creates the board header with view options and actions
   */
  private HBox createBoardHeader() {
    HBox header = new HBox(15);
    header.setAlignment(Pos.CENTER_RIGHT);
    header.setPadding(new Insets(0, 0, 10, 0));

    //Add new task button
    addTaskButton = new Button("Add new task");
    addTaskButton.setGraphic(createViewIcon("+"));
    addTaskButton.getStyleClass().add("pill-toggle");
    addTaskButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    header.getChildren().addAll(addTaskButton);
    return header;
  }



  /**
   * Helper method to create view option icons
   */
  private Label createViewIcon(String text) {
    Label icon = new Label(text);
    icon.setStyle("-fx-font-size: 12px; -fx-padding: 0 5px 0 0;");
    return icon;
  }


  /**
   * Creates the main board columns (To do, Done)
   */
  private VBox createBoardColumns() {
    columnsContainer = new VBox(20);
    columnsContainer.setAlignment(Pos.TOP_CENTER);
    columnsContainer.setPadding(new Insets(10, 0, 20, 0));

    todoColumn = createTaskColumn("To do (0)", true); // Start with 0 tasks
    todoColumn.getStyleClass().add("issueBoardColumContainer");
    doneColumn = createTaskColumn("Done (0)", false); // Start with 0 tasks
    doneColumn.getStyleClass().add("issueBoardColumContainer");

    // Initially add columns to the VBox
    columnsContainer.getChildren().addAll(todoColumn, doneColumn);
    return columnsContainer;
  }

  /**
   * Creates a task column with title and task cards.
   *
   * @param title       The title of the column.
   * @param isTodoColumn Whether this is the "To do" column.
   */
  private VBox createTaskColumn(String title, boolean isTodoColumn) {
    VBox column = new VBox(15);
    column.setAlignment(Pos.TOP_CENTER);
    column.setMinWidth(400);
    column.setMaxWidth(500);

    // Column header
    HBox columnHeader = new HBox(10);
    columnHeader.setAlignment(Pos.CENTER_LEFT);
    Label titleLabel = new Label(title);
    titleLabel.setStyle("-fx-font-weight: bold;");
    titleLabel.getStyleClass().add("text");
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    columnHeader.getChildren().addAll(titleLabel, spacer);

    // Task cards container
    VBox tasksContainer = new VBox(15);
    tasksContainer.setAlignment(Pos.TOP_CENTER);

    // Initially, show household tasks
    if (isTodoColumn) {
      tasksContainer.getChildren().addAll(
          createTaskCard("Wash the house", "Dribbble marketing", "24 Aug 2022", true, 7, true),
          createTaskCard("Clean behind the washer", "Pinterest promotion", "25 Aug 2022", false, 0, true),
          createTaskCard("Buy toilet paper", "Dropbox mobile app", "26 Aug 2022", true, 6, true),
          createTaskCard("Throw the trash", "Twitter marketing", "27 Aug 2022", false, 0, true)
      );
    } else {
      tasksContainer.getChildren().addAll(
          createTaskCard("Make dinner", "UI8 marketplace", "6 Jan 2022", false, 0, false),
          createTaskCard("Clean out common area", "Kickstarter campaign", "7 Jan 2022", false, 0, false),
          createTaskCard("Send Vipps request", "Twitter marketing", "8 Jan 2022", false, 0, false)
      );
    }
    ScrollPane scrollPane = new ScrollPane(tasksContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");
    scrollPane.setPadding(new Insets(0));
    VBox.setVgrow(scrollPane, Priority.ALWAYS);
    column.getChildren().addAll(columnHeader, scrollPane);

    // Update the column title with the initial count
    updateColumnTitle(column, tasksContainer.getChildren().size());
    return column;
  }

  /**
   * Shows tasks related to household chores.
   */
  private void showHouseholdTasks() {
    // Clear the current tasks in the columns
    VBox todoTasksContainer = (VBox) ((ScrollPane) todoColumn.getChildren().get(1)).getContent();
    VBox doneTasksContainer = (VBox) ((ScrollPane) doneColumn.getChildren().get(1)).getContent();
    todoTasksContainer.getChildren().clear();
    doneTasksContainer.getChildren().clear();

    // Add household tasks to the "To do" column
    todoTasksContainer.getChildren().addAll(
        createTaskCard("Wash the house", "Dribbble marketing", "24 Aug 2022", true, 7, true),
        createTaskCard("Clean behind the washer", "Pinterest promotion", "25 Aug 2022", false, 0, true),
        createTaskCard("Buy toilet paper", "Dropbox mobile app", "26 Aug 2022", true, 6, true),
        createTaskCard("Throw the trash", "Twitter marketing", "27 Aug 2022", false, 0, true)
    );

    // Add household tasks to the "Done" column
    doneTasksContainer.getChildren().addAll(
        createTaskCard("Make dinner", "UI8 marketplace", "6 Jan 2022", false, 0, false),
        createTaskCard("Clean out common area", "Kickstarter campaign", "7 Jan 2022", false, 0, false),
        createTaskCard("Send Vipps request", "Twitter marketing", "8 Jan 2022", false, 0, false)
    );

    // Update the column titles with the new counts
    updateColumnTitle(todoColumn, todoTasksContainer.getChildren().size());
    updateColumnTitle(doneColumn, doneTasksContainer.getChildren().size());
  }

  /**
   * Shows tasks related to personal tasks.
   */
  private void showMyTasks() {
    // Clear the current tasks in the columns
    VBox todoTasksContainer = (VBox) ((ScrollPane) todoColumn.getChildren().get(1)).getContent();
    VBox doneTasksContainer = (VBox) ((ScrollPane) doneColumn.getChildren().get(1)).getContent();
    todoTasksContainer.getChildren().clear();
    doneTasksContainer.getChildren().clear();

    // Add personal tasks to the "To do" column
    todoTasksContainer.getChildren().addAll(
        createTaskCard("Finish project report", "Work", "30 Aug 2022", true, 3, true),
        createTaskCard("Call plumber", "Home", "31 Aug 2022", false, 0, true)
    );

    // Add personal tasks to the "Done" column
    doneTasksContainer.getChildren().addAll(
        createTaskCard("Pay electricity bill", "Finance", "5 Jan 2022", false, 0, false),
        createTaskCard("Book flight tickets", "Travel", "10 Jan 2022", false, 0, false)
    );

    // Update the column titles with the new counts
    updateColumnTitle(todoColumn, todoTasksContainer.getChildren().size());
    updateColumnTitle(doneColumn, doneTasksContainer.getChildren().size());
  }

  /**
   * Creates a task card with title, description, and date.
   *
   * @param title         The title of the task.
   * @param description   The description of the task.
   * @param date          The date of the task.
   * @param hasComments   Whether the task has comments.
   * @param commentCount  The number of comments.
   * @param isTodoColumn  Whether the task belongs to the "To do" column.
   */
  private VBox createTaskCard(String title, String description, String date, boolean hasComments, int commentCount, boolean isTodoColumn) {
    VBox card = new VBox(8);
    card.setPadding(new Insets(15));
    card.getStyleClass().add("issue");
    //card.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: #E0E0E0; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 1);");
    HBox cardHeader = new HBox();
    cardHeader.setAlignment(Pos.CENTER_LEFT);
    Label titleLabel = new Label(title);
    titleLabel.setStyle("-fx-font-weight: bold;");
    titleLabel.getStyleClass().add("text");
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    Button menuButton = new Button("•••");
    menuButton.setStyle("-fx-background-color: transparent;");
    cardHeader.getChildren().addAll(titleLabel, spacer, menuButton);

    // Description
    Label descriptionLabel = new Label(description);
    descriptionLabel.setStyle("-fx-text-fill: #777777;");

    // Card footer with date and indicators
    HBox cardFooter = new HBox(10);
    cardFooter.setAlignment(Pos.CENTER_LEFT);
    cardFooter.setPadding(new Insets(10, 0, 0, 0));
    Label dateLabel = new Label(date);
    dateLabel.setStyle("-fx-background-color: #F5F5F5; -fx-padding: 5px 10px; -fx-background-radius: 5px; -fx-text-fill: #777777;");
    Region footerSpacer = new Region();
    HBox.setHgrow(footerSpacer, Priority.ALWAYS);
    HBox indicators = new HBox(10);
    if (hasComments) {
      Button commentsLabel = new Button("💬 " + commentCount);
      commentsLabel.getStyleClass().add("pill-toggle");
      indicators.getChildren().add(commentsLabel);
    }

    // Only add the checkmark button for tasks in the "To do" column
    if (isTodoColumn) {
      // Create a new checkmark button for this task card
      Button checkmarkLabel = new Button("✓");
      checkmarkLabel.getStyleClass().add("pill-toggle");
      checkmarkLabel.setStyle("-fx-text-fill: #4CAF50;");

      // Store the task card in the checkmark button's user data
      checkmarkLabel.setUserData(card);

      // Add an event listener to the checkmark button
      checkmarkLabel.setOnAction(event -> {
        System.out.println("Checkmark clicked for task: " + title);
        // Get the task card associated with the checkmark button
        VBox taskCard = (VBox) checkmarkLabel.getUserData();

        // Move the task card to the "Done" column
        moveTaskToDoneColumn(taskCard);
      });
      indicators.getChildren().add(checkmarkLabel);
    }
    cardFooter.getChildren().addAll(dateLabel, footerSpacer, indicators);
    card.getChildren().addAll(cardHeader, descriptionLabel, cardFooter);
    return card;
  }

  /**
   * Adds a new task to the "To do" column.
   *
   * @param title       The title of the task.
   * @param description The description of the task.
   * @param date        The date of the task.
   */
  public void addTaskToTodoColumn(String title, String description, String date) {
    VBox tasksContainer = (VBox) ((ScrollPane) todoColumn.getChildren().get(1)).getContent();
    tasksContainer.getChildren().add(createTaskCard(title, description, date, false, 0, true));

    // Update the column title with the new count
    updateColumnTitle(todoColumn, tasksContainer.getChildren().size());
  }
  /**
   * Updates the title of a task column with the given count.
   *
   * @param column The column to update.
   * @param count  The new count of tasks.
   */
  private void updateColumnTitle(VBox column, int count) {
    HBox columnHeader = (HBox) column.getChildren().get(0); // Get the header
    Label titleLabel = (Label) columnHeader.getChildren().get(0); // Get the title label
    String currentTitle = titleLabel.getText();
    String newTitle = currentTitle.replaceAll("\\(\\d+\\)", "(" + count + ")"); // Update the count
    titleLabel.setText(newTitle);
  }

  /**
   * Moves a task card from the "To do" column to the "Done" column.
   *
   * @param taskCard The task card to move.
   */
  public void moveTaskToDoneColumn(VBox taskCard) {
    System.out.println("Moving task to Done column");
    // Remove the task card from the "To do" column
    VBox todoTasksContainer = (VBox) ((ScrollPane) todoColumn.getChildren().get(1)).getContent();
    todoTasksContainer.getChildren().remove(taskCard);

    // Update the "To do" column title
    updateColumnTitle(todoColumn, todoTasksContainer.getChildren().size());

    // Remove the checkmark button from the task card
    HBox cardFooter = (HBox) taskCard.getChildren().get(2); // Get the footer
    HBox indicators = (HBox) cardFooter.getChildren().get(2); // Get the indicators
    indicators.getChildren().removeIf(node -> node instanceof Button && ((Button) node).getText().equals("✓"));

    // Add the task card to the "Done" column
    VBox doneTasksContainer = (VBox) ((ScrollPane) doneColumn.getChildren().get(1)).getContent();
    doneTasksContainer.getChildren().add(taskCard);

    // Update the "Done" column title
    updateColumnTitle(doneColumn, doneTasksContainer.getChildren().size());
  }

  public void showAddTaskDialog() {
    // Create a dialog
    Dialog<Void> dialog = new Dialog<>();
    dialog.setTitle("Create New Task");
    dialog.getDialogPane().setPrefWidth(500);
    dialog.getDialogPane().setPrefHeight(650);
    dialog.getDialogPane().getStyleClass().add("modern-dialog");

    // Create header with close button
    Label headerLabel = new Label("Create New Task");
    headerLabel.setFont(new Font("Arial", 18));
    headerLabel.setStyle("-fx-font-weight: bold;");

    Button closeButton = new Button("✕");
    closeButton.setStyle("-fx-background-color: transparent; -fx-font-size: 16px;");
    closeButton.setOnAction(e -> dialog.close());

    Region headerSpacer = new Region();
    HBox.setHgrow(headerSpacer, Priority.ALWAYS);

    HBox header = new HBox(headerLabel, headerSpacer, closeButton);
    header.setAlignment(Pos.CENTER_LEFT);
    header.setPadding(new Insets(10, 10, 20, 10));

    // Create "For" section with user avatar and plus button
    Label forLabel = new Label("For");
    forLabel.setStyle("-fx-text-fill: #555;");

    // User avatar with name and remove button
    Label avatarLabel = new Label();
    avatarLabel.setStyle(
        "-fx-background-color: #F0F0F0;" +
            "-fx-background-radius: 50%;" +
            "-fx-min-width: 25px;" +
            "-fx-min-height: 25px;" +
            "-fx-max-width: 25px;" +
            "-fx-max-height: 25px;" +
            "-fx-alignment: center;"
    );

    Label nameLabel = new Label("Vincent (You)");
    nameLabel.setStyle("-fx-text-fill: #333;");

    Button removeButton = new Button("×");
    removeButton.setStyle(
        "-fx-background-color: transparent;" +
            "-fx-text-fill: #999;" +
            "-fx-padding: 0 0 0 5;"
    );

    HBox userChip = new HBox(5, avatarLabel, nameLabel, removeButton);
    userChip.setStyle(
        "-fx-background-color: #F5F5F5;" +
            "-fx-background-radius: 20px;" +
            "-fx-padding: 5 10 5 5;" +
            "-fx-alignment: center-left;"
    );

    Button addUserButton = new Button("+");
    addUserButton.setStyle(
        "-fx-background-color: transparent;" +
            "-fx-border-color: #CCC;" +
            "-fx-border-radius: 50%;" +
            "-fx-min-width: 30px;" +
            "-fx-min-height: 30px;" +
            "-fx-max-width: 30px;" +
            "-fx-max-height: 30px;" +
            "-fx-alignment: center;" +
            "-fx-padding: 0;"
    );

    HBox userContainer = new HBox(10, userChip, addUserButton);
    userContainer.setAlignment(Pos.CENTER_LEFT);

    VBox forSection = new VBox(5, forLabel, userContainer);
    forSection.setPadding(new Insets(0, 0, 20, 0));

    // Create title input field
    TextField titleField = new TextField();
    titleField.setPromptText("Title");
    titleField.setStyle(
        "-fx-background-color: #FFFFFF;" +
            "-fx-border-color: #E0E0E0;" +
            "-fx-border-radius: 5px;" +
            "-fx-padding: 10px;" +
            "-fx-pref-height: 40px;"
    );

    // Create description textarea
    TextArea descriptionArea = new TextArea();
    descriptionArea.setPromptText("Description...");
    descriptionArea.setPrefHeight(200);
    descriptionArea.setWrapText(true);
    descriptionArea.setStyle(
        "-fx-background-color: #FFFFFF;" +
            "-fx-border-color: #E0E0E0;" +
            "-fx-border-radius: 5px;" +
            "-fx-padding: 10px;"
    );

    // Time estimate, hours and date fields
    TextField timeEstimateField = new TextField();
    timeEstimateField.setPromptText("Time Estimate");
    timeEstimateField.setStyle(
        "-fx-background-color: #FFFFFF;" +
            "-fx-border-color: #E0E0E0;" +
            "-fx-border-radius: 5px;" +
            "-fx-padding: 10px;" +
            "-fx-pref-height: 40px;"
    );

    TextField hoursField = new TextField();
    hoursField.setPromptText("Hours");
    hoursField.setPrefWidth(100);
    hoursField.setStyle(
        "-fx-background-color: #FFFFFF;" +
            "-fx-border-color: #E0E0E0;" +
            "-fx-border-radius: 5px;" +
            "-fx-padding: 10px;" +
            "-fx-pref-height: 40px;"
    );

    TextField dateField = new TextField();
    dateField.setPromptText("Enter start date");
    dateField.setStyle(
        "-fx-background-color: #FFFFFF;" +
            "-fx-border-color: #E0E0E0;" +
            "-fx-border-radius: 5px;" +
            "-fx-padding: 10px;" +
            "-fx-pref-height: 40px;"
    );

    HBox timeAndDateContainer = new HBox(10, timeEstimateField, hoursField, dateField);
    HBox.setHgrow(timeEstimateField, Priority.ALWAYS);
    HBox.setHgrow(dateField, Priority.ALWAYS);

    // Recurring checkbox
    CheckBox recurringCheckbox = new CheckBox("Recurring?");
    recurringCheckbox.setStyle("-fx-padding: 10 0;");

    // Interval field
    TextField intervalField = new TextField();
    intervalField.setPromptText("Interval");
    intervalField.setStyle(
        "-fx-background-color: #FFFFFF;" +
            "-fx-border-color: #E0E0E0;" +
            "-fx-border-radius: 5px;" +
            "-fx-padding: 10px;" +
            "-fx-pref-height: 40px;"
    );

    // Create Task button
    Button createTaskButton = new Button("Create Task");
    createTaskButton.setStyle(
        "-fx-background-color: #4285F4;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5px;" +
            "-fx-padding: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;"
    );
    createTaskButton.setMaxWidth(Double.MAX_VALUE);

    // Main content container
    VBox dialogContent = new VBox(15);
    dialogContent.setPadding(new Insets(0, 20, 20, 20));
    dialogContent.getChildren().addAll(
        header,
        forSection,
        titleField,
        descriptionArea,
        timeAndDateContainer,
        recurringCheckbox,
        intervalField,
        createTaskButton
    );

    // Add hidden OK button for proper dialog closing functionality
    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
    okButton.setVisible(false);

    // Connect create task button to the hidden OK button
    createTaskButton.setOnAction(event -> {
      String title = titleField.getText();
      String description = descriptionArea.getText();

      if (title != null && !title.trim().isEmpty()) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        String stringDate = formatter.format(date);
        addTaskToTodoColumn(title, description, stringDate);
        okButton.fire(); // This properly closes the dialog
      }
    });

    // Set the dialog content
    dialog.getDialogPane().setContent(dialogContent);

    // Make the dialog properly respect the close (X) window button
    dialog.setOnCloseRequest(event -> dialog.close());


    // Show the dialog
    dialog.showAndWait();
  }

  /**
   * Getter for the "Add new task" button
   */
  public Button getAddTaskButton() {
    return addTaskButton;
  }
}
