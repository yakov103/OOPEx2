package api;

import com.google.gson.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class check {
    public static void main(String[] args) {

//        Point p[];
//        Gson json=new Gson();
//        p=json.fromJson("{[{\"x\":8,\"y\":2}]}",Point[].class);
//        System.out.println(p[0].x);

//        String s;
//        Gson json=new Gson();
//        s=json.fromJson("27",String.class);
//        System.out.println(s);

//        Edge e;
//        Gson json=new Gson();
//        e=json.fromJson("{\"w\":\"8\",\"src\":\"2\",\"dest\":\"8\"}",Edge.class);
        PriorityQueue<Integer> q=new PriorityQueue<Integer>((o1, o2) -> o1-o2 );
        q.add(12);
        q.add(6);
        q.add(10);
        System.out.println(q.peek());
        int n;
        List<Integer> l1 = new ArrayList<Integer>();

//        DirectedGraph d;
//        Gson json = new Gson();
//        String s="{ \"Edges\": [ { \"src\":0,\"w\":1.3118716362419698, \"dest\":16}] }"  ;
//        d = json.fromJson(s,DirectedGraph.class);
//        System.out.println(Math.pow(2,3));
    }
}
