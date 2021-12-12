import GUI.GraphicsController;
import api.*;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedGraph ans=null;
        Gson json=new Gson();
        try {
            ans=json.fromJson(new FileReader(json_file), DirectedGraph.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DirectedGraph(ans);
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans=new graphAlgo() ;
        ans.load(json_file);
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        GraphicsController controller=new GraphicsController(alg);

    }

    public static void main(String[] args) {
    String json_file= "C:\\Users\\HP\\IdeaProjects\\OOPEx2\\120.json";
//        DirectedGraph g1=(DirectedGraph) getGrapg(json_file);
//        g1.nodeIter().next();
//        g1.getMC();
      //   String json_file= "/Users/yakovkhodorkovski/IdeaProjects/OOP-Ex2/data/G3.json";
        DirectedWeightedGraphAlgorithms algo =new graphAlgo() ;
        algo.load(json_file);
        System.out.println("load");
     //   runGUI(json_file);
//        boolean  flag= algo.isConnected();
//        System.out.println(algo.isConnected());
//        System.out.println("///////////////////////");
//        System.out.println((algo.shortestPathDist(220,235)));
//        System.out.println((algo.shortestPathDist(11,19)));
//        System.out.println("///////////////////////");
//        algo.shortestPath(20,14).forEach((a)->{
//            System.out.println( a.getKey());
//        });
//        System.out.println("///////////////////////");
//        System.out.println(algo.center());
        //  GraphicsController controller = new GraphicsController((DirectedWeightedGraphAlgorithms)g);
        List<NodeData> list=new ArrayList();
        list.add(algo.getGraph().getNode(3));
        list.add(algo.getGraph().getNode(4));
        list.add(algo.getGraph().getNode(14));
        //list.add(algo.getGraph().getNode(7));
        algo.tsp(list).forEach((a)->{
            System.out.println( a.getKey());
        });
     //  runGUI(json_file);

    }

}