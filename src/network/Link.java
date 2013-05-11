package network;

import java.util.ArrayList;
import java.util.List;

public class Link {
    public Node startNode;
    public Node endNode;
    public double capacity;
    public List<Packet> carrying;
    
    public Link(Node startNode, Node endNode, double linkCapacity){
    	this.startNode=startNode;
    	this.endNode= endNode;
    	this.capacity= linkCapacity;
    	this.carrying=new ArrayList<Packet>();
    }
}
