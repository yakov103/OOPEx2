package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class graphAlgo implements DirectedWeightedGraphAlgorithms{
    private DirectedGraph graph ;

    public graphAlgo(){
    }

    public graphAlgo(DirectedGraph graph){
        this.graph=graph;
    }


    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = new DirectedGraph((DirectedGraph)g);
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        if (this.graph==null)
            return null;
        DirectedGraph copyG=new DirectedGraph();
        Iterator<NodeData> nodeIter=this.graph.nodeIter();
        Iterator<EdgeData> edgeIter=this.graph.edgeIter();
        while(nodeIter.hasNext()){
            NodeV temp=new NodeV((NodeV)nodeIter.next());
            copyG.NodesHash.put(temp.id,temp);
            copyG.Nodes.add(temp);
        }
        while(edgeIter.hasNext()){
            Edge temp=new Edge((Edge) edgeIter.next());
            copyG.EdgesHash.put(temp.getKey(),temp);
            copyG.Edges.add(temp);
        }
        int size=this.graph.IEdges.size();
        for (int i=0;i<size;i++) {
            Iterator iterCurrent= this.graph.nodeIter(i); //save this current id iterator of this graph
            copyG.IEdges.add(new ArrayList<NodeV>());  // add the id i array list
            ArrayList copyCurrentArray=copyG.IEdges.get(i);     // save i array list as pramter
            while(iterCurrent.hasNext()){
                NodeV temp=(NodeV)iterCurrent.next();
                copyCurrentArray.add(temp);
            }
        }
        Iterator<NodeData> nodeIter2=this.graph.nodeIter();
        while (nodeIter2.hasNext()){
            copyG.nodeList.add(nodeIter2.next().getKey());
        }

        return copyG;

    }

    @Override
    public boolean isConnected() {

        Iterator iterator=this.graph.nodeIter();          //set all white
        while (iterator.hasNext()) {
            NodeV nodeV = (NodeV) iterator.next();
            nodeV.setInfo("white");
        }

        BFS(this.graph,"gray");
        if (!graph.checkIfAllTheSameColor("gray"))
            return false;
        iterator=this.graph.nodeIter();                   //set all white
        while (iterator.hasNext()) {
            NodeV nodeV = (NodeV) iterator.next();
            nodeV.setInfo("white");
        }
        DirectedGraph Rg=DirectedGraph.reverseGraph(graph);
        BFS(Rg,"black");
        return Rg.checkIfAllTheSameColor("black");
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        DirectedGraph graph=this.graph;
        PriorityQueue<NodeV> q=new PriorityQueue<NodeV>((o1, o2) ->((int)(o1.length-o2.length )));

        Iterator iterNode=graph.nodeIter();
        NodeV first= ((NodeV)graph.NodesHash.get(src));
        first.length=0;
        while(iterNode.hasNext()){    //add the nodes to the q
            NodeV n=(NodeV)iterNode.next();
            q.add(n);
            if (n!=first)
                n.length=Integer.MAX_VALUE;
            n.previous=null;
        }
        while(!q.isEmpty()){
            NodeV current=q.remove();
            Iterator iteratorOfCurrent=graph.nodeIter(current.id);
            while(iteratorOfCurrent.hasNext()){
                NodeV neighbor=(NodeV)iteratorOfCurrent.next();
                double dist=current.length+graph.EdgesHash.get(current.id+"_"+neighbor.id).getWeight();
                if (dist<neighbor.length) {
                    neighbor.length = dist;
                    neighbor.previous=current;
                    q.remove(neighbor);
                    q.add(neighbor);              //to update his location in the q;
                }
            }
        }
        if (((NodeV)graph.NodesHash.get(dest)).length!=Integer.MAX_VALUE)
            return ((NodeV)graph.NodesHash.get(dest)).length;
        else return -1;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        DirectedGraph graph=this.graph;
        shortestPathDist(src,dest);
        List<NodeData> l=new ArrayList<NodeData>();
        NodeV destNode=(NodeV)this.graph.NodesHash.get(dest);
        NodeV destNodeTemp;

        while(destNode.previous!=null) {
            l.add(0,destNode);
            destNodeTemp=destNode;
            destNode=destNode.previous;
            destNodeTemp.previous=null;
        }
        l.add(0,destNode);

        return l;
    }

    @Override
    public NodeData center() {
        if (!this.isConnected())
            return null;
        DirectedGraph graph = this.graph;
        Iterator<NodeData> iterNodeMain = graph.nodeIter();
        double longestLength = Integer.MAX_VALUE;
        NodeData center = null;

        while (iterNodeMain.hasNext()) {              //active shortpathdist on all the nodes
            NodeData t = iterNodeMain.next();
            double tempLong = 0;
            NodeV tempNode = null;
            this.shortestPathDist(t.getKey(), 0);
            Iterator<NodeData> iterNode = graph.nodeIter();
            while (iterNode.hasNext()) {
                tempNode = ((NodeV) iterNode.next());
                if (tempNode.length > tempLong) {
                    tempLong = tempNode.length;
                }
            }
            if (tempLong < longestLength) {                       //take the smallest
                longestLength = tempLong;
                center = t;                                   //save the node
            }
        }
        ((NodeV)center).previous=null;
        return center;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {

        DirectedGraph graph=this.graph;
        List<NodeData>mainList=new ArrayList<>();
        mainList.add(cities.get(0));
        ArrayList<Integer>idCities=new ArrayList<>();        //array for the cities id
        for (int j=0;j<cities.size();j++)
            idCities.add(cities.get(j).getKey());

        int currentStart=idCities.get(0);
        idCities.remove(0);
        while (idCities.size()!=0) {                        // until pass in all the cities

            List<NodeData> templest = shortestPath(currentStart, idCities.get(0));
            currentStart=idCities.get(0);
            for (int i = 0; i < templest.size(); i++) {
                idCities.contains(templest.get(i).getKey());
                idCities.remove((Integer) templest.get(i).getKey());
            }
            for (int i=1;i<templest.size();i++){
                mainList.add(templest.get(i));
            }

        }
        return mainList;
    }

    @Override
    public boolean save(String file) {
        DirectedGraph graph=(DirectedGraph) this.copy();
        DirectedGraph.setGraphColor(null,graph);
        DirectedGraph.setprevious(graph);
        graph.EdgesHash=null;
        graph.NodesHash=null;
        graph.MC=null;
        graph.IEdges=null;
        graph.IEdgesEdge=null;
        graph.nodeList=null;

        Gson json=new GsonBuilder().setPrettyPrinting().create();
        String jsonString=json.toJson(graph);

        PrintWriter writer= null;
        try {
            writer = new PrintWriter(file);
            writer.write(jsonString);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    @Override
    public boolean load(String file) {

        DirectedGraph ans=null;
        Gson json=new Gson();
        try {
            ans=json.fromJson(new FileReader(file), DirectedGraph.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
       this.graph=new DirectedGraph(ans);
        return true;
    }

    public static void BFS(DirectedGraph graph,String color) {
        Stack<NodeData> S = new Stack();
        int startNode = graph.nodeList.get(0);
        S.push(graph.getNode(startNode));
        graph.getNode(startNode).setInfo("gray");

        while (!S.isEmpty()) {
            NodeV t = (NodeV) S.pop();
            Iterator iterator= graph.nodeIter(t.id);
            while (iterator.hasNext()) {

                NodeV srcNode=(NodeV) iterator.next();
                if (srcNode.getInfo() != color) {
                    srcNode.setInfo(color);
                    S.push(srcNode);
                }
            }
        }

    }

}
