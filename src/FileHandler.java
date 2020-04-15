import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
 
public class FileHandler {
   
    Map<String, Integer> totmatches = new HashMap<>();
    Map<String, Integer> totmerginOfVictory = new HashMap<>();
    Map<String, Integer> totmerginOfLoss = new HashMap<>();
    
    Map<String, Integer> maxmerginOfVictory = new HashMap<>();
    Map<String, Integer> maxmerginOfLoss = new HashMap<>();

    private static String fileName = "EdgeEPLData.csv";
    //private static String outputFileName = "RankingData.csv";
    public List<String> write_data = new ArrayList<String>();

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
 
    /**
     * Write csv file
     * @param fileName
     * @param write_data : a list of strings
     * Returns nothing
     */
    public void writeTextFile(String outputFileName, List<String> write_data)    {      
        try(BufferedWriter out = new BufferedWriter( new FileWriter(outputFileName, true))) {
            /**
             * write a line at a time 
             */
                out.write("OUTPUT STARTS HERE");
                out.write("\n");
                for (String line : write_data) {
                    out.write(line);
                    out.newLine();
                }        
        }
        catch (Exception e) {
            e.printStackTrace();
        }        
    }    
    
    /**
    * Read csv file
    * @param fileName
    * Returns dataArray which is a list of strings
    */    
    public List<RankingClass> readTextFile() {              
        List<RankingClass> dataArray = new ArrayList<RankingClass>();          
        try {
            String thisLine = null;
            FileReader fr;
            fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);           
            br.readLine();
            while ((thisLine = br.readLine()) != null) {    
                
                String[] tokens = thisLine.split(COMMA_DELIMITER);                
                
//                team hometeam = new team(tokens[0]);
//                team awayteam = new team(tokens[1]);
//                String ftr = tokens[2];
                
                String hometeamName = tokens[0];
                String awayteamName = tokens[1];
                int homenoOfgoals = Integer.parseInt(tokens[2]);
                int awaynoOfgoals = Integer.parseInt(tokens[3]);
                String ftr = tokens[4];
                
                team hometeam = new team(hometeamName);                           
                team awayteam = new team(awayteamName);
                
                RankingClass obj = new RankingClass(hometeam, awayteam, ftr);           
                dataArray.add(obj);   
                
                /*
                 * Calculate total number of match for each team
                 */
                if (totmatches.size() == 0) {
                	totmatches.put(hometeamName, 1);
                	totmatches.put(awayteamName, 1);
                }
                else if (totmatches.containsKey(hometeamName) && totmatches.containsKey(awayteamName)) {
                	totmatches.put(hometeamName, totmatches.get(hometeamName) + 1);
                	totmatches.put(awayteamName, totmatches.get(awayteamName) + 1);              		
                	}
                else {
                	totmatches.put(hometeamName, 1);
                	totmatches.put(awayteamName, 1);
                }
                
                /*
                 * Calculate total Margin Of Victory for each team
                 */
                int hometeammerginofVictory = homenoOfgoals - awaynoOfgoals;
                int awayteammerginofVictory = awaynoOfgoals - homenoOfgoals;
                               
                if (totmerginOfVictory.size() == 0) {
                	totmerginOfVictory.put(hometeamName, hometeammerginofVictory);
                	totmerginOfVictory.put(awayteamName, awayteammerginofVictory);
                }
                else if (totmerginOfVictory.containsKey(hometeamName) && totmerginOfVictory.containsKey(awayteamName)) {
                	totmerginOfVictory.put(hometeamName, totmerginOfVictory.get(hometeamName) + hometeammerginofVictory);
                	totmerginOfVictory.put(awayteamName, totmerginOfVictory.get(awayteamName) + awayteammerginofVictory);              		
                	}
                else {
                	totmerginOfVictory.put(hometeamName, hometeammerginofVictory);
                	totmerginOfVictory.put(awayteamName, awayteammerginofVictory);
                }
                
                /*
                 * Calculate total Margin Of Loss for each team
                 */
                int hometeammerginofLoss = awaynoOfgoals - homenoOfgoals;
                int awayteammerginofLoss = homenoOfgoals - awaynoOfgoals;
                
                if (totmerginOfLoss.size() == 0) {
                	totmerginOfLoss.put(hometeamName, hometeammerginofLoss);
                	totmerginOfLoss.put(awayteamName, awayteammerginofLoss);
                }
                else if (totmerginOfLoss.containsKey(hometeamName) && totmerginOfLoss.containsKey(awayteamName)) {
                	totmerginOfLoss.put(hometeamName, totmerginOfLoss.get(hometeamName) + hometeammerginofLoss);
                	totmerginOfLoss.put(awayteamName, totmerginOfLoss.get(awayteamName) + awayteammerginofLoss);              		
                	}
                else {
                	totmerginOfLoss.put(hometeamName, hometeammerginofLoss);
                	totmerginOfLoss.put(awayteamName, awayteammerginofLoss);
                }
                            
                /*
                 * Calculate Maximum Margin Of Victory for each team
                 */
                if (maxmerginOfVictory.size() == 0) {
                	maxmerginOfVictory.put(hometeamName, hometeammerginofVictory);
                	maxmerginOfVictory.put(awayteamName, awayteammerginofVictory);
                }
                else if (maxmerginOfVictory.containsKey(hometeamName) && maxmerginOfVictory.containsKey(awayteamName)) {
                	maxmerginOfVictory.put(hometeamName, Math.max(maxmerginOfVictory.get(hometeamName), hometeammerginofVictory));
                	maxmerginOfVictory.put(awayteamName, Math.max(maxmerginOfVictory.get(awayteamName), awayteammerginofVictory));              		
                	}
                else {
                	maxmerginOfVictory.put(hometeamName, hometeammerginofVictory);
                	maxmerginOfVictory.put(awayteamName, awayteammerginofVictory);
                }
                
                /*
                 * Calculate Maximum Margin Of Loss for each team
                 */
                if (maxmerginOfLoss.size() == 0) {
                	maxmerginOfLoss.put(hometeamName, hometeammerginofLoss);
                	maxmerginOfLoss.put(awayteamName, awayteammerginofLoss);
                }
                else if (maxmerginOfLoss.containsKey(hometeamName) && maxmerginOfLoss.containsKey(awayteamName)) {
                	maxmerginOfLoss.put(hometeamName, Math.max(maxmerginOfLoss.get(hometeamName), hometeammerginofLoss));
                	maxmerginOfLoss.put(awayteamName, Math.max(maxmerginOfLoss.get(awayteamName), hometeammerginofLoss));              		
                	}
                else {
                	maxmerginOfLoss.put(hometeamName, hometeammerginofLoss);
                	maxmerginOfLoss.put(awayteamName, hometeammerginofLoss);
                }
                
            }            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dataArray;

    }
        
}