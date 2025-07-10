package com.coherentsolutions.session1.antipatterns.valuetypes;

import java.math.BigDecimal;

/**
 * ANTI-PATTERN: Value Types in Java
 * 
 * This class demonstrates the WRONG way to handle value types in Java.
 * C# developers are used to:
 * - Value types (struct) vs reference types (class)
 * - Automatic value semantics
 * - Built-in decimal type
 * - Non-nullable value types by default
 * 
 * Java doesn't have user-defined value types (before Project Valhalla).
 * Common mistakes C# developers make when transitioning.
 */
public class ValueTypeAntiPattern {
    
    public static void main(String[] args) {
        demonstrateValueTypeProblems();
    }
    
    public static void demonstrateValueTypeProblems() {
        System.out.println("=== VALUE TYPE ANTI-PATTERNS ===");
        
        // PROBLEM 1: Assuming primitives behave like C# value types
        System.out.println("\n1. Primitive wrapper confusion:");
        demonstratePrimitiveWrapperProblems();
        
        // PROBLEM 2: Using == for value comparison
        System.out.println("\n2. Using == for wrapper comparison:");
        demonstrateWrapperComparisonProblems();
        
        // PROBLEM 3: BigDecimal misuse
        System.out.println("\n3. BigDecimal misuse:");
        demonstrateBigDecimalProblems();
        
        // PROBLEM 4: Nullable value type confusion
        System.out.println("\n4. Nullable value type confusion:");
        demonstrateNullableProblems();
        
        // PROBLEM 5: Immutability misunderstanding
        System.out.println("\n5. Immutability misunderstanding:");
        demonstrateImmutabilityProblems();
    }
    
    public static void demonstratePrimitiveWrapperProblems() {
        // ❌ WRONG: Treating Integer like C# int
        
        System.out.println("C# int vs Java Integer confusion:");
        
        // This works fine with primitives
        int primitive1 = 100;
        int primitive2 = 100;
        System.out.println("primitive1 == primitive2: " + (primitive1 == primitive2)); // true
        
        // ❌ WRONG: Assuming same behavior with Integer
        Integer wrapper1 = 200;  // Auto-boxing
        Integer wrapper2 = 200;  // Auto-boxing
        System.out.println("wrapper1 == wrapper2: " + (wrapper1 == wrapper2)); // false! (above cache range)
        
        // ❌ WRONG: Not understanding Integer cache
        Integer cached1 = 100;   // Cached (-128 to 127)
        Integer cached2 = 100;   // Same cached instance
        System.out.println("cached1 == cached2: " + (cached1 == cached2)); // true (misleading!)
        
        System.out.println("This behavior confuses C# developers who expect consistent value semantics");
    }
    
    public static void demonstrateWrapperComparisonProblems() {
        // ❌ WRONG: Using == for wrapper comparison
        
        Double price1 = 29.99;
        Double price2 = new Double(29.99);
        
        System.out.println("price1 == price2: " + (price1 == price2)); // false!
        System.out.println("This is wrong - should use .equals()");
        
        // ❌ WRONG: Null pointer exception with auto-unboxing
        Integer nullInt = null;
        try {
            int result = nullInt + 5;  // NullPointerException!
        } catch (NullPointerException e) {
            System.out.println("NullPointerException when unboxing null Integer");
        }
        
        // ❌ WRONG: Inconsistent behavior with collections
        java.util.List<Integer> numbers = java.util.Arrays.asList(1, 2, 3);
        System.out.println("Contains 1: " + numbers.contains(1)); // true
        System.out.println("Contains new Integer(1): " + numbers.contains(new Integer(1))); // true (but uses equals)
        
        System.out.println("Collection behavior is different from direct comparison");
    }
    
    public static void demonstrateBigDecimalProblems() {
        // ❌ WRONG: Treating BigDecimal like C# decimal
        
        System.out.println("BigDecimal problems that confuse C# developers:");
        
        // ❌ WRONG: Using == for BigDecimal comparison
        BigDecimal amount1 = new BigDecimal("10.00");
        BigDecimal amount2 = new BigDecimal("10.0");
        
        System.out.println("amount1 == amount2: " + (amount1 == amount2)); // false
        System.out.println("amount1.equals(amount2): " + amount1.equals(amount2)); // false! (different scale)
        
        // ❌ WRONG: Not understanding scale differences
        System.out.println("10.00 vs 10.0 are not equal in BigDecimal!");
        
        // ❌ WRONG: Creating BigDecimal from double
        BigDecimal wrongWay = new BigDecimal(0.1);  // Imprecise!
        BigDecimal rightWay = new BigDecimal("0.1"); // Precise
        
        System.out.println("From double: " + wrongWay);
        System.out.println("From string: " + rightWay);
        System.out.println("Doubles have precision issues!");
        
        // ❌ WRONG: Not using proper comparison
        BigDecimal price = new BigDecimal("100.00");
        BigDecimal threshold = new BigDecimal("100");
        
        // This is wrong way to compare
        if (price.equals(threshold)) {
            System.out.println("Equal prices");
        } else {
            System.out.println("Different prices (because of scale!)");
        }
    }
    
    public static void demonstrateNullableProblems() {
        // ❌ WRONG: Not handling null wrapper types properly
        
        System.out.println("Nullable wrapper problems:");
        
        // C# nullable: int? nullableInt = null;
        // Java equivalent: Integer nullableInt = null;
        
        Integer nullableInt = null;
        
        // ❌ WRONG: Not checking for null before operations
        try {
            int doubled = nullableInt * 2;  // NullPointerException!
        } catch (NullPointerException e) {
            System.out.println("NPE when operating on null Integer");
        }
        
        // ❌ WRONG: Using wrong null-safe patterns
        Integer value = getValue(); // Might return null
        
        // Wrong way (C# style)
        if (value != null) {
            System.out.println("Value: " + value);
        }
        // This works but is not idiomatic Java
        
        System.out.println("Should use Optional<T> for null safety in modern Java");
    }
    
    public static void demonstrateImmutabilityProblems() {
        // ❌ WRONG: Not understanding immutability of wrapper types
        
        System.out.println("Immutability confusion:");
        
        Integer original = 100;
        Integer modified = original;
        
        // ❌ WRONG: Trying to modify immutable wrapper
        // This doesn't actually modify the original
        modified = modified + 50;
        
        System.out.println("Original: " + original);   // Still 100
        System.out.println("Modified: " + modified);   // 150
        
        System.out.println("Wrapper types are immutable - 'modification' creates new objects");
        
        // ❌ WRONG: Not understanding string immutability impact
        String str = "Hello";
        str.toUpperCase(); // Returns new string, doesn't modify original
        System.out.println("String after toUpperCase(): " + str); // Still "Hello"
        
        System.out.println("Forgot to assign result of toUpperCase()!");
    }
    
    // ❌ WRONG: Method that can return null without indication
    public static Integer getValue() {
        if (Math.random() > 0.5) {
            return 42;
        }
        return null; // No indication this can be null!
    }
}