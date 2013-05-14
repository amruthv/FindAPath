package Protocols;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

public class RandomizedRouting extends RoutingProtocol {
	public Graph graph;
	
	public RandomizedRouting(Graph g) {
		this.graph = g;
		this.lm = LinkMetric.cost;
	}
	
	@Override
	public void route(Node sender, List<Packet> packets) {
		Collections.shuffle(packets);
		for (int i=0; i<packets.size(); i++) {
			if (sender.id == packets.get(i).destination){
				graph.packetsInNetwork -=1;
				continue;
			}
			int nextNodeID = getRandomCloserNeighbor(sender, packets.get(i).destination);
			Link linkToUse = sender.getOutLinkToNode(nextNodeID);
			if (linkToUse.capacity < linkToUse.maxCapacity){
				linkToUse.addPacket(packets.get(i));
				packets.remove(i);
				i--;
			}			
		}
		sender.queue=packets;
	}
	
	public int getRandomCloserNeighbor(Node node, int dest) {
		List<Integer> closerNeighbors = new ArrayList<Integer>();
		for (Link l: node.outLinks) {
			Node neighbor = l.toNode;
			if (graph.dist[neighbor.id][dest] < graph.dist[node.id][dest])
				closerNeighbors.add(neighbor.id);
		}
		return closerNeighbors.get((int) (closerNeighbors.size() * Math.random()));
	}
	
	@Override
	public String toString(){
		return "RandomizedRoutingProtocol";
	}
}
