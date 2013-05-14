package Metrics;

import network.Graph;
import network.Node;

public class WorstNode implements Metric {
	
	@Override
	public double score(Graph g) {
		double most = 0;
		for (Node node : g.nodes) 
			most = Math.max(most, node.queue.size());
		return most;
	}

	@Override
	public String toString(){
		return "Worst Node: ";
	}
}
