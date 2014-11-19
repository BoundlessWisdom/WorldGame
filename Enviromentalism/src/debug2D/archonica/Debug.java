package debug2D.archonica;

public class Debug {
	
	static Location[][] worldArray;
	
	//Borders
	//Tiles
	//Print tiles?
	
	public static void main(String[] args) 
	{
		worldArray = new Location[9][9];
		init();
//		while (true)
			print();
	}
	
	public static void init() {
		for(int i = 0; i < worldArray.length; i++)
		{
			for(int j = 0; j < worldArray[i].length; j++)
			{
				if((i % 2) == 0)
				{
					if((j % 2) == 0)
						worldArray[i][j] = new Point(i, j, 0);
					else
						worldArray[i][j] = new Interaction(i, j);
				}
				else
				{
					if((j % 2) == 0)
						worldArray[i][j] = new Interaction(i, j);
					else
						worldArray[i][j] = new Tile(i, j);
				}
			}
		}
	}
	
	public static void print() {
		for (int i = 0; i < worldArray.length; i++) {
			if (i % 2 == 0) {
				for (int j = 0; j < worldArray[0].length; j++) {
					worldArray[i][j].print(false);
				}
			} else {
				
				for (int j = 0; j < worldArray[0].length; j++) {
					worldArray[i][j].print(true);
				}
				
				System.out.println();
				
				for (int j = 0; j < worldArray[0].length; j++) {
					worldArray[i][j].print(true);
				}
			}
			
			System.out.println();
		}
	}

}
