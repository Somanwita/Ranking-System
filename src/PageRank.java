import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class PageRank<Vertex, Edge> {
	
    /**
     * Default number of maximum iterations.
     */
    public static final int MAX_ITERATIONS_DEFAULT = 100;

    /**
     * Default value for the tolerance. The calculation will stop if the difference of PageRank
     * values between iterations change less than this value.
     */
    public static final double TOLERANCE_DEFAULT = 0.0001;

    /**
     * Damping factor default value.
     */
    public static final double DAMPING_FACTOR_DEFAULT = 0.85d;

    /**
     * The input graph
     */
    private EdgeWeightedDigraph<team> graph;
    private Vertex vertex;

    /**
     * The damping factor
     */
    private final double dampingFactor;

    /**
     * Maximum iterations to run
     */
    private final int maxIterations;

    /**
     * The calculation will stop if the difference of PageRank values between iterations change less
     * than this value
     */
    private final double tolerance;

    /**
     * The result
     */
    private Map<Vertex, Double> scores;

     /**
     * Create and execute an instance of PageRank.
     * 
     * @param graph the input graph
     * @param dampingFactor the damping factor
     * @param maxIterations the maximum number of iterations to perform
     * @param tolerance the calculation will stop if the difference of PageRank values between
     *        iterations change less than this value
     */
    public PageRank(EdgeWeightedDigraph<team> graph, double dampingFactor, int maxIterations, double tolerance)
    {
        this.graph = graph;

        if (maxIterations <= 0) {
            throw new IllegalArgumentException("Maximum iterations must be positive");
        }
        this.maxIterations = maxIterations;

        if (dampingFactor < 0.0 || dampingFactor > 1.0) {
            throw new IllegalArgumentException("Damping factor not valid");
        }
        this.dampingFactor = dampingFactor;

        if (tolerance <= 0.0) {
            throw new IllegalArgumentException("Tolerance not valid, must be positive");
        }
        this.tolerance = tolerance;
    }

    /**
     * {@inheritDoc}
     */
    
    public Map<Vertex, Double> getScores()
    {
        if (scores == null) {
            scores = Collections.unmodifiableMap(new Algorithm().getScores());
        }
        return scores;
    }

    /**
     * {@inheritDoc}
     */
    
    @SuppressWarnings("unchecked")
	public Double getVertexScore(Vertex v)
    {
        //if (!graph.containsVertex(v)) {
        if (graph.findVertex(((EdgeWeightedDigraph<team>.Vertex) v).getValue())!=null) {
            throw new IllegalArgumentException("Cannot return score of unknown vertex");
        }
        return getScores().get(v);
    }

    /**
     * The actual implementation.
     * 
     * <p>
     * We use this pattern with the inner class in order to be able to cache the result but also
     * allow the garbage collector to acquire all auxiliary memory used during the execution of the
     * algorithm.
     * 
     * @author Dimitrios Michail
     * 
     * @param <V> the graph type
     * @param <E> the edge type
     */
    private class Algorithm
    {
        private int totalVertices;
        private boolean isWeighted;

        private Map<Vertex, Integer> vertexIndexMap;
        private Vertex[] vertexMap;

        private double[] weights;
        private double[] curScore;
        private double[] nextScore;
        private int[] outDegree;
        private ArrayList<int[]> adjList;

        @SuppressWarnings("unchecked")
        public Algorithm()
        {
            this.totalVertices = graph.vertices.size();
            this.isWeighted = true;

            /*
             * Initialize score, map vertices to [0,n) and pre-compute degrees and adjacency lists
             */
            this.curScore = new double[totalVertices];
            this.nextScore = new double[totalVertices];
            this.vertexIndexMap = new HashMap<>();
            this.vertexMap = (Vertex[]) new Object[totalVertices];
            this.outDegree = new int[totalVertices];

            double initScore = 1.0d / totalVertices;
            int i = 0;
            for (EdgeWeightedDigraph<team>.Vertex v : graph.vertices) {
                vertexIndexMap.put((Vertex) v, i);
                vertexMap[i] = (Vertex) v;
                outDegree[i] = ((EdgeWeightedDigraph<team>.Vertex) vertex).outDegreeOf(v);
                curScore[i] = initScore;
                i++;
            }

            if (isWeighted) {
                this.weights = new double[totalVertices];
                for (EdgeWeightedDigraph<team>.Vertex v : graph.vertices) {
                    double sum = 0;
                    for (EdgeWeightedDigraph<team>.Edge e : ((EdgeWeightedDigraph<team>.Vertex) vertex).outgoingEdgesOf(v)) {
                        sum += e.getEdgeWeight(e);
                    }
                    weights[vertexIndexMap.get(v)] = sum;
                }
            }
                
//            } else {
//                this.adjList = new ArrayList<>(totalVertices);
//                for (i = 0; i < totalVertices; i++) {
//                    V v = vertexMap[i];
//                    int[] inNeighbors = new int[graph.inDegreeOf(v)];
//                    int j = 0;
//                    for (E e : graph.incomingEdgesOf(v)) {
//                        V w = Graphs.getOppositeVertex(graph, e, v);
//                        inNeighbors[j++] = vertexIndexMap.get(w);
//                    }
//                    adjList.add(inNeighbors);
//                }
//            }
        }

        public Map<Vertex, Double> getScores()
        {
            // compute
            if (isWeighted) {
                runWeighted();
            } else {
                run();
            }

            // make results user friendly
            Map<Vertex, Double> scores = new HashMap<>();
            for (int i = 0; i < totalVertices; i++) {
                Vertex v = vertexMap[i];
                scores.put(v, curScore[i]);
                System.out.println("Rank of Vertex" + v.toString() + "is : " + curScore[i]);
            }
            return scores;
        }

        private void run()
        {
            double maxChange = tolerance;
            int iterations = maxIterations;

            while (iterations > 0 && maxChange >= tolerance) {
                double r = teleProp();

                maxChange = 0d;
                for (int i = 0; i < totalVertices; i++) {
                    double contribution = 0d;
                    for (int w : adjList.get(i)) {
                        contribution += dampingFactor * curScore[w] / outDegree[w];
                    }

                    double vOldValue = curScore[i];
                    double vNewValue = r + contribution;
                    maxChange = Math.max(maxChange, Math.abs(vNewValue - vOldValue));
                    nextScore[i] = vNewValue;
                }

                // progress
                swapScores();
                iterations--;
            }
        }
        EdgeWeightedDigraph<team> edgeDigraph = new EdgeWeightedDigraph<>();
        EdgeWeightedDigraph<team>.Edge edges;

        private void runWeighted()
        {
            double maxChange = tolerance;
            int iterations = maxIterations;

            while (iterations > 0 && maxChange >= tolerance) {
                double r = teleProp();

                maxChange = 0d;
                for (int i = 0; i < totalVertices; i++) {
                    Vertex v = vertexMap[i];
                    double contribution = 0d;

                    for (EdgeWeightedDigraph<team>.Edge e : ((EdgeWeightedDigraph<team>.Vertex) vertex).incomingEdgesOf((EdgeWeightedDigraph<team>.Vertex) v)) {
                        Vertex w = edgeDigraph.getOppositeVertex(edgeDigraph, e, v);
                        int wIndex = vertexIndexMap.get(w);
                        contribution += dampingFactor * curScore[wIndex] * edges.getEdgeWeight(e)
                            / weights[wIndex];
                    }

                    double vOldValue = curScore[i];
                    double vNewValue = r + contribution;
                    maxChange = Math.max(maxChange, Math.abs(vNewValue - vOldValue));
                    nextScore[i] = vNewValue;
                }

                // progress
                swapScores();
                iterations--;
            }
        }

		private double teleProp()
        {
            double r = 0d;
            for (int i = 0; i < totalVertices; i++) {
                if (outDegree[i] > 0) {
                    r += (1d - dampingFactor) * curScore[i];
                } else {
                    r += curScore[i];
                }
            }
            r /= totalVertices;
            return r;
        }

        private void swapScores()
        {
            double[] tmp = curScore;
            curScore = nextScore;
            nextScore = tmp;
        }

    }


}