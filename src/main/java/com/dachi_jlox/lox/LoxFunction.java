package com.dachi_jlox.lox;

import java.util.List;

public class LoxFunction implements LoxCallable {
    private final Stmt.Function declaration;
    private final Environment closure;
    private final boolean isInitializer;
    public LoxFunction(Stmt.Function declaration,  Environment closure,  boolean isInitializer) {
        this.isInitializer = isInitializer;
        this.closure = closure;
        this.declaration = declaration;
    }

    public LoxFunction bind(LoxInstance loxInstance){
        Environment environment = new Environment();
        environment.define("this", loxInstance);
        return new LoxFunction(declaration, environment,  isInitializer);
    }

    @Override
    public int arity() {
        return declaration.parameters.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.parameters.size(); i++){
            environment.define(declaration.parameters.get(i).getLexeme(), arguments.get(i));
        }

        try {
            interpreter.executeBlock(declaration.body, environment);
        }catch (Return returnValue){
            if(isInitializer){
                return closure.getAt(0, "this");
            }

            return returnValue.value;
        }

        if(isInitializer){
            return closure.getAt(0, "this");
        }
        return null;
    }

    @Override
    public String toString() {
        return "<fn " +  declaration.name.getLexeme() + ">";
    }
}
