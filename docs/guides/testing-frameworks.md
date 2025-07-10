# Testing Frameworks Deep Dive
## Java Testing Ecosystem for .NET Developers

> **Comprehensive guide** to Java testing frameworks with extensive .NET comparisons, covering JUnit 5, Mockito, AssertJ, and enterprise testing patterns.

## 🎯 What You'll Master

By the end of this guide, you'll understand:
- **WHY** Java testing frameworks differ from .NET testing approaches
- **HOW** to write comprehensive test suites using JUnit 5, Mockito, and AssertJ
- **WHEN** to use different testing patterns and strategies appropriately  
- **WHERE** to focus testing efforts for maximum value and maintainability

**Time Investment**: 60-75 minutes of focused reading + 3-4 hours of hands-on practice

## 🧪 **Testing Philosophy Comparison**

### **Framework Architecture: Convention vs. Configuration**

**Java Testing Stack:**
```java
// JUnit 5 - Modern, annotation-driven testing
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    
    @Mock
    private PaymentService paymentService;
    
    @Mock  
    private InventoryService inventoryService;
    
    @InjectMocks
    private OrderService orderService;
    
    @BeforeEach
    void setUp() {
        // Setup before each test
    }
    
    @Test
    @DisplayName("Should process order successfully when inventory is available")
    void shouldProcessOrderWhenInventoryAvailable() {
        // Given
        given(inventoryService.isAvailable("product-1", 2)).willReturn(true);
        given(paymentService.processPayment(any())).willReturn(PaymentResult.success());
        
        // When
        Order result = orderService.processOrder(new OrderRequest("product-1", 2));
        
        // Then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CONFIRMED);
        then(paymentService).should().processPayment(any());
    }
}
```

**.NET Testing Stack:**
```csharp
// xUnit - Minimal, attribute-driven testing
public class OrderServiceTests
{
    private readonly Mock<IPaymentService> _paymentServiceMock;
    private readonly Mock<IInventoryService> _inventoryServiceMock;
    private readonly OrderService _orderService;
    
    public OrderServiceTests()
    {
        _paymentServiceMock = new Mock<IPaymentService>();
        _inventoryServiceMock = new Mock<IInventoryService>();
        _orderService = new OrderService(_paymentServiceMock.Object, _inventoryServiceMock.Object);
    }
    
    [Fact]
    [Trait("Category", "Unit")]
    public void ProcessOrder_WhenInventoryAvailable_ShouldReturnConfirmedOrder()
    {
        // Arrange
        _inventoryServiceMock.Setup(x => x.IsAvailable("product-1", 2)).Returns(true);
        _paymentServiceMock.Setup(x => x.ProcessPayment(It.IsAny<PaymentRequest>()))
                          .Returns(PaymentResult.Success());
        
        // Act
        var result = _orderService.ProcessOrder(new OrderRequest("product-1", 2));
        
        // Assert
        result.Status.Should().Be(OrderStatus.Confirmed);
        _paymentServiceMock.Verify(x => x.ProcessPayment(It.IsAny<PaymentRequest>()), Times.Once);
    }
}
```

**🎯 Key Philosophical Differences:**
- **Java**: Convention-heavy with rich annotations and lifecycle management
- **.NET**: Minimal framework with explicit setup and configuration

---

## 🔬 **JUnit 5 vs .NET Testing Frameworks**

### **Test Structure and Organization**

**JUnit 5 Test Lifecycle:**
```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // Share instance across tests
class UserServiceIntegrationTest {
    
    private UserService userService;
    
    @BeforeAll
    void setUpClass() {
        // One-time setup for all tests
        System.out.println("Setting up test class");
    }
    
    @BeforeEach  
    void setUp() {
        // Setup before each test method
        userService = new UserService();
    }
    
    @AfterEach
    void tearDown() {
        // Cleanup after each test method
        userService.clearCache();
    }
    
    @AfterAll
    void tearDownClass() {
        // One-time cleanup for all tests
        System.out.println("Tearing down test class");
    }
    
    @Test
    @DisplayName("User creation should assign unique ID")
    @Tag("unit")
    @Tag("fast")
    void shouldAssignUniqueIdWhenCreatingUser() {
        // Test implementation
    }
    
    @ParameterizedTest
    @DisplayName("Should validate email format")
    @ValueSource(strings = {"invalid-email", "@invalid.com", "user@", ""})
    void shouldRejectInvalidEmailFormats(String invalidEmail) {
        assertThatThrownBy(() -> userService.createUser("John", invalidEmail))
            .isInstanceOf(InvalidEmailException.class);
    }
    
    @RepeatedTest(10)
    @DisplayName("ID generation should be thread-safe")
    void idGenerationShouldBeThreadSafe() {
        // Test that runs 10 times
    }
    
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    @Test
    void shouldCompleteWithinTimeout() {
        // Test that must complete within 2 seconds
    }
}
```

