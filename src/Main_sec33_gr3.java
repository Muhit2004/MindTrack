/*
  Replaced the IntelliJ template with a full console menu application as requested.
  Implemented: session User management, main menu, Option 1 (Log New Activity) with submenu,
  basic Option 3 (Log Mood/Stress) and Option 4 (Set/Update Wellness Goals) flows.
  No HashMap or enum used; simple arrays/conditionals only. — [Changed Code]
*/

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    //just one sessions
    private static User currentUser;
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Main entry point - this is where the program starts running
     * Sets up the user, shows the menu, and handles all the menu choices
     */
    public static void main(String[] args) {
        // Initialize user once for the session.
        System.out.println("Welcome to MindTrack!\n");
        System.out.print("Please enter your name to begin: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Guest";
        currentUser = new User(name);
        System.out.println("Hello, " + currentUser.getName() + "! Let's take care of your wellness.\n");

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = readInt("Choose an option (1-8): ", 1, 9);
            switch (choice) {
                case 1:
                    logNewActivity();
                    break; // Option 1 —
                case 2:
                    updateActivity();
                    break;
                case 3:
                    logMoodStress();
                    break;
                case 4:
                    setUpdateWellnessGoals();
                    break;
                case 5:
                    viewWellnessProgress();
                    break;
                case 6:
                    generateWellnessReports();
                    break;
                case 7:
                    removeActivity();
                    break;
                case 8:
                    innovativeFeature();
                    break;
                case 9:
                    System.out.println("Thank you for using MindTrack. Stay healthy and mindful!");
                    running = false;
                    break;
                default:
                    // wont happed
                    System.out.println("Invalid choice. Try again.");
            }
            System.out.println();
        }
    }

    /**
     * Shows the main menu with all 8 options
     * Displays user info at the top so they know who's logged in
     */

    private static void displayMainMenu() {
        System.out.println("------------------------------------------------------------");
        System.out.println("Personal Mental Wellness Tracker System (MindTrack)");
        System.out.println("User: " + currentUser.getName() + " | Joined: " + currentUser.getJoinDate());
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Log New Activity");
        System.out.println("2. Update Activity");
        System.out.println("3. Log Mood/Stress");
        System.out.println("4. Set/Update Wellness Goals");
        System.out.println("5. View Wellness Progress");
        System.out.println("6. Generate Wellness Reports");
        System.out.println("7. Remove Activity");
        System.out.println("8. [Your innovative feature]");
        System.out.println("9. Exit");
        System.out.println("------------------------------------------------------------");
    }

    /**
     * Option 1 - Let user log a new wellness activity
     * Shows submenu for Sleep, Meditation, Journaling, or ScreenTime
     * Gets all the details and saves it to the user's activity list -check User.java for how the data structure works.
     */
    // Option 1: Log New Activity — includes submenu for activity types.
    private static void logNewActivity() {
        System.out.println("-- Log New Activity --");
        System.out.println("Select activity type:");
        System.out.println("  1. Sleep");
        System.out.println("  2. Meditation");
        System.out.println("  3. Journaling");
        System.out.println("  4. ScreenTime");
        int type = readInt("Your choice (1-4): ", 1, 4);

        LocalDate date = readDate("Enter date (YYYY-MM-DD), blank for today: ");
        String notes = readLine("Any notes (optional): ");

        try {
            switch (type) {
                case 1:
                    // Sleep
                    double sleepMinutes = readHourAsMinutes("Duration in Hours (0-24): ", 0, 1440);
                    int quality = readInt("Sleep quality (1-10): ", 1, 10);
                    LocalTime bedtime = readTime("Bedtime (HH:MM), blank to skip: ", true);
                    LocalTime wake = readTime("Wake time (HH:MM), blank to skip: ", true);
                    boolean nightmares = readYesNo("Had nightmares? (y/n): ");

                    Sleep sleep = new Sleep(date, sleepMinutes, notes, quality, bedtime, wake, nightmares); //object

                    if (!sleep.isValidDuration(sleepMinutes)) {
                        throw new InvalidActivityDurationException("Invalid duration for Sleep: " + sleepMinutes);
                    }
                    currentUser.addActivity(sleep); //adds to the user's list of activities
                    System.out.println("Added: " + sleep);
                    System.out.println(sleep.getProgress()); //shows progress of activity
                    break;
                case 2:
                    // Meditation
                    double medMinutes = readHourAsMinutes("Duration in Hours (0-3): ", 0, 180);
                    String medType = readLine("Meditation type (guided/unguided/breathing/etc.): ");
                    String focus = readLine("Focus area (stress/anxiety/focus/general/etc.): ");
                    int distractions = readInt("Distraction count (0+): ", 0, Integer.MAX_VALUE);
                    boolean completed = readYesNo("Completed session? (y/n): ");

                    Meditation meditation = new Meditation(date, medMinutes, notes, medType, focus, distractions, completed);
                    if (!meditation.isValidDuration(medMinutes)) {
                        throw new InvalidActivityDurationException("Invalid duration for Meditation: " + medMinutes);
                    }
                    currentUser.addActivity(meditation);
                    System.out.println("Added: " + meditation);
                    System.out.println(meditation.getProgress());
                    break;
                case 3:
                    // Journaling
                    double jrnlMinutes = readHourAsMinutes("Duration in Hours (0-2): ", 0, 120);

                    int moodBefore = readInt("Mood before (1-10): ", 1, 10);
                    int moodAfter = readInt("Mood after (1-10): ", 1, 10);
                    int words = readInt("Approx. word count (0+): ", 0, Integer.MAX_VALUE);
                    String theme = readLine("Journal theme (gratitude/reflection/goals/free-writing/etc.): ");
                    boolean beneficial = readYesNo("Felt beneficial? (y/n): ");

                    Journaling journaling = new Journaling(date, jrnlMinutes, notes, moodBefore, moodAfter, words, theme, beneficial);
                    if (!journaling.isValidDuration(jrnlMinutes)) {
                        throw new InvalidActivityDurationException("Invalid duration for Journaling: " + jrnlMinutes);
                    }
                    currentUser.addActivity(journaling);
                    System.out.println("Added: " + journaling);
                    System.out.println(journaling.getProgress());
                    break;
                case 4:
                    // ScreenTime
                    double stMinutes = readHourAsMinutes("Duration in Hours (0-24): ", 0, 1440);
                    String device = readLine("Device type (phone/computer/tablet/tv/etc.): ");
                    String purpose = readLine("Purpose (work/education/entertainment/social/etc.): ");
                    int breaks = readInt("Breaks taken (0+): ", 0, Integer.MAX_VALUE);
                    boolean eyeStrain = readYesNo("Caused eye strain? (y/n): ");

                    ScreenTime screenTime = new ScreenTime(date, stMinutes, notes, device, purpose, breaks, eyeStrain);
                    if (!screenTime.isValidDuration(stMinutes)) {
                        throw new InvalidActivityDurationException("Invalid duration for ScreenTime: " + stMinutes);
                    }
                    currentUser.addActivity(screenTime);
                    System.out.println("Added: " + screenTime);
                    System.out.println(screenTime.getProgress());
                    break;
            }
        } catch (InvalidActivityDurationException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("An error occurred while logging activity: " + ex.getMessage());
        }
    }

    /**
     * Option 2 - Update an existing activity
     * User picks which activity to edit, then can change specific fields
     * Uses a loop so they can update multiple fields before exiting
     * it might be a big method , but it is easy to understand
     */
    private static void updateActivity() {

        try {
            System.out.println("-- Update Activity --");
            if (currentUser.getActivities().isEmpty()) {
                System.out.println("No activities to update.");
                return;
            }
            for (int i = 0; i < currentUser.getActivities().size(); i++) {
                System.out.println((i + 1) + ". " + currentUser.getActivities().get(i));
            }
            int idx = readInt("Select activity to update: ", 1, currentUser.getActivities().size()) - 1;
            WellnessActivity activity = currentUser.getActivities().get(idx);

            if (activity instanceof Sleep sleep) {
                boolean updating = true;
                while (updating) {
                    System.out.println("Which field do you want to update?");
                    System.out.println("1. Notes");
                    System.out.println("2. Duration");
                    System.out.println("3. Quality");
                    System.out.println("4. Bedtime");
                    System.out.println("5. Wake Time");
                    System.out.println("6. Nightmares");
                    System.out.println("7. Exit");
                    int choice = readInt("Choose (1-7): ", 1, 7);
                    switch (choice) {
                        case 1:
                            String notes = readLine("New notes: ");
                            if (!notes.isEmpty()) sleep.setNotes(notes); //sets note using setter method , and the same goes for all the cases.
                            break;
                        case 2:
                            double dur = readHourAsMinutes("New duration in hours: ", 0, 24 * 60);
                            if (dur > 0 && sleep.isValidDuration(dur)) {
                                try {
                                    sleep.setDuration(dur);
                                } catch (InvalidActivityDurationException e) {
                                    System.out.println("Error updating duration: " + e.getMessage());
                                }
                            }
                            break;
                        case 3:
                            int qual = readInt("New quality (1-10): ", 1, 10);
                            sleep.setQuality(qual);
                            break;
                        case 4:
                            LocalTime bt = readTime("New bedtime (HH:MM): ", true);
                            if (bt != null) sleep.setBedtime(bt);
                            break;
                        case 5:
                            LocalTime wt = readTime("New wake time (HH:MM): ", true);
                            if (wt != null) sleep.setWakeTime(wt);
                            break;
                        case 6:
                            boolean nm = readYesNo("Had nightmares? (y/n): ");
                            sleep.setHadNightmares(nm);
                            break;
                        case 7:
                            updating = false;
                            break;
                    }
                }
                System.out.println("Activity updated: " + sleep);
            } else if (activity instanceof Meditation meditation) {  //using interface functions makes it easier for us
                boolean updating = true;
                while (updating) {
                    System.out.println("Which field do you want to update?");
                    System.out.println("1. Notes");
                    System.out.println("2. Duration");
                    System.out.println("3. Type");
                    System.out.println("4. Focus");
                    System.out.println("5. Distractions");
                    System.out.println("6. Completed");
                    System.out.println("7. Exit");
                    int choice = readInt("Choose (1-7): ", 1, 7);
                    switch (choice) {
                        case 1:
                            String notes = readLine("New notes: ");
                            if (!notes.isEmpty()) meditation.setNotes(notes);
                            break;
                        case 2:
                            double dur = readHourAsMinutes("New duration in hours: ", 0, 3 * 60);
                            if (dur > 0 && meditation.isValidDuration(dur)) {
                                try {
                                    meditation.setDuration(dur);
                                } catch (InvalidActivityDurationException e) {
                                    System.out.println("Error updating duration: " + e.getMessage());
                                }
                            }
                            break;
                        case 3:
                            String type = readLine("New meditation type: ");
                            if (!type.isEmpty()) meditation.setMeditationType(type);
                            break;
                        case 4:
                            String focus = readLine("New focus area: ");
                            if (!focus.isEmpty()) meditation.setFocusArea(focus);
                            break;
                        case 5:
                            int dist = readInt("New distraction count: ", 0, Integer.MAX_VALUE);
                            meditation.setDistractionCount(dist);
                            break;
                        case 6:
                            boolean comp = readYesNo("Completed session? (y/n): ");
                            meditation.setCompletedSession(comp);
                            break;
                        case 7:
                            updating = false;
                            break;
                    }
                }
                System.out.println("Activity updated: " + meditation);
            } else if (activity instanceof Journaling journaling) {
                boolean updating = true;
                while (updating) {
                    System.out.println("Which field do you want to update?");
                    System.out.println("1. Notes");
                    System.out.println("2. Duration");
                    System.out.println("3. Mood Before");
                    System.out.println("4. Mood After");
                    System.out.println("5. Word Count");
                    System.out.println("6. Theme");
                    System.out.println("7. Beneficial");
                    System.out.println("8. Exit");
                    int choice = readInt("Choose (1-8): ", 1, 8);
                    switch (choice) {
                        case 1:
                            String notes = readLine("New notes: ");
                            if (!notes.isEmpty()) journaling.setNotes(notes);
                            break;
                        case 2:
                            double dur = readHourAsMinutes("New duration in hours: ", 0, 2 * 60);
                            if (dur > 0 && journaling.isValidDuration(dur)) {
                                try {
                                    journaling.setDuration(dur);
                                } catch (InvalidActivityDurationException e) {
                                    System.out.println("Error updating duration: " + e.getMessage());
                                }
                            }
                            break;
                        case 3:
                            int mb = readInt("New mood before (1-10): ", 1, 10);
                            journaling.setMoodBefore(mb);
                            break;
                        case 4:
                            int ma = readInt("New mood after (1-10): ", 1, 10);
                            journaling.setMoodAfter(ma);
                            break;
                        case 5:
                            int wc = readInt("New word count: ", 0, Integer.MAX_VALUE);
                            journaling.setWordCount(wc);
                            break;
                        case 6:
                            String theme = readLine("New theme: ");
                            if (!theme.isEmpty()) journaling.setJournalTheme(theme);
                            break;
                        case 7:
                            boolean ben = readYesNo("Felt beneficial? (y/n): ");
                            journaling.setFeltBeneficial(ben);
                            break;
                        case 8:
                            updating = false;
                            break;
                    }
                }
                System.out.println("Activity updated: " + journaling);
            } else if (activity instanceof ScreenTime screenTime) {
                boolean updating = true;
                while (updating) {
                    System.out.println("Which field do you want to update?");
                    System.out.println("1. Notes");
                    System.out.println("2. Duration");
                    System.out.println("3. Device");
                    System.out.println("4. Purpose");
                    System.out.println("5. Breaks");
                    System.out.println("6. Eye Strain");
                    System.out.println("7. Exit");
                    int choice = readInt("Choose (1-7): ", 1, 7);
                    switch (choice) {
                        case 1:
                            String notes = readLine("New notes: ");
                            if (!notes.isEmpty()) screenTime.setNotes(notes);
                            break;
                        case 2:
                            double dur = readHourAsMinutes("New duration in hours: ", 0, 24 * 60);
                            if (dur > 0 && screenTime.isValidDuration(dur)) {
                                try {
                                    screenTime.setDuration(dur);
                                } catch (InvalidActivityDurationException e) {
                                    System.out.println("Error updating duration: " + e.getMessage());
                                }
                            }
                            break;
                        case 3:
                            String device = readLine("New device type: ");
                            if (!device.isEmpty()) screenTime.setDeviceType(device);
                            break;
                        case 4:
                            String purpose = readLine("New purpose: ");
                            if (!purpose.isEmpty()) screenTime.setPurpose(purpose);
                            break;
                        case 5:
                            int breaks = readInt("New breaks taken: ", 0, Integer.MAX_VALUE);
                            screenTime.setBreaksTaken(breaks);
                            break;
                        case 6:
                            boolean es = readYesNo("Caused eye strain? (y/n): ");
                            screenTime.setCausedEyeStrain(es);
                            break;
                        case 7:
                            updating = false;
                            break;
                    }
                }
                System.out.println("Activity updated: " + screenTime);
            }
        }catch (Exception ex) {
            System.out.println("An error occurred while updating activity: " + ex.getMessage());
        }
    }

    /**
     * Option 3 - Log daily mood and stress levels
     * Simple form to capture how the user is feeling today
     * Also lets them add tags and notes about their mood
     */

    private static void logMoodStress() {
        System.out.println("-- Log Mood/Stress --");
        LocalDate date = readDate("Enter date (YYYY-MM-DD), blank for today: ");
        int mood = readInt("Mood level (1-10): ", 1, 10);
        int stress = readInt("Stress level (1-10): ", 1, 10);
        String tags = readLine("Mood tags (comma-separated, optional): ");
        String triggers = readLine("Stress triggers (comma-separated, optional): ");
        String notes = readLine("Notes (optional): ");
        try {
            MoodStressEntry entry = new MoodStressEntry(date, mood, stress, tags, triggers, notes);
            currentUser.addMoodStressEntry(entry);
            System.out.println("Saved: " + entry);
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Option 4 - Manage wellness goals
     * Shows submenu to either add new goals or update existing ones
     * Goals help users track their wellness targets over time

     */

    private static void setUpdateWellnessGoals() {
        System.out.println("-- Set/Update Wellness Goals --");
        System.out.println("1. Add new goal");
        System.out.println("2. Update existing goal (target value/description)");
        int choice = readInt("Your choice (1-2): ", 1, 2);
        if (choice == 1) {
            addGoalFlow();
        } else {
            updateGoalFlow();
        }
    }

    /**
     * Helper method for adding new wellness goals
     * Shows list of available goal types and gets all the details
     * Validates input and saves the new goal to user's goal list
     */
    private static void addGoalFlow() {

        System.out.println("Available goal types:");
        String[] types = GoalType.ALL_GOAL_TYPES;  // using goaltype array from goalType.java
        for (int i = 0; i < types.length; i++) {
            System.out.println("  " + (i + 1) + ". " + types[i]); //shows goaltypes in order
        }
        int idx = readInt("Select goal type: ", 1, types.length) - 1;
        String goalType = types[idx];
        double target = readDouble("Target value (>0): ", 0.000001, Double.MAX_VALUE);
        LocalDate start = readDate("Start date (YYYY-MM-DD), blank for today: ");
        LocalDate end = readDate("End date (YYYY-MM-DD), blank for today: ");
        String desc = readLine("Short description: ");
        try {
            WellnessGoal goal = new WellnessGoal(goalType, target, start, end, desc);
            currentUser.addGoal(goal);
            System.out.println("Added: " + goal);
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Helper method for updating existing goals
     * User picks a goal and can change the target value or description
     * Usefull when circumstances change or they want to adjust targets
     */
    private static void updateGoalFlow() {
        if (currentUser.getWellnessGoals().isEmpty()) {
            System.out.println("No goals yet. Add one first.");
            return;
        }
        System.out.println("Your goals:");
        for (int i = 0; i < currentUser.getWellnessGoals().size(); i++) {
            WellnessGoal g = currentUser.getWellnessGoals().get(i);
            System.out.println("  " + (i + 1) + ". " + g);
        }
        int idx = readInt("Select a goal to update: ", 1, currentUser.getWellnessGoals().size()) - 1;
        WellnessGoal goal = currentUser.getWellnessGoals().get(idx);
        System.out.println("1. Update target value");
        System.out.println("2. Update description");
        int what = readInt("Choose (1-2): ", 1, 2);
        try {
            if (what == 1) {
                double newTarget = readDouble("New target (>0): ", 0.000001, Double.MAX_VALUE);
                goal.setTargetValue(newTarget);
            } else {
                String newDesc = readLine("New description: ");
                goal.setDescription(newDesc);
            }
            System.out.println("Updated: " + goal);
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Option 5 - View current wellness progress
     * Shows recent activities, mood entries, and goal progress
     * Like a dashboard summarry of the user's wellness journey
     */
    private static void viewWellnessProgress() {
        System.out.println("-- Wellness Progress --");
        System.out.println(currentUser);
        System.out.println("Recent activities:");
        for (WellnessActivity a : currentUser.getRecentActivities(5)) {
            System.out.println("  - " + a);
        }
        System.out.println("Mood/Stress entries (latest 5):");
        int shown = 0;
        for (int i = currentUser.getMoodStressEntries().size() - 1; i >= 0 && shown < 5; i--) {
            System.out.println("  - " + currentUser.getMoodStressEntries().get(i));
            shown++;
        }
        if (currentUser.getWellnessGoals().isEmpty()) {
            System.out.println("No goals set yet.");
        } else {
            System.out.println("Goals:");
            for (WellnessGoal g : currentUser.getWellnessGoals()) {
                System.out.println("  - " + g);
            }
        }
    }

    /**
     * Option 6 - Generate detailed wellness reports
     * Groups activities by type (sleep, meditation, etc) and shows everything
     * Great for seeing patterns and tracking long-term progress
     * good use of toString in each classes
     */

    private static void generateWellnessReports() {
        System.out.println("-- Generate Wellness Reports --");

        if (currentUser.getActivities().isEmpty()) {
            System.out.println("No activities logged yet.");
            return;
        }

        System.out.println("=== ACTIVITY SUMMARIES ===\n");

        // Sleep Summary
        System.out.println("--- SLEEP ACTIVITIES ---");
        boolean hasSleep = false;
        for (WellnessActivity activity : currentUser.getActivities()) {
            if (activity instanceof Sleep) {
                System.out.println("• " + activity.toString());
                hasSleep = true;
            }
        }
        if (!hasSleep) System.out.println("No sleep activities logged.");
        System.out.println();

        // Meditation Summary
        System.out.println("--- MEDITATION ACTIVITIES ---");
        boolean hasMeditation = false;
        for (WellnessActivity activity : currentUser.getActivities()) {
            if (activity instanceof Meditation) {
                System.out.println("• " + activity.toString());
                hasMeditation = true;
            }
        }
        if (!hasMeditation) System.out.println("No meditation activities logged.");
        System.out.println();

        // Journaling Summary
        System.out.println("--- JOURNALING ACTIVITIES ---");
        boolean hasJournaling = false;
        for (WellnessActivity activity : currentUser.getActivities()) {
            if (activity instanceof Journaling) {
                System.out.println("• " + activity.toString());
                hasJournaling = true;
            }
        }
        if (!hasJournaling) System.out.println("No journaling activities logged.");
        System.out.println();

        // Screen Time Summary
        System.out.println("--- SCREEN TIME ACTIVITIES ---");
        boolean hasScreenTime = false;
        for (WellnessActivity activity : currentUser.getActivities()) {
            if (activity instanceof ScreenTime) {
                System.out.println("• " + activity.toString());
                hasScreenTime = true;
            }
        }
        if (!hasScreenTime) System.out.println("No screen time activities logged.");
        System.out.println();

        // Mood/Stress entries
        if (!currentUser.getMoodStressEntries().isEmpty()) {
            System.out.println("--- MOOD/STRESS ENTRIES ---");
            for (MoodStressEntry entry : currentUser.getMoodStressEntries()) {
                System.out.println("• " + entry.toString());
            }
            System.out.println();
        }

        // Wellness goals
        if (!currentUser.getWellnessGoals().isEmpty()) {
            System.out.println("--- WELLNESS GOALS ---");
            for (WellnessGoal goal : currentUser.getWellnessGoals()) {
                System.out.println("• " + goal.toString());
            }
        }
    }

    /**
     remove activity option , chack user to understand
    */
    private static void removeActivity() {
        System.out.println("-- Remove Activity --");
        if (currentUser.getActivities().isEmpty()) {
            System.out.println("No activities logged yet.");
            return;
        }
        System.out.println("Your activities:");
        for (int i = 0; i < currentUser.getActivities().size(); i++) {
            WellnessActivity a = currentUser.getActivities().get(i);
            System.out.println("  " + (i + 1) + ". " + a);
        }

        int idx = readInt("Select an activity to remove: ", 1, currentUser.getActivities().size()) - 1;

        currentUser.removeActivity(idx);
        System.out.println("Removed activity.");


    }

    /**
  add a new features
     */

    private static void innovativeFeature() {
        System.out.println("-- Quick Calm Breathing Coach --");
        System.out.println("Try 4-4-6 breathing for 1 minute: Inhale 4s, Hold 4s, Exhale 6s. You got this!");
    }

    // =====================
    // Input helper methods
    // =====================

    /**
     * Reads an integer from user with validation
     * Keeps asking until they enter a valid number in the range
     * Used for menu choices, ratings, counts, etc.
     */
    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(line);
                if (v < min || v > max) throw new NumberFormatException();
                return v;
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number between " + min + " and " + max + ".");
            }
        }
    }

    /**
     * Reads a decimal number with validation
     * Similar to readInt but for things like target values that can be decimal
     * Handles special case where max could be infinity
     */
    private static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                double v = Double.parseDouble(line);
                if (v < min || v > max) throw new NumberFormatException();
                return v;
            } catch (NumberFormatException ex) {
                String maxTxt = (max == Double.MAX_VALUE) ? "+∞" : String.valueOf(max);
                System.out.println("Please enter a valid number in range [" + min + ", " + maxTxt + "].");
            }
        }
    }

    /**
     * Reads a date from user input
     * If they leave it blank, uses today's date as default
     * Keeps asking if they enter invalid format until they get it right
     */
    private static LocalDate readDate(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) return LocalDate.now();
        while (true) {
            try {
                return LocalDate.parse(line);
            } catch (Exception ex) {
                System.out.print("Invalid date format. Please use YYYY-MM-DD: ");
                line = scanner.nextLine().trim();
                if (line.isEmpty()) return LocalDate.now();
            }
        }
    }

    /**
     * Reads a time from user (like 23:30 for bedtime)
     * Can allow blank input if allowBlank is true
     * Returns null for blank input when allowed
     */
    private static LocalTime readTime(String prompt, boolean allowBlank) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        if (allowBlank && line.isEmpty()) return null;
        while (true) {
            try {
                return LocalTime.parse(line);
            } catch (Exception ex) {
                System.out.print("Invalid time format. Please use HH:MM: ");
                line = scanner.nextLine().trim();
                if (allowBlank && line.isEmpty()) return null;
            }
        }
    }

    /**
     * Special method to read hours and convert to minutes automatically
     * Makes it easier for users to think in hours but we store as minutes
     * Returns -1 for blank input (used in update flows to mean "no change")
     */
    private static double readHourAsMinutes(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) return -1;
            try {
                double hour = Double.parseDouble(line);
                double minutes = hour * 60;
                if (minutes < min || minutes > max) throw new NumberFormatException();
                return minutes;
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid hour so that minutes are in range [" + min + ", " + max + "].");
            }
        }
    }

    /**
     * Reads yes/no response from user
     * Accepts y, yes, n, no in any case
     * Keeps asking until they give a valid yes/no answer
     */
    private static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    /**
     * Reads a line of text from user
     * Trims whitespace automatically
     * Can return empty string if user just presses enter
     */
    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}