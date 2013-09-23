package walls;

public abstract class WallEgg {
	protected double myX, myY, myLength, myThickness, myMagnitude, myExponent;
	public WallEgg(double x, double y, double len, double thick, double mag, double exp) {
		myX = x;
		myY = y;
		myLength = len;
		myThickness = thick;
		myMagnitude = mag;
		myExponent = exp;
	}
	public abstract Wall hatchEgg();
	
	public String toString() {
		return String.format("EGG --> x: %f, y: %f, len: %f, thick: %f", myX, myY, myLength, myThickness);
	}
	
}
