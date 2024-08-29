import java.util.ArrayList;
import java.util.List;

public class ScheduleGenerator {

    // Inner class representing a Team
    public class Team {
        private String name;

        public Team(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Inner class representing a Matchup between two teams
    public class Matchup {
        private Team team1;
        private Team team2;

        public Matchup(Team team1, Team team2) {
            this.team1 = team1;
            this.team2 = team2;
        }

        @Override
        public String toString() {
            return team1 + " vs " + team2;
        }
    }

    // Method to generate the schedule using Divide and Conquer
    public List<Matchup> generateSchedule(List<Team> teams) {
        if (teams.size() == 2) {
            // Base case: Only two teams, so one match
            List<Matchup> matchups = new ArrayList<>();
            matchups.add(new Matchup(teams.get(0), teams.get(1)));
            return matchups;
        }

        // Divide the teams into two groups
        List<Team> firstHalf = teams.subList(0, teams.size() / 2);
        List<Team> secondHalf = teams.subList(teams.size() / 2, teams.size());

        // Recursively generate matchups for both halves
        List<Matchup> firstHalfMatchups = generateSchedule(firstHalf);
        List<Matchup> secondHalfMatchups = generateSchedule(secondHalf);

        // Combine the matchups
        List<Matchup> combinedMatchups = new ArrayList<>(firstHalfMatchups);
        combinedMatchups.addAll(secondHalfMatchups);

        // Add the cross matches between the two halves
        for (Team team1 : firstHalf) {
            for (Team team2 : secondHalf) {
                combinedMatchups.add(new Matchup(team1, team2));
            }
        }

        return combinedMatchups;
    }

    // Main method for testing the schedule generation
    public static void main(String[] args) {
        ScheduleGenerator generator = new ScheduleGenerator();

        // Sample teams
        List<Team> teams = new ArrayList<>();
        teams.add(generator.new Team("Team A"));
        teams.add(generator.new Team("Team B"));
        teams.add(generator.new Team("Team C"));
        teams.add(generator.new Team("Team D"));

        // Generate the schedule
        List<Matchup> schedule = generator.generateSchedule(teams);

        // Print the schedule
        System.out.println("Generated Schedule:");
        for (Matchup matchup : schedule) {
            System.out.println(matchup);
        }
    }
}
