package com.coherentsolutions.session1.reference;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * JSON EXAMPLES REFERENCE: Java JSON Processing vs .NET JSON
 * 
 * This reference class demonstrates JSON processing in Java using Jackson,
 * with comparisons to .NET JSON handling (System.Text.Json, Newtonsoft.Json).
 * 
 * COVERAGE:
 * 1. Jackson ObjectMapper vs JsonSerializer
 * 2. JSON annotations and customization
 * 3. Serialization and deserialization patterns
 * 4. Custom serializers and deserializers
 * 5. Error handling and validation
 * 6. Performance considerations
 * 7. Spring Boot JSON integration
 * 8. Best practices and common patterns
 * 
 * FOR LECTURE USE:
 * - Compare Jackson with .NET JSON libraries
 * - Show practical JSON processing examples
 * - Demonstrate common patterns and pitfalls
 * - Explain configuration and customization options
 */
public class JsonExamples {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        // Configure ObjectMapper with common settings
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    public static void main(String[] args) {
        System.out.println("=== JSON EXAMPLES REFERENCE ===");
        
        demonstrateBasicSerialization();
        demonstrateJsonAnnotations();
        demonstrateCustomSerialization();
        demonstrateJsonProcessing();
        demonstrateErrorHandling();
        demonstrateSpringIntegration();
        demonstratePerformanceConsiderations();
        demonstrateBestPractices();
        
        System.out.println("\n=== JSON EXAMPLES COMPLETE ===");
    }
    
    /**
     * BASIC SERIALIZATION
     * 
     * Basic JSON serialization and deserialization
     */
    public static void demonstrateBasicSerialization() {
        System.out.println("\n--- Basic Serialization ---");
        
        System.out.println("JAVA VS .NET JSON LIBRARIES:");
        System.out.println("Java: Jackson ObjectMapper (default in Spring Boot)");
        System.out.println(".NET: System.Text.Json (default in .NET Core 3+)");
        System.out.println(".NET: Newtonsoft.Json (popular third-party)");
        
        System.out.println("\nBASIC USAGE COMPARISON:");
        System.out.println("// Java Jackson");
        System.out.println("ObjectMapper mapper = new ObjectMapper();");
        System.out.println("String json = mapper.writeValueAsString(object);");
        System.out.println("MyClass obj = mapper.readValue(json, MyClass.class);");
        System.out.println("");
        System.out.println("// .NET System.Text.Json");
        System.out.println("string json = JsonSerializer.Serialize(obj);");
        System.out.println("MyClass obj = JsonSerializer.Deserialize<MyClass>(json);");
        System.out.println("");
        System.out.println("// .NET Newtonsoft.Json");
        System.out.println("string json = JsonConvert.SerializeObject(obj);");
        System.out.println("MyClass obj = JsonConvert.DeserializeObject<MyClass>(json);");
        
        try {
            // Create test object
            Person person = new Person(1L, "John Doe", "john@example.com", 30, LocalDateTime.now());
            
            // Serialize to JSON
            String json = objectMapper.writeValueAsString(person);
            System.out.println("\nSerialized JSON:");
            System.out.println(json);
            
            // Deserialize from JSON
            Person deserializedPerson = objectMapper.readValue(json, Person.class);
            System.out.println("\nDeserialized object:");
            System.out.println("Name: " + deserializedPerson.getName());
            System.out.println("Email: " + deserializedPerson.getEmail());
            System.out.println("Age: " + deserializedPerson.getAge());
            
            // Pretty print JSON
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);
            System.out.println("\nPretty printed JSON:");
            System.out.println(prettyJson);
            
        } catch (JsonProcessingException e) {
            System.err.println("JSON processing error: " + e.getMessage());
        }
        
