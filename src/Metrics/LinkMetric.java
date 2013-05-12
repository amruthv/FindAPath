package Metrics;
import network.Link;

public interface LinkMetric {
	static LinkMetric simpleCost = new LinkMetric() {
		public double getCost(Link l) {
			return l.cost;
		}
	};
	
	public double getCost(Link l);
}
