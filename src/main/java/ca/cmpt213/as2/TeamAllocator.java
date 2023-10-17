package ca.cmpt213.as2;

import java.util.*;

public class TeamAllocator {

    public List<Team> organizeIntoTeams(List<Tokimon> allTokimons) {
        Map<String, Team> teamsMap = new HashMap<>();

        for (Tokimon toki : allTokimons) {
            // System.out.println(teamsMap.toString());
            String teamName = extractTeamName(toki.getName());

            if (teamsMap.containsKey(teamName)) {
                Team existingTeam = teamsMap.get(teamName);

                // Validate and add Tokimon to the existing team
                existingTeam.addTokimon(toki);
                // if (isValidToAdd(toki, existingTeam, allTokimons)) {
                // } else {
                //     System.err.println("Error: Invalid Tokimon properties or missing associations in team: " + teamName);
                //     System.exit(-1);
                // }

            } else {
                // Create a new team and add the Tokimon
                Team newTeam = new Team();
                newTeam.addTokimon(toki);
                teamsMap.put(teamName, newTeam);
            }
        }

        // Further validations such as checking if each Tokimon mentions all other Tokimons in the team
        // for (Team team : teamsMap.values()) {
        //     if (!isValidTeam(team)) {
        //         System.err.println("Error: Inconsistent associations within the team.");
        //         System.exit(-1);
        //     }
        // }

        return new ArrayList<>(teamsMap.values());
    }

    private static String extractTeamName(String tokimonName) {
        // Extract team name from the Tokimon's name (assuming a certain naming convention)
        return tokimonName.split(" ")[0];
    }

    // private static boolean isValidToAdd(Tokimon toki, Team team, List<Tokimon> allTokimons) {
    //     // Check if Tokimon is already in a team
    //     for (Tokimon teamToki : team.getTeam()) {
    //         if (teamToki.getId().equalsIgnoreCase(toki.getId().trim())) {
    //             // Check for consistent properties
    //             if (!teamToki.getName().equalsIgnoreCase(toki.getName().trim())) {
    //                 return false;
    //             }
    //         }
    //     }

    //     // Check if Tokimon is mentioned by others in the team
    //     for (Tokimon otherToki : allTokimons) {
    //         if (otherToki.getCompatibilities() != null) {
    //             for (Compatibility comp : otherToki.getCompatibilities()) {
    //                 if (comp.getComment().contains(toki.getId())) {
    //                     return true;
    //                 }
    //             }
    //         }
    //     }

    //     return false;
    // }

    // private static boolean isValidTeam(Team team) {
    //     // Validate if each Tokimon in the team mentions all other Tokimons
    //     for (Tokimon toki : team.getTeam()) {
    //         for (Tokimon otherToki : team.getTeam()) {
    //             if (!toki.getId().equalsIgnoreCase(otherToki.getId())) {
    //                 boolean isMentioned = false;
    //                 for (Compatibility comp : toki.getCompatibilities()) {
    //                     if (comp.getComment().contains(otherToki.getId())) {
    //                         isMentioned = true;
    //                         break;
    //                     }
    //                 }
    //                 if (!isMentioned) {
    //                     return false;
    //                 }
    //             }
    //         }
    //     }

    //     return true;
    // }

    // public static void main(String[] args) {
    //     // Test the organizeIntoTeams function with your list of Tokimons
    //     List<Tokimon> allTokimons = new ArrayList<>();
    //     // Populate the allTokimons list

    //     List<Team> teams = organizeIntoTeams(allTokimons);
    //     // Now you have a list of organized and validated teams
    // }
}
