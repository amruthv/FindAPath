package network;

import java.util.List;

public class Graph {
	
	public List<Node> nodes;
	
	public Graph(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public void setRoutingProtocol(){
		
	}
	
	public int calcDiameter() {
		return 0;
	}
	
	public double[] calcBetweenessCentrality() {
		return null;
	}
	
	public double[] calcEigenvectorCentrality() {
		return null;
	}
	
	public double calcClusteringCoeff() {
		return 0;
	}
	
}
