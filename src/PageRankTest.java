import java.util.Map;

public class PageRankTest {
	
	public static void main(String[] args)
	{

		EdgeWeightedDiGraph graph = new EdgeWeightedDiGraph();

        team U = new team("USA");
        team A = new team("Australia");
        team B = new team("Bangladesh");
        team S = new team("Srilanka");
        team E = new team("England");
        team SA = new team("South Africa");
        
        graph.addEdge(B, S , 1); graph.addEdge(A, B, 1); graph.addEdge(B, E, 1); graph.addEdge(SA, B, 1);
        graph.addEdge(E, SA, 1); graph.addEdge(S, E, 1); graph.addEdge(A, U, 6); graph.addEdge(B, U, 1); 
        graph.addEdge(S, U, 1); graph.addEdge(E, U, 1); graph.addEdge(SA, U, 1); graph.addEdge(SA, A, 1); 
        graph.addEdge(S, A, 1); graph.addEdge(A, E, 1); graph.addEdge(SA, S, 1);
		

            PageRank<team, Edge> pr = new PageRank(graph, 0.15, 100, 0.0001);
            
            Map<team, Double> finalPR = pr.getScores();
            
            for (team v : finalPR.keySet()) {
                System.out.println("Final PR of " + v.getTeamName() + "is : " + finalPR.get(v));
            }
	
}}
