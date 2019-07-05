package org.sdoaj.eloncraft.blocks.launch.controller;

public enum Destination {
    ISS, MOON, MARS;

    public static Destination fromOrdinal(int ordinal) {
        for (Destination destination : values()) {
            if (destination.ordinal() == ordinal) {
                return destination;
            }
        }

        return null;
    }
}