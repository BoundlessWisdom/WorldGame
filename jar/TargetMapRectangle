package jar;

public class TargetMapRectangle implements TargetMapInterface {
	public int xUpperLimit;
	public int xLowerLimit;
	public int yUpperLimit;
	public int yLowerLimit;
	
	public TargetMapRectangle(int i, int j, int k, int l) {
		xUpperLimit = i;  xLowerLimit = j;  yUpperLimit = k;  yLowerLimit = l;
	}
	
	public TargetMapRectangle(int i, int j) {
		this(i, i, j, j);
	}
	
	public boolean contains(Location l) {
		return l.x >= xLowerLimit && l.x <= xUpperLimit && l.y >= yLowerLimit && l.y <= yUpperLimit;
	}
}
