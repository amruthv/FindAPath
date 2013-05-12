package Metrics;

import network.Graph;
import network.Link;
import network.Node;

public class WorstLink implements Metric {

	public double score(Graph g) {
		double worst = 0;
		for (Node node: g.nodes) {
			for (Link link : node.outLinks)
				worst = Math.max(worst, link.capacity);
		}
		return worst;
	}
	
}
