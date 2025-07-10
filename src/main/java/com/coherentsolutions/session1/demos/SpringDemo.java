package com.coherentsolutions.session1.demos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SPRING DEMO
 * 
 * Live coding demonstration of Spring Framework fundamentals for .NET developers.
 * This demo showcases Spring's approach to dependency injection, configuration,
 * and enterprise application development compared to .NET Core/.NET 5+.
 * 
 * DEMO OBJECTIVES:
 * 1. Map .NET Core DI concepts to Spring equivalents
 * 2. Demonstrate Spring's annotation-driven configuration
 * 3. Show Spring Boot's auto-configuration capabilities
 * 4. Practice building REST APIs with Spring
 * 5. Highlight Spring's approach to enterprise patterns
 * 
 * LECTURE FLOW:
 * 1. Start with dependency injection basics
 * 2. Progress through Spring annotations
 * 3. Show REST controller development
 * 4. Demonstrate configuration patterns
 * 5. End with practical business scenarios
 * 
 * INTERACTIVE ELEMENTS:
 * - .NET Core vs Spring comparisons
 * - Live API development
 * - Configuration exercises
 * - Error handling discussions
 * 
 * NOTE: This is a demonstration class. In a real Spring Boot application,
 * these components would be in separate files with proper package structure.
 */
@Slf4j
@SpringBootApplication
public class SpringDemo implements CommandLineRunner {
    
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    
    // Constructor injection (recommended)
    public SpringDemo(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }
    
    public static void main(String[] args) {
        System.out.println("=== SPRING DEMO ===");
        System.out.println("Spring Framework for .NET developers");
        
        // In a real application, this would start the Spring Boot application
        // SpringApplication.run(SpringDemo.class, args);
        
        // For demo purposes, we'll simulate the Spring container
        demonstrateSpringConcepts();
    }
    
    @Override
    public void run(String... args) throws Exception {
        // This method runs after Spring Boot startup
        log.info("Spring Demo application started");
        demonstrateBusinessScenarios();
    }
    
    /**
     * DEMO: Core Spring Concepts
     * 
     * Demonstrate fundamental Spring concepts without full container
     */
    public static void demonstrateSpringConcepts() {
        System.out.println("\n--- Spring Core Concepts ---");
        
        // Dependency Injection comparison
        System.out.println("DEPENDENCY INJECTION:");
        System.out.println(".NET Core: services.AddScoped<IUserService, UserService>();");
        System.out.println("Spring:     @Service public class UserService { ... }");
        System.out.println("");
        System.out.println(".NET Core: public UserController(IUserService userService)");
        System.out.println("Spring:     @Autowired or constructor injection");
        
        // Annotation comparison
        System.out.println("\nANNOTATION MAPPING:");
        System.out.println("┌────────────────────┬─────────────────────┐");
        System.out.println("│ .NET Core          │ Spring              │");
        System.out.println("├────────────────────┼─────────────────────┤");
        System.out.println("│ [ApiController]    │ @RestController     │");
        System.out.println("│ [HttpGet]          │ @GetMapping         │");
        System.out.println("│ [FromRoute]        │ @PathVariable       │");
        System.out.println("│ [FromBody]         │ @RequestBody        │");
        System.out.println("│ [FromQuery]        │ @RequestParam       │");
        System.out.println("│ services.AddScoped │ @Service            │");
        System.out.println("│ IOptions<T>        │ @Value, @ConfigProp │");
        System.out.println("└────────────────────┴─────────────────────┘");
        
        // Configuration comparison
        System.out.println("\nCONFIGURATION:");
        System.out.println(".NET Core:");
        System.out.println("  appsettings.json + IConfiguration");
        System.out.println("  services.Configure<MyOptions>(config);");
        System.out.println("");
        System.out.println("Spring:");
        System.out.println("  application.properties/yml + @Value");
        System.out.println("  @ConfigurationProperties classes");
        
        // Lifecycle comparison
        System.out.println("\nLIFECYCLE MANAGEMENT:");
        System.out.println(".NET Core: Constructor, IDisposable.Dispose()");
        System.out.println("Spring:     @PostConstruct, @PreDestroy");
        
        // Create demo instances
        createDemoInstances();
    }
    
    /**
     * Create demo instances to show Spring concepts
     */
    private static void createDemoInstances() {
        System.out.println("\n--- Demo Instance Creation ---");
        
        // Simulate Spring container behavior
        UserRepository userRepo = new UserRepositoryImpl();
        UserService userService = new UserService(userRepo);
        UserController userController = new UserController(userService);
        
        // Demonstrate the wired components
        System.out.println("Spring container has wired:");
        System.out.println("  UserRepository -> UserService -> UserController");
        
        // Test the wiring
        User testUser = userController.createUser(new CreateUserRequest("John Doe", "john@example.com"));
        System.out.println("Created user: " + testUser);
        
        List<User> allUsers = userController.getAllUsers();
        System.out.println("All users: " + allUsers.size());
    }
    
