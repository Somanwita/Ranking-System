import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EdgeWeightedDigraphTest {
	public static void main(String[] args)
	{

		EdgeWeightedDigraph<team> graph = new EdgeWeightedDigraph<>();
//		EdgeWeightedDigraph<team>.Vertex obj1 = new EdgeWeightedDigraph.Vertex();
//		EdgeWeightedDigraph<team>.Vertex obj2 = null;
//
        team U = new team("USA");
        team A = new team("Australia");
        team B = new team("Bangladesh");
        team S = new team("Srilanka");
        team E = new team("England");
        team SA = new team("South Africa");
//        
//        obj1.setValue(U);
//        obj1.setValue(A);
//        obj1.setValue(B);
////        obj1.setValue(S);
////        obj1.setValue(E);
////        obj1.setValue(SA);
//
//
        graph.add(B, S , 1); graph.add(A, B, 2); graph.add(B, E, 2); graph.add(SA, B, 1);
        graph.add(E, SA, 4); graph.add(S, E, 5); graph.add(A, U, 6); graph.add(B, U, 1); 
        graph.add(S, U, 3); graph.add(E, U, 1); graph.add(SA, U, 1);
        graph.add(S, A, 1); graph.add(A, E, 4); graph.add(SA, S, 3); graph.add(SA, A, 1); 

		
		
		System.out.println(graph);
		System.out.println(graph.edgesToString());
		
		
		PageRank<EdgeWeightedDigraph<team>.Vertex, EdgeWeightedDigraph<team>.Edge> pr = new PageRank<>(graph, 0.85, 100, 0.0001);
		pr.getScores();
	}
}