**xUnit Test Structure:**
```csharp
public class UserServiceIntegrationTests : IClassFixture<DatabaseFixture>, IDisposable
{
    private readonly DatabaseFixture _databaseFixture;
    private readonly UserService _userService;
    
    public UserServiceIntegrationTests(DatabaseFixture databaseFixture)
    {
        _databaseFixture = databaseFixture;
        _userService = new UserService();
    }
    
    [Fact]
    [Trait("Category", "Unit")]
    [Trait("Speed", "Fast")]
    public void CreateUser_ShouldAssignUniqueId()
    {
        // Test implementation
    }
    
    [Theory]
    [InlineData("invalid-email")]
    [InlineData("@invalid.com")]
    [InlineData("user@")]
    [InlineData("")]
    public void CreateUser_WithInvalidEmail_ShouldThrowException(string invalidEmail)
    {
        // Arrange & Act & Assert
        var exception = Assert.Throws<InvalidEmailException>(
            () => _userService.CreateUser("John", invalidEmail));
    }
    
    [Fact(Timeout = 2000)]
    public void ShouldCompleteWithinTimeout()
    {
        // Test that must complete within 2 seconds
    }
    
    public void Dispose()
    {
        _userService.ClearCache();
    }
}

// Class fixture for shared setup
public class DatabaseFixture : IDisposable
{
    public DatabaseFixture()
    {
        // One-time setup
    }
    
    public void Dispose()
    {
        // One-time cleanup
    }
}
```

**NUnit Alternative:**
```csharp
[TestFixture]
public class UserServiceTests
{
    private UserService _userService;
    
    [OneTimeSetUp]
    public void OneTimeSetUp()
    {
        // One-time setup
    }
    
    [SetUp]
    public void SetUp()
    {
        _userService = new UserService();
    }
    
    [TearDown]
    public void TearDown()
    {
        _userService?.ClearCache();
    }
    
    [Test]
    [Category("Unit")]
    [Category("Fast")]
    public void CreateUser_ShouldAssignUniqueId()
    {
        // Test implementation
    }
    
    [TestCase("invalid-email")]
    [TestCase("@invalid.com")]
    [TestCase("user@")]
    [TestCase("")]
    public void CreateUser_WithInvalidEmail_ShouldThrowException(string invalidEmail)
    {
        Assert.Throws<InvalidEmailException>(
            () => _userService.CreateUser("John", invalidEmail));
    }
    
    [Test, Timeout(2000)]
    public void ShouldCompleteWithinTimeout()
    {
        // Test that must complete within 2 seconds
    }
}
```

### **Dynamic Tests and Parameterization**

