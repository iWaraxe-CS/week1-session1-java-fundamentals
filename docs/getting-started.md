# Getting Started
## Java Fundamentals for .NET Developers - Setup and First Steps

> **Complete setup guide** to get your Java development environment ready and run your first Java examples. Designed specifically for .NET developers.

## 🎯 What You'll Achieve

By the end of this guide, you'll have:
- ✅ Java development environment fully configured
- ✅ IDE set up with .NET developer-friendly settings
- ✅ Project successfully running and tested
- ✅ First Java code executed and understood
- ✅ Confidence to proceed with the learning materials

**Time Required:** 15-30 minutes (depending on download speeds)

## 📋 Prerequisites

### Required Knowledge
- **C# and .NET development experience** (ASP.NET Core, Entity Framework, dependency injection)
- **Basic command line usage** (equivalent to PowerShell or Command Prompt)
- **Git basics** for cloning repositories
- **JSON configuration** understanding (similar to appsettings.json)

### System Requirements
- **Operating System**: Windows 10+, macOS 10.14+, or Linux (Ubuntu 18.04+)
- **Memory**: 8GB RAM minimum (16GB recommended for IDE)
- **Disk Space**: 5GB for JDK, IDE, and dependencies
- **Internet**: Required for downloading dependencies

## ⚙️ Environment Setup

### Step 1: Install Java Development Kit (JDK)

#### Why Java 17?
- **LTS (Long Term Support)**: Like .NET 6/8, provides stability
- **Modern Features**: Records, pattern matching, improved APIs
- **Industry Standard**: Most enterprise projects use Java 11 or 17
- **Spring Boot Compatibility**: Full support for latest Spring features

#### Download and Install

**Option A: Oracle JDK (Recommended for beginners)**
```bash
# Download from: https://www.oracle.com/java/technologies/downloads/
# Choose Java 17 for your operating system
```

**Option B: OpenJDK (Free alternative)**
```bash
# Windows (using Chocolatey)
choco install openjdk17

# macOS (using Homebrew)
brew install openjdk@17

# Linux (Ubuntu/Debian)
sudo apt update
sudo apt install openjdk-17-jdk
```

#### Verify Installation
```bash
# Should show version 17.x.x
java -version

# Should show version 17.x.x
javac -version
```

**Expected Output:**
```
java version "17.0.8" 2023-07-18 LTS
Java(TM) SE Runtime Environment (build 17.0.8+9-LTS-211)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.8+9-LTS-211, mixed mode, sharing)
```

#### Set JAVA_HOME Environment Variable

**Windows:**
```powershell
# Find Java installation path
where java

# Set JAVA_HOME (adjust path as needed)
setx JAVA_HOME "C:\Program Files\Java\jdk-17"
```

**macOS/Linux:**
```bash
# Add to ~/.bashrc or ~/.zshrc
export JAVA_HOME=$(/usr/libexec/java_home -v 17)  # macOS
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk    # Linux

# Reload shell configuration
source ~/.bashrc  # or ~/.zshrc
```

**Verification:**
```bash
echo $JAVA_HOME
# Should show path to Java 17 installation
```

---

### Step 2: Verify Maven (Build Tool)

#### Why Maven vs. Gradle?
- **Closer to MSBuild**: XML-based configuration like .csproj files
- **Industry Standard**: Most enterprise Java projects use Maven
- **Excellent IDE Support**: Better integration with IntelliJ and Eclipse
- **Spring Boot Default**: Official Spring Boot guides use Maven

#### Check Maven Installation
```bash
# This project includes Maven Wrapper, so this is optional
mvn -version
```

**If Maven not installed (optional):**
```bash
# Windows (Chocolatey)
choco install maven

# macOS (Homebrew)
brew install maven

# Linux (Ubuntu/Debian)
sudo apt install maven
```

**Using Maven Wrapper (Recommended):**
```bash
# No Maven installation required - uses included wrapper
./mvnw -version    # macOS/Linux
mvnw.cmd -version  # Windows
```

---

### Step 3: IDE Setup

#### Recommended: IntelliJ IDEA

**Why IntelliJ for .NET Developers?**
- **Superior Java support**: Best-in-class refactoring, debugging, code analysis
- **VS Code keymap**: Use familiar shortcuts from Visual Studio
- **Integrated Maven**: No plugin setup required
- **Spring Boot support**: Excellent framework integration
- **Database tools**: Built-in database management

