package jar;

public abstract class FreeEntity extends Entity {

	FreeEntity(Base b, float size) {
		super(b, size);
	}
	
	/*
	 * This is a placeholder for flying objects and anything else that temporarily leaves the coordinate system.
	 */
	
	protected abstract void onLanding();
}
