package GUI;

import GUI.MainWindow;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;

import javax.swing.*;
import java.awt.*;

public class GraphicsController {

    MainWindow frame;
    DirectedWeightedGraphAlgorithms directedGraph;

    public GraphicsController(DirectedWeightedGraphAlgorithms directedGraph){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int height = dimension.height;
        int width = dimension.width;
        frame = new MainWindow(1000,1000, directedGraph);
        ImageIcon icon = new ImageIcon("node.png");
        frame.setIconImage(icon.getImage());
        this.directedGraph = directedGraph;

    }


    private void drawGraph(){
//        Iterator iter = directedGraph.edgeIter();
//        while(iter.hasNext()){
//            Edge edge = (Edge) iter.next();
//            frame.drawEdge(ed);
//        }

    }

    public void run() {}
}
