package main;

import model.Ware;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class Main {
    public static void main(String[] args) {

        Ware ware = new Ware(3, 2, "sajt", 5, 1);
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(ware);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Ware> wares = session.createQuery("from Ware ", Ware.class).list();
            wares.forEach(s -> System.out.println(s.getName()));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }
}
