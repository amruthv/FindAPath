package utils;

public class NodeIDDistPair implements Comparable{
	
	public int id;
	public double dist;
	
	public NodeIDDistPair(int id, double dist){
		this.id=id;
		this.dist=dist;
	}

	@Override
	public int compareTo(Object o) {
		if (this.dist < ((NodeIDDistPair) o).dist){
			return -1;
		}
		else if (this.dist==((NodeIDDistPair)o).dist){
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
