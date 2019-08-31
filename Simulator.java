import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class Simulator {
	public static void main(String[] args) throws IOException { 
		Integer nSC = PreProcessor.loadAllUniqueServiceCenters("serviceCenters.txt");
		Integer nVertices = PreProcessor.loadAllVertices("all.txt");
		PreProcessor.loadSC(nSC);
		Graph g = new Graph(nVertices);
		Integer nEdges = PreProcessor.loadCCNVD("edges.txt", g);
		//System.out.println("Number of Edges: " + nEdges);
		//g.printGraph();
		PreProcessor.floydWarshall(g);
		Graph dist = new Graph(nVertices);
		PreProcessor.dijkstra(g);
		Loral loral = new Loral();
		PreProcessor.LoadDistanceMatrixAndBuildMinHeap("distance.txt");
		Test.PrintDistanceMatrix();
		loral.scAssign();
//		loral.startAssigning();
		
	}
		
	
	

}