# Dependency Injection Deep Dive
## Spring IoC Container for .NET Developers

> **Comprehensive guide** to Spring's dependency injection with extensive comparisons to Microsoft.Extensions.DependencyInjection, focusing on enterprise patterns and best practices.

## 🎯 What You'll Master

By the end of this guide, you'll understand:
- **WHY** Spring's DI approach differs from .NET Core's built-in DI container
- **HOW** to design service layers using Spring's IoC patterns effectively
- **WHEN** to use different injection methods and bean scopes appropriately
- **WHERE** common DI anti-patterns occur and how to avoid them

**Time Investment**: 45-60 minutes of focused reading + 2-3 hours of hands-on practice

## 🏗️ **Dependency Injection Philosophy Comparison**

### **Design Philosophy: Container-Centric vs. Framework-Integrated**

**Spring's Container-Centric Approach:**
```java
// Spring: The ApplicationContext IS the application
@SpringBootApplication
public class ECommerceApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ECommerceApplication.class, args);
        
        // The container manages the entire application lifecycle
        UserService userService = context.getBean(UserService.class);
        // Container handles all dependency resolution, lifecycle, proxying
    }
}

// Everything is a bean in the container
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    // Constructor injection - container automatically resolves dependencies
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
}
```

**.NET Core's Framework-Integrated Approach:**
```csharp
// .NET: DI is integrated into the web framework
public class Program
{
    public static void Main(string[] args)
    {
        var builder = WebApplication.CreateBuilder(args);
        
        // Configure services in the DI container
        builder.Services.AddScoped<IUserService, UserService>();
        builder.Services.AddScoped<IUserRepository, UserRepository>();
        
        var app = builder.Build();
        // DI container is part of the framework, not the focus
    }
}

// Services are registered explicitly
public class UserService : IUserService
{
    private readonly IUserRepository _userRepository;
    private readonly IEmailService _emailService;
    
    public UserService(IUserRepository userRepository, IEmailService emailService)
    {
        _userRepository = userRepository;
        _emailService = emailService;
    }
}
```

**🎯 Key Philosophical Differences:**
- **Spring**: "Everything is a bean" - comprehensive lifecycle management
- **.NET**: "DI is a tool" - lightweight container for framework integration

---

## 🔧 **Spring Annotations vs .NET Attributes**

### **Service Registration Patterns**

**Spring Annotation-Based Registration:**
```java
// Automatic component scanning and registration
@Component    // Generic component
public class ConfigurationManager { }

@Service      // Business logic layer
public class OrderService {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    
    // Constructor injection - no additional annotations needed
    public OrderService(PaymentService paymentService, InventoryService inventoryService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }
}

@Repository   // Data access layer
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    
    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

@Controller   // Web layer
public class OrderController {
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
}
```

**.NET Explicit Registration:**
```csharp
// Manual registration in Program.cs
public class Program
{
    public static void Main(string[] args)
    {
        var builder = WebApplication.CreateBuilder(args);
        
        // Explicit service registration
        builder.Services.AddScoped<IOrderService, OrderService>();
        builder.Services.AddScoped<IPaymentService, PaymentService>();
        builder.Services.AddScoped<IInventoryService, InventoryService>();
        builder.Services.AddScoped<IOrderRepository, OrderRepository>();
        
        // Or using reflection-based auto-registration (third-party)
        builder.Services.Scan(scan => scan
            .FromAssemblyOf<IOrderService>()
            .AddClasses(classes => classes.AssignableTo<IOrderService>())
            .AsImplementedInterfaces()
            .WithScopedLifetime());
    }
}
```

**⚖️ Trade-offs:**
- **Spring**: Less explicit, but automatic discovery and lifecycle management
- **.NET**: More explicit control, but requires manual registration or third-party tools

### **Configuration Approaches**

