package Metrics;

import network.Graph;
import network.Link;
import network.Node;

public class SquaredSums implements Metric {

	@Override
	public double score(Graph g) {
		double total = 0;
		for (Node node : g.nodes) {
			for (Link inLink : node.inLinks)
				total += Math.pow(inLink.capacity, 2);
		}
		return total;
	}
	
	@Override
	public String toString(){
		return "SquaredSum: ";
	}

}
