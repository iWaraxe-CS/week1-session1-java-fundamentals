package com.coherentsolutions.session1.exercises.product;

import java.math.BigDecimal;
import java.util.List;

/**
 * EXERCISE 1.1: Syntax Translation (5 min)
 * 
 * Convert this C# code to Java:
 * 
 * public class Product
 * {
 *     public int Id { get; init; }
 *     public string Name { get; set; }
 *     public decimal Price { get; set; }
 *     public List<string> Tags { get; } = new();
 *     
 *     public bool IsExpensive => Price > 100m;
 *     
 *     public override string ToString() => $"{Name}: ${Price}";
 * }
 * 
 * EXPECTED DISCUSSION POINTS:
 * - No init-only properties in Java
 * - BigDecimal vs decimal
 * - String formatting differences
 * - Computed properties → methods
 * 
 * INSTRUCTIONS:
 * 1. Convert C# properties to Java fields with getters/setters
 * 2. Change 'decimal' to 'BigDecimal'
 * 3. Convert computed property to method
 * 4. Fix string formatting in toString()
 * 5. Handle collection initialization properly
 * 6. Consider using Lombok annotations for cleaner code
 */
public class ProductExercise {
    
    // TODO: Convert C# properties to Java fields
    // C#: public int Id { get; init; }
    // Java: ???
    
    // TODO: Convert C# string to Java String
    // C#: public string Name { get; set; }
    // Java: ???
    
    // TODO: Convert C# decimal to Java BigDecimal  
    // C#: public decimal Price { get; set; }
    // Java: ???
    
    // TODO: Convert C# collection property
    // C#: public List<string> Tags { get; } = new();
    // Java: ???
    
    // TODO: Convert C# computed property to Java method
    // C#: public bool IsExpensive => Price > 100m;
    // Java: ???
    
    // TODO: Convert C# string interpolation to Java string formatting
    // C#: public override string ToString() => $"{Name}: ${Price}";
    // Java: ???
    
    public static void main(String[] args) {
        // TODO: Test your Product class here
        System.out.println(\"=== PRODUCT EXERCISE ===\");\n        \n        // Create a product instance\n        // Set some properties\n        // Test the isExpensive method\n        // Print the product using toString\n        \n        System.out.println(\"Exercise completed! Check ProductSolution.java for the answer.\");
    }
}