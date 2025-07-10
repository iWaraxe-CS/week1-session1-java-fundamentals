package com.coherentsolutions.session1.demos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * STREAMS DEMO
 * 
 * Live coding demonstration of Java Streams API for .NET developers.
 * This demo showcases the functional programming capabilities of Java Streams
 * and their relationship to C# LINQ operations.
 * 
 * DEMO OBJECTIVES:
 * 1. Map LINQ operations to Java Streams equivalents
 * 2. Demonstrate functional programming concepts in Java
 * 3. Show stream processing pipelines and lazy evaluation
 * 4. Practice common data transformation patterns
 * 5. Highlight performance considerations and best practices
 * 
 * LECTURE FLOW:
 * 1. Start with simple filtering and mapping
 * 2. Progress through aggregation operations
 * 3. Show grouping and partitioning
 * 4. Demonstrate advanced stream operations
 * 5. End with practical business scenarios
 * 
 * INTERACTIVE ELEMENTS:
 * - LINQ vs Streams comparisons
 * - Performance predictions
 * - Live coding challenges
 * - Error scenario discussions
 */
@Slf4j
public class StreamsDemo {
    
    public static void main(String[] args) {
        System.out.println("=== STREAMS DEMO ===");
        System.out.println("Java Streams API for .NET developers");
        
        // Demo progression
        demonstrateBasicOperations();
        demonstrateFiltering();
        demonstrateMapping();
        demonstrateAggregation();
        demonstrateGrouping();
        demonstrateAdvancedOperations();
        demonstrateBusinessScenarios();
        demonstratePerformanceConsiderations();
        
        System.out.println("\n=== DEMO COMPLETE ===");
    }
    
    /**
     * DEMO 1: Basic Stream Operations
     * 
     * Show fundamental stream concepts and creation
     */
    public static void demonstrateBasicOperations() {
        System.out.println("\n--- Demo 1: Basic Stream Operations ---");
        
        // Stream creation
        System.out.println("STREAM CREATION:");
        System.out.println("C#:   var numbers = Enumerable.Range(1, 10);");
        System.out.println("Java: Stream<Integer> numbers = IntStream.rangeClosed(1, 10).boxed();");
        System.out.println("Java: Stream<String> names = Arrays.stream(array);");
        System.out.println("Java: Stream<String> names = list.stream();");
        
        // From collection
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");
        Stream<String> nameStream = names.stream();
        
        // From array
        String[] nameArray = {"Eve", "Frank", "Grace"};
        Stream<String> arrayStream = Arrays.stream(nameArray);
        
        // From range
        IntStream numberStream = IntStream.rangeClosed(1, 10);
        System.out.println("Numbers 1-10: " + numberStream.boxed().collect(Collectors.toList()));
        
        // Stream.of()
        Stream<String> literalStream = Stream.of("One", "Two", "Three");
        System.out.println("Literal stream: " + literalStream.collect(Collectors.toList()));
        
        // Infinite streams
        System.out.println("\nINFINITE STREAMS:");
        System.out.println("C#:   var infinite = Enumerable.Repeat(\"Hello\", int.MaxValue);");
        System.out.println("Java: Stream<String> infinite = Stream.generate(() -> \"Hello\");");
        
        Stream<Integer> fibonacci = Stream.iterate(new int[]{0, 1}, arr -> new int[]{arr[1], arr[0] + arr[1]})
            .mapToInt(arr -> arr[0])
            .boxed();
        
        List<Integer> first10Fibonacci = fibonacci.limit(10).collect(Collectors.toList());
        System.out.println("First 10 Fibonacci: " + first10Fibonacci);
        
        // Stream characteristics
        System.out.println("\nSTREAM CHARACTERISTICS:");
        System.out.println("1. Lazy evaluation - operations not executed until terminal operation");
        System.out.println("2. Single-use - streams can only be consumed once");
        System.out.println("3. Functional - no side effects in intermediate operations");
        System.out.println("4. Parallel processing - can run operations in parallel");
        
        // Demonstrate lazy evaluation
        System.out.println("\nLAZY EVALUATION DEMO:");
        Stream<String> lazyStream = names.stream()
            .peek(name -> System.out.println("Processing: " + name))
            .filter(name -> name.length() > 3)
            .peek(name -> System.out.println("After filter: " + name));
        
        System.out.println("Stream created but not executed yet...");
        List<String> result = lazyStream.collect(Collectors.toList());
        System.out.println("Final result: " + result);
    }
    
