package com.sda.project.management.dto;

public class ProjectAccessInfo {

    private Long id;
    private Long projectId;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ProjectAccessInfo{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", userId=" + userId +
                '}';
    }
}