**Download and Install:**
1. Download IntelliJ IDEA Community (free) from [jetbrains.com](https://www.jetbrains.com/idea/)
2. Install with default settings
3. Launch and configure:

**Essential Configuration for .NET Developers:**
```
File → Settings (Ctrl+Alt+S)
├── Keymap → Select "Visual Studio Code"
├── Editor → General → Auto Import → Enable "Add unambiguous imports on the fly"
├── Build → Build Tools → Maven → Auto-reload projects
└── Plugins → Install "Lombok" plugin
```

#### Alternative: Visual Studio Code

**If you prefer VS Code:**
```bash
# Install Java extensions
code --install-extension vscjava.vscode-java-pack
code --install-extension vscjava.vscode-spring-boot-pack
```

**VS Code Configuration (.vscode/settings.json):**
```json
{
    "java.home": "/path/to/java-17",
    "java.configuration.maven.userSettings": null,
    "spring-boot.ls.checkForJvmSupport": true,
    "java.compile.nullAnalysis.mode": "automatic"
}
```

---

## 🚀 Project Setup

### Step 1: Clone and Open Project

```bash
# Clone the repository
git clone <repository-url>
cd week1-session1-java-fundamentals

# Open in IntelliJ IDEA
idea .

# Or open in VS Code
code .
```

### Step 2: Verify Project Structure

**Expected Structure (compare to .NET solution):**
```
week1-session1-java-fundamentals/           # Like .sln file
├── pom.xml                                  # Like .csproj (project configuration)
├── src/                                     # Source code
│   ├── main/                               # Production code
│   │   ├── java/                           # Java source files
│   │   └── resources/                      # Configuration files (like appsettings.json)
│   └── test/                               # Test code (like .Tests project)
│       └── java/                           # Test source files
├── target/                                 # Build output (like bin/obj folders)
└── docs/                                   # Documentation
```

### Step 3: Build and Verify Project

```bash
# Build project (like dotnet build)
./mvnw clean compile

# Run tests (like dotnet test)
./mvnw test

# Package application (like dotnet publish)
./mvnw clean package
```

**Expected Output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: 45.123 s
[INFO] Finished at: 2024-01-15T10:30:00Z
```

### Step 4: Run Your First Java Demo

```bash
# Run basic Java demonstration
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.demos.BasicJavaDemo"
```

**Expected Output:**
```
=== BASIC JAVA DEMO ===
Java fundamentals for .NET developers

--- Demo 1: Basic Syntax ---
CONSOLE OUTPUT:
C# CONSOLE OUTPUT:
Console.WriteLine("Hello World!");
Java: System.out.println("Hello World!");

Java output:
Hello World!
...
```

### Step 5: IDE Integration Test

**In IntelliJ IDEA:**
1. Navigate to `src/main/java/com/coherentsolutions/session1/demos/BasicJavaDemo.java`
2. Right-click → Run 'BasicJavaDemo.main()'
3. Verify output appears in console

**In VS Code:**
1. Open the same file
2. Click "Run" above the main method
3. Check terminal output

---

## 🔍 Verification Tests

### Test 1: Java Environment
```bash
# All should return version 17.x.x
java -version
javac -version
echo $JAVA_HOME
```

### Test 2: Build System
```bash
# Should show Maven information
./mvnw -version

# Should compile successfully
./mvnw clean compile
```

### Test 3: IDE Integration
```bash
# Should run without errors
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.demos.BasicJavaDemo"
```

### Test 4: Spring Boot Application
```bash
# Should start web server on port 8080
./mvnw spring-boot:run
```

**Verify:** Open browser to `http://localhost:8080` (may show error page - that's normal for demo)

---

## 🛠️ Troubleshooting Common Issues

### Issue 1: "JAVA_HOME not set"
**Problem:** Environment variable not configured
**Solution:**
```bash
# Windows
setx JAVA_HOME "C:\Program Files\Java\jdk-17"

# macOS/Linux
export JAVA_HOME=/path/to/java-17
```

### Issue 2: "Maven not found"
**Problem:** Maven not in PATH
**Solution:** Use Maven wrapper instead
```bash
# Instead of 'mvn', use:
./mvnw    # macOS/Linux
mvnw.cmd  # Windows
```

### Issue 3: "Permission denied" on Unix/Mac
**Problem:** Maven wrapper not executable
**Solution:**
```bash
chmod +x mvnw
./mvnw clean compile
```

### Issue 4: IDE doesn't recognize Java project
**Problem:** IDE configuration issues
**Solution:**
```bash
# Reimport Maven project
./mvnw clean compile
# Then restart IDE and reimport
```

### Issue 5: OutOfMemoryError
**Problem:** Insufficient memory for Maven
**Solution:**
```bash
# Set Maven memory options
export MAVEN_OPTS="-Xmx1024m -Xms256m"
./mvnw clean compile
```

---

## 🎯 Validate Your Setup

### Checklist: Environment Ready
- [ ] Java 17+ installed and accessible
- [ ] JAVA_HOME environment variable set
- [ ] Maven wrapper executable
- [ ] IDE configured with appropriate plugins
- [ ] Project builds successfully
- [ ] Demo classes run without errors

### Quick Verification Script
```bash
#!/bin/bash
# Run this script to verify complete setup

echo "=== Environment Verification ==="

echo "1. Java Version:"
java -version

echo "2. Java Compiler:"
javac -version

echo "3. JAVA_HOME:"
echo $JAVA_HOME

echo "4. Maven Wrapper:"
./mvnw -version

echo "5. Project Build:"
./mvnw clean compile

echo "6. Run Demo:"
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.demos.BasicJavaDemo"

echo "=== Verification Complete ==="
```

---

## 🎓 Understanding What You've Set Up

### Java vs. .NET Development Environment

| Component | .NET Equivalent | Purpose |
|-----------|-----------------|---------|
| **JDK 17** | .NET 6/8 SDK | Runtime and development tools |
| **Maven** | MSBuild + NuGet | Build tool and package manager |
| **pom.xml** | .csproj file | Project configuration and dependencies |
| **src/main/java** | Main project folder | Production source code |
| **src/test/java** | .Tests project | Unit test source code |
| **target/** | bin/obj folders | Build output directory |

### Key Differences to Remember

1. **Package Structure**: Java uses directory structure matching package names
2. **Build Lifecycle**: Maven has specific phases (compile, test, package, install)
3. **Dependency Management**: Dependencies declared in pom.xml, downloaded to local repository
4. **IDE Integration**: Less "magic" than Visual Studio - more explicit configuration

---

## 📚 Next Steps

### Immediate Actions
1. **[Session Guide](session-guide.md)** - Complete walkthrough of Week 1 Session 1
2. **[Java Fundamentals](guides/java-fundamentals.md)** - Deep dive into core concepts
3. **[Quick Reference](../quick-reference-conversions.md)** - C# to Java syntax mapping

### Recommended Learning Path
1. Run all demo classes to see Java patterns in action
2. Work through exercises to practice new concepts
3. Review anti-patterns to avoid common mistakes
4. Build something small using the patterns learned

### Development Workflow
```bash
# Your daily Java development commands (like dotnet CLI)
./mvnw clean compile    # Build project
./mvnw test            # Run tests
./mvnw spring-boot:run # Start application
./mvnw package         # Create JAR file
```

---

## 💡 Pro Tips for .NET Developers

### IDE Productivity
- **Use IntelliJ's "Generate" menu** (Alt+Insert) - like VS scaffolding
- **Enable auto-import** - saves typing import statements
- **Use live templates** - similar to code snippets in Visual Studio
- **Learn refactoring shortcuts** - IntelliJ excels at safe refactoring

### Build Understanding
- **Maven phases are sequential** - unlike MSBuild targets
- **Local repository** (~/.m2/repository) - like global NuGet cache
- **Dependency scope** - compile, test, runtime (similar to NuGet package types)

### Debugging Tips
- **System.out.println()** is like Console.WriteLine() - but use logging frameworks in real code
- **IntelliJ debugger** works similarly to Visual Studio debugger
- **Hot code replacement** is more limited than .NET hot reload

---

**You're ready to start learning Java! 🚀**

> *"A journey of a thousand miles begins with a single step - and setting up the right development environment."*