    /**
     * DEMO 2: Filtering Operations
     * 
     * Show filtering with predicates
     */
    public static void demonstrateFiltering() {
        System.out.println("\n--- Demo 2: Filtering Operations ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry", "fig");
        
        // Basic filtering
        System.out.println("BASIC FILTERING:");
        System.out.println("C#:   var evens = numbers.Where(n => n % 2 == 0);");
        System.out.println("Java: List<Integer> evens = numbers.stream().filter(n -> n % 2 == 0).collect(toList());");
        
        List<Integer> evens = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("Even numbers: " + evens);
        
        // String filtering
        System.out.println("\nSTRING FILTERING:");
        System.out.println("C#:   var longWords = words.Where(w => w.Length > 5);");
        System.out.println("Java: List<String> longWords = words.stream().filter(w -> w.length() > 5).collect(toList());");
        
        List<String> longWords = words.stream()
            .filter(word -> word.length() > 5)
            .collect(Collectors.toList());
        System.out.println("Long words: " + longWords);
        
        // Multiple conditions
        System.out.println("\nMULTIPLE CONDITIONS:");
        System.out.println("C#:   var result = numbers.Where(n => n > 3 && n < 8);");
        System.out.println("Java: List<Integer> result = numbers.stream().filter(n -> n > 3 && n < 8).collect(toList());");
        
        List<Integer> filtered = numbers.stream()
            .filter(n -> n > 3)
            .filter(n -> n < 8)  // Can chain filters
            .collect(Collectors.toList());
        System.out.println("Numbers between 3 and 8: " + filtered);
        
        // Complex object filtering
        System.out.println("\nCOMPLEX OBJECT FILTERING:");
        List<Employee> employees = createEmployeeData();
        
        System.out.println("All employees: " + employees.size());
        
        List<Employee> seniorDevs = employees.stream()
            .filter(emp -> emp.getDepartment().equals("Engineering"))
            .filter(emp -> emp.getSalary().compareTo(new BigDecimal("80000")) > 0)
            .filter(emp -> emp.getYearsExperience() >= 5)
            .collect(Collectors.toList());
        
        System.out.println("Senior developers: " + seniorDevs);
        
        // Distinct filtering
        System.out.println("\nDISTINCT FILTERING:");
        List<String> duplicates = Arrays.asList("apple", "banana", "apple", "cherry", "banana", "date");
        
        System.out.println("C#:   var unique = duplicates.Distinct();");
        System.out.println("Java: List<String> unique = duplicates.stream().distinct().collect(toList());");
        
        List<String> unique = duplicates.stream()
            .distinct()
            .collect(Collectors.toList());
        System.out.println("Original: " + duplicates);
        System.out.println("Unique: " + unique);
        
        // Limit and skip
        System.out.println("\nLIMIT AND SKIP:");
        System.out.println("C#:   var page = items.Skip(5).Take(3);");
        System.out.println("Java: List<String> page = items.stream().skip(5).limit(3).collect(toList());");
        
        List<String> page = words.stream()
            .skip(2)
            .limit(3)
            .collect(Collectors.toList());
        System.out.println("Page (skip 2, take 3): " + page);
    }
    
