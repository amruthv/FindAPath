package network;

import java.util.ArrayList;
import java.util.List;

import utils.Point;

public class Node {
    
    public final Point loc;
	public final List<Link> inLinks;
	public final List<Link> outLinks;
    
    public Node(double x, double y) {
    	loc = new Point(x, y);
        inLinks = new ArrayList<Link>();
        outLinks = new ArrayList<Link>();
    }
    
    public void addLink(Node other) {
    	inLinks.add(new Link(other, this));
    	outLinks.add(new Link(this, other));
    }
    
    public double getDistance(Node other) {
    	return loc.distance(other.loc);
    }
}
