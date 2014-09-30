package jar;

public abstract class Location {
	public int x;
	public int y;
	
	public Location(int i, int j) {
		x = i;  y = j;
	}
	
	public abstract int[] translate(int sx, int sz);
}
