import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageRank {
	//private static Digraph<team> graph;	
	public void calc(Digraph<team> graph)	{	
		int totalNodes = graph.V();
		int edgeCount = graph.E();
	    Map<team,Double> pageRank = new HashMap<>(totalNodes);
	    Map<team,Double> TempPageRank = new HashMap<>(totalNodes);
		final double dampingFactor = 0.85;	
		
		double InitialPageRank;
		double OutgoingLinks=0; 
		//team ExternalNodeNumber;
		//team InternalNodeNumber; 
		int ITERATION_STEP=1;

		InitialPageRank = (double) 1/totalNodes;
		System.out.printf(" Total Number of Nodes :"+totalNodes+"\t Initial PageRank  of All Nodes :"+InitialPageRank+"\n");
	 
		// 0th ITERATION  _ OR _ INITIALIZATION PHASE
		for (team v : graph.neighbors.keySet()) {
			pageRank.put(v,InitialPageRank);
		}   
	    
		System.out.println("\n Initial PageRank Values , 0th Step \n");
		
		for (team v : graph.neighbors.keySet())	{
			System.out.printf("Page Rank of " + v.getTeamName() + " is : " + pageRank.get(v) + "\n");
		} 
	    
		double normDiff = 0;
		double precision = 1e-8;
		while(ITERATION_STEP<=2) {
		//while(normDiff < precision) {
			// Store the PageRank for All Nodes in Temporary Array 
			for (team v : graph.neighbors.keySet())	{
				TempPageRank.put(v, pageRank.get(v));
				pageRank.put(v, 0.0);
			}
	    
			for (team InternalNodeNumber : graph.neighbors.keySet())	{
				for (team ExternalNodeNumber : graph.neighbors.keySet())	{
					//if(this.path[ExternalNodeNumber][InternalNodeNumber] == 1)	{ 
					if (graph.neighbors.get(ExternalNodeNumber).contains(InternalNodeNumber)) {
						OutgoingLinks=0;  // Count the Number of Outgoing Links for each ExternalNodeNumber
						for (team k : graph.neighbors.keySet())	{
							//if(this.path[ExternalNodeNumber][k] == 1 )	{
							if ((graph.neighbors.get(ExternalNodeNumber).contains(k))) {
								OutgoingLinks=OutgoingLinks+1; // Counter for Outgoing Links
							}  
						} 
						// Calculate PageRank   
						double value = pageRank.get(InternalNodeNumber) + TempPageRank.get(ExternalNodeNumber)*(1/OutgoingLinks);
						pageRank.put(InternalNodeNumber, value); 
						
					}
				}  
			}    
	     
			System.out.printf("\n After "+ITERATION_STEP+"th Step \n");
	  
			for (team v : graph.neighbors.keySet())	{
				System.out.printf("Page Rank of " + v.getTeamName() + " is : " + pageRank.get(v) + "\n"); 
			}
	  
			ITERATION_STEP = ITERATION_STEP+1;
		}

		// Add the Damping Factor to PageRank
		for (team v : graph.neighbors.keySet())	{
			double value = (1-dampingFactor)+ dampingFactor*pageRank.get(v);
			pageRank.put(v, value); 
		} 
	  
		// Display PageRank
		System.out.printf("\n Final Page Rank : \n"); 
		for (team v : graph.neighbors.keySet())	{
			System.out.printf(" Page Rank of "+v.getTeamName()+" is :\t"+pageRank.get(v)+"\n"); 
		}	  
	}
}