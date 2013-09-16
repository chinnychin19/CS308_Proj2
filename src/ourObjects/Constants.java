package ourObjects;

import jgame.JGColor;

public interface Constants {
	public static final int 
		CID_MASS		= 0x01,
		CID_FIXEDMASS 	= 0x02,
		CID_SPRING 		= 0x04,
		CID_MUSCLE 		= 0x08;
	public static final double MASS_RADIUS = 10;
	public static final double MASS_DEFAULT_MASS = 5;
	public static final JGColor MASS_COLOR = JGColor.cyan, SPRING_COLOR = JGColor.orange;
	public static final double DEFAULT_SPRING_CONSTANT = 1;
	public static final double DAMPING_FACTOR = .8;

}
