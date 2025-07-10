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
 * This exercise demonstrates Java's string interning behavior,
 * which is different from C# string handling.
 */
public class StringPuzzleDemo {
    
    public static void main(String[] args) {
        System.out.println("=== STRING PUZZLE DEMO ===");
        
        // Let students guess the output first!
        System.out.println("\nBefore running, guess the output for each comparison:");
        System.out.println("Remember: == compares references, .equals() compares values");
        
        interactiveStringPuzzle();
        detailedStringInternExplanation();
        practicalStringHandling();
    }
    
    public static void interactiveStringPuzzle() {
        System.out.println("\n=== INTERACTIVE STRING PUZZLE ===");
        
        // The classic string puzzle
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = new String("Hello");
        String s4 = "Hel" + "lo";
        String s5 = "Hel";
        String s6 = s5 + "lo";
        
        System.out.println("String definitions:");
        System.out.println("String s1 = \"Hello\";");
        System.out.println("String s2 = \"Hello\";");
        System.out.println("String s3 = new String(\"Hello\");");
        System.out.println("String s4 = \"Hel\" + \"lo\";");
        System.out.println("String s5 = \"Hel\";");
        System.out.println("String s6 = s5 + \"lo\";");
        
        System.out.println("\n--- QUIZ RESULTS ---");
        
        // Quiz 1: String literals
        System.out.println("s1 == s2: " + (s1 == s2));
        System.out.println("Explanation: String literals are interned - same reference");
        
        // Quiz 2: new String() vs literal
        System.out.println("s1 == s3: " + (s1 == s3));
        System.out.println("Explanation: new String() creates new object - different reference");
        
        // Quiz 3: Compile-time concatenation
        System.out.println("s1 == s4: " + (s1 == s4));
        System.out.println("Explanation: Compile-time constants are interned - same reference");
        
        // Quiz 4: Runtime concatenation
        System.out.println("s1 == s6: " + (s1 == s6));
        System.out.println("Explanation: Runtime concatenation creates new object - different reference");
        
        // Quiz 5: Value comparison
        System.out.println("s1.equals(s6): " + (s1.equals(s6)));
        System.out.println("Explanation: .equals() compares values - same content");
        
        // Memory addresses (for educational purposes)
        System.out.println("\n--- MEMORY ANALYSIS ---");
        System.out.println("s1 identity hash: " + System.identityHashCode(s1));
        System.out.println("s2 identity hash: " + System.identityHashCode(s2));
        System.out.println("s3 identity hash: " + System.identityHashCode(s3));
        System.out.println("s4 identity hash: " + System.identityHashCode(s4));
        System.out.println("s5 identity hash: " + System.identityHashCode(s5));
        System.out.println("s6 identity hash: " + System.identityHashCode(s6));
    }
    
    public static void detailedStringInternExplanation() {
        System.out.println("\n=== STRING INTERNING DEEP DIVE ===");
        
        // Demonstrate string pool behavior
        String literal1 = "Java";
        String literal2 = "Java";
        String object1 = new String("Java");
        String object2 = new String("Java");
        
        System.out.println("--- String Pool Behavior ---");
        System.out.println("literal1 == literal2: " + (literal1 == literal2));
        System.out.println("object1 == object2: " + (object1 == object2));
        System.out.println("literal1 == object1: " + (literal1 == object1));
        
        // Manual interning
        String interned = object1.intern();
        System.out.println("literal1 == object1.intern(): " + (literal1 == interned));
        
        System.out.println("\n--- Concatenation Behavior ---");
        
        // Compile-time concatenation (interned)
        String compileTime = "Ja" + "va";
        System.out.println("literal1 == \"Ja\" + \"va\": " + (literal1 == compileTime));
        
        // Runtime concatenation (not interned)
        String prefix = "Ja";
        String runtime = prefix + "va";
        System.out.println("literal1 == prefix + \"va\": " + (literal1 == runtime));
        
        // StringBuilder concatenation (not interned)
        StringBuilder sb = new StringBuilder();
        sb.append("Ja").append("va");
        String fromBuilder = sb.toString();
        System.out.println("literal1 == StringBuilder result: " + (literal1 == fromBuilder));
        
        System.out.println("\n--- Performance Implications ---");
        demonstrateStringPerformance();
    }
    
    public static void demonstrateStringPerformance() {
        int iterations = 10000;
        
        // String concatenation performance
        long start = System.nanoTime();
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += "a";  // Creates new String each time
        }
        long stringTime = System.nanoTime() - start;
        
