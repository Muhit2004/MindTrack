/**
 * Custom exception for when we cant find a specific wellness goal
 * Thrown when users try to access, update, or delete goals that dont exist
 * Helps provide clear error messages when goal operations fail
 */
public class GoalNotFoundException extends Exception {

    /**
     * Constructor that creates exception with a custom error message
     * Message should explain which goal couldnt be found and why
     * Helps users understand what went wrong with there goal request
     */
    public GoalNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor for wrapping another exception that caused this problem
     * Usefull when the goal search fails due to some other underlying issue
     * Preservs the original error information for better debugging
     */
    public GoalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}