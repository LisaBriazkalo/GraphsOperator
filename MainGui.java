import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class MainGui extends JFrame {
    private JPanel panel;
    private JPanel matrixPanel;
    private JButton addV;
    private JButton remV;
    private TextField ajacencyMatrix[][];
    private Label ajM[][];
    private Label labelAdj[];
    private JPanel frame = new JPanel();
    private IncMatrix incMatrix;
    private JCheckBoxMenuItem weight = new JCheckBoxMenuItem("Показати вагу");
    Graph graph;

    void buildAjacencyMatrix(Graph graph){
        Label lAjacencyMatrix = new Label("Матриця суміжності");
        lAjacencyMatrix.setBounds(10,10,120,20);
        graph.sortByNum(graph.getVertices());
        matrixPanel.add(lAjacencyMatrix);
        ajM = new Label[graph.getCountOfVertices()][graph.getCountOfVertices()];
        for(int i=0;i<graph.getCountOfVertices();i++) {
            for(int j=0;j<graph.getCountOfVertices();j++) {
                ajM[i][j] =new Label();
            }
        }
        labelAdj = new Label[graph.getCountOfVertices()*2+1];
        for(int i=0;i<graph.getCountOfVertices()*2+1;i++) {
            labelAdj[i] = new Label();
        }
        int count=0;
        for(int i=0;i<graph.getCountOfVertices()+1;i++){
            labelAdj[count].setBounds(10+20*i,30,20,20);
            if(count!=0)
                labelAdj[count].setText("x"+graph.getVertices()[i-1].getNum());
            labelAdj[count].setBackground(Color.pink);
            if(count==0)
                labelAdj[count].setBackground(Color.pink.darker());
            labelAdj[count].setAlignment(Label.CENTER);
            matrixPanel.add(labelAdj[count]);
            count++;
        }
        for(int i=0;i<graph.getCountOfVertices();i++){
            labelAdj[count].setBounds(10,20*(i+2)+10,20,20);
            labelAdj[count].setText("x"+graph.getVertices()[i].getNum());
            labelAdj[count].setBackground(Color.pink);
            labelAdj[count].setAlignment(Label.CENTER);
            matrixPanel.add(labelAdj[count]);
            count++;
        }
        for(int i=0;i<graph.getCountOfVertices();i++){
            for(int j=0;j<graph.getCountOfVertices();j++){
                ajM[i][j].setBounds(10+20*(i+1),20*(j+2)+10,20,20);
                ajM[i][j].setText("0");
                ajM[i][j].setBackground(Color.white);
                ajM[i][j].setAlignment(Label.CENTER);
                matrixPanel.add(ajM[i][j]);
            }
        }
        addV.setBounds(10+20*(graph.getCountOfVertices()+1),30,20,20);
        addV=createButton(addV);
        remV.setBounds(10,30+20*(graph.getCountOfVertices()+1),20,20);
        remV=createButton(remV);
        matrixPanel.updateUI();
    }
    JButton createButton(JButton button){
        button.setMargin(new Insets(0,0,0,0));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        matrixPanel.add(button);
        button.setVisible(true);
        return button;
    }
    void takingValues(Graph graph){
        if(graph.getDirect()==false) {
            for (int i = 0; i < graph.getCountOfVertices(); i++) {
                for (int j = 0; j < graph.getCountOfVertices(); j++) {
                        if (i < j && Integer.parseInt(ajM[i][j].getText()) !=
                                Integer.parseInt(ajM[j][i].getText()))
                    {
                        System.out.println("Матриця суміжності для неорієнтованого графа" +
                                " завжди симетрична відносно головної діагоналі");
                        return;
                    }
                }
            }
        }
        graph.sortByNum(graph.getVertices());
        for(int i=0; i<graph.getCountOfVertices();i++) {
            for (int j = 0; j < graph.getCountOfVertices(); j++){
                int ww=Integer.parseInt(ajM[i][j].getText());
                if (ww != 0) {
                    if(graph.getDirect()==false) {
                        if (i <= j){
                            if(!weight.isSelected()){
                                for(int w=1;w<ww+1;w++){
                                    graph.addEdge(graph.getVertices()[j], graph.getVertices()[i], w);
                                }
                            }
                            else graph.addEdge(graph.getVertices()[j], graph.getVertices()[i], ww);
                        }
                    }
                    if(graph.getDirect()==true) {
                        if(!weight.isSelected()){
                            for(int w=1;w<ww+1;w++){
                                graph.addEdge(graph.getVertices()[j], graph.getVertices()[i], w);
                            }
                        }
                        else graph.addEdge(graph.getVertices()[j], graph.getVertices()[i], ww);
                    }
                }
            }
        }
    }
    void convertIntoAj(Graph graph){
        for(int i=0;i<graph.getCountOfVertices();i++){
            for(int j=0;j<graph.getCountOfVertices();j++){
                ajM[i][j].setText("0");
            }
        }
        graph.sortByNum(graph.getVertices());
        for(int i=0;i<graph.getCountOfEdges();i++){
            int x=0, y=0;
            for(; x< graph.getCountOfVertices();x++){
                if(graph.getVertices()[x].equals(graph.getEdges()[i].getSource()))
                    break;
            }
            for(; y< graph.getCountOfVertices();y++){
                if(graph.getVertices()[y]==graph.getEdges()[i].getDestination())
                    break;
            }
            if(!graph.getDirect())
                ajM[x][y].setText(Integer.toString(graph.getEdges()[i].getWeight()));
            ajM[y][x].setText(Integer.toString(graph.getEdges()[i].getWeight()));
        }
    }
    void clearMatrix(){
        for(int i=0;i<ajM.length;i++){
            for(int j=0;j<ajM[i].length;j++)
                matrixPanel.remove(ajM[i][j]);
        }
        for(int i=0;i<labelAdj.length;i++){
            matrixPanel.remove(labelAdj[i]);
        }

    }
    void editMatrix(MouseEvent e){
        System.out.println(e.getComponent());
        for(int i=0;i<graph.getCountOfVertices();i++){
            for(int j=0;j<graph.getCountOfVertices();j++){
                if(ajM[i][j].getX()==e.getComponent().getX() &&
                        ajM[i][j].getY()==e.getComponent().getY()) {
                    JTextField field = new JTextField(ajM[i][j].getText());
                    field.setBounds(ajM[i][j].getBounds());
                    ajM[i][j].setVisible(false);
                    matrixPanel.add(field);
                    field.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent key) {}
                        @Override
                        public void keyPressed(KeyEvent key) {
                            if(key.getKeyCode()==10){
                                for(int i=0;i<graph.getCountOfVertices();i++){
                                    for(int j=0;j<graph.getCountOfVertices();j++){
                                        if(ajM[i][j].getX()==e.getComponent().getX() &&
                                                ajM[i][j].getY()==e.getComponent().getY()) {
                                            ajM[i][j].setText(field.getText());
                                            ajM[j][i].setText(field.getText());
                                            matrixPanel.remove(field);
                                            ajM[i][j].setVisible(true);
                                            matrixPanel.updateUI();
                                        }
                                    }
                                }
                            }
                        }
                        @Override
                        public void keyReleased(KeyEvent key) {}
                    });
                }
            }
        }
    }

    public MainGui() {
        setContentPane(panel);
        setTitle("Лабораторні роботи з КДМ. Брязкало Єлізавета ПІ-211 НУЧП 2022");
        setSize(550, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        matrixPanel.setLayout(null);
        frame.setBounds(250,10,250,200);
        frame.setBorder(BorderFactory.createMatteBorder(
                2, 1, 1, 1, Color.pink));
        frame.setBackground(Color.white);
        matrixPanel.add(frame);
        addV = new JButton("+");
        remV = new JButton("-");
        graph = new Graph();



//        graph.addVertex(1,60,60);
//        graph.addVertex(2,90,30);
//        graph.addVertex(3,150,30);
//        graph.addVertex(4,120,60);
//        graph.addVertex(5,90,90);
//        graph.addVertex(6,150,90);
//        graph.addVertex(7,180,60);
//        graph.addEdge(graph.getVertices()[0],graph.getVertices()[1],7);
//        graph.addEdge(graph.getVertices()[0],graph.getVertices()[4],3);
//        graph.addEdge(graph.getVertices()[0],graph.getVertices()[3],4);
//        graph.addEdge(graph.getVertices()[1],graph.getVertices()[2],7);
//        graph.addEdge(graph.getVertices()[2],graph.getVertices()[3],3);
//        graph.addEdge(graph.getVertices()[2],graph.getVertices()[6],2);
//        graph.addEdge(graph.getVertices()[4],graph.getVertices()[5],3);
//        graph.addEdge(graph.getVertices()[3],graph.getVertices()[5],2);
//        graph.addEdge(graph.getVertices()[5],graph.getVertices()[6],2);

        graph.addVertex(1,90,90);
        graph.addVertex(2,150,90);
        graph.addVertex(3,60,60);
        graph.addVertex(4,90,30);
        graph.addVertex(5,180,60);
        graph.addVertex(6,150,30);
        graph.addEdge(graph.getVertices()[0],graph.getVertices()[1],1);
        graph.addEdge(graph.getVertices()[0],graph.getVertices()[2],1);
        graph.addEdge(graph.getVertices()[1],graph.getVertices()[2],1);
        graph.addEdge(graph.getVertices()[1],graph.getVertices()[3],1);
        graph.addEdge(graph.getVertices()[1],graph.getVertices()[4],1);
        graph.addEdge(graph.getVertices()[2],graph.getVertices()[3],1);
        graph.addEdge(graph.getVertices()[2],graph.getVertices()[4],1);
        graph.addEdge(graph.getVertices()[3],graph.getVertices()[4],1);
        graph.addEdge(graph.getVertices()[3],graph.getVertices()[5],1);
        graph.addEdge(graph.getVertices()[4],graph.getVertices()[5],1);


        buildAjacencyMatrix(graph);
        convertIntoAj(graph);
        for(int i=0;i<graph.getCountOfVertices();i++){
            for(int j=0;j<graph.getCountOfVertices();j++){
                ajM[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if(e.getClickCount()==2){
                        editMatrix(e);
                        }
                    }
                });
            }
        }
        frame.removeAll();
        DrawGraph drawgraph = new DrawGraph();
        frame.add(drawgraph.drawAllVertices(graph));
        frame.add(drawgraph.drawAllEdges(graph));
        frame.updateUI();
        addV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.addVertex(graph.getCountOfVertices()+1,0,0);
                EditVertexPosition editPos = new EditVertexPosition();
                editPos.setGraph(graph);
                editPos.setVisible(true);
                clearMatrix();
                buildAjacencyMatrix(graph);
                convertIntoAj(graph);

                for(int i=0;i<graph.getCountOfVertices();i++){
                    for(int j=0;j<graph.getCountOfVertices();j++){
                        ajM[i][j].addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                if(e.getClickCount()==2){
                                    editMatrix(e);
                                }
                            }
                        });
                    }
                }
            }
        });
        remV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.removeVertex(graph.getVertices()[graph.getCountOfVertices()-1]);
                clearMatrix();
                buildAjacencyMatrix(graph);
                convertIntoAj(graph);

                for(int i=0;i<graph.getCountOfVertices();i++){
                    for(int j=0;j<graph.getCountOfVertices();j++){
                        ajM[i][j].addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                if(e.getClickCount()==2){
                                    editMatrix(e);
                                }
                            }
                        });
                    }
                }
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Опції");
        JMenuItem findSpanningTree = new JMenuItem("Кістякове дерево");
        JMenuItem build = new JMenuItem("Побудувати за матрицею суміжності");
        JMenuItem buildInc = new JMenuItem("Побудувати за матрицею інцидентності");
        JMenuItem cycle = new JMenuItem("Пошук ейлерового циклу/ланцюгу");
        JMenuItem paintEdges = new JMenuItem("Розфарбувати ребра");
        JMenuItem paintVertex = new JMenuItem("Розфарбувати вершини");
        JMenu edit = new JMenu("Редагування");
        JMenuItem editVertPos = new JMenuItem("Змінити положення вершин");
        JMenuItem editWeight = new JMenuItem("Змінити вагу");
        JCheckBoxMenuItem incM = new JCheckBoxMenuItem("Показати матрицю інцидентності");
        weight = new JCheckBoxMenuItem("Показати вагу");
        JMenuItem clear = new JMenuItem("Очистити");
        JCheckBoxMenuItem direct = new JCheckBoxMenuItem("Орієнтований граф");
        clear.setBounds(clear.getX(),clear.getY(),50,clear.getHeight());
        menuBar.add(menu);
        menu.add(build);
        menu.add(buildInc);
        menu.add(cycle);
        menu.add(paintVertex);
        menu.add(paintEdges);
        menu.add(findSpanningTree);
        menuBar.add(edit);
        edit.add(editVertPos);
        edit.add(editWeight);
        edit.add(incM);
        edit.add(direct);
        edit.add(weight);
        menu.add(clear);
        direct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.setDirect(direct.isSelected());
            }
        });
        paintEdges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graph color = graph.colorEdges();
                frame.removeAll();
                DrawGraph drawgraph = new DrawGraph();
                frame.add(drawgraph.drawAllVertices(color));
                frame.add(drawgraph.drawAllEdges(color));
                frame.updateUI();
            }
        });
        paintVertex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graph color = graph.colorVerties();
                frame.removeAll();
                DrawGraph drawgraph = new DrawGraph();
                frame.add(drawgraph.drawAllVertices(color));
                frame.add(drawgraph.drawAllEdges(color));
                frame.updateUI();
            }
        });
        build.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Побудова графу");
                Boolean b=graph.getShowWeight();
                graph.clear();
                frame.removeAll();
                takingValues(graph);
                System.out.println(graph.displayGraph());
                graph.showWeight(b);
                DrawGraph drawgraph = new DrawGraph();
                frame.add(drawgraph.drawAllVertices(graph));
                frame.add(drawgraph.drawAllEdges(graph));
                frame.updateUI();
                if(incM.isSelected()){
                    incMatrix.update(graph);
                }
            }
        });
        buildInc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graph gg = incMatrix.build();
                graph.clearAll();
                for(int i=0; i<gg.getCountOfVertices(); i++){
                    int x, y;
                    x=gg.getVertices()[i].getX(); y=gg.getVertices()[i].getY();
                    graph.addVertex(gg.getVertices()[i].getNum(),x,y);
                }
                for(int i=0; i<gg.getCountOfEdges(); i++){
                    graph.addEdge(graph.findVertex(gg.getEdges()[i].getSource().getNum()),
                           graph.findVertex(gg.getEdges()[i].getDestination().getNum()),1);
                }
                System.out.println(graph.displayGraph());
                frame.removeAll();
                DrawGraph drawgraph = new DrawGraph();
                frame.add(drawgraph.drawAllVertices(graph));
                frame.add(drawgraph.drawAllEdges(graph));
                clearMatrix();
                buildAjacencyMatrix(graph);
                convertIntoAj(graph);
                for(int i=0;i<graph.getCountOfVertices();i++){
                    for(int j=0;j<graph.getCountOfVertices();j++){
                        ajM[i][j].addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                if(e.getClickCount()==2){
                                    editMatrix(e);
                                }
                            }
                        });
                    }
                }
                frame.updateUI();
            }
        });
        cycle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Edge[] passed = graph.cycleSearch();
                frame.removeAll();
                DrawGraph drawgraph = new DrawGraph();
                frame.add(drawgraph.drawAllVertices(graph));
                frame.add(drawgraph.drawAllEdges(graph));
                frame.add(drawgraph.drawCycle(passed));
                frame.updateUI();
            }
        });
        findSpanningTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Пошук кістякового дерева");
                Graph spanningTree = graph.findSpanningTree();
                frame.removeAll();
                DrawGraph drawgraph = new DrawGraph();
                frame.add(drawgraph.drawAllVertices(graph));
                frame.add(drawgraph.drawAllEdges(graph));
                frame.add(drawgraph.drawSpanningTree(spanningTree));
                frame.updateUI();
            }
        });
        editVertPos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditVertexPosition editPos = new EditVertexPosition();
                editPos.setGraph(graph);
                editPos.setVisible(true);
                frame.removeAll();
                DrawGraph drawgraph = new DrawGraph();
                frame.add(drawgraph.drawAllVertices(graph));
                frame.add(drawgraph.drawAllEdges(graph));
                frame.updateUI();
            }
        });
        editWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditWeight editW = new EditWeight();
                editW.setGraph(graph);
                editW.setVisible(true);
                frame.removeAll();
                DrawGraph drawgraph = new DrawGraph();
                frame.add(drawgraph.drawAllVertices(graph));
                frame.add(drawgraph.drawAllEdges(graph));
                frame.updateUI();
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Очищення");
                graph.clearAll();
