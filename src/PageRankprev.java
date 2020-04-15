import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageRankprev {
	public void calc(Digraph<team> graph, FileHandler filehandler)	{	
		int totalNodes = graph.V();
		int edgeCount = graph.E();
	    Map<team,Double> pageRank = new HashMap<>(totalNodes);
	    Map<team,Double> TempPageRank = new HashMap<>(totalNodes);
		final double dampingFactor = 0.85;	
		
		double InitialPageRank;
		double OutgoingLinks=0; 
		int ITERATION_STEP=1;

		InitialPageRank = (double) 1/totalNodes;
	 
		// 0th ITERATION  _ OR _ INITIALIZATION PHASE
		for (team v : graph.neighbors.keySet()) {
			pageRank.put(v,InitialPageRank);
		}   
   
		double normDiff;
		double precision = 1e-5;
		boolean uptoDate = false;
		while(!uptoDate) {
			for (team v : graph.neighbors.keySet())	{
				TempPageRank.put(v, pageRank.get(v));
				pageRank.put(v, 0.0);
			}
	    
			double dampingTerm = (1 - dampingFactor) / totalNodes; 
			double danglingRank = 0; 
			for (team InternalNodeNumber : graph.neighbors.keySet())	{
				double sum = 0;
				for (team ExternalNodeNumber : graph.neighbors.keySet())	{	
					if (graph.neighbors.get(ExternalNodeNumber).contains(InternalNodeNumber)) {
						OutgoingLinks=0;  
						for (team k : graph.neighbors.keySet())	{
						//	if ((graph.neighbors.get(ExternalNodeNumber).contains(k))) {
							for (team l : graph.neighbors.get(ExternalNodeNumber)) {
								if (l.equals(k))
								OutgoingLinks=OutgoingLinks+1; 
							}  
						} 
						if (OutgoingLinks > 3) {
							double val = TempPageRank.get(ExternalNodeNumber)*(1/OutgoingLinks);
							sum += val ;
						}
						else {
							double val = TempPageRank.get(ExternalNodeNumber)*(1/2);
							sum += val ;
						}
					}
					pageRank.put(InternalNodeNumber, dampingTerm + dampingFactor * sum); 
					Map<team,Integer> outdegree = graph.outDegree();
					if (outdegree.get(InternalNodeNumber) == 0) {
					    danglingRank += TempPageRank.get(InternalNodeNumber); 
					} 
				}  			
			}    
			danglingRank *= (dampingFactor / totalNodes);
			normDiff = 0; 
			for (team v : graph.neighbors.keySet()) { 
			   double currentRank = TempPageRank.get(v); 
			   double newRank = pageRank.get(v) + danglingRank; 
			   normDiff += Math.abs(newRank - currentRank); 
			   pageRank.put(v, newRank); 
			} 
			
			if (normDiff >= precision)	uptoDate = true;
			ITERATION_STEP++; 
		} 
		
		LinkedHashMap<team, Double> sortedpageRank = new LinkedHashMap<>();
				pageRank.entrySet()
						.stream()
						.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
						.forEachOrdered(x -> sortedpageRank.put(x.getKey(), x.getValue()));
				
		// Display PageRank
		System.out.printf("\n Final Page Rank : \n"); 
		
		for (team v : sortedpageRank.keySet())	{
			System.out.printf(" Page Rank of "+v.getTeamName()+" is :\t"+sortedpageRank.get(v)+"\n"); 
		}
		
		
		
		for (team v : graph.neighbors.keySet())	{
			for (team w : graph.neighbors.keySet()) {
				if (!v.getTeamName().equals(w.getTeamName())) {
					double vp = sortedpageRank.get(v)/(sortedpageRank.get(v) + sortedpageRank.get(w));
					double wp = sortedpageRank.get(w)/(sortedpageRank.get(v) + sortedpageRank.get(w));
					System.out.println();
					System.out.println("Prediction of Result for Match between " + v.getTeamName() + " and " + w.getTeamName());
					System.out.println("---------------------------------------------------------------------");
					System.out.println("Probability of Win of Team " + v.getTeamName() + " is : " + vp); 
					System.out.println("Probability of Win of Team " + w.getTeamName() + " is : " + wp); 
					
					if (vp > wp)	{
						System.out.println(v.getTeamName() + " team should win!!");
						double avgmerginOfVictoryWin = filehandler.totmerginOfVictory.get(v)/filehandler.totmatches.get(v);
						System.out.println("Average mergin of victory of winning team " + v.getTeamName() + " is : " + avgmerginOfVictoryWin);			
						System.out.println("Total number of matches played by winning team " + w.getTeamName() + " is : " + filehandler.totmatches.get(v));
						
						double avgmerginOfVictoryLoss = filehandler.totmerginOfVictory.get(w)/filehandler.totmatches.get(w);
						System.out.println("Average mergin of victory of loosing team " + w.getTeamName() + " is : " + avgmerginOfVictoryLoss);
						System.out.println("Total number of matches played by loosing team " + w.getTeamName() + " is : " + filehandler.totmatches.get(w));

					}
					else if (vp < wp) {
						System.out.println(w.getTeamName() + " team should win!!");
						double avgmerginOfVictoryWin = filehandler.totmerginOfVictory.get(w)/filehandler.totmatches.get(w);
						System.out.println("Average mergin of victory of winning team " + w.getTeamName() + " is : " + avgmerginOfVictoryWin);
						System.out.println("Total number of matches played by winning team " + w.getTeamName() + " is : " + filehandler.totmatches.get(w));
						
						double avgmerginOfVictoryLoss = filehandler.totmerginOfVictory.get(v)/filehandler.totmatches.get(v);
						System.out.println("Average mergin of victory of loosing team " + v.getTeamName() + " is : " + avgmerginOfVictoryLoss);
						System.out.println("Total number of matches played by loosing team " + v.getTeamName() + " is : " + filehandler.totmatches.get(v));

					}
					else {
						System.out.println("Both team have same chance of win!!");
					}
					System.out.println();
				}
			}
		}
		
  
	}
}