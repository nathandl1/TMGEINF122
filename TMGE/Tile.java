package TMGE;
// Tile Class

/*
Tiles represent the objects that fill the board
Tiles have a color
*/

// Imports
//import java.awt.Color;

public class Tile {

	enum Color {
		NULL, // 0
		RED, // 1
		GREEN, // 2
		BLUE, // 3
		YELLOW, // 4
		ORANGE, // 5
		PURPLE, // 6
		BLACK, // 7
		WHITE // 8
	}

	Color color;
	
	/**
	 * Copy constructor. Creates a copy of a given tile.
	 * @param color
	 * @return 
	 */
	public Tile(Tile someTile) {
		this.color = someTile.getColor();
	}

	/**
	 * Constructor. Creates a tile based on a color value.
	 * @param color
	 * @return 
	 */
	public Tile(Color someColor) {
		this.color = someColor;
	}

	/**
	 * Constructor. Creates a tile based on a color colorID (enum value).
	 * @param colorID
	 */
	public Tile(int colorID) {
		try {
			if (colorID < 0 || colorID > 8)
				throw new IllegalArgumentException("Invalid colorID: " + colorID);
			this.color = Color.values()[colorID];
		} catch (Exception e) {
			System.out.println("Tile Constructor failure");
			e.printStackTrace();
		}
	}

	/**
	 * Function to return Color object associated with the tile.
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Function to set the Color object to a new Object.
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Color: " + this.color;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Tile)) {
			return false;
		}

		return this.color == ((Tile) obj).color;
	}

}
