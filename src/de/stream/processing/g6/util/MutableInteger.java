package de.stream.processing.g6.util;

public class MutableInteger {
    private int value;

    public MutableInteger() {
        this.value = 0;
    }

    public MutableInteger(int initValue) {
        this.value = initValue;
    }

    public void increment () {
        ++value;
    }

    public void set(int value) {
        this.value = value;
    }

    public int  get () {
        return value;
    }
}
