package ui;

public class OptionButtonFloat extends Button {
	float option;

	public OptionButtonFloat(Menu menu, int index, int x, int xlength, int y,
			int ylength) {
		super(menu, x, xlength, y, ylength);
	}

	public Button setVariable(Object par) {
		if (!(par instanceof Float))
			throw new IllegalArgumentException("Float button isn't applying to float.");
		
		option = (float) par;
		return this;
	}

	public void function() {
	}

}
