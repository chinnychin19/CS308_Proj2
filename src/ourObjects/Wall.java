package ourObjects;

import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGColor;

import org.jbox2d.collision.PolygonDef;

public class Wall extends PhysicalObjectRect {
	private String myId;
	private double myMagnitude;
	private double myExponent;
	private boolean myActivity;
	
	public Wall(String id, double width, double height, double magnitude, double exponent) {
		super(id,Constants.CID_WALL, Constants.WALL_COLOR, width, height);
		myId = id;
		myMagnitude = magnitude;
		myExponent = exponent;
		myActivity = true;
	}
	
	public boolean isVertical(){
		return myId.equals(Constants.ID_LEFT_WALL) || myId.equals(Constants.ID_RIGHT_WALL);
	}
	
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
	
	public void setActivity(boolean activity) {
		myActivity = activity;
	}

}
