package com.coherentsolutions.session1.exercises.stringpuzzle;

/**
 * EXERCISE 1.2: String Puzzle (5 min)
 * 
 * What's the output? Why?
 * 
 * String s1 = "Hello";
 * String s2 = "Hello";
 * String s3 = new String("Hello");
 * String s4 = "Hel" + "lo";
 * String s5 = "Hel";
 * String s6 = s5 + "lo";
 * 
 * System.out.println(s1 == s2);    // ?
 * System.out.println(s1 == s3);    // ?
 * System.out.println(s1 == s4);    // ?
 * System.out.println(s1 == s6);    // ?
 * System.out.println(s1.equals(s6)); // ?
 * 
 * This demonstrates the difference between == (reference equality) 
 * and .equals() (value equality) in Java strings.
 * 
 * Key Learning: Always use .equals() for string content comparison!
 */
public class StringPuzzleDemo {
    
    public static void main(String[] args) {
        System.out.println("=== STRING PUZZLE DEMO ===");
        
        // Let students guess the output first!
        System.out.println("\nBefore running, guess the output for each comparison:");
        System.out.println("Remember: == compares references, .equals() compares values");
        
        interactiveStringPuzzle();
        detailedStringInternExplanation();
        demonstrateCommonStringMistakes();
        demonstrateMemoryUsage();
    }
    
    public static void interactiveStringPuzzle() {
        System.out.println("\n=== INTERACTIVE STRING PUZZLE ===");
        
        String s1 = "Hello";          // String literal - goes to pool
        String s2 = "Hello";          // Same literal - reuses from pool
        String s3 = new String("Hello"); // Explicit object - new heap object
        String s4 = "Hel" + "lo";     // Compile-time concatenation - goes to pool
        String s5 = "Hel";            // String literal - goes to pool
        String s6 = s5 + "lo";        // Runtime concatenation - new object
        
        // Now reveal the answers with explanations
        System.out.println("\n--- QUIZ RESULTS ---");
        
        System.out.println("s1 == s2: " + (s1 == s2) + " (true - both reference same pooled object)");
        System.out.println("s1 == s3: " + (s1 == s3) + " (false - s3 is new object, not from pool)");
        System.out.println("s1 == s4: " + (s1 == s4) + " (true - compile-time concat goes to pool)");
        System.out.println("s1 == s6: " + (s1 == s6) + " (false - runtime concat creates new object)");
        System.out.println("s1.equals(s6): " + s1.equals(s6) + " (true - content is identical)");
        
        // Memory addresses (for educational purposes)
        System.out.println("\nMemory locations (for illustration):");
        System.out.println("s1 identity: " + System.identityHashCode(s1));
        System.out.println("s2 identity: " + System.identityHashCode(s2));
        System.out.println("s3 identity: " + System.identityHashCode(s3));
        System.out.println("s4 identity: " + System.identityHashCode(s4));
        System.out.println("s6 identity: " + System.identityHashCode(s6));
    }
    
    public static void detailedStringInternExplanation() {
        System.out.println("\n=== STRING INTERN EXPLANATION ===");
        
        // String pool demonstration
        String pooled1 = "Java";
        String pooled2 = "Java";
        String notPooled = new String("Java");
        String interned = notPooled.intern(); // Force into pool
        
        System.out.println("pooled1 == pooled2: " + (pooled1 == pooled2)); // true
        System.out.println("pooled1 == notPooled: " + (pooled1 == notPooled)); // false
        System.out.println("pooled1 == interned: " + (pooled1 == interned)); // true
        
        // Concatenation behavior
        String prefix = "Ja";
        String suffix = "va";
        String runtime = prefix + suffix;  // Runtime concatenation
        String compiletime = "Ja" + "va";  // Compile-time concatenation
        
        System.out.println("\nConcatenation behavior:");
        System.out.println("runtime == pooled1: " + (runtime == pooled1)); // false
        System.out.println("compiletime == pooled1: " + (compiletime == pooled1)); // true
        System.out.println("runtime.equals(pooled1): " + runtime.equals(pooled1)); // true
    }
    
