package com.github.thomasdarimont.keycloakx.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.serializer.JacksonSerializer;
import com.github.kagkarlsson.scheduler.task.Task;
import com.github.thomasdarimont.keycloakx.scheduler.domain.Command;
import com.github.thomasdarimont.keycloakx.scheduler.domain.SendMailCommand;
import com.github.thomasdarimont.keycloakx.scheduler.domain.SendMailCommandData;
import com.github.thomasdarimont.keycloakx.scheduler.domain.SendSmsCommand;
import com.github.thomasdarimont.keycloakx.scheduler.domain.SendSmsCommandData;
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
import java.util.List;

@AutoService(EventListenerProviderFactory.class)
public class SchedulerEventListenerProviderFactory implements EventListenerProviderFactory, EventListenerProvider {

    private static final String ID = "acme-scheduler-event-listener";

    private Scheduler scheduler;

    private Command sendMailCommand;
    private Command sendSmsCommand;

    @Override
    public EventListenerProvider create(KeycloakSession session) {

        var atTime = Instant.now().plusSeconds(3);

        {
            var malData = new SendMailCommandData();
            malData.realmId = session.getContext().getRealm().getId();
            var oneMailCommandInstance = this.sendMailCommand.instantiate(malData);
            scheduler.schedule(oneMailCommandInstance, atTime);
        }

        {
            var smsData = new SendSmsCommandData();
            smsData.realmId = session.getContext().getRealm().getId();
            var oneSmsCommandInstance = this.sendSmsCommand.instantiate(smsData);
            scheduler.schedule(oneSmsCommandInstance, atTime);
        }

        return this;
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory sessionFactory) {
        var dataSource = extractDataSource();

        this.sendMailCommand = new SendMailCommand(sessionFactory);
        this.sendSmsCommand = new SendSmsCommand();

        List<Task<?>> allTasks = List.of(
                this.sendMailCommand.getTask(),
                this.sendSmsCommand.getTask()
        );

        this.scheduler = Scheduler
                .create(dataSource, allTasks)
                .tableName("custom_scheduled_tasks")
                .serializer(new JacksonSerializer(new ObjectMapper()))
                .registerShutdownHook()
                .build();

        scheduler.start();
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
