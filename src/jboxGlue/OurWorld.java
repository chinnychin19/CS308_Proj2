package jboxGlue;

import java.util.ArrayList;
import java.util.Collection;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import ourObjects.Constants;
import ourObjects.Mass;
import ourObjects.Spring;
import walls.Wall;
import walls.WallEgg;

/**
 * 
 * This is where the magic happens. All force calculations are done here, as
 * well as the storage of all game objects. Game objects are stored so that they
 * can be accessed without having to go through the engine and manage object ID
 * numbers. All forces and force multipliers are set here, but will generally be
 * modified through parsing an environment, or toggling forces.
 * 
 * 
 * 
 * 
 * @author Chandy
 */

public class OurWorld extends World {
	private Collection<Mass> massList;
	private Collection<Spring> springList;
	private Collection<Wall> wallList;
	private double viscosity = Constants.DEFAULT_VISCOSITY,
			com_magnitude = Constants.DEFAULT_COM_MAGNITUDE,
			com_exponent = Constants.DEFAULT_COM_EXPONENT;
	private float v_multiplier = Constants.VISCOSITY_MULTIPLIER,
			g_multiplier = Constants.GRAVITY_MULTIPLIER,
			c_multiplier = Constants.COM_MULTIPLIER,
			w_multiplier = Constants.WALL_MULTIPLIER;
	private Vec2 gravity = new Vec2(0, 0);

	public OurWorld(AABB worldBounds, Vec2 gravity, boolean doSleep) {
		super(worldBounds, gravity, doSleep);
		massList = new ArrayList<Mass>();
		springList = new ArrayList<Spring>();
	}

	public void setGravity(Vec2 g) {
		gravity = g;
	}

	public Collection<Wall> getWalls() {
		return wallList;
	}

	public void setViscosity(double v) {
		viscosity = v;
	}

	public void setCenterOfMass(double magnitude, double exponent) {
		com_magnitude = magnitude;
		com_exponent = exponent;
	}

	public void addMasses(Collection<Mass> masses) {
		massList.addAll(masses);
	}

	public void addSprings(Collection<Spring> springs) {
		springList.addAll(springs);
	}

	public void setWalls(Collection<Wall> walls) {
		wallList = walls;
	}

	public void toggleCOM() {
		c_multiplier = (c_multiplier == 0 ? Constants.COM_MULTIPLIER : 0);
	}

	public void toggleGravity() {
		g_multiplier = (g_multiplier == 0 ? Constants.GRAVITY_MULTIPLIER : 0);
	}

	public void toggleViscosity() {
		v_multiplier = (v_multiplier == 0 ? Constants.VISCOSITY_MULTIPLIER : 0);
	}

	/**
	 * Toggles whether a wall's repulsion force should be calculated
	 * @param id The ID of the wall to toggle
	 */
	public void toggleWall(String id) {
		for (Wall w : wallList) {
			if (w.getId().equals(id)) {
				w.toggle();
			}
		}
	}

	public void setWallMultiplier(float multiplier) {
		w_multiplier = multiplier;
	}

	public float getCOMMultiplier() {
		return c_multiplier;
	}

	public float getGravityMultiplier() {
		return g_multiplier;
	}

	public float getViscosityMultiplier() {
		return v_multiplier;
	}

	public float getWallMultiplier() {
		return w_multiplier;
	}

	// F = m * a, so multiply gravity by mass
	/**
	 * Gravity is applied to masses by calculating the force acting on them and
	 * then applying it to that mass. Calculations occur independently of
	 * application of the force.
	 * 
	 * @param m
	 *            The mass for which gravity is being calculated
	 * @return
	 */
	private Vec2 forceOfGravity(Mass m) {
		float mass = m.getBody().getMass();
		return new Vec2(gravity.x * mass * g_multiplier, gravity.y * mass
				* g_multiplier);
	}

	public void applyForces() {
		applyGravity();
		applyViscosity();
		applyCenterOfMass();
		applyWallRepulsion();
	}

	/**
	 * Center of Mass (COM) is a force that pulls all objects towards the
	 * collective center of mass. This is calculated by first finding the COM
	 * point, and then applying a force to each object individually towards the
	 * center of mass.
	 */
	private void applyCenterOfMass() {
		Vec2 comPoint = getCOMPoint();
		for (Mass m : massList) {
			Vec2 massLoc = m.getBody().getPosition();
			Vec2 unit = UnitVectors.unitVector(massLoc, comPoint);
			double dist = Math.sqrt(Math.pow(massLoc.x - comPoint.x, 2)
					+ Math.pow(massLoc.y - comPoint.y, 2));
			double scalar = Math.pow(1.0 / dist, com_exponent) * com_magnitude;
			Vec2 force = new Vec2((float) (unit.x * scalar),
					(float) (unit.y * scalar));
			m.getBody().applyForce(force, massLoc);
		}
	}

