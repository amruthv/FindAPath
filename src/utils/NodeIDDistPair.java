package utils;

import java.util.Comparator;

public class NodeIDDistPair {
	
	public int id;
	public double dist;
	
	public NodeIDDistPair(int id, double dist){
		this.id=id;
		this.dist=dist;
	}

	public int compareTo(NodeIDDistPair o) {
		if (this.dist < o.dist){
			return -1;
		}
		else if (this.dist==o.dist){
			return 0;
		}
		else{
			return 1;
		}
	}
	
	@Override
	public String toString(){
		return "Id: "+id+ ", dist: "+dist;
	}
}
