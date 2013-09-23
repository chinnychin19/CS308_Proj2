package walls;

public class BottomWall extends Wall {

	public BottomWall(String id, double x, double y, double length, double thickness, double magnitude, double exponent) {
		super(id, x, y, length, thickness, magnitude, exponent);
		myLength = length;
		myThickness = thickness;
	}

	@Override
	public boolean isVertical() {
		return false;
	}
	@Override
	public WallEgg layEgg(double pixels) {
		double newX = myX;
		double newY = myY + pixels;
		return new BottomWallEgg(newX, newY, myLength + 2*pixels, myThickness, myMagnitude, myExponent);
	}
}
