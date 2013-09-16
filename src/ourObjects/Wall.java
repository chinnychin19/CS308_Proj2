package ourObjects;

import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGColor;

import org.jbox2d.collision.PolygonDef;

public class Wall extends PhysicalObjectRect {
	private double myMagnitude;
	private double myExponent;
	public Wall(String id, double width, double height, double magnitude, double exponent) {
		super(id,Constants.CID_WALL, Constants.WALL_COLOR, width, height);
		myMagnitude = magnitude;
		myExponent = exponent;
	}
	
	public void repelMasses(){
		
	}

}
