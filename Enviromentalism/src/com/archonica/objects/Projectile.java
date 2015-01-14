package com.archonica.objects;

import com.archonica.Updateable;
import com.archonica.sparks.SparkProjectile;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;
import com.game.EntityObject;

public abstract class Projectile extends EntityObject implements Updateable {
	public final float speed;
	
	//Should be a unit vector.
	protected Vector2f direction;
	
	protected Projectile(float spd) {
		this.speed = spd;
	}
	
	//The spark which created this projectile.
	SparkProjectile source;
	
	//On hitting ground.
	protected void onLanding() {}
	
	//On collision, passes on modifiers.
	protected abstract void onCollision(EntityObject otherObject);
	
	//Formation "animation"
	public abstract void form(SparkProjectile spark);
	
	public void launch(Vector2f dir) {
		this.direction = dir.Normalized();
		Vector3f vel = new Vector3f(dir.GetX(), 0, dir.GetY());
		vel.Mul(speed);
		this.SetVelocity(vel);
	}
	
	//What it does while it's flying.
	public abstract void fly(long dtime);
	
	public void update(long dtime) {
	//	move(direction.Mul(speed * dtime)); //Movement
		fly(dtime);							//Other effects
		detectCollision();
	}
	
	private void detectCollision() {
		
	}

	protected void lockCamera() {
		cameraLock = GetTransform().GetPos();
	}
}
