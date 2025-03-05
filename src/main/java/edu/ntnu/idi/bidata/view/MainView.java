package edu.ntnu.idi.bidata.view;

import java.util.Objects;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MainView {
  private final BorderPane root;
  private HBox topBar;
  private VBox leftMenu;
  private final StackPane contentArea;
  private ContextMenu notificationMenu;

  // Expose buttons so the controller can access them
  private Button issueBoardButton;
  private Button statisticsButton;
  private Button profilePageButton;
  private Button loginRegisterButton;
  private Button helpButton;
  private Button profileButton;
  private Button notificationButton;

  public MainView() {
    root = new BorderPane();
    topBar = createTopBar();
    leftMenu = createLeftMenu();
    contentArea = new StackPane();

    // VBox to hold the top bar and content area
    VBox contentContainer = new VBox(topBar, contentArea);
    VBox.setVgrow(contentArea, Priority.ALWAYS); // Allow content area to grow

    // Set up the layout
    root.setLeft(leftMenu);
    root.setCenter(contentContainer);
  }

  private HBox createTopBar() {
    topBar = new HBox();
    topBar.getStyleClass().add("top-bar");

    // Welcome text
    Label welcomeText = new Label("Welcome Back, Vincent 👋");
    welcomeText.getStyleClass().add("welcome-text");
    welcomeText.getStyleClass().add("text");

    // Spacer to push the buttons to the right
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    //Create buttons
    profileButton = createIconButton("Icons/Profile-Black-100.png");
    notificationButton = createNotificationButton();

    //Add style
    notificationButton.getStyleClass().add("top-bar-button");
    profileButton.getStyleClass().add("top-bar-button");

    topBar.getChildren().addAll(welcomeText, spacer, notificationButton, profileButton);

    return topBar;
  }

  private VBox createLeftMenu() {
    leftMenu = new VBox();
    leftMenu.getStyleClass().add("left-menu");

    // ImageView for the logo
    ImageView logo = new ImageView(new Image("Icons/Logo.png"));
    logo.setPreserveRatio(true);
    logo.setFitHeight(50);

    // Padding around logo
    VBox.setMargin(logo, new Insets(10, 0, 20, 8));

    // Nav buttons
    issueBoardButton = createIconButton("Icons/Home.png");
    statisticsButton = createIconButton("Icons/Statistics.png");
    profilePageButton = createIconButton("Icons/Profile.png");
    loginRegisterButton = createIconButton("Icons/Profile.png");
    helpButton = createIconButton("Icons/Q&A.png");

    Button[] navButtons = {issueBoardButton, statisticsButton, profilePageButton, loginRegisterButton, helpButton};

    // Apply style to buttons
    for (Button button : navButtons) {
      button.getStyleClass().add("nav-button");
    }

    // Add the logo and navigation buttons to the left menu
    leftMenu.getChildren().addAll(logo);
    leftMenu.getChildren().addAll(navButtons);

    return leftMenu;
  }

  private Button createNotificationButton() {
    Button bellButton = createIconButton("Icons/Notification.png");
    bellButton.getStyleClass().add("top-bar-button");

    notificationMenu = new ContextMenu();
    notificationMenu.getStyleClass().add("notification-menu");

    // Dummy notifications
    addNotification("Elton", "Added a new task: Wash the dishes");
    addNotification("Alex", "Completed a task: Take out the trash");
    return bellButton;
  }

  public ContextMenu getNotificationMenu() {
    return notificationMenu;
  }

  public void addNotification(String personName, String message) {
    // Create a MenuItem
    MenuItem notification = new MenuItem();
    notification.getStyleClass().add("notification-item");

    // Create a VBox to hold the header and text
    VBox notificationContent = new VBox();
    notificationContent.setSpacing(5);

    // Notification header (person's name)
    Text header = new Text(personName);
    header.getStyleClass().add("notification-header");

    // Description of the notification
    Text description = new Text(message);
    description.getStyleClass().add("notification-description");

    notificationContent.getChildren().addAll(header, description);
    notification.setGraphic(notificationContent);
    notificationMenu.getItems().add(notification);
  }

  private Button createIconButton(String imagePath) {
    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + imagePath)));
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(24);
    imageView.setFitHeight(24);

    Button button = new Button("", imageView);
    button.getStyleClass().add("icon-button");

    return button;
  }

  public BorderPane getRoot() {
    return root;
  }

  public StackPane getContentArea() {
    return contentArea;
  }

  // Getters for buttons
  public Button getIssueBoardButton() {
    return issueBoardButton;
  }

  public Button getStatisticsButton() {
    return statisticsButton;
  }

  public Button getProfilePageButton() {
    return profilePageButton;
  }

  public Button getLoginRegisterButton() {
    return loginRegisterButton;
  }

  public Button getHelpButton() {
    return helpButton;
  }

  public Button getProfileButton() {
    return profileButton;
  }

  public Button getNotificationButton() {
    return notificationButton;
  }
}