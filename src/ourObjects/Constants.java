package ourObjects;

import jgame.JGColor;

public interface Constants {
	//Collision IDs
	public static final int 
		CID_MASS		= 0x01,
		CID_FIXEDMASS 	= 0x02,
		CID_SPRING 		= 0x04,
		CID_MUSCLE 		= 0x08,
		CID_WALL		= 0x12;
	
	//Wall constants
	public static final String 
		ID_TOP_WALL = "1",
		ID_RIGHT_WALL = "2",
		ID_BOTTOM_WALL = "3",
		ID_LEFT_WALL = "4";
	public static final float WALL_MULTIPLIER = 10f; //scales wall repulsion force
	public static final double WALL_THICKNESS = 10;

	//Colors
	public static final JGColor MASS_COLOR = JGColor.cyan, SPRING_COLOR = JGColor.orange, 
			WALL_COLOR = JGColor.red;
	
	//Mass constants
	public static final double MASS_RADIUS = 10;
	public static final double MASS_DEFAULT_MASS = 5;
	public static final double DAMPING_FACTOR = .95;
	public static final double DEFAULT_SPRING_CONSTANT = 1;
	public static final float VISCOSITY_MULTIPLIER = 200;
	public static final float SPRING_MULTIPLIER = 100f;
	public static final float GRAVITY_MULTIPLIER = .01f;
	public static final float COM_MULTIPLIER = 1f;
	public static final float MUSCLE_FREQUENCY = .01f;
	public static final int PIXELS_FOR_INFLATION = 10;
	public static final double AMPLITUDE_MULTIPLIER = 30;
}
