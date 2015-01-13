package com.archonica.objects;

import com.archonica.Tile;
import com.archonica.sparks.SparkProjectile;
import com.engine.rendering.Material;
import com.engine.rendering.Mesh;
import com.engine.rendering.Texture;
import com.game.EntityObject;

public class ProjectileFireball extends Projectile {
	static{
		class_mesh = new Mesh("fireball_placeholder.obj");
	}
	protected ProjectileFireball(float spd) { 
		super(spd); 
		
		try {
			AddMaterial(new Material(new Texture("menubg.png"), 1, 8,
					new Texture("menubg.png"), new Texture("menubg.png"), 0.03f, -0.5f));
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	Tile origin;
	Tile target;
	
	private float age;
	
	protected void onLanding() {
		//Elemental.fire.entity.place(target.x, target.z);
	}
	
	protected void onCollision(EntityObject otherObject) {
	}

	public void respond() {
		//Responds to water, fire- and power-enhancing modifiers, and speed modifiers.
		//More?
	}

	public void form(SparkProjectile spark) {
		//TODO: Determine intricacy of projectile formation.
		//Consider using semi-transparent particles for fire.
		//This would make the formation of a fireball much simpler.
	}
	
	public void fly(long dtime) {
		if (age >= 0.1f) {
			age += dtime - 0.1f;
			
			if (direction.zeroLength())
				for (int i = 0; i < 10; i++) {
					//Generate flame particles randomly around fireball.
					/*
					 * Particles generate both inside and outside of the fireball.
					 */
					
					
					//Flame particles have random expiration time within a range.
					//Also, four different particles to choose from, different colors.
				}
			else
				for (int i = 0; i < 20; i++) {
					//Generate flame particles as before.
				}
		}
		else {
			age += dtime;
		}
	}
}
