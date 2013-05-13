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
		this.lm=LinkMetric.cost;
	}
	
	
	
	@Override
	public void route(Node sender, List<Packet> packets) {
//		System.out.println("Flloyd Warshall algo: "+graph.nextNodeInPath.get(sender.id).toString());
//		System.out.println(nextNodeInPath.toString());
		for (Packet p : packets) {
			if (sender.id == p.destination)
				continue;
			Map<Integer,Integer> nextNodeInPath = sssp(sender, graph);
//			System.out.println("packet destination: "+p.destination);
			int nextNodeID = (nextNodeInPath).get(p.destination);
			sender.getOutLinkToNode(nextNodeID).addPacket(p);
		}
	}
	
	@Override
	public String toString(){
		return "LeastCongestionRouting";
	}
}
