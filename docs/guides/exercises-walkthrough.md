# Exercises Walkthrough
## Hands-On Practice Guide for .NET Developers

> **Step-by-step walkthrough** of all exercises in the repository with detailed solutions, explanations, and .NET comparisons to reinforce learning through practice.

## 🎯 What You'll Accomplish

By completing these exercises, you'll:
- **APPLY** Java concepts learned in theoretical guides
- **PRACTICE** converting .NET patterns to Java equivalents
- **BUILD** confidence through hands-on coding experience
- **VALIDATE** your understanding with working examples

**Time Investment**: 3-4 hours of hands-on coding + review

## 📋 **Exercise Overview**

### **Exercise Categories**

| Exercise | Skill Level | Focus Area | Time Required |
|----------|-------------|------------|---------------|
| **[Product Conversion](#exercise-1-product-conversion)** | Beginner | C# to Java syntax conversion | 30 minutes |
| **[String Puzzle](#exercise-2-string-puzzle)** | Beginner | String handling and references | 20 minutes |
| **[Collections Practice](#exercise-3-collections-practice)** | Intermediate | Collections Framework usage | 45 minutes |
| **[User Service](#exercise-4-user-service)** | Intermediate | Dependency injection and testing | 60 minutes |
| **[File Processor](#exercise-5-file-processor)** | Advanced | I/O, error handling, performance | 45 minutes |

---

## 🏃‍♂️ **Exercise 1: Product Conversion**

### **Goal: Convert C# Class to Java**

**Learning Objectives:**
- Master Java syntax differences from C#
- Understand proper encapsulation patterns
- Apply validation and Lombok annotations

**File Location:** [`src/main/java/com/coherentsolutions/session1/exercises/product/`](../../src/main/java/com/coherentsolutions/session1/exercises/product/)

### **Step 1: Analyze the C# Starting Point**

```csharp
// C# starting point (provided in exercise description)
public class Product
{
    public int Id { get; init; }
    public string Name { get; set; }
    public decimal Price { get; set; }
    public List<string> Categories { get; } = new();
    
    public bool IsExpensive => Price > 100m;
    
    public override string ToString() => $"{Name}: ${Price}";
}
```

**What this C# code demonstrates:**
- Auto-properties with `init` and `set` accessors
- Collection property with automatic initialization
- Computed property using expression body
- String interpolation in ToString override

### **Step 2: Initial Java Conversion (Common Mistakes)**

**❌ First Attempt (Common .NET Developer Mistakes):**
```java
// ❌ This is what .NET developers often try first
public class Product {
    // ❌ Trying to use C# property syntax
    public int Id { get; init; }  // ❌ Doesn't compile
    public String Name { get; set; }  // ❌ Doesn't compile
    
    // ❌ Using wrong decimal type
    public decimal Price { get; set; }  // ❌ No decimal type in Java
    
    // ❌ Trying C# collection initialization
    public List<String> Categories = new List<String>() {  // ❌ Wrong syntax
        "Default"
    };
}
```

### **Step 3: Basic Java Conversion**

**✅ Basic Working Java Version:**
```java
public class Product {
    private int id;           // ❌ Should be Long for consistency
    private String name;
    private BigDecimal price; // ✅ Correct type for money
    private List<String> categories;
    
    // Constructors
    public Product() {
        this.categories = new ArrayList<>();
    }
    
    public Product(int id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categories = new ArrayList<>();
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    
    // Computed property equivalent
    public boolean isExpensive() {
        return price.compareTo(new BigDecimal("100")) > 0;
    }
    
    @Override
    public String toString() {
        return name + ": $" + price;
    }
}
```

### **Step 4: Modern Java with Lombok**

**✅ Production-Ready Java Solution:**
```java
package com.coherentsolutions.session1.exercises.product;

import lombok.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Product entity demonstrating modern Java practices for .NET developers.
 * 
 * Key differences from C#:
 * - Uses BigDecimal instead of decimal for money calculations
 * - Lombok reduces boilerplate (similar to C# auto-properties)
 * - Validation annotations instead of property setters
 * - Defensive copying for collections
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    // Use Long instead of int for database entities
    private Long id;
    
    // Validation annotations (like C# data annotations)
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;
    
    // BigDecimal for precise money calculations
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;
    
    // Initialize collections to prevent null pointer exceptions
    @Builder.Default
    private List<String> categories = new ArrayList<>();
    
    // Business logic methods
    public boolean isExpensive() {
        return price != null && price.compareTo(new BigDecimal("100")) > 0;
    }
    
    // Defensive copying for collections (prevent external modification)
    public List<String> getCategories() {
        return new ArrayList<>(categories);
    }
    
    public void setCategories(List<String> categories) {
        this.categories = categories != null ? new ArrayList<>(categories) : new ArrayList<>();
    }
    
    // Add category method (better than exposing mutable collection)
    public void addCategory(String category) {
        if (category != null && !category.trim().isEmpty()) {
            this.categories.add(category.trim());
        }
    }
    
    public void removeCategory(String category) {
        this.categories.remove(category);
    }
    
    // Custom toString with null safety
    @Override
    public String toString() {
        return String.format("%s: $%s", 
            name != null ? name : "Unknown", 
            price != null ? price : "0.00");
    }
    
    // Custom equals and hashCode if using in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
```

### **Step 5: Alternative with Java Records (Java 14+)**

**✅ Immutable Alternative Using Records:**
```java
package com.coherentsolutions.session1.exercises.product;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Immutable Product using Java Records (similar to C# records).
 * Best for data transfer objects or value objects.
 */
public record ProductRecord(
    Long id,
    
    @NotBlank(message = "Product name cannot be blank")
    String name,
    
    @NotNull @DecimalMin("0.01")
    BigDecimal price,
    
    List<String> categories
) {
    // Compact constructor for validation
    public ProductRecord {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        // Defensive copying for mutable collections
        categories = categories != null ? List.copyOf(categories) : List.of();
    }
    
    // Additional methods
    public boolean isExpensive() {
        return price.compareTo(new BigDecimal("100")) > 0;
    }
    
    // Factory methods
    public static ProductRecord create(String name, BigDecimal price) {
        return new ProductRecord(null, name, price, List.of());
    }
    
    public static ProductRecord from(Product product) {
        return new ProductRecord(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getCategories()
        );
    }
}
```

### **Step 6: Unit Tests**

**Test the Conversion:**
```java
package com.coherentsolutions.session1.exercises.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;
import java.math.BigDecimal;
import java.util.List;

class ProductTest {
    
    @Test
    @DisplayName("Should create product with valid data")
    void shouldCreateProductWithValidData() {
        // Given
        Product product = Product.builder()
            .id(1L)
            .name("Java Book")
            .price(new BigDecimal("89.99"))
            .categories(List.of("Books", "Technology"))
            .build();
        
        // Then
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("Java Book");
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("89.99"));
        assertThat(product.isExpensive()).isFalse();
        assertThat(product.getCategories()).containsExactly("Books", "Technology");
    }
    
    @Test
    @DisplayName("Should identify expensive products correctly")
    void shouldIdentifyExpensiveProducts() {
        // Given
        Product expensiveProduct = Product.builder()
            .name("Enterprise Software")
            .price(new BigDecimal("999.99"))
            .build();
        
        Product cheapProduct = Product.builder()
            .name("Small Tool")
            .price(new BigDecimal("9.99"))
            .build();
        
        // Then
        assertThat(expensiveProduct.isExpensive()).isTrue();
        assertThat(cheapProduct.isExpensive()).isFalse();
    }
    
    @Test
    @DisplayName("Should handle category operations safely")
    void shouldHandleCategoryOperations() {
        // Given
        Product product = new Product();
        
        // When
        product.addCategory("Electronics");
        product.addCategory("  Gadgets  "); // Test trimming
        product.addCategory(null); // Test null handling
        product.addCategory(""); // Test empty string
        
        // Then
        assertThat(product.getCategories()).containsExactly("Electronics", "Gadgets");
        
        // Verify defensive copying
        List<String> categories = product.getCategories();
        categories.add("Hacked"); // This shouldn't affect the original
        assertThat(product.getCategories()).containsExactly("Electronics", "Gadgets");
    }
}
```

---

## 🔤 **Exercise 2: String Puzzle**

### **Goal: Master Java String Behavior**

**Learning Objectives:**
- Understand the difference between `==` and `.equals()`
- Learn about string interning and memory management
- Practice proper string comparison patterns

**File Location:** [`src/main/java/com/coherentsolutions/session1/exercises/stringpuzzle/`](../../src/main/java/com/coherentsolutions/session1/exercises/stringpuzzle/)

### **Step 1: The String Puzzle Challenge**

```java
package com.coherentsolutions.session1.exercises.stringpuzzle;

/**
 * String Puzzle: Predict the output of each comparison.
 * This demonstrates the key differences between Java and C# string handling.
 */
public class StringPuzzle {
    
    public static void main(String[] args) {
        // Test cases - predict true/false before running
        
        // Case 1: String literals
        String str1 = "hello";
        String str2 = "hello";
        System.out.println("Case 1 - Literals:");
        System.out.println("str1 == str2: " + (str1 == str2)); // ?
        System.out.println("str1.equals(str2): " + (str1.equals(str2))); // ?
        
        // Case 2: new String()
        String str3 = new String("hello");
        String str4 = new String("hello");
        System.out.println("\nCase 2 - new String():");
        System.out.println("str3 == str4: " + (str3 == str4)); // ?
        System.out.println("str3.equals(str4): " + (str3.equals(str4))); // ?
        
        // Case 3: Mixed comparison
        System.out.println("\nCase 3 - Mixed:");
        System.out.println("str1 == str3: " + (str1 == str3)); // ?
        System.out.println("str1.equals(str3): " + (str1.equals(str3))); // ?
        
        // Case 4: String concatenation
        String str5 = "hel" + "lo"; // Compile-time constant
        String str6 = "hel";
        String str7 = str6 + "lo"; // Runtime concatenation
        System.out.println("\nCase 4 - Concatenation:");
        System.out.println("str1 == str5: " + (str1 == str5)); // ?
        System.out.println("str1 == str7: " + (str1 == str7)); // ?
        System.out.println("str1.equals(str7): " + (str1.equals(str7))); // ?
        
        // Case 5: intern() method
        String str8 = str7.intern();
        System.out.println("\nCase 5 - intern():");
        System.out.println("str1 == str8: " + (str1 == str8)); // ?
        
        // Case 6: null handling
        String str9 = null;
        System.out.println("\nCase 6 - null handling:");
        try {
            System.out.println("str9.equals(str1): " + (str9.equals(str1))); // ?
        } catch (Exception e) {
            System.out.println("Exception: " + e.getClass().getSimpleName());
        }
        
        try {
            System.out.println("str1.equals(str9): " + (str1.equals(str9))); // ?
        } catch (Exception e) {
            System.out.println("Exception: " + e.getClass().getSimpleName());
        }
    }
}
```

### **Step 2: Solutions and Explanations**

**Expected Output with Explanations:**
```java
/**
 * SOLUTIONS AND EXPLANATIONS:
 */
public class StringPuzzleSolutions {
    
    public static void explainResults() {
        // Case 1: String literals - TRUE, TRUE
        // Both str1 and str2 reference the same object in string pool
        String str1 = "hello";
        String str2 = "hello";
        System.out.println("str1 == str2: true (same object in string pool)");
        System.out.println("str1.equals(str2): true (same content)");
        
        // Case 2: new String() - FALSE, TRUE  
        // Each new String() creates a separate object
        String str3 = new String("hello");
        String str4 = new String("hello");
        System.out.println("str3 == str4: false (different objects)");
        System.out.println("str3.equals(str4): true (same content)");
        
        // Case 3: Mixed - FALSE, TRUE
        // Literal vs new String() = different objects
        System.out.println("str1 == str3: false (literal vs new object)");
        System.out.println("str1.equals(str3): true (same content)");
        
        // Case 4: Concatenation - TRUE, FALSE, TRUE
        String str5 = "hel" + "lo"; // Compile-time optimization -> string pool
        String str6 = "hel";
        String str7 = str6 + "lo"; // Runtime concatenation -> new object
        System.out.println("str1 == str5: true (compile-time constant)");
        System.out.println("str1 == str7: false (runtime concatenation)");
        System.out.println("str1.equals(str7): true (same content)");
        
        // Case 5: intern() - TRUE
        String str8 = str7.intern(); // Moves to string pool
        System.out.println("str1 == str8: true (intern() returns pool reference)");
        
        // Case 6: null handling
        String str9 = null;
        // str9.equals(str1) -> NullPointerException
        // str1.equals(str9) -> false (safe)
    }
}
```

### **Step 3: Best Practices Implementation**

**Safe String Handling Class:**
```java
package com.coherentsolutions.session1.exercises.stringpuzzle;

import java.util.Objects;

/**
 * Demonstrates safe string handling patterns for .NET developers.
 */
public class SafeStringHandling {
    
    // ✅ BEST: Use Objects.equals() for null-safe comparison
    public static boolean safeEquals(String str1, String str2) {
        return Objects.equals(str1, str2);
    }
    
    // ✅ GOOD: Constant-first comparison prevents NPE
    public static boolean isAdmin(String username) {
        return "admin".equals(username); // Safe even if username is null
    }
    
    // ✅ BEST: null-safe string operations
    public static String safeToUpperCase(String input) {
        return input != null ? input.toUpperCase() : null;
    }
    
    // ✅ BETTER: Use Optional for explicit nullability
    public static Optional<String> safeToUpperCaseOptional(String input) {
        return Optional.ofNullable(input).map(String::toUpperCase);
    }
    
    // ✅ EXCELLENT: Comprehensive null-safe utilities
    public static class StringUtils {
        
        public static boolean isBlank(String str) {
            return str == null || str.trim().isEmpty();
        }
        
        public static boolean isNotBlank(String str) {
            return !isBlank(str);
        }
        
        public static String defaultIfBlank(String str, String defaultValue) {
            return isBlank(str) ? defaultValue : str;
        }
        
        public static String join(String delimiter, String... strings) {
            return Arrays.stream(strings)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(delimiter));
        }
    }
    
    // Example usage demonstrating differences from C#
    public static void demonstrateUsage() {
        String userInput = null;
        
        // ❌ C# style - NullReferenceException risk
        // if (userInput.ToUpper() == "ADMIN") { }
        
        // ✅ Java safe patterns
        if ("ADMIN".equalsIgnoreCase(userInput)) {
            System.out.println("Admin user");
        }
        
        if (Objects.equals("admin", userInput)) {
            System.out.println("Exact match");
        }
        
        String result = StringUtils.defaultIfBlank(userInput, "guest");
        System.out.println("User: " + result);
    }
}
```

---

## 📚 **Exercise 3: Collections Practice**

### **Goal: Master Java Collections Framework**

**Learning Objectives:**
- Choose appropriate collection types for different scenarios
- Use Streams API effectively (Java's LINQ equivalent)
- Apply functional programming patterns

**File Location:** [`src/main/java/com/coherentsolutions/session1/exercises/collections/`](../../src/main/java/com/coherentsolutions/session1/exercises/collections/)

### **Step 1: Collection Selection Challenge**

```java
package com.coherentsolutions.session1.exercises.collections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collections Exercise: Choose the right collection for each scenario.
 * Demonstrates Java Collections Framework vs .NET collections.
 */
public class CollectionsExercise {
    
    // Scenario 1: Shopping cart items (order matters, duplicates allowed)
    // What collection type should we use?
    public class ShoppingCart {
        // ❌ Wrong choices:
        // Set<Product> items;           // No duplicates allowed
        // Map<Product, Integer> items;  // More complex than needed
        
        // ✅ Correct choice:
        private List<CartItem> items = new ArrayList<>(); // Order + duplicates
        
        public void addItem(Product product, int quantity) {
            items.add(new CartItem(product, quantity));
        }
        
        public List<CartItem> getItems() {
            return new ArrayList<>(items); // Defensive copy
        }
    }
    
    // Scenario 2: User permissions (unique, fast lookup)
    // What collection type should we use?
    public class UserPermissions {
        // ❌ Wrong choices:
        // List<Permission> permissions;    // Duplicates allowed, slow lookup
        // TreeSet<Permission> permissions; // Sorted not needed, slower
        
        // ✅ Correct choice:
        private Set<Permission> permissions = new HashSet<>(); // Unique + fast lookup
        
        public void addPermission(Permission permission) {
            permissions.add(permission);
        }
        
        public boolean hasPermission(Permission permission) {
            return permissions.contains(permission); // O(1) lookup
        }
    }
    
    // Scenario 3: Configuration cache (key-value, fast access)
    public class ConfigurationCache {
        // ✅ Correct choice:
        private Map<String, String> config = new HashMap<>(); // Fast key-value lookup
        
        public String getValue(String key) {
            return config.get(key);
        }
        
        public String getValue(String key, String defaultValue) {
            return config.getOrDefault(key, defaultValue);
        }
    }
    
    // Scenario 4: Event log (chronological order, frequent insertions)
    public class EventLog {
        // ❌ Wrong choice:
        // ArrayList<LogEntry> entries; // Slower insertions in middle
        
        // ✅ Correct choice for frequent insertions:
        private LinkedList<LogEntry> entries = new LinkedList<>();
        
        // ✅ Better choice for most scenarios:
        // private List<LogEntry> entries = new ArrayList<>(); // Usually better overall
        
        public void addEntry(LogEntry entry) {
            entries.addFirst(entry); // O(1) with LinkedList
        }
    }
}
```

### **Step 2: LINQ to Streams Conversion**

```java
/**
 * Converting LINQ queries to Java Streams.
 * Side-by-side comparison for .NET developers.
 */
public class LinqToStreamsExercise {
    
    private List<Employee> employees = Arrays.asList(
        new Employee("Alice", "Engineering", 85000, 5),
        new Employee("Bob", "Sales", 55000, 2),
        new Employee("Charlie", "Engineering", 95000, 7),
        new Employee("Diana", "Marketing", 65000, 3)
    );
    
    // Exercise 1: Filter and transform
    public void filterAndTransformExample() {
        // C# LINQ:
        // var engineerNames = employees
        //     .Where(e => e.Department == "Engineering")
        //     .Select(e => e.Name)
        //     .ToList();
        
        // ✅ Java Streams:
        List<String> engineerNames = employees.stream()
            .filter(e -> "Engineering".equals(e.getDepartment()))
            .map(Employee::getName)
            .collect(Collectors.toList());
        
        System.out.println("Engineers: " + engineerNames);
    }
    
    // Exercise 2: Grouping and aggregation
    public void groupingExample() {
        // C# LINQ:
        // var salaryByDept = employees
        //     .GroupBy(e => e.Department)
        //     .ToDictionary(g => g.Key, g => g.Average(e => e.Salary));
        
        // ✅ Java Streams:
        Map<String, Double> salaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        
        System.out.println("Average salary by department: " + salaryByDept);
    }
    
    // Exercise 3: Complex aggregation
    public void complexAggregationExample() {
        // C# LINQ:
        // var stats = employees
        //     .Where(e => e.Experience > 3)
        //     .GroupBy(e => e.Department)
        //     .Select(g => new {
        //         Department = g.Key,
        //         Count = g.Count(),
        //         TotalSalary = g.Sum(e => e.Salary),
        //         AvgExperience = g.Average(e => e.Experience)
        //     });
        
        // ✅ Java Streams:
        Map<String, DepartmentStats> stats = employees.stream()
            .filter(e -> e.getExperience() > 3)
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    empList -> new DepartmentStats(
                        empList.size(),
                        empList.stream().mapToDouble(Employee::getSalary).sum(),
                        empList.stream().mapToDouble(Employee::getExperience).average().orElse(0)
                    )
                )
            ));
        
        stats.forEach((dept, stat) -> 
            System.out.printf("%s: %d employees, $%.2f total, %.1f avg experience%n",
                dept, stat.getCount(), stat.getTotalSalary(), stat.getAvgExperience()));
    }
    
    // Exercise 4: Finding and matching
    public void findingExample() {
        // C# LINQ:
        // var highEarner = employees.FirstOrDefault(e => e.Salary > 90000);
        // bool hasNewHires = employees.Any(e => e.Experience < 1);
        // bool allWellPaid = employees.All(e => e.Salary > 50000);
        
        // ✅ Java Streams:
        Optional<Employee> highEarner = employees.stream()
            .filter(e -> e.getSalary() > 90000)
            .findFirst();
        
        boolean hasNewHires = employees.stream()
            .anyMatch(e -> e.getExperience() < 1);
        
        boolean allWellPaid = employees.stream()
            .allMatch(e -> e.getSalary() > 50000);
        
        System.out.println("High earner: " + highEarner.map(Employee::getName).orElse("None"));
        System.out.println("Has new hires: " + hasNewHires);
        System.out.println("All well paid: " + allWellPaid);
    }
}
```

### **Step 3: Performance Optimization**

```java
/**
 * Performance considerations when choosing collections and streams.
 */
public class CollectionPerformanceExercise {
    
    // Exercise: Optimize for different scenarios
    public void performanceComparison() {
        List<Integer> smallList = IntStream.range(1, 100).boxed().collect(Collectors.toList());
        List<Integer> largeList = IntStream.range(1, 1_000_000).boxed().collect(Collectors.toList());
        
        // Scenario 1: Simple operations on small collections
        // ✅ Traditional loop often faster for simple operations
        long sum1 = 0;
        for (Integer num : smallList) {
            sum1 += num;
        }
        
        // ✅ Stream for complex operations or readability
        long sum2 = smallList.stream()
            .mapToLong(Integer::longValue)
            .sum();
        
        // Scenario 2: Large collections with complex operations
        // ✅ Parallel streams can help with CPU-intensive operations
        List<Integer> processedLarge = largeList.parallelStream()
            .filter(n -> n % 2 == 0)
            .map(this::expensiveOperation)
            .collect(Collectors.toList());
        
        // ❌ Don't use parallel for simple operations or small datasets
        // List<Integer> processedSmall = smallList.parallelStream() // Overhead > benefit
        //     .map(n -> n * 2)
        //     .collect(Collectors.toList());
    }
    
    private Integer expensiveOperation(Integer input) {
        // Simulate CPU-intensive operation
        return input * input + input * 2;
    }
    
    // Collection choice impact on performance
    public void collectionChoicePerformance() {
        int size = 100_000;
        
        // ArrayList vs LinkedList
        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();
        
        // ✅ ArrayList: Better for random access, iteration
        for (int i = 0; i < size; i++) {
            arrayList.add("Item " + i);
        }
        
        // ❌ LinkedList: Slower for indexed access
        String item = arrayList.get(50000); // O(1)
        // String item2 = linkedList.get(50000); // O(n) - slow!
        
        // ✅ LinkedList: Better for insertions at beginning/middle
        linkedList.addFirst("First item"); // O(1)
        // arrayList.add(0, "First item");    // O(n) - slower
    }
}
```

---

## 👤 **Exercise 4: User Service**

### **Goal: Build Complete Service Layer**

**Learning Objectives:**
- Implement dependency injection with Spring
- Write comprehensive unit tests
- Apply validation and error handling
- Use Optional for null safety

**File Location:** [`src/main/java/com/coherentsolutions/session1/exercises/userservice/`](../../src/main/java/com/coherentsolutions/session1/exercises/userservice/)

### **Step 1: Domain Model Design**

```java
package com.coherentsolutions.session1.exercises.userservice;

import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User entity demonstrating modern Java patterns.
 * Compare with typical C# entity classes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private Long id;
    
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 150, message = "Age cannot exceed 150")
    private Integer age;
    
    private boolean active;
    
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Business logic methods
    public boolean isAdult() {
        return age != null && age >= 18;
    }
    
    public boolean isEligibleForDiscount() {
        return active && age != null && age >= 65;
    }
    
    // Custom equals/hashCode for entity
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
```

### **Step 2: Repository Layer**

```java
package com.coherentsolutions.session1.exercises.userservice;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface following Spring Data JPA patterns.
 * Compare with .NET Entity Framework repository pattern.
 */
public interface UserRepository {
    
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findByActiveTrue();
    List<User> findByNameContainingIgnoreCase(String namePattern);
    
    User save(User user);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    
    long count();
    List<User> findAll();
}

/**
 * In-memory implementation for testing and exercises.
 * In real applications, use Spring Data JPA.
 */
@Repository
public class InMemoryUserRepository implements UserRepository {
    
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
            .filter(user -> Objects.equals(user.getEmail(), email))
            .findFirst();
    }
    
    @Override
    public List<User> findByActiveTrue() {
        return users.values().stream()
            .filter(User::isActive)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<User> findByNameContainingIgnoreCase(String namePattern) {
        return users.values().stream()
            .filter(user -> user.getName().toLowerCase().contains(namePattern.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        users.put(user.getId(), user);
        return user;
    }
    
    @Override
    public void deleteById(Long id) {
        users.remove(id);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }
    
    @Override
    public long count() {
        return users.size();
    }
    
    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
```

### **Step 3: Service Layer Implementation**

```java
package com.coherentsolutions.session1.exercises.userservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * User service demonstrating proper dependency injection and business logic.
 * Compare with .NET service layer patterns.
 */
@Service
@RequiredArgsConstructor  // ✅ Constructor injection with Lombok
@Slf4j  // ✅ Logging
public class UserService {
    
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ValidationService validationService;
    
    /**
     * Create a new user with validation.
     * Compare with .NET: async Task<User> CreateUserAsync(CreateUserRequest request)
     */
    public User createUser(@Valid CreateUserRequest request) {
        log.info("Creating user with email: {}", request.getEmail());
        
        // Business validation
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User with email already exists: " + request.getEmail());
        }
        
        validationService.validateCreateUserRequest(request);
        
        // Create user
        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .age(request.getAge())
            .active(true)
            .build();
        
        User savedUser = userRepository.save(user);
        
        // Send welcome email (could be async)
        try {
            emailService.sendWelcomeEmail(savedUser);
        } catch (EmailServiceException e) {
            log.warn("Failed to send welcome email to {}", savedUser.getEmail(), e);
            // Don't fail user creation due to email issues
        }
        
        log.info("User created successfully with ID: {}", savedUser.getId());
        return savedUser;
    }
    
    /**
     * Find user by ID with Optional return type.
     * Compare with .NET: async Task<User?> FindUserByIdAsync(long id)
     */
    public Optional<User> findUserById(Long id) {
        log.debug("Finding user by ID: {}", id);
        return userRepository.findById(id);
    }
    
    /**
     * Find user by email.
     */
    public Optional<User> findUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return userRepository.findByEmail(email.toLowerCase().trim());
    }
    
    /**
     * Update user information.
     */
    public User updateUser(Long id, UpdateUserRequest request) {
        log.info("Updating user with ID: {}", id);
        
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        
        // Update fields if provided
        if (request.getName() != null) {
            validationService.validateName(request.getName());
            user.setName(request.getName());
        }
        
        if (request.getAge() != null) {
            validationService.validateAge(request.getAge());
            user.setAge(request.getAge());
        }
        
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully: {}", updatedUser.getId());
        
        return updatedUser;
    }
    
    /**
     * Activate user account.
     */
    public void activateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        
        if (!user.isActive()) {
            user.setActive(true);
            userRepository.save(user);
            log.info("User activated: {}", id);
        }
    }
    
    /**
     * Get all active users.
     */
    public List<User> getActiveUsers() {
        return userRepository.findByActiveTrue();
    }
    
    /**
     * Search users by name pattern.
     */
    public List<User> searchUsersByName(String namePattern) {
        if (namePattern == null || namePattern.trim().isEmpty()) {
            return List.of();
        }
        
        return userRepository.findByNameContainingIgnoreCase(namePattern.trim());
    }
}
```

### **Step 4: Exception Handling**

```java
package com.coherentsolutions.session1.exercises.userservice;

/**
 * Custom exceptions for user service.
 * Compare with .NET exception hierarchy.
 */
public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }
    
    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class UserNotFoundException extends UserServiceException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

public class UserAlreadyExistsException extends UserServiceException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

public class InvalidUserDataException extends UserServiceException {
    public InvalidUserDataException(String message) {
        super(message);
    }
}
```

### **Step 5: Comprehensive Unit Tests**

```java
package com.coherentsolutions.session1.exercises.userservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import java.util.Optional;

/**
 * Comprehensive unit tests demonstrating Java testing patterns.
 * Compare with .NET testing (xUnit, Moq, FluentAssertions).
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private EmailService emailService;
    
    @Mock
    private ValidationService validationService;
    
    @InjectMocks  // ✅ Mockito injects mocks automatically
    private UserService userService;
    
    private CreateUserRequest validRequest;
    
    @BeforeEach
    void setUp() {
        validRequest = CreateUserRequest.builder()
            .name("John Doe")
            .email("john@example.com")
            .age(30)
            .build();
    }
    
    @Test
    @DisplayName("Should create user successfully with valid data")
    void shouldCreateUserSuccessfully() {
        // Given
        given(userRepository.existsByEmail(validRequest.getEmail())).willReturn(false);
        
        User savedUser = User.builder()
            .id(1L)
            .name(validRequest.getName())
            .email(validRequest.getEmail())
            .age(validRequest.getAge())
            .active(true)
            .build();
        
        given(userRepository.save(any(User.class))).willReturn(savedUser);
        
        // When
        User result = userService.createUser(validRequest);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        assertThat(result.isActive()).isTrue();
        
        // Verify interactions
        then(validationService).should().validateCreateUserRequest(validRequest);
        then(userRepository).should().save(any(User.class));
        then(emailService).should().sendWelcomeEmail(result);
    }
    
    @Test
    @DisplayName("Should throw exception when user already exists")
    void shouldThrowExceptionWhenUserExists() {
        // Given
        given(userRepository.existsByEmail(validRequest.getEmail())).willReturn(true);
        
        // When & Then
        assertThatThrownBy(() -> userService.createUser(validRequest))
            .isInstanceOf(UserAlreadyExistsException.class)
            .hasMessage("User with email already exists: john@example.com");
        
        // Verify no user was saved
        then(userRepository).should(never()).save(any(User.class));
        then(emailService).should(never()).sendWelcomeEmail(any(User.class));
    }
    
    @Test
    @DisplayName("Should handle email service failure gracefully")
    void shouldHandleEmailServiceFailure() {
        // Given
        given(userRepository.existsByEmail(validRequest.getEmail())).willReturn(false);
        
        User savedUser = User.builder()
            .id(1L)
            .name(validRequest.getName())
            .email(validRequest.getEmail())
            .age(validRequest.getAge())
            .active(true)
            .build();
        
        given(userRepository.save(any(User.class))).willReturn(savedUser);
        willThrow(new EmailServiceException("SMTP server unavailable"))
            .given(emailService).sendWelcomeEmail(any(User.class));
        
        // When - should not throw exception
        User result = userService.createUser(validRequest);
        
        // Then - user still created despite email failure
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }
    
    @Test
    @DisplayName("Should find user by ID when exists")
    void shouldFindUserByIdWhenExists() {
        // Given
        Long userId = 1L;
        User user = User.builder().id(userId).name("John").email("john@example.com").build();
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        
        // When
        Optional<User> result = userService.findUserById(userId);
        
        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(userId);
        assertThat(result.get().getName()).isEqualTo("John");
    }
    
    @Test
    @DisplayName("Should return empty when user not found")
    void shouldReturnEmptyWhenUserNotFound() {
        // Given
        Long userId = 999L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        
        // When
        Optional<User> result = userService.findUserById(userId);
        
        // Then
        assertThat(result).isEmpty();
    }
}
```

---

## 📁 **Exercise 5: File Processor**

### **Goal: Advanced I/O and Error Handling**

**Learning Objectives:**
- Use try-with-resources for proper resource management
- Handle checked exceptions appropriately
- Apply functional programming to file processing
- Optimize for performance with large files

**File Location:** [`src/main/java/com/coherentsolutions/session1/exercises/fileprocessor/`](../../src/main/java/com/coherentsolutions/session1/exercises/fileprocessor/)

### **Step 1: Basic File Processing**

```java
package com.coherentsolutions.session1.exercises.fileprocessor;

import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;
import java.util.List;
import java.util.Optional;

/**
 * File processing demonstrating modern Java I/O patterns.
 * Compare with .NET System.IO patterns.
 */
@Slf4j
public class FileProcessor {
    
    /**
     * Read entire file to string.
     * Compare with .NET: File.ReadAllText(path)
     */
    public Optional<String> readFileToString(String filePath) {
        try {
            // ✅ Modern Java NIO.2 API (similar to .NET)
            String content = Files.readString(Paths.get(filePath));
            return Optional.of(content);
        } catch (IOException e) {
            log.error("Failed to read file: {}", filePath, e);
            return Optional.empty();
        }
    }
    
    /**
     * Read file lines to list.
     * Compare with .NET: File.ReadAllLines(path)
     */
    public Optional<List<String>> readFileLines(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            return Optional.of(lines);
        } catch (IOException e) {
            log.error("Failed to read file lines: {}", filePath, e);
            return Optional.empty();
        }
    }
    
    /**
     * Process large file line by line.
     * Compare with .NET: foreach(var line in File.ReadLines(path))
     */
    public void processLargeFile(String filePath, LineProcessor processor) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            // ✅ Stream automatically closed, memory efficient
            lines.forEach(line -> {
                try {
                    processor.processLine(line);
                } catch (Exception e) {
                    log.warn("Failed to process line: {}", line, e);
                }
            });
        } catch (IOException e) {
            log.error("Failed to process file: {}", filePath, e);
            throw new FileProcessingException("Cannot process file: " + filePath, e);
        }
    }
    
    /**
     * Write string to file.
     * Compare with .NET: File.WriteAllText(path, content)
     */
    public void writeStringToFile(String filePath, String content) {
        try {
            Files.writeString(Paths.get(filePath), content);
            log.info("File written successfully: {}", filePath);
        } catch (IOException e) {
            log.error("Failed to write file: {}", filePath, e);
            throw new FileProcessingException("Cannot write file: " + filePath, e);
        }
    }
    
    /**
     * Append string to file.
     * Compare with .NET: File.AppendAllText(path, content)
     */
    public void appendStringToFile(String filePath, String content) {
        try {
            Files.writeString(Paths.get(filePath), content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Failed to append to file: {}", filePath, e);
            throw new FileProcessingException("Cannot append to file: " + filePath, e);
        }
    }
}

@FunctionalInterface
public interface LineProcessor {
    void processLine(String line) throws Exception;
}
```

### **Step 2: Advanced File Operations**

```java
/**
 * Advanced file processing with functional programming.
 */
@Slf4j
public class AdvancedFileProcessor {
    
    /**
     * Filter and transform file content.
     * Compare with .NET LINQ on File.ReadLines()
     */
    public List<String> filterAndTransformLines(String filePath, 
                                               Predicate<String> filter,
                                               Function<String, String> transformer) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines
                .filter(filter)
                .map(transformer)
                .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Failed to filter and transform file: {}", filePath, e);
            return Collections.emptyList();
        }
    }
    
    /**
     * Count lines matching criteria.
     */
    public long countLinesMatching(String filePath, Predicate<String> criteria) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.filter(criteria).count();
        } catch (IOException e) {
            log.error("Failed to count lines in file: {}", filePath, e);
            return 0;
        }
    }
    
    /**
     * Process CSV file with functional approach.
     */
    public <T> List<T> processCsvFile(String filePath, Function<String, T> lineMapper) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines
                .skip(1) // Skip header
                .filter(line -> !line.trim().isEmpty())
                .map(lineMapper)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Failed to process CSV file: {}", filePath, e);
            throw new FileProcessingException("Cannot process CSV file: " + filePath, e);
        }
    }
    
    /**
     * Batch process files in directory.
     */
    public void processFilesInDirectory(String directoryPath, String filePattern, 
                                      Consumer<Path> fileProcessor) {
        try (Stream<Path> files = Files.list(Paths.get(directoryPath))) {
            files
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().matches(filePattern))
                .forEach(fileProcessor);
        } catch (IOException e) {
            log.error("Failed to process files in directory: {}", directoryPath, e);
            throw new FileProcessingException("Cannot process directory: " + directoryPath, e);
        }
    }
    
    /**
     * Copy file with progress reporting.
     * Compare with .NET file copy operations.
     */
    public void copyFileWithProgress(String sourcePath, String targetPath, 
                                   ProgressCallback progressCallback) {
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);
        
        try {
            long fileSize = Files.size(source);
            
            try (InputStream in = Files.newInputStream(source);
                 OutputStream out = Files.newOutputStream(target)) {
                
                byte[] buffer = new byte[8192];
                long totalBytesRead = 0;
                int bytesRead;
                
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                    
                    // Report progress
                    double progress = (double) totalBytesRead / fileSize * 100;
                    progressCallback.onProgress(progress);
                }
            }
        } catch (IOException e) {
            log.error("Failed to copy file from {} to {}", sourcePath, targetPath, e);
            throw new FileProcessingException("Cannot copy file", e);
        }
    }
}

@FunctionalInterface
public interface ProgressCallback {
    void onProgress(double percentage);
}
```

### **Step 3: Error Handling and Resource Management**

```java
/**
 * Demonstrating proper error handling and resource management.
 * Critical differences from .NET using statements.
 */
public class ResourceManagementExamples {
    
    /**
     * Process multiple files with proper resource management.
     * Compare with .NET using statements.
     */
    public void processMultipleFiles(List<String> filePaths, FileProcessor processor) {
        List<String> failedFiles = new ArrayList<>();
        
        for (String filePath : filePaths) {
            try {
                // ✅ Try-with-resources automatically closes streams
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
                    processor.process(reader);
                }
            } catch (IOException e) {
                log.warn("Failed to process file: {}", filePath, e);
                failedFiles.add(filePath);
            }
        }
        
        if (!failedFiles.isEmpty()) {
            log.error("Failed to process {} files: {}", failedFiles.size(), failedFiles);
            throw new FileProcessingException(
                String.format("Failed to process %d files", failedFiles.size()));
        }
    }
    
    /**
     * Database connection with file processing.
     * Demonstrates multiple resource management.
     */
    public void processFileToDatabase(String filePath, String jdbcUrl) {
        // ✅ Multiple resources in try-with-resources
        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO data (value) VALUES (?)");
             BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                stmt.setString(1, line);
                stmt.addBatch();
            }
            
            stmt.executeBatch();
            conn.commit();
            
        } catch (SQLException | IOException e) {
            log.error("Failed to process file to database", e);
            throw new FileProcessingException("Database processing failed", e);
        }
        // All resources automatically closed in reverse order
    }
    
    /**
     * Custom resource management.
     */
    public static class ManagedFileProcessor implements AutoCloseable {
        private final String processingId;
        private final List<Path> tempFiles = new ArrayList<>();
        
        public ManagedFileProcessor(String processingId) {
            this.processingId = processingId;
            log.info("Starting file processing: {}", processingId);
        }
        
        public void createTempFile(String content) throws IOException {
            Path tempFile = Files.createTempFile("processing", ".tmp");
            Files.writeString(tempFile, content);
            tempFiles.add(tempFile);
        }
        
        @Override
        public void close() {
            // Clean up temporary files
            for (Path tempFile : tempFiles) {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException e) {
                    log.warn("Failed to delete temp file: {}", tempFile, e);
                }
            }
            log.info("Completed file processing: {}", processingId);
        }
    }
    
    // Usage of custom resource
    public void useCustomResource() {
        try (ManagedFileProcessor processor = new ManagedFileProcessor("batch-001")) {
            processor.createTempFile("Some content");
            // Do processing
        } // Automatically calls close() and cleans up temp files
    }
}

@FunctionalInterface
public interface FileProcessor {
    void process(BufferedReader reader) throws IOException;
}
```

---

## ✅ **Exercise Solutions Summary**

### **Key Learning Outcomes**

After completing all exercises, you should have mastered:

#### **Java Language Fundamentals** ✅
- [x] Converting C# classes to idiomatic Java
- [x] Understanding string reference vs. value semantics
- [x] Using BigDecimal for monetary calculations
- [x] Applying Lombok for reducing boilerplate

#### **Collections and Streams** ✅
- [x] Choosing appropriate collection types for scenarios
- [x] Converting LINQ queries to Stream operations
- [x] Understanding performance implications
- [x] Using functional programming patterns

#### **Enterprise Java Patterns** ✅
- [x] Implementing dependency injection with Spring
- [x] Writing comprehensive unit tests with Mockito
- [x] Using Optional for null safety
- [x] Handling exceptions appropriately

#### **Advanced Concepts** ✅
- [x] Managing resources with try-with-resources
- [x] Processing files efficiently
- [x] Applying functional programming to I/O
- [x] Understanding Java's approach to checked exceptions

### **Common Patterns Learned**

| Pattern | Java Implementation | .NET Equivalent |
|---------|-------------------|-----------------|
| **Null Safety** | `Optional<T>` | `T?` (nullable types) |
| **Resource Management** | try-with-resources | `using` statements |
| **Dependency Injection** | Constructor injection | Constructor injection |
| **Validation** | Bean Validation | Data Annotations |
| **Error Handling** | Specific exceptions | Specific exceptions |
| **Functional Programming** | Streams API | LINQ |

---

## 🚀 **Next Steps**

### **Practice Recommendations**

1. **Immediate Practice** (Today):
   - Complete all 5 exercises
   - Run tests and verify understanding
   - Experiment with different approaches

2. **Short-term Practice** (This Week):
   - Build a small project combining all concepts
   - Create additional test cases
   - Refactor existing C# code to Java

3. **Long-term Mastery** (Next Month):
   - Contribute to open-source Java projects
   - Mentor other .NET developers learning Java
   - Build production-ready applications

### **Additional Challenges**

**Challenge 1: E-commerce System**
```java
// Build a complete e-commerce system with:
// - Product catalog with categories
// - Shopping cart functionality
// - Order processing with validation
// - User management with roles
// - File-based data persistence
```

**Challenge 2: Log Analysis Tool**
```java
// Create a log file analysis tool that:
// - Processes large log files efficiently
// - Extracts and aggregates metrics
// - Generates reports in multiple formats
// - Handles various log formats
```

**Challenge 3: API Client Library**
```java
// Develop a REST API client library featuring:
// - HTTP client with proper resource management
// - JSON serialization/deserialization
// - Error handling and retries
// - Comprehensive test coverage
```

---

**You've completed all Java fundamental exercises! 🎉**

> *"Practice doesn't make perfect - practice makes permanent. Make sure you're practicing the right patterns from the start."*