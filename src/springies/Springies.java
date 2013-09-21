package springies;

import java.util.HashMap;
import java.util.List;
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
		p.parseObjects("assets/example.xml");
	}
	
	@Override
	public void doFrame( )
	{
		// update game objects
		WorldManager.getWorld().step( 1f, 1 );
		WorldManager.getWorld().applyForces();
		checkForcesToggle();
		moveObjects();
		
		checkCollision( 1 + 2, 1 );
	}
	
	@Override
	public void paintFrame( )
	{
		// nothing to do
		// the objects paint themselves
	}
	
	public void checkForcesToggle() {
		// Toggle Gravity
		if(getKey('G')){
			clearKey('G');
			if(WorldManager.getWorld().getGravityMultiplier() == Constants.GRAVITY_MULTIPLIER){
				WorldManager.getWorld().setGravityMultiplier(0.0f);
			}
			else {
				WorldManager.getWorld().setGravityMultiplier(Constants.GRAVITY_MULTIPLIER);
			}
		}
		
		// Toggle Viscosity
		if(getKey('V')) {
			clearKey('V');
			if(WorldManager.getWorld().getViscosityMultiplier() == Constants.VISCOSITY_MULTIPLIER){
				WorldManager.getWorld().setViscosityMultiplier(0.0f);
			}
			else {
				WorldManager.getWorld().setViscosityMultiplier(Constants.VISCOSITY_MULTIPLIER);
			}
		}
		
		// Toggle COM Force
		if(getKey('M')) {
			clearKey('M');
			if(WorldManager.getWorld().getCOMMultiplier() == Constants.COM_MULTIPLIER){
				WorldManager.getWorld().setCOMMultiplier(0.0f);
			}
			else {
				WorldManager.getWorld().setCOMMultiplier(Constants.COM_MULTIPLIER);
			}
		}
		
		// Toggle Wall Repulsion
		toggleWallRepulsion();
	}
	
	public void toggleWallRepulsion() {
		List<Wall> walls = (List) WorldManager.getWorld().getWalls();
		
		if(getKey('1')){
			clearKey('1');
			for(Wall w: walls){
				if (w.getId().equals(Constants.ID_TOP_WALL)){
					w.setActivity(!w.getActivity());
				}
			}
		}

		if(getKey('2')){
			clearKey('2');
			for(Wall w: walls){
				if (w.getId().equals(Constants.ID_RIGHT_WALL)){
					w.setActivity(!w.getActivity());
				}
			}
		}

		if(getKey('3')){
			clearKey('3');
			for(Wall w: walls){
				if (w.getId().equals(Constants.ID_BOTTOM_WALL)){
					w.setActivity(!w.getActivity());
				}
			}
		}

		if(getKey('4')){
			clearKey('4');
			for(Wall w: walls){
				if (w.getId().equals(Constants.ID_LEFT_WALL)){
					w.setActivity(!w.getActivity());
				}
			}
		}
	}
}
