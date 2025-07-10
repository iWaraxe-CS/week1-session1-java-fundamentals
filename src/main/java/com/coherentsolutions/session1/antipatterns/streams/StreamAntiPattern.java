package com.coherentsolutions.session1.antipatterns.streams;

import com.coherentsolutions.session1.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ANTI-PATTERN: Stream Handling in Java
 * 
 * This class demonstrates the WRONG way to use Java Streams.
 * C# developers are familiar with LINQ, which is similar to Java Streams
 * but has some key differences:
 * - LINQ queries are reusable, Java Streams are consumed once
 * - LINQ has different method names (Where vs filter, Select vs map)
 * - Java Streams require explicit collection at the end
 * 
 * Common mistakes C# developers make with Java Streams.
 */
public class StreamAntiPattern {
    
    public static void main(String[] args) {
        demonstrateStreamProblems();
    }
    
    public static void demonstrateStreamProblems() {
        System.out.println("=== STREAM ANTI-PATTERNS ===");
        
        // PROBLEM 1: Trying to reuse streams like LINQ queries
        System.out.println("\n1. Trying to reuse streams:");
        demonstrateStreamReuse();
        
        // PROBLEM 2: Using wrong method names (LINQ vs Streams)
        System.out.println("\n2. Using wrong method names:");
        demonstrateWrongMethodNames();
        
        // PROBLEM 3: Not collecting streams
        System.out.println("\n3. Not collecting streams:");
        demonstrateNotCollecting();
        
        // PROBLEM 4: Using streams inefficiently
        System.out.println("\n4. Using streams inefficiently:");
        demonstrateInefficiencStreams();
        
        // PROBLEM 5: Overusing streams
        System.out.println("\n5. Overusing streams:");
        demonstrateOverusingStreams();
    }
    
    public static void demonstrateStreamReuse() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // ❌ WRONG: Trying to reuse stream like LINQ query
        Stream<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0);
        
        try {
            // First use - works fine
            long count = evenNumbers.count();
            System.out.println("Count of even numbers: " + count);
            
            // ❌ WRONG: Trying to reuse the same stream
            List<Integer> evenList = evenNumbers.collect(Collectors.toList());
            System.out.println("Even numbers: " + evenList);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Streams can only be consumed once!");
        }
    }
    
    public static void demonstrateWrongMethodNames() {
        List<Product> products = createSampleProducts();
        
        // ❌ WRONG: Trying to use LINQ method names
        // C# LINQ: products.Where(p => p.Price > 100).Select(p => p.Name)
        
        // This won't compile - these methods don't exist in Java
        // List<String> expensiveNames = products.stream()
        //     .Where(p -> p.getPrice().compareTo(new BigDecimal("100")) > 0)  // Should be filter
        //     .Select(p -> p.getName())                                        // Should be map
        //     .ToList();                                                       // Should be collect
        
        System.out.println("LINQ method names don't exist in Java:");
        System.out.println("- Where() → filter()");
        System.out.println("- Select() → map()");
        System.out.println("- ToList() → collect(Collectors.toList())");
        System.out.println("- Any() → anyMatch()");
        System.out.println("- All() → allMatch()");
        System.out.println("- First() → findFirst()");
    }
    
    public static void demonstrateNotCollecting() {
        List<Product> products = createSampleProducts();
        
        // ❌ WRONG: Not collecting the stream result
        Stream<String> productNames = products.stream()
            .map(Product::getName);
        
        // ❌ WRONG: Stream is not a collection!
        // This won't work as expected
        System.out.println("Stream object: " + productNames);
        System.out.println("This just prints the stream object, not the data!");
        
        // ❌ WRONG: Trying to iterate over uncollected stream
        try {
            for (String name : productNames.collect(Collectors.toList())) {
                System.out.println("Product: " + name);
            }
        } catch (IllegalStateException e) {
            System.out.println("Error: Stream already consumed!");
        }
    }
    
    public static void demonstrateInefficiencStreams() {
        List<Product> products = createSampleProducts();
        
        // ❌ WRONG: Multiple stream operations instead of chaining
        List<Product> expensiveProducts = products.stream()
            .filter(p -> p.getPrice().compareTo(new BigDecimal("100")) > 0)
            .collect(Collectors.toList());
        
        List<String> expensiveNames = expensiveProducts.stream()
            .map(Product::getName)
            .collect(Collectors.toList());
        
        List<String> upperCaseNames = expensiveNames.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        
        System.out.println("Created 3 intermediate collections unnecessarily");
        System.out.println("Result: " + upperCaseNames);
        
        // ❌ WRONG: Using streams for simple operations
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Overkill for simple operations
        int sum = numbers.stream()
            .mapToInt(Integer::intValue)
            .sum();
        
        System.out.println("Sum: " + sum + " (could be done with simple loop)");
    }
    
    public static void demonstrateOverusingStreams() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // ❌ WRONG: Using stream for simple operations
        
        // Simple get first element
        String first = names.stream()
            .findFirst()
            .orElse("Unknown");
        
        // Should just be: names.get(0) with bounds check
        
        // Simple check if list is empty
        boolean hasElements = names.stream()
            .findAny()
            .isPresent();
        
        // Should just be: !names.isEmpty()
        
        // Simple get size
        long count = names.stream()
            .count();
        
        // Should just be: names.size()
        
        System.out.println("Overusing streams for simple operations:");
        System.out.println("First: " + first);
        System.out.println("Has elements: " + hasElements);
        System.out.println("Count: " + count);
    }
    
    // ❌ WRONG: Method that creates stream but doesn't use it properly
    public static List<String> getProductNamesWrong(List<Product> products) {
        // Multiple problems here
        if (products.stream().count() == 0) {  // Should use isEmpty()
            return new ArrayList<>();
        }
        
        Stream<Product> productStream = products.stream();
        
        // ❌ WRONG: Trying to use stream multiple times
        boolean hasExpensive = productStream.anyMatch(p -> 
            p.getPrice().compareTo(new BigDecimal("100")) > 0);
        
        if (hasExpensive) {
            // This will fail - stream already consumed
            return productStream
                .map(Product::getName)
                .collect(Collectors.toList());
        }
        
        return new ArrayList<>();
    }
    
    // ❌ WRONG: Method that doesn't handle null values in stream
    public static List<String> processProductNamesWrong(List<Product> products) {
        return products.stream()
            .map(Product::getName)  // What if product is null?
            .map(String::toUpperCase)  // What if name is null?
            .collect(Collectors.toList());
    }
    
    // ❌ WRONG: Using parallel streams incorrectly
    public static void demonstrateWrongParallelUsage() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // ❌ WRONG: Using parallel for small collections
        List<Integer> doubled = numbers.parallelStream()
            .map(n -> n * 2)
            .collect(Collectors.toList());
        
        System.out.println("Parallel overhead for small collection: " + doubled);
        
        // ❌ WRONG: Parallel with non-thread-safe operations
        List<String> results = new ArrayList<>(); // Not thread-safe!
        
        numbers.parallelStream()
            .forEach(n -> results.add(n.toString())); // Race condition!
        
        System.out.println("Potentially corrupted results: " + results);
    }
    
    // Helper method to create sample products
    private static List<Product> createSampleProducts() {
        return Arrays.asList(
            Product.builder()
                .name("Laptop")
                .price(new BigDecimal("1299.99"))
                .inStock(true)
                .build(),
            Product.builder()
                .name("Mouse")
                .price(new BigDecimal("29.99"))
                .inStock(true)
                .build(),
            Product.builder()
                .name("Monitor")
                .price(new BigDecimal("399.99"))
                .inStock(false)
                .build()
        );
    }
}