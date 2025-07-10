package com.coherentsolutions.session1.reference;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * COLLECTION EXAMPLES REFERENCE: Java Collections Framework
 * 
 * This reference class provides comprehensive examples of Java Collections Framework
 * with direct comparisons to C# collections. Perfect for live coding demonstrations.
 * 
 * COVERAGE:
 * 1. List implementations (ArrayList, LinkedList, Vector)
 * 2. Set implementations (HashSet, LinkedHashSet, TreeSet)
 * 3. Map implementations (HashMap, LinkedHashMap, TreeMap)
 * 4. Queue implementations (ArrayDeque, PriorityQueue)
 * 5. Concurrent collections
 * 6. Collection utilities and algorithms
 * 7. Performance characteristics
 * 8. Best practices for collection selection
 * 
 * FOR LECTURE USE:
 * - Demonstrate each collection type with examples
 * - Show when to use which collection
 * - Compare performance characteristics
 * - Highlight common mistakes and solutions
 */
public class CollectionExamples {
    
    public static void main(String[] args) {
        System.out.println("=== COLLECTION EXAMPLES REFERENCE ===");
        
        demonstrateListImplementations();
        demonstrateSetImplementations();
        demonstrateMapImplementations();
        demonstrateQueueImplementations();
        demonstrateConcurrentCollections();
        demonstrateCollectionUtilities();
        demonstratePerformanceComparison();
        demonstrateBestPractices();
        
        System.out.println("\n=== COLLECTION EXAMPLES COMPLETE ===");
    }
    
    /**
     * LIST IMPLEMENTATIONS
     * 
     * Java: ArrayList, LinkedList, Vector, CopyOnWriteArrayList
     * C#: List<T>, LinkedList<T>, ArrayList (legacy)
     */
    public static void demonstrateListImplementations() {
        System.out.println("\n--- List Implementations ---");
        
        // ArrayList - Resizable array, most commonly used
        List<String> arrayList = new ArrayList<>();
        arrayList.add("First");
        arrayList.add("Second");
        arrayList.add("Third");
        arrayList.add(1, "Inserted"); // Insert at index
        
        System.out.println("ArrayList (like C# List<T>):");
        System.out.println("- Dynamic array implementation");
        System.out.println("- Fast random access O(1)");
        System.out.println("- Slow insertion/deletion in middle O(n)");
        System.out.println("Contents: " + arrayList);
        System.out.println("Get index 2: " + arrayList.get(2));
        
        // LinkedList - Doubly linked list
        List<String> linkedList = new LinkedList<>();
        linkedList.add("Alpha");
        linkedList.add("Beta");
        linkedList.add("Gamma");
        ((LinkedList<String>) linkedList).addFirst("Beginning");
        ((LinkedList<String>) linkedList).addLast("End");
        
        System.out.println("\nLinkedList (like C# LinkedList<T>):");
        System.out.println("- Doubly linked list implementation");
        System.out.println("- Fast insertion/deletion O(1)");
        System.out.println("- Slow random access O(n)");
        System.out.println("Contents: " + linkedList);
        System.out.println("First: " + ((LinkedList<String>) linkedList).getFirst());
        System.out.println("Last: " + ((LinkedList<String>) linkedList).getLast());
        
        // Vector - Synchronized ArrayList (legacy, like C# ArrayList)
        List<String> vector = new Vector<>();
        vector.add("Legacy");
        vector.add("Synchronized");
        vector.add("Rarely used");
        
        System.out.println("\nVector (legacy, like C# ArrayList):");
        System.out.println("- Synchronized ArrayList");
        System.out.println("- Thread-safe but slower");
        System.out.println("- Generally avoid in new code");
        System.out.println("Contents: " + vector);
        
        // List operations comparison
        System.out.println("\nList Operations Comparison:");
        System.out.println("Java ArrayList.add() ↔ C# List<T>.Add()");
        System.out.println("Java ArrayList.get(i) ↔ C# List<T>[i]");
        System.out.println("Java ArrayList.set(i, val) ↔ C# List<T>[i] = val");
        System.out.println("Java ArrayList.remove(i) ↔ C# List<T>.RemoveAt(i)");
        System.out.println("Java ArrayList.size() ↔ C# List<T>.Count");
        System.out.println("Java ArrayList.contains() ↔ C# List<T>.Contains()");
        System.out.println("Java ArrayList.indexOf() ↔ C# List<T>.IndexOf()");
        
        // Demonstrate some operations
        System.out.println("\nList operation examples:");
        System.out.println("Size: " + arrayList.size());
        System.out.println("Contains 'Second': " + arrayList.contains("Second"));
        System.out.println("Index of 'Third': " + arrayList.indexOf("Third"));
        
        // Sublist operations
        List<String> subList = arrayList.subList(1, 3);
        System.out.println("Sublist [1,3): " + subList);
    }
    
