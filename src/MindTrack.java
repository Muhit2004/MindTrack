import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MindTrack {
    private static int testsPassed = 0;
    private static int totalTests = 0;

    public static void main(String[] args) {
        System.out.println("=== MINDTRACK COMPREHENSIVE TEST SUITE ===\n");

        // Run all test categories
        testUserClass();
        testSleepActivity();
        testMeditationActivity();
        testJournalingActivity();
        testScreenTimeActivity();
        testWellnessGoal();
        testMoodStressEntry();
        testIntegrationScenarios();
        testEdgeCases();
        testErrorHandling();

        // Print final results
        System.out.println("\n=== TEST SUMMARY ===");
        System.out.printf("Tests Passed: %d/%d\n", testsPassed, totalTests);
        System.out.printf("Success Rate: %.1f%%\n", (testsPassed * 100.0) / totalTests);

        if (testsPassed == totalTests) {
            System.out.println("🎉 ALL TESTS PASSED! Your MindTrack system is working correctly.");
        } else {
            System.out.println("❌ Some tests failed. Please review the issues above.");
        }
    }

    private static void testUserClass() {
        System.out.println("--- TESTING USER CLASS ---");

        try {
            // Test user creation
            User user = new User("John Doe");
            assertEquals("User name should be set correctly", "John Doe", user.getName());
            assertEquals("New user should have 0 activities", 0, user.getActivities().size());
            assertEquals("New user should have 0 goals", 0, user.getGoals().size());
            assertEquals("New user should have 0 mood entries", 0, user.getMoodStressEntries().size());

            // Test adding activities
            Sleep sleep = new Sleep(LocalDate.now().minusDays(1), 420, "Short sleep", 6,
                    LocalTime.of(0, 0), LocalTime.of(7, 0), false);
            user.addActivity(sleep);


            assertEquals("User should have 1 activity after adding", 1, user.getActivities().size());

            System.out.println("✅ User class tests passed\n");
        } catch (Exception e) {
            System.out.println("❌ User class test failed: " + e.getMessage());
        }
    }

    private static void testSleepActivity() {
        System.out.println("--- TESTING SLEEP ACTIVITY ---");

        try {
            Sleep sleep = new Sleep(LocalDate.now(), 480, "Good sleep", 8,
                    LocalTime.of(23, 0), LocalTime.of(7, 0), false);

            assertEquals("Sleep activity type should be 'Sleep'", "Sleep", sleep.getActivityType());
            assertEquals("Sleep duration should be 480", 480.0, sleep.getDuration());
            assertEquals("Sleep quality should be 8", 8, sleep.getQuality());
            assertTrue("8 hours should be valid duration", sleep.isValidDuration(480));
            assertFalse("30 minutes should be invalid", sleep.isValidDuration(30));

            double progress = sleep.calculateProgress();
            assertTrue("Progress should be between 0-100", progress >= 0 && progress <= 100);

            System.out.println("✅ Sleep activity tests passed\n");
        } catch (Exception e) {
            System.out.println("❌ Sleep activity test failed: " + e.getMessage());
        }
    }

    private static void testMeditationActivity() {
        System.out.println("--- TESTING MEDITATION ACTIVITY ---");

        try {
            Meditation meditation = new Meditation(LocalDate.now(), 20, "Morning meditation",
                    "guided", "stress", 2, true);

            assertEquals("Meditation activity type should be 'Meditation'", "Meditation", meditation.getActivityType());
            assertEquals("Meditation duration should be 20", 20.0, meditation.getDuration());
            assertTrue("Should be completed session", meditation.isCompletedSession());
            assertEquals("Should have 2 distractions", 2, meditation.getDistractionCount());

            assertTrue("20 minutes should be valid", meditation.isValidDuration(20));
            assertFalse("200 minutes should be invalid", meditation.isValidDuration(200));

            System.out.println("✅ Meditation activity tests passed\n");
        } catch (Exception e) {
            System.out.println("❌ Meditation activity test failed: " + e.getMessage());
        }
    }

    private static void testJournalingActivity() {
        System.out.println("--- TESTING JOURNALING ACTIVITY ---");

        try {
            Journaling journal = new Journaling(LocalDate.now(), 15, "Daily reflection",
                    7, 8, 250, "gratitude", true);

            assertEquals("Journaling activity type should be 'Journaling'", "Journaling", journal.getActivityType());
            assertEquals("Word count should be 250", 250, journal.getWordCount());
            assertEquals("Mood before should be 7", 7, journal.getMoodBefore());
            assertEquals("Mood after should be 8", 8, journal.getMoodAfter());

            assertTrue("15 minutes should be valid", journal.isValidDuration(15));

            System.out.println("✅ Journaling activity tests passed\n");
        } catch (Exception e) {
            System.out.println("❌ Journaling activity test failed: " + e.getMessage());
        }
    }

    private static void testScreenTimeActivity() {
        System.out.println("--- TESTING SCREEN TIME ACTIVITY ---");

        try {
            ScreenTime screenTime = new ScreenTime(LocalDate.now(), 120, "Work session",
                    "computer", "work", 3, false);

            assertEquals("ScreenTime activity type should be 'ScreenTime'", "ScreenTime", screenTime.getActivityType());
            assertEquals("Break count should be 3", 3, screenTime.getBreaksTaken());
            assertFalse("Should not have blue light filter", screenTime.usedBlueLightFilter());

            System.out.println("✅ ScreenTime activity tests passed\n");
        } catch (Exception e) {
            System.out.println("❌ ScreenTime activity test failed: " + e.getMessage());
        }
    }

    private static void testWellnessGoal() {
        System.out.println("--- TESTING WELLNESS GOAL ---");

        try {
            WellnessGoal goal = new WellnessGoal(GoalType.SLEEP_HOURS, 8.0, LocalDate.now(),
                    LocalDate.now().plusDays(30), "Sleep 8 hours daily");

            assertEquals("Goal target should be 8.0", 8.0, goal.getTargetValue());
            assertEquals("Goal type should be SLEEP_HOURS", GoalType.SLEEP_HOURS, goal.getGoalType());

            // Test goal meeting
            Sleep goodSleep = new Sleep(LocalDate.now(), 480, "8 hour sleep", 8,
                    LocalTime.of(22, 0), LocalTime.of(6, 0), false);
            assertTrue("8 hour sleep should meet 8 hour goal", goodSleep.meetsGoal(goal));

            Sleep shortSleep = new Sleep(LocalDate.now(), 300, "5 hour sleep", 6,
                    LocalTime.of(1, 0), LocalTime.of(6, 0), false);
            assertFalse("5 hour sleep should not meet 8 hour goal", shortSleep.meetsGoal(goal));

            System.out.println("✅ Wellness goal tests passed\n");
        } catch (Exception e) {
            System.out.println("❌ Wellness goal test failed: " + e.getMessage());
        }
    }

    private static void testMoodStressEntry() {
        System.out.println("--- TESTING MOOD STRESS ENTRY ---");

        try {
            MoodStressEntry entry = new MoodStressEntry(LocalDate.now(), 7, 4,
                    "calm,focused", "work deadline", "Feeling better");

            assertEquals("Mood level should be 7", 7, entry.getMoodLevel());
            assertEquals("Stress level should be 4", 4, entry.getStressLevel());
            assertTrue("Should contain calm tag", entry.getMoodTags().contains("calm"));
            assertTrue("Should contain focused tag", entry.getMoodTags().contains("focused"));

            System.out.println("✅ Mood stress entry tests passed\n");
        } catch (Exception e) {
            System.out.println("❌ Mood stress entry test failed: " + e.getMessage());
        }
    }

    private static void testIntegrationScenarios() {
        System.out.println("--- TESTING INTEGRATION SCENARIOS ---");

        try {
            User user = new User("Integration Test User");

            // Add multiple activities
            user.addActivity(new Sleep(LocalDate.now(), 480, "Good sleep", 8,
                    LocalTime.of(23, 0), LocalTime.of(7, 0), false));
            user.addActivity(new Meditation(LocalDate.now(), 20, "Morning meditation",
                    "guided", "stress", 1, true));
            user.addActivity(new Sleep(LocalDate.now().minusDays(1), 420, "Short sleep", 6,
                    LocalTime.of(21, 0), LocalTime.of(7, 0), false));

            // Test filtering by type
            List<WellnessActivity> sleepActivities = user.getActivitiesByType("Sleep");
            assertEquals("Should have 2 sleep activities", 2, sleepActivities.size());

            // Test recent activities
            List<WellnessActivity> recentActivities = user.getRecentActivities(2);
            assertEquals("Should return 2 recent activities", 2, recentActivities.size());

            System.out.println("✅ Integration scenario tests passed\n");
        } catch (Exception e) {
            System.out.println("❌ Integration scenario test failed: " + e.getMessage());
        }
    }

    private static void testEdgeCases() {
        System.out.println("--- TESTING EDGE CASES ---");

        try {
            // Test boundary values
            Sleep minSleep = new Sleep(LocalDate.now(), 60, "Minimal sleep", 1,
                    LocalTime.of(6, 0), LocalTime.of(7, 0), false);
            assertTrue("1 hour should be valid (boundary)", minSleep.isValidDuration(60));

            Sleep maxSleep = new Sleep(LocalDate.now(), 720, "Maximum sleep", 10,
                    LocalTime.of(22, 0), LocalTime.of(10, 0), false);
            assertTrue("12 hours should be valid (boundary)", maxSleep.isValidDuration(720));

            // Test null/empty values
            User userWithEmptyName = new User("");
            assertTrue("Empty name should be handled", userWithEmptyName.getName() != null);

            System.out.println("✅ Edge case tests passed\n");
        } catch (Exception e) {
            System.out.println("❌ Edge case test failed: " + e.getMessage());
        }
    }

    private static void testErrorHandling() {
        System.out.println("--- TESTING ERROR HANDLING ---");

        try {
            // Test invalid duration handling
            try {
                Sleep invalidSleep = new Sleep(LocalDate.now(), -10, "Invalid", 5,
                        LocalTime.of(22, 0), LocalTime.of(6, 0), false);
                invalidSleep.setDuration(-10);
                System.out.println("❌ Should have thrown exception for negative duration");
                totalTests++;
            } catch (InvalidActivityDurationException e) {
                System.out.println("✅ Correctly caught invalid duration exception");
                testsPassed++;
                totalTests++;
            }

            // Test invalid quality values
            Sleep sleep = new Sleep(LocalDate.now(), 480, "Test", 15,
                    LocalTime.of(22, 0), LocalTime.of(6, 0), false);
            assertEquals("Quality above 10 should be capped at 10", 10, sleep.getQuality());

            System.out.println("✅ Error handling tests passed\n");
        } catch (Exception e) {
            System.out.println("❌ Error handling test failed: " + e.getMessage());
        }
    }

    // Helper assertion methods
    private static void assertEquals(String message, Object expected, Object actual) {
        totalTests++;
        if (expected.equals(actual)) {
            testsPassed++;
        } else {
            System.out.println("❌ " + message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertTrue(String message, boolean condition) {
        totalTests++;
        if (condition) {
            testsPassed++;
        } else {
            System.out.println("❌ " + message + " - Expected: true, Actual: false");
        }
    }

    private static void assertFalse(String message, boolean condition) {
        totalTests++;
        if (!condition) {
            testsPassed++;
        } else {
            System.out.println("❌ " + message + " - Expected: false, Actual: true");
        }
    }
}