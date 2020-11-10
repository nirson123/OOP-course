package ex0;

import java.util.Collection;
import java.util.HashMap;

public class NodeData implements node_data {

	private int key;
	private HashMap<Integer,node_data> neighbors;
	private String info;
	private int tag;
	private static int CURENT_KEY = 0;

	//*****  constructors  *****

	private NodeData(HashMap<Integer,node_data> neighbors , String info , int tag , int key) {
		this.key = key;
		this.neighbors = new HashMap<Integer,node_data>(neighbors);
		this.info = info;
		this.tag = tag;
	}

	//constructor with given info
	public NodeData(String info) {
		this(new HashMap<Integer,node_data>(),info,0,CURENT_KEY++);
	}
	
	//constructor with given info and key
	public NodeData (String info , int key) {
		this(new HashMap<Integer,node_data>(),info,0,key);
	}
	
	//default constructor
	public NodeData() {
		this(new HashMap<Integer,node_data>(),"",0,CURENT_KEY++);
	}
	

	//*****  methods  *****

	/**
	 * Return the key (id) associated with this node
	 */
	@Override
	public int getKey() {
		return key;
	}

	/**
	 * Return a collection of the neighbors nodes of this node
	 */
	@Override
	public Collection<node_data> getNi() {
		return neighbors.values();			//return the neighbors list as collection
	}

	/**
	 * Return true iff this node have the node with the given key as a neighbor
	 * @param key
	 */
	@Override
	public boolean hasNi(int key) {
		return neighbors.containsKey(key);	//check if the given key is in the neighbors list
	}

	/**
	 * Add the given node to this node neighbors list
	 * @param t -the node to add
	 */
	@Override
	public void addNi(node_data t) {
		neighbors.put(t.getKey(), t);		//add given node to the neighbors list

	}

	/**
	 * Remove the given node from this node neighbors list
	 * @param node -the nodes to remove
	 */
	@Override
	public void removeNode(node_data node) {
		neighbors.remove(node.getKey());	//remove given node from the neighbors list
	}

	/**
	 * Return this node info
	 */
	@Override
	public String getInfo() {
		return info;
	}
	
	/**
	 * Set this node info
	 * @param s  -the new info
	 */
	@Override
	public void setInfo(String s) {
		info = s;
	}

	/**
	 * Return this node tag
	 */
	@Override
	public int getTag() {
		return tag;
	}

	/**
	 * Set this node tag
	 * @param t -the new tag
	 */
	@Override
	public void setTag(int t) {
		tag = t;
	}

	/**
	 * Return a string representing this node
	 */
	public String toString() {
		return "key:"+key+" info:"+info;
	}
}
