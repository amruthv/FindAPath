package network;

import java.util.ArrayList;
import java.util.List;

import Protocols.RoutingProtocol;

public class Router {
	public Graph graph;
	public Router(Graph g){
		this.graph=g;
	}
	
	public List<Packet> getPacketsAtNode(Node node) {
		int currID = node.id;
		List<Packet> packets = new ArrayList<Packet>();
		
		for (int dest : node.selfTraffic.keySet()) {
			for (int i = 0; i < node.selfTraffic.get(dest); i++)
				packets.add(new Packet(currID, dest));
		}
		
		for (Link inlink: node.inLinks) {
			for (Packet p: inlink.packets)
				packets.add(p);
			inlink.flushPackets();
		}
		
		return packets;
	}
	
	public void routeAllNodes(int numTimes, RoutingProtocol protocol, boolean dynamic) {
		System.out.println("in routing");
		for (int i = 0; i < numTimes; i++) {
			if (i%100==0){
				System.out.println(i);
			}
			for (Node sender : graph.nodes)
					protocol.route(sender, getPacketsAtNode(sender));
		}
	}
	
}
