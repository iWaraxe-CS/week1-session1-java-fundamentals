package com.coherentsolutions.session1.antipatterns.nullhandling;

import com.coherentsolutions.session1.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * CORRECT PATTERN: Null Handling with Optional in Java
 * 
 * This class demonstrates the CORRECT way to handle null values in Java.
 * Key principles:
 * 1. Use Optional<T> for methods that might not return a value
 * 2. Use Optional.ofNullable() for potentially null values
 * 3. Use Optional chaining methods (map, flatMap, orElse, etc.)
 * 4. Make null handling explicit and consistent
 */
public class NullHandlingCorrect {
    
    private static Map<Long, User> userMap = new HashMap<>();
    
    static {
        // Initialize with some test data
        userMap.put(1L, User.builder().id(1L).name("John").email("john@example.com").build());
        userMap.put(2L, User.builder().id(2L).name("Jane").email("jane@example.com").build());
    }
    
    public static void main(String[] args) {
        demonstrateCorrectNullHandling();
    }
    
    public static void demonstrateCorrectNullHandling() {
        System.out.println("=== CORRECT NULL HANDLING PATTERNS ===");
        
        // SOLUTION 1: Using Optional instead of null
        System.out.println("\n1. Using Optional instead of null:");
        Optional<User> userOpt = findUser(999L); // Non-existent user
        
        // ✅ CORRECT: Safe handling with Optional
        userOpt.ifPresentOrElse(
            user -> System.out.println("User name: " + user.getName()),
            () -> System.out.println("User not found")
        );
        
        // SOLUTION 2: Consistent Optional handling
        System.out.println("\n2. Consistent Optional handling:");
        processUser(1L);  // Exists
        processUser(999L); // Doesn't exist
        
        // SOLUTION 3: Optional chaining
        System.out.println("\n3. Optional chaining:");
        String userEmail = getUserEmail(1L);
        System.out.println("User email: " + userEmail);
        
        String nullUserEmail = getUserEmail(999L);
        System.out.println("Null user email: " + nullUserEmail);
        
        // SOLUTION 4: Proper functional programming with Optional
        System.out.println("\n4. Functional programming with Optional:");
        String result = processUserData(1L);
        System.out.println("Result: " + result);
        
        String resultForMissing = processUserData(999L);
        System.out.println("Result for missing: " + resultForMissing);
    }
    
    // ✅ CORRECT: Using Optional to indicate possible absence
    public static Optional<User> findUser(Long id) {
        // Optional.ofNullable handles null values gracefully
        return Optional.ofNullable(userMap.get(id));
    }
    
    // ✅ CORRECT: Consistent Optional handling
    public static void processUser(Long id) {
        findUser(id)
            .ifPresentOrElse(
                user -> {
                    System.out.println("Processing user: " + user.getName());
                    updateUserLastLogin(user);
                },
                () -> System.out.println("User not found")
            );
    }
    
    // ✅ CORRECT: Method that safely handles User objects
    public static void updateUserLastLogin(User user) {
        // Input validation - fail fast with clear error
        Objects.requireNonNull(user, "User cannot be null");
        
        user.setLastLoginAt(java.time.LocalDateTime.now());
        System.out.println("Updated login time for: " + user.getName());
    }
    
    // ✅ CORRECT: Using Optional chaining
    public static String getUserEmail(Long id) {
        return findUser(id)
            .map(User::getEmail)
            .orElse("unknown@example.com");
    }
    
    // ✅ CORRECT: Using Optional for functional composition
    public static String processUserData(Long id) {
        return findUser(id)
            .filter(user -> user.getName() != null && user.getEmail() != null)
            .map(user -> user.getName() + " - " + user.getEmail())
            .orElse("User data not available");
    }
    
    // ✅ CORRECT: Clear input validation and consistent error handling
    public static User createUser(String name, String email) {
        // Use Objects.requireNonNull for mandatory parameters
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(email, "Email cannot be null");
        
        // Additional validation if needed
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        return User.builder()
                   .name(name)
                   .email(email)
                   .build();
    }
    
    // ✅ CORRECT: Java equivalent of C# null-conditional operator
    public static String getUserDisplayName(Long id) {
        return findUser(id)
            .map(User::getName)
            .orElse("Unknown");
    }
    
    // ✅ CORRECT: Advanced Optional usage
    public static Optional<String> getUserEmailOptional(Long id) {
        return findUser(id)
            .map(User::getEmail)
            .filter(email -> !email.isEmpty());
    }
    
    // ✅ CORRECT: Combining multiple Optionals
    public static Optional<String> getUserFullProfile(Long id) {
        return findUser(id)
            .flatMap(user -> {
                String name = user.getName();
                String email = user.getEmail();
                
                if (name != null && email != null) {
                    return Optional.of(name + " <" + email + ">");
                }
                return Optional.empty();
            });
    }
    
    // ✅ CORRECT: Safe collection processing
    public static void processUsers() {
        Long[] userIds = {1L, 2L, 999L, null}; // Contains null!
        
        for (Long id : userIds) {
            // Handle null id gracefully
            Optional.ofNullable(id)
                .flatMap(NullHandlingCorrect::findUser)
                .ifPresentOrElse(
                    user -> System.out.println("Processing: " + user.getName()),
                    () -> System.out.println("Skipping null or non-existent user")
                );
        }
    }
    
    // ✅ CORRECT: Utility methods for common Optional patterns
    public static class OptionalUtils {
        
        // Convert null to empty Optional
        public static <T> Optional<T> ofNullable(T value) {
            return Optional.ofNullable(value);
        }
        
        // Get value or default
        public static <T> T getOrDefault(Optional<T> optional, T defaultValue) {
            return optional.orElse(defaultValue);
        }
        
        // Get value or compute default
        public static <T> T getOrCompute(Optional<T> optional, java.util.function.Supplier<T> supplier) {
            return optional.orElseGet(supplier);
        }
        
        // Get value or throw custom exception
        public static <T> T getOrThrow(Optional<T> optional, RuntimeException exception) {
            return optional.orElseThrow(() -> exception);
        }
        
        // Chain multiple Optional operations
        public static <T, U> Optional<U> mapIfPresent(Optional<T> optional, 
                                                     java.util.function.Function<T, U> mapper) {
            return optional.map(mapper);
        }
        
        // Filter and map in one operation
        public static <T, U> Optional<U> filterAndMap(Optional<T> optional,
                                                     java.util.function.Predicate<T> predicate,
                                                     java.util.function.Function<T, U> mapper) {
            return optional.filter(predicate).map(mapper);
        }
    }
}