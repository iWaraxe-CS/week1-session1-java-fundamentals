package com.coherentsolutions.session1.reference;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * TYPE CONVERSIONS REFERENCE: Java vs C# Type System
 * 
 * This reference class demonstrates the differences between Java and C# type systems
 * and provides examples of type conversions, casting, and boxing/unboxing.
 * 
 * KEY DIFFERENCES:
 * 1. Java: All objects extend Object, primitives are separate
 * 2. C#: All types inherit from object, value types vs reference types
 * 3. Java: No implicit conversions, explicit casting required
 * 4. C#: Rich implicit conversion system with operators
 * 5. Java: Wrapper classes for primitives (Integer, Double, etc.)
 * 6. C#: Nullable value types (int?, double?, etc.)
 * 
 * FOR LECTURE USE:
 * - Demonstrate each conversion pattern
 * - Show common mistakes and solutions
 * - Compare side-by-side with C# equivalents
 * - Highlight boxing/unboxing performance implications
 */
public class TypeConversions {
    
    public static void main(String[] args) {
        System.out.println("=== TYPE CONVERSIONS REFERENCE ===");
        
        demonstratePrimitiveConversions();
        demonstrateWrapperClasses();
        demonstrateStringConversions();
        demonstrateNumericConversions();
        demonstrateDateTimeConversions();
        demonstrateCollectionConversions();
        demonstrateNullableTypes();
        demonstrateAdvancedConversions();
        
        System.out.println("\n=== TYPE CONVERSIONS COMPLETE ===");
    }
    
    /**
     * PRIMITIVE TYPE CONVERSIONS
     * 
     * C# has implicit conversions for compatible types.
     * Java requires explicit casting for narrowing conversions.
     */
    public static void demonstratePrimitiveConversions() {
        System.out.println("\n--- Primitive Type Conversions ---");
        
        // Widening conversions (implicit in Java)
        int intValue = 42;
        long longValue = intValue;        // int → long (implicit)
        float floatValue = intValue;      // int → float (implicit)
        double doubleValue = intValue;    // int → double (implicit)
        
        System.out.println("Widening conversions (implicit):");
        System.out.println("int → long: " + intValue + " → " + longValue);
        System.out.println("int → float: " + intValue + " → " + floatValue);
        System.out.println("int → double: " + intValue + " → " + doubleValue);
        
        // Narrowing conversions (explicit casting required)
        double sourceDouble = 123.456;
        int narrowedInt = (int) sourceDouble;           // double → int (explicit)
        float narrowedFloat = (float) sourceDouble;     // double → float (explicit)
        long narrowedLong = (long) sourceDouble;        // double → long (explicit)
        
        System.out.println("\nNarrowing conversions (explicit casting):");
        System.out.println("double → int: " + sourceDouble + " → " + narrowedInt);
        System.out.println("double → float: " + sourceDouble + " → " + narrowedFloat);
        System.out.println("double → long: " + sourceDouble + " → " + narrowedLong);
        
        // Character conversions
        char charValue = 'A';
        int charAsInt = charValue;              // char → int (implicit)
        char intAsChar = (char) 65;             // int → char (explicit)
        
        System.out.println("\nCharacter conversions:");
        System.out.println("char → int: '" + charValue + "' → " + charAsInt);
        System.out.println("int → char: " + 65 + " → '" + intAsChar + "'");
        
        // Boolean conversions (no implicit conversions in Java)
        boolean boolValue = true;
        // int boolAsInt = boolValue;           // ❌ Compilation error in Java
        // boolean intAsBool = 1;               // ❌ Compilation error in Java
        
        System.out.println("\nBoolean conversions:");
        System.out.println("Java: No implicit boolean conversions");
        System.out.println("C#: bool can convert to/from int (true=1, false=0)");
        
        // Manual boolean conversions
        int boolAsInt = boolValue ? 1 : 0;
        boolean intAsBool = (intValue != 0);
        
        System.out.println("Manual: boolean → int: " + boolValue + " → " + boolAsInt);
        System.out.println("Manual: int → boolean: " + intValue + " → " + intAsBool);
    }
    
