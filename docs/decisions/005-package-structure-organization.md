# ADR-005: Package Structure Organization Strategy

## Status
**ACCEPTED** - December 2024

## Context

Java package organization differs significantly from C# namespace conventions, and .NET developers often struggle with Java package naming and structure. We needed to establish a clear package organization strategy that:

- **Follows Java conventions** (reverse domain, lowercase)
- **Maps clearly to .NET concepts** for easier learning
- **Demonstrates enterprise patterns** used in real Java projects
- **Supports educational progression** from simple to complex

## Decision

We adopted a **hybrid organization strategy** combining **package-by-feature** for business logic with **package-by-layer** for foundational code, using Java standard reverse domain naming.

## Package Structure Design

### 1. Root Package Convention
```java
// Base package follows reverse domain convention
com.coherentsolutions.session1
//  └── com: commercial domain
//      └── coherentsolutions: organization/company
//          └── session1: project/module identifier
```

**Compare to .NET:**
```csharp
// .NET: Company-first hierarchical namespaces
CoherentSolutions.Session1
//  └── CoherentSolutions: Company
//      └── Session1: Project
```

### 2. Educational Package Structure
```
com.coherentsolutions.session1/
├── 📚 reference/              # Educational reference materials
│   ├── TypeConversions.java
│   ├── CollectionExamples.java
│   ├── LinqToStreams.java
│   ├── PropertiesAndLombok.java
│   ├── SpringAnnotations.java
│   ├── TestingExamples.java
│   └── JsonExamples.java
├── 🎬 demos/                  # Live coding demonstrations
│   ├── BasicJavaDemo.java
│   ├── CollectionsDemo.java
│   ├── StreamsDemo.java
│   ├── SpringDemo.java
│   └── TestingDemo.java
├── 💪 exercises/              # Hands-on practice
│   ├── product/
│   ├── stringpuzzle/
│   ├── collections/
│   ├── userservice/
│   └── fileprocessor/
├── 🚫 antipatterns/           # Common mistakes
│   ├── stringcomparison/
│   ├── nullhandling/
│   ├── properties/
│   ├── collections/
│   ├── exceptions/
│   ├── streams/
│   ├── async/
│   ├── valuetypes/
│   ├── packages/
│   └── di/
└── 🏗️ domain/                # Business entities
    ├── User.java
    ├── Product.java
    ├── Order.java
    ├── Event.java
    ├── Movie.java
    └── Ticket.java
```

### 3. Enterprise Package Structure (Example)
```
com.coherentsolutions.ecommerce/        # Enterprise application
├── 🏗️ domain/                          # Domain entities (DDD approach)
│   ├── user/
│   │   ├── User.java                   # Entity
│   │   ├── UserRepository.java         # Domain repository interface
│   │   └── UserService.java            # Domain service
│   ├── product/
│   │   ├── Product.java
│   │   ├── ProductCategory.java
│   │   ├── ProductRepository.java
│   │   └── ProductService.java
│   └── order/
│       ├── Order.java
│       ├── OrderItem.java
│       ├── OrderRepository.java
│       └── OrderService.java
├── 🔧 infrastructure/                   # Technical concerns
│   ├── persistence/
│   │   ├── jpa/                        # JPA implementations
│   │   └── config/                     # Database configuration
│   ├── web/
│   │   ├── controller/                 # REST controllers
│   │   ├── dto/                        # Data transfer objects
│   │   └── config/                     # Web configuration
│   └── messaging/
│       ├── producer/                   # Message producers
│       └── consumer/                   # Message consumers
└── 🎯 application/                      # Application services
    ├── UserApplicationService.java     # Use case orchestration
    ├── ProductApplicationService.java
    └── OrderApplicationService.java
```

## Rationale

### 1. Educational Benefits

**Progressive Learning Structure:**
- **Reference packages**: Isolated examples for focused learning
- **Demo packages**: Live coding materials with clear organization
- **Exercise packages**: Hands-on practice with realistic structure
- **Anti-pattern packages**: Common mistakes with categorized fixes

**Clear .NET Mapping:**
```java
// Java package organization
com.coherentsolutions.session1.demos.BasicJavaDemo
//  └── Similar to .NET: CoherentSolutions.Session1.Demos.BasicJavaDemo

// Java package-by-feature
com.coherentsolutions.session1.exercises.userservice.UserService
//  └── Similar to .NET: CoherentSolutions.Session1.Exercises.UserService.UserService
```

