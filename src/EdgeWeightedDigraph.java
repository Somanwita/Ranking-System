import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;


public class EdgeWeightedDiGraph<team, Edge> {
	private Set<team> vertices = new HashSet<>();
	public Set<Edge> edges = new HashSet<>();
	
	private Map<team, Set<Edge>> adjList = new HashMap<>();
	
	public Edge getEdge(team sourceVertex, team targetVertex) {
		for (Edge e : edges) {
			if (e.from().equals(sourceVertex) && e.to().equals(targetVertex)) {
				return e;
			}
		}
		return null;
	}
	
	public void addEdge(team sourceVertex, team targetVertex, double weight) {
		Edge e = new Edge(sourceVertex, targetVertex, weight);		
		vertices.add(sourceVertex);
		vertices.add(targetVertex);
		edges.add(e);
		adjList.put(sourceVertex, edges);
		//adjList.put(sourceVertex, e);
		//.add(sourceVertex, e);
	}
	
	public boolean containsEdge(Edge e) {
		for (Edge edge: edges) {
			if (edge.equals(e))	return true;
		}
		return false;
	}
	
	public boolean containsVertex(team v) {
		for (team t: vertices) {
			if (t.equals(v))	return true;
		}
		return false;
	}
	
	public Set<Edge> edgeSet() {
		return edges;
	}
	
//	public int degreeOf(team vertex) {
//		// TODO Auto-generated method stub
//		return 0;
//	}	
	
//	public Set<Edge> edgesOf(team vertex) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	public int inDegreeOf(team v) {
		int inDegree = 0;
		for (Edge e : edges) {
			if (e.to().equals(v))	inDegree++;
		}
		return inDegree;
	}
	
	public int outDegreeOf(team v) {
		int outDegree = 0;
		for (Edge e : edges) {
			if (e.from().equals(v))	outDegree++;
		}
		return outDegree;
	}
	public Set<Edge> incomingEdgesOf(team v) {
		Set<Edge> incomingEdges = new HashSet<>();
		for (Edge e : edges) {
			if (e.to().equals(v))	incomingEdges.add(e);
		}
		return incomingEdges;
	}

	public Set<Edge> outgoingEdgesOf(team v) {
		Set<Edge> outgoingEdges = new HashSet<>();
		for (Edge e : edges) {
			if (e.from().equals(v))	outgoingEdges.add(e);
		}
		return outgoingEdges;
	}
	
	public Set<team> vertexSet() {
		return vertices;
	}
	
	public team getEdgeSource(Edge e) {
		return e.from();
	}
	
	public team getEdgeTarget(Edge e) {
		return e.to();
	}
	
	public double getEdgeWeight(Edge e) {
		return e.weight();
	}
	
    public static <team, Edge> team getOppositeVertex(EdgeWeightedDiGraph<team, Edge> g, EdgeWeightedDiGraph<team, Edge>.Edge e, team v)
    {
        team source = g.getEdgeSource(e);
        team target = g.getEdgeTarget(e);
        if (v.equals(source)) {
            return target;
        } else if (v.equals(target)) {
            return source;
        } else {
            throw new IllegalArgumentException("no such vertex: " + v.toString());
        }
    }

	public class Edge { 
		public team v;
	    private team w;
	    private double weight;

	    public Edge(team v, team w, double weight) {
	        this.v = v;
	        this.w = w;
	        this.weight = weight;
	    }

	    public team from() {
	        return v;
	    }

	    public team to() {
	        return w;
	    }

	    public double weight() {
	        return weight;
	    }

	    public String toString() {
	        return v + "->" + w + " " + String.format("%5.2f", weight);
	    }

	}


	
}