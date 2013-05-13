package network;

import java.util.ArrayList;
import java.util.List;

import Protocols.RoutingProtocol;

public class Router {
	public Graph graph;
	public Router(Graph g){
		this.graph=g;
	}
	
	public void routeAllNodes(int numTimes, RoutingProtocol protocol) {
		graph.lm = protocol.lm;
		graph.calcShortestPaths();
		for (int i = 0; i < numTimes; i++) {
			if (i%100==0){
				System.out.println(i);
			}
			for (Node sender : graph.nodes)
					protocol.route(sender, sender.getInPackets());
		}
	}
	
}
