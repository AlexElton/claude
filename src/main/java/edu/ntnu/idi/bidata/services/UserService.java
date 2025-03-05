package edu.ntnu.idi.bidata.services;

import edu.ntnu.idi.bidata.dao.UserDAO;
import edu.ntnu.idi.bidata.data.User;

public class UserService {

  private final UserDAO userDAO;

  public UserService(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  public User login(String email, String password) {
    return userDAO.loginUser(email, password);
  }

  public User register(String fullName, String email, String password) {
    User user = new User();
    user.setFullName(fullName);
    user.setEmail(email);
    user.setPassword(password);
    return userDAO.addUser(user);
  }
}
