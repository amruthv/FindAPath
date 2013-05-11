package network;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
	
	public List<Node> nodes;
	public Map<Integer, Map<Integer,Integer>> shortestPaths;
	double[][] dist;
	Double[][] next;
	int numNodes;
	
	public Graph(List<Node> nodes) {
		this.nodes = nodes;
		this.shortestPaths=null;
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
		
		//Initialize all distances to infinity
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
				shortestPaths.get(i).put(j,nextStep);
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
