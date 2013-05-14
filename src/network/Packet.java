package network;

public class Packet {

	public final int source;
	public final int destination;
	
	public Packet(int source, int destination) {
		this.source = source;
		this.destination = destination;
	}
}