**Spring Java Configuration:**
```java
@Configuration
public class ServiceConfiguration {
    
    // Bean definition method
    @Bean
    public EmailService emailService() {
        return new EmailService("smtp.company.com", 587);
    }
    
    // Bean with dependencies
    @Bean
    public UserService userService(UserRepository userRepository, EmailService emailService) {
        return new UserService(userRepository, emailService);
    }
    
    // Conditional bean creation
    @Bean
    @ConditionalOnProperty(name = "feature.cache.enabled", havingValue = "true")
    public CacheService cacheService() {
        return new RedisCacheService();
    }
    
    // Profile-specific beans
    @Bean
    @Profile("development")
    public MockPaymentService paymentService() {
        return new MockPaymentService();
    }
    
    @Bean
    @Profile("production")
    public StripePaymentService paymentService() {
        return new StripePaymentService();
    }
}
```

**.NET Configuration Patterns:**
```csharp
// Extension method pattern for service registration
public static class ServiceCollectionExtensions
{
    public static IServiceCollection AddBusinessServices(this IServiceCollection services, 
                                                         IConfiguration configuration)
    {
        services.AddScoped<IEmailService>(provider => 
            new EmailService(configuration.GetConnectionString("EmailServer")));
        
        services.AddScoped<IUserService, UserService>();
        
        // Conditional registration
        if (configuration.GetValue<bool>("Features:Cache:Enabled"))
        {
            services.AddScoped<ICacheService, RedisCacheService>();
        }
        
        // Environment-specific registration
        if (Environment.IsDevelopment())
        {
            services.AddScoped<IPaymentService, MockPaymentService>();
        }
        else
        {
            services.AddScoped<IPaymentService, StripePaymentService>();
        }
        
        return services;
    }
}

// Usage in Program.cs
var builder = WebApplication.CreateBuilder(args);
builder.Services.AddBusinessServices(builder.Configuration);
```

---

## 💉 **Injection Patterns: Best Practices vs Anti-Patterns**

### **Constructor Injection: The Gold Standard**

**✅ Best Practice - Constructor Injection:**
```java
@Service
public class OrderService {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final NotificationService notificationService;
    
    // Constructor injection with final fields
    public OrderService(PaymentService paymentService, 
                       InventoryService inventoryService,
                       NotificationService notificationService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
        this.notificationService = notificationService;
    }
    
    public Order processOrder(OrderRequest request) {
        // All dependencies are guaranteed to be available
        // Class is immutable and thread-safe
        return new Order(/* ... */);
    }
}

// With Lombok - even cleaner
@Service
@RequiredArgsConstructor  // Generates constructor for final fields
public class OrderServiceLombok {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final NotificationService notificationService;
    
    public Order processOrder(OrderRequest request) {
        // Implementation
    }
}
```

**❌ Anti-Pattern - Field Injection:**
```java
@Service
public class OrderServiceBad {
    @Autowired
    private PaymentService paymentService;  // ❌ Mutable, testing nightmare
    @Autowired
    private InventoryService inventoryService;  // ❌ Hidden dependencies
    @Autowired
    private NotificationService notificationService;  // ❌ Reflection-based injection
    
    public Order processOrder(OrderRequest request) {
        // What if services are null? No compile-time safety!
        return new Order(/* ... */);
    }
}
```

**🎯 WHY Constructor Injection is Superior:**
1. **Immutability**: Final fields prevent accidental modification
2. **Fail-fast**: Missing dependencies cause startup failure, not runtime NPE
3. **Testability**: Easy to create instances with mocks in tests
4. **Explicit contracts**: Constructor clearly shows what the class needs

### **Bean Lifecycle and Scopes**

