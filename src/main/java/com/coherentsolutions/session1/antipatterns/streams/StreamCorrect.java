package com.coherentsolutions.session1.antipatterns.streams;

import com.coherentsolutions.session1.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * CORRECT PATTERN: Stream Handling in Java
 * 
 * This class demonstrates the CORRECT way to use Java Streams.
 * Key principles:
 * 1. Streams are consumed once - create new streams for multiple operations
 * 2. Chain operations efficiently instead of creating intermediate collections
 * 3. Use proper method names (filter, map, collect, etc.)
 * 4. Handle null values and edge cases
 * 5. Know when NOT to use streams (simple operations)
 * 6. Use parallel streams appropriately
 */
public class StreamCorrect {
    
    public static void main(String[] args) {
        demonstrateCorrectStreams();
    }
    
    public static void demonstrateCorrectStreams() {
        System.out.println("=== CORRECT STREAM PATTERNS ===");
        
        // SOLUTION 1: Proper stream usage (create new streams)
        System.out.println("\n1. Proper stream usage:");
        demonstrateProperStreamUsage();
        
        // SOLUTION 2: Correct method names and chaining
        System.out.println("\n2. Correct method names and chaining:");
        demonstrateCorrectMethodNames();
        
        // SOLUTION 3: Proper stream collection
        System.out.println("\n3. Proper stream collection:");
        demonstrateProperCollection();
        
        // SOLUTION 4: Efficient stream operations
        System.out.println("\n4. Efficient stream operations:");
        demonstrateEfficientStreams();
        
        // SOLUTION 5: When NOT to use streams
        System.out.println("\n5. When NOT to use streams:");
        demonstrateWhenNotToUseStreams();
        
        // SOLUTION 6: Advanced stream operations
        System.out.println("\n6. Advanced stream operations:");
        demonstrateAdvancedStreams();
    }
    
    public static void demonstrateProperStreamUsage() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // ✅ CORRECT: Create new stream for each operation
        long count = numbers.stream()
            .filter(n -> n % 2 == 0)
            .count();
        
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        
        System.out.println("Count of even numbers: " + count);
        System.out.println("Even numbers: " + evenNumbers);
        
        // ✅ CORRECT: Store intermediate stream if reuse is needed
        List<Integer> filteredNumbers = numbers.stream()
            .filter(n -> n > 5)
            .collect(Collectors.toList());
        
        // Now work with the collected list
        long filteredCount = filteredNumbers.size();
        int sum = filteredNumbers.stream().mapToInt(Integer::intValue).sum();
        
