package com.coherentsolutions.session1.demos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * BASIC JAVA DEMO
 * 
 * Live coding demonstration for Java fundamentals targeting .NET developers.
 * This demo showcases the essential differences between C# and Java syntax,
 * focusing on practical examples that can be coded live during lectures.
 * 
 * DEMO OBJECTIVES:
 * 1. Show Java syntax vs C# syntax side-by-side
 * 2. Demonstrate package structure and imports
 * 3. Explain Java's approach to properties and methods
 * 4. Show basic OOP concepts in Java
 * 5. Highlight common gotchas for .NET developers
 * 
 * LECTURE FLOW:
 * 1. Start with simple "Hello World" comparison
 * 2. Progress through variable declarations
 * 3. Show object creation and method calls
 * 4. Demonstrate collections usage
 * 5. End with practical business logic example
 * 
 * INTERACTIVE ELEMENTS:
 * - Live coding exercises
 * - Student predictions of output
 * - Common mistake corrections
 * - Performance discussions
 */
@Slf4j
public class BasicJavaDemo {
    
    public static void main(String[] args) {
        System.out.println("=== BASIC JAVA DEMO ===");
        System.out.println("Java fundamentals for .NET developers");
        
        // Demo progression
        demonstrateBasicSyntax();
        demonstrateVariables();
        demonstrateObjectCreation();
        demonstrateCollections();
        demonstrateBusinessLogic();
        
        System.out.println("\n=== DEMO COMPLETE ===");
    }
    
    /**
     * DEMO 1: Basic Syntax Comparison
     * 
     * Show fundamental differences between C# and Java syntax
     */
    public static void demonstrateBasicSyntax() {
        System.out.println("\n--- Demo 1: Basic Syntax ---");
        
        // Console output comparison
        System.out.println("C# CONSOLE OUTPUT:");
        System.out.println("Console.WriteLine(\"Hello World!\");");
        System.out.println("Console.Write(\"No newline\");");
        System.out.println();
        
        System.out.println("JAVA CONSOLE OUTPUT:");
        System.out.println("System.out.println(\"Hello World!\");");
        System.out.println("System.out.print(\"No newline\");");
        System.out.println();
        
        // Actual output
        System.out.println("Java output:");
        System.out.println("Hello World!");
        System.out.print("No newline");
        System.out.println(); // Add newline for demo clarity
        
        // String formatting
        String name = "Java";
        int version = 21;
        
        System.out.println("\nSTRING FORMATTING:");
        System.out.println("C#:   $\"Welcome to {name} {version}\"");
        System.out.println("Java: String.format(\"Welcome to %s %d\", name, version)");
        System.out.println("Java: \"Welcome to \" + name + \" \" + version");
        
        // Actual formatted output
        System.out.println("Result: " + String.format("Welcome to %s %d", name, version));
        
        // Method declaration
        System.out.println("\nMETHOD DECLARATIONS:");
        System.out.println("C#:   public string GetGreeting(string name)");
        System.out.println("Java: public String getGreeting(String name)");
        
        // Call example method
        String greeting = getGreeting("Student");
        System.out.println("Method result: " + greeting);
    }
    
