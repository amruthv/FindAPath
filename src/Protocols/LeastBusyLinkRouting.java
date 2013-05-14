package Protocols;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

public class LeastBusyLinkRouting extends RoutingProtocol {
	public LinkMetric lm;
	
	public LeastBusyLinkRouting(Graph g){
		super(g);
		this.lm=LinkMetric.cost;
	}	
	
	@Override
	public String toString() {
		return "LeastBusyLinkRouter";
	}



	@Override
	public void initialize() {
		g.lm = lm;
		g.calcShortestPaths();
		g.computeAllWholePaths();
	}



	@Override
	public void prepareGraph() {	
	}



	@Override
	public void prepareNode(Node sender) {		
	}

	@Override
	public Link routePacket(Node sender, Packet p) {
		int nextNodeID = getLeastBusyLink(sender,p.destination);
		return sender.getOutLinkToNode(nextNodeID);
	}
	public int getLeastBusyLink(Node sender, int dest){
		double minBusy = Double.MAX_VALUE;
		int minBusyID = -1;
		for (Link l: sender.outLinks) {
			Node neighbor = l.toNode;
			if (neighbor.id == dest)
				return dest;
			if (g.dist[neighbor.id][dest] < g.dist[sender.id][dest] && l.packets.size() < minBusy) {
				minBusy = ((double)l.packets.size())/l.maxCapacity;
				minBusyID = neighbor.id;
			}
		}
		if (minBusyID == -1)
			throw new IllegalArgumentException("No closer node");
		return minBusyID;
	}
	
}
