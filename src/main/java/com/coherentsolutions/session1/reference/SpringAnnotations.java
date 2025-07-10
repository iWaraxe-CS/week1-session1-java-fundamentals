package com.coherentsolutions.session1.reference;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * SPRING ANNOTATIONS REFERENCE: Spring Framework Annotations
 * 
 * This reference class demonstrates Spring Framework annotations and their usage,
 * with comparisons to .NET dependency injection and MVC patterns.
 * 
 * COVERAGE:
 * 1. Core annotations (@Component, @Service, @Repository, @Controller)
 * 2. Dependency injection annotations (@Autowired, @Qualifier, @Value)
 * 3. Configuration annotations (@Configuration, @Bean, @Profile)
 * 4. Web annotations (@RestController, @RequestMapping, @PathVariable)
 * 5. Lifecycle annotations (@PostConstruct, @PreDestroy)
 * 6. Conditional annotations (@ConditionalOnProperty, @ConditionalOnClass)
 * 7. AOP annotations (@Aspect, @Around, @Before, @After)
 * 8. Testing annotations (@SpringBootTest, @MockBean, @TestConfiguration)
 * 
 * FOR LECTURE USE:
 * - Compare Spring DI with .NET Core DI
 * - Show annotation-driven configuration vs XML
 * - Demonstrate Spring Boot auto-configuration
 * - Explain Spring's approach to enterprise patterns
 */
@Slf4j
public class SpringAnnotations {
    
    public static void main(String[] args) {
        System.out.println("=== SPRING ANNOTATIONS REFERENCE ===");
        
        demonstrateCoreAnnotations();
        demonstrateDependencyInjection();
        demonstrateConfiguration();
        demonstrateWebAnnotations();
        demonstrateLifecycleAnnotations();
        demonstrateConditionalAnnotations();
        demonstrateValidationAnnotations();
        demonstrateBestPractices();
        
        System.out.println("\n=== SPRING ANNOTATIONS COMPLETE ===");
    }
    
    /**
     * CORE ANNOTATIONS
     * 
     * Spring's stereotype annotations for component scanning
     */
    public static void demonstrateCoreAnnotations() {
        System.out.println("\n--- Core Annotations ---");
        
        System.out.println("SPRING STEREOTYPE ANNOTATIONS:");
        System.out.println("@Component  - Generic Spring-managed component");
        System.out.println("@Service    - Business logic layer");
        System.out.println("@Repository - Data access layer");
        System.out.println("@Controller - Web controller layer");
        System.out.println("@RestController - REST API controller (@Controller + @ResponseBody)");
        
        System.out.println("\n.NET CORE EQUIVALENT:");
        System.out.println("services.AddScoped<IUserService, UserService>();");
        System.out.println("services.AddScoped<IUserRepository, UserRepository>();");
        System.out.println("[ApiController] public class UserController : ControllerBase");
        
        System.out.println("\nSPRING COMPONENT SCANNING:");
        System.out.println("@ComponentScan(basePackages = \"com.example\")");
        System.out.println("Automatically discovers and registers beans");
        
        System.out.println("\nSTEREOTYPE ANNOTATION HIERARCHY:");
        System.out.println("@Component");
        System.out.println("├── @Service");
        System.out.println("├── @Repository");
        System.out.println("└── @Controller");
        System.out.println("    └── @RestController");
        
        System.out.println("\nANNOTATION BENEFITS:");
        System.out.println("1. Clear separation of concerns");
        System.out.println("2. Automatic exception translation (@Repository)");
        System.out.println("3. Better tooling support");
        System.out.println("4. Easier testing and mocking");
        System.out.println("5. Convention over configuration");
    }
    
