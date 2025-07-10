package com.coherentsolutions.session1.exercises.collections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * COLLECTIONS SOLUTION: Java vs C# Collections
 * 
 * This class provides the complete solutions for the Collections Exercise.
 * Compare your implementation with these solutions and learn from the differences.
 * 
 * KEY LEARNING POINTS:
 * 1. Java Collection Framework hierarchy
 * 2. Interface-based programming (List, Set, Map)
 * 3. Stream API for functional-style operations
 * 4. Performance characteristics of different collections
 * 5. Best practices for collection usage
 */
public class CollectionsSolution {
    
    public static void main(String[] args) {
        System.out.println("=== COLLECTIONS SOLUTION ===");
        
        // Run all solutions
        exercise1_BasicCollections();
        exercise2_ListOperations();
        exercise3_SetOperations();
        exercise4_MapOperations();
        exercise5_StreamOperations();
        exercise6_CollectionConversions();
        exercise7_CustomCollections();
        exercise8_PerformanceComparison();
        
        // Run bonus challenges
        System.out.println("\n--- Bonus Challenges ---");
        bonusChallenges();
        
        System.out.println("\n=== ALL SOLUTIONS DEMONSTRATED ===");
    }
    
    /**
     * SOLUTION 1: Basic Collections
     */
    public static void exercise1_BasicCollections() {
        System.out.println("\n--- Solution 1: Basic Collections ---");
        
        // ✅ SOLUTION: Create a List of strings
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        // Alternative: List<String> names = List.of("Alice", "Bob", "Charlie"); // Java 9+
        // Alternative: List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie"));
        
        // ✅ SOLUTION: Create a Set of integers
        Set<Integer> numbers = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        // Alternative: Set<Integer> numbers = Set.of(1, 2, 3, 4, 5); // Java 9+
        
        // ✅ SOLUTION: Create a Map with name -> age mappings
        Map<String, Integer> ages = new HashMap<>();
        ages.put("Alice", 25);
        ages.put("Bob", 30);
        ages.put("Charlie", 35);
        // Alternative: Map<String, Integer> ages = Map.of("Alice", 25, "Bob", 30, "Charlie", 35); // Java 9+
        
        System.out.println("Names: " + names);
        System.out.println("Numbers: " + numbers);
        System.out.println("Ages: " + ages);
        
        // ✅ EXPLANATION: Collection Type Choices
        System.out.println("\nCollection Type Choices:");
        System.out.println("- List: Ordered, allows duplicates, indexed access");
        System.out.println("- Set: Unique elements, no duplicates, no indexing");
        System.out.println("- Map: Key-value pairs, unique keys, fast lookup");
    }
    
    /**
     * SOLUTION 2: List Operations
     */
    public static void exercise2_ListOperations() {
        System.out.println("\n--- Solution 2: List Operations ---");
        
        List<String> fruits = new ArrayList<>(Arrays.asList("apple", "banana", "orange"));
        System.out.println("Initial fruits: " + fruits);
        
        // ✅ SOLUTION: Add "grape" to the end
        fruits.add("grape");
        
        // ✅ SOLUTION: Add "mango" at index 1
        fruits.add(1, "mango");
        
        // ✅ SOLUTION: Remove "banana"
        fruits.remove("banana");
        
        // ✅ SOLUTION: Check if contains "apple"
        boolean containsApple = fruits.contains("apple");
        
        // ✅ SOLUTION: Get size
        int size = fruits.size();
        
        // ✅ SOLUTION: Get first element
        String firstElement = fruits.isEmpty() ? null : fruits.get(0);
        
        // ✅ SOLUTION: Get last element
        String lastElement = fruits.isEmpty() ? null : fruits.get(fruits.size() - 1);
        
        System.out.println("Final fruits: " + fruits);
        System.out.println("Contains apple: " + containsApple);
        System.out.println("Size: " + size);
        System.out.println("First element: " + firstElement);
        System.out.println("Last element: " + lastElement);
        
        // ✅ EXPLANATION: Java vs C# List Operations
        System.out.println("\nJava vs C# List Operations:");
        System.out.println("C# list.Add(item) → Java list.add(item)");
        System.out.println("C# list.Remove(item) → Java list.remove(item)");
        System.out.println("C# list.Contains(item) → Java list.contains(item)");
        System.out.println("C# list.Count → Java list.size()");
        System.out.println("C# list[index] → Java list.get(index)");
    }
    
