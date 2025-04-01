package com.example.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @Column(name = "group_id", unique = true, nullable = false)
    private String groupId; // Format: G001, G002, etc.

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @Column(name = "project_title", nullable = false)
    private String projectTitle;

    @Column(name = "project_description", nullable = false)
    private String projectDescription;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    // Constructors
    public Group() {}

    public Group(String groupId, Batch batch, String projectTitle, String projectDescription) {
        this.groupId = groupId;
        this.batch = batch;
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
    }

    // Getters and Setters
    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }

    public Batch getBatch() { return batch; }
    public void setBatch(Batch batch) { this.batch = batch; }

    public String getProjectTitle() { return projectTitle; }
    public void setProjectTitle(String projectTitle) { this.projectTitle = projectTitle; }

    public String getProjectDescription() { return projectDescription; }
    public void setProjectDescription(String projectDescription) { this.projectDescription = projectDescription; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