    /**
     * DEPENDENCY INJECTION ANNOTATIONS
     * 
     * Spring's DI annotations vs .NET Core DI
     */
    public static void demonstrateDependencyInjection() {
        System.out.println("\n--- Dependency Injection ---");
        
        System.out.println("SPRING DI ANNOTATIONS:");
        System.out.println("@Autowired - Inject dependencies automatically");
        System.out.println("@Qualifier - Specify which bean to inject");
        System.out.println("@Primary   - Default bean when multiple candidates");
        System.out.println("@Value     - Inject property values");
        System.out.println("@Lazy      - Lazy initialization");
        
        System.out.println("\n.NET CORE DI COMPARISON:");
        System.out.println("Spring: @Autowired UserRepository userRepository");
        System.out.println(".NET:   public UserService(IUserRepository userRepository)");
        System.out.println("");
        System.out.println("Spring: @Qualifier(\"primaryDB\") DataSource dataSource");
        System.out.println(".NET:   [FromKeyedServices(\"primaryDB\")]");
        System.out.println("");
        System.out.println("Spring: @Value(\"${app.name}\") String appName");
        System.out.println(".NET:   IOptions<AppConfig> options");
        
        System.out.println("\nCONSTRUCTOR INJECTION (RECOMMENDED):");
        System.out.println("@Service");
        System.out.println("@RequiredArgsConstructor  // Lombok");
        System.out.println("public class UserService {");
        System.out.println("    private final UserRepository userRepository;");
        System.out.println("    private final EmailService emailService;");
        System.out.println("}");
        
        System.out.println("\nFIELD INJECTION (NOT RECOMMENDED):");
        System.out.println("@Service");
        System.out.println("public class UserService {");
        System.out.println("    @Autowired");
        System.out.println("    private UserRepository userRepository;  // Avoid this");
        System.out.println("}");
        
        System.out.println("\nDI BEST PRACTICES:");
        System.out.println("✅ Use constructor injection");
        System.out.println("✅ Use @RequiredArgsConstructor with final fields");
        System.out.println("✅ Use @Qualifier for multiple implementations");
        System.out.println("✅ Use @Primary for default implementations");
        System.out.println("❌ Avoid field injection");
        System.out.println("❌ Avoid circular dependencies");
    }
    
    /**
     * CONFIGURATION ANNOTATIONS
     * 
     * Spring configuration and bean definition
     */
    public static void demonstrateConfiguration() {
        System.out.println("\n--- Configuration ---");
        
        System.out.println("SPRING CONFIGURATION:");
        System.out.println("@Configuration - Class contains bean definitions");
        System.out.println("@Bean         - Method produces a bean");
        System.out.println("@Profile      - Activate beans for specific profiles");
        System.out.println("@PropertySource - Load properties files");
        System.out.println("@EnableAutoConfiguration - Enable Spring Boot auto-config");
        
        System.out.println("\n.NET CORE EQUIVALENT:");
        System.out.println("public void ConfigureServices(IServiceCollection services) {");
        System.out.println("    services.AddScoped<IUserService, UserService>();");
        System.out.println("    services.Configure<DatabaseOptions>(config);");
        System.out.println("}");
        
        System.out.println("\nCONFIGURATION CLASS EXAMPLE:");
        System.out.println("@Configuration");
        System.out.println("@EnableJpaRepositories");
        System.out.println("@PropertySource(\"classpath:application.properties\")");
        System.out.println("public class AppConfig {");
        System.out.println("    ");
        System.out.println("    @Bean");
        System.out.println("    @Primary");
        System.out.println("    public DataSource primaryDataSource() {");
        System.out.println("        return DataSourceBuilder.create().build();");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    @Bean(\"secondaryDB\")");
        System.out.println("    public DataSource secondaryDataSource() {");
        System.out.println("        return DataSourceBuilder.create().build();");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nPROFILE-SPECIFIC CONFIGURATION:");
        System.out.println("@Configuration");
        System.out.println("@Profile(\"dev\")");
        System.out.println("public class DevConfig { ... }");
        System.out.println("");
        System.out.println("@Configuration");
        System.out.println("@Profile(\"prod\")");
        System.out.println("public class ProdConfig { ... }");
        
        System.out.println("\nPROPERTY INJECTION:");
        System.out.println("@Value(\"${app.name:DefaultApp}\")  // With default value");
        System.out.println("private String appName;");
        System.out.println("");
        System.out.println("@Value(\"${app.timeout:30}\")");
        System.out.println("private int timeoutSeconds;");
        System.out.println("");
        System.out.println("@Value(\"#{systemProperties['java.home']}\")  // SpEL");
        System.out.println("private String javaHome;");
        
        System.out.println("\nCONFIGURATION PROPERTIES:");
        System.out.println("@ConfigurationProperties(prefix = \"app.database\")");
        System.out.println("@Data");
        System.out.println("public class DatabaseProperties {");
        System.out.println("    private String url;");
        System.out.println("    private String username;");
        System.out.println("    private String password;");
        System.out.println("    private int maxConnections = 10;");
        System.out.println("}");
    }
    
