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
	public final Set<Link> inLinks;
	public final Set<Link> outLinks;
	public final Map<Integer, Integer> selfTraffic;
	public int id;
    
    public Node(double x, double y) {
    	loc = new Point(x, y);
        inLinks = new HashSet<Link>();
        outLinks = new HashSet<Link>();
        selfTraffic = new HashMap<Integer, Integer>();
    }
    
    public void addLinks(Link outLink, Link inLink) {
    	inLinks.add(inLink);
    	outLinks.add(outLink);
    }
    
    public void addTraffic(int dest) {
    	if (!selfTraffic.containsKey(dest))
    		selfTraffic.put(dest, 1);
    	else
    	selfTraffic.put(dest, selfTraffic.get(dest) + 1);
    }
    
    public double getDistance(Node other) {
    	return loc.distance(other.loc);
    }
    
    public Link getOutLinkToNode(int nextNodeID) {
    	for (Link l : this.outLinks){
    		if (l.toNode.id == nextNodeID){
    			return l;
    		}
    	}
    	//No link to that node
    	return null;
    }
    
	public List<Packet> getInPackets() {
		List<Packet> packets = new ArrayList<Packet>();
		
		for (int dest : selfTraffic.keySet()) {
			for (int i = 0; i < selfTraffic.get(dest); i++)
				packets.add(new Packet(id, dest));
		}
		
		for (Link inLink : inLinks) {
			for (Packet p: inLink.packets)
				packets.add(p);
			inLink.flushPackets();
		}
		
		return packets;
	}
	
	public int countInPackets() {
		int count = 0;
		for (int dest : selfTraffic.keySet())
			count += selfTraffic.get(dest);
		for (Link inLink : inLinks)
			count += inLink.capacity;
		return count;
	}
    
    public boolean equals(Node node) {
    	return this.id == node.id;
    }
    
}
