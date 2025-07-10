# Student Handbook - Java Fundamentals for .NET Developers

## Welcome to Java! 👋

You're an experienced .NET developer, which means you already have 80% of the skills needed for Java development. This handbook will help you bridge the remaining 20% efficiently.

## Your Learning Journey

### What You Already Know (Leverage This!)
- **Object-Oriented Programming**: Classes, inheritance, interfaces, polymorphism
- **Collections**: Lists, dictionaries, sets, LINQ operations
- **Dependency Injection**: Constructor injection, service lifetimes
- **Testing**: Unit tests, mocking, assertions
- **Web APIs**: REST controllers, routing, middleware
- **Enterprise Patterns**: Repository pattern, service layers, DTOs

### What's Different in Java (Focus Here!)
- **Syntax Variations**: Package imports, method signatures, access modifiers
- **Type System**: Primitives vs objects, wrapper classes, generics syntax
- **String Handling**: `.equals()` instead of `==`, `StringBuilder` usage
- **Build Tools**: Maven instead of MSBuild/NuGet
- **Framework**: Spring Boot instead of ASP.NET Core

## Self-Study Schedule

### Week 1: Core Language Transition (8-10 hours)
**Day 1-2: Environment & Basic Syntax (3 hours)**
- [ ] Set up development environment (IntelliJ IDEA + Java 17+)
- [ ] Complete "Hello World" and basic class creation
- [ ] Practice package structure and imports

**Day 3-4: Types & Collections (3 hours)**
- [ ] Master primitive types vs wrapper classes
- [ ] Practice collections operations (List, Map, Set)
- [ ] Learn string comparison patterns

**Day 5-7: OOP & Advanced Features (4 hours)**
- [ ] Build inheritance hierarchies
- [ ] Practice interface implementation
- [ ] Introduce Lombok for boilerplate reduction

## Daily Learning Routine

### Morning Warmup (15 minutes)
```java
// Daily coding kata - translate this C# to Java
public class User 
{
    public int Id { get; set; }
    public string Name { get; set; }
    public List<string> Roles { get; set; } = new();
}
```

### Core Learning Block (45 minutes)
1. **Read** one concept from guides (10 min)
2. **Code** the examples yourself (20 min)
3. **Exercise** on the topic (15 min)

### Evening Review (15 minutes)
- Review what you learned
- Note differences from .NET
- Plan tomorrow's focus area

## Checkpoint System

### Checkpoint 1: Basic Java Syntax ✅
**Can you confidently:**
- [ ] Create Java classes with proper naming conventions
- [ ] Use correct access modifiers (public, private, protected, package-private)
- [ ] Import classes and packages correctly
- [ ] Handle basic I/O operations

**Self-Test:**
```java
// Create a Book class with:
// - Private fields: id (Long), title (String), authors (List<String>)
// - Public constructor taking all parameters
// - Getters and setters for all fields
// - Override toString() method
```

### Checkpoint 2: Collections & Generics ✅
**Can you confidently:**
- [ ] Use List<T>, Map<K,V>, Set<T> effectively
- [ ] Understand difference between ArrayList and LinkedList
- [ ] Iterate collections using enhanced for-loops
- [ ] Apply basic stream operations

**Self-Test:**
```java
// Given a List<User>, write methods to:
// 1. Find all users with a specific role
// 2. Group users by their primary role
// 3. Get the names of active users sorted alphabetically
```

### Checkpoint 3: OOP Patterns ✅
**Can you confidently:**
- [ ] Create abstract classes and interfaces
- [ ] Implement inheritance hierarchies correctly
- [ ] Use polymorphism effectively
- [ ] Apply proper encapsulation

**Self-Test:**
```java
// Design a vehicle hierarchy:
// - Abstract Vehicle class with common properties
// - Car and Motorcycle implementations
// - Drivable interface with common behaviors
// - Demonstrate polymorphism with a collection
```

### Checkpoint 4: Lombok & Modern Java ✅
**Can you confidently:**
- [ ] Use @Data, @Builder, @RequiredArgsConstructor annotations
- [ ] Understand when to use Records vs classes
- [ ] Configure Lombok in IDE and build tools
- [ ] Balance Lombok usage appropriately

