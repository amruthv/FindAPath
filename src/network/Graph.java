package network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
	
	public List<Node> nodes;
	public Map<Integer, Map<Integer,Integer>> nextNodeInPath;
	public Map<Integer, Map<Integer,List<Integer>>> shortestPaths;
	double[][] dist;
	Double[][] next;
	int numNodes;
	
	public Graph(List<Node> nodes) {
		this.nodes = nodes;
		this.nextNodeInPath=new HashMap<Integer, Map<Integer,Integer>>();
		this.shortestPaths= new HashMap<Integer, Map<Integer,List<Integer>>>();
		this.numNodes=this.nodes.size();
		this.dist = new double[numNodes][numNodes];
		this.next = new Double[numNodes][numNodes];
	}
	
	
	public void calcShortestPaths(){		
		//Initialize all distances to infinity
		for (int i=0; i<numNodes;i++){
			for (int j=0;j<numNodes;j++){
				dist[i][j]=Integer.MAX_VALUE;
			}
		}
		
		//Initialize all next node to null
		for (int i=0; i<numNodes;i++){
			for (int j=0;j<numNodes;j++){
				next[i][j]=null;
			}
		}
		
		//Initialize all self-distances
		for (int i=0;i<numNodes;i++){
			dist[i][i]=0;
		}
		//Initialize all edges in matrix
		for (Node node: this.nodes){
			Set<Link> outedges= node.outLinks;
			for (Link outlink: outedges){
				dist[outlink.fromNode.getId()][outlink.toNode.getId()]=outlink.cost;
			}
		}
		//Compute shortest distances
		for (int k=0;k<numNodes;k++){
			for (int i=0;i<numNodes;i++){
				for (int j=0;j<numNodes;j++){
					if (dist[i][k]+dist[k][j] < dist[i][j]){
						dist[i][j]=dist[i][k]+dist[k][j];
						next[i][j]=new Double(k);
					}
				}
			}
		}
		
		//Reconstruct shortest paths
		computeAllNextInPath();
	}
	/**
	 * Computes and stores the next node for all source-destination paths
	 */
	public void computeAllNextInPath(){
		for (int i=0;i<numNodes;i++){
			for (int j=0;j<numNodes;j++){
				int nextStep = getNextInPath(i,j);
				nextNodeInPath.get(i).put(j,nextStep);
			}
		}
	}
	
	/**
	 * Get id of next node in path from i to j
	 * @param i source
	 * @param j destination
	 * @return id of next node after i in path
	 */
	public int getNextInPath(int i, int j){
		if (i==j){
			return i;
		}
		if (dist[i][j]==Integer.MAX_VALUE){
			return -Integer.MAX_VALUE;
		}
		else{
			Double intermediate=next[i][j];
			if (intermediate == null){
				return j;
			}
			else{
				return getNextInPath(i,(int) intermediate.doubleValue());
			}
		}
	}
	
	
	
	/**
	 * Computes and stores the next node for all source-destination paths
	 */
	public void computeAllWholePaths(){
		for (int i=0;i<numNodes;i++){
			for (int j=0;j<numNodes;j++){
				if (!hasPath(i,j)){
					shortestPaths.get(i).put(j,getWholePath(i,j));
				}
			}
		}
	}
	
	/**
	 * Get id of next node in path from i to j
	 * @param i source
	 * @param j destination
	 * @return list of node id's in order to get from i to j
	 */
	public List<Integer> getWholePath(int i, int j){
		List<Integer> paths= new ArrayList<Integer>();
		paths.add(i);
		if (i==j){
			if (!hasPath(i,j)){
				return paths;
			}
		}
		//not reachable
		if (dist[i][j]==Integer.MAX_VALUE){
			return null;
		}
		//reachable
		else{
			Double intermediate=next[i][j];
			int interVal = (int) intermediate.doubleValue();
			//no intermediate node. Edge between them is shortest path
			if (intermediate == null){
				paths.add(j);
				return paths;
			}
			else{
				List<Integer> firstPart= getWholePath(i,interVal);
				if (!hasPath(i,interVal)){
					firstPart.add(interVal);
					shortestPaths.get(i).put(interVal, firstPart);
					//pop off intermediate val
					firstPart.remove(firstPart.size()-1);
				}
				List<Integer> secondPart = getWholePath((int) intermediate.doubleValue(),j);
				if(!hasPath(interVal,j)){
					secondPart.add(j);
					shortestPaths.get(i).put(interVal, secondPart);
					//pop off intermediate val
					secondPart.remove(firstPart.size()-1);
				}
				firstPart.addAll(secondPart);
				return firstPart;
			}
		}
	}
	
	public boolean hasPath(int i, int j){
		if (shortestPaths.containsKey(i) && (shortestPaths.get(i)).containsKey(j)){
			return true;
		}
		return false;
	}

	public int calcDiameter() {
		return 0;
	}
	
	public double[] calcBetweenessCentrality() {
		return null;
	}
	
	public double[] calcEigenvectorCentrality() {
		return null;
	}
	
	public double calcClusteringCoeff() {
		return 0;
	}
	
}
