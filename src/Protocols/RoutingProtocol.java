package Protocols;

import java.util.Collections;
import java.util.List;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;

public abstract class RoutingProtocol {
	public Graph g;
	
	public RoutingProtocol(Graph g) {
		this.g = g;
	}
	
	public void load(Node sender) {
		sender.loadPackets();
	}
	
	public void route(Node sender) {
		prepareNode(sender);
		
		List<Packet> queue = sender.queue;
		Collections.shuffle(queue);
		Link linkToUse;
		
		while (queue.size() > sender.maxQueue)
			queue.remove(0);
		sender.dropped++;
		
		for (int i=0;i<queue.size();i++) {
			linkToUse = routePacket(sender, queue.get(i));

			if (linkToUse.packets.size() < linkToUse.maxCapacity){
				linkToUse.addPacket(queue.get(i));
				queue.remove(i);
				i--;
			}
		}
	}
	
	public abstract void initialize();
	public abstract void prepareGraph();
	public abstract void prepareNode(Node sender);
	public abstract Link routePacket(Node sender, Packet p);
}
