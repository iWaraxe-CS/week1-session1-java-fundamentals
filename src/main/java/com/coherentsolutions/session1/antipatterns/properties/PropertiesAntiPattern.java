package com.coherentsolutions.session1.antipatterns.properties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ANTI-PATTERN: Properties and Fields in Java
 * 
 * This class demonstrates the WRONG way to handle properties in Java.
 * C# developers are used to auto-properties like:
 * public string Name { get; set; }
 * public int Age { get; init; }
 * public bool IsActive => Active && DateTime.Now < ExpiryDate;
 * 
 * Java doesn't have auto-properties, so developers sometimes use public fields
 * or try to mimic C# patterns incorrectly.
 */
public class PropertiesAntiPattern {
    
    public static void main(String[] args) {
        demonstratePropertiesProblems();
    }
    
    public static void demonstratePropertiesProblems() {
        System.out.println("=== PROPERTIES ANTI-PATTERNS ===");
        
        // PROBLEM 1: Using public fields like C# auto-properties
        System.out.println("\n1. Public fields (trying to mimic C# auto-properties):");
        
        UserAntiPattern user = new UserAntiPattern();
        user.name = "John";  // Direct field access
        user.age = 25;
        user.email = "john@example.com";
        
        System.out.println("User: " + user.name + ", Age: " + user.age);
        
        // PROBLEM 2: No validation or encapsulation
        System.out.println("\n2. No validation:");
        user.age = -5;  // Invalid age!
        user.email = "";  // Empty email!
        System.out.println("Invalid data set: Age=" + user.age + ", Email='" + user.email + "'");
        
        // PROBLEM 3: Inconsistent property access
        System.out.println("\n3. Inconsistent property access:");
        ProductAntiPattern product = new ProductAntiPattern();
        product.name = "Laptop";
        product.price = new BigDecimal("999.99");
        // Some fields have getters, some don't - inconsistent!
        
        // PROBLEM 4: Trying to implement computed properties like C#
        System.out.println("\n4. Computed properties issues:");
        System.out.println("Is expensive: " + product.isExpensive()); // Method call, not property
        
        // PROBLEM 5: Mutable collections exposed
        System.out.println("\n5. Mutable collections exposed:");
        product.tags.add("electronics");
        product.tags.add("computers");
        // External code can modify internal state!
        modifyProductTags(product);
        System.out.println("Tags after external modification: " + product.tags);
    }
    
    public static void modifyProductTags(ProductAntiPattern product) {
        // External code can break encapsulation
        product.tags.clear();
        product.tags.add("modified");
    }
    
    // ❌ WRONG: Public fields trying to mimic C# auto-properties
    public static class UserAntiPattern {
        public String name;  // No encapsulation
        public int age;      // No validation
        public String email; // No validation
        public boolean active;
        public LocalDateTime createdAt;
        
        // ❌ WRONG: Mix of public fields and methods
        public void setName(String name) {
            this.name = name;  // Why have a setter if field is public?
        }
        
        // ❌ WRONG: Getter for public field
        public String getName() {
            return name;  // Field is already public!
        }
        
        // ❌ WRONG: No validation in setters
        public void setAge(int age) {
            this.age = age;  // Should validate age > 0
        }
        
        // ❌ WRONG: Inconsistent - some fields have getters, some don't
        public boolean isActive() {
            return active;
        }
        
        // ❌ WRONG: Computed property as method (should be consistent)
        public boolean isAdult() {
            return age >= 18;
        }
    }
    
    // ❌ WRONG: Another example of property anti-patterns
    public static class ProductAntiPattern {
        public String name;
        public BigDecimal price;
        public String description;
        public List<String> tags = new ArrayList<>();  // Mutable collection exposed
        public boolean inStock;
        public int quantity;
        
        // ❌ WRONG: Computed property as method with business logic
        public boolean isExpensive() {
            // What if price is null? No null check!
            return price.compareTo(new BigDecimal("100")) > 0;
        }
        
        // ❌ WRONG: Method tries to act like C# property
        public String getDisplayName() {
            // What if name is null?
            return name.toUpperCase();
        }
        
        // ❌ WRONG: Inconsistent access patterns
        public void updatePrice(BigDecimal newPrice) {
            // Direct field access, no validation
            this.price = newPrice;
        }
        
        // ❌ WRONG: Exposing mutable collection
        public List<String> getTags() {
            return tags;  // Caller can modify internal state!
        }
        
        // ❌ WRONG: Manual property implementation (verbose)
        private String category;
        
        public String getCategory() {
            return category;
        }
        
        public void setCategory(String category) {
            this.category = category;
        }
    }
    
    // ❌ WRONG: Trying to implement C# init-only properties
    public static class ImmutableUserAntiPattern {
        public final String name;
        public final String email;
        private int age;  // Mix of final and non-final
        
        public ImmutableUserAntiPattern(String name, String email) {
            this.name = name;
            this.email = email;
        }
        
        // ❌ WRONG: Trying to implement init-only behavior
        public void setAge(int age) {
            if (this.age == 0) {  // Can only set once
                this.age = age;
            } else {
                throw new IllegalStateException("Age already set");
            }
        }
        
        public int getAge() {
            return age;
        }
    }
    
    // ❌ WRONG: Trying to implement C# expression-bodied properties
    public static class OrderAntiPattern {
        public BigDecimal subtotal;
        public BigDecimal tax;
        public BigDecimal shipping;
        
        // ❌ WRONG: Trying to mimic C# => syntax
        public BigDecimal getTotal() {
            // C# would be: public decimal Total => Subtotal + Tax + Shipping;
            // Java doesn't have expression-bodied properties
            return subtotal.add(tax).add(shipping);
        }
        
        // ❌ WRONG: No null checks
        public boolean isLargeOrder() {
            return getTotal().compareTo(new BigDecimal("1000")) > 0;
        }
    }
}