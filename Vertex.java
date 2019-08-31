public class Vertex {

	private Integer color;
	private Integer index;

	public Integer getIndex() {
		return index;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public Vertex() {
		super();
	}

	public Vertex(int vertexIndex, Integer color) {
		super();
		this.color = color;
		this.index = vertexIndex;
	}

}