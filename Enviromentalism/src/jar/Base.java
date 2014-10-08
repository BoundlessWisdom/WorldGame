package jar;

public abstract class Base implements Updateable {

public Base(float mh, int a) {
	this.maxHealth = mh;
	this.attack = a;
}

public final float maxHealth;
public final int attack;

}
