# Anti-Patterns Deep Dive
## Common Java Mistakes for .NET Developers

> **Comprehensive analysis** of the most common anti-patterns .NET developers encounter when transitioning to Java, with detailed explanations, examples, and proven solutions.

## 🎯 What You'll Master

By the end of this guide, you'll understand:
- **WHY** these anti-patterns occur specifically for .NET developers
- **HOW** to identify these patterns in existing codebases quickly
- **WHEN** these patterns cause problems and performance issues
- **WHERE** to focus code review efforts for maximum impact

**Time Investment**: 40-60 minutes of focused reading + hands-on practice with examples

## 🚨 **The Top 10 Anti-Patterns**

### **1. String Comparison Anti-Pattern**

#### **The Problem: Using == Instead of .equals()**

**❌ Anti-Pattern (C# Style):**
```java
public class UserAuthenticationService {
    
    // ❌ WRONG: This will fail unpredictably
    public boolean authenticateUser(String username, String password) {
        String storedPassword = getUserPassword(username);
        
        // This works in C# but fails in Java!
        if (password == storedPassword) {  // ❌ Reference comparison
            return true;
        }
        
        return false;
    }
    
    // ❌ WRONG: Even worse - mixed reference and content comparison
    public UserRole determineUserRole(String username) {
        if (username == "admin") {  // ❌ Will almost always be false
            return UserRole.ADMIN;
        } else if (username.equals("user")) {  // ✅ Correct, but inconsistent
            return UserRole.USER;
        }
        return UserRole.GUEST;
    }
}
```

**✅ Correct Pattern:**
```java
public class UserAuthenticationService {
    
    // ✅ CORRECT: Always use .equals() for content comparison
    public boolean authenticateUser(String username, String password) {
        String storedPassword = getUserPassword(username);
        
        // Null-safe comparison
        return Objects.equals(password, storedPassword);
    }
    
    // ✅ CORRECT: Consistent use of .equals() with null safety
    public UserRole determineUserRole(String username) {
        if ("admin".equals(username)) {  // ✅ Constant first prevents NPE
            return UserRole.ADMIN;
        } else if ("user".equals(username)) {
            return UserRole.USER;
        }
        return UserRole.GUEST;
    }
    
    // ✅ BEST: Use switch expressions (Java 14+)
    public UserRole determineUserRoleModern(String username) {
        return switch (username != null ? username : "") {
            case "admin" -> UserRole.ADMIN;
            case "user" -> UserRole.USER;
            case "moderator" -> UserRole.MODERATOR;
            default -> UserRole.GUEST;
        };
    }
}
```

**🔍 Why This Happens:**
- In C#, `==` operator is overloaded for strings to perform content comparison
- In Java, `==` always compares references (memory addresses)
- String interning can make this work sometimes, leading to false confidence

**💡 Detection Strategy:**
```java
// Use this test to identify string comparison issues
@Test
void demonstrateStringComparisonProblem() {
    String str1 = "hello";
    String str2 = new String("hello");
    String str3 = "hello";
    
    // These show the difference between == and .equals()
    assertThat(str1 == str2).isFalse();   // Different objects
    assertThat(str1 == str3).isTrue();    // String pool optimization
    assertThat(str1.equals(str2)).isTrue(); // Content comparison
    
    // This is why you MUST use .equals() in Java
}
```

---

### **2. Null Handling Anti-Pattern**

#### **The Problem: Returning null Instead of Optional**

**❌ Anti-Pattern (.NET Style):**
```java
public class UserService {
    
    // ❌ WRONG: Returning null like C# nullable types
    public User findUserByEmail(String email) {
        // Database lookup
        User user = userRepository.findByEmail(email);
        return user; // May return null - caller doesn't know!
    }
    
    // ❌ WRONG: Null checks scattered throughout code
    public void processUserOrder(String email, Order order) {
        User user = findUserByEmail(email);
        
        if (user != null) {  // ❌ Manual null checking
            if (user.getPreferences() != null) {
                if (user.getPreferences().getNotificationSettings() != null) {
                    // Deeply nested null checks
                    sendOrderNotification(user, order);
                }
            }
        }
    }
    
    // ❌ WRONG: Inconsistent null handling
    public List<User> findActiveUsers() {
        List<User> users = userRepository.findActive();
        return users != null ? users : new ArrayList<>(); // Sometimes defensive, sometimes not
    }
}
```

**✅ Correct Pattern:**
```java
public class UserService {
    
    // ✅ CORRECT: Use Optional to make null possibility explicit
    public Optional<User> findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return Optional.ofNullable(user);
    }
    
    // ✅ CORRECT: Chain Optional operations
    public void processUserOrder(String email, Order order) {
        findUserByEmail(email)
            .filter(User::isActive)
            .map(User::getPreferences)
            .map(UserPreferences::getNotificationSettings)
            .ifPresent(settings -> sendOrderNotification(email, order, settings));
    }
    
    // ✅ CORRECT: Never return null for collections
    public List<User> findActiveUsers() {
        List<User> users = userRepository.findActive();
        return users != null ? users : Collections.emptyList();
    }
    
    // ✅ BEST: Use Optional for complex null handling
    public String getUserDisplayName(String email) {
        return findUserByEmail(email)
            .map(User::getDisplayName)
            .filter(name -> !name.isBlank())
            .orElse("Unknown User");
    }
    
    // ✅ EXCELLENT: Combine Optional with validation
    public void updateUserProfile(String email, UserProfileUpdate update) {
        findUserByEmail(email)
            .filter(User::isActive)
            .ifPresentOrElse(
                user -> {
                    user.updateProfile(update);
                    userRepository.save(user);
                },
                () -> {
                    throw new UserNotFoundException("User not found or inactive: " + email);
                }
            );
    }
}
```

**🔍 Why This Happens:**
- .NET has nullable reference types (C# 8+) with compiler warnings
- Java's Optional is a library solution, not built into the type system
- .NET developers are used to `?.` null-conditional operator

**💡 Best Practices:**
```java
// ✅ Optional usage guidelines
public class OptionalBestPractices {
    
    // ✅ DO: Use Optional for return types that might be absent
    public Optional<User> findById(Long id) { /* */ }
    
    // ❌ DON'T: Use Optional for parameters
    public void updateUser(Optional<User> user) { /* BAD */ }
    
    // ✅ DO: Use Optional as parameter when absence is meaningful
    public void processOrder(Order order, Optional<DiscountCode> discount) { /* OK */ }
    
    // ❌ DON'T: Use Optional for collections
    public Optional<List<User>> getUsers() { /* BAD - return empty list instead */ }
    
    // ✅ DO: Use Optional.ofNullable() when source might be null
    public Optional<String> getConfigValue(String key) {
        String value = System.getProperty(key);
        return Optional.ofNullable(value);
    }
    
    // ✅ DO: Chain operations instead of nested if statements
    public String processUserData(Long userId) {
        return findById(userId)
            .filter(User::isVerified)
            .map(User::getProfile)
            .map(Profile::getDisplayName)
            .orElse("Anonymous");
    }
}
```

---

### **3. Property Access Anti-Pattern**

#### **The Problem: Public Fields Instead of Encapsulation**

**❌ Anti-Pattern (.NET Style):**
```java
public class User {
    // ❌ WRONG: Public fields like C# auto-properties
    public String name;
    public String email;
    public int age;
    public boolean isActive;
    
    // ❌ WRONG: Inconsistent access patterns
    public String getName() {
        return name;
    }
    
    // ❌ WRONG: No validation in setters
    public void setAge(int age) {
        this.age = age; // No validation!
    }
}

// ❌ WRONG: Using the class with direct field access
public class UserService {
    public void updateUserInfo(User user, String newEmail) {
        user.email = newEmail; // ❌ Direct field modification
        user.isActive = true;  // ❌ No validation or business logic
    }
}
```

**✅ Correct Pattern:**
```java
// ✅ CORRECT: Proper encapsulation with Lombok
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    
    @NonNull
    private String name;
    
    @Email
    private String email;
    
    @Min(0)
    @Max(150)
    private int age;
    
    private boolean active;
    
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    
    // Custom validation method
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email.toLowerCase().trim();
    }
    
    // Business logic in domain object
    public boolean isEligibleForDiscount() {
        return active && age >= 65;
    }
    
    // Controlled state transitions
    public void activate() {
        if (!active) {
            this.active = true;
            this.lastLoginAt = LocalDateTime.now();
        }
    }
}

// ✅ CORRECT: Using proper encapsulation
public class UserService {
    public void updateUserInfo(User user, String newEmail) {
        user.setEmail(newEmail); // ✅ Goes through validation
        user.activate();         // ✅ Uses business logic
    }
}
```

**✅ Alternative: Record for Immutable Data (Java 14+):**
```java
// ✅ EXCELLENT: Use records for immutable data transfer objects
public record UserDto(
    @NotNull Long id,
    @NotBlank String name,
    @Email String email,
    @Min(0) @Max(150) int age,
    boolean active
) {
    // Custom constructor for validation
    public UserDto {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
    }
    
    // Additional methods
    public boolean isEligibleForDiscount() {
        return active && age >= 65;
    }
    
    // Factory methods
    public static UserDto from(User user) {
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getAge(),
            user.isActive()
        );
    }
}
```

---

### **4. Collection Initialization Anti-Pattern**

#### **The Problem: Using C# Collection Initializer Syntax**

**❌ Anti-Pattern (.NET Style):**
```java
public class ProductService {
    
    // ❌ WRONG: Trying to use C# collection initializer syntax
    // This doesn't compile in Java!
    /*
    private List<String> categories = new ArrayList<String>() {
        "Electronics", "Books", "Clothing", "Home"  // ❌ Syntax error
    };
    */
    
    // ❌ WRONG: Manual element addition
    private List<String> categories = new ArrayList<>();
    
    public ProductService() {
        categories.add("Electronics");
        categories.add("Books");
        categories.add("Clothing");
        categories.add("Home");
        // Verbose and error-prone
    }
    
    // ❌ WRONG: Inefficient array conversion
    public List<Product> getProductsByCategory(String category) {
        Product[] products = getProductArray(category);
        List<Product> productList = new ArrayList<>();
        for (Product product : products) {
            productList.add(product);
        }
        return productList;
    }
    
    // ❌ WRONG: Creating unnecessary mutable collections
    public Set<String> getAllowedCategories() {
        Set<String> allowed = new HashSet<>();
        allowed.add("Electronics");
        allowed.add("Books");
        allowed.add("Clothing");
        return allowed; // Mutable set returned - dangerous!
    }
}
```

**✅ Correct Pattern:**
```java
public class ProductService {
    
    // ✅ CORRECT: Use Arrays.asList() for small fixed lists
    private static final List<String> CATEGORIES = Arrays.asList(
        "Electronics", "Books", "Clothing", "Home"
    );
    
    // ✅ BETTER: Use List.of() for immutable lists (Java 9+)
    private static final List<String> CATEGORIES_IMMUTABLE = List.of(
        "Electronics", "Books", "Clothing", "Home"
    );
    
    // ✅ BEST: Use Set.of() for unique values (Java 9+)
    private static final Set<String> ALLOWED_CATEGORIES = Set.of(
        "Electronics", "Books", "Clothing", "Home"
    );
    
    // ✅ CORRECT: Efficient array to list conversion
    public List<Product> getProductsByCategory(String category) {
        Product[] products = getProductArray(category);
        return Arrays.asList(products);
    }
    
    // ✅ BETTER: Stream-based conversion with filtering
    public List<Product> getActiveProductsByCategory(String category) {
        return Arrays.stream(getProductArray(category))
            .filter(Product::isActive)
            .collect(Collectors.toList());
    }
    
    // ✅ EXCELLENT: Return immutable collections
    public Set<String> getAllowedCategories() {
        return Set.copyOf(ALLOWED_CATEGORIES); // Defensive copy
    }
    
    // ✅ MODERN: Use collection factory methods (Java 9+)
    public Map<String, List<String>> getCategoryHierarchy() {
        return Map.of(
            "Electronics", List.of("Phones", "Laptops", "Tablets"),
            "Books", List.of("Fiction", "Non-Fiction", "Technical"),
            "Clothing", List.of("Men", "Women", "Children")
        );
    }
    
    // ✅ BUILDER PATTERN: For complex collections
    public List<Product> buildProductCatalog() {
        return Stream.of(
                createProduct("iPhone", "Electronics"),
                createProduct("Java Book", "Books"),
                createProduct("T-Shirt", "Clothing")
            )
            .filter(Objects::nonNull)
            .collect(Collectors.toUnmodifiableList());
    }
}
```

**🔧 Collection Initialization Patterns:**
```java
public class CollectionInitializationExamples {
    
    // ✅ Static initialization for constants
    private static final Map<String, Integer> HTTP_STATUS_CODES = Map.of(
        "OK", 200,
        "NOT_FOUND", 404,
        "INTERNAL_ERROR", 500
    );
    
    // ✅ Builder pattern for complex initialization
    public static List<User> createTestUsers() {
        return Stream.of(
                User.builder().name("John").email("john@test.com").age(30).build(),
                User.builder().name("Jane").email("jane@test.com").age(25).build(),
                User.builder().name("Bob").email("bob@test.com").age(35).build()
            )
            .collect(Collectors.toList());
    }
    
    // ✅ Fluent initialization
    public Set<Permission> createAdminPermissions() {
        return EnumSet.of(
            Permission.READ,
            Permission.WRITE,
            Permission.DELETE,
            Permission.ADMIN
        );
    }
    
    // ✅ Conditional initialization
    public List<String> createMenuItems(UserRole role) {
        var menuItems = new ArrayList<>(List.of("Dashboard", "Profile"));
        
        if (role == UserRole.ADMIN) {
            menuItems.addAll(List.of("User Management", "System Settings"));
        }
        
        if (role == UserRole.MODERATOR) {
            menuItems.add("Content Moderation");
        }
        
        return Collections.unmodifiableList(menuItems);
    }
}
```

---

### **5. Stream Reuse Anti-Pattern**

#### **The Problem: Treating Streams Like LINQ Queries**

**❌ Anti-Pattern (LINQ Style):**
```java
public class OrderAnalysisService {
    
    // ❌ WRONG: Trying to reuse streams like LINQ queries
    public OrderAnalysisReport analyzeOrders(List<Order> orders) {
        // This will fail after first use!
        Stream<Order> orderStream = orders.stream()
            .filter(order -> order.getStatus() == OrderStatus.COMPLETED);
        
        // First use - works
        long totalOrders = orderStream.count();
        
        // Second use - throws IllegalStateException!
        double averageAmount = orderStream
            .mapToDouble(Order::getTotalAmount)
            .average()
            .orElse(0.0);  // ❌ CRASH: stream has already been operated upon
        
        // Third use - also fails
        List<Order> largeOrders = orderStream
            .filter(order -> order.getTotalAmount() > 1000)
            .collect(Collectors.toList());  // ❌ CRASH
        
        return new OrderAnalysisReport(totalOrders, averageAmount, largeOrders);
    }
    
    // ❌ WRONG: Storing streams as instance variables
    private final Stream<Product> productStream;
    
    public OrderAnalysisService(List<Product> products) {
        // ❌ Streams shouldn't be stored long-term
        this.productStream = products.stream()
            .filter(Product::isActive);
    }
}
```

**✅ Correct Pattern:**
```java
public class OrderAnalysisService {
    
    // ✅ CORRECT: Create new stream for each operation
    public OrderAnalysisReport analyzeOrders(List<Order> orders) {
        List<Order> completedOrders = orders.stream()
            .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
            .collect(Collectors.toList());  // Collect to reusable collection
        
        long totalOrders = completedOrders.size();
        
        double averageAmount = completedOrders.stream()  // New stream
            .mapToDouble(Order::getTotalAmount)
            .average()
            .orElse(0.0);
        
        List<Order> largeOrders = completedOrders.stream()  // Another new stream
            .filter(order -> order.getTotalAmount() > 1000)
            .collect(Collectors.toList());
        
        return new OrderAnalysisReport(totalOrders, averageAmount, largeOrders);
    }
    
    // ✅ BETTER: Use supplier to create streams on demand
    public OrderAnalysisReport analyzeOrdersWithSupplier(List<Order> orders) {
        Supplier<Stream<Order>> completedOrdersSupplier = () -> orders.stream()
            .filter(order -> order.getStatus() == OrderStatus.COMPLETED);
        
        long totalOrders = completedOrdersSupplier.get().count();
        
        double averageAmount = completedOrdersSupplier.get()
            .mapToDouble(Order::getTotalAmount)
            .average()
            .orElse(0.0);
        
        List<Order> largeOrders = completedOrdersSupplier.get()
            .filter(order -> order.getTotalAmount() > 1000)
            .collect(Collectors.toList());
        
        return new OrderAnalysisReport(totalOrders, averageAmount, largeOrders);
    }
    
    // ✅ BEST: Chain operations to avoid multiple stream creation
    public OrderAnalysisReport analyzeOrdersEfficiently(List<Order> orders) {
        List<Order> completedOrders = orders.stream()
            .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
            .collect(Collectors.toList());
        
        // Perform all calculations without additional streams when possible
        DoubleSummaryStatistics stats = completedOrders.stream()
            .mapToDouble(Order::getTotalAmount)
            .summaryStatistics();
        
        List<Order> largeOrders = completedOrders.stream()
            .filter(order -> order.getTotalAmount() > 1000)
            .collect(Collectors.toList());
        
        return new OrderAnalysisReport(
            stats.getCount(),
            stats.getAverage(),
            largeOrders
        );
    }
    
    // ✅ EXCELLENT: Use collectors for complex aggregations
    public Map<OrderStatus, OrderStats> analyzeOrdersByStatus(List<Order> orders) {
        return orders.stream()
            .collect(Collectors.groupingBy(
                Order::getStatus,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    orderList -> new OrderStats(
                        orderList.size(),
                        orderList.stream().mapToDouble(Order::getTotalAmount).average().orElse(0),
                        orderList.stream().mapToDouble(Order::getTotalAmount).sum()
                    )
                )
            ));
    }
}
```

---

### **6. Exception Handling Anti-Pattern**

#### **The Problem: Catching Generic Exception**

**❌ Anti-Pattern (.NET Style):**
```java
public class UserService {
    
    // ❌ WRONG: Catching generic Exception like C#
    public User createUser(String name, String email) {
        try {
            validateUserData(name, email);
            User user = new User(name, email);
            userRepository.save(user);
            emailService.sendWelcomeEmail(user);
            return user;
        } catch (Exception e) {  // ❌ Too broad - catches everything!
            // What type of error occurred? Database? Validation? Email?
            log.error("Failed to create user", e);
            throw new RuntimeException("User creation failed", e);
        }
    }
    
    // ❌ WRONG: Swallowing exceptions
    public void updateUserPreferences(Long userId, UserPreferences preferences) {
        try {
            User user = userRepository.findById(userId);
            user.setPreferences(preferences);
            userRepository.save(user);
        } catch (Exception e) {
            // ❌ Exception disappeared! Caller has no idea it failed
            log.warn("Failed to update preferences for user " + userId);
        }
    }
    
    // ❌ WRONG: Not handling checked exceptions appropriately
    public String readUserConfig(String filename) {
        try {
            return Files.readString(Paths.get(filename));
        } catch (IOException e) {
            // ❌ Converting checked to unchecked without context
            throw new RuntimeException(e);
        }
    }
}
```

**✅ Correct Pattern:**
```java
public class UserService {
    
    // ✅ CORRECT: Specific exception handling
    public User createUser(String name, String email) {
        try {
            validateUserData(name, email);
            User user = new User(name, email);
            userRepository.save(user);
            
            // Handle email failure separately - it's not critical
            try {
                emailService.sendWelcomeEmail(user);
            } catch (EmailServiceException e) {
                log.warn("Failed to send welcome email to {}", email, e);
                // Don't fail user creation because of email issues
            }
            
            return user;
        } catch (ValidationException e) {
            log.info("User creation failed due to validation: {}", e.getMessage());
            throw new InvalidUserDataException("Invalid user data: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            log.error("Database error during user creation", e);
            throw new UserCreationException("Failed to save user to database", e);
        }
    }
    
    // ✅ CORRECT: Proper error propagation
    public void updateUserPreferences(Long userId, UserPreferences preferences) 
            throws UserNotFoundException, InvalidPreferencesException {
        try {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
            
            validatePreferences(preferences);
            user.setPreferences(preferences);
            userRepository.save(user);
            
        } catch (ValidationException e) {
            throw new InvalidPreferencesException("Invalid preferences: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            log.error("Database error updating preferences for user {}", userId, e);
            throw new UserUpdateException("Failed to update user preferences", e);
        }
    }
    
    // ✅ CORRECT: Appropriate checked exception handling
    public Optional<String> readUserConfig(String filename) {
        try {
            String content = Files.readString(Paths.get(filename));
            return Optional.of(content);
        } catch (IOException e) {
            log.debug("Config file not found or unreadable: {}", filename);
            return Optional.empty();  // Convert to Optional instead of exception
        } catch (SecurityException e) {
            log.error("Security error reading config file: {}", filename, e);
            throw new ConfigurationException("Access denied to config file: " + filename, e);
        }
    }
    
    // ✅ EXCELLENT: Exception translation with context
    public void processUserBatch(List<UserRequest> requests) {
        List<Exception> errors = new ArrayList<>();
        List<User> successfulUsers = new ArrayList<>();
        
        for (UserRequest request : requests) {
            try {
                User user = createUser(request.getName(), request.getEmail());
                successfulUsers.add(user);
            } catch (InvalidUserDataException e) {
                errors.add(new BatchProcessingException(
                    "Failed to process user: " + request.getEmail(), e));
            } catch (UserCreationException e) {
                log.error("Critical error processing user batch", e);
                throw e; // Re-throw critical errors
            }
        }
        
        if (!errors.isEmpty()) {
            throw new BatchProcessingException(
                String.format("Batch processing completed with %d errors out of %d requests", 
                    errors.size(), requests.size()), errors);
        }
    }
}

// ✅ Custom exception hierarchy
public class UserServiceException extends Exception {
    public UserServiceException(String message) {
        super(message);
    }
    
    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class UserNotFoundException extends UserServiceException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

public class InvalidUserDataException extends UserServiceException {
    public InvalidUserDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

---

### **7. Dependency Injection Anti-Pattern**

#### **The Problem: Field Injection and Poor DI Practices**

**❌ Anti-Pattern (.NET Style):**
```java
@Service
public class OrderService {
    
    // ❌ WRONG: Field injection (like .NET [Inject])
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ShippingService shippingService;
    
    @Autowired
    private TaxService taxService;
    
    // ❌ WRONG: Too many dependencies - violates SRP
    // Hard to test, hidden dependencies, circular dependency risk
    
    public Order processOrder(OrderRequest request) {
        // Complex logic with many dependencies
        User user = userService.findById(request.getUserId());
        Product product = productService.findById(request.getProductId());
        
        if (!inventoryService.isAvailable(product, request.getQuantity())) {
            throw new InsufficientInventoryException();
        }
        
        BigDecimal tax = taxService.calculateTax(product.getPrice(), user.getLocation());
        PaymentResult payment = paymentService.processPayment(request.getPaymentInfo());
        
        if (payment.isSuccessful()) {
            Order order = new Order(user, product, request.getQuantity(), tax);
            shippingService.scheduleShipping(order);
            notificationService.sendOrderConfirmation(order);
            return order;
        }
        
        throw new PaymentFailedException();
    }
}
```

**✅ Correct Pattern:**
```java
// ✅ CORRECT: Constructor injection with focused responsibilities
@Service
@RequiredArgsConstructor
public class OrderProcessingService {
    
    private final OrderValidationService orderValidationService;
    private final PaymentProcessingService paymentProcessingService;
    private final OrderFulfillmentService orderFulfillmentService;
    
    // ✅ Single responsibility: orchestrate order processing
    public Order processOrder(OrderRequest request) {
        // Validate order
        OrderValidationResult validation = orderValidationService.validateOrder(request);
        if (!validation.isValid()) {
            throw new InvalidOrderException(validation.getErrors());
        }
        
        // Process payment
        PaymentResult payment = paymentProcessingService.processPayment(
            request.getPaymentInfo(), validation.getTotalAmount());
        
        if (!payment.isSuccessful()) {
            throw new PaymentFailedException(payment.getErrorMessage());
        }
        
        // Fulfill order
        return orderFulfillmentService.fulfillOrder(request, payment);
    }
}

// ✅ Separate service for validation concerns
@Service
@RequiredArgsConstructor
public class OrderValidationService {
    
    private final UserService userService;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final TaxService taxService;
    
    public OrderValidationResult validateOrder(OrderRequest request) {
        List<String> errors = new ArrayList<>();
        
        // Validate user
        Optional<User> userOpt = userService.findById(request.getUserId());
        if (userOpt.isEmpty()) {
            errors.add("User not found: " + request.getUserId());
            return OrderValidationResult.invalid(errors);
        }
        User user = userOpt.get();
        
        // Validate product
        Optional<Product> productOpt = productService.findById(request.getProductId());
        if (productOpt.isEmpty()) {
            errors.add("Product not found: " + request.getProductId());
            return OrderValidationResult.invalid(errors);
        }
        Product product = productOpt.get();
        
        // Validate inventory
        if (!inventoryService.isAvailable(product, request.getQuantity())) {
            errors.add("Insufficient inventory for product: " + product.getName());
        }
        
        if (!errors.isEmpty()) {
            return OrderValidationResult.invalid(errors);
        }
        
        // Calculate total with tax
        BigDecimal tax = taxService.calculateTax(product.getPrice(), user.getLocation());
        BigDecimal total = product.getPrice()
            .multiply(BigDecimal.valueOf(request.getQuantity()))
            .add(tax);
        
        return OrderValidationResult.valid(user, product, total);
    }
}

// ✅ Separate service for payment concerns
@Service
@RequiredArgsConstructor
public class PaymentProcessingService {
    
    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;
    
    @Transactional
    public PaymentResult processPayment(PaymentInfo paymentInfo, BigDecimal amount) {
        try {
            PaymentResult result = paymentGateway.charge(paymentInfo, amount);
            
            // Record payment attempt
            Payment payment = Payment.builder()
                .amount(amount)
                .paymentInfo(paymentInfo)
                .result(result)
                .timestamp(LocalDateTime.now())
                .build();
            
            paymentRepository.save(payment);
            
            return result;
        } catch (PaymentGatewayException e) {
            log.error("Payment gateway error", e);
            return PaymentResult.failed("Payment gateway unavailable");
        }
    }
}

// ✅ Separate service for fulfillment concerns
@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {
    
    private final OrderRepository orderRepository;
    private final ShippingService shippingService;
    private final NotificationService notificationService;
    
    @Transactional
    public Order fulfillOrder(OrderRequest request, PaymentResult payment) {
        // Create order
        Order order = Order.builder()
            .userId(request.getUserId())
            .productId(request.getProductId())
            .quantity(request.getQuantity())
            .paymentId(payment.getPaymentId())
            .status(OrderStatus.CONFIRMED)
            .createdAt(LocalDateTime.now())
            .build();
        
        order = orderRepository.save(order);
        
        // Schedule shipping (async)
        shippingService.scheduleShipping(order);
        
        // Send notifications (async)
        notificationService.sendOrderConfirmation(order);
        
        return order;
    }
}
```

---

### **8. Lombok Misuse Anti-Pattern**

#### **The Problem: Over-relying on @Data and Creating Mutable Objects**

**❌ Anti-Pattern:**
```java
// ❌ WRONG: @Data creates mutable objects with public setters
@Data  // Generates getters, setters, equals, hashCode, toString
public class User {
    private Long id;
    private String name;
    private String email;
    private Set<Role> roles;
    private LocalDateTime createdAt;
}

// ❌ WRONG: This allows uncontrolled mutation
public class UserService {
    public void processUser(User user) {
        // Anyone can modify user state!
        user.setId(999L);  // ❌ ID should be immutable
        user.setCreatedAt(LocalDateTime.now());  // ❌ Creation time should be immutable
        user.getRoles().clear();  // ❌ Direct collection mutation
        user.getRoles().add(Role.ADMIN);  // ❌ Bypasses business logic
    }
}

// ❌ WRONG: @Data with collections creates mutable references
@Data
public class Order {
    private Long id;
    private List<OrderItem> items;  // ❌ Direct access to mutable collection
    private OrderStatus status;
    
    // @Data generates this setter, which is dangerous:
    // public void setItems(List<OrderItem> items) { this.items = items; }
}
```

**✅ Correct Pattern:**
```java
// ✅ CORRECT: Controlled mutability with appropriate Lombok annotations
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    // ✅ ID should be immutable after creation
    @Setter(AccessLevel.NONE)
    private Long id;
    
    // ✅ Basic fields can have setters with validation
    @NonNull
    private String name;
    
    @Email
    private String email;
    
    // ✅ Collections should be protected
    @Setter(AccessLevel.NONE)
    private Set<Role> roles = new HashSet<>();
    
    // ✅ Creation timestamp should be immutable
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;
    
    // ✅ Custom methods for controlled mutation
    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }
    
    public void removeRole(Role role) {
        if (roles != null) {
            roles.remove(role);
        }
    }
    
    // ✅ Defensive copying for collections
    public Set<Role> getRoles() {
        return roles != null ? Set.copyOf(roles) : Set.of();
    }
    
    // ✅ Custom setter with validation
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email.toLowerCase().trim();
    }
}

