package api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

public class DirectedGraph implements DirectedWeightedGraph {
    public ArrayList<NodeV> Nodes;
    public ArrayList<Edge> Edges;
    public Integer MC;
    public ArrayList<ArrayList<NodeV>> IEdges;
    public ArrayList<ArrayList<Edge>> IEdgesEdge;
    public HashMap<Integer, NodeData> NodesHash;
    public HashMap<String, Edge> EdgesHash;
    public ArrayList<Integer> nodeList;
    public ArrayList<ArrayList<Edge>> AllIconnectTo;


    public DirectedGraph(DirectedGraph G) {
        this.EdgesHash = new HashMap<>();
        this.NodesHash = new HashMap<>();
        this.Nodes = G.Nodes;
        this.Edges = G.Edges;
        MC = 0;
        this.nodeList = new ArrayList<>();
        this.IEdges = new ArrayList<>();
        this.AllIconnectTo = new ArrayList<>();
        this.IEdgesEdge = new ArrayList<>();
        for (int i = 0; i < G.Nodes.size(); i++) {         //move the nodes,edges to the hashmap
            Nodes.get(i).setInfo("white");
            this.nodeList.add(G.Nodes.get(i).getKey());
            this.addNode(Nodes.get(i));
        }
        for (int i = 0; i < NodesHash.size(); i++) {
            this.IEdges.add(new ArrayList<>());
            this.IEdgesEdge.add(new ArrayList<Edge>());
            this.AllIconnectTo.add(new ArrayList<Edge>());
        }
        for (int i = 0; i < this.Edges.size(); i++) {
            //this.connect(G.Edges[i].getSrc(), G.Edges[i].getDest(), G.Edges[i].getWeight());
            //  this.IEdges.get(G.Edges[i].getSrc()).add((NodeV) G.getNode(G.Edges[i].getDest()));
            int src= G.Edges.get(i).getSrc();
            int dest=G.Edges.get(i).getDest();
            double w=G.Edges.get(i).getWeight();
            Edge newEdge = new Edge(src, dest, w);
            String key = src + "_" + dest;
            EdgesHash.put(key, newEdge);
            IEdges.get(src).add((NodeV) this.getNode(dest));
            IEdgesEdge.get(src).add(newEdge);
            AllIconnectTo.get(src).add(newEdge);
            AllIconnectTo.get(dest).add(newEdge);
        }
    }

    public DirectedGraph() {
        MC = 0;
        this.EdgesHash = new HashMap<>();
        this.NodesHash = new HashMap<>();
        this.IEdges = new ArrayList<ArrayList<NodeV>>();
        this.IEdgesEdge = new ArrayList<ArrayList<Edge>>();
        this.nodeList = new ArrayList<Integer>();
        this.Edges=new ArrayList<Edge>();
        this.Nodes=new ArrayList<NodeV>();


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
        if (n != null) {
            MC++;
            NodesHash.put(n.getKey(), n);
            while(IEdges.size() <= n.getKey())
                                             {
                IEdges.add(new ArrayList<>());
                IEdgesEdge.add(new ArrayList<>());
                AllIconnectTo.add(new ArrayList<>());
            }
            if (!this.nodeList.contains(n.getKey()))
                this.nodeList.add(n.getKey());
        }
    }

    @Override
    public void connect(int src, int dest, double w) {                  //check th mc++
        MC++;
        if (!NodesHash.containsKey(src) || !NodesHash.containsKey(dest)) {

        } else {
//            if (IEdgesEdge)
            Edge newEdge = new Edge(src, dest, w);
            String key = src + "_" + dest;
            EdgesHash.put(key, newEdge);
            IEdges.get(src).add((NodeV) this.getNode(dest));
            IEdgesEdge.get(src).add(newEdge);
            AllIconnectTo.get(src).add(newEdge);
            AllIconnectTo.get(dest).add(newEdge);

        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {

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
            }
        };
    }

    @Override
    public NodeData removeNode(int key) {
        MC++;
        NodeData removedNode;
        while(this.AllIconnectTo.get(key).size()!=0){
            Edge e= AllIconnectTo.get(key).get(0);
            this.EdgesHash.remove(e.getKey());
            this.AllIconnectTo.get(key).remove(e);
            this.IEdgesEdge.get(key).remove(e);
            e=null;


        }
        NodeData toRemove = NodesHash.remove(key);
        return toRemove;

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
            nodeV.setInfo(null);
        }
    }

}
