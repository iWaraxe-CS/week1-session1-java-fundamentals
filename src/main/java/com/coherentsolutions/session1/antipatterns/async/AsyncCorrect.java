package com.coherentsolutions.session1.antipatterns.async;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * CORRECT PATTERN: Async Programming in Java
 * 
 * This class demonstrates the CORRECT way to handle asynchronous operations in Java.
 * Key principles:
 * 1. Use CompletableFuture instead of async/await
 * 2. Compose operations instead of blocking
 * 3. Handle exceptions within the async chain
 * 4. Use proper thread pools instead of creating new threads
 * 5. Implement timeouts and cancellation
 * 6. Use appropriate async patterns for different scenarios
 */
public class AsyncCorrect {
    
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    
    public static void main(String[] args) {
        demonstrateCorrectAsync();
        
        // Cleanup
        executor.shutdown();
        scheduler.shutdown();
    }
    
    public static void demonstrateCorrectAsync() {
        System.out.println("=== CORRECT ASYNC PROGRAMMING PATTERNS ===");
        
        // SOLUTION 1: Proper async composition
        System.out.println("\n1. Proper async composition:");
        demonstrateAsyncComposition();
        
        // SOLUTION 2: Exception handling in async chains
        System.out.println("\n2. Exception handling in async chains:");
        demonstrateAsyncExceptionHandling();
        
        // SOLUTION 3: Parallel execution
        System.out.println("\n3. Parallel execution:");
        demonstrateParallelExecution();
        
        // SOLUTION 4: Timeout handling
        System.out.println("\n4. Timeout handling:");
        demonstrateTimeoutHandling();
        
        // SOLUTION 5: Proper resource management
        System.out.println("\n5. Proper resource management:");
        demonstrateResourceManagement();
        
        // Give async operations time to complete
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void demonstrateAsyncComposition() {
        // ✅ CORRECT: Async composition without blocking
        
        CompletableFuture<String> pipeline = fetchDataAsync()
            .thenCompose(data -> processDataAsync(data))
            .thenCompose(processed -> saveDataAsync(processed))
            .thenApply(saved -> "Final result: " + saved)
            .whenComplete((result, throwable) -> {
                if (throwable != null) {
                    System.out.println("Pipeline failed: " + throwable.getMessage());
                } else {
                    System.out.println("Pipeline completed: " + result);
                }
            });
        
        System.out.println("Async pipeline started (non-blocking)");
    }
    
    public static void demonstrateAsyncExceptionHandling() {
        // ✅ CORRECT: Exception handling within async chain
        
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.7) {
                throw new RuntimeException("Random failure!");
            }
            return "Success!";
        })
        .handle((result, throwable) -> {
            if (throwable != null) {
                System.out.println("Handled exception: " + throwable.getMessage());
                return "Default value";
            }
            return result;
        })
        .thenApply(String::toUpperCase)
        .exceptionally(throwable -> {
            System.out.println("Final exception handler: " + throwable.getMessage());
            return "FALLBACK";
        });
        
        future.whenComplete((result, throwable) -> {
            System.out.println("Final result: " + result);
        });
    }
    
    public static void demonstrateParallelExecution() {
        // ✅ CORRECT: Running multiple async operations in parallel
        
        List<CompletableFuture<String>> futures = Arrays.asList(
            fetchDataAsync("Source 1"),
            fetchDataAsync("Source 2"),
            fetchDataAsync("Source 3")
        );
        
        // Wait for all to complete
        CompletableFuture<List<String>> allResults = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0])
        ).thenApply(v -> 
            futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList())
        );
        
        allResults.whenComplete((results, throwable) -> {
            if (throwable != null) {
                System.out.println("Parallel execution failed: " + throwable.getMessage());
            } else {
                System.out.println("All parallel results: " + results);
            }
        });
        
        // ✅ CORRECT: Race condition - first one wins
        CompletableFuture<String> firstResult = CompletableFuture.anyOf(
            futures.toArray(new CompletableFuture[0])
        ).thenApply(result -> (String) result);
        
        firstResult.whenComplete((result, throwable) -> {
            System.out.println("First result: " + result);
        });
    }
    
    public static void demonstrateTimeoutHandling() {
        // ✅ CORRECT: Adding timeout to async operations
        
        CompletableFuture<String> futureWithTimeout = longRunningOperation("test")
            .orTimeout(2, TimeUnit.SECONDS)
            .handle((result, throwable) -> {
                if (throwable instanceof TimeoutException) {
                    System.out.println("Operation timed out");
                    return "Timeout result";
                } else if (throwable != null) {
                    System.out.println("Operation failed: " + throwable.getMessage());
                    return "Error result";
                }
                return result;
            });
        
        futureWithTimeout.whenComplete((result, throwable) -> {
            System.out.println("Operation with timeout result: " + result);
        });
        
        // ✅ CORRECT: Custom timeout implementation
        CompletableFuture<String> customTimeout = withTimeout(
            longRunningOperation("custom"),
            Duration.ofSeconds(1),
            "Custom timeout"
        );
        
        customTimeout.whenComplete((result, throwable) -> {
            System.out.println("Custom timeout result: " + result);
        });
    }
    
    public static void demonstrateResourceManagement() {
        // ✅ CORRECT: Using proper thread pools
        
        List<CompletableFuture<String>> tasks = Arrays.asList(
            CompletableFuture.supplyAsync(() -> processTask(1), executor),
            CompletableFuture.supplyAsync(() -> processTask(2), executor),
            CompletableFuture.supplyAsync(() -> processTask(3), executor),
            CompletableFuture.supplyAsync(() -> processTask(4), executor)
        );
        
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(
            tasks.toArray(new CompletableFuture[0])
        );
        
        allTasks.whenComplete((v, throwable) -> {
            if (throwable != null) {
                System.out.println("Some tasks failed: " + throwable.getMessage());
            } else {
                System.out.println("All tasks completed successfully");
                tasks.forEach(task -> {
                    try {
                        System.out.println("Task result: " + task.get());
                    } catch (Exception e) {
                        System.out.println("Task error: " + e.getMessage());
                    }
                });
            }
        });
    }
    
    // ✅ CORRECT: Async method that returns CompletableFuture
    public static CompletableFuture<String> getDataCorrect(String input) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException(e);
            }
            return "Processed: " + input;
        }, executor);
    }
    
    // ✅ CORRECT: Method with proper timeout handling
    public static CompletableFuture<String> getDataWithTimeout(String input, Duration timeout) {
        CompletableFuture<String> future = longRunningOperation(input);
        
        return future
            .orTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
            .exceptionally(throwable -> {
                if (throwable.getCause() instanceof TimeoutException) {
                    return "Operation timed out after " + timeout.toSeconds() + " seconds";
                }
                return "Operation failed: " + throwable.getMessage();
            });
    }
    
    // Helper methods
    private static CompletableFuture<String> fetchDataAsync() {
        return fetchDataAsync("default");
    }
    
    private static CompletableFuture<String> fetchDataAsync(String source) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException(e);
            }
            return "Fetched data from " + source;
        }, executor);
    }
    
    private static CompletableFuture<String> processDataAsync(String data) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException(e);
            }
            return "Processed: " + data;
        }, executor);
    }
    
    private static CompletableFuture<String> saveDataAsync(String data) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException(e);
            }
            return "Saved: " + data;
        }, executor);
    }
    
    private static CompletableFuture<String> longRunningOperation(String input) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000); // 5 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException(e);
            }
            return "Long result: " + input;
        }, executor);
    }
    
    private static String processTask(int taskId) {
        try {
            Thread.sleep(100 * taskId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CompletionException(e);
        }
        return "Task " + taskId + " completed on " + Thread.currentThread().getName();
    }
    
    // ✅ CORRECT: Utility methods
    
    // Custom timeout implementation
    public static <T> CompletableFuture<T> withTimeout(CompletableFuture<T> future, 
                                                      Duration timeout, 
                                                      T defaultValue) {
        CompletableFuture<T> timeoutFuture = new CompletableFuture<>();
        
        scheduler.schedule(() -> {
            timeoutFuture.complete(defaultValue);
        }, timeout.toMillis(), TimeUnit.MILLISECONDS);
        
        return future.applyToEither(timeoutFuture, result -> result);
    }
    
    // Retry mechanism
    public static <T> CompletableFuture<T> retryAsync(Supplier<CompletableFuture<T>> operation, 
                                                     int maxRetries) {
        CompletableFuture<T> future = operation.get();
        
        for (int i = 0; i < maxRetries; i++) {
            final int attempt = i;
            future = future.exceptionally(throwable -> {
                System.out.println("Retry attempt " + (attempt + 1) + " after failure: " + 
                    throwable.getMessage());
                return null;
            }).thenCompose(result -> {
                if (result != null) {
                    return CompletableFuture.completedFuture(result);
                } else {
                    return operation.get();
                }
            });
        }
        
        return future;
    }
}