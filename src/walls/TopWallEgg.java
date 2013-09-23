package walls;

import ourObjects.Constants;

public class TopWallEgg extends WallEgg {

	public TopWallEgg(double x, double y, double len, double thick, double mag,
			double exp) {
		super(x, y, len, thick, mag, exp);
	}
	
	@Override
	public Wall hatchEgg() {
		return new TopWall(Constants.ID_TOP_WALL, myX, myY, myLength, myThickness, myMagnitude, myExponent);
	}

}
