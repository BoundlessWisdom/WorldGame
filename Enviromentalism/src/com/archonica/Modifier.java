package com.archonica;

public abstract class Modifier{
	private int sp, sr, tgn, hp;
	public abstract void additionalEffect();
	public Modifier(int speed, int strength, int toughness, int health){
		sp = speed;
		sr = strength;
		tgn = toughness;
		hp = health;
	}
	public int getSpeedChange(){return sp;}
	public int getStrengthChange(){return sr;}
	public int getToughnessChange(){return tgn;}
	public int getHealthChange(){return hp;}
}
