package Metrics;
import network.Graph;
import network.Link;

public interface LinkMetric {
	static LinkMetric cost = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return l.cost;
		}
	};
	
	static LinkMetric linkLength = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return l.fromNode.loc.distance(l.toNode.loc);
		}
	};
	
	static LinkMetric availableBandwidth = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return l.maxCapacity - l.packets.size();
		}
	};
	
	static LinkMetric congestion = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return l.packets.size();
		}
	};
	
	static LinkMetric degreeCentrality = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return g.calcDegreeCentrality()[l.toNode.id];
		}
		public String toString() {return "DegreeCentrality";};
	};


	static LinkMetric katzCentrality04 = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return g.calcKatzCentrality(.04)[l.toNode.id];
		}
		public String toString() {return "Katz04";};
	};

	static LinkMetric katzCentrality10 = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return g.calcKatzCentrality(.10)[l.toNode.id];
		}
		public String toString() {return "Katz10";};
	};

	static LinkMetric katzCentrality20 = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return g.calcKatzCentrality(.20)[l.toNode.id];
		}
		public String toString() {return "Katz20";};
	};

	static LinkMetric pageRank25 = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return g.calcPageRank(.25)[l.toNode.id];
		}
		public String toString() {return "PageRank25";};
	};
	
	static LinkMetric pageRank50 = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return g.calcPageRank(.50)[l.toNode.id];
		}
		public String toString() {return "PageRank50";};
	};

	static LinkMetric pageRank99 = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return g.calcPageRank(.999)[l.toNode.id];
		}
		public String toString() {return "PageRank99";};
	};
	
	static LinkMetric hops = new LinkMetric() {
		public double getCost(Graph g, Link l) {
			return 1.0;
		}
		public String toString() {return "Hops";};
	};
	
	
	public double getCost(Graph g, Link l);
}
