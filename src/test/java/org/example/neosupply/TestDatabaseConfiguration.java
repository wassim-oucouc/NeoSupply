package org.example.neosupply.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class TestDatabaseConfiguration {

    /**
     * Override datasource with H2 in-memory database
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    /**
     * Force H2 Dialect and Inline Mutation Strategy
     * This will override ANY configuration from application.properties
     */
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return (hibernateProperties) -> {
            // Force H2 dialect (overrides PostgreSQL)
            hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

            // Force inline mutation strategy for inheritance
            hibernateProperties.put("hibernate.query.mutation_strategy",
                    "org.hibernate.query.sqm.mutation.internal.inline.InlineMutationStrategy");

            // Create-drop for tests
            hibernateProperties.put("hibernate.hbm2ddl.auto", "create-drop");

            // Show SQL for debugging (optional)
            hibernateProperties.put("hibernate.show_sql", "true");
            hibernateProperties.put("hibernate.format_sql", "true");
        };
    }
}