package ourObjects;

import java.util.List;

import jboxGlue.PhysicalObject;
import jgame.JGColor;
import jgame.JGObject;

public class Mass extends PhysicalObject {
	private double myX, myY;
	private double myXSpeed, myYSpeed;
	private double myMass;
	private int myCID;
	private List<Spring> mySprings;

	public Mass(String animId, double x, double y) {
		super(animId, Constants.CID_MASS, Constants.MASS_COLOR);
		myX = x;
		myY = y;
		myXSpeed = 0;
		myYSpeed = 0;
		myMass = 0; //fixed mass
	}
	
	public double getX() {
		return myX;
	}
	
	public double getY() {
		return myY;
	}
	
	@Override
	public void move() {
		//do nothing
	}
	
	@Override
	public void hit(JGObject obj) {
		//do nothing
	}
	
	@Override
	public void paintShape() {
		myEngine.setColor( myColor );
		myEngine.drawOval( myX, myY, Constants.MASS_RADIUS*2,
				Constants.MASS_RADIUS*2, true, true );
	}
	
	public void setSprings(List<Spring> springs) {
		mySprings = springs;
	}
}
