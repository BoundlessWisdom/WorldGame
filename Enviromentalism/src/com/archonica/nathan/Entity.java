package com.archonica.nathan;

import com.engine.core.EntityObject;

public class Entity extends EntityObject {
	public static class StatSet {
		private int atk, hp, def, spd, sz;
		public StatSet(){
			atk = hp = def = spd = sz = 0;
		}
		public StatSet(StatSet s) {
			atk = s.atk;
			hp = s.hp;
			def = s.def;
			spd = s.spd;
			sz = s.sz;
		}
		public StatSet(int attack, int healthPoints, int defense, int speed, int size) {
			atk = attack;
			hp = healthPoints;
			def = defense;
			spd = speed;
			sz = size;
		}
		public StatSet Value(){
			return new StatSet(atk, hp, def, spd, sz);
		}
		public int GetAttack(){return atk;}
		public int GetDefense(){return def;}
		public int GetHealth(){return hp;}
		public int GetSpeed(){return spd;}
		public int GetSize(){return sz;}
		public void AttackMod(int i){atk += i;}
		public void DefenseMod(int i){def += i;}
		public void HealthMod(int i){hp += i;}
		public void SpeedMod(int i){spd += i;}
		public void SizeMod(int i){sz += i;}
	}
	public StatSet Stats(){
		return new StatSet();
	}
	public String EntType(){
		return "Base Entity";
	}
	public String toString(){
		return EntType() + "#Stats:{"+Stats().GetAttack()+" Attack, "+Stats().GetDefense()+" Defense, "+Stats().GetHealth()+" HP, Size "+ Stats().GetSize()+", "+ Stats().GetSpeed()+" Speed}#Mass: "+GetMass()+"#Index: "+GetIndex()+"#Current Health: "+GetHealth();
	}
}
