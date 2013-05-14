package Protocols;
import Metrics.LinkMetric;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;

public class FewestHopsRouting extends RoutingProtocol {
	
	public LinkMetric lm;
	
	public FewestHopsRouting(Graph g) {
		super(g);
		this.lm = LinkMetric.hops;
	}
	
	public void initialize() {
		g.lm = lm;
		g.calcShortestPaths();
		g.computeAllWholePaths();
	}

	public void prepareGraph() {
	}

	public void prepareNode(Node sender) {
	}

	public Link routePacket(Node sender, Packet p) {
		int nextNodeID = (g.nextNodeInPath.get(sender.id)).get(p.destination);
		return sender.getOutLinkToNode(nextNodeID);
	}
	
	public String toString(){
		return "FewestHopsRouting";
	}

}
