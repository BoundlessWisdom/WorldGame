package debug2D.archonica;

import com.archonica.TargetMapInterface;

public class TargetMapRectangle implements TargetMapInterface {
	public int xUpperLimit;
	public int xLowerLimit;
	public int zUpperLimit;
	public int zLowerLimit;
	
	public TargetMapRectangle(int i, int j, int k, int l) {
		xUpperLimit = i;  xLowerLimit = j;  zUpperLimit = k;  zLowerLimit = l;
	}
	
	public TargetMapRectangle(int i, int j) {
		this(i, i, j, j);
	}
	
	public boolean contains(Location l) {
		return l.x >= xLowerLimit && l.x <= xUpperLimit && l.z >= zLowerLimit && l.z <= zUpperLimit;
	}

	@Override
	public boolean contains(com.archonica.Location l) {
		return false;
	}
}
