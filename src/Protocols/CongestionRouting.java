package Protocols;

import java.util.List;

import network.Graph;
import network.Node;
import network.Packet;

public class CongestionRouting extends RoutingProtocol {
	public Graph graph;
	
	public CongestionRouting(Graph g){
		this.graph=g;
	}
	
	@Override
	public void route(Node sender, List<Packet> packets) {
		graph.sssp(sender);
		for (Packet p : packets) {
			System.out.println("packet destination: "+p.destination);
			int nextNodeID = (graph.nextNodeInPath.get(sender.id)).get(p.destination);
			sender.getOutLinkToNode(nextNodeID).addPacket(p);
		}
	}
}
