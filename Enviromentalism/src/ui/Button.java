package ui;

import org.lwjgl.input.Mouse;

public abstract class Button {
	//public final Texture texture;
	
	public final int x;
	public final int xlength;
	
	public final int y;
	public final int ylength;
	
	public Button(Menu menu, int x, int xlength, int y, int ylength) {
		menu.buttons.add(this);
		this.x = x;
		this.xlength = xlength;
		this.y = y;
		this.ylength = ylength;
	}
	
	public boolean hover()
	{
		return (Mouse.getX() > x && Mouse.getX() < (x + xlength) && Mouse.getY() < y && Mouse.getY() > (y + ylength));
	}
	
	public abstract Button setVariable(Object par);
	
/*	public Button setBackground(Texture t) {
		texture = t;
		return this;
	}*/
	
	public abstract void function();

}
