package com.coherentsolutions.session1.reference;

import java.util.*;
import java.util.function.*;

/**
 * METHOD EXAMPLES REFERENCE: Java Methods vs C# Methods
 * 
 * This reference class demonstrates the differences between Java and C# methods,
 * including method signatures, overloading, varargs, lambda expressions, and
 * functional interfaces.
 * 
 * COVERAGE:
 * 1. Method declarations and signatures
 * 2. Method overloading vs overriding
 * 3. Static vs instance methods
 * 4. Varargs (variable arguments)
 * 5. Lambda expressions and method references
 * 6. Functional interfaces
 * 7. Default and static methods in interfaces
 * 8. Access modifiers and visibility
 * 
 * FOR LECTURE USE:
 * - Compare method syntax between Java and C#
 * - Demonstrate functional programming concepts
 * - Show method overloading and overriding patterns
 * - Explain visibility and access control
 */
public class MethodExamples {
    
    // Instance field for demonstration
    private String instanceField = "Instance Data";
    
    // Static field for demonstration
    private static String staticField = "Static Data";
    
    public static void main(String[] args) {
        System.out.println("=== METHOD EXAMPLES REFERENCE ===");
        
        MethodExamples examples = new MethodExamples();
        
        examples.demonstrateMethodDeclarations();
        examples.demonstrateMethodOverloading();
        examples.demonstrateStaticVsInstance();
        examples.demonstrateVarargs();
        examples.demonstrateLambdaExpressions();
        examples.demonstrateFunctionalInterfaces();
        examples.demonstrateMethodReferences();
        examples.demonstrateDefaultMethods();
        examples.demonstrateAccessModifiers();
        examples.demonstrateAdvancedPatterns();
        
        System.out.println("\n=== METHOD EXAMPLES COMPLETE ===");
    }
    
    /**
     * METHOD DECLARATIONS AND SIGNATURES
     * 
     * Java vs C# method syntax comparison
     */
    public void demonstrateMethodDeclarations() {
        System.out.println("\n--- Method Declarations ---");
        
        // Basic method examples
        String result1 = simpleMethod();
        String result2 = methodWithParameters("Hello", 42);
        int result3 = methodWithReturnType(10, 20);
        
        System.out.println("Simple method result: " + result1);
        System.out.println("Method with parameters: " + result2);
        System.out.println("Method with return type: " + result3);
        
        // Method syntax comparison
        System.out.println("\nJava vs C# Method Syntax:");
        System.out.println("Java:  public String methodName(int param) { }");
        System.out.println("C#:    public string MethodName(int param) { }");
        System.out.println("\nKey Differences:");
        System.out.println("- Java: camelCase method names");
        System.out.println("- C#: PascalCase method names");
        System.out.println("- Java: String (capital S)");
        System.out.println("- C#: string (lowercase s)");
        System.out.println("- Java: No 'out' or 'ref' parameters");
        System.out.println("- C#: Has 'out' and 'ref' parameters");
        
        // Void methods
        performVoidOperation("Testing void method");
        
        // Methods with different return types
        boolean boolResult = returnBoolean();
        double doubleResult = returnDouble();
        List<String> listResult = returnList();
        
        System.out.println("Boolean result: " + boolResult);
        System.out.println("Double result: " + doubleResult);
        System.out.println("List result: " + listResult);
    }
    
    // Basic method examples
    public String simpleMethod() {
        return "Simple method executed";
    }
    
    public String methodWithParameters(String text, int number) {
        return text + " - " + number;
    }
    
    public int methodWithReturnType(int a, int b) {
        return a + b;
    }
    
    public void performVoidOperation(String message) {
        System.out.println("Void operation: " + message);
    }
    
    public boolean returnBoolean() {
        return true;
    }
    
    public double returnDouble() {
        return 3.14159;
    }
    
    public List<String> returnList() {
        return Arrays.asList("item1", "item2", "item3");
    }
    
