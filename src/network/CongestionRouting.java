package network;

public class CongestionRouting extends RoutingProtocol {
	public Graph graph;
	
	public CongestionRouting(Graph g){
		this.graph=g;
	}
	
	@Override
	public void route(Node sender, Packet p){
		if (sender.id == p.destination)
			return;
		System.out.println("packet destination: "+p.destination);
		int nextNodeID = (graph.nextNodeInPath.get(sender.id)).get(p.destination);
		sender.getOutLinkToNode(nextNodeID).addPacket(p);
	}
}
