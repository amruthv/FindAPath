package Protocols;

import java.util.List;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

public class LeastBusyNeighborRouting extends RoutingProtocol {
	public Graph graph;
	public LinkMetric lm = LinkMetric.cost;
	
	public LeastBusyNeighborRouting(Graph g){
		this.graph=g;
	}
	
	
	
	@Override
	public void route(Node sender, List<Packet> packets) {
		for (Packet p : packets) {
//			System.out.println("packet destination: "+p.destination);
			int nextNodeID = getLeastBusyNeighbor(sender);
			sender.getOutLinkToNode(nextNodeID).addPacket(p);
		}
	}
	
	public int getLeastBusyNeighbor(Node sender){
		int maxBusy=-1;
		int maxBusyID=-1;
		for (Link l: sender.outLinks){
			Node neighbor = l.toNode;
			if (neighbor.countInPackets()>maxBusy){
				maxBusy=neighbor.countInPackets();
				maxBusyID=neighbor.id;
			}
		}
		return maxBusyID;
	}
}
