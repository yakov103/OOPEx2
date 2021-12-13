//package Tests;
//
//import api.DirectedWeightedGraphAlgorithms;
//import api.NodeData;
//import api.graphAlgo;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//public class testsG100000 {
//    static DirectedWeightedGraphAlgorithms algorithms = new graphAlgo();
//
//    @BeforeAll
//    @Test
//    static void createAndLoad() {
//        // load the file test
//        algorithms.load("100000.json");
//    }
//
//    @Test
//    void testIsConnected(){
//        Assertions.assertTrue(algorithms.isConnected());
//    }
//    @Test
//    void testshortpathDist(){
//        Assertions.assertEquals(311.88070524268943,algorithms.shortestPathDist(220,235));
//    }
//    @Test
//    void testshortpath(){
//        int arr[]={20, 72923, 88936, 8567, 10528, 81978, 9477, 8041, 21149, 23526, 14};
//        List<NodeData> l=algorithms.shortestPath(20,14);
//        for (int i = 0; i <arr.length; i++) {
//            Assertions.assertEquals(arr[i],l.get(i).getKey());
//        }
//
//    }
//
//}
