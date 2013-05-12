package network;


public class DistanceVector extends RoutingProtocol {
	Graph g;
	public DistanceVector(Graph g){
		this.g=g;
	}
	public void Route(Node sender){
		int currID=sender.id;
		for (Link inlink: sender.inLinks){
			for (Packet p: inlink.packets){
				int destID=p.destination.id;
				if (destID!=currID){
					int nextNodeID=(g.nextNodeInPath.get(currID)).get(destID);
					Link linkToUse= sender.getOutLinkToNode(nextNodeID);
					linkToUse.addPacket(p);
				}
			}
			inlink.flushPackets();
		}
	}
}
