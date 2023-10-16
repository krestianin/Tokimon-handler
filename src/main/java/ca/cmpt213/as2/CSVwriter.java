package ca.cmpt213.as2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVwriter {
    public static void writeTeamToCSV(Team team, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.append("Team#,From Toki,To Toki,Score,Comment,,Extra\n");

            // Assuming team number is 1 for simplicity
            writer.append("Team 1,,,,,,\n");

            List<Tokimon> tokimons = team.getTeam();
    
            

            tokimons.sort((a, b) -> a.getId().compareToIgnoreCase(b.getId()));

            for (Tokimon toki : tokimons) {
                int compatibilityIndex = 0;
                for (Tokimon otherToki : tokimons) {
                    if (!toki.getId().equalsIgnoreCase(otherToki.getId())) {
                        if (compatibilityIndex < toki.getCompatibilities().size()) {
                        Compatibility compatibility = toki.getCompatibilities().get(compatibilityIndex);
            
                        writer.append(",");
                        writer.append(toki.getId());
                        writer.append(",");
                        writer.append(otherToki.getId());
                        writer.append(",");
                        writer.append(String.valueOf(compatibility.getScore()));
                        writer.append(",");
                        writer.append("\"" + compatibility.getComment().replace("\"", "\"\"") + "\"");
                        writer.append(",,");
                        writer.append("\n");
                        
                        compatibilityIndex++; // Increment the index after each compatibility is processed
                        }
                    }
                }
                writer.append(",");
                writer.append(toki.getId());
                writer.append(",");
                writer.append("-");
                writer.append(",");
                writer.append(String.valueOf(toki.getCompatibility().getScore()));
                writer.append(",");
                writer.append("\"" + toki.getCompatibility().getComment().replace("\"", "\"\"") + "\"");
                // writer.append(toki.getCompatibility().getComment());
                // writer.append("\"" + toki.getCompatibility().getComment().replace("\"", "\"\"") + "\"");

                writer.append(",,");
                writer.append(toki.getExtraComment());
                writer.append("\n");
                // writer.append(",");
                // writer.append(toki.getId());
                // writer.append(",");
                // writer.append("-");
                // writer.append(",");
                // writer.append(String.valueOf(toki.getCompatibility().getScore()));
                // writer.append(",");
                // writer.append(toki.getCompatibility().getComment());
                // writer.append(",,");
          
                // writer.append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + outputPath);
            e.printStackTrace();
        }
    }
}
