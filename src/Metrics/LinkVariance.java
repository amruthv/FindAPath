package Metrics;

import java.util.ArrayList;
import java.util.List;

import network.Graph;
import network.Link;
import network.Node;

public class LinkVariance implements Metric {

	@Override
	public double score(Graph g) {
		List<Double> congests = new ArrayList<Double>();
		double total = 0;
		for (Node node : g.nodes) {
			for (Link inLink : node.inLinks) {
				congests.add((double) inLink.packets.size());
				total += inLink.packets.size();
			}
		}
		double avg = total / congests.size();
		double var = 0;
		for (double d : congests)
			var += Math.pow(d - avg, 2);
		return var / congests.size();
	}
	
	@Override
	public String toString(){
		return "Link Variance: ";
	}

}
