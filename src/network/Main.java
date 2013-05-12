package network;

import vis.GraphView;

public class Main {
    public static void main(String[] args) {
        Graph g = GraphGenerator.generateCloseConnectGraph(1000, .06, new double[][] {{-500,500},{-500,500}});
        new GraphView(g);
        
        g = GraphGenerator.generateCloseProbGraph(500, .001, 0, new double[][] {{-500,500},{-500,500}});
        new GraphView(g);
        
        g = GraphGenerator.generateHierachGraph(500, .6, .05, new double[][] {{-500,500},{-500,500}});
        new GraphView(g);
    }
}
