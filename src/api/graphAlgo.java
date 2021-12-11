package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class graphAlgo implements DirectedWeightedGraphAlgorithms{
    DirectedGraph graph ;

    public graphAlgo(){
    }

    public graphAlgo(DirectedGraph graph){
        this.graph=graph;
    }


    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = new DirectedGraph( (DirectedGraph )g);
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        DirectedGraph copyG=new DirectedGraph();
        Iterator<NodeData> nodeIter=this.graph.nodeIter();
        Iterator<EdgeData> edgeIter=this.graph.edgeIter();
        while(nodeIter.hasNext()){
            NodeV temp=new NodeV((NodeV)nodeIter.next());
            copyG.NodesHash.put(temp.id,temp);
        }
        while(edgeIter.hasNext()){
            Edge temp=new Edge((Edge) edgeIter.next());
            copyG.EdgesHash.put(temp.getKey(),temp);
        }
        int size=this.graph.IEdges.size();
        for (int i=0;i<size;i++) {
            Iterator iterCurrent= this.graph.nodeIter(i); //save this current id iterator of this graph
            copyG.IEdges.add(new ArrayList<NodeV>());  // add the id i array list
            ArrayList copyCurrentArray=copyG.IEdges.get(i);     // save i array list as pramter
            while(iterCurrent.hasNext()){
                NodeV temp=(NodeV)iterCurrent.next();
                copyCurrentArray.add(iterCurrent.next());
            }
        }
        Iterator<NodeData> nodeIter2=this.graph.nodeIter();
        while (nodeIter2.hasNext()){
            copyG.nodeList.add(nodeIter2.next().getKey());
        }

        return null;

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

        while(destNode.previous!=null) {
            l.add(0,destNode);
            destNode=destNode.previous;
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
        return center;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        DirectedGraph graph=this.graph;
        ArrayList<Integer>idCities=new ArrayList<>();        //array for the cities id
        for (int j=0;j<cities.size();j++)
            idCities.add(cities.get(j).getKey());
        DirectedGraph.setGraphColor("white",graph);    //set graph to white

        List<NodeData>l=new ArrayList();
        l.add(cities.remove(0));
        idCities.remove((Integer)0);

        while(idCities.size()!=0) {                            // until pass on all the nodes

            Iterator<EdgeData> iterCurrent = graph.edgeIter(l.get(l.size()-1).getKey());  //iterator of the last in my list
            double minW = Integer.MAX_VALUE;
            NodeData NodeWithMinW = null;
            while (iterCurrent.hasNext()) {
                EdgeData etemp = iterCurrent.next();
                NodeData destNode = graph.NodesHash.get(etemp.getDest());
                if (idCities.contains(destNode.getKey())&&etemp.getWeight() < minW && destNode.getInfo() != "gray") {        // take the Node with min w to get him
                    NodeWithMinW = destNode;
                    // minW=etemp.getWeight();                     // without this is better
                }
            }
            if (NodeWithMinW == null) {                                         //if all already gray or not in cities so take the first node
                l.add(new NodeV(graph.IEdges.get(idCities.get(idCities.size()-1)).get((int)(Math.random()*graph.IEdges.get(idCities.get(idCities.size()-1)).size()))));
                l.get(l.size()-1).setInfo("gray");
            } else {
                idCities.remove((Integer)NodeWithMinW.getKey());
                l.add(NodeWithMinW);
                NodeWithMinW.setInfo("gray");
            }
        }
        return l;
    }

    @Override
    public boolean save(String file) {

        DirectedGraph.setGraphColor(null,graph);
        DirectedGraph.setprevious(graph);

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
        String JsonString="";
        try {
            File f = new File(file);
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                JsonString += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        DirectedWeightedGraph ans=null;
        String JsonS=JsonString;
        Gson json =new Gson();
        ans= json.fromJson(JsonString,DirectedGraph.class);
        ans=new DirectedGraph((DirectedGraph) ans);
        this.graph=(DirectedGraph) ans;
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
