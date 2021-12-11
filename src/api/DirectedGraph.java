package api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

public class DirectedGraph implements DirectedWeightedGraph {
    public NodeV[] Nodes;
    public Edge[] Edges;
    public int MC;
    public ArrayList<ArrayList<NodeV>> IEdges;
    public ArrayList<ArrayList<Edge>> IEdgesEdge;
    public HashMap<Integer, NodeData> NodesHash;
    public HashMap<String, Edge> EdgesHash;
    public ArrayList<Integer> nodeList;

    public DirectedGraph(DirectedGraph G) {
        this.EdgesHash = new HashMap<>();
        this.NodesHash = new HashMap<>();
        this.Nodes = G.Nodes;
        this.Edges = G.Edges;
        MC = 0;
        this.nodeList = new ArrayList<>();
        this.IEdges = new ArrayList<>();
        this.IEdgesEdge = new ArrayList<>();
        for (int i = 0; i < G.Nodes.length; i++) {         //move the nodes,edges to the hashmap
            Nodes[i].setInfo("white");
            this.nodeList.add(G.Nodes[i].id);
            this.addNode(Nodes[i]);
        }
        for (int i = 0; i < NodesHash.size(); i++) {
            this.IEdges.add(new ArrayList<>());
            this.IEdgesEdge.add(new ArrayList<Edge>());
        }
        for (int i = 0; i < this.Edges.length; i++) {
            //this.connect(G.Edges[i].getSrc(), G.Edges[i].getDest(), G.Edges[i].getWeight());
            //  this.IEdges.get(G.Edges[i].getSrc()).add((NodeV) G.getNode(G.Edges[i].getDest()));
            int src= G.Edges[i].getSrc();
            int dest=G.Edges[i].getDest();
            double w=G.Edges[i].getWeight();
            Edge newEdge = new Edge(src, dest, w);
            String key = src + "_" + dest;
            EdgesHash.put(key, newEdge);
            IEdges.get(src).add((NodeV) this.getNode(dest));
            IEdgesEdge.get(src).add(newEdge);
        }
    }

    public DirectedGraph() {
        MC = 0;
        this.EdgesHash = new HashMap<>();
        this.NodesHash = new HashMap<>();
        this.IEdges = new ArrayList<ArrayList<NodeV>>();
        this.IEdgesEdge = new ArrayList<ArrayList<Edge>>();
        this.nodeList = new ArrayList<Integer>();


    }


    @Override
    public NodeData getNode(int key) {
        return NodesHash.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        String t = src + "_" + dest;
        if (EdgesHash.containsKey(t)){
            return EdgesHash.get(t);
        }
        else {
         return null;
        }

    }

    @Override
    public void addNode(NodeData n) {
        MC++;
        NodesHash.put(n.getKey(), n);
        if (IEdges.size() <= n.getKey()) {
            IEdges.add(new ArrayList<>());       //need to check if the id is ok,if we get order id
            IEdgesEdge.add(new ArrayList<>());
        }
        if (!this.nodeList.contains(n.getKey()))
            this.nodeList.add(n.getKey());
    }

