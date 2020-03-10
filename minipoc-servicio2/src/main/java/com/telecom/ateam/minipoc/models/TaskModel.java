package com.telecom.ateam.minipoc.models;

public class TaskModel {
  public TaskModel(String id, String title, String description) {
    this.id = id;
    this.title = title;
    this.description = description;
  }

  public TaskModel() {}

  private String id;
  private String title;

  public String get_id() {
    return id;
  }

  public void set_id(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  private String description;
}
