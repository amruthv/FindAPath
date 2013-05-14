package network;

import vis.GraphView;
import Metrics.LinkStDev;
import Metrics.LinkVariance;
import Metrics.Metric;
import Metrics.SquaredSums;
import Metrics.WorstLink;
import Protocols.*;

public class Main {

	public static void main(String[] args) {
		// seeGraphs();
		runSolution(true);
	}

	public static void seeGraphs() {
		// Graph g = GraphGenerator.generateCloseConnectGraph(500, .08, new double[][] {{-350,350},{-350,350}});
		// Graph g = GraphGenerator.generateCloseProbGraph(500, 1.5, 25, new double[][] {{-350,350},{-350,350}});
		// Graph g = GraphGenerator.generateHierachGraph(200, .8, 3, new double[][] {{-250,250},{-250,250}});
		Graph g = GraphGenerator.genHGraph(100, .4, 350);

		new GraphView(g);
	}

	public static Graph GRAPH = GraphGenerator.generatePrefGraph(200, 5, 30, 2.5, new double[][] { { -500, 500 }, { -500, 500 } });
	// public static Graph GRAPH = GraphGenerator.generateCloseConnectGraph(100, .05, new double[][]{{-500, 500}, {-500, 500}});
	// public static Graph GRAPH = GraphGenerator.genHGraph(116, .4, 350);
	// public static Graph GRAPH = GraphGenerator.generateCloseProbGraph(200, 10, 30, new double[][]{{-500, 500}, {-500, 500}});

	public static RoutingProtocol[] protocols = new RoutingProtocol[] { new FewestHopsRouting(GRAPH), new AvoidCentralityRouting(GRAPH), new LeastBusyNeighborRouting(GRAPH), new RandomizedRouting(GRAPH), new LeastCongestionRouting(GRAPH) };
	// public static RoutingProtocol[] protocols = new RoutingProtocol[]{new AvoidCentralityRouting(GRAPH)};

	// public static RoutingProtocol PROTOCOL = new FewestHopsRouting(GRAPH);
	// public static RoutingProtocol PROTOCOL = new LeastCongestionRouting(GRAPH);
	// public static RoutingProtocol PROTOCOL = new AvoidCentralityRouting(GRAPH);
	// public static RoutingProtocol PROTOCOL = new LeastBusyNeighborRouting(GRAPH);
	// public static RoutingProtocol PROTOCOL = new RandomizedRouting(GRAPH);

	public static Router ROUTER = new Router(GRAPH);
	public static Metric[] METRICS = new Metric[] { new WorstLink(), new SquaredSums(), new LinkVariance(), new LinkStDev() };

	public static void runSolution(boolean dynamic) {
		// new GraphView(GRAPH);
		TrafficAssigner.assignPackets(GRAPH, 800);
		GraphView gv = new GraphView(GRAPH);
		for (RoutingProtocol p : protocols) {
			System.out.println("----------" + p.toString() + "----------");
			ROUTER.routeAllNodes(200, p);
			for (Metric m : METRICS)
				System.out.println(m.toString() + m.score(GRAPH));

			gv.renderToImage("renders/img_" + p.getClass().getName() + ".png");
		}
	}
}
