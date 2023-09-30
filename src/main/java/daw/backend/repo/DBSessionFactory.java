package daw.backend.repo;

import daw.backend.model.entity.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.reactive.stage.Stage.SessionFactory;

import java.util.Properties;

public class DBSessionFactory
{
    private static SessionFactory sessionFactory;

    public DBSessionFactory()
    {
        ServiceRegistry serviceRegistry = new ReactiveServiceRegistryBuilder()
            .applySettings(getConfig().getProperties())
            .build();

        sessionFactory = getConfig().buildSessionFactory(serviceRegistry)
            .unwrap(SessionFactory.class);

//        ServiceRegistry serviceRegistry = new ReactiveServiceRegistryBuilder()
//            .build();
//
//        sessionFactory = getConfig().buildSessionFactory(serviceRegistry)
//            .unwrap(SessionFactory.class);
    }

    private Configuration getConfig()
    {
        String databaseURL = "jdbc:postgresql://localhost:5432/hibernatedb";

        Properties databaseProperties = new Properties();
        databaseProperties.put("hibernate.connection.url", databaseURL);
        databaseProperties.put("hibernate.connection.username", "user");
        databaseProperties.put("hibernate.connection.password", "secret");
        databaseProperties.put("javax.persistence.schema-generation.database.action", "create");
//        databaseProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        databaseProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        Configuration databaseConfiguration = new Configuration();
        databaseConfiguration.setProperties(databaseProperties);

        databaseConfiguration.addAnnotatedClass(UserEntity.class);
        databaseConfiguration.addAnnotatedClass(AudioDocumentEntity.class);
        databaseConfiguration.addAnnotatedClass(WorkspaceEntity.class);
        databaseConfiguration.addAnnotatedClass(UserAllowedWorkspaceEntity.class);
        databaseConfiguration.addAnnotatedClass(AudioDocumentPermissionEntity.class);

        return databaseConfiguration;
    }

    public SessionFactory getSessionFactory() { return sessionFactory; }
}