    /**
     * SET IMPLEMENTATIONS
     * 
     * Java: HashSet, LinkedHashSet, TreeSet, EnumSet
     * C#: HashSet<T>, SortedSet<T>
     */
    public static void demonstrateSetImplementations() {
        System.out.println("\n--- Set Implementations ---");
        
        // HashSet - Hash table implementation
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");
        hashSet.add("Apple"); // Duplicate, won't be added
        
        System.out.println("HashSet (like C# HashSet<T>):");
        System.out.println("- Hash table implementation");
        System.out.println("- Fast add/remove/contains O(1)");
        System.out.println("- No ordering guarantee");
        System.out.println("Contents: " + hashSet);
        System.out.println("Size: " + hashSet.size() + " (duplicate ignored)");
        
        // LinkedHashSet - Maintains insertion order
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("First");
        linkedHashSet.add("Second");
        linkedHashSet.add("Third");
        linkedHashSet.add("First"); // Duplicate
        
        System.out.println("\nLinkedHashSet:");
        System.out.println("- Hash table + linked list");
        System.out.println("- Maintains insertion order");
        System.out.println("- Slightly slower than HashSet");
        System.out.println("Contents: " + linkedHashSet);
        
        // TreeSet - Sorted set
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Zebra");
        treeSet.add("Apple");
        treeSet.add("Monkey");
        treeSet.add("Cat");
        
        System.out.println("\nTreeSet (like C# SortedSet<T>):");
        System.out.println("- Red-black tree implementation");
        System.out.println("- Maintains sorted order");
        System.out.println("- O(log n) operations");
        System.out.println("Contents: " + treeSet);
        System.out.println("First: " + ((TreeSet<String>) treeSet).first());
        System.out.println("Last: " + ((TreeSet<String>) treeSet).last());
        
        // Set operations
        Set<String> set1 = new HashSet<>(Arrays.asList("A", "B", "C"));
        Set<String> set2 = new HashSet<>(Arrays.asList("B", "C", "D"));
        
        // Union
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        
        // Intersection
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        
        // Difference
        Set<String> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        
        System.out.println("\nSet Operations:");
        System.out.println("Set1: " + set1);
        System.out.println("Set2: " + set2);
        System.out.println("Union: " + union);
        System.out.println("Intersection: " + intersection);
        System.out.println("Difference (Set1 - Set2): " + difference);
        
        // Comparison with C#
        System.out.println("\nJava vs C# Set Operations:");
        System.out.println("Java set1.addAll(set2) ↔ C# set1.UnionWith(set2)");
        System.out.println("Java set1.retainAll(set2) ↔ C# set1.IntersectWith(set2)");
        System.out.println("Java set1.removeAll(set2) ↔ C# set1.ExceptWith(set2)");
    }
    
