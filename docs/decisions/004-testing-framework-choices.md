# ADR-004: Testing Framework Stack Selection

## Status
**ACCEPTED** - December 2024

## Context

Java has multiple testing framework options, and .NET developers are familiar with comprehensive testing stacks (xUnit/NUnit + Moq + FluentAssertions). We needed to select a testing stack that:

- **Maps clearly** to .NET testing concepts
- **Provides comprehensive coverage** (unit, integration, assertions, mocking)
- **Follows industry standards** for enterprise Java development
- **Offers gentle learning curve** for .NET developers

## Decision

We selected **JUnit 5 + Mockito + AssertJ** as our comprehensive testing stack, bundled through Spring Boot's `spring-boot-starter-test`.

## Selected Testing Stack

### 1. Core Testing Framework
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <!-- Includes: JUnit 5, Mockito, AssertJ, Hamcrest, JSONassert, Spring Test -->
</dependency>
```

### 2. Additional Testing Libraries
```xml
<!-- For integration testing with real databases -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>

<!-- Database testing -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

## Framework Mapping to .NET

### Testing Framework Comparison
| Java | .NET | Purpose | Learning Curve |
|------|------|---------|----------------|
| **JUnit 5** | xUnit/NUnit/MSTest | Test framework | ⭐ Easy |
| **Mockito** | Moq/NSubstitute | Mocking | ⭐⭐ Moderate |
| **AssertJ** | FluentAssertions | Fluent assertions | ⭐ Easy |
| **Spring Test** | ASP.NET Core Test Host | Integration testing | ⭐⭐ Moderate |
| **Testcontainers** | Docker.DotNet | Infrastructure testing | ⭐⭐⭐ Advanced |

## Rationale

### 1. JUnit 5 for Test Structure

**Why JUnit 5 over alternatives:**

```java
// JUnit 5 - Modern, annotation-driven (familiar to .NET developers)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    @DisplayName("Should create user with valid data")
    void shouldCreateUserWithValidData() {
        // Given, When, Then pattern (familiar from .NET)
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "invalid-email"})
    void shouldRejectInvalidEmails(String email) {
        // Data-driven testing like [Theory] in xUnit
    }
}
```

**Compare to .NET xUnit:**
```csharp
public class UserServiceTests
{
    private readonly Mock<IUserRepository> _userRepositoryMock;
    private readonly UserService _userService;
    
    public UserServiceTests()
    {
        _userRepositoryMock = new Mock<IUserRepository>();
        _userService = new UserService(_userRepositoryMock.Object);
    }
    
    [Fact]
    public void ShouldCreateUserWithValidData()
    {
        // Arrange, Act, Assert pattern
    }
    
    [Theory]
    [InlineData("")]
    [InlineData(" ")]
    [InlineData("invalid-email")]
    public void ShouldRejectInvalidEmails(string email)
    {
        // Data-driven testing
    }
}
```

### 2. Mockito for Mocking

**Why Mockito over alternatives:**

```java
// Mockito - BDD style similar to Moq
@Test
void shouldProcessOrderSuccessfully() {
    // Given (Arrange)
    given(paymentService.processPayment(any())).willReturn(PaymentResult.success());
    given(inventoryService.isAvailable(any(), anyInt())).willReturn(true);
    
    // When (Act)
    Order result = orderService.processOrder(createOrderRequest());
    
    // Then (Assert)
    assertThat(result.getStatus()).isEqualTo(OrderStatus.CONFIRMED);
    then(paymentService).should().processPayment(any());
    then(inventoryService).should().isAvailable(any(), anyInt());
}
```

**Compare to .NET Moq:**
```csharp
[Fact]
public void ShouldProcessOrderSuccessfully()
{
    // Arrange
    _paymentServiceMock.Setup(x => x.ProcessPayment(It.IsAny<PaymentRequest>()))
                      .Returns(PaymentResult.Success());
    _inventoryServiceMock.Setup(x => x.IsAvailable(It.IsAny<Product>(), It.IsAny<int>()))
                        .Returns(true);
    
    // Act
    var result = _orderService.ProcessOrder(CreateOrderRequest());
    
    // Assert
    result.Status.Should().Be(OrderStatus.Confirmed);
    _paymentServiceMock.Verify(x => x.ProcessPayment(It.IsAny<PaymentRequest>()), Times.Once);
    _inventoryServiceMock.Verify(x => x.IsAvailable(It.IsAny<Product>(), It.IsAny<int>()), Times.Once);
}
```