**JUnit 5 Dynamic Tests:**
```java
class DynamicTestExamples {
    
    @TestFactory
    @DisplayName("Email validation tests")
    Stream<DynamicTest> emailValidationTests() {
        List<String> validEmails = Arrays.asList(
            "user@example.com",
            "test.email@domain.co.uk",
            "admin+tag@company.org"
        );
        
        return validEmails.stream()
            .map(email -> DynamicTest.dynamicTest(
                "Testing valid email: " + email,
                () -> assertDoesNotThrow(() -> userService.validateEmail(email))
            ));
    }
    
    @ParameterizedTest
    @MethodSource("orderScenarios")
    @DisplayName("Order processing scenarios")
    void shouldProcessOrderCorrectly(OrderScenario scenario) {
        // Given
        given(inventoryService.isAvailable(scenario.productId, scenario.quantity))
            .willReturn(scenario.inventoryAvailable);
        given(paymentService.processPayment(any()))
            .willReturn(scenario.paymentResult);
        
        // When & Then
        if (scenario.shouldSucceed) {
            Order result = orderService.processOrder(scenario.request);
            assertThat(result.getStatus()).isEqualTo(OrderStatus.CONFIRMED);
        } else {
            assertThatThrownBy(() -> orderService.processOrder(scenario.request))
                .isInstanceOf(scenario.expectedException);
        }
    }
    
    static Stream<OrderScenario> orderScenarios() {
        return Stream.of(
            new OrderScenario("Happy path", "product-1", 1, true, 
                             PaymentResult.success(), true, null),
            new OrderScenario("Insufficient inventory", "product-1", 10, false, 
                             null, false, InsufficientInventoryException.class),
            new OrderScenario("Payment failed", "product-1", 1, true, 
                             PaymentResult.failed(), false, PaymentFailedException.class)
        );
    }
}
```

**.NET Data-Driven Tests:**
```csharp
public class DynamicTestExamples
{
    public static IEnumerable<object[]> EmailValidationData =>
        new List<object[]>
        {
            new object[] { "user@example.com", true },
            new object[] { "test.email@domain.co.uk", true },
            new object[] { "admin+tag@company.org", true },
            new object[] { "invalid-email", false },
            new object[] { "@invalid.com", false }
        };
    
    [Theory]
    [MemberData(nameof(EmailValidationData))]
    public void ValidateEmail_WithVariousInputs_ShouldReturnExpectedResult(
        string email, bool expectedValid)
    {
        // Act
        bool isValid = _userService.ValidateEmail(email);
        
        // Assert
        isValid.Should().Be(expectedValid);
    }
    
    public static IEnumerable<object[]> OrderScenarios =>
        new List<object[]>
        {
            new object[] { "product-1", 1, true, PaymentResult.Success(), true, null },
            new object[] { "product-1", 10, false, null, false, typeof(InsufficientInventoryException) },
            new object[] { "product-1", 1, true, PaymentResult.Failed(), false, typeof(PaymentFailedException) }
        };
    
    [Theory]
    [MemberData(nameof(OrderScenarios))]
    public void ProcessOrder_WithVariousScenarios_ShouldBehaveCorrectly(
        string productId, int quantity, bool inventoryAvailable, 
        PaymentResult paymentResult, bool shouldSucceed, Type expectedException)
    {
        // Arrange
        _inventoryServiceMock.Setup(x => x.IsAvailable(productId, quantity))
                            .Returns(inventoryAvailable);
        if (paymentResult != null)
        {
            _paymentServiceMock.Setup(x => x.ProcessPayment(It.IsAny<PaymentRequest>()))
                              .Returns(paymentResult);
        }
        
        var request = new OrderRequest(productId, quantity);
        
        // Act & Assert
        if (shouldSucceed)
        {
            var result = _orderService.ProcessOrder(request);
            result.Status.Should().Be(OrderStatus.Confirmed);
        }
        else
        {
            _orderService.Invoking(x => x.ProcessOrder(request))
                        .Should().Throw<Exception>()
                        .Which.Should().BeOfType(expectedException);
        }
    }
}
```

---

## 🎭 **Mockito vs .NET Mocking Frameworks**

### **Mock Creation and Setup**

