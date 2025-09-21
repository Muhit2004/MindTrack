import java.time.LocalDate;

/**
 * WellnessGoal class - represents a wellness goal that users want to achieve
 * Tracks progress towards targets like sleep hours, meditation minutes, etc.
 * Helps users stay motivated and measure there improvement over time
 */
public class WellnessGoal_sec33_gr3 {

    // Goal data fields for tracking progress and targets
    private String goalType; // what kind of goal this is (from GoalType_sec33_gr3 constants)
    private double targetValue; // the target they want to reach
    private double currentProgress; // how much progress they've made so far
    private LocalDate startDate; // when they started working on this goal
    private LocalDate endDate; // when they want to achieve it by
    private String description; // user's personal description of the goal

    /**
     * Constructor for creating a new wellness goal
     * Takes all the importent goal details and sets initial progress to zero
     * Goal type should match one of the valid types from GoalType class
     */
    public WellnessGoal_sec33_gr3(String goalType, double targetValue, LocalDate startDate,
                       LocalDate endDate, String description) {
        if (!GoalType_sec33_gr3.isValidGoalType(goalType)) {
            throw new IllegalArgumentException("Invalid goal type: " + goalType);
        }
        if (targetValue <= 0) {
            throw new IllegalArgumentException("Target value must be positive");
        }

        this.goalType = goalType;
        this.targetValue = targetValue;
        this.currentProgress = 0.0; // starts at zero obviously
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description != null ? description : "";
    }

    // Getters for accessing goal information

    /**
     * Gets the type of goal this is
     * Should match one of the valid goal types from GoalType_sec33_gr3 class
     */
    public String getGoalType() { return goalType; }

    /**
     * Gets the target value user wants to achieve
     * Higher numbers are usually better except for screen time goals
     */
    public double getTargetValue() { return targetValue; }

    /**
     * Gets current progress towards the goal
     * Shows how much they've accomplished so far
     */
    public double getCurrentProgress() { return currentProgress; }

    /**
     * Gets when the user started working on this goal
     * Usefull for calculating how long they've been trying
     */
    public LocalDate getStartDate() { return startDate; }

    /**
     * Gets the target completion date for this goal
     * Not all goals have end dates - some are ongoing
     */
    public LocalDate getEndDate() { return endDate; }

    /**
     * Gets the user's description of what this goal means to them
     * Personal descriptions help users stay motivated and remember why its importent
     */
    public String getDescription() { return description; }

    // Setters for modifying goal data

    /**
     * Sets new target value with validation
     * Target must be positive because negative goals dont make sense
     */
    public void setTargetValue(double targetValue) {
        if (targetValue <= 0) {
            throw new IllegalArgumentException("Target value must be positive");
        }
        this.targetValue = targetValue;
    }

    /**
     * Updates current progress towards the goal
     * Progress can be higher than target if user exceeds there goal
     */
    public void setCurrentProgress(double currentProgress) {
        this.currentProgress = Math.max(0, currentProgress); // never negative
    }

    /**
     * Sets new description for this goal
     * Helps users update there motivation or clarify what they want to achieve
     */
    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    // Goal tracking and progress methods

    /**
     * Checks if the goal has been completed
     * Returns true if current progress meets or exceeds the target
     * Screen time goals work backwards - lower is better for those
     */
    public boolean isCompleted() {
        if (goalType.equals(GoalType_sec33_gr3.SCREEN_TIME_LIMIT)) {
            return currentProgress <= targetValue; // opposite for screen time
        }
        return currentProgress >= targetValue; // normal goals
    }

    /**
     * Calculates what percentage of the goal has been achieved
     * Returns 0-100+ percentage, can go over 100 if they exceed there target
     */
    public double getProgressPercentage() {
        if (targetValue == 0) return 0;
        return (currentProgress / targetValue) * 100;
    }

    /**
     * Updates progress based on a new activity
     * Different goal types track progress diferently
     * This is where the magic happens for automatic progress tracking
     */
    public void updateProgressFromActivity(WellnessActivity_sec33_gr3 activity) {
        if (activity == null) return;

        // Each goal type updates progress diferently
        switch (goalType) {
            case GoalType_sec33_gr3.SLEEP_HOURS:
                if (activity instanceof Sleep_sec33_gr_3) {
                    currentProgress += activity.getDuration() / 60.0; // convert to hours
                }
                break;
            case GoalType_sec33_gr3.MEDITATION_MINUTES:
                if (activity instanceof Meditation_sec33_gr3) {
                    currentProgress += activity.getDuration();
                }
                break;
            case GoalType_sec33_gr3.JOURNAL_ENTRIES:
                if (activity instanceof Journaling_sec33_gr3) {
                    currentProgress += 1; // one entry = one point
                }
                break;
            case GoalType_sec33_gr3.SCREEN_TIME_LIMIT:
                if (activity instanceof ScreenTime_sec33_gr3) {
                    currentProgress += activity.getDuration(); // total screen time
                }
                break;
        }
    }

    /**
     * Creates a nice string representation of the goal
     * Shows goal type, progress, target, and completion status
     * Usefull for displaying goals in lists and reports
     */
    @Override
    public String toString() {
        return String.format("%s: %.1f/%.1f (%.1f%%) - %s | %s",
                goalType, currentProgress, targetValue, getProgressPercentage(),
                isCompleted() ? "✓ Completed" : "In Progress",
                description.isEmpty() ? "No description" : description);
    }
}