    /**
     * WEB ANNOTATIONS
     * 
     * Spring MVC and REST annotations
     */
    public static void demonstrateWebAnnotations() {
        System.out.println("\n--- Web Annotations ---");
        
        System.out.println("SPRING WEB ANNOTATIONS:");
        System.out.println("@RestController    - REST controller class");
        System.out.println("@RequestMapping    - Map HTTP requests to methods");
        System.out.println("@GetMapping        - Handle GET requests");
        System.out.println("@PostMapping       - Handle POST requests");
        System.out.println("@PutMapping        - Handle PUT requests");
        System.out.println("@DeleteMapping     - Handle DELETE requests");
        System.out.println("@PathVariable      - Extract path variables");
        System.out.println("@RequestParam      - Extract query parameters");
        System.out.println("@RequestBody       - Bind request body to object");
        System.out.println("@ResponseBody      - Return object as response body");
        
        System.out.println("\n.NET CORE MVC EQUIVALENT:");
        System.out.println("Spring: @RestController");
        System.out.println(".NET:   [ApiController] public class UserController : ControllerBase");
        System.out.println("");
        System.out.println("Spring: @GetMapping(\"/users/{id}\")");
        System.out.println(".NET:   [HttpGet(\"users/{id}\")]");
        System.out.println("");
        System.out.println("Spring: @PathVariable Long id");
        System.out.println(".NET:   [FromRoute] long id");
        System.out.println("");
        System.out.println("Spring: @RequestBody CreateUserRequest request");
        System.out.println(".NET:   [FromBody] CreateUserRequest request");
        
        System.out.println("\nREST CONTROLLER EXAMPLE:");
        System.out.println("@RestController");
        System.out.println("@RequestMapping(\"/api/users\")");
        System.out.println("@Validated");
        System.out.println("public class UserController {");
        System.out.println("    ");
        System.out.println("    @GetMapping");
        System.out.println("    public List<User> getAllUsers() { ... }");
        System.out.println("    ");
        System.out.println("    @GetMapping(\"/{id}\")");
        System.out.println("    public User getUser(@PathVariable Long id) { ... }");
        System.out.println("    ");
        System.out.println("    @PostMapping");
        System.out.println("    public User createUser(@Valid @RequestBody CreateUserRequest request) { ... }");
        System.out.println("    ");
        System.out.println("    @PutMapping(\"/{id}\")");
        System.out.println("    public User updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) { ... }");
        System.out.println("    ");
        System.out.println("    @DeleteMapping(\"/{id}\")");
        System.out.println("    @ResponseStatus(HttpStatus.NO_CONTENT)");
        System.out.println("    public void deleteUser(@PathVariable Long id) { ... }");
        System.out.println("}");
        
        System.out.println("\nEXCEPTION HANDLING:");
        System.out.println("@ControllerAdvice");
        System.out.println("public class GlobalExceptionHandler {");
        System.out.println("    ");
        System.out.println("    @ExceptionHandler(UserNotFoundException.class)");
        System.out.println("    @ResponseStatus(HttpStatus.NOT_FOUND)");
        System.out.println("    public ErrorResponse handleUserNotFound(UserNotFoundException ex) {");
        System.out.println("        return new ErrorResponse(\"User not found\", ex.getMessage());");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nCONTENT NEGOTIATION:");
        System.out.println("@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)");
        System.out.println("@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)");
        System.out.println("");
        System.out.println("@GetMapping(value = \"/users/{id}\", produces = {");
        System.out.println("    MediaType.APPLICATION_JSON_VALUE,");
        System.out.println("    MediaType.APPLICATION_XML_VALUE");
        System.out.println("})");
    }
    
