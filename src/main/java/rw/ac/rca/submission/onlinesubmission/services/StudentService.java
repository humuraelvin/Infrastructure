package rw.ac.rca.submission.onlinesubmission.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import rw.ac.rca.submission.onlinesubmission.models.Student;
import rw.ac.rca.submission.onlinesubmission.utils.HibernateUtil;

import java.util.List;

public class StudentService extends UserService {

    public Student registerStudent(String firstName, String lastName, String email, String password, String registrationNumber) {
        if (isEmailTaken(email)) {
            throw new IllegalArgumentException("Email has already been taken");
        }

        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            Student student = new Student(firstName, lastName, email, hashPassword(password), registrationNumber);

            session.persist(session);
            transaction.commit();
            return student;
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }

    }

    public Student getStudentById(Long id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Student.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Student> getAllStudents() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM Student").list();
        }
    }

    public void updateStudent(Student student) {

        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(student);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }

    }

}