**Spring Bean Scopes:**
```java
@Service
@Scope("singleton")  // Default - one instance per container
public class ConfigurationService {
    // Stateless services should be singleton
    // Thread-safe by design
}

@Service
@Scope("prototype")  // New instance every time
public class EmailBuilder {
    private StringBuilder content = new StringBuilder();
    
    // Stateful objects that shouldn't be shared
    public EmailBuilder addLine(String line) {
        content.append(line).append("\n");
        return this;
    }
}

@Component
@Scope("request")  // One instance per HTTP request (web apps)
public class RequestContextHolder {
    private final String requestId = UUID.randomUUID().toString();
    
    // Request-specific data
    public String getRequestId() { return requestId; }
}

@Component
@Scope("session")  // One instance per HTTP session
public class UserSession {
    private User currentUser;
    private String sessionToken;
    
    // Session-specific state
}
```

**.NET Service Lifetimes:**
```csharp
public class Startup
{
    public void ConfigureServices(IServiceCollection services)
    {
        // Singleton - one instance for application lifetime
        services.AddSingleton<IConfigurationService, ConfigurationService>();
        
        // Scoped - one instance per request (web apps)
        services.AddScoped<IOrderService, OrderService>();
        
        // Transient - new instance every time
        services.AddTransient<IEmailBuilder, EmailBuilder>();
        
        // Custom scope example
        services.AddScoped<IUserSession, UserSession>();
    }
}
```

**📊 Scope Comparison Table:**

| Spring Scope | .NET Lifetime | Use Case | Example |
|--------------|---------------|----------|---------|
| `singleton` | `Singleton` | Stateless services, configuration | `ConfigurationService` |
| `prototype` | `Transient` | Stateful objects, builders | `EmailBuilder` |
| `request` | `Scoped` (web) | Request-specific data | `RequestContext` |
| `session` | Custom implementation | User session data | `UserSession` |

### **Conditional Bean Creation**

**Spring Conditional Annotations:**
```java
@Configuration
public class ConditionalConfiguration {
    
    // Bean created only if property is set
    @Bean
    @ConditionalOnProperty(name = "app.feature.email.enabled", havingValue = "true")
    public EmailService realEmailService() {
        return new SmtpEmailService();
    }
    
    // Bean created only if above bean is missing
    @Bean
    @ConditionalOnMissingBean(EmailService.class)
    public EmailService mockEmailService() {
        return new MockEmailService();
    }
    
    // Bean created only if class is on classpath
    @Bean
    @ConditionalOnClass(RedisTemplate.class)
    public CacheService redisCacheService() {
        return new RedisCacheService();
    }
    
    // Bean created only for specific profiles
    @Bean
    @Profile("!production")
    public DataSeeder testDataSeeder() {
        return new TestDataSeeder();
    }
}
```

**.NET Conditional Registration:**
```csharp
public static class ConditionalConfiguration
{
    public static IServiceCollection AddConditionalServices(
        this IServiceCollection services, 
        IConfiguration configuration)
    {
        // Conditional registration based on configuration
        if (configuration.GetValue<bool>("App:Features:Email:Enabled"))
        {
            services.AddScoped<IEmailService, SmtpEmailService>();
        }
        else
        {
            services.AddScoped<IEmailService, MockEmailService>();
        }
        
        // Conditional registration based on environment
        if (!Environment.IsProduction())
        {
            services.AddScoped<IDataSeeder, TestDataSeeder>();
        }
        
        // Conditional registration based on type availability
        if (IsRedisAvailable())
        {
            services.AddScoped<ICacheService, RedisCacheService>();
        }
        else
        {
            services.AddScoped<ICacheService, InMemoryCacheService>();
        }
        
        return services;
    }
}
```

---

## 🧪 **Testing with Dependency Injection**

### **Unit Testing Patterns**

