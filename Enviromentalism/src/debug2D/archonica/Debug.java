package debug2D.archonica;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.input.Keyboard.*;

public class Debug {
	
	public static Entity archon;
	static Location[][] worldArray;
	
	//Borders
	//Tiles
	//Print tiles?
	
	public static boolean isTile(int x, int z)
	{
		if ((x % 2) != 0 && (z % 2) != 0)
			return true;
		return false;
	}
	
	public static boolean isPoint(int x, int z)
	{
		if((x % 2) == 0 && (z % 2) ==0)
			return true;
		return false;
	}
	
	public static boolean isInteraction(int x, int z)
	{
		if((x + z) % 2 != 0)
			return true;
		return false;
	}
	
	public static void main(String[] args) throws LWJGLException, IOException 
	{
		worldArray = new Location[9][9];
		init();
		archon = new Entity(null, 1, 1);
		archon.identifier = 'A';
		worldArray[1][1].addEntity(archon);
		
		archon.moveTo(1,  5);
		
		Entity tree = new Entity(null, 0, 0);
		tree.identifier = 'T';
		worldArray[1][3].addEntity(tree);
		
		print();
		
		update();
	}
	
	public static void init() throws LWJGLException {
		Display.create();
		Display.setDisplayMode(new DisplayMode(0, 0)); // Anyone know how to actually hide it?
		Keyboard.create();
		for(int i = 0; i < worldArray.length; i++)
		{
			for(int j = 0; j < worldArray[i].length; j++)
			{
				if(isPoint(i, j))
					worldArray[i][j] = new Point(i, j, 0);
				else if(isTile(i, j))
					worldArray[i][j] = new Tile(i, j);
				else if(isInteraction(i, j))
					worldArray[i][j] = new Interaction(i, j);
			}
		}
	}
	
	public static void update() throws IOException
	{
		while(!Keyboard.isKeyDown(KEY_ESCAPE))
		{
			if(Keyboard.isKeyDown(KEY_DOWN))
			{
			//	System.out.printf("DOWN!\n");
				if(archon.x + 2 < worldArray[0].length)
					archon.moveTo(archon.x + 2,  archon.z);
			}
			
			else if(Keyboard.isKeyDown(KEY_UP))
			{
				//System.out.printf("UP\n");
				if(archon.x - 2 > 0)
					archon.moveTo(archon.x - 2,  archon.z);
			}
			
			else if(Keyboard.isKeyDown(KEY_RIGHT))
			{
				//System.out.printf("UP\n");
				if(archon.z + 2 < worldArray.length)
					archon.moveTo(archon.x,  archon.z + 2);
			}
			
			else if(Keyboard.isKeyDown(KEY_LEFT))
			{
				//System.out.printf("UP\n");
				if(archon.z - 2 > 0)
					archon.moveTo(archon.x,  archon.z - 2);
			}
			
			Display.update();
			long x = System.currentTimeMillis();
		
			print();
			
			while(x + 70 > System.currentTimeMillis());
			System.out.print("\n\n\n");
		}
	}
	public static void print() {
		for (int i = 0; i < worldArray.length; i++) {
			if (i%2 == 0) {
				for (int j = 0; j < worldArray[0].length; j++) {
					worldArray[i][j].print(false, false);
				}
			} else {
				for (int j = 0; j < worldArray[0].length; j++) {
					worldArray[i][j].print(true, true);
				}
				System.out.println();
				
				for (int j = 0; j < worldArray[0].length; j++) {
					worldArray[i][j].print(false, true);
				}
			}
			
			System.out.println();
		}
	}

}
