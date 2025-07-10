package com.coherentsolutions.session1.antipatterns.valuetypes;

import lombok.Value;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * CORRECT PATTERN: Value Types in Java
 * 
 * This class demonstrates the CORRECT way to implement value-like behavior in Java.
 * Key principles:
 * 1. Use immutable objects for value semantics
 * 2. Properly implement equals(), hashCode(), and toString()
 * 3. Use records (Java 14+) for simple value objects
 * 4. Use Lombok @Value for automatic value object implementation
 * 5. Create type-safe wrappers instead of primitive obsession
 * 6. Ensure objects are truly immutable
 */
public class ValueTypeCorrect {
    
    public static void main(String[] args) {
        demonstrateCorrectValueTypes();
    }
    
    public static void demonstrateCorrectValueTypes() {
        System.out.println("=== CORRECT VALUE TYPE PATTERNS ===");
        
        // SOLUTION 1: Records for simple value objects
        System.out.println("\n1. Records for simple value objects:");
        demonstrateRecords();
        
        // SOLUTION 2: Lombok @Value annotation
        System.out.println("\n2. Lombok @Value annotation:");
        demonstrateLombokValue();
        
        // SOLUTION 3: Manual immutable implementation
        System.out.println("\n3. Manual immutable implementation:");
        demonstrateManualImmutable();
        
        // SOLUTION 4: Type-safe wrappers
        System.out.println("\n4. Type-safe wrappers:");
        demonstrateTypeSafeWrappers();
        
        // SOLUTION 5: Complex value objects
        System.out.println("\n5. Complex value objects:");
        demonstrateComplexValueObjects();
    }
    
    public static void demonstrateRecords() {
        // ✅ CORRECT: Using records (Java 14+)
        
        PointRecord p1 = new PointRecord(10, 20);
        PointRecord p2 = new PointRecord(10, 20);
        PointRecord p3 = p1; // Reference copy
        
        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);
        
        // ✅ CORRECT: Value equality
        System.out.println("p1.equals(p2): " + p1.equals(p2)); // true
        System.out.println("p1 == p2: " + (p1 == p2)); // false (different references)
        System.out.println("p1 == p3: " + (p1 == p3)); // true (same reference)
        
        // ✅ CORRECT: Proper hashCode implementation
        System.out.println("p1.hashCode(): " + p1.hashCode());
        System.out.println("p2.hashCode(): " + p2.hashCode());
        System.out.println("Equal objects have same hash code!");
        