**Spring Testing with Mocks:**
```java
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    
    @Mock
    private PaymentService paymentService;
    
    @Mock
    private InventoryService inventoryService;
    
    @Mock
    private NotificationService notificationService;
    
    @InjectMocks
    private OrderService orderService;  // Mockito injects mocks automatically
    
    @Test
    @DisplayName("Should process order successfully when inventory is available")
    void shouldProcessOrderSuccessfully() {
        // Given
        OrderRequest request = new OrderRequest("product-123", 2);
        when(inventoryService.isAvailable("product-123", 2)).thenReturn(true);
        when(paymentService.processPayment(any())).thenReturn(PaymentResult.success());
        
        // When
        Order result = orderService.processOrder(request);
        
        // Then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CONFIRMED);
        verify(notificationService).sendOrderConfirmation(result);
    }
    
    @Test
    @DisplayName("Should throw exception when inventory is insufficient")
    void shouldThrowExceptionWhenInventoryInsufficient() {
        // Given
        OrderRequest request = new OrderRequest("product-123", 10);
        when(inventoryService.isAvailable("product-123", 10)).thenReturn(false);
        
        // When/Then
        assertThatThrownBy(() -> orderService.processOrder(request))
            .isInstanceOf(InsufficientInventoryException.class)
            .hasMessage("Insufficient inventory for product: product-123");
    }
}
```

**.NET Testing with Mocks:**
```csharp
public class OrderServiceTests
{
    private readonly Mock<IPaymentService> _paymentServiceMock;
    private readonly Mock<IInventoryService> _inventoryServiceMock;
    private readonly Mock<INotificationService> _notificationServiceMock;
    private readonly OrderService _orderService;
    
    public OrderServiceTests()
    {
        _paymentServiceMock = new Mock<IPaymentService>();
        _inventoryServiceMock = new Mock<IInventoryService>();
        _notificationServiceMock = new Mock<INotificationService>();
        
        // Manual dependency injection for testing
        _orderService = new OrderService(
            _paymentServiceMock.Object,
            _inventoryServiceMock.Object,
            _notificationServiceMock.Object);
    }
    
    [Fact]
    [Trait("Category", "Unit")]
    public void ProcessOrder_WhenInventoryAvailable_ShouldReturnConfirmedOrder()
    {
        // Arrange
        var request = new OrderRequest("product-123", 2);
        _inventoryServiceMock.Setup(x => x.IsAvailable("product-123", 2)).Returns(true);
        _paymentServiceMock.Setup(x => x.ProcessPayment(It.IsAny<PaymentRequest>()))
                          .Returns(PaymentResult.Success());
        
        // Act
        var result = _orderService.ProcessOrder(request);
        
        // Assert
        result.Status.Should().Be(OrderStatus.Confirmed);
        _notificationServiceMock.Verify(x => x.SendOrderConfirmation(result), Times.Once);
    }
}
```

### **Integration Testing Patterns**

**Spring Integration Testing:**
```java
@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "app.feature.email.enabled=false"  // Use mock email service
})
class OrderServiceIntegrationTest {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @MockBean  // Replace real bean with mock in context
    private PaymentService paymentService;
    
    @Test
    @Transactional
    @Rollback
    void shouldProcessOrderEndToEnd() {
        // Given - real database setup
        Product product = new Product("Test Product", BigDecimal.valueOf(100));
        entityManager.persistAndFlush(product);
        
        // Mock external service
        when(paymentService.processPayment(any())).thenReturn(PaymentResult.success());
        
        // When - test with real database and mocked external service
        Order result = orderService.processOrder(new OrderRequest(product.getId(), 1));
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CONFIRMED);
    }
}
```

