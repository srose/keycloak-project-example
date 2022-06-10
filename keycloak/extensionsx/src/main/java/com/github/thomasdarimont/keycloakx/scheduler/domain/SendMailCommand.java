package com.github.thomasdarimont.keycloakx.scheduler.domain;

import com.github.kagkarlsson.scheduler.task.ExecutionContext;
import com.github.kagkarlsson.scheduler.task.TaskInstance;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SendMailCommand extends AbstractCommand<SendMailCommandData> implements Command<SendMailCommandData> {

    public static final String NAME = "Send-Mail-Exactly-Once";

    private final KeycloakSessionFactory sessionFactory;

    public SendMailCommand(KeycloakSessionFactory sessionFactory) {
        super(SendMailCommandData.class);
        this.sessionFactory = sessionFactory;
    }

    @Override
    String getName() {
        return NAME;
    }

    @Override
    public void execute(TaskInstance<SendMailCommandData> sendMailTaskDataTaskInstance, ExecutionContext executionContext) {
        KeycloakSession keycloakSession = this.sessionFactory.create();
        try {

            System.out.println("Send Mail Executed! Custom data, RealmId: " + sendMailTaskDataTaskInstance.getData().realmId);

        } finally {
            keycloakSession.close();
        }

    }

}
