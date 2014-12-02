package ui;

import com.engine.core.CoreEngine;

public class QuitButton extends Button {

	public QuitButton(Menu menu, int index, int x, int xlength, int y, int ylength) {
		super(menu, x, xlength, y, ylength);
	}
	
	public Button setVariable(Object par) {
		return this;
	}

	public void function() {
		CoreEngine.Stop();
	}

}
