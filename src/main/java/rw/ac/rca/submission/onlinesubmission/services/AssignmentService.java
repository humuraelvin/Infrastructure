package rw.ac.rca.submission.onlinesubmission.services;

import org.hibernate.Session;
import org.hibernate.Transaction;

import rw.ac.rca.submission.onlinesubmission.utils.FileUploadUtil;
import rw.ac.rca.submission.onlinesubmission.utils.HibernateUtil;
import rw.ac.rca.submission.onlinesubmission.models.Assignment;
import rw.ac.rca.submission.onlinesubmission.models.Teacher;

import java.time.LocalDateTime;
import java.util.List;

public class AssignmentService {

    public Assignment createAssignment(String title, String description, LocalDateTime dueDate, Teacher teacher, String courseCode, String allowedFileTypes) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Assignment assignment = new Assignment(title, description, dueDate, teacher, courseCode, allowedFileTypes);

            session.persist(assignment);
            transaction.commit();
            return assignment;
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Assignment getAssignmentById(Long id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
           return session.get(Assignment.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Assignment> getAllAssignments() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Assignment").list();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Assignment> getAssignmentsByTeacher(Long teacherID) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM Assignment WHERE teacher.id = :teacherID ORDER BY createdAt DESC")
                    .setParameter("teacherID", teacherID)
                    .list();
        }
    }

    public void updateAssignment(Assignment assignment) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(assignment);
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public void deleteAssignment(Long id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Assignment assignment = session.get(Assignment.class, id);

            if (assignment != null) {

                assignment.getSubmissions().forEach(submission -> {
                    FileUploadUtil.deleteFile(submission.getFilePath());
                    session.remove(submission);
                });
                session.remove(assignment);
            }
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public List<Assignment> getActiveAssignments() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Assignment a WHERE a.dueDate > :now ORDER BY a.dueDate DESC",
                    Assignment.class)
                    .setParameter("now", LocalDateTime.now())
                    .list();
        }
    }

    public boolean isAssignmentOverdue(Assignment assignment) {
        return assignment.getDueDate().isBefore(LocalDateTime.now());
    }

}
