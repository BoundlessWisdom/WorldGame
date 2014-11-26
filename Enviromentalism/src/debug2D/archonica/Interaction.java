package debug2D.archonica;

public class Interaction extends Location {

	public Interaction(int i, int j) {
		super(i, j);
	}

	public void print(boolean vertical) {
		if (vertical)
			System.out.print("| ");
		else
			System.out.print("- - ");
	}
	

	
	/*************************************************************************************/
//	public int fireSpread;
//	public boolean oneWayFireSpread;
	
//	public EDirection whichWayFireSpread;  //TODO: Interaction: Reduce complexity and calculations of Fire calculations.
	
//	public boolean canFirePass(EDirection d, float fireSize) {
//		boolean canPass = fireSize >= fireSpread;
//		return canPass & (oneWayFireSpread ? d == whichWayFireSpread : true);
//	}

}