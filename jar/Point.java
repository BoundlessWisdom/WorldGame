package jar;

public class Point extends Location {
	float y;

	public Point(int i, int j, int height) {
		super(i, j);
		this.y = height;
	}
	
	public int[] translate(int sx, int sz) {
		return translatePoint(sx, sz);
	}
	
	public static int[] translatePoint(int sx, int sz) {
		int[] translated = {sx, sz};
		return translated;
	}
}
