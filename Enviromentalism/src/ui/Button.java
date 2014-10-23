package ui;

public abstract class Button {
	public final String name;

	public final int x;
	public final int xlength;
	
	public final int y;
	public final int ylength;
	
	public Button(Menu menu, int index, String name, int x, int xlength, int y, int ylength) {
		menu.buttons[index] = this;
		this.name = name;
		this.x = x;
		this.xlength = xlength;
		this.y = y;
		this.ylength = ylength;
	}
	
	public void setVariable(Object o) {}
	
	public abstract void function();

}
//Your face looks like a monkey.