package com.coherentsolutions.session1.exercises.userservice;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

/**
 * USER SERVICE EXERCISE: Building a Complete Service Layer
 * 
 * This exercise demonstrates building a complete service layer following
 * Java best practices and Spring conventions.
 * 
 * LEARNING OBJECTIVES:
 * 1. Implement service layer with proper separation of concerns
 * 2. Handle validation, exceptions, and business logic
 * 3. Use Java Optional for null safety
 * 4. Implement repository pattern
 * 5. Practice dependency injection patterns
 * 6. Learn testing strategies for service layer
 * 
 * INSTRUCTIONS:
 * 1. Implement the missing methods in UserService
 * 2. Create proper exception handling
 * 3. Add validation logic
 * 4. Implement the repository interface
 * 5. Add business logic for user operations
 * 6. Run the main method to test your implementation
 * 7. Compare with UserServiceSolution.java
 * 
 * BUSINESS REQUIREMENTS:
 * - Users must have unique email addresses
 * - Passwords must be at least 8 characters with special characters
 * - Users can be activated/deactivated
 * - Track user creation and last login dates
 * - Support user roles (ADMIN, USER, GUEST)
 * - Email validation must be implemented
 * - Soft delete support (mark as deleted instead of removing)
 */
public class UserServiceExercise {
    
    public static void main(String[] args) {
        System.out.println("=== USER SERVICE EXERCISE ===");
        
        // Create the service with in-memory repository
        UserRepository repository = new InMemoryUserRepository();
        UserService userService = new UserServiceImpl(repository);
        
        // Test the implementation
        testUserService(userService);
        
        System.out.println("\n=== EXERCISE COMPLETED ===");
        System.out.println("Check UserServiceSolution.java for complete implementation");
    }
    
