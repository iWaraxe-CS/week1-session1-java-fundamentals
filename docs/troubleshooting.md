# Troubleshooting Guide - Java Fundamentals

This guide addresses common issues .NET developers encounter when learning Java, organized by category with specific solutions.

## Environment Setup Issues

### Problem: "Java version not found" or wrong version
**Symptoms:**
```bash
java -version
# Command not found or shows Java 8
```

**Solutions:**
1. **Install Java 17+**:
   ```bash
   # macOS with Homebrew
   brew install openjdk@17
   
   # Windows with Chocolatey
   choco install openjdk17
   
   # Linux (Ubuntu/Debian)
   sudo apt install openjdk-17-jdk
   ```

2. **Set JAVA_HOME**:
   ```bash
   # macOS/Linux (add to ~/.bashrc or ~/.zshrc)
   export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
   export PATH=$JAVA_HOME/bin:$PATH
   
   # Windows (System Environment Variables)
   JAVA_HOME=C:\Program Files\Java\jdk-17
   PATH=%JAVA_HOME%\bin;%PATH%
   ```

3. **Verify installation**:
   ```bash
   java -version
   javac -version
   echo $JAVA_HOME
   ```

### Problem: IntelliJ IDEA not recognizing Java project
**Symptoms:**
- Red underlines everywhere
- "Cannot resolve symbol" errors
- No code completion

**Solutions:**
1. **Check Project SDK**:
   - File → Project Structure → Project → Project SDK
   - Should be Java 17 or higher

2. **Refresh Maven project**:
   - Maven tool window → Refresh icon
   - Or: View → Tool Windows → Maven → Refresh

3. **Invalidate caches**:
   - File → Invalidate Caches and Restart
   - Choose "Invalidate and Restart"

### Problem: Lombok not working
**Symptoms:**
- @Data annotations not generating methods
- "Cannot resolve method" errors for getters/setters
- IDE shows red squiggles on Lombok annotations

**Solutions:**
1. **Install Lombok plugin**:
   - IntelliJ: File → Settings → Plugins → Search "Lombok" → Install
   - Restart IDE after installation

2. **Enable annotation processing**:
   - File → Settings → Build → Compiler → Annotation Processors
   - Check "Enable annotation processing"

3. **Check Maven dependency**:
   ```xml
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <version>1.18.30</version>
       <scope>provided</scope>
   </dependency>
   ```

4. **Verify annotation processor**:
   ```xml
   <plugin>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-compiler-plugin</artifactId>
       <configuration>
           <annotationProcessorPaths>
               <path>
                   <groupId>org.projectlombok</groupId>
                   <artifactId>lombok</artifactId>
                   <version>1.18.30</version>
               </path>
           </annotationProcessorPaths>
       </configuration>
   </plugin>
   ```

## Build and Dependency Issues

### Problem: Maven build failures
**Symptoms:**
```
[ERROR] Failed to execute goal... compilation failure
[ERROR] dependency not found
```

**Solutions:**
1. **Clean and rebuild**:
   ```bash
   mvn clean compile
   mvn clean install
   ```

2. **Update dependencies**:
   ```bash
   mvn dependency:resolve
   mvn dependency:tree  # Check for conflicts
   ```

3. **Clear local repository** (nuclear option):
   ```bash
   # Delete Maven cache
   rm -rf ~/.m2/repository
   mvn clean install
   ```

4. **Check internet connection**:
   - Maven needs internet to download dependencies
   - Corporate networks may require proxy configuration

### Problem: "Package does not exist" errors
**Symptoms:**
```java
import com.example.service.UserService;  // Red underline
// Package com.example.service does not exist
```

**Solutions:**
1. **Check package structure**:
   ```
   src/main/java/
   └── com/
       └── example/
           └── service/
               └── UserService.java
   ```

2. **Verify package declaration**:
   ```java
   // In UserService.java
   package com.example.service;  // Must match directory structure
   ```

3. **Refresh project**:
   - IntelliJ: File → Reload Gradle/Maven Project
   - Or use Ctrl+Shift+O (Organize Imports)

### Problem: Spring Boot application won't start
**Symptoms:**
```
APPLICATION FAILED TO START
No qualifying bean of type 'UserService' available
```

**Solutions:**
1. **Check component scanning**:
   ```java
   @SpringBootApplication
   public class Application {
       // This scans current package and subpackages
   }
   
   // Or specify packages explicitly
   @SpringBootApplication(scanBasePackages = "com.example")
   public class Application {
   }
   ```

2. **Verify service annotations**:
   ```java
   @Service  // Don't forget this!
   public class UserService {
   }
   ```

