package org.sdoaj.eloncraft.entity;

import java.util.ArrayList;
import java.util.List;

public class TimedTaskExecutor {
    private final List<TimedTask> tasks = new ArrayList<>();

    public void submit(TimedTask task) {
        tasks.add(task);
    }

    public void update() {
        tasks.forEach(TimedTask::update);
        tasks.removeIf(TimedTask::isDone);
    }

    public void clear() {
        tasks.clear();
    }

    public List<TimedTask> getTasks() {
        return new ArrayList<>(tasks);
    }
}
