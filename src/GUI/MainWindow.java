package GUI;
import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class MainWindow extends JFrame implements ActionListener {
    private static final int ARROW_SIZE = 7;

    JPanel panel;
    JFrame popUP;
    popMSG newPOP;
    AffineTransform tx = new AffineTransform();
    Line2D.Double line = new Line2D.Double(0,0,100,100);
    Polygon arrowHead = new Polygon();
    HashMap<String, Edge> edgeMap = new HashMap<>();
    ArrayList<NodeV> nodes;
    DirectedWeightedGraph graph;
    DirectedWeightedGraphAlgorithms algoGraph;
    private double scaleX = 0;
    private double scaleY = 0;
    private double maxX = Integer.MIN_VALUE;
    private double minX = Integer.MAX_VALUE;
    private double maxY = Integer.MIN_VALUE;
    private double minY = Integer.MAX_VALUE;
    private int incrementY = 100;
    private int incrementX = 100;

    JMenuBar mb ;

    JMenu file_menu;
    JMenuItem load_file;
    JMenuItem save_file;
    JMenuItem exit;


    JMenu nodes_menu;
    JMenuItem get_node;
    JMenuItem add_node;
    JMenuItem remove_node;
    JMenuItem size_nodes;

    JMenu edges_menu;
    JMenuItem get_edge;
    JMenuItem add_edge;
    JMenuItem remove_edge;
    JMenuItem size_edges;

    JMenu algorithem_menu;
    JMenuItem check_if_connected;
    JMenuItem find_shortest_path;
    JMenuItem shortest_path;
    JMenuItem center_node;
    JMenuItem tsp ;






    Boolean is_shortest_path;
    List<NodeData> listOfShort;
    List <Integer> index_of_shortest;

    MainWindow(int width, int height, DirectedWeightedGraphAlgorithms g){
        this.algoGraph = g;
        this.graph= g.getGraph();
        this.index_of_shortest = new ArrayList<>();
        panel = new JPanel();
//        getContentPane().add(panel);
        nodes = new ArrayList();
        setSize(width,height);
        setLayout(null);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        ImageIcon icon = new ImageIcon("node.png");
        setIconImage(icon.getImage());
        mb = new JMenuBar();
        file_menu = new JMenu("File");
        nodes_menu = new JMenu("Nodes");
        edges_menu = new JMenu("Edges");
        algorithem_menu = new JMenu("Algorithms");



        check_if_connected=new JMenuItem("check if connected");
        find_shortest_path=new JMenuItem("find shortest path");
        shortest_path=new JMenuItem("shortest path");
        center_node=new JMenuItem("find center");
        tsp =new JMenuItem("tsp");

        load_file = new JMenuItem("load graph");
        save_file = new JMenuItem("save graph");
        exit = new JMenuItem("exit");

        get_edge = new JMenuItem("get edge");
        add_edge = new JMenuItem("add edge");
        remove_edge = new JMenuItem("remove edge");
        size_edges = new JMenuItem("count edges");

        get_node = new JMenuItem("get node");
        add_node = new JMenuItem("add node");
        remove_node = new JMenuItem("remove node");
        size_nodes = new JMenuItem("count nodes");

        algorithem_menu.add(check_if_connected);
        algorithem_menu.add(find_shortest_path);
        algorithem_menu.add(center_node);
        algorithem_menu.add(tsp );


        file_menu.add(load_file);
        file_menu.add(save_file);
        file_menu.add(exit);

        nodes_menu.add(get_node);
        nodes_menu.add(add_node);
        nodes_menu.add(remove_node);
        nodes_menu.add(size_nodes);

        edges_menu.add(get_edge);
        edges_menu.add(add_edge);
        edges_menu.add(remove_edge);
        edges_menu.add(size_edges);

        mb.add(file_menu);
        mb.add(nodes_menu);
        mb.add(edges_menu);
        mb.add(algorithem_menu);
        exit.addActionListener(this);
        check_if_connected.addActionListener(this);
        find_shortest_path.addActionListener(this);
        center_node.addActionListener(this);
        tsp .addActionListener(this);
        exit.addActionListener(this);
        get_node.addActionListener(this);
        add_node.addActionListener(this);
        remove_node.addActionListener(this);
        size_nodes.addActionListener(this);
        get_edge.addActionListener(this);
        add_edge.addActionListener(this);
        remove_edge.addActionListener(this);
        size_edges.addActionListener(this);
        load_file.addActionListener(this);
        save_file.addActionListener(this);
        this.setJMenuBar(mb);


        drawGraph();

        setLayout(null);
        setVisible(true);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        double factorX = getWidth() / scaleX * 0.8;
        double factorY = getHeight() / scaleY * 0.8;

        Color color = Color.BLUE;

        g2.setColor(color);
        Iterator iter = graph.edgeIter();
        while (iter.hasNext()) {
            Edge edge = (Edge) iter.next();
            String key = edge.getKey();
            NodeData srcNode = graph.getNode(edge.getSrc());
            NodeData destNode = graph.getNode(edge.getDest());
            if (index_of_shortest.contains(srcNode.getKey()) && index_of_shortest.contains(destNode.getKey())){
                g2.setColor(Color.red);
            } else {
                g2.setColor(Color.blue);
            }
            double x1 =incrementX +(srcNode.getLocation().x()-minX)*factorX;
            double y1 =incrementY+ (srcNode.getLocation().y()-minY)*factorY;
            double x2 = incrementX+ (destNode.getLocation().x()-minX)*factorX ;
            double y2 = incrementY+ (destNode.getLocation().y()-minY)*factorY ;
            drawArrow(g, (int)x1, (int)y1, (int)x2, (int)y2);
        }



            for (NodeV p : nodes) {

                double x = (p.getLocation().x() - minX) * factorX + incrementX;
                double y = (p.getLocation().y() - minY) * factorY + incrementY;
                Ellipse2D.Double ellipse = new Ellipse2D.Double(x-10 , y-10 ,15, 15);
                color = Color.BLACK;
                if (index_of_shortest.contains(p.getKey())){
                    color = Color.red;
                }

                g2.setColor(color);
                g2.fill(ellipse);
                g2.draw(ellipse);
                g2.drawString(p.getKey()+"",(float) x-10,(float)y-10);
            }




    }

    private void drawArrowHead(Graphics2D g2d, Line2D line) {
        tx.setToIdentity();
        double angle = Math.atan2(line.getY2()-line.getY1(), line.getX2()-line.getX1());
        tx.translate(line.getX2(), line.getY2());
        tx.rotate((angle-Math.PI/2d));

        Graphics2D g = (Graphics2D) g2d.create();
        g.setTransform(tx);
        g.fill(arrowHead);
        g.dispose();
    }

    public void drawGraph(){
        Iterator iter = graph.nodeIter();
        while(iter.hasNext()){
            NodeV node = (NodeV) iter.next();
            Location loc = (Location) node.getLocation();
            minX = Math.min(minX, loc.x());
            maxX = Math.max(maxX, loc.x());
            minY = Math.min(minY, loc.y());
            maxY = Math.max(maxY, loc.y());
            nodes.add(node);
        }
        scaleX = Math.abs(maxX-minX);
        scaleY = Math.abs(maxY-minY);
        repaint();
    }


    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.setColor(Color.MAGENTA);
        g.fillPolygon(new int[] {len, len- ARROW_SIZE, len- ARROW_SIZE, len},
                new int[] {0, -ARROW_SIZE, ARROW_SIZE, 0}, 4);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.exit){
            System.exit(0);
        }
        else if (e.getSource() == this.check_if_connected){
        boolean flag= algoGraph.isConnected();
        if (flag) {
            this.infoBox("the graph is conncted ! ", "isConnceted", flag);
        }
        else {
            this.infoBox("the graph is not connected !", "isConnceted", flag);
        }

        }
        else if (e.getSource() == this.find_shortest_path){
             showInputDialog();
        }

        else if (e.getSource() == this.center_node){
         int centerNodeKey=  algoGraph.center().getKey();
         index_of_shortest =new ArrayList<>();
         index_of_shortest.add(centerNodeKey);
         repaint();
         JOptionPane.showMessageDialog(null,"the center node is : "+ centerNodeKey);

        }
        else if (e.getSource() == this.tsp ){

        }



    }

    public void infoBox(String infoMessage, String titleBar, boolean flag)
    {

        this.popUP = new JFrame();
        if ( flag == true){
            JOptionPane.showMessageDialog(popUP,infoMessage,titleBar, 1);
        }
        else {
            JOptionPane.showMessageDialog(popUP,infoMessage,titleBar, 0);
        }

    }


    private void showInputDialog() {
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);
        index_of_shortest = new ArrayList<>();

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("source:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("destination:"));
        myPanel.add(yField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int src = Integer.parseInt(xField.getText()) ;
            int dest = Integer.parseInt(yField.getText());

            double distance = algoGraph.shortestPathDist(src,dest);

            List<NodeData> nodes = algoGraph.shortestPath(src,dest);
            index_of_shortest = nodes.stream().map(x -> x.getKey()).collect(Collectors.toList());
            repaint();
            JOptionPane.showMessageDialog(this,"The distance is " + distance);

        }
    }
}
