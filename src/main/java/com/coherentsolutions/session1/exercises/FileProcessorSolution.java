package com.coherentsolutions.session1.exercises;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FILE PROCESSOR SOLUTION
 * 
 * Complete solution for the File Processor exercise demonstrating:
 * - File I/O with proper exception handling
 * - Stream processing and functional programming
 * - Collection manipulation and grouping
 * - Data parsing and validation
 * - Report generation
 * 
 * This solution shows Java equivalents of common .NET patterns:
 * - File.ReadAllLines() -> Files.readAllLines()
 * - LINQ operations -> Stream operations
 * - Using statements -> try-with-resources
 * - String manipulation and parsing
 * 
 * KEY CONCEPTS DEMONSTRATED:
 * 1. Proper exception handling with try-with-resources
 * 2. Stream API for data processing pipeline
 * 3. Functional programming with method references
 * 4. Optional handling for graceful error recovery
 * 5. Collections grouping and aggregation
 * 6. Lombok for reducing boilerplate code
 * 7. Logging for debugging and monitoring
 */
@Slf4j
public class FileProcessorSolution {
    
    public static void main(String[] args) {
        System.out.println("=== FILE PROCESSOR SOLUTION ===");
        
        // Create sample log data for testing
        createSampleLogFile();
        
        FileProcessor processor = new FileProcessor();
        
        try {
            // Process the log file
            LogSummary summary = processor.processLogFile("sample.log");
            
            // Display results
            displayResults(summary);
            
            // Generate and save report
            processor.generateReport(summary, "log_report.txt");
            System.out.println("\nReport saved to log_report.txt");
            
        } catch (Exception e) {
            log.error("Error processing log file", e);
            System.err.println("Error processing log file: " + e.getMessage());
        }
    }
    
