package com.sda.project.management.mapper;

import com.sda.project.management.dto.TaskEdit;
import com.sda.project.management.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(Task existingTask, TaskEdit taskData) {
        existingTask.setProject(taskData.getProject());
        existingTask.setTaskType(taskData.getTaskType());
        existingTask.setSummary(taskData.getSummary());
        existingTask.setDescription(taskData.getDescription());
        existingTask.setAssignee(taskData.getAssignee());
        existingTask.setStoryPoints(taskData.getStoryPoints());
        existingTask.setSprint(taskData.getSprint());

        return existingTask;
    }
}
