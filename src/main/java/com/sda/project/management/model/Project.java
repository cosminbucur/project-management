package com.sda.project.management.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User projectLead;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProjectAccess> projectAccessList = new HashSet<>();

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Sprint> sprints = new HashSet<>();

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public void addSprint(Sprint sprint) {
        sprints.add(sprint);
        sprint.setProject(this);
    }

    public void removeSprint(Sprint sprint) {
        sprints.remove(sprint);
        sprint.setProject(null);
    }

    public User getProjectLead() {
        return projectLead;
    }

    public void setProjectLead(User projectLead) {
        this.projectLead = projectLead;
    }

    public Set<ProjectAccess> getProjectAccessList() {
        return projectAccessList;
    }

    public void setProjectAccessList(Set<ProjectAccess> projectAccessList) {
        this.projectAccessList = projectAccessList;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}