package edu.ntnu.idi.bidata.view;

import edu.ntnu.idi.bidata.utils.ThemeManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StatisticsView extends BaseView {

  private ToggleButton householdToggleButton;
  private ToggleButton myViewToggleButton;
  private ToggleButton lightModeButton;
  private ToggleButton darkModeButton;

  private VBox userSidebar;
  private HBox summaryCardContainer;
  private VBox userCardsContainer;

  @Override
  protected void createView() {
    // Main layout with sidebar and content
    HBox mainLayout = new HBox();

    // Create the sidebar
    userSidebar = createUserSidebar();

    // Create the content area
    VBox contentArea = createContentArea();
    contentArea.getStyleClass().add("statContainer");
    contentArea.setPadding(new Insets(20));
    HBox.setHgrow(contentArea, Priority.ALWAYS);

    // Add sidebar and content to main layout
    mainLayout.getChildren().addAll(userSidebar, contentArea);

    // Set the view
    view = mainLayout;
    setupThemeToggleBehavior();
  }

  /**
   * Creates the user sidebar with a vertical+horizontal lines graphic,
   * Login/Register toggles, and Light/Dark mode toggles.
   */
  private VBox createUserSidebar() {
    VBox sidebar = new VBox(20);
    sidebar.setPadding(new Insets(20));
    sidebar.setAlignment(Pos.TOP_LEFT);
    sidebar.setPrefWidth(200);
    sidebar.getStyleClass().add("sidebar");

    Label userLabel = new Label("Statistics");
    userLabel.setFont(new Font("Arial", 16));
    userLabel.setStyle("-fx-font-weight: bold;");
    userLabel.getStyleClass().add("text");

    HBox userTitle = new HBox(5, userLabel);
    userTitle.setAlignment(Pos.CENTER_LEFT);

    ToggleGroup navToggleGroup = new ToggleGroup();

    householdToggleButton = new ToggleButton("Household");
    householdToggleButton.setToggleGroup(navToggleGroup);
    householdToggleButton.setSelected(true);
    householdToggleButton.setMaxWidth(Double.MAX_VALUE);
    householdToggleButton.getStyleClass().add("pill-toggle");

    myViewToggleButton = new ToggleButton("My View");
    myViewToggleButton.setToggleGroup(navToggleGroup);
    myViewToggleButton.setMaxWidth(Double.MAX_VALUE);
    myViewToggleButton.getStyleClass().add("pill-toggle");

    VBox navigation = new VBox(10, householdToggleButton, myViewToggleButton);
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
    return sidebar;
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
   * Creates the main content area with statistics cards.
   */
  private VBox createContentArea() {
    VBox contentArea = new VBox(20);

    // Task header section
    Label tasksLabel = new Label("Tasks");
    tasksLabel.getStyleClass().add("text");
    tasksLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

    HBox taskHeader = new HBox();
    taskHeader.getChildren().add(tasksLabel);

    // Summary card container
    summaryCardContainer = new HBox();
    HBox.setHgrow(summaryCardContainer, Priority.ALWAYS);

    // Main task cards container
    userCardsContainer = new VBox(15);

    // Combine task list and user cards in horizontal layout
    HBox taskContent = new HBox(20);
    taskContent.getChildren().addAll(userCardsContainer);
    HBox.setHgrow(userCardsContainer, Priority.ALWAYS);

    contentArea.getChildren().addAll(taskHeader, summaryCardContainer, taskContent);

    return contentArea;
  }

  /**
   * Clears all user cards from the view.
   */
  public void clearCards() {
    userCardsContainer.getChildren().clear();
  }

  /**
   * Adds a user card to the view.
   */
  public void addUserCard(String tasksValue, String tasksLabel,
      String doneValue, String doneLabel,
      String remainingValue, String remainingLabel,
      String profileImagePath) {
    HBox card = createUserCard(tasksValue, tasksLabel, doneValue, doneLabel, remainingValue, remainingLabel, profileImagePath);
    userCardsContainer.getChildren().add(card);
  }

  /**
   * Sets the summary card in the view.
   */
  public void setSummaryCard(String tasksValue, String doneValue, String remainingValue) {
    summaryCardContainer.getChildren().clear();
    summaryCardContainer.getChildren().add(createSummaryCard(tasksValue, doneValue, remainingValue));
  }

  /**
   * Creates a user card (UI rendering only).
   */
  private HBox createUserCard(String tasksValue, String tasksLabel,
      String doneValue, String doneLabel,
      String remainingValue, String remainingLabel,
      String profileImagePath) {
    HBox card = new HBox(15);
    card.setPadding(new Insets(15, 20, 15, 20));
    card.getStyleClass().add("statCard");
    HBox.setHgrow(card, Priority.ALWAYS);

    // Profile picture
    Circle profilePic = new Circle(30);
    profilePic.setFill(Color.LIGHTBLUE);

    // Tasks section
    VBox tasksSection = createStatSection(tasksValue, tasksLabel, "👤");

    // Done section
    VBox doneSection = createStatSection(doneValue, doneLabel, "✓");

    // Remaining section
    VBox remainingSection = createStatSection(remainingValue, remainingLabel, "⏳");

    // Add spacers between sections for even distribution
    Region spacer1 = new Region();
    Region spacer2 = new Region();
    Region spacer3 = new Region();
    HBox.setHgrow(spacer1, Priority.ALWAYS);
    HBox.setHgrow(spacer2, Priority.ALWAYS);
    HBox.setHgrow(spacer3, Priority.ALWAYS);

    card.getChildren().addAll(
        profilePic, spacer1,
        tasksSection, spacer2,
        doneSection, spacer3,
        remainingSection
    );

    return card;
  }

  /**
   * Creates a summary card (UI rendering only).
   */
  private HBox createSummaryCard(String tasksValue, String doneValue, String remainingValue) {
    HBox card = new HBox(15);
    card.setPadding(new Insets(15, 20, 15, 20));
    card.getStyleClass().add("statCard");
    HBox.setHgrow(card, Priority.ALWAYS);

    // Tasks section
    VBox tasksSection = createStatSection(tasksValue, "Total tasks", "👤");
    tasksSection.getStyleClass().add("text");

    // Done section
    VBox doneSection = createStatSection(doneValue, "Total done", "✓");
    doneSection.getStyleClass().add("text");

    // Remaining section
    VBox remainingSection = createStatSection(remainingValue, "Total remaining", "⏳");
    remainingSection.getStyleClass().add("text");

    // Add spacers between sections for even distribution
    Region spacer1 = new Region();
    Region spacer2 = new Region();
    Region spacer3 = new Region();
    HBox.setHgrow(spacer1, Priority.ALWAYS);
    HBox.setHgrow(spacer2, Priority.ALWAYS);
    HBox.setHgrow(spacer3, Priority.ALWAYS);

    card.getChildren().addAll(
        tasksSection, spacer1,
        doneSection, spacer2,
        remainingSection, spacer3
    );

    return card;
  }

  /**
   * Creates a stat section with value, label, and icon (UI rendering only).
   */
  private VBox createStatSection(String value, String label, String icon) {
    VBox section = new VBox(5);

    // Value label with icon
    HBox headerWithIcon = new HBox();
    Label valueLabel = new Label(value);
    valueLabel.getStyleClass().add("text");
    valueLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Label iconLabel = new Label(icon);
    iconLabel.setTextFill(Color.LIGHTGRAY);

    headerWithIcon.getChildren().addAll(valueLabel, spacer, iconLabel);

    // Description label
    Label descLabel = new Label(label);
    descLabel.setFont(Font.font("System", 14));
    descLabel.setTextFill(Color.GRAY);

    section.getChildren().addAll(headerWithIcon, descLabel);

    return section;
  }
}