package Protocols;

import java.util.List;

import Metrics.LinkMetric;

import network.Node;
import network.Packet;

public abstract class RoutingProtocol {
	
	public LinkMetric lm;
	
	public void route(Node sender, List<Packet> p) {
	}
	
}
