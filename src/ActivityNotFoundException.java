/**
 * Custom exception for when we cant find a specific activity
 * Thrown when users try to access, update, or delete activities that dont exist
 * Helps provide meaningfull error messages instead of generic exceptions
 */
public class ActivityNotFoundException extends Exception {

    /**
     * Constructor that creates exception with a custom error message
     * Message should explain which activity couldnt be found and why
     * Helps users understand what went wrong with there request
     */
    public ActivityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor for wrapping another exception that caused this problem
     * Usefull when the search fails due to some other underlying issue
     * Preservs the original error information for better troubleshooting
     */
    public ActivityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
