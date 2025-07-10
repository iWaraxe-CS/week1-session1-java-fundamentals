package com.coherentsolutions.session1.demos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TESTING DEMO
 * 
 * Live coding demonstration of Java testing frameworks for .NET developers.
 * This demo showcases JUnit 5, Mockito, and AssertJ testing patterns
 * compared to .NET testing frameworks (NUnit, Moq, FluentAssertions).
 * 
 * DEMO OBJECTIVES:
 * 1. Map .NET testing concepts to Java equivalents
 * 2. Demonstrate JUnit 5 features and annotations
 * 3. Show mocking with Mockito vs Moq
 * 4. Practice fluent assertions with AssertJ
 * 5. Highlight Spring Boot testing approaches
 * 
 * LECTURE FLOW:
 * 1. Start with basic unit test comparisons
 * 2. Progress through assertion libraries
 * 3. Show mocking frameworks
 * 4. Demonstrate integration testing
 * 5. End with Spring Boot testing patterns
 * 
 * INTERACTIVE ELEMENTS:
 * - .NET vs Java testing comparisons
 * - Live test development
 * - TDD demonstration
 * - Test failure analysis
 * 
 * NOTE: This class contains the production code for demonstration.
 * The actual tests would be in src/test/java with proper test structure.
 * This is formatted as a demo to show testing concepts side-by-side.
 */
@Slf4j
public class TestingDemo {
    
    public static void main(String[] args) {
        System.out.println("=== TESTING DEMO ===");
        System.out.println("Java testing frameworks for .NET developers");
        
        // Demo progression
        demonstrateTestingFrameworks();
        demonstrateAssertions();
        demonstrateMocking();
        demonstrateTestingPatterns();
        demonstrateSpringTesting();
        demonstrateBestPractices();
        
        System.out.println("\n=== DEMO COMPLETE ===");
    }
    
    /**
     * DEMO 1: Testing Frameworks
     * 
     * Show mapping between .NET and Java testing frameworks
     */
    public static void demonstrateTestingFrameworks() {
        System.out.println("\n--- Demo 1: Testing Frameworks ---");
        
        System.out.println("FRAMEWORK MAPPING:");
        System.out.println("┌─────────────────────┬─────────────────────┐");
        System.out.println("│ .NET                │ Java                │");
        System.out.println("├─────────────────────┼─────────────────────┤");
        System.out.println("│ NUnit               │ JUnit 5             │");
        System.out.println("│ xUnit               │ JUnit 5             │");
        System.out.println("│ MSTest              │ JUnit 5             │");
        System.out.println("│ Moq                 │ Mockito             │");
        System.out.println("│ NSubstitute         │ Mockito             │");
        System.out.println("│ FluentAssertions    │ AssertJ             │");
        System.out.println("│ AutoFixture         │ EasyRandom          │");
        System.out.println("│ TestContainers.NET  │ Testcontainers      │");
        System.out.println("└─────────────────────┴─────────────────────┘");
        
        System.out.println("\nANNOTATION MAPPING:");
        System.out.println("┌─────────────────────┬─────────────────────┐");
        System.out.println("│ .NET                │ Java (JUnit 5)      │");
        System.out.println("├─────────────────────┼─────────────────────┤");
        System.out.println("│ [Test]              │ @Test               │");
        System.out.println("│ [SetUp]             │ @BeforeEach         │");
        System.out.println("│ [TearDown]          │ @AfterEach          │");
        System.out.println("│ [OneTimeSetUp]      │ @BeforeAll          │");
        System.out.println("│ [OneTimeTearDown]   │ @AfterAll           │");
        System.out.println("│ [Ignore]            │ @Disabled           │");
        System.out.println("│ [Category]          │ @Tag                │");
        System.out.println("│ [TestCase]          │ @ParameterizedTest  │");
        System.out.println("└─────────────────────┴─────────────────────┘");
        
        System.out.println("\nBASIC TEST STRUCTURE:");
        System.out.println(".NET (NUnit):");
        System.out.println("  [TestFixture]");
        System.out.println("  public class CalculatorTests {");
        System.out.println("      [Test]");
        System.out.println("      public void Add_TwoNumbers_ReturnsSum() { ... }");
        System.out.println("  }");
        System.out.println("");
        System.out.println("Java (JUnit 5):");
        System.out.println("  class CalculatorTest {");
        System.out.println("      @Test");
        System.out.println("      void add_TwoNumbers_ReturnsSum() { ... }");
        System.out.println("  }");
        
        // Demonstrate with actual calculator
        demonstrateCalculatorTesting();
    }
    
