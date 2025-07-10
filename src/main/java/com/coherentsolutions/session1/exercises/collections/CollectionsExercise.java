package com.coherentsolutions.session1.exercises.collections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * COLLECTIONS EXERCISE: Java vs C# Collections
 * 
 * This exercise helps C# developers understand Java collections through
 * practical examples and challenges.
 * 
 * LEARNING OBJECTIVES:
 * 1. Understand Java Collection Framework vs C# Collections
 * 2. Master List, Set, Map, Queue operations
 * 3. Learn Stream API vs LINQ equivalents
 * 4. Practice collection iteration patterns
 * 5. Understand immutable vs mutable collections
 * 
 * INSTRUCTIONS:
 * 1. Read each TODO comment carefully
 * 2. Implement the missing functionality
 * 3. Compare with C# equivalent (shown in comments)
 * 4. Test your implementation by running main method
 * 5. Check your answers against CollectionsSolution.java
 */
public class CollectionsExercise {
    
    public static void main(String[] args) {
        System.out.println("=== COLLECTIONS EXERCISE ===");
        
        // Run each exercise
        exercise1_BasicCollections();
        exercise2_ListOperations();
        exercise3_SetOperations();
        exercise4_MapOperations();
        exercise5_StreamOperations();
        exercise6_CollectionConversions();
        exercise7_CustomCollections();
        exercise8_PerformanceComparison();
        
        System.out.println("\n=== EXERCISE COMPLETED ===");
        System.out.println("Check your answers in CollectionsSolution.java");
    }
    
    /**
     * EXERCISE 1: Basic Collections
     * 
     * C# Example:
     * List<string> names = new List<string> { "Alice", "Bob", "Charlie" };
     * HashSet<int> numbers = new HashSet<int> { 1, 2, 3, 4, 5 };
     * Dictionary<string, int> ages = new Dictionary<string, int> 
     * {
     *     { "Alice", 25 },
     *     { "Bob", 30 },
     *     { "Charlie", 35 }
     * };
     */
    public static void exercise1_BasicCollections() {
        System.out.println("\n--- Exercise 1: Basic Collections ---");
        
        // TODO: Create a List of strings with names: "Alice", "Bob", "Charlie"
        List<String> names = null; // Replace with your implementation
        
        // TODO: Create a Set of integers: 1, 2, 3, 4, 5
        Set<Integer> numbers = null; // Replace with your implementation
        
        // TODO: Create a Map with name -> age mappings:
        // Alice -> 25, Bob -> 30, Charlie -> 35
        Map<String, Integer> ages = null; // Replace with your implementation
        
        // Test your implementation
        if (names != null) {
            System.out.println("Names: " + names);
        }
        if (numbers != null) {
            System.out.println("Numbers: " + numbers);
        }
        if (ages != null) {
            System.out.println("Ages: " + ages);
        }
    }
    
    /**
     * EXERCISE 2: List Operations
     * 
     * C# Example:
     * list.Add("item");
     * list.Remove("item");
     * list.Contains("item");
     * list.Count;
     * list[0]; // indexer
     */
    public static void exercise2_ListOperations() {
        System.out.println("\n--- Exercise 2: List Operations ---");
        
        List<String> fruits = new ArrayList<>(Arrays.asList("apple", "banana", "orange"));
        System.out.println("Initial fruits: " + fruits);
        
        // TODO: Add "grape" to the end of the list
        // Your code here
        
        // TODO: Add "mango" at index 1
        // Your code here
        
        // TODO: Remove "banana" from the list
        // Your code here
        
        // TODO: Check if the list contains "apple"
        boolean containsApple = false; // Replace with your implementation
        
        // TODO: Get the size of the list
        int size = 0; // Replace with your implementation
        
        // TODO: Get the first element
        String firstElement = null; // Replace with your implementation
        
        // TODO: Get the last element
        String lastElement = null; // Replace with your implementation
        
        System.out.println("Final fruits: " + fruits);
        System.out.println("Contains apple: " + containsApple);
        System.out.println("Size: " + size);
        System.out.println("First element: " + firstElement);
        System.out.println("Last element: " + lastElement);
    }
    
