package com.coherentsolutions.session1.reference;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * LINQ TO STREAMS REFERENCE: C# LINQ vs Java Streams
 * 
 * This reference class provides comprehensive side-by-side comparisons between
 * C# LINQ and Java Streams API. Perfect for .NET developers learning Java.
 * 
 * COVERAGE:
 * 1. Basic filtering and mapping operations
 * 2. Aggregation operations (sum, count, average)
 * 3. Grouping and partitioning
 * 4. Sorting and ordering
 * 5. Set operations (distinct, union, intersect)
 * 6. Advanced operations (flatMap, reduce, collect)
 * 7. Lazy evaluation and performance considerations
 * 8. Common patterns and best practices
 * 
 * FOR LECTURE USE:
 * - Show exact LINQ to Stream equivalents
 * - Demonstrate functional programming concepts
 * - Compare performance characteristics
 * - Highlight differences in syntax and behavior
 */
public class LinqToStreams {
    
    public static void main(String[] args) {
        System.out.println("=== LINQ TO STREAMS REFERENCE ===");
        
        demonstrateBasicOperations();
        demonstrateFilteringAndMapping();
        demonstrateAggregationOperations();
        demonstrateGroupingOperations();
        demonstrateSortingOperations();
        demonstrateSetOperations();
        demonstrateAdvancedOperations();
        demonstratePerformanceConsiderations();
        demonstrateBestPractices();
        
        System.out.println("\n=== LINQ TO STREAMS COMPLETE ===");
    }
    
    /**
     * BASIC OPERATIONS
     * 
     * Fundamental LINQ vs Streams operations
     */
    public static void demonstrateBasicOperations() {
        System.out.println("\n--- Basic Operations ---");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana", "Eve");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Where / Filter
        System.out.println("FILTERING:");
        System.out.println("C#:   names.Where(n => n.Length > 3)");
        System.out.println("Java: names.stream().filter(n -> n.length() > 3)");
        
        List<String> longNames = names.stream()
            .filter(n -> n.length() > 3)
            .collect(Collectors.toList());
        System.out.println("Result: " + longNames);
        
        // Select / Map
        System.out.println("\nMAPPING:");
        System.out.println("C#:   names.Select(n => n.ToUpper())");
        System.out.println("Java: names.stream().map(n -> n.toUpperCase())");
        
        List<String> upperNames = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Result: " + upperNames);
        
        // Where + Select combined
        System.out.println("\nCOMBINED OPERATIONS:");
        System.out.println("C#:   numbers.Where(n => n % 2 == 0).Select(n => n * n)");
        System.out.println("Java: numbers.stream().filter(n -> n % 2 == 0).map(n -> n * n)");
        
        List<Integer> evenSquares = numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * n)
            .collect(Collectors.toList());
        System.out.println("Result: " + evenSquares);
        
        // First / FindFirst
        System.out.println("\nFIRST ELEMENT:");
        System.out.println("C#:   names.First(n => n.StartsWith(\"C\"))");
        System.out.println("Java: names.stream().filter(n -> n.startsWith(\"C\")).findFirst()");
        
        Optional<String> firstC = names.stream()
            .filter(n -> n.startsWith("C"))
            .findFirst();
        System.out.println("Result: " + firstC.orElse("Not found"));
        
        // Any / AnyMatch
        System.out.println("\nANY MATCH:");
        System.out.println("C#:   numbers.Any(n => n > 5)");
        System.out.println("Java: numbers.stream().anyMatch(n -> n > 5)");
        
        boolean anyGreaterThan5 = numbers.stream().anyMatch(n -> n > 5);
        System.out.println("Result: " + anyGreaterThan5);
        
