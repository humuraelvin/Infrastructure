package rw.ac.rca.submission.onlinesubmission.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import rw.ac.rca.submission.onlinesubmission.models.*;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();

            // Database connection settings
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/online_submission_db?createDatabaseIfNotExist=true");
            settings.put(Environment.USER, "java_user");
            settings.put(Environment.PASS, "eljava");

            // Hibernate properties
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.FORMAT_SQL, "true");
            settings.put(Environment.HBM2DDL_AUTO, "update");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            configuration.setProperties(settings);

            // Add all entities here
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Teacher.class);
            configuration.addAnnotatedClass(Student.class);
            configuration.addAnnotatedClass(Assignment.class);
            configuration.addAnnotatedClass(Submission.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}