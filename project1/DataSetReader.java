package dm.project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSetReader {

	
	FileReader fr = null;
	String fileName = "";
	Map<String, List<String>> transactionMap;
	Map<String, Integer> mapOfcounts;

	
	public DataSetReader(String filename) throws Exception {
		this.fileName = filename;
		this.fr = new FileReader(new File(this.fileName));
		transactionMap = new HashMap<>();
		mapOfcounts = new HashMap<>();
		fr.close();
	}
	
	public void generateDataSet() throws Exception {
		fr = new FileReader(new File(fileName));
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		while(( line =br.readLine()) != null){
			
			String[] splitLine = line.split(" ");
			String transaction = splitLine[0];
			String item = splitLine[1];
			
			addToTransactionMap(transaction, item);
			
			addToMapOfCounts(item);
			

			
		}


		
	}

	private void addToMapOfCounts(String item) {
		int count = 0;

		if(mapOfcounts.containsKey(item)) {
			count = mapOfcounts.get(item);
		}
		mapOfcounts.put(item, count+1);
	}
	
	private void addToTransactionMap(String transaction, String item) {
		List<String> tempList;
		
		
		if(transactionMap.containsKey(transaction)) {
			tempList = transactionMap.get(transaction);
		}
		else {
			tempList = new ArrayList<>();
		}
		
		tempList.add(item);
		transactionMap.remove(transaction);
		transactionMap.put(transaction, tempList);
	}

/*	private void addToTransactionMap(String transaction, String item) {
		if(transactionMap.get(transaction)==null) {
			transactionMap.put(transaction, new ArrayList<>());
		}
		
		List<String> tempList = transactionMap.get(transaction);
		tempList.add(item);
		transactionMap.remove(transaction);
		transactionMap.put(transaction, tempList);
	}*/
	
	
	public Map<String, Integer> getMapOfcounts() {
		return mapOfcounts;
	}
	
	public Map<String, List<String>> getTransactionMap() {
		return transactionMap;
	}
	
	public List<String> getItemsToRemove(int support) {
		
		List<String> itemsToRemove = new ArrayList<>();
		//System.out.println("Adding to item to remove");
		this.mapOfcounts.forEach((k,v) -> {if(v<support) {itemsToRemove.add(k);/*System.out.println("Eliminated " + k + " with count " + v);*/}});

		/*		System.out.println("Removing from map of counts");
		itemsToRemove.forEach((v) -> this.mapOfcounts.remove(v));
*/
		
		return itemsToRemove;
		
/*		System.out.println("Removing from transactions");
		this.transactionMap.forEach((k,v) -> v.removeAll(itemsToRemove));
*/		
	}
	
}
