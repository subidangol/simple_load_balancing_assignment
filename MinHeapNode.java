public class MinHeapNode {
	//stores the minimum distance of demandNode to particular Service center
	private float distance;
	//stores demand Node id
    private Integer dnIndex;
    //stores service center id of service center from which distance of demand node is minimum
    private Integer scIndex;
	public MinHeapNode(float distanceOfDemandNodeToR, Integer dnIndex, Integer scIndex) {
		super();
		this.distance = distanceOfDemandNodeToR;
		this.dnIndex = dnIndex;
		this.scIndex = scIndex;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public Integer getDemandNodeID() {
		return dnIndex;
	}
	
	public Integer getServiceCenterID() {
		return scIndex;
	}

}