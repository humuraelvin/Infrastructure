package rw.ac.rca.submission.onlinesubmission.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import rw.ac.rca.submission.onlinesubmission.models.User;
import rw.ac.rca.submission.onlinesubmission.utils.HibernateUtil;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class UserService {

    public User authenticate(String email, String password) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);

            query.select(root).where(cb.equal(root.get("email"), email));

            User user = session.createQuery(query).getSingleResult();

            if (user!=null && BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        }catch (NoResultException e){
            return null;
        }
        return null;
    }

    public boolean isEmailTaken(String email) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<User> root = query.from(User.class);

            query.select(cb.count(root)).where(cb.equal(root.get("email"), email));

            return session.createQuery(query).getSingleResult() > 0;
        }
    }

    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public User getUserById(Long id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(User.class, id);
        }
    }

    public void updateUser(User user) {
        Transaction transaction = null;
        try(Session sesssion = HibernateUtil.getSessionFactory().openSession()){
            transaction = sesssion.beginTransaction();
            sesssion.merge(user);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

}