    /**
     * WRAPPER CLASSES AND BOXING/UNBOXING
     * 
     * Java: Autoboxing/unboxing between primitives and wrapper classes
     * C#: Boxing/unboxing between value types and object
     */
    public static void demonstrateWrapperClasses() {
        System.out.println("\n--- Wrapper Classes and Boxing ---");
        
        // Autoboxing: primitive → wrapper
        int primitiveInt = 42;
        Integer wrappedInt = primitiveInt;              // Autoboxing
        Double wrappedDouble = 3.14;                    // Autoboxing
        Boolean wrappedBoolean = true;                  // Autoboxing
        
        System.out.println("Autoboxing (primitive → wrapper):");
        System.out.println("int → Integer: " + primitiveInt + " → " + wrappedInt);
        System.out.println("double → Double: 3.14 → " + wrappedDouble);
        System.out.println("boolean → Boolean: true → " + wrappedBoolean);
        
        // Unboxing: wrapper → primitive
        int unboxedInt = wrappedInt;                    // Unboxing
        double unboxedDouble = wrappedDouble;           // Unboxing
        boolean unboxedBoolean = wrappedBoolean;        // Unboxing
        
        System.out.println("\nUnboxing (wrapper → primitive):");
        System.out.println("Integer → int: " + wrappedInt + " → " + unboxedInt);
        System.out.println("Double → double: " + wrappedDouble + " → " + unboxedDouble);
        System.out.println("Boolean → boolean: " + wrappedBoolean + " → " + unboxedBoolean);
        
        // Wrapper class methods
        System.out.println("\nWrapper class utility methods:");
        System.out.println("Integer.parseInt(\"123\"): " + Integer.parseInt("123"));
        System.out.println("Double.parseDouble(\"3.14\"): " + Double.parseDouble("3.14"));
        System.out.println("Boolean.parseBoolean(\"true\"): " + Boolean.parseBoolean("true"));
        System.out.println("Integer.valueOf(\"42\"): " + Integer.valueOf("42"));
        System.out.println("Integer.toString(42): " + Integer.toString(42));
        
        // Comparison: value vs reference equality
        Integer int1 = 127;
        Integer int2 = 127;
        Integer int3 = 128;
        Integer int4 = 128;
        
        System.out.println("\nWrapper equality comparison:");
        System.out.println("Integer(127) == Integer(127): " + (int1 == int2));     // true (cached)
        System.out.println("Integer(128) == Integer(128): " + (int3 == int4));     // false (not cached)
        System.out.println("Integer(127).equals(Integer(127)): " + int1.equals(int2)); // true
        System.out.println("Integer(128).equals(Integer(128)): " + int3.equals(int4)); // true
        
        // Null handling with wrappers
        Integer nullableInt = null;
        // int primitiveFromNull = nullableInt;         // ❌ NullPointerException
        
        System.out.println("\nNull handling:");
        System.out.println("Nullable Integer: " + nullableInt);
        System.out.println("Safe conversion: " + Optional.ofNullable(nullableInt).orElse(0));
    }
    
    /**
     * STRING CONVERSIONS
     * 
     * Converting between strings and other types
     */
    public static void demonstrateStringConversions() {
        System.out.println("\n--- String Conversions ---");
        
        // Primitive/wrapper to String
        int number = 42;
        double decimal = 3.14159;
        boolean flag = true;
        
        System.out.println("To String conversions:");
        System.out.println("int → String: " + String.valueOf(number));
        System.out.println("double → String: " + String.valueOf(decimal));
        System.out.println("boolean → String: " + String.valueOf(flag));
        System.out.println("Concatenation: " + number + " + \"\" = " + (number + ""));
        
        // String to primitive/wrapper
        String numberStr = "123";
        String decimalStr = "45.67";
        String boolStr = "true";
        
        System.out.println("\nFrom String conversions:");
        System.out.println("String → int: \"" + numberStr + "\" → " + Integer.parseInt(numberStr));
        System.out.println("String → double: \"" + decimalStr + "\" → " + Double.parseDouble(decimalStr));
        System.out.println("String → boolean: \"" + boolStr + "\" → " + Boolean.parseBoolean(boolStr));
        
        // Safe conversions with error handling
        String invalidNumber = "abc";
        System.out.println("\nSafe conversions with error handling:");
        
        try {
            int parsed = Integer.parseInt(invalidNumber);
            System.out.println("Parsed: " + parsed);
        } catch (NumberFormatException e) {
            System.out.println("Failed to parse \"" + invalidNumber + "\" as integer");
        }
        
        // Using Optional for safe conversion
        Optional<Integer> safeInt = parseIntSafely("123");
        Optional<Integer> safeFail = parseIntSafely("abc");
        
        System.out.println("Safe parse \"123\": " + safeInt.orElse(-1));
        System.out.println("Safe parse \"abc\": " + safeFail.orElse(-1));
        
        // Formatting numbers to strings
        System.out.println("\nFormatted string conversions:");
        System.out.println("Formatted double: " + String.format("%.2f", 3.14159));
        System.out.println("Padded integer: " + String.format("%05d", 42));
        System.out.println("Hex format: " + String.format("%x", 255));
        System.out.println("Binary format: " + Integer.toBinaryString(42));
    }
    
