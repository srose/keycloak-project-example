package com.github.thomasdarimont.keycloakx.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.serializer.JacksonSerializer;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import com.github.thomasdarimont.keycloakx.scheduler.data.CommandData;
import com.google.auto.service.AutoService;
import org.keycloak.Config;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import javax.enterprise.inject.spi.CDI;
import javax.sql.DataSource;
import java.time.Instant;
import java.util.UUID;

@AutoService(EventListenerProviderFactory.class)
public class SchedulerEventListenerProviderFactory implements EventListenerProviderFactory, EventListenerProvider {

    private static final String ID = "acme-scheduler-event-listener";

    private Scheduler scheduler;

    private OneTimeTask<CommandData> myAdhocTask;

    private SchedulerExecutionHandler handler;

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        String id = UUID.randomUUID().toString();

        var taskData = new CommandData();
        taskData.realmId = session.getContext().getRealm().getId();

        scheduler.schedule(myAdhocTask.instance(id, taskData), Instant.now().plusSeconds(3));

        return this;
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory sessionFactory) {
        var dataSource = extractDataSource();

        myAdhocTask = Tasks.oneTime("Send-Mail-Exactly-Once", CommandData.class)
                .execute(this.handler::sendMail);

        this.scheduler = Scheduler
                .create(dataSource, myAdhocTask)
                .tableName("custom_scheduled_tasks")
                .serializer(new JacksonSerializer(new ObjectMapper()))
                .registerShutdownHook()
                .build();

        scheduler.start();

        this.handler = new SchedulerExecutionHandler(sessionFactory);
    }

    private DataSource extractDataSource() {
        return CDI.current().select(DataSource.class).get();
    }

    @Override
    public void close() {
        //TODO close dataSource?
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }
}