// ✅ BETTER: Immutable object with Lombok
@Value  // Creates immutable object with getters, equals, hashCode, toString
@Builder
public class UserDto {
    Long id;
    @NonNull String name;
    @Email String email;
    Set<Role> roles;
    LocalDateTime createdAt;
    
    // ✅ @Value creates defensive copies of collections automatically
    // getRoles() returns unmodifiable view
    
    // ✅ Custom factory methods
    public static UserDto from(User user) {
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .roles(user.getRoles())
            .createdAt(user.getCreatedAt())
            .build();
    }
}

// ✅ EXCELLENT: Domain object with controlled mutability
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Builder
public class Order {
    
    private final Long id;
    private final Long customerId;
    private final LocalDateTime createdAt;
    
    // ✅ Mutable status with validation
    private OrderStatus status;
    
    // ✅ Protected collection with controlled access
    @Builder.Default
    private final List<OrderItem> items = new ArrayList<>();
    
    // ✅ Controlled mutation methods
    public void addItem(OrderItem item) {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot modify shipped order");
        }
        items.add(item);
    }
    
    public void removeItem(OrderItem item) {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot modify shipped order");
        }
        items.remove(item);
    }
    
    // ✅ Defensive copying for external access
    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }
    
    // ✅ Business logic for state transitions
    public void ship() {
        if (status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed orders can be shipped");
        }
        this.status = OrderStatus.SHIPPED;
    }
    
    public void cancel() {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel shipped order");
        }
        this.status = OrderStatus.CANCELLED;
    }
}
```

---

### **9. Resource Management Anti-Pattern**

#### **The Problem: Not Using Try-With-Resources**

**❌ Anti-Pattern (.NET Style):**
```java
public class FileProcessingService {
    
