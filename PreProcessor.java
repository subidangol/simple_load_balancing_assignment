import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
/*
 * This class loads service centers, demand vertices and distance matrix from a file.
 */
public class PreProcessor {
	public static String     CONSTCOMMA = ",";
	public static Integer    CONSTZERO    = 0;
	public static Integer    CONSTONE     = 1;
	public static Integer    CONSTTWO     = 2;
	public static Integer    CONSTTHREE   = 3;
	public static Integer    CONSTFOUR    = 4;
	public static Integer    CONSTFIVE    = 5;
	public static Integer    CONSTSIX     = 6;
	public static Integer    CONSTSEVEN   = 7;
	public static Integer    CONSTEIGHT   = 8;
	public static String     CONSTSPACE   = " ";
	public static Integer    CONSTGREYCOL = 8224125;
	
	public static HashMap<Integer, Vertex> allVertices    = new HashMap<Integer, Vertex>();
	public static HashMap<Integer, Vertex> demandVertices = new HashMap<Integer, Vertex>();
	public static HashMap<Integer, ServiceCenter> SC      = new HashMap<Integer, ServiceCenter>();
	public static ArrayList<Integer> availableSCIDs       = new ArrayList<Integer>();
	
	//store global pool of service centers in HashMap <ServiceCenterIDs, capacity-penalty>
	public static HashMap<Integer,String> serviceCenterIDs= new HashMap<Integer,String>();
	
	//these data structures are taken only for loading 65k * ns cost matrix
	public static ArrayList<Integer> allNodeIDs = new ArrayList<Integer>();
	public static ArrayList<Integer> serviceIDs = new ArrayList<Integer>();

	//Stores distances of demand node to various service centers with id as key. 
	public static HashMap<Integer,ArrayList<Float>> distanceMatrix = new HashMap<Integer,ArrayList<Float>>();
	