        // All / AllMatch
        System.out.println("\nALL MATCH:");
        System.out.println("C#:   numbers.All(n => n > 0)");
        System.out.println("Java: numbers.stream().allMatch(n -> n > 0)");
        
        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        System.out.println("Result: " + allPositive);
    }
    
    /**
     * FILTERING AND MAPPING
     * 
     * Advanced filtering and transformation operations
     */
    public static void demonstrateFilteringAndMapping() {
        System.out.println("\n--- Filtering and Mapping ---");
        
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, "Engineer", 75000),
            new Person("Bob", 30, "Manager", 85000),
            new Person("Charlie", 22, "Developer", 65000),
            new Person("Diana", 28, "Designer", 70000),
            new Person("Eve", 35, "Architect", 95000)
        );
        
        // Complex filtering
        System.out.println("COMPLEX FILTERING:");
        System.out.println("C#:   people.Where(p => p.Age >= 25 && p.Salary > 70000)");
        System.out.println("Java: people.stream().filter(p -> p.age >= 25 && p.salary > 70000)");
        
        List<Person> seniorHighPaid = people.stream()
            .filter(p -> p.age >= 25 && p.salary > 70000)
            .collect(Collectors.toList());
        
        System.out.println("Result:");
        seniorHighPaid.forEach(p -> System.out.println("  " + p));
        
        // SelectMany / FlatMap
        System.out.println("\nFLATTEN COLLECTIONS:");
        List<List<String>> departments = Arrays.asList(
            Arrays.asList("Engineering", "QA", "DevOps"),
            Arrays.asList("Marketing", "Sales"),
            Arrays.asList("HR", "Finance", "Legal")
        );
        
        System.out.println("C#:   departments.SelectMany(d => d)");
        System.out.println("Java: departments.stream().flatMap(Collection::stream)");
        
        List<String> allDepartments = departments.stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        System.out.println("Result: " + allDepartments);
        
        // Conditional mapping
        System.out.println("\nCONDITIONAL MAPPING:");
        System.out.println("C#:   people.Select(p => p.Age >= 30 ? \"Senior\" : \"Junior\")");
        System.out.println("Java: people.stream().map(p -> p.age >= 30 ? \"Senior\" : \"Junior\")");
        
        List<String> seniority = people.stream()
            .map(p -> p.age >= 30 ? "Senior" : "Junior")
            .collect(Collectors.toList());
        System.out.println("Result: " + seniority);
        
        // OfType / Filter by type
        System.out.println("\nFILTER BY TYPE:");
        List<Object> mixedObjects = Arrays.asList("String", 42, 3.14, "Another String", 100);
        
        System.out.println("C#:   objects.OfType<string>()");
        System.out.println("Java: objects.stream().filter(String.class::isInstance).map(String.class::cast)");
        
        List<String> onlyStrings = mixedObjects.stream()
            .filter(String.class::isInstance)
            .map(String.class::cast)
            .collect(Collectors.toList());
        System.out.println("Result: " + onlyStrings);
    }
    
    /**
     * AGGREGATION OPERATIONS
     * 
     * Mathematical and statistical operations
     */
    public static void demonstrateAggregationOperations() {
        System.out.println("\n--- Aggregation Operations ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, "Engineer", 75000),
            new Person("Bob", 30, "Manager", 85000),
            new Person("Charlie", 22, "Developer", 65000),
            new Person("Diana", 28, "Designer", 70000),
            new Person("Eve", 35, "Architect", 95000)
        );
        
        // Count
        System.out.println("COUNT:");
        System.out.println("C#:   numbers.Count()");
        System.out.println("Java: numbers.stream().count()");
        long count = numbers.stream().count();
        System.out.println("Result: " + count);
        
        // Count with condition
        System.out.println("\nCOUNT WITH CONDITION:");
        System.out.println("C#:   numbers.Count(n => n > 5)");
        System.out.println("Java: numbers.stream().filter(n -> n > 5).count()");
        long countGreaterThan5 = numbers.stream().filter(n -> n > 5).count();
        System.out.println("Result: " + countGreaterThan5);
        
        // Sum
        System.out.println("\nSUM:");
        System.out.println("C#:   numbers.Sum()");
        System.out.println("Java: numbers.stream().mapToInt(Integer::intValue).sum()");
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Result: " + sum);
        
        // Sum with projection
        System.out.println("\nSUM WITH PROJECTION:");
        System.out.println("C#:   people.Sum(p => p.Salary)");
        System.out.println("Java: people.stream().mapToDouble(p -> p.salary).sum()");
        double totalSalary = people.stream().mapToDouble(p -> p.salary).sum();
        System.out.println("Result: $" + totalSalary);
        
        // Average
        System.out.println("\nAVERAGE:");
        System.out.println("C#:   people.Average(p => p.Age)");
        System.out.println("Java: people.stream().mapToInt(p -> p.age).average()");
        OptionalDouble avgAge = people.stream().mapToInt(p -> p.age).average();
        System.out.println("Result: " + avgAge.orElse(0.0));
        
        // Min / Max
        System.out.println("\nMIN / MAX:");
        System.out.println("C#:   numbers.Min() / numbers.Max()");
        System.out.println("Java: numbers.stream().min(Integer::compareTo) / max(Integer::compareTo)");
        
        OptionalInt min = numbers.stream().mapToInt(Integer::intValue).min();
        OptionalInt max = numbers.stream().mapToInt(Integer::intValue).max();
        System.out.println("Min: " + min.orElse(0) + ", Max: " + max.orElse(0));
        
        // Min/Max by projection
        System.out.println("\nMIN/MAX BY PROJECTION:");
        System.out.println("C#:   people.Min(p => p.Salary) / people.Max(p => p.Salary)");
        System.out.println("Java: people.stream().min/max(Comparator.comparing(p -> p.salary))");
        
        Optional<Person> youngestPerson = people.stream()
            .min(Comparator.comparing(p -> p.age));
        Optional<Person> highestPaid = people.stream()
            .max(Comparator.comparing(p -> p.salary));
        
        System.out.println("Youngest: " + youngestPerson.map(p -> p.name).orElse("None"));
        System.out.println("Highest paid: " + highestPaid.map(p -> p.name).orElse("None"));
        
        // Aggregate / Reduce
        System.out.println("\nAGGREGATE / REDUCE:");
        System.out.println("C#:   numbers.Aggregate((a, b) => a + b)");
        System.out.println("Java: numbers.stream().reduce((a, b) -> a + b)");
        
        Optional<Integer> reduced = numbers.stream().reduce((a, b) -> a + b);
        System.out.println("Reduced sum: " + reduced.orElse(0));
        
        // Custom aggregation
        String concatenated = people.stream()
            .map(p -> p.name)
            .reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);
        System.out.println("Concatenated names: " + concatenated);
    }
    
    /**
     * GROUPING OPERATIONS
     * 
     * GroupBy and partitioning operations
     */
    public static void demonstrateGroupingOperations() {
        System.out.println("\n--- Grouping Operations ---");
        
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, "Engineer", 75000),
            new Person("Bob", 30, "Manager", 85000),
            new Person("Charlie", 22, "Engineer", 65000),
            new Person("Diana", 28, "Designer", 70000),
            new Person("Eve", 35, "Manager", 95000),
            new Person("Frank", 26, "Engineer", 68000)
        );
        
        // GroupBy
        System.out.println("GROUP BY:");
        System.out.println("C#:   people.GroupBy(p => p.Department)");
        System.out.println("Java: people.stream().collect(Collectors.groupingBy(p -> p.department))");
        
        Map<String, List<Person>> byDepartment = people.stream()
            .collect(Collectors.groupingBy(p -> p.department));
        
        System.out.println("Grouped by department:");
        byDepartment.forEach((dept, peopleInDept) -> {
            System.out.println("  " + dept + ": " + peopleInDept.size() + " people");
            peopleInDept.forEach(p -> System.out.println("    " + p.name + " (" + p.age + ")"));
        });
        
        // GroupBy with aggregation
        System.out.println("\nGROUP BY WITH AGGREGATION:");
        System.out.println("C#:   people.GroupBy(p => p.Department).Select(g => new { Dept = g.Key, Count = g.Count() })");
        System.out.println("Java: people.stream().collect(Collectors.groupingBy(p -> p.department, Collectors.counting()))");
        
        Map<String, Long> departmentCounts = people.stream()
            .collect(Collectors.groupingBy(p -> p.department, Collectors.counting()));
        
        System.out.println("Department counts: " + departmentCounts);
        
        // Average salary by department
        Map<String, Double> avgSalaryByDept = people.stream()
            .collect(Collectors.groupingBy(
                p -> p.department,
                Collectors.averagingDouble(p -> p.salary)
            ));
        
        System.out.println("Average salary by department:");
        avgSalaryByDept.forEach((dept, avg) -> 
            System.out.println("  " + dept + ": $" + String.format("%.0f", avg)));
        
        // Multiple grouping levels
        System.out.println("\nMULTI-LEVEL GROUPING:");
        Map<String, Map<String, List<Person>>> multiLevel = people.stream()
            .collect(Collectors.groupingBy(
                p -> p.department,
                Collectors.groupingBy(p -> p.age >= 30 ? "Senior" : "Junior")
            ));
        
        System.out.println("Multi-level grouping:");
        multiLevel.forEach((dept, ageGroups) -> {
            System.out.println("  " + dept + ":");
            ageGroups.forEach((ageGroup, peopleInGroup) ->
                System.out.println("    " + ageGroup + ": " + peopleInGroup.size() + " people"));
        });
        
        // Partition (binary grouping)
        System.out.println("\nPARTITIONING:");
        System.out.println("C#:   people.GroupBy(p => p.Salary > 70000).ToDictionary(g => g.Key, g => g.ToList())");
        System.out.println("Java: people.stream().collect(Collectors.partitioningBy(p -> p.salary > 70000))");
        
        Map<Boolean, List<Person>> partitioned = people.stream()
            .collect(Collectors.partitioningBy(p -> p.salary > 70000));
        
        System.out.println("High earners (>$70k): " + partitioned.get(true).size());
        System.out.println("Lower earners (≤$70k): " + partitioned.get(false).size());
    }
    
    /**
     * SORTING OPERATIONS
     * 
     * OrderBy and sorting operations
     */
    public static void demonstrateSortingOperations() {
        System.out.println("\n--- Sorting Operations ---");
        
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, "Engineer", 75000),
            new Person("Bob", 30, "Manager", 85000),
            new Person("Charlie", 22, "Engineer", 65000),
            new Person("Diana", 28, "Designer", 70000),
            new Person("Eve", 35, "Manager", 95000)
        );
        
        // OrderBy
        System.out.println("ORDER BY:");
        System.out.println("C#:   people.OrderBy(p => p.Age)");
        System.out.println("Java: people.stream().sorted(Comparator.comparing(p -> p.age))");
        
        List<Person> sortedByAge = people.stream()
            .sorted(Comparator.comparing(p -> p.age))
            .collect(Collectors.toList());
        
        System.out.println("Sorted by age:");
        sortedByAge.forEach(p -> System.out.println("  " + p.name + " (" + p.age + ")"));
        
        // OrderByDescending
        System.out.println("\nORDER BY DESCENDING:");
        System.out.println("C#:   people.OrderByDescending(p => p.Salary)");
        System.out.println("Java: people.stream().sorted(Comparator.comparing(p -> p.salary).reversed())");
        
        List<Person> sortedBySalaryDesc = people.stream()
            .sorted(Comparator.comparing((Person p) -> p.salary).reversed())
            .collect(Collectors.toList());
        
        System.out.println("Sorted by salary (descending):");
        sortedBySalaryDesc.forEach(p -> System.out.println("  " + p.name + " ($" + p.salary + ")"));
        
        // ThenBy (multiple sorting criteria)
        System.out.println("\nMULTIPLE SORTING CRITERIA:");
        System.out.println("C#:   people.OrderBy(p => p.Department).ThenBy(p => p.Age)");
        System.out.println("Java: people.stream().sorted(Comparator.comparing(p -> p.department).thenComparing(p -> p.age))");
        
        List<Person> multiSorted = people.stream()
            .sorted(Comparator.comparing((Person p) -> p.department)
                   .thenComparing(p -> p.age))
            .collect(Collectors.toList());
        
        System.out.println("Sorted by department, then age:");
        multiSorted.forEach(p -> System.out.println("  " + p.department + " - " + p.name + " (" + p.age + ")"));
        
        // Reverse
        System.out.println("\nREVERSE:");
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        System.out.println("C#:   names.Reverse()");
        System.out.println("Java: names.stream().collect(Collectors.collectingAndThen(Collectors.toList(), list -> { Collections.reverse(list); return list; }))");
        
        // Simple approach using sorted with reverseOrder
        List<String> reversed = names.stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
        System.out.println("Reversed names: " + reversed);
        
        // Custom sorting
        System.out.println("\nCUSTOM SORTING:");
        List<String> words = Arrays.asList("apple", "pie", "a", "elephant", "cat");
        
        List<String> sortedByLength = words.stream()
            .sorted(Comparator.comparing(String::length).thenComparing(String::compareTo))
            .collect(Collectors.toList());
        
        System.out.println("Sorted by length, then alphabetically: " + sortedByLength);
    }
    
    /**
     * SET OPERATIONS
     * 
     * Distinct, Union, Intersect, Except operations
     */
    public static void demonstrateSetOperations() {
        System.out.println("\n--- Set Operations ---");
        
        List<String> list1 = Arrays.asList("apple", "banana", "cherry", "apple");
        List<String> list2 = Arrays.asList("banana", "cherry", "date", "elderberry");
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 5);
        
        // Distinct
        System.out.println("DISTINCT:");
        System.out.println("C#:   list.Distinct()");
        System.out.println("Java: list.stream().distinct()");
        
        List<String> distinctFruits = list1.stream()
            .distinct()
            .collect(Collectors.toList());
        System.out.println("Original: " + list1);
        System.out.println("Distinct: " + distinctFruits);
        
        List<Integer> distinctNumbers = numbers.stream()
            .distinct()
            .collect(Collectors.toList());
        System.out.println("Numbers distinct: " + distinctNumbers);
        
        // Union
        System.out.println("\nUNION:");
        System.out.println("C#:   list1.Union(list2)");
        System.out.println("Java: Stream.concat(list1.stream(), list2.stream()).distinct()");
        
        List<String> union = Stream.concat(list1.stream(), list2.stream())
            .distinct()
            .collect(Collectors.toList());
        System.out.println("List1: " + list1);
        System.out.println("List2: " + list2);
        System.out.println("Union: " + union);
        
        // Intersect
        System.out.println("\nINTERSECT:");
        System.out.println("C#:   list1.Intersect(list2)");
        System.out.println("Java: list1.stream().filter(list2::contains).distinct()");
        
        List<String> intersect = list1.stream()
            .filter(list2::contains)
            .distinct()
            .collect(Collectors.toList());
        System.out.println("Intersect: " + intersect);
        
        // Except (Java equivalent)
        System.out.println("\nEXCEPT:");
        System.out.println("C#:   list1.Except(list2)");
        System.out.println("Java: list1.stream().filter(item -> !list2.contains(item)).distinct()");
        
        List<String> except = list1.stream()
            .filter(item -> !list2.contains(item))
            .distinct()
            .collect(Collectors.toList());
        System.out.println("Except (list1 - list2): " + except);
        
        // Symmetric difference
        System.out.println("\nSYMMETRIC DIFFERENCE:");
        List<String> list1Only = list1.stream()
            .filter(item -> !list2.contains(item))
            .distinct()
            .collect(Collectors.toList());
        
        List<String> list2Only = list2.stream()
            .filter(item -> !list1.contains(item))
            .distinct()
            .collect(Collectors.toList());
        
        List<String> symmetricDiff = Stream.concat(list1Only.stream(), list2Only.stream())
            .collect(Collectors.toList());
        
        System.out.println("Symmetric difference: " + symmetricDiff);
        
        // SequenceEqual equivalent
        System.out.println("\nSEQUENCE EQUAL:");
        List<String> sameList = Arrays.asList("apple", "banana", "cherry", "apple");
        boolean areEqual = list1.equals(sameList);
        System.out.println("Lists equal: " + areEqual);
    }
    
    /**
     * ADVANCED OPERATIONS
     * 
     * Complex stream operations and patterns
     */
    public static void demonstrateAdvancedOperations() {
        System.out.println("\n--- Advanced Operations ---");
        
        // Zip (not directly available in Java, need custom implementation)
        System.out.println("ZIP OPERATION:");
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<Integer> ages = Arrays.asList(25, 30, 35);
        
        System.out.println("C#:   names.Zip(ages, (name, age) => new { Name = name, Age = age })");
        System.out.println("Java: IntStream.range(0, Math.min(names.size(), ages.size())).mapToObj(i -> ...)");
        
        List<String> zipped = IntStream.range(0, Math.min(names.size(), ages.size()))
            .mapToObj(i -> names.get(i) + " (" + ages.get(i) + ")")
            .collect(Collectors.toList());
        System.out.println("Zipped: " + zipped);
        
        // Skip and Take
        System.out.println("\nSKIP AND TAKE:");
        List<Integer> numbers = IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList());
        
        System.out.println("C#:   numbers.Skip(5).Take(10)");
        System.out.println("Java: numbers.stream().skip(5).limit(10)");
        
        List<Integer> skippedAndTaken = numbers.stream()
            .skip(5)
            .limit(10)
            .collect(Collectors.toList());
        System.out.println("Skip 5, take 10: " + skippedAndTaken);
        
        // TakeWhile / DropWhile (Java 9+)
        System.out.println("\nTAKE WHILE / DROP WHILE:");
        System.out.println("C#:   numbers.TakeWhile(n => n < 10)");
        System.out.println("Java: numbers.stream().takeWhile(n -> n < 10) // Java 9+");
        
        List<Integer> takenWhile = numbers.stream()
            .takeWhile(n -> n < 10)
            .collect(Collectors.toList());
        System.out.println("Take while < 10: " + takenWhile);
        
        // Parallel processing
        System.out.println("\nPARALLEL PROCESSING:");
        System.out.println("C#:   numbers.AsParallel().Where(...).Select(...)");
        System.out.println("Java: numbers.parallelStream().filter(...).map(...)");
        
        List<Integer> parallelResult = numbers.parallelStream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * n)
            .collect(Collectors.toList());
        System.out.println("Parallel even squares: " + parallelResult);
        
        // Custom collector
        System.out.println("\nCUSTOM COLLECTOR:");
        String joined = names.stream()
            .collect(Collectors.joining(", ", "Names: [", "]"));
        System.out.println("Custom joined: " + joined);
        
        // Complex reduce operation
        System.out.println("\nCOMPLEX REDUCE:");
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, "Engineer", 75000),
            new Person("Bob", 30, "Manager", 85000),
            new Person("Charlie", 22, "Engineer", 65000)
        );
        
        // Find person with highest salary in each department
        Map<String, Optional<Person>> topEarnersByDept = people.stream()
            .collect(Collectors.groupingBy(
                p -> p.department,
                Collectors.maxBy(Comparator.comparing(p -> p.salary))
            ));
        
        System.out.println("Top earners by department:");
        topEarnersByDept.forEach((dept, person) ->
            System.out.println("  " + dept + ": " + person.map(p -> p.name + " ($" + p.salary + ")").orElse("None")));
    }
    
    /**
     * PERFORMANCE CONSIDERATIONS
     * 
     * Understanding lazy evaluation and performance implications
     */
    public static void demonstratePerformanceConsiderations() {
        System.out.println("\n--- Performance Considerations ---");
        
        List<Integer> largeList = IntStream.rangeClosed(1, 1000000).boxed().collect(Collectors.toList());
        
        System.out.println("LAZY EVALUATION:");
        System.out.println("Both C# LINQ and Java Streams use lazy evaluation");
        System.out.println("Operations are not executed until a terminal operation is called");
        
        // Demonstrate lazy evaluation
        Stream<Integer> lazyStream = largeList.stream()
            .filter(n -> {
                System.out.println("Filtering: " + n); // This won't execute
                return n % 2 == 0;
            })
            .map(n -> {
                System.out.println("Mapping: " + n); // This won't execute either
                return n * n;
            });
        
        System.out.println("Stream created but no output from filters/maps yet...");
        
        // Only when we call a terminal operation
        long count = lazyStream.limit(3).count(); // Only processes first few elements
        System.out.println("Count of first 3 even numbers: " + count);
        
        System.out.println("\nPERFORMANCE TIPS:");
        System.out.println("1. Use primitive streams (IntStream, LongStream, DoubleStream) for better performance");
        System.out.println("2. Consider parallel streams for CPU-intensive operations on large datasets");
        System.out.println("3. Avoid unnecessary boxing/unboxing");
        System.out.println("4. Use collect() instead of reduce() for mutable reductions");
        System.out.println("5. Short-circuit operations (findFirst, anyMatch) can improve performance");
        
        // Primitive stream example
        System.out.println("\nPRIMITIVE STREAMS:");
        double average = largeList.stream()
            .mapToInt(Integer::intValue)  // Convert to IntStream
            .filter(n -> n % 2 == 0)
            .limit(1000)
            .average()
            .orElse(0.0);
        System.out.println("Average of first 1000 even numbers: " + average);
        
        // Parallel vs Sequential comparison
        System.out.println("\nPARALLEL VS SEQUENTIAL:");
        long start = System.currentTimeMillis();
        long sequentialSum = largeList.stream()
            .mapToInt(Integer::intValue)
            .filter(n -> n % 2 == 0)
            .limit(10000)
            .sum();
        long sequentialTime = System.currentTimeMillis() - start;
        
        start = System.currentTimeMillis();
        long parallelSum = largeList.parallelStream()
            .mapToInt(Integer::intValue)
            .filter(n -> n % 2 == 0)
            .limit(10000)
            .sum();
        long parallelTime = System.currentTimeMillis() - start;
        
        System.out.println("Sequential time: " + sequentialTime + "ms, result: " + sequentialSum);
        System.out.println("Parallel time: " + parallelTime + "ms, result: " + parallelSum);
        System.out.println("Note: For small datasets, parallel streams may be slower due to overhead");
    }
    
    /**
     * BEST PRACTICES
     * 
     * Recommended patterns and common mistakes to avoid
     */
    public static void demonstrateBestPractices() {
        System.out.println("\n--- Best Practices ---");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");
        
        System.out.println("BEST PRACTICES:");
        
        System.out.println("\n1. PREFER METHOD REFERENCES WHEN POSSIBLE:");
        System.out.println("❌ .map(name -> name.toUpperCase())");
        System.out.println("✅ .map(String::toUpperCase)");
        
        List<String> upperNames = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        
        System.out.println("\n2. USE APPROPRIATE COLLECTORS:");
        System.out.println("❌ .collect(Collectors.toList()) when you need specific list type");
        System.out.println("✅ .collect(Collectors.toCollection(ArrayList::new))");
        
        System.out.println("\n3. AVOID SIDE EFFECTS IN LAMBDA EXPRESSIONS:");
        System.out.println("❌ .forEach(item -> externalList.add(item))");
        System.out.println("✅ .collect(Collectors.toList())");
        
        System.out.println("\n4. USE OPTIONAL PROPERLY:");
        System.out.println("❌ .findFirst().get()");
        System.out.println("✅ .findFirst().orElse(defaultValue)");
        
        Optional<String> firstLongName = names.stream()
            .filter(name -> name.length() > 5)
            .findFirst();
        System.out.println("First long name: " + firstLongName.orElse("No long names"));
        
        System.out.println("\n5. CHOOSE APPROPRIATE TERMINAL OPERATIONS:");
        System.out.println("✅ Use anyMatch() instead of filter().findFirst().isPresent()");
        System.out.println("✅ Use count() instead of collect(toList()).size()");
        
        boolean hasLongName = names.stream().anyMatch(name -> name.length() > 5);
        long longNameCount = names.stream().filter(name -> name.length() > 5).count();
        
        System.out.println("Has long name: " + hasLongName);
        System.out.println("Long name count: " + longNameCount);
        
        System.out.println("\n6. COMMON MISTAKES TO AVOID:");
        System.out.println("❌ Reusing streams (they can only be consumed once)");
        System.out.println("❌ Using parallel streams for small datasets");
        System.out.println("❌ Using streams for simple iterations");
        System.out.println("❌ Ignoring the fact that streams can be infinite");
        System.out.println("❌ Using peek() for non-debugging purposes");
        
        System.out.println("\nCONVERSION SUMMARY:");
        System.out.println("C# LINQ              → Java Streams");
        System.out.println("Where()              → filter()");
        System.out.println("Select()             → map()");
        System.out.println("SelectMany()         → flatMap()");
        System.out.println("OrderBy()            → sorted(Comparator.comparing())");
        System.out.println("GroupBy()            → collect(groupingBy())");
        System.out.println("First()              → findFirst().orElseThrow()");
        System.out.println("FirstOrDefault()     → findFirst().orElse(default)");
        System.out.println("Any()                → anyMatch()");
        System.out.println("All()                → allMatch()");
        System.out.println("Count()              → count()");
        System.out.println("Sum()                → mapToInt().sum()");
        System.out.println("Average()            → mapToInt().average()");
        System.out.println("ToList()             → collect(Collectors.toList())");
        System.out.println("ToArray()            → toArray()");
        System.out.println("Distinct()           → distinct()");
        System.out.println("Skip()               → skip()");
        System.out.println("Take()               → limit()");
    }
    
    // Helper class for examples
    public static class Person {
        public final String name;
        public final int age;
        public final String department;
        public final double salary;
        
        public Person(String name, int age, String department, double salary) {
            this.name = name;
            this.age = age;
            this.department = department;
            this.salary = salary;
        }
        
        @Override
        public String toString() {
            return String.format("%s (%d, %s, $%.0f)", name, age, department, salary);
        }
    }
}