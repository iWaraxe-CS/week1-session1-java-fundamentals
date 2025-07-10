package com.coherentsolutions.session1.reference;

/**
 * TESTING EXAMPLES REFERENCE: Java Testing vs .NET Testing
 * 
 * This reference class demonstrates testing frameworks and patterns in Java
 * compared to .NET testing approaches.
 * 
 * COVERAGE:
 * 1. JUnit 5 vs NUnit/MSTest/xUnit
 * 2. Mockito vs Moq
 * 3. AssertJ vs FluentAssertions
 * 4. Spring Boot Test annotations
 * 5. Testing patterns and best practices
 * 6. Integration testing approaches
 * 7. Test data builders and fixtures
 * 8. Performance and load testing
 * 
 * FOR LECTURE USE:
 * - Compare testing frameworks and philosophies
 * - Show practical testing examples
 * - Demonstrate mocking and test doubles
 * - Explain testing pyramid and strategies
 * 
 * NOTE: This is a reference/documentation class showing testing concepts.
 * For actual runnable tests, see the test directory structure.
 */
public class TestingExamples {
    
    public static void main(String[] args) {
        System.out.println("=== TESTING EXAMPLES REFERENCE ===");
        
        demonstrateTestingFrameworks();
        demonstrateJUnit5Features();
        demonstrateMockingWithMockito();
        demonstrateAssertions();
        demonstrateSpringBootTesting();
        demonstrateTestingPatterns();
        demonstrateIntegrationTesting();
        demonstrateBestPractices();
        
        System.out.println("\n=== TESTING EXAMPLES COMPLETE ===");
    }
    
    /**
     * TESTING FRAMEWORKS COMPARISON
     * 
     * Java vs .NET testing frameworks
     */
    public static void demonstrateTestingFrameworks() {
        System.out.println("\n--- Testing Frameworks ---");
        
        System.out.println("JAVA TESTING FRAMEWORKS:");
        System.out.println("JUnit 5        - Primary testing framework (like NUnit/xUnit)");
        System.out.println("TestNG         - Alternative to JUnit (annotation-rich)");
        System.out.println("Mockito        - Mocking framework (like Moq)");
        System.out.println("AssertJ        - Fluent assertions (like FluentAssertions)");
        System.out.println("Hamcrest       - Matcher library");
        System.out.println("WireMock       - HTTP service mocking");
        System.out.println("Testcontainers - Integration testing with Docker");
        
        System.out.println("\n.NET TESTING EQUIVALENT:");
        System.out.println("JUnit 5     ↔ NUnit/xUnit/MSTest");
        System.out.println("Mockito     ↔ Moq/NSubstitute");
        System.out.println("AssertJ     ↔ FluentAssertions");
        System.out.println("WireMock    ↔ WireMock.NET");
        System.out.println("Testcontainers ↔ Testcontainers for .NET");
        
        System.out.println("\nMAVEN TEST DEPENDENCIES:");
        System.out.println("<dependency>");
        System.out.println("    <groupId>org.junit.jupiter</groupId>");
        System.out.println("    <artifactId>junit-jupiter</artifactId>");
        System.out.println("    <scope>test</scope>");
        System.out.println("</dependency>");
        System.out.println("<dependency>");
        System.out.println("    <groupId>org.mockito</groupId>");
        System.out.println("    <artifactId>mockito-core</artifactId>");
        System.out.println("    <scope>test</scope>");
        System.out.println("</dependency>");
        System.out.println("<dependency>");
        System.out.println("    <groupId>org.assertj</groupId>");
        System.out.println("    <artifactId>assertj-core</artifactId>");
        System.out.println("    <scope>test</scope>");
        System.out.println("</dependency>");
        
        System.out.println("\nTEST DIRECTORY STRUCTURE:");
        System.out.println("src/");
        System.out.println("├── main/java/           # Production code");
        System.out.println("└── test/java/           # Test code");
        System.out.println("    ├── unit/            # Unit tests");
        System.out.println("    ├── integration/     # Integration tests");
        System.out.println("    └── resources/       # Test resources");
    }
    