    /**
     * NUMERIC CONVERSIONS
     * 
     * Working with different numeric types and precision
     */
    public static void demonstrateNumericConversions() {
        System.out.println("\n--- Numeric Conversions ---");
        
        // Integer types
        byte byteValue = 127;
        short shortValue = 32767;
        int intValue = 2147483647;
        long longValue = 9223372036854775807L;
        
        System.out.println("Integer type hierarchy:");
        System.out.println("byte: " + byteValue + " (8 bits)");
        System.out.println("short: " + shortValue + " (16 bits)");
        System.out.println("int: " + intValue + " (32 bits)");
        System.out.println("long: " + longValue + " (64 bits)");
        
        // Floating point types
        float floatValue = 3.14159f;
        double doubleValue = 3.141592653589793;
        
        System.out.println("\nFloating point types:");
        System.out.println("float: " + floatValue + " (32 bits)");
        System.out.println("double: " + doubleValue + " (64 bits)");
        
        // BigDecimal for precise decimal arithmetic
        BigDecimal precise1 = new BigDecimal("0.1");
        BigDecimal precise2 = new BigDecimal("0.2");
        BigDecimal preciseSum = precise1.add(precise2);
        
        System.out.println("\nPrecision with BigDecimal:");
        System.out.println("double: 0.1 + 0.2 = " + (0.1 + 0.2));
        System.out.println("BigDecimal: 0.1 + 0.2 = " + preciseSum);
        
        // BigInteger for large integers
        BigInteger bigInt1 = new BigInteger("12345678901234567890");
        BigInteger bigInt2 = new BigInteger("98765432109876543210");
        BigInteger bigSum = bigInt1.add(bigInt2);
        
        System.out.println("\nLarge integers with BigInteger:");
        System.out.println("BigInteger sum: " + bigSum);
        
        // Overflow behavior
        int maxInt = Integer.MAX_VALUE;
        int overflow = maxInt + 1;
        
        System.out.println("\nOverflow behavior:");
        System.out.println("Integer.MAX_VALUE: " + maxInt);
        System.out.println("MAX_VALUE + 1: " + overflow + " (overflow)");
        
        // Math operations and conversions
        System.out.println("\nMath operations:");
        System.out.println("Math.ceil(3.14): " + Math.ceil(3.14));
        System.out.println("Math.floor(3.14): " + Math.floor(3.14));
        System.out.println("Math.round(3.14): " + Math.round(3.14));
        System.out.println("Math.round(3.64): " + Math.round(3.64));
    }
    
