package com.github.thomasdarimont.keycloakx.scheduler.domain;

import com.github.kagkarlsson.scheduler.task.ExecutionContext;
import com.github.kagkarlsson.scheduler.task.TaskInstance;
import com.github.kagkarlsson.scheduler.task.helper.OneTimeTask;

public class SendSmsCommand extends AbstractCommand<SendSmsCommandData> implements Command<SendSmsCommandData> {

    public SendSmsCommand() {
        super(SendSmsCommandData.class);
    }

    @Override
    String getName() {
        return "Send-SMS-Exactly-Once";
    }

    @Override
    public void execute(TaskInstance<SendSmsCommandData> sendMailTaskDataTaskInstance, ExecutionContext executionContext) {

    }
}
