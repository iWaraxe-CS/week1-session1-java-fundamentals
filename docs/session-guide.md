# Session Guide
## Week 1 Session 1: Java Fundamentals for .NET Developers

> **Complete 2-3 hour learning experience** covering Java fundamentals with constant .NET comparisons, hands-on exercises, and practical application building.

## 🎯 Session Overview

### What You'll Build Today
By the end of this session, you'll have created a complete Java application demonstrating:
- ✅ **Modern Java syntax** and conventions
- ✅ **Spring Boot REST API** with dependency injection
- ✅ **Data processing** using Collections and Streams
- ✅ **Comprehensive testing** with JUnit 5 and Mockito
- ✅ **Maven project structure** following best practices

### Session Structure
| Phase | Topic | Duration | Activity Type |
|-------|-------|----------|---------------|
| **Phase 1** | Environment & Syntax | 30 min | Setup + Live Demo |
| **Phase 2** | Collections & Streams | 45 min | Interactive Coding |
| **Phase 3** | Spring Boot Basics | 45 min | Guided Building |
| **Phase 4** | Testing & Best Practices | 30 min | TDD Exercise |
| **Phase 5** | Integration & Review | 15 min | Discussion & Q&A |

---

## 📚 Phase 1: Environment & Java Syntax (30 minutes)

### Objective
Understand fundamental Java syntax differences from C# and set up a working development environment.

### 🚀 Quick Start (5 minutes)

**Verify your setup** (if not done already):
```bash
# Verify Java 17+
java -version

# Build the project
./mvnw clean compile

# Run first demo
./mvnw exec:java -Dexec.mainClass="com.coherentsolutions.session1.demos.BasicJavaDemo"
```

### 🔍 Live Demo: Basic Java vs C# (15 minutes)

**Open:** [`src/main/java/.../demos/BasicJavaDemo.java`](../src/main/java/com/coherentsolutions/session1/demos/BasicJavaDemo.java)

#### Key Concepts to Observe:

**1. Package and Import Differences**
```java
// Java: Lowercase packages (reverse domain)
package com.coherentsolutions.session1.demos;

// Java: Explicit imports
import java.util.List;
import java.util.Arrays;
```

```csharp
// C#: PascalCase namespaces
namespace CoherentSolutions.Session1.Demos

// C#: Using statements
using System.Collections.Generic;
using System.Linq;
```

**2. Method Naming Conventions**
```java
// Java: camelCase methods
public String getUserName() { }
public void setUserName(String name) { }
```

```csharp
// C#: PascalCase methods
public string GetUserName() { }
public void SetUserName(string name) { }
```

**3. String Handling Differences**
```java
// Java: Use .equals() for comparison
String name1 = "John";
String name2 = "John";
if (name1.equals(name2)) { // Correct
    System.out.println("Names match");
}
```

```csharp
// C#: == operator is overloaded
string name1 = "John";
string name2 = "John";
if (name1 == name2) { // Works correctly
    Console.WriteLine("Names match");
}
```

### 💡 Interactive Exercise: Syntax Translation (10 minutes)

**Exercise 1.1:** Convert this C# code to Java
```csharp
public class Product
{
    public int Id { get; init; }
    public string Name { get; set; }
    public decimal Price { get; set; }
    public List<string> Tags { get; } = new();
    
    public bool IsExpensive => Price > 100m;
    
    public override string ToString() => $"{Name}: ${Price}";
}
```

**Solution Discussion Points:**
- No `init` accessors in Java (use constructor or builder)
- `BigDecimal` vs `decimal` for money calculations
- Getters/setters vs properties
- String formatting differences

**👥 Pair Activity:** Work with a partner to identify 3 key differences

---

## 📦 Phase 2: Collections & Streams (45 minutes)

### Objective
Master Java Collections Framework and Streams API as LINQ equivalents.

### 🎬 Live Demo: Collections Framework (20 minutes)

