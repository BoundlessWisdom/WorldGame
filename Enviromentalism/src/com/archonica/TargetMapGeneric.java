package com.archonica;

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

	public boolean contains(Tile t) {
		return (baseMap.contains(t) || pMap.contains(t)) && !nMap.contains(t);
	}

}
