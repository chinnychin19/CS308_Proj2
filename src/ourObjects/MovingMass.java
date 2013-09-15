package ourObjects;

import java.util.List;

import jboxGlue.PhysicalObject;
import jgame.JGColor;
import jgame.JGObject;

public class MovingMass extends Mass {

	public MovingMass(String animId, double x, double y, double xSpeed, double ySpeed) {
		this(animId, x, y, xSpeed, ySpeed, Constants.MASS_DEFAULT_MASS);
	}

	public MovingMass(String animId, double x, double y, double xSpeed, double ySpeed, double mass) {
		super(animId, x, y);
		myXSpeed = xSpeed;
		myYSpeed = ySpeed;
		setMass(mass);
	}

	@Override
	public void move() {
		// Move masses based on the forces applied to them. (Spring, Wall Repulsion, and Viscous Drag)
		// Find net force in X and Y directions, then apply the net force to the object 
	}

	@Override
	public void hit(JGObject obj) {
		// Handle interaction with walls. (No collisions between mass/spring objects)
		// Calculate angle of incident, and apply 
	}

}