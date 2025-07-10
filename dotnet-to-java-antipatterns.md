# .NET to Java Anti-Patterns Guide

## Overview
Common mistakes .NET developers make when learning Java, with explanations and corrections.

## 1. String Comparison

### ❌ Anti-Pattern
```java
String name1 = "John";
String name2 = new String("John");
if (name1 == name2) { // WRONG: Compares references
    // This won't work as expected
}
```

### ✅ Correct Pattern
```java
if (name1.equals(name2)) { // Compares values
    // This works correctly
}

// Null-safe comparison
if (Objects.equals(name1, name2)) {
    // Handles null cases
}
```

### Why It Happens
In C#, `==` is overloaded for strings. In Java, `==` always compares references.

## 2. Null Handling

### ❌ Anti-Pattern
```java
public User findUser(Long id) {
    // Returning null like C#
    return userMap.get(id); // May return null
}

// Usage
User user = findUser(123L);
user.getName(); // NullPointerException risk
```

### ✅ Correct Pattern
```java
public Optional<User> findUser(Long id) {
    return Optional.ofNullable(userMap.get(id));
}

// Usage
findUser(123L)
    .map(User::getName)
    .orElse("Unknown");
```

### Why It Happens
C# has nullable reference types (`User?`). Java uses `Optional<T>` for explicit null handling.

## 3. Properties vs Fields

### ❌ Anti-Pattern
```java
public class User {
    public String name; // Public field like C# property
    public int age;
}
```

### ✅ Correct Pattern
```java
// Option 1: Traditional JavaBean
public class User {
    private String name;
    private int age;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

// Option 2: Lombok
@Data
public class User {
    private String name;
    private int age;
}
```

### Why It Happens
C# auto-properties look like fields. Java requires explicit getters/setters.

## 4. Collection Initialization

### ❌ Anti-Pattern
```java
// Trying C# collection initializer syntax
List<String> names = new ArrayList<String>() { "John", "Jane" }; // Syntax error

// Modifying immutable list
List<String> colors = List.of("red", "blue");
colors.add("green"); // UnsupportedOperationException
```

### ✅ Correct Pattern
```java
// Mutable list
List<String> names = new ArrayList<>(Arrays.asList("John", "Jane"));
// or
List<String> names = new ArrayList<>();
names.add("John");
names.add("Jane");

// Immutable list
List<String> colors = List.of("red", "blue"); // Java 9+
// or
List<String> colors = Collections.unmodifiableList(Arrays.asList("red", "blue"));
```

### Why It Happens
C# has collection initializer syntax. Java's syntax is different.

## 5. Exception Handling

### ❌ Anti-Pattern
```java
// Ignoring checked exceptions
public void readFile(String path) {
    FileReader reader = new FileReader(path); // Compilation error
    // Unhandled IOException
}

// Catching and ignoring
try {
    readFile();
} catch (Exception e) {
    // Swallowing exception like C#
}
```

### ✅ Correct Pattern
```java
// Declare or handle
public void readFile(String path) throws IOException {
    try (FileReader reader = new FileReader(path)) {
        // Process file
    }
}

// Proper handling
try {
    readFile(path);
} catch (IOException e) {
    log.error("Failed to read file: {}", path, e);
    throw new ServiceException("File processing failed", e);
}
```

### Why It Happens
C# only has unchecked exceptions. Java has checked exceptions that must be handled.

## 6. LINQ vs Streams

### ❌ Anti-Pattern
```java
// Trying to use Stream like LINQ
List<User> users = getUsers();
Stream<User> adults = users.stream()
    .filter(u -> u.getAge() >= 18);
    
adults.count(); // Stream already consumed
adults.collect(Collectors.toList()); // IllegalStateException
```

### ✅ Correct Pattern
```java
// Streams are consumed once
List<User> users = getUsers();
List<User> adults = users.stream()
    .filter(u -> u.getAge() >= 18)
    .collect(Collectors.toList());

// For reuse, create new stream
long count = users.stream()
    .filter(u -> u.getAge() >= 18)
    .count();
```

### Why It Happens
LINQ queries are reusable. Java Streams are consumed once.

## 7. Async Programming

### ❌ Anti-Pattern
```java
// Trying to use async/await pattern
public async Task<User> GetUserAsync(Long id) { // Syntax error
    await database.FindAsync(id);
}

// Blocking on async operations
CompletableFuture<User> future = fetchUserAsync(id);
User user = future.get(); // Blocks thread
```

### ✅ Correct Pattern
```java
// CompletableFuture for async
public CompletableFuture<User> getUserAsync(Long id) {
    return CompletableFuture.supplyAsync(() -> 
        database.find(id)
    );
}

// Non-blocking composition
getUserAsync(id)
    .thenApply(User::getName)
    .thenAccept(System.out::println);
```

### Why It Happens
C# has async/await keywords. Java uses CompletableFuture or reactive streams.

## 8. Value Types

### ❌ Anti-Pattern
```java
// Trying to create value types
public struct Point { // 'struct' doesn't exist
    int x, y;
}

// Using primitives in generics
List<int> numbers = new ArrayList<int>(); // Compilation error
```

### ✅ Correct Pattern
```java
// Use records (Java 14+) for value-like types
public record Point(int x, int y) {}

// Use wrapper types in generics
List<Integer> numbers = new ArrayList<>();

// Or use primitive collections (third-party)
IntList numbers = new IntArrayList(); // Eclipse Collections
```

### Why It Happens
C# has value types (struct). Java only has reference types and primitives.

## 9. Namespace/Package Organization

### ❌ Anti-Pattern
```java
// C# style namespace
namespace Com.Company.Project { // Syntax error
    public class User {}
}

// Wrong package naming
package Com.Company.Project; // Should be lowercase
```

### ✅ Correct Pattern
```java
// Correct package declaration
package com.company.project;

// File structure must match
// File: src/main/java/com/company/project/User.java
public class User {
    // Implementation
}
```

### Why It Happens
C# uses PascalCase namespaces. Java uses lowercase packages matching reverse domain.

## 10. Dependency Injection

### ❌ Anti-Pattern
```java
@Service
public class UserService {
    @Autowired // Field injection like C# [Inject]
    private UserRepository repository;
    
    @Autowired
    private EmailService emailService;
}
```

### ✅ Correct Pattern
```java
@Service
@RequiredArgsConstructor // Lombok
public class UserService {
    private final UserRepository repository;
    private final EmailService emailService;
}

// Without Lombok
@Service
public class UserService {
    private final UserRepository repository;
    private final EmailService emailService;
    
    public UserService(UserRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }
}
```

### Why It Happens
C# allows property injection. Java best practice is constructor injection.

## Discussion Starters

### For Session 1
1. "What surprised you most about Java strings?"
2. "How do you feel about checked exceptions?"
3. "What C# feature do you miss most already?"

### For Session 2
1. "How does Maven compare to NuGet for you?"
2. "What build tool features matter most?"
3. "How would you organize a multi-module project?"

## Quick Fix Cheat Sheet

| C# Habit | Java Fix |
|----------|----------|
| `string ==` | `.equals()` |
| `null` return | `Optional<T>` |
| Public fields | Private + getters |
| `var` everywhere | Use sparingly |
| Ignore exceptions | Must handle/declare |
| `async/await` | CompletableFuture |
| `List<int>` | `List<Integer>` |
| `PascalCase` packages | `lowercase` packages |