package edu.ntnu.idi.bidata.session;

import edu.ntnu.idi.bidata.data.User;

public class UserSession {

  private static UserSession instance;
  private User loggedInUser;

  private UserSession() {
    // Private constructor to prevent external instantiation
  }

  public static synchronized UserSession getInstance() {
    if (instance == null) {
      instance = new UserSession();
    }
    return instance;
  }

  public User getLoggedInUser() {
    return loggedInUser;
  }

  public void setLoggedInUser(User user) {
    this.loggedInUser = user;
  }

  public void logoutUser() {
    this.loggedInUser = null;
  }

  public boolean isLoggedIn() {
    return loggedInUser != null;
  }
}
