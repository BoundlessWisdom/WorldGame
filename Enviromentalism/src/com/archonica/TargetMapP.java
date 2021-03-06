package com.archonica;

public class TargetMapP implements TargetMapInterface {
	TargetMapRectangle baseMap;
	TargetMapList pMap;
	
	public TargetMapP(int i, int j, int k, int l) {
		baseMap = new TargetMapRectangle(i, j, k, l);
	}
	
	public void map(TargetMapList tml) {
		pMap = tml;
	}
	
	public boolean contains(Tile t) {
		return baseMap.contains(t) || pMap.contains(t);
	}
}