    /**
     * LIFECYCLE ANNOTATIONS
     * 
     * Bean lifecycle management
     */
    public static void demonstrateLifecycleAnnotations() {
        System.out.println("\n--- Lifecycle Annotations ---");
        
        System.out.println("SPRING LIFECYCLE ANNOTATIONS:");
        System.out.println("@PostConstruct - Called after dependency injection");
        System.out.println("@PreDestroy    - Called before bean destruction");
        System.out.println("@EventListener - Listen to application events");
        System.out.println("@Async         - Asynchronous method execution");
        System.out.println("@Scheduled     - Schedule method execution");
        
        System.out.println("\n.NET CORE EQUIVALENT:");
        System.out.println("Spring: @PostConstruct");
        System.out.println(".NET:   Constructor or IHostedService.StartAsync()");
        System.out.println("");
        System.out.println("Spring: @PreDestroy");
        System.out.println(".NET:   IDisposable.Dispose() or IHostedService.StopAsync()");
        System.out.println("");
        System.out.println("Spring: @Async");
        System.out.println(".NET:   async Task methods");
        
        System.out.println("\nLIFECYCLE EXAMPLE:");
        System.out.println("@Service");
        System.out.println("public class UserService {");
        System.out.println("    ");
        System.out.println("    @PostConstruct");
        System.out.println("    public void init() {");
        System.out.println("        log.info(\"UserService initialized\");");
        System.out.println("        // Initialize resources, caches, etc.");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    @PreDestroy");
        System.out.println("    public void cleanup() {");
        System.out.println("        log.info(\"UserService shutting down\");");
        System.out.println("        // Cleanup resources");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nASYNC PROCESSING:");
        System.out.println("@Service");
        System.out.println("public class EmailService {");
        System.out.println("    ");
        System.out.println("    @Async");
        System.out.println("    public CompletableFuture<Void> sendEmailAsync(String to, String subject) {");
        System.out.println("        // Send email asynchronously");
        System.out.println("        return CompletableFuture.completedFuture(null);");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nSCHEDULED TASKS:");
        System.out.println("@Component");
        System.out.println("public class ScheduledTasks {");
        System.out.println("    ");
        System.out.println("    @Scheduled(fixedRate = 60000)  // Every minute");
        System.out.println("    public void performPeriodicTask() { ... }");
        System.out.println("    ");
        System.out.println("    @Scheduled(cron = \"0 0 2 * * ?\")  // Daily at 2 AM");
        System.out.println("    public void performDailyCleanup() { ... }");
        System.out.println("}");
        
        System.out.println("\nEVENT HANDLING:");
        System.out.println("@Component");
        System.out.println("public class UserEventListener {");
        System.out.println("    ");
        System.out.println("    @EventListener");
        System.out.println("    public void handleUserCreated(UserCreatedEvent event) {");
        System.out.println("        log.info(\"User created: {}\", event.getUserId());");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    @EventListener");
        System.out.println("    @Async");
        System.out.println("    public void handleUserDeleted(UserDeletedEvent event) {");
        System.out.println("        // Handle asynchronously");
        System.out.println("    }");
        System.out.println("}");
    }
    
