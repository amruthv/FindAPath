package network;



public class DistanceVector extends RoutingProtocol {
	
	public Graph g;
	
	public DistanceVector(Graph g){
		this.g=g;
		g.computeAllNextInPath();
	}
	
	@Override
	public void route(Node sender, Packet p) {
		if (sender.id == p.destination)
			return;
		int nextNodeID = (g.nextNodeInPath.get(sender.id)).get(p.destination);
		sender.getOutLinkToNode(nextNodeID).addPacket(p);
	}
	
}
