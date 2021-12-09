package GUI;

import GUI.MainWindow;
import api.DirectedWeightedGraph;

public class GraphicsController {

    MainWindow frame;
    DirectedWeightedGraph directedGraph;

    public GraphicsController(DirectedWeightedGraph directedGraph){
        frame = new MainWindow(1000,1000, directedGraph);
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
