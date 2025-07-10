package com.coherentsolutions.session1.antipatterns.di;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

/**
 * CORRECT PATTERN: Dependency Injection in Java
 * 
 * This class demonstrates the CORRECT way to handle dependency injection in Java.
 * Key principles:
 * 1. Use constructor injection with @RequiredArgsConstructor
 * 2. Depend on interfaces, not concrete implementations
 * 3. Use proper Spring annotations (@Service, @Repository, @Controller)
 * 4. Avoid circular dependencies
 * 5. Keep dependencies focused (Single Responsibility)
 * 6. Use proper scoping and lifecycle management
 * 7. Leverage Spring's features (profiles, configuration, etc.)
 */
@Slf4j
public class DependencyInjectionCorrect {
    
    public static void main(String[] args) {
        demonstrateCorrectDI();
    }
    
    public static void demonstrateCorrectDI() {
        System.out.println("=== CORRECT DEPENDENCY INJECTION PATTERNS ===");
        
        // SOLUTION 1: Constructor injection with interfaces
        System.out.println("\n1. Constructor injection with interfaces:");
        demonstrateConstructorInjection();
        
        // SOLUTION 2: Proper service layering
        System.out.println("\n2. Proper service layering:");
        demonstrateServiceLayering();
        
        // SOLUTION 3: Configuration-based DI
        System.out.println("\n3. Configuration-based DI:");
        demonstrateConfigurationDI();
        
        // SOLUTION 4: Avoiding circular dependencies
        System.out.println("\n4. Avoiding circular dependencies:");
        demonstrateCircularDependencyAvoidance();
        
        // SOLUTION 5: Focused dependencies
        System.out.println("\n5. Focused dependencies:");
        demonstrateFocusedDependencies();
        
        // SOLUTION 6: Testing with DI
        System.out.println("\n6. Testing with DI:");
        demonstrateTestability();
    }
    
    public static void demonstrateConstructorInjection() {
        // ✅ CORRECT: Constructor injection example
        
        System.out.println("Constructor injection benefits:");
        System.out.println("- Dependencies are explicit");
        System.out.println("- Immutable dependencies");
        System.out.println("- Easy to test");
        System.out.println("- Fails fast if dependencies missing");
        System.out.println("- Works with final fields");
        
        // Manual creation for demo (in real app, Spring handles this)
        UserRepository repository = new UserRepositoryImpl();
        UserService service = new UserServiceImpl(repository);
        UserController controller = new UserControllerImpl(service);
        
        controller.createUser("test@example.com");
    }
    
    public static void demonstrateServiceLayering() {
        System.out.println("\nProper service layering:");
        System.out.println("- Controller → Service → Repository");
        System.out.println("- Each layer has single responsibility");
        System.out.println("- Dependencies flow one direction");
        System.out.println("- Easy to test each layer independently");
    }
    
    public static void demonstrateConfigurationDI() {
        System.out.println("\nConfiguration-based DI:");
        System.out.println("- @Configuration classes for setup");
        System.out.println("- @Bean methods for complex objects");
        System.out.println("- @Profile for environment-specific beans");
        System.out.println("- @Conditional for conditional bean creation");
    }
    
    public static void demonstrateCircularDependencyAvoidance() {
        System.out.println("\nAvoiding circular dependencies:");
        System.out.println("- Use events/messaging for loose coupling");
        System.out.println("- Extract common logic to shared service");
        System.out.println("- Use interfaces to break direct dependencies");
        System.out.println("- Redesign to have clear dependency hierarchy");
    }
    
    public static void demonstrateFocusedDependencies() {
        System.out.println("\nFocused dependencies:");
        System.out.println("- Each service has 1-3 dependencies maximum");
        System.out.println("- Clear single responsibility");
        System.out.println("- Easy to understand and maintain");
        System.out.println("- Better testability");
    }
    
    public static void demonstrateTestability() {
        System.out.println("\nTestability with DI:");
        System.out.println("- Easy to mock dependencies");
        System.out.println("- Constructor injection works with test frameworks");
        System.out.println("- Can create test configurations");
        System.out.println("- Proper separation of concerns");
    }
    
    // ✅ CORRECT: Repository interface
    public interface UserRepository {
        void save(User user);
        Optional<User> findByEmail(String email);
        List<User> findAll();
        void deleteById(Long id);
    }
    
    // ✅ CORRECT: Service interface
    public interface UserService {
        void createUser(String email);
        Optional<User> findUserByEmail(String email);
        List<User> getAllUsers();
        void deleteUser(Long id);
    }
    
    // ✅ CORRECT: Controller interface
    public interface UserController {
        void createUser(String email);
        void getUser(String email);
        void getAllUsers();
        void deleteUser(Long id);
    }
    
    // ✅ CORRECT: Repository implementation with proper annotations
    @Repository
    @RequiredArgsConstructor
    public static class UserRepositoryImpl implements UserRepository {
        
