# Java Fundamentals for .NET Developers
## Week 1 Session 1: Foundation Concepts

> **Complete educational repository** for .NET developers transitioning to Java, featuring live coding demonstrations, hands-on exercises, and comprehensive reference materials.

[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Educational-brightgreen.svg)](#)

## 🎯 Purpose & Target Audience

This repository is designed for **senior .NET developers** beginning their transition to Java and Spring Boot. It provides a comprehensive foundation covering Java fundamentals with constant references to .NET equivalents, making the learning curve as smooth as possible.

### Who This Is For
- **.NET developers** (C#, ASP.NET Core, Entity Framework) learning Java
- **Instructors** teaching Java to .NET developers
- **Students** in formal .NET-to-Java transition courses
- **Self-learners** seeking structured Java fundamentals with .NET comparisons

### What Makes This Different
- **Compare, Don't Replace**: Every Java concept is related to .NET equivalents
- **WHY over HOW**: Explanations focus on reasoning behind design decisions
- **Production-Ready**: All examples use modern, production-ready patterns
- **Hands-On Learning**: Practical coding over theoretical discussions
- **Progressive Complexity**: Starts simple, builds systematically

## 📚 Learning Objectives

By completing this session, you will:

### Core Java Fundamentals
- [ ] **Understand Java syntax** differences from C# (packages, imports, naming conventions)
- [ ] **Master string handling** and avoid common comparison pitfalls
- [ ] **Use Java Collections Framework** effectively (List, Set, Map vs .NET collections)
- [ ] **Apply Streams API** for functional programming (Java equivalent of LINQ)
- [ ] **Handle null safety** with Optional (Java's approach to nullable types)

### Enterprise Java Patterns
- [ ] **Configure Maven projects** (Java's equivalent to NuGet/MSBuild)
- [ ] **Implement dependency injection** with Spring annotations
- [ ] **Create REST APIs** using Spring Boot (compare to ASP.NET Core)
- [ ] **Write comprehensive tests** with JUnit 5, Mockito, and AssertJ
- [ ] **Organize code** following Java/Spring conventions

### .NET Developer Transition
- [ ] **Map .NET concepts** to Java equivalents confidently
- [ ] **Avoid common anti-patterns** that .NET developers fall into
- [ ] **Use modern Java features** (Records, var keyword, Lambda expressions)
- [ ] **Understand trade-offs** between different approaches
- [ ] **Build production-ready code** from day one

## 🚀 Quick Start

### Prerequisites
- **Java 17+** installed and configured
- **Maven 3.6+** or use included Maven Wrapper
- **IDE**: IntelliJ IDEA (recommended) with VS Code keymap, or VS Code with Java extensions
- **Basic .NET knowledge**: C#, ASP.NET Core, dependency injection concepts

### Setup & Run
```bash
# Clone and navigate to project
git clone <repository-url>
cd week1-session1-java-fundamentals

# Verify Java installation (should be 17+)
java -version

# Run with Maven wrapper (works like dotnet CLI)
./mvnw clean compile
./mvnw test
./mvnw spring-boot:run

# Or with local Maven installation
mvn clean compile
mvn test
mvn spring-boot:run
```

### Quick Verification
```bash
# Run a demo class to verify setup
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.demos.BasicJavaDemo"
```

## 🗂️ Repository Structure

```
week1-session1-java-fundamentals/
├── 📋 README.md                           # This file - start here
├── ⚙️ pom.xml                             # Maven configuration (like .csproj)
├── 📚 docs/                               # Comprehensive documentation
│   ├── 📖 README.md                       # Documentation overview
│   ├── 🎯 learning-objectives.md          # Detailed learning goals
│   ├── 🚀 getting-started.md             # Setup and first steps
│   ├── 👨‍🏫 session-guide.md                # Complete session guide
│   ├── 📂 guides/                         # Topic-specific deep dives
│   ├── 📋 decisions/                      # Architecture Decision Records
│   ├── 👨‍💻 instructor-notes.md             # Teaching guidance
│   ├── 📖 student-handbook.md             # Self-study guide
│   ├── 🔧 troubleshooting.md              # Common issues & solutions
│   └── 📚 further-reading.md              # Additional resources
├── 🚫 dotnet-to-java-antipatterns.md     # Common mistakes & corrections
├── 💪 in-class-exercises.md              # Hands-on exercises
├── ⚡ quick-reference-conversions.md     # C# to Java syntax mapping
└── 💻 src/                               # All source code
    ├── main/java/com/coherentsolutions/session1/
    │   ├── 🚫 antipatterns/               # Common mistakes & corrections
    │   │   ├── stringcomparison/          # String == vs .equals()
    │   │   ├── nullhandling/              # null vs Optional<T>
    │   │   ├── properties/                # Fields vs getters/setters
    │   │   ├── collections/               # Collection initialization
    │   │   ├── exceptions/                # Checked vs unchecked exceptions
    │   │   ├── streams/                   # Stream reuse mistakes
    │   │   ├── async/                     # async/await vs CompletableFuture
    │   │   ├── valuetypes/                # struct vs record/class
    │   │   ├── packages/                  # namespace vs package organization
    │   │   └── di/                        # DI patterns and best practices
    │   ├── 💪 exercises/                  # Hands-on coding challenges
    │   │   ├── product/                   # C# to Java conversion exercise
    │   │   ├── stringpuzzle/              # String reference understanding
    │   │   ├── collections/               # Collections and Streams practice
    │   │   ├── userservice/               # Service layer with DI
    │   │   └── fileprocessor/             # File I/O and error handling
    │   ├── 📖 reference/                  # Comprehensive reference materials
    │   │   ├── TypeConversions.java       # Java types vs C# types
    │   │   ├── CollectionExamples.java    # Collections Framework guide
    │   │   ├── MethodExamples.java        # Java methods and lambdas
    │   │   ├── LinqToStreams.java         # LINQ to Streams conversion
    │   │   ├── PropertiesAndLombok.java   # Properties handling
    │   │   ├── SpringAnnotations.java     # Spring Framework annotations
    │   │   ├── TestingExamples.java       # Testing frameworks comparison
    │   │   └── JsonExamples.java          # JSON processing
    │   ├── 🎬 demos/                      # Live coding demonstrations
    │   │   ├── BasicJavaDemo.java         # Java syntax vs C#
    │   │   ├── CollectionsDemo.java       # Collections Framework
    │   │   ├── StreamsDemo.java           # Streams API and LINQ
    │   │   ├── SpringDemo.java            # Spring Boot fundamentals
    │   │   └── TestingDemo.java           # Testing patterns
    │   └── 🏗️ domain/                     # Business domain examples
    │       ├── User.java                  # User entity
    │       ├── Product.java               # Product entity
    │       ├── Order.java                 # Order aggregate
    │       ├── Event.java                 # Event base class
    │       ├── Movie.java                 # Movie event
    │       └── Ticket.java                # Ticket entity
    └── test/java/                         # Test examples (JUnit 5)
```

## 🎓 Learning Pathways

### 🚀 For Beginners: "I'm New to Java"
1. **Start Here**: [`docs/getting-started.md`](docs/getting-started.md) - Setup and first steps
2. **Syntax Overview**: [`quick-reference-conversions.md`](quick-reference-conversions.md) - C# to Java mapping
3. **Avoid Pitfalls**: [`dotnet-to-java-antipatterns.md`](dotnet-to-java-antipatterns.md) - Common mistakes
4. **Practice**: [`in-class-exercises.md`](in-class-exercises.md) - Hands-on exercises
5. **Deep Dive**: [`docs/guides/java-fundamentals.md`](docs/guides/java-fundamentals.md) - Core concepts

### 👨‍💻 For Self-Learners: "I Want to Study Independently"
1. **Study Guide**: [`docs/student-handbook.md`](docs/student-handbook.md) - Self-directed learning path
2. **Reference Materials**: [`src/main/java/.../reference/`](src/main/java/com/coherentsolutions/session1/reference/) - Comprehensive examples
3. **Practice Exercises**: [`src/main/java/.../exercises/`](src/main/java/com/coherentsolutions/session1/exercises/) - Coding challenges
4. **Verify Understanding**: Run demo classes and modify them
5. **Troubleshooting**: [`docs/troubleshooting.md`](docs/troubleshooting.md) - Common issues

### 👨‍🏫 For Instructors: "I'm Teaching This Course"
1. **Teaching Guide**: [`docs/instructor-notes.md`](docs/instructor-notes.md) - Lesson plans and tips
2. **Session Structure**: [`docs/session-guide.md`](docs/session-guide.md) - Complete session flow
3. **Live Demos**: [`src/main/java/.../demos/`](src/main/java/com/coherentsolutions/session1/demos/) - Interactive coding
4. **Exercise Solutions**: Each exercise includes complete solutions
5. **Discussion Points**: Built into anti-patterns and exercises

### 🏃‍♂️ For Experienced Developers: "I Want Key Differences Only"
1. **Quick Reference**: [`quick-reference-conversions.md`](quick-reference-conversions.md) - Syntax mapping
2. **Anti-Patterns**: [`dotnet-to-java-antipatterns.md`](dotnet-to-java-antipatterns.md) - What to avoid
3. **Architecture Decisions**: [`docs/decisions/`](docs/decisions/) - Why we chose specific approaches
4. **Advanced Topics**: [`docs/guides/`](docs/guides/) - Deep dives into specific areas
5. **Spring Boot**: [`src/main/java/.../demos/SpringDemo.java`](src/main/java/com/coherentsolutions/session1/demos/SpringDemo.java) - Enterprise patterns

## 💼 Key Topics Covered

### 🔧 Java Language Fundamentals
| Topic | C# Equivalent | Key Files |
|-------|---------------|-----------|
| **Package Organization** | Namespaces | [`antipatterns/packages/`](src/main/java/com/coherentsolutions/session1/antipatterns/packages/) |
| **String Handling** | String equality | [`antipatterns/stringcomparison/`](src/main/java/com/coherentsolutions/session1/antipatterns/stringcomparison/) |
| **Null Safety** | Nullable types | [`antipatterns/nullhandling/`](src/main/java/com/coherentsolutions/session1/antipatterns/nullhandling/) |
| **Properties** | Auto-properties | [`antipatterns/properties/`](src/main/java/com/coherentsolutions/session1/antipatterns/properties/) |
| **Collections** | List<T>, Dictionary<K,V> | [`reference/CollectionExamples.java`](src/main/java/com/coherentsolutions/session1/reference/CollectionExamples.java) |
| **Streams API** | LINQ | [`reference/LinqToStreams.java`](src/main/java/com/coherentsolutions/session1/reference/LinqToStreams.java) |

### 🏢 Enterprise Development
| Topic | .NET Equivalent | Key Files |
|-------|-----------------|-----------|
| **Dependency Injection** | Microsoft.Extensions.DI | [`demos/SpringDemo.java`](src/main/java/com/coherentsolutions/session1/demos/SpringDemo.java) |
| **Testing** | xUnit + Moq | [`demos/TestingDemo.java`](src/main/java/com/coherentsolutions/session1/demos/TestingDemo.java) |
| **Build System** | MSBuild + NuGet | [`pom.xml`](pom.xml) |
| **REST APIs** | ASP.NET Core | [`demos/SpringDemo.java`](src/main/java/com/coherentsolutions/session1/demos/SpringDemo.java) |
| **JSON Processing** | System.Text.Json | [`reference/JsonExamples.java`](src/main/java/com/coherentsolutions/session1/reference/JsonExamples.java) |

## 🛠️ Technologies & Frameworks

### Core Stack
- **Java 17+**: Modern Java with latest features (compare to C# 10/11)
- **Spring Boot 3.x**: Application framework (equivalent to ASP.NET Core)
- **Maven**: Build tool and dependency management (like MSBuild + NuGet)
- **Lombok**: Reduces boilerplate code (like C# auto-properties)

### Development Tools
- **IntelliJ IDEA**: Recommended IDE with VS Code keymap
- **JUnit 5**: Testing framework (like xUnit/NUnit)
- **Mockito**: Mocking framework (like Moq)
- **AssertJ**: Fluent assertions (like FluentAssertions)

### .NET Comparisons
| Java Technology | .NET Equivalent | Notes |
|-----------------|-----------------|-------|
| Spring Boot | ASP.NET Core | Web application framework |
| Maven/Gradle | MSBuild/NuGet | Build and package management |
| JUnit 5 | xUnit/NUnit/MSTest | Unit testing framework |
| Mockito | Moq/NSubstitute | Mocking framework |
| Jackson | System.Text.Json | JSON serialization |
| SLF4J + Logback | ILogger<T> | Logging abstraction |

## 📖 Documentation & Learning Resources

### 📚 Essential Reading
1. **[Documentation Overview](docs/README.md)** - Navigate all learning materials
2. **[Learning Objectives](docs/learning-objectives.md)** - Detailed goals and outcomes
3. **[Session Guide](docs/session-guide.md)** - Complete walkthrough for Week 1 Session 1
4. **[Student Handbook](docs/student-handbook.md)** - Self-study guide with checkpoints

### 🔍 Quick References
- **[Syntax Conversions](quick-reference-conversions.md)** - C# to Java syntax mapping
- **[Anti-Patterns Guide](dotnet-to-java-antipatterns.md)** - Common mistakes and fixes
- **[Exercise Collection](in-class-exercises.md)** - Hands-on practice problems

### 🎯 Topic Deep Dives
- **[Java Fundamentals](docs/guides/java-fundamentals.md)** - Core language concepts
- **[Collections & Streams](docs/guides/collections-and-streams.md)** - Data manipulation
- **[Dependency Injection](docs/guides/dependency-injection.md)** - Spring DI patterns
- **[Testing Frameworks](docs/guides/testing-frameworks.md)** - JUnit, Mockito, AssertJ
- **[Build & Project Structure](docs/guides/build-and-project-structure.md)** - Maven and organization

## 🚀 Running Examples

### Execute Demo Classes
```bash
# Basic Java syntax demonstration
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.demos.BasicJavaDemo"

# Collections Framework demo
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.demos.CollectionsDemo"

# Streams API (Java LINQ) demo
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.demos.StreamsDemo"

# Spring Boot fundamentals demo
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.demos.SpringDemo"

# Testing frameworks demo
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.demos.TestingDemo"
```

### Run Tests
```bash
# Run all tests (like dotnet test)
./mvnw test

# Run specific test pattern
./mvnw test -Dtest="*Exercise*"

# Run with coverage
./mvnw test jacoco:report
```

### Practice Exercises
```bash
# Try the hands-on exercises
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.exercises.ProductExercise"
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.exercises.CollectionsExercise"
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.exercises.UserServiceExercise"
```

## ⚠️ Common Pitfalls for .NET Developers

> 💡 **Pro Tip**: Review the [Anti-Patterns Guide](dotnet-to-java-antipatterns.md) before diving into coding!

### Top 5 Mistakes
1. **String Comparison**: Using `==` instead of `.equals()` (works differently than C#)
2. **Null Handling**: Returning `null` instead of `Optional<T>` (Java's nullable approach)
3. **Collection Initialization**: Trying to use C# collection initializer syntax
4. **Stream Reuse**: Attempting to reuse Streams like LINQ queries (single-use only)
5. **Field Injection**: Using `@Autowired` on fields instead of constructor injection

### Quick Fixes
| ❌ C# Habit | ✅ Java Best Practice |
|-------------|---------------------|
| `str1 == str2` | `str1.equals(str2)` |
| `User findUser()` | `Optional<User> findUser()` |
| `new List<>{"a","b"}` | `Arrays.asList("a","b")` |
| `stream.count(); stream.toList()` | Create new stream for each operation |
| `@Autowired private field` | Constructor injection with `final` |

## 📞 Getting Help

### 🔧 Troubleshooting
- **[Common Issues](docs/troubleshooting.md)** - Solutions to frequent problems
- **Setup Problems**: Check Java version, Maven installation, IDE configuration
- **Compilation Errors**: Review package declarations and import statements
- **Runtime Issues**: Check classpath and dependency versions

### 💬 Discussion Questions
Perfect for study groups or classroom discussions:
1. "What surprised you most about Java compared to C#?"
2. "How does dependency injection differ between Spring and .NET Core?"
3. "When would you choose Java Streams over traditional loops?"
4. "What are the trade-offs between Lombok and traditional Java code?"

### 📚 Additional Resources
- **[Further Reading](docs/further-reading.md)** - Books, courses, and online resources
- **Official Documentation**: [Java](https://docs.oracle.com/en/java/), [Spring](https://spring.io/guides)
- **Community**: Stack Overflow, Reddit r/learnjava, Java Discord communities

## 🎓 Next Steps

### After Completing This Session
1. **Week 2**: Advanced Java features (Generics, Annotations, Reflection)
2. **Week 3**: Spring Boot deep dive (Configuration, Profiles, Actuator)
3. **Week 4**: Data persistence with JPA/Hibernate
4. **Future Sessions**: Microservices, Testing strategies, Cloud deployment

### Recommended Learning Path
1. Complete all exercises in this repository
2. Build a small project using the patterns learned
3. Read through the advanced guides in the `docs/guides/` directory
4. Practice TDD with the testing frameworks introduced
5. Explore Spring Boot starters and auto-configuration

---

## 📝 Course Information

**Course**: .NET to Java/Spring Transition  
**Session**: Week 1 Session 1 - Java Fundamentals  
**Duration**: 2-3 hours (can be split across multiple sessions)  
**Level**: Beginner to Intermediate (assumes .NET experience)  
**Format**: Interactive coding with live demonstrations  

---

## 📄 License & Attribution

This educational repository is created for learning purposes. All code examples are designed to demonstrate best practices for .NET developers learning Java.

**Happy Learning! 🚀**

> "The best way to learn Java as a .NET developer is not to forget what you know, but to build bridges between the two worlds." - Course Philosophy