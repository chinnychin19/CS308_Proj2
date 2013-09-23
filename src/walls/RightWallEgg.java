package walls;

import ourObjects.Constants;

public class RightWallEgg extends WallEgg {

	
	public RightWallEgg(double x, double y, double len, double thick,
			double mag, double exp) {
		super(x, y, len, thick, mag, exp);
	}

	@Override
	public Wall hatchEgg() {
		return new RightWall(Constants.ID_RIGHT_WALL, myX, myY, myLength, myThickness, myMagnitude, myExponent);
	}

}