        @Override
        public void save(User user) {
            // Simulate database save
            System.out.println("Saving user: " + user.getEmail());
        }
        
        @Override
        public Optional<User> findByEmail(String email) {
            // Simulate database lookup
            return Optional.of(new User(1L, email));
        }
        
        @Override
        public List<User> findAll() {
            // Simulate fetching all users
            return List.of(
                new User(1L, "user1@example.com"),
                new User(2L, "user2@example.com")
            );
        }
        
        @Override
        public void deleteById(Long id) {
            System.out.println("Deleting user with ID: " + id);
        }
    }
    
    // ✅ CORRECT: Service implementation with constructor injection
    @Service
    @RequiredArgsConstructor
    @Slf4j
    public static class UserServiceImpl implements UserService {
        
        private final UserRepository userRepository;
        
        @Override
        public void createUser(String email) {
            // ✅ CORRECT: Input validation
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }
            
            // ✅ CORRECT: Business logic
            if (userRepository.findByEmail(email).isPresent()) {
                throw new IllegalArgumentException("User already exists");
            }
            
            User user = new User(null, email);
            userRepository.save(user);
            
            log.info("User created successfully: {}", email);
        }
        
        @Override
        public Optional<User> findUserByEmail(String email) {
            return userRepository.findByEmail(email);
        }
        
