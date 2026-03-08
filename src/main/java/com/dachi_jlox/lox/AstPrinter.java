package com.dachi_jlox.lox;

public class AstPrinter implements Expr.Visitor<String>{
    String print(Expr expr){
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.getLexeme(), expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) {
            return "nil";
        }
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.getLexeme(), expr.right);
    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return parenthesize(expr.name.getLexeme(), expr.value);
    }

    @Override
    public String visitTernaryExpr(Expr.Ternary expr) {
        return parenthesize("ternary", expr.condition, expr.thenBranch, expr.elseBranch);
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return expr.name.toString();
    }

    private String parenthesize(String name, Expr... exprs){
        StringBuilder sb = new StringBuilder();

        sb.append("(").append(name);
        for (Expr expr : exprs){
            sb.append(" ");
            sb.append(expr.accept(this));
        }
        sb.append(")");

        return sb.toString();
    }
}
