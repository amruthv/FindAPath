package Protocols;

import java.util.List;
import java.util.Map;

import Metrics.LinkMetric;

import network.Graph;
import network.Node;
import network.Packet;

public class LeastCongestionRouting extends DynamicProtocol {
	public Graph graph;
	
	public LeastCongestionRouting(Graph g){
		this.graph=g;
		this.lm=LinkMetric.congestion;
	}
	
	
	
	@Override
	public void route(Node sender, List<Packet> packets) {
		for (Packet p : packets) {
			if (sender.id == p.destination)
				continue;
			Map<Integer,Integer> nextNodeInPath = sssp(sender, graph);
			int nextNodeID = (nextNodeInPath).get(p.destination);
			sender.getOutLinkToNode(nextNodeID).addPacket(p);
		}
	}
	
	@Override
	public String toString(){
		return "LeastCongestionRouting";
	}
}
