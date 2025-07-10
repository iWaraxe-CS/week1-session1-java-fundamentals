package com.coherentsolutions.session1.antipatterns.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * CORRECT PATTERN: Properties and Fields in Java
 * 
 * This class demonstrates the CORRECT way to handle properties in Java.
 * Key principles:
 * 1. Use private fields with public getters/setters
 * 2. Use Lombok to reduce boilerplate
 * 3. Implement proper validation
 * 4. Use immutable collections where appropriate
 * 5. Use builder pattern for complex objects
 */
public class PropertiesCorrect {
    
    public static void main(String[] args) {
        demonstrateCorrectProperties();
    }
    
    public static void demonstrateCorrectProperties() {
        System.out.println("=== CORRECT PROPERTIES PATTERNS ===");
        
        // SOLUTION 1: Using Lombok for automatic getters/setters
        System.out.println("\n1. Using Lombok @Data annotation:");
        
        UserCorrect user = new UserCorrect();
        user.setName("John");
        user.setAge(25);
        user.setEmail("john@example.com");
        
        System.out.println("User: " + user.getName() + ", Age: " + user.getAge());
        
        // SOLUTION 2: Validation in setters
        System.out.println("\n2. Validation in setters:");
        try {
            user.setAge(-5);  // This will throw an exception
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught: " + e.getMessage());
        }
        
        // SOLUTION 3: Using Builder pattern
        System.out.println("\n3. Builder pattern:");
        ProductCorrect product = ProductCorrect.builder()
            .name("Laptop")
            .price(new BigDecimal("999.99"))
            .description("High-end laptop")
            .inStock(true)
            .quantity(10)
            .build();
        
        System.out.println("Product: " + product.getName() + ", Price: $" + product.getPrice());
        
        // SOLUTION 4: Computed properties as methods
        System.out.println("\n4. Computed properties:");
        System.out.println("Is expensive: " + product.isExpensive());
        System.out.println("Display name: " + product.getDisplayName());
        
        // SOLUTION 5: Immutable collections
        System.out.println("\n5. Immutable collections:");
        product.addTag("electronics");
        product.addTag("computers");
        System.out.println("Tags: " + product.getTags());
        
        // External code cannot modify internal state
        List<String> tags = product.getTags();
        try {
            tags.add("modified");  // This will throw an exception
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify immutable collection");
        }
        
        // SOLUTION 6: Immutable objects
        System.out.println("\n6. Immutable objects:");
        ImmutableUserCorrect immutableUser = ImmutableUserCorrect.builder()
            .name("Jane")
            .email("jane@example.com")
            .age(30)
            .build();
        
        System.out.println("Immutable user: " + immutableUser.getName());
        
        // SOLUTION 7: Record classes (Java 14+)
        System.out.println("\n7. Record classes:");
        PersonRecord person = new PersonRecord("Alice", 28, "alice@example.com");
        System.out.println("Person record: " + person.name() + ", Age: " + person.age());
    }
    
    // ✅ CORRECT: Using Lombok @Data for automatic getters/setters
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserCorrect {
        private String name;
        private int age;
        private String email;
        private boolean active;
        private LocalDateTime createdAt;
        
        // ✅ CORRECT: Custom setter with validation
        public void setAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            if (age > 150) {
                throw new IllegalArgumentException("Age cannot be greater than 150");
            }
            this.age = age;
        }
        
        // ✅ CORRECT: Custom setter with validation
        public void setEmail(String email) {
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be null or empty");
            }
            if (!email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            this.email = email.toLowerCase(); // Normalize
        }
        
        // ✅ CORRECT: Computed property as method
        public boolean isAdult() {
            return age >= 18;
        }
        
        // ✅ CORRECT: Computed property with null safety
        public String getDisplayName() {
            return name != null ? name.toUpperCase() : "UNKNOWN";
        }
    }
    
    // ✅ CORRECT: Using Builder pattern with Lombok
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCorrect {
        private String name;
        private BigDecimal price;
        private String description;
        private boolean inStock;
        private int quantity;
        
        // ✅ CORRECT: Private mutable collection
        @Builder.Default
        private List<String> tags = new ArrayList<>();
        
        // ✅ CORRECT: Computed property with null safety
        public boolean isExpensive() {
            return price != null && price.compareTo(new BigDecimal("100")) > 0;
        }
        
        // ✅ CORRECT: Computed property with validation
        public String getDisplayName() {
            return name != null ? name.toUpperCase() : "UNKNOWN PRODUCT";
        }
        
        // ✅ CORRECT: Method to modify internal collection safely
        public void addTag(String tag) {
            if (tag != null && !tag.trim().isEmpty()) {
                tags.add(tag.trim().toLowerCase());
            }
        }
        
        // ✅ CORRECT: Return immutable view of collection
        public List<String> getTags() {
            return Collections.unmodifiableList(tags);
        }
        
        // ✅ CORRECT: Validation in setter
        public void setPrice(BigDecimal price) {
            if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            this.price = price;
        }
        
        // ✅ CORRECT: Validation in setter
        public void setQuantity(int quantity) {
            if (quantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative");
            }
            this.quantity = quantity;
        }
        
        // ✅ CORRECT: Business logic method
        public boolean isAvailable() {
            return inStock && quantity > 0;
        }
    }
    
    // ✅ CORRECT: Immutable object with Builder
    @Data
    @Builder
    @AllArgsConstructor
    public static class ImmutableUserCorrect {
        private final String name;
        private final String email;
        private final int age;
        private final LocalDateTime createdAt;
        
        // ✅ CORRECT: No setters for immutable object
        // All fields are final
        
        // ✅ CORRECT: Computed properties work fine
        public boolean isAdult() {
            return age >= 18;
        }
        
        // ✅ CORRECT: Factory methods for common cases
        public static ImmutableUserCorrect createUser(String name, String email, int age) {
            return ImmutableUserCorrect.builder()
                .name(name)
                .email(email)
                .age(age)
                .createdAt(LocalDateTime.now())
                .build();
        }
    }
    
    // ✅ CORRECT: Using Java 14+ Record classes
    public record PersonRecord(String name, int age, String email) {
        
        // ✅ CORRECT: Validation in record constructor
        public PersonRecord {
            Objects.requireNonNull(name, "Name cannot be null");
            Objects.requireNonNull(email, "Email cannot be null");
            
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            
            if (!email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
        }
        
        // ✅ CORRECT: Computed properties in records
        public boolean isAdult() {
            return age >= 18;
        }
        
        // ✅ CORRECT: Custom methods in records
        public String getDisplayName() {
            return name.toUpperCase();
        }
    }
    
    // ✅ CORRECT: Traditional JavaBean approach (if not using Lombok)
    public static class TraditionalJavaBean {
        private String name;
        private int age;
        private String email;
        
        // ✅ CORRECT: Default constructor
        public TraditionalJavaBean() {}
        
        // ✅ CORRECT: Constructor with parameters
        public TraditionalJavaBean(String name, int age, String email) {
            setName(name);
            setAge(age);
            setEmail(email);
        }
        
        // ✅ CORRECT: Getters and setters with validation
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public int getAge() {
            return age;
        }
        
        public void setAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            this.age = age;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email");
            }
            this.email = email;
        }
        
        // ✅ CORRECT: Standard object methods
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TraditionalJavaBean that = (TraditionalJavaBean) o;
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
            return "TraditionalJavaBean{" +
                   "name='" + name + '\'' +
                   ", age=" + age +
                   ", email='" + email + '\'' +
                   '}';
        }
    }
}