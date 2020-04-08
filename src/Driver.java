import java.util.Map;

public class Driver {

    public static void main(String[] args) {
    	
        /**
         * Main program (for testing).
         */
        // Create a Graph with Integer nodes
        Digraph<team> graph = new Digraph<team>();
        team U = new team("USA");
        team A = new team("Australia");
        team B = new team("Bangladesh");
        team S = new team("Srilanka");
        team E = new team("England");
        team SA = new team("South Africa");

        graph.add(B, S); graph.add(A, B); graph.add(B, E); graph.add(SA, B);
        graph.add(E, SA); graph.add(S, E); graph.add(A, U); graph.add(B, U); 
        graph.add(S, U); graph.add(E, U); graph.add(SA, U);
        graph.add(S, A); graph.add(A, E); graph.add(SA, S); graph.add(SA, A);     
        
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