    /**
     * Display processing results in a formatted manner
     */
    private static void displayResults(LogSummary summary) {
        System.out.println("\n--- Log Processing Results ---");
        System.out.println("Total entries: " + summary.getTotalEntries());
        System.out.println("Error count: " + summary.getErrorCount());
        System.out.println("Warning count: " + summary.getWarningCount());
        System.out.println("Info count: " + summary.getInfoCount());
        
        // Display entries by date
        System.out.println("\nEntries by date:");
        summary.getEntriesByDate().entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                LocalDate date = entry.getKey();
                List<LogEntry> entries = entry.getValue();
                System.out.println(date + ": " + entries.size() + " entries");
                
                // Show breakdown by level for each date
                Map<LogLevel, Long> levelCounts = entries.stream()
                    .collect(Collectors.groupingBy(LogEntry::getLevel, Collectors.counting()));
                
                levelCounts.forEach((level, count) -> 
                    System.out.println("  " + level + ": " + count));
            });
        
        // Display most common error messages
        System.out.println("\nMost common error messages:");
        summary.getMostCommonErrors().entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> {
                String message = entry.getKey();
                Long count = entry.getValue();
                System.out.println("  " + message + " (occurred " + count + " times)");
            });
        
        // Display recent entries
        System.out.println("\nMost recent entries:");
        summary.getRecentEntries().forEach(entry -> 
            System.out.println("  " + entry.getFormattedEntry()));
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
            "2023-12-02 09:35:40 WARN High CPU usage detected",
            "2023-12-03 08:45:12 INFO System startup completed",
            "2023-12-03 08:46:33 ERROR Service unavailable",
            "2023-12-03 08:47:15 WARN Connection pool exhausted",
            "2023-12-03 08:48:45 INFO Cache warmed up",
            "2023-12-03 08:49:20 ERROR Database connection failed",
            "invalid log line without proper format",
            "2023-12-03 08:50:00 DEBUG Debug message should be ignored",
            "2023-12-03 08:51:30 INFO Final processing complete"
        );
        
        try {
            Files.write(Paths.get("sample.log"), sampleLogs);
            System.out.println("Sample log file created: sample.log");
        } catch (IOException e) {
            log.error("Failed to create sample log file", e);
            System.err.println("Failed to create sample log file: " + e.getMessage());
        }
    }
    
    /**
     * Enum representing log levels
     */
    public enum LogLevel {
        INFO, WARN, ERROR, DEBUG;
        
        public static Optional<LogLevel> fromString(String level) {
            try {
                return Optional.of(LogLevel.valueOf(level.toUpperCase()));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
    }
    
    /**
     * LogEntry represents a single log entry
     * 
     * Uses Lombok to reduce boilerplate code
     */
    @Data
    @AllArgsConstructor
    public static class LogEntry {
        private LocalDateTime timestamp;
        private LogLevel level;
        private String message;
        
        /**
         * Parse a log line into LogEntry
         * 
         * @param line log line in format: "yyyy-MM-dd HH:mm:ss LEVEL message"
         * @return Optional<LogEntry> - empty if parsing fails
         */
        public static Optional<LogEntry> parse(String line) {
            if (line == null || line.trim().isEmpty()) {
                return Optional.empty();
            }
            
            // Split into components: date, time, level, message
            String[] parts = line.trim().split("\\s+", 4);
            
            if (parts.length < 4) {
                log.debug("Invalid log line format: {}", line);
                return Optional.empty();
            }
            
            try {
                // Parse date and time
                String dateTimeStr = parts[0] + " " + parts[1];
                LocalDateTime timestamp = LocalDateTime.parse(dateTimeStr, 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                
                // Parse log level
                Optional<LogLevel> level = LogLevel.fromString(parts[2]);
                if (level.isEmpty()) {
                    log.debug("Unknown log level: {}", parts[2]);
                    return Optional.empty();
                }
                
                // Extract message
                String message = parts[3];
                
                return Optional.of(new LogEntry(timestamp, level.get(), message));
                
            } catch (DateTimeParseException e) {
                log.debug("Failed to parse timestamp in line: {}", line, e);
                return Optional.empty();
            }
        }
        
        /**
         * Get the date component of the timestamp
         */
        public LocalDate getDate() {
            return timestamp.toLocalDate();
        }
        
        /**
         * Get formatted entry for display
         */
        public String getFormattedEntry() {
            return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + 
                   " " + level + " " + message;
        }
        
        /**
         * Check if this is an error entry
         */
        public boolean isError() {
            return level == LogLevel.ERROR;
        }
        
        /**
         * Check if this is a warning entry
         */
        public boolean isWarning() {
            return level == LogLevel.WARN;
        }
        
        /**
         * Check if this is an info entry
         */
        public boolean isInfo() {
            return level == LogLevel.INFO;
        }
    }
    
    /**
     * LogSummary contains aggregated statistics about log entries
     * 
     * Uses Lombok to reduce boilerplate code
     */
    @Data
    @RequiredArgsConstructor
    public static class LogSummary {
        private final List<LogEntry> allEntries;
        
        // Cached calculations
        private Map<LocalDate, List<LogEntry>> entriesByDate;
        private Map<String, Long> mostCommonErrors;
        private List<LogEntry> recentEntries;
        
        /**
         * Get total number of entries
         */
        public int getTotalEntries() {
            return allEntries.size();
        }
        
        /**
         * Get count of error entries
         */
        public long getErrorCount() {
            return allEntries.stream()
                .filter(LogEntry::isError)
                .count();
        }
        
        /**
         * Get count of warning entries
         */
        public long getWarningCount() {
            return allEntries.stream()
                .filter(LogEntry::isWarning)
                .count();
        }
        
        /**
         * Get count of info entries
         */
        public long getInfoCount() {
            return allEntries.stream()
                .filter(LogEntry::isInfo)
                .count();
        }
        
        /**
         * Get entries grouped by date
         */
        public Map<LocalDate, List<LogEntry>> getEntriesByDate() {
            if (entriesByDate == null) {
                entriesByDate = allEntries.stream()
                    .collect(Collectors.groupingBy(LogEntry::getDate));
            }
            return entriesByDate;
        }
        
        /**
         * Get most common error messages
         */
        public Map<String, Long> getMostCommonErrors() {
            if (mostCommonErrors == null) {
                mostCommonErrors = allEntries.stream()
                    .filter(LogEntry::isError)
                    .collect(Collectors.groupingBy(LogEntry::getMessage, Collectors.counting()));
            }
            return mostCommonErrors;
        }
        
        /**
         * Get most recent entries (last 5)
         */
        public List<LogEntry> getRecentEntries() {
            if (recentEntries == null) {
                recentEntries = allEntries.stream()
                    .sorted(Comparator.comparing(LogEntry::getTimestamp).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
            }
            return recentEntries;
        }
        
        /**
         * Generate summary statistics text
         */
        public String getSummaryText() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== LOG PROCESSING SUMMARY ===\n");
            sb.append("Total entries: ").append(getTotalEntries()).append("\n");
            sb.append("Error count: ").append(getErrorCount()).append("\n");
            sb.append("Warning count: ").append(getWarningCount()).append("\n");
            sb.append("Info count: ").append(getInfoCount()).append("\n\n");
            
            sb.append("Entries by date:\n");
            getEntriesByDate().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    LocalDate date = entry.getKey();
                    List<LogEntry> entries = entry.getValue();
                    sb.append(date).append(": ").append(entries.size()).append(" entries\n");
                });
            
            sb.append("\nMost common error messages:\n");
            getMostCommonErrors().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> {
                    String message = entry.getKey();
                    Long count = entry.getValue();
                    sb.append(message).append(" (occurred ").append(count).append(" times)\n");
                });
            
            return sb.toString();
        }
    }
    
    /**
     * FileProcessor handles the main file processing logic
     */
    @Slf4j
    public static class FileProcessor {
        
        /**
         * Process a log file and return summary statistics
         * 
         * @param filename the log file to process
         * @return LogSummary with aggregated statistics
         * @throws IOException if file cannot be read
         */
        public LogSummary processLogFile(String filename) throws IOException {
            Path filePath = Paths.get(filename);
            
            if (!Files.exists(filePath)) {
                throw new IOException("Log file does not exist: " + filename);
            }
            
            log.info("Processing log file: {}", filename);
            
            // Read and process file using try-with-resources
            try (Stream<String> lines = Files.lines(filePath)) {
                List<LogEntry> entries = lines
                    .filter(this::isValidLogLine)
                    .map(LogEntry::parse)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
                
                log.info("Successfully processed {} log entries", entries.size());
                
                return new LogSummary(entries);
            }
        }
        
        /**
         * Process log file with parallel processing for better performance
         * 
         * @param filename the log file to process
         * @return LogSummary with aggregated statistics
         * @throws IOException if file cannot be read
         */
        public LogSummary processLogFileParallel(String filename) throws IOException {
            Path filePath = Paths.get(filename);
            
            if (!Files.exists(filePath)) {
                throw new IOException("Log file does not exist: " + filename);
            }
            
            log.info("Processing log file with parallel processing: {}", filename);
            
            // Read all lines first, then process in parallel
            List<String> allLines = Files.readAllLines(filePath);
            
            List<LogEntry> entries = allLines.parallelStream()
                .filter(this::isValidLogLine)
                .map(LogEntry::parse)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
            
            log.info("Successfully processed {} log entries", entries.size());
            
            return new LogSummary(entries);
        }
        
        /**
         * Generate a text report from log summary
         * 
         * @param summary the log summary to report on
         * @param outputFile the file to write report to
         * @throws IOException if file cannot be written
         */
        public void generateReport(LogSummary summary, String outputFile) throws IOException {
            String reportContent = generateReportContent(summary);
            
            Path outputPath = Paths.get(outputFile);
            Files.write(outputPath, reportContent.getBytes());
            
            log.info("Report generated: {}", outputFile);
        }
        
        /**
         * Generate detailed report content
         */
        private String generateReportContent(LogSummary summary) {
            StringBuilder sb = new StringBuilder();
            
            sb.append("LOG FILE PROCESSING REPORT\n");
            sb.append("Generated: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
            sb.append("=".repeat(50)).append("\n\n");
            
            // Summary statistics
            sb.append(summary.getSummaryText()).append("\n");
            
            // Detailed breakdown by date
            sb.append("DETAILED BREAKDOWN BY DATE:\n");
            sb.append("-".repeat(30)).append("\n");
            
            summary.getEntriesByDate().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    LocalDate date = entry.getKey();
                    List<LogEntry> entries = entry.getValue();
                    
                    sb.append("\n").append(date).append(" (").append(entries.size()).append(" entries):\n");
                    
                    // Count by level
                    Map<LogLevel, Long> levelCounts = entries.stream()
                        .collect(Collectors.groupingBy(LogEntry::getLevel, Collectors.counting()));
                    
                    levelCounts.forEach((level, count) -> 
                        sb.append("  ").append(level).append(": ").append(count).append("\n"));
                });
            
            // All error messages
            sb.append("\nALL ERROR MESSAGES:\n");
            sb.append("-".repeat(20)).append("\n");
            
            summary.getAllEntries().stream()
                .filter(LogEntry::isError)
                .forEach(entry -> 
                    sb.append(entry.getFormattedEntry()).append("\n"));
            
            // Recent entries
            sb.append("\nRECENT ENTRIES (last 10):\n");
            sb.append("-".repeat(25)).append("\n");
            
            summary.getAllEntries().stream()
                .sorted(Comparator.comparing(LogEntry::getTimestamp).reversed())
                .limit(10)
                .forEach(entry -> 
                    sb.append(entry.getFormattedEntry()).append("\n"));
            
            return sb.toString();
        }
        
        /**
         * Validate if a log line has correct format
         * 
         * @param line the log line to validate
         * @return true if valid format
         */
        private boolean isValidLogLine(String line) {
            if (line == null || line.trim().isEmpty()) {
                return false;
            }
            
            // Basic format check: should have at least 4 parts when split by whitespace
            String[] parts = line.trim().split("\\s+");
            
            if (parts.length < 4) {
                return false;
            }
            
            // Check if third part is a valid log level
            LogLevel.LogLevel level = LogLevel.fromString(parts[2]);
            return level.isPresent();
        }
        
        /**
         * Get file statistics
         */
        public Map<String, Object> getFileStats(String filename) throws IOException {
            Path filePath = Paths.get(filename);
            
            if (!Files.exists(filePath)) {
                throw new IOException("File does not exist: " + filename);
            }
            
            Map<String, Object> stats = new HashMap<>();
            
            try (Stream<String> lines = Files.lines(filePath)) {
                List<String> allLines = lines.collect(Collectors.toList());
                
                stats.put("totalLines", allLines.size());
                stats.put("emptyLines", allLines.stream().filter(String::isEmpty).count());
                stats.put("validLogLines", allLines.stream().filter(this::isValidLogLine).count());
                stats.put("fileSize", Files.size(filePath));
                stats.put("lastModified", Files.getLastModifiedTime(filePath));
            }
            
            return stats;
        }
    }
}

