import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Driver {

    public static void main(String[] args) {
    	Digraph<team> graph = new Digraph<>();
    	    	
    	FileHandler filehandler = new FileHandler();
    	List<RankingClass> inputData= new ArrayList<>();
    	
    	
    	inputData = filehandler.readTextFile();
    	for (RankingClass data : inputData) {
    		if (data.getFtr().equals("H"))	{
    			graph.add(data.getAwayTeam(), data.getHomeTeam());
    		}
    		else if (data.getFtr().equals("A"))	{
    			graph.add(data.getHomeTeam(), data.getAwayTeam());
    		}
    		else if (data.getFtr().equals("D"))	{
//    			graph.add(data.getHomeTeam(), data.getAwayTeam());
//    			graph.add(data.getAwayTeam(), data.getHomeTeam());
    		}
    	}
       
     //   System.out.println("The current graph: " + graph.toString());
             
        System.out.println("Vertices:" + graph.V());
        System.out.println("Edges" + graph.E());
               
        PageRank pgobj = new PageRank();
        pgobj.calc(graph, filehandler);
        
    }

}
