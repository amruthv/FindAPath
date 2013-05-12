package network;

import java.util.Random;

public class PacketAssigner {

	public static void assignPackets(Graph graph, int p) {
		Random r = new Random();
		for (int i = 0; i < p; i++) {
			Node from = graph.nodes.get(r.nextInt(graph.numNodes));
			Node to = graph.nodes.get(r.nextInt(graph.numNodes));
			Packet newPacket = new Packet(from, to, i);
			from.addSourcePacket(newPacket);
		}
	}	
}
