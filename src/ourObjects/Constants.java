package ourObjects;

import org.jbox2d.common.Vec2;

import jgame.JGColor;

/**
 * This class stores useful constants that are used in various places throughout
 * our project. Keeping them all stored in one place makes them easy to find.
 * 
 * @author Chandy
 */

public interface Constants {
	// Collision IDs
	public static final int CID_MASS = 0x01, CID_FIXEDMASS = 0x02,
			CID_SPRING = 0x04, CID_MUSCLE = 0x08, CID_WALL = 0x12;

	// Wall constants
	public static final String ID_TOP_WALL = "1", ID_RIGHT_WALL = "2",
			ID_BOTTOM_WALL = "3", ID_LEFT_WALL = "4";
	public static final float WALL_MULTIPLIER = 10f; // scales wall repulsion
														// force
	public static final double WALL_THICKNESS = 10;
	public static final int PIXELS_FOR_INFLATION = 5;

	// Colors
	public static final JGColor MASS_COLOR = JGColor.cyan,
			SPRING_COLOR = JGColor.orange, WALL_COLOR = JGColor.red;

	// Mass constants
	public static final double MASS_RADIUS = 10;
	public static final double MASS_DEFAULT_MASS = 5;

	// Force multipliers
	public static final double DAMPING_FACTOR = .95;
	public static final float VISCOSITY_MULTIPLIER = 200;
	public static final float SPRING_MULTIPLIER = 100f;
	public static final float GRAVITY_MULTIPLIER = .01f;
	public static final float COM_MULTIPLIER = 1f;
	public static final double AMPLITUDE_MULTIPLIER = 30;

	// Miscellaneous
	public static final double DEFAULT_SPRING_CONSTANT = 1;
	public static final float MUSCLE_FREQUENCY = .01f;

	// Default force constants
	public static final double DEFAULT_VISCOSITY = 0,
			DEFAULT_COM_MAGNITUDE = 1000, DEFAULT_COM_EXPONENT = 2;
	public static final Vec2 DEFAULT_GRAVITY = new Vec2(0.0f, 0.0f);
	public static final double DEFAULT_WALL_REPULSION = 100,
			DEFAULT_WALL_EXPONENT = 1;
}
