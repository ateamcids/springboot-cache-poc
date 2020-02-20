package com.example.minipocservicio1.models;

public class TaskModel {
    public TaskModel(String _id, String title, String description) {
        this._id = _id;
        this.title = title;
        this.description = description;
    }
    public TaskModel() {

    }


    private String _id;
    private String title;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
