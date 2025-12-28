# Setup Instructions

## JDK Installation

### For Windows
1. Download JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
2. Run the installer
3. Set JAVA_HOME environment variable:
   - Right-click "This PC" → Properties
   - Advanced System Settings → Environment Variables
   - Add new variable: JAVA_HOME = `C:\Program Files\Java\jdk-11`
   - Add to Path: `%JAVA_HOME%\bin`

### For Mac
```bash
brew install openjdk@11
```

### For Linux
```bash
sudo apt update
sudo apt install openjdk-11-jdk
```

## Verify Installation

Open terminal/command prompt and run:
```bash
java -version
javac -version
```

You should see output similar to:
java version "11.0.x"
Java(TM) SE Runtime Environment

## IDE Setup

### IntelliJ IDEA (Recommended)
1. Download [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/)
2. Install and open
3. Create New Project → Java
4. Set JDK path
5. Import/Create the LearnTrack project

### Eclipse
1. Download [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/)
2. Install and open
3. File → New → Java Project
4. Import source files

### VS Code
1. Install VS Code
2. Install "Extension Pack for Java" from Extensions marketplace
3. Open project folder
4. VS Code will auto-detect Java project structure

## Hello World Test

Create a file `HelloWorld.java`:
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

### Compile and Run
```bash
javac HelloWorld.java
java HelloWorld
```

Expected output: `Hello, World!`

## Project Import

1. Download/Clone the LearnTrack project
2. Open in your IDE
3. Ensure the `src` folder is marked as source root
4. Build the project
5. Run `Main.java`

## Troubleshooting

### "javac not recognized"
- JAVA_HOME not set correctly
- Java bin folder not in PATH

### "Cannot find symbol" errors
- Ensure all files are in correct package structure
- Check import statements

### IDE not detecting Java
- Set JDK in IDE project settings
- Reload/Invalidate caches



