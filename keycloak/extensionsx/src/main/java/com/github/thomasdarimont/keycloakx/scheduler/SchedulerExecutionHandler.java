package com.github.thomasdarimont.keycloakx.scheduler;

import com.github.kagkarlsson.scheduler.task.ExecutionContext;
import com.github.kagkarlsson.scheduler.task.TaskInstance;
import com.github.thomasdarimont.keycloakx.scheduler.data.CommandData;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SchedulerExecutionHandler {

    private final KeycloakSessionFactory sessionFactory;

    public SchedulerExecutionHandler(KeycloakSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void sendMail(TaskInstance<CommandData> sendMailTaskDataTaskInstance, ExecutionContext executionContext) {
        KeycloakSession keycloakSession = this.sessionFactory.create();
        try {

            System.out.println("Executed! Custom data, RealmId: " + sendMailTaskDataTaskInstance.getData().realmId);

        } finally {
            keycloakSession.close();
        }

    }
}
