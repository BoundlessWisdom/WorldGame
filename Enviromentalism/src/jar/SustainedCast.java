package jar;

public abstract class SustainedCast extends Cast implements Updateable {

	public SustainedCast(Caster caster) {
		super(caster);
	}
	
	public abstract void terminate();

}
