package com.coherentsolutions.session1.exercises.userservice;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * USER SERVICE SOLUTION: Complete Service Layer Implementation
 * 
 * This class provides the complete solution for the User Service Exercise.
 * It demonstrates proper service layer architecture with:
 * - Dependency injection
 * - Validation and error handling
 * - Repository pattern implementation
 * - Business logic separation
 * - Optional usage for null safety
 * - Proper exception handling
 * 
 * KEY LEARNING POINTS:
 * 1. Service layer responsibilities and boundaries
 * 2. Repository pattern for data access abstraction
 * 3. Validation strategies and error handling
 * 4. Optional usage for null safety
 * 5. DTOs for data transfer
 * 6. Business logic implementation
 * 7. Testing strategies for service layer
 */
@Slf4j
public class UserServiceSolution {
    
    public static void main(String[] args) {
        System.out.println("=== USER SERVICE SOLUTION ===");
        
        // Create the service with in-memory repository
        UserRepository repository = new InMemoryUserRepositorySolution();
        UserService userService = new UserServiceImplSolution(repository);
        
        // Test the implementation
        testUserService(userService);
        
        // Demonstrate advanced features
        demonstrateAdvancedFeatures(userService);
        
        System.out.println("\n=== SOLUTION COMPLETED ===");
    }
    
