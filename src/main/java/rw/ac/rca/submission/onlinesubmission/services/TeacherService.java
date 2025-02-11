package rw.ac.rca.submission.onlinesubmission.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import rw.ac.rca.submission.onlinesubmission.models.Teacher;
import rw.ac.rca.submission.onlinesubmission.utils.HibernateUtil;

import java.util.List;

public class TeacherService extends UserService {

    public Teacher registerTeacher(String firstName, String lastName, String email, String password, String department) {
        if (isEmailTaken(email)){
            throw new IllegalArgumentException("Email has been already taken");
        }

        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            Teacher teacher = new Teacher(firstName, lastName, email, hashPassword(password), department);
            session.persist(teacher);
            transaction.commit();
            return teacher;
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Teacher getTeacherById(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Teacher.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Teacher> getAllTeachers() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM Teacher").list();
        }
    }

    public void updateTeacher(Teacher teacher) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(teacher);
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

}
