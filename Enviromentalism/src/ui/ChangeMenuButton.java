package ui;

import com.archonica.Game;

public class ChangeMenuButton extends Button {

	private Menu changeTo;
	
	public ChangeMenuButton(Menu menu, int index, String name, int x,
			int xlength, int y, int ylength) {
		super(menu, index, name, x, xlength, y, ylength);
	}
	
	public void setVariable(Object par) {
		if (!(par instanceof Menu)) {
			throw new IllegalArgumentException("Variable passed in was not a Menu");
		}
		changeTo = (Menu) par;
	}

	public void function() {
		Game.activeMenu = changeTo;
	}

}