    // ❌ WRONG: Manual resource management like C# using
    public String readFile(String filename) throws IOException {
        FileInputStream fis = null;
        BufferedReader reader = null;
        
        try {
            fis = new FileInputStream(filename);
            reader = new BufferedReader(new InputStreamReader(fis));
            
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            
            return content.toString();
        } finally {
            // ❌ Verbose and error-prone cleanup
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // What to do with this exception?
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // What to do with this exception?
                }
            }
        }
    }
    
    // ❌ WRONG: Forgetting to close resources
    public void writeFile(String filename, String content) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write(content);
        // ❌ Resource leak! FileWriter never closed
    }
    
    // ❌ WRONG: Nested try blocks
    public void copyFile(String source, String destination) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        
        try {
            fis = new FileInputStream(source);
            try {
                fos = new FileOutputStream(destination);
                
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            } finally {
                if (fos != null) fos.close();
            }
        } finally {
            if (fis != null) fis.close();
        }
    }
}
```

**✅ Correct Pattern:**
```java
public class FileProcessingService {
    
    // ✅ CORRECT: Try-with-resources automatically closes resources
    public String readFile(String filename) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            return reader.lines()
                .collect(Collectors.joining("\n"));
        }
        // Automatically closes reader, even if exception occurs
    }
    
    // ✅ BETTER: Use modern Files API
    public String readFileModern(String filename) throws IOException {
        return Files.readString(Paths.get(filename));
    }
    
    // ✅ CORRECT: Multiple resources in try-with-resources
    public void copyFile(String source, String destination) throws IOException {
        try (InputStream fis = Files.newInputStream(Paths.get(source));
             OutputStream fos = Files.newOutputStream(Paths.get(destination))) {
            
            fis.transferTo(fos);
        }
        // Both streams automatically closed
    }
    
    // ✅ BETTER: Use modern Files API for copying
    public void copyFileModern(String source, String destination) throws IOException {
        Files.copy(Paths.get(source), Paths.get(destination), 
            StandardCopyOption.REPLACE_EXISTING);
    }
    
    // ✅ EXCELLENT: Process large files efficiently
    public void processLargeFile(String filename, Consumer<String> lineProcessor) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            lines.forEach(lineProcessor);
        }
        // Stream automatically closed, memory efficient
    }
    
    // ✅ Custom resource with try-with-resources
    public void processDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                processUser(rs.getString("name"), rs.getString("email"));
            }
        }
        // All resources automatically closed in reverse order
    }
    
    // ✅ Custom AutoCloseable resource
    public static class ManagedResource implements AutoCloseable {
        private final String resourceName;
        private boolean closed = false;
        
        public ManagedResource(String resourceName) {
            this.resourceName = resourceName;
            System.out.println("Opening resource: " + resourceName);
        }
        
        public void doSomething() {
            if (closed) {
                throw new IllegalStateException("Resource is closed");
            }
            System.out.println("Using resource: " + resourceName);
        }
        
        @Override
        public void close() {
            if (!closed) {
                System.out.println("Closing resource: " + resourceName);
                closed = true;
            }
        }
    }
    
    // ✅ Usage of custom resource
    public void useCustomResource() {
        try (ManagedResource resource = new ManagedResource("MyResource")) {
            resource.doSomething();
        } // Automatically calls close()
    }
}
```

---

### **10. Performance Anti-Pattern**

#### **The Problem: Inefficient String Concatenation and Boxing**

**❌ Anti-Pattern:**
```java
public class ReportGenerator {
    
