import java.time.LocalDate;

/**
 * Meditation activity class - tracks meditation sessions and there quality
 * Extends WellnessActivity and implements Trackable interface
 * Helps users monitor mindfulness practice and mediation progress
 */
public class Meditation_sec33_gr3 extends WellnessActivity_sec33_gr3 implements Trackable_sec33_gr3 {

    // Meditation fields for tracking session details
    private String meditationType; // guided, unguided, breathing - what kind of meditation , you can write anythingyou want
    private String focusArea; // "stress", "anxiety", "focus", "general" - what they focused on , same
    private int distractionCount; // how many times they got distracted
    private boolean completedSession; // did they finish the whole session

    /**
     * Constructor for creating a new meditation activity
     * Takes all the importent meditation details like type and focus area
     * Distraction count helps track how well the session went
     */
    public Meditation_sec33_gr3(LocalDate date, double duration, String notes, String meditationType,
                      String focusArea, int distractionCount, boolean completedSession) {
        super(date, duration, notes);
        this.meditationType = meditationType;
        this.focusArea = focusArea;
        this.distractionCount = distractionCount;
        this.completedSession = completedSession;
    }

    // Getters and Setters section - for accessing and modifying meditation data

    /**
     * Gets the type of meditation that was done
     * Could be guided, unguided, breathing exercises, etc.
     */
    public String getMeditationType() { return meditationType; }
    
    /**
     * Sets new meditation type
     * Usefull when user wants to correct what type they actually did
     */
    public void setMeditationType(String meditationType) { this.meditationType = meditationType; }

    /**
     * Gets what area the user focused on during meditation
     * Like stress relief, anxiety management, or general mindfulness
     */
    public String getFocusArea() { return focusArea; }
    
    /**
     * Updates the focus area for this meditation session
     * Sometimes users realize they focused on something diffrent than planned
     */
    public void setFocusArea(String focusArea) { this.focusArea = focusArea; }

    /**
     * Gets how many times user got distracted during session
     * Lower numbers mean better focus and concentration
     */
    public int getDistractionCount() { return distractionCount; }
    
    /**
     * Sets new distraction count with validation
     * Makes sure count never goes below zero because negative distractions dont make sense
     */
    public void setDistractionCount(int distractionCount) {
        this.distractionCount = Math.max(0, distractionCount);
    }

    /**
     * Checks if user completed the full meditation session
     * Returns true if they finished, false if they quit early
     */
    public boolean isCompletedSession() { return completedSession; }
    
    /**
     * Sets whether the session was completed or not
     * Good for when users want to update there records later
     */
    public void setCompletedSession(boolean completedSession) { this.completedSession = completedSession; }

    /**
     * Sets new notes for the activity
     * Useful for adding or updating session notes later
     */
    public void setNotes(String notes) { super.setNotes(notes); }

    /**
     * Sets a new duration for the activity
     * Includes validation to ensure the duration is realistic
     */
    public void setDuration(double duration) throws InvalidActivityDurationException_sec33_gr_3 { super.setDuration(duration); }

    // Abstract method implementations - required by parent WellnessActivity class

    /**
     * Returns the activity type as a string
     * Always returns "Meditation" for this class, obviously
     */
    @Override
    public String getActivityType() { return "Meditation"; }

    /**
     * Calculates overall meditation progress score
     * Completed sessions get higher base score than incomplete ones
     * Longer duration gives bonus points, distractions reduce the score
     * took from the internet
     */
    @Override
    public double calculateProgress() {
        double baseScore = completedSession ? 70 : 40; // completion is most importent
        double durationBonus = Math.min(30, getDuration()); // longer = better, but caps at 30
        double distractionPenalty = Math.min(20, distractionCount * 5); // each distraction hurts
        return Math.max(0, baseScore + durationBonus - distractionPenalty); // never go below 0
    }

    /**
     * Checks if meditation duration makes sense
     * Should be between 1 minute and 3 hours - anything else is probaly wrong
     * Most people dont meditate for more than 3 hours anyway
     */
    @Override
    public boolean isValidDuration(double duration) {
        return duration >= 1 && duration <= 180; // 1 minute to 3 hours in minutes
    }

    // Trackable interface implementations - required for the tracking system

    /**
     * Returns a formated progress string for display
     * Shows meditation score, duration, completion status, and distractions
     * Easy to read format that gives users all the importent info at a glance
     */
    @Override
    public String getProgress() {
        return String.format("Meditation Score: %.1f%% (%.01f min, %s, %d distractions)",
                calculateProgress(), getDuration(), completedSession ? "Completed" : "Incomplete", distractionCount);
    }

    /**
     * Checks if this meditation session meets a wellness goal
     * Currently only supports meditation minutes goals
     * Returns true if session duration meets or exceeds the goal target
     */
    @Override
    public boolean meetsGoal(WellnessGoal_sec33_gr3 goal) {
        if (goal.getGoalType() == GoalType_sec33_gr3.MEDITATION_MINUTES) {
            return getDuration() >= goal.getTargetValue();
        }
        return false; // wrong goal type or doesnt meet target
    }

    /**
     * Creates a nice string representation of the meditation session
     * Includes all the importent details like type, focus, completion, and distractions
     * Used when displaying meditation activities to users in lists and reports
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Type: %s | Focus: %s | Completed: %s | Distractions: %d",
                meditationType, focusArea, completedSession ? "Yes" : "No", distractionCount);
    }
}