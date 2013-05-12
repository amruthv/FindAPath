package network;

import java.util.Random;

public class TrafficAssigner {

	public static void assignPackets(Graph graph, int numPackets) {
		Random r = new Random();
		int count = 0;
		while (count < numPackets) {
			Node from = graph.nodes.get(r.nextInt(graph.numNodes));
			int to = r.nextInt(graph.numNodes);
			if (graph.dist[from.id][to] < Integer.MAX_VALUE) {
				from.addTraffic(to);
				count++;
			}
		}
	}	
}
