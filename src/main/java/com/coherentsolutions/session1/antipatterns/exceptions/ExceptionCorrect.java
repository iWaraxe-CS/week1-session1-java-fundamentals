package com.coherentsolutions.session1.antipatterns.exceptions;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * CORRECT PATTERN: Exception Handling in Java
 * 
 * This class demonstrates the CORRECT way to handle exceptions in Java.
 * Key principles:
 * 1. Handle checked exceptions appropriately
 * 2. Use try-with-resources for automatic resource management
 * 3. Provide specific exception handling for different exception types
 * 4. Translate exceptions appropriately
 * 5. Log exceptions properly
 * 6. Validate input parameters
 */
public class ExceptionCorrect {
    
    private static final Logger logger = Logger.getLogger(ExceptionCorrect.class.getName());
    
    public static void main(String[] args) {
        demonstrateCorrectExceptionHandling();
    }
    
    public static void demonstrateCorrectExceptionHandling() {
        System.out.println("=== CORRECT EXCEPTION HANDLING PATTERNS ===");
        
        // SOLUTION 1: Proper checked exception handling
        System.out.println("\n1. Proper checked exception handling:");
        try {
            String content = readFileCorrect("file.txt");
            System.out.println("File content: " + content);
        } catch (FileProcessingException e) {
            System.out.println("File processing failed: " + e.getMessage());
        }
        
        // SOLUTION 2: Using try-with-resources
        System.out.println("\n2. Using try-with-resources:");
        demonstrateProperResourceManagement();
        
        // SOLUTION 3: Specific exception handling
        System.out.println("\n3. Specific exception handling:");
        demonstrateSpecificExceptionHandling();
        
        // SOLUTION 4: Proper exception translation
        System.out.println("\n4. Proper exception translation:");
        demonstrateExceptionTranslation();
        
        // SOLUTION 5: Input validation
        System.out.println("\n5. Input validation:");
        demonstrateInputValidation();
    }
    
    // ✅ CORRECT: Custom business exception
    public static class FileProcessingException extends Exception {
        public FileProcessingException(String message) {
            super(message);
        }
        
        public FileProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    // ✅ CORRECT: Custom business exception for validation
    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
    
    // ✅ CORRECT: Method that handles checked exceptions properly
    public static String readFileCorrect(String path) throws FileProcessingException {
        // Input validation
        if (path == null || path.trim().isEmpty()) {
            throw new FileProcessingException("File path cannot be null or empty");
        }
        
        // ✅ CORRECT: Using try-with-resources
        try (FileReader reader = new FileReader(path)) {
            // Read file content
            return "file content from " + path;
        } catch (IOException e) {
            // ✅ CORRECT: Proper exception translation with cause
            logger.severe("Failed to read file: " + path + ", error: " + e.getMessage());
            throw new FileProcessingException("Unable to read file: " + path, e);
        }
    }
    
    public static void demonstrateProperResourceManagement() {
        String filePath = "example.txt";
        
        // ✅ CORRECT: try-with-resources automatically closes resources
        try (FileReader reader = new FileReader(filePath)) {
            // Process file
            System.out.println("Processing file: " + filePath);
        } catch (IOException e) {
            // ✅ CORRECT: Specific exception handling with logging
            logger.warning("Failed to process file: " + filePath + ", error: " + e.getMessage());
            System.out.println("Could not process file: " + e.getMessage());
        }
        // Resource is automatically closed, even if exception occurs
    }
    
    public static void demonstrateSpecificExceptionHandling() {
        try {
            processMultipleOperations();
        } catch (IOException e) {
            // ✅ CORRECT: Specific handling for IO issues
            logger.severe("IO operation failed: " + e.getMessage());
            System.out.println("File operation failed: " + e.getMessage());
        } catch (NumberFormatException e) {
            // ✅ CORRECT: Specific handling for number format issues
            logger.warning("Number format error: " + e.getMessage());
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // ✅ CORRECT: Specific handling for validation issues
            logger.warning("Validation error: " + e.getMessage());
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            // ✅ CORRECT: Generic handler as fallback
            logger.severe("Unexpected error: " + e.getMessage());
            System.out.println("Unexpected error occurred: " + e.getMessage());
        }
    }
    
    // ✅ CORRECT: Method with specific exception declarations
    public static void processMultipleOperations() throws IOException, NumberFormatException, IllegalArgumentException {
        // Different operations with specific exceptions
        // This method clearly declares what can go wrong
        throw new NumberFormatException("Invalid number format");
    }
    
