package Tests;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import api.graphAlgo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class tests10000G {
    static DirectedWeightedGraphAlgorithms algorithms = new graphAlgo();

    @BeforeAll
    static void createG() {
        algorithms.load("data/10000Nodes.json");
    }
    @Test
    void testLoad(){
        //check the time off the load
        algorithms.load("data/10000Nodes.json");
    }
    @Test
    void testIsConnected(){
        Assertions.assertTrue(algorithms.isConnected());
    }
    @Test
    void testshortpathDist(){
        Assertions.assertEquals(1194.7136977554017,algorithms.shortestPathDist(220,235));
        Assertions.assertEquals(1113.3601770824541,algorithms.shortestPathDist(11,19));
    }
    @Test
    void testshortpath(){
        int arr[]={20, 8214, 9223, 6258, 506, 4324,14};
        List<NodeData> l=algorithms.shortestPath(20,14);
        for (int i = 0; i <arr.length; i++) {
            Assertions.assertEquals(arr[i],l.get(i).getKey());
        }

    }

}
