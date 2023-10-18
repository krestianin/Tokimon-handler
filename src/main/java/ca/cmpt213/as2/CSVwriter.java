package ca.cmpt213.as2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
// Generate a comma separated value file (.csv) which contains the outputting data
public class CSVwriter {
    public static void writeTeamToCSV(List<Team> teamsArray, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.append("Team#,From Toki,To Toki,Score,Comment,,Extra\n");
            int i = 1;
            for (Team team : teamsArray)
            {
                writer.append("Team ");
                writer.append(String.valueOf(i++));
                writer.append(",,,,,,\n");

                List<Tokimon> tokimons = team.getTeam();
        
                

                tokimons.sort((a, b) -> a.getId().compareToIgnoreCase(b.getId()));

                for (Tokimon toki : tokimons) {
                    for (String key : toki.getCompatibilities().keySet()) {
                        // String toTokiId = extractTokiIdFromComment(toki.getCompatibilities().get(key).getComment());

                        writer.append(",");
                        writer.append(toki.getId());
                        writer.append(",");
                        writer.append(key);
                        writer.append(",");
                        writer.append(String.valueOf(toki.getCompatibilities().get(key).getScore()));
                        writer.append(",");
                        writer.append("\"" + toki.getCompatibilities().get(key).getComment().replace("\"", "\"\"") + "\"");
                        writer.append(",,");
                        writer.append("\n");
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
          
                }

            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + outputPath);
            e.printStackTrace();
        }
    }

}
