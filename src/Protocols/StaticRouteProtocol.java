package Protocols;
import network.Graph;
import network.Link;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

public class StaticRouteProtocol extends RoutingProtocol {
	
	public LinkMetric lm;
		
	public StaticRouteProtocol(Graph g, LinkMetric lm) {
		super(g);
		this.lm = lm;
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
		return "Static_" + lm.toString();
	}

}
