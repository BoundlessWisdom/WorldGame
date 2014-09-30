package jar;

public abstract class Entity {
protected Base base;

public final float size;

World w;
public int x, z;

Entity(Base b, float size) {
	this.w = Game.activeWorld;
	this.base = b;
	this.size = size;
}

public Entity place(int x, int z) {
	this.x = x;
	this.z = z;
	return this;
}

public boolean move() {
	return false;   //Write this up here.
}

public void die() {   //Write this up here.

}

public int getTeamID() {
	return base.getTeamID();
}

}