**Mockito Patterns:**
```java
@ExtendWith(MockitoExtension.class)
class MockitoExamples {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock  
    private EmailService emailService;
    
    @Spy  // Partial mock - real object with some methods mocked
    private UserValidator userValidator = new UserValidator();
    
    @Captor  // Capture arguments passed to mocks
    private ArgumentCaptor<User> userCaptor;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void shouldCreateUserWithValidData() {
        // Arrange - Method stubbing
        User savedUser = new User(1L, "John", "john@example.com");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        
        // Act
        User result = userService.createUser("John", "john@example.com");
        
        // Assert - Verification
        assertThat(result.getId()).isEqualTo(1L);
        verify(userRepository).save(userCaptor.capture());
        verify(emailService).sendWelcomeEmail(savedUser);
        
        // Verify captured arguments
        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getName()).isEqualTo("John");
        assertThat(capturedUser.getEmail()).isEqualTo("john@example.com");
    }
    
    @Test
    void shouldHandleExceptionGracefully() {
        // Arrange - Exception stubbing
        when(userRepository.save(any(User.class)))
            .thenThrow(new DatabaseException("Connection failed"));
        
        // Act & Assert
        assertThatThrownBy(() -> userService.createUser("John", "john@example.com"))
            .isInstanceOf(UserCreationException.class)
            .hasCauseInstanceOf(DatabaseException.class);
        
        // Verify email was not sent due to exception
        verify(emailService, never()).sendWelcomeEmail(any());
    }
    
    @Test
    void shouldUseAnswerForComplexBehavior() {
        // Custom behavior with Answer
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(System.currentTimeMillis());  // Simulate ID generation
            return user;
        });
        
        User result = userService.createUser("John", "john@example.com");
        assertThat(result.getId()).isNotNull();
    }
}
```

**Moq Patterns (.NET):**
```csharp
public class MoqExamples
{
    private readonly Mock<IUserRepository> _userRepositoryMock;
    private readonly Mock<IEmailService> _emailServiceMock;
    private readonly UserService _userService;
    
    public MoqExamples()
    {
        _userRepositoryMock = new Mock<IUserRepository>();
        _emailServiceMock = new Mock<IEmailService>();
        _userService = new UserService(_userRepositoryMock.Object, _emailServiceMock.Object);
    }
    
    [Fact]
    public void CreateUser_WithValidData_ShouldReturnUserWithId()
    {
        // Arrange
        var savedUser = new User(1, "John", "john@example.com");
        _userRepositoryMock.Setup(x => x.Save(It.IsAny<User>())).Returns(savedUser);
        
        // Act
        var result = _userService.CreateUser("John", "john@example.com");
        
        // Assert
        result.Id.Should().Be(1);
        _userRepositoryMock.Verify(x => x.Save(It.Is<User>(u => 
            u.Name == "John" && u.Email == "john@example.com")), Times.Once);
        _emailServiceMock.Verify(x => x.SendWelcomeEmail(savedUser), Times.Once);
    }
    
    [Fact]
    public void CreateUser_WhenRepositoryFails_ShouldThrowException()
    {
        // Arrange
        _userRepositoryMock.Setup(x => x.Save(It.IsAny<User>()))
                          .Throws<DatabaseException>();
        
        // Act & Assert
        _userService.Invoking(x => x.CreateUser("John", "john@example.com"))
                   .Should().Throw<UserCreationException>()
                   .WithInnerException<DatabaseException>();
        
        _emailServiceMock.Verify(x => x.SendWelcomeEmail(It.IsAny<User>()), Times.Never);
    }
    
    [Fact]
    public void CreateUser_WithCustomCallback_ShouldWork()
    {
        // Custom behavior with Callback
        _userRepositoryMock.Setup(x => x.Save(It.IsAny<User>()))
                          .Callback<User>(user => user.Id = DateTime.Now.Ticks)
                          .Returns<User>(user => user);
        
        var result = _userService.CreateUser("John", "john@example.com");
        result.Id.Should().BeGreaterThan(0);
    }
}
```

**NSubstitute Alternative (.NET):**
```csharp
public class NSubstituteExamples
{
    private readonly IUserRepository _userRepository;
    private readonly IEmailService _emailService;
    private readonly UserService _userService;
    
    public NSubstituteExamples()
    {
        _userRepository = Substitute.For<IUserRepository>();
        _emailService = Substitute.For<IEmailService>();
        _userService = new UserService(_userRepository, _emailService);
    }
    
    [Fact]
    public void CreateUser_WithValidData_ShouldReturnUserWithId()
    {
        // Arrange
        var savedUser = new User(1, "John", "john@example.com");
        _userRepository.Save(Arg.Any<User>()).Returns(savedUser);
        
        // Act
        var result = _userService.CreateUser("John", "john@example.com");
        
        // Assert
        result.Id.Should().Be(1);
        _userRepository.Received(1).Save(Arg.Is<User>(u => 
            u.Name == "John" && u.Email == "john@example.com"));
        _emailService.Received(1).SendWelcomeEmail(savedUser);
    }
    
    [Fact]
    public void CreateUser_WhenRepositoryFails_ShouldThrowException()
    {
        // Arrange
        _userRepository.When(x => x.Save(Arg.Any<User>()))
                      .Do(x => { throw new DatabaseException(); });
        
        // Act & Assert
        _userService.Invoking(x => x.CreateUser("John", "john@example.com"))
                   .Should().Throw<UserCreationException>();
        
        _emailService.DidNotReceive().SendWelcomeEmail(Arg.Any<User>());
    }
}
```

