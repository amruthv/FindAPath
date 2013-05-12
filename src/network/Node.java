package network;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utils.Point;
//NODE IDS START AT 0

public class Node {
    public final Point loc;
	public final Set<Link> inLinks;
	public final Set<Link> outLinks;
	private int id;
    
    public Node(double x, double y) {
    	loc = new Point(x, y);
        inLinks = new HashSet<Link>();
        outLinks = new HashSet<Link>();
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public int getId() {
    	return id;
    }
    
    public void addLink(Node other) {
    	inLinks.add(new Link(other, this));
    	outLinks.add(new Link(this, other));
    }
    
    public double getDistance(Node other) {
    	return loc.distance(other.loc);
    }
    
    public Link getOutLinkToNode(int nextNodeID){
    	for (Link l : this.outLinks){
    		if (l.toNode.getId()==nextNodeID){
    			return l;
    		}
    	}
    	//No link to that node
    	return null;
    }
    
    public boolean equals(Node node){
    	if (this==node){
    		return true;
    	}
    	else if (this.id ==node.id){
    		return true;
    	}
    	return false;
    }
    
}
