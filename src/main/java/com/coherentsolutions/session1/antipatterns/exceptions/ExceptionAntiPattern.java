package com.coherentsolutions.session1.antipatterns.exceptions;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * ANTI-PATTERN: Exception Handling in Java
 * 
 * This class demonstrates the WRONG way to handle exceptions in Java.
 * C# developers are used to:
 * - Only unchecked exceptions (RuntimeException)
 * - try-catch-finally blocks
 * - using statements for resource management
 * 
 * Java has both checked and unchecked exceptions, and different resource management.
 */
public class ExceptionAntiPattern {
    
    public static void main(String[] args) {
        demonstrateExceptionProblems();
    }
    
    public static void demonstrateExceptionProblems() {
        System.out.println("=== EXCEPTION HANDLING ANTI-PATTERNS ===");
        
        // PROBLEM 1: Ignoring checked exceptions
        System.out.println("\n1. Ignoring checked exceptions:");
        try {
            // ❌ WRONG: This won't compile - FileReader throws IOException
            // FileReader reader = new FileReader("file.txt");
            System.out.println("FileReader would throw IOException");
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        }
        
        // PROBLEM 2: Swallowing exceptions
        System.out.println("\n2. Swallowing exceptions:");
        demonstrateSwallowingExceptions();
        
        // PROBLEM 3: Wrong resource management
        System.out.println("\n3. Wrong resource management:");
        demonstrateWrongResourceManagement();
        
        // PROBLEM 4: Generic exception handling
        System.out.println("\n4. Generic exception handling:");
        demonstrateGenericExceptionHandling();
        
        // PROBLEM 5: Exception translation issues
        System.out.println("\n5. Exception translation issues:");
        demonstrateExceptionTranslation();
    }
    
    public static void demonstrateSwallowingExceptions() {
        try {
            // ❌ WRONG: Swallowing exceptions like in C#
            String content = readFileWrong("nonexistent.txt");
            System.out.println("Content: " + content);
        } catch (Exception e) {
            // ❌ WRONG: Silently ignoring exception
            // This hides the real problem
        }
        
        System.out.println("File reading completed (but did it really?)");
    }
    
    // ❌ WRONG: Method that doesn't handle checked exceptions properly
    public static String readFileWrong(String path) {
        FileReader reader = null;
        try {
            reader = new FileReader(path);
            // Read file content
            return "file content";
        } catch (IOException e) {
            // ❌ WRONG: Swallowing the exception
            return null;
        } finally {
            // ❌ WRONG: Not properly closing resources
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ❌ WRONG: Ignoring close exception
                }
            }
        }
    }
    
    public static void demonstrateWrongResourceManagement() {
        // ❌ WRONG: Trying to use C# 'using' statement pattern
        FileReader reader = null;
        try {
            reader = new FileReader("file.txt");
            // Process file
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // ❌ WRONG: Manual resource cleanup (error-prone)
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // What to do here?
                }
            }
        }
    }
    
    public static void demonstrateGenericExceptionHandling() {
        try {
            processMultipleOperations();
        } catch (Exception e) {
            // ❌ WRONG: Catching generic Exception
            // Doesn't provide specific handling for different exception types
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
    
    // ❌ WRONG: Method that throws generic Exception
    public static void processMultipleOperations() throws Exception {
        // Different operations that throw different exceptions
        // But method signature is too generic
        throw new Exception("Generic error");
    }
    
    public static void demonstrateExceptionTranslation() {
        try {
            // ❌ WRONG: Not translating low-level exceptions to business exceptions
            businessOperation();
        } catch (IOException e) {
            // ❌ WRONG: Exposing implementation details
            throw new RuntimeException("File error: " + e.getMessage());
        }
    }
    
    // ❌ WRONG: Business method that exposes implementation details
    public static void businessOperation() throws IOException {
        // This is a business operation, but it throws IOException
        // Client code has to know about file operations
        throw new IOException("File not found");
    }
    
    // ❌ WRONG: Method that doesn't validate input
    public static void processUsers(List<String> users) {
        // ❌ WRONG: No null check
        for (String user : users) {
            // ❌ WRONG: No null check for individual users
            System.out.println("Processing: " + user.toUpperCase());
        }
    }
    
    // ❌ WRONG: Method that catches and rethrows incorrectly
    public static void operationWithBadRethrowing() {
        try {
            riskyOperation();
        } catch (Exception e) {
            // ❌ WRONG: Losing original stack trace
            throw new RuntimeException("Operation failed");
        }
    }
    
    public static void riskyOperation() throws Exception {
        throw new Exception("Original error");
    }
    
    // ❌ WRONG: Method that uses exceptions for control flow
    public static boolean isValidNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            // ❌ WRONG: Using exceptions for control flow
            return false;
        }
    }
    
    // ❌ WRONG: Method that doesn't follow exception naming conventions
    public static void methodThatFailsSilently() {
        try {
            // Some operation
            throw new RuntimeException("Error");
        } catch (RuntimeException e) {
            // ❌ WRONG: Failing silently
            // No logging, no rethrowing, no handling
        }
    }
}