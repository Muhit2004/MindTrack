import java.time.LocalDate;

/**
 * Abstract base class for all wellness activities in the system
 * Provides common functionality that all activities share like date and duration
 * Makes it easier to work with diferent types of activities using polymorphism
 */
public abstract class WellnessActivity_sec33_gr3 {

    // Common fields that every wellness activity needs to have
    protected int activityId; // unique ID for each activity - helps with tracking
    protected LocalDate date; // when the activity happened
    protected double duration; // how long it lasted in minutes
    protected String notes; // user's personal notes about the activity
    protected static int nextId = 1; // counter for generating unique IDs automaticaly

    /**
     * Constructor for creating new wellness activities
     * Takes the basic info that all activities need and generates a unique ID
     * Duration is in minutes to keep everything consistant across all activity types
     */
    public WellnessActivity_sec33_gr3(LocalDate date, double duration, String notes) {
        this.activityId = nextId++; // gets next available ID and increments counter
        this.date = date;
        this.duration = duration;
        this.notes = notes != null ? notes : ""; // prevents null notes from causing problems
    }

    // Getters for accessing activity data - these are the same for all activity types

    /**
     * Gets the unique ID of this activity
     * Usefull for finding specific activities in lists or databases
     */
    public int getActivityId() { return activityId; }

    /**
     * Gets the date when this activity happened
     * Important for sorting and filtering activities by time periods
     */
    public LocalDate getDate() { return date; }

    /**
     * Gets how long the activity lasted in minutes
     * All durations are stored as minutes for consistancy across activity types
     */
    public double getDuration() { return duration; }

    /**
     * Gets the user's personal notes about this activity
     * Notes help users remember importent details about there activities
     */
    public String getNotes() { return notes; }

    // Setters for modifying activity data - includes validation where needed

    /**
     * Sets new notes for this activity
     * Prevents null notes by converting them to empty strings automaticaly
     */
    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }

    /**
     * Sets new duration for this activity with validation
     * Throws exception if duration is invalid for this activity type
     * Each activity type has there own rules about valid durations
     */
    public void setDuration(double duration) throws InvalidActivityDurationException {
        if (!isValidDuration(duration)) {
            throw new InvalidActivityDurationException("Invalid duration: " + duration + " minutes");
        }
        this.duration = duration;
    }

    // Abstract methods - each activity type must implement these diferently

    /**
     * Gets the type of activity as a string
     * Each subclass returns there own activity type like "Sleep" or "Meditation"
     */
    public abstract String getActivityType();

    /**
     * Calculates progress or quality score for this activity
     * Each activity type has there own way of measuring success
     * Returns a score between 0-100 usually
     */
    public abstract double calculateProgress();

    /**
     * Checks if a duration value makes sense for this activity type
     * Sleep activities have diferent valid durations than meditation activities
     * Helps prevent users from entering unrealistic values
     */
    public abstract boolean isValidDuration(double duration);

    /**
     * Creates a nice string representation of the activity
     * Shows the basic info that all activities share in a readable format
     * Subclasses can add there own specific details to this
     */
    @Override
    public String toString() {
        return String.format("ID: %d | %s | Date: %s | Duration: %.1f min | Notes: %s",
                activityId, getActivityType(), date, duration, notes.isEmpty() ? "None" : notes);
    }
}