        // StringBuilder performance
        start = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("a");  // Modifies existing buffer
        }
        String sbResult = sb.toString();
        long sbTime = System.nanoTime() - start;
        
        System.out.println("String concatenation time: " + stringTime / 1_000_000 + " ms");
        System.out.println("StringBuilder time: " + sbTime / 1_000_000 + " ms");
        System.out.println("Performance ratio: " + (stringTime / sbTime) + "x faster with StringBuilder");
    }
    
    public static void practicalStringHandling() {
        System.out.println("\n=== PRACTICAL STRING HANDLING ===");
        
        // Best practices for C# developers
        System.out.println("--- Best Practices for C# Developers ---");
        
        // 1. String comparison
        System.out.println("\n1. String Comparison:");
        String email1 = "user@example.com";
        String email2 = "USER@EXAMPLE.COM";
        
        // ❌ Wrong: Using ==
        System.out.println("Using == (wrong): " + (email1 == email2));
        
        // ✅ Correct: Using equals()
        System.out.println("Using equals(): " + email1.equals(email2));
        
        // ✅ Correct: Case-insensitive comparison
        System.out.println("Using equalsIgnoreCase(): " + email1.equalsIgnoreCase(email2));
        
        // 2. Null safety
        System.out.println("\n2. Null Safety:");
        String nullString = null;
        String validString = "test";
        
        // ❌ Wrong: Will throw NullPointerException
        try {\n            boolean result = nullString.equals(validString);\n        } catch (NullPointerException e) {\n            System.out.println(\"NullPointerException with nullString.equals()\");\n        }\n        \n        // ✅ Correct: Safe comparison\n        System.out.println(\"Safe comparison: \" + java.util.Objects.equals(nullString, validString));\n        \n        // ✅ Correct: Defensive programming\n        System.out.println(\"Defensive comparison: \" + validString.equals(nullString));\n        \n        // 3. String building\n        System.out.println(\"\\n3. String Building:\");\n        \n        // ❌ Wrong: Multiple concatenations\n        String wrong = \"Hello\" + \" \" + \"World\" + \"!\";\n        \n        // ✅ Correct: StringBuilder for multiple operations\n        StringBuilder correct = new StringBuilder()\n            .append(\"Hello\")\n            .append(\" \")\n            .append(\"World\")\n            .append(\"!\");\n        \n        System.out.println(\"Result: \" + correct.toString());\n        \n        // 4. String formatting\n        System.out.println(\"\\n4. String Formatting:\");\n        String name = \"Alice\";\n        int age = 30;\n        \n        // C# style: $\"Name: {name}, Age: {age}\"\n        // Java alternatives:\n        System.out.println(\"String.format(): \" + String.format(\"Name: %s, Age: %d\", name, age));\n        System.out.println(\"printf style: \");\n        System.out.printf(\"Name: %s, Age: %d%n\", name, age);\n        \n        // Java 15+ Text Blocks\n        String textBlock = \"\"\"\n            Name: %s\n            Age: %d\n            Status: Active\n            \"\"\";\n        System.out.println(\"Text block: \" + String.format(textBlock, name, age));\n    }\n    \n    // Utility methods for string handling\n    public static class StringUtils {\n        \n        // Safe string comparison\n        public static boolean safeEquals(String s1, String s2) {\n            return java.util.Objects.equals(s1, s2);\n        }\n        \n        // Safe case-insensitive comparison\n        public static boolean safeEqualsIgnoreCase(String s1, String s2) {\n            if (s1 == null && s2 == null) return true;\n            if (s1 == null || s2 == null) return false;\n            return s1.equalsIgnoreCase(s2);\n        }\n        \n        // Check if string is null or empty\n        public static boolean isNullOrEmpty(String s) {\n            return s == null || s.isEmpty();\n        }\n        \n        // Check if string is null, empty, or whitespace\n        public static boolean isNullOrBlank(String s) {\n            return s == null || s.trim().isEmpty();\n        }\n        \n        // Safe substring\n        public static String safeSubstring(String s, int start, int end) {\n            if (s == null) return null;\n            if (start < 0) start = 0;\n            if (end > s.length()) end = s.length();\n            if (start >= end) return \"\";\n            return s.substring(start, end);\n        }\n    }\n    \n    // Memory usage demonstration\n    public static void demonstrateMemoryUsage() {\n        System.out.println(\"\\n=== MEMORY USAGE DEMO ===\");\n        \n        // This creates many temporary String objects\n        String inefficient = \"\";\n        for (int i = 0; i < 1000; i++) {\n            inefficient += \"x\";  // Creates new String each time\n        }\n        \n        // This reuses the internal buffer\n        StringBuilder efficient = new StringBuilder();\n        for (int i = 0; i < 1000; i++) {\n            efficient.append(\"x\");  // Modifies existing buffer\n        }\n        \n        System.out.println(\"Both methods create same result, but different memory usage\");\n        System.out.println(\"Inefficient length: \" + inefficient.length());\n        System.out.println(\"Efficient length: \" + efficient.length());\n    }\n}