package network;

import Protocols.RoutingProtocol;

public class Router {
	public Graph graph;
	public RoutingProtocol p;

	public Router(Graph g, RoutingProtocol p) {
		this.graph = g;
		this.p = p;
		
		graph.flushGraph();
		p.initialize();
	}
	
	public void routeAllNodes(int numTimes) {
		for (int i = 0; i < numTimes; i++)
			routeAllNodes();
	}

	public void routeAllNodes() {
		p.prepareGraph();
		
		for (Node sender : graph.nodes)
			p.load(sender);
			
		for (Node sender : graph.nodes)
			p.route(sender);
	}
}