**Open:** [`src/main/java/.../demos/CollectionsDemo.java`](../src/main/java/com/coherentsolutions/session1/demos/CollectionsDemo.java)

#### Collection Type Selection Guide

| Use Case | .NET Choice | Java Choice | Why? |
|----------|-------------|-------------|------|
| **Ordered list, frequent access** | `List<T>` | `ArrayList<T>` | Random access performance |
| **Ordered list, frequent insert/delete** | `LinkedList<T>` | `LinkedList<T>` | Insert/delete performance |
| **Unique items, fast lookup** | `HashSet<T>` | `HashSet<T>` | O(1) lookup time |
| **Key-value pairs, fast lookup** | `Dictionary<K,V>` | `HashMap<K,V>` | O(1) key lookup |
| **Sorted unique items** | `SortedSet<T>` | `TreeSet<T>` | Automatic sorting |

#### Hands-On Activity: Collection Operations
```java
// Create and populate collections
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");
Set<String> uniqueNames = new HashSet<>(names);
Map<String, Integer> nameLengths = new HashMap<>();

// Practice different operations
for (String name : names) {
    nameLengths.put(name, name.length());
}
```

### 🌊 Interactive Session: Streams API (25 minutes)

**Open:** [`src/main/java/.../demos/StreamsDemo.java`](../src/main/java/com/coherentsolutions/session1/demos/StreamsDemo.java)

#### LINQ to Streams Translation Table

| LINQ Operation | Stream Equivalent | Example |
|----------------|-------------------|---------|
| `Where(x => x > 5)` | `filter(x -> x > 5)` | Filter elements |
| `Select(x => x.Name)` | `map(x -> x.getName())` | Transform elements |
| `OrderBy(x => x.Age)` | `sorted(Comparator.comparing(X::getAge))` | Sort elements |
| `GroupBy(x => x.Type)` | `collect(groupingBy(X::getType))` | Group elements |
| `Count()` | `count()` | Count elements |
| `ToList()` | `collect(toList())` | Collect to list |

#### 💻 Live Coding Exercise: Data Processing

**Scenario:** Process employee data using Streams

```java
// Sample data
List<Employee> employees = Arrays.asList(
    new Employee("Alice", "Engineering", 85000, 5),
    new Employee("Bob", "Sales", 55000, 2),
    new Employee("Charlie", "Engineering", 95000, 7)
);

// Task: Find average salary by department for employees with 3+ years experience
Map<String, Double> avgSalaryByDept = employees.stream()
    .filter(emp -> emp.getYearsExperience() >= 3)
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingDouble(emp -> emp.getSalary())
    ));
```

**🔄 Your Turn:** Implement these requirements:
1. Find all engineers earning over $80,000
2. Get list of all unique departments
3. Calculate total company payroll
4. Find the highest-paid employee in each department

---

## 🚀 Phase 3: Spring Boot Basics (45 minutes)

### Objective
Build a REST API using Spring Boot with proper dependency injection patterns.

### 🏗️ Guided Building: REST API (30 minutes)

**Open:** [`src/main/java/.../demos/SpringDemo.java`](../src/main/java/com/coherentsolutions/session1/demos/SpringDemo.java)

#### Step 1: Understanding Spring Annotations

**Spring vs .NET Core Mapping:**
```java
// Spring Boot Application
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
```

```csharp
// .NET Core Program.cs
var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
var app = builder.Build();
app.MapControllers();
app.Run();
```

#### Step 2: Dependency Injection Patterns

**❌ Don't do this (.NET style):**
```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository; // Field injection
}
```

**✅ Do this (Java best practice):**
```java
@Service
@RequiredArgsConstructor  // Lombok generates constructor
public class UserService {
    private final UserRepository userRepository; // Constructor injection
}
```

#### Step 3: REST Controller Implementation

**Build together:**
```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
    }
    
    @PostMapping
    public User createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request.getName(), request.getEmail());
    }
}
```

### 🧪 Testing Integration (15 minutes)