    @Override
    public void connect(int src, int dest, double w) {                  //check th mc++
        MC++;
        if (!NodesHash.containsKey(src) || !NodesHash.containsKey(dest)) {

        } else {
            Edge newEdge = new Edge(src, dest, w);
            String key = src + "_" + dest;
            EdgesHash.put(key, newEdge);
            IEdges.get(src).add((NodeV) this.getNode(dest));
            IEdgesEdge.get(src).add(newEdge);

        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        //return this.NodesHash.values().iterator();
        return new Iterator<NodeData>() {

            Iterator<NodeData> iterator = NodesHash.values().iterator();
            NodeData lastNode = null;
            private int iterMC = MC;

            @Override
            public boolean hasNext() {
                if (this.iterMC != MC)
                    throw new RuntimeException("the graph was update while the iterator was running");
                return iterator.hasNext();
            }

            @Override
            public NodeData next() {
                if (this.iterMC != MC)
                    throw new RuntimeException("the graph was update while the iterator was running");
                lastNode = iterator.next();
                return lastNode;
            }

            @Override
            public void remove() {
                if (this.iterMC != MC)
                    throw new RuntimeException("the graph was update while the iterator was running");
                if (lastNode != null)
                    removeNode(lastNode.getKey());
                Iterator.super.remove();
            }
        };

    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        // Iterator iterator=this.EdgesHash.values().iterator();
        //return iterator;
        return new Iterator<EdgeData>() {
            Iterator<Edge> iterator = EdgesHash.values().iterator();
            Edge lastEdge = null;
            private int iterMC = MC;

            @Override
            public boolean hasNext() {
                if (this.iterMC != MC)
                    throw new RuntimeException("the graph was update while the iterator was running");
                return iterator.hasNext();
            }

            @Override
            public EdgeData next() {
                if (this.iterMC != MC)
                    throw new RuntimeException("the graph was update while the iterator was running");
                lastEdge = iterator.next();
                return lastEdge;
            }
            public void remove() {
                if (this.iterMC != MC)
                    throw new RuntimeException("the graph was update while the iterator was running");
                if (lastEdge != null)
                    removeEdge(lastEdge.getSrc(),lastEdge.getDest());
                Iterator.super.remove();
            }
        };
    }

    public Iterator<NodeData> nodeIter(int node_id) {                  //not from the interface
        Iterator iterator = this.IEdges.get(node_id).iterator();
        return iterator;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
//        Iterator iterator = this.IEdgesEdge.get(node_id).iterator();
//        return iterator;
        return new Iterator<EdgeData>() {
            Iterator<Edge> iterator =IEdgesEdge.get(node_id).iterator();
            Edge lastEdge = null;
            private int iterMC = MC;

            @Override
            public boolean hasNext() {
                if (this.iterMC != MC)
                    throw new RuntimeException("the graph was update while the iterator was running");
                return iterator.hasNext();
            }

            @Override
            public EdgeData next() {
                if (this.iterMC != MC)
                    throw new RuntimeException("the graph was update while the iterator was running");
                lastEdge = iterator.next();
                return lastEdge;
            }
            public void remove() {
                if (this.iterMC != MC)
                    throw new RuntimeException("the graph was update while the iterator was running");
                if (lastEdge != null)
                    removeEdge(lastEdge.getSrc(),lastEdge.getDest());
                Iterator.super.remove();
            }
        };
    }

    @Override
    public NodeData removeNode(int key) {
        MC++;
        return NodesHash.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        MC++;
        return EdgesHash.remove(src + "_" + dest);
    }

    @Override
    public int nodeSize() {
        return NodesHash.size();
    }

    @Override
    public int edgeSize() {
        return EdgesHash.size();
    }

    @Override
    public int getMC() {
        return MC;
    }

    public HashMap<Integer, NodeData> getHashNode() {
        return this.NodesHash;
    }

    public HashMap<String, Edge> getHashEdge() {
        return this.EdgesHash;
    }

    public NodeV[] getArrNodes() {
        return this.Nodes;
    }

    public Edge[] getArrEdges() {
        return this.Edges;
    }

    public static DirectedGraph reverseGraph(DirectedGraph G) {
        DirectedGraph Rg = new DirectedGraph();
        Rg.NodesHash = (HashMap<Integer, NodeData>) G.NodesHash.clone();
        for (int i = 0; i < G.NodesHash.size(); i++)                           //made the IEdges for Rg
            Rg.IEdges.add(new ArrayList<>());

        Iterator iterator = G.edgeIter();
        while (iterator.hasNext()) {//made the reverse Edges
            Edge edge = (Edge) iterator.next();
            Edge REdge = new Edge(edge.getDest(), edge.getSrc(), edge.getWeight());
            String key = (edge.getDest() + "_" + edge.getSrc());
            Rg.EdgesHash.put(key, REdge);
            Rg.IEdges.get(edge.getDest()).add((NodeV) G.NodesHash.get(edge.getSrc()));  //revrese IEdges
        }
        Iterator iterator2 = G.nodeIter();
        while (iterator2.hasNext()) {
            Rg.nodeList.add(((NodeV) iterator2.next()).getKey());
        }

        return Rg;
    }

    public boolean checkIfAllTheSameColor(String color) {
        Iterator iterator = this.nodeIter();
        while (iterator.hasNext()) {
            if (((NodeV) iterator.next()).getInfo() != color)
                return false;
        }
        return true;
    }

    public static void setGraphColor(String color, DirectedGraph graph) {
        Iterator iterator = graph.nodeIter();          //set all white
        while (iterator.hasNext()) {
            NodeV nodeV = (NodeV) iterator.next();
            nodeV.setInfo(color);
        }
    }

    public static void setprevious(DirectedGraph graph){
        Iterator iterator=graph.nodeIter();
        while(iterator.hasNext()){
            NodeV nodeV=(NodeV) iterator.next();
            nodeV.previous=null;
        }
    }

}
