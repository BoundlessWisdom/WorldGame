package debug2D.archonica;

public class Debug {
	
	Location[][] worldArray;
	
	//Borders
	//Tiles
	//Print tiles?
	
	public static void main(String[] args) {
		
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