---

## 🔍 **AssertJ vs FluentAssertions**

### **Fluent Assertion Patterns**

**AssertJ (Java):**
```java
class AssertJExamples {
    
    @Test
    void shouldDemonstrateBasicAssertions() {
        String name = "John Doe";
        int age = 30;
        List<String> hobbies = Arrays.asList("reading", "coding", "gaming");
        
        // Basic assertions
        assertThat(name)
            .isNotNull()
            .isNotEmpty()
            .contains("John")
            .startsWith("John")
            .endsWith("Doe")
            .hasSize(8);
        
        assertThat(age)
            .isPositive()
            .isBetween(25, 35)
            .isEqualTo(30);
        
        assertThat(hobbies)
            .isNotEmpty()
            .hasSize(3)
            .contains("coding")
            .containsExactly("reading", "coding", "gaming")
            .allMatch(hobby -> hobby.length() > 4);
    }
    
    @Test
    void shouldDemonstrateObjectAssertions() {
        User user = new User(1L, "John", "john@example.com", 30);
        User otherUser = new User(2L, "Jane", "jane@example.com", 25);
        
        assertThat(user)
            .isNotNull()
            .satisfies(u -> {
                assertThat(u.getId()).isEqualTo(1L);
                assertThat(u.getName()).isEqualTo("John");
                assertThat(u.getAge()).isGreaterThan(18);
            })
            .extracting("name", "age")
            .containsExactly("John", 30);
        
        assertThat(user)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isNotEqualTo(otherUser);
    }
    
    @Test
    void shouldDemonstrateCollectionAssertions() {
        List<User> users = Arrays.asList(
            new User(1L, "John", "john@example.com", 30),
            new User(2L, "Jane", "jane@example.com", 25),
            new User(3L, "Bob", "bob@example.com", 35)
        );
        
        assertThat(users)
            .hasSize(3)
            .extracting(User::getName)
            .containsExactly("John", "Jane", "Bob");
        
        assertThat(users)
            .filteredOn(user -> user.getAge() > 25)
            .hasSize(2)
            .extracting("name")
            .containsExactly("John", "Bob");
        
        assertThat(users)
            .anySatisfy(user -> assertThat(user.getName()).startsWith("J"))
            .allSatisfy(user -> assertThat(user.getAge()).isGreaterThan(20));
    }
    
    @Test
    void shouldDemonstrateExceptionAssertions() {
        assertThatThrownBy(() -> userService.createUser(null, "email"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Name cannot be null")
            .hasNoCause();
        
        assertThatThrownBy(() -> userService.createUser("John", "invalid-email"))
            .isInstanceOf(ValidationException.class)
            .hasMessageContaining("Invalid email format")
            .satisfies(ex -> {
                ValidationException validationEx = (ValidationException) ex;
                assertThat(validationEx.getFieldErrors()).hasSize(1);
                assertThat(validationEx.getFieldErrors().get(0).getField()).isEqualTo("email");
            });
    }
}
```

