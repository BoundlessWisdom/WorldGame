package pizazz;

public abstract class Base {
protected Entity entity;

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

public abstract void update();

}