    // ❌ WRONG: String concatenation in loop
    public String generateUserReport(List<User> users) {
        String report = "User Report\n";
        report += "Generated on: " + LocalDateTime.now() + "\n";
        report += "Total users: " + users.size() + "\n\n";
        
        for (User user : users) {
            // ❌ Creates new String objects in each iteration!
            report += "ID: " + user.getId() + "\n";
            report += "Name: " + user.getName() + "\n";
            report += "Email: " + user.getEmail() + "\n";
            report += "Created: " + user.getCreatedAt() + "\n";
            report += "---\n";
        }
        
        return report;
    }
    
    // ❌ WRONG: Boxing primitives unnecessarily
    public List<Integer> processNumbers(int[] numbers) {
        List<Integer> result = new ArrayList<>();
        
        for (int num : numbers) {
            // ❌ Boxing on every iteration
            Integer boxed = Integer.valueOf(num);
            if (boxed > 100) {
                result.add(boxed);
            }
        }
        
        return result;
    }
    
    // ❌ WRONG: Inefficient collection operations
    public Map<String, Integer> countUsersByDepartment(List<User> users) {
        Map<String, Integer> counts = new HashMap<>();
        
        for (User user : users) {
            String dept = user.getDepartment();
            // ❌ Multiple map lookups
            if (counts.containsKey(dept)) {
                counts.put(dept, counts.get(dept) + 1);
            } else {
                counts.put(dept, 1);
            }
        }
        
        return counts;
    }
}
```

**✅ Correct Pattern:**
```java
public class ReportGenerator {
    