    public static void demonstrateCommonStringMistakes() {
        System.out.println("\n=== COMMON STRING MISTAKES ===");
        
        // 1. Using == instead of .equals()
        System.out.println("1. Comparison Mistakes:");
        String userInput = new String("admin"); // Simulates user input
        String expected = "admin";
        
        System.out.println("Wrong way (==): " + (userInput == expected)); // false
        System.out.println("Correct way (.equals): " + userInput.equals(expected)); // true
        
        // 2. Null safety
        System.out.println("\n2. Null Safety:");
        String nullString = null;
        String validString = "test";
        
        // ❌ Wrong: Will throw NullPointerException
        try {
            boolean result = nullString.equals(validString);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException with nullString.equals()");
        }
        
        // ✅ Correct: Safe comparison
        System.out.println("Safe comparison: " + java.util.Objects.equals(nullString, validString));
        
        // ✅ Correct: Defensive programming
        System.out.println("Defensive comparison: " + validString.equals(nullString));
        
        // 3. String building
        System.out.println("\n3. String Building:");
        
        // ❌ Wrong: Multiple concatenations
        String wrong = "Hello" + " " + "World" + "!";
        
        // ✅ Correct: StringBuilder for multiple operations
        StringBuilder correct = new StringBuilder()
            .append("Hello")
            .append(" ")
            .append("World")
            .append("!");
        
        System.out.println("Result: " + correct.toString());
        
        // 4. String formatting
        System.out.println("\n4. String Formatting:");
        String name = "Alice";
        int age = 30;
        
        // C# style: $"Name: {name}, Age: {age}"
        // Java alternatives:
        System.out.println("String.format(): " + String.format("Name: %s, Age: %d", name, age));
        System.out.println("printf style: ");
        System.out.printf("Name: %s, Age: %d%n", name, age);
        
        // Java 15+ Text Blocks
        String textBlock = """
            Name: %s
            Age: %d
            Status: Active
            """;
        System.out.println("Text block: " + String.format(textBlock, name, age));
    }
    
    // Utility methods for string handling
    public static class StringUtils {
        
        // Safe string comparison
        public static boolean safeEquals(String s1, String s2) {
            return java.util.Objects.equals(s1, s2);
        }
        
        // Safe case-insensitive comparison
        public static boolean safeEqualsIgnoreCase(String s1, String s2) {
            if (s1 == null && s2 == null) return true;
            if (s1 == null || s2 == null) return false;
            return s1.equalsIgnoreCase(s2);
        }
        
        // Check if string is null or empty
        public static boolean isNullOrEmpty(String s) {
            return s == null || s.isEmpty();
        }
        
        // Check if string is null, empty, or whitespace
        public static boolean isNullOrBlank(String s) {
            return s == null || s.trim().isEmpty();
        }
        
        // Safe substring
        public static String safeSubstring(String s, int start, int end) {
            if (s == null) return null;
            if (start < 0) start = 0;
            if (end > s.length()) end = s.length();
            if (start >= end) return "";
            return s.substring(start, end);
        }
    }
    
    // Memory usage demonstration
    public static void demonstrateMemoryUsage() {
        System.out.println("\n=== MEMORY USAGE DEMO ===");
        
        // This creates many temporary String objects
        String inefficient = "";
        for (int i = 0; i < 1000; i++) {
            inefficient += "x";  // Creates new String each time
        }
        
        // This reuses the internal buffer
        StringBuilder efficient = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            efficient.append("x");  // Modifies existing buffer
        }
        
        System.out.println("Both methods create same result, but different memory usage");
        System.out.println("Inefficient length: " + inefficient.length());
        System.out.println("Efficient length: " + efficient.length());
    }
}