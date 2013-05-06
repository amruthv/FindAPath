package network;

import java.util.ArrayList;

import utils.Point;

public class Node {
    ArrayList<Link> links;
    Point loc;
    
    public Node(double xsize, double ysize) {
        links = new ArrayList<Link>();
        
    }
}
