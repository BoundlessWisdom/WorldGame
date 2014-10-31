package ui;

public class Menu {
	//TODO: Interface: Refer to GoHarsha to acquire Texture support.
	
//	public static final Texture background;
	
	public static final Menu noMenu				= new Menu(null);
	public static final Menu rootMenu			= new Menu("");
	public static final Menu multiplayerMenu	= new Menu("");
	public static final Menu newWorldMenu		= new Menu("");
	public static final Menu menuOptions		= new Menu("");
	public static final Menu ingameOptions		= new Menu("");
	
	
	
	public Menu(String backgroundURL) {
//		background = Texture.loadTexture(backgroundURL);
	}
	
	
	
	
	public final Button[] buttons = new Button[16];
	
	public static final Button backButton0	= new ChangeMenuButton(newWorldMenu, 0, 0, 0, 0, 0).setVariable(rootMenu);
	public static final Button backButton1	= new ChangeMenuButton(multiplayerMenu, 0, 0, 0, 0, 0).setVariable(rootMenu);
	public static final Button backButton2	= new ChangeMenuButton(menuOptions, 0, 0, 0, 0, 0).setVariable(rootMenu);
	public static final Button backButton3	= new ChangeMenuButton(ingameOptions, 0, 0, 0, 0, 0).setVariable(noMenu);
	
	public static final Button quitButton0	= new QuitButton(rootMenu, 0, 0, 0, 0, 0);
	public static final Button quitButton1	= new ChangeMenuButton(ingameOptions, 1, 0, 0, 0, 0).setVariable(noMenu);
	
}