    /**
     * DEMO 2: Assertions
     * 
     * Show assertion patterns and libraries
     */
    public static void demonstrateAssertions() {
        System.out.println("\n--- Demo 2: Assertions ---");
        
        System.out.println("ASSERTION COMPARISON:");
        System.out.println(".NET (NUnit):           Assert.AreEqual(expected, actual);");
        System.out.println("Java (JUnit):           assertEquals(expected, actual);");
        System.out.println(".NET (FluentAssert):    result.Should().Be(expected);");
        System.out.println("Java (AssertJ):         assertThat(result).isEqualTo(expected);");
        
        // Create test data
        User user = new User(1L, "John Doe", "john@example.com", "USER", LocalDateTime.now());
        List<String> fruits = Arrays.asList("apple", "banana", "cherry");
        
        System.out.println("\nASSERTION EXAMPLES:");
        
        // Basic assertions
        System.out.println("Basic equality:");
        System.out.println("  .NET: user.Name.Should().Be(\"John Doe\");");
        System.out.println("  Java: assertThat(user.getName()).isEqualTo(\"John Doe\");");
        
        boolean nameMatches = user.getName().equals("John Doe");
        System.out.println("  Result: " + nameMatches);
        
        // Null checks
        System.out.println("\nNull checks:");
        System.out.println("  .NET: user.Should().NotBeNull();");
        System.out.println("  Java: assertThat(user).isNotNull();");
        
        boolean notNull = user != null;
        System.out.println("  Result: " + notNull);
        
        // Collection assertions
        System.out.println("\nCollection assertions:");
        System.out.println("  .NET: fruits.Should().Contain(\"apple\").And.HaveCount(3);");
        System.out.println("  Java: assertThat(fruits).contains(\"apple\").hasSize(3);");
        
        boolean containsApple = fruits.contains("apple");
        boolean hasThreeItems = fruits.size() == 3;
        System.out.println("  Contains apple: " + containsApple);
        System.out.println("  Has 3 items: " + hasThreeItems);
        
        // String assertions
        System.out.println("\nString assertions:");
        System.out.println("  .NET: email.Should().StartWith(\"john\").And.EndWith(\".com\");");
        System.out.println("  Java: assertThat(email).startsWith(\"john\").endsWith(\".com\");");
        
        String email = user.getEmail();
        boolean startsWithJohn = email.startsWith("john");
        boolean endsWithCom = email.endsWith(".com");
        System.out.println("  Starts with 'john': " + startsWithJohn);
        System.out.println("  Ends with '.com': " + endsWithCom);
        
        // Exception assertions
        System.out.println("\nException assertions:");
        System.out.println("  .NET: action.Should().Throw<ArgumentException>();");
        System.out.println("  Java: assertThatThrownBy(() -> action()).isInstanceOf(IllegalArgumentException.class);");
        
        try {
            validateEmail("");
            System.out.println("  No exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage());
        }
        
        // Object property assertions
        System.out.println("\nObject property assertions:");
        System.out.println("  .NET: user.Should().Match(u => u.Id > 0 && u.Role == \"USER\");");
        System.out.println("  Java: assertThat(user).matches(u -> u.getId() > 0 && \"USER\".equals(u.getRole()));");
        
        boolean userMatches = user.getId() > 0 && "USER".equals(user.getRole());
        System.out.println("  User matches criteria: " + userMatches);
    }
    
    /**
     * DEMO 3: Mocking
     * 
     * Show mocking frameworks and patterns
     */
    public static void demonstrateMocking() {
        System.out.println("\n--- Demo 3: Mocking ---");
        
        System.out.println("MOCKING FRAMEWORK COMPARISON:");
        System.out.println(".NET (Moq):");
        System.out.println("  var mockRepo = new Mock<IUserRepository>();");
        System.out.println("  mockRepo.Setup(r => r.FindById(1)).Returns(user);");
        System.out.println("  mockRepo.Verify(r => r.Save(It.IsAny<User>()), Times.Once);");
        System.out.println("");
        System.out.println("Java (Mockito):");
        System.out.println("  UserRepository mockRepo = mock(UserRepository.class);");
        System.out.println("  when(mockRepo.findById(1L)).thenReturn(Optional.of(user));");
        System.out.println("  verify(mockRepo).save(any(User.class));");
        
        // Simulate mocking behavior
        System.out.println("\nMOCKING SIMULATION:");
        
        // Create a simple mock simulation
        MockUserRepository mockRepo = new MockUserRepository();
        UserService userService = new UserService(mockRepo);
        
        // Setup mock behavior
        User expectedUser = new User(1L, "John Doe", "john@example.com", "USER", LocalDateTime.now());
        mockRepo.setupFindById(1L, expectedUser);
        
        // Test the service
        Optional<User> result = userService.findById(1L);
        System.out.println("Mock returned: " + result.orElse(null));
        
        // Verify interactions
        System.out.println("Mock findById called: " + mockRepo.wasFindByIdCalled());
        
        System.out.println("\nMOCKING PATTERNS:");
        System.out.println("1. Arrange: Set up mock behavior");
        System.out.println("2. Act: Execute the code under test");
        System.out.println("3. Assert: Verify results and interactions");
        
        System.out.println("\nCOMMON MOCKING SCENARIOS:");
        System.out.println("✅ External dependencies (databases, APIs)");
        System.out.println("✅ Slow or unreliable operations");
        System.out.println("✅ Hard-to-reproduce conditions");
        System.out.println("❌ Value objects or simple data structures");
        System.out.println("❌ The class under test itself");
    }
    
    /**
     * DEMO 4: Testing Patterns
     * 
     * Show common testing patterns and best practices
     */
    public static void demonstrateTestingPatterns() {
        System.out.println("\n--- Demo 4: Testing Patterns ---");
        
        System.out.println("TESTING PATTERNS:");
        
        // Test naming
        System.out.println("\n1. TEST NAMING:");
        System.out.println(".NET: MethodName_StateUnderTest_ExpectedBehavior");
        System.out.println("Java: methodName_StateUnderTest_ExpectedBehavior");
        System.out.println("");
        System.out.println("Examples:");
        System.out.println("  calculateTotal_EmptyCart_ReturnsZero()");
        System.out.println("  findUser_InvalidId_ThrowsException()");
        System.out.println("  processOrder_ValidOrder_UpdatesStatus()");
        
        // Test data builders
        System.out.println("\n2. TEST DATA BUILDERS:");
        System.out.println(".NET: var user = new UserBuilder().WithName(\"John\").Build();");
        System.out.println("Java: User user = UserTestDataBuilder.aUser().withName(\"John\").build();");
        
        // Demonstrate test data builder
        User testUser = UserTestDataBuilder.aUser()
            .withName("Test User")
            .withEmail("test@example.com")
            .build();
        System.out.println("Built test user: " + testUser.getName());
        
        // Parameterized tests
        System.out.println("\n3. PARAMETERIZED TESTS:");
        System.out.println(".NET: [TestCase(1, 2, 3)] public void Add_Parameters_ReturnsSum(int a, int b, int expected)");
        System.out.println("Java: @ParameterizedTest @ValueSource(ints = {1, 2, 3}) void test_WithParameters(int value)");
        
        // Test categories/tags
        System.out.println("\n4. TEST CATEGORIES:");
        System.out.println(".NET: [Category(\"Integration\")] or [Category(\"Unit\")]");
        System.out.println("Java: @Tag(\"integration\") or @Tag(\"unit\")");
        
        // Test lifecycle
        System.out.println("\n5. TEST LIFECYCLE:");
        System.out.println("@BeforeAll    - Once before all tests in class");
        System.out.println("@BeforeEach   - Before each test method");
        System.out.println("@Test         - The actual test");
        System.out.println("@AfterEach    - After each test method");
        System.out.println("@AfterAll     - Once after all tests in class");
        
        // Demonstrate lifecycle
        demonstrateTestLifecycle();
        
        // Test doubles
        System.out.println("\n6. TEST DOUBLES:");
        System.out.println("Dummy:   Objects passed but never used");
        System.out.println("Fake:    Working implementation, simplified");
        System.out.println("Stub:    Provides canned answers");
        System.out.println("Spy:     Records information about calls");
        System.out.println("Mock:    Pre-programmed with expectations");
        
        // AAA Pattern
        System.out.println("\n7. AAA PATTERN:");
        System.out.println("Arrange: Set up test data and conditions");
        System.out.println("Act:     Execute the method under test");
        System.out.println("Assert:  Verify the results");
        
        demonstrateAAAPattern();
    }
    
    /**
     * DEMO 5: Spring Testing
     * 
     * Show Spring Boot testing patterns
     */
    public static void demonstrateSpringTesting() {
        System.out.println("\n--- Demo 5: Spring Testing ---");
        
        System.out.println("SPRING TEST ANNOTATIONS:");
        System.out.println("@SpringBootTest        - Full integration test");
        System.out.println("@WebMvcTest           - Web layer only");
        System.out.println("@DataJpaTest          - JPA repositories only");
        System.out.println("@JsonTest             - JSON serialization");
        System.out.println("@TestConfiguration    - Test-specific configuration");
        System.out.println("@MockBean             - Mock Spring beans");
        System.out.println("@SpyBean              - Spy on Spring beans");
        
        System.out.println("\n.NET CORE TESTING EQUIVALENT:");
        System.out.println(".NET: TestServer and WebApplicationFactory");
        System.out.println("Java: @SpringBootTest with TestRestTemplate");
        System.out.println("");
        System.out.println(".NET: Mock services in DI container");
        System.out.println("Java: @MockBean replaces beans in context");
        
        System.out.println("\nSPRING TEST SLICES:");
        System.out.println("1. @WebMvcTest - Test controllers without full context");
        System.out.println("2. @DataJpaTest - Test repositories with embedded DB");
        System.out.println("3. @JsonTest - Test JSON mapping");
        System.out.println("4. @SpringBootTest - Full integration tests");
        
        // Demonstrate test configurations
        System.out.println("\nTEST CONFIGURATION EXAMPLES:");
        
        System.out.println("\nWeb Layer Test:");
        System.out.println("@WebMvcTest(UserController.class)");
        System.out.println("class UserControllerTest {");
        System.out.println("    @Autowired MockMvc mockMvc;");
        System.out.println("    @MockBean UserService userService;");
        System.out.println("}");
        
        System.out.println("\nRepository Test:");
        System.out.println("@DataJpaTest");
        System.out.println("class UserRepositoryTest {");
        System.out.println("    @Autowired TestEntityManager entityManager;");
        System.out.println("    @Autowired UserRepository repository;");
        System.out.println("}");
        
        System.out.println("\nIntegration Test:");
        System.out.println("@SpringBootTest(webEnvironment = RANDOM_PORT)");
        System.out.println("class UserIntegrationTest {");
        System.out.println("    @Autowired TestRestTemplate restTemplate;");
        System.out.println("    @LocalServerPort int port;");
        System.out.println("}");
        
        System.out.println("\nTEST PROFILES:");
        System.out.println("application-test.properties:");
        System.out.println("  spring.datasource.url=jdbc:h2:mem:testdb");
        System.out.println("  logging.level.org.springframework=DEBUG");
        
        // Simulate Spring test execution
        simulateSpringTest();
    }
    
    /**
     * DEMO 6: Best Practices
     * 
     * Show testing best practices and anti-patterns
     */
    public static void demonstrateBestPractices() {
        System.out.println("\n--- Demo 6: Best Practices ---");
        
        System.out.println("TESTING BEST PRACTICES:");
        
        System.out.println("\n✅ DO:");
        System.out.println("• Write tests first (TDD)");
        System.out.println("• Keep tests simple and focused");
        System.out.println("• Use descriptive test names");
        System.out.println("• Test behavior, not implementation");
        System.out.println("• Mock external dependencies");
        System.out.println("• Use test data builders");
        System.out.println("• Follow AAA pattern");
        System.out.println("• Keep tests independent");
        System.out.println("• Use appropriate test doubles");
        System.out.println("• Test edge cases and error conditions");
        
        System.out.println("\n❌ DON'T:");
        System.out.println("• Test private methods directly");
        System.out.println("• Create brittle tests tied to implementation");
        System.out.println("• Use production data in tests");
        System.out.println("• Ignore test failures");
        System.out.println("• Write tests that depend on other tests");
        System.out.println("• Test framework code");
        System.out.println("• Mock value objects");
        System.out.println("• Have tests without assertions");
        System.out.println("• Use Thread.sleep() in tests");
        System.out.println("• Test everything (focus on business logic)");
        
        System.out.println("\nTEST PYRAMID:");
        System.out.println("     /\\");
        System.out.println("    /UI\\     <- Few, expensive, slow");
        System.out.println("   /____\\");
        System.out.println("  /      \\");
        System.out.println(" /INTEGR-\\   <- Some, moderate cost");
        System.out.println("/________\\");
        System.out.println("/  UNIT    \\  <- Many, cheap, fast");
        System.out.println("/__________\\");
        
        System.out.println("\nTEST COVERAGE GUIDELINES:");
        System.out.println("• Aim for 80%+ line coverage");
        System.out.println("• 100% coverage of critical paths");
        System.out.println("• Focus on branch coverage");
        System.out.println("• Don't chase coverage metrics blindly");
        System.out.println("• Quality > Quantity");
        
        System.out.println("\nCOMMON ANTI-PATTERNS:");
        System.out.println("1. Happy Path Only - Not testing error cases");
        System.out.println("2. Mystery Guest - Tests that depend on external state");
        System.out.println("3. Test Logic in Production - Using test-specific code in production");
        System.out.println("4. Indirect Testing - Testing through other classes");
        System.out.println("5. Assertion Roulette - Multiple assertions without clear failure messages");
        
        System.out.println("\nTDD CYCLE:");
        System.out.println("1. 🔴 RED:    Write failing test");
        System.out.println("2. 🟢 GREEN:  Make it pass (minimal code)");
        System.out.println("3. 🔵 REFACTOR: Clean up the code");
        System.out.println("4. Repeat");
        
        // Demonstrate TDD cycle
        demonstrateTDDCycle();
        
        System.out.println("\nTEST ORGANIZATION:");
        System.out.println("src/test/java/");
        System.out.println("├── unit/              # Unit tests");
        System.out.println("├── integration/       # Integration tests");
        System.out.println("├── fixtures/          # Test data");
        System.out.println("└── resources/         # Test resources");
        
        System.out.println("\nTEST EXECUTION:");
        System.out.println("mvn test              # Run all tests");
        System.out.println("mvn test -Dtest=UserServiceTest  # Run specific test");
        System.out.println("mvn test -Dgroups=unit           # Run by tag/group");
    }
    
    // ============================================================================
    // DEMONSTRATION METHODS
    // ============================================================================
    
    private static void demonstrateCalculatorTesting() {
        System.out.println("\nCALCULATOR TESTING DEMO:");
        
        Calculator calculator = new Calculator();
        
        // Test addition
        int result = calculator.add(2, 3);
        boolean testPassed = result == 5;
        System.out.println("add(2, 3) = " + result + " (expected: 5) - " + (testPassed ? "PASS" : "FAIL"));
        
        // Test division
        try {
            double divResult = calculator.divide(10, 2);
            boolean divTestPassed = divResult == 5.0;
            System.out.println("divide(10, 2) = " + divResult + " (expected: 5.0) - " + (divTestPassed ? "PASS" : "FAIL"));
        } catch (Exception e) {
            System.out.println("divide(10, 2) - FAIL: " + e.getMessage());
        }
        
        // Test division by zero
        try {
            calculator.divide(10, 0);
            System.out.println("divide(10, 0) - FAIL: Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("divide(10, 0) - PASS: Correctly threw exception");
        }
    }
    
    private static void demonstrateTestLifecycle() {
        System.out.println("\nTEST LIFECYCLE SIMULATION:");
        
        TestLifecycleDemo demo = new TestLifecycleDemo();
        
        // Simulate @BeforeAll
        demo.setupClass();
        
        // Simulate first test
        demo.setupTest();
        demo.testMethod1();
        demo.tearDownTest();
        
        // Simulate second test
        demo.setupTest();
        demo.testMethod2();
        demo.tearDownTest();
        
        // Simulate @AfterAll
        demo.tearDownClass();
    }
    
    private static void demonstrateAAAPattern() {
        System.out.println("\nAAA PATTERN DEMO:");
        
        System.out.println("Testing: OrderService.calculateTotal()");
        
        // ARRANGE
        System.out.println("ARRANGE: Setting up test data");
        OrderService orderService = new OrderService();
        Order order = new Order();
        order.addItem(new OrderItem("Laptop", new BigDecimal("999.99"), 1));
        order.addItem(new OrderItem("Mouse", new BigDecimal("29.99"), 2));
        
        // ACT
        System.out.println("ACT: Executing method under test");
        BigDecimal total = orderService.calculateTotal(order);
        
        // ASSERT
        System.out.println("ASSERT: Verifying results");
        BigDecimal expected = new BigDecimal("1059.97");
        boolean testPassed = total.compareTo(expected) == 0;
        System.out.println("Total: $" + total + " (expected: $" + expected + ") - " + (testPassed ? "PASS" : "FAIL"));
    }
    
    private static void simulateSpringTest() {
        System.out.println("\nSPRING TEST SIMULATION:");
        
        // Simulate @SpringBootTest
        System.out.println("Starting Spring context...");
        System.out.println("Loading test configuration...");
        System.out.println("Setting up test beans...");
        
        // Simulate test execution
        MockUserRepository mockRepo = new MockUserRepository();
        UserService userService = new UserService(mockRepo);
        
        User testUser = new User(1L, "Test User", "test@example.com", "USER", LocalDateTime.now());
        mockRepo.setupFindById(1L, testUser);
        
        // Execute test
        Optional<User> result = userService.findById(1L);
        boolean testPassed = result.isPresent() && result.get().getName().equals("Test User");
        
        System.out.println("Test execution: " + (testPassed ? "PASS" : "FAIL"));
        System.out.println("Shutting down Spring context...");
    }
    
    private static void demonstrateTDDCycle() {
        System.out.println("\nTDD CYCLE DEMO:");
        
        System.out.println("Implementing: StringUtils.reverse()");
        
        // RED: Write failing test
        System.out.println("🔴 RED: Write failing test");
        System.out.println("  Test: reverse(\"hello\") should return \"olleh\"");
        
        StringUtils stringUtils = new StringUtils();
        try {
            String result = stringUtils.reverse("hello");
            System.out.println("  Result: " + result + " - Test would FAIL (not implemented)");
        } catch (UnsupportedOperationException e) {
            System.out.println("  Method not implemented - Test FAILS ✓");
        }
        
        // GREEN: Make it pass
        System.out.println("🟢 GREEN: Implement minimal code to make test pass");
        // StringUtils would be implemented here
        System.out.println("  Implement reverse method...");
        
        // REFACTOR: Clean up
        System.out.println("🔵 REFACTOR: Clean up and optimize");
        System.out.println("  Code is simple, no refactoring needed");
        
        System.out.println("  Repeat cycle for next requirement...");
    }
    
    private static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
    
    // ============================================================================
    // PRODUCTION CODE FOR TESTING
    // ============================================================================
    
    /**
     * Simple calculator for testing demonstration
     */
    public static class Calculator {
        public int add(int a, int b) {
            return a + b;
        }
        
        public int subtract(int a, int b) {
            return a - b;
        }
        
        public int multiply(int a, int b) {
            return a * b;
        }
        
        public double divide(int a, int b) {
            if (b == 0) {
                throw new IllegalArgumentException("Cannot divide by zero");
            }
            return (double) a / b;
        }
    }
    
    /**
     * String utilities for TDD demonstration
     */
    public static class StringUtils {
        public String reverse(String input) {
            // For TDD demo, this starts as not implemented
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        public boolean isPalindrome(String input) {
            if (input == null) return false;
            String cleaned = input.toLowerCase().replaceAll("[^a-z0-9]", "");
            return cleaned.equals(reverse(cleaned));
        }
    }
    
    /**
     * User service for testing demonstration
     */
    @Service
    @RequiredArgsConstructor
    public static class UserService {
        private final UserRepository userRepository;
        
        public Optional<User> findById(Long id) {
            return userRepository.findById(id);
        }
        
        public User createUser(String name, String email) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email");
            }
            
            User user = new User(null, name, email, "USER", LocalDateTime.now());
            return userRepository.save(user);
        }
        
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }
    }
    
