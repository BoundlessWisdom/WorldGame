package jar;

public class Effect {
	@SuppressWarnings("unused")
	private CasterBase caster;
	
	public boolean expired;
	public final int index;
	
	public Effect(int i) {
		index = i;
	}
	
	public void casted(CasterBase c) {
		caster = c;
	}
	
	void expire() {
		expired = true;
	}
}