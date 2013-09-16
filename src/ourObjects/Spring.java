package ourObjects;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;

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
	
	public Vec2 getForce(Mass m) {
		Mass otherMass = (m == myMass1 ? myMass2 : myMass1);
		
		float unitVectorX = (float) ((otherMass.getX()-m.getX())/getCurLength());
		float unitVectorY = (float) ((otherMass.getX()-m.getX())/getCurLength());
		
		float springForce = (float) ((getCurLength()-myRestLength) * myConstant);
		
		return new Vec2(unitVectorX * springForce, unitVectorY * springForce);
		// F = k*x, positive means spring is longer that restLength 
	}
		
	@Override
	public void paintShape() {
		myEngine.setColor( myColor );
		Vec2 p1 = myMass1.getBody().getPosition();
		Vec2 p2 = myMass2.getBody().getPosition();
		myEngine.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

}
