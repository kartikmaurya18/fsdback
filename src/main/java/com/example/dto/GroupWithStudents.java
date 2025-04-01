package com.example.dto;

import java.util.List;
import com.example.entity.Group;

public class GroupWithStudents {
    private Group group;
    private List<String> studentIds;

    // Getters and Setters
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<String> studentIds) {
        this.studentIds = studentIds;
    }
}