    /**
     * User repository interface
     */
    public interface UserRepository {
        Optional<User> findById(Long id);
        User save(User user);
        List<User> findAll();
        void deleteById(Long id);
    }
    
    /**
     * Mock repository for demonstration
     */
    public static class MockUserRepository implements UserRepository {
        private final Map<Long, User> users = new HashMap<>();
        private boolean findByIdCalled = false;
        
        public void setupFindById(Long id, User user) {
            users.put(id, user);
        }
        
        @Override
        public Optional<User> findById(Long id) {
            findByIdCalled = true;
            return Optional.ofNullable(users.get(id));
        }
        
        @Override
        public User save(User user) {
            if (user.getId() == null) {
                user.setId(System.currentTimeMillis());
            }
            users.put(user.getId(), user);
            return user;
        }
        
        @Override
        public List<User> findAll() {
            return new ArrayList<>(users.values());
        }
        
        @Override
        public void deleteById(Long id) {
            users.remove(id);
        }
        
        public boolean wasFindByIdCalled() {
            return findByIdCalled;
        }
    }
    
    /**
     * Order service for testing demonstration
     */
    public static class OrderService {
        public BigDecimal calculateTotal(Order order) {
            return order.getItems().stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        
        public Order processOrder(Order order) {
            if (order.getItems().isEmpty()) {
                throw new IllegalStateException("Cannot process empty order");
            }
            
            BigDecimal total = calculateTotal(order);
            order.setTotal(total);
            order.setStatus("PROCESSED");
            
            return order;
        }
    }
    
