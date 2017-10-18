package dm.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class FPTree {

	public FPNode header;
	public HashMap<String, List<FPNode>> linkedListMap;
	
	public FPTree() {

		this.header = new FPNode(null, "null",0);
		linkedListMap = new HashMap<>();
		
	}
	
	public FPNode addTransaction(List<String> items) {
		
		FPNode tempHeader = this.header;
		for (String string : items) {			
			
			if(tempHeader.containsChild(string)) {
				FPNode childNode = tempHeader.getChildNode(string);
				childNode.incrementCount();
				updateCountInLinkedList(childNode, string);
				//tempHeader.incrementCount();				
			}
			else {
				FPNode childNode = new FPNode(tempHeader, string);
				//System.out.println("printing uuid of each child : "+childNode.getUuid());
				tempHeader.addChild(string, childNode);	
				addNodeToLinkedList(childNode);
			}
			
			tempHeader = tempHeader.getChildNode(string);
		}
				
		//this.header = tempHeader;
		return this.header;
		
	}

	private void updateCountInLinkedList(FPNode childNode, String string) {
		List<FPNode> linkedList = this.linkedListMap.get(string);
		for (FPNode fpNode : linkedList) {
			if(fpNode.equals(childNode)) {
				fpNode.setCount(childNode.getCount());
				fpNode.setChildren(childNode.getChildren());
				//System.out.println("Count of "+fpNode.getValue()+" is : "+fpNode.getCount());

				break;
			}
				
		}
		
	}

	private void addNodeToLinkedList(FPNode childNode) {
		String value = childNode.getValue();
		List<FPNode> listOfNodes;
		if(this.linkedListMap.containsKey(value)) {
			listOfNodes= linkedListMap.get(value);			
		}
		else {
			listOfNodes = new ArrayList<>();
		}
		
		listOfNodes.add(childNode);
		linkedListMap.remove(value);;
		linkedListMap.put(value, listOfNodes);
	}
	
	public HashMap<String, List<FPNode>> getLinkedListMap() {
		return linkedListMap;
	}
	
/*	@Override
	public String toString() {
	
		String print ="";
		
		if(header.getChildren().isEmpty()) {
			print = "null (Header)";
		}
		else {
			header.getChildren().forEach((k,v) -> System.out.println("node : "+k+"->"+v.toString()));
		}
		return print;
	}*/
	
}
