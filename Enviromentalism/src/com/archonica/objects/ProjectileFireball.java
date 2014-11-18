package com.archonica.objects;

import com.archonica.Tile;

public class ProjectileFireball extends Projectile {
	Tile origin;
	Tile target;
	
	protected void onLanding() {
//		Elemental.fire.entity.place(target.x, target.z);
	}
}
