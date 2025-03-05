package edu.ntnu.idi.bidata.view;

import edu.ntnu.idi.bidata.utils.ThemeManager;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class HelpView extends BaseView {

  private VBox helpSidebar;
  private ToggleButton gettingStartedToggle;
  private ToggleButton faqToggle;
  private ToggleButton troubleshootingToggle;
  private ToggleButton contactUsToggle;
  private ToggleButton lightModeButton;
  private ToggleButton darkModeButton;

  private VBox gettingStartedContainer;
  private VBox faqContainer;
  private VBox troubleshootingContainer;
  private VBox contactUsContainer;

  @Override
  protected void createView() {
    helpSidebar = createHelpSidebar();

    gettingStartedContainer = createGettingStartedContainer();
    faqContainer = createFAQContainer();
    troubleshootingContainer = createTroubleshootingContainer();
    contactUsContainer = createContactUsContainer();

    faqContainer.setVisible(false);
    troubleshootingContainer.setVisible(false);
    contactUsContainer.setVisible(false);

    StackPane contentPane = new StackPane(gettingStartedContainer, faqContainer, troubleshootingContainer, contactUsContainer);
    contentPane.setAlignment(Pos.CENTER);
    HBox.setHgrow(contentPane, Priority.ALWAYS);

    HBox mainLayout = new HBox(helpSidebar, contentPane);
    mainLayout.setSpacing(0);
    mainLayout.setPadding(new Insets(0));
    mainLayout.setAlignment(Pos.TOP_LEFT);

    view = mainLayout;
    setupToggleBehavior();
  }
  /**
   * Creates the help sidebar with a vertical+horizontal lines graphic,
   * Help pages toggle, and Light/Dark mode toggles.
   */
  private VBox createHelpSidebar() {
    VBox sidebar = new VBox(20);
    sidebar.setPadding(new Insets(20));
    sidebar.setAlignment(Pos.TOP_LEFT);
    sidebar.setPrefWidth(200);
    sidebar.getStyleClass().add("sidebar");

    Label userLabel = new Label("User");
    userLabel.setFont(new Font("Arial", 16));
    userLabel.setStyle("-fx-font-weight: bold;");
    userLabel.getStyleClass().add("text");

    HBox userTitle = new HBox(5, userLabel);
    userTitle.setAlignment(Pos.CENTER_LEFT);

    ToggleGroup navToggleGroup = new ToggleGroup();

    gettingStartedToggle = new ToggleButton("Getting Started");
    gettingStartedToggle.setToggleGroup(navToggleGroup);
    gettingStartedToggle.setSelected(true);
    gettingStartedToggle.setMaxWidth(Double.MAX_VALUE);
    gettingStartedToggle.getStyleClass().add("pill-toggle");

    faqToggle = new ToggleButton("FAQ");
    faqToggle.setToggleGroup(navToggleGroup);
    faqToggle.setMaxWidth(Double.MAX_VALUE);
    faqToggle.getStyleClass().add("pill-toggle");

    troubleshootingToggle = new ToggleButton("Troubleshooting");
    troubleshootingToggle.setToggleGroup(navToggleGroup);
    troubleshootingToggle.setMaxWidth(Double.MAX_VALUE);
    troubleshootingToggle.getStyleClass().add("pill-toggle");

    contactUsToggle = new ToggleButton("Contact Us");
    contactUsToggle.setToggleGroup(navToggleGroup);
    contactUsToggle.setMaxWidth(Double.MAX_VALUE);
    contactUsToggle.getStyleClass().add("pill-toggle");

    VBox navigation = new VBox(10, gettingStartedToggle, faqToggle, troubleshootingToggle, contactUsToggle);
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

  private VBox createGettingStartedContainer() {
    VBox container = new VBox(10);
    container.setPadding(new Insets(40));
    container.setAlignment(Pos.CENTER);
    container.getStyleClass().add("container");
    Label title = new Label("Getting Started");
    title.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
    title.getStyleClass().add("text");
    Label step1 = new Label("- Create an account");
    step1.getStyleClass().add("text");
    Label step2 = new Label("- Set up your profile");
    step2.getStyleClass().add("text");
    Label step3 = new Label("- Create/join a household");
    step3.getStyleClass().add("text");
    Label step4 = new Label("- Explore features!");
    step4.getStyleClass().add("text");
    container.getChildren().addAll(title, step1, step2, step3, step4);
    return container;
  }

  private VBox createFAQContainer() {
    VBox container = new VBox(10);
    container.setPadding(new Insets(40));
    container.setAlignment(Pos.CENTER);
    container.getStyleClass().add("container");
    Label title = new Label("Frequently Asked Questions");
    title.getStyleClass().add("text");
    title.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
    Label question1 = new Label("Q: How do I reset my password?");
    question1.getStyleClass().add("text");
    Label answer1 = new Label("A: Go to settings and click 'Reset Password'.");
    answer1.getStyleClass().add("text");
    Label question2 = new Label("Q: How do I contact support?");
    question2.getStyleClass().add("text");
    Label answer2 = new Label("A: Send an email to alexanoe@stud.ntnu.no.");
    answer2.getStyleClass().add("text");
    container.getChildren().addAll(title, question1, answer1, question2, answer2);
    return container;
  }

  private VBox createTroubleshootingContainer() {
    VBox container = new VBox(10);
    container.setPadding(new Insets(40));
    container.setAlignment(Pos.CENTER);
    container.getStyleClass().add("container");
    Label title = new Label("Troubleshooting");
    title.getStyleClass().add("text");
    title.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
    Label issue1 = new Label("Issue: App not loading?");
    issue1.getStyleClass().add("text");
    Label fix1 = new Label("Solution: Check your internet connection and restart the app.");
    fix1.getStyleClass().add("text");
    Label issue2 = new Label("Issue: Login problems?");
    issue2.getStyleClass().add("text");
    Label fix2 = new Label("Solution: Ensure correct credentials and reset password if needed.");
    fix2.getStyleClass().add("text");
    container.getChildren().addAll(title, issue1, fix1, issue2, fix2);
    return container;
  }

  private VBox createContactUsContainer() {
    VBox container = new VBox(10);
    container.setPadding(new Insets(40));
    container.setAlignment(Pos.CENTER);
    container.getStyleClass().add("container");
    Label title = new Label("Contact Us");
    title.getStyleClass().add("text");
    title.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
    Label email = new Label("Email: alexanoe@stud.ntnu.no");
    email.getStyleClass().add("text");
    Label phone = new Label("Phone: +47 476 72 818");
    phone.getStyleClass().add("text");
    Label address = new Label("Address: Høgskoleringen 1 - 7034 Trondheim");
    address.getStyleClass().add("text");
    container.getChildren().addAll(title, email, phone, address);
    return container;
  }

  private void setupToggleBehavior() {
    gettingStartedToggle.setOnAction(e -> showSection(gettingStartedContainer));
    faqToggle.setOnAction(e -> showSection(faqContainer));
    troubleshootingToggle.setOnAction(e -> showSection(troubleshootingContainer));
    contactUsToggle.setOnAction(e -> showSection(contactUsContainer));

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

  private void showSection(VBox section) {
    gettingStartedContainer.setVisible(false);
    faqContainer.setVisible(false);
    troubleshootingContainer.setVisible(false);
    contactUsContainer.setVisible(false);
    section.setVisible(true);
  }
}
