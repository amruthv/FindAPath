package Protocols;

import java.util.List;

import network.Graph;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

import Metrics.LinkMetric;

public class RandomizedRouting extends RoutingProtocol {
	public Graph graph;
	public LinkMetric lm=LinkMetric.cost;
	
	public RandomizedRouting(Graph g){
		this.graph=g;
	}
	
	@Override
	public void route(Node sender, List<Packet> packets) {
		if (sender.id == p.destination)
			continue;
		for (Packet p : packets) {
			if (sender.id == p.destination)
				return;
			int nextNodeID = (graph.nextNodeInPath.get(sender.id)).get(p.destination);
			sender.getOutLinkToNode(nextNodeID).addPacket(p);
		}
	}
}
