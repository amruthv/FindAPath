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
			return l.maxCapacity - l.packets.size();
		}
	};
	
	static LinkMetric congestion = new LinkMetric() {
		public double getCost(Link l) {
			return l.packets.size();
		}
	};
	
	static LinkMetric centrality = new LinkMetric() {
		public double getCost(Link l) {
			return Math.pow(l.fromNode.centrality, 2) + Math.pow(l.toNode.centrality, 2);
		}
	};
	
	static LinkMetric hops = new LinkMetric() {
		public double getCost(Link l) {
			return 1.0;
		}
	};
	
	
	public double getCost(Link l);
}
