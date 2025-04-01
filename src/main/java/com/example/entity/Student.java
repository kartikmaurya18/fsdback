package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "uid", unique = true, nullable = false)
    private String uid; // Format: S001, S002, etc.

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "gender", nullable = false)
    private String gender;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    private Group group;
    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = true)
    private Mentor mentor;
    // Constructors
    public Student() {}

    public Student(String uid, String name, String email, String phone, String gender, Batch batch, Mentor mentor) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.batch = batch;
        this.mentor = mentor;
    }

    // Getters and Setters
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Batch getBatch() { return batch; }
    public void setBatch(Batch batch) { this.batch = batch; }

    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
    
    public Mentor getMentor() { return mentor; }
    public void setMentor(Mentor mentor) { this.mentor = mentor; }
}



