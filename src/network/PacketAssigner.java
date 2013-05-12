package network;

import java.util.Random;

public class PacketAssigner {

	public static void assignPackets(Graph graph, int p) {
		Random r = new Random();
		for (int i = 0; i < p; i++) {
			Node from = graph.nodes.get(r.nextInt(graph.numNodes));
			int to = r.nextInt(graph.numNodes);
			from.addTraffic(to);
		}
	}	
}
