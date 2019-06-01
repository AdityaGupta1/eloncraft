package org.sdoaj.entity;

import java.util.function.DoubleConsumer;

public class TimedTask {
    private static final double dt = 1.0 / 20.0; // seconds

    private final double to;
    private double current;
    private final double speed; // distance per dt
    private double timeLeft;
    private final DoubleConsumer consumer;

    public TimedTask(double from, double to, double time, DoubleConsumer consumer) {
        this.current = from;
        this.to = to;
        this.timeLeft = time;
        this.speed = (to - from) / time * dt;
        this.consumer = consumer;
    }

    public TimedTask(double from, double current, double to, double time, DoubleConsumer consumer) {
        this(current, to, (to - current) / (to - from) * time, consumer);
    }

    void update() {
        if (timeLeft < dt) {
            timeLeft = 0;
            current = to;
        } else {
            timeLeft -= dt;
            current += speed;
        }

        consumer.accept(current);
    }

    public double getTarget() {
        return to;
    }

    public double getTimeLeft() {
        return timeLeft;
    }

    public boolean isDone() {
        return timeLeft == 0;
    }

    @Override
    public String toString() {
        return String.format("current: %s, to: %s, speed: %s, time left: %s", current, to, speed, timeLeft);
    }
}