    /**
     * CONDITIONAL ANNOTATIONS
     * 
     * Conditional bean creation and configuration
     */
    public static void demonstrateConditionalAnnotations() {
        System.out.println("\n--- Conditional Annotations ---");
        
        System.out.println("SPRING CONDITIONAL ANNOTATIONS:");
        System.out.println("@ConditionalOnProperty     - Based on property values");
        System.out.println("@ConditionalOnClass        - Based on class presence");
        System.out.println("@ConditionalOnMissingClass - Based on class absence");
        System.out.println("@ConditionalOnBean         - Based on bean presence");
        System.out.println("@ConditionalOnMissingBean  - Based on bean absence");
        System.out.println("@ConditionalOnProfile      - Based on active profiles");
        
        System.out.println("\nCONDITIONAL PROPERTY EXAMPLE:");
        System.out.println("@Service");
        System.out.println("@ConditionalOnProperty(");
        System.out.println("    name = \"feature.email.enabled\",");
        System.out.println("    havingValue = \"true\",");
        System.out.println("    matchIfMissing = false");
        System.out.println(")");
        System.out.println("public class EmailService { ... }");
        
        System.out.println("\nCONDITIONAL CLASS EXAMPLE:");
        System.out.println("@Configuration");
        System.out.println("@ConditionalOnClass(name = \"redis.clients.jedis.Jedis\")");
        System.out.println("public class RedisConfiguration {");
        System.out.println("    ");
        System.out.println("    @Bean");
        System.out.println("    @ConditionalOnMissingBean");
        System.out.println("    public RedisTemplate<String, Object> redisTemplate() { ... }");
        System.out.println("}");
        
        System.out.println("\nCONDITIONAL PROFILES:");
        System.out.println("@Configuration");
        System.out.println("@Profile(\"!prod\")  // Not production");
        System.out.println("public class DevToolsConfig { ... }");
        System.out.println("");
        System.out.println("@Configuration");
        System.out.println("@Profile({\"dev\", \"test\"})  // Multiple profiles");
        System.out.println("public class NonProdConfig { ... }");
        
        System.out.println("\nCUSTOM CONDITIONS:");
        System.out.println("@Target({ElementType.TYPE, ElementType.METHOD})");
        System.out.println("@Retention(RetentionPolicy.RUNTIME)");
        System.out.println("@Conditional(DatabaseCondition.class)");
        System.out.println("public @interface ConditionalOnDatabase {");
        System.out.println("    DatabaseType value();");
        System.out.println("}");
        
        System.out.println("\nENVIRONMENT-SPECIFIC BEANS:");
        System.out.println("# application-dev.properties");
        System.out.println("feature.caching.enabled=false");
        System.out.println("feature.email.enabled=false");
        System.out.println("");
        System.out.println("# application-prod.properties");
        System.out.println("feature.caching.enabled=true");
        System.out.println("feature.email.enabled=true");
    }
    
