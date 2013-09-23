package walls;

/**
 * This class exists solely for the purpose of destroying and reconstructing
 * walls during expansion/contraction of the field.
 * 
 * @author Chandy
 * 
 */

public abstract class WallEgg {
	protected double myX, myY, myLength, myThickness, myMagnitude, myExponent;

	/**
	 * This constructor simply stores all the fields used by a Wall constructor.
	 */
	public WallEgg(double x, double y, double len, double thick, double mag,
			double exp) {
		myX = x;
		myY = y;
		myLength = len;
		myThickness = thick;
		myMagnitude = mag;
		myExponent = exp;
	}

	/**
	 * This is where the magic happens. The previous wall will have already been
	 * destroyed, but the egg contains the ability to spawn a new wall here.
	 * This will create the correct concrete Wall object to go with the type of
	 * egg. i.e. a TopWallEgg will hatch a TopWall and so on.
	 * 
	 * @return A concrete Wall object
	 */
	public abstract Wall hatchEgg();

	public String toString() {
		return String.format("EGG --> x: %f, y: %f, len: %f, thick: %f", myX,
				myY, myLength, myThickness);
	}

}
