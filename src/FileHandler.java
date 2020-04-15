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
   
    Map<team, Integer> totmatches = new HashMap<>();
    Map<team, Integer> totmerginOfVictory = new HashMap<>();
    Map<team, Integer> totmerginOfLoss = new HashMap<>();

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
                
                boolean hometotmatchAdded = false;
                boolean awaytotmatchAdded = false; 
                
                if (totmatches.size() == 0) {
                	totmatches.put(hometeam, 1);
                    totmatches.put(awayteam, 1);	
                    hometotmatchAdded = true;
                    awaytotmatchAdded = true; 
                }
                
                if (hometotmatchAdded == false)   {
                	for (team x : totmatches.keySet()) {
                		if (x.getTeamName().equals(hometeamName)) {
                			int hometotmtch = totmatches.get(x)+1;
                			totmatches.put(x, hometotmtch);
                			hometotmatchAdded = true;
                		}
                	}
                }
                
                if (awaytotmatchAdded == false) {
                 	for (team x : totmatches.keySet()) {
                		if (x.getTeamName().equals(awayteamName)) {
                			int awaytotmtch = totmatches.get(x)+1;
                			totmatches.put(x, awaytotmtch);
                			awaytotmatchAdded = true;
                		}
                	}
                }
                
                if (hometotmatchAdded == false &&  awaytotmatchAdded == false) {
                	totmatches.put(hometeam, 1);
                	hometotmatchAdded = true;
                    totmatches.put(awayteam, 1);
                    awaytotmatchAdded = true;
                }

                boolean hometotmerginVictory = false;
                boolean awaytotmerginVictory = false; 
                
                if (totmerginOfVictory.size() == 0) {
                	totmerginOfVictory.put(hometeam, homenoOfgoals - awaynoOfgoals);
                	totmerginOfVictory.put(awayteam, awaynoOfgoals - homenoOfgoals);	
                	hometotmerginVictory = true;
                	awaytotmerginVictory = true; 
                }
                
                if (hometotmerginVictory == false)   {
                	for (team y : totmerginOfVictory.keySet()) {
                		if (y.getTeamName().equals(hometeamName)) {
                			int hometotmtch = totmerginOfVictory.get(y)+(homenoOfgoals - awaynoOfgoals);
                			totmerginOfVictory.put(y, hometotmtch);
                			hometotmerginVictory = true;
                		}
                	}
                }
                
                if (awaytotmerginVictory == false) { 
                	for (team y : totmerginOfVictory.keySet()) {
                		 if (y.getTeamName().equals(awayteamName)) {
                			int awaytotmtch = totmerginOfVictory.get(y)+(awaynoOfgoals - homenoOfgoals);
                			totmerginOfVictory.put(y, awaytotmtch);
                			awaytotmerginVictory = true;
                		}
                	}
                }              
                if (hometotmerginVictory == false &&  awaytotmerginVictory == false) {
                	totmerginOfVictory.put(hometeam, (homenoOfgoals - awaynoOfgoals));
                	hometotmerginVictory = true;
                	totmerginOfVictory.put(awayteam, (awaynoOfgoals - homenoOfgoals));
                	awaytotmerginVictory = true;
                }               
                RankingClass obj = new RankingClass(hometeam, awayteam, ftr);           
                dataArray.add(obj);                  
            }            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dataArray;

    }
        
}