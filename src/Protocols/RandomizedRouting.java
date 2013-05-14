package Protocols;

import java.util.ArrayList;
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
		for (Packet p : packets) {
			if (sender.id == p.destination){
				graph.packetsInNetwork -=1;
				continue;
			}
			int nextNodeID = getRandomCloserNeighbor(sender, p.destination);
			sender.getOutLinkToNode(nextNodeID).addPacket(p);
		}
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