    /**
     * DEMO: Business Scenarios
     * 
     * Show practical Spring application patterns
     */
    public void demonstrateBusinessScenarios() {
        System.out.println("\n--- Business Scenarios ---");
        
        try {
            // Scenario 1: User management
            demonstrateUserManagement();
            
            // Scenario 2: Product catalog
            demonstrateProductCatalog();
            
            // Scenario 3: Order processing
            demonstrateOrderProcessing();
            
            // Scenario 4: Configuration management
            demonstrateConfigurationManagement();
            
        } catch (Exception e) {
            log.error("Error in business scenarios", e);
        }
    }
    
    private void demonstrateUserManagement() {
        System.out.println("\nSCENARIO 1: User Management");
        
        // Create users
        User user1 = userService.createUser("Alice Johnson", "alice@example.com");
        User user2 = userService.createUser("Bob Smith", "bob@example.com");
        
        System.out.println("Created users: " + Arrays.asList(user1, user2));
        
        // Find user
        Optional<User> found = userService.findByEmail("alice@example.com");
        System.out.println("Found user: " + found.orElse(null));
        
        // List all users
        System.out.println("Total users: " + userService.getAllUsers().size());
    }
    
    private void demonstrateProductCatalog() {
        System.out.println("\nSCENARIO 2: Product Catalog");
        
        // Add products
        Product product1 = productService.addProduct("Laptop", new BigDecimal("999.99"), "Electronics");
        Product product2 = productService.addProduct("Mouse", new BigDecimal("29.99"), "Electronics");
        Product product3 = productService.addProduct("Book", new BigDecimal("19.99"), "Books");
        
        System.out.println("Added products: " + Arrays.asList(product1, product2, product3));
        
        // Search products
        List<Product> electronics = productService.findByCategory("Electronics");
        System.out.println("Electronics: " + electronics.size() + " items");
        
        // Find expensive products
        List<Product> expensive = productService.findByPriceRange(new BigDecimal("50"), new BigDecimal("1000"));
        System.out.println("Expensive products: " + expensive.size() + " items");
    }
    
    private void demonstrateOrderProcessing() {
        System.out.println("\nSCENARIO 3: Order Processing");
        
        // Create an order
        Order order = orderService.createOrder("alice@example.com");
        
        // Add items to order
        orderService.addItem(order.getId(), "Laptop", 1);
        orderService.addItem(order.getId(), "Mouse", 2);
        
        // Calculate total
        BigDecimal total = orderService.calculateTotal(order.getId());
        System.out.println("Order total: $" + total);
        
        // Process order
        Order processed = orderService.processOrder(order.getId());
        System.out.println("Processed order: " + processed.getStatus());
    }
    
    private void demonstrateConfigurationManagement() {
        System.out.println("\nSCENARIO 4: Configuration Management");
        
        // This would normally use @Value and @ConfigurationProperties
        System.out.println("Application name: MySpringApp");
        System.out.println("Database URL: jdbc:mysql://localhost:3306/mydb");
        System.out.println("Cache enabled: true");
        System.out.println("Max connections: 20");
    }
    
    // ============================================================================
    // SPRING COMPONENTS - In a real app, these would be in separate files
    // ============================================================================
    
    /**
     * REST Controller - Web layer
     * 
     * Equivalent to .NET Core ApiController
     */
    @RestController
    @RequestMapping("/api/users")
    @RequiredArgsConstructor
    @Slf4j
    public static class UserController {
        
        private final UserService userService;
        
        @GetMapping
        public List<User> getAllUsers() {
            log.info("Getting all users");
            return userService.getAllUsers();
        }
        
        @GetMapping("/{id}")
        public User getUser(@PathVariable Long id) {
            log.info("Getting user by id: {}", id);
            return userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        }
        
        @PostMapping
        public User createUser(@RequestBody CreateUserRequest request) {
            log.info("Creating user: {}", request.getName());
            return userService.createUser(request.getName(), request.getEmail());
        }
        
        @PutMapping("/{id}")
        public User updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
            log.info("Updating user: {}", id);
            return userService.updateUser(id, request.getName(), request.getEmail());
        }
        
        @DeleteMapping("/{id}")
        public void deleteUser(@PathVariable Long id) {
            log.info("Deleting user: {}", id);
            userService.deleteUser(id);
        }
        
