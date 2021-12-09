import GUI.GraphicsController;
import api.*;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
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
        String JsonString="";
        try {
            File f = new File(json_file);
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                JsonString += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        DirectedWeightedGraph ans=null;
        String JsonS=JsonString;
        Gson json =new Gson();
        ans= json.fromJson(JsonString,DirectedGraph.class);
        ans=new DirectedGraph((DirectedGraph) ans);
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new graphAlgo((DirectedGraph) getGrapg(json_file));
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
    String json_file= "C:\\Users\\HP\\IdeaProjects\\OOPEx2\\data\\G1.json";

        //  String json_file= "/Users/yakovkhodorkovski/IdeaProjects/OOP-Ex2/data/G3.json";
        DirectedWeightedGraph g = getGrapg(json_file);
        graphAlgo algo = new graphAlgo();
        algo.init(g);
        runGUI(json_file);
      //  boolean  flag= algo.isConnected();
        System.out.println(algo.isConnected());
        System.out.println(algo.center());
            System.out.println((algo.shortestPathDist(8,60)));
        algo.shortestPath(1,12).forEach((a)->{
            System.out.println( a.getKey());
        });
      //  GraphicsController controller = new GraphicsController((DirectedWeightedGraphAlgorithms)g);
    }

}