import java.util.Map;

public class Drivertest {

    public static void main(String[] args) {
    	
        /**
         * Main program (for testing).
         */
        // Create a Graph with Integer nodes
        Digraph<team> graph = new Digraph<team>();
        team Liverpool = new team("Liverpool");
        team Norwich = new team("Norwich");
        team WestHam = new team("West Ham");
        team ManCity = new team("Man City");
        team Bournemouth = new team("Bournemouth");
        team SheffieldUnited = new team("Sheffield United");
        team Burnley = new team("Burnley");
        team CrystalPalace = new team("Crystal Palace");
        team Southampton = new team("Southampton");
        team Everton = new team("Everton");
        team Watford = new team("Watford");
        team Brighton = new team("Brighton");
        team Tottenham = new team("Tottenham");
        team AstonVilla = new team("Aston Villa");
        team Leicester = new team("Leicester");
        team Wolves = new team("Wolves");
            
      graph.add(Norwich, Liverpool); 
      graph.add(WestHam,ManCity); 
      graph.add(Bournemouth, SheffieldUnited); // graph.add(SheffieldUnited, Bournemouth);
      graph.add(Southampton, Burnley); 
      graph.add(CrystalPalace, Everton); //graph.add(Everton, CrystalPalace);
      graph.add(Watford, Brighton); 
      graph.add(AstonVilla, Tottenham); 
      graph.add(Leicester, Wolves); //graph.add(Wolves, Leicester); 
        
//        graph.add(A, B); graph.add(C, A); 
//        graph.add(B, E); graph.add(C, B);
//        graph.add(C, D); graph.add(C, E); graph.add(D, C);
//        graph.add(E, D); graph.add(D, E); 
//        graph.add(B, A); graph.add(A, C); 
//        graph.add(E, B); graph.add(B, C);
//        graph.add(C, D); graph.add(C, E); graph.add(D, C);
//        graph.add(E, D); graph.add(D, E); 
        
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
               
        PageRankprev pgobj = new PageRankprev();
        
        pgobj.calc(graph);
    }

}
