package edu.ntnu.idi.bidata.controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import edu.ntnu.idi.bidata.view.StatisticsView;

public class StatisticsController implements PageController {
  private StatisticsView view;
  private List<UserData> usersData;

  public StatisticsController() {
    view = new StatisticsView();
    initializeSampleData();
    refreshCards();
  }

  @Override
  public Pane getView() {
    return view.getView();
  }

  // Data model class
  private static class UserData {
    private String name;
    private int taskCount;
    private int completedCount;
    private int remainingCount;
    private double taskGrowth;
    private double completedGrowth;
    private double remainingGrowth;
    private String profileImagePath;

    public UserData(String name, int taskCount, int completedCount, int remainingCount,
        double taskGrowth, double completedGrowth, double remainingGrowth,
        String profileImagePath) {
      this.name = name;
      this.taskCount = taskCount;
      this.completedCount = completedCount;
      this.remainingCount = remainingCount;
      this.taskGrowth = taskGrowth;
      this.completedGrowth = completedGrowth;
      this.remainingGrowth = remainingGrowth;
      this.profileImagePath = profileImagePath;
    }
  }

  // Initialize sample data
  private void initializeSampleData() {
    usersData = new ArrayList<>();
    usersData.add(new UserData("Benjamin", 19, 16, 3, 10.2, 3.1, -0.91, "benjamin.jpg"));
    usersData.add(new UserData("Alex", 19, 16, 3, 10.2, 3.1, -0.91, "alex.jpg"));
    usersData.add(new UserData("Sarah", 19, 16, 3, 10.2, 3.1, -0.91, "sarah.jpg"));
  }

  // Refresh cards based on current data
  private void refreshCards() {
    view.clearCards();

    for (UserData userData : usersData) {
      view.addUserCard(
          String.valueOf(userData.taskCount), "Total tasks",
          String.valueOf(userData.completedCount), "Total done",
          String.valueOf(userData.remainingCount), "Total remaining",
          userData.profileImagePath
      );
    }

    updateHouseholdSummary();
  }

  // Update household summary
  private void updateHouseholdSummary() {
    int totalTasks = 0;
    int totalCompleted = 0;
    int totalRemaining = 0;

    for (UserData userData : usersData) {
      totalTasks += userData.taskCount;
      totalCompleted += userData.completedCount;
      totalRemaining += userData.remainingCount;
    }

    view.setSummaryCard(
        String.format("%,d", totalTasks),
        String.format("%,.1f", (double) totalCompleted),
        String.format("%,d", totalRemaining)
    );
  }
}