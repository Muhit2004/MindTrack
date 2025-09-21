/**
 * Custom exception for when mood or stress values are out of valid range
 * Thrown when users enter mood/stress levels that arent between 1-10
 * Helps maintain data quality by preventing impossible values
 */
public class InvalidMoodStressValueException extends RuntimeException {

    /**
     * Constructor that creates exception with a custom error message
     * Message should explain what the valid range is (1-10 for both mood and stress)
     * Makes it clear to users what values are acceptible
     */
    public InvalidMoodStressValueException(String message) {
        super(message);
    }

    /**
     * Constructor for wrapping another exception that caused this problem
     * Usefull when the validation fails due to some other underlying issue
     * Preservs the original error details for better debugging
     */
    public InvalidMoodStressValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
