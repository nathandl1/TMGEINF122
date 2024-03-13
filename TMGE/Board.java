package TMGE;

/*
The board of the game. It has matrix array that can hold tiles and represents the game's current state.
It spawns in tiles.
*/
// Board Class

// Imports
import java.util.*;


//import java.util.HashMap;
//import java.util.Map;
//import java.awt.Color;


public class Board {

	Tile[][] tileArray;
	int row;
	int col;
	int size;

	//Selector indicates the highlighted tile
	int selectorX;
	int selectorY;

	int saveX;
	int saveY;


	Set<Integer> openSpaces;
	Set<Integer> toBeDeleted;
	//private Spawner spawner;

	public void resetAttributes(){
		this.tileArray = new Tile[row][col];

		this.size = row * col;
		this.selectorX = 0;
		this.selectorY = 0;
		this.saveX = -1;
		this.saveY = -1;
		this.openSpaces = new HashSet<Integer>();
		this.toBeDeleted= new HashSet<Integer>();

		for (int i = 0; i < this.size; i++) {
			this.openSpaces.add(i);
		}

		for (int i = 0; i < this.tileArray.length; i++) {
			for (int j = 0; j < this.tileArray[i].length; j++) {
				this.tileArray[i][j] = new Tile(0);
			}
		}
	}
	// Matthew's Work RN
	public Board(Board template) {
		this(template.row, template.col);
		resetAttributes();
	}

	public Board(int[][] twoDArray) {

		//May be not usable
		this.row = twoDArray.length;
		this.col = twoDArray[0].length;
		resetAttributes();

		//System.out.println("Board constructor\n" + this.row +" by "+ this.col);

		try {
			for (int i = 0; i < this.row; i++){
				for (int j = 0; j < this.col; j++){
					this.addTile(new Tile(twoDArray[i][j]), i, j);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Board Constructor failure, likely input array is incorrectly sized.");
			e.printStackTrace();
		}

	}

	public Board(int row, int col) {
		this.row = row;
		this.col = col;
		resetAttributes();
	}


	//This function is moved over to Spawner; this class does not have a spawner
	// public void fill(){
	// 	while (this.openSpaces.size() > 0) this.spawner.spawn(this);
	// }

	public void addTile(Tile someTile, int row, int col) {
		int index = getIndex(row, col);
		this.openSpaces.remove(index);
		this.tileArray[row][col] = someTile;
	}

	public void addTile(Tile someTile, int index) {
		int row = index / this.row;
    	int col = index % this.row;
		this.openSpaces.remove(index);
		this.tileArray[row][col] = someTile;
	}

	public void removeTile(int row, int col) {
		int index = row + col * (this.row);
		this.openSpaces.add(index);
		this.tileArray[row][col] = new Tile(0);
	}

	public void removeTile(int index) {
		int row = index / this.row;
    	int col = index % this.row;
		this.openSpaces.add(index);
		this.tileArray[row][col] = new Tile(0);	//0 = empty or null
	}

	public int toIndex(int row, int col) {
		return row + col * (this.row);
	}

	public Tile getTile(int row, int col) {
		//System.out.println("getting tile "+row+","+col);
		return this.tileArray[row][col];
	}
	public Tile getTile(int index) {
		int row = index / this.row;
    	int col = index % this.row;
		return this.getTile(row, col);
	}


	public boolean selectorSwap() {


		if (saveX < 0 || saveY < 0) return false;
		
		Tile.Color color1 = getTile(saveX, saveY).getColor();
		Tile.Color color2 = getTile(selectorX,selectorY).getColor();

		this.getTile(saveX,saveY).setColor(color2);
		this.getTile(selectorX,selectorY).setColor(color1);
		
		saveX = -1;
		saveY = -1;
		return true;
	}

	public boolean selectorSelects(){

		this.saveX = this.selectorX;
		this.saveY = this.selectorY;
		if (this.saveX < 0 || this.saveY < 0) return false;
		return true;
	}

	public boolean moveSelectorUp(){
		if(this.selectorX > 0){
			this.selectorX--;
			return true;
		}
		return false;
	}
	public boolean moveSelectorDown(){
		System.out.println(this.selectorX);
		if(this.selectorX+1 < row){
			this.selectorX++;
			return true;
		}
		return false;
	}
	public boolean moveSelectorLeft(){
		if(this.selectorY > 0){
			this.selectorY--;
			return true;
		}
		return false;
	}
	public boolean moveSelectorRight(){
		System.out.println("X="+this.selectorX+"; Y="+this.selectorY);
		if(this.selectorY+1 < col){
			this.selectorY++;
			return true;
		}

		return false;
	}

	public int getIndex(int row, int col){
		return row + col * (this.row);
	}

	@Override
	public String toString() {
		Tile.Color value;
		String returnString = "---------\n";
		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.col; j++) {
				value = this.tileArray[i][j].getColor();
				String addString = "";
				
				if (value.ordinal() == 0) {
					addString += "x";
                }
				else {
					addString += value.ordinal() + " ";
                }
			
				if (isSaveTile(i, j)) {
					addString = "(" + addString + ")";
				}
				else {
					addString = " " + addString + " ";
				}

				if (isSelectorTile(i, j)) {
					returnString += "[" + addString + "]";
				}
				else {
					returnString += " " + addString + " ";
				}
			}
			returnString += "\n";
		}
		return returnString;

	}