    /**
     * METHOD OVERLOADING
     * 
     * Same method name, different parameters
     */
    public void demonstrateMethodOverloading() {
        System.out.println("\n--- Method Overloading ---");
        
        // Method overloading examples
        String result1 = processData("String data");
        String result2 = processData(42);
        String result3 = processData("Mixed", 123);
        String result4 = processData(1, 2, 3, 4, 5);
        
        System.out.println("Overloaded method results:");
        System.out.println("String: " + result1);
        System.out.println("Integer: " + result2);
        System.out.println("Mixed: " + result3);
        System.out.println("Multiple ints: " + result4);
        
        // Constructor overloading
        OverloadExample obj1 = new OverloadExample();
        OverloadExample obj2 = new OverloadExample("Custom");
        OverloadExample obj3 = new OverloadExample("Custom", 42);
        
        System.out.println("Constructor overloading:");
        System.out.println("Default: " + obj1.getValue());
        System.out.println("String: " + obj2.getValue());
        System.out.println("String + int: " + obj3.getValue());
        
        System.out.println("\nOverloading Rules:");
        System.out.println("1. Same method name");
        System.out.println("2. Different parameter list (type, number, order)");
        System.out.println("3. Return type alone cannot differentiate");
        System.out.println("4. Access modifier can be different");
        System.out.println("5. Can throw different exceptions");
    }
    
    // Method overloading examples
    public String processData(String data) {
        return "Processing string: " + data;
    }
    
    public String processData(int data) {
        return "Processing integer: " + data;
    }
    
    public String processData(String text, int number) {
        return "Processing mixed: " + text + " + " + number;
    }
    
    public String processData(int... numbers) {
        return "Processing multiple integers: " + Arrays.toString(numbers);
    }
    
    // Constructor overloading example
    public static class OverloadExample {
        private String value;
        
        public OverloadExample() {
            this.value = "Default value";
        }
        
        public OverloadExample(String value) {
            this.value = value;
        }
        
