package com.coherentsolutions.session1.antipatterns.stringcomparison;

import com.coherentsolutions.session1.domain.User;

/**
 * ANTI-PATTERN: String Comparison in Java
 * 
 * This class demonstrates the WRONG way to compare strings in Java.
 * C# developers often make this mistake because in C#, the == operator
 * is overloaded for strings to compare values, not references.
 * 
 * In Java, == ALWAYS compares references, not values!
 */
public class StringComparisonAntiPattern {
    
    public static void main(String[] args) {
        demonstrateStringComparisonProblems();
    }
    
    public static void demonstrateStringComparisonProblems() {
        System.out.println("=== STRING COMPARISON ANTI-PATTERNS ===");
        
        // PROBLEM 1: Using == for string comparison
        System.out.println("\n1. Using == for string comparison:");
        String name1 = "John";
        String name2 = new String("John");
        
        // ❌ WRONG: This compares references, not values
        if (name1 == name2) {
            System.out.println("Names are equal (using ==)");
        } else {
            System.out.println("Names are NOT equal (using ==)"); // This will execute!
        }
        
        // PROBLEM 2: Not handling null values properly
        System.out.println("\n2. Not handling null values:");
        String nullName = null;
        String actualName = "John";
        
        try {
            // ❌ WRONG: This will throw NullPointerException
            if (nullName.equals(actualName)) {
                System.out.println("Names match");
            }
        } catch (NullPointerException e) {
            System.out.println("Oops! NullPointerException occurred");
        }
        
        // PROBLEM 3: Case sensitivity issues
        System.out.println("\n3. Case sensitivity issues:");
        String email1 = "john@example.com";
        String email2 = "JOHN@EXAMPLE.COM";
        
        // ❌ WRONG: Emails should be case-insensitive but this won't work
        if (email1.equals(email2)) {
            System.out.println("Emails match");
        } else {
            System.out.println("Emails don't match (case sensitive)"); // This will execute!
        }
        
        // PROBLEM 4: Inefficient user validation
        System.out.println("\n4. Inefficient user validation:");
        User user1 = User.builder().email("john@example.com").build();
        User user2 = User.builder().email("john@example.com").build();
        
        // ❌ WRONG: This doesn't work for email comparison
        if (user1.getEmail() == user2.getEmail()) {
            System.out.println("Same user");
        } else {
            System.out.println("Different users"); // This will execute!
        }
    }
    
    // ❌ WRONG: This method has multiple string comparison issues
    public static boolean isValidUser(String username, String expectedUsername) {
        // Problem: null check is after the comparison
        if (username == expectedUsername) {
            return true;
        }
        
        // Problem: This will throw NullPointerException if username is null
        if (username.length() > 0 && username == expectedUsername) {
            return true;
        }
        
        return false;
    }
    
    // ❌ WRONG: This email validation is problematic
    public static boolean isEmailValid(String email) {
        String validEmail = "admin@example.com";
        
        // Problem: Case sensitive comparison
        // Problem: Using == instead of equals
        return email == validEmail;
    }
    
    // ❌ WRONG: This method doesn't handle string comparison properly
    public static String findUserByEmail(String targetEmail) {
        String[] emails = {"john@example.com", "jane@example.com", "admin@example.com"};
        
        for (String email : emails) {
            // Problem: Using == for string comparison
            if (email == targetEmail) {
                return email;
            }
        }
        
        return null;
    }
}