package com.coherentsolutions.session1.reference;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * PROPERTIES AND LOMBOK REFERENCE: C# Properties vs Java Properties
 * 
 * This reference class demonstrates the differences between C# properties and
 * Java field encapsulation, with extensive Lombok examples to reduce boilerplate.
 * 
 * COVERAGE:
 * 1. C# auto-properties vs Java getter/setter boilerplate
 * 2. Lombok annotations for reducing boilerplate
 * 3. Property validation and computed properties
 * 4. Immutable objects and builder patterns
 * 5. Advanced Lombok features and configurations
 * 6. Best practices for field encapsulation
 * 7. Records (Java 14+) as alternative to Lombok
 * 8. Performance considerations and trade-offs
 * 
 * FOR LECTURE USE:
 * - Show the pain of Java boilerplate vs C# simplicity
 * - Demonstrate how Lombok solves the boilerplate problem
 * - Compare different approaches to object design
 * - Highlight modern Java alternatives
 */
@Slf4j
public class PropertiesAndLombok {
    
    public static void main(String[] args) {
        System.out.println("=== PROPERTIES AND LOMBOK REFERENCE ===");
        
        demonstrateBasicProperties();
        demonstrateLombokBasics();
        demonstrateAdvancedLombok();
        demonstrateImmutableObjects();
        demonstrateBuilderPatterns();
        demonstrateValidationAndComputed();
        demonstrateRecordsVsLombok();
        demonstrateBestPractices();
        
        System.out.println("\n=== PROPERTIES AND LOMBOK COMPLETE ===");
    }
    
    /**
     * BASIC PROPERTIES
     * 
     * C# auto-properties vs Java getter/setter boilerplate
     */
    public static void demonstrateBasicProperties() {
        System.out.println("\n--- Basic Properties ---");
        
        System.out.println("C# AUTO-PROPERTIES:");
        System.out.println("public class Person {");
        System.out.println("    public string Name { get; set; }");
        System.out.println("    public int Age { get; set; }");
        System.out.println("    public string Email { get; set; }");
        System.out.println("}");
        
        System.out.println("\nJAVA TRADITIONAL APPROACH:");
        System.out.println("public class Person {");
        System.out.println("    private String name;");
        System.out.println("    private int age;");
        System.out.println("    private String email;");
        System.out.println("    ");
        System.out.println("    public String getName() { return name; }");
        System.out.println("    public void setName(String name) { this.name = name; }");
        System.out.println("    public int getAge() { return age; }");
        System.out.println("    public void setAge(int age) { this.age = age; }");
        System.out.println("    // ... more boilerplate");
        System.out.println("}");
        
        // Using traditional Java class
        TraditionalPerson traditional = new TraditionalPerson();
        traditional.setName("John Doe");
        traditional.setAge(30);
        traditional.setEmail("john@example.com");
        
        System.out.println("\nTraditional Java object:");
        System.out.println("Name: " + traditional.getName());
        System.out.println("Age: " + traditional.getAge());
        System.out.println("Email: " + traditional.getEmail());
        
        System.out.println("\nPROBLEMS WITH TRADITIONAL APPROACH:");
        System.out.println("1. Massive boilerplate code");
        System.out.println("2. Easy to make mistakes in getters/setters");
        System.out.println("3. Need to maintain equals(), hashCode(), toString()");
        System.out.println("4. Constructor overloading becomes complex");
        System.out.println("5. 10x more code than C# for same functionality");
    }
    
    // Traditional Java class with all boilerplate
    public static class TraditionalPerson {
        private String name;
        private int age;
        private String email;
        
        public TraditionalPerson() {}
        
        public TraditionalPerson(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TraditionalPerson that = (TraditionalPerson) obj;
            return age == that.age && 
                   Objects.equals(name, that.name) && 
                   Objects.equals(email, that.email);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(name, age, email);
        }
        
        @Override
        public String toString() {
            return "TraditionalPerson{" +
                   "name='" + name + '\'' +
                   ", age=" + age +
                   ", email='" + email + '\'' +
                   '}';
        }
    }
    
