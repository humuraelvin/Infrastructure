package rw.ac.rca.submission.onlinesubmission.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Teacher extends User {

    @Column(nullable = false)
    private String department;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Assignment> assignments = new ArrayList<>();

    public Teacher() {
        super();
        setRole("TEACHER");
    }

    public Teacher(String firstName, String lastName, String email, String password, String department) {
        super(firstName, lastName, email, password, "TEACHER");
        this.department = department;
    }

    // Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }
}