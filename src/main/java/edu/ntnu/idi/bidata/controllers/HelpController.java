package edu.ntnu.idi.bidata.controllers;

import edu.ntnu.idi.bidata.view.HelpView;
import edu.ntnu.idi.bidata.view.HelpView;
import edu.ntnu.idi.bidata.view.HouseholdView;
import javafx.scene.layout.Pane;

public class HelpController implements PageController {
  private HelpView view;

  public HelpController() {view = new HelpView();
  }

  @Override
  public Pane getView() {
    return view.getView();
  }
}
