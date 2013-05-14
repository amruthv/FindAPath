package Metrics;

import network.Graph;
import network.Node;

public class TotalReceived implements Metric {

	@Override
	public double score(Graph g) {
		double score=0;
		for (Node node: g.nodes){
			score+=node.received;
		}
		return score;
	}
	
	@Override
	public String toString(){
		return "Total received packets: ";
	}

}
