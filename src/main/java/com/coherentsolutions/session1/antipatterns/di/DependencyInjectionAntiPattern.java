package com.coherentsolutions.session1.antipatterns.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * ANTI-PATTERN: Dependency Injection in Java
 * 
 * This class demonstrates the WRONG way to handle dependency injection in Java.
 * C# developers are used to:
 * - Constructor injection with IServiceCollection
 * - Built-in DI container in .NET Core
 * - Automatic dependency resolution
 * - Service lifetime management (Singleton, Scoped, Transient)
 * 
 * Java uses Spring Framework for DI, but there are many anti-patterns
 * that C# developers commonly fall into.
 */
public class DependencyInjectionAntiPattern {
    
    public static void main(String[] args) {
        demonstrateDependencyInjectionProblems();
    }
    
    public static void demonstrateDependencyInjectionProblems() {
        System.out.println("=== DEPENDENCY INJECTION ANTI-PATTERNS ===");
        
        // PROBLEM 1: Manual object creation instead of DI
        System.out.println("\n1. Manual object creation instead of DI:");
        demonstrateManualCreation();
        
        // PROBLEM 2: Field injection instead of constructor injection
        System.out.println("\n2. Field injection problems:");
        demonstrateFieldInjection();
        
        // PROBLEM 3: Service locator pattern
        System.out.println("\n3. Service locator anti-pattern:");
        demonstrateServiceLocator();
        
        // PROBLEM 4: Circular dependencies
        System.out.println("\n4. Circular dependencies:");
        demonstrateCircularDependencies();
        
        // PROBLEM 5: Over-injection (too many dependencies)
        System.out.println("\n5. Over-injection problems:");
        demonstrateOverInjection();
        
        // PROBLEM 6: Mixing DI with static methods
        System.out.println("\n6. Mixing DI with static methods:");
        demonstrateStaticMixing();
    }
    
    public static void demonstrateManualCreation() {
        // ❌ WRONG: Creating dependencies manually
        
        // In C#, you might register services:
        // services.AddScoped<IUserRepository, UserRepository>();
        // services.AddScoped<IUserService, UserService>();
        
        // But then manually create objects in Java:
        UserRepositoryAntiPattern repository = new UserRepositoryAntiPattern();
        UserServiceAntiPattern service = new UserServiceAntiPattern(repository);
        UserControllerAntiPattern controller = new UserControllerAntiPattern(service);
        
        System.out.println("Manual creation breaks DI container benefits:");
        System.out.println("- No lifecycle management");
        System.out.println("- No proxy creation");
        System.out.println("- No AOP integration");
        System.out.println("- Hard to test");
        System.out.println("- Tight coupling");
        
        controller.createUser("test@example.com");
    }
    
    public static void demonstrateFieldInjection() {
        // ❌ WRONG: Field injection example
        
        System.out.println("Field injection problems:");
        System.out.println("- Hard to test (can't mock dependencies)");
        System.out.println("- Hidden dependencies");
        System.out.println("- Violates immutability");
        System.out.println("- Reflection-based (slower)");
        System.out.println("- Can't detect circular dependencies early");
        
        // Cannot properly demonstrate without Spring context
        // but this shows the pattern
        FieldInjectionExample example = new FieldInjectionExample();
        System.out.println("Field injection class created (dependencies would be null)");
    }
    
    public static void demonstrateServiceLocator() {
        // ❌ WRONG: Service locator pattern
        
        System.out.println("Service locator anti-pattern:");
        System.out.println("- Hides dependencies");
        System.out.println("- Hard to test");
        System.out.println("- Violates Dependency Inversion Principle");
        System.out.println("- Runtime failures instead of compile-time");
        
        ServiceLocatorExample locator = new ServiceLocatorExample();
        locator.doSomething();
    }
    
