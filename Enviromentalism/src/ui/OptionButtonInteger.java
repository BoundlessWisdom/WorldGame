package ui;

public class OptionButtonInteger extends Button {
	int option;

	public OptionButtonInteger(Menu menu, int index, int x, int xlength, int y,
			int ylength) {
		super(menu, index, x, xlength, y, ylength);
	}

	public Button setVariable(Object par) {
		if (!(par instanceof Integer))
			throw new IllegalArgumentException("Int button isn't applying to int.");
		
		option = (int) par;
		return this;
	}

	public void function() {
	}

}
