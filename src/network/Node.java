package network;

import java.util.ArrayList;

import utils.Point;

public class Node {
    public ArrayList<Link> links;
    public Point loc;
    
    public Node(double xsize, double ysize) {
        links = new ArrayList<Link>();
        
    }
}
