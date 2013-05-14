package network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.ejml.simple.SimpleMatrix;

import utils.NodeIDDistPair;
import Metrics.LinkMetric;

public class Graph {

	public final List<Node> nodes;
	public Map<Integer, Map<Integer, Integer>> nextNodeInPath;
	public Map<Integer, Map<Integer, List<Integer>>> shortestPaths;
	public double[][] dist;
	public Double[][] next;
	public int numNodes;
	public LinkMetric lm;
	int[][] adjacency;

	public Graph(List<Node> nodes) {
		this.nodes = nodes;
		this.nextNodeInPath = new HashMap<Integer, Map<Integer, Integer>>();
		this.shortestPaths = new HashMap<Integer, Map<Integer, List<Integer>>>();
		this.numNodes = this.nodes.size();
		this.dist = new double[numNodes][numNodes];
		this.next = new Double[numNodes][numNodes];
		this.adjacency = new int[numNodes][numNodes];

		for (int i = 0; i < nodes.size(); i++)
			nodes.get(i).id = i;
		lm = LinkMetric.cost;
		calcShortestPaths();
	}
	
	public void flushGraph(){
		for (Node node: nodes){
			for (Link inlink: node.inLinks){
				inlink.flushPackets();
			}
		}
		this.nextNodeInPath = new HashMap<Integer, Map<Integer, Integer>>();
		this.shortestPaths = new HashMap<Integer, Map<Integer, List<Integer>>>();
		this.numNodes = this.nodes.size();
		this.dist = new double[numNodes][numNodes];
		this.next = new Double[numNodes][numNodes];
		this.adjacency = new int[numNodes][numNodes];
		
	}
	
