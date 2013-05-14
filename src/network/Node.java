package network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.Point;

//NODE IDS START AT 0

public class Node {
	public final Point loc;
	public final List<Link> inLinks;
	public final List<Link> outLinks;
	public final Set<Node> neighbors;
	public final Map<Integer, Integer> selfTraffic;
	public int id;
	public double centrality;
	public List<Packet> queue;
	public int received;
	public static final int maxQueue = 300;

	public Node(double x, double y) {
		loc = new Point(x, y);
		inLinks = new ArrayList<Link>();
		outLinks = new ArrayList<Link>();
		neighbors = new HashSet<Node>();
		selfTraffic = new HashMap<Integer, Integer>();
		this.queue = new ArrayList<Packet>();
	}

	public void addLinks(Link outLink, Link inLink) {
		if (!neighbors.add(outLink.toNode))
			return;

		inLinks.add(inLink);
		outLinks.add(outLink);
	}

	public void addTraffic(int dest) {
		if (!selfTraffic.containsKey(dest))
			selfTraffic.put(dest, 1);
		else
			selfTraffic.put(dest, selfTraffic.get(dest) + 1);
	}

	public double distance(Node other) {
		return loc.distance(other.loc);
	}

	public Link getOutLinkToNode(int nextNodeID) {
		if (neighbors.contains(nextNodeID))
			return null;

		for (Link l : this.outLinks) {
			if (l.toNode.id == nextNodeID) {
				return l;
			}
		}
		// No link to that node
		return null;
	}

	public boolean equals(Node node) {
		return this.id == node.id;
	}

	public String toString() {
		return loc.toString();
	}

	public void flush() {
		this.queue.clear();
		received=0;
		for (Link inlink : inLinks) {
			inlink.flushPackets();
		}
	}

	public void loadPackets() {
		for (int dest : selfTraffic.keySet()) {
			for (int i = 0; i < selfTraffic.get(dest); i++)
				queue.add(new Packet(id, dest));
		}

		for (Link inLink : inLinks) {
			for (Packet p : inLink.packets) {
				if (p.destination == id) 
					received++;
				else
					queue.add(p);
			}
			inLink.flushPackets();
		}

	}
}
