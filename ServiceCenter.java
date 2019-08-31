import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class ServiceCenter {
    private Integer color;
    private Integer scIndex;
    private Integer maxCapacity;
    private Integer currCapacity;
    private Integer penalty;
    HashMap<Integer, GraphNode> boundary;
    HashSet<Integer> allocations;
    public static ArrayList<Float> distances = new ArrayList<Float>();
    
	public static PriorityQueue<MinHeapNode> minheap=new PriorityQueue<MinHeapNode>(1,new Comparator<MinHeapNode>() {
        @Override
        public int compare(MinHeapNode o1, MinHeapNode o2) {
        	if(o1.getDistance() == o2.getDistance()){
        		return o1.getDemandNodeID().compareTo(o2.getDemandNodeID());
        	} else{
        		return (int) (o1.getDistance()-o2.getDistance());
        	}
        	
        }
    });
   
	public ServiceCenter(int scIndex, Integer color, Integer maxCapacity, Integer penalty){
        this.scIndex         = scIndex;
        this.color           = color;
        this.maxCapacity     = maxCapacity;
        this.currCapacity    = maxCapacity;
        this.penalty         = penalty;
        this.boundary        = new HashMap<>();
        this.allocations     = new HashSet<>();
        this.distances       = new ArrayList<>();
    }
	
	 public HashMap<Integer, GraphNode> getBoundary() {
		return boundary;
	 }
	 
	 public void addNewAllocation(Integer demandNodeID){
		 allocations.add(demandNodeID);
	 }
	 
	public HashSet<Integer> getAllocations() {
		return allocations;
	}

	public void setBoundary(HashMap<Integer, GraphNode> boundary) {
		this.boundary = boundary;
	}

    public Integer getScIndex() {
		return scIndex;
	}
    public void setScIndex(int scIndex) {
		this.scIndex = scIndex;
	}

    public Integer getColor() {
        return color;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public Integer getCurrCapacity() {
        return currCapacity;
    }

    
    public void setCurrCapacity(int currCapacity) {
		this.currCapacity = currCapacity;
		
		if(currCapacity == 0){
			
		}
	}

	public Integer getPenalty() {
        return penalty;
    }

    
}