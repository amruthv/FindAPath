package network;

import Metrics.LinkMetric;


public class DistanceVector extends RoutingProtocol {
	Graph g;
	public DistanceVector(Graph g){
		this.g=g;
		g.calcShortestPaths(LinkMetric.simpleCost);
		g.computeAllNextInPath();
		
	}
	
	@Override
	public void route(Node sender, Packet p) {
		int nextNodeID=(g.nextNodeInPath.get(sender.id)).get(p.destination);
		sender.getOutLinkToNode(nextNodeID).addPacket(p);
		/*
		int currID = sender.id;
		for (int dest : sender.selfTraffic.keySet()) {
			for (int i = 0; i < sender.selfTraffic.get(dest); i++) {
				Packet p = new Packet(currID, dest);
				int nextNodeID = g.nextNodeInPath.get(currID).get(dest);
				Link linkToUse= sender.getOutLinkToNode(nextNodeID);
				linkToUse.addPacket(p);			
			}
		}
		
		for (Link inlink: sender.inLinks) {
			for (Packet p: inlink.packets) {
				int destID=p.destination;
				if (destID!=currID){
					int nextNodeID=(g.nextNodeInPath.get(currID)).get(destID);
					Link linkToUse= sender.getOutLinkToNode(nextNodeID);
					linkToUse.addPacket(p);
				}
			}
			inlink.flushPackets();*/
		
	}
}
