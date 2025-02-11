package rw.ac.rca.submission.onlinesubmission.services;

import jakarta.servlet.http.Part;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rw.ac.rca.submission.onlinesubmission.models.Assignment;
import rw.ac.rca.submission.onlinesubmission.models.Submission;
import rw.ac.rca.submission.onlinesubmission.models.Student;
import rw.ac.rca.submission.onlinesubmission.models.Teacher;
import rw.ac.rca.submission.onlinesubmission.utils.HibernateUtil;
import rw.ac.rca.submission.onlinesubmission.utils.FileUploadUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class SubmissionService {

    public Submission submitAssignment(Student student, Assignment assignment, Part filePart) throws IOException {

        if (assignment.getDueDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Sorry, Assignment deadline has already passed by!");
        }

        if (hasStudentSubmitted(student.getId(), assignment.getId())){
            throw new IllegalStateException("Woof, Assignment has already been submitted!");
        }

        String filePath = FileUploadUtil.saveFile(filePart, student.getId().toString(), assignment.getId().toString());

        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();


            Submission submission = new Submission(
                    student,
                    assignment,
                    filePath,
                    filePart.getSubmittedFileName(),
                    filePart.getContentType(),
                    filePart.getSize()
            );

            session.persist(submission);
            transaction.commit();
            return submission;
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            FileUploadUtil.deleteFile(filePath);
            throw e;
        }

    }

    public boolean hasStudentSubmitted(Long studentId, Long assignmentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(s) FROM Submission s WHERE s.student.id = :studentId " +
                                    "AND s.assignment.id = :assignmentId", Long.class)
                    .setParameter("studentId", studentId)
                    .setParameter("assignmentId", assignmentId)
                    .getSingleResult();
            return count > 0;
        }
    }

    public Submission getSubmission(Long studentId, Long assignmentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Submission s WHERE s.assignment.id =:assignmetId" +
                    "ORDER BY s.submissionDate DESC", Submission.class)
                    .setParameter("assignmetId", assignmentId)
                    .setParameter("studentId", studentId)
                    .uniqueResult();
        }
    }


    public List<Submission> getSubmissionsByStudent(Long studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Submission s WHERE s.student.id = :studentId " +
                    "ORDER BY s.submissionDate DESC", Submission.class)
                    .setParameter("studentId", studentId)
                    .list();
        }
    }

    public void deleteSubmission(Long id) {

        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            Submission submission = session.get(Submission.class, id);
            if (submission != null) {

                FileUploadUtil.deleteFile(submission.getFilePath());
                session.remove(submission);
            }
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }

    }


}