        @GetMapping("/search")
        public List<User> searchUsers(@RequestParam String email) {
            log.info("Searching users by email: {}", email);
            return userService.findByEmail(email)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
        }
    }
    
    /**
     * Service Layer - Business logic
     * 
     * Equivalent to .NET Core services
     */
    @Service
    @RequiredArgsConstructor
    @Slf4j
    public static class UserService {
        
        private final UserRepository userRepository;
        
        @Value("${app.user.default-role:USER}")
        private String defaultRole;
        
        @PostConstruct
        public void init() {
            log.info("UserService initialized with default role: {}", defaultRole);
        }
        
        public User createUser(String name, String email) {
            log.info("Creating user: {} with email: {}", name, email);
            
            // Business validation
            if (findByEmail(email).isPresent()) {
                throw new IllegalArgumentException("User with email already exists: " + email);
            }
            
            User user = new User(null, name, email, defaultRole, LocalDateTime.now());
            return userRepository.save(user);
        }
        
        public Optional<User> findById(Long id) {
            return userRepository.findById(id);
        }
        
        public Optional<User> findByEmail(String email) {
            return userRepository.findByEmail(email);
        }
        
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }
        
        public User updateUser(Long id, String name, String email) {
            User user = findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
            
            user.setName(name);
            user.setEmail(email);
            
            return userRepository.save(user);
        }
        
        public void deleteUser(Long id) {
            if (!userRepository.existsById(id)) {
                throw new UserNotFoundException("User not found: " + id);
            }
            userRepository.deleteById(id);
        }
        
        @PreDestroy
        public void cleanup() {
            log.info("UserService shutting down");
        }
    }
    
    /**
     * Repository Layer - Data access
     * 
     * Equivalent to .NET Core repositories
     */
    @Repository
    public interface UserRepository {
        User save(User user);
        Optional<User> findById(Long id);
        Optional<User> findByEmail(String email);
        List<User> findAll();
        boolean existsById(Long id);
        void deleteById(Long id);
    }
    
    /**
     * Repository Implementation
     * 
     * In a real app, this might be JPA/Hibernate
     */
    @Repository
    @Slf4j
    public static class UserRepositoryImpl implements UserRepository {
        
        private final Map<Long, User> users = new ConcurrentHashMap<>();
        private Long nextId = 1L;
        
        @Override
        public User save(User user) {
            if (user.getId() == null) {
                user.setId(nextId++);
                log.info("Saving new user: {}", user.getId());
            } else {
                log.info("Updating user: {}", user.getId());
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
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        }
        
        @Override
        public List<User> findAll() {
            return new ArrayList<>(users.values());
        }
        
        @Override
        public boolean existsById(Long id) {
            return users.containsKey(id);
        }
        
        @Override
        public void deleteById(Long id) {
            users.remove(id);
        }
    }
    
    /**
     * Product Service
     */
    @Service
    @Slf4j
    public static class ProductService {
        
        private final Map<Long, Product> products = new ConcurrentHashMap<>();
        private Long nextId = 1L;
        
        public Product addProduct(String name, BigDecimal price, String category) {
            Product product = new Product(nextId++, name, price, category);
            products.put(product.getId(), product);
            log.info("Added product: {}", product.getName());
            return product;
        }
        
        public Optional<Product> findById(Long id) {
            return Optional.ofNullable(products.get(id));
        }
        
        public List<Product> findByCategory(String category) {
            return products.values().stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(java.util.stream.Collectors.toList());
        }
        
        public List<Product> findByPriceRange(BigDecimal min, BigDecimal max) {
            return products.values().stream()
                .filter(p -> p.getPrice().compareTo(min) >= 0 && p.getPrice().compareTo(max) <= 0)
                .collect(java.util.stream.Collectors.toList());
        }
        
        public List<Product> getAllProducts() {
            return new ArrayList<>(products.values());
        }
    }
    
    /**
     * Order Service
     */
    @Service
    @RequiredArgsConstructor
    @Slf4j
    public static class OrderService {
        
        private final UserService userService;
        private final ProductService productService;
        private final Map<Long, Order> orders = new ConcurrentHashMap<>();
        private Long nextId = 1L;
        
        public Order createOrder(String userEmail) {
            User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userEmail));
            
            Order order = new Order(nextId++, user.getId(), new ArrayList<>(), 
                BigDecimal.ZERO, "PENDING", LocalDateTime.now());
            orders.put(order.getId(), order);
            
            log.info("Created order: {} for user: {}", order.getId(), userEmail);
            return order;
        }
        
        public void addItem(Long orderId, String productName, int quantity) {
            Order order = orders.get(orderId);
            if (order == null) {
                throw new IllegalArgumentException("Order not found: " + orderId);
            }
            
            // Find product by name (simplified)
            Product product = productService.getAllProducts().stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productName));
            
            OrderItem item = new OrderItem(product.getId(), productName, product.getPrice(), quantity);
            order.getItems().add(item);
            
            log.info("Added item to order {}: {} x{}", orderId, productName, quantity);
        }
        
        public BigDecimal calculateTotal(Long orderId) {
            Order order = orders.get(orderId);
            if (order == null) {
                throw new IllegalArgumentException("Order not found: " + orderId);
            }
            
            BigDecimal total = order.getItems().stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            order.setTotal(total);
            return total;
        }
        
        public Order processOrder(Long orderId) {
            Order order = orders.get(orderId);
            if (order == null) {
                throw new IllegalArgumentException("Order not found: " + orderId);
            }
            
            // Business logic for processing
            calculateTotal(orderId);
            order.setStatus("PROCESSED");
            
            log.info("Processed order: {} with total: ${}", orderId, order.getTotal());
            return order;
        }
    }
    
    /**
     * Configuration class
     */
    @Configuration
    @Profile("demo")
    public static class DemoConfiguration {
        
        @Bean
        public String applicationName() {
            return "Spring Demo Application";
        }
        
        @Bean
        public Map<String, Object> applicationSettings() {
            Map<String, Object> settings = new HashMap<>();
            settings.put("database.url", "jdbc:mysql://localhost:3306/demo");
            settings.put("cache.enabled", true);
            settings.put("max.connections", 20);
            return settings;
        }
    }
    
    /**
     * Component for demonstration
     */
    @Component
    @Slf4j
    public static class ApplicationStartupListener {
        
        @PostConstruct
        public void onStartup() {
            log.info("Application startup completed");
        }
        
        @PreDestroy
        public void onShutdown() {
            log.info("Application shutting down");
        }
    }
    
    // ============================================================================
    // DATA CLASSES
    // ============================================================================
    
    @Data
    @AllArgsConstructor
    public static class User {
        private Long id;
        private String name;
        private String email;
        private String role;
        private LocalDateTime createdAt;
    }
    
    @Data
    @AllArgsConstructor
    public static class Product {
        private Long id;
        private String name;
        private BigDecimal price;
        private String category;
    }
    
    @Data
    @AllArgsConstructor
    public static class Order {
        private Long id;
        private Long userId;
        private List<OrderItem> items;
        private BigDecimal total;
        private String status;
        private LocalDateTime createdAt;
    }
    
    @Data
    @AllArgsConstructor
    public static class OrderItem {
        private Long productId;
        private String productName;
        private BigDecimal price;
        private int quantity;
    }
    
    @Data
    @AllArgsConstructor
    public static class CreateUserRequest {
        private String name;
        private String email;
    }
    
    @Data
    @AllArgsConstructor
    public static class UpdateUserRequest {
        private String name;
        private String email;
    }
    
    // ============================================================================
    // EXCEPTIONS
    // ============================================================================
    
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}

