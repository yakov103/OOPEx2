package Tests;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import api.graphAlgo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class testG3 {
    static DirectedWeightedGraphAlgorithms algorithms = new graphAlgo();

    @BeforeAll
    static void createG() {
        algorithms.load("data/G3.json");
    }
    @Test
    void testLoad(){
        //check the time off the load
        algorithms.load("data/G3.json");
    }
    @Test
    void testIsConnected(){
        Assertions.assertTrue(algorithms.isConnected());
    }
    @Test
    void testshortpathDist(){
        Assertions.assertEquals(9.726745781590104,algorithms.shortestPathDist(12,20));
        Assertions.assertEquals(2.4782397407340064,algorithms.shortestPathDist(11,0));
    }
    @Test
    void testCenter(){
        Assertions.assertEquals(40,algorithms.center().getKey());
    }
    @Test
    void testshortpath(){
        int arr[]={6,11};
        List<NodeData> l=algorithms.shortestPath(6,11);
        for (int i = 0; i <arr.length; i++) {
            Assertions.assertEquals(arr[i],l.get(i).getKey());
        }

    }

}
