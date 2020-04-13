import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * An example class for directed graphs.  The vertex type can be specified.
 * There are no edge costs/weights.
 * 
 * @author Dey Somanwita
 */

public class Digraph<V extends team> {
	
    private int V = 0;          
    private int E = 0;
    
    
//    public Digraph(int v, int e, Map<V, List<V>> neighbors) {
//		super();
//		V = v;
//		E = e;
//		this.neighbors = neighbors;
//		for (V vertex : neighbors.keySet()) {
//			neighbors.get(vertex).add(null);
//		}
//	}

	/**
     * The implementation here is basically an adjacency list, but instead
     * of an array of lists, a Map is used to map each vertex to its list of 
     * adjacent vertices.
     */   
    Map<V,List<V>> neighbors = new HashMap<V,List<V>>();
    
    /**
     * String representation of graph.
     */    
    public String toString () {
        StringBuffer s = new StringBuffer();
        //for (V v: this.neighbors.keySet()) s.append("\n    " + v.getTeamName() + " -> " + this.neighbors.get(v));
        for (V v: this.neighbors.keySet()) {
            for (V w : neighbors.get(v)) {
            	s.append("\n    " + v.getTeamName() + " -> " + w.getTeamName());
            }
        }
        return s.toString();                
    }
    
    /**
     * Add a vertex to the graph.  Nothing happens if vertex is already in graph.
     */
    public void addVertex (V vertex) {
       // if (neighbors.containsKey(vertex)) return;
    	for (V v : neighbors.keySet()) {
    		if (v.getTeamName().equals(vertex.getTeamName())) {
    			return; }   		
    	}
        neighbors.put(vertex, new ArrayList<V>());
        V++;
    }
    
    /**
     * True iff graph contains vertex.
     */
    public boolean contains (V vertex) {
        return neighbors.containsKey(vertex);
    }
    
    /**
     * Add an edge to the graph; if either vertex does not exist, it's added.
     * This implementation allows the creation of  multi-edges and self-loops.
     */
    public void add (V from, V to) {
        this.addVertex(from); this.addVertex(to);
        for(V v : neighbors.keySet()) {
        	if(v.getTeamName().equals(from.getTeamName())) {
        		neighbors.get(v).add(to);
        		E++;        		
        	}
        	
        }
        //neighbors.get(from).add(to);
        
    }

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
     * Remove an edge from the graph.  Nothing happens if no such edge.
     * @throws IllegalArgumentException if either vertex doesn't exist.
     */
    public void remove (V from, V to) {
        if (!(this.contains(from) && this.contains(to)))
            throw new IllegalArgumentException("Nonexistent vertex");
        neighbors.get(from).remove(to);
        E--;
    }
    
    /**
     * Report (as a Map) the out-degree of each vertex.
     */
    public Map<V,Integer> outDegree () {
        Map<V,Integer> result = new HashMap<V,Integer>();
        for (V v: neighbors.keySet()) result.put(v, neighbors.get(v).size());
        return result;
    }
    
    /**
     * Report (as a Map) the in-degree of each vertex.
     */
    public Map<V,Integer> inDegree () {
        Map<V,Integer> result = new HashMap<V,Integer>();
        //Map<V,List<V>> inDegreeeList = new HashMap<V,List<V>>();
        for (V v: neighbors.keySet()) result.put(v, 0);       // All in-degrees are 0
        for (V from: neighbors.keySet()) {
        	List<V> vList = new ArrayList<>();
            for (V to: neighbors.get(from)) {
            	//vList.add(from);
                result.put(to, result.get(to) + 1);           // Increment in-degree
            }
            //inDegreeeList.put(from,vList);
        }
        return result;
    }
}