package jar;

public abstract class Base {
protected Entity entity;

public Base(float mh, int a) {
	this.maxHealth = mh;
	this.health = maxHealth;
	this.attack = a;
}

public Base(float mh, float h, int a) {
	this(mh, a);
	this.health = h;
}

public final float maxHealth;
public float health;
protected int attack;

public void attack() {
	
}

public void die() {   //Write this up here.  Fill in more.
	entity.die();
}

/************************************************************************/

private int teamID = -1;

public void joinTeam(int id) {
	if (teamID == -1)
		teamID = id;
}

public int getTeamID() {
	return teamID;
}

public abstract void update(long dtime);

}
