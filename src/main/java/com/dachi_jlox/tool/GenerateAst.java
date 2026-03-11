package com.dachi_jlox.tool;

import com.dachi_jlox.lox.Token;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if(args.length != 1){
            System.err.println("Usage: generate_ast <filename>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expr", Arrays.asList(
                "Assign   : Token name, Expr value",
                "Ternary  : Expr condition, Expr thenBranch, Expr elseBranch",
                "Binary   : Expr left, Token operator, Expr right",
                "Call     : Expr callee, Token paren, List<Expr> arguments",
                "Grouping : Expr expression",
                "Literal  : Object value",
                "Logical  : Expr left, Token operator, Expr right",
                "Unary    : Token operator, Expr right",
                "Variable : Token name"
        ));

        defineAst(outputDir, "Stmt", Arrays.asList(
                "Block     : List<Stmt> statements",
                "Expression: Expr expression",
                "If        : Expr condition, Stmt thenBranch, Stmt elseBranch",
                "Print     : Expr expression",
                "Var       : Token name, Expr initializer",
                "While     : Expr condition, Stmt body",
                "For       : Stmt initializer, Expr condition, Expr increment, Stmt body",
                "Break     : Token keyword",
                "Continue  : Token keyword"
        ));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);

        writer.println("package com.dachi_jlox.lox;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + " {");
        writer.println("    abstract <R> R accept(Visitor<R> visitor);");
        writer.println();

        defineVisitor(writer, baseName, types);

        for (String type : types){
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
        }




        writer.println("}");
        writer.close();
    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types){
        writer.println("    interface Visitor<R> {");

        for (String type : types){
            String className = type.split(":")[0].trim();
            writer.println("        R visit" + className + baseName + " (" + className + " " + baseName.toLowerCase() + ");");
        }
        writer.println("    }");
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fields){
        writer.println("    static class " + className + " extends " + baseName + " {");

        writer.println("        @Override");
        writer.println("        <R> R accept(Visitor<R> visitor) {");
        writer.println("            return visitor.visit" + className + baseName + "(this);");
        writer.println("        }");
        String[] fieldList = fields.split(", ");
        for (String field : fieldList){
            writer.println("        final " + field + ";");
        }

        writer.println();
        writer.println("        " + className + "(" +  fields + ") {");

        for (String field : fieldList){
            String name = field.split(" ")[1].trim();
            writer.println("            this." +  name + " = " + name + ";");
        }
        writer.println("        }");
        writer.println("    }");
        writer.println();
    }
}
