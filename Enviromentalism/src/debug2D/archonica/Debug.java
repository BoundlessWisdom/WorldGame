package debug2D.archonica;

public class Debug {
	
	static Location[][] worldArray;
	
	//Borders
	//Tiles
	//Print tiles?
	
	public static void main(String[] args) 
	{
		for(int i = 0; i < worldArray.length; i++)
		{
			for(int j = 0; j < worldArray[i].length; j++)
			{
				if((i % 2) == 0 || i == 0)
				{
					if(j == 0 || (j % 2) == 0)
						worldArray[i][j] = new Point(i, j, 0);
					else
						worldArray[i][j] = new Interaction(i, j);
				}
				else
				{
					if(j == 0 || (j % 2) == 0)
						worldArray[i][j] = new Interaction(i, j);
					else
						worldArray[i][j] = new Tile(i, j);
				}
			}
		}
	}
	
	public void print() {
		for (int i = 0; i < worldArray.length; i++) {
			if (i % 3 == 0) {
				for (int j = 0; j < worldArray[0].length; j++) {
					System.out.print(0);
				}
				
				System.out.println();
			} else {
				
				for (int j = 0; j < worldArray[0].length; j++) {
					System.out.print(0);
				}
				
				System.out.println();
				i++;
				
				for (int j = 0; j < worldArray[0].length; j++) {
					System.out.print(0);
				}
			}
		}
	}

}
