package com.game;

import com.archonica.World;
import com.engine.core.CoreEngine;

import ui.Menu;

public class Main {
	
	public static Menu activeMenu = Menu.rootMenu;
	
	public static void main(String[] args) {
		activeWorld = new World(20);
		CoreEngine.CreateEngine(800, 600, 60, new Archonica());
		CoreEngine.CreateWindow("Archonica");
		CoreEngine.Start();
	}
	
	public static World activeWorld;

	/****************************************************************************************
	 * 									IMPORTANT NOTICE									*
	 * 																						*
	 * 										   Nesh.										*
	 ****************************************************************************************/
	
	//Interface list:
	//		TargetMapInterface
	//		Updateable
}
