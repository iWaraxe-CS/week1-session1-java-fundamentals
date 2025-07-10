package com.coherentsolutions.session1.exercises.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * SOLUTION: Product Exercise - Converting C# to Java
 * 
 * This demonstrates multiple approaches to create a Product class in Java,
 * showing how to handle properties, collections, and computed values
 * that C# developers are familiar with.
 * 
 * C# Original:
 * public class Product {
 *     public int Id { get; set; }
 *     public string Name { get; set; }
 *     public decimal Price { get; set; }
 *     public List<string> Tags { get; } = new();
 *     public bool IsExpensive => Price > 100m;
 * }
 */
public class ProductSolution {
    
    public static void main(String[] args) {
        System.out.println("=== PRODUCT EXERCISE SOLUTIONS ===");
        
        System.out.println("\n1. Traditional Java approach:");
        demonstrateTraditionalProduct();
        
        System.out.println("\n2. Lombok approach:");
        demonstrateLombokProduct();
        
        System.out.println("\n3. Immutable approach:");
        demonstrateImmutableProduct();
        
        System.out.println("\n4. Java Record approach (Java 14+):");
        demonstrateRecordProduct();
    }
    
    public static void demonstrateTraditionalProduct() {
        TraditionalProduct product = new TraditionalProduct();
        product.setId(1);
        product.setName("Laptop");
        product.setPrice(new BigDecimal("1299.99"));
        product.getTags().add("electronics");
        product.getTags().add("computers");
        
        System.out.println("Product: " + product);
        System.out.println("Is expensive: " + product.isExpensive());
    }
    
    public static void demonstrateLombokProduct() {
        LombokProduct product = LombokProduct.builder()
            .id(2)
            .name("Mouse")
            .price(new BigDecimal("29.99"))
            .build();
        
        product.getTags().add("electronics");
        product.getTags().add("accessories");
        
        System.out.println("Product: " + product);
        System.out.println("Is expensive: " + product.isExpensive());
    }
    
    public static void demonstrateImmutableProduct() {
        ImmutableProduct product = ImmutableProduct.builder()
            .id(3)
            .name("Keyboard")
            .price(new BigDecimal("149.99"))
            .tag("electronics")
            .tag("accessories")
            .build();
        
        System.out.println("Product: " + product);
        System.out.println("Is expensive: " + product.isExpensive());
    }
    
    public static void demonstrateRecordProduct() {
        ProductRecord product = ProductRecord.create(4, "Monitor", new BigDecimal("299.99"));
        
        System.out.println("Product: " + product);
        System.out.println("Is expensive: " + product.isExpensive());
    }
    
    // APPROACH 1: Traditional Java (verbose but clear)
    public static class TraditionalProduct {
        private Integer id;
        private String name;
        private BigDecimal price;
        private List<String> tags = new ArrayList<>();
        
        // Default constructor
        public TraditionalProduct() {}
        
        // Full constructor
        public TraditionalProduct(Integer id, String name, BigDecimal price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
        
        // Getters and setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        
        public List<String> getTags() { return tags; }
        public void setTags(List<String> tags) { this.tags = tags; }
        
        // ✅ SOLUTION: Computed property becomes a method
        public boolean isExpensive() {
            return price != null && price.compareTo(new BigDecimal("100")) > 0;
        }
        
        // ✅ SOLUTION: String formatting in Java
        @Override
        public String toString() {
            return String.format("%s: $%s", name, price);
        }
    }
    
    // APPROACH 2: Lombok approach (concise)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LombokProduct {
        private Integer id;
        private String name;
        private BigDecimal price;
        
        @Builder.Default
        private List<String> tags = new ArrayList<>();
        
        // ✅ SOLUTION: Computed property as method
        public boolean isExpensive() {
            return price != null && price.compareTo(new BigDecimal("100")) > 0;
        }
        
        // ✅ SOLUTION: Custom toString with proper formatting
        @Override
        public String toString() {
            return String.format("%s: $%s", name, price);
        }
    }
    
    // APPROACH 3: Immutable approach with builder
    @lombok.Value
    @Builder
    public static class ImmutableProduct {
        Integer id;
        String name;
        BigDecimal price;
        
        @lombok.Singular
        List<String> tags;
        
        // ✅ SOLUTION: Computed property works fine with immutable objects
        public boolean isExpensive() {
            return price != null && price.compareTo(new BigDecimal("100")) > 0;
        }
        
        // ✅ SOLUTION: toString with null safety
        @Override
        public String toString() {
            String nameStr = name != null ? name : "Unknown";
            String priceStr = price != null ? price.toString() : "0.00";
            return String.format("%s: $%s", nameStr, priceStr);
        }
    }
    
    // BONUS: Record approach (Java 14+)
    public record ProductRecord(Integer id, String name, BigDecimal price, List<String> tags) {
        
        // Compact constructor for validation
        public ProductRecord {
            if (tags == null) {
                tags = new ArrayList<>();
            }
        }
        
        // ✅ SOLUTION: Computed property in record
        public boolean isExpensive() {
            return price != null && price.compareTo(new BigDecimal("100")) > 0;
        }
        
        // ✅ SOLUTION: Custom toString for record
        @Override
        public String toString() {
            String nameStr = name != null ? name : "Unknown";
            String priceStr = price != null ? price.toString() : "0.00";
            return String.format("%s: $%s", nameStr, priceStr);
        }
        
        // Factory method for easy creation
        public static ProductRecord create(Integer id, String name, BigDecimal price) {
            return new ProductRecord(id, name, price, new ArrayList<>());
        }
    }
    
    // DISCUSSION POINTS FOR INSTRUCTOR:
    
    /**
     * KEY DIFFERENCES BETWEEN C# AND JAVA:
     * 
     * 1. PROPERTIES:
     *    C#: public string Name { get; set; }
     *    Java: private String name; + getter/setter OR @Data
     * 
     * 2. INIT-ONLY PROPERTIES:
     *    C#: public int Id { get; init; }
     *    Java: final fields OR immutable objects OR builder pattern
     * 
     * 3. DECIMAL TYPE:
     *    C#: decimal (built-in value type)
     *    Java: BigDecimal (reference type, requires null checks)
     * 
     * 4. COLLECTION INITIALIZATION:
     *    C#: public List<string> Tags { get; } = new();
     *    Java: @Builder.Default private List<String> tags = new ArrayList<>();
     * 
     * 5. COMPUTED PROPERTIES:
     *    C#: public bool IsExpensive => Price > 100m;
     *    Java: public boolean isExpensive() { return price.compareTo(new BigDecimal("100")) > 0; }
     * 
     * 6. STRING INTERPOLATION:
     *    C#: $"{Name}: ${Price}"
     *    Java: String.format("%s: $%s", name, price)
     * 
     * 7. NULL HANDLING:
     *    C#: Nullable reference types (string? Name)
     *    Java: Explicit null checks OR Optional<T>
     * 
     * 8. BOILERPLATE CODE:
     *    C#: Minimal with auto-properties
     *    Java: Use Lombok to reduce boilerplate
     */
}