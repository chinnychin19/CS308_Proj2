package ourObjects;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jboxGlue.UnitVectors;

/**
 * This is the base class to represent springs. It keeps track of its rest
 * length and spring constant. It doesn't need to store coordinates because it
 * has references to each of the Mass objects to which it is anchored. Every
 * frame, a spring will apply a force to its masses if it has not been
 * destroyed.
 * 
 * @author Chandy
 * 
 */

public class Spring extends PhysicalObject {
	private double myRestLength, myConstant;
	private Mass myMass1, myMass2;
	private boolean destroyed;

	/**
	 * The only constructor. The rest length is set to the initial length and
	 * the spring constant is set to a default value and may be overridden by
	 * calling setConstant()
	 * 
	 * @param animId
	 *            Used by the underlying engine
	 * @param m1
	 *            First anchored mass
	 * @param m2
	 *            Second anchored mass
	 */
	public Spring(String animId, Mass m1, Mass m2) {
		super(animId, Constants.CID_SPRING, Constants.SPRING_COLOR);
		myRestLength = distance(m1.getX(), m1.getY(), m2.getX(), m2.getY());
		myConstant = Constants.DEFAULT_SPRING_CONSTANT;
		myMass1 = m1;
		myMass2 = m2;
		destroyed = false;
	}

	public void setRestLength(double restLength) {
		myRestLength = restLength;
	}

	public void setConstant(double constant) {
		myConstant = constant;
	}

	@Override
	public void destroy() {
		destroyed = true;
	}

	private static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	private double getCurLength() {
		return distance(myMass1.getX(), myMass1.getY(), myMass2.getX(),
				myMass2.getY());
	}

	public double getRestLength() {
		return myRestLength;
	}

	public Mass getMass1() {
		return myMass1;
	}

	public Mass getMass2() {
		return myMass2;
	}

	public void move() {
		if (destroyed) {
			return;
		}
		applyForce();
	}

	/**
	 * This uses Hooke's Law (F = kx, where x is the displacement vector) to
	 * calculate and spring forces on each of the anchored masses.
	 */
	private void applyForce() {
		// Unit vector pointing from m1 to m2
		Vec2 uVec = UnitVectors.unitVector(myMass1.getBody().getPosition(),
				myMass2.getBody().getPosition());

		// Force is positive if contracting
		float force = Constants.SPRING_MULTIPLIER
				* (float) ((getCurLength() - myRestLength) * myConstant);

		myMass1.getBody().applyForce(new Vec2(uVec.x * force, uVec.y * force),
				myMass1.getBody().getPosition());
		myMass2.getBody().applyForce(
				new Vec2(-uVec.x * force, -uVec.y * force),
				myMass2.getBody().getPosition());
	}

	/**
	 * Draws a line from first Mass to second Mass
	 */
	@Override
	public void paintShape() {
		if (destroyed) {
			return;
		}
		myEngine.setColor(myColor);
		Vec2 p1 = myMass1.getBody().getPosition();
		Vec2 p2 = myMass2.getBody().getPosition();
		myEngine.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

}
