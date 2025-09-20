import java.time.LocalDate;

/**
 * MoodStressEntry class - tracks daily mood and stress levels for users
 * Helps monitor emotional wellbeing and identify patterns over time
 * Important for understanding how activities affect there mental state
 */
public class MoodStressEntry {

    // Fields for tracking mood and stress data
    private LocalDate date; // when this mood/stress reading was taken
    private int moodLevel; // 1-10 scale - how good user felt
    private int stressLevel; // 1-10 scale - how stressed user felt
    private String moodTags; // comma-separated tags like "happy,calm,energetic"
    private String stressTriggers; // what caused the stress like "work,traffic,family"
    private String notes; // user's personal notes about there emotional state

    /**
     * Constructor for creating a new mood/stress entry
     * Validates mood and stress values to make sure they're in valid range
     * Tags and notes are optional but can provide usefull context
     */
    public MoodStressEntry(LocalDate date, int moodLevel, int stressLevel,
                          String moodTags, String stressTriggers, String notes) {
        if (moodLevel < 1 || moodLevel > 10) {
            throw new InvalidMoodStressValueException("Mood level must be between 1 and 10");
        }
        if (stressLevel < 1 || stressLevel > 10) {
            throw new InvalidMoodStressValueException("Stress level must be between 1 and 10");
        }

        this.date = date;
        this.moodLevel = moodLevel;
        this.stressLevel = stressLevel;
        this.moodTags = moodTags != null ? moodTags : "";
        this.stressTriggers = stressTriggers != null ? stressTriggers : "";
        this.notes = notes != null ? notes : "";
    }

    // Getters for accessing mood and stress data

    /**
     * Gets the date of this mood/stress reading
     * Important for tracking patterns and trends over time
     */
    public LocalDate getDate() { return date; }

    /**
     * Gets the mood level from 1-10
     * Higher numbers mean better mood, 1 = terrible, 10 = amazing
     */
    public int getMoodLevel() { return moodLevel; }

    /**
     * Gets the stress level from 1-10
     * Higher numbers mean more stress, 1 = very relaxed, 10 = extremely stressed
     */
    public int getStressLevel() { return stressLevel; }

    /**
     * Gets the mood tags that describe how user was feeling
     * Usually comma-separated words like "happy,excited,confident"
     */
    public String getMoodTags() { return moodTags; }

    /**
     * Gets what triggered the user's stress
     * Helps identify patterns and things that consistently cause problems
     */
    public String getStressTriggers() { return stressTriggers; }

    /**
     * Gets additional notes about the user's emotional state
     * Free-form text where users can explain there feelings in detail
     */
    public String getNotes() { return notes; }

    // Setters for modifying mood/stress data with validation

    /**
     * Sets new mood level with validation
     * Must be between 1-10 or throws exception
     */
    public void setMoodLevel(int moodLevel) {
        if (moodLevel < 1 || moodLevel > 10) {
            throw new InvalidMoodStressValueException("Mood level must be between 1 and 10");
        }
        this.moodLevel = moodLevel;
    }

    /**
     * Sets new stress level with validation
     * Must be between 1-10 or throws exception
     */
    public void setStressLevel(int stressLevel) {
        if (stressLevel < 1 || stressLevel > 10) {
            throw new InvalidMoodStressValueException("Stress level must be between 1 and 10");
        }
        this.stressLevel = stressLevel;
    }

    /**
     * Updates mood tags for this entry
     * Tags help categorize and search through emotional states later
     */
    public void setMoodTags(String moodTags) {
        this.moodTags = moodTags != null ? moodTags : "";
    }

    /**
     * Updates stress triggers for this entry
     * Identifying triggers is the first step to managing stress better
     */
    public void setStressTriggers(String stressTriggers) {
        this.stressTriggers = stressTriggers != null ? stressTriggers : "";
    }

    /**
     * Updates notes for this entry
     * More detailed notes help users remember what was happening
     */
    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }

    // Analysis methods for calculating wellness metrics

    /**
     * Calculates overall wellness score based on mood and stress
     * Higher mood and lower stress = better wellness score
     * Returns value between 0-100 for easy understanding
     */
    public double getWellnessScore() {
        // High mood is good, high stress is bad
        double moodContribution = (moodLevel / 10.0) * 60; // up to 60 points from mood
        double stressContribution = ((10 - stressLevel) / 10.0) * 40; // up to 40 points from low stress
        return moodContribution + stressContribution;
    }

    /**
     * Checks if this was a particularly good day emotionally
     * Returns true if mood is high and stress is low
     */
    public boolean isGoodDay() {
        return moodLevel >= 7 && stressLevel <= 4; // arbitrary but reasonable thresholds
    }

    /**
     * Checks if this was a particularly stressfull day
     * Returns true if stress is very high, regardless of mood
     */
    public boolean isStressfulDay() {
        return stressLevel >= 8; // high stress threshold
    }

    /**
     * Creates a readable string representation of the mood/stress entry
     * Shows all the importent info in a compact format
     * Usefull for displaying entries in lists and reports
     */
    @Override
    public String toString() {
        return String.format("Date: %s | Mood: %d/10 | Stress: %d/10 | Wellness: %.1f%% | Tags: %s | Triggers: %s%s",
                date, moodLevel, stressLevel, getWellnessScore(),
                moodTags.isEmpty() ? "None" : moodTags,
                stressTriggers.isEmpty() ? "None" : stressTriggers,
                notes.isEmpty() ? "" : " | Notes: " + notes);
    }
}