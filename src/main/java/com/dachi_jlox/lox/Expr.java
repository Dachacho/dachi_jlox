package com.dachi_jlox.lox;

import java.util.List;

abstract class Expr {
    abstract <R> R accept(Visitor<R> visitor);

    interface Visitor<R> {
        R visitAssignExpr (Assign expr);
        R visitTernaryExpr (Ternary expr);
        R visitBinaryExpr (Binary expr);
        R visitCallExpr (Call expr);
        R visitGetExpr (Get expr);
        R visitSetExpr (Set expr);
        R visitSuperExpr (Super expr);
        R visitThisExpr (This expr);
        R visitGroupingExpr (Grouping expr);
        R visitLiteralExpr (Literal expr);
        R visitLogicalExpr (Logical expr);
        R visitUnaryExpr (Unary expr);
        R visitVariableExpr (Variable expr);
    }
    static class Assign extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }
        final Token name;
        final Expr value;

        Assign(Token name, Expr value) {
            this.name = name;
            this.value = value;
        }
    }

    static class Ternary extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitTernaryExpr(this);
        }
        final Expr condition;
        final Expr thenBranch;
        final Expr elseBranch;

        Ternary(Expr condition, Expr thenBranch, Expr elseBranch) {
            this.condition = condition;
            this.thenBranch = thenBranch;
            this.elseBranch = elseBranch;
        }
    }

    static class Binary extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }
        final Expr left;
        final Token operator;
        final Expr right;

        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }
    }

    static class Call extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitCallExpr(this);
        }
        final Expr callee;
        final Token paren;
        final List<Expr> arguments;

        Call(Expr callee, Token paren, List<Expr> arguments) {
            this.callee = callee;
            this.paren = paren;
            this.arguments = arguments;
        }
    }

    static class Get extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitGetExpr(this);
        }
        final Expr object;
        final Token name;

        Get(Expr object, Token name) {
            this.object = object;
            this.name = name;
        }
    }

    static class Set extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitSetExpr(this);
        }
        final Expr object;
        final Token name;
        final Expr value;

        Set(Expr object, Token name, Expr value) {
            this.object = object;
            this.name = name;
            this.value = value;
        }
    }

    static class Super extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitSuperExpr(this);
        }
        final Token keyword;
        final Token method;

        Super(Token keyword, Token method) {
            this.keyword = keyword;
            this.method = method;
        }
    }

    static class This extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitThisExpr(this);
        }
        final Token keyword;

        This(Token keyword) {
            this.keyword = keyword;
        }
    }

    static class Grouping extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }
        final Expr expression;

        Grouping(Expr expression) {
            this.expression = expression;
        }
    }

    static class Literal extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }
        final Object value;

        Literal(Object value) {
            this.value = value;
        }
    }

    static class Logical extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLogicalExpr(this);
        }
        final Expr left;
        final Token operator;
        final Expr right;

        Logical(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }
    }

    static class Unary extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnaryExpr(this);
        }
        final Token operator;
        final Expr right;

        Unary(Token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }
    }

    static class Variable extends Expr {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariableExpr(this);
        }
        final Token name;

        Variable(Token name) {
            this.name = name;
        }
    }

}
