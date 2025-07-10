# ADR-001: Maven over Gradle for Build Tool

## Status
**ACCEPTED** - December 2024

## Context

For this Java fundamentals educational project targeting .NET developers, we needed to choose a build tool. The main candidates were:

- **Maven** - XML-based, convention-over-configuration
- **Gradle** - Groovy/Kotlin DSL, flexible and modern
- **SBT** - Scala Build Tool (less relevant for Java)

## Decision

We chose **Maven** as the primary build tool for this educational project.

## Rationale

### For .NET Developers Learning Java

**Maven provides better learning experience because:**

1. **Familiar XML Structure**
   - .NET developers are familiar with XML from `.csproj` files
   - Maven's `pom.xml` structure is conceptually similar to MSBuild
   - Easier to understand dependencies and configuration

2. **Explicit Convention-Over-Configuration**
   - Standard directory layout matches .NET project structure concepts
   - Clear separation of concerns (src/main/java vs src/test/java)
   - Predictable build lifecycle phases

3. **Industry Standard for Enterprise Java**
   - Most enterprise Java projects use Maven
   - Better job market preparation for .NET developers transitioning
   - Extensive documentation and community support

### Educational Benefits

**Maven Advantages for Learning:**
- **Lower Cognitive Load**: One less DSL to learn while mastering Java
- **Immediate Productivity**: Works out-of-the-box with minimal configuration
- **Clear Mental Model**: Fixed lifecycle phases vs. flexible task graphs
- **Better IDE Integration**: Superior support in IntelliJ IDEA and Eclipse

**Example - Maven pom.xml (Familiar to .NET developers):**
```xml
<project>
    <groupId>com.company</groupId>
    <artifactId>my-app</artifactId>
    <version>1.0.0</version>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>3.2.0</version>
        </dependency>
    </dependencies>
</project>
```

**vs. Gradle build.gradle (Additional DSL to learn):**
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.0'
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

## Consequences

### Positive
- ✅ **Faster Onboarding**: .NET developers can focus on Java language features
- ✅ **Industry Relevance**: Prepares students for most enterprise Java environments
- ✅ **Comprehensive Ecosystem**: Extensive plugin ecosystem and documentation
- ✅ **Tool Integration**: Better integration with Spring Boot and enterprise tools
- ✅ **Predictable Builds**: Clear lifecycle phases reduce build complexity

### Negative
- ❌ **Verbosity**: More verbose than Gradle for complex build scenarios
- ❌ **Flexibility**: Less flexible than Gradle for custom build logic
- ❌ **Modern Features**: Slower adoption of modern build features
- ❌ **Performance**: Generally slower builds compared to Gradle (with build cache)

### Neutral
- 🔄 **Learning Curve**: Different from Gradle, but similar complexity overall
- 🔄 **Maintenance**: Requires understanding of Maven-specific concepts

## Implementation

### Project Structure
```
project/
├── pom.xml                    # Maven configuration
├── src/
│   ├── main/java/            # Production code
│   ├── main/resources/       # Configuration files
│   ├── test/java/           # Test code
│   └── test/resources/      # Test resources
├── target/                   # Build output
└── .mvn/                    # Maven wrapper
```

### Key Maven Features Used
- **Dependency Management**: BOM (Bill of Materials) for version management
- **Profiles**: Environment-specific configuration (dev, prod, ci)
- **Plugin Management**: Standardized plugin configuration
- **Maven Wrapper**: Ensures consistent Maven version across environments

## Alternatives Considered

### Gradle
**Pros:**
- More flexible and modern
- Better performance with build cache
- Groovy/Kotlin DSL is powerful
- Incremental builds

**Cons:**
- Additional DSL to learn alongside Java
- More complex for beginners
- Less standardized project structures
- Steeper learning curve for .NET developers

**Decision:** Rejected for educational context - too much cognitive load

### SBT (Scala Build Tool)
**Pros:**
- Very powerful for Scala/Java mixed projects
- Excellent incremental compilation

**Cons:**
- Primarily designed for Scala
- Very steep learning curve
- Not relevant for Java-focused curriculum

**Decision:** Rejected - not appropriate for Java fundamentals

## Monitoring and Review

### Success Metrics
- Student feedback on build tool ease-of-use
- Time-to-first-successful-build for new students
- Number of build-related questions in course forums

### Review Triggers
- If >30% of students request Gradle content
- If industry adoption patterns change significantly
- If Maven becomes deprecated or unsupported

### Future Considerations
- **Gradle Module**: Could add supplementary Gradle content in advanced courses
- **Multi-Build Tool**: Could demonstrate both Maven and Gradle in later curriculum
- **Build Tool Comparison**: Could add section comparing Maven vs. Gradle objectively

## References

- [Maven vs Gradle Performance Comparison](https://gradle.org/maven-vs-gradle/)
- [Spring Boot Build Tool Support](https://spring.io/guides/gs/maven/)
- [Java Developer Survey 2024 - Build Tool Usage](https://www.jetbrains.com/lp/devecosystem-2024/java/)
- [Enterprise Java Development Standards](https://www.oracle.com/java/technologies/java-development-standards.html)

---

**Last Updated**: December 2024  
**Next Review**: June 2025  
**Status**: ✅ Active