    public static void demonstrateCircularDependencies() {
        // ❌ WRONG: Circular dependencies
        
        System.out.println("Circular dependency problems:");
        System.out.println("- A depends on B, B depends on A");
        System.out.println("- Causes infinite loops");
        System.out.println("- Hard to understand and maintain");
        System.out.println("- May cause stack overflow");
        
        // This would cause issues in Spring:
        // CircularA -> CircularB -> CircularA
        System.out.println("Circular dependencies detected at runtime, not compile-time");
    }
    
    public static void demonstrateOverInjection() {
        // ❌ WRONG: Too many dependencies
        
        System.out.println("Over-injection problems:");
        System.out.println("- God object with too many responsibilities");
        System.out.println("- Hard to test");
        System.out.println("- Violates Single Responsibility Principle");
        System.out.println("- Performance overhead");
        
        // Example of class with too many dependencies
        OverInjectedService service = new OverInjectedService();
        System.out.println("Service with 10+ dependencies created");
    }
    
    public static void demonstrateStaticMixing() {
        // ❌ WRONG: Mixing DI with static methods
        
        System.out.println("Static methods with DI problems:");
        System.out.println("- Static methods can't be injected");
        System.out.println("- Hard to test static methods");
        System.out.println("- Mixing paradigms");
        System.out.println("- Can't mock static dependencies");
        
        StaticMixingExample.processData("test");
    }
    
    // ❌ WRONG: Manual dependency creation
    public static class UserRepositoryAntiPattern {
        public void save(String email) {
            // Simulate database operations
            System.out.println("Saving user: " + email);
        }
        
        public String findByEmail(String email) {
            return "User: " + email;
        }
    }
    
    // ❌ WRONG: Service with manual dependencies
    public static class UserServiceAntiPattern {
        private final UserRepositoryAntiPattern repository;
        
        public UserServiceAntiPattern(UserRepositoryAntiPattern repository) {
            this.repository = repository;
        }
        
        public void createUser(String email) {
            // Manual validation
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }
            
