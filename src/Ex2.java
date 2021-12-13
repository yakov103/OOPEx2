import GUI.GraphicsController;
import api.*;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
   // String json_file= args[0];
    //runGUI(json_file);
//        String s="data\\G1.json";
//        graphAlgo algo=new graphAlgo();
//        algo.load(s);




    }

}