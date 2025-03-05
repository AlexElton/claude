package edu.ntnu.idi.bidata.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Household class
 */

public class Household {
  private String name;
  private List<User> members;
  private List<Task> tasks;

  public Household(String name) {
    this.name = name;
    this.members = new ArrayList<>();
    this.tasks = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void addMember(User user) {
    members.add(user);
  }

  public void addTask(Task task) {
    tasks.add(task);
  }

  public List<Task> getTasks() {
    return tasks;
  }
}