    /**
     * MAP IMPLEMENTATIONS
     * 
     * Java: HashMap, LinkedHashMap, TreeMap, ConcurrentHashMap
     * C#: Dictionary<K,V>, SortedDictionary<K,V>, ConcurrentDictionary<K,V>
     */
    public static void demonstrateMapImplementations() {
        System.out.println("\n--- Map Implementations ---");
        
        // HashMap - Hash table implementation
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("apple", 5);
        hashMap.put("banana", 3);
        hashMap.put("cherry", 8);
        hashMap.put("apple", 7); // Overwrites previous value
        
        System.out.println("HashMap (like C# Dictionary<K,V>):");
        System.out.println("- Hash table implementation");
        System.out.println("- Fast get/put/remove O(1)");
        System.out.println("- No ordering guarantee");
        System.out.println("Contents: " + hashMap);
        System.out.println("Get 'banana': " + hashMap.get("banana"));
        System.out.println("Get 'orange': " + hashMap.get("orange")); // Returns null
        System.out.println("Get with default: " + hashMap.getOrDefault("orange", 0));
        
        // LinkedHashMap - Maintains insertion order
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("first", "1st");
        linkedHashMap.put("second", "2nd");
        linkedHashMap.put("third", "3rd");
        
        System.out.println("\nLinkedHashMap:");
        System.out.println("- Hash table + linked list");
        System.out.println("- Maintains insertion order");
        System.out.println("- Useful for LRU caches");
        System.out.println("Contents: " + linkedHashMap);
        
        // TreeMap - Sorted map
        Map<String, Double> treeMap = new TreeMap<>();
        treeMap.put("zebra", 2.5);
        treeMap.put("apple", 1.2);
        treeMap.put("monkey", 3.7);
        
        System.out.println("\nTreeMap (like C# SortedDictionary<K,V>):");
        System.out.println("- Red-black tree implementation");
        System.out.println("- Maintains sorted key order");
        System.out.println("- O(log n) operations");
        System.out.println("Contents: " + treeMap);
        System.out.println("First key: " + ((TreeMap<String, Double>) treeMap).firstKey());
        System.out.println("Last key: " + ((TreeMap<String, Double>) treeMap).lastKey());
        
        // Map operations
        System.out.println("\nMap Operations:");
        System.out.println("Keys: " + hashMap.keySet());
        System.out.println("Values: " + hashMap.values());
        System.out.println("Entries: " + hashMap.entrySet());
        
        // Iterating over map
        System.out.println("\nMap iteration patterns:");
        System.out.println("1. Key iteration:");
        for (String key : hashMap.keySet()) {
            System.out.println("   " + key + " = " + hashMap.get(key));
        }
        
        System.out.println("2. Entry iteration:");
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println("   " + entry.getKey() + " = " + entry.getValue());
        }
        
        System.out.println("3. forEach (Java 8+):");
        hashMap.forEach((key, value) -> System.out.println("   " + key + " = " + value));
        
        // Map utility methods
        System.out.println("\nMap utility methods:");
        hashMap.putIfAbsent("date", 4);
        System.out.println("After putIfAbsent: " + hashMap);
        
        hashMap.compute("apple", (key, value) -> value * 2);
        System.out.println("After compute: " + hashMap);
        
        hashMap.merge("fig", 1, Integer::sum);
        System.out.println("After merge: " + hashMap);
        
