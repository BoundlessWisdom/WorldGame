package ui;

public class OptionButtonBoolean extends Button {
	boolean option;

	public OptionButtonBoolean(Menu menu, int index, int x, int xlength, int y,
			int ylength) {
		super(menu, index, x, xlength, y, ylength);
	}
	
	public Button setVariable(Object par) {
		if (!(par instanceof Boolean))
			throw new IllegalArgumentException("Boolean button isn't applying to boolean.");
		
		option = (boolean) par;
		return this;
	}
	
	public void function() {
		option = !option;
	}

}
