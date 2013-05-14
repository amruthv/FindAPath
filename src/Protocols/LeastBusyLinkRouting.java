package Protocols;

import java.util.Collections;
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
		Collections.shuffle(packets);
		for (int i=0;i<packets.size();i++) {
			if (sender.id == packets.get(i).destination){
				graph.packetsInNetwork -= 1;
				continue;
			}
			int nextNodeID = getLeastBusyLink(sender,packets.get(i).destination);
			Link linkToUse = sender.getOutLinkToNode(nextNodeID);
			if (linkToUse.capacity <linkToUse.maxCapacity){
				linkToUse.addPacket(packets.get(i));
				packets.remove(i);
				i--;
			}
		}
		sender.queue=packets;
	}
	
	public int getLeastBusyLink(Node sender, int dest){
		int minBusy = Integer.MAX_VALUE;
		int minBusyID = -1;
		for (Link l: sender.outLinks) {
			Node neighbor = l.toNode;
			if (neighbor.id == dest)
				return dest;
			if (graph.dist[neighbor.id][dest] < graph.dist[sender.id][dest] && l.capacity < minBusy) {
				minBusy = l.capacity;
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
