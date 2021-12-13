
# The Graph Editor 🗺️
#### made by Yakov Khodorkovski and Daniel Zaken 
##### The Graph Editor is a graphical representation of directed graph  . The Graph Editor lets you edit your graph , run algrothems and get information .

## how to run 
1. Download the Ex2.jar file and json file of your graph that you want to load . 
2. open terminal at downloded folder . 
3. run ```java -jar Ex2.rar your_json_file.json```


## how to use 
you can navigate the menu bar to :
* File - save and load graphs 
* Nodes and Edges - edit your graph by removing or adding. 
* Algorithms - get information on your graph by using system Algorithms. 

<br />

## about code 

### data structure 

the Graph Editor captures the data in 2 HashMaps . 
* Hash for the nodes  - implementing editing nodes by their id in O(1)
* Hash for the edges  - implementing editing edges by taking the sourse and destantion of the edge, and convert it to 1 string (```src_dest```) key that gives us the edge on O(1)
<br />


### implemented classes 

* **NodeV** - implementing NodeData of graph. 
* **Edge** - implementing EdgesData of graph. 
* **Location** - implementing GeoLocation of graph. 
* **DirectedGraph**  - implementing DirectedWightedGraph using the HashMaps of the edges and nodes , and iterator implementation to iter the nodes and edges . 
* **algoGraph** - implementing DirectedWightedGraphAlgorithms by loading DirectedGraph and run algorithms to run the functions. 
<br />


### Algorithms functions 

* IsConnected - This algorithm check if the graph is Connected graph or not.
                To check we use BFS algorithm on the graph and another BFS on the reverse graph.
                
* shortestPathDist- This functions get src and dest Id and return the length of the shortest path. We use dijkstra algorithm to find this path.
 
* shortestPath - This function get src and dest Id and return NodeData list of all the NodeData(vertex) that in the shortest path in the same order as they show up in the path. 

* center - This functions return the NodeData(vertex) that in the center of the graph (relative to the rest of the vertices).In this function we use the dijkstra algorithm to caculte the length from node to the other nodes and this help us to find the center node(vertex).
 
 
* tsp - This function get list of nodes(vertex) and return list of nodes that present the path that pass in the nodes that we get,also the function return "good" path and not path that pass to mach vertex and visit vertex that not necessaries .We use shortestPathDist function to find the best path from node to node.
* BFS - This function get graph and color and set the color of the nodes that in the component of the first id in our list.We build this function to check if the graph is connected.  
<br />


## classes functions


(functions of the interfaces that not shows here is functions we didnt use because they was not relvant for this assignment or we didnt need to use them for our graph and they do nothing when using them.)
### Edge (implements EdgeData interface) class functions
* getKey
* getSrc
* getWeight
* getInfo
* setInfo

### NodeV (implements NodeData interface) class functions
* getKey
* getLocation
* setLocation
* getInfo
* setInfo

### Location (implements GeoLocation interface) class functions
* x
* y
* z
* distance

### DirectedGraph (implements DirectedWeightedGraph interface) class functions
* getNode
* getEdge
* addNode
* connect
* nodeIter()
* edgeIter()
* nodeIter(int node_id)
* edgeIter(int node_id)
* removeEdge
* nodeSize
* edgeSize
* getMC
* reverseGraph
* checkIfAllTheSameColor
* setGraphColor
* setprevious

### graphAlgo (implements DirectedWeightedGraphAlgorithms) class functions 
* init
* getGraph
* copy
* isConnected
* shortestPathDist
* shortestPath
* center
* tsp
* save
* load
* BFS


## GUI interface  

using the Graphical User Interface (GUI) our program is displaying the graph to the user . by easy navigation using the menu bar ,represating the graph from the json file to the user and see algorithms resualts . 
<br />


<img width="1438" alt="Screen Shot 2021-12-13 at 18 59 21" src="https://user-images.githubusercontent.com/66936716/145862819-16d29709-28a6-4125-b53f-7e6cb6b24049.png">

## UML 
<br />

![UML-EX2](https://user-images.githubusercontent.com/66936716/145880967-042d582d-b70b-4f31-b35d-e6249628a0d2.png)


