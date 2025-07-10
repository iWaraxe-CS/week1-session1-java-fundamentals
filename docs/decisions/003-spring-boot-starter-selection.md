# ADR-003: Spring Boot Starter Selection Strategy

## Status
**ACCEPTED** - December 2024

## Context

Spring Boot provides numerous "starter" dependencies that bundle related libraries together. For an educational project targeting .NET developers learning Java fundamentals, we needed to carefully select which starters to include to balance:

- **Learning Value**: Essential vs. advanced concepts
- **Complexity**: Cognitive load for beginners
- **Real-world Relevance**: Industry standard practices
- **Curriculum Scope**: Week 1 fundamentals focus

## Decision

We chose a **minimal, progressive starter selection** focused on core enterprise patterns that .NET developers would recognize, with clear upgrade paths to more advanced features.

## Selected Starters

### 1. Core Application Framework
```xml
<!-- Web MVC - equivalent to ASP.NET Core MVC -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Validation - equivalent to Data Annotations -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

### 2. Data Access Layer
```xml
<!-- JPA/Hibernate - equivalent to Entity Framework -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### 3. Testing Framework
```xml
<!-- Comprehensive testing - equivalent to .NET test stack -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

### 4. Monitoring and Operations
```xml
<!-- Health checks and metrics - equivalent to .NET health checks -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## Rationale

### For .NET Developers

**Selected starters provide direct equivalents to familiar .NET concepts:**

| Spring Boot Starter | .NET Equivalent | Learning Value |
|-------------------|-----------------|----------------|
| `spring-boot-starter-web` | ASP.NET Core MVC | ⭐⭐⭐ Essential |
| `spring-boot-starter-data-jpa` | Entity Framework Core | ⭐⭐⭐ Essential |
| `spring-boot-starter-validation` | Data Annotations | ⭐⭐⭐ Essential |
| `spring-boot-starter-test` | xUnit + Moq + FluentAssertions | ⭐⭐⭐ Essential |
| `spring-boot-starter-actuator` | Health Checks + Metrics | ⭐⭐ Important |

### Progressive Learning Path

**Week 1 Focus - Fundamentals:**
```java
// 1. Basic Web MVC (familiar to ASP.NET developers)
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    
    @PostMapping
    public User createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
}

// 2. Data access with JPA (similar to Entity Framework)
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
}

// 3. Repository pattern (familiar to .NET developers)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByActiveTrue();
}
```

## Implementation Details

### 1. Web Starter Configuration
```java
// Auto-configures:
// - Embedded Tomcat server (like Kestrel in .NET)
// - Spring MVC framework (like ASP.NET Core MVC)
// - JSON serialization (like System.Text.Json)
// - Error handling (like ProblemDetails)

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        // Similar to WebApplication.CreateBuilder().Build().Run()
    }
}
```

### 2. Data JPA Configuration
```yaml
# application.yml - similar to appsettings.json
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

### 3. Testing Configuration
```java
// Comprehensive testing setup (similar to .NET test projects)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class UserServiceIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @MockBean
    private EmailService emailService;
    
    @Test
    void shouldCreateUserSuccessfully() {
        // Test implementation using familiar patterns
    }
}
```

## Starters Excluded and Why

### ❌ Excluded for Week 1

**Security Starter** - Too complex for fundamentals
```xml
<!-- Excluded: spring-boot-starter-security -->
<!-- Reason: Authentication/authorization is advanced topic -->
<!-- Alternative: Cover in Week 3-4 of curriculum -->
```

**WebFlux Starter** - Reactive programming is advanced
```xml
<!-- Excluded: spring-boot-starter-webflux -->
<!-- Reason: Reactive streams unfamiliar to most .NET developers -->
<!-- Alternative: Traditional MVC is more similar to ASP.NET Core -->
```

**Data MongoDB/Redis** - Non-relational databases are advanced
```xml
<!-- Excluded: spring-boot-starter-data-mongodb -->
<!-- Reason: Focus on familiar relational patterns first -->
<!-- Alternative: JPA/Hibernate similar to Entity Framework -->
```

**Cloud/Microservices Starters** - Distributed systems are advanced
```xml
<!-- Excluded: spring-cloud-starter-* -->
<!-- Reason: Microservices patterns need foundational knowledge first -->
<!-- Alternative: Monolithic approach for learning -->
```

### 🔄 Available for Later Weeks

**Advanced starters can be added progressively:**

```xml
<!-- Week 3: Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- Week 4: Caching -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>

