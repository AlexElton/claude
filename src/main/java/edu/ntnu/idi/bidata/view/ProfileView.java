package edu.ntnu.idi.bidata.view;

import edu.ntnu.idi.bidata.utils.ThemeManager;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ProfileView extends BaseView {

  // Sidebar components
  private VBox sidebar;
  private ToggleButton editProfileTab;
  private ToggleButton passwordSecurityTab;
  private ToggleButton householdTab;
  private ToggleButton logoutTab;
  private ToggleButton lightModeButton;
  private ToggleButton darkModeButton;

  // Content area
  private VBox profileContent;

  @Override
  protected void createView() {
    profileContent = createOriginalContent();

    sidebar = createSidebar();

    HBox mainLayout = new HBox();
    mainLayout.getChildren().addAll(sidebar, profileContent);
    HBox.setHgrow(profileContent, Priority.ALWAYS);
    mainLayout.setAlignment(Pos.TOP_LEFT);

    view = mainLayout;
  }

  private VBox createOriginalContent() {
    VBox content = new VBox(20);
    content.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    content.setPadding(new Insets(25));

    // Header with title and last update info
    BorderPane header = createHeader();
    content.getChildren().add(header);

    // Main content container with Personal and Contact sections
    HBox contentBox = new HBox(40);
    contentBox.setAlignment(Pos.TOP_CENTER);
    contentBox.getChildren().addAll(createPersonalSection(), createContactSection());
    content.getChildren().add(contentBox);

    // Save button at bottom
    Button saveButton = new Button("Save");
    saveButton.getStyleClass().add("save-button");
    saveButton.setPrefWidth(100);
    saveButton.setPrefHeight(40);
    saveButton.setOnAction(event -> {
      System.out.println("Profile saved!");
    });
    content.getChildren().add(saveButton);

    return content;
  }

  private BorderPane createHeader() {
    BorderPane header = new BorderPane();
    header.setPadding(new Insets(0, 0, 10, 0));

    // Left side - Title
    Label title = new Label("Edit Profile");
    title.getStyleClass().add("title");
    header.setLeft(title);

    // Right side - Last update info with current date
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d", Locale.ENGLISH);
    String formattedDate = currentDate.format(formatter);

    Label lastUpdate = new Label("Last update: " + formattedDate);
    lastUpdate.getStyleClass().add("update-info");
    header.setRight(lastUpdate);

    return header;
  }

  private VBox createPersonalSection() {
    VBox personalSection = new VBox(15);
    personalSection.setAlignment(Pos.TOP_LEFT);
    personalSection.setPrefWidth(250);

    // Section title
    Label personalTitle = new Label("Personal");
    personalTitle.getStyleClass().add("subtitle");
    personalSection.getChildren().add(personalTitle);

    // First Name field
    VBox firstNameBox = new VBox(5);
    Label firstNameLabel = new Label("First Name");
    firstNameLabel.getStyleClass().add("field-label");
    TextField firstNameField = new TextField();
    firstNameField.setPromptText("John");
    firstNameField.setPrefWidth(200);
    firstNameBox.getChildren().addAll(firstNameLabel, firstNameField);
    personalSection.getChildren().add(firstNameBox);

    // Surname field
    VBox surnameBox = new VBox(5);
    Label surnameLabel = new Label("Surname");
    surnameLabel.getStyleClass().add("field-label");
    TextField surnameField = new TextField();
    surnameField.setPromptText("Doe");
    surnameField.setPrefWidth(200);
    surnameBox.getChildren().addAll(surnameLabel, surnameField);
    personalSection.getChildren().add(surnameBox);

    // Date of birth field
    VBox dobBox = new VBox(5);
    Label dobLabel = new Label("Date of birth");
    dobLabel.getStyleClass().add("field-label");

    // Using DatePicker instead of TextField with eye icon
    DatePicker dobPicker = new DatePicker();
    dobPicker.setPromptText("Enter Value");
    dobPicker.setShowWeekNumbers(false);
    Locale.setDefault(Locale.ENGLISH);

    dobBox.getChildren().addAll(dobLabel, dobPicker);
    personalSection.getChildren().add(dobBox);

    // Education level field
    VBox educationBox = new VBox(5);
    Label educationLabel = new Label("Gender");
    educationLabel.getStyleClass().add("field-label");
    ComboBox<String> educationCombo = new ComboBox<>();
    educationCombo.getItems().addAll("Man", "Woman", "Other");
    educationCombo.setPrefWidth(150);
    educationBox.getChildren().addAll(educationLabel, educationCombo);
    personalSection.getChildren().add(educationBox);

    return personalSection;
  }

  // Create Contact section (same as before)
  private VBox createContactSection() {
    VBox contactSection = new VBox(15);
    contactSection.setAlignment(Pos.TOP_LEFT);
    contactSection.setPrefWidth(250);

    // Section title
    Label contactTitle = new Label("Contact");
    contactTitle.getStyleClass().add("subtitle");
    contactSection.getChildren().add(contactTitle);

    // Email field
    VBox emailBox = new VBox(5);
    Label emailLabel = new Label("Email");
    emailLabel.getStyleClass().add("field-label");
    TextField emailField = new TextField();
    emailField.setPromptText("Enter email");
    emailBox.getChildren().addAll(emailLabel, emailField);
    contactSection.getChildren().add(emailBox);

    // Phone Number field with country code
    VBox phoneBox = new VBox(5);
    Label phoneLabel = new Label("Phone Number");
    phoneLabel.getStyleClass().add("field-label");

    HBox phoneRow = new HBox(5);
    ComboBox<String> countryCodeCombo = new ComboBox<>();
    countryCodeCombo.getItems().addAll("+47", "+46", "+45", "+44", "+1");
    countryCodeCombo.setPrefWidth(60);

    TextField phoneField = new TextField();
    phoneField.setPromptText("Enter Phone Number");
    HBox.setHgrow(phoneField, Priority.ALWAYS);

    phoneRow.getChildren().addAll(countryCodeCombo, phoneField);
    phoneBox.getChildren().addAll(phoneLabel, phoneRow);
    contactSection.getChildren().add(phoneBox);

    // Country field
    VBox countryBox = new VBox(5);
    Label countryLabel = new Label("Country");
    countryLabel.getStyleClass().add("field-label");
    ComboBox<String> countryCombo = new ComboBox<>();
    countryCombo.getItems().addAll("Norway", "Sweden", "Denmark", "Finland", "USA");
    countryCombo.setPrefWidth(250);
    countryBox.getChildren().addAll(countryLabel, countryCombo);
    contactSection.getChildren().add(countryBox);

    // City field
    VBox cityBox = new VBox(5);
    Label cityLabel = new Label("City");
    cityLabel.getStyleClass().add("field-label");
    TextField cityField = new TextField();
    cityField.setPromptText("Enter city");
    cityField.setPrefWidth(250);
    cityBox.getChildren().addAll(cityLabel, cityField);
    contactSection.getChildren().add(cityBox);

    return contactSection;
  }

  // Create the sidebar with tabs and theme toggles
  private VBox createSidebar() {
    VBox side = new VBox(20);
    side.setPadding(new Insets(20));
    side.setAlignment(Pos.TOP_LEFT);
    side.setPrefWidth(200);
    side.getStyleClass().add("sidebar");

    Label sideTitle = new Label("Profile");
    sideTitle.setFont(new Font("Arial", 16));
    sideTitle.setStyle("-fx-font-weight: bold;");
    sideTitle.getStyleClass().add("text");

    HBox titleBox = new HBox(5, sideTitle);
    titleBox.setAlignment(Pos.CENTER_LEFT);

    ToggleGroup navGroup = new ToggleGroup();
    editProfileTab = new ToggleButton("Edit Profile");
    editProfileTab.setToggleGroup(navGroup);
    editProfileTab.setSelected(true);
    editProfileTab.setMaxWidth(Double.MAX_VALUE);
    editProfileTab.getStyleClass().add("pill-toggle");

    passwordSecurityTab = new ToggleButton("Password & Security");
    passwordSecurityTab.setToggleGroup(navGroup);
    passwordSecurityTab.setMaxWidth(Double.MAX_VALUE);
    passwordSecurityTab.getStyleClass().add("pill-toggle");

    householdTab = new ToggleButton("Household");
    householdTab.setToggleGroup(navGroup);
    householdTab.setMaxWidth(Double.MAX_VALUE);
    householdTab.getStyleClass().add("pill-toggle");

    logoutTab = new ToggleButton("Logout");
    logoutTab.setToggleGroup(navGroup);
    logoutTab.setMaxWidth(Double.MAX_VALUE);
    logoutTab.getStyleClass().add("pill-toggle");

    VBox navButtons = new VBox(10, editProfileTab, passwordSecurityTab, householdTab, logoutTab);
    HBox lineNav = new HBox(10, createLineGraphic(), navButtons);
    lineNav.setAlignment(Pos.CENTER_LEFT);

    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);

    ToggleGroup themeGroup = new ToggleGroup();
    lightModeButton = new ToggleButton("    Light");
    lightModeButton.setToggleGroup(themeGroup);
    lightModeButton.setMaxWidth(Double.MAX_VALUE);
    lightModeButton.getStyleClass().addAll("themeSwitcherButtons", "lightButton");

    darkModeButton = new ToggleButton("    Dark");
    darkModeButton.setToggleGroup(themeGroup);
    darkModeButton.setMaxWidth(Double.MAX_VALUE);
    darkModeButton.getStyleClass().addAll("themeSwitcherButtons", "darkButton");

    HBox themeSwitcher = new HBox(10, lightModeButton, darkModeButton);
    themeSwitcher.setAlignment(Pos.CENTER);
    themeSwitcher.getStyleClass().add("themeSwitcher");

    side.getChildren().addAll(titleBox, lineNav, spacer, themeSwitcher);

    setupThemeToggleBehavior();

    // (Optional) Add navigation actions for the sidebar tabs here.
    // For example, you could update the content area based on the selected tab.
    // For now, the ProfileView always shows the same content.

    return side;
  }

  private Pane createLineGraphic() {
    Pane linesPane = new Pane();
    linesPane.setPrefSize(16, 63);
    Line verticalLine = new Line(1, 0, 1, 138);
    verticalLine.getStyleClass().add("sidebarLines");
    verticalLine.setStrokeWidth(2);
    Line horizontal1 = new Line(2, 18, 16, 18);
    horizontal1.getStyleClass().add("sidebarLines");
    horizontal1.setStrokeWidth(2);
    Line horizontal2 = new Line(2, 58, 16, 58);
    horizontal2.getStyleClass().add("sidebarLines");
    horizontal2.setStrokeWidth(2);
    Line horizontal3 = new Line(2, 98, 16, 98);
    horizontal3.getStyleClass().add("sidebarLines");
    horizontal3.setStrokeWidth(2);
    Line horizontal4 = new Line(2, 138, 16, 138);
    horizontal4.getStyleClass().add("sidebarLines");
    horizontal4.setStrokeWidth(2);
    linesPane.getChildren().addAll(verticalLine, horizontal1, horizontal2, horizontal3, horizontal4);
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
}
