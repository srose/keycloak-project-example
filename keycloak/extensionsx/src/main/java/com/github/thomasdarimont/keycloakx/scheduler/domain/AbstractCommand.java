package com.github.thomasdarimont.keycloakx.scheduler.domain;

import com.github.kagkarlsson.scheduler.task.TaskInstance;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;

import java.util.UUID;

abstract class AbstractCommand<T> implements Command<T> {

    protected final OneTimeTask<T> task;

    private final Class<T> clazz;

    protected AbstractCommand(Class<T> clazz) {
        this.clazz = clazz;
        task = Tasks.oneTime(getName(), clazz)
                .execute(this::execute);
    }

    abstract String getName();

    protected String getNextInstanceIdentifier() {
        return UUID.randomUUID().toString();
    }

    public TaskInstance<T> instantiate(T taskData) {
        return this.task.instance(getNextInstanceIdentifier(), taskData);
    }

    public OneTimeTask<T> getTask() {
        return task;
    }
}
