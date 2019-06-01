package org.sdoaj.entity;

import java.util.function.DoubleConsumer;

public class TimedTask {
    private static final double dt = 1.0 / 20.0; // seconds

    private final double to;
    private double current;
    private final double speed; // distance per dt
    private double timeLeft;
    private final DoubleConsumer consumer;

    TimedTask(double from, double to, double time, DoubleConsumer consumer) {
        this.current = from;
        this.to = to;
        this.timeLeft = time;
        this.speed = (to - from) / time * dt;
        this.consumer = consumer;
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

    public double getTimeLeft() {
        return timeLeft;
    }

    public boolean isDone() {
        return timeLeft == 0;
    }
}
