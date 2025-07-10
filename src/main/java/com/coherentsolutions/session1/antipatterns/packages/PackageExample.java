package com.coherentsolutions.session1.antipatterns.packages;

/**
 * PACKAGE ORGANIZATION EXAMPLES
 * 
 * This class demonstrates the differences between C# namespace organization
 * and Java package organization.
 * 
 * KEY DIFFERENCES:
 * 1. C# namespaces vs Java packages
 * 2. PascalCase vs lowercase
 * 3. File structure requirements
 * 4. Access modifiers and visibility
 * 5. Using/import statement differences
 * 
 * COMMON MISTAKES C# DEVELOPERS MAKE:
 * - Using PascalCase in package names
 * - Not matching file structure to package structure
 * - Misunderstanding package-private access
 * - Trying to use C# using alias syntax
 */
public class PackageExample {
    
    public static void main(String[] args) {
        demonstratePackageOrganization();
        demonstratePackagePatterns();
        demonstrateNamingBestPractices();
    }
    
    public static void demonstratePackageOrganization() {
        System.out.println("=== PACKAGE ORGANIZATION DEMO ===");
        
        // Current package information
        Package currentPackage = PackageExample.class.getPackage();
        System.out.println("Current package: " + currentPackage.getName());
        
        // ❌ C# NAMESPACE STYLE (WRONG IN JAVA):
        // namespace Com.CoherentSolutions.Session1.AntiPatterns.Packages
        // {
        //     public class PackageExample
        //     {
        //         // C# code here
        //     }
        // }
        
        // ✅ JAVA PACKAGE STYLE (CORRECT):
        // package com.coherentsolutions.session1.antipatterns.packages;
        // public class PackageExample {
        //     // Java code here
        // }
        
        System.out.println("\n=== PACKAGE NAMING CONVENTIONS ===");
        System.out.println("C# Namespace: Com.CoherentSolutions.Session1.AntiPatterns");
        System.out.println("Java Package: com.coherentsolutions.session1.antipatterns");
        System.out.println("\nKey Differences:");
        System.out.println("1. C# uses PascalCase, Java uses lowercase");
        System.out.println("2. C# allows spaces in project names, Java doesn't");
        System.out.println("3. Java follows reverse domain naming convention");
        
        System.out.println("\n=== FILE STRUCTURE REQUIREMENTS ===");
        System.out.println("Java package MUST match directory structure:");
        System.out.println("Package: com.coherentsolutions.session1.antipatterns.packages");
        System.out.println("File: src/main/java/com/coherentsolutions/session1/antipatterns/packages/PackageExample.java");
        System.out.println("\nC# namespace can be independent of folder structure");
        
        System.out.println("\n=== ACCESS MODIFIER DIFFERENCES ===");
        demonstrateAccessModifiers();
        
        System.out.println("\n=== IMPORT STATEMENT DIFFERENCES ===");
        demonstrateImports();
    }
    
    public static void demonstrateAccessModifiers() {
        System.out.println("\nACCESS MODIFIERS COMPARISON:");
        System.out.println("\nC# Access Modifiers:");
        System.out.println("- public: Accessible everywhere");
        System.out.println("- private: Only within the same class");
        System.out.println("- protected: Class and subclasses");
        System.out.println("- internal: Same assembly");
        System.out.println("- protected internal: Assembly + subclasses");
        
        System.out.println("\nJava Access Modifiers:");
        System.out.println("- public: Accessible everywhere");
        System.out.println("- private: Only within the same class");
        System.out.println("- protected: Package + subclasses");
        System.out.println("- (package-private): Same package only");
        
        System.out.println("\nKEY DIFFERENCE:");
        System.out.println("Java has package-private (no modifier) instead of C# internal");
        System.out.println("Java protected includes package access");
    }
    
