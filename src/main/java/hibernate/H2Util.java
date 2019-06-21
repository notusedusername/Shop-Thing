package hibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import shopthing.controller.util.Popup;
import shopthing.model.Ware;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;


public class H2Util {

    private static Transaction transaction;

    public static void persist(Ware ware) throws PersistenceException {
        transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(ware);
            transaction.commit();
            new Popup("Új sor hozzáadva.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            if (e instanceof PersistenceException) {
                throw e;
            } else {
                new Popup("HIBA:" + e.getStackTrace().toString(), Alert.AlertType.ERROR);
            }
        }
    }

    public static ObservableList<Ware> runQuery(String command) {
        List<Ware> wares = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            if (command == null || command.equals("")) {
                wares = session.createQuery("from Ware ", Ware.class).list();
            } else {
                wares = session.createQuery(command, Ware.class).list();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return FXCollections.observableList(wares);
    }

    public static Integer updateTable(String command) {
        Query query = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            query = session.createQuery(command);
            return query.executeUpdate();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return 0;
    }

}