**Self-Test:**
```java
// Convert a traditional Java class with 50+ lines
// to Lombok-annotated class with <10 lines
// while maintaining same functionality
```

### Checkpoint 5: Spring Boot Basics ✅
**Can you confidently:**
- [ ] Create a Spring Boot application from scratch
- [ ] Implement REST controllers with proper annotations
- [ ] Use dependency injection with constructor injection
- [ ] Configure application properties

**Self-Test:**
```java
// Create a complete REST API for managing Books:
// - BookController with CRUD endpoints
// - BookService with business logic
// - Book entity with proper annotations
// - Proper error handling and validation
```

## Learning Resources by Topic

### Environment Setup
**Required Reading:**
- 📖 [Getting Started Guide](getting-started.md)
- 🎯 Practice: Set up "Hello World" project

**Common Issues:**
- JDK version conflicts → Use Java 17+
- Lombok not working → Install IDE plugin
- Maven build failures → Check internet connection

### Type System Translation
**Required Reading:**
- 📖 [Java Fundamentals Guide](guides/java-fundamentals.md)
- 🎯 Practice: [Type Conversion Exercise](../src/main/java/com/coherentsolutions/session1/exercises/)

**Key Differences:**
```java
// C# nullable types
int? nullableInt = null;
string? nullableString = null;

// Java wrapper classes
Integer nullableInt = null;
String nullableString = null; // String is already nullable

// C# value types
struct Point { public int X, Y; }

// Java - no value types, use classes or records
record Point(int x, int y) { }
```

### Collections Mastery
**Required Reading:**
- 📖 [Collections and Streams Guide](guides/collections-and-streams.md)
- 🎯 Practice: [Collections Exercise](../src/main/java/com/coherentsolutions/session1/exercises/collections/)

**Mental Model:**
```java
// Interface Types (prefer these)
List<String> names = new ArrayList<>();     // C#: IList<string>
Map<String, User> users = new HashMap<>();  // C#: IDictionary<string, User>
Set<String> roles = new HashSet<>();        // C#: ISet<string>

// Immutable versions
List<String> immutableNames = List.of("John", "Jane");        // C#: ImmutableList
Map<String, String> immutableMap = Map.of("key", "value");    // C#: ImmutableDictionary
```

### Spring Boot Transition
**Required Reading:**
- 📖 [Dependency Injection Guide](guides/dependency-injection.md)
- 🎯 Practice: [Spring Demo](../src/main/java/com/coherentsolutions/session1/demos/SpringDemo.java)

**Framework Mapping:**
```java
// ASP.NET Core
[ApiController]
[Route("api/[controller]")]
public class UsersController : ControllerBase
{
    private readonly IUserService _userService;
    public UsersController(IUserService userService) => _userService = userService;
}

// Spring Boot
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
```

## Practice Exercises by Difficulty

### Beginner Level 🟢
1. **Product Converter**: Convert C# Product class to Java
2. **String Puzzle**: Fix string comparison bugs
3. **Collections Basics**: Implement shopping cart operations

### Intermediate Level 🟡
4. **User Service**: Build complete user management with validation
5. **File Processor**: Handle file I/O and error handling
6. **Movie Catalog**: Implement search and filtering

### Advanced Level 🔴
7. **Library System**: Full CRUD application with Spring Boot
8. **Banking API**: Complex business rules with testing
9. **E-commerce Platform**: Microservice architecture patterns

## Study Groups & Community

### Online Communities
- **Stack Overflow**: Tag your questions with `java` and `spring-boot`
- **r/learnjava**: Reddit community for Java learners
- **Java Discord**: Real-time help and discussions
- **LinkedIn Learning**: Structured Java courses

### Local Groups
- Java User Groups (JUGs) in your area
- Spring meetups and conferences
- Developer bootcamps and workshops

### Code Review Partners
Find a study buddy for:
- Code review sessions
- Pair programming practice
- Knowledge sharing discussions

## Common Pitfalls & Solutions

### Pitfall 1: String Comparison
```java
// ❌ Wrong (C# habit)
if (name == "John") { }

// ✅ Correct (Java way)
if ("John".equals(name)) { }  // Null-safe
if (Objects.equals(name, "John")) { }  // Even safer
```

