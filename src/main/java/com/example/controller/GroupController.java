package com.example.controller;

import com.example.entity.Batch;
import com.example.entity.Group;
import com.example.entity.Student;
import com.example.repository.BatchRepository;
import com.example.repository.GroupRepository;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Create a new group
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group, @RequestParam List<String> studentIds) {
        Optional<Batch> batchOpt = batchRepository.findById(group.getBatch().getBatchId());

        if (batchOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        group.setBatch(batchOpt.get());
        Group savedGroup = groupRepository.save(group);

        // Update student entities with this group ID
        List<Student> students = studentRepository.findAllById(studentIds);
        for (Student student : students) {
            student.setGroup(savedGroup);
            studentRepository.save(student);
        }

        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    // Delete a group
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable String groupId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);

        if (groupOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Group group = groupOpt.get();

        // Set group ID to null for students before deletion
        List<Student> students = studentRepository.findAllById(
                group.getStudents().stream().map(Student::getUid).toList()
        );

        for (Student student : students) {
            student.setGroup(null);
            studentRepository.save(student);
        }

        groupRepository.delete(group);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Update project title and description
    @PutMapping("/{groupId}")
    public ResponseEntity<Group> updateGroup(@PathVariable String groupId, @RequestBody Group groupDetails) {
        Optional<Group> existingGroupOpt = groupRepository.findById(groupId);

        if (existingGroupOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Group existingGroup = existingGroupOpt.get();
        existingGroup.setProjectTitle(groupDetails.getProjectTitle());
        existingGroup.setProjectDescription(groupDetails.getProjectDescription());

        Group updatedGroup = groupRepository.save(existingGroup);
        return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
    }

    // Get all groups with student details
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    // Get a specific group with student details
    @GetMapping("/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable String groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        return group.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
