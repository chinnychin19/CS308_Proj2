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
	private double viscosity = 1, com_magnitude = 0, com_exponent = 1;
	private Vec2 gravity = new Vec2(0,0);
	
	public OurWorld(AABB worldBounds, Vec2 gravity, boolean doSleep) {
		super(worldBounds, gravity, doSleep);
		massList = new ArrayList<Mass>();
		springList = new ArrayList<Spring>();
	}
	
	public void setGravity(Vec2 g) {
		gravity = g;
	}
	
	public void setViscosity(double v) {
		viscosity = v;
	}
	
	public void setCenterOfMass(double magnitude, double exponent) {
		com_magnitude = magnitude;
		com_exponent = exponent;
	}
	
	public void setMasses(Collection<Mass> masses) {
		massList = masses;
	}
	
	public void setSprings(Collection<Spring> springs) {
		springList = springs;
	}
	public void print(Object o) {
		System.out.println(o.toString());
	}
	
	//F = m * a, so multiply gravity by mass
	private Vec2 forceOfGravity(Mass m) {
		float mass = m.getBody().getMass();
		return new Vec2(gravity.x * mass, gravity.y * mass);
	}
	
	public void applyForces() {
		//gravity
		for (Mass m: massList) {
			m.getBody().applyForce(forceOfGravity(m), m.getBody().getPosition());
		}
		
		//viscosity
		for (Mass m: massList) {
			Vec2 dir = m.getBody().getLinearVelocity();
			System.out.println(dir);
			Vec2 oppDir = dir.negate(); //returns the negative vector
			float newX = (float) (1000*oppDir.x * (1-viscosity));
			float newY = (float) (1000*oppDir.y * (1-viscosity));
			m.getBody().applyForce(new Vec2(newX, newY), 
					m.getBody().getPosition()); //proportional to speed
		}
		
		//center of mass
		
		//wall repulsion done inside of wall
	}
}
