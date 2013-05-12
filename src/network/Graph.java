package network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ejml.simple.SimpleMatrix;

import Metrics.LinkMetric;

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
		
		for (int i = 0; i < nodes.size(); i++)
			nodes.get(i).id = i;
		
		calcShortestPaths(LinkMetric.simpleCost);
	}
	
	
	public void calcShortestPaths(LinkMetric lm) {
		
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
				dist[outlink.fromNode.id][outlink.toNode.id]=lm.getCost(outlink);
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
				if (!nextNodeInPath.containsKey(i)){
					nextNodeInPath.put(i, new HashMap<Integer,Integer>());
				}
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
				if (!shortestPaths.containsKey(i)){
					shortestPaths.put(i, new HashMap<Integer,List<Integer>>());
				}
				shortestPaths.get(i).put(j,getWholePath(i,j));
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
			return paths;
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
				List<Integer> secondPart = getWholePath(interVal,j);
				firstPart.addAll(secondPart);
				return firstPart;
			}
		}
	}

	public int calcDiameter() {
		return 0;
	}
	
	public double[] calcDegreeCentrality() {
		double[] result = new double[nodes.size()];
		for (int i = 0; i<nodes.size(); i++)
			result[i] = nodes.get(i).inLinks.size()/(double)nodes.size();;
		return result;
	}
	
	public double[] calcBetweenessCentrality() {
		return null;
	}
	
	public double[] calcKatzCentrality() {
		int n = nodes.size();
		
		double alpha = .04;
		SimpleMatrix a = getAdjacencyMatrix();
		SimpleMatrix v = new SimpleMatrix(new double[n][1]);
		for (int i = 0; i<n; i++)
			v.set(i, 0, 1);
		
		SimpleMatrix katz = SimpleMatrix.identity(n);
		katz = katz.minus(a.scale(alpha).transpose()).invert().mult(v);
		
		
		double[] result = new double[n];
		for (int i = 0; i < n; i++)
			result[i] = katz.get(i,0);
		
		return result;
	}

	public double[] pageRank() {
		  double alpha = 0.04;
		  SimpleMatrix a = getAdjacencyMatrix();
		  return null;
		  //double
	}
	
	public double[] calcEigenvectorCentrality() {
		return null;
	}
	
	public double calcClusteringCoeff() {
		return 0;
	}
	
	public SimpleMatrix getAdjacencyMatrix() {
		SimpleMatrix a = new SimpleMatrix(nodes.size(), nodes.size());
		for (Node n : nodes)
			for (Link l : n.outLinks)
				a.set(n.id, l.toNode.id, 1);
		
		return a;
	}
	
}