### 2. Enterprise Patterns

**Package-by-Feature for Business Logic:**
```java
// ✅ Package-by-feature (preferred for business domains)
com.coherentsolutions.ecommerce.user/
├── User.java              # Entity
├── UserRepository.java    # Repository interface
├── UserService.java       # Business logic
└── UserController.java    # Web interface

// vs. Package-by-layer (technical grouping)
com.coherentsolutions.ecommerce/
├── entities/
│   └── User.java
├── repositories/
│   └── UserRepository.java
├── services/
│   └── UserService.java
└── controllers/
    └── UserController.java
```

**Benefits of Package-by-Feature:**
- **High Cohesion**: Related code grouped together
- **Clear Boundaries**: Business concepts are isolated
- **Easy Navigation**: All user-related code in one place
- **Modular**: Can extract features to separate modules

### 3. Java Convention Compliance

**Reverse Domain Naming:**
```java
// ✅ Correct: Reverse domain (Java standard)
com.coherentsolutions.session1.user.UserService

// ❌ Wrong: Forward domain (not Java standard)
coherentsolutions.com.session1.user.UserService

// ❌ Wrong: Mixed case (violates Java convention)
Com.CoherentSolutions.Session1.User.UserService
```

**Lowercase Packages:**
```java
// ✅ Correct: All lowercase
com.coherentsolutions.session1.userservice

// ❌ Wrong: CamelCase (C# style)
com.coherentsolutions.session1.UserService

// ❌ Wrong: Mixed case
com.coherentsolutions.session1.userService
```

## Implementation Guidelines

### 1. Package Naming Rules

**Educational Packages:**
```java
// Educational content packages
com.coherentsolutions.session1.reference.*    // Reference materials
com.coherentsolutions.session1.demos.*        // Live demonstrations  
com.coherentsolutions.session1.exercises.*    // Practice exercises
com.coherentsolutions.session1.antipatterns.* // Common mistakes
com.coherentsolutions.session1.domain.*       // Business entities
```

**Enterprise Packages:**
```java
// Business domain packages
com.coherentsolutions.ecommerce.user.*        // User domain
com.coherentsolutions.ecommerce.product.*     // Product domain
com.coherentsolutions.ecommerce.order.*       // Order domain

// Technical packages
com.coherentsolutions.ecommerce.infrastructure.persistence.*
com.coherentsolutions.ecommerce.infrastructure.web.*
com.coherentsolutions.ecommerce.application.*
```

### 2. File Organization Within Packages

**Feature Package Structure:**
```java
// User feature package
com.coherentsolutions.ecommerce.user/
├── User.java                    # Main entity
├── UserRepository.java          # Data access interface
├── UserService.java             # Business logic
├── UserController.java          # Web interface
├── dto/                         # Data transfer objects
│   ├── CreateUserRequest.java
│   ├── UpdateUserRequest.java
│   └── UserResponse.java
├── exception/                   # Domain-specific exceptions
│   ├── UserNotFoundException.java
│   └── UserAlreadyExistsException.java
└── config/                      # Feature-specific configuration
    └── UserConfiguration.java
```

### 3. Import Organization

**Import Grouping (Standard Java Style):**
```java
// 1. Java standard library imports
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// 2. Third-party library imports
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

// 3. Application imports
import com.coherentsolutions.session1.domain.User;
import com.coherentsolutions.session1.exercises.userservice.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    // Implementation
}
```

## .NET Developer Guidance

### 1. Namespace to Package Mapping

| .NET Concept | Java Equivalent | Example |
|-------------|-----------------|---------|
| **Namespace** | Package | `namespace MyApp.Services` → `package myapp.services` |
| **Assembly** | JAR/Module | `MyApp.Services.dll` → `myapp-services.jar` |
| **Project** | Maven Module | `MyApp.Services.csproj` → `myapp-services/pom.xml` |
| **Solution** | Multi-module Project | `MyApp.sln` → Parent `pom.xml` |

### 2. Common .NET to Java Conversions

