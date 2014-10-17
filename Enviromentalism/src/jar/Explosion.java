package jar;

public class Explosion extends Entity {
	private float radius;  //TODO: Explosion: How to detect what just entered range.
	final float speed;

	Explosion(Base b, float size, Modifier m, float speed) {
		super(b, EntClass.abstractEnt, size, m);
		this.speed = speed;
	}
	
	Explosion(Base b, float size, float speed) {
		this(b, size, null, speed);
	}
	
	Explosion(Base b, float size) {
		this(b, size, 0);
	}
	
	public void update(long dtime) {
		radius += speed * dtime;
	}
	
	public float radius() {
		return radius;
	}
}
