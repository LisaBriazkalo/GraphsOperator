import java.awt.*;

public class Edge {
    private Vertex source;
    private Vertex destination;
    private int weight;
    private Boolean showWeight;
    private Color color;
    public Color getColor() {
        return color;
    }

    public void setShowWeight(Boolean showWeight) {
        this.showWeight = showWeight;
    }

    public Boolean getShowWeight() {
        return showWeight;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source.getNum() +
                ", destination=" + destination.getNum() +
                ", weight=" + weight +
                '}';
    }

    public Edge(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        color=Color.BLACK;
        showWeight=false;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }
}
