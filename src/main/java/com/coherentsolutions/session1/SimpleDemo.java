package com.coherentsolutions.session1;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple working demo to verify project compilation
 * This demonstrates basic Java concepts for .NET developers
 */
public class SimpleDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java Fundamentals Demo ===");
        
        // Basic collections (similar to C# List<T>)
        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob"); 
        names.add("Charlie");
        
        System.out.println("Names: " + names);
        
        // String comparison (key difference from C#)
        String s1 = "Hello";
        String s2 = new String("Hello");
        
        System.out.println("s1 == s2: " + (s1 == s2));       // false - reference comparison
        System.out.println("s1.equals(s2): " + s1.equals(s2)); // true - value comparison
        
        // Basic class usage
        User user = new User("John", "john@example.com");
        System.out.println("User: " + user);
        
        System.out.println("Demo completed successfully!");
    }
    
    // Simple POJO class (Plain Old Java Object)
    public static class User {
        private String name;
        private String email;
        
        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        @Override
        public String toString() {
            return "User{name='" + name + "', email='" + email + "'}";
        }
    }
}