    /**
     * JUNIT 5 FEATURES
     * 
     * Modern JUnit 5 features and annotations
     */
    public static void demonstrateJUnit5Features() {
        System.out.println("\n--- JUnit 5 Features ---");
        
        System.out.println("JUNIT 5 ANNOTATIONS:");
        System.out.println("@Test              - Test method");
        System.out.println("@BeforeEach        - Setup before each test");
        System.out.println("@AfterEach         - Cleanup after each test");
        System.out.println("@BeforeAll         - Setup before all tests");
        System.out.println("@AfterAll          - Cleanup after all tests");
        System.out.println("@DisplayName       - Custom test names");
        System.out.println("@Nested            - Nested test classes");
        System.out.println("@ParameterizedTest - Parameterized tests");
        System.out.println("@RepeatedTest      - Repeat tests");
        System.out.println("@Tag               - Test categorization");
        System.out.println("@Disabled          - Skip test");
        System.out.println("@Timeout           - Test timeout");
        
        System.out.println("\n.NET TESTING EQUIVALENT:");
        System.out.println("@Test           ↔ [Test] (NUnit) / [Fact] (xUnit)");
        System.out.println("@BeforeEach     ↔ [SetUp] (NUnit) / Constructor (xUnit)");
        System.out.println("@AfterEach      ↔ [TearDown] (NUnit) / IDisposable (xUnit)");
        System.out.println("@BeforeAll      ↔ [OneTimeSetUp] (NUnit) / IClassFixture (xUnit)");
        System.out.println("@DisplayName    ↔ [TestCase(TestName = \"..\")] (NUnit)");
        System.out.println("@ParameterizedTest ↔ [TestCase] (NUnit) / [Theory] (xUnit)");
        
        System.out.println("\nBASIC TEST STRUCTURE:");
        System.out.println("class UserServiceTest {");
        System.out.println("    ");
        System.out.println("    private UserService userService;");
        System.out.println("    private UserRepository userRepository;");
        System.out.println("    ");
        System.out.println("    @BeforeEach");
        System.out.println("    void setUp() {");
        System.out.println("        userRepository = Mockito.mock(UserRepository.class);");
        System.out.println("        userService = new UserService(userRepository);");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    @Test");
        System.out.println("    @DisplayName(\"Should create user with valid email\")");
        System.out.println("    void shouldCreateUserWithValidEmail() {");
        System.out.println("        // Given");
        System.out.println("        String email = \"test@example.com\";");
        System.out.println("        ");
        System.out.println("        // When");
        System.out.println("        User result = userService.createUser(email);");
        System.out.println("        ");
        System.out.println("        // Then");
        System.out.println("        assertThat(result.getEmail()).isEqualTo(email);");
        System.out.println("        verify(userRepository).save(any(User.class));");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nPARAMETERIZED TESTS:");
        System.out.println("@ParameterizedTest");
        System.out.println("@ValueSource(strings = {\"test@example.com\", \"user@domain.org\"})");
        System.out.println("void shouldAcceptValidEmails(String email) {");
        System.out.println("    assertTrue(EmailValidator.isValid(email));");
        System.out.println("}");
        System.out.println("");
        System.out.println("@ParameterizedTest");
        System.out.println("@CsvSource({");
        System.out.println("    \"1, John\",");
        System.out.println("    \"2, Jane\",");
        System.out.println("    \"3, Bob\"");
        System.out.println("})");
        System.out.println("void shouldCreateUserWithIdAndName(long id, String name) {");
        System.out.println("    User user = new User(id, name);");
        System.out.println("    assertThat(user.getId()).isEqualTo(id);");
        System.out.println("    assertThat(user.getName()).isEqualTo(name);");
        System.out.println("}");
        
        System.out.println("\nNESTED TESTS:");
        System.out.println("class UserServiceTest {");
        System.out.println("    ");
        System.out.println("    @Nested");
        System.out.println("    @DisplayName(\"User Creation Tests\")");
        System.out.println("    class UserCreationTests {");
        System.out.println("        ");
        System.out.println("        @Test");
        System.out.println("        void shouldCreateValidUser() { ... }");
        System.out.println("        ");
        System.out.println("        @Test");
        System.out.println("        void shouldRejectInvalidEmail() { ... }");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    @Nested");
        System.out.println("    @DisplayName(\"User Retrieval Tests\")");
        System.out.println("    class UserRetrievalTests {");
        System.out.println("        ");
        System.out.println("        @Test");
        System.out.println("        void shouldFindExistingUser() { ... }");
        System.out.println("        ");
        System.out.println("        @Test");
        System.out.println("        void shouldReturnEmptyForNonExistentUser() { ... }");
        System.out.println("    }");
        System.out.println("}");
    }
    
