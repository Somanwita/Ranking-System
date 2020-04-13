import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageRankprev {
	//private static Digraph<team> graph;	
	public void calc(Digraph<team> graph)	{	
		int totalNodes = graph.V();
		int edgeCount = graph.E();
	    Map<team,Double> pageRank = new HashMap<>(totalNodes);
	    Map<team,Double> TempPageRank = new HashMap<>(totalNodes);
		final double dampingFactor = 0.05;	
		
		double InitialPageRank;
		double OutgoingLinks=0; 
		//team ExternalNodeNumber;
		//team InternalNodeNumber; 
		int ITERATION_STEP=1;

		InitialPageRank = (double) 1/totalNodes;
		//System.out.printf(" Total Number of Nodes :"+totalNodes+"\t Initial PageRank  of All Nodes :"+InitialPageRank+"\n");
	 
		// 0th ITERATION  _ OR _ INITIALIZATION PHASE
		for (team v : graph.neighbors.keySet()) {
			pageRank.put(v,InitialPageRank);
		}   
//	    
//		System.out.println("\n Initial PageRank Values , 0th Step \n");
//		
//		for (team v : graph.neighbors.keySet())	{
//			System.out.printf("Page Rank of " + v.getTeamName() + " is : " + pageRank.get(v) + "\n");
//		} 
	    
		double normDiff;
		double precision = 1e-5;
		boolean uptoDate = false;
		//while(ITERATION_STEP<=2) {
		while(!uptoDate) {
			//System.out.println("Inside WHILE");
			// Store the PageRank for All Nodes in Temporary Array 
			for (team v : graph.neighbors.keySet())	{
				TempPageRank.put(v, pageRank.get(v));
				pageRank.put(v, 0.0);
			}
	    
			double dampingTerm = (1 - dampingFactor) / totalNodes; 
			double danglingRank = 0; 
			for (team InternalNodeNumber : graph.neighbors.keySet())	{
				//System.out.println("InternalNodeNumber Entering:- " +InternalNodeNumber.getTeamName());
				double sum = 0;
				for (team ExternalNodeNumber : graph.neighbors.keySet())	{			
					if (graph.neighbors.get(ExternalNodeNumber).contains(InternalNodeNumber)) {
						//System.out.println("ExternalNode Entering "+ExternalNodeNumber.getTeamName());
						//System.out.println("InternalNode"+InternalNodeNumber.getTeamName());
						OutgoingLinks=0;  // Count the Number of Outgoing Links for each ExternalNodeNumber
						for (team k : graph.neighbors.keySet())	{
							if ((graph.neighbors.get(ExternalNodeNumber).contains(k))) {
								OutgoingLinks=OutgoingLinks+1; // Counter for Outgoing Links
								//System.out.println("ExternalNodeNumber:-" +ExternalNodeNumber.getTeamName());
								//System.out.println("OutgoingLinks:-" +k.getTeamName()+ OutgoingLinks);
								//System.out.println("OutgoingLinks:- " +k.getTeamName()+ OutgoingLinks);
							}  
						} 
						if (OutgoingLinks != 0) {
							double val = TempPageRank.get(ExternalNodeNumber)*(1/OutgoingLinks);
							sum += val ;
							//System.out.println("Sum:- "+sum);
						}
						// Calculate PageRank  
						//sum += TempPageRank.get(ExternalNodeNumber)*(1/OutgoingLinks);
						//double value = pageRank.get(InternalNodeNumber) + TempPageRank.get(ExternalNodeNumber)*(1/OutgoingLinks);						
						//pageRank.put(InternalNodeNumber, value);	
					}
					//System.out.println(dampingTerm + dampingFactor * sum);
					pageRank.put(InternalNodeNumber, dampingTerm + dampingFactor * sum); 
					Map<team,Integer> outdegree = graph.outDegree();
					//if (node.getOutDegree() == 0) 
					if (outdegree.get(InternalNodeNumber) == 0) {
					    //danglingRank += node.getNumber(rankAttribute); 
					    danglingRank += TempPageRank.get(InternalNodeNumber); 
					} 
					//System.out.println("Pagerank of " + InternalNodeNumber.getTeamName() + " : " + pageRank.get(InternalNodeNumber));
				}  			
			}    
			danglingRank *= (dampingFactor / totalNodes);
			normDiff = 0; 
			for (team v : graph.neighbors.keySet()) { 
			   double currentRank = TempPageRank.get(v); 
			   double newRank = pageRank.get(v) + danglingRank; 
			   normDiff += Math.abs(newRank - currentRank); 
			   pageRank.put(v, newRank); 
			   System.out.println("Final Pagerank of " + v.getTeamName() + " : " + pageRank.get(v));
			} 
			
			if (normDiff >= precision)	uptoDate = true;
			ITERATION_STEP++; 
		}
			//.out.printf("\n After "+ITERATION_STEP+"th Step \n");
	  
//			for (team v : graph.neighbors.keySet())	{
//				System.out.printf("Page Rank of " + v.getTeamName() + " is : " + pageRank.get(v) + "\n"); 
//			}
	  
//			ITERATION_STEP = ITERATION_STEP+1;

//		// Add the Damping Factor to PageRank
//		for (team v : graph.neighbors.keySet())	{
//			double value = (1-dampingFactor)+ dampingFactor*pageRank.get(v);
//			pageRank.put(v, value); 
//		} 
	  
		// Display PageRank
		System.out.printf("\n Final Page Rank : \n"); 
		for (team v : graph.neighbors.keySet())	{
			System.out.printf(" Page Rank of "+v.getTeamName()+" is :\t"+pageRank.get(v)+"\n"); 
		}	  
	}
}