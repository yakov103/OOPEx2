package GUI;
import api.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainWindow extends JFrame implements ActionListener, MouseListener {
    private static final int ARROW_SIZE = 7;
    int width;
    int height;
    boolean needToChoose;
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



    JFileChooser fileChooser;
    int x_and_y[];
    int choosen_key;

    Boolean is_shortest_path;
    List<NodeData> listOfShort;
    List <Integer> index_of_shortest;

    MainWindow(int width, int height, DirectedWeightedGraphAlgorithms g){
        this.algoGraph = g;
        this.graph= algoGraph.getGraph();
        this.index_of_shortest = new ArrayList<>();
        needToChoose =false;
        x_and_y= new int[2];
//        getContentPane().add(panel);
        nodes = new ArrayList();
        this.height = height;
        this.width = width;
        setSize(this.width,this.height);
        setLayout(null);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
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

        algorithem_menu.add(check_if_connected);//done
        algorithem_menu.add(find_shortest_path);//done
        algorithem_menu.add(center_node);//done
        algorithem_menu.add(tsp );


        file_menu.add(load_file);//done
        file_menu.add(save_file);//done
        file_menu.add(exit);//done

        nodes_menu.add(get_node);// done
        nodes_menu.add(add_node);// need to add fixed place on graph
        nodes_menu.add(remove_node);// need to be fixed
       // nodes_menu.add(size_nodes);// is needed ?

        edges_menu.add(get_edge);//done
        edges_menu.add(add_edge);//done
        edges_menu.add(remove_edge);
        //edges_menu.add(size_edges);// is needed ?

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
        //size_nodes.addActionListener(this);
        get_edge.addActionListener(this);
        add_edge.addActionListener(this);
        remove_edge.addActionListener(this);
        //size_edges.addActionListener(this);
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
        HashMap<String,Integer> alreadyWay= new HashMap<String,Integer>();
        while (iter.hasNext()) {
            boolean isFromPath= false;
            color = Color.BLUE;
            g2.setColor(color);
            Edge edge = (Edge) iter.next();
            String key = edge.getKey();
            NodeData srcNode = graph.getNode(edge.getSrc());
            NodeData destNode = graph.getNode(edge.getDest());
            if (index_of_shortest.contains(srcNode.getKey()) && index_of_shortest.contains(destNode.getKey())){
                if (index_of_shortest.indexOf(srcNode.getKey())+1 == index_of_shortest.indexOf(destNode.getKey())  ){
                    g2.setColor(Color.red);
                    isFromPath = true;
                }



            }
            double x1 =incrementX +(srcNode.getLocation().x()-minX)*factorX ;
            double y1 =incrementY+ (srcNode.getLocation().y()-minY)*factorY ;
            double x2 = incrementX+ (destNode.getLocation().x()-minX)*factorX ;
            double y2 = incrementY+ (destNode.getLocation().y()-minY)*factorY ;
            if (x1 > x2){
                if (!isFromPath) g2.setColor(new Color(0x045D04));
                y1 = y1 - 5;
                y2 = y2 - 5;


            }else {
                y1 = y1+ 5;
                y2 = y2 + 5;
            }



            drawArrow(g, (int)x1, (int)y1-5 , (int)x2, (int)y2-5);

        }



            for (NodeV p : nodes) {

                double x = (p.getLocation().x() - minX) * factorX + incrementX;
                //  = p.getLocation().x()
                double y = (p.getLocation().y() - minY) * factorY + incrementY;
                Ellipse2D.Double ellipse = new Ellipse2D.Double(x-5 , y-5 ,10, 10);
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
        setSize(width,height);
        setLayout(null);
        setVisible(true);
    }


    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1 -5 , dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
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

        else if (e.getSource() == this.load_file){
            fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String location_file = selectedFile.getAbsolutePath();
                algoGraph.load(location_file);
                graph= algoGraph.getGraph();
                nodes = new ArrayList<>();
                drawGraph();
            }


        }

        else if (e.getSource() == this.save_file){
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("."));
            fileChooser.setDialogTitle("save graph");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == fileChooser.APPROVE_OPTION){
                JPanel myPanel = new JPanel();
                JTextField xField = new JTextField(20);
                myPanel.add(new JLabel("Please choose file name to save :"));
                myPanel.add(xField);
                int result = JOptionPane.showConfirmDialog(null, myPanel, "Save file", JOptionPane.OK_CANCEL_OPTION);

                if (result ==  JOptionPane.OK_OPTION) {
                    String devider = "/";
                    if (isWindows(System.getProperty("os.name"))){
                        devider = "\\";

                    }
                    System.out.println("\\");
                    File selectedFolder = fileChooser.getSelectedFile();
                    String pathToSave = selectedFolder.getAbsolutePath()+devider+xField.getText()+".json";
                    if (algoGraph.save(pathToSave)) {
                        JOptionPane.showMessageDialog(null, "the graph saved successfully at : " + pathToSave);
                    }
                }

            }
            else {
                    JOptionPane.showMessageDialog(null, "error saving file");
            }
        }

        else if (e.getSource() == this.center_node){
         int centerNodeKey=  algoGraph.center().getKey();
         index_of_shortest =new ArrayList<>();
         index_of_shortest.add(centerNodeKey);
         drawGraph();
         JOptionPane.showMessageDialog(null,"the center node is : "+ centerNodeKey);

        }
        else if (e.getSource() == this.tsp ){
            JPanel myPanel = new JPanel();
            JTextField countField = new JTextField(5);
            myPanel.add(new JLabel( "please enter im how many nodes do you want to travel :"));
            myPanel.add(countField);
            int resualt = JOptionPane.showConfirmDialog(null,myPanel,"TSP", JOptionPane.OK_CANCEL_OPTION);
            if (resualt == JOptionPane.OK_OPTION){
                myPanel = new JPanel();
                int cnt = Integer.parseInt(countField.getText());
                JTextField numbersField []= new JTextField[cnt];
                myPanel.add(new JLabel("please add the nodes by order"));
                for ( int i = 0 ; i < cnt ; i++){
                    numbersField[i] = new JTextField(5);
                    myPanel.add(numbersField[i]);
                }
                resualt = JOptionPane.showConfirmDialog(null,myPanel,"TSP", JOptionPane.OK_CANCEL_OPTION);
                if (resualt == JOptionPane.OK_OPTION){
                    List <NodeData> listToAdd = new LinkedList<>();
                    int key;
                    for ( int i = 0 ; i < cnt ; i ++){
                        key = Integer.parseInt(numbersField[i].getText());
                        listToAdd.add(this.algoGraph.getGraph().getNode(key));
                    }

                    listToAdd = this.algoGraph.tsp(listToAdd);
                    for ( int i =0 ; i < listToAdd.size() ; i ++){
                        System.out.println(listToAdd.get(i).getKey());
                    }

                    index_of_shortest = listToAdd.stream().map(x -> x.getKey()).collect(Collectors.toList());
                    drawGraph();


                }
            }





        }
        else if (e.getSource() == this.get_node){
            JPanel myPanel = new JPanel();
            JTextField xField = new JTextField(5);
            myPanel.add(new JLabel("Please choose key node :"));
            myPanel.add(xField);
            int result = JOptionPane.showConfirmDialog(null, myPanel, "Get Node", JOptionPane.OK_CANCEL_OPTION);

            if (result ==  JOptionPane.OK_OPTION) {
               int keynode = Integer.parseInt(xField.getText());
                index_of_shortest =new ArrayList<>();
                index_of_shortest.add(keynode);
                drawGraph();
                JOptionPane.showMessageDialog(null,"the postion of youre node is ("+graph.getNode(keynode).getLocation().x()+","+graph.getNode(keynode).getLocation().y()+")");
            }


        }


        else if (e.getSource() == this.add_node) {
            JPanel myPanel = new JPanel();
            JTextField xField = new JTextField(5);
            myPanel.add(new JLabel("please choose new key num for :"));
            myPanel.add(xField);
            int result = JOptionPane.showConfirmDialog(null, myPanel, "Get Node", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                choosen_key = Integer.parseInt(xField.getText());
                List <Integer> checkIfContain= new ArrayList<>();
                checkIfContain = nodes.stream().map(x ->x.getKey()).collect(Collectors.toList());

                if (checkIfContain.contains(choosen_key)){
                   JOptionPane.showMessageDialog(null,"key node already in the graph, please try again another key ! ");
                   return ;
                }

                needToChoose = true;
                this.addMouseListener(this);


            }


        }

        else if (e.getSource() == remove_node){
            JPanel myPanel = new JPanel();
            JTextField xField = new JTextField(5);
            myPanel.add(new JLabel("please choose node key to remove  :"));
            myPanel.add(xField);
            int result = JOptionPane.showConfirmDialog(null, myPanel, "Remove Node", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                this.graph.removeNode(Integer.parseInt(xField.getText()));
                algoGraph.init(this.graph);
                drawGraph();
            }

        }

        else if (e.getSource() == get_edge){
            JPanel myPanel = new JPanel();
            JTextField fieldSrc = new JTextField(5);
            JTextField fieldDest = new JTextField(5);
            myPanel.add(new JLabel("source:"));
            myPanel.add(fieldSrc);
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(new JLabel("destination: "));
            myPanel.add(fieldDest);

            int resualt = JOptionPane.showConfirmDialog(null,myPanel,"get edge ", JOptionPane.OK_CANCEL_OPTION);
            if (resualt == JOptionPane.OK_OPTION){
                int srcEdge = Integer.parseInt(fieldSrc.getText());
                int destEdge = Integer.parseInt(fieldDest.getText());
                index_of_shortest =new ArrayList<>();
                index_of_shortest.add(srcEdge);
                index_of_shortest.add(destEdge);
                drawGraph();

                JOptionPane.showMessageDialog(null,"the weight of the edge is "+this.algoGraph.getGraph().getEdge(srcEdge,destEdge).getWeight() );

            }

        }

        else if (e.getSource() == add_edge){
        JTextField fieldSrc = new JTextField(5);
        JTextField fieldDest = new JTextField(5);
        JTextField fieldWeight = new JTextField(10);
        JPanel myPanel = new JPanel();

        myPanel.add(new JLabel("source:"));
        myPanel.add(fieldSrc);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("destination: "));
        myPanel.add(fieldDest);
        myPanel.add(new JLabel("Weight: "));
        myPanel.add(fieldWeight);

        int resualt = JOptionPane.showConfirmDialog(null,myPanel,"Add edge ", JOptionPane.OK_CANCEL_OPTION);
        if (resualt == JOptionPane.OK_OPTION){
            int srcEdge = Integer.parseInt(fieldSrc.getText());
            int destEdge = Integer.parseInt(fieldDest.getText());
            double weightEdge = Double.valueOf(fieldWeight.getText());
            this.graph.connect(srcEdge,destEdge,weightEdge);
            this.algoGraph.getGraph().connect(srcEdge,destEdge,weightEdge);
            drawGraph();

        }




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

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Shortest path", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int src = Integer.parseInt(xField.getText()) ;
            int dest = Integer.parseInt(yField.getText());

            double distance = algoGraph.shortestPathDist(src,dest);

            List<NodeData> nodes = algoGraph.shortestPath(src,dest);
            index_of_shortest = nodes.stream().map(x -> x.getKey()).collect(Collectors.toList());
            drawGraph();
            JOptionPane.showMessageDialog(this,"The distance is " + distance);

        }
    }

    public static boolean isWindows(String OSname){
        return OSname.startsWith("Windows");
    }
    public static double scaleMouse(double data, double r_min, double r_max, double t_min, double t_max)
    {
        double res = ((data -t_min)/(t_max - t_min))*(r_max-r_min)+r_min;
        return res;

        //32.343434343 -> 100
        //33.0001
        //(res1 -  t_min ) = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) ;
        //((res1 -  t_min )/(t_max - t_min))*(r_max-r_min)+r_min = data

    }

    @Override
    public void mouseClicked(MouseEvent e) {
            double factorX = getWidth() / scaleX * 0.8;
            double factorY = getHeight() / scaleY * 0.8;
            x_and_y[0]= e.getX();
            x_and_y[1]= e.getY();

            double x =  ((x_and_y[0] - incrementX ) / factorX) + minX;
            double y =  ((x_and_y[1] - incrementY ) / factorY) + minY;
            NodeV newNodeAdd = new NodeV(choosen_key,x+","+y+",0.0");
            algoGraph.getGraph().addNode((NodeData) newNodeAdd);
            graph = algoGraph.getGraph();
            drawGraph();
            this.removeMouseListener(this);



    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
