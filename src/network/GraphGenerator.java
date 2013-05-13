package network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Metrics.LinkMetric;

public class GraphGenerator {

	// range is [[xLow, xHigh], [yLow, yHigh]]

	// connects if distance < threshold
	public static Graph generateCloseConnectGraph(int n, double threshold, double[][] range) {
		List<Node> nodes = makeRandomPoints(n, range);
		double size = .5 * (range[0][1] - range[0][0] + range[1][1] - range[1][0]);
		//System.out.println(size);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				Node n1 = nodes.get(i);
				Node n2 = nodes.get(j);
				if (n1.distance(n2) / size <= threshold)
					connect(n1, n2);
			}
		}
		return new Graph(nodes);
	}

	// connects with prob = alpha * e ^ (-beta * distance)
	public static Graph generateCloseProbGraph(int n, double alpha, double beta, double[][] range) {
		List<Node> nodes = makeRandomPoints(n, range);
		double size = .5 * (range[0][1] - range[0][0] + range[1][1] - range[1][0]);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				Node n1 = nodes.get(i);
				Node n2 = nodes.get(j);
				if (Math.random() < alpha * Math.pow(Math.E, -beta * n1.distance(n2) / size))
					connect(n1, n2);
			}
		}
		return new Graph(nodes);
	}
	
	// nodes are given alpha_i distributed according power law with parameter p
	// connect with prob = alpha * alpha[i] * alpha[j] * e ^ (-beta * distance)
	public static Graph generatePrefGraph(int n, double alpha, double beta, double p, double[][] range) {
		List<Node> nodes = makeRandomPoints(n, range);
		double size = .5 * (range[0][1] - range[0][0] + range[1][1] - range[1][0]);
		double[] alphas = getAlphas(n, p);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				Node n1 = nodes.get(i);
				Node n2 = nodes.get(j);
				if (Math.random() < alpha * Math.pow(alphas[i] * alphas[j], .7) * Math.pow(Math.E, -beta * n1.distance(n2) / size))
					connect(n1, n2);
			}
		}
		return new Graph(nodes);
	}
	
	public static Graph generateHierachGraph(int n, double alpha, double beta, double[][] range) {
		int m = (int) Math.sqrt(n);
		double size = .5 * (range[0][1] - range[0][0] + range[1][1] - range[1][0]);
		Graph layout = generateCloseProbGraph(m, alpha, beta, range);
		
		Graph[] subGraphs = new Graph[m];
		for (int i = 0; i < m; i++)
			subGraphs[i] = generateCloseProbGraph(m, alpha, beta, getRangeOfNode(layout.nodes.get(i), m, size));
		Node[][] connectionPoints = getConnectionPoints(subGraphs);
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < i; j++) {
				if (layout.nodes.get(i).inLinks.contains(layout.nodes.get(j))) {
					connect(connectionPoints[i][0], connectionPoints[j][0]);
					connect(connectionPoints[i][1], connectionPoints[j][1]);
				}
			}
		}
		
		List<Node> finalNodes = new ArrayList<Node>();
		for (Graph g : subGraphs)
			finalNodes.addAll(g.nodes);
		return new Graph(finalNodes);
	}
	
	public static double[][] getRangeOfNode(Node node, int m, double size) {
		double halfS = .5 * size / Math.sqrt(m);
		double x = node.loc.x;
		double y = node.loc.y;
		return new double[][]{{x - halfS, x + halfS}, {y - halfS, y + halfS}};
	}
	
	public static Node[][] getConnectionPoints(Graph[] subGraphs) {
		Node[][] connectionPoints = new Node[subGraphs.length][];
		for (int i = 0; i < subGraphs.length; i++)
			connectionPoints[i] = pickTwo(subGraphs[i].nodes);
		return connectionPoints;
	}
	
	// assumes randomly ordered Graph.nodes
	public static Node[] pickTwo(List<Node> nodes) {
		return new Node[]{nodes.get(0), nodes.get(1)};
	}
	
	public static List<Node> makeRandomPoints(int n, double[][] range) {
		List<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < n; i++)
			nodes.add(new Node(getRandomInRange(range[0][0], range[0][1]), getRandomInRange(range[1][0], range[1][1])));
		return nodes;
	}
	
	public static double getRandomInRange(double low, double high) {
		return low + Math.random() * (high - low);
	}
	
	public static void connect(Node n1, Node n2) {
		Link link1 = new Link(n1, n2);
		Link link2 = new Link(n2, n1);
		n1.addLinks(link1, link2);
		n2.addLinks(link2, link1);
	}
	
	public static double[] getAlphas(int n, double p) {
		double[] alphas = new double[n];
		for (int i = 0; i < n; i++) {
			boolean chosen = false;
			double a = 0;
			while (!chosen) {
				a = 1 + Math.random() * 50;
				if (Math.random() < (p - 1) / Math.pow(a, p))
					chosen = true;
			}
			alphas[i] = a;
		}
		return alphas;
	}
	
}
