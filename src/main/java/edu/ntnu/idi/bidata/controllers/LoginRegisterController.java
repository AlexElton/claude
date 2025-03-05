package edu.ntnu.idi.bidata.controllers;

import edu.ntnu.idi.bidata.dao.DBConnectionProvider;
import edu.ntnu.idi.bidata.dao.UserDAO;
import edu.ntnu.idi.bidata.data.User;
import edu.ntnu.idi.bidata.services.UserService;
import edu.ntnu.idi.bidata.session.UserSession;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import edu.ntnu.idi.bidata.view.LoginRegisterView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginRegisterController implements PageController {
  private final LoginRegisterView view;
  private final UserService userService;

  public LoginRegisterController() {
    view = new LoginRegisterView();
    DBConnectionProvider connectionProvider = DBConnectionProvider.instance();
    UserDAO userDAO = new UserDAO(connectionProvider);
    userService = new UserService(userDAO);
    setupButtons();
  }

  @Override
  public Pane getView() {
    return view.getView();
  }

  private void setupButtons() {
    Button registerButton = view.getRegisterFormButton();
    registerButton.setOnAction(this::handleRegister);

    Button loginButton = view.getLoginFormButton();
    loginButton.setOnAction(this::handleLogin);
  }

  private void handleLogin(ActionEvent event) {
    String email = view.getLoginEmailField().getText().trim();
    String password = view.getLoginPasswordField().getText();

    if (email.isEmpty() || password.isEmpty()) {
      showAlert(Alert.AlertType.ERROR, "Login Error", "Please fill in all fields.");
      return;
    }

    User loggedInUser = userService.login(email, password);

    if (loggedInUser != null) {
      UserSession.getInstance().setLoggedInUser(loggedInUser);
      showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + loggedInUser.getFullName() + "!");
      clearLoginFields();
      // Proceed with logged-in user
    } else {
      showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect email or password.");
    }
  }

  private void handleRegister(ActionEvent event) {
    String fullName = view.getRegisterFullNameField().getText().trim();
    String email = view.getRegisterEmailField().getText().trim();
    String password = view.getRegisterPasswordField().getText();
    String confirmPassword = view.getRegisterConfirmField().getText();

    if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
      showAlert(Alert.AlertType.ERROR, "Registration Error", "Please fill in all fields.");
      return;
    }

    if (!email.contains("@")) {
      showAlert(Alert.AlertType.ERROR, "Registration Error", "Invalid email address.");
      return;
    }

    if (!password.equals(confirmPassword)) {
      showAlert(Alert.AlertType.ERROR, "Registration Error", "Passwords do not match.");
      return;
    }

    User registeredUser = userService.register(fullName, email, password);

    if (registeredUser != null && registeredUser.getUserId() > 0) {
      showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully!");
      clearRegisterFields();
      view.getLoginToggleButton().fire();
    } else {
      showAlert(Alert.AlertType.ERROR, "Registration Failed", "User could not be registered. Email might already be in use.");
    }
  }

  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void clearLoginFields() {
    view.getLoginEmailField().clear();
    view.getLoginPasswordField().clear();
  }

  private void clearRegisterFields() {
    view.getRegisterFullNameField().clear();
    view.getRegisterEmailField().clear();
    view.getRegisterPasswordField().clear();
    view.getRegisterConfirmField().clear();
  }
}
