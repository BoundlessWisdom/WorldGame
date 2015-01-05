package com.archonica.objects;

import com.archonica.Updateable;
import com.archonica.sparks.SparkProjectile;
import com.engine.core.Vector2f;
import com.engine.core.Vector3f;
import com.game.EntityObject;

public abstract class Projectile extends EntityObject implements Updateable {
	public Vector3f v0;  //?
	
	public final float speed;
	protected Vector2f direction;
	
	protected Projectile(float spd) {
		this.speed = spd;
	}
	
	SparkProjectile source;
	
	public abstract void form(SparkProjectile spark);
	
	public void launch(Vector2f dir) {
		this.direction = dir.Normalized();
	}
	
	public abstract void fly(long dtime);
	
	public void update(long dtime) {
		move(direction.Mul(speed * dtime));
		fly(dtime);
		detectCollision();
	}
	
	private void detectCollision() {
		
	}

	protected void lockCamera() {
		cameraLock = GetTransform().GetPos();
	}
}
