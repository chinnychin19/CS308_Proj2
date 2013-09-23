package walls;

import ourObjects.Constants;

public class BottomWallEgg extends WallEgg {

	public BottomWallEgg(double x, double y, double len, double thick,
			double mag, double exp) {
		super(x, y, len, thick, mag, exp);
	}

	@Override
	public Wall hatchEgg() {
		return new BottomWall(Constants.ID_BOTTOM_WALL, myX, myY, myLength,
				myThickness, myMagnitude, myExponent);
	}

}
