package debug2D.archonica;

import com.archonica.ETerrain;

public abstract class TerrainBody extends TargetMapGeneric /*implements Updateable*/ {

	public TerrainBody(int i, int j, int k, int l) { super(i, j, k, l); }
	
	ETerrain terrain;

}
