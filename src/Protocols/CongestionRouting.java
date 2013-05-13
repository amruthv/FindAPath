package Protocols;

import java.util.List;
import java.util.Map;

import network.Graph;
import network.Node;
import network.Packet;

public class CongestionRouting extends DynamicProtocol {
	public Graph graph;
	
	public CongestionRouting(Graph g){
		this.graph=g;
	}
	
	
	
	@Override
	public void route(Node sender, List<Packet> packets) {
//		System.out.println("Flloyd Warshall algo: "+graph.nextNodeInPath.get(sender.id).toString());
		Map<Integer,Integer> nextNodeInPath = sssp(sender, graph);
//		System.out.println(nextNodeInPath.toString());
		for (Packet p : packets) {
//			System.out.println("packet destination: "+p.destination);
			int nextNodeID = (nextNodeInPath).get(p.destination);
			sender.getOutLinkToNode(nextNodeID).addPacket(p);
		}
	}
}
