package jar;

import static jar.ECastType.*;

public class ControlBar {
	ECastType type;
	
	public static ControlBar[] ebars = new ControlBar[16];
	
	public static ControlBar air = new ControlBar(AIR);
	public static ControlBar earth = new ControlBar(EARTH);
	public static ControlBar fire = new ControlBar(FIRE);
	public static ControlBar water = new ControlBar(WATER);
	
	public ControlBar(ECastType type) {
		this.type = type;
	}
}
