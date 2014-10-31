package com.game;

import com.engine.rendering.Texture;
import com.engine.components.lighting.BaseLight;
import com.engine.components.lighting.SpotLight;
import com.engine.core.GameInstance;
import com.engine.core.GameObject;
import com.engine.core.Vector3f;
import com.engine.rendering.Attenuation;
import com.engine.rendering.Material;

<<<<<<< HEAD
public class Archonica extends GameInstance{
	
	Monkey monkey = new Monkey(new GameObject(), 10f, "monkey");
	
=======
public class Archonica extends GameInstance {
>>>>>>> branch 'master' of https://github.com/BoundlessWisdom/WorldGame.git
	@Override
	public void Init() {
		super.Init();
		
		BaseLight light = new SpotLight(new Vector3f(0, 1, 0), 0.8f, 
				new Attenuation(0, 0, 1), 0.7f);
		
		GetRootObject().AddComponent(light);
		
		 monkey.AddMaterial(new Material(new Texture("bricks.jpg"), 1, 8,
					new Texture("bricks_normal.jpg"), new Texture("bricks_disp.png"), 0.03f, -0.5f));
		 
		 AddObject(monkey);
	}
	
	@Override
	public void Update(float delta) {
		super.Update(delta);
	}
}
