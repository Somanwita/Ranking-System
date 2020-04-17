import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageRank {
	public void calc(Digraph<team> graph, FileHandler filehandler)	{	
		int totalNodes = graph.V();
		int edgeCount = graph.E();
	    Map<team,Double> pageRank = new HashMap<>(totalNodes);
	    Map<team,Double> TempPageRank = new HashMap<>(totalNodes);
		final double dampingFactor = 0.85;	
		normalDistri normalDist = new normalDistri();
		
		double InitialPageRank;
		double OutgoingLinks=0; 
		int ITERATION_STEP=1;

		InitialPageRank = (double) 1/totalNodes;
	 
		/*
		 *  At Initialization Phase all nodes have equal weights
		 */
		for (team v : graph.neighbors.keySet()) {
			pageRank.put(v,InitialPageRank);
		}   
   
		/*
		 * Calculate Pagerank of each node
		 */
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
							double val = TempPageRank.get(ExternalNodeNumber)*(1/1.6);
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
				
//		// Display PageRank
//		System.out.printf("\n Final Page Rank : \n"); 		
//		for (team v : sortedpageRank.keySet())	{
//			System.out.printf(" Page Rank of "+v.getTeamName()+" is :\t"+sortedpageRank.get(v)+"\n"); 
//		}
		
		// Display Ranking order
		System.out.printf("\nRank\tTeam Name \tPageRank\n"); 		
		int i = 1;
		for (team v : sortedpageRank.keySet())	{
			System.out.printf(i + "\t"+v.getTeamName() + "  \t"+ sortedpageRank.get(v)+"\n"); 
			i++;
		}	
		
		/*
		 * Display Prediction of matches for each pair of team
		 */
		
//		for (team v : graph.neighbors.keySet())	{
//			for (team w : graph.neighbors.keySet()) {
//				if (!v.getTeamName().equals(w.getTeamName())) {
//					double vp = sortedpageRank.get(v)/(sortedpageRank.get(v) + sortedpageRank.get(w));
//					double wp = sortedpageRank.get(w)/(sortedpageRank.get(v) + sortedpageRank.get(w));
//					System.out.println();
//					System.out.println("Prediction of Result for Match between " + v.getTeamName() + " and " + w.getTeamName());
//					System.out.println("---------------------------------------------------------------------");
//					System.out.println("Probability of Win of Team " + v.getTeamName() + " is : " + vp); 
//					System.out.println("Probability of Win of Team " + w.getTeamName() + " is : " + wp); 
//					
//					if (vp > wp)	{
//						System.out.println(v.getTeamName() + " team should win!!");
//						
//						double avgmarginOfVictoryWinning = filehandler.totmarginOfVictory.get(v.getTeamName())/filehandler.totmatches.get(v.getTeamName());
//						double avgmarginOfLossWinnning = filehandler.totmarginOfLoss.get(v.getTeamName())/filehandler.totmatches.get(v.getTeamName());
//						
//						System.out.println("Total margin of victory of winning team " + v.getTeamName() + " is : +  " + filehandler.totmarginOfVictory.get(v.getTeamName()));
//						System.out.println("Total margin of loss of winning team " + v.getTeamName() + " is : +  " + filehandler.totmarginOfLoss.get(v.getTeamName()));						
//						System.out.println("Average margin of Victory of winning team " + v.getTeamName() + " is : " + avgmarginOfVictoryWinning);			
//						System.out.println("Average margin of Loss of winning team " + v.getTeamName() + " is : " + avgmarginOfLossWinnning);
//						System.out.println("Maximum margin of Victory of winning team " + v.getTeamName() + " is : " + filehandler.maxmarginOfVictory.get(v.getTeamName()));			
//						System.out.println("Maximum margin of Loss of winning team " + v.getTeamName() + " is : " + filehandler.maxmarginOfLoss.get(v.getTeamName()));
//						System.out.println("Total number of matches played by winning team " + v.getTeamName() + " is : " + filehandler.totmatches.get(v.getTeamName()));
//						
//						System.out.println();
//						
//						double avgmarginOfVictoryLoosing = filehandler.totmarginOfVictory.get(w.getTeamName())/filehandler.totmatches.get(w.getTeamName());
//						double avgmarginOfLossLoosing = filehandler.totmarginOfLoss.get(w.getTeamName())/filehandler.totmatches.get(w.getTeamName());
////						double val = normalDist.calculateND(4, 0.392857143, 2.02377451); 
////						System.out.println(val);
//						System.out.println("Total margin of victory of loosing team " + w.getTeamName() + " is : " + filehandler.totmarginOfVictory.get(w.getTeamName()));
//						System.out.println("Total margin of loss of loosing team " + w.getTeamName() + " is : " + filehandler.totmarginOfLoss.get(w.getTeamName()));
//						System.out.println("Average margin of Victory of loosing team " + w.getTeamName() + " is : " + avgmarginOfVictoryLoosing);
//						System.out.println("Average margin of Loss of loosing team " + w.getTeamName() + " is : " + avgmarginOfLossLoosing);
//						System.out.println("Maximum margin of Victory of loosing team " + w.getTeamName() + " is : " + filehandler.maxmarginOfVictory.get(w.getTeamName()));			
//						System.out.println("Maximum margin of Loss of loosing team " + w.getTeamName() + " is : " + filehandler.maxmarginOfLoss.get(w.getTeamName()));
//						System.out.println("Total number of matches played by loosing team " + w.getTeamName() + " is : " + filehandler.totmatches.get(w.getTeamName()));
//
//					}
//					else if (vp < wp) {
//						System.out.println(w.getTeamName() + " team should win!!");
//						
//						double avgmarginOfVictoryWinning = filehandler.totmarginOfVictory.get(w.getTeamName())/filehandler.totmatches.get(w.getTeamName());
//						double avgmarginOfLossWinning = filehandler.totmarginOfLoss.get(w.getTeamName())/filehandler.totmatches.get(w.getTeamName());
//						
//						System.out.println("Total margin of victory of winning team " + w.getTeamName() + " is : " + filehandler.totmarginOfVictory.get(w.getTeamName()));
//						System.out.println("Total margin of loss of winning team " + w.getTeamName() + " is : " + filehandler.totmarginOfLoss.get(w.getTeamName()));
//						System.out.println("Average margin of Victory of winning team " + w.getTeamName() + " is : " + avgmarginOfVictoryWinning);
//						System.out.println("Average margin of Loss of winning team " + w.getTeamName() + " is : " + avgmarginOfLossWinning);
//						System.out.println("Maximum margin of Victory of winning team " + w.getTeamName() + " is : " + filehandler.maxmarginOfVictory.get(w.getTeamName()));			
//						System.out.println("Maximum margin of Loss of winning team " + w.getTeamName() + " is : " + filehandler.maxmarginOfLoss.get(w.getTeamName()));									
//						System.out.println("Total number of matches played by winning team " + w.getTeamName() + " is : " + filehandler.totmatches.get(w.getTeamName()));
//						
//						System.out.println();
//						
//						double avgmarginOfVictoryLoosing = filehandler.totmarginOfVictory.get(v.getTeamName())/filehandler.totmatches.get(v.getTeamName());
//						double avgmarginOfLossLoosing = filehandler.totmarginOfLoss.get(v.getTeamName())/filehandler.totmatches.get(v.getTeamName());
//						
//						System.out.println("Total margin of victory of loosing team " + v.getTeamName() + " is : " + filehandler.totmarginOfVictory.get(v.getTeamName()));
//						System.out.println("Total margin of loss of loosing team " + v.getTeamName() + " is : " + filehandler.totmarginOfLoss.get(v.getTeamName()));
//						System.out.println("Average margin of Victory of loosing team " + v.getTeamName() + " is : " + avgmarginOfVictoryLoosing);
//						System.out.println("Average margin of Loss of loosing team " + v.getTeamName() + " is : " + avgmarginOfLossLoosing);
//						System.out.println("Maximum margin of Victory of loosing team " + v.getTeamName() + " is : " + filehandler.maxmarginOfVictory.get(v.getTeamName()));			
//						System.out.println("Maximum margin of Loss of loosing team " + v.getTeamName() + " is : " + filehandler.maxmarginOfLoss.get(v.getTeamName()));
//						System.out.println("Total number of matches played by loosing team " + v.getTeamName() + " is : " + filehandler.totmatches.get(v.getTeamName()));
//					}
//					else {
//						System.out.println("Both team have same chance of win!!");
//					}
//					System.out.println();
//				}
//			}
//		}

		/*
		 * Display Prediction of matches between teams that did happen
		 */
		
		for (team v : graph.neighbors.keySet())	{
			for (team w : graph.neighbors.keySet()) {
		if(!graph.neighbors.get(v).contains(w) && !v.equals(w)) {
			double vp = sortedpageRank.get(v)/(sortedpageRank.get(v) + sortedpageRank.get(w));
			double wp = sortedpageRank.get(w)/(sortedpageRank.get(v) + sortedpageRank.get(w));
			if(vp > wp) {
			System.out.println();
			System.out.println("Prediction of Result for Match between " + v.getTeamName() + " and " + w.getTeamName());
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Probability of " + v.getTeamName() + " Winning  is : " + vp); 
			System.out.println("Probability of " + w.getTeamName() + " Winning is : " + wp); 
			}
			else {
				System.out.println();
				System.out.println("Prediction of Result for Match between " + v.getTeamName() + " and " + w.getTeamName());
				System.out.println("---------------------------------------------------------------------");
				System.out.println("Probability of " + v.getTeamName() + " Winning is : " + vp); 
				System.out.println("Probability of " + w.getTeamName() + " Winning is : " + wp); 
			}
		}
			}
		}
		
  
	}
}