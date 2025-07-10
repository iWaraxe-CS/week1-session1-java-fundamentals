# Quick Reference: C# to Java Conversions

## Syntax Mapping

### Basic Types
| C# | Java | Notes |
|----|------|-------|
| `string` | `String` | Capital S |
| `bool` | `boolean` | Lowercase |
| `int` | `int` | Same |
| `long` | `long` | Same |
| `decimal` | `BigDecimal` | Import required |
| `double` | `double` | Same |
| `object` | `Object` | Capital O |
| `dynamic` | `Object` | No true equivalent |

### Nullable Types
| C# | Java | Notes |
|----|------|-------|
| `int?` | `Integer` | Wrapper class |
| `string?` | `String` | Always nullable |
| `T?` | `Optional<T>` | Explicit null handling |

### Collections
| C# | Java | Notes |
|----|------|-------|
| `List<T>` | `List<T>` | Interface in Java |
| `new List<T>()` | `new ArrayList<>()` | Concrete implementation |
| `Dictionary<K,V>` | `Map<K,V>` | Interface |
| `new Dictionary<K,V>()` | `new HashMap<>()` | Concrete implementation |
| `HashSet<T>` | `Set<T>` | Interface |
| `IEnumerable<T>` | `Stream<T>` | Or `Iterable<T>` |
| `T[]` | `T[]` | Same syntax |

### Class Features
| C# | Java | Notes |
|----|------|-------|
| `namespace` | `package` | Lowercase |
| `using` | `import` | Different keyword |
| `partial class` | ❌ | Not supported |
| `sealed class` | `final class` | Different keyword |
| `abstract class` | `abstract class` | Same |
| `interface` | `interface` | Same |
| `struct` | `record` | Java 14+ |
| `: BaseClass` | `extends BaseClass` | Inheritance |
| `: IInterface` | `implements Interface` | No 'I' prefix |

### Properties
| C# | Java |
|----|------|
| `public string Name { get; set; }` | Private field + getter/setter |
| `public string Name { get; init; }` | Final field + constructor |
| `public string Name => "Value";` | Method: `getName()` |
| Auto-property | Use Lombok `@Data` |

### Methods
| C# | Java | Notes |
|----|------|-------|
| `void Method()` | `void method()` | camelCase |
| `async Task Method()` | `CompletableFuture<Void>` | Different async |
| `params int[] nums` | `int... nums` | Varargs |
| `out parameter` | Return object/tuple | No out params |
| `ref parameter` | Pass mutable object | No ref params |
| `default parameter` | Method overloading | No default params |

### Access Modifiers
| C# | Java | Scope |
|----|------|-------|
| `public` | `public` | Everyone |
| `private` | `private` | Class only |
| `protected` | `protected` | Package + subclasses |
| `internal` | (no modifier) | Package only |
| `protected internal` | `protected` | Similar |
| `private protected` | ❌ | Not supported |

### Common Operations
| C# | Java |
|----|------|
| `string.IsNullOrEmpty(s)` | `s == null \|\| s.isEmpty()` |
| `string.Join(",", list)` | `String.join(",", list)` |
| `list.Count` | `list.size()` |
| `dict.ContainsKey(key)` | `map.containsKey(key)` |
| `list.Any()` | `!list.isEmpty()` |
| `list.First()` | `list.get(0)` |
| `list.FirstOrDefault()` | `list.stream().findFirst()` |

### LINQ to Streams
| C# LINQ | Java Stream |
|---------|-------------|
| `Where(x => x > 5)` | `filter(x -> x > 5)` |
| `Select(x => x.Name)` | `map(x -> x.getName())` |
| `SelectMany(x => x.Items)` | `flatMap(x -> x.getItems().stream())` |
| `OrderBy(x => x.Age)` | `sorted(Comparator.comparing(X::getAge))` |
| `GroupBy(x => x.Type)` | `collect(Collectors.groupingBy(X::getType))` |
| `Count()` | `count()` |
| `ToList()` | `collect(Collectors.toList())` |
| `Any(x => x > 5)` | `anyMatch(x -> x > 5)` |
| `All(x => x > 5)` | `allMatch(x -> x > 5)` |
| `Sum()` | `mapToInt(x -> x).sum()` |

