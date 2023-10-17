package ca.cmpt213.as2;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;


public class TokimonProcessor {

    static List<File> files = new ArrayList<File>();
    static List<Team> Teams = new ArrayList<Team>();
    public static void main(String[] args) {
      // "./testdata/InputTestDataSets"
      if (args.length != 2) {
        System.err.println("Error: Incorrect number of arguments provided.");
        System.err.println("Expected: [Path to input JSON files] [Path to output directory]");
        System.exit(-1);
    }
        File folder = new File(args[0]);
        String output = args[1];
        File arr[] = folder.listFiles();

        JSONFilter(arr, 0);
  
        readFiles(files, output);
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
            System.out.println();
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
                    mapOfCompatibilities.put(t.getTeam().get(i).getId(),t.getTeam().get(i).getCompatibility());
                    // System.out.println(listOfCompatibilities.get(i).getScore());
                }
                
                // Compatibility temp = listOfCompatibilities.get(0);
                // listOfCompatibilities.set(0, listOfCompatibilities.get(listOfCompatibilities.size()-1));
                // listOfCompatibilities.set(listOfCompatibilities.size()-1, temp);


                // System.out.println(listOfCompatibilities);
                mainToki.setCompatibilities(mapOfCompatibilities);
                // System.out.println(t.getTeam().get(0).getCompatibilities().get(0).getScore());
                // Add compatibilities for the main Tokimon
                // mainToki.setCompatibilities(t.getTeam().subList(1, t.getTeam().size()));
                
                // Add the main Tokimon to the list of all Tokimons
                teamTokimons.add(mainToki);
                
                // **** validate compatibilities with others

                // for (Tokimon td : teamTokimons)
                // System.out.println(td.getName());

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
