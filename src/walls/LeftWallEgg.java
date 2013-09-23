package walls;

import ourObjects.Constants;

public class LeftWallEgg extends WallEgg {

	public LeftWallEgg(double x, double y, double len, double thick,
			double mag, double exp) {
		super(x, y, len, thick, mag, exp);
	}

	@Override
	public Wall hatchEgg() {
		return new LeftWall(Constants.ID_LEFT_WALL, myX, myY, myLength, myThickness, myMagnitude, myExponent);
	}

}
