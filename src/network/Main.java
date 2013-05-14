package network;

import vis.GraphView;
import Metrics.*;
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

	//public static Graph GRAPH = GraphGenerator.generatePrefGraph(150, 5, 30, 2.5, new double[][] { { -350, 350 },{ -350, 350 }});
	public static Graph GRAPH = GraphGenerator.generateCloseConnectGraph(300, .1, new double[][]{{-350, 350}, {-350, 350}});
	//public static Graph GRAPH = GraphGenerator.genHGraph(116, .4, 350);
	//public static Graph GRAPH = GraphGenerator.generateCloseProbGraph(200, 10, 30, new double[][]{{-500, 500}, {-500, 500}});

	public static RoutingProtocol[] protocols = new RoutingProtocol[] {
			new StaticRouteProtocol(GRAPH, LinkMetric.hops), new StaticRouteProtocol(GRAPH, LinkMetric.degreeCentrality),
			new StaticRouteProtocol(GRAPH, LinkMetric.katzCentrality04), new StaticRouteProtocol(GRAPH, LinkMetric.katzCentrality10),
			new StaticRouteProtocol(GRAPH, LinkMetric.katzCentrality20), new StaticRouteProtocol(GRAPH, LinkMetric.pageRank99), 
			new StaticRouteProtocol(GRAPH, LinkMetric.pageRank50), new StaticRouteProtocol(GRAPH, LinkMetric.pageRank25), 
			new LeastBusyLinkRouting(GRAPH), new LeastBusyNeighborRouting(GRAPH), new LeastCongestionRouting(GRAPH),
			new RandomizedRouting(GRAPH) };
	// new LeastBusyNeighborRouting(GRAPH), new LeastBusyLinkRouting(GRAPH), new RandomizedRouting(GRAPH), new LeastCongestionRouting(GRAPH) };
	// public static RoutingProtocol[] protocols = new RoutingProtocol[] {new FewestHopsRouting(GRAPH)};

	public static Metric[] METRICS = new Metric[] { new WorstLink(), new SquaredSums(), new LinkVariance(),
			new LinkStDev(), new TotalReceived() };

	public static void runSolution(boolean dynamic) {
		TrafficAssigner.assignPackets(GRAPH, 800);
		GraphView gv = new GraphView(GRAPH);
		Router r;

		for (RoutingProtocol p : protocols) {
			r = new Router(GRAPH, p);
//			System.out.println("----------" + p.toString() + "----------");
			for (int i = 0; i < 100; i++) {
				r.routeAllNodes(10);
				// gv.repaint();
				// try{Thread.sleep(0);}catch (Exception e){};
			}
			System.out.print("=split(\"");
			System.out.print(p.toString()+",");
			for (Metric m : METRICS)
				System.out.print(m.score(GRAPH)+",");
			System.out.println("\"),\",\")");
			gv.renderToImage("renders/img_" + p.toString() + ".png");
		}
	}
}
