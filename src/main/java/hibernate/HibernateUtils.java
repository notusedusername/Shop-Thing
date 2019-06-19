package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

public class HibernateUtils {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                File properties = getPropertyFile();
                if (properties != null) {
                    registry = new StandardServiceRegistryBuilder().configure().loadProperties(properties).build();
                } else {
                    registry = new StandardServiceRegistryBuilder().configure().build();

                }
                MetadataSources sources = new MetadataSources(registry);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static File getPropertyFile() {
        File file = null;
        try {
            file = new File("db.properties");
            if (file.exists()) {
            } else {
                file = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;

    }
}