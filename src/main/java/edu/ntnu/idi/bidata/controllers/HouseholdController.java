package edu.ntnu.idi.bidata.controllers;

import edu.ntnu.idi.bidata.view.HouseholdView;
import edu.ntnu.idi.bidata.models.Household;

import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.VBox;

/**
 * Controller that manages operations for the household such as
 * creating, joining, leaving household
 *
 * The controller interacts with {@link edu.ntnu.idi.bidata.view.HouseholdView} to update the User Interface
 * and manages one household per user.
 */


public class HouseholdController implements PageController {

  private HouseholdView view;
  private Household household = null; //We will create only one household per user
  private static Map<String, Household> householdDatabase = new HashMap<>();

  public HouseholdController() {
    view = new HouseholdView();
    initializeUIActions();
    updateView();
  }

  @Override
  public Pane getView() {
    return view.getView();
  }

  private void initializeUIActions() {
    view.getChooseCreateButton().setOnAction(event -> view.showCreateForm());
    view.getChooseJoinButton().setOnAction(event -> view.showJoinForm());
    view.getCreateButton().setOnAction(event -> handleCreateHousehold());
    view.getJoinButton().setOnAction(event -> handleJoinHousehold());
    view.getLeaveButton().setOnAction(event -> handleLeaveHousehold());
  }

  private void handleCreateHousehold() {
    if (household == null) {
      String name = view.getHouseholdNameField().getText().trim();
      if (!name.isEmpty()) {
        String householdCode = "HH-" + (householdDatabase.size() + 1);
        household = new Household(name);
        householdDatabase.put(householdCode, household);

        showAlert("Success", "Household '" + name + "' created. Your code: " + householdCode);
        view.getLeaveButton().setDisable(false);
        updateView();
      } else {
        showAlert("Error", "Please enter a valid household name. ");
      }
    } else {
      showAlert("Error", "You already have a household. ");
    }
  }

  private void handleJoinHousehold() {
    if (household == null) {
      String code = view.getHouseholdCodeField().getText().trim();
      if (!code.isEmpty() && householdDatabase.containsKey(code)) {
        household = householdDatabase.get(code);
        showAlert("Success", "You have joined the household: " + household.getName());
        view.getLeaveButton().setDisable(false);
        updateView();
      } else {
        showAlert("Error", "Invalid household code. ");
      }
    } else {
      showAlert("Error", "You are already in a household.");
    }
  }

  private void handleLeaveHousehold() {
    if (household != null) {
      showAlert("Info", "You have left your household: " + household.getName());
      household = null;
      updateView();
    }
  }

  private void updateView() {
    ((VBox) view.getView()).getChildren().clear();

    if (household != null) {
      view.showHouseholdView(household.getName());
    } else {
      ((VBox) view.getView()).getChildren().addAll(
          view.getHouseholdLabel(),
          view.getChooseCreateButton(),
          view.getChooseJoinButton()
      );
    }
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.showAndWait();
  }
}