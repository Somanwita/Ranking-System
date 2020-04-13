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

