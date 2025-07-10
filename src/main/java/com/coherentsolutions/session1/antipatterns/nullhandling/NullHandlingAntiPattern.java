package com.coherentsolutions.session1.antipatterns.nullhandling;

import com.coherentsolutions.session1.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
 * ANTI-PATTERN: Null Handling in Java
 * 
 * This class demonstrates the WRONG way to handle null values in Java.
 * C# developers are used to nullable reference types (User?) and 
 * null-conditional operators (user?.Name). Java traditionally uses
 * null return values, leading to NullPointerException issues.
 * 
 * Modern Java prefers Optional<T> for explicit null handling.
 */
public class NullHandlingAntiPattern {
    
    private static Map<Long, User> userMap = new HashMap<>();
    
    static {
        // Initialize with some test data
        userMap.put(1L, User.builder().id(1L).name("John").email("john@example.com").build());
        userMap.put(2L, User.builder().id(2L).name("Jane").email("jane@example.com").build());
    }
    
    public static void main(String[] args) {
        demonstrateNullHandlingProblems();
    }
    
    public static void demonstrateNullHandlingProblems() {
        System.out.println("=== NULL HANDLING ANTI-PATTERNS ===");
        
        // PROBLEM 1: Returning null instead of Optional
        System.out.println("\n1. Returning null (C# style):");
        User user = findUser(999L); // Non-existent user
        
        try {
            // ❌ WRONG: This will throw NullPointerException
            System.out.println("User name: " + user.getName());
        } catch (NullPointerException e) {
            System.out.println("Oops! NullPointerException occurred");
        }
        
        // PROBLEM 2: Inconsistent null checking
        System.out.println("\n2. Inconsistent null checking:");
        processUser(1L);  // Exists
        processUser(999L); // Doesn't exist
        
        // PROBLEM 3: Null propagation issues
        System.out.println("\n3. Null propagation issues:");
        String userEmail = getUserEmail(1L);
        System.out.println("User email: " + userEmail);
        
        String nullUserEmail = getUserEmail(999L);
        System.out.println("Null user email: " + nullUserEmail);
        
        // PROBLEM 4: Defensive programming gone wrong
        System.out.println("\n4. Defensive programming issues:");
        String result = processUserData(null);
        System.out.println("Result: " + result);
    }
    
    // ❌ WRONG: Returning null like C# methods
    public static User findUser(Long id) {
        // This returns null when user is not found
        // C# developers expect this, but it's problematic in Java
        return userMap.get(id);
    }
    
    // ❌ WRONG: Inconsistent null handling
    public static void processUser(Long id) {
        User user = findUser(id);
        
        // Sometimes we check for null...
        if (user != null) {
            System.out.println("Processing user: " + user.getName());
        } else {
            System.out.println("User not found");
        }
        
        // But sometimes we forget to check...
        // This could cause NullPointerException
        updateUserLastLogin(user);
    }
    
    // ❌ WRONG: This method doesn't handle null input
    public static void updateUserLastLogin(User user) {
        // No null check - will throw NullPointerException if user is null
        user.setLastLoginAt(java.time.LocalDateTime.now());
        System.out.println("Updated login time for: " + user.getName());
    }
    
    // ❌ WRONG: Returning null from a method that should return a value
    public static String getUserEmail(Long id) {
        User user = findUser(id);
        
        // This returns null if user doesn't exist
        // Caller has to remember to check for null
        return user != null ? user.getEmail() : null;
    }
    
    // ❌ WRONG: Defensive programming with poor error handling
    public static String processUserData(User user) {
        // Too many null checks make code hard to read
        if (user == null) {
            return null; // Or should we return empty string? Or throw exception?
        }
        
        if (user.getName() == null) {
            return null; // Inconsistent handling
        }
        
        if (user.getEmail() == null) {
            return ""; // Different handling for similar case
        }
        
        return user.getName() + " - " + user.getEmail();
    }
    
    // ❌ WRONG: Method that can return null or throw exception
    public static User createUser(String name, String email) {
        // What should happen if name is null?
        if (name == null) {
            return null; // Or should we throw an exception?
        }
        
        // What about email?
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        
        // Inconsistent error handling makes the API unpredictable
        return User.builder()
                   .name(name)
                   .email(email)
                   .build();
    }
    
    // ❌ WRONG: Trying to use C# null-conditional operator pattern
    public static String getUserDisplayName(Long id) {
        User user = findUser(id);
        
        // C# would be: user?.Name ?? "Unknown"
        // Java doesn't have null-conditional operator
        // This is verbose and error-prone
        if (user != null) {
            if (user.getName() != null) {
                return user.getName();
            }
        }
        return "Unknown";
    }
    
    // ❌ WRONG: Collection with null elements
    public static void processUsers() {
        Long[] userIds = {1L, 2L, 999L, null}; // Contains null!
        
        for (Long id : userIds) {
            // This will throw NullPointerException when id is null
            User user = findUser(id);
            if (user != null) {
                System.out.println("Processing: " + user.getName());
            }
        }
    }
}