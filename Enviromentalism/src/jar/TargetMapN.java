package jar;

public class TargetMapN implements TargetMapInterface {
	TargetMapRectangle baseMap;
	TargetMapList nMap;
	
	public TargetMapN(int i, int j, int k, int l) {
		baseMap = new TargetMapRectangle(i, j, k, l);
	}
	
	public void map(TargetMapList tml) {
		nMap = tml;
	}
	
	public boolean contains(Location l) {
		return baseMap.contains(l) && !nMap.contains(l);
	}
}
