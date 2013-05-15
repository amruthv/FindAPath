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
		Graph g = GraphGenerator.genHGraph(100, .4, 350, 10);

		new GraphView(g);
	}

	static Graph GRAPH = GraphGenerator.generatePrefGraph(150, 5, 30, 2.5, new double[][] { { -350, 350 },{ -350, 350 }}, 10);
	//static Graph GRAPH = GraphGenerator.generateCloseConnectGraph(200, .12, new double[][] { { -350, 350 },{ -350, 350 }}, 10);
	//static Graph GRAPH = GraphGenerator.genHGraph(116, .4, 350, 10);
	//static Graph GRAPH = GraphGenerator.generateCloseProbGraph(200, 2, 20, new double[][]{{-350, 350}, {-350, 350}}, 10);
	//static Graph GRAPH = GraphGenerator.generateHierachGraph(250, 5, 5, new double[][] {{-350,350},{-350,350}}, 10);
	
	
	public static RoutingProtocol[] protocols = new RoutingProtocol[] {
			new StaticRouteProtocol(GRAPH, LinkMetric.hops),
			new StaticRouteProtocol(GRAPH, LinkMetric.degreeCentrality),
			new StaticRouteProtocol(GRAPH, LinkMetric.hopBetweeness),
			new StaticRouteProtocol(GRAPH, LinkMetric.degBetweeness),
			//new StaticRouteProtocol(GRAPH, LinkMetric.katzBetweeness),
			//new StaticRouteProtocol(GRAPH, LinkMetric.prankBetweeness),
			new StaticRouteProtocol(GRAPH, LinkMetric.katzCentrality04),
			new StaticRouteProtocol(GRAPH, LinkMetric.katzCentrality10),
			new StaticRouteProtocol(GRAPH, LinkMetric.katzCentrality20),
			new StaticRouteProtocol(GRAPH, LinkMetric.pageRank99),
			new StaticRouteProtocol(GRAPH, LinkMetric.pageRank50),
			new StaticRouteProtocol(GRAPH, LinkMetric.pageRank25), 
			new LeastBusyLinkRouting(GRAPH),
			new LeastBusyNeighborRouting(GRAPH), 
			new LeastCongestionRouting(GRAPH), 
			new RandomizedRouting(GRAPH) };

	// new LeastBusyNeighborRouting(GRAPH), new LeastBusyLinkRouting(GRAPH), new RandomizedRouting(GRAPH), new LeastCongestionRouting(GRAPH) };
	// public static RoutingProtocol[] protocols = new RoutingProtocol[] {new FewestHopsRouting(GRAPH)};

	public static Metric[] METRICS = new Metric[] { new WorstLink(), new SquaredSums(), new LinkVariance(),
			new LinkStDev(), new TotalReceived() };

	public static void runSolution(boolean dynamic) {
		TrafficAssigner.assignPackets(GRAPH, 2000);
		GraphView gv = new GraphView(GRAPH);
		Router r;

		System.out.print("=split(\"");
		System.out.print("Routing, ");
		for (Metric m : METRICS)
			System.out.print(m.toString()+",");
		System.out.println("\",\",\")");
		
		long time = System.currentTimeMillis();
		
		gv.renderToImage("renders/" + time + "/img_g_degree.png", GraphView.NodeColoring.DEG);
		gv.renderToImage("renders/" + time + "/img_g_katz.png", GraphView.NodeColoring.KATZ);
		gv.renderToImage("renders/" + time + "/img_g_prank.png", GraphView.NodeColoring.PAGE);
		gv.renderToImage("renders/" + time + "/img_g_bet_hop.png", GraphView.NodeColoring.HOP_BET);

		for (RoutingProtocol p : protocols) {
			r = new Router(GRAPH, p);
//			System.out.println("----------" + p.toString() + "----------");
			for (int i = 0; i < 50; i++) {
				r.routeAllNodes(10);
				// gv.repaint();
				// try{Thread.sleep(0);}catch (Exception e){};
			}
			System.out.print("=split(\"");
			System.out.print(p.toString()+",");
			for (Metric m : METRICS)
				System.out.print(m.score(GRAPH)+",");
			System.out.println("\",\",\")");
			gv.renderToImage("renders/" + time + "/img_q_" + p.toString() + ".png", GraphView.NodeColoring.QUEUE);
			gv.renderToImage("renders/" + time + "/img_qr_" + p.toString() + ".png", GraphView.NodeColoring.Q_RATE);
			gv.renderToImage("renders/" + time + "/img_f_" + p.toString() + ".png", GraphView.NodeColoring.FLOW);
		}
	}
}
