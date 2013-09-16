package ourObjects;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.CircleDef;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectCircle;
import jgame.JGColor;
import jgame.JGObject;

public class Mass extends PhysicalObjectCircle {
	private double myX, myY;
	private double myXSpeed, myYSpeed;
	private double myMass, myRadius;
	private int myCID;
	private List<Spring> mySprings;

	public Mass(String animId, double x, double y) {
		super(animId, Constants.CID_MASS, Constants.MASS_COLOR, Constants.MASS_RADIUS);
		myX = x;
		myY = y;
		myXSpeed = 0;
		myYSpeed = 0;
		myMass = 0; //fixed mass
		myRadius = Constants.MASS_RADIUS;
		mySprings = new ArrayList<Spring>();
	}
	
	public double getX() {
		return myX;
	}
	
	public double getY() {
		return myY;
	}
	
	public void setMass(double m) {
		myMass = m;
	}
	
	@Override
	public void move() {
		this.x = myX;
		this.y = myY;
		this.xspeed = myXSpeed;
		this.yspeed = myYSpeed;
	}
	
	@Override
	public void hit(JGObject obj) {
		//do nothing
	}
	
	@Override
	public void paintShape() {
		myEngine.setColor( myColor );
		myEngine.drawOval( myX, myY, myRadius*2, myRadius*2, true, true );
	}
	
	public List<Spring> getSprings() {
		return mySprings;
	}
	
	public void setXSpeed(double xs) {
		myXSpeed = xs;
	}
	
	public void setYSpeed(double ys) {
		myYSpeed = ys;
	}
	
	public double getXSpeed() {
		return myXSpeed;
	}
	
	public double getYSpeed() {
		return myYSpeed;
	}
}
