package pizazz;

public class ArchonBase extends CasterBase {
	
	//spawn world, region coords, exact coords
	public ArchonBase(int size) {
		this.entity = new ArchonEntity(this, size);
	}

	public void update() {
		
	}
}
