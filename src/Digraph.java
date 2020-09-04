import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * Directed graphs with Generic vertex type and no edge weight.
 * 
 */

public class Digraph<V extends team> {
	
    private int V = 0;          
    private int E = 0;
    
    /**
     * Define adjacency list named neighbors. Used Map to map each vertex to its list of 
     * adjacent vertices.
     */   
    Map<V,List<V>> neighbors = new HashMap<V,List<V>>();
    
    /**
     * String representation of graph.
     */    
    public String toString () {
        StringBuffer s = new StringBuffer();
        for (V v: this.neighbors.keySet()) {
            for (V w : neighbors.get(v)) {
            	s.append("\n    " + v.getTeamName() + " -> " + w.getTeamName());
            }
        }
        return s.toString();                
    }
    
    /**
     * Add a vertex to the graph. Return if vertex is already in graph.
     */
    public void addVertex (V vertex) {
    	for (V v : neighbors.keySet()) {
    		if (v.getTeamName().equals(vertex.getTeamName())) {
    			return; 
    		}   		
    	}
        neighbors.put(vertex, new ArrayList<V>());
        V++;
    }
    
    /**
     * Check if graph contains vertex.
     */
    public boolean contains (V vertex) {
        return neighbors.containsKey(vertex);
    }
    
    /**
     * Add an edge to the graph; add vertex if does not exist.
     */
    public void add (V from, V to) {
        this.addVertex(from); this.addVertex(to);
        for(V v : neighbors.keySet()) {
        	if(v.getTeamName().equals(from.getTeamName())) {
        		for (V w : neighbors.keySet()) {
        			if (w.getTeamName().equals(to.getTeamName())) {
        				neighbors.get(v).add(w);
        				E++;
        				return;
        			}
        		}
        		neighbors.get(v).add(to);
        		E++;        		
        	}
        	
        }
        //neighbors.get(from).add(to);
        
    }

    /**
     * @return the number of vertices in this digraph
     */
    public int V() {
        return V;
    }

    /**
     * @return the number of edges in this digraph
     */
    public int E() {
        return E;
    }
    
    /**
     * Remove an edge from the graph.  Returns if there the edge is not there.
     */
    public void remove (V from, V to) {
        if (!(this.contains(from) && this.contains(to)))
            throw new IllegalArgumentException("Nonexistent vertex");
        neighbors.get(from).remove(to);
        E--;
    }
    
    /**
     * Returns a Map of the out-degree of each vertex.
     */
    public Map<V,Integer> outDegree () {
        Map<V,Integer> result = new HashMap<V,Integer>();
        for (V v: neighbors.keySet()) result.put(v, neighbors.get(v).size());
        return result;
    }
    
    /**
     * Returns a Map of the in-degree of each vertex.
     */
    public Map<V,Integer> inDegree () {
        Map<V,Integer> result = new HashMap<V,Integer>();
        for (V v: neighbors.keySet()) result.put(v, 0);       
        for (V from: neighbors.keySet()) {
            for (V to: neighbors.get(from)) {
                result.put(to, result.get(to) + 1);         
            }
        }
        return result;
    }
}
