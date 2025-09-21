/**
 * GoalType class - defines all the diferent types of wellness goals users can set
 * Uses string constants instead of enum to keep things simple
 * Helps validate goal types and makes sure users dont enter invalid goals
 */
public class GoalType {

    // Goal type constants - these are all the valid goal types users can choose from

    /**
     * Sleep duration goal - how many hours user wants to sleep per night
     * Most people need around 7-8 hours but everyone is diffrent
     */
    public static final String SLEEP_HOURS = "Sleep Duration";

    /**
     * Meditation time goal - how many minutes user wants to meditate daily
     * Even 10-15 minutes can make a big diferance for mental health
     */
    public static final String MEDITATION_MINUTES = "Meditation Time";

    /**
     * Journal entries goal - how many journal sessions user wants to do
     * Regular journaling helps with emotional processing and self reflection
     */
    public static final String JOURNAL_ENTRIES = "Journal Entries";

    /**
     * Screen time limit goal - maximum screen time user wants to stay under
     * This is backwards from other goals - lower numbers are better here
     */
    public static final String SCREEN_TIME_LIMIT = "Screen Time Limit";

    /**
     * Mood improvement goal - how much user wants there mood to improve
     * Tracks the diferance between mood before and after activities
     */
    public static final String MOOD_IMPROVEMENT = "Mood Score";

    /**
     * Activity streak goal - how many consecutive days of activities
     * Building habits requires consistency over time, streaks help with that
     */
    public static final String ACTIVITY_STREAK = "Activity Streak";

    /**
     * Array containing all valid goal types for easy validation and display
     * Makes it easy to loop through all posible goal types in menus
     */
    public static final String[] ALL_GOAL_TYPES = {
            SLEEP_HOURS, MEDITATION_MINUTES, JOURNAL_ENTRIES,
            SCREEN_TIME_LIMIT, MOOD_IMPROVEMENT, ACTIVITY_STREAK
    };

    /**
     * Checks if a goal type string is valid by comparing against known types
     * Returns true if the goal type exists, false if its unknown
     * Usefull for validation before creating new goals
     */
    public static boolean isValidGoalType(String goalType) {
        for (String type : ALL_GOAL_TYPES) {
            if (type.equals(goalType)) {
                return true;
            }
        }
        return false; // goal type not found in our list
    }

    /**
     * Gets a nice display name for a goal type
     * Currently just returns the same string but could be expanded later
     * Returns "Unknown Goal Type" for invalid types to avoid confusing users
     */
    public static String getDisplayName(String goalType) {
        return isValidGoalType(goalType) ? goalType : "Unknown Goal Type";
    }
}