    public static void demonstrateImports() {
        System.out.println("\nIMPORT STATEMENT COMPARISON:");
        
        System.out.println("\nC# using statements:");
        System.out.println("using System;");
        System.out.println("using System.Collections.Generic;");
        System.out.println("using System.Linq;");
        System.out.println("using MyAlias = Some.Very.Long.Namespace.ClassName;");
        
        System.out.println("\nJava import statements:");
        System.out.println("import java.util.List;");
        System.out.println("import java.util.ArrayList;");
        System.out.println("import java.util.*; // Wildcard import");
        System.out.println("import static java.lang.Math.PI; // Static import");
        
        System.out.println("\nKEY DIFFERENCES:");
        System.out.println("1. Java doesn't have using aliases");
        System.out.println("2. Java has static imports for static members");
        System.out.println("3. Java wildcard imports (*) vs C# using directives");
        System.out.println("4. Java automatically imports java.lang.*");
    }
    
    // Examples of different access levels
    public String publicField = "Accessible everywhere";
    protected String protectedField = "Package + subclasses";
    String packagePrivateField = "Same package only"; // No modifier = package-private
    private String privateField = "This class only";
    
    public void publicMethod() {
        System.out.println("Public method - accessible everywhere");
    }
    
    protected void protectedMethod() {
        System.out.println("Protected method - package + subclasses");
    }
    
    void packagePrivateMethod() { // No modifier = package-private
        System.out.println("Package-private method - same package only");
    }
    
    private void privateMethod() {
        System.out.println("Private method - this class only");
    }
    
    // Static nested class to demonstrate package structure
    public static class NestedExample {
        public void demonstrateNesting() {
            System.out.println("\n=== NESTED CLASS ORGANIZATION ===");
            System.out.println("Nested classes in Java are similar to C#");
            System.out.println("Full name: " + this.getClass().getName());
        }
    }
    
    // Examples of common package organization patterns
    public static void demonstratePackagePatterns() {
        System.out.println("\n=== COMMON PACKAGE ORGANIZATION PATTERNS ===");
        
        System.out.println("\n1. LAYERED ARCHITECTURE:");
        System.out.println("com.company.project.controller");
        System.out.println("com.company.project.service");
        System.out.println("com.company.project.repository");
        System.out.println("com.company.project.domain");
        
        System.out.println("\n2. FEATURE-BASED ARCHITECTURE:");
        System.out.println("com.company.project.user");
        System.out.println("com.company.project.order");
        System.out.println("com.company.project.payment");
        System.out.println("com.company.project.notification");
        
        System.out.println("\n3. HEXAGONAL ARCHITECTURE:");
        System.out.println("com.company.project.domain");
        System.out.println("com.company.project.application");
        System.out.println("com.company.project.infrastructure");
        System.out.println("com.company.project.adapters");
        
        System.out.println("\n4. CLEAN ARCHITECTURE:");
        System.out.println("com.company.project.entities");
        System.out.println("com.company.project.usecases");
        System.out.println("com.company.project.adapters");
        System.out.println("com.company.project.frameworks");
    }
    
    // Examples of package naming best practices
    public static void demonstrateNamingBestPractices() {
        System.out.println("\n=== PACKAGE NAMING BEST PRACTICES ===");
        
        System.out.println("\n✅ GOOD PACKAGE NAMES:");
        System.out.println("com.coherentsolutions.project.user");
        System.out.println("com.coherentsolutions.project.user.service");
        System.out.println("com.coherentsolutions.project.user.repository");
        System.out.println("com.coherentsolutions.project.common.utils");
        
        System.out.println("\n❌ BAD PACKAGE NAMES:");
        System.out.println("Com.CoherentSolutions.Project.User // PascalCase");
        System.out.println("com.coherentsolutions.project.User // Class name in package");
        System.out.println("com.coherentsolutions.project.utilities // Too verbose");
        System.out.println("com.company.proj.u // Too abbreviated");
        
        System.out.println("\nBEST PRACTICES:");
        System.out.println("1. Use lowercase letters only");
        System.out.println("2. Use reverse domain name convention");
        System.out.println("3. Use singular nouns (user, not users)");
        System.out.println("4. Keep package names short but meaningful");
        System.out.println("5. Avoid java.* and javax.* prefixes");
        System.out.println("6. Use meaningful hierarchy");
    }
    
    // Demonstrate package-private access
    static class PackagePrivateExample {
        // This class is only visible within the same package
        String packageData = "Only accessible within package";
        
        void packageMethod() {
            System.out.println("Package-private method");
        }
    }
}