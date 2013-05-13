package network;

import vis.GraphView;
import Metrics.LinkMetric;
import Metrics.LinkVariance;
import Metrics.Metric;
import Metrics.SquaredSums;
import Metrics.WorstLink;
import Protocols.*;

public class Main {
    public static void main(String[] args) {
    	runSolution(true);
    }
    
    public static void seeGraphs() {
        Graph g = GraphGenerator.generateCloseConnectGraph(500, .08, new double[][] {{-250,250},{-250,250}});
        new GraphView(g);
        
        //g = GraphGenerator.generateCloseProbGraph(500, .001, 0, new double[][] {{-500,500},{-500,500}});
        //new GraphView(g);
        
        //g = GraphGenerator.generateHierachGraph(500, .6, .05, new double[][] {{-500,500},{-500,500}});
        //new GraphView(g);
    }
    
    //public static Graph GRAPH = GraphGenerator.generateCloseConnectGraph(100, .1, new double[][]{{-500, 500}, {-500, 500}});
    //public static Graph GRAPH = GraphGenerator.generateCloseProbGraph(200, 10, 30, new double[][]{{-500, 500}, {-500, 500}});
    public static Graph GRAPH = GraphGenerator.generatePrefGraph(200, 2, 30, 2.5, new double[][]{{-500, 500}, {-500, 500}});
    
    public static RoutingProtocol PROTOCOL = new FewestHopsRouting(GRAPH);
  //public static RoutingProtocol PROTOCOL = new LeastCongestionRouting(GRAPH);
  //public static RoutingProtocol PROTOCOL = new AvoidCentralityRouting(GRAPH);
    //public static RoutingProtocol PROTOCOL = new LeastBusyNeighborRouting(GRAPH);
    //public static RoutingProtocol PROTOCOL = new RandomizedRouting(GRAPH);
    
    public static Router ROUTER = new Router(GRAPH);
    public static Metric[] METRICS = new Metric[]{new WorstLink(), new SquaredSums(), new LinkVariance()};
    
    public static void runSolution(boolean dynamic) {
    	new GraphView(GRAPH);

    	TrafficAssigner.assignPackets(GRAPH, 1000);
    	ROUTER.routeAllNodes(200, PROTOCOL);
    	for (Metric m : METRICS) 
    		System.out.println(m.score(GRAPH));
    	
    }
}
