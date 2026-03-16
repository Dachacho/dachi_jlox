# dachi_jlox

A small **jlox** interpreter implementation from *Crafting Interpreters* (CI), written in Java.
This version also includes most of the book's challenge extensions, so it goes beyond the base jlox in CI.

## Quick start

Build once from the repo root:

```bash
mvn package
```

Run a `.lox` file:

```bash
java -cp target/classes com.dachi_jlox.lox.Lox path/to/script.lox
```

Open the REPL:

```bash
java -cp target/classes com.dachi_jlox.lox.Lox
```

## Examples

Sample programs live in:

`src/main/java/com/dachi_jlox/examples/`

---

__Side Note:__
This is probably one of the best written books I have picked up (still a long way to go Bytecode Interpreter is up next and thats a bigger challange).
Way the author really goes into detail about every single thing and leaves you feeling powerful (for lack of a better word) is just unmatched no book has ever given me that.