    /**
     * Test data builder for User
     */
    public static class UserTestDataBuilder {
        private Long id = 1L;
        private String name = "Default Name";
        private String email = "default@example.com";
        private String role = "USER";
        private LocalDateTime createdAt = LocalDateTime.now();
        
        public static UserTestDataBuilder aUser() {
            return new UserTestDataBuilder();
        }
        
        public UserTestDataBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public UserTestDataBuilder withName(String name) {
            this.name = name;
            return this;
        }
        
        public UserTestDataBuilder withEmail(String email) {
            this.email = email;
            return this;
        }
        
        public UserTestDataBuilder withRole(String role) {
            this.role = role;
            return this;
        }
        
        public UserTestDataBuilder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public User build() {
            return new User(id, name, email, role, createdAt);
        }
    }
    
    /**
     * Test lifecycle demonstration
     */
    public static class TestLifecycleDemo {
        
        public void setupClass() {
            System.out.println("  @BeforeAll: Setting up test class resources");
        }
        
        public void setupTest() {
            System.out.println("  @BeforeEach: Setting up individual test");
        }
        
        public void testMethod1() {
            System.out.println("  @Test: Running test method 1");
        }
        
        public void testMethod2() {
            System.out.println("  @Test: Running test method 2");
        }
        
        public void tearDownTest() {
            System.out.println("  @AfterEach: Cleaning up after test");
        }
        
