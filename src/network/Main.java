package network;

import vis.GraphView;
import Metrics.Metric;
import Metrics.WorstLink;

public class Main {
    public static void main(String[] args) {
    	runSolution();
    	seeGraphs();
    }
    
    public static void seeGraphs() {
        Graph g = GraphGenerator.generateCloseConnectGraph(500, .08, new double[][] {{-250,250},{-250,250}});
        new GraphView(g);
        
        //g = GraphGenerator.generateCloseProbGraph(500, .001, 0, new double[][] {{-500,500},{-500,500}});
        //new GraphView(g);
        
        //g = GraphGenerator.generateHierachGraph(500, .6, .05, new double[][] {{-500,500},{-500,500}});
        //new GraphView(g);
    }
    
    public static Graph GRAPH = GraphGenerator.generateCloseConnectGraph(10, .05, new double[][]{{-500, 500}, {-500, 500}});
    public static RoutingProtocol PROTOCOL = new DistanceVector(GRAPH);
    public static Router ROUTER = new Router(GRAPH);
    public static Metric[] METRICS = new Metric[]{new WorstLink()};
    
    public static void runSolution() {
        TrafficAssigner.assignPackets(GRAPH,20000);
    	ROUTER.routeAllNodes(20000, PROTOCOL);
    	for (Metric m : METRICS) 
    		System.out.println(m.score(GRAPH));    	
    }
}