    /**
     * MOCKING WITH MOCKITO
     * 
     * Mockito framework for mocking dependencies
     */
    public static void demonstrateMockingWithMockito() {
        System.out.println("\n--- Mocking with Mockito ---");
        
        System.out.println("MOCKITO FEATURES:");
        System.out.println("Mock creation      - Create mock objects");
        System.out.println("Stubbing          - Define mock behavior");
        System.out.println("Verification      - Verify interactions");
        System.out.println("Argument matchers - Flexible parameter matching");
        System.out.println("Spies            - Partial mocking");
        System.out.println("Capturing        - Capture method arguments");
        
        System.out.println("\nMOCKITO VS MOQ COMPARISON:");
        System.out.println("Mockito: UserRepository mock = Mockito.mock(UserRepository.class);");
        System.out.println("Moq:     var mock = new Mock<IUserRepository>();");
        System.out.println("");
        System.out.println("Mockito: when(mock.findById(1L)).thenReturn(user);");
        System.out.println("Moq:     mock.Setup(x => x.FindById(1)).Returns(user);");
        System.out.println("");
        System.out.println("Mockito: verify(mock).save(any(User.class));");
        System.out.println("Moq:     mock.Verify(x => x.Save(It.IsAny<User>()), Times.Once);");
        
        System.out.println("\nMOCK CREATION:");
        System.out.println("// Method 1: Programmatic");
        System.out.println("UserRepository mockRepo = Mockito.mock(UserRepository.class);");
        System.out.println("");
        System.out.println("// Method 2: Annotation-based");
        System.out.println("@ExtendWith(MockitoExtension.class)");
        System.out.println("class UserServiceTest {");
        System.out.println("    @Mock");
        System.out.println("    private UserRepository userRepository;");
        System.out.println("    ");
        System.out.println("    @InjectMocks");
        System.out.println("    private UserService userService;");
        System.out.println("}");
        
        System.out.println("\nSTUBBING BEHAVIOR:");
        System.out.println("// Return values");
        System.out.println("when(userRepository.findById(1L)).thenReturn(Optional.of(user));");
        System.out.println("when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));");
        System.out.println("");
        System.out.println("// Throw exceptions");
        System.out.println("when(userRepository.findById(999L))");
        System.out.println("    .thenThrow(new UserNotFoundException(\"User not found\"));");
        System.out.println("");
        System.out.println("// Multiple calls");
        System.out.println("when(userRepository.count())");
        System.out.println("    .thenReturn(0L)");
        System.out.println("    .thenReturn(1L)");
        System.out.println("    .thenReturn(2L);");
        System.out.println("");
        System.out.println("// Conditional stubbing");
        System.out.println("when(userRepository.findById(anyLong()))");
        System.out.println("    .thenAnswer(invocation -> {");
        System.out.println("        Long id = invocation.getArgument(0);");
        System.out.println("        return id > 0 ? Optional.of(new User(id)) : Optional.empty();");
        System.out.println("    });");
        
        System.out.println("\nVERIFICATION:");
        System.out.println("// Verify method calls");
        System.out.println("verify(userRepository).save(user);");
        System.out.println("verify(userRepository, times(2)).findById(anyLong());");
        System.out.println("verify(userRepository, never()).deleteById(anyLong());");
        System.out.println("verify(userRepository, atLeastOnce()).findAll();");
        System.out.println("");
        System.out.println("// Verify with argument matchers");
        System.out.println("verify(userRepository).save(argThat(user -> ");
        System.out.println("    user.getEmail().endsWith(\"@example.com\")));");
        System.out.println("");
        System.out.println("// Verify no more interactions");
        System.out.println("verifyNoMoreInteractions(userRepository);");
        
        System.out.println("\nARGUMENT CAPTURE:");
        System.out.println("@Captor");
        System.out.println("private ArgumentCaptor<User> userCaptor;");
        System.out.println("");
        System.out.println("// In test method");
        System.out.println("userService.createUser(\"test@example.com\");");
        System.out.println("");
        System.out.println("verify(userRepository).save(userCaptor.capture());");
        System.out.println("User capturedUser = userCaptor.getValue();");
        System.out.println("assertThat(capturedUser.getEmail()).isEqualTo(\"test@example.com\");");
        
        System.out.println("\nSPIES (PARTIAL MOCKS):");
        System.out.println("UserService spy = Mockito.spy(new UserService(userRepository));");
        System.out.println("");
        System.out.println("// Call real method, but stub specific behavior");
        System.out.println("doReturn(\"mocked@example.com\").when(spy).generateEmail();");
        System.out.println("");
        System.out.println("// Verify interactions");
        System.out.println("verify(spy).generateEmail();");
    }
    