        public OverloadExample(String prefix, int number) {
            this.value = prefix + number;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    /**
     * STATIC VS INSTANCE METHODS
     * 
     * Differences between static and instance methods
     */
    public void demonstrateStaticVsInstance() {
        System.out.println("\n--- Static vs Instance Methods ---");
        
        // Static method calls
        String staticResult = staticMethod("Static call");
        int staticCalculation = calculateSum(10, 20);
        
        System.out.println("Static method result: " + staticResult);
        System.out.println("Static calculation: " + staticCalculation);
        
        // Instance method calls
        String instanceResult = instanceMethod("Instance call");
        String fieldAccess = accessInstanceField();
        
        System.out.println("Instance method result: " + instanceResult);
        System.out.println("Instance field access: " + fieldAccess);
        
        // Utility class example
        String formatted = StringUtils.formatName("john", "doe");
        boolean isEmpty = StringUtils.isEmpty("");
        
        System.out.println("Utility method result: " + formatted);
        System.out.println("Is empty check: " + isEmpty);
        
        System.out.println("\nStatic vs Instance Comparison:");
        System.out.println("Static methods:");
        System.out.println("- Belong to class, not instance");
        System.out.println("- Cannot access instance fields/methods");
        System.out.println("- Called with ClassName.methodName()");
        System.out.println("- Memory efficient (single copy)");
        System.out.println("\nInstance methods:");
        System.out.println("- Belong to object instance");
        System.out.println("- Can access all instance and static members");
        System.out.println("- Called with objectInstance.methodName()");
        System.out.println("- Each instance has access to method");
    }
    
    // Static method examples
    public static String staticMethod(String input) {
        return "Static: " + input + " - " + staticField;
    }
    
    public static int calculateSum(int a, int b) {
        return a + b;
    }
    
    // Instance method examples
    public String instanceMethod(String input) {
        return "Instance: " + input + " - " + instanceField;
    }
    
    public String accessInstanceField() {
        return "Accessing: " + instanceField;
    }
    
    // Utility class example
    public static class StringUtils {
        public static String formatName(String firstName, String lastName) {
            return firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase() +
                   " " + lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        }
        
        public static boolean isEmpty(String str) {
            return str == null || str.trim().isEmpty();
        }
        
        public static String reverse(String str) {
            return str == null ? null : new StringBuilder(str).reverse().toString();
        }
    }
    
    /**
     * VARARGS (VARIABLE ARGUMENTS)
     * 
     * Java varargs vs C# params
     */
    public void demonstrateVarargs() {
        System.out.println("\n--- Varargs (Variable Arguments) ---");
        
        // Varargs examples
        int sum1 = calculateSum();
        int sum2 = calculateSum(5);
        int sum3 = calculateSum(1, 2, 3, 4, 5);
        
        System.out.println("Sum with no args: " + sum1);
        System.out.println("Sum with 1 arg: " + sum2);
        System.out.println("Sum with 5 args: " + sum3);
        
        // String varargs
        String message1 = createMessage("Hello");
        String message2 = createMessage("Hello", "World");
        String message3 = createMessage("Java", "is", "awesome");
        
        System.out.println("Message 1: " + message1);
        System.out.println("Message 2: " + message2);
        System.out.println("Message 3: " + message3);
        
        // Mixed parameters with varargs
        String formatted = formatText("Template: %s %s %s", "arg1", "arg2", "arg3");
        System.out.println("Formatted: " + formatted);
        
        // Array can be passed to varargs
        int[] numbers = {10, 20, 30, 40};
        int arraySum = calculateSum(numbers);
        System.out.println("Array sum: " + arraySum);
        
        System.out.println("\nJava vs C# Variable Arguments:");
        System.out.println("Java: public void method(int... args)");
        System.out.println("C#:   public void Method(params int[] args)");
        System.out.println("\nVarargs Rules:");
        System.out.println("1. Must be last parameter");
        System.out.println("2. Only one varargs per method");
        System.out.println("3. Treated as array inside method");
        System.out.println("4. Can pass array or individual elements");
    }
    
    // Varargs method examples
    public static int calculateSum(int... numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }
    
    public static String createMessage(String... words) {
        return String.join(" ", words);
    }
    
    public static String formatText(String template, String... args) {
        // Simple string formatting
        String result = template;
        for (String arg : args) {
            result = result.replaceFirst("%s", arg);
        }
        return result;
    }
    
    /**
     * LAMBDA EXPRESSIONS
     * 
     * Java 8+ lambda expressions vs C# lambda expressions
     */
    public void demonstrateLambdaExpressions() {
        System.out.println("\n--- Lambda Expressions ---");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Lambda with forEach
        System.out.println("Names (forEach with lambda):");
        names.forEach(name -> System.out.println("  " + name));
        
        // Lambda with filter and map
        List<String> upperCaseNames = names.stream()
            .filter(name -> name.length() > 3)
            .map(name -> name.toUpperCase())
            .collect(java.util.stream.Collectors.toList());
        
        System.out.println("Filtered and mapped names: " + upperCaseNames);
        
        // Lambda with sorting
        List<String> sortedNames = new ArrayList<>(names);
        sortedNames.sort((a, b) -> a.compareToIgnoreCase(b));
        System.out.println("Sorted names: " + sortedNames);
        
        // Lambda with numbers
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(java.util.stream.Collectors.toList());
        
        System.out.println("Even numbers: " + evenNumbers);
        
        // Lambda with reduce
        int sum = numbers.stream()
            .reduce(0, (a, b) -> a + b);
        
        System.out.println("Sum using reduce: " + sum);
        
        // Complex lambda
        Map<Boolean, List<String>> partitioned = names.stream()
            .collect(java.util.stream.Collectors.partitioningBy(name -> name.length() > 4));
        
        System.out.println("Partitioned by length > 4: " + partitioned);
        
        System.out.println("\nJava vs C# Lambda Syntax:");
        System.out.println("Java: (x, y) -> x + y");
        System.out.println("C#:   (x, y) => x + y");
        System.out.println("Java: x -> x * 2");
        System.out.println("C#:   x => x * 2");
        System.out.println("\nLambda Features:");
        System.out.println("1. Concise function syntax");
        System.out.println("2. Can capture local variables (effectively final)");
        System.out.println("3. Type inference available");
        System.out.println("4. Can be assigned to functional interfaces");
    }
    
    /**
     * FUNCTIONAL INTERFACES
     * 
     * Built-in and custom functional interfaces
     */
    public void demonstrateFunctionalInterfaces() {
        System.out.println("\n--- Functional Interfaces ---");
        
        // Predicate<T> - takes T, returns boolean
        Predicate<String> isLong = s -> s.length() > 5;
        System.out.println("Is 'Hello' long? " + isLong.test("Hello"));
        System.out.println("Is 'Hello World' long? " + isLong.test("Hello World"));
        
        // Function<T, R> - takes T, returns R
        Function<String, Integer> stringLength = s -> s.length();
        System.out.println("Length of 'Java': " + stringLength.apply("Java"));
        
        // Consumer<T> - takes T, returns void
        Consumer<String> printer = s -> System.out.println("Printing: " + s);
        printer.accept("Hello from Consumer");
        
        // Supplier<T> - takes nothing, returns T
        Supplier<String> randomString = () -> "Random-" + Math.random();
        System.out.println("Random string: " + randomString.get());
        
        // BiFunction<T, U, R> - takes T and U, returns R
        BiFunction<String, String, String> concatenator = (a, b) -> a + " " + b;
        System.out.println("Concatenated: " + concatenator.apply("Hello", "World"));
        
        // Custom functional interface
        Calculator add = (a, b) -> a + b;
        Calculator multiply = (a, b) -> a * b;
        
        System.out.println("Custom functional interface:");
        System.out.println("Add: " + performCalculation(10, 5, add));
        System.out.println("Multiply: " + performCalculation(10, 5, multiply));
        
        // Method references
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        
        // Instance method reference
        words.forEach(System.out::println);
        
        // Static method reference
        List<Integer> lengths = words.stream()
            .map(String::length)
            .collect(java.util.stream.Collectors.toList());
        System.out.println("Lengths: " + lengths);
        
        System.out.println("\nCommon Functional Interfaces:");
        System.out.println("Predicate<T>: T -> boolean");
        System.out.println("Function<T,R>: T -> R");
        System.out.println("Consumer<T>: T -> void");
        System.out.println("Supplier<T>: () -> T");
        System.out.println("BiFunction<T,U,R>: (T,U) -> R");
        System.out.println("UnaryOperator<T>: T -> T");
        System.out.println("BinaryOperator<T>: (T,T) -> T");
    }
    
    // Custom functional interface
    @FunctionalInterface
    public interface Calculator {
        double calculate(double a, double b);
        
        // Default method allowed in functional interface
        default String getDescription() {
            return "A calculator that performs operations on two doubles";
        }
    }
    
    public static double performCalculation(double a, double b, Calculator calc) {
        return calc.calculate(a, b);
    }
    
    /**
     * METHOD REFERENCES
     * 
     * Different types of method references
     */
    public void demonstrateMethodReferences() {
        System.out.println("\n--- Method References ---");
        
        List<String> words = Arrays.asList("hello", "world", "java", "programming");
        
        // Static method reference
        System.out.println("Static method reference (String::valueOf):");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<String> stringNumbers = numbers.stream()
            .map(String::valueOf)
            .collect(java.util.stream.Collectors.toList());
        System.out.println("Numbers as strings: " + stringNumbers);
        
        // Instance method reference on class
        System.out.println("\nInstance method reference (String::toUpperCase):");
        List<String> upperWords = words.stream()
            .map(String::toUpperCase)
            .collect(java.util.stream.Collectors.toList());
        System.out.println("Uppercase words: " + upperWords);
        
        // Instance method reference on object
        System.out.println("\nInstance method reference on object:");
        PrintService printer = new PrintService("PREFIX");
        words.forEach(printer::printWithPrefix);
        
        // Constructor reference
        System.out.println("\nConstructor reference (ArrayList::new):");
        List<String> newList = words.stream()
            .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        System.out.println("New list: " + newList);
        
        // Array constructor reference
        String[] wordsArray = words.stream()
            .toArray(String[]::new);
        System.out.println("Words array: " + Arrays.toString(wordsArray));
        
        System.out.println("\nMethod Reference Types:");
        System.out.println("1. Static method: ClassName::methodName");
        System.out.println("2. Instance method of class: ClassName::instanceMethod");
        System.out.println("3. Instance method of object: object::instanceMethod");
        System.out.println("4. Constructor: ClassName::new");
        System.out.println("5. Array constructor: Type[]::new");
    }
    
    // Helper class for method reference examples
    public static class PrintService {
        private final String prefix;
        
        public PrintService(String prefix) {
            this.prefix = prefix;
        }
        
        public void printWithPrefix(String text) {
            System.out.println("  " + prefix + ": " + text);
        }
    }
    
    /**
     * DEFAULT AND STATIC METHODS IN INTERFACES
     * 
     * Java 8+ interface features
     */
    public void demonstrateDefaultMethods() {
        System.out.println("\n--- Default and Static Methods in Interfaces ---");
        
        // Using interface with default methods
        Drawable circle = new Circle(5);
        Drawable rectangle = new Rectangle(4, 6);
        
        System.out.println("Circle:");
        circle.draw();
        circle.printInfo();
        System.out.println("Area: " + circle.calculateArea());
        
        System.out.println("\nRectangle:");
        rectangle.draw();
        rectangle.printInfo();
        System.out.println("Area: " + rectangle.calculateArea());
        
        // Using static method from interface
        String info = Drawable.getVersion();
        System.out.println("Drawable version: " + info);
        
        System.out.println("\nInterface Features (Java 8+):");
        System.out.println("1. Default methods: provide default implementation");
        System.out.println("2. Static methods: utility methods in interfaces");
        System.out.println("3. Multiple inheritance of behavior");
        System.out.println("4. Backward compatibility for existing interfaces");
        
        System.out.println("\nComparison with C#:");
        System.out.println("Java default methods ≈ C# default interface methods (.NET Core 3+)");
        System.out.println("Java static interface methods ≈ C# static interface methods");
    }
    
    // Interface with default and static methods
    public interface Drawable {
        // Abstract method
        void draw();
        
        // Default method
        default void printInfo() {
            System.out.println("This is a drawable object");
        }
        
        // Default method with logic
        default double calculateArea() {
            return 0.0; // Default implementation
        }
        
        // Static method
        static String getVersion() {
            return "Drawable v1.0";
        }
        
        // Static utility method
        static void validateDimension(double dimension) {
            if (dimension <= 0) {
                throw new IllegalArgumentException("Dimension must be positive");
            }
        }
    }
    
    // Implementation classes
    public static class Circle implements Drawable {
        private final double radius;
        
        public Circle(double radius) {
            Drawable.validateDimension(radius);
            this.radius = radius;
        }
        
        @Override
        public void draw() {
            System.out.println("Drawing a circle with radius " + radius);
        }
        
        @Override
        public double calculateArea() {
            return Math.PI * radius * radius;
        }
    }
    
    public static class Rectangle implements Drawable {
        private final double width;
        private final double height;
        
        public Rectangle(double width, double height) {
            Drawable.validateDimension(width);
            Drawable.validateDimension(height);
            this.width = width;
            this.height = height;
        }
        
        @Override
        public void draw() {
            System.out.println("Drawing a rectangle " + width + "x" + height);
        }
        
        @Override
        public double calculateArea() {
            return width * height;
        }
    }
    
    /**
     * ACCESS MODIFIERS AND VISIBILITY
     * 
     * Java access modifiers vs C# access modifiers
     */
    public void demonstrateAccessModifiers() {
        System.out.println("\n--- Access Modifiers ---");
        
        AccessExample example = new AccessExample();
        
        // Public access
        example.publicMethod();
        System.out.println("Public field: " + example.publicField);
        
        // Package-private access (default)
        example.packageMethod();
        System.out.println("Package field: " + example.packageField);
        
        // Protected access (within same package)
        example.protectedMethod();
        System.out.println("Protected field: " + example.protectedField);
        
        // Private access (not accessible from outside class)
        // example.privateMethod(); // Compilation error
        // System.out.println(example.privateField); // Compilation error
        
        System.out.println("\nJava vs C# Access Modifiers:");
        System.out.println("┌─────────────────┬─────────────┬─────────────┐");
        System.out.println("│ Access Level    │ Java        │ C#          │");
        System.out.println("├─────────────────┼─────────────┼─────────────┤");
        System.out.println("│ Same class      │ private     │ private     │");
        System.out.println("│ Same package    │ (default)   │ internal    │");
        System.out.println("│ Subclass        │ protected   │ protected   │");
        System.out.println("│ All classes     │ public      │ public      │");
        System.out.println("│ Assembly+sub    │ N/A         │ protected   │");
        System.out.println("│                 │             │ internal    │");
        System.out.println("└─────────────────┴─────────────┴─────────────┘");
        
        System.out.println("\nAccess Modifier Rules:");
        System.out.println("1. private: Only within the same class");
        System.out.println("2. (default): Same package (package-private)");
        System.out.println("3. protected: Same package + subclasses");
        System.out.println("4. public: Accessible everywhere");
        System.out.println("5. Java has no 'internal' equivalent to C#");
        System.out.println("6. Java protected includes package access");
    }
    
    // Access modifier examples
    public static class AccessExample {
        public String publicField = "Public";
        String packageField = "Package";
        protected String protectedField = "Protected";
        private String privateField = "Private";
        
        public void publicMethod() {
            System.out.println("Public method called");
        }
        
        void packageMethod() {
            System.out.println("Package method called");
        }
        
        protected void protectedMethod() {
            System.out.println("Protected method called");
        }
        
        private void privateMethod() {
            System.out.println("Private method called");
        }
    }
    
    /**
     * ADVANCED METHOD PATTERNS
     * 
     * Advanced method usage patterns and best practices
     */
    public void demonstrateAdvancedPatterns() {
        System.out.println("\n--- Advanced Method Patterns ---");
        
        // Builder pattern with fluent methods
        Person person = new Person.Builder()
            .setName("John Doe")
            .setAge(30)
            .setEmail("john@example.com")
            .setCity("New York")
            .build();
        
        System.out.println("Builder pattern result: " + person);
        
        // Method chaining
        String result = new StringBuilder()
            .append("Hello")
            .append(" ")
            .append("World")
            .append("!")
            .toString();
        
        System.out.println("Method chaining result: " + result);
        
        // Optional return patterns
        Optional<String> found = findUserById(123);
        Optional<String> notFound = findUserById(999);
        
        System.out.println("Found user: " + found.orElse("Not found"));
        System.out.println("Not found user: " + notFound.orElse("Not found"));
        
        // Factory methods
        List<String> list1 = createList("a", "b", "c");
        Map<String, Integer> map1 = createMap("key1", 1, "key2", 2);
        
        System.out.println("Factory list: " + list1);
        System.out.println("Factory map: " + map1);
        
        System.out.println("\nAdvanced Method Patterns:");
        System.out.println("1. Builder pattern for complex object construction");
        System.out.println("2. Method chaining for fluent APIs");
        System.out.println("3. Optional returns for null safety");
        System.out.println("4. Factory methods for object creation");
        System.out.println("5. Immutable objects with wither methods");
        System.out.println("6. Command pattern with functional interfaces");
    }
    
    // Builder pattern example
    public static class Person {
        private final String name;
        private final int age;
        private final String email;
        private final String city;
        
        private Person(Builder builder) {
            this.name = builder.name;
            this.age = builder.age;
            this.email = builder.email;
            this.city = builder.city;
        }
        
        public static class Builder {
            private String name;
            private int age;
            private String email;
            private String city;
            
            public Builder setName(String name) {
                this.name = name;
                return this;
            }
            
            public Builder setAge(int age) {
                this.age = age;
                return this;
            }
            
            public Builder setEmail(String email) {
                this.email = email;
                return this;
            }
            
            public Builder setCity(String city) {
                this.city = city;
                return this;
            }
            
            public Person build() {
                return new Person(this);
            }
        }
        
        @Override
        public String toString() {
            return String.format("Person{name='%s', age=%d, email='%s', city='%s'}", 
                               name, age, email, city);
        }
    }
    
    // Optional return pattern
    public static Optional<String> findUserById(int id) {
        // Simulate database lookup
        return id == 123 ? Optional.of("John Doe") : Optional.empty();
    }
    
    // Factory methods
    @SafeVarargs
    public static <T> List<T> createList(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }
    
    public static <K, V> Map<K, V> createMap(Object... keyValuePairs) {
        Map<K, V> map = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            @SuppressWarnings("unchecked")
            K key = (K) keyValuePairs[i];
            @SuppressWarnings("unchecked")
            V value = (V) keyValuePairs[i + 1];
            map.put(key, value);
        }
        return map;
    }
}