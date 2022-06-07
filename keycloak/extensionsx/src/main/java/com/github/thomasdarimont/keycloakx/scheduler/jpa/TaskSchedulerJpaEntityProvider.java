package com.github.thomasdarimont.keycloakx.scheduler.jpa;

import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

import java.util.Collections;
import java.util.List;

public class TaskSchedulerJpaEntityProvider implements JpaEntityProvider {

    public static final String ID = "jpa-task-scheduler-entity";

    private static final List<Class<?>> ENTITIES = Collections.singletonList(TaskSchedulerEntity.class);

    @Override
    public List<Class<?>> getEntities() {
        return ENTITIES;
    }

    @Override
    public String getChangelogLocation() {
        return "META-INF/task-scheduler-changelog.xml";
    }

    @Override
    public String getFactoryId() {
        return ID;
    }

    @Override
    public void close() {

    }
}
