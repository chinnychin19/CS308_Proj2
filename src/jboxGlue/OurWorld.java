package jboxGlue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import ourObjects.Mass;
import ourObjects.Spring;

public class OurWorld extends World {
	private Collection<Mass> massList;
	private Collection<Spring> springList;
	
	public OurWorld(AABB worldBounds, Vec2 gravity, boolean doSleep) {
		super(worldBounds, gravity, doSleep);
		massList = new ArrayList<Mass>();
		springList = new ArrayList<Spring>();
	}
	
	public void setMasses(Collection<Mass> masses) {
		massList = masses;
	}
	
	public void setSprings(Collection<Spring> springs) {
		springList = springs;
	}
	
	public void applyForces() {
		//gravity
		for (Mass m: massList) {
			m.getBody().applyForce(getGravity(), m.getBody().getPosition());
		}
		
		//viscosity
		
		//center of mass
		
		//wall repulsion done inside of wall
	}
}
