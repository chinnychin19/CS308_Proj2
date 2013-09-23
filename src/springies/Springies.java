package springies;

import java.util.Collection;

import javax.swing.JOptionPane;

import jboxGlue.*;
import jgame.platform.JGEngine;
import ourObjects.*;

/**
 * This class sets up the canvas, calls the WorldManager to initialize the
 * World, and listens for user interaction through the mouse and keyboard. In
 * this way, it is able to take appropriate action to toggle certain forces,
 * drag assemblies, create/destroy assemblies, and change the size of the walled
 * in area.
 * 
 * @author Chandy
 * 
 */

@SuppressWarnings("serial")
public class Springies extends JGEngine {
	private static Parser myParser;
	private Mass closestMass, mouseMass;
	private Spring mouseSpring;
	private boolean isClicked = false;

	public Springies() {
		// set the window size
		int height = 480;
		double aspect = 16.0 / 9.0;
		initEngine((int) (height * aspect), height);
	}

	@Override
	public void initCanvas() {
		// I have no idea what tiles do...
		setCanvasSettings(1, // width of the canvas in tiles
				1, // height of the canvas in tiles
				displayWidth(), // width of one tile
				displayHeight(), // height of one tile
				null,// foreground colour -> use default colour white
				null,// background colour -> use default colour black
				null // standard font -> use default font
		);
	}

	/**
	 * Calls initWorld() in WorldManager and tells the parser to parse the given
	 * environment and assembly XML files.
	 */
	@Override
	public void initGame() {
		setFrameRate(60, 2);
		WorldManager.initWorld(this);
		myParser = new Parser(displayWidth(), displayHeight());
		myParser.parseEnvironment("assets/environment.xml");
		myParser.parseObjects("assets/jello.xml");
	}

	/**
	 * This is called each frame. It tells the world to apply all environmental
	 * forces and checks for user input.
	 */
	@Override
	public void doFrame() {
		// update game objects
		WorldManager.getWorld().step(1f, 1);
		WorldManager.getWorld().applyForces();
		checkMouse();
		checkForcesToggle();
		checkWallInflation();
		moveObjects();
		checkAssemblies();
	}

	/**
	 * If the user clicks, this instantiates a temporary mass and spring to move
	 * the targeted assembly around the area. The mass and spring are removed
	 * when the user releases the click.
	 */
	private void checkMouse() {
		if (!isClicked && getMouseButton(1)) {
			isClicked = true;
			// clearMouseButton(1);
			int mx = getMouseX(), my = getMouseY();
			closestMass = findClosestMass(mx, my);
			mouseMass = new Mass("mouseMass", mx, my, 0);
			mouseSpring = new Spring("mouseSpring", mouseMass, closestMass);
		} else if (isClicked && !getMouseButton(1)) {
			isClicked = false;
			mouseMass.remove();
			mouseMass = null;
			mouseSpring.remove();
			mouseSpring = null;
		}
		if (isClicked) {
			mouseMass.setPos(getMouseX(), getMouseY());
		}
	}

	private Mass findClosestMass(int mx, int my) {
		Collection<Mass> masses = WorldManager.getWorld().getMasses();
		double minDist = Integer.MAX_VALUE; // will store the squared distance
		Mass closest = null;
		for (Mass m : masses) {
			if (Math.pow(mx - m.getX(), 2) + Math.pow(my - m.getY(), 2) < minDist) {
				minDist = Math.pow(mx - m.getX(), 2)
						+ Math.pow(my - m.getY(), 2);
				closest = m;
			}
		}
		return closest;
	}

	private void checkWallInflation() {
		if (getKey(KeyUp)) {
			clearKey(KeyUp);
			WorldManager.getWorld()
					.inflateWalls(Constants.PIXELS_FOR_INFLATION);
		} else if (getKey(KeyDown)) {
			clearKey(KeyDown);
			WorldManager.getWorld().inflateWalls(
					-Constants.PIXELS_FOR_INFLATION);
		}
	}

	@Override
	public void paintFrame() {
		// nothing to do
		// the objects paint themselves
	}

	private void checkAssemblies() {
		if (getKey('C')) {
			clearKey('C');
			WorldManager.getWorld().clearObjects();
		} else if (getKey('N')) {
			clearKey('N');
			String file = JOptionPane
					.showInputDialog("Which assembly would you like to add?"
							+ " (Just file name with no .xml extension)");
			myParser.parseObjects("assets/" + file + ".xml");
		}
	}

	private void checkForcesToggle() {
		// Toggle Gravity
		if (getKey('G')) {
			clearKey('G');
			WorldManager.getWorld().toggleGravity();
		}

		// Toggle Viscosity
		if (getKey('V')) {
			clearKey('V');
			WorldManager.getWorld().toggleViscosity();
			;
		}

		// Toggle COM Force
		if (getKey('M')) {
			clearKey('M');
			WorldManager.getWorld().toggleCOM();
		}

		// Toggle Wall Repulsion
		if (getKey('1')) {
			clearKey('1');
			WorldManager.getWorld().toggleWall(Constants.ID_TOP_WALL);
		}
		if (getKey('2')) {
			clearKey('2');
			WorldManager.getWorld().toggleWall(Constants.ID_RIGHT_WALL);
		}
		if (getKey('3')) {
			clearKey('3');
			WorldManager.getWorld().toggleWall(Constants.ID_BOTTOM_WALL);
		}
		if (getKey('4')) {
			clearKey('4');
			WorldManager.getWorld().toggleWall(Constants.ID_LEFT_WALL);
		}
	}
}
