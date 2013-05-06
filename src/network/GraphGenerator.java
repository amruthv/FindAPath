package network;

import java.util.ArrayList;
import java.util.List;

public class GraphGenerator {

	// range is [[xLow, xHigh], [yLow, yHigh]]
	
	public static Graph generateCloseConnectGraph(int n, double threshold, double[][] range) {
		
	}
	
	public static Graph generateCloseProbGraph(int n, double[][] range) {
		
	}
	
	public static Graph generatePrefGraph(int n, double[][] range) {
		
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
	
}