    /**
     * DATE AND TIME CONVERSIONS
     * 
     * Working with Java 8+ time API vs older Date classes
     */
    public static void demonstrateDateTimeConversions() {
        System.out.println("\n--- Date and Time Conversions ---");
        
        // Modern Java time API (Java 8+)
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        
        System.out.println("Modern Java Time API:");
        System.out.println("LocalDate.now(): " + today);
        System.out.println("LocalDateTime.now(): " + now);
        
        // String to date conversion
        String dateStr = "2023-12-25";
        String datetimeStr = "2023-12-25T15:30:00";
        
        LocalDate parsedDate = LocalDate.parse(dateStr);
        LocalDateTime parsedDateTime = LocalDateTime.parse(datetimeStr);
        
        System.out.println("\nString to Date conversions:");
        System.out.println("Parsed date: " + parsedDate);
        System.out.println("Parsed datetime: " + parsedDateTime);
        
        // Custom date format parsing
        String customFormat = "25/12/2023";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate customParsed = LocalDate.parse(customFormat, formatter);
        
        System.out.println("Custom format parse: " + customFormat + " → " + customParsed);
        
        // Date to string conversion
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        System.out.println("\nDate to String conversions:");
        System.out.println("Formatted date: " + formattedDate);
        System.out.println("Formatted datetime: " + formattedDateTime);
        
        // Epoch conversions
        long epochSecond = now.toEpochSecond(java.time.ZoneOffset.UTC);
        System.out.println("DateTime to epoch: " + epochSecond);
        
        // Comparison with C# DateTime
        System.out.println("\nJava vs C# DateTime:");
        System.out.println("Java: LocalDate, LocalDateTime, ZonedDateTime");
        System.out.println("C#: DateTime, DateTimeOffset, DateOnly, TimeOnly");
        System.out.println("Java: Immutable by design");
        System.out.println("C#: DateTime is mutable (with some immutable operations)");
    }
    
    /**
     * COLLECTION CONVERSIONS
     * 
     * Converting between different collection types
     */
    public static void demonstrateCollectionConversions() {
        System.out.println("\n--- Collection Conversions ---");
        
        // Array to List
        String[] stringArray = {"apple", "banana", "cherry"};
        List<String> listFromArray = Arrays.asList(stringArray);
        List<String> mutableList = new java.util.ArrayList<>(Arrays.asList(stringArray));
        
        System.out.println("Array to List conversions:");
        System.out.println("Arrays.asList(): " + listFromArray);
        System.out.println("Mutable copy: " + mutableList);
        
        // List to Array
        String[] arrayFromList = listFromArray.toArray(new String[0]);
        Object[] objectArray = listFromArray.toArray();
        
        System.out.println("\nList to Array conversions:");
        System.out.println("toArray(T[]): " + Arrays.toString(arrayFromList));
        System.out.println("toArray(): " + Arrays.toString(objectArray));
        
        // Primitive array conversions
        int[] intArray = {1, 2, 3, 4, 5};
        List<Integer> integerList = Arrays.stream(intArray)
            .boxed()
            .collect(java.util.stream.Collectors.toList());
        
        int[] backToIntArray = integerList.stream()
            .mapToInt(Integer::intValue)
            .toArray();
        
        System.out.println("\nPrimitive array conversions:");
        System.out.println("int[] to List<Integer>: " + integerList);
        System.out.println("List<Integer> to int[]: " + Arrays.toString(backToIntArray));
        
        // Collection type conversions
        java.util.Set<String> setFromList = new java.util.HashSet<>(listFromArray);
        List<String> listFromSet = new java.util.ArrayList<>(setFromList);
        
        System.out.println("\nCollection type conversions:");
        System.out.println("List to Set: " + setFromList);
        System.out.println("Set to List: " + listFromSet);
    }
    
    /**
     * NULLABLE TYPES AND OPTIONAL
     * 
     * Java Optional vs C# nullable types
     */
    public static void demonstrateNullableTypes() {
        System.out.println("\n--- Nullable Types and Optional ---");
        
        // C# nullable types: int?, double?, bool?
        // Java equivalent: Optional<Integer>, Optional<Double>, Optional<Boolean>
        
        System.out.println("C# vs Java nullable types:");
        System.out.println("C#: int? nullableInt = null;");
        System.out.println("Java: Optional<Integer> optionalInt = Optional.empty();");
        
        // Optional usage patterns
        Optional<String> presentValue = Optional.of("Hello");
        Optional<String> emptyValue = Optional.empty();
        Optional<String> nullableValue = Optional.ofNullable(null);
        
        System.out.println("\nOptional usage:");
        System.out.println("Present value: " + presentValue.isPresent());
        System.out.println("Empty value: " + emptyValue.isPresent());
        System.out.println("Nullable value: " + nullableValue.isPresent());
        
        // Optional operations
        String result1 = presentValue.orElse("Default");
        String result2 = emptyValue.orElse("Default");
        String result3 = emptyValue.orElseGet(() -> "Generated Default");
        
        System.out.println("\nOptional operations:");
        System.out.println("orElse with present: " + result1);
        System.out.println("orElse with empty: " + result2);
        System.out.println("orElseGet with empty: " + result3);
        
        // Optional chaining
        Optional<String> chainResult = presentValue
            .map(String::toUpperCase)
            .filter(s -> s.length() > 3)
            .map(s -> s + "!");
        
        System.out.println("Optional chaining: " + chainResult.orElse("No result"));
        
        // Converting nullable to Optional
        String nullableString = Math.random() > 0.5 ? "Value" : null;
        Optional<String> converted = Optional.ofNullable(nullableString);
        
        System.out.println("Nullable to Optional: " + converted.orElse("Was null"));
    }
    