    /**
     * DEMO 2: Variables and Types
     * 
     * Show variable declaration and type differences
     */
    public static void demonstrateVariables() {
        System.out.println("\n--- Demo 2: Variables and Types ---");
        
        // Primitive types
        System.out.println("PRIMITIVE TYPES:");
        System.out.println("C#:   int age = 25;");
        System.out.println("Java: int age = 25;");
        
        int age = 25;
        System.out.println("age = " + age);
        
        // String vs string
        System.out.println("\nSTRING TYPES:");
        System.out.println("C#:   string name = \"John\";");
        System.out.println("Java: String name = \"John\";");
        
        String name = "John";
        System.out.println("name = " + name);
        
        // Nullable types
        System.out.println("\nNULLABLE TYPES:");
        System.out.println("C#:   int? nullableAge = null;");
        System.out.println("Java: Integer nullableAge = null;");
        
        Integer nullableAge = null;
        System.out.println("nullableAge = " + nullableAge);
        
        // Type inference
        System.out.println("\nTYPE INFERENCE:");
        System.out.println("C#:   var message = \"Hello\";");
        System.out.println("Java: var message = \"Hello\"; // Java 10+");
        
        var message = "Hello";
        System.out.println("message = " + message);
        
        // BigDecimal vs decimal
        System.out.println("\nDECIMAL TYPES:");
        System.out.println("C#:   decimal price = 19.99m;");
        System.out.println("Java: BigDecimal price = new BigDecimal(\"19.99\");");
        
        BigDecimal price = new BigDecimal("19.99");
        System.out.println("price = " + price);
        
        // Common mistake: double precision
        System.out.println("\nCOMMON MISTAKE - PRECISION:");
        double doublePrice = 19.99;
        System.out.println("double price = " + doublePrice);
        System.out.println("Better: BigDecimal for money calculations");
        
        // String comparison
        System.out.println("\nSTRING COMPARISON:");
        System.out.println("C#:   name1 == name2 (works correctly)");
        System.out.println("Java: name1.equals(name2) (correct)");
        System.out.println("Java: name1 == name2 (WRONG - reference comparison)");
        
        String name1 = "John";
        String name2 = "John";
        String name3 = new String("John");
        
        System.out.println("name1.equals(name2): " + name1.equals(name2));
        System.out.println("name1 == name2: " + (name1 == name2)); // String pooling
        System.out.println("name1 == name3: " + (name1 == name3)); // Different objects
        System.out.println("name1.equals(name3): " + name1.equals(name3));
    }
    
    /**
     * DEMO 3: Object Creation and Properties
     * 
     * Show how object creation differs between C# and Java
     */
    public static void demonstrateObjectCreation() {
        System.out.println("\n--- Demo 3: Object Creation ---");
        
        // Object initializer syntax
        System.out.println("OBJECT CREATION:");
        System.out.println("C#:   var person = new Person { Name = \"John\", Age = 30 };");
        System.out.println("Java: Person person = new Person(\"John\", 30);");
        System.out.println("Java: Person person = Person.builder().name(\"John\").age(30).build();");
        
        // Traditional Java approach
        Person person = new Person("John", 30);
        System.out.println("Traditional: " + person);
        
        // Builder pattern (if available)
        Person builderPerson = Person.builder()
            .name("Jane")
            .age(25)
            .email("jane@example.com")
            .build();
        System.out.println("Builder: " + builderPerson);
        
        // Properties vs getters/setters
        System.out.println("\nPROPERTIES:");
        System.out.println("C#:   person.Name = \"New Name\";");
        System.out.println("Java: person.setName(\"New Name\");");
        System.out.println("C#:   var name = person.Name;");
        System.out.println("Java: String name = person.getName();");
        
        person.setName("Updated John");
        System.out.println("Updated name: " + person.getName());
        
        // Method chaining
        System.out.println("\nMETHOD CHAINING:");
        Person chainedPerson = new Person()
            .setName("Chained")
            .setAge(35)
            .setEmail("chained@example.com");
        System.out.println("Chained: " + chainedPerson);
        
        // Null safety
        System.out.println("\nNULL SAFETY:");
        Person nullPerson = null;
        
        // Safe navigation
        System.out.println("C#:   var name = person?.Name ?? \"Unknown\";");
        System.out.println("Java: String name = person != null ? person.getName() : \"Unknown\";");
        System.out.println("Java: String name = Optional.ofNullable(person).map(Person::getName).orElse(\"Unknown\");");
        
        String safeName = nullPerson != null ? nullPerson.getName() : "Unknown";
        System.out.println("Safe name: " + safeName);
        
        Optional<String> optionalName = Optional.ofNullable(nullPerson)
            .map(Person::getName);
        System.out.println("Optional name: " + optionalName.orElse("Unknown"));
    }
    
