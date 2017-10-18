package dm.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class FPNode {
	
	
	private String value;
	private int count;
	private FPNode parent;
	private HashMap<String, FPNode> children;
	private String uuid;
	
	public FPNode(FPNode parent, String value) {
	
		this.value = value;
		count=1;
		this.parent = parent;
		children = new HashMap<String, FPNode>();
		this.uuid = UUID.randomUUID().toString();
		
	}
	
	public FPNode(FPNode parent, String value, int count) {
		
		this.value = value;
		this.count=count;
		this.parent = parent;
		children = new HashMap<String, FPNode>();
		
	}
	
	public Map addChild(String name, FPNode object) {
		children.put(name, object);
		return children;
		
	}
	
	public HashMap getChildren() {
		return children;
	}
	
	public String getValue() {
		return value;
	}
	
	public Set getChildrenKeys() {
		
		return this.children.keySet();
	}
	
	public FPNode getChildNode(String key) {
		return (FPNode) this.children.get(key);
		
	}

	public void incrementCount() {
		this.count ++;
		
	}
	
public FPNode getParent() {
	return parent;
}

public int getCount() {
	return count;
}

public boolean containsChild(String name) {
	return this.children.containsKey(name);
}
	

public String getUuid() {
	return uuid;
}
/*	@Override
	public String toString() {
		String toPrint = "";
		
		if(this.children.isEmpty())
			return "(leaf) " + this.value + "[" + this.count + "]";
		else
			children.forEach((k,v) -> System.out.println("node : "+k+"["+this.count+"]"+"->"+v.toString()));
		
		return "";
		
	}*/
	
public void setCount(int count) {
	this.count = count;
}

public void setChildren(HashMap<String, FPNode> children) {
	this.children = new HashMap<>(children);
}

public String getParentValue(){
	return this.parent.getValue();
}

@Override
public boolean equals(Object obj) {
	boolean val = this.uuid.equals(((FPNode)obj).getUuid());
/*	if (val)
		System.out.println("Val for "+this.getValue() +" and "+((FPNode)obj).getValue());
*/	return val;
}

}