    public static void testUserService(UserService userService) {
        try {
            System.out.println("\n--- Testing User Service Implementation ---");
            
            // Test 1: Create users
            System.out.println("\n1. Creating users:");
            CreateUserRequest request1 = new CreateUserRequest("john.doe@example.com", "SecurePass123!", "John", "Doe");
            CreateUserRequest request2 = new CreateUserRequest("jane.smith@example.com", "MyPassword456@", "Jane", "Smith");
            CreateUserRequest request3 = new CreateUserRequest("admin@example.com", "AdminPass789#", "Admin", "User");
            
            User user1 = userService.createUser(request1);
            User user2 = userService.createUser(request2);
            User user3 = userService.createUser(request3);
            
            System.out.println("✅ Created user 1: " + user1.getEmail() + " (ID: " + user1.getId() + ")");
            System.out.println("✅ Created user 2: " + user2.getEmail() + " (ID: " + user2.getId() + ")");
            System.out.println("✅ Created user 3: " + user3.getEmail() + " (ID: " + user3.getId() + ")");
            
            // Test 2: Find users
            System.out.println("\n2. Finding users:");
            Optional<User> foundUser = userService.findUserByEmail("john.doe@example.com");
            if (foundUser.isPresent()) {
                System.out.println("✅ Found user: " + foundUser.get().getFullName());
            } else {
                System.out.println("❌ User not found");
            }
            
            Optional<User> foundById = userService.findUserById(user2.getId());
            if (foundById.isPresent()) {
                System.out.println("✅ Found user by ID: " + foundById.get().getFullName());
            }
            
            // Test 3: List all users
            System.out.println("\n3. Listing all users:");
            List<User> allUsers = userService.getAllUsers();
            allUsers.forEach(user -> System.out.println("- " + user.getFullName() + " (" + user.getEmail() + ") - " + user.getStatus()));
            
            // Test 4: Update user
            System.out.println("\n4. Updating user:");
            UpdateUserRequest updateRequest = new UpdateUserRequest(user3.getId(), "Super", "Admin", UserRole.ADMIN);
            User updatedUser = userService.updateUser(updateRequest);
            System.out.println("✅ Updated user: " + updatedUser.getFullName() + " - Role: " + updatedUser.getRole());
            
            // Test 5: Activate/Deactivate user
            System.out.println("\n5. User activation:");
            userService.deactivateUser(user2.getId());
            System.out.println("✅ User 2 deactivated");
            
            // Verify deactivation
            Optional<User> deactivatedUser = userService.findUserById(user2.getId());
            if (deactivatedUser.isPresent()) {
                System.out.println("Status: " + deactivatedUser.get().getStatus());
            }
            
            userService.activateUser(user2.getId());
            System.out.println("✅ User 2 reactivated");
            
            // Test 6: Authentication
            System.out.println("\n6. Authentication test:");
            boolean authenticated = userService.authenticate("john.doe@example.com", "SecurePass123!");
            System.out.println("✅ Authentication result: " + authenticated);
            
            boolean wrongPassword = userService.authenticate("john.doe@example.com", "wrongpassword");
            System.out.println("✅ Wrong password result: " + wrongPassword);
            
            // Test 7: Role-based operations
            System.out.println("\n7. Role-based operations:");
            List<User> adminUsers = userService.getUsersByRole(UserRole.ADMIN);
            System.out.println("✅ Admin users: " + adminUsers.size());
            adminUsers.forEach(user -> System.out.println("  - " + user.getFullName()));
            
            List<User> activeUsers = userService.getActiveUsers();
            System.out.println("✅ Active users: " + activeUsers.size());
            
            // Test 8: Error handling
            System.out.println("\n8. Error handling:");
            try {
                userService.createUser(new CreateUserRequest("invalid-email", "weak", "Test", "User"));
                System.out.println("❌ Should have thrown validation error");
            } catch (UserValidationException e) {
                System.out.println("✅ Caught expected validation error: " + e.getMessage());
            }
            
            try {
                userService.createUser(new CreateUserRequest("john.doe@example.com", "SecurePass123!", "Duplicate", "User"));
                System.out.println("❌ Should have thrown duplicate email error");
            } catch (DuplicateEmailException e) {
                System.out.println("✅ Caught expected duplicate email error: " + e.getMessage());
            }
            
            try {
                userService.findUserById(999L);
                System.out.println("✅ Non-existent user returned empty Optional");
            } catch (Exception e) {
                System.out.println("❌ Unexpected exception: " + e.getMessage());
            }
            
            // Test 9: Soft delete
            System.out.println("\n9. Soft delete test:");
            int userCountBefore = userService.getAllUsers().size();
            userService.deleteUser(user1.getId());
            int userCountAfter = userService.getAllUsers().size();
            System.out.println("✅ Users before delete: " + userCountBefore);
            System.out.println("✅ Users after delete: " + userCountAfter);
            
            // Verify user is soft deleted
            Optional<User> deletedUser = userService.findUserById(user1.getId());
            if (deletedUser.isEmpty()) {
                System.out.println("✅ Deleted user is not visible in queries");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void demonstrateAdvancedFeatures(UserService userService) {
        System.out.println("\n--- Advanced Features Demo ---");
        
        // Create test users for advanced features
        try {
            CreateUserRequest guestRequest = new CreateUserRequest("guest@example.com", "GuestPass123!", "Guest", "User");
            userService.createUser(guestRequest);
            
            // Test last login tracking
            System.out.println("\n1. Last login tracking:");
            boolean authResult = userService.authenticate("guest@example.com", "GuestPass123!");
            if (authResult) {
                Optional<User> user = userService.findUserByEmail("guest@example.com");
                if (user.isPresent()) {
                    System.out.println("✅ Last login updated: " + user.get().getLastLoginAt());
                }
            }
            
            // Test different user roles
            System.out.println("\n2. User roles distribution:");
            Arrays.stream(UserRole.values()).forEach(role -> {
                List<User> usersWithRole = userService.getUsersByRole(role);
                System.out.println("- " + role + ": " + usersWithRole.size() + " users");
            });
            
        } catch (Exception e) {
            System.err.println("❌ Advanced features test failed: " + e.getMessage());
        }
    }
    
    // Domain Models (same as exercise)
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
    
    // Request/Response DTOs (same as exercise)
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
    
    // Repository Interface (same as exercise)
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
    
    // Service Interface (same as exercise)
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
    
    // ✅ SOLUTION: UserServiceImpl Implementation
    @Slf4j
    public static class UserServiceImplSolution implements UserService {
        private final UserRepository userRepository;
        
        // Email validation pattern
        private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        );
        
        // Password validation pattern (min 8 chars, at least 1 special char, 1 number)
        private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$"
        );
        
        public UserServiceImplSolution(UserRepository userRepository) {
            this.userRepository = userRepository;
        }
        
        @Override
        public User createUser(CreateUserRequest request) {
            log.info("Creating user with email: {}", request.getEmail());
            
            // Validate request
            validateCreateRequest(request);
            
            // Check for duplicate email
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateEmailException("User with email " + request.getEmail() + " already exists");
            }
            
            // Create user
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPasswordHash(hashPassword(request.getPassword()));
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setRole(UserRole.USER); // Default role
            user.setStatus(UserStatus.ACTIVE); // Default status
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setDeleted(false);
            
            User savedUser = userRepository.save(user);
            log.info("User created successfully with ID: {}", savedUser.getId());
            return savedUser;
        }
        
        @Override
        public Optional<User> findUserById(Long id) {
            if (id == null) {
                return Optional.empty();
            }
            
            return userRepository.findById(id)
                .filter(user -> !user.isDeleted()); // Filter out deleted users
        }
        
        @Override
        public Optional<User> findUserByEmail(String email) {
            if (email == null || email.trim().isEmpty()) {
                return Optional.empty();
            }
            
            return userRepository.findByEmail(email.trim().toLowerCase())
                .filter(user -> !user.isDeleted()); // Filter out deleted users
        }
        
        @Override
        public List<User> getAllUsers() {
            return userRepository.findAll().stream()
                .filter(user -> !user.isDeleted())
                .collect(Collectors.toList());
        }
        
        @Override
        public List<User> getUsersByRole(UserRole role) {
            if (role == null) {
                return Collections.emptyList();
            }
            
            return userRepository.findByRole(role).stream()
                .filter(user -> !user.isDeleted())
                .collect(Collectors.toList());
        }
        
        @Override
        public List<User> getActiveUsers() {
            return userRepository.findByStatus(UserStatus.ACTIVE).stream()
                .filter(user -> !user.isDeleted())
                .collect(Collectors.toList());
        }
        
        @Override
        public User updateUser(UpdateUserRequest request) {
            validateUpdateRequest(request);
            
            User user = findUserById(request.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + request.getId()));
            
            // Update fields
            if (request.getFirstName() != null) {
                user.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null) {
                user.setLastName(request.getLastName());
            }
            if (request.getRole() != null) {
                user.setRole(request.getRole());
            }
            
            user.setUpdatedAt(LocalDateTime.now());
            
            User updatedUser = userRepository.save(user);
            log.info("User updated successfully: {}", updatedUser.getId());
            return updatedUser;
        }
        