	private boolean isSelectorTile(int x, int y) {
		return x == this.selectorX && y == this.selectorY;
	}

	private boolean isSaveTile(int x, int y) {
		return x == this.saveX && y == this.saveY;
	}

	public void postMatch() {
		dropTiles();	//Gravity
		// fill();
	}
	/*
	indexing orde
	0 4 8
	1 5 9
	2 6 10
	3 7 11
	

	00 01 02 03
	10 11 12 13
	20 21 22 23
	30 31 32 33
	
	from bottom row to top
	




	PYTHON EXAMPLE OF 
	def simulate_gravity(matrix):
		nrows = len(matrix)
		ncols = len(matrix[0]) if nrows > 0 else 0

		for col in range(ncols):
			# Start from the second-to-last row and move upwards
			for row in range(nrows - 2, -1, -1):
				if matrix[row][col] != 0:  # If the current cell is not empty
					# "Fall" towards the bottom of the column
					current_row = row
					while current_row < nrows - 1 and matrix[current_row + 1][col] == 0:
						# Swap elements to simulate "falling"
						matrix[current_row][col], matrix[current_row + 1][col] = matrix[current_row + 1][col], matrix[current_row][col]
						current_row += 1
    return matrix

	# Example usage
	matrix = [
		[0, 2, 0, 0],
		[3, 0, 4, 0],
		[0, 0, 0, 5],
		[1, 0, 2, 0]
	]
	becomes
		[0, 0, 0, 0],
		[0, 0, 0, 0],
		[3, 0, 4, 0],
		[1, 2, 2, 5]
	which is correct but i 

	print("Before gravity:")
	for row in matrix:
		print(row)

	simulate_gravity(matrix)

	print("\nAfter gravity:")
	for row in matrix:
		print(row)



scene1
		X 3 4 5 			
		X 4 5 6
		X 7 8 9
		9 3 4 5 			
		8 4 5 6
		4 7 8 9
		
		stored = [4,8,9]
		presumably fill the col right after traversing
		continue to next col
		uses two traversals instead of 1
		is not bubble sort

scene2

		9 3 4 5 			
		X 4 5 6
		X 7 8 9
		X 3 4 5 			
		8 4 5 6
		4 7 8 9
		stored = [4,8,9]

scene3:
		X 3 4 5 			
		X 4 5 6
		5 7 8 9
		9 3 4 5 			
		X 4 5 6
		X 7 X 9
	*/ 
	private void dropTiles() {
		for (int currentCol = 0; currentCol < this.col; currentCol++) {


			/*
			1. get nonempty tiles
			2. drop?
			*/
			
			for (int currentRow = this.row - 2; currentRow >= 0; currentRow++) {
				// store tile that is not empty in this array1
				if (isEmpty(currentRow, currentCol)) {
					
				}

			}
				
			
			for (int currentRow = this.row - 1; currentRow >= 0; currentRow++) {
				// from bottom to top fill the tile we stored in array1 with another loop
				// and the rest would be empty
				if (isEmpty)

			}
		}
	}

public void sweepHorizontal(int matchX){
	Tile.Color currentColor;
	Set<Integer> returnSet = new HashSet<>();
	Tile tempTile;
	System.out.println("Running sweepHorizontal");
	for (int row = 0; row < this.row; row++) {
		Set<Integer> temp = new HashSet<>();
		currentColor = this.getTile(row, 0).getColor();
		for (int col = 0; col < this.col; col++) {
			tempTile = this.getTile(row, col);	//Get tile
			System.out.println("tempTile:"+tempTile);
			// same color
			if (tempTile.getColor() == currentColor && currentColor != Tile.Color.NULL) {
				temp.add(getIndex(row, col));
				// Temp Big enough
				if (temp.size() >= matchX && row == this.row-1) {
					returnSet.addAll(temp);
				}
			}
			// not same color
			else
			{
				// Temp big enough
				if (temp.size() >= matchX) {
					returnSet.addAll(temp);
				}
				temp = new HashSet<>();
				temp.add(getIndex(row, col));
				currentColor = tempTile.getColor();
			}
		}
	}
	System.out.println("remove these indexes:" + returnSet);
}



	public void sweepVertical(int matchX){
		Tile.Color currentColor;
		Set<Integer> returnSet = new HashSet<>();
		Tile tempTile;
		System.out.println("Running sweepVertical");
		for (int col = 0; col < this.col; col++) {
			
			Set<Integer> temp = new HashSet<>();
			currentColor = this.getTile(0, col).getColor();
			
			for (int row = 0; row < this.row; row++) {
				tempTile = this.getTile(row, col);	//Get tile
				// same color
				if (tempTile.getColor() == currentColor && currentColor != Tile.Color.NULL) {
					temp.add(getIndex(row, col));
					// Temp Big enough
					if (temp.size() >= matchX && row == this.row-1) {
						returnSet.addAll(temp);
					}
				}
				// not same color
				else
				{
					// Temp big enough
					if (temp.size() >= matchX) {
						returnSet.addAll(temp);
					}

					temp = new HashSet<>();
					temp.add(getIndex(row, col));
					currentColor = tempTile.getColor();
				}
			}
		}
		System.out.println("remove these indexes:" + returnSet);
	}

}

