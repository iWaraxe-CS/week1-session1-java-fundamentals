# ADR-002: Lombok for Boilerplate Reduction

## Status
**ACCEPTED** - December 2024

## Context

Java requires significant boilerplate code for common patterns like getters, setters, constructors, and equals/hashCode methods. This is particularly jarring for .NET developers accustomed to auto-properties and records. We needed to decide whether to:

- Use **Traditional Java** with explicit getters/setters
- Use **Lombok** annotations to reduce boilerplate
- Use **Java Records** (Java 14+) for immutable data
- Mix approaches based on use case

## Decision

We chose to use **Lombok annotations** as the primary approach for reducing boilerplate, with **Java Records** for immutable data transfer objects, while teaching traditional Java patterns for understanding.

## Rationale

### For .NET Developers Learning Java

**Lombok provides immediate productivity because:**

1. **Familiar Developer Experience**
   - Similar to C# auto-properties: `{ get; set; }`
   - Reduces cognitive friction when learning Java
   - Focuses attention on business logic rather than boilerplate

2. **Industry Standard in Enterprise Java**
   - Widely adopted in Spring Boot projects
   - Standard in most modern Java codebases
   - Essential skill for Java developers

3. **Bridges the Gap Between Java and C#**
   - `@Data` ≈ C# auto-properties + ToString + Equals
   - `@Builder` ≈ C# object initializers with validation
   - `@RequiredArgsConstructor` ≈ C# constructor injection patterns

### Educational Benefits

**Before (Traditional Java) - 50+ lines:**
```java
public class User {
    private Long id;
    private String name;
    private String email;
    private boolean active;
    
    public User() {}
    
    public User(Long id, String name, String email, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = active;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return active == user.active &&
                Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, active);
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}
```

**After (Lombok) - 8 lines:**
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private boolean active;
}
```

**Equivalent C# (for comparison) - 6 lines:**
```csharp
public class User
{
    public long Id { get; set; }
    public string Name { get; set; }
    public string Email { get; set; }
    public bool Active { get; set; }
}
```

## Implementation Strategy

### 1. Progressive Introduction
```java
// Week 1: Show traditional Java first (for understanding)
public class UserTraditional {
    private String name;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

// Week 1: Then introduce Lombok (for productivity)
@Data
public class UserLombok {
    private String name;
}
```

### 2. Annotation Selection
```java
// Entity classes - controlled mutability
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Setter(AccessLevel.NONE)  // Immutable ID
    private Long id;
    
    private String name;
    private String email;
}

// DTOs - immutable by default
@Value  // Immutable
@Builder
public class UserDto {
    Long id;
    String name;
    String email;
}

// Service classes - constructor injection
@Service
@RequiredArgsConstructor  // Final fields only
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
}
```

### 3. Alternative: Java Records for Simple Cases
```java
// Java Record (Java 14+) - for immutable data
public record UserRecord(Long id, String name, String email) {
    // Compact constructor for validation
    public UserRecord {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }
}
```

## Consequences

### Positive
- ✅ **Reduced Learning Friction**: .NET developers can focus on Java concepts, not boilerplate
- ✅ **Industry Readiness**: Students learn tools used in real Java projects
- ✅ **Productivity**: 80% less boilerplate code compared to traditional Java
- ✅ **Code Quality**: Generated methods are consistent and bug-free
- ✅ **Maintainability**: Changes to fields automatically update all generated methods

### Negative
- ❌ **Magic Dependency**: Introduces compile-time dependency and IDE plugin requirement
- ❌ **Debugging Complexity**: Generated code not visible in source, harder to debug
- ❌ **Learning Gap**: Students might not understand underlying Java patterns
- ❌ **Tool Dependency**: Requires Lombok plugin in IDE and build configuration

### Mitigations
- 🔧 **Show Both Approaches**: Teach traditional Java first, then introduce Lombok
- 🔧 **IDE Setup**: Provide clear instructions for Lombok plugin installation
- 🔧 **Generated Code Understanding**: Use "delombok" to show generated code
- 🔧 **Gradual Introduction**: Start with simple annotations (@Getter, @Setter)

## Best Practices Established

### 1. Annotation Guidelines
```java
// ✅ DO: Use @Data for simple entities
@Data
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
}

// ✅ DO: Use @Value for immutable DTOs
@Value
public class ProductDto {
    Long id;
    String name;
    BigDecimal price;
}

// ✅ DO: Control access with specific annotations
@Getter
@ToString
@EqualsAndHashCode
public class BankAccount {
    @Setter(AccessLevel.NONE)  // Read-only
    private final String accountNumber;
    
    @Setter(AccessLevel.PACKAGE)  // Package-private setter
    private BigDecimal balance;
}

// ❌ DON'T: Use @Data with inheritance (equals/hashCode issues)
@Data
public class Manager extends Employee { // Problematic
    private String department;
}
```

### 2. Constructor Patterns
```java
// ✅ DO: Use @RequiredArgsConstructor for DI
@Service
@RequiredArgsConstructor
public class OrderService {
    private final PaymentService paymentService;  // Final = required
    private final EmailService emailService;      // Final = required
}

// ✅ DO: Use @Builder for complex object creation
@Builder
public class Order {
    private Long id;
    private Customer customer;
    private List<OrderItem> items;
    private OrderStatus status;
    
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
```

### 3. IDE Configuration
```xml
<!-- Maven configuration -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>

<!-- Annotation processor configuration -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.30</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

## Alternatives Considered

### Traditional Java Only
**Pros:**
- No external dependencies
- Complete visibility of generated code
- Pure Java learning experience

**Cons:**
- Massive boilerplate overhead
- Poor developer experience for .NET developers
- Not representative of modern Java development

**Decision:** Rejected - creates unnecessary friction for .NET developers

### Java Records Only
**Pros:**
- Built into modern Java (no dependencies)
- Excellent for immutable data
- Similar to C# records

**Cons:**
- Limited to immutable objects
- Available only in Java 14+
- Doesn't cover all use cases (mutable entities, DI)

**Decision:** Adopted as complement, not replacement

### Manual Code Generation
**Pros:**
- No runtime dependencies
- Full control over generated code

**Cons:**
- Requires additional build steps
- Poor developer experience
- Not standard in Java ecosystem

**Decision:** Rejected - too complex for educational setting

## Success Metrics

### Student Feedback Targets
- **Understanding**: >85% of students understand Lombok purpose after introduction
- **Productivity**: >70% report faster development with Lombok
- **Transition**: >90% can convert between traditional Java and Lombok code

### Code Quality Metrics
- Reduced boilerplate: 70-80% fewer lines of code
- Consistency: Zero manual equals/hashCode bugs
- Maintainability: Faster field additions/modifications

## Future Considerations

### Evolution Path
1. **Java Records Integration**: Increase usage as ecosystem matures
2. **Annotation Expansion**: Consider additional Lombok annotations as needed
3. **Alternative Libraries**: Evaluate competitors like AutoValue, Immutables

### Review Triggers
- Major Lombok version updates
- Java language feature additions (pattern matching, etc.)
- Student feedback indicating confusion or preference changes
- Industry adoption pattern shifts

## References

- [Project Lombok Official Documentation](https://projectlombok.org/)
- [Lombok vs Records Comparison](https://blog.jetbrains.com/idea/2020/03/java-14-and-intellij-idea/)
- [Spring Boot Lombok Integration](https://spring.io/guides/gs/accessing-data-jpa/)
- [Java Boilerplate Reduction Strategies](https://www.baeldung.com/lombok-ide)

---

**Last Updated**: December 2024  
**Next Review**: June 2025  
**Status**: ✅ Active