/*
SOLUTION EXPLANATION:

1. FILE I/O OPERATIONS:
   - Uses Files.lines() with try-with-resources for automatic resource management
   - Handles IOException properly with meaningful error messages
   - Provides both sequential and parallel processing options

2. STREAM PROCESSING:
   - Uses functional programming approach with method chaining
   - Filters invalid entries gracefully
   - Maps strings to structured objects
   - Collects results into appropriate data structures

3. PARSING AND VALIDATION:
   - Robust parsing with proper error handling
   - Uses Optional to handle parsing failures gracefully
   - Validates log format before processing
   - Provides clear error messages and logging

4. COLLECTION OPERATIONS:
   - Groups entries by date using Collectors.groupingBy()
   - Counts occurrences using Collectors.counting()
   - Sorts and limits results for top N queries
   - Uses appropriate collection types for different use cases

5. LOMBOK USAGE:
   - @Data for automatic getters, setters, equals, hashCode, toString
   - @AllArgsConstructor for constructor generation
   - @RequiredArgsConstructor for final fields
   - @Slf4j for logging

6. EXCEPTION HANDLING:
   - Proper exception propagation with meaningful messages
   - Graceful degradation with Optional usage
   - Resource cleanup with try-with-resources

7. PERFORMANCE CONSIDERATIONS:
   - Lazy evaluation with caching in LogSummary
   - Parallel processing option for large files
   - Memory efficient streaming for large datasets
   - Appropriate use of collectors for aggregation

8. LOGGING AND MONITORING:
   - Comprehensive logging for debugging
   - Progress tracking for long-running operations
   - Clear status messages for user feedback

This solution demonstrates enterprise-grade Java development practices
suitable for production environments.
*/