package com.coherentsolutions.session1.antipatterns.collections;

import com.coherentsolutions.session1.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ANTI-PATTERN: Collection Handling in Java
 * 
 * This class demonstrates the WRONG way to handle collections in Java.
 * C# developers are used to:
 * - Collection initializers: new List<string> { "item1", "item2" }
 * - LINQ methods: list.Where(x => x > 5).Select(x => x.ToString())
 * - Mutable and immutable collections
 * 
 * Java has different syntax and patterns that C# developers often get wrong.
 */
public class CollectionAntiPattern {
    
    public static void main(String[] args) {
        demonstrateCollectionProblems();
    }
    
    public static void demonstrateCollectionProblems() {
        System.out.println("=== COLLECTION ANTI-PATTERNS ===");
        
        // PROBLEM 1: Trying to use C# collection initializer syntax
        System.out.println("\n1. Wrong collection initialization:");
        try {
            // ❌ WRONG: This doesn't work in Java
            // List<String> names = new ArrayList<String>() { "John", "Jane" };
            
            // ❌ WRONG: This creates an immutable list, then tries to modify it
            List<String> names = List.of("John", "Jane");
            names.add("Bob"); // This will throw UnsupportedOperationException
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // PROBLEM 2: Confusing mutable and immutable collections
        System.out.println("\n2. Mutable vs Immutable confusion:");
        demonstrateImmutableConfusion();
        
        // PROBLEM 3: Using wrong collection types
        System.out.println("\n3. Wrong collection types:");
        demonstrateWrongCollectionTypes();
        
        // PROBLEM 4: Trying to use LINQ-style syntax
        System.out.println("\n4. LINQ-style syntax attempts:");
        demonstrateLinqStyleProblems();
        
        // PROBLEM 5: Dictionary/Map confusion
        System.out.println("\n5. Dictionary/Map confusion:");
        demonstrateDictionaryProblems();
    }
    
    public static void demonstrateImmutableConfusion() {
        // ❌ WRONG: Creating immutable list but expecting it to be mutable
        List<String> colors = List.of("red", "blue", "green");
        
        try {
            colors.add("yellow"); // UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify immutable list: " + e.getMessage());
        }
        
        // ❌ WRONG: Converting to mutable but creating new references
        List<String> mutableColors = new ArrayList<>(colors);
        mutableColors.add("yellow");
        
        // Original list is still immutable
        System.out.println("Original: " + colors);
        System.out.println("Mutable copy: " + mutableColors);
    }
    
    public static void demonstrateWrongCollectionTypes() {
        // ❌ WRONG: Using concrete types instead of interfaces
        ArrayList<String> names = new ArrayList<>(); // Should use List<String>
        HashMap<String, Integer> ages = new HashMap<>(); // Should use Map<String, Integer>
        
        // ❌ WRONG: This makes it hard to change implementation later
        processArrayList(names); // Tied to ArrayList implementation
        processHashMap(ages);    // Tied to HashMap implementation
    }
    
    // ❌ WRONG: Methods tied to concrete types
    public static void processArrayList(ArrayList<String> list) {
        list.add("item");
    }
    
    public static void processHashMap(HashMap<String, Integer> map) {
        map.put("key", 1);
    }
    
    public static void demonstrateLinqStyleProblems() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // ❌ WRONG: Trying to use LINQ-style chaining incorrectly
        List<Integer> evenNumbers = new ArrayList<>();
        for (Integer num : numbers) {
            if (num % 2 == 0) {
                evenNumbers.add(num);
            }
        }
        
        // ❌ WRONG: Manual transformation instead of using streams
        List<String> stringNumbers = new ArrayList<>();
        for (Integer num : evenNumbers) {
            stringNumbers.add(num.toString());
        }
        
        System.out.println("Even numbers as strings: " + stringNumbers);
    }
    
    public static void demonstrateDictionaryProblems() {
        // ❌ WRONG: Using Dictionary terminology and patterns
        Map<String, Product> productDictionary = new HashMap<>();
        
        // ❌ WRONG: Not checking if key exists (like C# TryGetValue)
        Product product = productDictionary.get("laptop");
        if (product != null) {
            System.out.println("Product found: " + product.getName());
        } else {
            System.out.println("Product not found");
        }
        
        // ❌ WRONG: Using ContainsKey then get (two lookups)
        if (productDictionary.containsKey("laptop")) {
            Product p = productDictionary.get("laptop");
            System.out.println("Found: " + p.getName());
        }
    }
    
    // ❌ WRONG: Method that doesn't handle collections properly
    public static List<Product> getExpensiveProducts(List<Product> products) {
        List<Product> expensive = new ArrayList<>();
        
        // ❌ WRONG: Manual iteration instead of using streams
        for (Product product : products) {
            // ❌ WRONG: No null checks
            if (product.getPrice().compareTo(new BigDecimal("100")) > 0) {
                expensive.add(product);
            }
        }
        
        return expensive;
    }
    
    // ❌ WRONG: Method that modifies input collection
    public static void removeOutOfStockProducts(List<Product> products) {
        // ❌ WRONG: Modifying collection while iterating
        for (Product product : products) {
            if (!product.isInStock()) {
                products.remove(product); // ConcurrentModificationException
            }
        }
    }
    
    // ❌ WRONG: Method that exposes internal mutable state
    public static class ProductCatalog {
        private List<Product> products = new ArrayList<>();
        
        // ❌ WRONG: Returning mutable internal collection
        public List<Product> getProducts() {
            return products;
        }
        
        // ❌ WRONG: No validation when adding
        public void addProduct(Product product) {
            products.add(product); // What if product is null?
        }
    }
    
    // ❌ WRONG: Trying to use C# collection patterns
    public static void demonstrateCollectionPatterns() {
        List<String> items = Arrays.asList("apple", "banana", "cherry");
        
        // ❌ WRONG: Trying to use C# Count property
        // int count = items.Count; // Should be items.size()
        
        // ❌ WRONG: Trying to use C# Any() method
        // boolean hasItems = items.Any(); // Should use !items.isEmpty()
        
        // ❌ WRONG: Trying to use C# First() method
        // String first = items.First(); // Should use items.get(0) with bounds check
        
        System.out.println("Items: " + items);
    }
}