    /**
     * ADVANCED CONVERSIONS
     * 
     * Type conversions in complex scenarios
     */
    public static void demonstrateAdvancedConversions() {
        System.out.println("\n--- Advanced Conversions ---");
        
        // Generic type conversions
        List<Object> objectList = Arrays.asList("string", 42, 3.14, true);
        System.out.println("Mixed type list: " + objectList);
        
        // Safe casting with instanceof
        for (Object obj : objectList) {
            if (obj instanceof String) {
                String str = (String) obj;
                System.out.println("String: " + str.toUpperCase());
            } else if (obj instanceof Integer) {
                Integer num = (Integer) obj;
                System.out.println("Integer: " + (num * 2));
            } else if (obj instanceof Double) {
                Double dbl = (Double) obj;
                System.out.println("Double: " + String.format("%.1f", dbl));
            } else if (obj instanceof Boolean) {
                Boolean bool = (Boolean) obj;
                System.out.println("Boolean: " + (bool ? "YES" : "NO"));
            }
        }
        
        // Functional interface conversions
        java.util.function.Function<String, Integer> stringToLength = String::length;
        java.util.function.Function<Integer, String> intToString = Object::toString;
        
        System.out.println("\nFunctional conversions:");
        System.out.println("String length: " + stringToLength.apply("Hello"));
        System.out.println("Int to string: " + intToString.apply(42));
        
        // Stream conversions
        List<String> words = Arrays.asList("one", "two", "three", "four");
        List<Integer> lengths = words.stream()
            .map(String::length)
            .collect(java.util.stream.Collectors.toList());
        
        System.out.println("Stream conversion: " + words + " → " + lengths);
        
        // Enum conversions
        System.out.println("\nEnum conversions:");
        ConversionType type1 = ConversionType.IMPLICIT;
        String enumToString = type1.toString();
        ConversionType stringToEnum = ConversionType.valueOf("EXPLICIT");
        
        System.out.println("Enum to String: " + enumToString);
        System.out.println("String to Enum: " + stringToEnum);
    }
    
    // Helper method for safe integer parsing
    private static Optional<Integer> parseIntSafely(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    
    // Example enum for conversions
    public enum ConversionType {
        IMPLICIT, EXPLICIT, UNSAFE
    }
    
    /**
     * CONVERSION PATTERNS SUMMARY
     * 
     * Common patterns for type conversions in Java vs C#:
     * 
     * 1. PRIMITIVE CONVERSIONS:
     *    - Java: Explicit casting for narrowing, implicit for widening
     *    - C#: Similar, but more implicit conversions available
     * 
     * 2. STRING CONVERSIONS:
     *    - Java: String.valueOf(), Type.parseType(), Type.toString()
     *    - C#: .ToString(), Type.Parse(), Convert.ToType()
     * 
     * 3. NULLABLE HANDLING:
     *    - Java: Optional<T> for null safety
     *    - C#: T? nullable value types
     * 
     * 4. COLLECTION CONVERSIONS:
     *    - Java: Stream API, Arrays.asList(), toArray()
     *    - C#: LINQ, ToArray(), ToList(), Cast<T>()
     * 
     * 5. SAFE CONVERSIONS:
     *    - Java: Try-catch, Optional, instanceof
     *    - C#: TryParse(), is/as operators, nullable types
     */
}