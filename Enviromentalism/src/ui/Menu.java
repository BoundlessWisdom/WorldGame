package ui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.engine.components.Camera;
import com.engine.core.CoreEngine;
import com.engine.core.Input;
import com.engine.core.GameObject;
import com.engine.core.Quaternion;
import com.engine.core.Vector3f;
import com.game.ArchonicaApp;
import com.game.CameraInfo;

import static com.engine.core.Input.*;

@SuppressWarnings("unused")
public class Menu extends GameObject {

	// TODO: Interface: Refer to GoHarsha to acquire Texture support.

	// public static final Texture background;

	public static boolean OneIsRunning = true;
	public boolean IsCompiled = false;

	public static final Menu noMenu = new Menu("");
	public static final Menu rootMenu = new Menu("res/textures/menubg.png");
	public static final Menu multiplayerMenu = new Menu("");
	public static final Menu newWorldMenu = new Menu("");
	public static final Menu menuOptions = new Menu("");
	public static final Menu ingameOptions = new Menu("");

	public final ArrayList<Button> buttons = new ArrayList<Button>();

	public static final Button backButton0 = new ChangeMenuButton(newWorldMenu,
			0, 0, 0, 0).setVariable(rootMenu);
	public static final Button backButton1 = new ChangeMenuButton(
			multiplayerMenu, 0, 0, 0, 0).setVariable(rootMenu);
	public static final Button backButton2 = new ChangeMenuButton(menuOptions,
			0, 0, 0, 0).setVariable(rootMenu);
	public static final Button backButton3 = new ChangeMenuButton(
			ingameOptions, 0, 0, 0, 0).setVariable(noMenu);

	public static final Button quitButton0 = new QuitButton(rootMenu, 0, 0,
			100, 0, 100);
	public static final Button quitButton1 = new ChangeMenuButton(
			ingameOptions, 1, 0, 0, 0).setVariable(noMenu);

	public Texture background = null;

	public Menu(String backgroundURL) {
		background = null;
		try {
			background = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("res/textures/menubg.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Compile() {
		if (IsCompiled)
			return;

		for (Button button : buttons) {
			if (!button.IsCompiled)
				button.Compile();

			AddChild(button);
		}

		IsCompiled = true;
	}

	@Override
	public void Update(float delta) // Handles mouse clicks
	{
		for (Button b : buttons) {
			if (Input.GetMouse(0)) {
				if (b.Hover()) {
					b.function();
				}
			}
		}
	}

	public void LoadMenu(ArchonicaApp app, GameObject root) {
		
		Camera camera = CoreEngine.GetRenderingEngine().GetMainCamera();
		
		if (OneIsRunning) 
		{
			CameraInfo ci = app.DifCameraInfo.get("Game");
			camera.GetTransform().SetPos(ci.pos);
			camera.GetTransform().SetRot(ci.rot);
			app.CanMoveCamera(true);
			root.SetChildren(0);
			OneIsRunning = false;
			return;
		}
		
		CameraInfo ci = app.DifCameraInfo.get("Menu");
		app.CanMoveCamera(false);
		app.DifCameraInfo.get("Game").Set(camera.GetTransform().GetPos(), camera.GetTransform().GetRot());
		camera.GetTransform().SetPos(ci.pos);
		camera.GetTransform().SetRot(ci.rot);
		root.SetChildren(1);
		Mouse.setGrabbed(false);
		OneIsRunning = true;
		return;
	}

	public static void LoadMenu(int KeyID, ArchonicaApp app, GameObject root) 
	{
		switch (KeyID) 
		{
		case KEY_P:
			rootMenu.LoadMenu(app, root);
			break;
		}
	}
}
