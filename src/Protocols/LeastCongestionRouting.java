package Protocols;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

public class LeastCongestionRouting extends DynamicProtocol {
	public Graph graph;
	
	public LeastCongestionRouting(Graph g){
		this.graph=g;
		this.lm=LinkMetric.congestion;
	}
	
	
	
	@Override
	public void route(Node sender, List<Packet> packets) {
		Map<Integer,Integer> nextNodeInPath = sssp(sender, graph);
		Collections.shuffle(packets);
		for (int i=0;i<packets.size();i++) {
			if (sender.id == packets.get(i).destination){
				graph.packetsInNetwork -=1;
				continue;
			}
			int nextNodeID = (nextNodeInPath).get(packets.get(i).destination);
			Link linkToUse = sender.getOutLinkToNode(nextNodeID);
			if (linkToUse.capacity < linkToUse.maxCapacity){
				linkToUse.addPacket(packets.get(i));
				packets.remove(i);
				i--;
			}
		}
		sender.queue=packets;
	}
	
	@Override
	public String toString(){
		return "LeastCongestionRouting";
	}
}
