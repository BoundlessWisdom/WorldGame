package com.archonica;

public class TargetMapN implements TargetMapInterface {
	
	TargetMapRectangle baseMap;
	TargetMapList nMap;
	
	public TargetMapN(int i, int j, int k, int l) {
		baseMap = new TargetMapRectangle(i, j, k, l);
	}
	
	public void map(TargetMapList tml) {
		nMap = tml;
	}
	
	public boolean contains(Tile t) {
		return baseMap.contains(t) && !nMap.contains(t);
	}
}
