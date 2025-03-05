package edu.ntnu.idi.bidata.controllers;

import javafx.scene.layout.Pane;
import edu.ntnu.idi.bidata.view.ProfileView;

public class ProfileController implements PageController {
  private ProfileView view;

  public ProfileController() {
    view = new ProfileView();
  }

  @Override
  public Pane getView() {
    return view.getView();
  }

}
