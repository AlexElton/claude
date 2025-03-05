package edu.ntnu.idi.bidata.utils;


import edu.ntnu.idi.bidata.controllers.HelpController;
import edu.ntnu.idi.bidata.controllers.HouseholdController;
import edu.ntnu.idi.bidata.controllers.IssueBoardController;
import edu.ntnu.idi.bidata.controllers.LoginRegisterController;
import edu.ntnu.idi.bidata.controllers.PageController;
import edu.ntnu.idi.bidata.controllers.ProfileController;
import edu.ntnu.idi.bidata.controllers.StatisticsController;

public class ControllerFactory {
  public static PageController createController(Page page) {
    return switch (page) {
      case ISSUE_BOARD -> new IssueBoardController();
      case STATISTICS -> new StatisticsController();
      case PROFILE -> new ProfileController();
      case LOGIN_REGISTER -> new LoginRegisterController();
      case HELP -> new HelpController();
      default -> throw new IllegalArgumentException("Unknown page: " + page);
    };
  }
}