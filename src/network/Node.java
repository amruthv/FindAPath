package network;

import java.util.HashSet;
import java.util.Set;

import utils.Point;

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
    
}
