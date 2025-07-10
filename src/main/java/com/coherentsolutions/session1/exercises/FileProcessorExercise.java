package com.coherentsolutions.session1.exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * FILE PROCESSOR EXERCISE
 * 
 * This exercise combines multiple Java fundamentals concepts:
 * - File I/O operations
 * - Stream processing
 * - Collection manipulation
 * - Exception handling
 * - Functional programming
 * 
 * SCENARIO:
 * You're building a log file analyzer that processes server log files.
 * The log files contain entries in the format:
 * "2023-12-01 10:15:30 INFO User john logged in"
 * "2023-12-01 10:16:45 ERROR Database connection failed"
 * "2023-12-01 10:17:20 WARN Memory usage at 85%"
 * 
 * TASKS TO COMPLETE:
 * 1. Read log file line by line
 * 2. Parse each log entry into structured data
 * 3. Filter entries by log level (INFO, WARN, ERROR)
 * 4. Group entries by date
 * 5. Generate summary statistics
 * 6. Write results to output file
 * 
 * LEARNING OBJECTIVES:
 * - Practice file I/O with proper exception handling
 * - Use Streams API for data processing
 * - Apply functional programming concepts
 * - Handle optional values properly
 * - Work with collections and grouping
 * 
 * FOR .NET DEVELOPERS:
 * This exercise demonstrates Java equivalents of:
 * - File.ReadAllLines() -> Files.readAllLines()
 * - LINQ operations -> Stream operations
 * - Using statements -> try-with-resources
 * - String manipulation and parsing
 */
public class FileProcessorExercise {
    
