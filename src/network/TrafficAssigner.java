package network;

import java.util.Random;

public class TrafficAssigner {

	public static void assignPackets(Graph graph, int numPackets) {
		Random r = new Random();
		int count = 0;
		while (count < numPackets) {
			int from = r.nextInt(graph.numNodes);
			int to = r.nextInt(graph.numNodes);
			if (from != to && graph.dist[from][to] < Integer.MAX_VALUE) {
				graph.nodes.get(from).addTraffic(to);
				count++;
			}
		}
	}	
}
