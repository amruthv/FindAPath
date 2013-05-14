package Protocols;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import network.Graph;
import network.Link;
import network.Node;
import utils.NodeIDDistPair;
import java.util.Comparator;

public class DynamicProtocol extends RoutingProtocol {
	public Map<Integer,Integer> sssp(Node source, Graph g){
		int sID=source.id;
		Map<Integer,Integer> nextNodeInPath = new HashMap<Integer,Integer>();
		double[] dist = new double[g.numNodes];
		
		List<NodeIDDistPair> pairs = new ArrayList<NodeIDDistPair>(1);
		PriorityQueue<NodeIDDistPair> distances= new PriorityQueue<NodeIDDistPair>(11, new Comparator<NodeIDDistPair> () {
			@Override
			public int compare(NodeIDDistPair n1, NodeIDDistPair n2) {
				return n1.compareTo(n2);
			}
			
			@Override
			public boolean equals(Object o) {
				return false;
			}
		}); 

		for (int i=0;i<g.numNodes;i++){
			//Create a node infinity far away
			pairs.add(new NodeIDDistPair(i,Integer.MAX_VALUE));
			//Clear all next node in paths
			nextNodeInPath.put(i, null);
		}
		//Initialize distances and self for base step of dijkstra
		pairs.get(sID).dist=0;

		for(NodeIDDistPair p: pairs) {
			distances.add(p);
		}
		nextNodeInPath.put(sID, sID);
		while (distances.size()!=0){
			NodeIDDistPair min = distances.poll();
			dist[min.id]=min.dist;
			if (min.dist==Integer.MAX_VALUE){
				break;
			}
			for (Link l:g.nodes.get(min.id).outLinks){
				int neighborID = l.toNode.id;
				double neighborCost=pairs.get(neighborID).dist;
				double newPathCost =  min.dist + g.lm.getCost(l)+1000;
				if (neighborCost > newPathCost){
					NodeIDDistPair neighborToUpdate= pairs.get(neighborID);
					distances.remove(neighborToUpdate);
					neighborToUpdate.dist=newPathCost;
					distances.add(neighborToUpdate);
					//Set the next node to forward to be the next node in the shortest path through u
					int stepToU = nextNodeInPath.get(min.id);
					//More than 1 step away
					if (stepToU != sID){
						nextNodeInPath.put(neighborID, stepToU);
					}
					//Just one step away
					else{
						nextNodeInPath.put(neighborID, neighborID);
					}
				}
			}
		}
		
		return nextNodeInPath;
		
	}
}
