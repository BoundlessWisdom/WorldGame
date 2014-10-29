package com.game;

import com.engine.core.EntityObject;
import com.engine.core.GameObject;

public class Monkey extends EntityObject 
{	
	public Monkey(GameObject gameObj, double mass, String name) {
		super(gameObj, mass, name);
		fixElastic(0.2);
	}

	/*@Override
	public void update(float delta) {
		super.update(delta);
		if(Keyboard.isKeyDown(Keyboard.KEY_F))
		{
			Game.getPhysicsEngine().force(this, new Vector3f(1,0,0));
			//System.out.println("pressing");
			//getPhysicsEngine().force(crack, new Vector3f(-1,0,0));
		}
	}*/
	
	@Override
	public double GetMu() {
		return .5;
	}

	@Override
	public double GetElasticConstant() {
		return elasticConstant;
	}

	@Override
	public double GetDragConstant() {
		return 0;
	}

	@Override
	public double GetCrossSectionArea() {
		return 0;
	}	
}
