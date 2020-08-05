package org.infinispan.tutorial.simple.server.tasks;

import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;
import org.infinispan.tasks.TaskExecutionMode;

public class DistTask implements ServerTask<String> {

    private TaskContext ctx = null;

    @Override
    public void setTaskContext(TaskContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String getName() {
        return "dist-greet";
    }

    @Override
    public TaskExecutionMode getExecutionMode() {
        return TaskExecutionMode.ALL_NODES;
    }

    @Override
    public String call() throws Exception {
        String greetee = (String) ctx.getParameters().get().get("greetee");
        return "Hello " + greetee + " from " + ctx.getCacheManager().getCacheManagerInfo().getName();
    }

}
