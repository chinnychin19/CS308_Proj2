package walls;

public class RightWall extends Wall {

	public RightWall(String id, double x, double y, double length, double thickness, double magnitude, double exponent) {
		super(id, x, y, thickness, length, magnitude, exponent);
		myLength = length;
		myThickness = thickness;
	}

	@Override
	public boolean isVertical() {
		return true;
	}
	@Override
	public WallEgg layEgg(double pixels) {
		double newX = myX + pixels;
		double newY = myY;
		return new RightWallEgg(newX, newY, myLength + 2*pixels, myThickness, myMagnitude, myExponent);
	}
}
