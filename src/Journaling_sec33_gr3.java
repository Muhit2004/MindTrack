import java.time.LocalDate;
import java.util.Objects;

/**
 * Journaling activity class - tracks writing sessions and mood changes
 * Extends WellnessActivity and implements Trackable interface
 * Helps users monitor there emotional wellbeing through reflective writing
 */
public class Journaling extends WellnessActivity_sec33_gr3 implements Trackable_sec33_gr3 {

    // Journaling-specific fields for tracking writing sessions and mood impact
    private int moodBefore; // 1-10 scale - how user felt before writing
    private int moodAfter; // 1-10 scale - how user felt after writing
    private int wordCount; // aproximate number of words written
    private String journalTheme; // "gratitude", "reflection", "goals", "free-writing" - what they wrote about
    private boolean feltBeneficial; // did the journaling session help them feel better

    /**
     * Constructor for creating a new journaling activity
     * Takes all the importent journaling details like mood changes and theme
     * Word count helps track how much effort they put into writing
     */
    public Journaling(LocalDate date, double duration, String notes, int moodBefore,
                      int moodAfter, int wordCount, String journalTheme, boolean feltBeneficial) {
        super(date, duration, notes);
        this.moodBefore = moodBefore;
        this.moodAfter = moodAfter;
        this.wordCount = wordCount;
        this.journalTheme = journalTheme;
        this.feltBeneficial = feltBeneficial;
    }

    // Getters and Setters section - for accessing and modifying journaling data

    /**
     * Gets the mood rating before journaling session
     * Shows how user was feeling before they started writing
     */
    public int getMoodBefore() { return moodBefore; }

    /**
     * Sets new mood before rating with validation
     * Makes sure mood stays beetween 1-10 because anything else doesnt make sense
     */
    public void setMoodBefore(int moodBefore) {
        if (moodBefore >= 1 && moodBefore <= 10) {
            this.moodBefore = moodBefore;
        } else {
            throw new IllegalArgumentException("Mood must be between 1 and 10");
        }
    }

    /**
     * Gets the mood rating after journaling session
     * Shows how user felt after finishing there writing
     */
    public int getMoodAfter() { return moodAfter; }

    /**
     * Sets new mood after rating with validation
     * Also checks that mood stays in valid range like the before mood
     */
    public void setMoodAfter(int moodAfter) {
        if (moodAfter >= 1 && moodAfter <= 10) {
            this.moodAfter = moodAfter;
        } else {
            throw new IllegalArgumentException("Mood must be between 1 and 10");
        }
    }

    /**
     * Gets aproximate word count for this journaling session
     * Higher word counts usually mean more thoughtfull writing
     */
    public int getWordCount() { return wordCount; }

    /**
     * Sets new word count, makes sure it never goes negative
     * Negative word counts would be wierd and confusing
     */
    public void setWordCount(int wordCount) { this.wordCount = Math.max(0, wordCount); }

    /**
     * Gets the theme or topic of the journaling session
     * Could be gratitude, reflection, goals, free-writing, etc.
     */
    public String getJournalTheme() { return journalTheme; }

    /**
     * Sets new journal theme for this session
     * Usefull when user wants to categorize there writing differently
     */
    public void setJournalTheme(String journalTheme) { this.journalTheme = journalTheme; }

    /**
     * Checks if user felt the journaling session was beneficial
     * Returns true if it helped them, false if it wasnt helpfull
     */
    public boolean feltBeneficial() { return feltBeneficial; }

    /**
     * Sets whether the session felt beneficial or not
     * Sometimes users change there mind about how helpfull it was
     */
    public void setFeltBeneficial(boolean feltBeneficial) { this.feltBeneficial = feltBeneficial; }

    // Abstract method implementations - required by parent WellnessActivity class

    /**
     * Returns the activity type as a string
     * Always returns "Journaling" for this class, duh
     */
    @Override
    public String getActivityType() { return "Journaling"; }

    /**
     * Calculates overall journaling progress score
     * Mood improvement is the most importent factor - bigger improvement = higher score
     * Word count gives bonus points, beneficial sessions get extra points too
     * Duration also matters because longer sessions show more commitment
     * took from the internet
     */
    @Override
    public double calculateProgress() {
        double moodImprovement = ((moodAfter - moodBefore) + 10) * 5; // Scale to 0-100
        double wordBonus = Math.min(20, wordCount / 25); // 1 point per 25 words, max 20
        double benefitBonus = feltBeneficial ? 20 : 0;
        double durationScore = Math.min(30, getDuration() * 2); // 2 points per minute, max 30
        return Math.max(0, Math.min(100, moodImprovement + wordBonus + benefitBonus + durationScore));
    }

    /**
     * Checks if journaling duration makes sense
     * Should be between 5 minutes and 2 hours - anything else is probaly wrong
     * Most people dont journal for more than 2 hours at a time anyway
     */
    @Override
    public boolean isValidDuration(double duration) {
        return duration >= 5 && duration <= 120; // 5 minutes to 2 hours
    }

    // Trackable interface implementations - required for the tracking system

    /**
     * Returns a formated progress string for display
     * Shows journaling score, mood change, word count, and benefit status
     * Easy to read format that shows all the importent metrics at once
     */
    @Override
    public String getProgress() {
        return String.format("Journaling Score: %.1f%% (Mood: %d→%d, %d words, %s)",
                calculateProgress(), moodBefore, moodAfter, wordCount,
                feltBeneficial ? "Beneficial" : "Not beneficial");
    }

    /**
     * Checks if this journaling session meets a wellness goal
     * Currently only supports mood improvement goals
     * Returns true if mood increased enough to meet the goal target
     */
    @Override
    public boolean meetsGoal(WellnessGoal_sec33_gr3 goal) {
        if (goal.getGoalType() == GoalType.MOOD_IMPROVEMENT) {
            return (moodAfter - moodBefore) >= goal.getTargetValue();
        }
        return false; // wrong goal type or doesnt meet target
    }

    /**
     * Creates a nice string representation of the journaling session
     * Includes all the importent details like theme, mood change, word count, and benefit
     * Used when displaying journaling activities to users in lists and reports
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Theme: %s | Mood: %d→%d | Words: %d | Beneficial: %s",
                journalTheme, moodBefore, moodAfter, wordCount, feltBeneficial ? "Yes" : "No");
    }
}