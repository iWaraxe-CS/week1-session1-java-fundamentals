package com.coherentsolutions.session1.exercises.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * SOLUTION 1.1: Product Class Conversion from C# to Java
 * 
 * This shows multiple approaches to convert the C# Product class:
 * 1. Traditional Java approach (verbose)
 * 2. Lombok approach (concise)
 * 3. Modern Java approach with builder
 */
public class ProductSolution {
    
    public static void main(String[] args) {
        demonstrateProductConversions();
    }
    
    public static void demonstrateProductConversions() {
        System.out.println("=== PRODUCT CONVERSION SOLUTIONS ===");
        
        // APPROACH 1: Traditional Java
        System.out.println("\n1. Traditional Java approach:");
        TraditionalProduct traditionalProduct = new TraditionalProduct();
        traditionalProduct.setId(1);
        traditionalProduct.setName("Laptop");
        traditionalProduct.setPrice(new BigDecimal("1299.99"));
        traditionalProduct.getTags().add("electronics");
        traditionalProduct.getTags().add("computers");
        
        System.out.println("Product: " + traditionalProduct);
        System.out.println("Is expensive: " + traditionalProduct.isExpensive());
        
        // APPROACH 2: Lombok approach
        System.out.println("\n2. Lombok approach:");
        LombokProduct lombokProduct = LombokProduct.builder()
            .id(2)
            .name("Smartphone")
            .price(new BigDecimal("899.99"))
            .build();
        
        lombokProduct.getTags().add("electronics");
        lombokProduct.getTags().add("mobile");
        
        System.out.println("Product: " + lombokProduct);
        System.out.println("Is expensive: " + lombokProduct.isExpensive());
        
        // APPROACH 3: Immutable approach
        System.out.println("\n3. Immutable approach:");
        ImmutableProduct immutableProduct = ImmutableProduct.builder()
            .id(3)
            .name("Tablet")
            .price(new BigDecimal("599.99"))
            .tag("electronics")
            .tag("tablets")
            .build();
        
        System.out.println("Product: " + immutableProduct);
        System.out.println("Is expensive: " + immutableProduct.isExpensive());
    }
    
    // APPROACH 1: Traditional Java approach (verbose but clear)
    public static class TraditionalProduct {
        private Integer id;  // Using Integer instead of int for null safety
        private String name;
        private BigDecimal price;
        private List<String> tags;
        
        // Default constructor
        public TraditionalProduct() {
            this.tags = new ArrayList<>();
        }
        
        // Constructor with parameters
        public TraditionalProduct(Integer id, String name, BigDecimal price) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.tags = new ArrayList<>();
        }
        
        // Getters and setters
        public Integer getId() {
            return id;
        }
        
        public void setId(Integer id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public BigDecimal getPrice() {
            return price;
        }
        
        public void setPrice(BigDecimal price) {
            this.price = price;
        }
        
        public List<String> getTags() {
            return tags;
        }
        
        public void setTags(List<String> tags) {
            this.tags = tags != null ? tags : new ArrayList<>();
        }
        
        // ✅ SOLUTION: Computed property becomes a method
        public boolean isExpensive() {\n            return price != null && price.compareTo(new BigDecimal(\"100\")) > 0;\n        }\n        \n        // ✅ SOLUTION: String formatting in Java\n        @Override\n        public String toString() {\n            return String.format(\"%s: $%s\", name, price);\n        }\n    }\n    \n    // APPROACH 2: Lombok approach (concise)\n    @Data\n    @Builder\n    @NoArgsConstructor\n    @AllArgsConstructor\n    public static class LombokProduct {\n        private Integer id;\n        private String name;\n        private BigDecimal price;\n        \n        @Builder.Default\n        private List<String> tags = new ArrayList<>();\n        \n        // ✅ SOLUTION: Computed property as method\n        public boolean isExpensive() {\n            return price != null && price.compareTo(new BigDecimal(\"100\")) > 0;\n        }\n        \n        // ✅ SOLUTION: Custom toString with proper formatting\n        @Override\n        public String toString() {\n            return String.format(\"%s: $%s\", name, price);\n        }\n    }\n    \n    // APPROACH 3: Immutable approach with builder\n    @lombok.Value\n    @Builder\n    public static class ImmutableProduct {\n        Integer id;\n        String name;\n        BigDecimal price;\n        \n        @lombok.Singular\n        List<String> tags;\n        \n        // ✅ SOLUTION: Computed property works fine with immutable objects\n        public boolean isExpensive() {\n            return price != null && price.compareTo(new BigDecimal(\"100\")) > 0;\n        }\n        \n        // ✅ SOLUTION: toString with null safety\n        @Override\n        public String toString() {\n            String nameStr = name != null ? name : \"Unknown\";\n            String priceStr = price != null ? price.toString() : \"0.00\";\n            return String.format(\"%s: $%s\", nameStr, priceStr);\n        }\n    }\n    \n    // BONUS: Record approach (Java 14+)\n    public record ProductRecord(Integer id, String name, BigDecimal price, List<String> tags) {\n        \n        // Compact constructor for validation\n        public ProductRecord {\n            if (tags == null) {\n                tags = new ArrayList<>();\n            }\n        }\n        \n        // ✅ SOLUTION: Computed property in record\n        public boolean isExpensive() {\n            return price != null && price.compareTo(new BigDecimal(\"100\")) > 0;\n        }\n        \n        // ✅ SOLUTION: Custom toString for record\n        @Override\n        public String toString() {\n            String nameStr = name != null ? name : \"Unknown\";\n            String priceStr = price != null ? price.toString() : \"0.00\";\n            return String.format(\"%s: $%s\", nameStr, priceStr);\n        }\n        \n        // Factory method for easy creation\n        public static ProductRecord create(Integer id, String name, BigDecimal price) {\n            return new ProductRecord(id, name, price, new ArrayList<>());\n        }\n    }\n    \n    // DISCUSSION POINTS FOR INSTRUCTOR:\n    \n    /**\n     * KEY DIFFERENCES BETWEEN C# AND JAVA:\n     * \n     * 1. PROPERTIES:\n     *    C#: public string Name { get; set; }\n     *    Java: private String name; + getter/setter OR @Data\n     * \n     * 2. INIT-ONLY PROPERTIES:\n     *    C#: public int Id { get; init; }\n     *    Java: final fields OR immutable objects OR builder pattern\n     * \n     * 3. DECIMAL TYPE:\n     *    C#: decimal (built-in value type)\n     *    Java: BigDecimal (reference type, requires null checks)\n     * \n     * 4. COLLECTION INITIALIZATION:\n     *    C#: public List<string> Tags { get; } = new();\n     *    Java: @Builder.Default private List<String> tags = new ArrayList<>();\n     * \n     * 5. COMPUTED PROPERTIES:\n     *    C#: public bool IsExpensive => Price > 100m;\n     *    Java: public boolean isExpensive() { return price.compareTo(new BigDecimal(\"100\")) > 0; }\n     * \n     * 6. STRING INTERPOLATION:\n     *    C#: $\"{Name}: ${Price}\"\n     *    Java: String.format(\"%s: $%s\", name, price)\n     * \n     * 7. NULL HANDLING:\n     *    C#: Nullable reference types (string? Name)\n     *    Java: Explicit null checks OR Optional<T>\n     * \n     * 8. BOILERPLATE CODE:\n     *    C#: Minimal with auto-properties\n     *    Java: Use Lombok to reduce boilerplate\n     */\n}