    // ✅ CORRECT: Use StringBuilder for string concatenation
    public String generateUserReport(List<User> users) {
        StringBuilder report = new StringBuilder();
        report.append("User Report\n");
        report.append("Generated on: ").append(LocalDateTime.now()).append("\n");
        report.append("Total users: ").append(users.size()).append("\n\n");
        
        for (User user : users) {
            report.append("ID: ").append(user.getId()).append("\n");
            report.append("Name: ").append(user.getName()).append("\n");
            report.append("Email: ").append(user.getEmail()).append("\n");
            report.append("Created: ").append(user.getCreatedAt()).append("\n");
            report.append("---\n");
        }
        
        return report.toString();
    }
    
    // ✅ BETTER: Use Streams for complex string building
    public String generateUserReportStreams(List<User> users) {
        String header = String.format("User Report\nGenerated on: %s\nTotal users: %d\n\n",
            LocalDateTime.now(), users.size());
        
        String userDetails = users.stream()
            .map(user -> String.format(
                "ID: %d\nName: %s\nEmail: %s\nCreated: %s\n---",
                user.getId(), user.getName(), user.getEmail(), user.getCreatedAt()))
            .collect(Collectors.joining("\n"));
        
        return header + userDetails;
    }
    
    // ✅ CORRECT: Use primitive streams to avoid boxing
    public List<Integer> processNumbers(int[] numbers) {
        return Arrays.stream(numbers)
            .filter(num -> num > 100)
            .boxed()  // Only box when necessary
            .collect(Collectors.toList());
    }
    
