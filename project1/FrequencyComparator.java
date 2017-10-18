package dm.project1;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FrequencyComparator implements Comparator<String>{

	Map<String, Integer> mapOfcounts;
	
	public FrequencyComparator(Map<String, Integer> mapOfcounts) {
		this.mapOfcounts = mapOfcounts;
	}
	
	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		
		if(mapOfcounts.get(o1) > mapOfcounts.get(o2))
			return -1;
		else if(mapOfcounts.get(o1) < mapOfcounts.get(o2))
			return 1;
		return o1.compareTo(o2)*-1;
		
	}


}
