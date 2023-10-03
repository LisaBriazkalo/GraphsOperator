import javax.swing.*;
import javax.xml.crypto.dsig.Transform;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.GregorianCalendar;

public class DrawGraph {
    JPanel frame = new JPanel();
    JLabel lbl = new JLabel();
    BufferedImage tempImage = new BufferedImage(250, 200, BufferedImage.TYPE_INT_ARGB);

    JLabel drawAllVertices(Graph graph){
        for(int i=0; i<graph.getCountOfVertices();i++){
            lbl=drawVertex(graph.getVertices()[i]);
        }
        ImageIcon icon=new ImageIcon(tempImage);
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.updateUI();
        return lbl;
    }
    JLabel drawAllEdges(Graph graph){
        for(int i=0; i<graph.getCountOfEdges();i++){
            lbl=drawEdge(graph.getDirect(), graph.getEdges()[i],1);
        }
        ImageIcon icon=new ImageIcon(tempImage);
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.updateUI();
        return lbl;
    }
    JLabel drawSpanningTree(Graph spanTree){
        for(int i=0; i<spanTree.getCountOfEdges();i++){
            lbl=drawEdge(false, spanTree.getEdges()[i],1);
        }
        ImageIcon icon=new ImageIcon(tempImage);
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.updateUI();
        return lbl;
    }
    JLabel drawVertex(Vertex vertex){
        Graphics2D g = tempImage.createGraphics();
        g.setColor(vertex.getColor());
        g.fillOval(vertex.getX()-10, vertex.getY()-10,20,20);
        g.setColor(Color.black);
        g.drawOval(vertex.getX()-10,vertex.getY()-10,21,21);
        g.drawOval(vertex.getX()-10,vertex.getY()-9,21,21);
        g.drawString(Integer.toString(vertex.getNum()),vertex.getX()-2,vertex.getY()+5);
        g.drawString(Integer.toString(vertex.getNum()),vertex.getX()-1,vertex.getY()+5);
        ImageIcon icon=new ImageIcon(tempImage);
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.updateUI();
        return lbl;
    }
    JLabel drawCycle(Edge[] passed) {
        Graphics2D g = tempImage.createGraphics();
        for (int i = 0; i < passed.length; i++){
            int x1 = passed[i].getSource().getX();
            int y1 = passed[i].getSource().getY();
            int x2 = passed[i].getDestination().getX();
            int y2 = passed[i].getDestination().getY();
            if(x1>x2) x1-=40;
            g.setColor(Color.yellow);
            g.drawString("c"+Integer.toString(i+1), x1 + 20, (y1 + y2) / 2 + 8);
            g.drawString("c"+Integer.toString(i+1), x1+21, (y1 + y2) / 2 + 8);
            g.setColor(Color.red);
            g.drawString("c"+Integer.toString(i+1), x1 + 19, (y1 + y2) / 2 + 8);
            g.drawString("c"+Integer.toString(i+1), x1 + 18, (y1 + y2) / 2 + 8);
        }
        ImageIcon icon=new ImageIcon(tempImage);
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.updateUI();
        return lbl;
    }
    JLabel drawEdge(Boolean direct,Edge edge, int repeat){
        Boolean withNum=edge.getShowWeight();
        int x1 = edge.getSource().getX();
        int y1 = edge.getSource().getY();
        int x2 = edge.getDestination().getX();
        int y2 = edge.getDestination().getY();
        double newx1=(double)x1, newx2=(double)x2, newy1=(double)y1,
                newy2=(double)y2, arrowx2=(double)x2;
        if(Math.abs(x2-x1)>5){
            double k=((double) y2-(double)y1)/((double)x2-(double)x1);
            newx1=(double)x1;
            double x1i=11;
            newx2=(double)x2-11;
            double x2i=0;
            if(x2<x1) {
                newx1 = (double) x1 - 11;
                x1i=0;
                newx2=(double)x2;
                x2i=11;
            }
            double a=(newx1-(double)x1)*(newx1-(double)x1)*(k*k+1);
            while((int)a!=100 && newx1<(double)x1+x1i){
                newx1+=0.01;
                a=(newx1-(double)x1)*(newx1-(double)x1)*(k*k+1);
            }
            newy1=(newx1 - (double)x1)*k + (double)y1;

            a=(k*(newx2-(double)x1)+(double)y1-(double)y2)*(k*(newx2-(double)x1)+(double)y1-(double) y2)+(newx2-(double)x2)*(newx2-(double)x2);
            while((int)a!=100 && newx2<(double)x2+x2i){
                newx2+=0.01;
                a=(k*(newx2-(double)x1)+(double)y1-(double)y2)*(k*(newx2-(double)x1)+(double)y1-(double) y2)+(newx2-(double)x2)*(newx2-(double)x2);
            }
            newy2=(newx2 - (double)x1)*k + (double)y1;
        }
        else {
            double i=1;
            if(y2<y1) i=-1;
            newy2=newy2-10*i;
            newy1=newy1+10*i;
        }
        //double k=(newy2-newy1)/(newx2-newx1);
        Graphics2D g = tempImage.createGraphics();
        g.setColor(edge.getColor());
        Color dev=edge.getColor();
        if(!withNum)
            repeat=edge.getWeight();
        if(edge.getSource()==edge.getDestination()){
            if(edge.getSource().getX()<=100 && edge.getSource().getY()<=75){
                g.drawArc(x1-(20+5*repeat),y1-(20+5*repeat),20+5*repeat,20+5*repeat,0,270);
            }
            if(edge.getSource().getX()>100 && edge.getSource().getY()<=75){
                g.drawArc(x1,y1-(20+5*repeat),20+5*repeat,20+5*repeat,270,270);
            }
            if(edge.getSource().getX()<=100 && edge.getSource().getY()>75){
                g.drawArc(x1-(20+5*repeat),y1,20+5*repeat,20+5*repeat,90,270);
            }
            if(edge.getSource().getX()>100 && edge.getSource().getY()>75){
                g.drawArc(x1,y1,20+5*repeat,20+5*repeat,180,270);
            }
        }
        else {
            if(repeat==0){
                g.drawLine((int)newx1, (int)newy1, (int)newx2, (int)newy2);
                g.drawLine((int)newx1, (int)newy1+1, (int)newx2, (int)newy2+1);
                g.drawLine((int)newx1+1, (int)newy1, (int)newx2+1, (int)newy2);
            }
            if(repeat!=0){
                if(repeat%2==1) {
                    drawArcs(newx1, newy1, newx2, newy2, 5 * repeat, false,dev,direct);
                    drawArcs(newx1, newy1+1, newx2, newy2+1, 5 * repeat, false,dev,direct);
                    if(dev!=Color.BLACK)
                        drawArcs(newx1, newy1-1, newx2, newy2-1, 5 * repeat, false,dev,direct);
                }
                if(repeat%2==0) {
                    drawArcs(newx1, newy1, newx2, newy2, 5 * (repeat - 1), true,dev,direct);
                    drawArcs(newx1, newy1+1, newx2, newy2+1, 5 * (repeat - 1), true,dev,direct);
                    if(dev!=Color.BLACK)
                        drawArcs(newx1, newy1-1, newx2, newy2-1, 5 * (repeat - 1), true,dev,direct);
                }
            }
        }

        if(withNum) {
            g.setColor(Color.CYAN);
            g.drawString(Integer.toString(edge.getWeight()), (x1 + x2) / 2 + 3, (y1 + y2) / 2 + 8);
            g.drawString(Integer.toString(edge.getWeight()), (x1 + x2) / 2 + 2, (y1 + y2) / 2 + 8);
            g.setColor(Color.BLUE);
            g.drawString(Integer.toString(edge.getWeight()), (x1 + x2) / 2+1, (y1 + y2) / 2 + 8);
            g.drawString(Integer.toString(edge.getWeight()), (x1 + x2) / 2, (y1 + y2) / 2 + 8);
        }
        ImageIcon icon=new ImageIcon(tempImage);
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.updateUI();
        return lbl;
    }
    private void drawArcs(double x1, double y1, double x2, double y2, double h, Boolean up, Color dev, Boolean direct){
        Graphics2D g = tempImage.createGraphics();
        g.setColor(dev);
        double upp=-1;
        if(up==false) upp=1;
        double k=(y2-y1)/(x2-x1);
        double tg=Math.toDegrees(Math.atan(k));
        double w=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        w=w/2;
        double newx1=x1*Math.cos(Math.toRadians(tg))+y1*Math.sin(Math.toRadians(tg));
        double newy1=-x1*Math.sin(Math.toRadians(tg))+y1*Math.cos(Math.toRadians(tg));
        double newy2=-x2*Math.sin(Math.toRadians(tg))+y2*Math.cos(Math.toRadians(tg));
        double newx2=x2*Math.cos(Math.toRadians(tg))+y2*Math.sin(Math.toRadians(tg));
        Path2D path;
        path = new Path2D.Double();
        path.moveTo(x1,y1);
        double var1=newx1, var2=newx2, var3=newy1;
        if(x1>x2) { var1=newx2; var2=newx1; path.moveTo(x2,y2);}
        if(y1>y2) var3=newy2;
        double x=0,y=0;
        for(double i=var1; i<=var2; i++){
            y=upp*(h/w)*Math.sqrt(w*w-(i-var1-w)*(i-var1-w)) + var3;
            x=i*Math.cos(Math.toRadians(tg))-y*Math.sin(Math.toRadians(tg));
            y=i*Math.sin(Math.toRadians(tg))+y*Math.cos(Math.toRadians(tg));
            path.lineTo(x,y);
            if(direct && x2<x1 && i==var1)
                g.drawOval((int)x-3,(int)y-3,6,6);
        }
        if(direct && x2>=x1)
            g.drawOval((int)x-3,(int)y-3,6,6);
        g.draw(path);
    }
}
