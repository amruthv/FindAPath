package Protocols;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

public class AvoidCentralityRouting extends RoutingProtocol {
	
	public LinkMetric lm;
	
	public AvoidCentralityRouting(Graph g){
		super(g);
		lm = LinkMetric.centrality;
	}
	
	public String toString(){
		return "AvoidCentralityRouting";
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
		int nextNodeID = (g.nextNodeInPath.get(sender.id)).get(p.destination);
		return sender.getOutLinkToNode(nextNodeID);
	}
}
