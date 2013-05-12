package network;

public class Router {
	public Graph graph;
	public Router(Graph g){
		this.graph=g;
	}
	
	public void routeAllNodes(int numTimes, RoutingProtocol protocol){
		for (int i=0;i<numTimes;i++){
			for(Node sender:graph.nodes){
				protocol.route(sender);
			}
		}
	}
	
}
