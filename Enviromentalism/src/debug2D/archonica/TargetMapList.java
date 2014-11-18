package debug2D.archonica;

import java.util.ArrayList;

import com.archonica.TargetMapInterface;

public class TargetMapList implements TargetMapInterface {
	ArrayList<Location> targetMap;
	
	public TargetMapList(ArrayList<Location> a) {
		targetMap = a;
	}
	
	public TargetMapList() {
		targetMap = new ArrayList<Location>();
	}
	
	public void add(Location ... l) {
		for (int i = 0; i < l.length; i++) {
			targetMap.add(l[i]);
		}
	}
	
	public boolean contains(Location l) {
		for (int i = 0; i < targetMap.size(); i++) {
			if (targetMap.get(i).equals(l))
				return true;
		}
		return false;
	}

	@Override
	public boolean contains(com.archonica.Location l) {
		return false;
	}
}
