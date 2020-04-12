import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class EdgeWeightedDigraph<V extends team>	{

	ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;

	/**
	 * Default Constructor
	 */
	public EdgeWeightedDigraph()
	{
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}

	/**
	 * Creates Edge from two values T directed from -- to
	 * @param from Value for Vertex 1
	 * @param to Value for Vertex 2
	 * @param cost Cost or weight of edge
	 */
	public void add(V from, V to, int cost)
	{
		Edge temp = findEdge(from, to);
		if (temp != null)
		{
			// Don't allow multiple edges, update cost.
			System.out.println("Edge " + from + "," + to + " already exists. Changing cost.");
			temp.cost = cost;
		}
		else
		{
			// this will also create the vertices
			Edge e = new Edge(from, to, cost);
			edges.add(e);
		}
	}

	/**
	 * find Vertex in Graph from value
	 * @param v value of type T
	 * @return Vertex, or <code>null</code> if not found.
	 */
	Vertex findVertex(V v)
	{
		for (Vertex each : vertices)
		{
			if (each.value.equals(v))
				return each;
		}
		return null;
	}

	/**
	 * Find edge containg two vertices
	 * in direction v1 -> v2
	 * @param v1 Vertex 'from'
	 * @param v2 Vertex 'to'
	 * @return Edge, or <code>null</code> if not found.
	 */
	private Edge findEdge(Vertex v1, Vertex v2)
	{
		for (Edge each : edges)
		{
			if (each.from.equals(v1) && each.to.equals(v2))
			{
				return each;
			}
		}
		return null;
	}

	/**
	 * Find edge from two values
	 * @param from from value of type T
	 * @param to to value of type T
	 * @return Edge, or <code>null</code> if not found.
	 */
	private Edge findEdge(V from, V to)
	{
		for (Edge each : edges)
		{
			if (each.from.value.equals(from) && each.to.value.equals(to))
			{
				return each;
			}
		}
		return null;
	}

	/**
	 * @return string of vertices
	 */
	@Override
	public String toString()
	{
		String retval = "";
		for (Vertex each : vertices)
		{
			retval += each.toString() + "\n";
		}
		return retval;
	}

	/**
	 * list all the edges into a string
	 * @return string of edge data
	 */
	public String edgesToString()
	{
		String retval = "";
		for (Edge each : this.edges)
		{
			retval += each + "\n";
		}
		return retval;
	}
	
	/**
     * Gets the vertex opposite another vertex across an edge.
     *
     * @param g graph containing e and v
     * @param e edge in g
     * @param v vertex in g
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     *
     * @return vertex opposite to v across e
     */
    public static <Vertex, Edge> Vertex getOppositeVertex(EdgeWeightedDigraph<team> g,Edge e, Vertex v)
    {
        Vertex source = (Vertex) g.getSource((EdgeWeightedDigraph<team>.Edge) e);
        Vertex target = (Vertex) g.getEdgeTarget((EdgeWeightedDigraph<team>.Edge) e);
        if (v.equals(source)) {
            return target;
        } else if (v.equals(target)) {
            return source;
        } else {
            throw new IllegalArgumentException("no such vertex: " + v.toString());
        }
    }
  
	private Vertex getEdgeTarget(Edge e) {
    	Edge edge = new Edge();    	
    	return edge.getEdgeTarget(e); 
	}

	private Vertex getSource(Edge e) {
    	Edge edge = new Edge();    	
    	return edge.getEdgeSource(e); 
	}


	class Vertex 
	{
		V value;

		List<Vertex> incoming;
		List<Vertex> outgoing;

		/**
		 * Creates new Vertex with value T
		 * @param value T
		 */
		public Vertex(V value)
		{
			this.value = value;
			incoming = new ArrayList<>();
			outgoing = new ArrayList<>();
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Add Vertex to adjacent incoming list
		 * @param vert Vertex of incoming adjacent
		 */
		public ArrayList<Edge> outgoingEdgesOf(Vertex v) {
			ArrayList<Edge> outgoingEdges = new ArrayList<Edge>(); 
			
			for(Edge e: edges) {
				if(e.from.equals(v)) {
					outgoingEdges.add(e);
				}
				
				
			}
			return outgoingEdges;
		}
		
		public ArrayList<Edge> incomingEdgesOf(Vertex v) {
			ArrayList<Edge> outgoingEdges = new ArrayList<Edge>(); 
			
			for(Edge e: edges) {
				if(e.to.equals(v)) {
					outgoingEdges.add(e);
				}
				
				
			}
			return outgoingEdges;
		}
		
		
		public void addIncoming(Vertex vert)
		{
			incoming.add(vert);
		}
		public void addOutgoing(Vertex vert)
		{
			outgoing.add(vert);
		}
		
		public int outDegreeOf(EdgeWeightedDigraph<team>.Vertex v) {
			return v.outgoing.size();
		}
		
		public int inDegreeOf(Vertex v) {
			return v.incoming.size();
		}

		/**
		 * Get string of Vertex with all it's ingoing and outgoing adjacencies
		 * @ return string
		 */
		@Override
		public String toString()
		{
			String retval = "";
			retval += "Vertex: " + value + " : ";
			retval += " In: ";
			for (Vertex each : this.incoming) retval+= each.value.getTeamName() + " ";
			retval += "Out: ";
			for (Vertex each : this.outgoing) retval += each.value.getTeamName() + " ";
			return retval;
		}
	}

	class Edge
	{
		Vertex from;
		Vertex to;
		int cost;

		/**
		 * @param v1 value of type T for 'from' vertex
		 * @param v2 value of type T for 'to' vertex
		 * @param cost integer value for cost/weight of edge
		 */
		public Edge(V v1, V v2, int cost)
		{
			from = findVertex(v1);
			if (from == null)
			{
				from = new Vertex(v1);
				vertices.add(from);
			}
			to = findVertex(v2);
			if (to == null)
			{
				to = new Vertex(v2);
				vertices.add(to);
			}
			this.cost = cost;

			from.addOutgoing(to);
			to.addIncoming(from);
		}

		public Edge() {
			// TODO Auto-generated constructor stub
		}

		/**
		 * @return Edge as string
		 */
		@Override
		public String toString()
		{
			return "Edge From: " + this.from.value.getTeamName() + " to: " + this.to.value.getTeamName() + " cost: " + this.cost;
		}
		
		public int getEdgeWeight(Edge e) {
			return e.cost;
		}
		
		public Vertex getEdgeSource(Edge e) {
			return e.from;			
		}
		
		public Vertex getEdgeTarget(Edge e) {
			return e.to;
		}
	}


}

