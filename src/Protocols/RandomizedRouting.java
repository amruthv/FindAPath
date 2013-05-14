package Protocols;

import java.util.ArrayList;
import java.util.List;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

public class RandomizedRouting extends RoutingProtocol {
	
	public LinkMetric lm;
	public RandomizedRouting(Graph g) {
		super(g);
		this.lm = LinkMetric.cost;
	}
	
	
	@Override
	public String toString(){
		return "RandomizedRoutingProtocol";
	}

	@Override
	public void initialize() {
		g.lm = lm;
		g.calcShortestPaths();
		g.computeAllWholePaths();
	}

	@Override
	public void prepareGraph() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareNode(Node sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Link routePacket(Node sender, Packet p) {
		int nextNodeID = getRandomCloserNeighbor(sender, p.destination);
		return sender.getOutLinkToNode(nextNodeID);
	}
	
	
	public int getRandomCloserNeighbor(Node node, int dest) {
		List<Integer> closerNeighbors = new ArrayList<Integer>();
		for (Link l: node.outLinks) {
			Node neighbor = l.toNode;
			if (g.dist[neighbor.id][dest] < g.dist[node.id][dest])
				closerNeighbors.add(neighbor.id);
		}
		return closerNeighbors.get((int) (closerNeighbors.size() * Math.random()));
	}
}