    /**
     * ASSERTIONS
     * 
     * AssertJ fluent assertions vs traditional assertions
     */
    public static void demonstrateAssertions() {
        System.out.println("\n--- Assertions ---");
        
        System.out.println("ASSERTION LIBRARIES:");
        System.out.println("JUnit Assertions   - Built-in assertions (basic)");
        System.out.println("AssertJ           - Fluent assertions (recommended)");
        System.out.println("Hamcrest          - Matcher-based assertions");
        
        System.out.println("\nJUNIT VS ASSERTJ COMPARISON:");
        System.out.println("JUnit:   assertEquals(expected, actual);");
        System.out.println("AssertJ: assertThat(actual).isEqualTo(expected);");
        System.out.println("");
        System.out.println("JUnit:   assertTrue(condition);");
        System.out.println("AssertJ: assertThat(condition).isTrue();");
        System.out.println("");
        System.out.println("JUnit:   assertNotNull(object);");
        System.out.println("AssertJ: assertThat(object).isNotNull();");
        
        System.out.println("\nASSERTJ FLUENT ASSERTIONS:");
        System.out.println("// Basic assertions");
        System.out.println("assertThat(user.getName()).isEqualTo(\"John\");");
        System.out.println("assertThat(user.getAge()).isGreaterThan(18);");
        System.out.println("assertThat(user.getEmail()).isNotBlank();");
        System.out.println("assertThat(user.isActive()).isTrue();");
        System.out.println("");
        System.out.println("// String assertions");
        System.out.println("assertThat(user.getName())");
        System.out.println("    .isNotEmpty()");
        System.out.println("    .startsWith(\"J\")");
        System.out.println("    .endsWith(\"n\")");
        System.out.println("    .containsIgnoringCase(\"oh\");");
        System.out.println("");
        System.out.println("// Collection assertions");
        System.out.println("assertThat(users)");
        System.out.println("    .hasSize(3)");
        System.out.println("    .contains(user1, user2)");
        System.out.println("    .doesNotContain(user3)");
        System.out.println("    .extracting(User::getName)");
        System.out.println("    .containsExactly(\"John\", \"Jane\", \"Bob\");");
        System.out.println("");
        System.out.println("// Exception assertions");
        System.out.println("assertThatThrownBy(() -> userService.createUser(null))");
        System.out.println("    .isInstanceOf(IllegalArgumentException.class)");
        System.out.println("    .hasMessage(\"Email cannot be null\")");
        System.out.println("    .hasNoCause();");
        
        System.out.println("\nCUSTOM ASSERTIONS:");
        System.out.println("public class UserAssert extends AbstractAssert<UserAssert, User> {");
        System.out.println("    ");
        System.out.println("    public UserAssert(User user) {");
        System.out.println("        super(user, UserAssert.class);");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    public static UserAssert assertThat(User user) {");
        System.out.println("        return new UserAssert(user);");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    public UserAssert hasValidEmail() {");
        System.out.println("        isNotNull();");
        System.out.println("        if (!actual.getEmail().contains(\"@\")) {");
        System.out.println("            failWithMessage(\"Expected valid email but was <%s>\", actual.getEmail());");
        System.out.println("        }");
        System.out.println("        return this;");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("");
        System.out.println("// Usage");
        System.out.println("UserAssert.assertThat(user).hasValidEmail();");
        
        System.out.println("\nSOFT ASSERTIONS:");
        System.out.println("@Test");
        System.out.println("void shouldValidateUserWithSoftAssertions() {");
        System.out.println("    SoftAssertions softly = new SoftAssertions();");
        System.out.println("    ");
        System.out.println("    softly.assertThat(user.getName()).isEqualTo(\"John\");");
        System.out.println("    softly.assertThat(user.getAge()).isGreaterThan(18);");
        System.out.println("    softly.assertThat(user.getEmail()).contains(\"@\");");
        System.out.println("    ");
        System.out.println("    softly.assertAll(); // All assertions executed, failures collected");
        System.out.println("}");
    }
    
    /**
     * SPRING BOOT TESTING
     * 
     * Spring Boot specific testing annotations and features
     */
    public static void demonstrateSpringBootTesting() {
        System.out.println("\n--- Spring Boot Testing ---");
        
        System.out.println("SPRING BOOT TEST ANNOTATIONS:");
        System.out.println("@SpringBootTest        - Full integration test");
        System.out.println("@WebMvcTest           - Web layer testing");
        System.out.println("@DataJpaTest          - JPA repository testing");
        System.out.println("@JsonTest             - JSON serialization testing");
        System.out.println("@MockBean             - Mock Spring beans");
        System.out.println("@SpyBean              - Spy on Spring beans");
        System.out.println("@TestConfiguration    - Test-specific configuration");
        System.out.println("@DirtiesContext       - Reset application context");
        
        System.out.println("\n.NET CORE TESTING EQUIVALENT:");
        System.out.println("@SpringBootTest  ↔ TestHost/WebApplicationFactory");
        System.out.println("@WebMvcTest      ↔ [ApiController] testing with TestServer");
        System.out.println("@MockBean        ↔ Mock<T> registered in DI container");
        System.out.println("@TestConfiguration ↔ Test-specific Startup configuration");
        
        System.out.println("\nFULL INTEGRATION TEST:");
        System.out.println("@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)");
        System.out.println("@TestPropertySource(properties = \"spring.datasource.url=jdbc:h2:mem:testdb\")");
        System.out.println("class UserControllerIntegrationTest {");
        System.out.println("    ");
        System.out.println("    @Autowired");
        System.out.println("    private TestRestTemplate restTemplate;");
        System.out.println("    ");
        System.out.println("    @Autowired");
        System.out.println("    private UserRepository userRepository;");
        System.out.println("    ");
        System.out.println("    @Test");
        System.out.println("    void shouldCreateAndRetrieveUser() {");
        System.out.println("        // Given");
        System.out.println("        CreateUserRequest request = new CreateUserRequest(\"test@example.com\");");
        System.out.println("        ");
        System.out.println("        // When");
        System.out.println("        ResponseEntity<User> response = restTemplate.postForEntity(");
        System.out.println("            \"/api/users\", request, User.class);");
        System.out.println("        ");
        System.out.println("        // Then");
        System.out.println("        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);");
        System.out.println("        assertThat(userRepository.count()).isEqualTo(1);");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nWEB LAYER TEST:");
        System.out.println("@WebMvcTest(UserController.class)");
        System.out.println("class UserControllerTest {");
        System.out.println("    ");
        System.out.println("    @Autowired");
        System.out.println("    private MockMvc mockMvc;");
        System.out.println("    ");
        System.out.println("    @MockBean");
        System.out.println("    private UserService userService;");
        System.out.println("    ");
        System.out.println("    @Test");
        System.out.println("    void shouldReturnUser() throws Exception {");
        System.out.println("        // Given");
        System.out.println("        User user = new User(1L, \"test@example.com\");");
        System.out.println("        when(userService.findById(1L)).thenReturn(user);");
        System.out.println("        ");
        System.out.println("        // When & Then");
        System.out.println("        mockMvc.perform(get(\"/api/users/1\"))");
        System.out.println("            .andExpect(status().isOk())");
        System.out.println("            .andExpect(jsonPath(\"$.id\").value(1))");
        System.out.println("            .andExpect(jsonPath(\"$.email\").value(\"test@example.com\"));");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nDATA LAYER TEST:");
        System.out.println("@DataJpaTest");
        System.out.println("class UserRepositoryTest {");
        System.out.println("    ");
        System.out.println("    @Autowired");
        System.out.println("    private TestEntityManager entityManager;");
        System.out.println("    ");
        System.out.println("    @Autowired");
        System.out.println("    private UserRepository userRepository;");
        System.out.println("    ");
        System.out.println("    @Test");
        System.out.println("    void shouldFindUserByEmail() {");
        System.out.println("        // Given");
        System.out.println("        User user = new User(\"test@example.com\");");
        System.out.println("        entityManager.persistAndFlush(user);");
        System.out.println("        ");
        System.out.println("        // When");
        System.out.println("        Optional<User> found = userRepository.findByEmail(\"test@example.com\");");
        System.out.println("        ");
        System.out.println("        // Then");
        System.out.println("        assertThat(found).isPresent();");
        System.out.println("        assertThat(found.get().getEmail()).isEqualTo(\"test@example.com\");");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nJSON SERIALIZATION TEST:");
        System.out.println("@JsonTest");
        System.out.println("class UserJsonTest {");
        System.out.println("    ");
        System.out.println("    @Autowired");
        System.out.println("    private JacksonTester<User> json;");
        System.out.println("    ");
        System.out.println("    @Test");
        System.out.println("    void shouldSerializeUser() throws Exception {");
        System.out.println("        User user = new User(1L, \"test@example.com\");");
        System.out.println("        ");
        System.out.println("        assertThat(json.write(user))");
        System.out.println("            .hasJsonPath(\"@.id\")");
        System.out.println("            .hasJsonPath(\"@.email\")");
        System.out.println("            .extractingJsonPathNumberValue(\"@.id\").isEqualTo(1);");
        System.out.println("    }");
        System.out.println("}");
    }
    
    /**
     * TESTING PATTERNS
     * 
     * Common testing patterns and strategies
     */
    public static void demonstrateTestingPatterns() {
        System.out.println("\n--- Testing Patterns ---");
        
        System.out.println("TESTING PATTERNS:");
        System.out.println("Given-When-Then    - Behavior-driven test structure");
        System.out.println("Arrange-Act-Assert - Alternative test structure");
        System.out.println("Test Data Builders - Fluent test data creation");
        System.out.println("Object Mother      - Test data factory");
        System.out.println("Test Fixtures      - Reusable test data setup");
        System.out.println("Page Object        - UI testing pattern");
        
        System.out.println("\nGIVEN-WHEN-THEN PATTERN:");
        System.out.println("@Test");
        System.out.println("@DisplayName(\"Should create user when valid email provided\")");
        System.out.println("void shouldCreateUserWhenValidEmailProvided() {");
        System.out.println("    // Given");
        System.out.println("    String validEmail = \"test@example.com\";");
        System.out.println("    when(userRepository.existsByEmail(validEmail)).thenReturn(false);");
        System.out.println("    ");
        System.out.println("    // When");
        System.out.println("    User result = userService.createUser(validEmail);");
        System.out.println("    ");
        System.out.println("    // Then");
        System.out.println("    assertThat(result).isNotNull();");
        System.out.println("    assertThat(result.getEmail()).isEqualTo(validEmail);");
        System.out.println("    verify(userRepository).save(any(User.class));");
        System.out.println("}");
        
        System.out.println("\nTEST DATA BUILDER:");
        System.out.println("public class UserTestDataBuilder {");
        System.out.println("    private String email = \"default@example.com\";");
        System.out.println("    private String name = \"Default Name\";");
        System.out.println("    private int age = 25;");
        System.out.println("    ");
        System.out.println("    public static UserTestDataBuilder aUser() {");
        System.out.println("        return new UserTestDataBuilder();");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    public UserTestDataBuilder withEmail(String email) {");
        System.out.println("        this.email = email;");
        System.out.println("        return this;");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    public UserTestDataBuilder withName(String name) {");
        System.out.println("        this.name = name;");
        System.out.println("        return this;");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    public UserTestDataBuilder withAge(int age) {");
        System.out.println("        this.age = age;");
        System.out.println("        return this;");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    public User build() {");
        System.out.println("        return new User(email, name, age);");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("");
        System.out.println("// Usage");
        System.out.println("User user = aUser()");
        System.out.println("    .withEmail(\"john@example.com\")");
        System.out.println("    .withAge(30)");
        System.out.println("    .build();");
        
        System.out.println("\nOBJECT MOTHER:");
        System.out.println("public class UserMother {");
        System.out.println("    ");
        System.out.println("    public static User validUser() {");
        System.out.println("        return new User(\"valid@example.com\", \"Valid User\", 25);");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    public static User adminUser() {");
        System.out.println("        User user = validUser();");
        System.out.println("        user.setRole(Role.ADMIN);");
        System.out.println("        return user;");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    public static User userWithInvalidEmail() {");
        System.out.println("        return new User(\"invalid-email\", \"Invalid User\", 25);");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nTEST CATEGORIES:");
        System.out.println("@Tag(\"unit\")");
        System.out.println("@Tag(\"fast\")");
        System.out.println("class UnitTest { ... }");
        System.out.println("");
        System.out.println("@Tag(\"integration\")");
        System.out.println("@Tag(\"slow\")");
        System.out.println("class IntegrationTest { ... }");
        System.out.println("");
        System.out.println("// Run specific tags:");
        System.out.println("// mvn test -Dgroups=\"unit\"");
        System.out.println("// mvn test -Dgroups=\"integration\"");
    }
    
    /**
     * INTEGRATION TESTING
     * 
     * Integration testing strategies and tools
     */
    public static void demonstrateIntegrationTesting() {
        System.out.println("\n--- Integration Testing ---");
        
        System.out.println("INTEGRATION TESTING TOOLS:");
        System.out.println("Testcontainers     - Docker containers for testing");
        System.out.println("WireMock          - HTTP service mocking");
        System.out.println("Embedded databases - H2, HSQLDB for testing");
        System.out.println("TestRestTemplate   - REST client for testing");
        System.out.println("WebTestClient     - Reactive web testing");
        
        System.out.println("\nTESTCONTAINERS EXAMPLE:");
        System.out.println("@Testcontainers");
        System.out.println("@SpringBootTest");
        System.out.println("class DatabaseIntegrationTest {");
        System.out.println("    ");
        System.out.println("    @Container");
        System.out.println("    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(\"postgres:13\")");
        System.out.println("            .withDatabaseName(\"testdb\")");
        System.out.println("            .withUsername(\"test\")");
        System.out.println("            .withPassword(\"test\");");
        System.out.println("    ");
        System.out.println("    @DynamicPropertySource");
        System.out.println("    static void configureProperties(DynamicPropertyRegistry registry) {");
        System.out.println("        registry.add(\"spring.datasource.url\", postgres::getJdbcUrl);");
        System.out.println("        registry.add(\"spring.datasource.username\", postgres::getUsername);");
        System.out.println("        registry.add(\"spring.datasource.password\", postgres::getPassword);");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    @Test");
        System.out.println("    void shouldPersistUser() {");
        System.out.println("        // Test with real PostgreSQL database");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nWIREMOCK EXAMPLE:");
        System.out.println("@ExtendWith(WireMockExtension.class)");
        System.out.println("class ExternalServiceIntegrationTest {");
        System.out.println("    ");
        System.out.println("    @RegisterExtension");
        System.out.println("    static WireMockExtension wireMock = WireMockExtension.newInstance()");
        System.out.println("            .options(WireMockConfiguration.options().port(8089))");
        System.out.println("            .build();");
        System.out.println("    ");
        System.out.println("    @Test");
        System.out.println("    void shouldCallExternalService() {");
        System.out.println("        // Given");
        System.out.println("        wireMock.stubFor(get(urlEqualTo(\"/api/external\"))");
        System.out.println("                .willReturn(aResponse()");
        System.out.println("                        .withStatus(200)");
        System.out.println("                        .withHeader(\"Content-Type\", \"application/json\")");
        System.out.println("                        .withBody(\"{\\\"status\\\":\\\"success\\\"}\")));");
        System.out.println("        ");
        System.out.println("        // When");
        System.out.println("        String result = externalService.call();");
        System.out.println("        ");
        System.out.println("        // Then");
        System.out.println("        assertThat(result).isEqualTo(\"success\");");
        System.out.println("        wireMock.verify(getRequestedFor(urlEqualTo(\"/api/external\")));");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nTEST SLICES:");
        System.out.println("@WebMvcTest       - Load only web layer");
        System.out.println("@DataJpaTest      - Load only JPA configuration");
        System.out.println("@JsonTest         - Load only JSON configuration");
        System.out.println("@RestClientTest   - Load only REST client configuration");
        System.out.println("@MockitoTest      - Load only Mockito configuration");
        
        System.out.println("\nTEST PROFILES:");
        System.out.println("# application-test.properties");
        System.out.println("spring.datasource.url=jdbc:h2:mem:testdb");
        System.out.println("spring.jpa.hibernate.ddl-auto=create-drop");
        System.out.println("logging.level.org.springframework.web=DEBUG");
        System.out.println("");
        System.out.println("# Usage");
        System.out.println("@ActiveProfiles(\"test\")");
        System.out.println("@SpringBootTest");
        System.out.println("class IntegrationTest { ... }");
    }
    
    /**
     * BEST PRACTICES
     * 
     * Testing best practices and guidelines
     */
    public static void demonstrateBestPractices() {
        System.out.println("\n--- Best Practices ---");
        
        System.out.println("TESTING BEST PRACTICES:");
        
        System.out.println("\n1. TEST ORGANIZATION:");
        System.out.println("✅ Follow Given-When-Then structure");
        System.out.println("✅ Use descriptive test names");
        System.out.println("✅ One assertion per test (when possible)");
        System.out.println("✅ Group related tests with @Nested");
        System.out.println("✅ Use @DisplayName for clarity");
        
        System.out.println("\n2. TEST DATA MANAGEMENT:");
        System.out.println("✅ Use test data builders");
        System.out.println("✅ Create focused test data");
        System.out.println("✅ Avoid shared mutable state");
        System.out.println("✅ Clean up after tests");
        System.out.println("❌ Don't use production data in tests");
        
        System.out.println("\n3. MOCKING STRATEGY:");
        System.out.println("✅ Mock external dependencies");
        System.out.println("✅ Verify important interactions");
        System.out.println("✅ Use argument matchers wisely");
        System.out.println("❌ Don't mock value objects");
        System.out.println("❌ Don't over-verify interactions");
        
        System.out.println("\n4. ASSERTION STRATEGY:");
        System.out.println("✅ Use AssertJ for fluent assertions");
        System.out.println("✅ Create custom assertions for domain objects");
        System.out.println("✅ Use soft assertions for multiple checks");
        System.out.println("✅ Assert on business behavior, not implementation");
        
        System.out.println("\n5. TEST PERFORMANCE:");
        System.out.println("✅ Keep unit tests fast (<100ms)");
        System.out.println("✅ Use test slices for focused testing");
        System.out.println("✅ Parallelize test execution");
        System.out.println("✅ Use @DirtiesContext sparingly");
        
        System.out.println("\nTEST PYRAMID:");
        System.out.println("        /\\\\");
        System.out.println("       /  \\\\    UI Tests (Few, Slow, Expensive)");
        System.out.println("      /____\\\\");
        System.out.println("     /      \\\\");
        System.out.println("    /        \\\\  Integration Tests (Some, Medium)");
        System.out.println("   /__________\\\\");
        System.out.println("  /            \\\\");
        System.out.println(" /              \\\\ Unit Tests (Many, Fast, Cheap)");
        System.out.println("/________________\\\\");
        
        System.out.println("\nCOMMON TESTING MISTAKES:");
        System.out.println("❌ Testing implementation details");
        System.out.println("❌ Having too many integration tests");
        System.out.println("❌ Not testing edge cases");
        System.out.println("❌ Using @SpringBootTest for everything");
        System.out.println("❌ Ignoring test maintainability");
        System.out.println("❌ Not testing error scenarios");
        
        System.out.println("\nTEST NAMING CONVENTIONS:");
        System.out.println("// Method name approach");
        System.out.println("shouldReturnUserWhenValidIdProvided()");
        System.out.println("shouldThrowExceptionWhenInvalidEmailProvided()");
        System.out.println("");
        System.out.println("// Display name approach");
        System.out.println("@DisplayName(\"Should return user when valid ID is provided\")");
        System.out.println("@DisplayName(\"Should throw exception when invalid email is provided\")");
        
        System.out.println("\nMIGRATION FROM .NET TESTING:");
        System.out.println("1. Replace NUnit/xUnit with JUnit 5");
        System.out.println("2. Replace Moq with Mockito");
        System.out.println("3. Replace FluentAssertions with AssertJ");
        System.out.println("4. Use Spring Boot Test slices");
        System.out.println("5. Replace TestHost with @SpringBootTest");
        System.out.println("6. Use Testcontainers for database testing");
        System.out.println("7. Replace [Theory] with @ParameterizedTest");
        
        System.out.println("\nTEST CONFIGURATION:");
        System.out.println("# Maven Surefire Plugin");
        System.out.println("<plugin>");
        System.out.println("    <groupId>org.apache.maven.plugins</groupId>");
        System.out.println("    <artifactId>maven-surefire-plugin</artifactId>");
        System.out.println("    <configuration>");
        System.out.println("        <parallel>methods</parallel>");
        System.out.println("        <threadCount>4</threadCount>");
        System.out.println("        <groups>unit</groups>");
        System.out.println("    </configuration>");
        System.out.println("</plugin>");
    }
}