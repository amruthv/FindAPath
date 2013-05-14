package network;

import java.util.ArrayList;
import java.util.List;

public class Link {
	public final Node fromNode;
	public final Node toNode;
	public List<Packet> packets;
	public final int maxCapacity = 10;
	public double cost;
	public int timesUsed = 0;
	
	public Link(Node fromNode, Node toNode, int capacity) {
		if (fromNode == toNode)
			throw new IllegalArgumentException("Self link!");
		
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.packets = new ArrayList<Packet>();
		cost = fromNode.distance(toNode);
	}
	
	public void addPacket(Packet p) {
		packets.add(p);
		timesUsed++;
	}
	
	public void flushPackets(){
		packets.clear();
	}
	
	public double length() {
		return fromNode.distance(toNode);
	}
}