**FluentAssertions (.NET):**
```csharp
public class FluentAssertionsExamples
{
    [Fact]
    public void ShouldDemonstrateBasicAssertions()
    {
        string name = "John Doe";
        int age = 30;
        var hobbies = new List<string> { "reading", "coding", "gaming" };
        
        // Basic assertions
        name.Should().NotBeNull()
            .And.NotBeEmpty()
            .And.Contain("John")
            .And.StartWith("John")
            .And.EndWith("Doe")
            .And.HaveLength(8);
        
        age.Should().BePositive()
           .And.BeInRange(25, 35)
           .And.Be(30);
        
        hobbies.Should().NotBeEmpty()
               .And.HaveCount(3)
               .And.Contain("coding")
               .And.ContainInOrder("reading", "coding", "gaming")
               .And.OnlyContain(hobby => hobby.Length > 4);
    }
    
    [Fact]
    public void ShouldDemonstrateObjectAssertions()
    {
        var user = new User(1, "John", "john@example.com", 30);
        var otherUser = new User(2, "Jane", "jane@example.com", 25);
        
        user.Should().NotBeNull()
            .And.Satisfy(u => u.Id.Should().Be(1),
                        u => u.Name.Should().Be("John"),
                        u => u.Age.Should().BeGreaterThan(18));
        
        user.Should().BeEquivalentTo(otherUser, options => options
            .Excluding(u => u.Id)
            .Excluding(u => u.Name)
            .Excluding(u => u.Email));
    }
    
    [Fact]
    public void ShouldDemonstrateCollectionAssertions()
    {
        var users = new List<User>
        {
            new User(1, "John", "john@example.com", 30),
            new User(2, "Jane", "jane@example.com", 25),
            new User(3, "Bob", "bob@example.com", 35)
        };
        
        users.Should().HaveCount(3)
             .And.OnlyContain(u => u.Age > 20);
        
        users.Select(u => u.Name).Should().ContainInOrder("John", "Jane", "Bob");
        
        users.Where(u => u.Age > 25).Should().HaveCount(2)
             .And.Satisfy(
                 u => u.Name.Should().Be("John"),
                 u => u.Name.Should().Be("Bob"));
    }
    
    [Fact]
    public void ShouldDemonstrateExceptionAssertions()
    {
        Action act = () => _userService.CreateUser(null, "email");
        
        act.Should().Throw<ArgumentNullException>()
           .WithMessage("*Name cannot be null*")
           .And.ParamName.Should().Be("name");
        
        Action invalidEmailAct = () => _userService.CreateUser("John", "invalid-email");
        
        invalidEmailAct.Should().Throw<ValidationException>()
                      .WithMessage("*Invalid email format*")
                      .Which.FieldErrors.Should().HaveCount(1)
                      .And.Contain(error => error.Field == "email");
    }
}
```

---

## 🏗️ **Testing Spring Boot Applications**

### **Test Slices for Focused Testing**

**Web Layer Testing:**
```java
@WebMvcTest(UserController.class)  // Only load web layer
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean  // Mock Spring bean in context
    private UserService userService;
    
    @Test
    @DisplayName("GET /users/{id} should return user when found")
    void shouldReturnUserWhenFound() throws Exception {
        // Given
        User user = new User(1L, "John", "john@example.com");
        given(userService.findById(1L)).willReturn(Optional.of(user));
        
        // When & Then
        mockMvc.perform(get("/api/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
    
    @Test
    @DisplayName("POST /users should create user with valid data")
    void shouldCreateUserWithValidData() throws Exception {
        // Given
        CreateUserRequest request = new CreateUserRequest("John", "john@example.com");
        User createdUser = new User(1L, "John", "john@example.com");
        given(userService.createUser(any(CreateUserRequest.class))).willReturn(createdUser);
        
        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "John",
                        "email": "john@example.com"
                    }
                    """))
                .andExpected(status().isCreated())
                .andExpect(header().string("Location", "/api/users/1"))
                .andExpect(jsonPath("$.id").value(1));
    }
    
    @Test
    @DisplayName("POST /users should return 400 for invalid data")
    void shouldReturn400ForInvalidData() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "",
                        "email": "invalid-email"
                    }
                    """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[*].field").value(containsInAnyOrder("name", "email")));
    }
}
```

