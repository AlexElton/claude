package edu.ntnu.idi.bidata.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class BaseView {
  protected Pane view; // The root layout of the view

  protected BaseView() {
    this.view = new VBox(10);
    createView();
    if (this instanceof HouseholdView) {
      view.getStyleClass().add("household-container"); // Legger til CSS-klassen KUN for Household-sidene
    }
  }

  protected abstract void createView();

  public Pane getView() {
    return view;
  }
}
