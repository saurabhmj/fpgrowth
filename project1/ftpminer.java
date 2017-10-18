package dm.project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

public class ftpminer {
	
	private static final String itemsetIdentifier = "_";
	int minsup;
	double minconf;
	HashMap<String, Integer> frequentItemSets;
	HashMap<List<String>, Double> frequentItemSetsForAssociations;
	List<AssociationRule> associationRules;
	List<String> test = new ArrayList<>();
	
	public ftpminer(int minsup, double minconf) {
		this.minconf = minconf;
		this.minsup = minsup;
		this.frequentItemSets = new HashMap<>();
		this.frequentItemSetsForAssociations = new HashMap<>();
		this.associationRules = new ArrayList<>();
	}

	public static void main(String[] args) throws Exception {
		
		Date d1 = new Date();
		
		ftpminer fp = new ftpminer(50, 0.95);
		
		
		DataSetReader dsr = new DataSetReader("F:\\Study\\MS\\CSCI_5523_Introduction_To_Data_Mining\\small\\small");
		dsr.generateDataSet();
		//System.out.println("Before elimnation:" + dsr.getMapOfcounts().size());
		
		List<String> itemsToRemove = dsr.getItemsToRemove(fp.minsup);
		//System.out.println("After elimnation:" + dsr.getMapOfcounts().size());
		
		Map<String, Integer> mapOfcounts = dsr.getMapOfcounts();	
		Map<String, List<String>> transactions = dsr.getTransactionMap();
		
				//System.out.println("Removing from map of counts");
		


		FrequencyComparator fc = new FrequencyComparator(mapOfcounts);
		
		for (Entry<String, List<String>> entry	 : transactions.entrySet()) 
			Collections.sort(entry.getValue(), fc);

	
		FPTree fpTree = new FPTree();
		
		//transactions.forEach((k,v) -> fpTree.addTransaction(v)); ---Java 8
		for (Entry<String, List<String>> entry	 : transactions.entrySet()) {
			List<String> transaction = entry.getValue();
			transaction.removeAll(itemsToRemove);
			fpTree.addTransaction(transaction);
		}	

		for (String string : itemsToRemove) {
			mapOfcounts.remove(string);
		}
		
		for (Entry<String, Integer> entry	 : mapOfcounts.entrySet()) {
			fp.frequentItemSets.put(entry.getKey(), entry.getValue());
			
		}

				
		fp.FPGrowth(fpTree);
		
		
		System.out.println(fp.frequentItemSets.size());
		
		
		Date d2 = new Date();
		System.out.println("total time required : " + (d2.getTime() - d1.getTime())/1000.0);
		
		System.out.println("Now finding association rules...");
		
		if(fp.minsup > 20) {
			Date d3 = new Date();
			fp.frequentItemSetsForAssociations = fp.convertFrequentItems(fp.frequentItemSets);
			
			//List<String> test = new ArrayList<>(Arrays.asList("c","f","m"));
			
			System.out.println(fp.frequentItemSetsForAssociations);
			
			for (Entry<List<String>, Double> entry : fp.frequentItemSetsForAssociations.entrySet()) {
				
				if(entry.getKey().size()>1) {
				AssociationRule ar = new AssociationRule(entry.getKey(), entry.getKey(), new ArrayList<>());
				//ar.addAllToLHS(entry.getKey());
				int emptyRules = fp.findAssociationRules(ar);
				if(emptyRules==ar.getAssociation().size()) {
					fp.test.add("a");
					//System.out.println("No association rule found for : "+ar.getAssociation());
					fp.associationRules.add(ar);
					ar.setConfidence(-1);
					
				}
				}
			}
			
			Date d4 = new Date();
			System.out.println("Size of association rules : "+ fp.associationRules.size());
			System.out.println("Test : "+fp.test.size());
			System.out.println("total time required : " + (d4.getTime() - d3.getTime())/1000.0);
			
			
			/*for (AssociationRule ar : fp.associationRules) {
				if(ar.getConfidence()==-1)
					System.out.println("Got one" + ar.getAssociation());
			}*/
			}
	}

	private int findAssociationRules(AssociationRule ar) {
		
		List<AssociationRule> candidates = new ArrayList<>();
		int count=0;
		boolean isRecursion = true;
		
		if(ar.getSizeOfAssociation()==2) {
			AssociationRule candidate1 = new AssociationRule(ar.getAssociation(), new ArrayList<>(Arrays.asList(ar.getAssociation().get(0))), new ArrayList<>(Arrays.asList(ar.getAssociation().get(1))));
			AssociationRule candidate2 = new AssociationRule(ar.getAssociation(), new ArrayList<>(Arrays.asList(ar.getAssociation().get(1))), new ArrayList<>(Arrays.asList(ar.getAssociation().get(0))));
			candidates.add(candidate1);
			candidates.add(candidate2);
			
			isRecursion = false;
			
		}
		else if(ar.getSizeOfLHS()==1) {
			AssociationRule candidate = new AssociationRule(ar.getAssociation(), ar.getLHS(), ar.getRHS());
			candidates.add(candidate);
			
			isRecursion = false;
		}
		
		else {
			
			List<String> LHS = new ArrayList<>(ar.getLHS());
			for (String string : LHS) {
				AssociationRule candidate = new AssociationRule(ar.getAssociation(), ar.getLHS(), ar.getRHS());
				candidate.addToRHS(string);
				candidate.removeFromLHS(string);
				
				candidates.add(candidate);
			}
			
			isRecursion = true;
		}
		
		
		for (AssociationRule associationRule : candidates) {
			
			if(this.associationConfidence(associationRule) > this.minconf) {
				//this.test.add("a");
				this.associationRules.add(associationRule);
				if(isRecursion)
					this.findAssociationRules(associationRule);
			}else {
				count++;
			}
			
		}
		return count;
		
	}