    public static void main(String[] args) {
        System.out.println("=== FILE PROCESSOR EXERCISE ===");
        
        // Create sample log data for testing
        createSampleLogFile();
        
        // TODO: Complete the implementation
        FileProcessor processor = new FileProcessor();
        
        try {
            // Process the log file
            LogSummary summary = processor.processLogFile("sample.log");
            
            // Display results
            System.out.println("\n--- Log Processing Results ---");
            System.out.println("Total entries: " + summary.getTotalEntries());
            System.out.println("Error count: " + summary.getErrorCount());
            System.out.println("Warning count: " + summary.getWarningCount());
            System.out.println("Info count: " + summary.getInfoCount());
            
            // Display entries by date
            System.out.println("\nEntries by date:");
            summary.getEntriesByDate().forEach((date, entries) -> {
                System.out.println(date + ": " + entries.size() + " entries");
            });
            
            // Display most common error messages
            System.out.println("\nMost common error messages:");
            summary.getMostCommonErrors().forEach((message, count) -> {
                System.out.println(message + " (occurred " + count + " times)");
            });
            
            // Generate and save report
            processor.generateReport(summary, "log_report.txt");
            System.out.println("\nReport saved to log_report.txt");
            
        } catch (Exception e) {
            System.err.println("Error processing log file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a sample log file for testing
     */
    private static void createSampleLogFile() {
        List<String> sampleLogs = List.of(
            "2023-12-01 10:15:30 INFO User john logged in",
            "2023-12-01 10:16:45 ERROR Database connection failed",
            "2023-12-01 10:17:20 WARN Memory usage at 85%",
            "2023-12-01 10:18:10 INFO User mary logged in",
            "2023-12-01 10:19:33 ERROR Database connection failed",
            "2023-12-01 10:20:15 INFO Processing batch job",
            "2023-12-02 09:30:22 WARN Disk space low",
            "2023-12-02 09:31:45 INFO User alice logged in",
            "2023-12-02 09:32:10 ERROR Authentication failed",
            "2023-12-02 09:33:55 INFO Backup completed",
            "2023-12-02 09:34:20 ERROR Network timeout",
            "2023-12-02 09:35:40 WARN High CPU usage detected"
        );
        
        try {
            Files.write(Paths.get("sample.log"), sampleLogs);
            System.out.println("Sample log file created: sample.log");
        } catch (IOException e) {
            System.err.println("Failed to create sample log file: " + e.getMessage());
        }
    }
    
    /**
     * LogEntry represents a single log entry
     * 
     * TODO: Complete this class
     * - Add proper fields for date, time, level, message
     * - Add constructor that parses log line
     * - Add getters and other necessary methods
     * - Consider using Lombok annotations
     */
    public static class LogEntry {
        // TODO: Add fields for date, time, level, message
        
        // TODO: Add constructor that parses a log line
        // Hint: Use String.split() or regex to parse the line
        
        // TODO: Add getters and toString method
        
        // TODO: Consider validation and error handling
        
        // Stub methods to make compilation work
        public String getLevel() { throw new UnsupportedOperationException("Not implemented yet"); }
        public String getDate() { throw new UnsupportedOperationException("Not implemented yet"); }
        public String getMessage() { throw new UnsupportedOperationException("Not implemented yet"); }
    }
    
    /**
     * LogSummary contains aggregated statistics about log entries
     * 
     * TODO: Complete this class
     * - Add fields for counts and collections
     * - Add methods to calculate statistics
     * - Consider using Lombok annotations
     */
    public static class LogSummary {
        // TODO: Add fields for:
        // - Total entry count
        // - Count by log level (INFO, WARN, ERROR)
        // - Entries grouped by date
        // - Most common error messages
        
        // TODO: Add getters and calculation methods
        
        // TODO: Add method to generate summary text
        
        // Stub methods to make compilation work
        public int getTotalEntries() { throw new UnsupportedOperationException("Not implemented yet"); }
        public long getErrorCount() { throw new UnsupportedOperationException("Not implemented yet"); }
        public long getWarningCount() { throw new UnsupportedOperationException("Not implemented yet"); }
        public long getInfoCount() { throw new UnsupportedOperationException("Not implemented yet"); }
        public Map<String, List<LogEntry>> getEntriesByDate() { throw new UnsupportedOperationException("Not implemented yet"); }
        public Map<String, Long> getMostCommonErrors() { throw new UnsupportedOperationException("Not implemented yet"); }
    }
    
    /**
     * FileProcessor handles the main file processing logic
     * 
     * TODO: Complete this class with proper methods
     */
    public static class FileProcessor {
        
        /**
         * Process a log file and return summary statistics
         * 
         * TODO: Implement this method
         * Steps:
         * 1. Read all lines from file
         * 2. Parse each line into LogEntry
         * 3. Filter out invalid entries
         * 4. Group and aggregate data
         * 5. Return LogSummary object
         * 
         * @param filename the log file to process
         * @return LogSummary with aggregated statistics
         * @throws IOException if file cannot be read
         */
        public LogSummary processLogFile(String filename) throws IOException {
            // TODO: Implement file processing logic
            // Hint: Use Files.readAllLines() or Files.lines()
            // Hint: Use Stream API for processing
            
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        /**
         * Generate a text report from log summary
         * 
         * TODO: Implement this method
         * @param summary the log summary to report on
         * @param outputFile the file to write report to
         * @throws IOException if file cannot be written
         */
        public void generateReport(LogSummary summary, String outputFile) throws IOException {
            // TODO: Generate formatted report text
            // TODO: Write to output file
            
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        /**
         * Parse a single log line into LogEntry
         * 
         * TODO: Implement this helper method
         * @param line the log line to parse
         * @return LogEntry or null if invalid
         */
        private LogEntry parseLogLine(String line) {
            // TODO: Parse log line format: "date time level message"
            // TODO: Handle invalid formats gracefully
            
            return null;
        }
        
        /**
         * Validate if a log line has correct format
         * 
         * TODO: Implement this helper method
         * @param line the log line to validate
         * @return true if valid format
         */
        private boolean isValidLogLine(String line) {
            // TODO: Add validation logic
            // TODO: Check for required components
            
            return false;
        }
    }
}

/*
HINTS FOR IMPLEMENTATION:

1. FILE I/O:
   - Use Files.readAllLines() or Files.lines() for reading
   - Use try-with-resources for automatic resource management
   - Handle IOException appropriately

2. STREAM PROCESSING:
   - Use filter() to remove invalid entries
   - Use map() to transform strings to LogEntry objects
   - Use collect() with groupingBy() for aggregation
   - Use counting() for statistics

3. PARSING:
   - Use String.split() or regex Pattern.matcher()
   - Handle different date/time formats
   - Validate input before processing

4. COLLECTIONS:
   - Use Map for grouping (date -> entries)
   - Use Set for unique values
   - Use List for ordered collections

5. EXCEPTION HANDLING:
   - Handle parsing errors gracefully
   - Use Optional for methods that might fail
   - Provide meaningful error messages

6. PERFORMANCE:
   - Use parallel streams for large files
   - Consider memory usage with large datasets
   - Use appropriate collection types

EXAMPLE EXPECTED OUTPUT:
=== FILE PROCESSOR EXERCISE ===
Sample log file created: sample.log

--- Log Processing Results ---
Total entries: 12
Error count: 4
Warning count: 3
Info count: 5

Entries by date:
2023-12-01: 6 entries
2023-12-02: 6 entries

Most common error messages:
Database connection failed (occurred 2 times)
Authentication failed (occurred 1 times)
Network timeout (occurred 1 times)

Report saved to log_report.txt
*/