### Pitfall 2: Null Handling
```java
// ❌ Wrong (NullPointerException waiting to happen)
String upperName = user.getName().toUpperCase();

// ✅ Correct (defensive programming)
String upperName = Optional.ofNullable(user.getName())
    .map(String::toUpperCase)
    .orElse("UNKNOWN");
```

### Pitfall 3: Collection Initialization
```java
// ❌ Wrong (concrete types)
ArrayList<String> names = new ArrayList<>();

// ✅ Correct (interface types)
List<String> names = new ArrayList<>();

// ✅ Even better (immutable when possible)
List<String> names = List.of("John", "Jane", "Bob");
```

## Progress Tracking

### Weekly Goals
**Week 1:**
- [ ] Complete environment setup
- [ ] Pass all basic syntax checkpoints
- [ ] Build first Java application

**Week 2:**
- [ ] Master collections and streams
- [ ] Complete intermediate exercises
- [ ] Start Spring Boot project

**Week 3:**
- [ ] Build complete REST API
- [ ] Write comprehensive tests
- [ ] Deploy to cloud platform

### Monthly Milestones
**Month 1: Foundation**
- Comfortable with Java syntax and collections
- Built several small applications
- Understanding of Spring Boot basics

**Month 2: Proficiency**
- Can build complete web applications
- Confident with testing frameworks
- Understanding of Java ecosystem

**Month 3: Expertise**
- Contributing to Java projects
- Mentoring other .NET developers transitioning
- Exploring advanced Java frameworks

## Next Steps After This Course

### Immediate Next Learning (Weeks 2-4)
1. **Advanced Spring Boot**: Security, data access, caching
2. **Testing Deep Dive**: JUnit 5, Mockito, integration testing
3. **Build & Deployment**: Docker, CI/CD, cloud platforms

### Medium-term Goals (Months 2-6)
1. **Microservices**: Spring Cloud, distributed systems
2. **Performance**: JVM tuning, profiling, optimization
3. **Data Access**: JPA/Hibernate, database design

### Long-term Expertise (6+ months)
1. **Architecture**: Design patterns, clean architecture
2. **DevOps**: Kubernetes, monitoring, observability
3. **Team Leadership**: Mentoring, technical direction

## Support & Help

### When You're Stuck
1. **Check the troubleshooting guide**: Common issues and solutions
2. **Review the anti-patterns guide**: Avoid common mistakes
3. **Ask specific questions**: Include code samples and error messages
4. **Use debugging tools**: IntelliJ debugger, logging

### How to Ask for Help
**❌ Poor question:**
"My code doesn't work, help!"

**✅ Good question:**
"I'm trying to convert this C# LINQ query to Java Streams, but getting a compilation error on line 15. Here's my code... I expected X but got Y."

### Office Hours
- **Virtual office hours**: Every Tuesday/Thursday 2-3 PM
- **Code review sessions**: Submit code for feedback
- **Study group**: Weekly peer learning sessions

---

## Personal Learning Plan Template

### My Background
- Years of .NET experience: ____
- Primary C# version used: ____
- Frameworks I know: ____
- Areas I want to focus on: ____

### My Goals
**Short-term (1 month):**
- [ ] ________________________________
- [ ] ________________________________
- [ ] ________________________________

**Medium-term (3 months):**
- [ ] ________________________________
- [ ] ________________________________
- [ ] ________________________________

**Long-term (6+ months):**
- [ ] ________________________________
- [ ] ________________________________
- [ ] ________________________________

### My Schedule
**Daily commitment**: _____ minutes
**Best learning time**: _____
**Preferred learning style**: _____

### My Progress
**Week 1 completed**: ___/___/20__
**Week 2 completed**: ___/___/20__
**Week 3 completed**: ___/___/20__

**Confidence level (1-10):**
- Java syntax: ____
- Collections: ____
- Spring Boot: ____
- Overall: ____

---

**Remember**: You're not learning programming from scratch - you're translating your existing expertise to a new platform. Trust your experience and focus on the differences, not the similarities!

Happy coding! ☕