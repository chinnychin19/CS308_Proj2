package walls;

public class TopWall extends Wall {

	public TopWall(String id, double x, double y, double length, double thickness, double magnitude, double exponent) {
		super(id, x, y, length, thickness, magnitude, exponent);
		myLength = length;
		myThickness = thickness;
	}
	
	public String toString() {
		return "WALL --> "+getBBox().toString() + "(x,y) = "+myX+","+myY;
	}

	@Override
	public boolean isVertical() {
		return false;
	}
	@Override
	public WallEgg layEgg(double pixels) {
		double newX = myX;
		double newY = myY - pixels;
		return new TopWallEgg(newX, newY, myLength + 2*pixels, myThickness, myMagnitude, myExponent);
	}
}