    public static void demonstrateExceptionTranslation() {
        try {
            businessOperation();
        } catch (BusinessException e) {
            // ✅ CORRECT: Handling business-specific exception
            System.out.println("Business operation failed: " + e.getMessage());
        }
    }
    
    // ✅ CORRECT: Custom business exception
    public static class BusinessException extends Exception {
        public BusinessException(String message) {
            super(message);
        }
        
        public BusinessException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    // ✅ CORRECT: Business method that throws business exceptions
    public static void businessOperation() throws BusinessException {
        try {
            // Some low-level operation
            throw new IOException("File not found");
        } catch (IOException e) {
            // ✅ CORRECT: Translate to business exception
            throw new BusinessException("Failed to process user data", e);
        }
    }
    
    public static void demonstrateInputValidation() {
        try {
            processUsers(null);
        } catch (ValidationException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
    }
    
    // ✅ CORRECT: Method with proper input validation
    public static void processUsers(List<String> users) throws ValidationException {
        // ✅ CORRECT: Validate input parameters
        if (users == null) {
            throw new ValidationException("Users list cannot be null");
        }
        
        if (users.isEmpty()) {
            throw new ValidationException("Users list cannot be empty");
        }
        
        for (String user : users) {
            // ✅ CORRECT: Validate individual items
            if (user == null) {
                throw new ValidationException("User name cannot be null");
            }
            
            if (user.trim().isEmpty()) {
                throw new ValidationException("User name cannot be empty");
            }
            
            System.out.println("Processing: " + user.toUpperCase());
        }
    }
    
    // ✅ CORRECT: Method that catches and rethrows properly
    public static void operationWithProperRethrowing() throws BusinessException {
        try {
            riskyOperation();
        } catch (Exception e) {
            // ✅ CORRECT: Preserving original stack trace
            logger.severe("Operation failed: " + e.getMessage());
            throw new BusinessException("Operation failed", e);
        }
    }
    
    public static void riskyOperation() throws Exception {
        throw new Exception("Original error");
    }
    
    // ✅ CORRECT: Method that doesn't use exceptions for control flow
    public static boolean isValidNumber(String str) {
        // ✅ CORRECT: Input validation first
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        
        try {
            Integer.parseInt(str.trim());
            return true;
        } catch (NumberFormatException e) {
            // ✅ CORRECT: Exception handling, not control flow
            // Log if needed for debugging
            logger.fine("Invalid number format: " + str);
            return false;
        }
    }
    
    // ✅ CORRECT: Alternative approach without exceptions
    public static boolean isValidNumberNoException(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        
        String trimmed = str.trim();
        
        // Handle negative numbers
        if (trimmed.startsWith("-")) {
            trimmed = trimmed.substring(1);
        }
        
        // Check if all characters are digits
        return trimmed.chars().allMatch(Character::isDigit);
    }
    
    // ✅ CORRECT: Exception handling utility methods
    public static class ExceptionUtils {
        
        // Safe operation execution
        public static <T> T executeWithDefault(java.util.function.Supplier<T> operation, T defaultValue) {
            try {
                return operation.get();
            } catch (Exception e) {
                logger.warning("Operation failed, using default: " + e.getMessage());
                return defaultValue;
            }
        }
        
        // Safe operation execution with specific exception handling
        public static <T> T executeWithHandler(java.util.function.Supplier<T> operation, 
                                             java.util.function.Function<Exception, T> errorHandler) {
            try {
                return operation.get();
            } catch (Exception e) {
                logger.warning("Operation failed, using handler: " + e.getMessage());
                return errorHandler.apply(e);
            }
        }
        
        // Null-safe operation
        public static void executeIfNotNull(Object obj, Runnable operation) {
            Objects.requireNonNull(operation, "Operation cannot be null");
            
            if (obj != null) {
                operation.run();
            }
        }
        
        // Chain operations safely
        public static <T> T safeChain(T initial, java.util.function.Function<T, T>... operations) {
            T result = initial;
            
            for (java.util.function.Function<T, T> operation : operations) {
                if (result == null) {
                    break;
                }
                
                try {
                    result = operation.apply(result);
                } catch (Exception e) {
                    logger.warning("Operation in chain failed: " + e.getMessage());
                    return null;
                }
            }
            
            return result;
        }
    }
}