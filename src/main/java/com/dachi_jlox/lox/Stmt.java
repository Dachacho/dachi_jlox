package com.dachi_jlox.lox;

import java.util.List;

abstract class Stmt {
    abstract <R> R accept(Visitor<R> visitor);

    interface Visitor<R> {
        R visitBlockStmt (Block stmt);
        R visitExpressionStmt (Expression stmt);
        R visitIfStmt (If stmt);
        R visitPrintStmt (Print stmt);
        R visitVarStmt (Var stmt);
        R visitWhileStmt (While stmt);
        R visitForStmt (For stmt);
        R visitBreakStmt (Break stmt);
        R visitContinueStmt (Continue stmt);
    }
    static class Block extends Stmt {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBlockStmt(this);
        }
        final List<Stmt> statements;

        Block(List<Stmt> statements) {
            this.statements = statements;
        }
    }

    static class Expression extends Stmt {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitExpressionStmt(this);
        }
        final Expr expression;

        Expression(Expr expression) {
            this.expression = expression;
        }
    }

    static class If extends Stmt {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitIfStmt(this);
        }
        final Expr condition;
        final Stmt thenBranch;
        final Stmt elseBranch;

        If(Expr condition, Stmt thenBranch, Stmt elseBranch) {
            this.condition = condition;
            this.thenBranch = thenBranch;
            this.elseBranch = elseBranch;
        }
    }

    static class Print extends Stmt {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitPrintStmt(this);
        }
        final Expr expression;

        Print(Expr expression) {
            this.expression = expression;
        }
    }

    static class Var extends Stmt {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitVarStmt(this);
        }
        final Token name;
        final Expr initializer;

        Var(Token name, Expr initializer) {
            this.name = name;
            this.initializer = initializer;
        }
    }

    static class While extends Stmt {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitWhileStmt(this);
        }
        final Expr condition;
        final Stmt body;

        While(Expr condition, Stmt body) {
            this.condition = condition;
            this.body = body;
        }
    }

    static class For extends Stmt {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitForStmt(this);
        }
        final Stmt initializer;
        final Expr condition;
        final Expr increment;
        final Stmt body;

        For(Stmt initializer, Expr condition, Expr increment, Stmt body) {
            this.initializer = initializer;
            this.condition = condition;
            this.increment = increment;
            this.body = body;
        }
    }

    static class Break extends Stmt {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBreakStmt(this);
        }
        final Token keyword;

        Break(Token keyword) {
            this.keyword = keyword;
        }
    }

    static class Continue extends Stmt {
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitContinueStmt(this);
        }
        final Token keyword;

        Continue(Token keyword) {
            this.keyword = keyword;
        }
    }

}