	//this routine takes path of file containing service center details as argument and load in the Hash map
	public static Integer loadAllUniqueServiceCenters(String filePath) throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String line = "";
		line = bufferedReader.readLine();
		while ((line = bufferedReader.readLine()) != null) {
			//splitting the file with delimiter ',' and fourth position is of Id
			String[] details = line.split(CONSTSPACE);
			if(!serviceCenterIDs.containsKey(details[CONSTONE])){
				if(!details[CONSTONE].equals(CONSTSPACE)){
					//System.out.println("Adding SCPool "+details[CONSTONE]+CONSTCOMMA+details[CONSTFOUR]+CONSTCOMMA+details[CONSTFIVE]);
					//4th Pos -> Service Center ID, 4th Pos -> Capacity,5th Pos -> Penalty
					serviceCenterIDs.put(Integer.parseInt(details[CONSTONE]),details[CONSTFOUR]+CONSTCOMMA+details[CONSTFIVE]);
				}
			}
		}
		System.out.println("Total No of service center added in GlobalPool "+serviceCenterIDs.size());
		bufferedReader.close();
		return serviceCenterIDs.size();
	}

	//this function load all the vertex id along with vertex object(id and color) in HashMap
	public static Integer loadAllVertices(String filePath) throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String line = CONSTSPACE;
		line = bufferedReader.readLine();
		
		while((line = bufferedReader.readLine()) != null){
			String[] details = line.split(CONSTSPACE);
			Vertex vertex = new Vertex(Integer.parseInt(details[CONSTONE]),CONSTGREYCOL); // grey color
			allVertices.put(Integer.parseInt(details[CONSTONE]), vertex);
			allNodeIDs.add(Integer.parseInt(details[CONSTONE]));
			//System.out.println("Adding Vertex "+details[CONSTONE]);
			if(!serviceCenterIDs.containsKey(Integer.parseInt(details[CONSTONE]))){
				demandVertices.put(Integer.parseInt(details[CONSTONE]),vertex);
				//System.out.println("It is demand Node "+details[CONSTONE]);
			} else {
				availableSCIDs.add(Integer.parseInt(details[CONSTONE]));
				serviceIDs.add(Integer.parseInt(details[CONSTONE]));
				//System.out.println("It is Service Center "+details[CONSTONE]);
			}
		}
		System.out.println("Total no of all vertices are "+allVertices.size());
		System.out.println("Total no of demand vertices are "+demandVertices.size());
		System.out.println("Total no of service centers are "+availableSCIDs.size());
		bufferedReader.close();
		return allVertices.size();
	}


	//load Service Center Vertices argument ns-> no of service centers to be loaded
	public static void loadSC(int ns) {
		int totalServiceCenters = availableSCIDs.size();
		if(ns > totalServiceCenters){ //no of service centers to be loaded are more than no of service center available
			System.out.println("Not able to load service center. Service centers are few");
		} else {
			for(int i=0;i<ns;i++){
				Vertex scVertex = allVertices.get(availableSCIDs.get(i));
				String[] scMetrices = serviceCenterIDs.get(availableSCIDs.get(i)).split(CONSTCOMMA);
				int capacity = Integer.parseInt(scMetrices[CONSTZERO]);
				int penalty  = Integer.parseInt(scMetrices[CONSTONE]);

				ServiceCenter serviceCenter = new ServiceCenter(scVertex.getIndex(),Constants.color[i], capacity, penalty);
				SC.put(availableSCIDs.get(i), serviceCenter);
				//System.out.print("SC " + scVertex.getIndex() + " in Vornoi. ");
				//System.out.println("Color, Capacity and Penalty added "+serviceCenter.getColor()+CONSTCOMMA
				//		+serviceCenter.getCurrCapacity()+CONSTCOMMA+serviceCenter.getPenalty());
			}
		}
		System.out.println("Total No of Service Center Loaded in Vornoi "+SC.size());
	}

	/* Load everything in CCNVD */
	public static Integer loadCCNVD(String filePath, Graph g) throws IOException {
		int edgesCount = 0;
//		HashMap<String, List<GraphNode>> graph = new HashMap<>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String line = CONSTSPACE;
		line = bufferedReader.readLine();
		while ((line = bufferedReader.readLine()) != null) {
			String[] details  = line.split(CONSTSPACE);

			//check if both of the vertices are present in the allVertex HashMap
			if (allVertices.containsKey(Integer.parseInt(details[CONSTONE]))&&
					allVertices.containsKey(Integer.parseInt(details[CONSTTWO]))) {
				edgesCount++;
				g.addEdge(Integer.parseInt(details[CONSTONE]), Integer.parseInt(details[CONSTTWO]), Integer.parseInt(details[CONSTTWO]));
			}
		}
		bufferedReader.close();
		Loral.CCNVD.setGraph(g);
		List<ServiceCenter> list = new ArrayList<ServiceCenter>(SC.values());
		Loral.CCNVD.setSC(list);
		Loral.CCNVD.setDemandVertices(demandVertices);
		g.printSize();
		return edgesCount;
	}

	public static void LoadDistanceMatrixAndBuildMinHeap(String filePath) throws IOException{
		Iterator<Integer> allNodeIterator = allNodeIDs.iterator();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String line = bufferedReader.readLine();
		while(allNodeIterator.hasNext()){
			Integer nodeID = allNodeIterator.next();
			line = bufferedReader.readLine();
			Vertex node = demandVertices.get(nodeID);
			if(node!=null){
				ArrayList<Float> distances = new ArrayList<Float>();
				String[] distanceRow = line.split(CONSTCOMMA);
				float minDistance = Float.MAX_VALUE;
				Integer closestSC = null;
				for(int i = demandVertices.size();i<distanceRow.length;i++){
//					indexToSCIDMapping.put(i, serviceIDs.get(i));
					float distance = (Float) Float.parseFloat(distanceRow[i]);
					distances.add(distance);
					if(distance <= minDistance) {
						minDistance = distance;
						closestSC = allNodeIDs.get(i);
					}
				}
				System.out.println("Closest sc for "+nodeID+" is "+closestSC);
				MinHeapNode minHeapNode = new MinHeapNode(minDistance,nodeID,closestSC);
				
				Loral.minheap.add(minHeapNode);
				distanceMatrix.put(nodeID, distances);
			}
		}
	}
}
