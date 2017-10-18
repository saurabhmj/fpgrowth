package dm.project1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssociationRule {

	private List<String> LHS, RHS, association;
	private double confidence;
	
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	
	public List<String> getAssociation() {
		return association;
	}
	
	public AssociationRule(List<String> association, List<String> LHS, List<String> RHS) {
		this.association = new ArrayList<>(association);
		Collections.sort(this.association);
		this.LHS = new ArrayList<>(LHS);
		this.RHS = new ArrayList<>(RHS);
		confidence = 0;
		
	}
	
	public double getConfidence() {
		return confidence;
	}
	
	public void addToLHS(String item) {
		addToList(LHS, item);
	}
	
	public void addToRHS(String item) {
		addToList(RHS, item);
	}
	
	public List<String> getRHS() {
		return RHS;
	}

private void addToList(List list, String item) {
	list.add(item);
	Collections.sort(list);
}

/*public void addAllToLHS(List<String> lHS) {
	LHS = new ArrayList<>(lHS);
}*/

public void removeFromLHS(String item) {
	LHS.remove(item);
}

public int getSizeOfLHS() {
	return LHS.size();
}

public int getSizeOfRHS() {
	return RHS.size();
}

public List<String> getLHS() {
	return LHS;
}

public int getSizeOfAssociation() {
	return association.size();
}
/*public static void main(String[] args) {
	AssociationRule ar = new AssociationRule();
	ar.addToLHS("b");
	ar.addToLHS("a");
	System.out.println(ar.LHS);
}
*/
}
