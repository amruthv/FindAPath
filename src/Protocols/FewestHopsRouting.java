package Protocols;

import java.util.Collections;
import java.util.List;

import Metrics.LinkMetric;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;

public class FewestHopsRouting extends RoutingProtocol {
	
	public Graph g;
	
	public FewestHopsRouting(Graph g){
		this.g = g;
		lm = LinkMetric.hops;
	}
	
	@Override
	public void route(Node sender, List<Packet> packets) {
		Collections.shuffle(packets);
		for (int i=0;i<packets.size();i++) {
			if (sender.id == packets.get(i).destination){
				g.packetsInNetwork -=1;
				continue;
			}
			int nextNodeID = (g.nextNodeInPath.get(sender.id)).get(packets.get(i).destination);
			Link linkToUse = sender.getOutLinkToNode(nextNodeID);
			if (linkToUse.capacity <linkToUse.maxCapacity){
				linkToUse.addPacket(packets.get(i));
				packets.remove(i);
				i--;
			}
		}
		sender.queue=packets;
	}
	
	@Override
	public String toString(){
		return "FewestHopsRouting";
	}
	
}
