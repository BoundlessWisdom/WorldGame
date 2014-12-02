package debug2D.archonica;

public class Debug {
	
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
	
	public static void main(String[] args) 
	{
		worldArray = new Location[9][9];
		init();
		Entity archon = new Entity(null, 1, 1);
		archon.identifier = 'A';
		worldArray[1][1].addEntity(archon);
//		while (true)
		
		Entity tree = new Entity(null, 0, 0);
		tree.identifier = 'T';
		worldArray[1][3].addEntity(tree);
		
		print();
	}
	
	public static void init() {
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
