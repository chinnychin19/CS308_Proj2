package ourObjects;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.CircleDef;

import jboxGlue.PhysicalObject;
import jgame.JGColor;
import jgame.JGObject;

public class Mass extends PhysicalObject {
	private double myX, myY;
	protected double myXSpeed, myYSpeed;
	private double myMass, myRadius;
	private int myCID;
	private List<Spring> mySprings;

	public Mass(String animId, double x, double y) {
		super(animId, Constants.CID_MASS, Constants.MASS_COLOR);
		myX = x;
		myY = y;
		myXSpeed = 0;
		myYSpeed = 0;
		myMass = 0; //fixed mass
		myRadius = Constants.MASS_RADIUS;
		mySprings = new ArrayList<Spring>();
		initBody();
	}
	
	private void initBody() {
		// make it a circle
		CircleDef shape = new CircleDef();
		shape.radius = (float)myRadius;
		shape.density = (float)myMass;
		createBody( shape );
		setBBox( -(int)myRadius, -(int)myRadius, 2*(int)myRadius, 2*(int)myRadius );
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
		//do nothing
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
		myEngine.drawOval( myX, myY, Constants.MASS_RADIUS*2,
				Constants.MASS_RADIUS*2, true, true );
	}
	
	public List<Spring> getSprings() {
		return mySprings;
	}
}
