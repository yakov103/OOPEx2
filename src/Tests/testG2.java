package Tests;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import api.graphAlgo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class testG2 {
    static DirectedWeightedGraphAlgorithms algorithms = new graphAlgo();

    @BeforeAll
    static void createG() {
        algorithms.load("data/G2.json");
    }
    @Test
    void testLoad(){
        //check the time off the load
        algorithms.load("data/G2.json");
    }
    @Test
    void testIsConnected(){
        Assertions.assertTrue(algorithms.isConnected());
    }
    @Test
    void testshortpathDist(){
        Assertions.assertEquals(2.2997285064109363,algorithms.shortestPathDist(12,20));
        Assertions.assertEquals(6.740087822793423,algorithms.shortestPathDist(11,0));
    }
    @Test
    void testCenter(){
        Assertions.assertEquals(0,algorithms.center().getKey());
    }
    @Test
    void testshortpath(){
        int arr[]={6, 7, 8, 9, 10, 11};
        List<NodeData> l=algorithms.shortestPath(6,11);
        for (int i = 0; i <arr.length; i++) {
            Assertions.assertEquals(arr[i],l.get(i).getKey());
        }

    }

}
