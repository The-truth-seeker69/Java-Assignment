package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Audit Logger Utility
 * Provides centralized logging functionality for system audit trail.
 * All user activities and system events are logged with timestamps
 * to support maintenance and audit requirements.
 */
public class AuditLogger {

    private static final String LOG_FILE = "system_audit.log";
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);

    /**
     * Logs a message to the audit log file with timestamp.
     * Thread-safe implementation using synchronized block.
     * 
     * @param message The message to log (e.g., "System started", "User logged in")
     */
    public static synchronized void log(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
                PrintWriter pw = new PrintWriter(fw)) {

            String timestamp = dateFormatter.format(new Date());
            String logEntry = timestamp + " " + message;
            pw.println(logEntry);

        } catch (IOException e) {
            System.err.println("Error writing to audit log: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Logs a formatted message with parameters.
     * Useful for logging operations with dynamic data.
     * 
     * @param format The format string (like String.format)
     * @param args   The arguments to format
     */
    public static void logf(String format, Object... args) {
        log(String.format(format, args));
    }
}