**Data Layer Testing:**
```java
@DataJpaTest  // Only load JPA repositories and entities
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:testdb"
})
class UserRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    @DisplayName("Should find user by email")
    void shouldFindUserByEmail() {
        // Given
        User user = new User(null, "John", "john@example.com");
        entityManager.persistAndFlush(user);
        
        // When
        Optional<User> found = userRepository.findByEmail("john@example.com");
        
        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("John");
    }
    
    @Test
    @DisplayName("Should find users by name containing")
    void shouldFindUsersByNameContaining() {
        // Given
        entityManager.persistAndFlush(new User(null, "John Doe", "john@example.com"));
        entityManager.persistAndFlush(new User(null, "Jane Doe", "jane@example.com"));
        entityManager.persistAndFlush(new User(null, "Bob Smith", "bob@example.com"));
        
        // When
        List<User> found = userRepository.findByNameContainingIgnoreCase("doe");
        
        // Then
        assertThat(found).hasSize(2)
            .extracting(User::getName)
            .containsExactlyInAnyOrder("John Doe", "Jane Doe");
    }
    
    @Test
    @DisplayName("Should handle pagination correctly")
    void shouldHandlePaginationCorrectly() {
        // Given - create multiple users
        for (int i = 1; i <= 10; i++) {
            entityManager.persist(new User(null, "User " + i, "user" + i + "@example.com"));
        }
        entityManager.flush();
        
        // When
        Pageable pageable = PageRequest.of(0, 3, Sort.by("name"));
        Page<User> page = userRepository.findAll(pageable);
        
        // Then
        assertThat(page.getContent()).hasSize(3);
        assertThat(page.getTotalElements()).isEqualTo(10);
        assertThat(page.getTotalPages()).isEqualTo(4);
        assertThat(page.getContent().get(0).getName()).isEqualTo("User 1");
    }
}
```

### **Full Integration Testing**

**Complete Application Testing:**
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:integrationtest",
    "app.feature.email.enabled=false"
})
@Transactional
class UserServiceIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    @DisplayName("Should create and retrieve user end-to-end")
    void shouldCreateAndRetrieveUserEndToEnd() {
        // Given
        CreateUserRequest request = new CreateUserRequest("John", "john@example.com");
        
        // When - Create user
        ResponseEntity<UserResponse> createResponse = restTemplate.postForEntity(
            "/api/users", request, UserResponse.class);
        
        // Then - Verify creation
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResponse.getBody().getName()).isEqualTo("John");
        
        Long userId = createResponse.getBody().getId();
        
        // When - Retrieve user
        ResponseEntity<UserResponse> getResponse = restTemplate.getForEntity(
            "/api/users/" + userId, UserResponse.class);
        
        // Then - Verify retrieval
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo("John");
        assertThat(getResponse.getBody().getEmail()).isEqualTo("john@example.com");
        
        // Verify database state
        Optional<User> userInDb = userRepository.findById(userId);
        assertThat(userInDb).isPresent();
        assertThat(userInDb.get().getName()).isEqualTo("John");
    }
}
```

---

## 📊 **Testing Strategy and Best Practices**

### **Test Pyramid Implementation**

**Unit Tests (70% of tests):**
```java
// Fast, isolated, deterministic
@ExtendWith(MockitoExtension.class)
class OrderCalculatorTest {
    
    @Mock
    private TaxService taxService;
    
    @Mock
    private DiscountService discountService;
    
    @InjectMocks
    private OrderCalculator orderCalculator;
    
    @Test
    void shouldCalculateTotalWithTaxAndDiscount() {
        // Given
        Order order = createOrderWithItems(100.00);
        given(taxService.calculateTax(100.00)).willReturn(10.00);
        given(discountService.calculateDiscount(order)).willReturn(5.00);
        
        // When
        BigDecimal total = orderCalculator.calculateTotal(order);
        
        // Then
        assertThat(total).isEqualByComparingTo(new BigDecimal("105.00"));
    }
}
```

**Integration Tests (20% of tests):**
```java
// Test component interactions
@SpringBootTest
@Testcontainers
class OrderServiceIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    
    @Autowired
    private OrderService orderService;
    
    @Test
    void shouldProcessOrderWithRealDatabase() {
        // Test with real database but mocked external services
    }
}
```

**End-to-End Tests (10% of tests):**
```java
// Full application flow
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderE2ETest {
    
    @Test
    void shouldCompleteOrderFlowEndToEnd() {
        // Test complete user journey: create order → payment → fulfillment
    }
}
```

### **Test Data Management**

**Test Data Builders:**
```java
public class UserTestDataBuilder {
    private String name = "John Doe";
    private String email = "john@example.com";
    private int age = 30;
    private List<String> roles = new ArrayList<>();
    
    public static UserTestDataBuilder aUser() {
        return new UserTestDataBuilder();
    }
    
