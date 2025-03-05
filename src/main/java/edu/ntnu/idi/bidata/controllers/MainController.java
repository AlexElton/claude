package edu.ntnu.idi.bidata.controllers;



import edu.ntnu.idi.bidata.utils.ThemeManager;
import java.util.Objects;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import edu.ntnu.idi.bidata.utils.ControllerFactory;
import edu.ntnu.idi.bidata.utils.Page;
import edu.ntnu.idi.bidata.view.MainView;

public class MainController {
  private final MainView mainView;

  public MainController(Stage primaryStage) {
    mainView = new MainView();

    // Set up event handlers for buttons
    setupEventHandlers();

    // Set up the scene and stage
    Scene scene = new Scene(mainView.getRoot(), 800, 600);

    ThemeManager.setLightTheme(scene);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Household App");
    primaryStage.show();
  }

  private void setupEventHandlers() {
    // Issue Board Button
    mainView.getIssueBoardButton().setOnAction(e -> navigateToPage(Page.ISSUE_BOARD, mainView.getIssueBoardButton()));

    // Statistics Button
    mainView.getStatisticsButton().setOnAction(e -> navigateToPage(Page.STATISTICS, mainView.getStatisticsButton()));

    // Profile Page Button
    mainView.getProfilePageButton().setOnAction(e -> navigateToPage(Page.PROFILE, mainView.getProfilePageButton()));

    // Login / Register Button
    mainView.getLoginRegisterButton().setOnAction(e -> navigateToPage(Page.LOGIN_REGISTER, mainView.getLoginRegisterButton()));

    // Help Button
    mainView.getHelpButton().setOnAction(e -> navigateToPage(Page.HELP, mainView.getHelpButton()));

    // Profile Button (Top Bar)
    mainView.getProfileButton().setOnAction(e -> navigateToPage(Page.PROFILE, mainView.getProfileButton()));

    // Notification Button (Top Bar)
    mainView.getNotificationButton().setOnAction(e -> {
      ContextMenu notificationMenu = mainView.getNotificationMenu();
      if (!notificationMenu.isShowing()) {
        notificationMenu.show(mainView.getNotificationButton(), javafx.geometry.Side.BOTTOM, 0, 0);
      } else {
        notificationMenu.hide();
      }
    });
  }

  private void navigateToPage(Page page, Button selectedButton) {
    // Handle page navigation
    PageController controller = ControllerFactory.createController(page);
    Pane view = controller.getView();
    mainView.getContentArea().getChildren().setAll(view);

    // Unselect all buttons
    Button[] navButtons = {
        mainView.getIssueBoardButton(),
        mainView.getStatisticsButton(),
        mainView.getProfilePageButton(),
        mainView.getLoginRegisterButton(),
        mainView.getHelpButton(),
        mainView.getProfileButton()
    };

    for (Button btn : navButtons) {
      btn.getStyleClass().remove("selected");
    }

    // Select the clicked button
    selectedButton.getStyleClass().add("selected");
  }
}