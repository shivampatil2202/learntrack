# JVM Basics

## What is JDK, JRE, and JVM?

### JVM (Java Virtual Machine)
The JVM is the runtime engine that executes Java bytecode. It's a virtual machine that provides a runtime environment for Java applications.

**Key responsibilities:**
- Loads Java bytecode
- Verifies bytecode for security
- Executes bytecode
- Manages memory (garbage collection)
- Provides runtime environment

Think of JVM as an interpreter that reads and executes your compiled Java code.

### JRE (Java Runtime Environment)
The JRE is a software package that provides the libraries and components necessary to run Java applications.

**Components:**
- JVM (the execution engine)
- Core Java libraries (java.lang, java.util, etc.)
- Supporting files and resources

Think of JRE as the complete environment needed to run Java programs (but not to develop them).

### JDK (Java Development Kit)
The JDK is a complete software development kit for Java.

**Components:**
- JRE (runtime environment)
- Development tools:
  - javac (compiler)
  - java (launcher)
  - jar (archive tool)
  - javadoc (documentation generator)
  - debugger

Think of JDK as everything you need to develop, compile, and run Java applications.

### Relationship
JDK contains → JRE contains → JVM

- **To develop Java apps**: You need JDK
- **To run Java apps**: You need JRE (or JDK)
- **To execute bytecode**: JVM does the work

## What is Bytecode?

Bytecode is the intermediate representation of your Java code. When you compile a `.java` file, the Java compiler (`javac`) converts it into `.class` files containing bytecode.

**Example:**
```java
// HelloWorld.java (source code)
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

After compilation (`javac HelloWorld.java`), you get `HelloWorld.class` containing bytecode.

**Why bytecode?**
- Platform-independent
- Can be verified for security
- Optimized by JVM at runtime
- Enables "write once, run anywhere"

## Write Once, Run Anywhere (WORA)

This is Java's core promise: write your code once, and it will run on any platform with a JVM.

**How it works:**

1. **Write**: You write Java source code (`.java` files)
2. **Compile**: Compiler converts it to bytecode (`.class` files)
3. **Distribute**: You share the bytecode
4. **Run**: Any platform with a JVM can execute the bytecode

**Example scenario:**
Developer (Windows) writes code
↓
Compiles to bytecode
↓
Bytecode runs on:
- Windows JVM
- Mac JVM
- Linux JVM
- Any other platform with JVM

**Why this matters:**
- No need to recompile for different operating systems
- Same bytecode works everywhere
- Platform-specific details handled by JVM
- True cross-platform compatibility

**Contrast with C/C++:**
- C/C++ compiles to platform-specific machine code
- Need separate compilation for Windows, Mac, Linux
- Different executables for each platform

**In summary:**
Java source → Bytecode (platform-independent) → JVM (platform-specific) → Execution

The JVM acts as a bridge between your Java program and the underlying operating system, handling all platform-specific details.