    /**
     * DEMO 4: Collections
     * 
     * Show collection usage differences
     */
    public static void demonstrateCollections() {
        System.out.println("\n--- Demo 4: Collections ---");
        
        // List creation
        System.out.println("LIST CREATION:");
        System.out.println("C#:   var names = new List<string> { \"Alice\", \"Bob\", \"Charlie\" };");
        System.out.println("Java: List<String> names = Arrays.asList(\"Alice\", \"Bob\", \"Charlie\");");
        System.out.println("Java: List<String> names = List.of(\"Alice\", \"Bob\", \"Charlie\");");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        System.out.println("List: " + names);
        
        // Mutable list
        List<String> mutableNames = new ArrayList<>(names);
        mutableNames.add("Diana");
        System.out.println("Mutable list: " + mutableNames);
        
        // Dictionary vs Map
        System.out.println("\nDICTIONARY/MAP:");
        System.out.println("C#:   var dict = new Dictionary<string, int> { [\"Alice\"] = 25, [\"Bob\"] = 30 };");
        System.out.println("Java: Map<String, Integer> map = Map.of(\"Alice\", 25, \"Bob\", 30);");
        
        Map<String, Integer> ages = Map.of("Alice", 25, "Bob", 30);
        System.out.println("Map: " + ages);
        
        // Mutable map
        Map<String, Integer> mutableAges = new HashMap<>(ages);
        mutableAges.put("Charlie", 35);
        System.out.println("Mutable map: " + mutableAges);
        
        // Iteration
        System.out.println("\nITERATION:");
        System.out.println("C#:   foreach (var name in names)");
        System.out.println("Java: for (String name : names)");
        System.out.println("Java: names.forEach(System.out::println)");
        
        System.out.println("Enhanced for loop:");
        for (String name : names) {
            System.out.println("  " + name);
        }
        
        System.out.println("Lambda forEach:");
        names.forEach(name -> System.out.println("  " + name));
        
        // LINQ vs Streams
        System.out.println("\nLINQ vs STREAMS:");
        System.out.println("C#:   var longNames = names.Where(n => n.Length > 4).ToList();");
        System.out.println("Java: List<String> longNames = names.stream().filter(n -> n.length() > 4).collect(toList());");
        
        List<String> longNames = names.stream()
            .filter(name -> name.length() > 4)
            .collect(java.util.stream.Collectors.toList());
        System.out.println("Long names: " + longNames);
    }
    
    /**
     * DEMO 5: Business Logic Example
     * 
     * Show practical business logic implementation
     */
    public static void demonstrateBusinessLogic() {
        System.out.println("\n--- Demo 5: Business Logic ---");
        
        // Create some sample data
        List<Person> people = Arrays.asList(
            new Person("John", 30, "john@example.com"),
            new Person("Jane", 25, "jane@example.com"),
            new Person("Bob", 35, "bob@example.com"),
            new Person("Alice", 28, "alice@example.com")
        );
        
        System.out.println("Sample data:");
        people.forEach(person -> System.out.println("  " + person));
        
        // Business rules
        System.out.println("\nBUSINESS RULES:");
        System.out.println("1. Find people over 30");
        System.out.println("2. Get their email addresses");
        System.out.println("3. Sort by name");
        
        // C# LINQ equivalent
        System.out.println("\nC# LINQ:");
        System.out.println("var emails = people");
        System.out.println("    .Where(p => p.Age > 30)");
        System.out.println("    .OrderBy(p => p.Name)");
        System.out.println("    .Select(p => p.Email)");
        System.out.println("    .ToList();");
        
        // Java Streams
        System.out.println("\nJava Streams:");
        System.out.println("List<String> emails = people.stream()");
        System.out.println("    .filter(p -> p.getAge() > 30)");
        System.out.println("    .sorted(Comparator.comparing(Person::getName))");
        System.out.println("    .map(Person::getEmail)");
        System.out.println("    .collect(Collectors.toList());");
        
        // Execute the logic
        List<String> emails = people.stream()
            .filter(p -> p.getAge() > 30)
            .sorted(Comparator.comparing(Person::getName))
            .map(Person::getEmail)
            .collect(java.util.stream.Collectors.toList());
        
        System.out.println("Result: " + emails);
        
        // Error handling
        System.out.println("\nERROR HANDLING:");
        System.out.println("C#:   try { ... } catch (Exception ex) { ... }");
        System.out.println("Java: try { ... } catch (Exception ex) { ... }");
        
        try {
            // Simulate potential error
            String email = findPersonByName(people, "NonExistent").getEmail();
            System.out.println("Found email: " + email);
        } catch (PersonNotFoundException ex) {
            System.out.println("Caught exception: " + ex.getMessage());
        }
        
        // Optional handling
        System.out.println("\nOPTIONAL HANDLING:");
        Optional<Person> optionalPerson = findPersonByNameOptional(people, "John");
        
        if (optionalPerson.isPresent()) {
            System.out.println("Found person: " + optionalPerson.get());
        } else {
            System.out.println("Person not found");
        }
        
        // More elegant Optional usage
        String personInfo = findPersonByNameOptional(people, "Alice")
            .map(p -> p.getName() + " (" + p.getAge() + ")")
            .orElse("Unknown person");
        System.out.println("Person info: " + personInfo);
    }
    