        public void tearDownClass() {
            System.out.println("  @AfterAll: Cleaning up test class resources");
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
    public static class Order {
        private Long id;
        private List<OrderItem> items = new ArrayList<>();
        private BigDecimal total = BigDecimal.ZERO;
        private String status = "PENDING";
        private LocalDateTime createdAt = LocalDateTime.now();
        
        public void addItem(OrderItem item) {
            items.add(item);
        }
    }
    
    @Data
    @AllArgsConstructor
    public static class OrderItem {
        private String name;
        private BigDecimal price;
        private int quantity;
    }
}

/*
LIVE CODING TIPS:

1. START WITH COMPARISONS:
   - Always show .NET equivalent first
   - Explain conceptual similarities
   - Highlight syntax differences
   - Map frameworks and tools

2. BUILD TEST INCREMENTALLY:
   - Start with simple assertions
   - Add mocking gradually
   - Show integration testing
   - End with Spring Boot tests

3. INTERACTIVE ELEMENTS:
   - "How would you test this in .NET?"
   - "What assertion would you use?"
   - "When should we mock this dependency?"
   - Write failing tests first

4. COMMON GOTCHAS:
   - Test naming conventions
   - Mock vs Spy differences
   - Spring test slice confusion
   - Assertion library choices

5. PRACTICAL EXERCISES:
   - TDD kata exercises
   - Mock object scenarios
   - Integration test setup
   - Test data management

6. BEST PRACTICE EMPHASIS:
   - Test pyramid importance
   - AAA pattern consistency
   - Test independence
   - Coverage vs quality

EXPECTED QUESTIONS:
- "What's the difference between @Mock and @MockBean?"
- "When should I use @SpringBootTest vs @WebMvcTest?"
- "How do I test private methods?"
- "What's the difference between Mockito and PowerMock?"
- "How do I test asynchronous code?"
- "What test coverage should I aim for?"

DEMO VARIATIONS:
- Can focus more on TDD practices
- Can include parameterized tests
- Can show database testing with Testcontainers
- Can demonstrate property-based testing
- Can include performance testing basics

MAVEN TEST COMMANDS:
- mvn test: Run all tests
- mvn test -Dtest=ClassName: Run specific test class
- mvn test -Dgroups=unit: Run tests by tag
- mvn verify: Run integration tests
*/