### Exception Handling
| C# | Java |
|----|------|
| `try-catch-finally` | `try-catch-finally` |
| `using (var r = ...)` | `try (var r = ...)` |
| `throw new Exception()` | `throw new Exception()` |
| `throw;` | `throw e;` |
| All unchecked | Checked + unchecked |

## Common Libraries

### Dependency Injection
| C# | Java |
|----|------|
| Microsoft.Extensions.DependencyInjection | Spring Framework |
| `[Service]` | `@Service` |
| `[Scoped]` | Default in Spring |
| `[Singleton]` | `@Scope("singleton")` |
| `[Transient]` | `@Scope("prototype")` |

### Testing
| C# | Java |
|----|------|
| xUnit/NUnit/MSTest | JUnit 5 |
| `[Fact]`/`[Test]` | `@Test` |
| `[Theory]` | `@ParameterizedTest` |
| Moq | Mockito |
| FluentAssertions | AssertJ |

### JSON
| C# | Java |
|----|------|
| System.Text.Json | Jackson |
| Newtonsoft.Json | Gson |
| `[JsonProperty]` | `@JsonProperty` |
| `[JsonIgnore]` | `@JsonIgnore` |

### Logging
| C# | Java |
|----|------|
| ILogger<T> | SLF4J + Logback |
| Serilog | Log4j2 |
| NLog | Logback |

### HTTP Clients
| C# | Java |
|----|------|
| HttpClient | WebClient (Spring) |
| RestSharp | RestTemplate |
| Flurl | OkHttp |

## Build Tool Commands

### Package Management
| NuGet | Maven | Gradle |
|-------|-------|---------|
| `dotnet add package` | Add to pom.xml | Add to build.gradle |
| `dotnet restore` | `mvn dependency:resolve` | `gradle dependencies` |
| `dotnet list package` | `mvn dependency:list` | `gradle dependencies` |

### Build Commands
| .NET CLI | Maven | Gradle |
|----------|-------|---------|
| `dotnet build` | `mvn compile` | `gradle build` |
| `dotnet test` | `mvn test` | `gradle test` |
| `dotnet run` | `mvn spring-boot:run` | `gradle bootRun` |
| `dotnet publish` | `mvn package` | `gradle bootJar` |
| `dotnet clean` | `mvn clean` | `gradle clean` |

## IDE Shortcuts (IntelliJ with VS Keymap)

| Action | VS/VS Code | IntelliJ |
|--------|------------|----------|
| Run | F5 | Shift+F10 |
| Debug | F5 | Shift+F9 |
| Find usages | Shift+F12 | Alt+F7 |
| Go to definition | F12 | Ctrl+B |
| Rename | F2 | Shift+F6 |
| Quick fix | Ctrl+. | Alt+Enter |
| Format | Ctrl+K,D | Ctrl+Alt+L |

## Spring Annotations

### Core
| Purpose | Annotation |
|---------|------------|
| Component | `@Component` |
| Service | `@Service` |
| Repository | `@Repository` |
| Controller | `@Controller` |
| REST Controller | `@RestController` |
| Configuration | `@Configuration` |

### REST API
| Purpose | Annotation |
|---------|------------|
| GET mapping | `@GetMapping` |
| POST mapping | `@PostMapping` |
| Request body | `@RequestBody` |
| Path variable | `@PathVariable` |
| Query param | `@RequestParam` |
| Response status | `@ResponseStatus` |

### Dependency Injection
| Purpose | Annotation |
|---------|------------|
| Autowire | `@Autowired` |
| Qualifier | `@Qualifier` |
| Value | `@Value` |
| Bean | `@Bean` |
| Scope | `@Scope` |