package springies;

import java.util.HashMap;
import java.util.Map;

import jboxGlue.*;
import jgame.JGColor;
import jgame.platform.JGEngine;
import ourObjects.*;

import org.jbox2d.common.Vec2;

@SuppressWarnings( "serial" )
public class Springies extends JGEngine
{	
	public Springies( )
	{
		// set the window size
		int height = 480;
		double aspect = 16.0/9.0;
		initEngine( (int)(height*aspect), height );
	}
	
	@Override
	public void initCanvas( )
	{
		// I have no idea what tiles do...
		setCanvasSettings(
			1, // width of the canvas in tiles
			1, // height of the canvas in tiles
			displayWidth(), // width of one tile
			displayHeight(), // height of one tile
			null,// foreground colour -> use default colour white
			null,// background colour -> use default colour black
			null // standard font -> use default font
		);
	}
	
	@Override
	public void initGame( )
	{
		setFrameRate( 60, 2 );
		
		// init the world
		// One thing to keep straight: The world coordinates have y pointing down
		// the game coordinates have y pointing up
		// so gravity is along the positive y axis in world coords to point down in game coords
		// remember to set all directions (eg forces, velocities) in world coords
		WorldManager.initWorld( this );
		
		Parser p = new Parser();
		p.parseXML("assets/example.xml");
		WorldManager.getWorld().setGravity(new Vec2(0, 10000));
		WorldManager.getWorld().setMasses(p.getMassList());
		WorldManager.getWorld().setSprings(p.getSpringList());
		
		// add walls to bounce off of
		// NOTE: immovable objects must have no mass
		final double WALL_MARGIN = 10;
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = displayWidth() - WALL_MARGIN*2 + WALL_THICKNESS;
		final double WALL_HEIGHT = displayHeight() - WALL_MARGIN*2 + WALL_THICKNESS;
		PhysicalObject wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS );
		wall.setPos( displayWidth()/2, WALL_MARGIN );
		wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS );
		wall.setPos( displayWidth()/2, displayHeight() - WALL_MARGIN );
		wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT );
		wall.setPos( WALL_MARGIN, displayHeight()/2 );
		wall = new PhysicalObjectRect( "wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT );
		wall.setPos( displayWidth() - WALL_MARGIN, displayHeight()/2 );
	}
	
	@Override
	public void doFrame( )
	{
		// update game objects
		WorldManager.getWorld().step( 1f, 1 );
		WorldManager.getWorld().applyForces();
		moveObjects();
		
		checkCollision( 1 + 2, 1 );
	}
	
	@Override
	public void paintFrame( )
	{
		// nothing to do
		// the objects paint themselves
	}
}
