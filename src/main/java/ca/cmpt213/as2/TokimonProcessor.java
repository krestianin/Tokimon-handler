package ca.cmpt213.as2;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

// Finds all JSON files in a folder and processes them to create the teams.  
//It then generates a .CSV file which a handler can look at and evaluate each team’s strengths and weaknesses.

public class TokimonProcessor {

    static List<File> files = new ArrayList<File>();
    static List<Team> Teams = new ArrayList<Team>();
    public static void main(String[] args) {

      if (args.length != 2) {
        System.err.println("Error: Incorrect number of arguments provided.");
        System.err.println("Expected: [Path to input JSON files] [Path to output directory]");
        System.exit(-1);
    }
        File folder = new File(args[0]);
        String output = args[1];
        File arr[] = folder.listFiles();

        JSONFilter(arr, 0);
        
        if(arr.length != 0)
            readFiles(files, output);
        else{
            System.err.println("Error: Input file is empty");
        }
    //   File[] subFolders = folder.listFiles();
    //   // System.out.println(folder.isDirectory());
    //   if (subFolders != null) {
    //       for (File subFolder : subFolders)
    //       {
              
    //       }
    //   }
    }


    private static void readFiles(List<File> files2, String output){
        if (files2 == null) {
            System.err.println("Error: No JSON files found.");
            System.exit(-1);
        }

        Gson gson = new Gson();
        List<Tokimon> teamTokimons = new ArrayList<>();

        for(File subfile : files2)
        {
            try (Reader json = new FileReader(subfile)) {
                
                // Team t = gson.fromJson(json, Team.class); 
                // Tokimon toki = t.getTeam().get(0);
                // toki.setExtraComment(t.getExtra_comments());
                // // Validate toki and its compatibilities...
                // teamTokimons.addAll(t.getTeam());
                Team t = gson.fromJson(json, Team.class); 
        
                // Assuming the first Tokimon in the team list is the main Tokimon
                Tokimon mainToki = t.getTeam().get(0);
                mainToki.setExtraComment(t.getExtra_comments());
                Map<String, Compatibility> mapOfCompatibilities = new HashMap<>();
                for (int i = 1; i < t.getTeam().size(); i++)
                {
                    checkForDuplicateTokimons(t.getTeam().get(i), mapOfCompatibilities);
                    mapOfCompatibilities.put(t.getTeam().get(i).getId(),t.getTeam().get(i).getCompatibility());
                    // System.out.println(listOfCompatibilities.get(i).getScore());
                }
               
                // System.out.println(listOfCompatibilities);
                mainToki.setCompatibilities(mapOfCompatibilities);
                // System.out.println(t.getTeam().get(0).getCompatibilities().get(0).getScore());
                // Add compatibilities for the main Tokimon
                // mainToki.setCompatibilities(t.getTeam().subList(1, t.getTeam().size()));
                
                // Add the main Tokimon to the list of all Tokimons
                teamTokimons.add(mainToki);
                int c = 0; 
                for(Tokimon tok : teamTokimons)
                {
                    if((tok.getId().equals(mainToki.getId()) || tok.getName().equals(mainToki.getName())))
                    {
                        c++;
                    }
                    if(c > 1) 
                    {
                        System.err.println("Error: Duplication of ID " + mainToki.getId());
                        System.exit(-1);
                    }
                }
            

            } catch (IOException e) {
                System.err.println("Error: Reading file." + subfile);
                System.exit(-1);
            }
        }
        


        // Team combinedTeam = new Team();
        // combinedTeam.setTeam(teamTokimons);
        TeamAllocator ta = new TeamAllocator();
        List<Team> teams = ta.organizeIntoTeams(teamTokimons);

        CSVwriter.writeTeamToCSV(teams, output+"/team_info.csv");
     
    
    }

    private static void checkForDuplicateTokimons(Tokimon toki, Map<String, Compatibility> mapOfCompatibilities) {
        String tokiId = toki.getId().trim().toLowerCase();

        if (mapOfCompatibilities.containsKey(tokiId)) {
            System.err.println("Error: Tokimon with ID " + toki.getId() + " already exists in the map.");
            System.exit(-1);
        } else {
            // If the Tokimon is not in the map, you can add it.
            mapOfCompatibilities.put(tokiId, toki.getCompatibility());
        }
    }

    public static void JSONFilter(File[] arr, int index)
    {
        if (index == arr.length)
            return;

        if (arr[index].isFile())
        {
            // System.out.println("file");
            FileFilter jsonFilter = new FileFilter() {
                public boolean accept(File file) {
                    return file.getName().toLowerCase().endsWith(".json");
                }
            };
            // File[] fileList = folder.listFiles(jsonFilter);
            if (jsonFilter.accept(arr[index])) {
                // Process the JSON file
                


                //add to array of files 
                files.add(arr[index]);

                //readFile(arr[index]);
            } 

        }

        else if (arr[index].isDirectory()) { 
            // System.out.println("folder");
            // recursion for sub-directories
            JSONFilter(arr[index].listFiles(), 0);
        }
 
        // recursion for main directory
        JSONFilter(arr, ++index);
  
    }
}
