package network;

import java.util.Random;

public class TrafficAssigner {

	public static void assignPackets(Graph graph, int numPackets) {
		Random r = new Random();
		for (int i = 0; i < numPackets; i++) {
			Node from = graph.nodes.get(r.nextInt(graph.numNodes));
			int to = r.nextInt(graph.numNodes);
			from.addTraffic(to);
		}
	}	
}
