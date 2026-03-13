package com.dachi_jlox.lox;

import java.util.HashMap;
import java.util.Map;

public class LoxInstance {
    private LoxClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    public LoxInstance(LoxClass klass) {
        this.klass = klass;
    }

    public Object get(Token name){
        if(fields.containsKey(name.getLexeme())){
            return fields.get(name.getLexeme());
        }

        LoxFunction method = klass.findMethod(name.getLexeme());
        if(method != null){
            return method;
        }

        throw new RuntimeError(name, "Undefined field name " + name.getLexeme() + ".");
    }

    public void set(Token name, Object value){
        fields.put(name.getLexeme(), value);
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }
}