### 3. AssertJ for Fluent Assertions

**Why AssertJ over JUnit assertions:**

```java
// AssertJ - Fluent API similar to FluentAssertions
@Test
void shouldValidateUserData() {
    User user = createTestUser();
    List<String> errors = validateUser(user);
    
    // Fluent assertions
    assertThat(user)
        .isNotNull()
        .satisfies(u -> {
            assertThat(u.getName()).isNotBlank();
            assertThat(u.getEmail()).contains("@");
            assertThat(u.getAge()).isBetween(18, 100);
        });
    
    assertThat(errors)
        .isEmpty()
        .hasSize(0);
    
    assertThat(user.getRoles())
        .hasSize(2)
        .extracting(Role::getName)
        .containsExactly("USER", "CUSTOMER");
}
```

**Compare to .NET FluentAssertions:**
```csharp
[Fact]
public void ShouldValidateUserData()
{
    var user = CreateTestUser();
    var errors = ValidateUser(user);
    
    // Fluent assertions
    user.Should().NotBeNull()
        .And.Subject.Name.Should().NotBeNullOrWhiteSpace();
    user.Email.Should().Contain("@");
    user.Age.Should().BeInRange(18, 100);
    
    errors.Should().BeEmpty()
           .And.HaveCount(0);
    
    user.Roles.Should().HaveCount(2)
             .And.Select(r => r.Name)
             .Should().Equal("USER", "CUSTOMER");
}
```

## Implementation Strategy

### 1. Test Organization
```java
// Standard Java test structure (similar to .NET test projects)
src/test/java/
├── unit/                           // Unit tests
│   ├── service/
│   │   └── UserServiceTest.java
│   └── controller/
│       └── UserControllerTest.java
├── integration/                    // Integration tests
│   ├── repository/
│   │   └── UserRepositoryIT.java
│   └── web/
│       └── UserControllerIT.java
└── testutil/                      // Test utilities
    ├── TestDataBuilder.java
    └── TestConfiguration.java
```

### 2. Test Categories and Annotations
```java
// Unit tests - fast, isolated
@ExtendWith(MockitoExtension.class)
@Tag("unit")
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {
        // Fast test with mocks
    }
}

// Integration tests - slower, real components
@SpringBootTest
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
@Tag("integration")
class UserServiceIT {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    @Transactional
    @Rollback
    void shouldPersistUserToDatabase() {
        // Test with real database
    }
}

// Web layer tests - focused on controllers
@WebMvcTest(UserController.class)
@Tag("web")
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Test
    void shouldReturnUserList() throws Exception {
        mockMvc.perform(get("/api/users"))
               .andExpected(status().isOk())
               .andExpected(jsonPath("$", hasSize(2)));
    }
}
```

### 3. Test Data Management
```java
// Test data builders (similar to Object Mother pattern in .NET)
public class UserTestDataBuilder {
    private String name = "John Doe";
    private String email = "john@example.com";
    private Integer age = 30;
    
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
    
    public User build() {
        return User.builder()
                .name(name)
                .email(email)
                .age(age)
                .build();
    }
}

// Usage in tests
@Test
void shouldValidateUserEmail() {
    User user = aUser()
        .withEmail("invalid-email")
        .build();
    
    assertThatThrownBy(() -> userService.createUser(user))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("Invalid email format");
}
```

## Alternatives Considered

### TestNG vs JUnit 5
**TestNG Pros:**
- More powerful test configuration
- Better parallel execution support
- Flexible test dependencies

**TestNG Cons:**
- Less familiar to .NET developers
- Smaller ecosystem
- More complex for beginners

**Decision:** Rejected - JUnit 5 is industry standard and more beginner-friendly