**Open:** [`src/main/java/.../demos/TestingDemo.java`](../src/main/java/com/coherentsolutions/session1/demos/TestingDemo.java)

#### Testing Framework Comparison

| .NET | Java | Purpose |
|------|------|---------|
| `xUnit/NUnit/MSTest` | `JUnit 5` | Test framework |
| `Moq/NSubstitute` | `Mockito` | Mocking framework |
| `FluentAssertions` | `AssertJ` | Fluent assertions |
| `[Fact]`/`[Test]` | `@Test` | Test method marker |

#### Live Test Writing

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    @DisplayName("Should return user when valid ID provided")
    void shouldReturnUserWhenValidId() {
        // Given (Arrange)
        Long userId = 1L;
        User expectedUser = new User(userId, "John", "john@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        
        // When (Act)
        Optional<User> result = userService.findById(userId);
        
        // Then (Assert)
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John");
        verify(userRepository).findById(userId);
    }
}
```

---

## 🧪 Phase 4: Testing & Best Practices (30 minutes)

### Objective
Apply TDD principles and avoid common anti-patterns.

### 🚫 Anti-Pattern Recognition (15 minutes)

**Review:** [`dotnet-to-java-antipatterns.md`](../dotnet-to-java-antipatterns.md)

#### Quick Anti-Pattern Quiz

**Problem 1:** What's wrong with this code?
```java
String name1 = "John";
String name2 = new String("John");
if (name1 == name2) {
    System.out.println("Names match");
}
```

**Problem 2:** Fix this null handling:
```java
public User findUser(Long id) {
    return userMap.get(id); // May return null
}
```

**Problem 3:** Improve this collection initialization:
```java
List<String> names = new ArrayList<String>() { "John", "Jane" }; // Syntax error
```

### 💪 Hands-On Exercise: Build a Feature (15 minutes)

**Exercise:** Implement a Product Service with TDD

**Requirements:**
1. Create Product entity with name, price, category
2. Implement ProductService with CRUD operations
3. Add validation (price > 0, name not empty)
4. Write comprehensive tests

**Starter Code:**
```java
// 1. Start with the test
@Test
void shouldCreateProductWithValidData() {
    // Given
    CreateProductRequest request = new CreateProductRequest("Laptop", new BigDecimal("999.99"), "Electronics");
    
    // When
    Product result = productService.createProduct(request);
    
    // Then
    assertThat(result.getName()).isEqualTo("Laptop");
    assertThat(result.getPrice()).isEqualTo(new BigDecimal("999.99"));
}

