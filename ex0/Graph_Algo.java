package ex0;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph_Algo implements graph_algorithms {

	private graph g;
	
	
	//***** constructors  *****
	//constructor with given graph.\ 
	public Graph_Algo(graph g) {
		this.init(g);
	}
	
	//default constructor
	public Graph_Algo() {
		this(new Graph_DS());
	}

	
	
	//***** methods  *****
	
	/**
	 * initialize the algorithms to the given graph.
	 * @param g -the graph to initialize to
	 */
	@Override
	public void init(graph g) {
		this.g = g;
	}

	 /**
	  * Make a deep copy of the graph.
	  * @return the copied graph
	  */
	@Override
	public graph copy() {
		Graph_DS copy = new Graph_DS();								//create new graph
		
		for(node_data n : g.getV())									//copy the nodes to the new graph
			copy.addNode(new NodeData(n.getInfo(), n.getKey()));
		

		for(node_data n : g.getV()) 								//for each node, 
			for(node_data nei : n.getNi()) 							//go through it's neighbors
				copy.connect(n.getKey(), nei.getKey());				//and connect them at the new graph
		
		return copy;												//return the new graph
	}

	/**
	 * Return true iff the graph is connected.
	 */
	@Override
	public boolean isConnected() {
		Collection<node_data> nodes = g.getV();				//get the nodes in the graph
		
		if(nodes.isEmpty()) return true;					//if the graph is empty, return true
		
		mark_dis(nodes.iterator().next());					//mark all the node connected to first
		
		Iterator<node_data> ite = nodes.iterator();
		while(ite.hasNext()) 
			if(ite.next().getTag() == -1) return false;		//if a node is not connected to the first, return false
			
		return true;										//if all the nodes are connected, return true
	}
	
	
	/**
	 * the function mark the distance of every node in the graph from the given node , -1 if not connected
	 * uses a Breadth-first search (BFS) algorithm.
	 * @param n
	 */
	private void mark_dis(node_data n) {
		Collection<node_data> nodes = this.g.getV();		//get all the node on the graph
		
		for(node_data node : nodes) node.setTag(-1);		//set all the distances to -1
		
		Queue<node_data> q = new LinkedList<>();			//initialize the queue for bfs algorithm
 	
		n.setTag(0);										//set the distance of src node to zero and add it to the list
		q.add(n);
		
		node_data current;
		while(!q.isEmpty()) {								//while the queue is not empty
			current = q.poll();								//set the current node to the top of the queue
			for(node_data node : current.getNi()) {			//for each of the neighbors
				if(node.getTag() == -1) {					//if it's not yet visited
					node.setTag(current.getTag() + 1);		//set the distance
					q.add(node);							//and add it to the queue
				}	
			}
		}
	}
	
	/***
	 * Return the length of the shortest path between the given nodes.
	 * @param src -the source node
	 * @param dest -the destination node
	 * @return the length of the shortest path from src to dest
	 */
	@Override
	public int shortestPathDist(int src, int dest) {
		node_data srcN = this.g.getNode(src);				//get the nodes
		node_data destN = this.g.getNode(dest);
		
		if(srcN == null || destN == null) return -1;		//if the nodes are not in the graph, return -1
		
		mark_dis(srcN);										//compute the distance
		return destN.getTag();								//return the distance
	}

	/**
	 * Return the shortest path between the given nodes.
	 * @param src -the source node
	 * @param dest -the destination node
	 * @return a list of nodes representing the shortest path from src to dest
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		node_data srcN = this.g.getNode(src);						//get the nodes
		node_data destN = this.g.getNode(dest);
		LinkedList<node_data> lst = new LinkedList<node_data>();	//initialize the list
		
		if(srcN == null || destN == null) return null;				//if the nodes are not in the graph, return null
		
		mark_dis(srcN);												//compute the distance of each node from src
		
		if(destN.getKey() == -1) return null;						//make sure the nodes are connected, return null if not
		
		node_data current = destN;
		lst.add(current);											//add the destination node to the list
		Iterator<node_data> ite;
		for(int i = current.getTag() - 1;i >= 0;i--) {				//each step,
			ite = current.getNi().iterator();
			while(current.getTag() != i) current = ite.next();		//find the node with distance of one less then the current
			lst.addFirst(current);									//and add it to the list
		}
		
		return lst;													//return the list
	}
}
