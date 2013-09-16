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
	
	@Override
	public void hit( JGObject other )
	{
		// we hit something! bounce off it!
		Vec2 velocity = myBody.getLinearVelocity();
		
		// is it a tall wall?
		final double DAMPING_FACTOR = Constants.DAMPING_FACTOR;
		boolean isSide = other.getBBox().height > other.getBBox().width;
		if( isSide )
		{
			velocity.x *= -DAMPING_FACTOR;
		}
		else
		{
			velocity.y *= -DAMPING_FACTOR;
		}
		
		// apply the change
		myBody.setLinearVelocity( velocity );
	}
}
