package ex0;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Graph_DS implements graph {

	private HashMap<Integer , node_data> Nodes;
	private int number_of_nodes;
	private int number_of_edges;
	private int ModeCount;

	//*****  constructors  *****

	private Graph_DS (HashMap<Integer,node_data> Nodes , int number_of_nodes , int number_of_edges, int ModeCount) {
		this.Nodes = Nodes;
		this.number_of_edges = number_of_edges;
		this.number_of_nodes = number_of_nodes;
		this.ModeCount = ModeCount;
	}

	//default constructor
	public Graph_DS() {
		this(new HashMap<Integer,node_data>() , 0 , 0 , 0);
	}

	//*****  methods  *****

	/**
	 * Return the node with the given key
	 * @param key -the node to get
	 * @return the node with the given key, null if does'nt exist in the graph 
	 */
	@Override
	public node_data getNode(int key) {
		return Nodes.get(key);
	}

	/**
	 * Return true iff the given nodes are neighbors
	 * @param node1
	 * @param node2
	 */
	@Override
	public boolean hasEdge(int node1, int node2) {
		node_data Node1 = Nodes.get(node1);			//get node1 as object
		if(Node1 == null) return false;				//check if node1 exist in the graph
		return Node1.hasNi(node2);					//check if node2 appear in the neighbors list
	}

	/**
	 * Add the given node to the graph. if the node already exist, the method does nothing.
	 * @param n -the node to add
	 */
	@Override
	public void addNode(node_data n) {
		if(!Nodes.containsKey(n.getKey())) {	//check if the node already exist in the graph
			Nodes.put(n.getKey(), n);			//add the node

			number_of_nodes++;					//increase the counters
			ModeCount++;
		}
	}

	/**
	 * Connect node1 and node2 as neighbors. if they already connected, the method does nothing
	 * @param node1
	 * @param node2
	 */
	@Override
	public void connect(int node1, int node2) {		

		node_data Node1 = Nodes.get(node1);								//get the nodes as objects
		node_data Node2 = Nodes.get(node2);

		if(node1 == node2 || Node1 == null || Node2 == null ||
				 Node1.hasNi(node2)) return;							//check if trying to connect two valid nodes

			Node1.addNi(Node2);											//connect the nodes
			Node2.addNi(Node1);

			number_of_edges++;											//increase the counters
			ModeCount++;
	}
	
	/**
	 * Return the list of node in the graph as a collection
	 * @return a collection of the nodes in the graph 
	 */
	@Override
	public Collection<node_data> getV() {
		return Nodes.values();					//return the HashMap of the Nodes as Collection
	}

	/**
	 * Return a collection of the given node neighbors
	 * @param node_id -the key of the node
	 * @return collection of the given node neighbors, null if node does'nt exist
	 */
	@Override
	public Collection<node_data> getV(int node_id) {
		node_data n = Nodes.get(node_id);		//get the node
		if(n != null)							//check it exists
			return n.getNi();					//return the neighbors
		else return null;
	}

	/**
	 * Remove a node from the graph and return it
	 * @param key -the key of the node to remove
	 * @return the removed node
	 */
	@Override
	public node_data removeNode(int key) {
		node_data Node = Nodes.get(key);			//get the node as object

		if(Node == null) {							//check if the node exist
			return null;							//if not, return null
		}

		else {										//if the node exist,
			Collection<node_data> Ni = Node.getNi();//get his neighbors
			Iterator<node_data> ite = Ni.iterator();

			while(ite.hasNext()) {					//iterate through his neighbors
				ite.next().removeNode(Node);		//and remove the node from their neighbors lists
				number_of_edges--;
			}

			number_of_nodes--;						//change the counters
			ModeCount++;

			return Nodes.remove(key);				//remove the node from the graph and return it's data
		}

	}

	/**
	 * Remove the edge between node1 and node2. 
	 * if the nodes does'nt exist or their is'nt an edge between the, the method does nothing.
	 * @param node1
	 * @param node2
	 */
	@Override
	public void removeEdge(int node1, int node2) {
		if(!this.hasEdge(node1, node2)) return;		//check if the nodes are connected
		
		node_data Node1 = Nodes.get(node1);			//get the nodes as objects
		node_data Node2 = Nodes.get(node2);

		Node1.removeNode(Node2);					//remove the edge
		Node2.removeNode(Node1);

		number_of_edges--;							//change the counters
		ModeCount++;
	}

	/**
	 * @return the number of nodes in the graph
	 */
	@Override
	public int nodeSize() {
		return number_of_nodes;
	}

	/**
	 * @return the number of edges in the graph
	 */
	@Override
	public int edgeSize() {
		return number_of_edges;
	}

	/**
	 * @return the number of changes in the inner state of the graph (add/remove node/edge).
	 */
	@Override
	public int getMC() {
		return ModeCount;
	}

	/**
	 * @return A string representing the graph.
	 */
	public String toString() {
		String str = "";												//initialize the string

		Collection<node_data> NodesColl = this.getV();					//get a collection of all the nodes in the graph
		Iterator<node_data> ite = NodesColl.iterator();

		while(ite.hasNext()) {											//iterate through all the nodes
			node_data currentNode = ite.next();							//get the current node
			str += currentNode.toString() + " neighbors: ";				//add it's data to the string

			Iterator<node_data> ite2 = currentNode.getNi().iterator();
			while(ite2.hasNext()) {										//iterate through the current node's neighbors
				str += ite2.next().getKey() + ", ";						//and add them to the string
			}

			str = str.substring(0, str.length()-2) + "\n";				//cut the ", " at the end and go down a line
		}

		return str;														//return the string
	}

}
