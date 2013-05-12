package Metrics;
import network.Link;

public interface LinkMetric {
	static LinkMetric cost = new LinkMetric() {
		public double getCost(Link l) {
			return l.cost;
		}
	};
	
	static LinkMetric linkLength = new LinkMetric() {
		public double getCost(Link l) {
			return l.fromNode.loc.distance(l.toNode.loc);
		}
	};
	
	static LinkMetric availableBandwidth = new LinkMetric() {
		public double getCost(Link l) {
			return l.capacity - l.packets.size();
		}
	};
	
	static LinkMetric congestion = new LinkMetric() {
		public double getCost(Link l) {
			return l.packets.size();
		}
	};
	
	public double getCost(Link l);
}
