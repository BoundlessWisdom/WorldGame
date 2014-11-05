package ui;

import com.game.Archonica;

public class ChangeMenuButton extends Button {

	private Menu changeTo;
	
	public ChangeMenuButton(Menu menu, int x, int xlength, int y, int ylength) {
		super(menu, x, xlength, y, ylength);
	}
	
	public Button setVariable(Object par) {
		if (!(par instanceof Menu)) {
			throw new IllegalArgumentException("Variable passed in was not a Menu");
		}
		changeTo = (Menu) par;
		return this;
	}

	public void function() {
		//Archonica.activeMenu = changeTo;
	}

}
