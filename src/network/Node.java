package network;

import java.util.ArrayList;

import utils.Point;

public class Node {
    public ArrayList<Link> links;
    public Point loc;
    
    public Node(double xsize, double ysize) {
        links = new ArrayList<Link>();
    }
    
    public void addLink(Node other) {
    	links.add(new Link(this, other));
    }
    
    public Point getLoc() {
    	return loc;
    }
    
    public double getDistance(Node other) {
    	return loc.distance(other.getLoc());
    }
}