        @Override
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }
        
        @Override
        public void deleteUser(Long id) {
            userRepository.deleteById(id);
            log.info("User deleted: {}", id);
        }
    }
    
    // ✅ CORRECT: Controller with constructor injection
    @Controller
    @RequiredArgsConstructor
    @Slf4j
    public static class UserControllerImpl implements UserController {
        
        private final UserService userService;
        
        @Override
        public void createUser(String email) {
            try {
                userService.createUser(email);
                System.out.println("User created successfully via controller");
            } catch (IllegalArgumentException e) {
                log.error("Error creating user: {}", e.getMessage());
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        @Override
        public void getUser(String email) {
            Optional<User> user = userService.findUserByEmail(email);
            if (user.isPresent()) {
                System.out.println("Found user: " + user.get().getEmail());
            } else {
                System.out.println("User not found: " + email);
            }
        }
        
        @Override
        public void getAllUsers() {
            List<User> users = userService.getAllUsers();
            System.out.println("All users: " + users.size());
            users.forEach(user -> System.out.println("- " + user.getEmail()));
        }
        
        @Override
        public void deleteUser(Long id) {
            userService.deleteUser(id);
            System.out.println("User deleted via controller");
        }
    }
    
    // ✅ CORRECT: Focused service with single responsibility
    @Service
    @RequiredArgsConstructor
    @Slf4j
    public static class EmailService {
        
        private final EmailConfig emailConfig;
        
        public void sendWelcomeEmail(String toEmail) {
            log.info("Sending welcome email to: {}", toEmail);
            System.out.println("Welcome email sent to: " + toEmail);
        }
        
        public void sendPasswordResetEmail(String toEmail, String resetToken) {
            log.info("Sending password reset email to: {}", toEmail);
            System.out.println("Password reset email sent to: " + toEmail);
        }
    }
    
    // ✅ CORRECT: Configuration class
    @Component
    public static class EmailConfig {
        public String getSmtpHost() {
            return "smtp.example.com";
        }
        
        public int getSmtpPort() {
            return 587;
        }
        
        public String getFromAddress() {
            return "noreply@example.com";
        }
    }
    
    // ✅ CORRECT: Composite service with focused dependencies
    @Service
    @RequiredArgsConstructor
    @Slf4j
    public static class UserRegistrationService {
        
        private final UserService userService;
        private final EmailService emailService;
        
        public void registerUser(String email) {
            try {
                // Create user
                userService.createUser(email);
                
                // Send welcome email
                emailService.sendWelcomeEmail(email);
                
                log.info("User registration completed: {}", email);
                System.out.println("User registration completed: " + email);
                
            } catch (Exception e) {
                log.error("User registration failed: {}", e.getMessage());
                throw new RuntimeException("Registration failed", e);
            }
        }
    }
    
    // ✅ CORRECT: Multiple implementations with qualifiers
    public interface NotificationService {
        void sendNotification(String message, String recipient);
    }
    
    @Service
    @Qualifier("email")
    @RequiredArgsConstructor
    public static class EmailNotificationService implements NotificationService {
        
        private final EmailService emailService;
        
        @Override
        public void sendNotification(String message, String recipient) {
            emailService.sendWelcomeEmail(recipient);
        }
    }
    
    @Service
    @Qualifier("sms")
    public static class SmsNotificationService implements NotificationService {
        
        @Override
        public void sendNotification(String message, String recipient) {
            System.out.println("SMS sent to " + recipient + ": " + message);
        }
    }
    
    // ✅ CORRECT: Primary implementation
    @Service
    @Primary
    public static class PrimaryNotificationService implements NotificationService {
        
        @Override
        public void sendNotification(String message, String recipient) {
            System.out.println("Primary notification to " + recipient + ": " + message);
        }
    }
    
    // ✅ CORRECT: Using qualified dependencies
    @Service
    @RequiredArgsConstructor
    public static class NotificationOrchestrator {
        
        @Qualifier("email")
        private final NotificationService emailNotificationService;
        
        @Qualifier("sms")
        private final NotificationService smsNotificationService;
        
        private final NotificationService primaryNotificationService; // Uses @Primary
        
        public void sendAllNotifications(String message, String recipient) {
            emailNotificationService.sendNotification(message, recipient);
            smsNotificationService.sendNotification(message, recipient);
            primaryNotificationService.sendNotification(message, recipient);
        }
    }
    
    // ✅ CORRECT: Event-driven architecture to avoid circular dependencies
    @Component
    public static class UserEventPublisher {
        
        public void publishUserCreated(User user) {
            System.out.println("Publishing user created event: " + user.getEmail());
            // In real app, use Spring's ApplicationEventPublisher
        }
    }
    
    @Component
    @RequiredArgsConstructor
    public static class UserEventListener {
        
        private final EmailService emailService;
        
        public void handleUserCreated(User user) {
            System.out.println("Handling user created event: " + user.getEmail());
            emailService.sendWelcomeEmail(user.getEmail());
        }
    }
    
    // ✅ CORRECT: Factory pattern for complex object creation
    @Component
    public static class UserFactory {
        
        public User createUser(String email) {
            // Complex user creation logic
            return new User(null, email);
        }
        
        public User createAdminUser(String email) {
            // Admin user creation with special permissions
            return new User(null, email);
        }
    }
    
    // ✅ CORRECT: Utility class without DI (pure functions)
    public static class UserUtils {
        
        public static boolean isValidEmail(String email) {
            return email != null && email.contains("@");
        }
        
        public static String normalizeEmail(String email) {
            return email != null ? email.toLowerCase().trim() : null;
        }
    }
    
    // ✅ CORRECT: Test-friendly design
    @Service
    @RequiredArgsConstructor
    public static class TestableUserService {
        
        private final UserRepository userRepository;
        private final EmailService emailService;
        private final UserEventPublisher eventPublisher;
        
        public void createUserWithNotification(String email) {
            // Easy to test with mocked dependencies
            User user = new User(null, email);
            userRepository.save(user);
            emailService.sendWelcomeEmail(email);
            eventPublisher.publishUserCreated(user);
        }
    }
    
    // ✅ CORRECT: Simple entity class
    public static class User {
        private Long id;
        private String email;
        
        public User(Long id, String email) {
            this.id = id;
            this.email = email;
        }
        
        public Long getId() {
            return id;
        }
        
        public String getEmail() {
            return email;
        }
    }
    
    // ✅ CORRECT: Configuration properties
    @Component
    public static class ApplicationConfig {
        
        public String getDatabaseUrl() {
            return "jdbc:mysql://localhost:3306/myapp";
        }
        
        public int getMaxConnections() {
            return 20;
        }
        
        public boolean isDevMode() {
            return true;
        }
    }
    
    // ✅ CORRECT: Conditional bean creation
    @Service
    @RequiredArgsConstructor
    public static class ConditionalService {
        
        private final ApplicationConfig config;
        
        public void doSomething() {
            if (config.isDevMode()) {
                System.out.println("Development mode behavior");
            } else {
                System.out.println("Production mode behavior");
            }
        }
    }
    
    // ✅ CORRECT: Best practices summary
    public static class DIBestPractices {
        
        /**
         * DEPENDENCY INJECTION BEST PRACTICES:
         * 
         * 1. CONSTRUCTOR INJECTION:
         *    - Use @RequiredArgsConstructor with final fields
         *    - Makes dependencies explicit and immutable
         *    - Easy to test and debug
         * 
         * 2. INTERFACE SEGREGATION:
         *    - Depend on interfaces, not concrete classes
         *    - Enables easy mocking and testing
         *    - Supports multiple implementations
         * 
         * 3. SINGLE RESPONSIBILITY:
         *    - Each service should have one clear purpose
         *    - Keep dependencies focused (1-3 max)
         *    - Avoid God objects
         * 
         * 4. AVOID CIRCULAR DEPENDENCIES:
         *    - Use events for loose coupling
         *    - Extract shared logic to common services
         *    - Design clear dependency hierarchy
         * 
         * 5. PROPER ANNOTATIONS:
         *    - @Service for business logic
         *    - @Repository for data access
         *    - @Controller for web endpoints
         *    - @Component for general components
         * 
         * 6. CONFIGURATION:
         *    - Use @Configuration for complex setup
         *    - @Profile for environment-specific beans
         *    - @Conditional for conditional creation
         * 
         * 7. TESTING:
         *    - Design for testability
         *    - Use interfaces for easy mocking
         *    - Constructor injection works with test frameworks
         */
    }
}