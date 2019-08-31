public class GraphNode {
	//Vertex v;
	int adjacentNode;
	float weight;

	public GraphNode(Integer adjacentNode, float i) {
		super();
		this.adjacentNode = adjacentNode;
		this.weight = i;
	}

	public GraphNode() {
		super();
	}

	public Integer getVId() {
		return adjacentNode;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
}