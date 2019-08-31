import java.util.ArrayList;
import java.util.Iterator;

public class Test {

	public static void PrintDistanceMatrix(){
		Iterator<Integer> iterator = PreProcessor.SC.keySet().iterator();
		System.out.println("Distance Matrix");
		while(iterator.hasNext()) {
			Integer scID = iterator.next();
			System.out.print(scID+"-->");
			ServiceCenter sc = PreProcessor.SC.get(scID);
			ArrayList<Float> distances = sc.distances;
			for(int i=0;i<distances.size();i++){
				if(distances.get(i) == Integer.MAX_VALUE) System.out.print("INF,");
				else System.out.print(distances.get(i)+",");
			}
			System.out.println();
		}
	}
	
	public static void printSC(String scID){
		ServiceCenter sc = PreProcessor.SC.get(scID);
		System.out.println("--------------------------");
		System.out.println("Service Center "+sc.getScIndex()+" Details");
		System.out.println("Color is "+sc.getColor());
		System.out.println("Max Capacity is "+sc.getMaxCapacity());
		System.out.println("Curr Capacity is "+sc.getCurrCapacity());
		System.out.println("Penalty is "+sc.getPenalty());
		Iterator<Integer> it = sc.getBoundary().keySet().iterator();
		System.out.println("Boundaries are ");
		while(it.hasNext()){
			System.out.print(it.next()+" ");
		}
		System.out.println();
		Iterator<Integer> it1 = sc.getAllocations().iterator();
		System.out.println("Alloted vertices are ");
		while(it1.hasNext()){
			System.out.print(it1.next()+" ");
		}
		System.out.println();
		System.out.println("--------------------------");
		
	}
	public static void printSCStatus() {
		// TODO Auto-generated method stub
		Iterator<Integer> iterator = PreProcessor.SC.keySet().iterator();
		while(iterator.hasNext()){
			Integer scIndex = iterator.next();
			System.out.println("Details About Service Center "+scIndex);
			ServiceCenter sc = PreProcessor.SC.get(scIndex);
			System.out.println("Color is "+sc.getColor());
			System.out.println("Curr Capacity is "+sc.getCurrCapacity());
			System.out.println("Penalty is "+sc.getPenalty());
			Iterator<Integer> it = sc.getBoundary().keySet().iterator();
			System.out.println("Boundaries are ");
			while(it.hasNext()){
				System.out.print(it.next()+" ");
			}
			System.out.println();
			Iterator<Integer> it1 = sc.getAllocations().iterator();
			System.out.println("Alloted vertices are ");
			while(it1.hasNext()){
				System.out.print(it1.next()+" ");
			}
			System.out.println();
			
		}
	}
}