import javax.swing.*;
import java.awt.event.*;

public class EditVertexPosition extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scrollPane;
    private Graph graph;
    private JTextField[] xTextField;
    private JTextField[] yTextField;

    public EditVertexPosition() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Налаштування розташування вершин");
        setBounds(40,40,300,200);
        scrollPane.setLayout(null);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    public void setGraph(Graph graph){
        this.graph=graph;
        go(graph);
    }
    private void go(Graph graph){
        JLabel[] name = new JLabel[graph.getCountOfVertices()];
        JLabel[] x = new JLabel[graph.getCountOfVertices()];
        JLabel[] y = new JLabel[graph.getCountOfVertices()];
        xTextField = new JTextField[graph.getCountOfVertices()];
        yTextField = new JTextField[graph.getCountOfVertices()];
        for(int i=0;i<graph.getCountOfVertices();i++){
            name[i] = new JLabel();
            x[i] = new JLabel();
            y[i] = new JLabel();
            xTextField[i] = new JTextField();
            yTextField[i] = new JTextField();
            name[i].setText("Вершина x"+Integer.toString(graph.getVertices()[i].getNum()));
            x[i].setText("x:");
            y[i].setText("y:");
            xTextField[i].setText(Integer.toString(graph.getVertices()[i].getX()));
            yTextField[i].setText(Integer.toString(graph.getVertices()[i].getY()));
            name[i].setBounds(10,10+20*i,80,20);
            x[i].setBounds(100,10+20*i,30,20);
            y[i].setBounds(165,10+20*i,30,20);
            xTextField[i].setBounds(115,10+20*i, 30,20);
            yTextField[i].setBounds(180,10+20*i, 30,20);
            scrollPane.add(name[i]);
            scrollPane.add(x[i]);
            scrollPane.add(y[i]);
            scrollPane.add(xTextField[i]);
            scrollPane.add(yTextField[i]);
        }

    }

    private void onOK() {
        for(int i=0; i< graph.getCountOfVertices();i++){
            graph.getVertices()[i].setX(Integer.parseInt(xTextField[i].getText()));
            graph.getVertices()[i].setY(Integer.parseInt(yTextField[i].getText()));
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        EditVertexPosition dialog = new EditVertexPosition();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
