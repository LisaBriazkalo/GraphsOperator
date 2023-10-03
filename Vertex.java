import java.awt.*;
import java.util.LinkedList;

public class Vertex {
    private int num;
    private int x;
    private int y;
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vertex(int num, int x, int y) {
        this.num = num;
        this.x = x;
        this.y = y;
        color=Color.WHITE;
    }
    public int getNum() {
        return num;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "num=" + num +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public int getY() {
        return y;
    }
}