/*
LIVE CODING TIPS:

1. START WITH .NET COMPARISONS:
   - Show Startup.cs vs @SpringBootApplication
   - Compare dependency injection approaches
   - Highlight annotation equivalencies

2. BUILD INCREMENTALLY:
   - Start with simple @Service
   - Add @Repository layer
   - Create @RestController
   - Show configuration options

3. INTERACTIVE ELEMENTS:
   - "How would you do this in .NET Core?"
   - "What Spring annotation would you use?"
   - "Where would this code go in MVC?"

4. COMMON GOTCHAS:
   - Constructor vs field injection
   - @Autowired vs constructor injection
   - Circular dependencies
   - Bean lifecycle understanding

5. PRACTICAL EXERCISES:
   - Build a simple REST API
   - Add validation
   - Implement error handling
   - Show testing strategies

6. CONFIGURATION DEMOS:
   - Properties file setup
   - Environment-specific configs
   - @Profile usage
   - @ConfigurationProperties

EXPECTED QUESTIONS:
- "Why use @Service instead of just @Component?"
- "What's the difference between @Autowired and constructor injection?"
- "How do I handle database connections?"
- "Where do I put business logic?"
- "How do I test Spring components?"
- "What's the Spring Boot equivalent of Program.cs?"

DEMO VARIATIONS:
- Can focus more on Spring Boot features
- Can include database integration (JPA)
- Can show security basics
- Can demonstrate testing with @SpringBootTest
- Can include actuator endpoints

SPRING BOOT STARTER DEPENDENCIES:
- spring-boot-starter-web: Web applications
- spring-boot-starter-data-jpa: Database access
- spring-boot-starter-security: Security
- spring-boot-starter-test: Testing
- spring-boot-starter-actuator: Monitoring
*/