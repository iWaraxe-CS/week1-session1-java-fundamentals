package com.coherentsolutions.session1.antipatterns.stringcomparison;

import com.coherentsolutions.session1.domain.User;

import java.util.Objects;

/**
 * CORRECT PATTERN: String Comparison in Java
 * 
 * This class demonstrates the CORRECT way to compare strings in Java.
 * Key principles:
 * 1. Use .equals() for value comparison
 * 2. Use Objects.equals() for null-safe comparison
 * 3. Use .equalsIgnoreCase() for case-insensitive comparison
 * 4. Always handle null values properly
 */
public class StringComparisonCorrect {
    
    public static void main(String[] args) {
        demonstrateCorrectStringComparison();
    }
    
    public static void demonstrateCorrectStringComparison() {
        System.out.println("=== CORRECT STRING COMPARISON PATTERNS ===");
        
        // SOLUTION 1: Using .equals() for string comparison
        System.out.println("\n1. Using .equals() for string comparison:");
        String name1 = "John";
        String name2 = new String("John");
        
        // ✅ CORRECT: This compares values, not references
        if (name1.equals(name2)) {
            System.out.println("Names are equal (using .equals())"); // This will execute!
        } else {
            System.out.println("Names are NOT equal (using .equals())");
        }
        
        // SOLUTION 2: Using Objects.equals() for null-safe comparison
        System.out.println("\n2. Null-safe string comparison:");
        String nullName = null;
        String actualName = "John";
        
        // ✅ CORRECT: This handles null values gracefully
        if (Objects.equals(nullName, actualName)) {
            System.out.println("Names match");
        } else {
            System.out.println("Names don't match (null-safe)"); // This will execute!
        }
        
        // SOLUTION 3: Case-insensitive comparison
        System.out.println("\n3. Case-insensitive comparison:");
        String email1 = "john@example.com";
        String email2 = "JOHN@EXAMPLE.COM";
        
        // ✅ CORRECT: Using equalsIgnoreCase for case-insensitive comparison
        if (email1.equalsIgnoreCase(email2)) {
            System.out.println("Emails match (case-insensitive)"); // This will execute!
        } else {
            System.out.println("Emails don't match");
        }
        
        // SOLUTION 4: Proper user validation
        System.out.println("\n4. Proper user validation:");
        User user1 = User.builder().email("john@example.com").build();
        User user2 = User.builder().email("john@example.com").build();
        
        // ✅ CORRECT: Using .equals() for email comparison
        if (Objects.equals(user1.getEmail(), user2.getEmail())) {
            System.out.println("Same user email"); // This will execute!
        } else {
            System.out.println("Different users");
        }
        
        // SOLUTION 5: String interning demonstration
        System.out.println("\n5. String interning (when == works):");
        String literal1 = "Hello";
        String literal2 = "Hello";
        
        // ✅ INFO: String literals are interned, so == works here
        if (literal1 == literal2) {
            System.out.println("String literals are the same reference"); // This will execute!
        }
        
        // But still use .equals() for consistency
        if (literal1.equals(literal2)) {
            System.out.println("String literals have the same value"); // This will execute!
        }
    }
    
    // ✅ CORRECT: This method handles string comparison properly
    public static boolean isValidUser(String username, String expectedUsername) {
        // Handle null cases first
        if (username == null || expectedUsername == null) {
            return false;
        }
        
        // Use .equals() for value comparison
        return username.equals(expectedUsername);
    }
    
    // ✅ CORRECT: Alternative null-safe approach
    public static boolean isValidUserNullSafe(String username, String expectedUsername) {
        // Objects.equals() handles null values automatically
        return Objects.equals(username, expectedUsername);
    }
    
    // ✅ CORRECT: This email validation handles all cases
    public static boolean isEmailValid(String email) {
        String validEmail = "admin@example.com";
        
        // Handle null case
        if (email == null) {
            return false;
        }
        
        // Use equalsIgnoreCase for case-insensitive comparison
        return email.equalsIgnoreCase(validEmail);
    }
    
    // ✅ CORRECT: This method uses proper string comparison
    public static String findUserByEmail(String targetEmail) {
        String[] emails = {"john@example.com", "jane@example.com", "admin@example.com"};
        
        if (targetEmail == null) {
            return null;
        }
        
        for (String email : emails) {
            // Use equalsIgnoreCase for case-insensitive email comparison
            if (email.equalsIgnoreCase(targetEmail)) {
                return email;
            }
        }
        
        return null;
    }
    
    // ✅ CORRECT: Advanced string comparison utilities
    public static class StringComparisonUtils {
        
        // Null-safe equals
        public static boolean equals(String str1, String str2) {
            return Objects.equals(str1, str2);
        }
        
        // Null-safe case-insensitive equals
        public static boolean equalsIgnoreCase(String str1, String str2) {
            if (str1 == null && str2 == null) {
                return true;
            }
            if (str1 == null || str2 == null) {
                return false;
            }
            return str1.equalsIgnoreCase(str2);
        }
        
        // Safe string comparison with trimming
        public static boolean equalsTrimmed(String str1, String str2) {
            String trimmed1 = str1 != null ? str1.trim() : null;
            String trimmed2 = str2 != null ? str2.trim() : null;
            return Objects.equals(trimmed1, trimmed2);
        }
        
        // Check if string is null or empty
        public static boolean isNullOrEmpty(String str) {
            return str == null || str.isEmpty();
        }
        
        // Check if string is null, empty, or whitespace
        public static boolean isNullOrBlank(String str) {
            return str == null || str.trim().isEmpty();
        }
    }
}