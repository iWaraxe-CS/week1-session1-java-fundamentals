package com.coherentsolutions.session1.antipatterns.collections;

import com.coherentsolutions.session1.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CORRECT PATTERN: Collection Handling in Java
 * 
 * This class demonstrates the CORRECT way to handle collections in Java.
 * Key principles:
 * 1. Use interface types (List, Map, Set) instead of concrete types
 * 2. Understand mutable vs immutable collections
 * 3. Use streams for functional operations (Java's equivalent to LINQ)
 * 4. Use proper collection initialization patterns
 * 5. Handle null values and empty collections properly
 */
public class CollectionCorrect {
    
    public static void main(String[] args) {
        demonstrateCorrectCollections();
    }
    
    public static void demonstrateCorrectCollections() {
        System.out.println("=== CORRECT COLLECTION PATTERNS ===");
        
        // SOLUTION 1: Proper collection initialization
        System.out.println("\n1. Correct collection initialization:");
        demonstrateCollectionInitialization();
        
        // SOLUTION 2: Understanding mutable vs immutable
        System.out.println("\n2. Mutable vs Immutable collections:");
        demonstrateMutableVsImmutable();
        
        // SOLUTION 3: Using proper collection types
        System.out.println("\n3. Proper collection types:");
        demonstrateProperCollectionTypes();
        
        // SOLUTION 4: Using streams (Java's LINQ equivalent)
        System.out.println("\n4. Streams (Java's LINQ equivalent):");
        demonstrateStreams();
        
        // SOLUTION 5: Proper Map usage
        System.out.println("\n5. Proper Map usage:");
        demonstrateProperMapUsage();
    }
    
    public static void demonstrateCollectionInitialization() {
        // ✅ CORRECT: Various ways to initialize collections
        
        // Mutable list - can be modified
        List<String> names = new ArrayList<>(Arrays.asList("John", "Jane"));
        names.add("Bob");
        System.out.println("Mutable list: " + names);
        
        // Immutable list - cannot be modified (Java 9+)
        List<String> immutableNames = List.of("Alice", "Charlie");
        System.out.println("Immutable list: " + immutableNames);
        
        // Creating mutable copy of immutable
        List<String> mutableCopy = new ArrayList<>(immutableNames);
        mutableCopy.add("David");
        System.out.println("Mutable copy: " + mutableCopy);
        
        // Empty collections
        List<String> emptyList = new ArrayList<>();
        List<String> emptyImmutableList = Collections.emptyList();
        System.out.println("Empty lists created");
    }
    
    public static void demonstrateMutableVsImmutable() {
        // ✅ CORRECT: Clear distinction between mutable and immutable
        
        // Mutable collections
        List<String> mutableList = new ArrayList<>();
        mutableList.add("item1");
        mutableList.add("item2");
        System.out.println("Mutable list: " + mutableList);
        
        // Immutable collections (Java 9+)
        List<String> immutableList = List.of("item1", "item2");
        System.out.println("Immutable list: " + immutableList);
        
        // Creating immutable view of mutable collection
        List<String> unmodifiableView = Collections.unmodifiableList(mutableList);
        System.out.println("Unmodifiable view: " + unmodifiableView);
        
        // Note: unmodifiableView reflects changes to original mutable list
        mutableList.add("item3");
        System.out.println("After adding to mutable: " + unmodifiableView);
    }
    
    public static void demonstrateProperCollectionTypes() {
        // ✅ CORRECT: Using interface types
        List<String> names = new ArrayList<>(); // Interface type
        Map<String, Integer> ages = new HashMap<>(); // Interface type
        
        // ✅ CORRECT: Methods accept interface types
        processNames(names);
        processAges(ages);
        
        // ✅ CORRECT: Can easily switch implementations
        List<String> linkedNames = new java.util.LinkedList<>();
        processNames(linkedNames); // Same method works
    }
    
    // ✅ CORRECT: Methods accept interface types
    public static void processNames(List<String> names) {
        names.add("processed");
        System.out.println("Processed names: " + names);
    }
    
    public static void processAges(Map<String, Integer> ages) {
        ages.put("default", 0);
        System.out.println("Processed ages: " + ages);
    }
    
    public static void demonstrateStreams() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // ✅ CORRECT: Using streams for functional operations
        
        // Filter even numbers and convert to strings
        List<String> evenStrings = numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(Object::toString)
            .collect(Collectors.toList());
        
        System.out.println("Even numbers as strings: " + evenStrings);
        
        // Sum of squares of odd numbers
        int sumOfSquares = numbers.stream()
            .filter(n -> n % 2 == 1)
            .mapToInt(n -> n * n)
            .sum();
        
        System.out.println("Sum of squares of odd numbers: " + sumOfSquares);
        
        // Find first number greater than 5
        Optional<Integer> firstGreaterThan5 = numbers.stream()
            .filter(n -> n > 5)
            .findFirst();
        
        firstGreaterThan5.ifPresent(n -> System.out.println("First > 5: " + n));
        
        // Check if any number is greater than 8
        boolean hasLargeNumber = numbers.stream()
            .anyMatch(n -> n > 8);
        
        System.out.println("Has number > 8: " + hasLargeNumber);
    }
    
    public static void demonstrateProperMapUsage() {
        Map<String, Product> productMap = new HashMap<>();
        
        // Add some test products
        productMap.put("laptop", Product.builder()
            .name("Laptop")
            .price(new BigDecimal("999.99"))
            .inStock(true)
            .build());
        
        // ✅ CORRECT: Safe map access with Optional
        Optional<Product> laptop = Optional.ofNullable(productMap.get("laptop"));
        laptop.ifPresentOrElse(
            product -> System.out.println("Found laptop: " + product.getName()),
            () -> System.out.println("Laptop not found")
        );
        
        // ✅ CORRECT: Using computeIfAbsent for lazy initialization
        Product mouse = productMap.computeIfAbsent("mouse", k -> 
            Product.builder()
                .name("Mouse")
                .price(new BigDecimal("29.99"))
                .inStock(true)
                .build()
        );
        
        System.out.println("Mouse: " + mouse.getName());
        
        // ✅ CORRECT: Processing all entries
        productMap.forEach((key, product) -> 
            System.out.println(key + ": $" + product.getPrice())
        );
    }
    
    // ✅ CORRECT: Method using streams for filtering
    public static List<Product> getExpensiveProducts(List<Product> products) {
        if (products == null) {
            return Collections.emptyList();
        }
        
        return products.stream()
            .filter(product -> product != null)
            .filter(product -> product.getPrice() != null)
            .filter(product -> product.getPrice().compareTo(new BigDecimal("100")) > 0)
            .collect(Collectors.toList());
    }
    
    // ✅ CORRECT: Method that doesn't modify input, returns new collection
    public static List<Product> removeOutOfStockProducts(List<Product> products) {
        if (products == null) {
            return Collections.emptyList();
        }
        
        return products.stream()
            .filter(product -> product != null)
            .filter(Product::isInStock)
            .collect(Collectors.toList());
    }
    
    // ✅ CORRECT: Class that properly encapsulates collections
    public static class ProductCatalog {
        private final List<Product> products = new ArrayList<>();
        
        // ✅ CORRECT: Return immutable view
        public List<Product> getProducts() {
            return Collections.unmodifiableList(products);
        }
        
        // ✅ CORRECT: Validation when adding
        public void addProduct(Product product) {
            if (product == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            products.add(product);
        }
        
        // ✅ CORRECT: Safe product retrieval
        public Optional<Product> findProductByName(String name) {
            if (name == null) {
                return Optional.empty();
            }
            
            return products.stream()
                .filter(product -> name.equals(product.getName()))
                .findFirst();
        }
        
        // ✅ CORRECT: Using streams for business logic
        public List<Product> getProductsByCategory(Product.Category category) {
            return products.stream()
                .filter(product -> product.getCategory() == category)
                .collect(Collectors.toList());
        }
        
        // ✅ CORRECT: Statistical operations
        public long getProductCount() {
            return products.size();
        }
        
        public boolean isEmpty() {
            return products.isEmpty();
        }
        
        public boolean hasProduct(String name) {
            return findProductByName(name).isPresent();
        }
    }
    
    // ✅ CORRECT: Utility methods for common collection operations
    public static class CollectionUtils {
        
        // Safe null-to-empty conversion
        public static <T> List<T> nullToEmpty(List<T> list) {
            return list == null ? Collections.emptyList() : list;
        }
        
        // Safe size calculation
        public static <T> int safeSize(List<T> list) {
            return list == null ? 0 : list.size();
        }
        
        // Safe isEmpty check
        public static <T> boolean isEmpty(List<T> list) {
            return list == null || list.isEmpty();
        }
        
        // Safe contains check
        public static <T> boolean contains(List<T> list, T item) {
            return list != null && list.contains(item);
        }
        
        // Create mutable copy
        public static <T> List<T> mutableCopy(List<T> list) {
            return list == null ? new ArrayList<>() : new ArrayList<>(list);
        }
        
        // Partition list into chunks
        public static <T> List<List<T>> partition(List<T> list, int size) {
            if (list == null || size <= 0) {
                return Collections.emptyList();
            }
            
            List<List<T>> result = new ArrayList<>();
            for (int i = 0; i < list.size(); i += size) {
                result.add(list.subList(i, Math.min(i + size, list.size())));
            }
            return result;
        }
    }
}