### PowerMock vs Mockito
**PowerMock Pros:**
- Can mock static methods, final classes
- More powerful mocking capabilities

**PowerMock Cons:**
- Complex setup and configuration
- Performance overhead
- Indicates design issues (need to mock statics/finals)

**Decision:** Rejected - Encourages better design practices with standard Mockito

### Hamcrest vs AssertJ
**Hamcrest Pros:**
- Included with JUnit by default
- Matcher-based assertions

**Hamcrest Cons:**
- Less fluent API than AssertJ
- Smaller feature set
- Less familiar to .NET developers

**Decision:** AssertJ chosen for better developer experience

## Testing Patterns Established

### 1. Naming Conventions
```java
// Test method naming: should[ExpectedBehavior]When[StateUnderTest]
@Test
void shouldReturnUserWhenValidIdProvided() { }

@Test
void shouldThrowExceptionWhenUserNotFound() { }

@Test
void shouldCreateUserWhenValidDataProvided() { }
```

### 2. Test Structure (Given-When-Then)
```java
@Test
void shouldCalculateOrderTotalCorrectly() {
    // Given (Arrange)
    Order order = createOrderWithItems(
        item("Product A", 100.00, 2),
        item("Product B", 50.00, 1)
    );
    given(taxService.calculateTax(order)).willReturn(25.00);
    
    // When (Act)
    BigDecimal total = orderService.calculateTotal(order);
    
    // Then (Assert)
    assertThat(total).isEqualTo(new BigDecimal("275.00"));
    then(taxService).should().calculateTax(order);
}
```

### 3. Exception Testing
```java
@Test
void shouldThrowValidationExceptionForInvalidEmail() {
    // Given
    CreateUserRequest request = CreateUserRequest.builder()
        .name("John Doe")
        .email("invalid-email")
        .build();
    
    // When & Then
    assertThatThrownBy(() -> userService.createUser(request))
        .isInstanceOf(ValidationException.class)
        .hasMessage("Invalid email format")
        .satisfies(ex -> {
            ValidationException validationEx = (ValidationException) ex;
            assertThat(validationEx.getErrors()).hasSize(1);
            assertThat(validationEx.getErrors().get(0).getField()).isEqualTo("email");
        });
}
```

## Benefits Realized

### 1. Developer Experience
- **Familiar Patterns**: Testing patterns map directly to .NET equivalents
- **IDE Integration**: Excellent support in IntelliJ and VS Code
- **Fast Feedback**: Quick test execution with clear failure messages

### 2. Code Quality
- **High Coverage**: Comprehensive testing across all layers
- **Maintainable Tests**: Clean, readable test code
- **Reliable CI/CD**: Consistent test execution in build pipelines

### 3. Learning Curve
- **Gentle Transition**: .NET developers adapt quickly
- **Progressive Complexity**: Start simple, add advanced features gradually
- **Industry Standard**: Skills directly transferable to workplace

## Success Metrics

### Coverage and Quality
- **Unit Test Coverage**: >80% line coverage
- **Integration Test Coverage**: >70% of critical business paths
- **Test Reliability**: <1% flaky test rate

### Developer Adoption
- **Time to First Test**: Students write working test within 15 minutes
- **Test Writing Speed**: Comparable to .NET testing productivity
- **Confidence**: Students comfortable with Java testing patterns

## Future Considerations

### Advanced Testing Features
1. **Testcontainers**: Real database testing in later curriculum
2. **WireMock**: External service testing
3. **Spring Cloud Contract**: Contract testing for microservices
4. **Performance Testing**: JMeter integration

### Alternative Approaches
- **BDD Testing**: Cucumber for behavior-driven development
- **Property-Based Testing**: jqwik for generative testing
- **Mutation Testing**: PIT for test quality verification

## References

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [Spring Boot Testing Guide](https://spring.io/guides/gs/testing-web/)
- [Testing Best Practices for Java](https://phauer.com/2019/modern-best-practices-testing-java/)

---

**Last Updated**: December 2024  
**Next Review**: March 2025  
**Status**: ✅ Active