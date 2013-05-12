package network;

public class Packet {

	public final Node source;
	public final Node destination;
	public final int id;
	
	public Packet(Node source, Node destination, int id) {
		this.source = source;
		this.destination = destination;
		this.id = id;
	}
	
}
