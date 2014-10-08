package jar;

public abstract class Cast {
Caster caster;

public Cast(Caster caster) {
	this.caster = caster;
	query();
}

protected abstract void query();

protected abstract boolean activate();

}
