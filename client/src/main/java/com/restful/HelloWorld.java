package com.restful;

public class HelloWorld {

    private final String name;

    public HelloWorld(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Hello %s", name);
    }
}
