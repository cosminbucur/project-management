package com.sda.project.management.dto;

import com.sda.project.management.model.Project;
import com.sda.project.management.model.Sprint;
import com.sda.project.management.model.TaskStatus;
import com.sda.project.management.model.TaskType;
import com.sda.project.management.model.User;

public class TaskUpdate {

    private String summary;
    private String description;
    private Project project;
    private Sprint sprint;
    private User assignee;
    private TaskType taskType;
    private Integer storyPoints;
    private TaskStatus status;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Integer getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(Integer storyPoints) {
        this.storyPoints = storyPoints;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskUpdate{" +
                "summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", project=" + project +
                ", sprint=" + sprint +
                ", assignee=" + assignee +
                ", taskType=" + taskType +
                ", storyPoints=" + storyPoints +
                ", status=" + status +
                '}';
    }
}
