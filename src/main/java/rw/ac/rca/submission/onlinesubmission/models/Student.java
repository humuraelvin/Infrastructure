package rw.ac.rca.submission.onlinesubmission.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(nullable = false)
    private String registrationNumber;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Submission> submissions = new ArrayList<>();

    public Student() {
        super();
        setRole("STUDENT");
    }

    public Student(String firstName, String lastName, String email, String password, String registrationNumber) {
        super(firstName, lastName, email, password, "STUDENT");
        this.registrationNumber = registrationNumber;
    }

    // Getters and Setters
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }
}