    // ✅ BETTER: Use IntStream for primitive operations
    public IntSummaryStatistics analyzeNumbers(int[] numbers) {
        return Arrays.stream(numbers)
            .filter(num -> num > 0)
            .summaryStatistics();  // No boxing at all
    }
    
    // ✅ CORRECT: Use merge for efficient counting
    public Map<String, Integer> countUsersByDepartment(List<User> users) {
        Map<String, Integer> counts = new HashMap<>();
        
        for (User user : users) {
            counts.merge(user.getDepartment(), 1, Integer::sum);
        }
        
        return counts;
    }
    
    // ✅ BEST: Use Streams groupingBy collector
    public Map<String, Long> countUsersByDepartmentStreams(List<User> users) {
        return users.stream()
            .collect(Collectors.groupingBy(
                User::getDepartment,
                Collectors.counting()
            ));
    }
    
    // ✅ EXCELLENT: Optimize for different data sizes
    public String generateLargeReport(List<User> users) {
        if (users.size() < 100) {
            // Small dataset: simple approach
            return generateUserReportStreams(users);
        }
        
        // Large dataset: optimize for memory and performance
        StringBuilder report = new StringBuilder(users.size() * 100); // Pre-size
        report.append("User Report\n");
        report.append("Generated on: ").append(LocalDateTime.now()).append("\n");
        report.append("Total users: ").append(users.size()).append("\n\n");
        
        // Process in batches to control memory usage
        int batchSize = 1000;
        for (int i = 0; i < users.size(); i += batchSize) {
            int end = Math.min(i + batchSize, users.size());
            List<User> batch = users.subList(i, end);
            
            for (User user : batch) {
                report.append("ID: ").append(user.getId())
                      .append(", Name: ").append(user.getName())
                      .append(", Email: ").append(user.getEmail())
                      .append("\n");
            }
        }
        
        return report.toString();
    }
}
```

---

## 🔍 **Reference Code Examples**

### **Working Anti-Pattern Examples in Repository**

All anti-patterns covered in this guide have working examples with fixes:

| Anti-Pattern | Reference Directory | What It Demonstrates |
|-------------|-------------------|---------------------|
| **String Comparison** | [`antipatterns/stringcomparison/`](../../src/main/java/com/coherentsolutions/session1/antipatterns/stringcomparison/) | `==` vs `.equals()` with fixes |
| **Null Handling** | [`antipatterns/nullhandling/`](../../src/main/java/com/coherentsolutions/session1/antipatterns/nullhandling/) | `null` vs `Optional<T>` patterns |
| **Properties** | [`antipatterns/properties/`](../../src/main/java/com/coherentsolutions/session1/antipatterns/properties/) | Field access vs encapsulation |
| **Collections** | [`antipatterns/collections/`](../../src/main/java/com/coherentsolutions/session1/antipatterns/collections/) | Collection initialization issues |
| **Streams** | [`antipatterns/streams/`](../../src/main/java/com/coherentsolutions/session1/antipatterns/streams/) | Stream reuse problems |
| **DI Patterns** | [`antipatterns/di/`](../../src/main/java/com/coherentsolutions/session1/antipatterns/di/) | Dependency injection mistakes |

### **Detection Tools and Commands**

| Tool | Command | What It Finds |
|------|---------|---------------|
| **SpotBugs** | `mvn spotbugs:check` | Common bug patterns |
| **PMD** | `mvn pmd:check` | Code quality issues |
| **Checkstyle** | `mvn checkstyle:check` | Style violations |
| **SonarQube** | `mvn sonar:sonar` | Comprehensive analysis |

---

## ✅ **Anti-Pattern Detection Checklist**

### **Code Review Guidelines**

When reviewing Java code from .NET developers, look for:

#### **String Handling** ✅
- [ ] All string comparisons use `.equals()` or `Objects.equals()`
- [ ] String constants are placed first in comparisons (`"constant".equals(variable)`)
- [ ] StringBuilder is used for string concatenation in loops
- [ ] No use of `==` for string comparison

#### **Null Safety** ✅
- [ ] Methods return `Optional<T>` instead of null when appropriate
- [ ] No scattered null checks; use Optional chaining
- [ ] Collections never return null (return empty collections)
- [ ] Proper use of `Optional.ofNullable()` and null-safe operations

#### **Collection Usage** ✅
- [ ] Proper collection initialization (no C# syntax attempts)
- [ ] Immutable collections returned from public methods
- [ ] Appropriate collection types chosen for use case
- [ ] Stream operations create new streams, not reused

#### **Exception Handling** ✅
- [ ] Specific exceptions caught, not generic `Exception`
- [ ] Proper exception translation and context preservation
- [ ] No swallowed exceptions without logging
- [ ] Checked exceptions handled appropriately

#### **Dependency Injection** ✅
- [ ] Constructor injection used instead of field injection
- [ ] Services have single responsibility
- [ ] No circular dependencies
- [ ] Proper use of Spring annotations

### **Automated Detection Setup**

**Maven Configuration for Anti-Pattern Detection:**
```xml
<build>
    <plugins>
        <!-- SpotBugs for bug pattern detection -->
        <plugin>
            <groupId>com.github.spotbugs</groupId>
            <artifactId>spotbugs-maven-plugin</artifactId>
            <version>4.7.3.6</version>
            <configuration>
                <effort>Max</effort>
                <threshold>Low</threshold>
                <excludeFilterFile>spotbugs-exclude.xml</excludeFilterFile>
            </configuration>
        </plugin>
        
        <!-- PMD for code quality -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-pmd-plugin</artifactId>
            <version>3.21.2</version>
            <configuration>
                <rulesets>
                    <ruleset>/category/java/bestpractices.xml</ruleset>
                    <ruleset>/category/java/errorprone.xml</ruleset>
                </rulesets>
            </configuration>
        </plugin>
    </plugins>
