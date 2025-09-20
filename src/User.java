import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * User class - represents a single user of the wellness tracking system
 * Stores all the user's activities, mood entries, and wellness goals in memory
 * Acts as the main data container for everything related to one user's journy
 */
public class User {

    // User profile information and data storage
    private String name; // user's display name
    private LocalDate joinDate; // when they started using the system
    private List<WellnessActivity> activities; // all there wellness activities
    private List<MoodStressEntry> moodStressEntries; // daily mood and stress logs
    private List<WellnessGoal> wellnessGoals; // goals they want to achieve

    /**
     * Constructor for creating a new user
     * Sets up empty lists for storing user data and records join date
     * Name is required but we dont validate it much - users can use any name they want
     */
    public User(String name) {
        this.name = name;
        this.joinDate = LocalDate.now(); // automatically set to today
        this.activities = new ArrayList<>();
        this.moodStressEntries = new ArrayList<>();
        this.wellnessGoals = new ArrayList<>();
    }

    // Basic getters for user profile info

    /**
     * Gets the user's display name
     * This is what shows up in the interface and reports
     */
    public String getName() { return name; }

    /**
     * Gets when the user joined the system
     * Usefull for calculating how long they've been tracking there wellness
     */
    public LocalDate getJoinDate() { return joinDate; }

    // Activity management methods - for adding, removing, and accessing activities

    /**
     * Adds a new wellness activity to the user's list
     * Activities are stored in chronological order automaticaly
     */
    public void addActivity(WellnessActivity activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }

    /**
     * Removes an activity at the specified index
     * Returns true if removal was sucessful, false if index was invalid
     */
    public boolean removeActivity(int index) {
        if (index >= 0 && index < activities.size()) {
            activities.remove(index);
            return true;
        }
        return false; // index out of bounds
    }

    /**
     * Gets all activities for this user
     * Returns the actual list so changes will affect the original data
     */
    public List<WellnessActivity> getActivities() { return activities; }

    /**
     * Gets the most recent activities up to a specified limit
     * Usefull for showing recent activity summaries without overwhelming users
     */
    public List<WellnessActivity> getRecentActivities(int limit) {
        List<WellnessActivity> recent = new ArrayList<>();
        int start = Math.max(0, activities.size() - limit);
        for (int i = start; i < activities.size(); i++) {
            recent.add(activities.get(i));
        }
        return recent;
    }

    // Mood and stress tracking methods

    /**
     * Adds a new mood/stress entry to the user's records
     * These help track emotional wellbeing over time
     */
    public void addMoodStressEntry(MoodStressEntry entry) {
        if (entry != null) {
            moodStressEntries.add(entry);
        }
    }

    /**
     * Gets all mood/stress entries for this user
     * Returns the actual list so changes will affect the original data
     */
    public List<MoodStressEntry> getMoodStressEntries() { return moodStressEntries; }

    // Wellness goals management methods

    /**
     * Adds a new wellness goal to the user's list
     * Goals help users stay motivated and track there progress over time
     */
    public void addGoal(WellnessGoal goal) {
        if (goal != null) {
            wellnessGoals.add(goal);
        }
    }

    /**
     * Gets all wellness goals for this user
     * Returns the actual list so changes will affect the original data
     */
    public List<WellnessGoal> getWellnessGoals() { return wellnessGoals; }

    // Advanced tracking methods for calculating streaks and patterns

    /**
     * Calculates current streak of consecutive days with activities
     * Streaks are importent for building healthy habits and staying motivated
     * Returns 0 if no recent activities or if streak was broken
     */
    public int getCurrentStreak(String activityType) {
        if (activities.isEmpty()) return 0;

        int streak = 0;
        LocalDate currentDate = LocalDate.now();

        // Count backwards through days until we find a gap
        while (hasActivityOnDate(currentDate, activityType)) {
            streak++;
            currentDate = currentDate.minusDays(1);
        }

        return streak;
    }

    /**
     * Checks if user has any activity of specified type on a given date
     * Helper method for calculating streaks and analyzing patterns
     */
    private boolean hasActivityOnDate(LocalDate date, String activityType) {
        for (WellnessActivity activity : activities) {
            if (activity.getDate().equals(date) &&
                activity.getActivityType().equals(activityType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a summary string representation of the user
     * Shows basic profile info and counts of there data
     * Usefull for displaying user information in the interface
     */
    @Override
    public String toString() {
        return String.format("User: %s | Joined: %s | Activities: %d | Goals: %d | Mood Entries: %d",
                name, joinDate, activities.size(), wellnessGoals.size(), moodStressEntries.size());
    }
}
