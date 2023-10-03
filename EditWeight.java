import javax.swing.*;
import java.awt.event.*;

public class EditWeight extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scrollPane;
    private Graph graph;
    JTextField[] weight;

    public EditWeight() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Налаштування ваги ребер");
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

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
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
        JLabel[] name = new JLabel[graph.getCountOfEdges()];
        JLabel[] lWeight = new JLabel[graph.getCountOfEdges()];
        weight = new JTextField[graph.getCountOfEdges()];
        for(int i=0;i<graph.getCountOfEdges();i++){
            name[i]=new JLabel("Ребро x"+graph.getEdges()[i].getSource().getNum()
                    +"x"+graph.getEdges()[i].getDestination().getNum());
            lWeight[i]=new JLabel("вага:");
            weight[i]=new JTextField(Integer.toString(graph.getEdges()[i].getWeight()));
            name[i].setBounds(3,20*i+3,80,20);
            lWeight[i].setBounds(90,20*i+3,40,20);
            weight[i].setBounds(130,20*i+3,40,20);
            scrollPane.add(name[i]);
            scrollPane.add(lWeight[i]);
            scrollPane.add(weight[i]);
        }
    }

    private void onOK() {
        for(int i=0; i< graph.getCountOfEdges();i++){
            graph.getEdges()[i].setWeight(Integer.parseInt(weight[i].getText()));
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        EditWeight dialog = new EditWeight();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