    /**
     * LOMBOK BASICS
     * 
     * Using Lombok to eliminate boilerplate
     */
    public static void demonstrateLombokBasics() {
        System.out.println("\n--- Lombok Basics ---");
        
        System.out.println("LOMBOK SOLUTION:");
        System.out.println("@Data");
        System.out.println("public class Person {");
        System.out.println("    private String name;");
        System.out.println("    private int age;");
        System.out.println("    private String email;");
        System.out.println("}");
        System.out.println("// Lombok generates: getters, setters, equals, hashCode, toString, constructor");
        
        // Using Lombok class
        LombokPerson lombok = new LombokPerson();
        lombok.setName("Jane Smith");
        lombok.setAge(25);
        lombok.setEmail("jane@example.com");
        
        System.out.println("\nLombok-generated methods:");
        System.out.println("Name: " + lombok.getName());
        System.out.println("Age: " + lombok.getAge());
        System.out.println("toString(): " + lombok);
        
        // Testing equals and hashCode
        LombokPerson lombok2 = new LombokPerson();
        lombok2.setName("Jane Smith");
        lombok2.setAge(25);
        lombok2.setEmail("jane@example.com");
        
        System.out.println("Objects equal: " + lombok.equals(lombok2));
        System.out.println("HashCodes equal: " + (lombok.hashCode() == lombok2.hashCode()));
        
        System.out.println("\nCOMMON LOMBOK ANNOTATIONS:");
        System.out.println("@Getter - generates getter methods");
        System.out.println("@Setter - generates setter methods");
        System.out.println("@ToString - generates toString() method");
        System.out.println("@EqualsAndHashCode - generates equals() and hashCode()");
        System.out.println("@NoArgsConstructor - generates default constructor");
        System.out.println("@AllArgsConstructor - generates constructor with all fields");
        System.out.println("@RequiredArgsConstructor - generates constructor for final fields");
        System.out.println("@Data - combines @Getter, @Setter, @ToString, @EqualsAndHashCode");
        
        // Individual annotation examples
        GetterSetterExample getterSetter = new GetterSetterExample();
        getterSetter.setTitle("Test Title");
        System.out.println("\n@Getter/@Setter example: " + getterSetter.getTitle());
        
        ToStringExample toStringEx = new ToStringExample("Example", 42);
        System.out.println("@ToString example: " + toStringEx);
        
        ConstructorExample constructed = new ConstructorExample("Required Field");
        System.out.println("@RequiredArgsConstructor example: " + constructed);
    }
    
    // Lombok basic examples
    @Data
    public static class LombokPerson {
        private String name;
        private int age;
        private String email;
    }
    
    @Getter
    @Setter
    public static class GetterSetterExample {
        private String title;
        private int count;
    }
    
    @ToString
    @AllArgsConstructor
    public static class ToStringExample {
        private String name;
        private int value;
    }
    
    @RequiredArgsConstructor
    @Getter
    public static class ConstructorExample {
        private final String requiredField;
        private String optionalField;
    }
    
