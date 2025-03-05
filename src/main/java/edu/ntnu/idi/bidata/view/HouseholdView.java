package edu.ntnu.idi.bidata.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

/**
 * HouseholdView - UI for creating or joining a household.
 */
public class HouseholdView extends BaseView {

  private Label householdLabel;
  private Button chooseCreateButton;
  private Button chooseJoinButton;
  private TextField householdNameField;
  private TextField householdCodeField;
  private Button createButton;
  private Button joinButton;
  private Button leaveButton;

  @Override
  protected void createView() {
    // Sidebar for Create/Join Household selection
    VBox sidebar = new VBox(20);
    sidebar.setPadding(new Insets(20));
    sidebar.setAlignment(Pos.TOP_LEFT);
    sidebar.setPrefWidth(200);
    sidebar.getStyleClass().add("household-sidebar");

    // Sidebar buttons
    chooseCreateButton = new Button("Create Household");
    chooseCreateButton.getStyleClass().add("sidebar-button");
    chooseCreateButton.setMaxWidth(Double.MAX_VALUE);

    chooseJoinButton = new Button("Join Household");
    chooseJoinButton.getStyleClass().add("sidebar-button");
    chooseJoinButton.setMaxWidth(Double.MAX_VALUE);

    sidebar.getChildren().addAll(chooseCreateButton, chooseJoinButton);

    // Form container
    VBox formContainer = new VBox(15);
    formContainer.setAlignment(Pos.CENTER);
    formContainer.setPadding(new Insets(30));
    formContainer.getStyleClass().add("household-form");

    // Household label
    householdLabel = new Label("Create or join a new household");
    householdLabel.getStyleClass().add("title");

    // Create Household form
    householdNameField = new TextField();
    householdNameField.setPromptText("Enter Household Name");
    householdNameField.getStyleClass().add("input-field");

    createButton = new Button("Create Household");
    createButton.getStyleClass().add("main-button");

    VBox createContainer = new VBox(15, householdLabel, householdNameField, createButton);
    createContainer.setAlignment(Pos.CENTER);
    createContainer.setPadding(new Insets(40, 60, 40, 60));
    createContainer.setMaxWidth(400);

    // Join Household form
    householdCodeField = new TextField();
    householdCodeField.setPromptText("Enter Household Code");
    householdCodeField.getStyleClass().add("input-field");

    joinButton = new Button("Join Household");
    joinButton.getStyleClass().add("main-button");

    VBox joinContainer = new VBox(10, householdLabel, householdCodeField, joinButton);
    joinContainer.setAlignment(Pos.CENTER_LEFT);
    joinContainer.setPadding(new Insets(40, 60, 40, 60));

    // Leave Household button
    leaveButton = new Button("Leave Household");
    leaveButton.getStyleClass().add("danger-button");
    leaveButton.setDisable(true);

    VBox leaveContainer = new VBox(10, leaveButton);
    leaveContainer.setAlignment(Pos.CENTER_LEFT);

    // Content area (switchable forms)
    StackPane contentPane = new StackPane(createContainer, joinContainer, leaveContainer);
    createContainer.setVisible(false);
    joinContainer.setVisible(false);
    leaveContainer.setVisible(false);

    // Button actions to toggle views
    chooseCreateButton.setOnAction(e -> {
      createContainer.setVisible(true);
      joinContainer.setVisible(false);
      leaveContainer.setVisible(false);
    });

    chooseJoinButton.setOnAction(e -> {
      joinContainer.setVisible(true);
      createContainer.setVisible(false);
      leaveContainer.setVisible(false);
    });

    // Main layout (Sidebar + Content)
    HBox mainLayout = new HBox(sidebar, contentPane);
    mainLayout.setSpacing(0);
    mainLayout.setPadding(new Insets(0));
    mainLayout.setAlignment(Pos.TOP_LEFT);

    // Ensure view is properly set
    view.getChildren().add(mainLayout);
  }

  // Getters for buttons and input fields

  public Button getChooseCreateButton() {
    return chooseCreateButton;
  }

  public Button getChooseJoinButton() {
    return chooseJoinButton;
  }

  public TextField getHouseholdNameField() {
    return householdNameField;
  }
  public Label getHouseholdLabel() {
    return householdLabel;
  }

  public TextField getHouseholdCodeField() {
    return householdCodeField;
  }

  public Button getCreateButton() {
    return createButton;
  }

  public Button getJoinButton() {
    return joinButton;
  }

  public Button getLeaveButton() {
    return leaveButton;

}

  public void showCreateForm() {
    view.getChildren().clear(); // Use view.getChildren() instead of getChildren()

    householdLabel.setText("Create a new Household");

    householdNameField.setPromptText("Enter Household Name");

    VBox createContainer = new VBox(10, householdLabel, householdNameField, createButton);
    createContainer.setAlignment(Pos.CENTER_LEFT);
    createContainer.setPadding(new Insets(40, 60, 40, 60));
    view.getChildren().add(createContainer);
  }

  public void showJoinForm() {
    view.getChildren().clear();
    householdLabel.setText("Join a Household");
    householdCodeField.setPromptText("Enter Household Code");

    VBox joinContainer = new VBox(15, householdLabel, householdCodeField, joinButton);
    joinContainer.setAlignment(Pos.CENTER);
    joinContainer.getStyleClass().add("form-container");

    VBox mainLayout = new VBox(joinContainer);
    mainLayout.setAlignment(Pos.CENTER);
    mainLayout.setPrefHeight(Double.MAX_VALUE);
    mainLayout.setPrefWidth(Double.MAX_VALUE);

    view.getChildren().add(mainLayout);
  }
  public void showHouseholdView(String householdName){
    ((VBox) view).getChildren().clear();
    householdLabel.setText("Household: " + householdName);
    ((VBox) view).getChildren().addAll(householdLabel, leaveButton);
  }
}