**.NET Integration Testing:**
```csharp
public class OrderServiceIntegrationTests : IClassFixture<WebApplicationFactory<Program>>
{
    private readonly WebApplicationFactory<Program> _factory;
    private readonly IServiceScope _scope;
    private readonly OrderService _orderService;
    
    public OrderServiceIntegrationTests(WebApplicationFactory<Program> factory)
    {
        _factory = factory.WithWebHostBuilder(builder =>
        {
            builder.ConfigureTestServices(services =>
            {
                // Replace real payment service with mock
                services.RemoveAll<IPaymentService>();
                services.AddScoped<IPaymentService, MockPaymentService>();
                
                // Use in-memory database
                services.RemoveAll<DbContextOptions<OrderContext>>();
                services.AddDbContext<OrderContext>(options =>
                    options.UseInMemoryDatabase("TestDb"));
            });
        });
        
        _scope = _factory.Services.CreateScope();
        _orderService = _scope.ServiceProvider.GetRequiredService<OrderService>();
    }
    
    [Fact]
    public async Task ProcessOrder_WithRealDatabase_ShouldWork()
    {
        // Arrange
        var context = _scope.ServiceProvider.GetRequiredService<OrderContext>();
        var product = new Product { Name = "Test Product", Price = 100m };
        context.Products.Add(product);
        await context.SaveChangesAsync();
        
        // Act
        var result = await _orderService.ProcessOrderAsync(
            new OrderRequest(product.Id, 1));
        
        // Assert
        result.Should().NotBeNull();
        result.Status.Should().Be(OrderStatus.Confirmed);
    }
}
```

---

## 🏢 **Real-World Enterprise Patterns**

### **Service Layer Design**

**Spring Service Layer Architecture:**
```java
// Domain service - business logic
@Service
@Transactional
public class OrderDomainService {
    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final PaymentService paymentService;
    
    public OrderDomainService(OrderRepository orderRepository,
                             InventoryRepository inventoryRepository,
                             PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
        this.paymentService = paymentService;
    }
    
    public Order createOrder(CreateOrderCommand command) {
        // Domain logic
        validateOrderRequest(command);
        reserveInventory(command);
        
        Order order = Order.builder()
            .customerId(command.getCustomerId())
            .items(command.getItems())
            .status(OrderStatus.PENDING)
            .build();
            
        order = orderRepository.save(order);
        
        // Process payment
        PaymentResult paymentResult = paymentService.processPayment(
            PaymentRequest.from(order));
            
        if (paymentResult.isSuccessful()) {
            order.confirm();
        } else {
            order.cancel();
            releaseInventory(command);
        }
        
        return orderRepository.save(order);
    }
}

// Application service - orchestration
@Service
public class OrderApplicationService {
    private final OrderDomainService orderDomainService;
    private final EventPublisher eventPublisher;
    private final NotificationService notificationService;
    
    public OrderApplicationService(OrderDomainService orderDomainService,
                                  EventPublisher eventPublisher,
                                  NotificationService notificationService) {
        this.orderDomainService = orderDomainService;
        this.eventPublisher = eventPublisher;
        this.notificationService = notificationService;
    }
    
    public OrderDto processOrder(CreateOrderRequest request) {
        CreateOrderCommand command = CreateOrderCommand.from(request);
        
        try {
            Order order = orderDomainService.createOrder(command);
            
            // Publish events
            eventPublisher.publish(new OrderCreatedEvent(order));
            
            // Send notifications
            notificationService.sendOrderConfirmation(order);
            
            return OrderDto.from(order);
            
        } catch (Exception e) {
            eventPublisher.publish(new OrderFailedEvent(command, e));
            throw e;
        }
    }
}
```

### **Configuration and Environment Management**

**Spring Configuration Properties:**
```java
@ConfigurationProperties(prefix = "app.payment")
@Data
@Component
public class PaymentConfiguration {
    private String apiUrl;
    private String apiKey;
    private int timeoutSeconds = 30;
    private boolean retryEnabled = true;
    private int maxRetries = 3;
    
    @NestedConfigurationProperty
    private SslConfiguration ssl = new SslConfiguration();
    
    @Data
    public static class SslConfiguration {
        private boolean enabled = true;
        private String trustStore;
        private String trustStorePassword;
    }
}

// Usage in service
@Service
public class PaymentService {
    private final PaymentConfiguration config;
    private final RestTemplate restTemplate;
    
    public PaymentService(PaymentConfiguration config, RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }
    
    public PaymentResult processPayment(PaymentRequest request) {
        // Use configuration
        return restTemplate.postForObject(
            config.getApiUrl() + "/process",
            request,
            PaymentResult.class
        );
    }
}
```