        @Override
        public void activateUser(Long id) {
            User user = findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
            
            user.setStatus(UserStatus.ACTIVE);
            user.setUpdatedAt(LocalDateTime.now());
            
            userRepository.save(user);
            log.info("User activated: {}", id);
        }
        
        @Override
        public void deactivateUser(Long id) {
            User user = findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
            
            user.setStatus(UserStatus.INACTIVE);
            user.setUpdatedAt(LocalDateTime.now());
            
            userRepository.save(user);
            log.info("User deactivated: {}", id);
        }
        
        @Override
        public void deleteUser(Long id) {
            User user = findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
            
            // Soft delete
            user.setDeleted(true);
            user.setStatus(UserStatus.INACTIVE);
            user.setUpdatedAt(LocalDateTime.now());
            
            userRepository.save(user);
            log.info("User soft deleted: {}", id);
        }
        
        @Override
        public boolean authenticate(String email, String password) {
            if (email == null || password == null) {
                return false;
            }
            
            Optional<User> userOpt = findUserByEmail(email);
            if (userOpt.isEmpty()) {
                return false;
            }
            
            User user = userOpt.get();
            if (!user.isActive()) {
                return false;
            }
            
            if (verifyPassword(password, user.getPasswordHash())) {
                updateLastLogin(user.getId());
                log.info("User authenticated successfully: {}", email);
                return true;
            }
            
            log.warn("Authentication failed for user: {}", email);
            return false;
        }
        
        @Override
        public void updateLastLogin(Long id) {
            User user = findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
            
            user.setLastLoginAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            
            userRepository.save(user);
            log.debug("Last login updated for user: {}", id);
        }
        
        // Helper methods
        private boolean isValidEmail(String email) {
            return email != null && EMAIL_PATTERN.matcher(email).matches();
        }
        
        private boolean isValidPassword(String password) {
            return password != null && PASSWORD_PATTERN.matcher(password).matches();
        }
        
        private String hashPassword(String password) {
            // For exercise purposes, use simple hashing
            // In real applications, use BCrypt or similar
            return "hashed_" + password.hashCode();
        }
        
        private boolean verifyPassword(String password, String hash) {
            // For exercise purposes, use simple verification
            // In real applications, use BCrypt or similar
            return hashPassword(password).equals(hash);
        }
        
        private void validateCreateRequest(CreateUserRequest request) {
            if (request == null) {
                throw new UserValidationException("Create request cannot be null");
            }
            
            if (!isValidEmail(request.getEmail())) {
                throw new UserValidationException("Invalid email format");
            }
            
            if (!isValidPassword(request.getPassword())) {
                throw new UserValidationException("Password must be at least 8 characters long and contain at least one number and one special character");
            }
            
            if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
                throw new UserValidationException("First name cannot be empty");
            }
            
            if (request.getLastName() == null || request.getLastName().trim().isEmpty()) {
                throw new UserValidationException("Last name cannot be empty");
            }
        }
        
        private void validateUpdateRequest(UpdateUserRequest request) {
            if (request == null) {
                throw new UserValidationException("Update request cannot be null");
            }
            
            if (request.getId() == null) {
                throw new UserValidationException("User ID cannot be null");
            }
            
            if (request.getFirstName() != null && request.getFirstName().trim().isEmpty()) {
                throw new UserValidationException("First name cannot be empty");
            }
            
            if (request.getLastName() != null && request.getLastName().trim().isEmpty()) {
                throw new UserValidationException("Last name cannot be empty");
            }
        }
    }
    
    // ✅ SOLUTION: InMemoryUserRepository Implementation
    public static class InMemoryUserRepositorySolution implements UserRepository {
        private final Map<Long, User> users = new HashMap<>();
        private Long nextId = 1L;
        
        @Override
        public User save(User user) {
            if (user.getId() == null) {
                user.setId(nextId++);
            }
            users.put(user.getId(), user);
            return user;
        }
        
        @Override
        public Optional<User> findById(Long id) {
            return Optional.ofNullable(users.get(id));
        }
        
        @Override
        public Optional<User> findByEmail(String email) {
            return users.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
        }
        
        @Override
        public List<User> findAll() {
            return new ArrayList<>(users.values());
        }
        
        @Override
        public List<User> findByRole(UserRole role) {
            return users.values().stream()
                .filter(user -> user.getRole() == role)
                .collect(Collectors.toList());
        }
        
        @Override
        public List<User> findByStatus(UserStatus status) {
            return users.values().stream()
                .filter(user -> user.getStatus() == status)
                .collect(Collectors.toList());
        }
        
        @Override
        public void deleteById(Long id) {
            users.remove(id);
        }
        
        @Override
        public boolean existsByEmail(String email) {
            return users.values().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email) && !user.isDeleted());
        }
    }
    
    // Custom Exceptions (same as exercise)
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
}