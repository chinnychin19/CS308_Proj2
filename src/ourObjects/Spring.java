package ourObjects;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jboxGlue.UnitVectors;

public class Spring extends PhysicalObject {
	private double myRestLength, myConstant;
	private Mass myMass1, myMass2;

	public Spring(String animId, Mass m1, Mass m2) {
		super(animId, Constants.CID_SPRING, Constants.SPRING_COLOR);
		myRestLength = distance(m1.getX(), m1.getY(), m2.getX(), m2.getY());
		myConstant = Constants.DEFAULT_SPRING_CONSTANT;
		myMass1 = m1;
		myMass2 = m2;
	}
	public void setRestLength(double restLength) {
		myRestLength = restLength;
	}
	public void setConstant(double constant) {
		myConstant = constant;
	}

	private static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	private double getCurLength() {
		return distance(myMass1.getX(), myMass1.getY(), myMass2.getX(), myMass2.getY());
	}
	
	public Mass getMass1() {
		return myMass1;
	}
	
	public Mass getMass2() {
		return myMass2;
	}
		
	public void move() {
		applyForce();
	}
	
	private void applyForce() {
		//Unit vector pointing from m1 to m2
		Vec2 uVec = UnitVectors.unitVector(myMass1.getBody().getPosition(), 
				myMass2.getBody().getPosition());
		
		//Force is positive if contracting
		float force = Constants.SPRING_MULTIPLIER * (float) 
				((getCurLength()-myRestLength) * myConstant);
		
		myMass1.getBody().applyForce(new Vec2(uVec.x * force, uVec.y * force), 
				myMass1.getBody().getPosition());
		myMass2.getBody().applyForce(new Vec2(-uVec.x * force, -uVec.y * force), 
				myMass2.getBody().getPosition());
	}
		
	@Override
	public void paintShape() {
		myEngine.setColor( myColor );
		Vec2 p1 = myMass1.getBody().getPosition();
		Vec2 p2 = myMass2.getBody().getPosition();
		myEngine.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

}
