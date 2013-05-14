package network;

import Protocols.RoutingProtocol;

public class Router {
	public Graph graph;
	public Router(Graph g){
		this.graph=g;
	}
	
	public void routeAllNodes(int numTimes, RoutingProtocol protocol) {
		graph.flushGraph();
		graph.lm = protocol.lm;
		graph.calcShortestPaths();
		for (int i = 0; i < numTimes; i++) {
			for (Node sender : graph.nodes)
					protocol.route(sender, sender.getInPackets());
		}
	}
	
}
