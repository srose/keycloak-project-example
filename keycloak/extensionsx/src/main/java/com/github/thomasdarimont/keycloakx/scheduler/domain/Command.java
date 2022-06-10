package com.github.thomasdarimont.keycloakx.scheduler.domain;

import com.github.kagkarlsson.scheduler.task.ExecutionContext;
import com.github.kagkarlsson.scheduler.task.TaskInstance;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;

public interface Command<T> {

    /* create an instance to be scheduled at a specific time, running on a given set of data */
    TaskInstance<T> instantiate(T taskData);

    /* actual behaviour of the command, executed when the instance is picked up */
    void execute(TaskInstance<T> sendMailTaskDataTaskInstance, ExecutionContext executionContext);

    /* to be provided to the framework when instantiating the framework */
    OneTimeTask<T> getTask();

}
