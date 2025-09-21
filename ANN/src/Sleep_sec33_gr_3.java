import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Sleep activity class - tracks sleeping patterns and quality
 * Extends WellnessActivity and implements Trackable interface
 * Helps users monitor there sleep habits and improve them
 */
public class Sleep_sec33_gr_3 extends WellnessActivity_sec33_gr3 implements Trackable_sec33_gr3 {

    // Sleep fields for tracking sleep quality and timing
    private int quality; // 1-10 scale - how good was the sleep
    private LocalTime bedtime; // when user went to bed
    private LocalTime wakeTime; // when user woke up
    private boolean hadNightmares; // did they have bad dreams

    /**
     * Constructor for creating a new sleep activity
     * Takes all the importent sleep details and stores them
     * Quality gets clamped between 1-10 automaticaly
     */
    public Sleep_sec33_gr_3(LocalDate date, double duration, String notes, int quality,
                 LocalTime bedtime, LocalTime wakeTime, boolean hadNightmares) {
        super(date, duration, notes);
        this.quality = Math.min(Math.max(quality, 1), 10); // keeps quality in valid range
        this.bedtime = bedtime;
        this.wakeTime = wakeTime;
        this.hadNightmares = hadNightmares;
    }

    /**
     * Gets the sleep quality rating
     * Returns a number between 1-10 showing how good the sleep was
     */
    public int getQuality() { return quality; }

    /**
     * Sets new sleep quality rating
     * Automatically clamps the value so it stays beetween 1-10
     */
    public void setQuality(int quality) {
        this.quality = Math.min(Math.max(quality, 1), 10);
    }

    /**
     * Gets the bedtime when user went to sleep
     * Usefull for tracking sleep patterns and routines
     */
    public LocalTime getBedtime() { return bedtime; }

    /**
     * Sets a new bedtime for this sleep activity
     * Helps when user wants to correct there sleep records
     */
    public void setBedtime(LocalTime bedtime) { this.bedtime = bedtime; }

    /**
     * Gets the wake up time
     * Shows when the user woke up from sleep
     */
    public LocalTime getWakeTime() { return wakeTime; }

    /**
     * Sets new wake up time
     * Good for updating records if user remembers the correct time later
     */
    public void setWakeTime(LocalTime wakeTime) { this.wakeTime = wakeTime; }

    /**
     * Checks if user had nightmares during sleep
     * Returns true if they had bad dreams, false if sleep was peacefull
     */
    public boolean hadNightmares() { return hadNightmares; }

    /**
     * Updates whether user had nightmares or not
     * Sometimes users might want to change this after thinking about it
     */
    public void setHadNightmares(boolean hadNightmares) { this.hadNightmares = hadNightmares; }

    // Abstract method implementations - required by parent class

    /**
     * Returns the activity type as a string
     * Always returns "Sleep" for this class
     */
    @Override
    public String getActivityType() { return "Sleep"; }

    /**
     * Calculates overall sleep progress score
     * Combines duration score and quality score to give final rating
     * Closer to 8 hours = better score, plus quality affects it too
     * a Calculation i fround from online
     */
    @Override
    public double calculateProgress() {
        double hoursSlept = getDuration() / 60.0; // convert minutes to hours
        double optimalHours = 8.0; // ideal sleep duration
        double timeScore = Math.max(0, 100 - Math.abs(hoursSlept - optimalHours) * 12.5);
        double qualityScore = quality * 10; // convert 1-10 to 10-100 scale
        return (timeScore + qualityScore) / 2; // average of both scores
    }

    /**
     * Checks if the sleep duration makes sense
     * Should be between 1-12 hours, anything else is probably wrong
     */
    @Override
    public boolean isValidDuration(double duration) {
        return duration >= 60 && duration <= 720; // 1-12 hours in minutes
    }


    /**
     * Returns a formated progress string for display
     * Shows sleep score, duration in hours, and quality rating
     * Easy to read format that users can understand quickly
     */
    @Override
    public String getProgress() {
        return String.format("Sleep Score: %.1f%% (Duration: %.1fh, Quality: %d/10)",
                calculateProgress(), getDuration() / 60.0, quality);
    }

    /**
     * Checks if this sleep activity meets a wellness goal
     * Currently only supports sleep hours goals
     * Returns true if sleep duration meets or exceeds the goal target
     */
    @Override
    public boolean meetsGoal(WellnessGoal_sec33_gr3 goal) {
        if (goal.getGoalType() == GoalType_sec33_gr3.SLEEP_HOURS) {
            return (getDuration() / 60.0) >= goal.getTargetValue();
        }
        return false; // doesn't meet goal or wrong goal type
    }

    /**
     * Creates a nice string representation of the sleep activity
     * Includes all the importent details in a readable format
     * Used when displaying sleep activities to the user
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Quality: %d/10 | %s-%s | Nightmares: %s",
                quality, bedtime, wakeTime, hadNightmares ? "Yes" : "No");
    }
}