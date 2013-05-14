package Protocols;

import java.util.Map;

import network.Graph;
import network.Link;
import network.Node;
import network.Packet;
import Metrics.LinkMetric;

public class LeastCongestionRouting extends DynamicProtocol {
	public LinkMetric lm;
	public Map<Integer,Integer> nextNodeInPath;
	
	public LeastCongestionRouting(Graph g){
		super(g);
		this.lm = LinkMetric.congestion;
	}
	
	@Override
	public String toString(){
		return "LeastCongestionRouting";
	}



	@Override
	public void initialize() {		
	}



	@Override
	public void prepareGraph() {
	}



	@Override
	public void prepareNode(Node sender) {
		this.nextNodeInPath=sssp(sender,g);
	}



	@Override
	public Link routePacket(Node sender, Packet p) {
		int nextNodeID = (nextNodeInPath).get(p.destination);
		return sender.getOutLinkToNode(nextNodeID);
	}
}