            repository.save(email);
            System.out.println("User created manually");
        }
    }
    
    // ❌ WRONG: Controller with manual dependencies
    public static class UserControllerAntiPattern {
        private final UserServiceAntiPattern userService;
        
        public UserControllerAntiPattern(UserServiceAntiPattern userService) {
            this.userService = userService;
        }
        
        public void createUser(String email) {
            userService.createUser(email);
            System.out.println("User created via controller");
        }
    }
    
    // ❌ WRONG: Field injection
    @Component
    public static class FieldInjectionExample {
        
        @Autowired
        private UserRepositoryAntiPattern userRepository; // Field injection
        
        @Autowired
        private UserServiceAntiPattern userService; // Field injection
        
        public void doSomething() {
            // These would be null outside of Spring context
            if (userRepository != null) {
                userRepository.save("test@example.com");
            }
        }
    }
    
    // ❌ WRONG: Service locator pattern
    public static class ServiceLocatorExample {
        
        public void doSomething() {
            // ❌ WRONG: Pulling dependencies from a global registry
            UserRepositoryAntiPattern repo = ServiceRegistry.getService(UserRepositoryAntiPattern.class);
            UserServiceAntiPattern service = ServiceRegistry.getService(UserServiceAntiPattern.class);
            
            if (repo != null && service != null) {
                service.createUser("servicelocator@example.com");
            }
        }
    }
    
    // ❌ WRONG: Global service registry
    public static class ServiceRegistry {
        private static final java.util.Map<Class<?>, Object> services = new java.util.HashMap<>();
        
        static {
            // Manual registration
            services.put(UserRepositoryAntiPattern.class, new UserRepositoryAntiPattern());
            services.put(UserServiceAntiPattern.class, 
                new UserServiceAntiPattern(new UserRepositoryAntiPattern()));
        }
        
        @SuppressWarnings("unchecked")
        public static <T> T getService(Class<T> serviceType) {
            return (T) services.get(serviceType);
        }
    }
    
    // ❌ WRONG: Circular dependency A
    @Service
    public static class CircularServiceA {
        @Autowired
        private CircularServiceB serviceB; // Depends on B
        
        public void methodA() {
            serviceB.methodB();
        }
    }
    
    // ❌ WRONG: Circular dependency B
    @Service
    public static class CircularServiceB {
        @Autowired
        private CircularServiceA serviceA; // Depends on A
        
        public void methodB() {
            serviceA.methodA();
        }
    }
    
    // ❌ WRONG: Over-injection - too many dependencies
    @Service
    public static class OverInjectedService {
        @Autowired
        private UserRepositoryAntiPattern userRepository;
        
        @Autowired
        private UserServiceAntiPattern userService;
        
        // Too many dependencies - sign of god object
        @Autowired
        private EmailService emailService;
        
        @Autowired
        private SmsService smsService;
        
        @Autowired
        private PaymentService paymentService;
        
        @Autowired
        private OrderService orderService;
        
        @Autowired
        private InventoryService inventoryService;
        
        @Autowired
        private ShippingService shippingService;
        
        @Autowired
        private TaxService taxService;
        
        @Autowired
        private ReportService reportService;
        
        public void processEverything() {
            // This class tries to do too much
            System.out.println("Processing with 10+ dependencies - God object!");
        }
    }
    
    // ❌ WRONG: Mixing static methods with DI
    public static class StaticMixingExample {
        
        @Autowired
        private static UserServiceAntiPattern userService; // Won't work!
        
        public static void processData(String data) {
            // ❌ WRONG: Can't inject into static methods
            // userService would be null
            
            // ❌ WRONG: Manual service creation in static method
            UserRepositoryAntiPattern repo = new UserRepositoryAntiPattern();
            UserServiceAntiPattern service = new UserServiceAntiPattern(repo);
            
            service.createUser(data + "@example.com");
            System.out.println("Static method with manual dependencies");
        }
    }
    
    // ❌ WRONG: Setter injection (less preferred)
    @Component
    public static class SetterInjectionExample {
        private UserRepositoryAntiPattern userRepository;
        private UserServiceAntiPattern userService;
        
        @Autowired
        public void setUserRepository(UserRepositoryAntiPattern userRepository) {
            this.userRepository = userRepository;
        }
        
        @Autowired
        public void setUserService(UserServiceAntiPattern userService) {
            this.userService = userService;
        }
        
        public void doSomething() {
            // ❌ WRONG: Object can be in partially initialized state
            if (userRepository != null && userService != null) {
                userService.createUser("setter@example.com");
            }
        }
    }
    
    // ❌ WRONG: Not using interfaces
    @Service
    public static class ConcreteService {
        @Autowired
        private UserRepositoryAntiPattern userRepository; // Should be interface
        
        public void processUser(String email) {
            userRepository.save(email);
        }
    }
    
    // Mock service implementations for compilation
    public static class EmailService {
        public void sendEmail(String to, String subject, String body) {
            System.out.println("Sending email to: " + to);
        }
    }
    
    public static class SmsService {
        public void sendSms(String to, String message) {
            System.out.println("Sending SMS to: " + to);
        }
    }
    
    public static class PaymentService {
        public void processPayment(double amount) {
            System.out.println("Processing payment: " + amount);
        }
    }
    
    public static class OrderService {
        public void createOrder(String userId) {
            System.out.println("Creating order for: " + userId);
        }
    }
    
    public static class InventoryService {
        public void updateInventory(String productId, int quantity) {
            System.out.println("Updating inventory: " + productId + " = " + quantity);
        }
    }
    
    public static class ShippingService {
        public void scheduleShipment(String orderId) {
            System.out.println("Scheduling shipment for: " + orderId);
        }
    }
    
    public static class TaxService {
        public double calculateTax(double amount) {
            System.out.println("Calculating tax for: " + amount);
            return amount * 0.1;
        }
    }
    
    public static class ReportService {
        public void generateReport(String type) {
            System.out.println("Generating report: " + type);
        }
    }
}