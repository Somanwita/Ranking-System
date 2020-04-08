import java.util.Map;

public class Drivertest {

    public static void main(String[] args) {
    	
        /**
         * Main program (for testing).
         */
        // Create a Graph with Integer nodes
        Digraph<team> graph = new Digraph<team>();
        team A = new team("A");
        team B = new team("B");
        team C = new team("C");
        team D = new team("D");
        team E = new team("E");
            
        graph.add(A, B); graph.add(C, A); 
        graph.add(B, E); graph.add(C, B);
        graph.add(C, D); graph.add(C, E); graph.add(D, C);
        graph.add(E, D); graph.add(D, E); 
        
        System.out.println("The current graph: " + graph.toString());
        
        System.out.println("In-degrees: ");
        Map<team,Integer> inDegree = graph.inDegree();
        for (team v: inDegree.keySet()) {
            StringBuffer s = new StringBuffer();
            s.append("    " + v.getTeamName() + " = " + inDegree.get(v));
            System.out.println(s.toString());
        }
        
        System.out.println("Out-degrees: ");
        
        Map<team,Integer> outDegree = graph.inDegree();
        for (team v: outDegree.keySet()) {
            StringBuffer s = new StringBuffer();
            s.append("    " + v.getTeamName() + " = " + outDegree.get(v));
            System.out.println(s.toString());
        }
        
        System.out.println("Veertices:" + graph.V());
        System.out.println("Edges" + graph.E());
               
        PageRank pgobj = new PageRank();
        
        pgobj.calc(graph);
    }

}
