<<<<<<< Updated upstream
=======


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

 
public class FileHandler {
   
    private static String fileName = "C:\\Users\\soman\\OneDrive\\Documents\\NEU\\Second Sem\\Algorithm\\Project\\Ranking-System\\EPLData.csv";
    private static String outputFileName = "RankingData.csv";
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
                
                team hometeam = new team(tokens[0]);
                team awayteam = new team(tokens[1]);
                String ftr = tokens[2];
                
                RankingClass obj = new RankingClass(hometeam, awayteam, ftr);
                
                //String data = obj.toString();                
                dataArray.add(obj);                  
            }            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dataArray;

 

    }

 

//    public void demo() {
//        System.out.println("\n\t FileUtil.demo...");
//     
//                
//        //    Creates File object to read and write file
//        FileHandler Fobj = new FileHandler();
//        write_data = Fobj.readTextFile(fileName);    
//        // Write data in file
//        Fobj.writeTextFile(outputFileName, write_data);    
//        
//        //    Print all the object data
//        System.out.println(write_data);
//        
//        System.out.println("\n\t FileUtil.demo...done!");
//    }
        
}
>>>>>>> Stashed changes