    /**
     * DEMO 3: Mapping Operations
     * 
     * Show data transformation with mapping
     */
    public static void demonstrateMapping() {
        System.out.println("\n--- Demo 3: Mapping Operations ---");
        
        List<String> words = Arrays.asList("hello", "world", "java", "streams");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Basic mapping
        System.out.println("BASIC MAPPING:");
        System.out.println("C#:   var upperWords = words.Select(w => w.ToUpper());");
        System.out.println("Java: List<String> upperWords = words.stream().map(String::toUpperCase).collect(toList());");
        
        List<String> upperWords = words.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Upper case: " + upperWords);
        
        // Transformation mapping
        System.out.println("\nTRANSFORMATION MAPPING:");
        System.out.println("C#:   var lengths = words.Select(w => w.Length);");
        System.out.println("Java: List<Integer> lengths = words.stream().map(String::length).collect(toList());");
        
        List<Integer> lengths = words.stream()
            .map(String::length)
            .collect(Collectors.toList());
        System.out.println("Word lengths: " + lengths);
        
        // Mathematical transformations
        System.out.println("\nMATHEMATICAL TRANSFORMATIONS:");
        System.out.println("C#:   var squares = numbers.Select(n => n * n);");
        System.out.println("Java: List<Integer> squares = numbers.stream().map(n -> n * n).collect(toList());");
        
        List<Integer> squares = numbers.stream()
            .map(n -> n * n)
            .collect(Collectors.toList());
        System.out.println("Squares: " + squares);
        
        // Object mapping
        System.out.println("\nOBJECT MAPPING:");
        List<Employee> employees = createEmployeeData();
        
        System.out.println("C#:   var names = employees.Select(e => e.Name);");
        System.out.println("Java: List<String> names = employees.stream().map(Employee::getName).collect(toList());");
        
        List<String> employeeNames = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.toList());
        System.out.println("Employee names: " + employeeNames);
        
