package com.dachi_jlox.lox;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> values = new HashMap<>();

    Object get(Token name) {
        if(values.containsKey(name.getLexeme())){
            return values.get(name.getLexeme());
        }

        throw new RuntimeError(name, "Undefined variable: " + name.getLexeme());
    }

    void define(String name, Object value) {
        values.put(name, value);
    }
}
