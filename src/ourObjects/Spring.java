package ourObjects;

import jboxGlue.PhysicalObject;

public class Spring extends PhysicalObject {
	private double myRestLength, myConstant;
	private Mass myMass1, myMass2;

	public Spring(String animId, Mass m1, Mass m2) {
		super(animId, Constants.CID_SPRING, Constants.SPRING_COLOR);
		myRestLength = distance(m1.getX(), m1.getY(), m2.getX(), m2.getY());
		myConstant = Constants.DEFAULT_SPRING_CONSTANT;
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
	
	public double getForce() {
		return (getCurLength()-myRestLength) * myConstant;
		// F = k*x, positive means spring is longer that restLength 
	}
	
	@Override
	public void move() {
		//do nothing
	}
	
	@Override
	public void paintShape() {
		myEngine.setColor( myColor );
		myEngine.drawLine(myMass1.getX(), myMass1.getY(), myMass2.getX(), myMass2.getY());
	}

}