    /**
     * ADVANCED LOMBOK
     * 
     * Advanced Lombok features and configurations
     */
    public static void demonstrateAdvancedLombok() {
        System.out.println("\n--- Advanced Lombok ---");
        
        // @FieldDefaults example
        FieldDefaultsExample fieldDefaults = new FieldDefaultsExample("Test", 100);
        System.out.println("@FieldDefaults example: " + fieldDefaults);
        
        // @Accessors example
        AccessorsExample accessors = new AccessorsExample()
            .name("Fluent")
            .age(30)
            .active(true);
        System.out.println("@Accessors fluent: " + accessors);
        
        // Exclude fields from @Data
        ExcludeFieldsExample exclude = new ExcludeFieldsExample();
        exclude.setName("Public Name");
        exclude.setPassword("secret123");
        System.out.println("Excluded sensitive field: " + exclude);
        
        // Custom toString
        CustomToStringExample customToString = new CustomToStringExample("Important", "secret", 42);
        System.out.println("Custom toString: " + customToString);
        
        // Lazy getter
        LazyExample lazy = new LazyExample();
        System.out.println("First call to expensive calculation: " + lazy.getExpensiveCalculation());
        System.out.println("Second call (cached): " + lazy.getExpensiveCalculation());
        
        // @With for immutable updates
        WithExample original = new WithExample("Original", 1, true);
        WithExample updated = original.withName("Updated").withVersion(2);
        System.out.println("Original: " + original);
        System.out.println("Updated: " + updated);
        
        System.out.println("\nADVANCED LOMBOK FEATURES:");
        System.out.println("@FieldDefaults - sets default access levels");
        System.out.println("@Accessors - customizes getter/setter behavior");
        System.out.println("@ToString.Exclude - excludes fields from toString");
        System.out.println("@EqualsAndHashCode.Exclude - excludes from equals/hashCode");
        System.out.println("@Getter(lazy=true) - lazy initialization");
        System.out.println("@With - creates wither methods for immutable updates");
        System.out.println("@NonNull - null check validation");
        System.out.println("@Cleanup - automatic resource management");
    }
    
    // Advanced Lombok examples
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class FieldDefaultsExample {
        String name;
        int value;
    }
    
    @Data
    @Accessors(fluent = true, chain = true)
    public static class AccessorsExample {
        private String name;
        private int age;
        private boolean active;
    }
    
    @Data
    public static class ExcludeFieldsExample {
        private String name;
        
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private String password;
    }
    
    @ToString(includeFieldNames = false, of = {"name", "count"})
    @AllArgsConstructor
    public static class CustomToStringExample {
        private String name;
        private String secret;
        private int count;
    }
    
    public static class LazyExample {
        @Getter(lazy = true)
        private final String expensiveCalculation = calculateExpensiveValue();
        
        private String calculateExpensiveValue() {
            System.out.println("Performing expensive calculation...");
            try {
                Thread.sleep(100); // Simulate expensive operation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Expensive Result: " + System.currentTimeMillis();
        }
    }
    
    @With
    @AllArgsConstructor
    @Data
    public static class WithExample {
        private final String name;
        private final int version;
        private final boolean active;
    }
    
    /**
     * IMMUTABLE OBJECTS
     * 
     * Creating immutable objects with Lombok
     */
    public static void demonstrateImmutableObjects() {
        System.out.println("\n--- Immutable Objects ---");
        
        System.out.println("C# IMMUTABLE PROPERTIES:");
        System.out.println("public class Person {");
        System.out.println("    public string Name { get; init; }");
        System.out.println("    public int Age { get; init; }");
        System.out.println("}");
        
        System.out.println("\nJAVA IMMUTABLE WITH LOMBOK:");
        System.out.println("@Value");
        System.out.println("public class Person {");
        System.out.println("    String name;");
        System.out.println("    int age;");
        System.out.println("}");
        
        // Using @Value for immutable objects
        ImmutablePerson immutable = new ImmutablePerson("John", 30, "john@example.com");
        System.out.println("Immutable person: " + immutable);
        
        // Cannot modify - compilation error
        // immutable.setName("Jane"); // No setter methods generated
        
        // Custom immutable with validation
        ValidatedImmutable validated = new ValidatedImmutable("Valid Name", 25);
        System.out.println("Validated immutable: " + validated);
        
        try {
            new ValidatedImmutable("", 25); // Should throw exception
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught: " + e.getMessage());
        }
        
        // Using @With for immutable updates
        ImmutableWithUpdates original = new ImmutableWithUpdates("Original", 1, LocalDateTime.now());
        ImmutableWithUpdates updated = original.withName("Updated").withVersion(2);
        System.out.println("Original: " + original.getName() + " v" + original.getVersion());
        System.out.println("Updated: " + updated.getName() + " v" + updated.getVersion());
        
        System.out.println("\nIMMUTABLE OBJECT BENEFITS:");
        System.out.println("1. Thread-safe by default");
        System.out.println("2. No defensive copying needed");
        System.out.println("3. Can be safely shared");
        System.out.println("4. Hashcode can be cached");
        System.out.println("5. Prevents accidental mutation");
        
        System.out.println("\nLOMBOK IMMUTABLE ANNOTATIONS:");
        System.out.println("@Value - creates immutable class");
        System.out.println("@With - adds wither methods for updates");
        System.out.println("@Builder(toBuilder = true) - builder with copy");
    }
    