    /**
     * VALIDATION ANNOTATIONS
     * 
     * Bean validation and Spring validation
     */
    public static void demonstrateValidationAnnotations() {
        System.out.println("\n--- Validation Annotations ---");
        
        System.out.println("SPRING VALIDATION ANNOTATIONS:");
        System.out.println("@Valid         - Enable validation");
        System.out.println("@Validated     - Spring's validation annotation");
        System.out.println("@NotNull       - Field cannot be null");
        System.out.println("@NotEmpty      - Collection/String cannot be empty");
        System.out.println("@NotBlank      - String cannot be blank");
        System.out.println("@Size          - Size constraints");
        System.out.println("@Min/@Max      - Numeric constraints");
        System.out.println("@Email         - Email format validation");
        System.out.println("@Pattern       - Regex pattern validation");
        
        System.out.println("\n.NET CORE VALIDATION EQUIVALENT:");
        System.out.println("Spring: @NotNull @Size(min = 2, max = 50) String name");
        System.out.println(".NET:   [Required] [StringLength(50, MinimumLength = 2)] string Name");
        System.out.println("");
        System.out.println("Spring: @Email String email");
        System.out.println(".NET:   [EmailAddress] string Email");
        System.out.println("");
        System.out.println("Spring: @Min(18) @Max(100) Integer age");
        System.out.println(".NET:   [Range(18, 100)] int Age");
        
        System.out.println("\nVALIDATION IN CONTROLLER:");
        System.out.println("@RestController");
        System.out.println("@Validated");
        System.out.println("public class UserController {");
        System.out.println("    ");
        System.out.println("    @PostMapping(\"/users\")");
        System.out.println("    public User createUser(@Valid @RequestBody CreateUserRequest request) {");
        System.out.println("        // Validation happens automatically");
        System.out.println("        return userService.createUser(request);");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    @GetMapping(\"/users/{id}\")");
        System.out.println("    public User getUser(@PathVariable @Min(1) Long id) {");
        System.out.println("        return userService.findById(id);");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nVALIDATION DTO EXAMPLE:");
        System.out.println("@Data");
        System.out.println("public class CreateUserRequest {");
        System.out.println("    ");
        System.out.println("    @NotBlank(message = \"Name is required\")");
        System.out.println("    @Size(min = 2, max = 50, message = \"Name must be 2-50 characters\")");
        System.out.println("    private String name;");
        System.out.println("    ");
        System.out.println("    @NotNull(message = \"Email is required\")");
        System.out.println("    @Email(message = \"Invalid email format\")");
        System.out.println("    private String email;");
        System.out.println("    ");
        System.out.println("    @NotNull");
        System.out.println("    @Min(value = 18, message = \"Must be at least 18 years old\")");
        System.out.println("    @Max(value = 120, message = \"Must be less than 120 years old\")");
        System.out.println("    private Integer age;");
        System.out.println("    ");
        System.out.println("    @Pattern(regexp = \"^[a-zA-Z0-9_]+$\", message = \"Username can only contain letters, numbers, and underscores\")");
        System.out.println("    private String username;");
        System.out.println("}");
        
        System.out.println("\nCUSTOM VALIDATION:");
        System.out.println("@Target({ElementType.FIELD})");
        System.out.println("@Retention(RetentionPolicy.RUNTIME)");
        System.out.println("@Constraint(validatedBy = PhoneNumberValidator.class)");
        System.out.println("public @interface ValidPhoneNumber {");
        System.out.println("    String message() default \"Invalid phone number\";");
        System.out.println("    Class<?>[] groups() default {};");
        System.out.println("    Class<? extends Payload>[] payload() default {};");
        System.out.println("}");
        
        System.out.println("\nVALIDATION ERROR HANDLING:");
        System.out.println("@ControllerAdvice");
        System.out.println("public class ValidationExceptionHandler {");
        System.out.println("    ");
        System.out.println("    @ExceptionHandler(MethodArgumentNotValidException.class)");
        System.out.println("    @ResponseStatus(HttpStatus.BAD_REQUEST)");
        System.out.println("    public ValidationErrorResponse handleValidationException(");
        System.out.println("            MethodArgumentNotValidException ex) {");
        System.out.println("        // Extract validation errors and return structured response");
        System.out.println("    }");
        System.out.println("}");
    }
    