    public static void testUserService(UserService userService) {
        try {
            System.out.println("\n--- Testing User Service ---");
            
            // Test 1: Create users
            System.out.println("\n1. Creating users:");
            CreateUserRequest request1 = new CreateUserRequest("john.doe@example.com", "SecurePass123!", "John", "Doe");
            CreateUserRequest request2 = new CreateUserRequest("jane.smith@example.com", "MyPassword456@", "Jane", "Smith");
            
            User user1 = userService.createUser(request1);
            User user2 = userService.createUser(request2);
            
            System.out.println("Created user 1: " + user1.getEmail());
            System.out.println("Created user 2: " + user2.getEmail());
            
            // Test 2: Find users
            System.out.println("\n2. Finding users:");
            Optional<User> foundUser = userService.findUserByEmail("john.doe@example.com");
            if (foundUser.isPresent()) {
                System.out.println("Found user: " + foundUser.get().getFullName());
            }
            
            // Test 3: List all users
            System.out.println("\n3. Listing all users:");
            List<User> allUsers = userService.getAllUsers();
            allUsers.forEach(user -> System.out.println("- " + user.getFullName() + " (" + user.getEmail() + ")"));
            
            // Test 4: Update user
            System.out.println("\n4. Updating user:");
            UpdateUserRequest updateRequest = new UpdateUserRequest(user1.getId(), "John", "Updated", UserRole.ADMIN);
            User updatedUser = userService.updateUser(updateRequest);
            System.out.println("Updated user: " + updatedUser.getFullName() + " - Role: " + updatedUser.getRole());
            
            // Test 5: Activate/Deactivate user
            System.out.println("\n5. User activation:");
            userService.deactivateUser(user2.getId());
            System.out.println("User 2 deactivated");
            
            userService.activateUser(user2.getId());
            System.out.println("User 2 reactivated");
            
            // Test 6: Authentication
            System.out.println("\n6. Authentication test:");
            boolean authenticated = userService.authenticate("john.doe@example.com", "SecurePass123!");
            System.out.println("Authentication result: " + authenticated);
            
            // Test 7: Role-based operations
            System.out.println("\n7. Role-based operations:");
            List<User> adminUsers = userService.getUsersByRole(UserRole.ADMIN);
            System.out.println("Admin users: " + adminUsers.size());
            
            // Test 8: Error handling
            System.out.println("\n8. Error handling:");
            try {
                userService.createUser(new CreateUserRequest("invalid-email", "weak", "Test", "User"));
            } catch (Exception e) {
                System.out.println("Caught expected error: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Domain Models
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private Long id;
        private String email;
        private String passwordHash;
        private String firstName;
        private String lastName;
        private UserRole role;
        private UserStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime lastLoginAt;
        private LocalDateTime updatedAt;
        private boolean deleted;
        
        public String getFullName() {
            return firstName + " " + lastName;
        }
        
        public boolean isActive() {
            return status == UserStatus.ACTIVE;
        }
    }
    
    public enum UserRole {
        ADMIN, USER, GUEST
    }
    
    public enum UserStatus {
        ACTIVE, INACTIVE, SUSPENDED
    }
    
    // Request/Response DTOs
    @Data
    @AllArgsConstructor
    public static class CreateUserRequest {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
    }
    
    @Data
    @AllArgsConstructor
    public static class UpdateUserRequest {
        private Long id;
        private String firstName;
        private String lastName;
        private UserRole role;
    }
    
    // Repository Interface
    public interface UserRepository {
        User save(User user);
        Optional<User> findById(Long id);
        Optional<User> findByEmail(String email);
        List<User> findAll();
        List<User> findByRole(UserRole role);
        List<User> findByStatus(UserStatus status);
        void deleteById(Long id);
        boolean existsByEmail(String email);
    }
    
    // Service Interface
    public interface UserService {
        User createUser(CreateUserRequest request);
        Optional<User> findUserById(Long id);
        Optional<User> findUserByEmail(String email);
        List<User> getAllUsers();
        List<User> getUsersByRole(UserRole role);
        List<User> getActiveUsers();
        User updateUser(UpdateUserRequest request);
        void activateUser(Long id);
        void deactivateUser(Long id);
        void deleteUser(Long id);
        boolean authenticate(String email, String password);
        void updateLastLogin(Long id);
    }
    
    // TODO: Implement the UserServiceImpl class
    public static class UserServiceImpl implements UserService {
        private final UserRepository userRepository;
        
        public UserServiceImpl(UserRepository userRepository) {
            this.userRepository = userRepository;
        }
        
        @Override
        public User createUser(CreateUserRequest request) {
            // TODO: Implement user creation
            // 1. Validate email format
            // 2. Validate password strength
            // 3. Check if email already exists
            // 4. Hash password
            // 5. Create user with default values
            // 6. Save and return user
            
            throw new UnsupportedOperationException("TODO: Implement createUser method");
        }
        
        @Override
        public Optional<User> findUserById(Long id) {
            // TODO: Implement find by ID
            // 1. Validate ID is not null
            // 2. Find user by ID
            // 3. Return empty if user is deleted
            
            throw new UnsupportedOperationException("TODO: Implement findUserById method");
        }
        
        @Override
        public Optional<User> findUserByEmail(String email) {
            // TODO: Implement find by email
            // 1. Validate email is not null/empty
            // 2. Find user by email
            // 3. Return empty if user is deleted
            
            throw new UnsupportedOperationException("TODO: Implement findUserByEmail method");
        }
        
        @Override
        public List<User> getAllUsers() {
            // TODO: Implement get all users
            // 1. Get all users from repository
            // 2. Filter out deleted users
            // 3. Return list
            
            throw new UnsupportedOperationException("TODO: Implement getAllUsers method");
        }
        
        @Override
        public List<User> getUsersByRole(UserRole role) {
            // TODO: Implement get users by role
            // 1. Validate role is not null
            // 2. Get users by role
            // 3. Filter out deleted users
            
            throw new UnsupportedOperationException("TODO: Implement getUsersByRole method");
        }
        
        @Override
        public List<User> getActiveUsers() {
            // TODO: Implement get active users
            // 1. Get users with ACTIVE status
            // 2. Filter out deleted users
            
            throw new UnsupportedOperationException("TODO: Implement getActiveUsers method");
        }
        
        @Override
        public User updateUser(UpdateUserRequest request) {
            // TODO: Implement user update
            // 1. Validate request
            // 2. Find existing user
            // 3. Update fields
            // 4. Set updated timestamp
            // 5. Save and return user
            
            throw new UnsupportedOperationException("TODO: Implement updateUser method");
        }
        
        @Override
        public void activateUser(Long id) {
            // TODO: Implement user activation
            // 1. Find user by ID
            // 2. Set status to ACTIVE
            // 3. Save user
            
            throw new UnsupportedOperationException("TODO: Implement activateUser method");
        }
        
        @Override
        public void deactivateUser(Long id) {
            // TODO: Implement user deactivation
            // 1. Find user by ID
            // 2. Set status to INACTIVE
            // 3. Save user
            
            throw new UnsupportedOperationException("TODO: Implement deactivateUser method");
        }
        
        @Override
        public void deleteUser(Long id) {
            // TODO: Implement soft delete
            // 1. Find user by ID
            // 2. Set deleted flag to true
            // 3. Set status to INACTIVE
            // 4. Save user
            
            throw new UnsupportedOperationException("TODO: Implement deleteUser method");
        }
        
        @Override
        public boolean authenticate(String email, String password) {
            // TODO: Implement authentication
            // 1. Find user by email
            // 2. Check if user is active
            // 3. Verify password hash
            // 4. Update last login if successful
            
            throw new UnsupportedOperationException("TODO: Implement authenticate method");
        }
        
        @Override
        public void updateLastLogin(Long id) {
            // TODO: Implement last login update
            // 1. Find user by ID
            // 2. Set lastLoginAt to current time
            // 3. Save user
            
            throw new UnsupportedOperationException("TODO: Implement updateLastLogin method");
        }
        
        // TODO: Implement helper methods
        
        private boolean isValidEmail(String email) {
            // TODO: Implement email validation
            // Use regex or simple validation
            return false;
        }
        
        private boolean isValidPassword(String password) {
            // TODO: Implement password validation
            // Check length, special characters, etc.
            return false;
        }
        
        private String hashPassword(String password) {
            // TODO: Implement password hashing
            // For exercise purposes, use simple hashing
            return password;
        }
        
        private boolean verifyPassword(String password, String hash) {
            // TODO: Implement password verification
            // For exercise purposes, use simple comparison
            return false;
        }
        
        private void validateCreateRequest(CreateUserRequest request) {
            // TODO: Implement request validation
            // Check for null values, validate fields
        }
        
        private void validateUpdateRequest(UpdateUserRequest request) {
            // TODO: Implement request validation
            // Check for null values, validate fields
        }
    }
    
    // TODO: Implement the InMemoryUserRepository class
    public static class InMemoryUserRepository implements UserRepository {
        private final Map<Long, User> users = new HashMap<>();
        private Long nextId = 1L;
        
        @Override
        public User save(User user) {
            // TODO: Implement save method
            // 1. If user.id is null, generate new ID
            // 2. Store user in map
            // 3. Return user
            
            throw new UnsupportedOperationException("TODO: Implement save method");
        }
        
        @Override
        public Optional<User> findById(Long id) {
            // TODO: Implement find by ID
            // 1. Get user from map
            // 2. Return Optional
            
            throw new UnsupportedOperationException("TODO: Implement findById method");
        }
        
        @Override
        public Optional<User> findByEmail(String email) {
            // TODO: Implement find by email
            // 1. Search through users map
            // 2. Find user with matching email
            // 3. Return Optional
            
            throw new UnsupportedOperationException("TODO: Implement findByEmail method");
        }
        
        @Override
        public List<User> findAll() {
            // TODO: Implement find all
            // 1. Return all users from map as list
            
            throw new UnsupportedOperationException("TODO: Implement findAll method");
        }
        
        @Override
        public List<User> findByRole(UserRole role) {
            // TODO: Implement find by role
            // 1. Filter users by role
            // 2. Return matching users
            
            throw new UnsupportedOperationException("TODO: Implement findByRole method");
        }
        
        @Override
        public List<User> findByStatus(UserStatus status) {
            // TODO: Implement find by status
            // 1. Filter users by status
            // 2. Return matching users
            
            throw new UnsupportedOperationException("TODO: Implement findByStatus method");
        }
        
        @Override
        public void deleteById(Long id) {
            // TODO: Implement delete by ID
            // 1. Remove user from map
            
            throw new UnsupportedOperationException("TODO: Implement deleteById method");
        }
        
        @Override
        public boolean existsByEmail(String email) {
            // TODO: Implement exists by email
            // 1. Check if any user has the given email
            
            throw new UnsupportedOperationException("TODO: Implement existsByEmail method");
        }
    }
    
    // Custom Exceptions
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class UserValidationException extends RuntimeException {
        public UserValidationException(String message) {
            super(message);
        }
    }
    
    public static class DuplicateEmailException extends RuntimeException {
        public DuplicateEmailException(String message) {
            super(message);
        }
    }
    
    /**
     * EXERCISE HINTS:
     * 
     * 1. EMAIL VALIDATION:
     *    - Use regex pattern or simple contains("@") check
     *    - Pattern: ^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$
     * 
     * 2. PASSWORD VALIDATION:
     *    - Minimum 8 characters
     *    - At least one special character
     *    - At least one number
     * 
     * 3. PASSWORD HASHING:
     *    - For this exercise, use simple string manipulation
     *    - In real applications, use BCrypt or similar
     * 
     * 4. EXCEPTION HANDLING:
     *    - Throw UserValidationException for validation errors
     *    - Throw UserNotFoundException when user not found
     *    - Throw DuplicateEmailException for duplicate emails
     * 
     * 5. OPTIONAL USAGE:
     *    - Use Optional.empty() for not found cases
     *    - Use Optional.of() for found cases
     *    - Use orElseThrow() for required values
     * 
     * 6. TIMESTAMPS:
     *    - Use LocalDateTime.now() for current time
     *    - Set createdAt only during creation
     *    - Set updatedAt during updates
     * 
     * 7. SOFT DELETE:
     *    - Set deleted flag to true
     *    - Filter out deleted users in queries
     *    - Don't physically remove from repository
     */
}