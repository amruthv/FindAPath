package network;

import java.util.Arrays;

import vis.GraphView;
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
    	runSolution();
    }
    
    public static void seeGraphs() {
        Graph g = GraphGenerator.generateCloseConnectGraph(500, .08, new double[][] {{-250,250},{-250,250}});
        new GraphView(g);
        
        //g = GraphGenerator.generateCloseProbGraph(500, .001, 0, new double[][] {{-500,500},{-500,500}});
        //new GraphView(g);
        
        //g = GraphGenerator.generateHierachGraph(500, .6, .05, new double[][] {{-500,500},{-500,500}});
        //new GraphView(g);
    }
    
    public static Graph GRAPH = GraphGenerator.generateCloseConnectGraph(100, .2, new double[][]{{-500, 500}, {-500, 500}});
    public static RoutingProtocol PROTOCOL = new DistanceVector(GRAPH);
    public static Router ROUTER = new Router(GRAPH);
    public static Metric[] METRICS = new Metric[]{new WorstLink()};
    
    public static void runSolution() {
    	TrafficAssigner.assignPackets(GRAPH, 2000);
    	ROUTER.routeAllNodes(500, PROTOCOL);

    	for (Metric m : METRICS) 
    		System.out.println(m.score(GRAPH));
    	
    	new GraphView(GRAPH);
    }
}