//                for(int i=graph.getCountOfVertices()-1; i>=0 ;i--)
//                    graph.removeVertex(graph.getVertices()[i]);
                frame.removeAll();
                frame.updateUI();
                clearMatrix();
                buildAjacencyMatrix(graph);
            }
        });
        incM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(incM.isSelected()) {
                    incMatrix = new IncMatrix(graph);
                    incMatrix.setVisible(true);
                }
                else incMatrix.close();
            }
        });
        weight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.showWeight(weight.isSelected());
                graph.weightOn();
                System.out.println(graph.displayGraph());
                frame.removeAll();
                DrawGraph drawgraph = new DrawGraph();
                frame.add(drawgraph.drawAllVertices(graph));
                frame.add(drawgraph.drawAllEdges(graph));
                frame.updateUI();
            }
        });
        setJMenuBar(menuBar);
        matrixPanel.updateUI();

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK && e.getClickCount() == 1){
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem add = new JMenuItem("Додати вершину");
                    JMenuItem remove = new JMenuItem("Видалити вершину");
                    JMenuItem newEdge = new JMenuItem("Побудувати ребро");
                    JMenuItem buildAj = new JMenuItem("Побудувати матрицю");
                    popupMenu.add(add);
                    popupMenu.add(remove);
                    popupMenu.add(newEdge);
                    popupMenu.add(buildAj);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    add.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e1) {
                            graph.addVertex(graph.getCountOfVertices()+1,e.getX(),e.getY());
                            frame.removeAll();
                            DrawGraph drawgraph = new DrawGraph();
                            frame.add(drawgraph.drawAllVertices(graph));
                            frame.add(drawgraph.drawAllEdges(graph));
                            frame.updateUI();
                        }
                    });
                    remove.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e1) {
                            Vertex vertexToRemove = graph.findVertex(e.getX(),e.getY());
                            graph.removeVertex(vertexToRemove);
                            frame.removeAll();
                            DrawGraph drawgraph = new DrawGraph();
                            frame.add(drawgraph.drawAllVertices(graph));
                            frame.add(drawgraph.drawAllEdges(graph));
                            frame.updateUI();
                        }
                    });
                    newEdge.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e1) {
                            frame.addMouseListener(new MouseAdapter(){
                                public void mouseClicked(MouseEvent f){
                                    if(f.getWhen()-e.getWhen()<4000 && f.getClickCount()==2){
                                        Vertex source=graph.findVertex(e.getX(),e.getY());
                                        Vertex destination=graph.findVertex(f.getX(),f.getY());
                                        if(source!=null && destination!=null){
                                            int w=1;
                                            for(int i=0; i<graph.getCountOfEdges(); i++){
                                                if(graph.getEdges()[i].getSource()==source &&
                                                        graph.getEdges()[i].getDestination()==destination) {
                                                    if(weight.isSelected()){
                                                    graph.getEdges()[i].setWeight(graph.getEdges()[i].getWeight() + 1);
                                                        frame.removeAll();
                                                        DrawGraph drawgraph = new DrawGraph();
                                                        frame.add(drawgraph.drawAllVertices(graph));
                                                        frame.add(drawgraph.drawAllEdges(graph));
                                                        frame.updateUI();
                                                    return;
                                                    }
                                                    else w++;
                                                }
                                                if(graph.getEdges()[i].getSource()==destination &&
                                                        graph.getEdges()[i].getDestination()==source){
                                                    if(weight.isSelected()){
                                                    graph.getEdges()[i].setWeight(graph.getEdges()[i].getWeight()+1);
                                                        frame.removeAll();
                                                        DrawGraph drawgraph = new DrawGraph();
                                                        frame.add(drawgraph.drawAllVertices(graph));
                                                        frame.add(drawgraph.drawAllEdges(graph));
                                                        frame.updateUI();
                                                    return;
                                                    }
                                                    else
                                                        w++;
                                                }
                                            }
                                            graph.addEdge(source,destination, w);
                                            if(weight.isSelected()){
                                                EditWeight editWeight = new EditWeight();
                                                editWeight.setGraph(graph);
                                                editWeight.setVisible(true);
                                            }
                                            frame.removeAll();
                                            DrawGraph drawgraph = new DrawGraph();
                                            frame.add(drawgraph.drawAllVertices(graph));
                                            frame.add(drawgraph.drawAllEdges(graph));
                                            frame.updateUI();
                                        }
                                    }
                                }
                            });
                        }
                    });
                    buildAj.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            clearMatrix();
                            buildAjacencyMatrix(graph);
                            convertIntoAj(graph);
                            for(int i=0;i<graph.getCountOfVertices();i++){
                                for(int j=0;j<graph.getCountOfVertices();j++){
                                    ajM[i][j].addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            super.mouseClicked(e);
                                            if(e.getClickCount()==2){
                                                editMatrix(e);
                                            }
                                        }
                                    });
                                }
                            }
                            if(incM.isSelected()){
                                incMatrix.update(graph);
                            }
                        }
                    });
                }
            }
        });
    }
}
