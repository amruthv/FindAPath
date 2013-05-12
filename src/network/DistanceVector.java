package network;

import Metrics.LinkMetric;


public class DistanceVector extends RoutingProtocol {
	Graph g;
	public DistanceVector(Graph g){
		this.g=g;
		g.calcShortestPaths(LinkMetric.simpleCost);
		g.computeAllNextInPath();
	}
	
	@Override
	public void route(Node sender, Packet p) {
		int nextNodeID=(g.nextNodeInPath.get(sender.id)).get(p.destination);
		sender.getOutLinkToNode(nextNodeID).addPacket(p);
	}
	
}
