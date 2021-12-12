package Tests;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import api.graphAlgo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class testsG1000 {
    static DirectedWeightedGraphAlgorithms algorithms = new graphAlgo();

    @BeforeAll
    static void createG() {
     algorithms.load("data/1000Nodes.json");
    }
    @Test
    void testLoad(){
        //check the time off the load
        algorithms.load("data/1000Nodes.json");
    }
    @Test
    void testIsConnected(){
        Assertions.assertTrue(algorithms.isConnected());
    }
    @Test
    void testshortpathDist(){
        Assertions.assertEquals(664.4101389180603,algorithms.shortestPathDist(120,130));
        Assertions.assertEquals(1021.7831848237844,algorithms.shortestPathDist(11,0));
    }
    @Test
    void testCenter(){
        Assertions.assertEquals(362,algorithms.center().getKey());
    }
    @Test
    void testshortpath(){
        int arr[]={8, 195, 835, 877, 175, 349, 14};
        List<NodeData> l=algorithms.shortestPath(8,14);
        for (int i = 0; i <arr.length; i++) {
            Assertions.assertEquals(arr[i],l.get(i).getKey());
        }

    }

}
