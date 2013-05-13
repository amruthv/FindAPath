package Protocols;

import java.util.List;

import network.Graph;
import network.Node;
import network.Packet;

public class FewestHopsRouting extends RoutingProtocol {
	
	public Graph g;
	
	public FewestHopsRouting(Graph g){
		this.g=g;
		g.computeAllNextInPath();
	}
	
	@Override
	public void route(Node sender, List<Packet> packets) {
		for (Packet p : packets) {
			if (sender.id == p.destination)
				return;
			int nextNodeID = (g.nextNodeInPath.get(sender.id)).get(p.destination);
			sender.getOutLinkToNode(nextNodeID).addPacket(p);
		}
	}
	
}
