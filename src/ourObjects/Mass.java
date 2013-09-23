package ourObjects;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObjectCircle;
import jgame.JGObject;

/**
 * This is the base class we use to represent masses. This base class
 * specifically represents a fixed mass and is extended by MovingMass. Each Mass
 * object only keeps track of mass, location, and velocity. In this base class,
 * the object has no reaction to collisions with other objects.
 * 
 * @author Chandy
 */

public class Mass extends PhysicalObjectCircle {

	/**
	 * The only constructor. By default, the initial velocity is 0.
	 * @param animId	This is only used by the underlying engine
	 * @param x			x coordinate of center
	 * @param y			y coordinate of center
	 * @param mass		The mass (may be 0 for fixed mass)
	 */
	public Mass(String animId, double x, double y, double mass) {
		super(animId, Constants.CID_MASS, Constants.MASS_COLOR,
				Constants.MASS_RADIUS, mass);
		setPos(x, y);
		getBody().setLinearVelocity(new Vec2(0, 0));
	}

	public double getX() {
		return getBody().getPosition().x;
	}

	public double getY() {
		return getBody().getPosition().y;
	}

	@Override
	public void paintShape() {
		myEngine.setColor(myColor);
		myEngine.drawOval(getBody().getPosition().x, getBody().getPosition().y,
				Constants.MASS_RADIUS * 2, Constants.MASS_RADIUS * 2, true,
				true);
	}

	@Override
	public void hit(JGObject obj) {
		// do nothing
	}
}