	private double associationConfidence(AssociationRule ar) {
		double confidence = this.frequentItemSetsForAssociations.get(ar.getAssociation())/this.frequentItemSetsForAssociations.get(ar.getLHS());
		ar.setConfidence(confidence);
		return confidence;
	}

	private HashMap<List<String>, Double> convertFrequentItems(HashMap<String, Integer> frequentItemSets2) {
		
		HashMap<List<String>, Double> frequentItemSetsForAssociations = new HashMap<>();
		
		for (Entry<String, Integer> entry : frequentItemSets2.entrySet()) {
			String[] split = entry.getKey().split(itemsetIdentifier);
			//if(split.length>1) {
			List<String> orderedItems = new ArrayList<>(Arrays.asList(split));
			Collections.sort(orderedItems);
			frequentItemSetsForAssociations.put(orderedItems, Double.valueOf(entry.getValue()));
			//}
		}

		
		return frequentItemSetsForAssociations;
	}

	private void FPGrowth(FPTree fpTree) {
		HashMap<String, List<FPNode>> linkedListMap = fpTree.getLinkedListMap();
		
		for (Entry<String, List<FPNode>> entry : linkedListMap.entrySet()) {
			this.mineProjectedDB(entry.getValue(), entry.getKey());
						
			
		}
		
	}

	private void mineProjectedDB(List<FPNode> fpNodes, String predecessor) {
		List<List<String>> transactions = new ArrayList<>();
		HashMap<String, Integer> mapOfCounts = new HashMap<>();
		for (FPNode fpNode : fpNodes) {
			FPNode original = fpNode;
			int countOfnode = fpNode.getCount();
			while(countOfnode>0) {
				countOfnode--;
				fpNode = original;
				List<String> tracer = new ArrayList<>();
				
				while(!"null".equals(fpNode.getParent().getValue())) {
					fpNode = fpNode.getParent();
					String value = fpNode.getValue();
					tracer.add(value);
					int defaultValue=0;
				
					if(mapOfCounts.containsKey(value)) {
						defaultValue=mapOfCounts.get(value);
					}
						mapOfCounts.put(value, defaultValue+1);
				}
				
				if(!tracer.isEmpty())
				transactions.add(tracer);
			}	
		}
		
		//System.out.println("map of counts" + mapOfCounts);
		transactions = this.eliminateLowSupportItems(mapOfCounts, transactions, this.minsup);
		
		for (Entry<String, Integer> entry : mapOfCounts.entrySet()) {
			this.frequentItemSets.put(predecessor.concat(itemsetIdentifier).concat(entry.getKey()),	entry.getValue());
		}

		//System.out.println(transactions.isEmpty());
			
		if(transactions.isEmpty())
		return;
		
		else {
			FPTree projectedTree = this.generateFPTree(transactions);
			HashMap<String, List<FPNode>> linkedListMap = projectedTree.getLinkedListMap();
			for (Entry<String, List<FPNode>> entry : linkedListMap.entrySet()) {
				this.mineProjectedDB(entry.getValue(), predecessor.concat(itemsetIdentifier).concat(entry.getKey()));
			}
			
		}
		
	}

	private FPTree generateFPTree(List<List<String>> transactions) {
		FPTree fpTree = new FPTree();
		
		
		for (List<String> entry	 : transactions) {
			fpTree.addTransaction(entry);
		}
		
		
		return fpTree;
	}

	private List<List<String>> eliminateLowSupportItems(HashMap<String, Integer> mapOfCounts, List<List<String>> transactions,
			int minsup2) {
		
		
		List<String> itemsToRemove = new ArrayList<>();

		
		for (Entry<String, Integer> entry : mapOfCounts.entrySet()) {
			if(entry.getValue() < minsup2) {
				itemsToRemove.add(entry.getKey());
			}	
		}
		
		//ListIterator<List<String>> transaction_itr = transactions.listIterator();
		
		for (ListIterator<List<String>> transaction_itr = transactions.listIterator(); transaction_itr.hasNext();) {
			List<String> transaction = transaction_itr.next();
			transaction.removeAll(itemsToRemove);
			if(transaction.isEmpty())
				transaction_itr.remove();
			
		}
		
		for (String string : itemsToRemove) {
			mapOfCounts.remove(string);
		}
		
		return transactions;

	}
	
	
}
