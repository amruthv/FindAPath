package network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.Point;

public class GraphGenerator {

	// range is [[xLow, xHigh], [yLow, yHigh]]

	// connects if distance < threshold
	public static Graph generateCloseConnectGraph(int n, double threshold, double[][] range) {
		List<Node> nodes = makeRandomPoints(n, range);
		double scale = .5 * (range[0][1] - range[0][0] + range[1][1] - range[1][0]);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Node n1 = nodes.get(i);
				Node n2 = nodes.get(j);
				if (n1.getDistance(n2) / scale <= threshold) {
					n1.addLink(n2);
					n2.addLink(n1);
				}
			}
		}
		return new Graph(nodes);
	}

	// connects with prob = alpha * e ^ (-beta * distance)
	public static Graph generateCloseProbGraph(int n, double alpha, double beta, double[][] range) {
		List<Node> nodes = makeRandomPoints(n, range);
		double scale = .5 * (range[0][1] - range[0][0] + range[1][1] - range[1][0]);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Node n1 = nodes.get(i);
				Node n2 = nodes.get(j);
				if (Math.random() < alpha * Math.pow(Math.E, -beta * n1.getDistance(n2) / scale)) {
					n1.addLink(n2);
					n2.addLink(n1);
				}
			}
		}
		return new Graph(nodes);
	}
	
	// nodes are given alpha_i distributed according power law with parameter p
	// connect with prob = alpha * alpha[i] * alpha[j] * e ^ (-beta * distance)
	public static Graph generatePrefGraph(int n, double alpha, double beta, double p, double[][] range) {
		List<Node> nodes = makeRandomPoints(n, range);
		double scale = .5 * (range[0][1] - range[0][0] + range[1][1] - range[1][0]);
		double[] alphas = getAlphas(n, p);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Node n1 = nodes.get(i);
				Node n2 = nodes.get(j);
				if (Math.random() < alpha * alphas[i] * alphas[j] * Math.pow(Math.E, -beta * n1.getDistance(n2) / scale)) {
					n1.addLink(n2);
					n2.addLink(n1);
				}
			}
		}
		return new Graph(nodes);
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