**.NET Configuration Patterns:**
```csharp
// Configuration class
public class PaymentConfiguration
{
    public const string SectionName = "Payment";
    
    public string ApiUrl { get; set; } = string.Empty;
    public string ApiKey { get; set; } = string.Empty;
    public int TimeoutSeconds { get; set; } = 30;
    public bool RetryEnabled { get; set; } = true;
    public int MaxRetries { get; set; } = 3;
    public SslConfiguration Ssl { get; set; } = new();
}

public class SslConfiguration
{
    public bool Enabled { get; set; } = true;
    public string TrustStore { get; set; } = string.Empty;
    public string TrustStorePassword { get; set; } = string.Empty;
}

// Registration
builder.Services.Configure<PaymentConfiguration>(
    builder.Configuration.GetSection(PaymentConfiguration.SectionName));

// Usage
public class PaymentService
{
    private readonly PaymentConfiguration _config;
    private readonly HttpClient _httpClient;
    
    public PaymentService(IOptions<PaymentConfiguration> config, HttpClient httpClient)
    {
        _config = config.Value;
        _httpClient = httpClient;
    }
}
```

---

## ⚠️ **Common Anti-Patterns and Solutions**

### **Circular Dependencies**

**❌ Problem - Circular Dependency:**
```java
@Service
public class UserService {
    private final OrderService orderService;  // ❌ Circular dependency
    
    public UserService(OrderService orderService) {
        this.orderService = orderService;
    }
    
    public void updateUserPreferences(User user) {
        // Logic that needs order service
        orderService.updateUserOrders(user);
    }
}

@Service
public class OrderService {
    private final UserService userService;  // ❌ Circular dependency
    
    public OrderService(UserService userService) {
        this.userService = userService;
    }
    
    public void createOrder(Order order) {
        // Logic that needs user service
        userService.validateUser(order.getUser());
    }
}
```

**✅ Solution 1 - Extract Shared Service:**
```java
@Service
public class UserValidationService {
    public void validateUser(User user) {
        // Extracted common logic
    }
}

@Service
public class UserService {
    private final UserValidationService userValidationService;
    
    public UserService(UserValidationService userValidationService) {
        this.userValidationService = userValidationService;
    }
}

@Service
public class OrderService {
    private final UserValidationService userValidationService;
    
    public OrderService(UserValidationService userValidationService) {
        this.userValidationService = userValidationService;
    }
}
```

**✅ Solution 2 - Event-Driven Communication:**
```java
@Service
public class UserService {
    private final EventPublisher eventPublisher;
    
    public UserService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
    public void updateUserPreferences(User user) {
        // Update preferences
        eventPublisher.publish(new UserPreferencesUpdatedEvent(user));
    }
}

@Service
public class OrderService {
    
    @EventListener
    public void handleUserPreferencesUpdated(UserPreferencesUpdatedEvent event) {
        // React to user changes without direct dependency
        updateUserOrders(event.getUser());
    }
}
```

### **Over-Use of @Autowired**

**❌ Anti-Pattern - Field Injection Everywhere:**
```java
@Service
public class OrderService {
    @Autowired
    private PaymentService paymentService;  // ❌ Field injection
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
    // Too many dependencies - violates SRP
}
```

**✅ Solution - Constructor Injection + Decomposition:**
```java
// Smaller, focused services
@Service
@RequiredArgsConstructor
public class OrderCreationService {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    
    public Order createOrder(CreateOrderRequest request) {
        // Focused responsibility
    }
}

@Service
@RequiredArgsConstructor
public class OrderNotificationService {
    private final NotificationService notificationService;
    private final UserService userService;
    
    public void notifyOrderCreated(Order order) {
        // Focused responsibility
    }
}

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {
    private final ShippingService shippingService;
    private final ProductService productService;
    
    public void fulfillOrder(Order order) {
        // Focused responsibility
    }
}
```

