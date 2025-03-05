package edu.ntnu.idi.bidata.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;

public class ThemeManager {

  private static final String LIGHT_THEME = "/light.css";
  private static final String DARK_THEME = "/dark.css";

  // Global property to track dark mode
  private static final BooleanProperty darkModeProperty = new SimpleBooleanProperty(false);

  public static BooleanProperty darkModeProperty() {
    return darkModeProperty;
  }

  public static boolean isDarkMode() {
    return darkModeProperty.get();
  }

  public static void setLightTheme(Scene scene) {
    scene.getStylesheets().remove(ThemeManager.class.getResource(DARK_THEME).toExternalForm());
    if (!scene.getStylesheets().contains(ThemeManager.class.getResource(LIGHT_THEME).toExternalForm())) {
      scene.getStylesheets().add(ThemeManager.class.getResource(LIGHT_THEME).toExternalForm());
    }
    darkModeProperty.set(false);
  }

  public static void setDarkTheme(Scene scene) {
    scene.getStylesheets().remove(ThemeManager.class.getResource(LIGHT_THEME).toExternalForm());
    if (!scene.getStylesheets().contains(ThemeManager.class.getResource(DARK_THEME).toExternalForm())) {
      scene.getStylesheets().add(ThemeManager.class.getResource(DARK_THEME).toExternalForm());
    }
    darkModeProperty.set(true);
  }

  public static void toggleTheme(Scene scene) {
    if (isDarkMode()) {
      setLightTheme(scene);
    } else {
      setDarkTheme(scene);
    }
  }
}
