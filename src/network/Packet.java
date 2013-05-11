package network;

public class Packet {

	public final Node source;
	public final Node destination;
	
	public Packet(Node source, Node destination) {
		this.source = source;
		this.destination = destination;
	}
	
}