3. **Check constructor injection**:
   ```java
   @RestController
   @RequiredArgsConstructor  // Generates constructor
   public class UserController {
       private final UserService userService;  // Must be final
   }
   ```

## Common Code Issues

### Problem: NullPointerException everywhere
**Symptoms:**
```java
Exception in thread "main" java.lang.NullPointerException
    at com.example.service.UserService.getUser(UserService.java:25)
```

**Solutions:**
1. **Check for null before use**:
   ```java
   // ❌ Dangerous
   String upperName = user.getName().toUpperCase();
   
   // ✅ Safe
   String name = user.getName();
   if (name != null) {
       String upperName = name.toUpperCase();
   }
   
   // ✅ Even better
   String upperName = Optional.ofNullable(user.getName())
       .map(String::toUpperCase)
       .orElse("UNKNOWN");
   ```

2. **Use defensive programming**:
   ```java
   public class UserService {
       public User findUser(String id) {
           if (id == null || id.trim().isEmpty()) {
               throw new IllegalArgumentException("User ID cannot be null or empty");
           }
           // Implementation
       }
   }
   ```

3. **Enable null annotations** (IntelliJ):
   - File → Settings → Editor → Inspections
   - Enable "Probable bugs → Nullability problems"

### Problem: String comparison not working
**Symptoms:**
```java
String name = getUserName();
if (name == "John") {  // Always false!
    System.out.println("Hello John");
}
```

**Solutions:**
1. **Use .equals() method**:
   ```java
   // ✅ Correct ways
   if ("John".equals(name)) {  // Null-safe
   }
   
   if (Objects.equals(name, "John")) {  // Null-safe for both
   }
   
   if (name != null && name.equals("John")) {  // Explicit null check
   }
   ```

2. **Understanding the difference**:
   ```java
   String name1 = "John";
   String name2 = new String("John");
   
   System.out.println(name1 == name2);        // false (different objects)
   System.out.println(name1.equals(name2));   // true (same content)
   ```

### Problem: Collections modification during iteration
**Symptoms:**
```java
Exception in thread "main" java.util.ConcurrentModificationException
```

**Solutions:**
1. **Use iterator for safe removal**:
   ```java
   // ❌ Wrong
   for (User user : users) {
       if (user.isInactive()) {
           users.remove(user);  // ConcurrentModificationException!
       }
   }
   
   // ✅ Correct
   Iterator<User> iterator = users.iterator();
   while (iterator.hasNext()) {
       User user = iterator.next();
       if (user.isInactive()) {
           iterator.remove();
       }
   }
   
   // ✅ Even better with streams
   users = users.stream()
       .filter(user -> !user.isInactive())
       .collect(Collectors.toList());
   ```

## Testing Issues

### Problem: Tests not running
**Symptoms:**
- Test methods not found
- "No tests found" message
- Tests appear grayed out in IDE

**Solutions:**
1. **Check test annotations**:
   ```java
   import org.junit.jupiter.api.Test;  // JUnit 5
   
   class UserServiceTest {
       @Test  // Don't forget this!
       void shouldCreateUser() {
           // Test implementation
       }
   }
   ```

2. **Verify test directory structure**:
   ```
   src/test/java/
   └── com/
       └── example/
           └── service/
               └── UserServiceTest.java
   ```

3. **Check Maven Surefire plugin**:
   ```xml
   <plugin>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-surefire-plugin</artifactId>
       <version>3.0.0-M7</version>
   </plugin>
   ```

### Problem: Mock objects not working
**Symptoms:**
```java
@Mock
private UserRepository userRepository;  // NullPointerException when used
```

**Solutions:**
1. **Enable Mockito annotations**:
   ```java
   @ExtendWith(MockitoExtension.class)  // JUnit 5
   class UserServiceTest {
       
       @Mock
       private UserRepository userRepository;
       
       @InjectMocks
       private UserService userService;
   }
   ```

2. **Manual mock initialization**:
   ```java
   @BeforeEach
   void setUp() {
       MockitoAnnotations.openMocks(this);
   }
   ```

3. **Check Spring Boot test setup**:
   ```java
   @SpringBootTest
   class UserServiceIntegrationTest {
       
       @MockBean  // Spring-managed mock
       private UserRepository userRepository;
       
       @Autowired
       private UserService userService;
   }
   ```

## Performance and Memory Issues

### Problem: OutOfMemoryError
**Symptoms:**
```
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
```

**Solutions:**
1. **Increase heap size**:
   ```bash
   # Run with more memory
   java -Xmx2g -jar myapp.jar
   
   # Or set in IDE run configuration
   VM options: -Xmx2g
   ```

