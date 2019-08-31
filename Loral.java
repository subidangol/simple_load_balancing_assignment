import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class Loral {
	private float objectiveFunctionCost = 0;
	private HashMap<Integer,Integer> assignments = new HashMap<Integer,Integer>();
	private Integer maxPathLengthSize;
	private Integer k;
	public static Integer CONSTGREYCOL = 8224125;
	
	public static PriorityQueue<MinHeapNode> minheap=new PriorityQueue<MinHeapNode>(1,new Comparator<MinHeapNode>() {
        @Override
        public int compare(MinHeapNode o1, MinHeapNode o2) {
        	if(o1.getDistance() == o2.getDistance()){
        		return o1.getDemandNodeID().compareTo(o2.getDemandNodeID());
        	} else{
        		return (int)(o1.getDistance()-o2.getDistance());
        	}
        	
        }
    });
	
	public static CCNVoronoiD CCNVD = new CCNVoronoiD();
	
	public Loral() {
	}

	public void scAssign() {
		HashMap<Integer, Vertex> demandNodeList = CCNVD.getDemandVertices();
		
		while(true){
			if(minheap.size() == 0)	break;

			MinHeapNode minHeapNode = minheap.element();
			
			if(assignments.containsKey(minHeapNode.getDemandNodeID())){
//				updateSCheap(minHeapNode.getServiceCenterID());
				System.out.println("updateSCheap called.");
				
			}
			
			Integer demandNodeID = minHeapNode.getDemandNodeID();
			ServiceCenter serviceCenter = PreProcessor.SC.get(minHeapNode.getServiceCenterID());
			
			int currCapacity = serviceCenter.getCurrCapacity();
			/*
			 * 1-> color the demandNode of the same color as of Service center 
			 * 2-> reduce the current capacity of service center by 1
			 * 3-> add distance cost to global objective function
			 * 4-> update the boundary nodes 
			*/
			demandNodeList.get(demandNodeID).setColor(serviceCenter.getColor());
			serviceCenter.setCurrCapacity(currCapacity-1);
			objectiveFunctionCost += minHeapNode.getDistance();
			assignments.put(demandNodeID, serviceCenter.getScIndex());
			CheckUpdateAndInsertBoundaryNodes(minHeapNode);
			serviceCenter.addNewAllocation(demandNodeID);
			minheap.remove();
		}
		
		printAllocations();
	}
	
	public void startAssigning() {
		HashMap<Integer, Vertex> demandNodeList = CCNVD.getDemandVertices();
		//System.out.println("Size of demandNodeList :"+ demandNodeList.size());
		while(true){
			if(minheap.size() == 0)	break;

			MinHeapNode minHeapNode = minheap.element();
			
			Integer demandNodeID = minHeapNode.getDemandNodeID();
			ServiceCenter serviceCenter = PreProcessor.SC.get(minHeapNode.getServiceCenterID());
			int currCapacity = serviceCenter.getCurrCapacity();		
			/*
			 * 1-> color the demandNode of the same color as of Service center 
			 * 2-> reduce the current capacity of service center by 1
			 * 3-> add distance cost to global objective function
			 * 4-> update the boundary nodes 
			*/
			demandNodeList.get(demandNodeID).setColor(serviceCenter.getColor());
			serviceCenter.setCurrCapacity(currCapacity-1);
			objectiveFunctionCost += minHeapNode.getDistance();
			assignments.put(demandNodeID, serviceCenter.getScIndex());
			CheckUpdateAndInsertBoundaryNodes(minHeapNode);
			serviceCenter.addNewAllocation(demandNodeID);
			minheap.remove();
		}
		printAllocations();
	}

	private void updateSCheap(String serviceCenterID) {
		// TODO Auto-generated method stub
		
	}

	void printAllocations() {
		Iterator<Integer> iter = assignments.keySet().iterator();
		while(iter.hasNext()){
			Integer id = iter.next();
			System.out.print(id+" is assigned to "+assignments.get(id));
			if(checkBoundary(id, assignments.get(id)))
				System.out.print(" as a boundary demand vertex.");
			System.out.println();
		}
	}

	private void CheckUpdateAndInsertBoundaryNodes(MinHeapNode minHeapNode) {
		//update existing boundary nodes of service center
		ServiceCenter serviceCenter = PreProcessor.SC.get(minHeapNode.getServiceCenterID());
		Iterator<Integer> allocatedIterator = serviceCenter.getAllocations().iterator();
		
		while(allocatedIterator.hasNext()){
			Integer demandNodeID = allocatedIterator.next();
			
			if(!checkBoundary(demandNodeID,serviceCenter.getScIndex())){
				serviceCenter.getBoundary().remove(demandNodeID);
				System.out.println("Demand Node : "+demandNodeID+" is no more boundary");
			} else {
				if(!serviceCenter.getBoundary().containsKey(demandNodeID)){
					//as we are not using distance in graphNode so distance is taken as 0 by default
					GraphNode graphNode = new GraphNode(demandNodeID,0);
					serviceCenter.getBoundary().put(demandNodeID, graphNode);
				}
				System.out.println("Demand Node : "+demandNodeID+" is boundary");
			}
		}

		if(checkBoundary(minHeapNode.getDemandNodeID(),minHeapNode.getServiceCenterID())){
			System.out.println("Assigning Boundary Node "+minHeapNode.getDemandNodeID()+" to SC "
				+minHeapNode.getServiceCenterID());
			//insert the demand node in boundary list of service center
			GraphNode graphNode = new GraphNode(minHeapNode.getDemandNodeID(),minHeapNode.getDistance());
			HashMap<Integer,GraphNode> existingBoundary = serviceCenter.getBoundary();
			existingBoundary.put(minHeapNode.getDemandNodeID(),graphNode);
		}
	}
	
	private boolean checkBoundary(Integer demandNodeID,Integer serviceCenterID){
		boolean isBoundaryNode = false;
		int color = CCNVD.getDemandVertices().get(demandNodeID).getColor();
		//fetch all the adjacent Node list from the CCNVD
		List<GraphNode> graphNodeList = CCNVD.getGraph().getNodeList(demandNodeID);
		//System.out.println("Checking for Demand Node: "+demandNodeID+"-"+color);
		for(int i=0;i<graphNodeList.size();i++){
			Integer adjacentNodeID = graphNodeList.get(i).getVId();
			if(CCNVD.getDemandVertices().containsKey(adjacentNodeID)){
				Integer adjacentNodeColor = CCNVD.getDemandVertices().get(adjacentNodeID).getColor();
				//System.out.print("Adjacent Demand Node: "+adjacentNodeID+"-"+adjacentNodeColor);
				if(color != adjacentNodeColor || adjacentNodeColor == CONSTGREYCOL){
					//System.out.println(" has gray or different color");
					isBoundaryNode = true;
					break;
				}
				else{
					//System.out.println(" has same color");
				}
			}
			else{
				//adjacent node is service center 
				//adjacent node is not one SC to which demand node is assigned
				if(!adjacentNodeID.equals(serviceCenterID)){
					//System.out.println("Adjacent Service center: "+adjacentNodeID);
					isBoundaryNode = true;
					break;
				}
			}
		}
		return isBoundaryNode;
	}
	
	public Integer getMaxPathLengthSize() {
		return maxPathLengthSize;
	}

	public Integer getK() {
		return k;
	}

	public float getObjectiveFunctionCost() {
		return objectiveFunctionCost;
	}

	public HashMap<Integer, Integer> getAssignments() {
		return assignments;
	}
	
}