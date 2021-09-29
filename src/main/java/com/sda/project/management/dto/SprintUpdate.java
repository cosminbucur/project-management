package com.sda.project.management.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class SprintUpdate {

    @NotEmpty(message = "name cannot be empty")
    private String name;
    @FutureOrPresent(message = "date must be in present or future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    @FutureOrPresent(message = "date must be in present or future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;
    @Size(max = 50, message = "maximum 50 chars for sprint goal")
    private String sprintGoal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getSprintGoal() {
        return sprintGoal;
    }

    public void setSprintGoal(String sprintGoal) {
        this.sprintGoal = sprintGoal;
    }

    @Override
    public String toString() {
        return "Sprint{" +
                ", name='" + name + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", sprintGoal=" + sprintGoal +
                '}';
    }
}