    /**
     * BEST PRACTICES
     * 
     * Spring annotation best practices and common patterns
     */
    public static void demonstrateBestPractices() {
        System.out.println("\n--- Best Practices ---");
        
        System.out.println("SPRING ANNOTATION BEST PRACTICES:");
        
        System.out.println("\n1. DEPENDENCY INJECTION:");
        System.out.println("✅ Use constructor injection with @RequiredArgsConstructor");
        System.out.println("✅ Make dependencies final");
        System.out.println("✅ Use @Qualifier for multiple implementations");
        System.out.println("❌ Avoid field injection (@Autowired on fields)");
        System.out.println("❌ Avoid circular dependencies");
        
        System.out.println("\n2. COMPONENT ORGANIZATION:");
        System.out.println("✅ Use appropriate stereotype annotations");
        System.out.println("✅ @Service for business logic");
        System.out.println("✅ @Repository for data access");
        System.out.println("✅ @Controller/@RestController for web layer");
        System.out.println("✅ @Component for other components");
        
        System.out.println("\n3. CONFIGURATION:");
        System.out.println("✅ Use @ConfigurationProperties for grouped properties");
        System.out.println("✅ Use profiles for environment-specific configuration");
        System.out.println("✅ Use @ConditionalOn* for optional features");
        System.out.println("✅ Keep @Configuration classes focused");
        
        System.out.println("\n4. WEB LAYER:");
        System.out.println("✅ Use @RestController for REST APIs");
        System.out.println("✅ Use specific mapping annotations (@GetMapping, @PostMapping)");
        System.out.println("✅ Use @Valid for request validation");
        System.out.println("✅ Use @ControllerAdvice for global exception handling");
        
        System.out.println("\n5. VALIDATION:");
        System.out.println("✅ Use Bean Validation annotations");
        System.out.println("✅ Provide meaningful error messages");
        System.out.println("✅ Group validations when necessary");
        System.out.println("✅ Create custom validators for complex rules");
        
        System.out.println("\nCOMMON ANTI-PATTERNS:");
        System.out.println("❌ @Autowired field injection");
        System.out.println("❌ Using @Component for everything");
        System.out.println("❌ Ignoring validation");
        System.out.println("❌ Circular dependencies");
        System.out.println("❌ Over-using @Primary");
        System.out.println("❌ Not using profiles");
        System.out.println("❌ Mixing business logic in controllers");
        
        System.out.println("\nMIGRATION FROM .NET CORE:");
        System.out.println("1. Map [ApiController] to @RestController");
        System.out.println("2. Map [FromRoute] to @PathVariable");
        System.out.println("3. Map [FromBody] to @RequestBody");
        System.out.println("4. Map [Required] to @NotNull/@NotBlank");
        System.out.println("5. Map IOptions<T> to @ConfigurationProperties");
        System.out.println("6. Map dependency injection to constructor injection");
        System.out.println("7. Map middleware to @ControllerAdvice or filters");
        
        System.out.println("\nRECOMMENDED PROJECT STRUCTURE:");
        System.out.println("src/main/java/com/example/app/");
        System.out.println("├── config/          # @Configuration classes");
        System.out.println("├── controller/      # @RestController classes");
        System.out.println("├── service/         # @Service classes");
        System.out.println("├── repository/      # @Repository classes");
        System.out.println("├── domain/          # Domain entities");
        System.out.println("├── dto/             # Data transfer objects");
        System.out.println("└── exception/       # Custom exceptions");
        
        System.out.println("\nSPRING BOOT STARTER DEPENDENCIES:");
        System.out.println("spring-boot-starter-web      # Web applications");
        System.out.println("spring-boot-starter-data-jpa # JPA repositories");
        System.out.println("spring-boot-starter-security # Security");
        System.out.println("spring-boot-starter-test     # Testing");
        System.out.println("spring-boot-starter-validation # Bean validation");
        System.out.println("spring-boot-starter-actuator # Monitoring");
    }
    
    // Example classes demonstrating annotations
    @Service
    @RequiredArgsConstructor
    @Slf4j
    public static class UserService {
        private final UserRepository userRepository;
        private final EmailService emailService;
        
        @Value("${app.user.default-role:USER}")
        private String defaultRole;
        
        @PostConstruct
        public void init() {
            log.info("UserService initialized with default role: {}", defaultRole);
        }
        
        public void createUser(String email) {
            log.info("Creating user with email: {}", email);
            // Business logic here
        }
        
        @PreDestroy
        public void cleanup() {
            log.info("UserService shutting down");
        }
    }
    
    @Repository
    public interface UserRepository {
        void save(String user);
        String findByEmail(String email);
    }
    
    @Service
    @ConditionalOnProperty(name = "feature.email.enabled", havingValue = "true")
    public static class EmailService {
        public void sendEmail(String to, String subject, String body) {
            log.info("Sending email to: {}", to);
        }
    }
    
    @RestController
    @RequestMapping("/api/users")
    @RequiredArgsConstructor
    @Slf4j
    public static class UserController {
        private final UserService userService;
        
        @PostMapping
        public String createUser(@RequestBody String email) {
            userService.createUser(email);
            return "User created";
        }
        
        @GetMapping("/{id}")
        public String getUser(@PathVariable Long id) {
            return "User " + id;
        }
    }
    
    @Configuration
    @Profile("dev")
    public static class DevConfiguration {
        
        @Bean
        @Primary
        public String devDataSource() {
            return "dev-database";
        }
    }
    
    @Component
    @Order(1)
    public static class FirstComponent {
        @PostConstruct
        public void init() {
            log.info("First component initialized");
        }
    }
    
    @Component
    @Order(2)
    public static class SecondComponent {
        @PostConstruct
        public void init() {
            log.info("Second component initialized");
        }
    }
}