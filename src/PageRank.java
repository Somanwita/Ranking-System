import java.util.*;

public final class PageRank<team, Edge>
{
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
    public static final double DAMPING_FACTOR_DEFAULT = 0.05d;

    /**
     * The input graph
     */
    private final EdgeWeightedDiGraph<team, Edge> graph;

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
    private Map<team, Double> scores;

//    /**
//     * Create and execute an instance of PageRank.
//     * 
//     * @param graph the input graph
//     */
//    public PageRank(Graph<V, E> graph)
//    {
//        this(graph, DAMPING_FACTOR_DEFAULT, MAX_ITERATIONS_DEFAULT, TOLERANCE_DEFAULT);
//    }
//
//    /**
//     * Create and execute an instance of PageRank.
//     * 
//     * @param graph the input graph
//     * @param dampingFactor the damping factor
//     */
//    public PageRank(Graph<V, E> graph, double dampingFactor)
//    {
//        this(graph, dampingFactor, MAX_ITERATIONS_DEFAULT, TOLERANCE_DEFAULT);
//    }
//
//    /**
//     * Create and execute an instance of PageRank.
//     * 
//     * @param graph the input graph
//     * @param dampingFactor the damping factor
//     * @param maxIterations the maximum number of iterations to perform
//     */
//    public PageRank(Graph<V, E> graph, double dampingFactor, int maxIterations)
//    {
//        this(graph, dampingFactor, maxIterations, TOLERANCE_DEFAULT);
//    }

    /**
     * Create and execute an instance of PageRank.
     * 
     * @param graph the input graph
     * @param dampingFactor the damping factor
     * @param maxIterations the maximum number of iterations to perform
     * @param tolerance the calculation will stop if the difference of PageRank values between
     *        iterations change less than this value
     */
    public PageRank(EdgeWeightedDiGraph<team, Edge> graph, double dampingFactor, int maxIterations, double tolerance)
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
    public Map<team, Double> getScores()
    {
        if (scores == null) {
            scores = Collections.unmodifiableMap(new Algorithm().getScores());
        }
        return scores;
    }

    /**
     * {@inheritDoc}
     */
    public Double getVertexScore(team v)
    {
        if (!graph.containsVertex(v)) {
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

        private Map<team, Integer> vertexIndexMap;
        private team[] vertexMap;

        private double[] weights;
        private double[] curScore;
        private double[] nextScore;
        private int[] outDegree;
        private ArrayList<int[]> adjList;

        @SuppressWarnings("unchecked")
        public Algorithm()
        {
            this.totalVertices = graph.vertexSet().size();
            this.isWeighted = true;

            /*
             * Initialize score, map vertices to [0,n) and pre-compute degrees and adjacency lists
             */
            this.curScore = new double[totalVertices];
            this.nextScore = new double[totalVertices];
            this.vertexIndexMap = new HashMap<>();
            this.vertexMap = (team[]) new Object[totalVertices];
            this.outDegree = new int[totalVertices];

            double initScore = 1.0d / totalVertices;
            int i = 0;
            for (team v : graph.vertexSet()) {
                vertexIndexMap.put(v, i);
                vertexMap[i] = v;
                outDegree[i] = graph.outDegreeOf(v);
                curScore[i] = initScore;
                i++;
            }

            if (isWeighted) {
                this.weights = new double[totalVertices];
                for (team v : graph.vertexSet()) {
                    double sum = 0;
                    for (EdgeWeightedDiGraph<team, Edge>.Edge e : graph.outgoingEdgesOf(v)) {
                        sum += graph.getEdgeWeight(e);
                    }
                    weights[vertexIndexMap.get(v)] = sum;
                }
            } 
        }

        public Map<team, Double> getScores()
        {
            // compute
            if (isWeighted) {
                runWeighted();
            } 
            
            // make results user friendly
            Map<team, Double> scores = new HashMap<>();
            for (int i = 0; i < totalVertices; i++) {
                team v = vertexMap[i];
                scores.put(v, curScore[i]);
            }
            return scores;
        }

        private void runWeighted()
        {
            double maxChange = tolerance;
            int iterations = maxIterations;

            while (iterations > 0 && maxChange >= tolerance) {
                double r = teleProp();

                maxChange = 0d;
                for (int i = 0; i < totalVertices; i++) {
                    team v = vertexMap[i];
                    double contribution = 0d;

                    for (EdgeWeightedDiGraph<team, Edge>.Edge e : graph.incomingEdgesOf(v)) {
                        team w = graph.getOppositeVertex(graph, e, v);
                        int wIndex = vertexIndexMap.get(w);
                        contribution += dampingFactor * curScore[wIndex] * graph.getEdgeWeight(e)
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
