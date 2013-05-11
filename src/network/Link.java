package network;

import java.util.HashSet;
import java.util.Set;

public class Link {
	public Node fromNode;
	public Node toNode;
	public Set<Packet> packets;
	public int capacity;
	public int maxCapacity = Integer.MAX_VALUE;
	
	public Link(Node fromNode, Node toNode) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.packets = new HashSet<Packet>();
		capacity = 0;
	}
	
	public void addPacket(Packet p) {
		packets.add(p);
		capacity++;
	}
	
	public void removePacket(Packet p) {
		if (!packets.contains(p))
			throw new IllegalArgumentException("Link did not contain packet");
		packets.remove(p);
		capacity--;
	}
}
