package Protocols;

import java.util.List;

import Metrics.LinkMetric;

import network.Graph;
import network.Node;
import network.Packet;

public class FewestHopsRouting extends RoutingProtocol {
	
	public Graph g;
	
	public FewestHopsRouting(Graph g){
		this.g = g;
		lm = LinkMetric.hops;
	}
	
	@Override
	public void route(Node sender, List<Packet> packets) {
		for (Packet p : packets) {
			if (sender.id == p.destination)
				continue;
			int nextNodeID = (g.nextNodeInPath.get(sender.id)).get(p.destination);
			sender.getOutLinkToNode(nextNodeID).addPacket(p);
		}
	}
	
}