        System.out.println("Filtered count: " + filteredCount);
        System.out.println("Sum of filtered: " + sum);
    }
    
    public static void demonstrateCorrectMethodNames() {
        List<Product> products = createSampleProducts();
        
        // ✅ CORRECT: Java Stream method names (LINQ → Java)
        
        // Where → filter
        // Select → map  
        // ToList → collect(Collectors.toList())
        List<String> expensiveProductNames = products.stream()
            .filter(p -> p.getPrice().compareTo(new BigDecimal("100")) > 0)
            .map(Product::getName)
            .collect(Collectors.toList());
        
        System.out.println("Expensive products: " + expensiveProductNames);
        
        // Any → anyMatch
        boolean hasExpensiveProducts = products.stream()
            .anyMatch(p -> p.getPrice().compareTo(new BigDecimal("100")) > 0);
        
        System.out.println("Has expensive products: " + hasExpensiveProducts);
        
        // All → allMatch
        boolean allInStock = products.stream()
            .allMatch(Product::isInStock);
        
        System.out.println("All products in stock: " + allInStock);
        
        // First → findFirst
        Optional<Product> firstExpensive = products.stream()
            .filter(p -> p.getPrice().compareTo(new BigDecimal("100")) > 0)
            .findFirst();
        
        firstExpensive.ifPresent(p -> System.out.println("First expensive: " + p.getName()));
        
        // Count → count
        long productCount = products.stream()
            .filter(Product::isInStock)
            .count();
        
        System.out.println("Products in stock: " + productCount);
    }
    
    public static void demonstrateProperCollection() {
        List<Product> products = createSampleProducts();
        
        // ✅ CORRECT: Always collect streams when you need the data
        List<String> productNames = products.stream()
            .map(Product::getName)
            .collect(Collectors.toList());
        
        System.out.println("Product names: " + productNames);
        
        // ✅ CORRECT: Different collection types
        
        // Collect to Set (removes duplicates)
        var categories = products.stream()
            .map(p -> p.getCategory())
            .filter(c -> c != null)
            .collect(Collectors.toSet());
        
        System.out.println("Categories: " + categories);
        
        // Collect to Map
        Map<String, BigDecimal> nameToPrice = products.stream()
            .collect(Collectors.toMap(
                Product::getName,
                Product::getPrice,
                (existing, replacement) -> existing // Handle duplicates
            ));
        
        System.out.println("Name to price map: " + nameToPrice);
        
        // Collect with custom collector
        String productSummary = products.stream()
            .map(Product::getName)
            .collect(Collectors.joining(", ", "Products: [", "]"));
        
        System.out.println(productSummary);
    }
    
    public static void demonstrateEfficientStreams() {
        List<Product> products = createSampleProducts();
        
        // ✅ CORRECT: Chain operations efficiently
        List<String> result = products.stream()
            .filter(p -> p.getPrice().compareTo(new BigDecimal("100")) > 0)
            .filter(Product::isInStock)
            .map(Product::getName)
            .map(String::toUpperCase)
            .sorted()
            .collect(Collectors.toList());
        
        System.out.println("Chained operations result: " + result);
        
        // ✅ CORRECT: Use appropriate terminal operations
        
        // Use reduce for aggregation
        Optional<BigDecimal> totalPrice = products.stream()
            .map(Product::getPrice)
            .reduce(BigDecimal::add);
        
        totalPrice.ifPresent(total -> System.out.println("Total price: $" + total));
        
        // Use specialized streams for primitives
        double averagePrice = products.stream()
            .mapToDouble(p -> p.getPrice().doubleValue())
            .average()
            .orElse(0.0);
        
        System.out.println("Average price: $" + averagePrice);
        
        // Use forEach for side effects (avoid if possible)
        System.out.println("Product prices:");
        products.stream()
            .forEach(p -> System.out.println("  " + p.getName() + ": $" + p.getPrice()));
    }
    
    public static void demonstrateWhenNotToUseStreams() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // ✅ CORRECT: Simple operations without streams
        
        // Get first element
        String first = !names.isEmpty() ? names.get(0) : "Unknown";
        System.out.println("First name: " + first);
        
        // Check if list has elements
        boolean hasElements = !names.isEmpty();
        System.out.println("Has elements: " + hasElements);
        
        // Get size
        int count = names.size();
        System.out.println("Count: " + count);
        
        // Simple sum (for small collections)
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        System.out.println("Simple sum: " + sum);
        
        // ✅ CORRECT: Use streams for complex operations
        
        // Complex filtering and transformation
        List<String> processedNames = names.stream()
            .filter(name -> name.length() > 3)
            .map(String::toUpperCase)
            .sorted()
            .collect(Collectors.toList());
        
        System.out.println("Complex processing: " + processedNames);
    }
    
    public static void demonstrateAdvancedStreams() {
        List<Product> products = createSampleProducts();
        
        // ✅ CORRECT: Grouping operations
        Map<Product.Category, List<Product>> productsByCategory = products.stream()
            .filter(p -> p.getCategory() != null)
            .collect(Collectors.groupingBy(Product::getCategory));
        
        System.out.println("Products by category: " + productsByCategory.keySet());
        
        // ✅ CORRECT: Partitioning
        Map<Boolean, List<Product>> partitionedByPrice = products.stream()
            .collect(Collectors.partitioningBy(p -> 
                p.getPrice().compareTo(new BigDecimal("100")) > 0));
        
        System.out.println("Expensive products: " + partitionedByPrice.get(true).size());
        System.out.println("Affordable products: " + partitionedByPrice.get(false).size());
        
        // ✅ CORRECT: Complex reductions
        Optional<Product> mostExpensive = products.stream()
            .max(Comparator.comparing(Product::getPrice));
        
        mostExpensive.ifPresent(p -> 
            System.out.println("Most expensive: " + p.getName() + " - $" + p.getPrice()));
        
        // ✅ CORRECT: Flat mapping
        List<String> allTags = products.stream()
            .map(Product::getTags)
            .filter(tags -> tags != null)
            .flatMap(List::stream)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
        
        System.out.println("All tags: " + allTags);
        
        // ✅ CORRECT: Statistical operations
        var priceStats = products.stream()
            .mapToDouble(p -> p.getPrice().doubleValue())
            .summaryStatistics();
        
        System.out.println("Price statistics:");
        System.out.println("  Min: $" + priceStats.getMin());
        System.out.println("  Max: $" + priceStats.getMax());
        System.out.println("  Average: $" + priceStats.getAverage());
    }
    
    // ✅ CORRECT: Method that handles streams properly
    public static List<String> getProductNamesCorrect(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return Collections.emptyList();
        }
        
        return products.stream()
            .filter(product -> product != null)
            .map(Product::getName)
            .filter(name -> name != null && !name.trim().isEmpty())
            .collect(Collectors.toList());
    }
    
    // ✅ CORRECT: Method that handles null values in stream
    public static List<String> processProductNamesCorrect(List<Product> products) {
        if (products == null) {
            return Collections.emptyList();
        }
        
        return products.stream()
            .filter(product -> product != null)
            .map(Product::getName)
            .filter(name -> name != null)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
    }
    
    // ✅ CORRECT: Using parallel streams appropriately
    public static void demonstrateCorrectParallelUsage() {
        // ✅ CORRECT: Use parallel for large collections with CPU-intensive operations
        List<Integer> largeList = IntStream.rangeClosed(1, 1000000)
            .boxed()
            .collect(Collectors.toList());
        
        long start = System.nanoTime();
        
        List<Integer> heavyProcessing = largeList.parallelStream()
            .filter(n -> n % 2 == 0)
            .map(n -> expensiveOperation(n))
            .collect(Collectors.toList());
        
        long duration = System.nanoTime() - start;
        System.out.println("Parallel processing completed in: " + duration / 1_000_000 + " ms");
        System.out.println("Processed " + heavyProcessing.size() + " items");
        
        // ✅ CORRECT: Thread-safe collection for parallel operations
        List<String> threadSafeResults = largeList.parallelStream()
            .limit(1000)
            .map(Object::toString)
            .collect(Collectors.toList()); // Collectors.toList() is thread-safe
        
        System.out.println("Thread-safe collection size: " + threadSafeResults.size());
    }
    
    // Simulate expensive CPU operation
    private static Integer expensiveOperation(Integer n) {
        // Simulate some CPU-intensive work
        return n * n + n;
    }
    
    // ✅ CORRECT: Utility class for common stream operations
    public static class StreamUtils {
        
        // Safe stream creation
        public static <T> Stream<T> safeStream(List<T> list) {
            return list == null ? Stream.empty() : list.stream();
        }
        
        // Null-safe filtering
        public static <T> Stream<T> filterNonNull(Stream<T> stream) {
            return stream.filter(item -> item != null);
        }
        
        // Batch processing
        public static <T> List<List<T>> batch(List<T> list, int batchSize) {
            if (list == null || batchSize <= 0) {
                return Collections.emptyList();
            }
            
            return IntStream.range(0, (list.size() + batchSize - 1) / batchSize)
                .mapToObj(i -> list.subList(
                    i * batchSize, 
                    Math.min((i + 1) * batchSize, list.size())))
                .collect(Collectors.toList());
        }
        
        // Find duplicates
        public static <T> List<T> findDuplicates(List<T> list) {
            return safeStream(list)
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        }
        
        // Safe parallel processing
        public static <T, R> List<R> parallelMap(List<T> list, 
                                                java.util.function.Function<T, R> mapper) {
            if (list == null || list.size() < 1000) {
                // Use sequential for small lists
                return safeStream(list)
                    .map(mapper)
                    .collect(Collectors.toList());
            }
            
            // Use parallel for large lists
            return list.parallelStream()
                .map(mapper)
                .collect(Collectors.toList());
        }
    }
    
    // Helper method to create sample products
    private static List<Product> createSampleProducts() {
        List<Product> products = new ArrayList<>();
        
        products.add(Product.builder()
            .name("Laptop")
            .price(new BigDecimal("1299.99"))
            .category(Product.Category.ELECTRONICS)
            .inStock(true)
            .tags(Arrays.asList("computers", "electronics", "portable"))
            .build());
            
        products.add(Product.builder()
            .name("Mouse")
            .price(new BigDecimal("29.99"))
            .category(Product.Category.ELECTRONICS)
            .inStock(true)
            .tags(Arrays.asList("accessories", "electronics"))
            .build());
            
        products.add(Product.builder()
            .name("Monitor")
            .price(new BigDecimal("399.99"))
            .category(Product.Category.ELECTRONICS)
            .inStock(false)
            .tags(Arrays.asList("displays", "electronics"))
            .build());
            
        products.add(Product.builder()
            .name("Book")
            .price(new BigDecimal("19.99"))
            .category(Product.Category.BOOKS)
            .inStock(true)
            .tags(Arrays.asList("education", "reading"))
            .build());
        
        return products;
    }
}