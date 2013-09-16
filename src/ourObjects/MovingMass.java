package ourObjects;

import java.util.List;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jgame.JGColor;
import jgame.JGObject;

public class MovingMass extends Mass {

	public MovingMass(String animId, double x, double y, double xSpeed, double ySpeed) {
		this(animId, x, y, xSpeed, ySpeed, Constants.MASS_DEFAULT_MASS);
	}

	public MovingMass(String animId, double x, double y, double xSpeed, double ySpeed, double mass) {
		super(animId, x, y);
		setXSpeed(xSpeed);
		setYSpeed(ySpeed);
		setMass(mass);
	}

	@Override
	public void move() {
		// Move masses based on the forces applied to them.
		// Find net force in X and Y directions, then apply the net force to the object
		Vec2 netForce = new Vec2();
		
		// TODO: Spring Forces
//		List<Spring> springs = getSprings();
//		for(Spring spring : springs) {
//			netForce = netForce.add(spring.getForce(this));
//		}
		
		// TODO: gravity
		
		// TODO: Wall Repulsion
		
		// TODO: Center of mass
		
		// TODO: Viscous Drag
		
		// Return Net of All forces
//		this.setForce(netForce);
		super.move();
	}

	@Override
	public void hit(JGObject obj) {
		// Handle interaction with walls. (No collisions between mass/spring objects)
		// Calculate angle of incident, and apply 
	}

}