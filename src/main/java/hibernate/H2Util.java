package hibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shopthing.model.Ware;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;


public class H2Util {

    private static Transaction transaction;

    public static void persist(Ware ware) {
        transaction = null;
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
    }

    public static ObservableList<Ware> selectAllRecord(String command) {
        List<Ware> wares = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            if (command == null || command.equals("")) {
                wares = session.createQuery("from Ware ", Ware.class).list();
            } else {
                session.createQuery(command).list();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return FXCollections.observableList(wares);
    }
}
