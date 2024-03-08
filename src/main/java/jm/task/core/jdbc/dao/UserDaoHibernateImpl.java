package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT not null auto_increment," +
                    "name VARCHAR(50) not null," +
                    "lastName VARCHAR(50) not null," +
                    "age TINYINT not null," +
                    "primary key (id))", User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users", User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try(Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            try{
                session.persist(new User(name, lastName, age));
                transaction.commit();
            } catch (Exception e) {
                if(transaction != null){
                    transaction.rollback();
                }
                e.printStackTrace();
            }

        }

    }

    @Override
    public void removeUserById(long id) {

        try(Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            User user = session.find(User.class, id);

            if (user != null){
                session.remove(user);
            }

            transaction.commit();

        }

    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            try{
                List<User> users = session.createQuery("SELECT u FROM User u",User.class).getResultList();
                transaction.commit();
                return users;
            } catch (Exception e) {
                if(transaction != null){
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User",null).executeUpdate();
            transaction.commit();
        }

    }


}
