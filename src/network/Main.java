package network;

import vis.GraphView;

public class Main {
    public static void main(String[] args) {
        Graph g = GraphGenerator.generateCloseConnectGraph(500, .1, new double[][] {{-500,500},{-500,500}});
        //new GraphView(g);
        
        g = GraphGenerator.generateCloseProbGraph(500, 9, 50, new double[][] {{-500,500},{-500,500}});
        new GraphView(g);
        
        g = GraphGenerator.generateHierachGraph(500, 50, 15, new double[][] {{-500,500},{-500,500}});
        //new GraphView(g);
    }
}