	public void calcShortestPaths() {
		if (lm == LinkMetric.centrality)
			setCentralities();

		// Initialize all distances to infinity
		for (int i = 0; i < numNodes; i++) {
			for (int j = 0; j < numNodes; j++) {
				dist[i][j] = Integer.MAX_VALUE;
			}
		}

		// Initialize all next node to null
		for (int i = 0; i < numNodes; i++) {
			for (int j = 0; j < numNodes; j++) {
				next[i][j] = null;
			}
		}

		// Initialize all self-distances
		for (int i = 0; i < numNodes; i++) {
			dist[i][i] = 0;
		}
		
		
		// Initialize all edges in matrix
		for (Node node : this.nodes) {
			List<Link> outedges = node.outLinks;
			for (Link outlink : outedges) {
				if (outlink.fromNode.id != outlink.toNode.id) {
					dist[outlink.fromNode.id][outlink.toNode.id] = lm.getCost(outlink);
				}
			}
		}

		// Compute shortest distances
		for (int k = 0; k < numNodes; k++) {
			for (int i = 0; i < numNodes; i++) {
				for (int j = 0; j < numNodes; j++) {
					if (dist[i][k] + dist[k][j] < dist[i][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						next[i][j] = new Double(k);
					}
				}
			}
		}

		// Reconstruct shortest paths
		computeAllNextInPath();
	}

	/**
	 * Computes and stores the next node for all source-destination paths
	 */
	public void computeAllNextInPath() {
		for (int i = 0; i < numNodes; i++) {
			for (int j = 0; j < numNodes; j++) {
				int nextStep = getNextInPath(i, j);
				if (!nextNodeInPath.containsKey(i)) {
					nextNodeInPath.put(i, new HashMap<Integer, Integer>());
				}
				nextNodeInPath.get(i).put(j, nextStep);
			}
		}
	}

	/**
	 * Get id of next node in path from i to j
	 * 
	 * @param i
	 *            source
	 * @param j
	 *            destination
	 * @return id of next node after i in path
	 */
	public int getNextInPath(int i, int j) {
		if (i == j) {
			return i;
		}
		if (dist[i][j] == Integer.MAX_VALUE) {
			return -Integer.MAX_VALUE;
		} else {
			Double intermediate = next[i][j];
			if (intermediate == null) {
				return j;
			} else {
				return getNextInPath(i, (int) intermediate.doubleValue());
			}
		}
	}

	/**
	 * Computes and stores the next node for all source-destination paths
	 */
	public void computeAllWholePaths() {
		for (int i = 0; i < numNodes; i++) {
			for (int j = 0; j < numNodes; j++) {
				if (!shortestPaths.containsKey(i)) {
					shortestPaths.put(i, new HashMap<Integer, List<Integer>>());
				}
				shortestPaths.get(i).put(j, getWholePath(i, j));
			}
		}
	}

	/**
	 * Get id of next node in path from i to j
	 * 
	 * @param i
	 *            source
	 * @param j
	 *            destination
	 * @return list of node id's in order to get from i to j
	 */
	public List<Integer> getWholePath(int i, int j) {
		List<Integer> paths = new ArrayList<Integer>();
		paths.add(i);
		if (i == j) {
			return paths;
		}
		// not reachable
		if (dist[i][j] == Integer.MAX_VALUE) {
			return null;
		}
		// reachable
		else {
			Double intermediate = next[i][j];

			// no intermediate node. Edge between them is shortest path
			if (intermediate == null) {
				paths.add(j);
				return paths;
			} else {
				int interVal = (int) intermediate.doubleValue();
				List<Integer> firstPart = getWholePath(i, interVal);
				List<Integer> secondPart = getWholePath(interVal, j);
				firstPart.addAll(secondPart);
				return firstPart;
			}
		}
	}

	public void sssp(Node source) {
		int sID = source.id;
		//System.out.println(nextNodeInPath.get(sID).toString());
		// Create hashmap for nextNodeInPath if necessary
		if (!nextNodeInPath.containsKey(sID)) {
			nextNodeInPath.put(sID, new HashMap<Integer, Integer>());
		}
		List<NodeIDDistPair> pairs = new ArrayList<NodeIDDistPair>();
		PriorityQueue<NodeIDDistPair> distances = new PriorityQueue<NodeIDDistPair>();
		for (int i = 0; i < numNodes; i++) {
			// Create a node infinity far away
			pairs.add(new NodeIDDistPair(i, Integer.MAX_VALUE));
			// Clear all next node in paths
			nextNodeInPath.get(sID).put(i, null);
		}
		distances.addAll(pairs);
		//System.out.println("distances size: " + distances.size());
		// Initialize distances and self for base step of dijkstra
		pairs.get(sID).dist = 0;
		nextNodeInPath.get(sID).put(sID, sID);
		while (distances.size() != 0) {
			NodeIDDistPair min = distances.poll();
			dist[sID][min.id] = min.dist;
			if (min.dist == Integer.MAX_VALUE) {
				break;
			}
			for (Link l : nodes.get(min.id).outLinks) {
				int neighborID = l.toNode.id;
				double neighborCost = pairs.get(neighborID).dist;
				double newPathCost = min.dist + lm.getCost(l);
				if (neighborCost > newPathCost) {
					//System.out.println("neighbor Ids: " + neighborID);
					//System.out.println("neighbor cost was more: "
							//+ neighborCost + " newPathCost: " + newPathCost);
					pairs.get(neighborID).dist = newPathCost;
					// Set the next node to forward to be the next node in the
					// shortest path through u
					int stepToU = nextNodeInPath.get(sID).get(min.id);
					// More than 1 step away
					if (stepToU != sID) {
						nextNodeInPath.get(sID).put(neighborID, stepToU);
					}
					// Just one step away
					else {
						nextNodeInPath.get(sID).put(neighborID, neighborID);
					}
				}
			}
		}
		//System.out.println(distances.toString());
		//System.out.println(nextNodeInPath.get(sID).toString());

	}

	public int calcDiameter() {
		return 0;
	}

	public double[] calcDegreeCentrality() {
		double[] result = new double[nodes.size()];
		for (int i = 0; i < nodes.size(); i++)
			result[i] = nodes.get(i).inLinks.size() / (double) nodes.size();
		;
		return result;
	}

	public double[] calcBetweenessCentrality() {
		calcShortestPaths();
		computeAllWholePaths();

		double[] result = new double[nodes.size()];


		for (Map<Integer, List<Integer>> paths : shortestPaths.values()) {
			for (List<Integer> path : paths.values()) {
				for (Integer id : path) {
					result[id]++;
				}
			}
		}

		return result;
	}

	public double[] calcKatzCentrality(double alpha) {
		int n = nodes.size();

		SimpleMatrix a = getAdjacencyMatrix();
		SimpleMatrix v = new SimpleMatrix(new double[n][1]);
		for (int i = 0; i < n; i++)
			v.set(i, 0, 1);

		SimpleMatrix katz = SimpleMatrix.identity(n);
		katz = katz.minus(a.scale(alpha).transpose()).invert().mult(v);

		double[] result = new double[n];
		for (int i = 0; i < n; i++)
			result[i] = katz.get(i, 0);

		return result;
	}

	public void setCentralities() {
		double[] centralities = calcPageRank(.5);
		//System.out.println(Arrays.toString(centralities));
		//double[] centralities = calcBetweenessCentrality();
		for (int i = 0; i < numNodes; i++)
			nodes.get(i).centrality = centralities[i];
	}

	public double[] calcPageRank(double alpha) {
		int n = nodes.size();

		SimpleMatrix a = getAdjacencyMatrix();
		SimpleMatrix v = new SimpleMatrix(new double[n][1]);
		for (int i = 0; i < n; i++)
			v.set(i, 0, 1);

		SimpleMatrix degIdent = new SimpleMatrix(n, n);
		SimpleMatrix prank = new SimpleMatrix(n, n);
		int deg;
		for (int i = 0; i < n; i++) {
			deg = nodes.get(i).outLinks.size();
			degIdent.set(i, i, deg > 0 ? deg : 1);
		}

		prank = degIdent.minus(a.scale(alpha).transpose());
		prank = degIdent.mult(prank.invert()).mult(v);

		double[] result = new double[n];
		for (int i = 0; i < n; i++)
			result[i] = prank.get(i, 0);

		return result;
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
