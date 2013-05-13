package network;

import java.util.HashSet;
import java.util.Set;

public class Link {
	public final Node fromNode;
	public final Node toNode;
	public Set<Packet> packets;
	public int capacity;
	public final int maxCapacity = Integer.MAX_VALUE;
	public double cost;
	public int timesUsed = 0;
	
	public Link(Node fromNode, Node toNode) {
		if (fromNode == toNode)
			throw new IllegalArgumentException("Self link!");
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.packets = new HashSet<Packet>();
		capacity = 0;
		cost = 1;//fromNode.distance(toNode);
	}
	
	
	public void addPacket(Packet p) {
		packets.add(p);
		capacity++;
		timesUsed++;
	}
	
	public void removePacket(Packet p) {
		if (!packets.contains(p))
			throw new IllegalArgumentException("Link did not contain packet");
		packets.remove(p);
		capacity--;
	}
	
	public void flushPackets(){
		this.packets=new HashSet<Packet>();
		capacity = 0;
	}
	
	public double length() {
		return fromNode.distance(toNode);
	}
}
