package com.archonica;

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
	
	public boolean contains(Tile t) {
		return t.x >= xLowerLimit && t.x <= xUpperLimit && t.z >= zLowerLimit && t.z <= zUpperLimit;
	}
}
