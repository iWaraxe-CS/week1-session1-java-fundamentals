# In-Class Exercises for .NET to Java Course

## Session 1 Exercises: Java Fundamentals

### Exercise 1.1: Syntax Translation (5 min)
**Convert this C# code to Java:**
```csharp
public class Product
{
    public int Id { get; init; }
    public string Name { get; set; }
    public decimal Price { get; set; }
    public List<string> Tags { get; } = new();
    
    public bool IsExpensive => Price > 100m;
    
    public override string ToString() => $"{Name}: ${Price}";
}
```

**Expected Discussion Points:**
- No init-only properties
- BigDecimal vs decimal
- String formatting differences
- Computed properties → methods

### Exercise 1.2: String Puzzle (5 min)
**What's the output? Why?**
```java
String s1 = "Hello";
String s2 = "Hello";
String s3 = new String("Hello");
String s4 = "Hel" + "lo";
String s5 = "Hel";
String s6 = s5 + "lo";

System.out.println(s1 == s2);    // ?
System.out.println(s1 == s3);    // ?
System.out.println(s1 == s4);    // ?
System.out.println(s1 == s6);    // ?
System.out.println(s1.equals(s6)); // ?
```

### Exercise 1.3: Collection Challenge (10 min)
**Implement a method that:**
1. Takes a list of products
2. Filters products over $50
3. Groups by first tag
4. Returns map of tag to product count

```csharp
// C# version for reference
public Dictionary<string, int> GroupExpensiveProducts(List<Product> products)
{
    return products
        .Where(p => p.Price > 50)
        .GroupBy(p => p.Tags.FirstOrDefault() ?? "untagged")
        .ToDictionary(g => g.Key, g => g.Count());
}
```

### Exercise 1.4: Null Safety Refactor (10 min)
**Refactor to use Optional:**
```java
public class UserService {
    private Map<Long, User> users = new HashMap<>();
    
    public User findUser(Long id) {
        return users.get(id); // Returns null
    }
    
    public String getUserEmail(Long id) {
        User user = findUser(id);
        if (user != null) {
            return user.getEmail();
        }
        return "unknown@example.com";
    }
}
```

### Exercise 1.5: Exception Handling (5 min)
**Fix the compilation errors:**
```java
public class FileProcessor {
    public String readFile(String path) {
        FileReader reader = new FileReader(path);
        // Read file content
        reader.close();
        return content;
    }
    
    public void processFiles(List<String> paths) {
        for (String path : paths) {
            String content = readFile(path);
            System.out.println(content);
        }
    }
}
```
