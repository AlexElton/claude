package edu.ntnu.idi.bidata.view;

import edu.ntnu.idi.bidata.utils.ThemeManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

/**
 * A view that shows Login or Register forms, with a user sidebar.
 */
public class LoginRegisterView extends BaseView {

  private VBox userSidebar;
  private ToggleButton loginToggleButton;
  private ToggleButton registerToggleButton;
  private ToggleButton lightModeButton;
  private ToggleButton darkModeButton;
  private VBox loginContainer;
  private VBox registerContainer;
  private TextField loginEmailField;
  private PasswordField loginPasswordField;
  private Button loginFormButton;
  private TextField registerFullNameField;
  private TextField registerEmailField;
  private PasswordField registerPasswordField;
  private PasswordField registerConfirmField;
  private Button registerFormButton;

  @Override
  protected void createView() {
    userSidebar = createUserSidebar();
    loginContainer = createLoginContainer();
    registerContainer = createRegisterContainer();
    registerContainer.setVisible(false);

    StackPane contentPane = new StackPane(loginContainer, registerContainer);
    contentPane.setAlignment(Pos.CENTER);
    HBox.setHgrow(contentPane, Priority.ALWAYS);

    HBox mainLayout = new HBox(userSidebar, contentPane);
    mainLayout.setSpacing(0);
    mainLayout.setPadding(new Insets(0));
    mainLayout.setAlignment(Pos.TOP_LEFT);

    view = mainLayout;
    setupToggleBehavior();
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

    Label userLabel = new Label("User");
    userLabel.setFont(new Font("Arial", 16));
    userLabel.setStyle("-fx-font-weight: bold;");
    userLabel.getStyleClass().add("text");

    HBox userTitle = new HBox(5, userLabel);
    userTitle.setAlignment(Pos.CENTER_LEFT);

    ToggleGroup navToggleGroup = new ToggleGroup();

    loginToggleButton = new ToggleButton("Login");
    loginToggleButton.setToggleGroup(navToggleGroup);
    loginToggleButton.setSelected(true);
    loginToggleButton.setMaxWidth(Double.MAX_VALUE);
    loginToggleButton.getStyleClass().add("pill-toggle");

    registerToggleButton = new ToggleButton("Register");
    registerToggleButton.setToggleGroup(navToggleGroup);
    registerToggleButton.setMaxWidth(Double.MAX_VALUE);
    registerToggleButton.getStyleClass().add("pill-toggle");

    VBox navigation = new VBox(10, loginToggleButton, registerToggleButton);
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

  /**
   * Creates the Login form container.
   */
  private VBox createLoginContainer() {
    VBox container = new VBox(20);
    container.setAlignment(Pos.CENTER);
    container.setPadding(new Insets(40, 60, 40, 60));
    container.getStyleClass().add("container");
    Label title = new Label("Login");
    title.getStyleClass().add("loginRegisterLabel");
    VBox emailBox = new VBox(5);
    emailBox.setAlignment(Pos.CENTER_LEFT);
    emailBox.setMaxWidth(300);
    Label emailLabel = new Label("Email");
    emailLabel.getStyleClass().add("text");
    loginEmailField = new TextField();
    loginEmailField.setPromptText("Enter email");
    loginEmailField.setMaxWidth(Double.MAX_VALUE);
    emailBox.getChildren().addAll(emailLabel, loginEmailField);
    VBox passwordBox = new VBox(5);
    passwordBox.setAlignment(Pos.CENTER_LEFT);
    passwordBox.setMaxWidth(300);
    Label passwordLabel = new Label("Password");
    passwordLabel.getStyleClass().add("text");
    loginPasswordField = new PasswordField();
    loginPasswordField.setPromptText("Enter password");
    loginPasswordField.setMaxWidth(Double.MAX_VALUE);
    passwordBox.getChildren().addAll(passwordLabel, loginPasswordField);
    Label registerText = new Label("Don't have a user?");
    registerText.getStyleClass().add("text");
    Hyperlink registerLink = new Hyperlink("Register here!");
    registerLink.setOnAction(e -> {
      loginContainer.setVisible(false);
      registerContainer.setVisible(true);
      registerToggleButton.setSelected(true);
    });
    HBox registerPrompt = new HBox(5, registerText, registerLink);
    registerPrompt.setAlignment(Pos.CENTER_LEFT);
    registerPrompt.setMaxWidth(300);
    loginFormButton = new Button("Login");
    loginFormButton.setPrefWidth(300);
    loginFormButton.getStyleClass().add("loginRegisterButton");
    container.getChildren().addAll(title, emailBox, passwordBox, registerPrompt, loginFormButton);
    return container;
  }

  /**
   * Creates the Register form container.
   */
  private VBox createRegisterContainer() {
    VBox container = new VBox(20);
    container.setAlignment(Pos.CENTER);
    container.setPadding(new Insets(40, 60, 40, 60));
    container.getStyleClass().add("container");
    Label title = new Label("Register");
    title.getStyleClass().add("loginRegisterLabel");
    VBox fullNameBox = new VBox(5);
    fullNameBox.setAlignment(Pos.CENTER_LEFT);
    fullNameBox.setMaxWidth(300);
    Label fullNameLabel = new Label("Full Name");
    fullNameLabel.getStyleClass().add("text");
    registerFullNameField = new TextField();
    registerFullNameField.setPromptText("Enter full name");
    registerFullNameField.setMaxWidth(Double.MAX_VALUE);
    fullNameBox.getChildren().addAll(fullNameLabel, registerFullNameField);
    VBox emailBox = new VBox(5);
    emailBox.setAlignment(Pos.CENTER_LEFT);
    emailBox.setMaxWidth(300);
    Label emailLabel = new Label("Email");
    emailLabel.getStyleClass().add("text");
    registerEmailField = new TextField();
    registerEmailField.setPromptText("Enter email");
    registerEmailField.setMaxWidth(Double.MAX_VALUE);
    emailBox.getChildren().addAll(emailLabel, registerEmailField);
    VBox passwordBox = new VBox(5);
    passwordBox.setAlignment(Pos.CENTER_LEFT);
    passwordBox.setMaxWidth(300);
    Label passwordLabel = new Label("Password");
    passwordLabel.getStyleClass().add("text");
    registerPasswordField = new PasswordField();
    registerPasswordField.setPromptText("Enter password");
    registerPasswordField.setMaxWidth(Double.MAX_VALUE);
    passwordBox.getChildren().addAll(passwordLabel, registerPasswordField);
    VBox confirmBox = new VBox(5);
    confirmBox.setAlignment(Pos.CENTER_LEFT);
    confirmBox.setMaxWidth(300);
    Label confirmLabel = new Label("Confirm password");
    confirmLabel.getStyleClass().add("text");
    registerConfirmField = new PasswordField();
    registerConfirmField.setPromptText("Confirm password");
    registerConfirmField.setMaxWidth(Double.MAX_VALUE);
    confirmBox.getChildren().addAll(confirmLabel, registerConfirmField);
    Label haveUserText = new Label("Have a user?");
    haveUserText.getStyleClass().add("text");
    Hyperlink loginLink = new Hyperlink("Login here!");
    loginLink.setOnAction(e -> {
      registerContainer.setVisible(false);
      loginContainer.setVisible(true);
      loginToggleButton.setSelected(true);
    });
    HBox haveUserContainer = new HBox(5, haveUserText, loginLink);
    haveUserContainer.setAlignment(Pos.CENTER_LEFT);
    haveUserContainer.setMaxWidth(300);
    registerFormButton = new Button("Register");
    registerFormButton.setPrefWidth(300);
    registerFormButton.getStyleClass().add("loginRegisterButton");
    container.getChildren().addAll(title, fullNameBox, emailBox, passwordBox, confirmBox, haveUserContainer, registerFormButton);
    return container;
  }

  private void setupToggleBehavior() {
    loginToggleButton.setOnAction(e -> {
      loginContainer.setVisible(true);
      registerContainer.setVisible(false);
    });
    registerToggleButton.setOnAction(e -> {
      loginContainer.setVisible(false);
      registerContainer.setVisible(true);
    });

    // Initialize theme toggle buttons from global state
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

  public Button getLoginFormButton() {
    return loginFormButton;
  }

  public Button getRegisterFormButton() {
    return registerFormButton;
  }

  public ToggleButton getLoginToggleButton() {
    return loginToggleButton;
  }

  public TextField getLoginEmailField() {
    return loginEmailField;
  }

  public PasswordField getLoginPasswordField() {
    return loginPasswordField;
  }

  public TextField getRegisterFullNameField () {
    return registerFullNameField;
  }

  public TextField getRegisterEmailField() {
    return registerEmailField;
  }

  public PasswordField getRegisterPasswordField() {
    return registerPasswordField;
  }

  public PasswordField getRegisterConfirmField() {
    return registerConfirmField;
  }


}
