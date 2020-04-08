import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Digraphsample<team> {
    private static final String NEWLINE = System.getProperty("line.separator");

    private int V;           // number of vertices in this digraph
    private int E;                 // number of edges in this digraph
    Map<team,Bag<team>> adj; 	// adj.get(v) = adjacency list for vertex v
    private Map<team,Integer> indegree; 	// indegree[v] = indegree of vertex v
    team I;
    team A;
    team B;
    team S;
    
    public Digraphsample() {
    	try {
    		this.V = 4;
    		indegree = new HashMap<team,Integer>();
    		adj = (Map<team,Bag<team>>) new HashMap<team,Bag<team>>(V);
            
    		adj.put(I, new Bag<team>());
    		adj.put(A, new Bag<team>());
    		adj.put(S, new Bag<team>());
    		adj.put(B, new Bag<team>());
    		
    		indegree.put(I, 0);
    		indegree.put(A, 0);
    		indegree.put(S, 0);
    		indegree.put(B, 0);
           
            addEdge(I, A); addEdge(I, B); addEdge(I, S);
            addEdge(S, A); addEdge(S, B); addEdge(A, B);
            
    		for (team v : adj.keySet()) {
    			System.out.println(v);
    		}
    	
    	}
    	catch (NoSuchElementException e) {
    		throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
    	}
	}
    
 /*   public Digraph(In in) {
        if (in == null) throw new IllegalArgumentException("argument is null");
        try {
            this.V = in.readInt();
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Digraph must be nonnegative");
            indegree = new HashMap<team,Integer>();
            adj = (Map<team,Bag<team>>) new HashMap<team,Bag<team>>(V);
//            for (int v = 0; v < V; v++) {
 //               adj[v] = new Bag<Integer>();
  //          }
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number of edges in a Digraph must be nonnegative");
            for (int i = 0; i < E; i++) {
                //int v = in.readInt();
                //int w = in.readInt();
                team v = (team) in.readString();
                team w = (team) in.readString();
                addEdge(v, w); 
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
        }
    }
*/        
    /**
     * Returns the number of vertices in this digraph.
     *
     * @return the number of vertices in this digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this digraph.
     *
     * @return the number of edges in this digraph
     */
    public int E() {
        return E;
    }

    /**
     * Adds the directed edge v→w to this digraph.
     *
     * @param  v the tail vertex
     * @param  w the head vertex
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(team v, team w) {
    	adj.get(v).add(w);
       // indegree[w]++;
        indegree.put(w, indegree.get(w)+1);
        E++;
    }

    /**
     * Returns the vertices adjacent from vertex {@code v} in this digraph.
     *
     * @param  v the vertex
     * @return the vertices adjacent from vertex {@code v} in this digraph, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<team> adj(team v) {
        return adj.get(v);
    }

    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the outdegree of vertex {@code v}               
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int outdegree(team v) {
    	return adj.get(v).size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the indegree of vertex {@code v}               
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int indegree(team v) {
        return indegree.get(v);
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,  
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {       
        StringBuffer s = new StringBuffer();
        for (team v: adj.keySet()) s.append("\n    " + v + " -> " + adj.get(v));
        return s.toString();  
    }



}
