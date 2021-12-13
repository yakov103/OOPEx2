
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

## about code 
### data structure 
the Graph Editor captures the data in 2 HashMaps . 
* Hash for the nodes  - implementing editing nodes by their id in O(1)
* Hash for the edges  - implementing editing edges by taking the sourse and destantion of the edge, and convert it to 1 string (```src_dest```) key that gives us the edge on O(1)

### implemented classes 
* **NodeV** - implementing NodeData of graph. 
* **Edge** - implementing EdgesData of graph. 
* **Location** - implementing GeoLocation of graph. 
* **DirectedGraph**  - implementing DirectedWightedGraph using the HashMaps of the edges and nodes . 
* **algoGraph** 
