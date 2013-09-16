package ourObjects;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectCircle;
import jgame.JGColor;
import jgame.JGObject;

public class Mass extends PhysicalObjectCircle {

	public Mass(String animId, double x, double y, double mass) {
		super(animId, Constants.CID_MASS, Constants.MASS_COLOR, Constants.MASS_RADIUS, mass);
		setPos(x, y);
		getBody().setLinearVelocity(new Vec2(0, 0));
	}
	
	@Override
	public void hit(JGObject obj) {
		//do nothing
	}
	
	public double getX() {
		return getBody().getPosition().x;
	}
	
	public double getY() {
		return getBody().getPosition().y;
	}
	
	@Override
	public void paintShape() {
		myEngine.setColor( myColor );
		myEngine.drawOval( getBody().getPosition().x, getBody().getPosition().y, 
				Constants.MASS_RADIUS*2, Constants.MASS_RADIUS*2, true, true );
	}
}
