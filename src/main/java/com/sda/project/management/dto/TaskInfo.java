package com.sda.project.management.dto;

public class TaskInfo {

    private Long id;
    private String text;
    private Boolean selected;

    public TaskInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", selected=" + selected +
                '}';
    }
}