    /**
     * SOLUTION 3: Set Operations
     */
    public static void exercise3_SetOperations() {
        System.out.println("\n--- Solution 3: Set Operations ---");
        
        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));
        
        System.out.println("Set1: " + set1);
        System.out.println("Set2: " + set2);
        
        // ✅ SOLUTION: Union of sets
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        
        // ✅ SOLUTION: Intersection of sets
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        
        // ✅ SOLUTION: Difference (set1 - set2)
        Set<Integer> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        
        System.out.println("Union: " + union);
        System.out.println("Intersection: " + intersection);
        System.out.println("Difference: " + difference);
        
        // ✅ EXPLANATION: Java vs C# Set Operations
        System.out.println("\nJava vs C# Set Operations:");
        System.out.println("C# set1.UnionWith(set2) → Java set1.addAll(set2)");
        System.out.println("C# set1.IntersectWith(set2) → Java set1.retainAll(set2)");
        System.out.println("C# set1.ExceptWith(set2) → Java set1.removeAll(set2)");
        System.out.println("Note: Java operations modify the original set, create copy first!");
    }
    
    /**
     * SOLUTION 4: Map Operations
     */
    public static void exercise4_MapOperations() {
        System.out.println("\n--- Solution 4: Map Operations ---");
        
        Map<String, String> countries = new HashMap<>();
        countries.put("USA", "Washington");
        countries.put("UK", "London");
        countries.put("France", "Paris");
        
        System.out.println("Initial countries: " + countries);
        
        // ✅ SOLUTION: Add Germany
        countries.put("Germany", "Berlin");
        
        // ✅ SOLUTION: Remove UK
        countries.remove("UK");
        
        // ✅ SOLUTION: Check if contains USA
        boolean containsUSA = countries.containsKey("USA");
        
        // ✅ SOLUTION: Get France capital safely
        String franceCapital = countries.get("France");
        // Alternative: String franceCapital = countries.getOrDefault("France", "Unknown");
        
        // ✅ SOLUTION: Get all keys
        Set<String> allCountries = countries.keySet();
        
        // ✅ SOLUTION: Get all values
        Collection<String> allCapitals = countries.values();
        
        // ✅ SOLUTION: Get map size
        int mapSize = countries.size();
        
        System.out.println("Final countries: " + countries);
        System.out.println("Contains USA: " + containsUSA);
        System.out.println("France capital: " + franceCapital);
        System.out.println("All countries: " + allCountries);
        System.out.println("All capitals: " + allCapitals);
        System.out.println("Map size: " + mapSize);
        
        // ✅ EXPLANATION: Java vs C# Map Operations
        System.out.println("\nJava vs C# Map Operations:");
        System.out.println("C# dict.Add(key, value) → Java map.put(key, value)");
        System.out.println("C# dict.Remove(key) → Java map.remove(key)");
        System.out.println("C# dict.ContainsKey(key) → Java map.containsKey(key)");
        System.out.println("C# dict.TryGetValue(key, out value) → Java map.getOrDefault(key, default)");
        System.out.println("C# dict.Keys → Java map.keySet()");
        System.out.println("C# dict.Values → Java map.values()");
    }
    
    /**
     * SOLUTION 5: Stream Operations
     */
    public static void exercise5_StreamOperations() {
        System.out.println("\n--- Solution 5: Stream Operations ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
        
        System.out.println("Numbers: " + numbers);
        System.out.println("Words: " + words);
        
        // ✅ SOLUTION: Filter, map, collect
        List<Integer> filteredNumbers = numbers.stream()
            .filter(n -> n > 5)
            .map(n -> n * 2)
            .collect(Collectors.toList());
        
        // ✅ SOLUTION: Filter words, transform, collect
        List<String> longWords = words.stream()
            .filter(word -> word.length() > 5)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        
        // ✅ SOLUTION: Sum all numbers
        int sum = numbers.stream()
            .mapToInt(Integer::intValue)
            .sum();
        
        // ✅ SOLUTION: Average word length
        double averageLength = words.stream()
            .mapToInt(String::length)
            .average()
            .orElse(0.0);
        
        // ✅ SOLUTION: Group by first letter
        Map<Character, List<String>> wordsByFirstLetter = words.stream()
            .collect(Collectors.groupingBy(word -> word.charAt(0)));
        
        // ✅ SOLUTION: Any match
        boolean anyGreaterThan8 = numbers.stream()
            .anyMatch(n -> n > 8);
        
        // ✅ SOLUTION: All match
        boolean allWordsLong = words.stream()
            .allMatch(word -> word.length() > 3);
        
        System.out.println("Filtered numbers: " + filteredNumbers);
        System.out.println("Long words: " + longWords);
        System.out.println("Sum: " + sum);
        System.out.println("Average length: " + averageLength);
        System.out.println("Words by first letter: " + wordsByFirstLetter);
        System.out.println("Any > 8: " + anyGreaterThan8);
        System.out.println("All words long: " + allWordsLong);
        
        // ✅ EXPLANATION: Java Streams vs C# LINQ
        System.out.println("\nJava Streams vs C# LINQ:");
        System.out.println("C# .Where() → Java .filter()");
        System.out.println("C# .Select() → Java .map()");
        System.out.println("C# .OrderBy() → Java .sorted()");
        System.out.println("C# .ToList() → Java .collect(Collectors.toList())");
        System.out.println("C# .Sum() → Java .mapToInt().sum()");
        System.out.println("C# .Average() → Java .mapToInt().average()");
        System.out.println("C# .GroupBy() → Java .collect(Collectors.groupingBy())");
        System.out.println("C# .Any() → Java .anyMatch()");
        System.out.println("C# .All() → Java .allMatch()");
    }
    
    /**
     * SOLUTION 6: Collection Conversions
     */
    public static void exercise6_CollectionConversions() {
        System.out.println("\n--- Solution 6: Collection Conversions ---");
        
        List<String> originalList = Arrays.asList("a", "b", "c", "a", "b");
        String[] originalArray = {"x", "y", "z"};
        
        System.out.println("Original list: " + originalList);
        System.out.println("Original array: " + Arrays.toString(originalArray));
        
        // ✅ SOLUTION: List to array
        String[] listToArray = originalList.toArray(new String[0]);
        
        // ✅ SOLUTION: Array to list
        List<String> arrayToList = Arrays.asList(originalArray);
        // Alternative: List<String> arrayToList = new ArrayList<>(Arrays.asList(originalArray));
        
        // ✅ SOLUTION: List to set (removes duplicates)
        Set<String> listToSet = new HashSet<>(originalList);
        
        // ✅ SOLUTION: Set to list
        List<String> setToList = new ArrayList<>(listToSet);
        
        // ✅ SOLUTION: Immutable list
        List<String> immutableList = Collections.unmodifiableList(new ArrayList<>(originalList));
        // Alternative: List<String> immutableList = List.copyOf(originalList); // Java 10+
        
        System.out.println("List to array: " + Arrays.toString(listToArray));
        System.out.println("Array to list: " + arrayToList);
        System.out.println("List to set: " + listToSet);
        System.out.println("Set to list: " + setToList);
        System.out.println("Immutable list: " + immutableList);
        
        // ✅ EXPLANATION: Java vs C# Conversions
        System.out.println("\nJava vs C# Conversions:");
        System.out.println("C# list.ToArray() → Java list.toArray(new T[0])");
        System.out.println("C# array.ToList() → Java Arrays.asList(array)");
        System.out.println("C# list.ToHashSet() → Java new HashSet<>(list)");
        System.out.println("C# ImmutableList.CreateRange() → Java Collections.unmodifiableList()");
    }
    
    /**
     * SOLUTION 7: Custom Collections
     */
    public static void exercise7_CustomCollections() {
        System.out.println("\n--- Solution 7: Custom Collections ---");
        
        UniqueListSolution<String> uniqueList = new UniqueListSolution<>();
        
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
        System.out.println("Get at index 1: " + uniqueList.get(1));
        
        uniqueList.remove("banana");
        System.out.println("After removing banana: " + uniqueList);
    }
    
    /**
     * SOLUTION 8: Performance Comparison
     */
    public static void exercise8_PerformanceComparison() {
        System.out.println("\n--- Solution 8: Performance Comparison ---");
        
        int size = 100000;
        
        // ✅ SOLUTION: Create different collections
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> treeSet = new TreeSet<>();
        
        // Fill collections with data
        for (int i = 0; i < size; i++) {
            arrayList.add(i);
            linkedList.add(i);
            hashSet.add(i);
            treeSet.add(i);
        }
        
        // ✅ SOLUTION: Performance measurements
        int searchElement = size / 2;
        
        // ArrayList - Fast random access, slow insertion in middle
        long startTime = System.nanoTime();
        boolean found = arrayList.contains(searchElement);
        long arrayListSearchTime = System.nanoTime() - startTime;
        
        // LinkedList - Slow random access, fast insertion/deletion
        startTime = System.nanoTime();
        found = linkedList.contains(searchElement);
        long linkedListSearchTime = System.nanoTime() - startTime;
        
        // HashSet - Fast contains, no ordering
        startTime = System.nanoTime();
        found = hashSet.contains(searchElement);
        long hashSetSearchTime = System.nanoTime() - startTime;
        
        // TreeSet - Sorted order, logarithmic operations
        startTime = System.nanoTime();
        found = treeSet.contains(searchElement);
        long treeSetSearchTime = System.nanoTime() - startTime;
        
        System.out.println("Performance comparison for " + size + " elements:");
        System.out.println("ArrayList search time: " + arrayListSearchTime + " ns");
        System.out.println("LinkedList search time: " + linkedListSearchTime + " ns");
        System.out.println("HashSet search time: " + hashSetSearchTime + " ns");
        System.out.println("TreeSet search time: " + treeSetSearchTime + " ns");
        
        // ✅ EXPLANATION: When to use each collection
        System.out.println("\nWhen to use each collection:");
        System.out.println("ArrayList: Default choice for most lists, fast random access");
        System.out.println("LinkedList: When you need fast insertion/deletion in middle");
        System.out.println("HashSet: When you need fast contains() and don't care about order");
        System.out.println("TreeSet: When you need sorted order and can accept slower operations");
        System.out.println("HashMap: Fast key-value lookups, no ordering");
        System.out.println("LinkedHashMap: Maintains insertion order, slightly slower");
        System.out.println("TreeMap: Sorted by keys, logarithmic operations");
    }
    
    /**
     * BONUS CHALLENGES SOLUTIONS
     */
    public static void bonusChallenges() {
        System.out.println("\n--- Bonus Challenges Solutions ---");
        
        // Test most frequent element
        List<String> testList = Arrays.asList("apple", "banana", "apple", "cherry", "apple", "banana");
        String mostFrequent = findMostFrequent(testList);
        System.out.println("Most frequent in " + testList + ": " + mostFrequent);
        
        // Test merge sorted lists
        List<Integer> list1 = Arrays.asList(1, 3, 5, 7);
        List<Integer> list2 = Arrays.asList(2, 4, 6, 8);
        List<Integer> merged = mergeSortedLists(list1, list2);
        System.out.println("Merged " + list1 + " and " + list2 + ": " + merged);
        
        // Test rotate list
        List<String> originalList = Arrays.asList("a", "b", "c", "d", "e");
        List<String> rotated = rotateList(originalList, 2);
        System.out.println("Rotated " + originalList + " by 2: " + rotated);
        
        // Test find pairs
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<int[]> pairs = findPairsWithSum(numbers, 7);
        System.out.println("Pairs that sum to 7 in " + numbers + ":");
        pairs.forEach(pair -> System.out.println("  [" + pair[0] + ", " + pair[1] + "]"));
        
        // Test LRU cache
        SimpleLRUCacheSolution<String, String> cache = new SimpleLRUCacheSolution<>(3);
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3");
        System.out.println("LRU cache after adding a,b,c: " + cache);
        cache.get("a"); // Access 'a' to make it recently used
        cache.put("d", "4"); // Should evict 'b' (least recently used)
        System.out.println("LRU cache after accessing 'a' and adding 'd': " + cache);
    }
    
    // ✅ SOLUTION: Custom UniqueList implementation
    public static class UniqueListSolution<T> {
        private final List<T> list;
        private final Set<T> set;
        
        public UniqueListSolution() {
            this.list = new ArrayList<>();
            this.set = new HashSet<>();
        }
        
        public boolean add(T element) {
            if (set.add(element)) {
                list.add(element);
                return true;
            }
            return false;
        }
        
        public boolean remove(T element) {
            if (set.remove(element)) {
                list.remove(element);
                return true;
            }
            return false;
        }
        
        public boolean contains(T element) {
            return set.contains(element);
        }
        
        public int size() {
            return list.size();
        }
        
        public T getFirst() {
            return list.isEmpty() ? null : list.get(0);
        }
        
        public T getLast() {
            return list.isEmpty() ? null : list.get(list.size() - 1);
        }
        
        public T get(int index) {
            return list.get(index);
        }
        
        public List<T> toList() {
            return new ArrayList<>(list);
        }
        
        @Override
        public String toString() {
            return "UniqueList" + list;
        }
    }
    
    // ✅ SOLUTION: Find most frequent element
    public static <T> T findMostFrequent(List<T> list) {
        if (list.isEmpty()) return null;
        
        Map<T, Integer> frequency = new HashMap<>();
        for (T element : list) {
            frequency.put(element, frequency.getOrDefault(element, 0) + 1);
        }
        
        return frequency.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
    
    // ✅ SOLUTION: Merge sorted lists
    public static List<Integer> mergeSortedLists(List<Integer> list1, List<Integer> list2) {
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;
        
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i) <= list2.get(j)) {
                result.add(list1.get(i++));
            } else {
                result.add(list2.get(j++));
            }
        }
        
        while (i < list1.size()) {
            result.add(list1.get(i++));
        }
        
        while (j < list2.size()) {
            result.add(list2.get(j++));
        }
        
        return result;
    }
    
    // ✅ SOLUTION: Rotate list
    public static <T> List<T> rotateList(List<T> list, int positions) {
        if (list.isEmpty()) return new ArrayList<>(list);
        
        int size = list.size();
        positions = positions % size;
        if (positions < 0) positions += size;
        
        List<T> result = new ArrayList<>(list);
        Collections.rotate(result, positions);
        return result;
    }
    
    // ✅ SOLUTION: Find pairs with sum
    public static List<int[]> findPairsWithSum(List<Integer> numbers, int target) {
        List<int[]> pairs = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        
        for (Integer num : numbers) {
            int complement = target - num;
            if (seen.contains(complement)) {
                pairs.add(new int[]{Math.min(num, complement), Math.max(num, complement)});
            }
            seen.add(num);
        }
        
        return pairs;
    }
    
    // ✅ SOLUTION: Simple LRU Cache
    public static class SimpleLRUCacheSolution<K, V> {
        private final int capacity;
        private final LinkedHashMap<K, V> cache;
        
        public SimpleLRUCacheSolution(int capacity) {
            this.capacity = capacity;
            this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                    return size() > SimpleLRUCacheSolution.this.capacity;
                }
            };
        }
        
        public V get(K key) {
            return cache.get(key);
        }
        
        public void put(K key, V value) {
            cache.put(key, value);
        }
        
        public int size() {
            return cache.size();
        }
        
        @Override
        public String toString() {
            return cache.toString();
        }
    }
}