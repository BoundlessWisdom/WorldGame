package com.archonica.objects;

import com.engine.core.Vector3f;
import com.engine.core.EntityObject;

public class Projectile extends EntityObject {
	public Vector3f v0;

	protected void lockCamera() {
		cameraLock = GetTransform().GetPos();
	}
}