        System.out.println("\nCOMMON CONFIGURATION:");
        System.out.println("// Java Jackson");
        System.out.println("objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);");
        System.out.println("objectMapper.registerModule(new JavaTimeModule());");
        System.out.println("");
        System.out.println("// .NET System.Text.Json");
        System.out.println("var options = new JsonSerializerOptions {");
        System.out.println("    PropertyNamingPolicy = JsonNamingPolicy.CamelCase,");
        System.out.println("    WriteIndented = true");
        System.out.println("};");
    }
    
    /**
     * JSON ANNOTATIONS
     * 
     * Jackson annotations for customizing JSON processing
     */
    public static void demonstrateJsonAnnotations() {
        System.out.println("\n--- JSON Annotations ---");
        
        System.out.println("JACKSON ANNOTATIONS:");
        System.out.println("@JsonProperty       - Customize property name");
        System.out.println("@JsonIgnore         - Ignore property");
        System.out.println("@JsonInclude        - Include/exclude based on value");
        System.out.println("@JsonFormat         - Format dates, numbers");
        System.out.println("@JsonCreator        - Custom constructor/factory");
        System.out.println("@JsonValue          - Use method value for serialization");
        System.out.println("@JsonAlias          - Multiple names for property");
        System.out.println("@JsonTypeInfo       - Polymorphic type handling");
        
        System.out.println("\n.NET JSON ATTRIBUTE COMPARISON:");
        System.out.println("@JsonProperty       ↔ [JsonPropertyName]");
        System.out.println("@JsonIgnore         ↔ [JsonIgnore]");
        System.out.println("@JsonInclude        ↔ [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]");
        System.out.println("@JsonFormat         ↔ [JsonConverter(typeof(DateTimeConverter))]");
        System.out.println("@JsonCreator        ↔ [JsonConstructor]");
        
        try {
            // Test annotated class
            AnnotatedUser user = new AnnotatedUser(
                1L, 
                "John Doe", 
                "john@example.com", 
                "secret123", 
                LocalDateTime.now(),
                null
            );
            
            String json = objectMapper.writeValueAsString(user);
            System.out.println("\nAnnotated user JSON:");
            System.out.println(json);
            
            // Test deserialization with different property names
            String inputJson = \"{\\\"user_id\\\":2,\\\"full_name\\\":\\\"Jane Smith\\\",\\\"email_address\\\":\\\"jane@example.com\\\",\\\"created_date\\\":\\\"2023-12-01T10:30:00\\\"}\";
            AnnotatedUser deserializedUser = objectMapper.readValue(inputJson, AnnotatedUser.class);
            System.out.println("\nDeserialized from different property names:");
            System.out.println("ID: " + deserializedUser.getId());
            System.out.println("Name: " + deserializedUser.getName());
            System.out.println("Email: " + deserializedUser.getEmail());
            
        } catch (JsonProcessingException e) {
            System.err.println("JSON processing error: " + e.getMessage());
        }
        
        System.out.println("\nCOMMON ANNOTATION PATTERNS:");
        System.out.println("// Custom property names");
        System.out.println("@JsonProperty(\\\"user_id\\\")");
        System.out.println("private Long id;");
        System.out.println("");
        System.out.println("// Ignore sensitive data");
        System.out.println("@JsonIgnore");
        System.out.println("private String password;");
        System.out.println("");
        System.out.println("// Include only non-null values");
        System.out.println("@JsonInclude(JsonInclude.Include.NON_NULL)");
        System.out.println("private String optionalField;");
        System.out.println("");
        System.out.println("// Format dates");
        System.out.println("@JsonFormat(pattern = \\\"yyyy-MM-dd HH:mm:ss\\\")");
        System.out.println("private LocalDateTime createdAt;");
    }
    
    /**
     * CUSTOM SERIALIZATION
     * 
     * Custom serializers and deserializers
     */
    public static void demonstrateCustomSerialization() {
        System.out.println("\n--- Custom Serialization ---");
        
        System.out.println("CUSTOM SERIALIZATION APPROACHES:");
        System.out.println("1. Custom Serializer classes");
        System.out.println("2. @JsonSerialize/@JsonDeserialize annotations");
        System.out.println("3. Mixins for third-party classes");
        System.out.println("4. Custom PropertyNamingStrategy");
        System.out.println("5. Module registration");
        
        try {
            // Test custom serialization
            CustomSerializedData data = new CustomSerializedData(
                "SENSITIVE_DATA", 
                123.456, 
                Arrays.asList(\"item1\", \"item2\", \"item3\")
            );
            
            // Create ObjectMapper with custom module
            ObjectMapper customMapper = new ObjectMapper();
            customMapper.registerModule(new JavaTimeModule());
            
            String json = customMapper.writeValueAsString(data);
            System.out.println("\nCustom serialized JSON:");
            System.out.println(json);
            
            // Test polymorphic serialization
            Animal dog = new Dog("Buddy", "Golden Retriever");
            Animal cat = new Cat("Whiskers", true);
            
            List<Animal> animals = Arrays.asList(dog, cat);
            String animalsJson = objectMapper.writeValueAsString(animals);
            System.out.println("\nPolymorphic JSON:");
            System.out.println(animalsJson);
            
            // Deserialize polymorphic types
            List<Animal> deserializedAnimals = objectMapper.readValue(
                animalsJson, 
                objectMapper.getTypeFactory().constructCollectionType(List.class, Animal.class)
            );
            
            System.out.println("\nDeserialized animals:");
            deserializedAnimals.forEach(animal -> 
                System.out.println(animal.getClass().getSimpleName() + ": " + animal.getName())
            );
            
        } catch (JsonProcessingException e) {
            System.err.println("JSON processing error: " + e.getMessage());
        }
        
        System.out.println("\nCUSTOM SERIALIZER EXAMPLE:");
        System.out.println("public class MoneySerializer extends JsonSerializer<Money> {");
        System.out.println("    @Override");
        System.out.println("    public void serialize(Money value, JsonGenerator gen, SerializerProvider serializers)");
        System.out.println("            throws IOException {");
        System.out.println("        gen.writeStartObject();");
        System.out.println("        gen.writeNumberField(\\\"amount\\\", value.getAmount());");
        System.out.println("        gen.writeStringField(\\\"currency\\\", value.getCurrency());");
        System.out.println("        gen.writeEndObject();");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nCUSTOM DESERIALIZER EXAMPLE:");
        System.out.println("public class MoneyDeserializer extends JsonDeserializer<Money> {");
        System.out.println("    @Override");
        System.out.println("    public Money deserialize(JsonParser p, DeserializationContext ctxt)");
        System.out.println("            throws IOException {");
        System.out.println("        JsonNode node = p.getCodec().readTree(p);");
        System.out.println("        double amount = node.get(\\\"amount\\\").asDouble();");
        System.out.println("        String currency = node.get(\\\"currency\\\").asText();");
        System.out.println("        return new Money(amount, currency);");
        System.out.println("    }");
        System.out.println("}");
    }
    
    /**
     * JSON PROCESSING
     * 
     * Working with JSON trees and streaming
     */
    public static void demonstrateJsonProcessing() {
        System.out.println("\n--- JSON Processing ---");
        
        System.out.println("JSON PROCESSING APPROACHES:");
        System.out.println("1. Data binding (Object mapping)");
        System.out.println("2. Tree model (JsonNode)");
        System.out.println("3. Streaming API (JsonParser/JsonGenerator)");
        
        try {
            // Tree model processing
            String jsonString = \"{\\\"name\\\":\\\"John\\\",\\\"age\\\":30,\\\"hobbies\\\":[\\\"reading\\\",\\\"coding\\\"],\\\"address\\\":{\\\"city\\\":\\\"New York\\\",\\\"zip\\\":\\\"10001\\\"}}\";
            
            JsonNode rootNode = objectMapper.readTree(jsonString);
            System.out.println("\nTree model processing:");
            System.out.println("Name: " + rootNode.get("name").asText());
            System.out.println("Age: " + rootNode.get("age").asInt());
            
            // Process array
            JsonNode hobbiesNode = rootNode.get("hobbies");
            if (hobbiesNode.isArray()) {
                System.out.println("Hobbies:");
                for (JsonNode hobby : hobbiesNode) {
                    System.out.println("  - " + hobby.asText());
                }
            }
            
            // Process nested object
            JsonNode addressNode = rootNode.get("address");
            if (addressNode != null) {
                System.out.println("Address:");
                System.out.println("  City: " + addressNode.get("city").asText());
                System.out.println("  ZIP: " + addressNode.get("zip").asText());
            }
            
            // Modify JSON tree
            ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).put("age", 31);
            ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).put("updated", true);
            
            String modifiedJson = objectMapper.writeValueAsString(rootNode);
            System.out.println("\nModified JSON:");
            System.out.println(modifiedJson);
            
            // Convert JsonNode to Map
            Map<String, Object> dataMap = objectMapper.convertValue(rootNode, Map.class);
            System.out.println("\nConverted to Map:");
            dataMap.forEach((key, value) -> System.out.println(key + ": " + value));
            
        } catch (JsonProcessingException e) {
            System.err.println("JSON processing error: " + e.getMessage());
        }
        
        System.out.println("\nJSON PATH-LIKE ACCESS:");
        System.out.println("// Java JsonNode");
        System.out.println("JsonNode nameNode = rootNode.get(\\\"name\\\");");
        System.out.println("JsonNode cityNode = rootNode.get(\\\"address\\\").get(\\\"city\\\");");
        System.out.println("JsonNode firstHobby = rootNode.get(\\\"hobbies\\\").get(0);");
        System.out.println("");
        System.out.println("// .NET JsonElement");
        System.out.println("JsonElement nameElement = document.RootElement.GetProperty(\\\"name\\\");");
        System.out.println("JsonElement cityElement = document.RootElement.GetProperty(\\\"address\\\").GetProperty(\\\"city\\\");");
        System.out.println("JsonElement firstHobby = document.RootElement.GetProperty(\\\"hobbies\\\")[0];");
        
        System.out.println("\nSTREAMING API BENEFITS:");
        System.out.println("✅ Memory efficient for large JSON");
        System.out.println("✅ Fast processing");
        System.out.println("✅ Suitable for partial processing");
        System.out.println("❌ More complex to use");
        System.out.println("❌ No random access");
    }
    
    /**
     * ERROR HANDLING
     * 
     * JSON processing error handling and validation
     */
    public static void demonstrateErrorHandling() {
        System.out.println("\n--- Error Handling ---");
        
        System.out.println("COMMON JSON ERRORS:");
        System.out.println("1. Malformed JSON syntax");
        System.out.println("2. Unknown properties");
        System.out.println("3. Type mismatches");
        System.out.println("4. Missing required properties");
        System.out.println("5. Circular references");
        
        // Test various error scenarios
        System.out.println("\nERROR HANDLING EXAMPLES:");
        
        // Malformed JSON
        try {
            String malformedJson = \"{\\\"name\\\": \\\"John\\\", \\\"age\\\": }\"; // Missing value
            objectMapper.readValue(malformedJson, Person.class);
        } catch (JsonProcessingException e) {
            System.out.println("Malformed JSON error: " + e.getMessage().substring(0, Math.min(e.getMessage().length(), 80)) + "...");
        }
        
        // Unknown properties
        try {
            ObjectMapper strictMapper = new ObjectMapper();
            strictMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            
            String jsonWithUnknownField = \"{\\\"id\\\":1,\\\"name\\\":\\\"John\\\",\\\"unknownField\\\":\\\"value\\\"}\";
            strictMapper.readValue(jsonWithUnknownField, BasicPerson.class);
        } catch (JsonProcessingException e) {
            System.out.println("Unknown property error: " + e.getMessage().substring(0, Math.min(e.getMessage().length(), 80)) + "...");
        }
        
        // Type mismatch
        try {
            String typeErrorJson = \"{\\\"id\\\":\\\"not-a-number\\\",\\\"name\\\":\\\"John\\\"}\";
            objectMapper.readValue(typeErrorJson, BasicPerson.class);
        } catch (JsonProcessingException e) {
            System.out.println("Type mismatch error: " + e.getMessage().substring(0, Math.min(e.getMessage().length(), 80)) + "...");
        }
        
        System.out.println("\nERROR HANDLING STRATEGIES:");
        System.out.println("// Lenient configuration");
        System.out.println("objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);");
        System.out.println("objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);");
        System.out.println("objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);");
        System.out.println("");
        System.out.println("// Custom error handling");
        System.out.println("try {");
        System.out.println("    MyObject obj = objectMapper.readValue(json, MyObject.class);");
        System.out.println("    return obj;");
        System.out.println("} catch (JsonParseException e) {");
        System.out.println("    log.error(\\\"Invalid JSON syntax: {}\\\", e.getMessage());");
        System.out.println("    throw new InvalidJsonException(\\\"Malformed JSON\\\", e);");
        System.out.println("} catch (JsonMappingException e) {");
        System.out.println("    log.error(\\\"JSON mapping error: {}\\\", e.getMessage());");
        System.out.println("    throw new JsonMappingException(\\\"Cannot map JSON to object\\\", e);");
        System.out.println("}");
        
        System.out.println("\nVALIDATION WITH BEAN VALIDATION:");
        System.out.println("@Data");
        System.out.println("public class ValidatedUser {");
        System.out.println("    @NotNull @Size(min = 1, max = 100)");
        System.out.println("    private String name;");
        System.out.println("    ");
        System.out.println("    @Email");
        System.out.println("    private String email;");
        System.out.println("    ");
        System.out.println("    @Min(0) @Max(150)");
        System.out.println("    private Integer age;");
        System.out.println("}");
    }
    
    /**
     * SPRING INTEGRATION
     * 
     * JSON processing in Spring Boot applications
     */
    public static void demonstrateSpringIntegration() {
        System.out.println("\n--- Spring Integration ---");
        
        System.out.println("SPRING BOOT JSON CONFIGURATION:");
        System.out.println("# application.properties");
        System.out.println("spring.jackson.serialization.write-dates-as-timestamps=false");
        System.out.println("spring.jackson.serialization.indent-output=true");
        System.out.println("spring.jackson.deserialization.fail-on-unknown-properties=false");
        System.out.println("spring.jackson.property-naming-strategy=SNAKE_CASE");
        System.out.println("spring.jackson.default-property-inclusion=NON_NULL");
        
        System.out.println("\nCUSTOM JACKSON CONFIGURATION:");
        System.out.println("@Configuration");
        System.out.println("public class JacksonConfig {");
        System.out.println("    ");
        System.out.println("    @Bean");
        System.out.println("    @Primary");
        System.out.println("    public ObjectMapper objectMapper() {");
        System.out.println("        ObjectMapper mapper = new ObjectMapper();");
        System.out.println("        mapper.registerModule(new JavaTimeModule());");
        System.out.println("        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);");
        System.out.println("        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);");
        System.out.println("        return mapper;");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nCONTROLLER JSON HANDLING:");
        System.out.println("@RestController");
        System.out.println("@RequestMapping(\\\"/api/users\\\")");
        System.out.println("public class UserController {");
        System.out.println("    ");
        System.out.println("    @PostMapping");
        System.out.println("    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest request) {");
        System.out.println("        // JSON automatically deserialized to CreateUserRequest");
        System.out.println("        User user = userService.createUser(request);");
        System.out.println("        // User automatically serialized to JSON response");
        System.out.println("        return ResponseEntity.ok(user);");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    @GetMapping(\\\"/{id}\\\")");
        System.out.println("    public User getUser(@PathVariable Long id) {");
        System.out.println("        return userService.findById(id);");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nGLOBAL EXCEPTION HANDLING:");
        System.out.println("@ControllerAdvice");
        System.out.println("public class JsonExceptionHandler {");
        System.out.println("    ");
        System.out.println("    @ExceptionHandler(HttpMessageNotReadableException.class)");
        System.out.println("    @ResponseStatus(HttpStatus.BAD_REQUEST)");
        System.out.println("    public ErrorResponse handleJsonParseError(HttpMessageNotReadableException ex) {");
        System.out.println("        return new ErrorResponse(\\\"Invalid JSON format\\\", ex.getMessage());");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    @ExceptionHandler(MethodArgumentNotValidException.class)");
        System.out.println("    @ResponseStatus(HttpStatus.BAD_REQUEST)");
        System.out.println("    public ValidationErrorResponse handleValidationError(MethodArgumentNotValidException ex) {");
        System.out.println("        // Handle Bean Validation errors");
        System.out.println("        return new ValidationErrorResponse(extractValidationErrors(ex));");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nCONTENT NEGOTIATION:");
        System.out.println("@GetMapping(value = \\\"/users/{id}\\\", produces = {");
        System.out.println("    MediaType.APPLICATION_JSON_VALUE,");
        System.out.println("    MediaType.APPLICATION_XML_VALUE");
        System.out.println("})");
        System.out.println("public User getUser(@PathVariable Long id) {");
        System.out.println("    return userService.findById(id);");
        System.out.println("}");
        
        System.out.println("\nTESTING JSON IN SPRING:");
        System.out.println("@WebMvcTest(UserController.class)");
        System.out.println("class UserControllerTest {");
        System.out.println("    ");
        System.out.println("    @Test");
        System.out.println("    void shouldCreateUser() throws Exception {");
        System.out.println("        mockMvc.perform(post(\\\"/api/users\\\")");
        System.out.println("                .contentType(MediaType.APPLICATION_JSON)");
        System.out.println("                .content(\\\"{\\\\\\\"name\\\\\\\":\\\\\\\"John\\\\\\\",\\\\\\\"email\\\\\\\":\\\\\\\"john@example.com\\\\\\\"}\\\"))");
        System.out.println("                .andExpect(status().isOk())");
        System.out.println("                .andExpect(jsonPath(\\\"$.name\\\").value(\\\"John\\\"))");
        System.out.println("                .andExpect(jsonPath(\\\"$.email\\\").value(\\\"john@example.com\\\"));");
        System.out.println("    }");
        System.out.println("}");
    }
    
    /**
     * PERFORMANCE CONSIDERATIONS
     * 
     * JSON processing performance tips and benchmarks
     */
    public static void demonstratePerformanceConsiderations() {
        System.out.println("\n--- Performance Considerations ---");
        
        System.out.println("PERFORMANCE FACTORS:");
        System.out.println("1. ObjectMapper creation overhead");
        System.out.println("2. Reflection vs compiled accessors");
        System.out.println("3. Memory allocation patterns");
        System.out.println("4. Streaming vs tree processing");
        System.out.println("5. Configuration impact");
        
        System.out.println("\nOPTIMIZATION STRATEGIES:");
        System.out.println("✅ Reuse ObjectMapper instances");
        System.out.println("✅ Use streaming API for large JSON");
        System.out.println("✅ Configure appropriate features");
        System.out.println("✅ Use primitive collections when possible");
        System.out.println("✅ Consider compiled Jackson modules");
        
        System.out.println("\nOBJECTMAPPER REUSE:");
        System.out.println("// ❌ Bad - creates new ObjectMapper every time");
        System.out.println("public String serialize(Object obj) {");
        System.out.println("    ObjectMapper mapper = new ObjectMapper(); // Expensive!");
        System.out.println("    return mapper.writeValueAsString(obj);");
        System.out.println("}");
        System.out.println("");
        System.out.println("// ✅ Good - reuse ObjectMapper");
        System.out.println("private static final ObjectMapper MAPPER = new ObjectMapper();");
        System.out.println("");
        System.out.println("public String serialize(Object obj) {");
        System.out.println("    return MAPPER.writeValueAsString(obj);");
        System.out.println("}");
        
        System.out.println("\nSTREAMING FOR LARGE DATA:");
        System.out.println("// For large arrays or objects");
        System.out.println("public void processLargeJsonArray(InputStream input) throws IOException {");
        System.out.println("    JsonFactory factory = new JsonFactory();");
        System.out.println("    JsonParser parser = factory.createParser(input);");
        System.out.println("    ");
        System.out.println("    if (parser.nextToken() == JsonToken.START_ARRAY) {");
        System.out.println("        while (parser.nextToken() == JsonToken.START_OBJECT) {");
        System.out.println("            MyObject obj = objectMapper.readValue(parser, MyObject.class);");
        System.out.println("            processObject(obj); // Process one at a time");
        System.out.println("        }");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\nPERFORMANCE CONFIGURATION:");
        System.out.println("ObjectMapper mapper = new ObjectMapper();");
        System.out.println("");
        System.out.println("// Disable expensive features if not needed");
        System.out.println("mapper.disable(MapperFeature.USE_ANNOTATIONS);");
        System.out.println("mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);");
        System.out.println("");
        System.out.println("// Use faster alternatives");
        System.out.println("mapper.enable(JsonParser.Feature.USE_FAST_DOUBLE_PARSER);");
        System.out.println("mapper.enable(JsonGenerator.Feature.USE_FAST_DOUBLE_WRITER);");
        
        System.out.println("\nMEMORY CONSIDERATIONS:");
        System.out.println("✅ Use streaming for large JSON files");
        System.out.println("✅ Process data in chunks");
        System.out.println("✅ Use primitive collections (TIntList, etc.)");
        System.out.println("✅ Consider memory-mapped files for very large JSON");
        System.out.println("❌ Don't load entire large JSON into memory");
        System.out.println("❌ Avoid creating unnecessary intermediate objects");
    }
    
    /**
     * BEST PRACTICES
     * 
     * JSON processing best practices and common patterns
     */
    public static void demonstrateBestPractices() {
        System.out.println("\n--- Best Practices ---");
        
        System.out.println("JSON PROCESSING BEST PRACTICES:");
        
        System.out.println("\n1. CONFIGURATION:");
        System.out.println("✅ Configure ObjectMapper once and reuse");
        System.out.println("✅ Use appropriate naming strategies");
        System.out.println("✅ Handle unknown properties gracefully");
        System.out.println("✅ Configure date/time handling properly");
        
        System.out.println("\n2. SECURITY:");
        System.out.println("✅ Validate input JSON structure");
        System.out.println("✅ Use @JsonIgnore for sensitive fields");
        System.out.println("✅ Limit JSON payload size");
        System.out.println("✅ Sanitize dynamic property names");
        System.out.println("❌ Don't expose internal object structure");
        System.out.println("❌ Don't trust client-provided type information");
        
        System.out.println("\n3. ERROR HANDLING:");
        System.out.println("✅ Provide meaningful error messages");
        System.out.println("✅ Handle different error types appropriately");
        System.out.println("✅ Log JSON processing errors");
        System.out.println("✅ Use validation frameworks");
        
        System.out.println("\n4. DESIGN PATTERNS:");
        System.out.println("✅ Use DTOs for API boundaries");
        System.out.println("✅ Separate internal and external representations");
        System.out.println("✅ Use builders for complex JSON structures");
        System.out.println("✅ Version your JSON schemas");
        
        System.out.println("\nCOMMON ANTI-PATTERNS:");
        System.out.println("❌ Creating ObjectMapper repeatedly");
        System.out.println("❌ Exposing entity classes directly in APIs");
        System.out.println("❌ Ignoring JSON processing errors");
        System.out.println("❌ Not handling null values properly");
        System.out.println("❌ Using generic Object for JSON processing");
        System.out.println("❌ Not configuring date/time serialization");
        
        System.out.println("\nDTO PATTERN EXAMPLE:");
        System.out.println("// External API representation");
        System.out.println("@Data");
        System.out.println("public class UserDto {");
        System.out.println("    private Long id;");
        System.out.println("    private String name;");
        System.out.println("    private String email;");
        System.out.println("    ");
        System.out.println("    @JsonFormat(pattern = \\\"yyyy-MM-dd\\\")");
        System.out.println("    private LocalDate birthDate;");
        System.out.println("    ");
        System.out.println("    // No password, internal IDs, etc.");
        System.out.println("}");
        
        System.out.println("\nVERSIONING STRATEGY:");
        System.out.println("// API versioning with JSON");
        System.out.println("@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = \\\"version\\\")");
        System.out.println("@JsonSubTypes({");
        System.out.println("    @JsonSubTypes.Type(value = UserV1.class, name = \\\"v1\\\"),");
        System.out.println("    @JsonSubTypes.Type(value = UserV2.class, name = \\\"v2\\\")");
        System.out.println("})");
        System.out.println("public abstract class User { ... }");
        
        System.out.println("\nMIGRATION FROM .NET:");
        System.out.println("1. Replace JsonConvert with ObjectMapper");
        System.out.println("2. Map [JsonProperty] to @JsonProperty");
        System.out.println("3. Map [JsonIgnore] to @JsonIgnore");
        System.out.println("4. Replace JsonConverter with custom serializers");
        System.out.println("5. Use Jackson modules instead of global converters");
        System.out.println("6. Configure ObjectMapper in Spring Boot configuration");
        
        System.out.println("\nTESTING JSON:");
        System.out.println("✅ Test serialization round-trips");
        System.out.println("✅ Test with malformed JSON");
        System.out.println("✅ Test with missing fields");
        System.out.println("✅ Test with extra fields");
        System.out.println("✅ Test edge cases (null, empty, large values)");
        System.out.println("✅ Use JSONAssert for flexible JSON comparison");
    }
    
    // Example classes for demonstration
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person {
        private Long id;
        private String name;
        private String email;
        private Integer age;
        
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasicPerson {
        private Long id;
        private String name;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AnnotatedUser {
        @JsonProperty("user_id")
        @JsonAlias({"id", "userId"})
        private Long id;
        
        @JsonProperty("full_name")
        @JsonAlias("name")
        private String name;
        
        @JsonProperty("email_address")
        private String email;
        
        @JsonIgnore
        private String password;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonProperty("created_date")
        private LocalDateTime createdAt;
        
        private String optionalField;
    }
    
    @Data
    @AllArgsConstructor
    public static class CustomSerializedData {
        private String sensitiveData;
        private Double value;
        private List<String> items;
    }
    
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = Dog.class, name = "dog"),
        @JsonSubTypes.Type(value = Cat.class, name = "cat")
    })
    @Data
    @AllArgsConstructor
    public static abstract class Animal {
        private String name;
    }
    
    @Data
    @AllArgsConstructor
    public static class Dog extends Animal {
        private String breed;
        
        public Dog(String name, String breed) {
            super(name);
            this.breed = breed;
        }
    }
    
    @Data
    @AllArgsConstructor
    public static class Cat extends Animal {
        private boolean indoor;
        
        public Cat(String name, boolean indoor) {
            super(name);
            this.indoor = indoor;
        }
    }
}