</build>
```

---

## 🎯 **Mastery Verification**

### **Anti-Pattern Recognition Skills**

After completing this guide, test yourself:

#### **Pattern Identification** ✅
- [ ] Spot string comparison issues in code reviews
- [ ] Identify null handling problems and suggest Optional usage
- [ ] Recognize stream reuse attempts and provide fixes
- [ ] Find dependency injection anti-patterns

#### **Quick Fixes** ✅
- [ ] Convert `==` string comparisons to `.equals()` safely
- [ ] Refactor null-returning methods to use Optional
- [ ] Replace field injection with constructor injection
- [ ] Fix collection initialization patterns

#### **Prevention Strategies** ✅
- [ ] Set up automated tools to catch anti-patterns
- [ ] Create code review checklists for teams
- [ ] Design APIs that prevent common mistakes
- [ ] Mentor other .NET developers transitioning to Java

### **Practical Exercises**

**Exercise 1: Code Review Challenge**
```java
// Review this code and identify all anti-patterns
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;
    
    public User findUser(String email) {
        // Implementation with anti-patterns
        return null;
    }
    
    public String processOrder(OrderRequest request) {
        // Implementation with multiple anti-patterns
    }
}
```

**Exercise 2: Refactoring Challenge**
```java
// Refactor this legacy code to modern Java standards
public class LegacyUserManager {
    // Contains multiple anti-patterns
    // Your task: Fix all issues while maintaining functionality
}
```

---

## 🚀 **Next Steps**

### **Continue Your Journey**

Now that you can identify and fix anti-patterns:

1. **[Exercises Walkthrough](exercises-walkthrough.md)** - Practice with hands-on examples
2. **Code Review Practice** - Review existing codebases for these patterns
3. **Team Training** - Share knowledge with other .NET developers

### **Advanced Topics**

- **Performance Profiling**: Finding performance anti-patterns
- **Security Anti-Patterns**: Java-specific security issues
- **Testing Anti-Patterns**: Common testing mistakes
- **Architecture Anti-Patterns**: Design-level issues

---

**You've mastered Java anti-pattern recognition! 🔍**

> *"The best way to write good Java code is to first understand what bad Java code looks like - especially when you're coming from a different language ecosystem."*