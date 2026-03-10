package com.dachi_jlox.lox;

public class BreakSignal extends RuntimeException {
    public BreakSignal() {
        super(null, null, false, false);
    }
}