    public UserTestDataBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    public UserTestDataBuilder withEmail(String email) {
        this.email = email;
        return this;
    }
    
    public UserTestDataBuilder withAge(int age) {
        this.age = age;
        return this;
    }
    
    public UserTestDataBuilder withRoles(String... roles) {
        this.roles = Arrays.asList(roles);
        return this;
    }
    
    public User build() {
        User user = new User(name, email, age);
        roles.forEach(user::addRole);
        return user;
    }
}

// Usage in tests
@Test
void shouldCreateAdminUser() {
    User admin = aUser()
        .withName("Admin User")
        .withEmail("admin@company.com")
        .withRoles("ADMIN", "USER")
        .build();
    
    // Test implementation
}
```

---

## 🎯 **Reference Code Examples**

### **Working Examples in Repository**

| Concept | Reference File | What It Demonstrates |
|---------|----------------|---------------------|
| **Testing Frameworks** | [`TestingExamples.java`](../../src/main/java/com/coherentsolutions/session1/reference/TestingExamples.java) | Complete testing patterns |
| **Testing Demo** | [`TestingDemo.java`](../../src/main/java/com/coherentsolutions/session1/demos/TestingDemo.java) | Live testing demonstration |
| **Test Examples** | [`src/test/java/`](../../src/test/java/) | Real test implementations |

### **Practice Exercises**

| Exercise | File | Skill Level |
|----------|------|-------------|
| **Basic Testing** | [`exercises/userservice/`](../../src/main/java/com/coherentsolutions/session1/exercises/userservice/) | Beginner |
| **Advanced Mocking** | Test files in exercises | Intermediate |
| **Integration Testing** | Spring Boot test configurations | Advanced |

---

## ✅ **Mastery Verification**

### **Testing Competency Checklist**

After completing this guide, verify your skills:

#### **Framework Proficiency** ✅
- [ ] Write unit tests using JUnit 5 with proper lifecycle management
- [ ] Create mocks and stubs using Mockito with verification
- [ ] Use AssertJ for fluent, readable assertions
- [ ] Organize test classes and methods effectively

#### **Spring Testing** ✅
- [ ] Use @WebMvcTest for controller testing
- [ ] Apply @DataJpaTest for repository testing  
- [ ] Write integration tests with @SpringBootTest
- [ ] Mock Spring beans appropriately

#### **Testing Strategy** ✅
- [ ] Implement test pyramid (unit/integration/e2e)
- [ ] Design testable code with dependency injection
- [ ] Create maintainable test data
- [ ] Apply TDD principles effectively

### **Practical Challenges**

**Challenge 1: Comprehensive Test Suite**
```java
// Create complete test coverage for this service
@Service
public class OrderProcessingService {
    // Multiple dependencies, complex business logic
    // Your task: Design full test suite with unit, integration, and e2e tests
}
```

**Challenge 2: Legacy Code Testing**
```java
// Add tests to this legacy code without modifying it
public class LegacyOrderProcessor {
    // Tightly coupled, static dependencies, complex conditionals
    // Your task: Use creative testing techniques to achieve coverage
}
```

**Challenge 3: Performance Testing**
```java
// Verify performance characteristics
@Test
void shouldProcessLargeOrdersWithinSLA() {
    // Your task: Test that 1000 orders process within 5 seconds
    // Include memory usage and throughput verification
}
```

---

## 🚀 **Next Steps**

### **Continue Your Testing Journey**

Now that you understand Java testing frameworks:

1. **[Build & Project Structure](build-and-project-structure.md)** - Maven and organization
2. **[Anti-Patterns Deep Dive](anti-patterns-deep-dive.md)** - Avoid testing mistakes
3. **[Exercises Walkthrough](exercises-walkthrough.md)** - Practice implementations

### **Advanced Testing Topics**

- **Test Containers**: Integration testing with real databases
- **Spring Boot Test**: Advanced Spring testing features
- **Performance Testing**: JMeter, Gatling integration
- **Mutation Testing**: PIT testing for test quality verification

---

**You've mastered Java testing frameworks! 🧪**

> *"Good tests are not just about finding bugs - they're about documenting behavior, enabling refactoring, and giving confidence to make changes."*