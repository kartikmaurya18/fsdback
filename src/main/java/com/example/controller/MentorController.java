package com.example.controller;

import com.example.entity.Mentor;
import com.example.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    @Autowired
    private MentorRepository mentorRepository;

    // Create a new mentor
    @PostMapping
    public ResponseEntity<Mentor> createMentor(@RequestBody Mentor mentor) {
        Mentor savedMentor = mentorRepository.save(mentor);
        return new ResponseEntity<>(savedMentor, HttpStatus.CREATED);
    }

    // Get all mentors
    @GetMapping
    public ResponseEntity<List<Mentor>> getAllMentors() {
        List<Mentor> mentors = mentorRepository.findAll();
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }

    // Get a specific mentor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Mentor> getMentorById(@PathVariable("id") Long id) {
        Optional<Mentor> mentor = mentorRepository.findById(id);
        return mentor.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update mentor details
    @PutMapping("/{id}")
    public ResponseEntity<Mentor> updateMentor(@PathVariable("id") Long id, @RequestBody Mentor mentorDetails) {
        Optional<Mentor> existingMentor = mentorRepository.findById(id);
        if (existingMentor.isPresent()) {
            Mentor mentor = existingMentor.get();
            mentor.setName(mentorDetails.getName());
            mentor.setContact(mentorDetails.getContact());
            mentor.setEmail(mentorDetails.getEmail());
            mentor.setGender(mentorDetails.getGender());
            mentor.setBatch(mentorDetails.getBatch());
            mentor.setDepartment(mentorDetails.getDepartment());
            mentor.setSpecialization(mentorDetails.getSpecialization());

            Mentor updatedMentor = mentorRepository.save(mentor);
            return new ResponseEntity<>(updatedMentor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a mentor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentor(@PathVariable("id") Long id) {
        Optional<Mentor> mentor = mentorRepository.findById(id);
        if (mentor.isPresent()) {
            mentorRepository.delete(mentor.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
