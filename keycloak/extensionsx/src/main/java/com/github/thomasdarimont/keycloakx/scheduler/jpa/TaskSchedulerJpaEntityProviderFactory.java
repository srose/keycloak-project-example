package com.github.thomasdarimont.keycloakx.scheduler.jpa;

import com.google.auto.service.AutoService;
import org.keycloak.Config;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

@AutoService(JpaEntityProviderFactory.class)
public class TaskSchedulerJpaEntityProviderFactory implements JpaEntityProviderFactory {

    private final TaskSchedulerJpaEntityProvider INSTANCE = new TaskSchedulerJpaEntityProvider();

    @Override
    public JpaEntityProvider create(KeycloakSession keycloakSession) {
        return INSTANCE;
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return TaskSchedulerJpaEntityProvider.ID;
    }
}