	/**
	 * Calculated the COM point by summing the components of each of the mass,
	 * then dividing the 'X' and 'Y' positions by the overall mass.
	 * 
	 * @return COMPoint The center of mass point
	 */
	private Vec2 getCOMPoint() {
		float sumX = 0, sumY = 0, totalMass = 0;
		for (Mass m : massList) {
			sumX += m.getBody().getPosition().x * m.getBody().getMass();
			sumY += m.getBody().getPosition().y * m.getBody().getMass();
			totalMass += m.getBody().getMass();
		}
		float avgX = sumX / totalMass;
		float avgY = sumY / totalMass;
		return new Vec2(avgX, avgY);
	}

	/**
	 * Where gravity is applied from the forceOfGravity method
	 */
	private void applyGravity() {
		for (Mass m : massList) {
			m.getBody()
					.applyForce(forceOfGravity(m), m.getBody().getPosition());
		}
	}

	/**
	 * Viscosity is a resistive force, so it is always in the opposite direction
	 * of the objects motion. This force is calculated by taking the objects
	 * linear velocity vector, reversing it, scaling it by the viscosity
	 * constants, and then finally re-applying the force to the object.
	 */
	private void applyViscosity() {
		for (Mass m : massList) {
			Vec2 dir = m.getBody().getLinearVelocity();
			Vec2 oppDir = dir.negate(); // returns the negative vector
			float newX = (float) (oppDir.x * viscosity * v_multiplier);
			float newY = (float) (oppDir.y * viscosity * v_multiplier);
			Vec2 vForce = new Vec2(newX, newY);
			m.getBody().applyForce(vForce, m.getBody().getPosition()); // proportional
																		// to
																		// speed
		}
	}

	/**
	 * Wall Repulsion is handled as a transverse force, meaning that the force
	 * from the wall is constant along its length. Wall Repulsion is calculated
	 * by summing up the individual wall repulsion forces on a mass, and then
	 * applying the net force to it. Vertical and horizontal walls repulse
	 * slightly differently(e.g. vertical walls only affect an objects 'X'
	 * dimension), so the calculation of the repulsion force has to reflect
	 * that.
	 */
	private void applyWallRepulsion() {
		for (Mass m : massList) {
			float xComp = 0;
			float yComp = 0;
			for (Wall w : wallList) {
				if (w.isVertical()) {
					if (w.getActivity()) {
						xComp += wallRepulsionForce(m, w);
					}
				} else {
					if (w.getActivity()) {
						yComp += wallRepulsionForce(m, w);
					}
				}
			}
			m.getBody().applyForce(new Vec2(xComp, yComp),
					m.getBody().getPosition());
		}
	}

	private float wallRepulsionForce(Mass m, Wall w) {
		float dist;
		int sign;

		if (w.isVertical()) {
			dist = Math.abs(w.getBody().getPosition().x
					- m.getBody().getPosition().x);
			sign = (w.getBody().getPosition().x < m.getBody().getPosition().x ? 1
					: -1);
		} else {
			dist = Math.abs(w.getBody().getPosition().y
					- m.getBody().getPosition().y);
			sign = (w.getBody().getPosition().y < m.getBody().getPosition().y ? 1
					: -1);
		}

		return (float) (sign * w_multiplier
				* Math.pow(1 / dist, w.getExponent()) * w.getMagnitude());
	}

	/**
	 * Clears all game objects (i.e. not walls) objects from the playing field
	 */
	public void clearObjects() {
		for (Spring s : springList) {
			s.remove();
		}
		for (Mass m : massList) {
			m.remove();
		}
		springList.clear();
		massList.clear();
	}

	/**
	 * 
	 * @param inflationPixels
	 */
	public void inflateWalls(double inflationPixels) {
		Collection<WallEgg> eggs = new ArrayList<WallEgg>();
		for (Wall w : wallList) {
			eggs.add(w.layEgg(inflationPixels));
			w.remove();
		}
		Collection<Wall> newWalls = new ArrayList<Wall>();
		for (WallEgg e : eggs) {
			newWalls.add(e.hatchEgg());
		}
		wallList = newWalls;
	}

	/**
	 * @return Collection<Mass> A List of the masses currently in the world
	 */
	public Collection<Mass> getMasses() {
		return massList;
	}
}
