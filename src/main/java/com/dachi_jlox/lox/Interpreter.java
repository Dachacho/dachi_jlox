package com.dachi_jlox.lox;

public class Interpreter implements Expr.Visitor<Object> {
    @Override
    public Object visitTernaryExpr(Expr.Ternary expr) {
        Object condition = evaluate(expr.condition);

        if((Boolean) condition){
            return evaluate(expr.thenBranch);
        }else{
            return evaluate(expr.elseBranch);
        }
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        return switch (expr.operator.getType()){
            case TokenType.GREATER -> {
                if (left instanceof String && right instanceof String) {
                    yield ((String) left).compareTo((String) right) > 0;
                }
                checkNumberOperands(expr.operator, left, right);
                yield (double)left > (double)right;
            }
            case TokenType.GREATER_EQUAL -> {
                if (left instanceof String && right instanceof String) {
                    yield ((String) left).compareTo((String) right) >= 0;
                }
                checkNumberOperands(expr.operator, left, right);
                yield (double)left >= (double)right;
            }
            case TokenType.LESS -> {
                if (left instanceof String && right instanceof String) {
                    yield ((String) left).compareTo((String) right) < 0;
                }
                checkNumberOperands(expr.operator, left, right);
                yield (double)left < (double)right;
            }
            case TokenType.LESS_EQUAL -> {
                if (left instanceof String && right instanceof String) {
                    yield ((String) left).compareTo((String) right) < 0;
                }
                checkNumberOperands(expr.operator, left, right);
                yield (double)left <= (double)right;
            }
            case TokenType.MINUS -> {
                checkNumberOperands(expr.operator, left, right);
                yield (double)left - (double)right;
            }
            case TokenType.SLASH ->  {
                checkNumberOperands(expr.operator, left, right);
                if((double) right == 0.0){
                    throw new RuntimeError(expr.operator, "Cannot divide by zero.");
                }
                yield (double)left / (double)right;
            }
            case TokenType.STAR -> {
                if (left instanceof String && right instanceof Double) {
                    yield ((String) left).repeat((int) ((Double) right).intValue());
                }
                if (left instanceof Double && right instanceof String) {
                    yield ((String) right).repeat((int) ((Double) left).intValue());
                }
                checkNumberOperands(expr.operator, left, right);
                yield (double)left * (double)right;
            }
            case TokenType.PLUS -> {
                //stringify coming in clutch
                if(left instanceof Double && right instanceof String) {
                    yield stringify(left) + right;
                }
                if(left instanceof String && right instanceof Double) {
                    yield left + stringify(right);
                }
                if(left instanceof Double && right instanceof Double) {
                    yield (double) left + (double)right;
                }
                if(left instanceof String && right instanceof String) {
                    yield (String) left + (String) right;
                }
                throw new RuntimeError(expr.operator, "Operands must be numbers or strings.");
            }
            case TokenType.BANG_EQUAL -> !isEqual(left, right);
            case TokenType.EQUAL_EQUAL -> isEqual(left, right);
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
            case TokenType.MINUS -> {
                checkNumberOperand(expr.operator ,right);
                yield -(double) right;
            }
            case TokenType.BANG -> !isTruthy(right);
            default -> null;
        };
    }

    private void checkNumberOperand(Token operator, Object right) {
        if(right instanceof Double) {
            return;
        }
        throw new RuntimeError(operator, "Operand Must be a number.");
    }

    private void checkNumberOperands(Token operator, Object left, Object right){
        if(left instanceof Double && right instanceof Double) {
            return;
        }

        throw new RuntimeError(operator, "Operands Must be numbers.");
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

    public String stringify(Object obj) {
        if(obj == null){
            return "nil";
        }

        if(obj instanceof Double){
            String text = obj.toString();
            if(text.endsWith(".0")){
                text = text.substring(0, text.length()-2);
            }
            return text;
        }

        return obj.toString();
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    public void interpret(Expr expression){
        try{
            Object value = evaluate(expression);
            System.out.println(stringify(value));
        }catch(RuntimeError error){
            Lox.runtimeError(error);
        }
    }
}