**.NET Style (DON'T):**
```java
// ❌ Don't use C# naming conventions in Java
package Com.CoherentSolutions.Session1.UserService;  // Wrong case
//or
package CoherentSolutions.Session1.UserService;      // Wrong structure
```

**Java Style (DO):**
```java
// ✅ Use Java conventions
package com.coherentsolutions.session1.userservice;  // Correct
```

### 3. Package Access vs Internal

**.NET Internal Access:**
```csharp
// C#: internal keyword for assembly-scoped access
internal class UserValidator
{
    internal bool ValidateUser(User user) { }
}
```

**Java Package Access:**
```java
// Java: Package-private (default) for package-scoped access
class UserValidator {  // Package-private class
    
    boolean validateUser(User user) {  // Package-private method
        return true;
    }
}
```

## Package Organization Anti-Patterns

### ❌ Anti-Pattern 1: C# Namespace Style
```java
// ❌ Wrong: Using C# naming conventions
package CoherentSolutions.Session1.UserService;
package Com.CoherentSolutions.Session1.UserService;
package coherentsolutions.session1.UserService;  // Mixed case
```

### ❌ Anti-Pattern 2: Deep Package Hierarchies
```java
// ❌ Wrong: Too deep package structure
com.coherentsolutions.session1.infrastructure.persistence.jpa.repositories.user.impl.UserRepositoryImpl
//  └── Too many levels, hard to navigate
```

### ❌ Anti-Pattern 3: Generic Package Names
```java
// ❌ Wrong: Non-descriptive package names
com.coherentsolutions.session1.utils         // Too generic
com.coherentsolutions.session1.common        // Vague purpose
com.coherentsolutions.session1.helpers       // No clear responsibility
```

### ✅ Correct Approaches
```java
// ✅ Correct: Clear, purposeful packages
com.coherentsolutions.session1.user.validation    // Specific purpose
com.coherentsolutions.session1.order.calculation  // Business domain
com.coherentsolutions.session1.infrastructure.email  // Technical domain
```

## Benefits and Trade-offs

### Positive Consequences
- ✅ **Clear Learning Path**: Educational structure guides progression
- ✅ **Industry Standard**: Follows Java enterprise conventions
- ✅ **Maintainable**: Features are cohesively organized
- ✅ **Scalable**: Structure supports growth from simple to complex
- ✅ **.NET Familiar**: Concepts map clearly to namespace patterns

### Negative Consequences
- ❌ **Initial Confusion**: Different from .NET naming conventions
- ❌ **Verbosity**: Longer package names than C# namespaces
- ❌ **Rigid Structure**: Less flexible than C# namespace organization

### Mitigations
- 🔧 **Documentation**: Clear mapping guides for .NET developers
- 🔧 **Examples**: Side-by-side comparisons with C# equivalents
- 🔧 **IDE Setup**: Configure imports and package templates
- 🔧 **Gradual Introduction**: Start simple, add complexity progressively

## Future Evolution

### Planned Enhancements
1. **Microservice Packages**: Multi-module structure for distributed systems
2. **Domain-Driven Design**: Advanced package organization for complex domains
3. **Hexagonal Architecture**: Port and adapter package structures

### Alternative Approaches
- **Package-by-Layer**: For simpler applications
- **Modular Monolith**: Feature-based modules with clear boundaries
- **Microservice Structure**: Service-specific package organization

## Success Metrics

### Developer Understanding
- **Navigation Speed**: Students find classes quickly using package structure
- **Package Purpose**: Students understand package organization rationale
- **Naming Consistency**: Students follow Java package naming conventions

### Code Organization Quality
- **Cohesion**: Related classes grouped in appropriate packages
- **Coupling**: Minimal dependencies between unrelated packages
- **Clarity**: Package names clearly indicate purpose and scope

## References

- [Java Package Naming Conventions](https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html)
- [Spring Boot Package Organization](https://spring.io/guides/gs/rest-service/)
- [Clean Architecture in Java](https://github.com/mattia-battiston/clean-architecture-example)
- [Domain-Driven Design Package Structure](https://www.baeldung.com/java-modules-ddd-bounded-contexts)
- [Maven Multi-Module Projects](https://maven.apache.org/guides/mini/guide-multiple-modules.html)

---

**Last Updated**: December 2024  
**Next Review**: March 2025  
**Status**: ✅ Active