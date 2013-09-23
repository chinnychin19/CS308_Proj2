package walls;

import jboxGlue.PhysicalObjectRect;

import ourObjects.Constants;

/**
 * This is an abstract class that all concrete Walls inherit from. It implements
 * getters and declares two abstract functions to be implemented the subclasses.
 * 
 * @author Chandy
 * 
 */

public abstract class Wall extends PhysicalObjectRect {
	protected String myId;
	protected double myX, myY, myLength, myThickness, myMagnitude, myExponent;
	protected boolean myActivity;

	/**
	 * This constructor is only call through super() from the subclasses.
	 * Ideally, subclasses will translate a wall length and thickness into a
	 * width and height. This reduces logic to correctly size vertical vs.
	 * horizontal walls.
	 * 
	 * @param id
	 *            Used by the underlying engine
	 * @param x
	 *            x coordinate of center of Wall
	 * @param y
	 *            y coordinate of center of Wall
	 * @param width
	 *            horizontal width
	 * @param height
	 *            vertical height
	 * @param magnitude
	 *            wall repulsion magnitude
	 * @param exponent
	 *            wall repulsion exponent
	 */
	public Wall(String id, double x, double y, double width, double height,
			double magnitude, double exponent) {
		super(id, Constants.CID_WALL, Constants.WALL_COLOR, width, height);
		setPos(x, y);
		myX = x;
		myY = y;
		myId = id;
		myMagnitude = magnitude;
		myExponent = exponent;
		myActivity = true;
	}

	public String toString() {
		return String.format("EGG --> x: %f, y: %f, len: %f, thick: %f", myX,
				myY, myLength, myThickness);
	}

	public abstract boolean isVertical();

	/**
	 * This is a really cool method. Period. It "lays" an egg that "knows" how
	 * to construct a new wall with the correct location and size to make the
	 * field either expand or contract.
	 * 
	 * @param pixels
	 *            How many pixels to expand in one increment
	 * @return	A concrete WallEgg object (TopWallEgg, LeftWallEgg, etc.)
	 */
	public abstract WallEgg layEgg(double pixels);

	public String getId() {
		return myId;
	}

	public double getMagnitude() {
		return myMagnitude;
	}

	public double getExponent() {
		return myExponent;
	}

	public boolean getActivity() {
		return myActivity;
	}

	public void toggle() {
		myActivity = !myActivity;
	}
}
