package debug2D.archonica;

public class TargetMapGeneric extends TargetMapP {
	TargetMapRectangle baseMap;
	TargetMapList pMap;
	TargetMapList nMap;
	
	public TargetMapGeneric(int i, int j, int k, int l) {
		super(i, j, k, l);
	}
	
	public void addMap(TargetMapList map) {
		if (pMap == null)
			pMap = map;
	}
	
	public void subtractMap(TargetMapList map) {
		if (nMap == null)
			nMap = map;
	}

	public boolean contains(Location l) {
		return (baseMap.contains(l) || pMap.contains(l)) && !nMap.contains(l);
	}

}