<!-- Week 5: Messaging -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

## Benefits Realized

### 1. Reduced Complexity
- **Minimal Dependencies**: Only essential starters included
- **Clear Learning Path**: Each starter has obvious .NET equivalent
- **Fast Feedback**: Applications start quickly, encouraging experimentation

### 2. Industry Relevance
- **Standard Stack**: Selected starters are enterprise standard
- **Job Market Preparation**: Skills directly transferable to workplace
- **Best Practices**: Auto-configuration demonstrates Spring conventions

### 3. .NET Developer Familiarity
```java
// Spring Boot patterns match .NET Core patterns
@Service
@RequiredArgsConstructor  // Similar to DI constructor injection
public class UserService {
    private final UserRepository userRepository;  // Like IUserRepository
    private final EmailService emailService;      // Like IEmailService
    
    @Transactional  // Similar to TransactionScope
    public User createUser(CreateUserRequest request) {
        // Business logic similar to .NET service patterns
    }
}
```

## Configuration Strategy

### 1. Sensible Defaults
```java
// Minimal configuration required (convention over configuration)
@SpringBootApplication  // Equivalent to Program.cs + Startup.cs
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 2. Explicit When Educational
```java
// Make implicit configuration explicit for learning
@Configuration
public class DatabaseConfig {
    
    @Bean
    @Profile("dev")
    public DataSource developmentDataSource() {
        // Show explicit configuration for educational value
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }
}
```

### 3. Progressive Enhancement
```yaml
# Start simple, add complexity gradually
# Week 1: Basic configuration
spring:
  application:
    name: java-fundamentals

# Week 2: Add profiles
spring:
  profiles:
    active: dev

# Week 3: Add advanced features
spring:
  security:
    oauth2:
      client: # ... when ready
```

## Consequences

### Positive
- ✅ **Fast Learning**: Students see working applications immediately
- ✅ **Industry Standard**: Skills directly applicable to real projects
- ✅ **Familiar Patterns**: Each starter maps to known .NET concepts
- ✅ **Progressive Complexity**: Can add more starters as learning progresses
- ✅ **Auto-configuration**: Demonstrates Spring Boot's "magic" appropriately

### Negative
- ❌ **Limited Scope**: Some important features deferred to later curriculum
- ❌ **Magic Dependency**: Auto-configuration can hide important concepts
- ❌ **Opinionated**: May not match all enterprise configurations

### Mitigations
- 🔧 **Documentation**: Explain what each starter provides
- 🔧 **Configuration Examples**: Show both auto and manual configuration
- 🔧 **Progressive Disclosure**: Add complexity gradually over curriculum

## Monitoring and Review

### Success Metrics
- **Time to First Success**: Students create working web app < 30 minutes
- **Conceptual Understanding**: Students map Spring concepts to .NET equivalents
- **Confidence**: Students feel comfortable adding new features

### Review Triggers
- Spring Boot major version updates
- Student feedback indicating confusion or missing features
- Industry standard practices evolution
- Curriculum expansion to additional weeks

## Future Evolution

### Planned Additions
1. **Week 3**: Security starter for authentication/authorization
2. **Week 4**: Cache starter for performance patterns
3. **Week 5**: Messaging starter for async communication
4. **Week 6**: Cloud starters for deployment patterns

### Alternative Approaches
- **Modular Approach**: Separate projects for each major starter
- **All-Inclusive**: Include all starters with feature flags
- **Build-Your-Own**: Students add starters based on requirements

## References

- [Spring Boot Starters Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters)
- [ASP.NET Core to Spring Boot Migration Guide](https://docs.microsoft.com/en-us/dotnet/architecture/porting-existing-aspnet-apps/)
- [Spring Boot Auto-Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration)
- [Enterprise Java Development Patterns](https://spring.io/guides/gs/rest-service/)

---

**Last Updated**: December 2024  
**Next Review**: March 2025  
**Status**: ✅ Active