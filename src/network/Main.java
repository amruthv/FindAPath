package network;

import vis.GraphView;
import Metrics.LinkMetric;
import Metrics.Metric;
import Metrics.WorstLink;
import Protocols.CongestionRouting;
import Protocols.RoutingProtocol;

public class Main {
    public static void main(String[] args) {
    	runSolution(true);
    }
    
    public static void seeGraphs() {
        Graph g = GraphGenerator.generateCloseConnectGraph(500, .08, new double[][] {{-250,250},{-250,250}},LinkMetric.congestion);
        new GraphView(g);
        
        //g = GraphGenerator.generateCloseProbGraph(500, .001, 0, new double[][] {{-500,500},{-500,500}});
        //new GraphView(g);
        
        //g = GraphGenerator.generateHierachGraph(500, .6, .05, new double[][] {{-500,500},{-500,500}});
        //new GraphView(g);
    }
    
    public static Graph GRAPH = GraphGenerator.generateCloseConnectGraph(100, .3, new double[][]{{-500, 500}, {-500, 500}}, LinkMetric.congestion);
    //public static RoutingProtocol PROTOCOL = new DistanceVector(GRAPH);
    public static RoutingProtocol PROTOCOL = new CongestionRouting(GRAPH);
    public static Router ROUTER = new Router(GRAPH);
    public static Metric[] METRICS = new Metric[]{new WorstLink()};
    
    public static void runSolution(boolean dynamic) {
    	TrafficAssigner.assignPackets(GRAPH, 200);
    	ROUTER.routeAllNodes(200, PROTOCOL);

    	for (Metric m : METRICS) 
    		System.out.println(m.score(GRAPH));
    	
    	new GraphView(GRAPH);
    }
}
