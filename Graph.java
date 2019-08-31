import java.util.List;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph {
	public int vCount;
	HashMap<Integer, List<GraphNode>> g;
	public int eCount;

	public Graph(int vCount) {
		this.vCount = vCount;
		g = new HashMap<>();
		this.eCount = 0;
	}

	public void addEdge(Integer i, Integer j, float weight) {
		List<GraphNode> existingGraphNodeList = g.get(i);
		if (existingGraphNodeList != null) {
			GraphNode graphNode = new GraphNode(i,j);
			existingGraphNodeList.add(graphNode);
			//System.out.println(i+SPLITBYCOMMA+j+SPLITBYCOMMA+j+" Existing node");
		} else {
			List<GraphNode> newGraphNodeList = new ArrayList<>();
			GraphNode graphNode = new GraphNode(i,j);
			newGraphNodeList.add(graphNode);
			g.put(i, newGraphNodeList);
			//System.out.println(i+SPLITBYCOMMA+j+SPLITBYCOMMA+j+" New node");
		}
		this.eCount = this.eCount+1;
	}

	public void removeEdge(Integer i, Integer j) {
//		adj[i][j] = Float.MAX_VALUE;
		System.out.println("remove edge called. nothing done");
	}

	public boolean hasEdge(Integer i, Integer j) {
		List<GraphNode> existingGraphNodeList = g.get(i);
		if (existingGraphNodeList != null) {
			Iterator itr = existingGraphNodeList.iterator();
			while(itr.hasNext()) {
				GraphNode gn = (GraphNode) itr.next();
				if(gn.getVId().equals(j)) return true;				
			}
		}
		return false;
	}

	public float getWeight(Integer i, Integer j) {
		List<GraphNode> existingGraphNodeList = g.get(i);
		if (existingGraphNodeList != null) {
			Iterator itr = existingGraphNodeList.iterator();
			while(itr.hasNext()) {
				GraphNode gn = (GraphNode) itr.next();
				if(gn.getVId().equals(j)) return gn.getWeight();				
			}
		}
		return Float.MAX_VALUE;
	}
	
	public void setWeight(Integer i, Integer j, float weight) {
		List<GraphNode> existingGraphNodeList = g.get(i);
		if (existingGraphNodeList != null) {
			Iterator itr = existingGraphNodeList.iterator();
			while(itr.hasNext()) {
				GraphNode gn = (GraphNode) itr.next();
				if(gn.getVId().equals(j)) gn.setWeight(weight);;				
			}
		}
	}
	
	public List<GraphNode> getNodeList(Integer i) {
		return g.get(i);
	}

	public HashMap<Integer, List<GraphNode>> getGraphMap() {
		return this.g;
	}
	
	public void printSize() {
		System.out.println("Size of graph is " + this.g.size() + " vertices and " + this.eCount);
	}
}