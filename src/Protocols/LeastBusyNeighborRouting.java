package Protocols;

import java.util.Arrays;
import java.util.List;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

public class LeastBusyNeighborRouting extends RoutingProtocol {
	public Graph graph;
	
	public LeastBusyNeighborRouting(Graph g){
		this.graph=g;
		this.lm=LinkMetric.cost;
	}
	
	
	
	@Override
	public void route(Node sender, List<Packet> packets) {
		System.out.println(Arrays.deepToString(graph.dist));
		for(int i=0; i<graph.numNodes;i++){
//			System.out.println(graph.dist[i].toString());
		}
		for (Packet p : packets) {
//			System.out.println("packet destination: "+p.destination);
			int nextNodeID = getLeastBusyNeighbor(sender, p.destination);
			sender.getOutLinkToNode(nextNodeID).addPacket(p);
		}
	}
	
	public int getLeastBusyNeighbor(Node sender, int dest){
		int maxBusy=-1;
		int maxBusyID=-1;
		for (Link l: sender.outLinks){
			Node neighbor = l.toNode;
			if (neighbor.countInPackets()>maxBusy){
				if(graph.dist[neighbor.id][dest] < graph.dist[sender.id][dest]){
					maxBusy=neighbor.countInPackets();
					maxBusyID=neighbor.id;
				}
			}
		}
		return maxBusyID;
	}
}
