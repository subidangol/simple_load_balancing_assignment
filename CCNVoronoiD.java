import java.util.HashMap;
import java.util.List;

/**
 * Created by ankita_mehta on 8/6/16.
 */

/**
 * Description about the class
 */

public class CCNVoronoiD {
    Graph graph;
    List<ServiceCenter> serviceCenters;
    HashMap<Integer, Vertex> demandVertices;
    
    public HashMap<Integer, Vertex> getDemandVertices() {
		return demandVertices;
	}

	public void setDemandVertices(HashMap<Integer, Vertex> demandVertices2) {
		this.demandVertices = demandVertices2;
	}

	public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public List<ServiceCenter> getSC() {
        return serviceCenters;
    }

    public void setSC(List<ServiceCenter> serviceCenters) {
        this.serviceCenters = serviceCenters;
    }
}