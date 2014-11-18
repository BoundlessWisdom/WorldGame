package debug2D.archonica;

public abstract class Location {
	public int x;
	public int z;
	
	public Location(int i, int j) {
		x = i;  z = j;
	}
	
	public abstract char[][] print();
}