        // Comparison with C#
        System.out.println("\nJava vs C# Map Operations:");
        System.out.println("Java map.put(k, v) ↔ C# dict[k] = v or dict.Add(k, v)");
        System.out.println("Java map.get(k) ↔ C# dict[k] or dict.TryGetValue(k, out v)");
        System.out.println("Java map.getOrDefault(k, def) ↔ C# dict.GetValueOrDefault(k, def)");
        System.out.println("Java map.containsKey(k) ↔ C# dict.ContainsKey(k)");
        System.out.println("Java map.remove(k) ↔ C# dict.Remove(k)");
        System.out.println("Java map.keySet() ↔ C# dict.Keys");
        System.out.println("Java map.values() ↔ C# dict.Values");
    }
    
    /**
     * QUEUE IMPLEMENTATIONS
     * 
     * Java: ArrayDeque, LinkedList, PriorityQueue
     * C#: Queue<T>, PriorityQueue<T>, Stack<T>
     */
    public static void demonstrateQueueImplementations() {
        System.out.println("\n--- Queue Implementations ---");
        
        // ArrayDeque - Double-ended queue
        Deque<String> arrayDeque = new ArrayDeque<>();
        arrayDeque.addFirst("First");
        arrayDeque.addLast("Last");
        arrayDeque.addFirst("New First");
        
        System.out.println("ArrayDeque (like C# Queue<T> + Stack<T>):");
        System.out.println("- Resizable array implementation");
        System.out.println("- Can be used as queue or stack");
        System.out.println("- Fast add/remove at both ends");
        System.out.println("Contents: " + arrayDeque);
        System.out.println("Peek first: " + arrayDeque.peekFirst());
        System.out.println("Peek last: " + arrayDeque.peekLast());
        
        // Queue operations
        Queue<String> queue = new ArrayDeque<>();
        queue.offer("Task 1");
        queue.offer("Task 2");
        queue.offer("Task 3");
        
        System.out.println("\nQueue operations:");
        System.out.println("Queue: " + queue);
        while (!queue.isEmpty()) {
            String task = queue.poll();
            System.out.println("Processing: " + task + ", remaining: " + queue);
        }
        
        // Stack operations using Deque
        Deque<String> stack = new ArrayDeque<>();
        stack.push("Bottom");
        stack.push("Middle");
        stack.push("Top");
        
        System.out.println("\nStack operations (using Deque):");
        System.out.println("Stack: " + stack);
        while (!stack.isEmpty()) {
            String item = stack.pop();
            System.out.println("Popped: " + item + ", remaining: " + stack);
        }
        
        // PriorityQueue - Heap implementation
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(5);
        priorityQueue.offer(1);
        priorityQueue.offer(8);
        priorityQueue.offer(3);
        
        System.out.println("\nPriorityQueue (like C# PriorityQueue<T>):");
        System.out.println("- Heap implementation");
        System.out.println("- Elements ordered by priority");
        System.out.println("- O(log n) insertion/removal");
        System.out.println("Order added: 5, 1, 8, 3");
        System.out.print("Priority order: ");
        while (!priorityQueue.isEmpty()) {
            System.out.print(priorityQueue.poll() + " ");
        }
        System.out.println();
        
        // Custom priority queue
        PriorityQueue<String> customPriority = new PriorityQueue<>((a, b) -> b.length() - a.length());
        customPriority.offer("a");
        customPriority.offer("medium");
        customPriority.offer("very long string");
        customPriority.offer("short");
        
        System.out.println("\nCustom priority (by length):");
        while (!customPriority.isEmpty()) {
            System.out.println("Next: " + customPriority.poll());
        }
        
        // Comparison with C#
        System.out.println("\nJava vs C# Queue/Stack Operations:");
        System.out.println("Java queue.offer() ↔ C# queue.Enqueue()");
        System.out.println("Java queue.poll() ↔ C# queue.Dequeue()");
        System.out.println("Java queue.peek() ↔ C# queue.Peek()");
        System.out.println("Java stack.push() ↔ C# stack.Push()");
        System.out.println("Java stack.pop() ↔ C# stack.Pop()");
        System.out.println("Java stack.peek() ↔ C# stack.Peek()");
    }
    
    /**
     * CONCURRENT COLLECTIONS
     * 
     * Thread-safe collections for concurrent programming
     */
    public static void demonstrateConcurrentCollections() {
        System.out.println("\n--- Concurrent Collections ---");
        
        // ConcurrentHashMap - Thread-safe map
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("counter", 0);
        concurrentMap.put("total", 100);
        
        System.out.println("ConcurrentHashMap (like C# ConcurrentDictionary<K,V>):");
        System.out.println("- Thread-safe hash map");
        System.out.println("- Better performance than synchronized HashMap");
        System.out.println("- Atomic operations available");
        System.out.println("Contents: " + concurrentMap);
        
        // Atomic operations
        concurrentMap.compute("counter", (key, value) -> value + 1);
        concurrentMap.computeIfAbsent("new_key", key -> 42);
        
        System.out.println("After atomic operations: " + concurrentMap);
        
        // CopyOnWriteArrayList - Thread-safe list
        List<String> cowList = new CopyOnWriteArrayList<>();
        cowList.add("Item 1");
        cowList.add("Item 2");
        
        System.out.println("\nCopyOnWriteArrayList:");
        System.out.println("- Thread-safe list");
        System.out.println("- Copy-on-write strategy");
        System.out.println("- Good for read-heavy scenarios");
        System.out.println("Contents: " + cowList);
        
        // Synchronized collections (legacy approach)
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        Map<String, String> syncMap = Collections.synchronizedMap(new HashMap<>());
        
        System.out.println("\nSynchronized Collections (legacy):");
        System.out.println("- Wrapper around non-thread-safe collections");
        System.out.println("- Synchronized methods but not atomic operations");
        System.out.println("- Generally prefer concurrent collections");
        
        // Comparison with C#
        System.out.println("\nJava vs C# Concurrent Collections:");
        System.out.println("Java ConcurrentHashMap ↔ C# ConcurrentDictionary<K,V>");
        System.out.println("Java CopyOnWriteArrayList ↔ C# ImmutableList<T> (similar concept)");
        System.out.println("Java Collections.synchronizedList() ↔ C# lock statements");
    }
    
    /**
     * COLLECTION UTILITIES
     * 
     * Utility methods and algorithms for collections
     */
    public static void demonstrateCollectionUtilities() {
        System.out.println("\n--- Collection Utilities ---");
        
        List<String> list = new ArrayList<>(Arrays.asList("apple", "banana", "cherry", "date"));
        
        // Collections utility methods
        System.out.println("Original list: " + list);
        
        Collections.sort(list);
        System.out.println("Sorted: " + list);
        
        Collections.reverse(list);
        System.out.println("Reversed: " + list);
        
        Collections.shuffle(list);
        System.out.println("Shuffled: " + list);
        
        String max = Collections.max(list);
        String min = Collections.min(list);
        System.out.println("Max: " + max + ", Min: " + min);
        
        // Binary search (requires sorted list)
        Collections.sort(list);
        int index = Collections.binarySearch(list, "cherry");
        System.out.println("Binary search for 'cherry': " + index);
        
        // Frequency and disjoint
        List<String> testList = Arrays.asList("a", "b", "a", "c", "a");
        int frequency = Collections.frequency(testList, "a");
        System.out.println("Frequency of 'a' in " + testList + ": " + frequency);
        
        // Creating immutable collections
        List<String> immutableList = Collections.unmodifiableList(new ArrayList<>(list));
        Set<String> immutableSet = Collections.unmodifiableSet(new HashSet<>(list));
        Map<String, Integer> immutableMap = Collections.unmodifiableMap(
            Map.of("key1", 1, "key2", 2));
        
        System.out.println("\nImmutable collections:");
        System.out.println("Immutable list: " + immutableList);
        System.out.println("Immutable set: " + immutableSet);
        System.out.println("Immutable map: " + immutableMap);
        
        // Empty collections
        List<String> emptyList = Collections.emptyList();
        Set<String> emptySet = Collections.emptySet();
        Map<String, String> emptyMap = Collections.emptyMap();
        
        System.out.println("\nEmpty collections (singletons):");
        System.out.println("Empty list: " + emptyList);
        System.out.println("Empty set: " + emptySet);
        System.out.println("Empty map: " + emptyMap);
        
        // Singleton collections
        List<String> singletonList = Collections.singletonList("only");
        Set<String> singletonSet = Collections.singleton("only");
        Map<String, String> singletonMap = Collections.singletonMap("key", "value");
        
        System.out.println("\nSingleton collections:");
        System.out.println("Singleton list: " + singletonList);
        System.out.println("Singleton set: " + singletonSet);
        System.out.println("Singleton map: " + singletonMap);
        
        // Comparison with C#
        System.out.println("\nJava vs C# Collection Utilities:");
        System.out.println("Java Collections.sort() ↔ C# list.Sort() or list.OrderBy()");
        System.out.println("Java Collections.reverse() ↔ C# list.Reverse()");
        System.out.println("Java Collections.shuffle() ↔ C# Random.Shuffle() (.NET 8+)");
        System.out.println("Java Collections.binarySearch() ↔ C# Array.BinarySearch()");
        System.out.println("Java Collections.unmodifiableList() ↔ C# IReadOnlyList<T>");
    }
    
    /**
     * PERFORMANCE COMPARISON
     * 
     * Understanding when to use which collection
     */
    public static void demonstratePerformanceComparison() {
        System.out.println("\n--- Performance Comparison ---");
        
        System.out.println("LIST IMPLEMENTATIONS:");
        System.out.println("ArrayList:");
        System.out.println("  ✓ Fast random access O(1)");
        System.out.println("  ✓ Memory efficient");
        System.out.println("  ✗ Slow insertion/deletion in middle O(n)");
        System.out.println("  ✗ Slow search O(n)");
        
        System.out.println("\nLinkedList:");
        System.out.println("  ✓ Fast insertion/deletion O(1)");
        System.out.println("  ✓ No capacity concerns");
        System.out.println("  ✗ Slow random access O(n)");
        System.out.println("  ✗ Higher memory overhead");
        
        System.out.println("\nSET IMPLEMENTATIONS:");
        System.out.println("HashSet:");
        System.out.println("  ✓ Fast add/remove/contains O(1)");
        System.out.println("  ✓ Memory efficient");
        System.out.println("  ✗ No ordering");
        System.out.println("  ✗ Requires good hashCode()");
        
        System.out.println("\nTreeSet:");
        System.out.println("  ✓ Sorted order");
        System.out.println("  ✓ Range operations");
        System.out.println("  ✗ Slower operations O(log n)");
        System.out.println("  ✗ Requires Comparable or Comparator");
        
        System.out.println("\nMAP IMPLEMENTATIONS:");
        System.out.println("HashMap:");
        System.out.println("  ✓ Fast get/put/remove O(1)");
        System.out.println("  ✓ Memory efficient");
        System.out.println("  ✗ No ordering");
        System.out.println("  ✗ Not thread-safe");
        
        System.out.println("\nTreeMap:");
        System.out.println("  ✓ Sorted key order");
        System.out.println("  ✓ Range operations");
        System.out.println("  ✗ Slower operations O(log n)");
        System.out.println("  ✗ Requires Comparable keys");
        
        System.out.println("\nConcurrentHashMap:");
        System.out.println("  ✓ Thread-safe");
        System.out.println("  ✓ Good concurrent performance");
        System.out.println("  ✗ Slightly slower than HashMap");
        System.out.println("  ✗ Higher memory usage");
    }
    
    /**
     * BEST PRACTICES
     * 
     * Guidelines for choosing and using collections
     */
    public static void demonstrateBestPractices() {
        System.out.println("\n--- Best Practices ---");
        
        System.out.println("COLLECTION SELECTION GUIDE:");
        System.out.println("\nUse ArrayList when:");
        System.out.println("- You need fast random access");
        System.out.println("- Most operations are reads");
        System.out.println("- Memory efficiency is important");
        
        System.out.println("\nUse LinkedList when:");
        System.out.println("- Frequent insertion/deletion in middle");
        System.out.println("- Unknown size variations");
        System.out.println("- Using as queue/deque");
        
        System.out.println("\nUse HashSet when:");
        System.out.println("- Need unique elements");
        System.out.println("- Fast contains() is important");
        System.out.println("- Order doesn't matter");
        
        System.out.println("\nUse TreeSet when:");
        System.out.println("- Need sorted unique elements");
        System.out.println("- Range operations required");
        System.out.println("- Natural ordering is sufficient");
        
        System.out.println("\nUse HashMap when:");
        System.out.println("- Key-value mapping needed");
        System.out.println("- Fast lookups required");
        System.out.println("- Single-threaded access");
        
        System.out.println("\nUse ConcurrentHashMap when:");
        System.out.println("- Multi-threaded access");
        System.out.println("- High read/write ratio");
        System.out.println("- Atomic operations needed");
        
        System.out.println("\nGENERAL BEST PRACTICES:");
        System.out.println("1. Program to interfaces (List, Set, Map)");
        System.out.println("2. Specify initial capacity for known sizes");
        System.out.println("3. Use diamond operator for type inference");
        System.out.println("4. Consider immutable collections for safety");
        System.out.println("5. Use concurrent collections for thread safety");
        System.out.println("6. Implement proper equals() and hashCode()");
        System.out.println("7. Use try-with-resources for Iterator");
        System.out.println("8. Prefer Stream API for complex operations");
        
        // Code examples of best practices
        System.out.println("\nCODE EXAMPLES:");
        
        // 1. Program to interfaces
        List<String> good = new ArrayList<>();           // ✓ Good
        // ArrayList<String> bad = new ArrayList<>();    // ✗ Less flexible
        
        // 2. Specify initial capacity
        List<String> sized = new ArrayList<>(100);      // ✓ Good for known size
        
        // 3. Diamond operator
        Map<String, List<String>> modern = new HashMap<>();  // ✓ Java 7+
        
        // 4. Immutable when possible
        List<String> immutable = List.of("a", "b", "c");     // ✓ Java 9+
        
        System.out.println("Best practice examples created successfully");
    }
}