    /**
     * EXERCISE 3: Set Operations
     * 
     * C# Example:
     * set1.UnionWith(set2);
     * set1.IntersectWith(set2);
     * set1.ExceptWith(set2);
     */
    public static void exercise3_SetOperations() {
        System.out.println("\n--- Exercise 3: Set Operations ---");
        
        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));
        
        System.out.println("Set1: " + set1);
        System.out.println("Set2: " + set2);
        
        // TODO: Create a new set containing the union of set1 and set2
        Set<Integer> union = null; // Replace with your implementation
        
        // TODO: Create a new set containing the intersection of set1 and set2
        Set<Integer> intersection = null; // Replace with your implementation
        
        // TODO: Create a new set containing elements in set1 but not in set2
        Set<Integer> difference = null; // Replace with your implementation
        
        System.out.println("Union: " + union);
        System.out.println("Intersection: " + intersection);
        System.out.println("Difference: " + difference);
    }
    
    /**
     * EXERCISE 4: Map Operations
     * 
     * C# Example:
     * dict.Add("key", "value");
     * dict.Remove("key");
     * dict.ContainsKey("key");
     * dict.TryGetValue("key", out value);
     * dict.Keys;
     * dict.Values;
     */
    public static void exercise4_MapOperations() {
        System.out.println("\n--- Exercise 4: Map Operations ---");
        
        Map<String, String> countries = new HashMap<>();
        countries.put("USA", "Washington");
        countries.put("UK", "London");
        countries.put("France", "Paris");
        
        System.out.println("Initial countries: " + countries);
        
        // TODO: Add "Germany" -> "Berlin" to the map
        // Your code here
        
        // TODO: Remove "UK" from the map
        // Your code here
        
        // TODO: Check if the map contains key "USA"
        boolean containsUSA = false; // Replace with your implementation
        
        // TODO: Get the value for "France" (handle case where key doesn't exist)
        String franceCapital = null; // Replace with your implementation
        
        // TODO: Get all keys as a Set
        Set<String> allCountries = null; // Replace with your implementation
        
        // TODO: Get all values as a Collection
        Collection<String> allCapitals = null; // Replace with your implementation
        
        // TODO: Get the size of the map
        int mapSize = 0; // Replace with your implementation
        
        System.out.println("Final countries: " + countries);
        System.out.println("Contains USA: " + containsUSA);
        System.out.println("France capital: " + franceCapital);
        System.out.println("All countries: " + allCountries);
        System.out.println("All capitals: " + allCapitals);
        System.out.println("Map size: " + mapSize);
    }
    
    /**
     * EXERCISE 5: Stream Operations (Java equivalent of C# LINQ)
     * 
     * C# Example:
     * numbers.Where(n => n > 5)
     *        .Select(n => n * 2)
     *        .OrderBy(n => n)
     *        .ToList();
     */
    public static void exercise5_StreamOperations() {
        System.out.println("\n--- Exercise 5: Stream Operations ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
        
        System.out.println("Numbers: " + numbers);
        System.out.println("Words: " + words);
        
        // TODO: Filter numbers greater than 5, multiply by 2, and collect to list
        List<Integer> filteredNumbers = null; // Replace with your implementation
        
        // TODO: Get words with length > 5, convert to uppercase, and collect to list
        List<String> longWords = null; // Replace with your implementation
        
        // TODO: Find the sum of all numbers
        int sum = 0; // Replace with your implementation
        
        // TODO: Find the average length of words
        double averageLength = 0.0; // Replace with your implementation
        
        // TODO: Group words by their first letter
        Map<Character, List<String>> wordsByFirstLetter = null; // Replace with your implementation
        
        // TODO: Check if any number is greater than 8
        boolean anyGreaterThan8 = false; // Replace with your implementation
        
        // TODO: Check if all words have length > 3
        boolean allWordsLong = false; // Replace with your implementation
        
        System.out.println("Filtered numbers: " + filteredNumbers);
        System.out.println("Long words: " + longWords);
        System.out.println("Sum: " + sum);
        System.out.println("Average length: " + averageLength);
        System.out.println("Words by first letter: " + wordsByFirstLetter);
        System.out.println("Any > 8: " + anyGreaterThan8);
        System.out.println("All words long: " + allWordsLong);
    }
    
    /**
     * EXERCISE 6: Collection Conversions
     * 
     * C# Example:
     * list.ToArray();
     * array.ToList();
     * list.ToHashSet();
     */
    public static void exercise6_CollectionConversions() {
        System.out.println("\n--- Exercise 6: Collection Conversions ---");
        
        List<String> originalList = Arrays.asList("a", "b", "c", "a", "b");
        String[] originalArray = {"x", "y", "z"};
        
        System.out.println("Original list: " + originalList);
        System.out.println("Original array: " + Arrays.toString(originalArray));
        
        // TODO: Convert list to array
        String[] listToArray = null; // Replace with your implementation
        
        // TODO: Convert array to list
        List<String> arrayToList = null; // Replace with your implementation
        
        // TODO: Convert list to set (removes duplicates)
        Set<String> listToSet = null; // Replace with your implementation
        
        // TODO: Convert set to list
        List<String> setToList = null; // Replace with your implementation
        
        // TODO: Create immutable list from original list
        List<String> immutableList = null; // Replace with your implementation
        
        System.out.println("List to array: " + Arrays.toString(listToArray));
        System.out.println("Array to list: " + arrayToList);
        System.out.println("List to set: " + listToSet);
        System.out.println("Set to list: " + setToList);
        System.out.println("Immutable list: " + immutableList);
    }
    
    /**
     * EXERCISE 7: Custom Collections
     * 
     * Create a custom collection class that maintains a list of unique elements
     * in insertion order (like LinkedHashSet but with custom methods).
     */
    public static void exercise7_CustomCollections() {
        System.out.println("\n--- Exercise 7: Custom Collections ---");
        
        // TODO: Implement UniqueList class (see below)
        UniqueList<String> uniqueList = new UniqueList<>();
        
        // Test the implementation
        uniqueList.add("apple");
        uniqueList.add("banana");
        uniqueList.add("apple"); // Should not add duplicate
        uniqueList.add("cherry");
        
        System.out.println("Unique list: " + uniqueList);
        System.out.println("Size: " + uniqueList.size());
        System.out.println("Contains apple: " + uniqueList.contains("apple"));
        System.out.println("Get first: " + uniqueList.getFirst());
        System.out.println("Get last: " + uniqueList.getLast());
    }
    
    /**
     * EXERCISE 8: Performance Comparison
     * 
     * Compare performance of different collection types for various operations.
     */
    public static void exercise8_PerformanceComparison() {
        System.out.println("\n--- Exercise 8: Performance Comparison ---");
        
        int size = 100000;
        
        // TODO: Create ArrayList, LinkedList, HashSet, and TreeSet with the same data
        List<Integer> arrayList = null; // Replace with your implementation
        List<Integer> linkedList = null; // Replace with your implementation
        Set<Integer> hashSet = null; // Replace with your implementation
        Set<Integer> treeSet = null; // Replace with your implementation
        
        // TODO: Measure and compare:
        // 1. Time to add elements
        // 2. Time to search for element
        // 3. Time to iterate through all elements
        
        System.out.println("Performance comparison completed");
        System.out.println("ArrayList: Fast random access, slower insertion in middle");
        System.out.println("LinkedList: Fast insertion/deletion, slower random access");
        System.out.println("HashSet: Fast add/contains, no ordering");
        System.out.println("TreeSet: Sorted order, slower add/contains");
    }
    
    // TODO: Implement this custom collection class
    public static class UniqueList<T> {
        // TODO: Add private fields to store data
        
        public UniqueList() {
            // TODO: Initialize your data structures
        }
        
        // TODO: Add element only if it's not already present
        public boolean add(T element) {
            // Your implementation here
            return false;
        }
        
        // TODO: Remove element if present
        public boolean remove(T element) {
            // Your implementation here
            return false;
        }
        
        // TODO: Check if element is present
        public boolean contains(T element) {
            // Your implementation here
            return false;
        }
        
        // TODO: Get number of elements
        public int size() {
            // Your implementation here
            return 0;
        }
        
        // TODO: Get first element
        public T getFirst() {
            // Your implementation here
            return null;
        }
        
        // TODO: Get last element
        public T getLast() {
            // Your implementation here
            return null;
        }
        
        // TODO: Get element at index
        public T get(int index) {
            // Your implementation here
            return null;
        }
        
        // TODO: Convert to regular list
        public List<T> toList() {
            // Your implementation here
            return null;
        }
        
        @Override
        public String toString() {
            // TODO: Implement toString method
            return "UniqueList[]";
        }
    }
    
    /**
     * BONUS CHALLENGES:
     * 
     * 1. Implement a method that finds the most frequent element in a list
     * 2. Create a method that merges two sorted lists into one sorted list
     * 3. Implement a method that rotates a list by n positions
     * 4. Create a method that finds all pairs of numbers that sum to a target
     * 5. Implement a simple LRU (Least Recently Used) cache using collections
     */
    
    // TODO: Implement bonus challenge methods here
    
    public static <T> T findMostFrequent(List<T> list) {
        // TODO: Find the most frequent element
        return null;
    }
    
    public static List<Integer> mergeSortedLists(List<Integer> list1, List<Integer> list2) {
        // TODO: Merge two sorted lists
        return null;
    }
    
    public static <T> List<T> rotateList(List<T> list, int positions) {
        // TODO: Rotate list by n positions
        return null;
    }
    
    public static List<int[]> findPairsWithSum(List<Integer> numbers, int target) {
        // TODO: Find all pairs that sum to target
        return null;
    }
    
    // TODO: Implement simple LRU cache
    public static class SimpleLRUCache<K, V> {
        private final int capacity;
        
        public SimpleLRUCache(int capacity) {
            this.capacity = capacity;
            // TODO: Initialize your data structures
        }
        
        public V get(K key) {
            // TODO: Implement get with LRU update
            return null;
        }
        
        public void put(K key, V value) {
            // TODO: Implement put with LRU eviction
        }
        
        public int size() {
            // TODO: Return current size
            return 0;
        }
    }
}