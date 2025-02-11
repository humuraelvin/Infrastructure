package rw.ac.rca.submission.onlinesubmission.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<Submission> submissions = new ArrayList<>();

    @Column(nullable = false)
    private String courseCode;

    @Column(name = "allowed_file_types", nullable = false)
    private String allowedFileTypes;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }

    public Assignment() {}

    public Assignment(String title, String description, LocalDateTime dueDate, Teacher teacher, String courseCode, String allowedFileTypes) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.teacher = teacher;
        this.courseCode = courseCode;
        this.allowedFileTypes = allowedFileTypes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getAllowedFileTypes() {
        return allowedFileTypes;
    }

    public void setAllowedFileTypes(String allowedFileTypes) {
        this.allowedFileTypes = allowedFileTypes;
    }

}
