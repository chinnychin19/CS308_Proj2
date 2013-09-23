package ourObjects;

import jgame.JGObject;

import org.jbox2d.common.Vec2;

/**
 * A Moving Mass is exactly a Mass with the added functionality of having an
 * initial velocity and checking for collisions.
 * 
 * @author Chandy
 * 
 */

public class MovingMass extends Mass {

	/**
	 * The only constructor.
	 * 
	 * @param animId
	 *            Only used by the underlying engine
	 * @param x
	 *            x coordinate of center
	 * @param y
	 *            y coordinate of center
	 * @param xSpeed
	 *            initial x velocity
	 * @param ySpeed
	 *            initial y velocity
	 * @param mass
	 *            Mass value
	 */
	public MovingMass(String animId, double x, double y, double xSpeed,
			double ySpeed, double mass) {
		super(animId, x, y, mass);
		setPos(x, y);
		getBody().setLinearVelocity(new Vec2((float) xSpeed, (float) ySpeed));
	}

	/**
	 * If it hits a wall, it reverses the velocity appropriately and loses some
	 * momentum due to damping.
	 * 
	 * @param other
	 *            The object being collided with
	 */
	@Override
	public void hit(JGObject other) {
		// we hit something! bounce off it!
		Vec2 velocity = myBody.getLinearVelocity();

		// is it a vertical wall?
		final double DAMPING_FACTOR = Constants.DAMPING_FACTOR;
		boolean isSide = other.getBBox().height > other.getBBox().width;
		if (isSide) {
			velocity.x *= -DAMPING_FACTOR;
		} else {
			velocity.y *= -DAMPING_FACTOR;
		}

		// apply the change
		myBody.setLinearVelocity(velocity);
	}
}