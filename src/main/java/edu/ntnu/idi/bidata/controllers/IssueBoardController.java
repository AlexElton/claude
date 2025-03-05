package edu.ntnu.idi.bidata.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import edu.ntnu.idi.bidata.view.IssueBoardView;

public class IssueBoardController implements PageController {
  private IssueBoardView view;

  public IssueBoardController() {
    view = new IssueBoardView();
    setupEventListeners();
  }

  @Override
  public Pane getView() {
    return view.getView();
  }

  private void setupEventListeners() {
    // Add event listener to the "Add new task" button
    view.getAddTaskButton().setOnAction(event -> view.showAddTaskDialog());
  }


}