package com.archonica;

public class Interaction extends Location {

	public Interaction(int i, int j) {
		super(i, j);
	}
	
	/*************************************************************************************/
	public int fireSpread;
	public boolean oneWayFireSpread;
	public EDirection whichWayFireSpread;  //TODO: Interaction: Reduce complexity and calculations of Fire calculations.
	
	public boolean canFirePass(EDirection d, float fireSize) {
		boolean canPass = fireSize >= fireSpread;
		return canPass & (oneWayFireSpread ? d == whichWayFireSpread : true);
	}

}
