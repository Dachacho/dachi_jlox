package com.dachi_jlox.lox;

public class LoxClass {
    final String name;

    public LoxClass(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
