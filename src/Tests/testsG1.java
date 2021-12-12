package Tests;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import api.graphAlgo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class testsG1 {
    static DirectedWeightedGraphAlgorithms algorithms = new graphAlgo();

    @BeforeAll
    static void createG() {
        algorithms.load("data/G1.json");
    }
    @Test
    void testLoad(){
        //check the time off the load
        algorithms.load("data/G1.json");
    }
    @Test
    void testIsConnected(){
        Assertions.assertTrue(algorithms.isConnected());
    }
    @Test
    void testshortpathDist(){
        Assertions.assertEquals(7.222204998814062,algorithms.shortestPathDist(2,10));
        //Assertions.assertEquals(10.71633463878431,algorithms.shortestPathDist(11,3));
    }
    @Test
    void testCenter(){
        Assertions.assertEquals(8,algorithms.center().getKey());
    }
    @Test
    void testshortpath(){
        int arr[]={1, 0, 16, 15, 14, 13, 12 };
        List<NodeData> l=algorithms.shortestPath(1,12);
        for (int i = 0; i <arr.length; i++) {
            Assertions.assertEquals(arr[i],l.get(i).getKey());
        }

    }

}
