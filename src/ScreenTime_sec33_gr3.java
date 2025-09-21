import java.time.LocalDate;

/**
 * ScreenTime activity class - tracks digital device usage and there health effects
 * Extends WellnessActivity and implements Trackable interface
 * Helps users monitor screen time and reduce eye strain from to much device use
 */public class ScreenTime_sec33_gr3 extends WellnessActivity_sec33_gr3 implements Trackable_sec33_gr3 {

    // Screen time specific fields for tracking device usage and health impact
    private String deviceType; // "phone", "computer", "tablet", "tv" - what device they used
    private String purpose; // "work", "education", "entertainment", "social" - why they used it
    private int breaksTaken; // how many breaks they took during screen time
    private boolean causedEyeStrain; // did the screen time hurt there eyes
    private boolean usedBlueLightFilter; // did they use blue light protection



    /**
     * Constructor for creating a new screen time activity
     * Takes all the importent screen time details like device and purpose
     * Blue light filter is set to false by default but can be changed later
     */
    public ScreenTime_sec33_gr3(LocalDate date, double duration, String notes, String deviceType,
                      String purpose, int breaksTaken, boolean causedEyeStrain) {
        super(date, duration, notes);
        this.deviceType = deviceType;
        this.purpose = purpose;
        this.breaksTaken = breaksTaken;
        this.causedEyeStrain = causedEyeStrain;
        this.usedBlueLightFilter = false; // most people dont use this inicially
    }

    // Getters and Setters section - for accessing and modifying screen time data

    /**
     * Gets what type of device was used for screen time
     * Could be phone, computer, tablet, tv, or anything with a screen
     */
    public String getDeviceType() { return deviceType; }

    /**
     * Sets new device type for this screen time session
     * Usefull when user realizes they got the device wrong
     */
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }

    /**
     * Gets the purpose or reason for using the screen
     * Work and education are considered more productive than entertainment
     */
    public String getPurpose() { return purpose; }

    /**
     * Sets new purpose for the screen time session
     * Sometimes users want to recategorize there usage later
     */
    public void setPurpose(String purpose) { this.purpose = purpose; }

    /**
     * Gets how many breaks user took during screen time
     * More breaks = better for eye health and prevents strain
     */
    public int getBreaksTaken() { return breaksTaken; }

    /**
     * Sets new break count with validation
     * Makes sure breaks never go negative because that would be wierd
     */
    public void setBreaksTaken(int breaksTaken) { this.breaksTaken = Math.max(0, breaksTaken); }

    /**
     * Checks if the screen time caused eye strain or discomfort
     * Returns true if eyes hurt, false if they felt fine
     */
    public boolean causedEyeStrain() { return causedEyeStrain; }

    /**
     * Sets whether screen time caused eye strain
     * Important for tracking screen time health affects over time
     */
    public void setCausedEyeStrain(boolean causedEyeStrain) { this.causedEyeStrain = causedEyeStrain; }

    /**
     * Checks if user used blue light filter during screen time
     * Blue light filters can help reduce eye strain, especialy at night
     */
    public boolean usedBlueLightFilter() { return usedBlueLightFilter; }

    /**
     * Sets whether blue light filter was used
     * Good for tracking if protective measures help with eye strain
     */
    public void setUsedBlueLightFilter(boolean usedBlueLightFilter) { this.usedBlueLightFilter = usedBlueLightFilter; }

    /**
     * Sets new notes for the screen time activity
     * Useful for adding details or reminders about this session
     */
    public void setNotes(String notes) { super.setNotes(notes); }

    /**
     * Sets a new duration for the screen time activity
     * Duration must be valid according to the isValidDuration method
     */
    public void setDuration(double duration) throws InvalidActivityDurationException { super.setDuration(duration); }

    // Abstract method implementations - required by parent WellnessActivity class

    /**
     * Returns the activity type as a string
     * Always returns "ScreenTime" for this class, obviously
     */
    @Override
    public String getActivityType() { return "ScreenTime"; }

    /**
     * Calculates overall screen time wellness score
     * Lower screen time is generaly better, but breaks and purpose matter too
     * Longer sessions get penalized, breaks give bonus points
     * Eye strain reduces score, productive purposes get bonus
     * Interesting algoritm that balances usage time with healthy habits
     */
    @Override
    public double calculateProgress() {
        // Lower screen time and more breaks = better score
        double durationPenalty = Math.min(60, getDuration() / 5); // Penalty for long sessions
        double breaksBonus = Math.min(25, breaksTaken * 5); // Bonus for taking breaks
        double eyeStrainPenalty = causedEyeStrain ? 15 : 0;
        double purposeBonus = (purpose.equals("work") || purpose.equals("education")) ? 10 : 0;

        return Math.max(0, 100 - durationPenalty + breaksBonus - eyeStrainPenalty + purposeBonus);
    }

    /**
     * Checks if screen time duration makes sense
     * Should be between 1 minute and 24 hours - covers everything from quick checks to all-day usage
     * Some people realy do use screens for most of the day unfortunatly
     */
    @Override
    public boolean isValidDuration(double duration) {
        return duration >= 1 && duration <= 1440; // 1 minute to 24 hours
    }

    // Trackable interface implementations - required for the tracking system

    /**
     * Returns a formated progress string for display
     * Shows screen time score, duration, breaks taken, and eye strain status
     * Easy to read format that gives users all the importent info quickly
     */
    @Override
    public String getProgress() {
        return String.format("Screen Time Score: %.1f%% (%.01f min, %d breaks, %s)",
        calculateProgress(),getDuration(), breaksTaken, causedEyeStrain ? "Eye strain" : "No eye strain");
    }

    /**
     * Checks if this screen time session meets a wellness goal
     * Currently only supports screen time limit goals
     * Returns true if usage stayed under the target limit - lower is better for screen time
     */
    @Override
    public boolean meetsGoal(WellnessGoal_sec33_gr3 goal) {
        if (goal.getGoalType() == GoalType_sec33_gr3.SCREEN_TIME_LIMIT) {
            return getDuration() <= goal.getTargetValue(); // Goal is to stay under limit
        }
        return false; // wrong goal type or went over limit
    }

    /**
     * Creates a nice string representation of the screen time session
     * Includes all the importent details like device, purpose, breaks, and eye strain
     * Used when displaying screen time activities to users in lists and reports
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Device: %s | Purpose: %s | Breaks: %d | Eye Strain: %s",
                deviceType, purpose, breaksTaken, causedEyeStrain ? "Yes" : "No");
    }
}