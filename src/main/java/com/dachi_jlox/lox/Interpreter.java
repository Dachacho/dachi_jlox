package com.dachi_jlox.lox;

public class Interpreter implements Expr.Visitor<Object> {

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        return switch (expr.operator.getType()){
            case TokenType.GREATER -> (double)left > (double)right;
            case TokenType.GREATER_EQUAL -> (double)left >= (double)right;
            case TokenType.LESS -> (double)left < (double)right;
            case TokenType.LESS_EQUAL -> (double)left <= (double)right;
            case TokenType.MINUS -> (double)left - (double)right;
            case TokenType.SLASH ->  (double)left / (double)right;
            case TokenType.STAR -> (double)left * (double)right;
            case TokenType.BANG_EQUAL -> !isEqual(left, right);
            case TokenType.EQUAL_EQUAL -> isEqual(left, right);
            case TokenType.PLUS -> {
                if(left instanceof Double && right instanceof Double) {
                    yield (double) left + (double)right;
                }
                if(left instanceof String && right instanceof String) {
                    yield (String) left + (String) right;
                }
                yield null;
            }
            default -> null;
        };
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.right);

        return switch (expr.operator.getType()) {
            case TokenType.MINUS -> -(double) right;
            case TokenType.BANG -> !isTruthy(right);
            default -> null;
        };
    }

    private boolean isTruthy(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        return obj != null;
    }

    private boolean isEqual(Object left, Object right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null){
            return false;
        }

        return left.equals(right);
    }

    @Override
    public Object visitTernaryExpr(Expr.Ternary expr) {
        Object condition = evaluate(expr.condition);

        if((Boolean) condition){
            return evaluate(expr.thenBranch);
        }else{
            return evaluate(expr.elseBranch);
        }
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }
}
