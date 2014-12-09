package com.archonica;

import java.util.ArrayList;

public class TargetMapList implements TargetMapInterface {
	ArrayList<Tile> targetMap;
	
	public TargetMapList(ArrayList<Tile> a) {
		targetMap = a;
	}
	
	public TargetMapList() {
		targetMap = new ArrayList<Tile>();
	}
	
	public void add(Tile ... t) {
		for (int i = 0; i < t.length; i++) {
			targetMap.add(t[i]);
		}
	}
	
	public boolean contains(Tile t) {
		for (int i = 0; i < targetMap.size(); i++) {
			if (targetMap.get(i).equals(t))
				return true;
		}
		return false;
	}
}