2. **Check for memory leaks**:
   ```java
   // ❌ Potential memory leak
   private static List<User> cache = new ArrayList<>();
   
   // ✅ Use weak references or limited cache
   private static Map<String, User> cache = new ConcurrentHashMap<>();
   ```

3. **Profile memory usage**:
   - IntelliJ: Run → Profile → CPU and Memory Profiler
   - Or use VisualVM, JProfiler

### Problem: Slow application startup
**Symptoms:**
- Spring Boot takes >30 seconds to start
- IDE freezes during compilation

**Solutions:**
1. **Reduce Spring Boot startup time**:
   ```java
   @SpringBootApplication
   @EnableAutoConfiguration(exclude = {
       DataSourceAutoConfiguration.class,  // If not using database
       SecurityAutoConfiguration.class     // If not using security
   })
   public class Application {
   }
   ```

2. **Optimize IDE settings**:
   - File → Settings → Build → Compiler
   - Increase "Heap size" to 2048 MB
   - Enable "Compile independent modules in parallel"

## Package and Import Issues

### Problem: Package naming conventions
**Symptoms:**
```java
package Com.Example.MyApp;  // Wrong capitalization
package mycompany;          // Too generic
```

**Solutions:**
1. **Follow Java conventions**:
   ```java
   // ✅ Correct format
   package com.coherentsolutions.session1.service;
   //      └── lowercase, reverse domain notation
   ```

2. **Package organization**:
   ```
   com.company.project/
   ├── controller/          # Web layer
   ├── service/            # Business logic
   ├── repository/         # Data access
   ├── domain/            # Entities
   └── config/            # Configuration
   ```

### Problem: Import conflicts
**Symptoms:**
```java
import java.util.Date;
import java.sql.Date;  // Conflict!
```

**Solutions:**
1. **Use fully qualified names**:
   ```java
   import java.util.Date;
   
   public class MyClass {
       private java.util.Date createdDate;
       private java.sql.Date sqlDate;
   }
   ```

2. **Choose the right import**:
   ```java
   import java.time.LocalDateTime;  // ✅ Preferred for new code
   import java.util.Date;           // ❌ Legacy, avoid if possible
   ```

## Quick Reference: Common Error Messages

### Compilation Errors
| Error Message | Likely Cause | Solution |
|---------------|--------------|----------|
| "package does not exist" | Wrong package structure | Check directory matches package |
| "cannot find symbol" | Missing import or typo | Add import or fix spelling |
| "class is public, should be declared in a file named" | Filename doesn't match class name | Rename file to match class |

### Runtime Errors
| Error Message | Likely Cause | Solution |
|---------------|--------------|----------|
| NullPointerException | Null reference access | Add null checks |
| ClassNotFoundException | Missing dependency | Add Maven dependency |
| NoSuchMethodError | Version mismatch | Check dependency versions |
| ConcurrentModificationException | Modifying collection during iteration | Use iterator or streams |

### Spring Boot Errors
| Error Message | Likely Cause | Solution |
|---------------|--------------|----------|
| "No qualifying bean" | Missing @Service/@Component | Add proper annotations |
| "Failed to determine a suitable driver class" | Missing database dependency | Add H2 or database driver |
| "Port 8080 was already in use" | Another app using port | Change server.port in properties |

## Getting Help

### Before Asking for Help
1. **Read the error message carefully** - it usually tells you what's wrong
2. **Check the stack trace** - find the line in YOUR code that caused the error
3. **Search for the exact error message** - someone probably had this issue before

### How to Ask Good Questions
**❌ Poor question:**
"My code doesn't work, please help!"

**✅ Good question:**
"I'm getting a NullPointerException on line 25 of UserService.java when trying to call getName() on a User object. Here's the relevant code... I've checked that the user is not null using the debugger, but getName() still throws NPE."

### Where to Get Help
1. **Stack Overflow**: Tag with `java`, `spring-boot`, and specific technology
2. **Official Documentation**: 
   - [Java Docs](https://docs.oracle.com/en/java/)
   - [Spring Boot Docs](https://spring.io/projects/spring-boot)
3. **Community Forums**:
   - Reddit: r/learnjava, r/java
   - Discord: Java Community servers
4. **IDE Help**: IntelliJ IDEA has excellent built-in help

### Emergency Fixes
**When you're completely stuck:**
1. **Restart everything**: IDE, terminal, computer
2. **Clean build**: `mvn clean install`
3. **Check Git history**: What changed since it last worked?
4. **Create minimal example**: Isolate the problem
5. **Ask for pair programming help**: Fresh eyes often spot issues quickly

---

**Remember**: Every Java developer has been through these issues. They're part of the learning process, not a sign that you're not cut out for Java development!