        // Complex object transformation
        System.out.println("\nCOMPLEX TRANSFORMATION:");
        List<EmployeeSummary> summaries = employees.stream()
            .map(emp -> new EmployeeSummary(
                emp.getName(),
                emp.getDepartment(),
                emp.getSalary().divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP)
            ))
            .collect(Collectors.toList());
        
        System.out.println("Employee summaries:");
        summaries.forEach(summary -> System.out.println("  " + summary));
        
        // FlatMap for nested collections
        System.out.println("\nFLATMAP:");
        System.out.println("C#:   var allChars = words.SelectMany(w => w.ToCharArray());");
        System.out.println("Java: List<Character> allChars = words.stream().flatMap(w -> w.chars().mapToObj(c -> (char)c)).collect(toList());");
        
        List<Character> allChars = words.stream()
            .flatMap(word -> word.chars().mapToObj(c -> (char) c))
            .collect(Collectors.toList());
        System.out.println("All characters: " + allChars);
        
        // FlatMap with object lists
        System.out.println("\nFLATMAP WITH OBJECTS:");
        List<Department> departments = createDepartmentData();
        
        List<Employee> allEmployees = departments.stream()
            .flatMap(dept -> dept.getEmployees().stream())
            .collect(Collectors.toList());
        
        System.out.println("All employees from all departments: " + allEmployees.size());
        
        // Chaining map operations
        System.out.println("\nCHAINING OPERATIONS:");
        List<String> processedWords = words.stream()
            .map(String::toUpperCase)
            .map(word -> "*** " + word + " ***")
            .map(word -> word.replace("A", "@"))
            .collect(Collectors.toList());
        
        System.out.println("Processed words: " + processedWords);
    }
    
    /**
     * DEMO 4: Aggregation Operations
     * 
     * Show reduction and aggregation operations
     */
    public static void demonstrateAggregation() {
        System.out.println("\n--- Demo 4: Aggregation Operations ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Employee> employees = createEmployeeData();
        
        // Count
        System.out.println("COUNT:");
        System.out.println("C#:   var count = numbers.Count();");
        System.out.println("Java: long count = numbers.stream().count();");
        
        long count = numbers.stream().count();
        System.out.println("Count: " + count);
        
        // Sum
        System.out.println("\nSUM:");
        System.out.println("C#:   var sum = numbers.Sum();");
        System.out.println("Java: int sum = numbers.stream().mapToInt(Integer::intValue).sum();");
        
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum: " + sum);
        
        // Average
        System.out.println("\nAVERAGE:");
        System.out.println("C#:   var avg = numbers.Average();");
        System.out.println("Java: OptionalDouble avg = numbers.stream().mapToInt(Integer::intValue).average();");
        
        OptionalDouble average = numbers.stream().mapToInt(Integer::intValue).average();
        System.out.println("Average: " + average.orElse(0.0));
        
        // Min/Max
        System.out.println("\nMIN/MAX:");
        System.out.println("C#:   var min = numbers.Min(); var max = numbers.Max();");
        System.out.println("Java: Optional<Integer> min = numbers.stream().min(Integer::compareTo);");
        
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        
        System.out.println("Min: " + min.orElse(0));
        System.out.println("Max: " + max.orElse(0));
        
        // Complex aggregations
        System.out.println("\nCOMPLEX AGGREGATIONS:");
        
        // Total salary
        BigDecimal totalSalary = employees.stream()
            .map(Employee::getSalary)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total salary: $" + totalSalary);
        
        // Average salary
        OptionalDouble avgSalary = employees.stream()
            .mapToDouble(emp -> emp.getSalary().doubleValue())
            .average();
        System.out.println("Average salary: $" + String.format("%.2f", avgSalary.orElse(0.0)));
        
        // Highest paid employee
        Optional<Employee> highestPaid = employees.stream()
            .max(Comparator.comparing(Employee::getSalary));
        
        if (highestPaid.isPresent()) {
            System.out.println("Highest paid: " + highestPaid.get().getName() + " ($" + highestPaid.get().getSalary() + ")");
        }
        
        // Custom reduce
        System.out.println("\nCUSTOM REDUCE:");
        System.out.println("C#:   var result = numbers.Aggregate((a, b) => a * b);");
        System.out.println("Java: Optional<Integer> result = numbers.stream().reduce((a, b) -> a * b);");
        
        Optional<Integer> product = numbers.stream().reduce((a, b) -> a * b);
        System.out.println("Product of all numbers: " + product.orElse(0));
        
        // String concatenation
        String concatenated = Arrays.asList("Hello", "World", "From", "Java").stream()
            .reduce("", (a, b) -> a + " " + b)
            .trim();
        System.out.println("Concatenated: " + concatenated);
        
        // Statistics
        System.out.println("\nSTATISTICS:");
        IntSummaryStatistics stats = numbers.stream()
            .mapToInt(Integer::intValue)
            .summaryStatistics();
        
        System.out.println("Statistics: " + stats);
        System.out.println("Count: " + stats.getCount());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Average: " + stats.getAverage());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Max: " + stats.getMax());
    }
    
    /**
     * DEMO 5: Grouping Operations
     * 
     * Show grouping and partitioning
     */
    public static void demonstrateGrouping() {
        System.out.println("\n--- Demo 5: Grouping Operations ---");
        
        List<Employee> employees = createEmployeeData();
        List<String> words = Arrays.asList("apple", "banana", "cherry", "apricot", "blueberry", "avocado");
        
        // Basic grouping
        System.out.println("BASIC GROUPING:");
        System.out.println("C#:   var byDept = employees.GroupBy(e => e.Department);");
        System.out.println("Java: Map<String, List<Employee>> byDept = employees.stream().collect(groupingBy(Employee::getDepartment));");
        
        Map<String, List<Employee>> byDepartment = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment));
        
        System.out.println("Employees by department:");
        byDepartment.forEach((dept, empList) -> {
            System.out.println("  " + dept + ": " + empList.size() + " employees");
            empList.forEach(emp -> System.out.println("    - " + emp.getName()));
        });
        
        // Counting in groups
        System.out.println("\nCOUNTING IN GROUPS:");
        Map<String, Long> countByDepartment = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        
        System.out.println("Employee count by department: " + countByDepartment);
        
        // Grouping by computed property
        System.out.println("\nGROUPING BY COMPUTED PROPERTY:");
        Map<String, List<Employee>> bySalaryRange = employees.stream()
            .collect(Collectors.groupingBy(emp -> {
                BigDecimal salary = emp.getSalary();
                if (salary.compareTo(new BigDecimal("60000")) < 0) return "Low";
                if (salary.compareTo(new BigDecimal("80000")) < 0) return "Medium";
                return "High";
            }));
        
        System.out.println("Employees by salary range:");
        bySalaryRange.forEach((range, empList) -> 
            System.out.println("  " + range + ": " + empList.size() + " employees"));
        
        // Multiple grouping levels
        System.out.println("\nMULTIPLE GROUPING LEVELS:");
        Map<String, Map<String, List<Employee>>> byDeptAndRange = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.groupingBy(emp -> {
                    BigDecimal salary = emp.getSalary();
                    if (salary.compareTo(new BigDecimal("80000")) < 0) return "Junior";
                    return "Senior";
                })
            ));
        
        System.out.println("Employees by department and level:");
        byDeptAndRange.forEach((dept, levelMap) -> {
            System.out.println("  " + dept + ":");
            levelMap.forEach((level, empList) -> 
                System.out.println("    " + level + ": " + empList.size() + " employees"));
        });
        
        // Partitioning
        System.out.println("\nPARTITIONING:");
        System.out.println("C#:   var partitioned = employees.GroupBy(e => e.Salary > 75000);");
        System.out.println("Java: Map<Boolean, List<Employee>> partitioned = employees.stream().collect(partitioningBy(e -> e.getSalary().compareTo(new BigDecimal(\"75000\")) > 0));");
        
        Map<Boolean, List<Employee>> partitioned = employees.stream()
            .collect(Collectors.partitioningBy(emp -> 
                emp.getSalary().compareTo(new BigDecimal("75000")) > 0));
        
        System.out.println("High earners (>$75k): " + partitioned.get(true).size());
        System.out.println("Regular earners (≤$75k): " + partitioned.get(false).size());
        
        // Advanced collectors
        System.out.println("\nADVANCED COLLECTORS:");
        
        // Average salary by department
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(emp -> emp.getSalary().doubleValue())
            ));
        
        System.out.println("Average salary by department:");
        avgSalaryByDept.forEach((dept, avg) -> 
            System.out.println("  " + dept + ": $" + String.format("%.2f", avg)));
        
        // Joining names
        String allNames = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.joining(", "));
        System.out.println("All employee names: " + allNames);
        
        // Custom collector
        String departmentSummary = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment))
            .entrySet().stream()
            .map(entry -> entry.getKey() + "(" + entry.getValue().size() + ")")
            .collect(Collectors.joining(", "));
        
        System.out.println("Department summary: " + departmentSummary);
    }
    
    /**
     * DEMO 6: Advanced Stream Operations
     * 
     * Show advanced patterns and techniques
     */
    public static void demonstrateAdvancedOperations() {
        System.out.println("\n--- Demo 6: Advanced Operations ---");
        
        List<Employee> employees = createEmployeeData();
        
        // Parallel streams
        System.out.println("PARALLEL STREAMS:");
        System.out.println("C#:   var result = largeList.AsParallel().Where(...).Select(...);");
        System.out.println("Java: List<Result> result = largeList.parallelStream().filter(...).map(...).collect(toList());");
        
        long startTime = System.currentTimeMillis();
        List<String> serialResult = employees.stream()
            .filter(emp -> emp.getSalary().compareTo(new BigDecimal("50000")) > 0)
            .map(Employee::getName)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        long serialTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        List<String> parallelResult = employees.parallelStream()
            .filter(emp -> emp.getSalary().compareTo(new BigDecimal("50000")) > 0)
            .map(Employee::getName)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        long parallelTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Serial time: " + serialTime + "ms");
        System.out.println("Parallel time: " + parallelTime + "ms");
        System.out.println("Results equal: " + serialResult.size() + " == " + parallelResult.size());
        
        // Optional handling
        System.out.println("\nOPTIONAL HANDLING:");
        Optional<Employee> topEarner = employees.stream()
            .max(Comparator.comparing(Employee::getSalary));
        
        topEarner.ifPresent(emp -> 
            System.out.println("Top earner: " + emp.getName() + " ($" + emp.getSalary() + ")"));
        
        String topEarnerName = topEarner
            .map(Employee::getName)
            .orElse("No employees found");
        System.out.println("Top earner name: " + topEarnerName);
        
        // FindFirst vs FindAny
        System.out.println("\nFINDFIRST vs FINDANY:");
        Optional<Employee> firstEngineer = employees.stream()
            .filter(emp -> emp.getDepartment().equals("Engineering"))
            .findFirst();
        
        Optional<Employee> anyEngineer = employees.parallelStream()
            .filter(emp -> emp.getDepartment().equals("Engineering"))
            .findAny();
        
        System.out.println("First engineer: " + firstEngineer.map(Employee::getName).orElse("None"));
        System.out.println("Any engineer: " + anyEngineer.map(Employee::getName).orElse("None"));
        
        // AllMatch, AnyMatch, NoneMatch
        System.out.println("\nMATCHING OPERATIONS:");
        boolean allHighEarners = employees.stream()
            .allMatch(emp -> emp.getSalary().compareTo(new BigDecimal("40000")) > 0);
        
        boolean anyVeryHighEarner = employees.stream()
            .anyMatch(emp -> emp.getSalary().compareTo(new BigDecimal("100000")) > 0);
        
        boolean noneUnpaid = employees.stream()
            .noneMatch(emp -> emp.getSalary().equals(BigDecimal.ZERO));
        
        System.out.println("All earn >$40k: " + allHighEarners);
        System.out.println("Any earn >$100k: " + anyVeryHighEarner);
        System.out.println("None unpaid: " + noneUnpaid);
        
        // Sorted operations
        System.out.println("\nSORTED OPERATIONS:");
        List<Employee> sortedByNameThenSalary = employees.stream()
            .sorted(Comparator.comparing(Employee::getName)
                .thenComparing(Employee::getSalary))
            .collect(Collectors.toList());
        
        System.out.println("Sorted by name then salary:");
        sortedByNameThenSalary.forEach(emp -> 
            System.out.println("  " + emp.getName() + " - $" + emp.getSalary()));
        
        // Top N
        System.out.println("\nTOP N OPERATIONS:");
        List<Employee> top3Earners = employees.stream()
            .sorted(Comparator.comparing(Employee::getSalary).reversed())
            .limit(3)
            .collect(Collectors.toList());
        
        System.out.println("Top 3 earners:");
        top3Earners.forEach(emp -> 
            System.out.println("  " + emp.getName() + " - $" + emp.getSalary()));
    }
    
    /**
     * DEMO 7: Business Scenarios
     * 
     * Show practical business use cases
     */
    public static void demonstrateBusinessScenarios() {
        System.out.println("\n--- Demo 7: Business Scenarios ---");
        
        List<Employee> employees = createEmployeeData();
        List<Order> orders = createOrderData();
        
        // Scenario 1: Payroll processing
        System.out.println("SCENARIO 1: Payroll Processing");
        
        Map<String, BigDecimal> payrollByDepartment = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.reducing(BigDecimal.ZERO, Employee::getSalary, BigDecimal::add)
            ));
        
        System.out.println("Total payroll by department:");
        payrollByDepartment.forEach((dept, total) -> 
            System.out.println("  " + dept + ": $" + total));
        
        // Scenario 2: Sales analysis
        System.out.println("\nSCENARIO 2: Sales Analysis");
        
        BigDecimal totalRevenue = orders.stream()
            .map(Order::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        System.out.println("Total revenue: $" + totalRevenue);
        
        Map<String, Long> ordersByCustomer = orders.stream()
            .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()));
        
        System.out.println("Orders by customer:");
        ordersByCustomer.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .forEach(entry -> 
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " orders"));
        
        // Scenario 3: Performance evaluation
        System.out.println("\nSCENARIO 3: Performance Evaluation");
        
        List<Employee> promotionCandidates = employees.stream()
            .filter(emp -> emp.getYearsExperience() >= 3)
            .filter(emp -> emp.getSalary().compareTo(new BigDecimal("70000")) < 0)
            .sorted(Comparator.comparing(Employee::getYearsExperience).reversed())
            .collect(Collectors.toList());
        
        System.out.println("Promotion candidates (3+ years, <$70k):");
        promotionCandidates.forEach(emp -> 
            System.out.println("  " + emp.getName() + " - " + emp.getYearsExperience() + " years, $" + emp.getSalary()));
        
        // Scenario 4: Data transformation for reporting
        System.out.println("\nSCENARIO 4: Reporting Data");
        
        List<DepartmentReport> departmentReports = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment))
            .entrySet().stream()
            .map(entry -> {
                String dept = entry.getKey();
                List<Employee> deptEmployees = entry.getValue();
                
                int count = deptEmployees.size();
                BigDecimal avgSalary = deptEmployees.stream()
                    .map(Employee::getSalary)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP);
                
                double avgExperience = deptEmployees.stream()
                    .mapToInt(Employee::getYearsExperience)
                    .average()
                    .orElse(0.0);
                
                return new DepartmentReport(dept, count, avgSalary, avgExperience);
            })
            .sorted(Comparator.comparing(DepartmentReport::getEmployeeCount).reversed())
            .collect(Collectors.toList());
        
        System.out.println("Department reports:");
        departmentReports.forEach(report -> 
            System.out.println("  " + report));
    }
    
    /**
     * DEMO 8: Performance Considerations
     * 
     * Show performance tips and best practices
     */
    public static void demonstratePerformanceConsiderations() {
        System.out.println("\n--- Demo 8: Performance Considerations ---");
        
        // Large dataset for performance testing
        List<Integer> largeList = IntStream.rangeClosed(1, 1000000)
            .boxed()
            .collect(Collectors.toList());
        
        System.out.println("PERFORMANCE TIPS:");
        System.out.println("1. Use primitive streams when possible (IntStream, LongStream, DoubleStream)");
        System.out.println("2. Avoid boxing/unboxing overhead");
        System.out.println("3. Use parallel streams for CPU-intensive operations on large datasets");
        System.out.println("4. Filter early to reduce dataset size");
        System.out.println("5. Avoid creating unnecessary intermediate objects");
        
        // Primitive streams vs boxed streams
        System.out.println("\nPRIMITIVE STREAMS:");
        
        long startTime = System.currentTimeMillis();
        long sumBoxed = largeList.stream()
            .mapToInt(Integer::intValue)
            .sum();
        long boxedTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        long sumPrimitive = IntStream.rangeClosed(1, 1000000)
            .sum();
        long primitiveTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Boxed stream time: " + boxedTime + "ms");
        System.out.println("Primitive stream time: " + primitiveTime + "ms");
        System.out.println("Speedup: " + (boxedTime / (double) primitiveTime) + "x");
        
        // Filter early vs late
        System.out.println("\nFILTER EARLY:");
        
        startTime = System.currentTimeMillis();
        List<String> result1 = IntStream.rangeClosed(1, 100000)
            .boxed()
            .map(n -> "Number: " + n)
            .filter(s -> s.contains("5"))
            .collect(Collectors.toList());
        long filterLateTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        List<String> result2 = IntStream.rangeClosed(1, 100000)
            .filter(n -> String.valueOf(n).contains("5"))
            .mapToObj(n -> "Number: " + n)
            .collect(Collectors.toList());
        long filterEarlyTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Filter late time: " + filterLateTime + "ms");
        System.out.println("Filter early time: " + filterEarlyTime + "ms");
        System.out.println("Results equal: " + (result1.size() == result2.size()));
        
        // Parallel processing guidelines
        System.out.println("\nPARALLEL PROCESSING GUIDELINES:");
        System.out.println("✅ Use for CPU-intensive operations");
        System.out.println("✅ Large datasets (>10,000 elements)");
        System.out.println("✅ Independent operations (no shared state)");
        System.out.println("❌ I/O operations (use async instead)");
        System.out.println("❌ Small datasets (overhead > benefit)");
        System.out.println("❌ Operations with side effects");
        
        System.out.println("\nCOMMON ANTI-PATTERNS:");
        System.out.println("❌ Collecting to List just to get size: .collect(toList()).size()");
        System.out.println("✅ Use count(): .count()");
        System.out.println("❌ Multiple terminal operations on same stream");
        System.out.println("✅ Store intermediate results or recreate stream");
        System.out.println("❌ Using streams for simple iterations");
        System.out.println("✅ Use enhanced for loop for simple cases");
    }
    
    // Helper methods for creating test data
    private static List<Employee> createEmployeeData() {
        return Arrays.asList(
            new Employee("Alice Johnson", "Engineering", new BigDecimal("85000"), 5),
            new Employee("Bob Smith", "Engineering", new BigDecimal("75000"), 3),
            new Employee("Charlie Brown", "Sales", new BigDecimal("55000"), 2),
            new Employee("Diana Prince", "Marketing", new BigDecimal("65000"), 4),
            new Employee("Eve Wilson", "Engineering", new BigDecimal("95000"), 7),
            new Employee("Frank Miller", "Sales", new BigDecimal("48000"), 1),
            new Employee("Grace Lee", "HR", new BigDecimal("58000"), 3),
            new Employee("Henry Davis", "Marketing", new BigDecimal("62000"), 2),
            new Employee("Ivy Chen", "Engineering", new BigDecimal("78000"), 4),
            new Employee("Jack Thompson", "Sales", new BigDecimal("52000"), 2)
        );
    }
    
    private static List<Department> createDepartmentData() {
        return Arrays.asList(
            new Department("Engineering", createEmployeeData().subList(0, 4)),
            new Department("Sales", createEmployeeData().subList(4, 7)),
            new Department("Marketing", createEmployeeData().subList(7, 10))
        );
    }
    
    private static List<Order> createOrderData() {
        return Arrays.asList(
            new Order("O001", "Alice Johnson", new BigDecimal("150.00"), LocalDate.of(2023, Month.JANUARY, 15)),
            new Order("O002", "Bob Smith", new BigDecimal("75.50"), LocalDate.of(2023, Month.JANUARY, 16)),
            new Order("O003", "Alice Johnson", new BigDecimal("200.00"), LocalDate.of(2023, Month.FEBRUARY, 1)),
            new Order("O004", "Charlie Brown", new BigDecimal("125.75"), LocalDate.of(2023, Month.FEBRUARY, 5)),
            new Order("O005", "Bob Smith", new BigDecimal("89.99"), LocalDate.of(2023, Month.FEBRUARY, 10)),
            new Order("O006", "Diana Prince", new BigDecimal("300.00"), LocalDate.of(2023, Month.MARCH, 1)),
            new Order("O007", "Alice Johnson", new BigDecimal("45.00"), LocalDate.of(2023, Month.MARCH, 15))
        );
    }
    
    // Data classes
    @Data
    @AllArgsConstructor
    public static class Employee {
        private String name;
        private String department;
        private BigDecimal salary;
        private int yearsExperience;
    }
    
    @Data
    @AllArgsConstructor
    public static class EmployeeSummary {
        private String name;
        private String department;
        private BigDecimal monthlySalary;
    }
    
    @Data
    @AllArgsConstructor
    public static class Department {
        private String name;
        private List<Employee> employees;
    }
    
    @Data
    @AllArgsConstructor
    public static class Order {
        private String orderId;
        private String customer;
        private BigDecimal total;
        private LocalDate orderDate;
    }
    
    @Data
    @AllArgsConstructor
    public static class DepartmentReport {
        private String departmentName;
        private int employeeCount;
        private BigDecimal averageSalary;
        private double averageExperience;
        
        @Override
        public String toString() {
            return String.format("%s: %d employees, avg salary $%.2f, avg experience %.1f years",
                departmentName, employeeCount, averageSalary, averageExperience);
        }
    }
}