    // Immutable object examples
    @Value
    public static class ImmutablePerson {
        String name;
        int age;
        String email;
    }
    
    @Value
    public static class ValidatedImmutable {
        String name;
        int age;
        
        public ValidatedImmutable(String name, int age) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            this.name = name.trim();
            this.age = age;
        }
    }
    
    @Value
    @With
    public static class ImmutableWithUpdates {
        String name;
        int version;
        LocalDateTime createdAt;
    }
    
    /**
     * BUILDER PATTERNS
     * 
     * Using Lombok's @Builder annotation
     */
    public static void demonstrateBuilderPatterns() {
        System.out.println("\n--- Builder Patterns ---");
        
        System.out.println("C# OBJECT INITIALIZER:");
        System.out.println("var person = new Person {");
        System.out.println("    Name = \"John\",");
        System.out.println("    Age = 30,");
        System.out.println("    Email = \"john@example.com\"");
        System.out.println("};");
        
        System.out.println("\nJAVA BUILDER PATTERN WITH LOMBOK:");
        System.out.println("@Builder");
        System.out.println("public class Person { ... }");
        System.out.println("\nPerson person = Person.builder()");
        System.out.println("    .name(\"John\")");
        System.out.println("    .age(30)");
        System.out.println("    .email(\"john@example.com\")");
        System.out.println("    .build();");
        
        // Using @Builder
        BuilderExample built = BuilderExample.builder()
            .name("Builder Example")
            .age(35)
            .email("builder@example.com")
            .active(true)
            .build();
        
        System.out.println("Built object: " + built);
        
        // Builder with defaults
        DefaultsExample withDefaults = DefaultsExample.builder()
            .name("Custom Name")
            .build();
        
        System.out.println("Builder with defaults: " + withDefaults);
        
        // Hierarchical builders
        Student student = Student.builder()
            .name("Alice")
            .age(20)
            .studentId("S12345")
            .major("Computer Science")
            .build();
        
        System.out.println("Student: " + student);
        
        // Builder with validation
        try {
            ValidatedBuilder.builder()
                .name("")
                .age(-5)
                .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Builder validation caught: " + e.getMessage());
        }
        
        // toBuilder for copying
        BuilderExample copy = built.toBuilder()
            .name("Modified Copy")
            .age(36)
            .build();
        
        System.out.println("Original: " + built.getName() + " (" + built.getAge() + ")");
        System.out.println("Copy: " + copy.getName() + " (" + copy.getAge() + ")");
        
        System.out.println("\nBUILDER PATTERN BENEFITS:");
        System.out.println("1. Fluent API for object construction");
        System.out.println("2. Optional parameters handling");
        System.out.println("3. Immutable object creation");
        System.out.println("4. Validation at build time");
        System.out.println("5. Easy to extend and maintain");
    }
    
    // Builder pattern examples
    @Data
    @Builder(toBuilder = true)
    public static class BuilderExample {
        private String name;
        private int age;
        private String email;
        private boolean active;
    }
    
    @Data
    @Builder
    public static class DefaultsExample {
        private String name;
        
        @Builder.Default
        private int age = 18;
        
        @Builder.Default
        private boolean active = true;
        
        @Builder.Default
        private LocalDateTime createdAt = LocalDateTime.now();
    }
    
    @Data
    @SuperBuilder
    public static class Person {
        private String name;
        private int age;
    }
    
    @Data
    @SuperBuilder
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Student extends Person {
        private String studentId;
        private String major;
    }
    
    @Builder
    @Getter
    public static class ValidatedBuilder {
        private String name;
        private int age;
        
