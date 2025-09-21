/**
 * Custom exception for when activity durations dont make sense
 * Thrown when users try to set unrealistic durations for activities
 * Each activity type has there own rules about what durations are valid
 */
public class InvalidActivityDurationException_sec33_gr_3 extends Exception {

    /**
     * Constructor that creates exception with a custom error message
     * Message should explain what went wrong and what the valid range is
     * Helps users understand why there input was rejected
     */
    public InvalidActivityDurationException_sec33_gr_3(String message) {
        super(message);
    }

    /**
     * Constructor for wrapping another exception that caused this problem
     * Usefull when the duration validation fails due to some other error
     * Preservs the original error information for debugging
     */
    public InvalidActivityDurationException_sec33_gr_3(String message, Throwable cause) {
        super(message, cause);
    }
 

}