/*
LIVE CODING TIPS:

1. START WITH LINQ COMPARISONS:
   - Always show C# LINQ equivalent first
   - Explain conceptual similarities
   - Highlight syntax differences

2. BUILD COMPLEXITY GRADUALLY:
   - Start with simple filter/map
   - Add multiple operations
   - Show business scenarios
   - End with performance considerations

3. INTERACTIVE ELEMENTS:
   - "What will this stream produce?"
   - "How would you write this in LINQ?"
   - "Which approach is more efficient?"

4. COMMON GOTCHAS:
   - Single-use nature of streams
   - Lazy evaluation behavior
   - Optional handling
   - Parallel processing considerations

5. PRACTICAL EXERCISES:
   - Employee data analysis
   - Order processing
   - Report generation
   - Performance comparisons

6. PERFORMANCE DISCUSSIONS:
   - When to use parallel streams
   - Primitive vs boxed streams
   - Early filtering benefits
   - Memory considerations

EXPECTED QUESTIONS:
- "Why are streams single-use?"
- "When should I use parallel streams?"
- "What's the difference between map and flatMap?"
- "How do I handle null values in streams?"
- "Is this more efficient than a for loop?"
- "How do I debug stream operations?"

DEMO VARIATIONS:
- Can focus more on LINQ comparisons
- Can include error handling patterns
- Can show debugging techniques
- Can demonstrate custom collectors
- Can include reactive streams introduction
*/