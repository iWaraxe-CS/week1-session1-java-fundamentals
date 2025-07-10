package com.coherentsolutions.session1.antipatterns.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * ANTI-PATTERN: Async Programming in Java
 * 
 * This class demonstrates the WRONG way to handle asynchronous operations in Java.
 * C# developers are used to:
 * - async/await keywords
 * - Task<T> and Task.Run()
 * - ConfigureAwait(false)
 * - Seamless async/sync integration
 * 
 * Java doesn't have async/await, uses CompletableFuture instead.
 * Common mistakes C# developers make when transitioning.
 */
public class AsyncAntiPattern {
    
    public static void main(String[] args) {
        demonstrateAsyncProblems();
    }
    
    public static void demonstrateAsyncProblems() {
        System.out.println("=== ASYNC PROGRAMMING ANTI-PATTERNS ===");
        
        // PROBLEM 1: Trying to use async/await syntax
        System.out.println("\n1. Trying to use async/await syntax:");
        demonstrateAsyncAwaitAttempts();
        
        // PROBLEM 2: Blocking on async operations
        System.out.println("\n2. Blocking on async operations:");
        demonstrateBlocking();
        
        // PROBLEM 3: Not handling exceptions properly
        System.out.println("\n3. Not handling exceptions properly:");
        demonstrateExceptionHandling();
        
        // PROBLEM 4: Creating too many threads
        System.out.println("\n4. Creating too many threads:");
        demonstrateThreadCreation();
        
        // PROBLEM 5: Not composing async operations
        System.out.println("\n5. Not composing async operations:");
        demonstrateComposition();
    }
    
    public static void demonstrateAsyncAwaitAttempts() {
        // ❌ WRONG: Trying to use C# async/await syntax
        
        // This doesn't work in Java:
        // public async Task<String> GetDataAsync() {
        //     String data = await SomeAsyncOperation();
        //     return data.ToUpper();
        // }
        
        System.out.println("Java doesn't have async/await keywords!");
        System.out.println("C# async/await → Java CompletableFuture");
        
        // ❌ WRONG: Trying to return Task<T>
        // This doesn't exist in Java:
        // Task<String> result = GetDataAsync();
        
        System.out.println("C# Task<T> → Java CompletableFuture<T>");
    }
    
    public static void demonstrateBlocking() {
        // ❌ WRONG: Blocking on async operations (defeats the purpose)
        
        CompletableFuture<String> future = fetchDataAsync();
        
        try {
            // ❌ WRONG: Using .get() blocks the current thread
            String result = future.get(); // This blocks!
            System.out.println("Result: " + result);
            System.out.println("This blocks the thread - defeats async purpose!");
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // ❌ WRONG: Using .join() also blocks
        CompletableFuture<String> anotherFuture = fetchDataAsync();
        String result2 = anotherFuture.join(); // Also blocks!
        System.out.println("Join also blocks: " + result2);
    }
    
    public static void demonstrateExceptionHandling() {
        // ❌ WRONG: Not handling exceptions in async operations
        
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("Random failure!");
            }
            return "Success!";
        });
        
        try {
            // ❌ WRONG: Exception handling happens at .get() level
            String result = future.get();
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Exception caught at wrong level: " + e.getMessage());
        }
        
        // ❌ WRONG: Not handling CompletionException properly
        CompletableFuture<Void> voidFuture = CompletableFuture.runAsync(() -> {
            throw new RuntimeException("Async error");
        });
        
        try {
            voidFuture.get();
        } catch (ExecutionException e) {
            // The actual exception is wrapped
            System.out.println("Wrapped exception: " + e.getCause().getMessage());
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }
    
    public static void demonstrateThreadCreation() {
        // ❌ WRONG: Creating new threads for every async operation
        
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            
            // ❌ WRONG: Creating new thread each time
            new Thread(() -> {
                System.out.println("Task " + taskId + " running on: " + 
                    Thread.currentThread().getName());
                
                // Simulate work
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
        
        System.out.println("Created 10 new threads - inefficient!");
        
        // Give threads time to complete
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void demonstrateComposition() {
        // ❌ WRONG: Not composing async operations properly
        
        // Sequential execution (not async composition)
        try {
            CompletableFuture<String> step1 = fetchDataAsync();
            String result1 = step1.get(); // Blocks!
            
            CompletableFuture<String> step2 = processDataAsync(result1);
            String result2 = step2.get(); // Blocks again!
            
            CompletableFuture<String> step3 = saveDataAsync(result2);
            String finalResult = step3.get(); // Blocks once more!
            
            System.out.println("Final result: " + finalResult);
            System.out.println("This is sequential, not async composition!");
        } catch (Exception e) {
            System.out.println("Error in sequential execution: " + e.getMessage());
        }
    }
    
    // ❌ WRONG: Method that doesn't handle async properly
    public static String getDataWrong(String input) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // Simulate async work
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return "Processed: " + input;
        });
        
        // ❌ WRONG: Blocking in a method that should be async
        return future.join();
    }
    
    // ❌ WRONG: Method that doesn't provide timeout
    public static String getDataWithoutTimeout(String input) {
        CompletableFuture<String> future = longRunningOperation(input);
        
        try {
            // ❌ WRONG: No timeout - could wait forever
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException("Operation failed", e);
        }
    }
    
    // ❌ WRONG: Method that doesn't handle cancellation
    public static void startLongRunningTaskWrong() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 1000000; i++) {
                // ❌ WRONG: Not checking for cancellation
                // Simulate long work
                if (i % 100000 == 0) {
                    System.out.println("Processing: " + i);
                }
            }
        });
        
        // No way to cancel this properly
        System.out.println("Started uncancellable task");
    }
    
    // ❌ WRONG: Not using proper async patterns for I/O
    public static String readFileWrong(String filename) {
        // ❌ WRONG: Blocking I/O in async context
        return CompletableFuture.supplyAsync(() -> {
            try {
                // This still blocks the thread pool thread!
                Thread.sleep(2000); // Simulating file I/O
                return "File content: " + filename;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }).join();
    }
    
    // Helper methods
    private static CompletableFuture<String> fetchDataAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return "Fetched data";
        });
    }
    
    private static CompletableFuture<String> processDataAsync(String data) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return "Processed: " + data;
        });
    }
    
    private static CompletableFuture<String> saveDataAsync(String data) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return "Saved: " + data;
        });
    }
    
    private static CompletableFuture<String> longRunningOperation(String input) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000); // 10 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return "Long result: " + input;
        });
    }
}