        ValidatedBuilder(String name, int age) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name is required");
            }
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            this.name = name;
            this.age = age;
        }
    }
    
    /**
     * VALIDATION AND COMPUTED PROPERTIES
     * 
     * Property validation and computed values
     */
    public static void demonstrateValidationAndComputed() {
        System.out.println("\n--- Validation and Computed Properties ---");
        
        System.out.println("C# PROPERTY VALIDATION:");
        System.out.println("public class Person {");
        System.out.println("    private int _age;");
        System.out.println("    public int Age { ");
        System.out.println("        get => _age;");
        System.out.println("        set => _age = value >= 0 ? value : throw new ArgumentException();");
        System.out.println("    }");
        System.out.println("    public string FullName => $\"{FirstName} {LastName}\";");
        System.out.println("}");
        
        System.out.println("\nJAVA VALIDATION WITH CUSTOM SETTERS:");
        
        // Using validation in setters
        ValidatedPerson person = new ValidatedPerson();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAge(30);
        
        System.out.println("Validated person: " + person.getFullName());
        System.out.println("Is adult: " + person.isAdult());
        System.out.println("Age category: " + person.getAgeCategory());
        
        try {
            person.setAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Age validation: " + e.getMessage());
        }
        
        try {
            person.setEmail("invalid-email");
        } catch (IllegalArgumentException e) {
            System.out.println("Email validation: " + e.getMessage());
        }
        
        // Using @NonNull validation
        try {
            NonNullExample.create(null, "value");
        } catch (NullPointerException e) {
            System.out.println("@NonNull validation: Parameter cannot be null");
        }
        
        // Complex validation with builder
        ComplexValidation complex = ComplexValidation.builder()
            .username("john_doe")
            .email("john@example.com")
            .age(25)
            .build();
        
        System.out.println("Complex validation passed: " + complex);
        
        System.out.println("\nVALIDATION STRATEGIES:");
        System.out.println("1. Custom setters with validation logic");
        System.out.println("2. Constructor validation");
        System.out.println("3. Builder validation");
        System.out.println("4. @NonNull for null checks");
        System.out.println("5. Bean Validation annotations");
        System.out.println("6. Computed properties with getters");
    }
    
    // Validation examples
    @Data
    public static class ValidatedPerson {
        private String firstName;
        private String lastName;
        private int age;
        private String email;
        
        public void setAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            if (age > 150) {
                throw new IllegalArgumentException("Age cannot exceed 150");
            }
            this.age = age;
        }
        
        public void setEmail(String email) {
            if (email != null && !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            this.email = email;
        }
        
        // Computed properties
        public String getFullName() {
            if (firstName == null && lastName == null) return null;
            if (firstName == null) return lastName;
            if (lastName == null) return firstName;
            return firstName + " " + lastName;
        }
        
        public boolean isAdult() {
            return age >= 18;
        }
        
        public String getAgeCategory() {
            if (age < 13) return "Child";
            if (age < 20) return "Teenager";
            if (age < 60) return "Adult";
            return "Senior";
        }
    }
    
    @AllArgsConstructor(staticName = "create")
    @Getter
    public static class NonNullExample {
        @NonNull
        private final String requiredField;
        private final String optionalField;
    }
    
    @Builder
    @Getter
    public static class ComplexValidation {
        private String username;
        private String email;
        private int age;
        
        ComplexValidation(String username, String email, int age) {
            // Username validation
            if (username == null || username.length() < 3) {
                throw new IllegalArgumentException("Username must be at least 3 characters");
            }
            if (!username.matches("^[a-zA-Z0-9_]+$")) {
                throw new IllegalArgumentException("Username can only contain letters, numbers, and underscores");
            }
            
            // Email validation
            if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            
            // Age validation
            if (age < 13 || age > 120) {
                throw new IllegalArgumentException("Age must be between 13 and 120");
            }
            
            this.username = username;
            this.email = email;
            this.age = age;
        }
        
        @Override
        public String toString() {
            return "ComplexValidation{username='" + username + "', email='" + email + "', age=" + age + "}";
        }
    }
    
    /**
     * RECORDS VS LOMBOK
     * 
     * Java 14+ Records as alternative to Lombok
     */
    public static void demonstrateRecordsVsLombok() {
        System.out.println("\n--- Records vs Lombok ---");
        
        System.out.println("JAVA RECORD (Java 14+):");
        System.out.println("public record Person(String name, int age, String email) {");
        System.out.println("    // Automatic: constructor, getters, equals, hashCode, toString");
        System.out.println("}");
        
        System.out.println("\nLOMBOK EQUIVALENT:");
        System.out.println("@Value");
        System.out.println("public class Person {");
        System.out.println("    String name;");
        System.out.println("    int age;");
        System.out.println("    String email;");
        System.out.println("}");
        
        // Using Java Record
        PersonRecord record = new PersonRecord("Record Person", 28, "record@example.com");
        System.out.println("Record: " + record);
        System.out.println("Name: " + record.name()); // Note: accessor methods, not getters
        System.out.println("Age: " + record.age());
        
        // Using Lombok @Value
        PersonValue lombokValue = new PersonValue("Lombok Person", 28, "lombok@example.com");
        System.out.println("Lombok: " + lombokValue);
        System.out.println("Name: " + lombokValue.getName()); // Traditional getters
        System.out.println("Age: " + lombokValue.getAge());
        
        // Records with validation
        try {
            new ValidatedRecord("", 25);
        } catch (IllegalArgumentException e) {
            System.out.println("Record validation: " + e.getMessage());
        }
        
        // Records with custom methods
        PersonWithMethods enhanced = new PersonWithMethods("Enhanced", "Person", 30);
        System.out.println("Full name: " + enhanced.fullName());
        System.out.println("Is adult: " + enhanced.isAdult());
        
        System.out.println("\nRECORDS VS LOMBOK COMPARISON:");
        System.out.println("┌─────────────────────┬─────────────────┬─────────────────┐");
        System.out.println("│ Feature             │ Records         │ Lombok          │");
        System.out.println("├─────────────────────┼─────────────────┼─────────────────┤");
        System.out.println("│ Conciseness         │ Very concise    │ Concise         │");
        System.out.println("│ Immutability        │ Always          │ @Value only     │");
        System.out.println("│ Inheritance         │ No extends      │ Full support    │");
        System.out.println("│ Mutable fields      │ No              │ Yes             │");
        System.out.println("│ Custom setters      │ No              │ Yes             │");
        System.out.println("│ Builder pattern     │ No              │ Yes             │");
        System.out.println("│ IDE support         │ Native          │ Plugin needed   │");
        System.out.println("│ Compilation         │ Standard        │ Annotation proc │");
        System.out.println("│ Debugging           │ Easier          │ Generated code  │");
        System.out.println("└─────────────────────┴─────────────────┴─────────────────┘");
        
        System.out.println("\nWHEN TO USE RECORDS:");
        System.out.println("✅ Simple data carriers");
        System.out.println("✅ Immutable objects");
        System.out.println("✅ DTOs and value objects");
        System.out.println("✅ When using Java 14+");
        
        System.out.println("\nWHEN TO USE LOMBOK:");
        System.out.println("✅ Need mutable objects");
        System.out.println("✅ Complex inheritance hierarchies");
        System.out.println("✅ Need builder pattern");
        System.out.println("✅ Custom validation in setters");
        System.out.println("✅ Working with older Java versions");
    }
    
    // Records vs Lombok examples
    public record PersonRecord(String name, int age, String email) {
        // Compact constructor for validation
        public PersonRecord {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
        }
    }
    
    @Value
    public static class PersonValue {
        String name;
        int age;
        String email;
    }
    
    public record ValidatedRecord(String name, int age) {
        public ValidatedRecord {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name is required");
            }
            if (age < 0 || age > 150) {
                throw new IllegalArgumentException("Invalid age");
            }
        }
    }
    
    public record PersonWithMethods(String firstName, String lastName, int age) {
        public String fullName() {
            return firstName + " " + lastName;
        }
        
        public boolean isAdult() {
            return age >= 18;
        }
        
        public PersonWithMethods withAge(int newAge) {
            return new PersonWithMethods(firstName, lastName, newAge);
        }
    }
    
    /**
     * BEST PRACTICES
     * 
     * Best practices for properties and object design
     */
    public static void demonstrateBestPractices() {
        System.out.println("\n--- Best Practices ---");
        
        System.out.println("PROPERTY DESIGN BEST PRACTICES:");
        
        System.out.println("\n1. FAVOR IMMUTABILITY WHEN POSSIBLE:");
        System.out.println("✅ Use @Value or records for value objects");
        System.out.println("✅ Use @With for immutable updates");
        System.out.println("✅ Make fields final when appropriate");
        
        System.out.println("\n2. VALIDATE INPUT EARLY:");
        System.out.println("✅ Validate in constructors");
        System.out.println("✅ Use @NonNull for required fields");
        System.out.println("✅ Fail fast with clear error messages");
        
        System.out.println("\n3. USE APPROPRIATE LOMBOK ANNOTATIONS:");
        System.out.println("✅ @Data for simple mutable classes");
        System.out.println("✅ @Value for immutable classes");
        System.out.println("✅ @Builder for complex object construction");
        System.out.println("✅ @RequiredArgsConstructor for DI");
        
        System.out.println("\n4. HANDLE SENSITIVE DATA PROPERLY:");
        System.out.println("✅ Use @ToString.Exclude for passwords");
        System.out.println("✅ Use @EqualsAndHashCode.Exclude for transient fields");
        System.out.println("✅ Consider custom toString for security");
        
        System.out.println("\n5. PERFORMANCE CONSIDERATIONS:");
        System.out.println("✅ Use @Getter(lazy=true) for expensive calculations");
        System.out.println("✅ Cache hashCode for immutable objects");
        System.out.println("✅ Use primitive types when possible");
        
        // Best practice examples
        BestPracticeExample example = BestPracticeExample.builder()
            .id("12345")
            .name("Best Practice")
            .email("best@example.com")
            .build();
        
        System.out.println("\nBest practice example: " + example);
        
        // Demonstrate proper validation
        try {
            BestPracticeExample.builder()
                .id("")
                .name("Invalid")
                .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation works: " + e.getMessage());
        }
        
        System.out.println("\nCOMMON MISTAKES TO AVOID:");
        System.out.println("❌ Using @Data with inheritance (equals/hashCode issues)");
        System.out.println("❌ Not excluding sensitive fields from toString");
        System.out.println("❌ Making everything mutable by default");
        System.out.println("❌ Not validating constructor parameters");
        System.out.println("❌ Using Lombok everywhere (records might be better)");
        System.out.println("❌ Not understanding generated code");
        
        System.out.println("\nMIGRATION STRATEGY FROM C#:");
        System.out.println("1. Start with @Data for simple classes");
        System.out.println("2. Use @Builder for complex initialization");
        System.out.println("3. Switch to @Value for immutable objects");
        System.out.println("4. Consider records for simple value objects");
        System.out.println("5. Add validation gradually");
        System.out.println("6. Use IDE plugins for better Lombok support");
    }
    
    // Best practice example
    @Builder
    @Value
    @With
    public static class BestPracticeExample {
        @NonNull
        String id;
        
        @NonNull
        String name;
        
        String email;
        
        @Builder.Default
        LocalDateTime createdAt = LocalDateTime.now();
        
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        String internalNotes;
        
        BestPracticeExample(String id, String name, String email, LocalDateTime createdAt, String internalNotes) {
            // Validation
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("ID is required");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name is required");
            }
            if (email != null && !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            
            this.id = id.trim();
            this.name = name.trim();
            this.email = email;
            this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
            this.internalNotes = internalNotes;
        }
        
        public boolean hasEmail() {
            return email != null && !email.trim().isEmpty();
        }
        
        public String getDisplayName() {
            return name + " (" + id + ")";
        }
    }
}