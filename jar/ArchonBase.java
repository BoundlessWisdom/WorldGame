package jar;

public class ArchonBase extends CasterBase {
	
	public ArchonBase(float mh, int a, int size) {
		super(mh, a);
		this.entity = new ArchonEntity(this, size);
	}

	public void update() {
		
	}

	public void update(long dtime) {
		
	}
}