---

## 🔍 **Reference Code Examples**

### **Working Examples in Repository**

| Concept | Reference File | What It Demonstrates |
|---------|----------------|---------------------|
| **Spring Annotations** | [`SpringAnnotations.java`](../../src/main/java/com/coherentsolutions/session1/reference/SpringAnnotations.java) | Complete annotation reference |
| **DI Patterns** | [`SpringDemo.java`](../../src/main/java/com/coherentsolutions/session1/demos/SpringDemo.java) | Live coding demonstration |
| **DI Anti-Patterns** | [`antipatterns/di/`](../../src/main/java/com/coherentsolutions/session1/antipatterns/di/) | Common mistakes and fixes |

### **Practical Exercises**

| Exercise | File | Skill Level |
|----------|------|-------------|
| **Service Layer Design** | [`exercises/userservice/`](../../src/main/java/com/coherentsolutions/session1/exercises/userservice/) | Intermediate |
| **Configuration Management** | [`exercises/userservice/UserServiceExercise.java`](../../src/main/java/com/coherentsolutions/session1/exercises/userservice/UserServiceExercise.java) | Beginner |
| **Testing with DI** | Test files in exercises | Advanced |

---

## ✅ **Mastery Verification**

### **Knowledge Checkpoints**

After completing this guide, verify your understanding:

#### **Basic DI Concepts** ✅
- [ ] Explain the difference between constructor and field injection
- [ ] Describe Spring bean scopes and their appropriate use cases
- [ ] Configure beans using @Configuration classes
- [ ] Use conditional bean creation appropriately

#### **Advanced Patterns** ✅
- [ ] Design service layers with proper separation of concerns
- [ ] Implement configuration properties binding
- [ ] Handle circular dependencies appropriately
- [ ] Write testable code using dependency injection

#### **Enterprise Applications** ✅
- [ ] Design domain and application service layers
- [ ] Implement event-driven communication patterns
- [ ] Configure environment-specific beans
- [ ] Apply DI best practices in large codebases

### **Hands-On Exercises**

**Exercise 1: Service Layer Refactoring**
```java
// Refactor this monolithic service into properly designed components
@Service
public class UserManagementService {
    // 15 dependencies injected via @Autowired
    // 20+ methods handling everything from user creation to billing
    // Mixed responsibilities and tight coupling
    
    // Your task: Break this into focused services with proper DI
}
```

**Exercise 2: Configuration Management**
```java
// Design a configuration system for a multi-tenant application
// Requirements:
// 1. Environment-specific settings (dev, staging, prod)
// 2. Tenant-specific overrides
// 3. Feature flags
// 4. External service configurations
```

**Exercise 3: Testing Strategy**
```java
// Create a comprehensive testing strategy for this service architecture
// Requirements:
// 1. Unit tests with proper mocking
// 2. Integration tests with test containers
// 3. Configuration testing
// 4. Profile-specific testing
```

---

## 🚀 **Next Steps**

### **Continue Your Learning Journey**

Now that you understand dependency injection:

1. **[Testing Frameworks Guide](testing-frameworks.md)** - JUnit, Mockito, AssertJ
2. **[Build & Project Structure](build-and-project-structure.md)** - Maven and organization
3. **[Anti-Patterns Deep Dive](anti-patterns-deep-dive.md)** - Avoid common mistakes

### **Advanced DI Topics**

- **Spring AOP**: Aspect-oriented programming with Spring
- **Spring Profiles**: Advanced environment configuration
- **Custom Annotations**: Creating your own DI annotations
- **Bean Post-Processors**: Customizing bean creation lifecycle

---

**You've mastered Spring's dependency injection! 🎉**

> *"Dependency injection isn't just about loose coupling - it's about designing systems that are testable, maintainable, and adaptable to changing requirements."*