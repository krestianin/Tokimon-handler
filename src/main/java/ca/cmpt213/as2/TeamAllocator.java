package ca.cmpt213.as2;

import java.util.*;

// Validate and add Tokimon to the existing team
public class TeamAllocator {

    public List<Team> organizeIntoTeams(List<Tokimon> allTokimons) {
        Map<String, Team> teamsMap = new HashMap<>();

        for (Tokimon toki : allTokimons) {
            String teamName = extractTeamName(toki.getName());
            
            if (teamsMap.containsKey(teamName)) {
                Team existingTeam = teamsMap.get(teamName);
                // test for : Tokimon S is mentioned by Tokimons who are in two different teams.
                // testForDifferentTeams();
                // testForDifferentTeams();
                if(!existingTeam.getTeam().contains(toki))
                {
                    existingTeam.addTokimon(toki);
                }
                else 
                {
                    System.err.println("Error: Duplication of ID " + toki.getId());
                    System.exit(-1);
                }
        

            } else {
                // Create a new team and add the Tokimon
                Team newTeam = new Team();
                newTeam.addTokimon(toki);
                teamsMap.put(teamName, newTeam);
            }
        }
        Boolean isError = false;
        // Further validations 
        for (Team team : teamsMap.values()) {
            for (Tokimon toki : team.getTeam()) {
                
                // Check if a Tokimon is mentioned by Tokimons in two different teams
                if (isMentionedInMultipleTeams(toki, teamsMap)) {
                    System.err.println("Error: Tokimon " + toki.getId() + " is mentioned by Tokimons in multiple teams.");
                    isError = true;
                }

                // Check if Tokimon properties are consistent across different JSON files
                if (!hasConsistentProperties(toki, team)) {
                    System.err.println("Error: Inconsistent properties for Tokimon " + toki.getId() + ".");
                    isError = true;
                }
            }
            if (!isValidMention(team)) {
                System.err.println("Error: Not every Tokimon mentions all other Tokimons in the " + extractTeamName(team.getTeam().get(0).getName()));
                isError = true;
            }
            if (!validateCompatibilities(team)) {
                System.err.println("Error: Compatibility ID does not match any existing Tokimon in the " + extractTeamName(team.getTeam().get(0).getName()));
                isError = true;
            }
            if (!doesHaveFiles(team)) {
                System.err.println("Error: Tokimon who is mentioned in the JSON file of another Tokimon in the team fails to submit a JSON file. " + extractTeamName(team.getTeam().get(0).getName()));
                isError = true;
            }

        }
    
        if(isError) System.exit(-1);
        return new ArrayList<>(teamsMap.values());
    }
    // Extract team name from the Tokimon's name 
    private static String extractTeamName(String tokimonName) {
        return tokimonName.split(" ")[0];
    }



    // Validate if each Tokimon in the team mentions all other Tokimons
    private static boolean isValidMention(Team team) {
        for (Tokimon toki : team.getTeam()) {
            for (Tokimon otherToki : team.getTeam()) {
                if (!toki.getId().equalsIgnoreCase(otherToki.getId())) {
                    boolean isMentioned = false;
                    for (String key : toki.getCompatibilities().keySet()) {
                        // System.out.println(toki.getCompatibilities().get(key).getComment());
                        // System.out.println(otherToki.getName());
                        if (toki.getCompatibilities().get(key).getComment().contains(otherToki.getName()) && 
                        key.equalsIgnoreCase(otherToki.getId())) {
                            // System.out.println(toki.getCompatibilities().get(key).getComment().contains(otherToki.getId()));
                            isMentioned = true;
                            break;
                        }
                    }
                    if (!isMentioned) {
                        return false;
                    }
                }
            }
        }

        return true;
    }


    private static boolean validateCompatibilities(Team team) {
        for (Tokimon toki : team.getTeam()) {
            Map<String, Compatibility> compatibilities = toki.getCompatibilities();

            for (String compatibilityId : compatibilities.keySet()) {
                boolean idMatchFound = false;

                for (Tokimon teamToki : team.getTeam()) {
                    if (compatibilityId.trim().equalsIgnoreCase(teamToki.getId().trim())) {
                        idMatchFound = true;
                        break;
                    }
                }

                if (!idMatchFound) {
                    return false;
                }
            }
        }
        return true;
    }


    // Check if any Tokimon who is mentioned in the JSON file of another Tokimon in the team fails to submit a JSON file.
    private static boolean doesHaveFiles(Team team) {
        for (int i = 0; i < team.getTeam().size(); i++) {
            if(team.getTeam().get(i).getCompatibilities().size() + 1 >  team.getTeam().size())
            {
                return false;
            }
        }
        // System.out.println( team.getTeam().get(0).getCompatibilities().size());
        return true;
    }
    // Check if a Tokimon is mentioned by Tokimons in two different teams
    private boolean isMentionedInMultipleTeams(Tokimon toki, Map<String, Team> teamsMap) {
        int mentionedTeamsCount = 0;
        for (Team team : teamsMap.values()) {
            for (Tokimon teamToki : team.getTeam()) {
                if (teamToki.getCompatibilities().containsKey(toki.getId().toLowerCase())) {
                    mentionedTeamsCount++;
                    if (mentionedTeamsCount > 1) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    // Check if Tokimon properties are consistent across different JSON files
    private boolean hasConsistentProperties(Tokimon toki, Team team) {
        for (Tokimon teamToki : team.getTeam()) {
            // Check if the Tokimon is mentioned in the team
            for (String key : teamToki.getCompatibilities().keySet()) {
                if (key.trim().equalsIgnoreCase(toki.getId().trim())) {
                    if(!teamToki.getCompatibilities().get(key).getComment().split("'")[3].trim().equalsIgnoreCase(toki.getName().trim())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    // private boolean hasConsistentComment(Tokimon toki, Team team) {
    //     for (Tokimon teamToki : team.getTeam()) {
    //         // Check if the Tokimon is mentioned in the team
    //         for (String key : teamToki.getCompatibilities().keySet()) {
    //             if (key.trim().equalsIgnoreCase(toki.getId().trim())) {
    //                 if(!teamToki.getCompatibilities().get(key).getComment().split("'")[3].trim().equalsIgnoreCase(toki.getName().trim())) {
    //                     return false;
    //                 }
    //             }
    //         }
    //     }
    //     return true;
    // }
    


}
