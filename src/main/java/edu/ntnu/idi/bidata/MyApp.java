package edu.ntnu.idi.bidata;

import javafx.application.Application;
import javafx.stage.Stage;
import edu.ntnu.idi.bidata.controllers.MainController;

public class MyApp extends Application {
  @Override
  public void start(Stage primaryStage) {
    new MainController(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}

