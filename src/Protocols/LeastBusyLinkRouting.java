package Protocols;

import java.util.Arrays;
import java.util.List;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

public class LeastBusyLinkRouting extends RoutingProtocol {
	public Graph graph;
	
	public LeastBusyLinkRouting(Graph g){
		this.graph=g;
		this.lm=LinkMetric.cost;
	}
	
	
	
	@Override
	public void route(Node sender, List<Packet> packets) {
		for (Packet p : packets) {
			if (p.destination == sender.id)
				continue;
			int nextNodeID = getLeastBusyLink(sender, p.destination);
			sender.getOutLinkToNode(nextNodeID).addPacket(p);
		}
	}
	
	public int getLeastBusyLink(Node sender, int dest){
		int minBusy = Integer.MAX_VALUE;
		int minBusyID = -1;
		for (Link l: sender.outLinks) {
			Node neighbor = l.toNode;
			if (neighbor.id == dest)
				return dest;
			if (graph.dist[neighbor.id][dest] < graph.dist[sender.id][dest] && l.capacity < minBusy) {
				minBusy = neighbor.countInPackets();
				minBusyID = neighbor.id;
			}
		}
		if (minBusyID == -1)
			throw new IllegalArgumentException("No closer node");
		return minBusyID;
	}
	
	@Override
	public String toString() {
		return "LeastBusyLinkRouter";
	}
}
