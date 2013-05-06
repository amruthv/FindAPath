package network;

import java.util.ArrayList;

public class Node {
    ArrayList<Link> links;
    double xpos;
    double ypos;
    
    public Node(double xsize, double ysize) {
        links = new ArrayList<Link>();
        
    }
}
