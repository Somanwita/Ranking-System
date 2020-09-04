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

		double InitialPageRank=0.0;
		double OutgoingLinks=0.0;
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

		// Display Ranking order
		System.out.printf("\nRank\tTeam Name \tPageRank\n");
		int i = 1;
		for (team v : sortedpageRank.keySet())	{
			System.out.printf(i + "\t"+v.getTeamName() + "  \t"+ sortedpageRank.get(v)+"\n");
			i++;
		}

		/*
		 * Display Prediction of matches between teams that did happen
		 */
		normalDistri normalDistri = new normalDistri();

		for (team v : graph.neighbors.keySet())	{
			for (team w : graph.neighbors.keySet()) {
				if(!v.getTeamName().equals(w.getTeamName())) {

					double meanofV = normalDistri.mean(filehandler.teamAndOpponents.get(v.getTeamName()));
					double meanOfW = normalDistri.mean(filehandler.teamAndOpponents.get(w.getTeamName()));
	        double sdV = normalDistri.standardDeviation(filehandler.teamAndOpponents.get(v.getTeamName()), meanofV);

	        double sdW = normalDistri.standardDeviation(filehandler.teamAndOpponents.get(w.getTeamName()), meanOfW);

	        double valueOfV = normalDistri.calculateND(filehandler.maxmarginOfVictory.get(v.getTeamName()), meanofV, sdV);
	        double valueofW = normalDistri.calculateND(filehandler.maxmarginOfVictory.get(w.getTeamName()), meanOfW, sdW);
	        double winningPercentOfV = valueOfV /(valueOfV+valueofW);
	        double winningPercentOfW = valueofW/ (valueOfV+valueofW);

	        System.out.println();
					System.out.println("Prediction of Result for Match between " + v.getTeamName() + " and " + w.getTeamName());
					System.out.println("---------------------------------------------------------------------");
					System.out.println("Probability of " + v.getTeamName() + " Winning  is : " + winningPercentOfV*100);
					System.out.println("Probability of " + w.getTeamName() + " Winning is : " + winningPercentOfW*100);
				}
			}
		}
	}
}
