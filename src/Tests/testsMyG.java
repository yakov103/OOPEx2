package Tests;

import api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class testsMyG {
    static DirectedWeightedGraphAlgorithms algorithms = new graphAlgo();

    @BeforeAll
    static void createG() {
        algorithms.load("data/MyG.json");
    }
    @Test
    void testIsConnected(){
        Assertions.assertTrue(algorithms.isConnected());
    }
    @Test
    void testshortpathDist(){
        Assertions.assertEquals(9.0,algorithms.shortestPathDist(2,10));
        Assertions.assertEquals(8.063160514269988,algorithms.shortestPathDist(11,3));
    }
    @Test
    void testCenter(){
        Assertions.assertEquals(6,algorithms.center().getKey());
    }
    @Test
    void testshortpath(){
        int arr[]={1, 2, 6, 7, 8, 9, 10, 11, 12};
        List<NodeData> l=algorithms.shortestPath(1,12);
        for (int i = 0; i <arr.length; i++) {
            Assertions.assertEquals(arr[i],l.get(i).getKey());
        }

    }

}
