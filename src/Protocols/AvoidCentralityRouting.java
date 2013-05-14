package Protocols;

import java.util.Collections;
import java.util.List;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

public class AvoidCentralityRouting extends RoutingProtocol {
	
	public Graph g;
	
	public AvoidCentralityRouting(Graph g){
		this.g = g;
		lm = LinkMetric.centrality;
	}
	
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
	
	public String toString(){
		return "AvoidCentralityRouting";
	}
}