    /**
     * Helper method for demo
     */
    private static String getGreeting(String name) {
        return "Hello, " + name + "!";
    }
    
    /**
     * Find person by name (throws exception if not found)
     */
    private static Person findPersonByName(List<Person> people, String name) {
        return people.stream()
            .filter(p -> p.getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new PersonNotFoundException("Person not found: " + name));
    }
    
    /**
     * Find person by name (returns Optional)
     */
    private static Optional<Person> findPersonByNameOptional(List<Person> people, String name) {
        return people.stream()
            .filter(p -> p.getName().equals(name))
            .findFirst();
    }
    
    /**
     * Simple Person class for demonstration
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person {
        private String name;
        private int age;
        private String email;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        /**
         * Fluent setter for method chaining
         */
        public Person setName(String name) {
            this.name = name;
            return this;
        }
        
        /**
         * Fluent setter for method chaining
         */
        public Person setAge(int age) {
            this.age = age;
            return this;
        }
        
        /**
         * Fluent setter for method chaining
         */
        public Person setEmail(String email) {
            this.email = email;
            return this;
        }
        
        /**
         * Builder pattern for demonstration
         */
        public static PersonBuilder builder() {
            return new PersonBuilder();
        }
        
        public static class PersonBuilder {
            private String name;
            private int age;
            private String email;
            
            public PersonBuilder name(String name) {
                this.name = name;
                return this;
            }
            
            public PersonBuilder age(int age) {
                this.age = age;
                return this;
            }
            
            public PersonBuilder email(String email) {
                this.email = email;
                return this;
            }
            
            public Person build() {
                return new Person(name, age, email);
            }
        }
    }
    
    /**
     * Custom exception for demonstration
     */
    public static class PersonNotFoundException extends RuntimeException {
        public PersonNotFoundException(String message) {
            super(message);
        }
    }
}

/*
LIVE CODING TIPS:

1. START SIMPLE:
   - Begin with "Hello World" comparison
   - Build complexity gradually
   - Pause for questions frequently

2. HIGHLIGHT DIFFERENCES:
   - Use side-by-side comparisons
   - Explain "why" not just "what"
   - Show common mistakes

3. INTERACTIVE ELEMENTS:
   - Ask students to predict output
   - Have them spot the differences
   - Encourage questions

4. PRACTICAL EXAMPLES:
   - Use business domain examples
   - Show real-world scenarios
   - Connect to previous C# experience

5. COMMON GOTCHAS:
   - String comparison with ==
   - Null handling differences
   - Property vs getter/setter
   - Collection initialization

6. PERFORMANCE NOTES:
   - When to use BigDecimal vs double
   - Stream vs traditional loops
   - Object creation patterns

7. MODERN JAVA:
   - Show var keyword (Java 10+)
   - Demonstrate text blocks (Java 15+)
   - Mention records (Java 14+)

8. TOOLS AND ENVIRONMENT:
   - Use IntelliJ with VS Code keymap
   - Show auto-completion
   - Demonstrate refactoring tools

EXPECTED STUDENT QUESTIONS:
- "Why do we need getters/setters?"
- "What's the difference between == and equals?"
- "How do I handle null values?"
- "Why is String comparison different?"
- "What's the Java equivalent of var?"
- "How do I initialize collections?"
- "What's the difference between List and ArrayList?"

DEMO VARIATIONS:
- Can focus more on syntax differences
- Can emphasize functional programming
- Can include more error handling
- Can show testing examples
- Can demonstrate Spring Boot basics
*/