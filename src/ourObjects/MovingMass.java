package ourObjects;

import java.util.List;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.JGObject;

public class MovingMass extends Mass {

	public MovingMass(String animId, double x, double y, double xSpeed, double ySpeed, double mass) {
		super(animId, x, y, mass);
		setPos(x, y);
		getBody().setLinearVelocity(new Vec2((float)xSpeed, (float)ySpeed));
	}
}