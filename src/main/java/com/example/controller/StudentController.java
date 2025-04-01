package com.example.controller;
import com.example.entity.Student;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // Create Student
    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        student.setUid("S" + String.format("%03d", studentRepository.count() + 1)); // Generate UID
        student.setGroup(null); // Default groupId to null
        studentRepository.save(student);
        return ResponseEntity.ok("Student created successfully with UID: " + student.getUid());
    }

    // Update Student by Admin (Cannot update batch & group)
    @PutMapping("/{studentId}")
    public ResponseEntity<String> updateStudentByAdmin(@PathVariable String studentId, @RequestBody Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            student.setPhone(studentDetails.getPhone());
            student.setGender(studentDetails.getGender());
            studentRepository.save(student);
            return ResponseEntity.ok("Student updated successfully");
        }
        return ResponseEntity.badRequest().body("Student not found");
    }

    // Update Student by Student (Cannot update UID, batchId, groupId)
    @PutMapping("/student/{studentId}")
    public ResponseEntity<String> updateStudentByStudent(@PathVariable String studentId, @RequestBody Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            student.setPhone(studentDetails.getPhone());
            student.setGender(studentDetails.getGender());
            studentRepository.save(student);
            return ResponseEntity.ok("Student updated successfully");
        }
        return ResponseEntity.badRequest().body("Student not found");
    }

    // Delete Student
    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable String studentId) {
        if (studentRepository.existsById(studentId)) {
            studentRepository.deleteById(studentId);
            return ResponseEntity.ok("Student deleted successfully");
        }
        return ResponseEntity.badRequest().body("Student not found");
    }

    // Get All Students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get Student by UID
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable String studentId) {
        return studentRepository.findById(studentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}


