import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class IncMatrix extends JFrame {
    private JScrollPane scrollPane;
    private JPanel panel;
    private Graph graph;
    private TextField incMatrix[][];
    private Label labelE[];
    private Label labelV[];
    private JButton addV;
    private JButton remV;
    private JButton addE;
    private JButton remE;
    void buildIncidenceMatrix(){
        incMatrix = new TextField[graph.getCountOfEdges()][graph.getCountOfVertices()];
        labelE = new Label[graph.getCountOfEdges()+1];
        labelV = new Label[graph.getCountOfVertices()];
        for(int i=0;i<graph.getCountOfEdges();i++) {
            for(int j=0;j<graph.getCountOfVertices();j++) {
                incMatrix[i][j] =new TextField();
            }
        }
        for(int i=0;i<labelE.length;i++) {
            labelE[i] = new Label();
        }
        for(int i=0;i<labelV.length;i++) {
            labelV[i] = new Label();
        }
        for(int i=0;i<graph.getCountOfEdges()+1;i++){
            labelE[i].setBounds(10+20*i,10,20,20);
            if(i!=0)
                labelE[i].setText("y"+Integer.toString(i));
            labelE[i].setBackground(new Color(176, 224, 230));
            labelE[i].setAlignment(Label.CENTER);
            scrollPane.add(labelE[i]);
        }
        for(int i=0;i<graph.getCountOfVertices();i++){
            labelV[i].setBounds(10,30+i*20,20,20);
            labelV[i].setText("x"+Integer.toString(graph.getVertices()[i].getNum()));
            labelV[i].setBackground(new Color(176, 224, 230));
            labelV[i].setAlignment(Label.CENTER);
            scrollPane.add(labelV[i]);
        }
        for(int i=0;i<graph.getCountOfEdges();i++){
            for(int j=0;j<graph.getCountOfVertices();j++){
                incMatrix[i][j].setBounds(10+20*(i+1),30+j*20,20,20);
                incMatrix[i][j].setText(Integer.toString(i)+Integer.toString(j));
                scrollPane.add(incMatrix[i][j]);
            }
        }
        addV.setBounds(20,20*graph.getCountOfVertices()+30,20,20);
        addV=createButton(addV);
        addE.setBounds(10+20*(graph.getCountOfEdges()+2),10,20,20);
        addE=createButton(addE);
        remV.setBounds(0,20*graph.getCountOfVertices()+30,20,20);
        remV=createButton(remV);
        remE.setBounds(10+20*(graph.getCountOfEdges()+1),10,20,20);
        remE=createButton(remE);
        scrollPane.updateUI();
    }
    JButton createButton(JButton button){
        button.setMargin(new Insets(0,0,0,0));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        scrollPane.add(button);
        button.setVisible(true);
        return button;
    }
    private int getIndex(Vertex vertex){
        int i=0;
        for(i=0;i<graph.getCountOfVertices();i++){
            if(graph.getVertices()[i]==vertex)
                break;
        }
        return i;
    }
    private int getIndex(int a){
        String s = new String();
        for(int j=1; j<labelV[a].getText().length();j++){
            s+=labelV[a].getText().charAt(j);
        }
        if(!s.isEmpty())
            return Integer.parseInt(s);
        else return -1;
    }
    void convertIntoInc(){
        for(int i=0;i<graph.getCountOfEdges();i++){
            for(int j=0;j<graph.getCountOfVertices();j++){
                incMatrix[i][j].setText("0");
            }
        }
        for(int i=0;i<graph.getCountOfEdges();i++){
            if(graph.getEdges()[i].getDestination()==graph.getEdges()[i].getSource())
                incMatrix[i][getIndex(graph.getEdges()[i].getDestination())].setText("2");
            else if(graph.getDirect()==false){
                incMatrix[i][getIndex(graph.getEdges()[i].getDestination())].setText("1");
                incMatrix[i][getIndex(graph.getEdges()[i].getSource())].setText("1");}
            else if(graph.getDirect()==true){
                incMatrix[i][getIndex(graph.getEdges()[i].getDestination())].setText("-1");
                incMatrix[i][getIndex(graph.getEdges()[i].getSource())].setText("1");}
        }
        scrollPane.updateUI();
    }
    Graph build(){
        Graph graphNew = new Graph();
        for(int i=0; i<labelV.length;i++){
            String a = new String();
            for(int j=1; j<labelV[i].getText().length();j++){
                a+=labelV[i].getText().charAt(j);
            }
            if(!a.isEmpty()){
                int x=graph.findVertex(Integer.parseInt(a)).getX();
                int y=graph.findVertex(Integer.parseInt(a)).getY();
                graphNew.addVertex(Integer.parseInt(a),x,y);
            }

        }
        for(int i=0; i<incMatrix.length;i++){
            int start=-1, end=-1;
            for(int j=0; j<incMatrix[i].length;j++){
                if(Integer.parseInt(incMatrix[i][j].getText())==2) {
                    start=getIndex(j);
                    end=getIndex(j);
                }
                if(graph.getDirect()==false){
                if(Integer.parseInt(incMatrix[i][j].getText())==1){
                    if(start==-1)
                        start=getIndex(j);
                    else end=getIndex(j);
                }}
                if(graph.getDirect()==true){
                    if(Integer.parseInt(incMatrix[i][j].getText())==1)
                        start=getIndex(j);
                    if(Integer.parseInt(incMatrix[i][j].getText())==-1)
                        end=getIndex(j);
                }
            }
            graphNew.addEdge(graphNew.findVertex(start),graphNew.findVertex(end),1);
        }
        return graphNew;
    }
    void update(Graph graph){
        this.graph=graph;
        scrollPane.removeAll();
        buildIncidenceMatrix();
        convertIntoInc();
        scrollPane.updateUI();
    }
    void close(){
        dispose();
    }

    public IncMatrix(Graph graph) {
        setContentPane(panel);
        scrollPane.setLayout(null);
        setTitle("Матриця інцидентності");
        setBounds(440,50,300,250);
        setVisible(true);
        this.graph=graph;
        addV = new JButton("+");
        remV = new JButton("-");
        addE = new JButton("+");
        remE = new JButton("-");
        buildIncidenceMatrix();
        convertIntoInc();
        build();
        addV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.addVertex(graph.getCountOfVertices()+1,0,0);
                EditVertexPosition editPos = new EditVertexPosition();
                editPos.setGraph(graph);
                editPos.setVisible(true);
                scrollPane.removeAll();
                buildIncidenceMatrix();
                convertIntoInc();
            }
        });
        remV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.removeVertex(graph.getVertices()[graph.getCountOfVertices()-1]);
                scrollPane.removeAll();
                buildIncidenceMatrix();
                convertIntoInc();
            }
        });
        addE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String a = new String();
                for(int j=1; j<labelE[labelE.length-1].getText().length();j++){
                    a+=labelE[labelE.length-1].getText().charAt(j);
                }
                int i=Integer.parseInt(a)+1;
                labelE  = Arrays.copyOf(labelE, labelE.length+1);
                labelE[labelE.length-1]=new Label("y"+i);
                labelE[labelE.length-1].setBounds(10+20*(labelE.length-1),10,20,20);
                labelE[labelE.length-1].setBackground(new Color(176, 224, 230));
                incMatrix = Arrays.copyOf(incMatrix,incMatrix.length+1);
                incMatrix[incMatrix.length-1] = new TextField[incMatrix[0].length];
                    for(int k=0; k<incMatrix[incMatrix.length-1].length;k++){
                        incMatrix[incMatrix.length-1][k] = new TextField("0");
                        incMatrix[incMatrix.length-1][k].setBounds(10+20*(incMatrix.length),30+k*20,20,20);
                        scrollPane.add(incMatrix[incMatrix.length-1][k]);
                    }
                System.out.println(incMatrix.length);
                scrollPane.add(labelE[labelE.length-1]);
                addE.setBounds(10+20*(labelE.length+1),10,20,20);
                addE=createButton(addE);
                remE.setBounds(10+20*(labelE.length),10,20,20);
                remE=createButton(remE);
                scrollPane.updateUI();
            }
        });
        remE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.removeEdge(graph.getEdges()[graph.getCountOfEdges()-1].getSource(),
                        graph.getEdges()[graph.getCountOfEdges()-1].getDestination() );
                scrollPane.removeAll();
                buildIncidenceMatrix();
                convertIntoInc();
            }
        });
    }

}