        // ✅ CORRECT: "Copying" by creating new instance
        PointRecord moved = new PointRecord(p1.x() + 10, p1.y() + 10);
        System.out.println("Original: " + p1);
        System.out.println("Moved: " + moved);
    }
    
    public static void demonstrateLombokValue() {
        // ✅ CORRECT: Using Lombok @Value
        
        PersonValue person1 = new PersonValue("John", 25);
        PersonValue person2 = new PersonValue("John", 25);
        
        System.out.println("person1: " + person1);
        System.out.println("person2: " + person2);
        
        // ✅ CORRECT: Automatic equals implementation
        System.out.println("person1.equals(person2): " + person1.equals(person2)); // true
        
        // ✅ CORRECT: Automatic hashCode implementation
        System.out.println("person1.hashCode(): " + person1.hashCode());
        System.out.println("person2.hashCode(): " + person2.hashCode());
        
        // ✅ CORRECT: "Updating" by creating new instance
        PersonValue olderPerson = person1.withAge(26);
        System.out.println("Original: " + person1);
        System.out.println("Older: " + olderPerson);
    }
    
    public static void demonstrateManualImmutable() {
        // ✅ CORRECT: Manual immutable implementation
        
        ImmutablePoint point1 = new ImmutablePoint(5, 15);
        ImmutablePoint point2 = new ImmutablePoint(5, 15);
        
        System.out.println("point1: " + point1);
        System.out.println("point2: " + point2);
        
        // ✅ CORRECT: Proper value equality
        System.out.println("point1.equals(point2): " + point1.equals(point2)); // true
        
        // ✅ CORRECT: Consistent hashCode
        System.out.println("point1.hashCode(): " + point1.hashCode());
        System.out.println("point2.hashCode(): " + point2.hashCode());
        
        // ✅ CORRECT: Immutable operations
        ImmutablePoint translated = point1.translate(10, 5);
        System.out.println("Original: " + point1);
        System.out.println("Translated: " + translated);
    }
    
    public static void demonstrateTypeSafeWrappers() {
        // ✅ CORRECT: Type-safe wrappers instead of primitives
        
        Temperature celsius = Temperature.celsius(25);
        Money usd = Money.of(100.50, "USD");
        UserId userId = new UserId("user-123");
        
        System.out.println("Celsius: " + celsius);
        System.out.println("USD: " + usd);
        System.out.println("User ID: " + userId);
        
        // ✅ CORRECT: Type safety prevents errors
        processTemperature(celsius);
        processMoney(usd);
        processUser(userId);
        
        System.out.println("Type safety prevents semantic errors!");
    }
    
    public static void demonstrateComplexValueObjects() {
        // ✅ CORRECT: Complex value objects with business logic
        
        Address address1 = Address.builder()
            .street("123 Main St")
            .city("New York")
            .state("NY")
            .zipCode("10001")
            .country("USA")
            .build();
        
        Address address2 = Address.builder()
            .street("123 Main St")
            .city("New York")
            .state("NY")
            .zipCode("10001")
            .country("USA")
            .build();
        
        System.out.println("address1: " + address1);
        System.out.println("address1.equals(address2): " + address1.equals(address2));
        
        // ✅ CORRECT: Business logic in value objects
        System.out.println("Formatted: " + address1.getFormattedAddress());
        System.out.println("Is US address: " + address1.isUSAddress());
        
        // ✅ CORRECT: Immutable updates
        Address newAddress = address1.withZipCode("10002");
        System.out.println("Original: " + address1.getZipCode());
        System.out.println("Updated: " + newAddress.getZipCode());
    }
    
    private static void processTemperature(Temperature temp) {
        System.out.println("Processing temperature: " + temp);
    }
    
    private static void processMoney(Money money) {
        System.out.println("Processing money: " + money);
    }
    
    private static void processUser(UserId userId) {
        System.out.println("Processing user: " + userId);
    }
    
    // ✅ CORRECT: Record for simple value object (Java 14+)
    public record PointRecord(int x, int y) {
        
        // ✅ CORRECT: Validation in compact constructor
        public PointRecord {
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("Coordinates must be non-negative");
            }
        }
        
        // ✅ CORRECT: Business methods
        public double distanceFromOrigin() {
            return Math.sqrt(x * x + y * y);
        }
        
        public PointRecord translate(int dx, int dy) {
            return new PointRecord(x + dx, y + dy);
        }
    }
    
    // ✅ CORRECT: Lombok @Value for automatic implementation
    @Value
    public static class PersonValue {
        String name;
        int age;
        
        // ✅ CORRECT: Validation
        public PersonValue(String name, int age) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            this.name = name.trim();
            this.age = age;
        }
        
        // ✅ CORRECT: "Wither" methods for immutable updates
        public PersonValue withAge(int newAge) {
            return new PersonValue(this.name, newAge);
        }
        
        public PersonValue withName(String newName) {
            return new PersonValue(newName, this.age);
        }
        
        // ✅ CORRECT: Business methods
        public boolean isAdult() {
            return age >= 18;
        }
    }
    
    // ✅ CORRECT: Manual immutable implementation
    public static final class ImmutablePoint {
        private final int x;
        private final int y;
        
        public ImmutablePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public int getX() {
            return x;
        }
        
        public int getY() {
            return y;
        }
        
        public ImmutablePoint translate(int dx, int dy) {
            return new ImmutablePoint(x + dx, y + dy);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            ImmutablePoint that = (ImmutablePoint) obj;
            return x == that.x && y == that.y;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
        
        @Override
        public String toString() {
            return "Point(" + x + ", " + y + ")";
        }
    }
    
    // ✅ CORRECT: Type-safe temperature
    @Value
    public static class Temperature {
        double value;
        TemperatureUnit unit;
        
        public static Temperature celsius(double value) {
            return new Temperature(value, TemperatureUnit.CELSIUS);
        }
        
        public static Temperature fahrenheit(double value) {
            return new Temperature(value, TemperatureUnit.FAHRENHEIT);
        }
        
        public Temperature toCelsius() {
            if (unit == TemperatureUnit.CELSIUS) {
                return this;
            }
            return celsius((value - 32) * 5.0 / 9.0);
        }
        
        public Temperature toFahrenheit() {
            if (unit == TemperatureUnit.FAHRENHEIT) {
                return this;
            }
            return fahrenheit(value * 9.0 / 5.0 + 32);
        }
        
        public enum TemperatureUnit {
            CELSIUS, FAHRENHEIT
        }
    }
    
    // ✅ CORRECT: Type-safe money
    @Value
    public static class Money {
        BigDecimal amount;
        String currencyCode;
        
        public static Money of(double amount, String currencyCode) {
            return new Money(BigDecimal.valueOf(amount), currencyCode);
        }
        
        public static Money of(BigDecimal amount, String currencyCode) {
            return new Money(amount, currencyCode);
        }
        
        public Money add(Money other) {
            if (!currencyCode.equals(other.currencyCode)) {
                throw new IllegalArgumentException("Cannot add different currencies");
            }
            return new Money(amount.add(other.amount), currencyCode);
        }
        
        public Money multiply(double factor) {
            return new Money(amount.multiply(BigDecimal.valueOf(factor)), currencyCode);
        }
    }
    
    // ✅ CORRECT: Type-safe ID classes
    @Value
    public static class UserId {
        String value;
        
        public UserId(String value) {
            if (value == null || value.trim().isEmpty()) {
                throw new IllegalArgumentException("User ID cannot be null or empty");
            }
            this.value = value.trim();
        }
    }
    
    // ✅ CORRECT: Complex value object with builder
    @Value
    @Builder
    public static class Address {
        String street;
        String city;
        String state;
        String zipCode;
        String country;
        
        public String getFormattedAddress() {
            return String.format("%s, %s, %s %s, %s", 
                street, city, state, zipCode, country);
        }
        
        public boolean isUSAddress() {
            return "USA".equalsIgnoreCase(country);
        }
        
        public Address withZipCode(String newZipCode) {
            return new Address(street, city, state, newZipCode, country);
        }
        
        public Address withStreet(String newStreet) {
            return new Address(newStreet, city, state, zipCode, country);
        }
    }
}