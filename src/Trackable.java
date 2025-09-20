import java.time.LocalDate;

/**
 * Interface for tracking wellness activites
 * All activities must implement these methods so we can track them properly
 * Makes it easier to work with diferent types of activities
 */
public interface Trackable {


    double getDuration();

    /**
     * Gets the date when this activity happened
     * Helpfull for sorting and filtering activities by time
     */
    LocalDate getDate();

    /**
     * Shows progress or score for this activity
     * Returns a nice formated string that users can understand
     */
    String getProgress();

    /**
     * Checks if this activity meets a specific wellness goal
     * Returns true if the activity helps achieve the goal, false otherwise
     * Very usefull for tracking goal completion
     */
    boolean meetsGoal(WellnessGoal goal);
}