// 2. Then implement the service
@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public Product createProduct(CreateProductRequest request) {
        // TODO: Implement validation and creation
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
```

**🔄 Your Implementation:** Write the service and make tests pass

---

## 🔗 Phase 5: Integration & Review (15 minutes)

### Objective
Consolidate learning and plan next steps.

### 🎯 Learning Verification

**Knowledge Check:**
1. **Syntax:** Can you convert a C# class to Java following conventions?
2. **Collections:** When would you choose ArrayList vs LinkedList vs HashSet?
3. **Streams:** Convert this LINQ: `users.Where(u => u.Age > 18).Select(u => u.Name).ToList()`
4. **Spring:** Explain why constructor injection is preferred over field injection
5. **Testing:** Write a test that mocks a repository and verifies behavior

### 🚀 What You've Accomplished

**Skills Acquired:**
- ✅ **Java syntax mastery** with .NET comparisons
- ✅ **Collections expertise** for efficient data handling
- ✅ **Streams proficiency** for functional programming
- ✅ **Spring Boot basics** for enterprise applications
- ✅ **Testing competency** with modern frameworks
- ✅ **Anti-pattern awareness** to write quality code

**Practical Outcomes:**
- ✅ **Working Java environment** properly configured
- ✅ **Complete project** demonstrating best practices
- ✅ **REST API implementation** using Spring patterns
- ✅ **Comprehensive test suite** with mocking
- ✅ **Maven project structure** following conventions

### 📝 Action Items

**Immediate (This Week):**
1. Complete all exercises in [`exercises/`](../src/main/java/com/coherentsolutions/session1/exercises/) directory
2. Review and run all reference classes in [`reference/`](../src/main/java/com/coherentsolutions/session1/reference/) directory
3. Fix all anti-patterns in [`antipatterns/`](../src/main/java/com/coherentsolutions/session1/antipatterns/) directory
4. Build a small Spring Boot application using learned patterns

**Short-term (Next 2 Weeks):**
1. Read advanced guides in [`docs/guides/`](guides/) directory
2. Explore Spring Boot documentation and tutorials
3. Practice TDD with more complex scenarios
4. Join Java development communities (Reddit, Discord, Stack Overflow)

**Long-term (Next Month):**
1. Build a complete application with database integration
2. Learn advanced Spring features (Security, Data JPA)
3. Explore Java ecosystem tools and frameworks
4. Consider Java certifications or advanced courses

---

## 📚 Resource Summary

### Essential Files Created Today
| Resource | Purpose | When to Use |
|----------|---------|-------------|
| **[Anti-Patterns Guide](../dotnet-to-java-antipatterns.md)** | Common mistakes & fixes | When writing Java code |
| **[Quick Reference](../quick-reference-conversions.md)** | C# to Java syntax | When translating concepts |
| **[Exercise Solutions](../src/main/java/com/coherentsolutions/session1/exercises/)** | Hands-on practice | For skill building |
| **[Demo Classes](../src/main/java/com/coherentsolutions/session1/demos/)** | Live coding examples | For understanding patterns |
| **[Reference Materials](../src/main/java/com/coherentsolutions/session1/reference/)** | Comprehensive guides | For deep understanding |

### Advanced Learning Paths
1. **[Java Fundamentals Deep Dive](guides/java-fundamentals.md)** - Language mastery
2. **[Collections & Streams Guide](guides/collections-and-streams.md)** - Data processing expertise
3. **[Dependency Injection Guide](guides/dependency-injection.md)** - Spring mastery
4. **[Testing Frameworks Guide](guides/testing-frameworks.md)** - Quality assurance
5. **[Build & Project Structure](guides/build-and-project-structure.md)** - Maven expertise

### Community Resources
- **Stack Overflow:** [java], [spring-boot], [maven] tags
- **Reddit:** r/learnjava, r/SpringBoot
- **Discord:** Java Discord Community
- **GitHub:** Spring Boot examples and tutorials
- **Baeldung:** Comprehensive Java and Spring tutorials

---

## 🎓 Congratulations!

You've completed Week 1 Session 1 of your Java journey! You now have:

### 🏆 Core Competencies
- **Java syntax fluency** with .NET context
- **Collections and Streams mastery** for data processing
- **Spring Boot fundamentals** for enterprise development
- **Testing competency** with modern frameworks
- **Anti-pattern awareness** for quality code

### 🔧 Practical Skills
- Set up and configure Java development environments
- Build and test Maven projects
- Create REST APIs with Spring Boot
- Write comprehensive tests with JUnit 5 and Mockito
- Apply dependency injection patterns correctly

### 🚀 Next Steps
You're ready for Week 2: Advanced Java Features, which will cover:
- Generics and type safety
- Annotation processing
- Advanced Spring Boot configuration
- Database integration with JPA
- Exception handling strategies

---

## 💬 Session Feedback

### Discussion Questions
1. What surprised you most about Java compared to C#?
2. Which concept do you find most challenging to transition from .NET?
3. What Java feature do you find most appealing?
4. How do you feel about Java's verbosity compared to C#?

### Self-Assessment
Rate your confidence (1-5) in:
- [ ] Java syntax and conventions
- [ ] Collections Framework usage
- [ ] Streams API for data processing
- [ ] Spring Boot application development
- [ ] Testing with JUnit and Mockito

**Well done! Your Java journey is off to a great start! 🚀**

> *"The expert in anything was once a beginner who refused to give up."*