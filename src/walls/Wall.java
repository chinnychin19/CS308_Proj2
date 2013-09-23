package walls;

import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGColor;

import org.jbox2d.collision.PolygonDef;

import ourObjects.Constants;

public abstract class Wall extends PhysicalObjectRect {
	protected String myId;
	protected double myX, myY, myLength, myThickness, myMagnitude, myExponent;
	protected boolean myActivity;
	
	public Wall(String id, double x, double y, double width, double height, double magnitude, double exponent) {
		super(id,Constants.CID_WALL, Constants.WALL_COLOR, width, height);
		setPos(x, y);
		myX = x;
		myY = y;
		myId = id;
		myMagnitude = magnitude;
		myExponent = exponent;
		myActivity = true;
	}
	
	public String toString() {
		return String.format("EGG --> x: %f, y: %f, len: %f, thick: %f", myX, myY, myLength, myThickness);
	}	

	public abstract boolean isVertical();
	
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
