package org.sdoaj.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;
import java.util.stream.Collectors;

public class TimedTaskExecutor {
    private final List<TimedTask> tasks = new ArrayList<>();

    public void submit(double from, double to, double time, DoubleConsumer consumer) {
        tasks.add(new TimedTask(from, to, time, consumer));
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
