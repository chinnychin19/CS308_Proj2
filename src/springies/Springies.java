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
		p.parseEnvironment("assets/environment.xml", displayWidth(), displayHeight());
		p.parseObjects("assets/daintywalker.xml");
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
