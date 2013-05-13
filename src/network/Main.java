package network;

import java.util.Arrays;

import vis.GraphView;
import Metrics.LinkMetric;
import Metrics.Metric;
import Metrics.WorstLink;

public class Main {
    public static void main(String[] args) {
    	/*
    	Graph g = GraphGenerator.generateCloseConnectGraph(10, .1, new double[][] {{0, 1}, {0, 1}});
    	for (int i = 0; i < 10; i++)
    		System.out.println(Arrays.toString(g.dist[i]));
    	System.out.println(g.nextNodeInPath.get(0).get(0));
    	new GraphView(g);*/
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
    
    public static Graph GRAPH = GraphGenerator.generateCloseConnectGraph(10, .2, new double[][]{{-500, 500}, {-500, 500}}, LinkMetric.congestion);
    //public static RoutingProtocol PROTOCOL = new DistanceVector(GRAPH);
    public static RoutingProtocol PROTOCOL = new CongestionRouting(GRAPH);
    public static Router ROUTER = new Router(GRAPH);
    public static Metric[] METRICS = new Metric[]{new WorstLink()};
    
    public static void runSolution(boolean dynamic) {
    	TrafficAssigner.assignPackets(GRAPH, 200);
    	ROUTER.routeAllNodes(200, PROTOCOL,dynamic);

    	for (Metric m : METRICS) 
    		System.out.println(m.score(GRAPH));
    	
    	new GraphView(GRAPH);
    }
}
