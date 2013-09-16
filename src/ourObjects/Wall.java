package ourObjects;

import jboxGlue.WorldManager;

import org.jbox2d.collision.PolygonDef;

public class Wall extends Mass {

	private double myWidth;
	private double myHeight;
	private boolean myHorizontal;
	private double[] myPolyx;
	private double[] myPolyy;
	
	public Wall(String animId, double x, double y, double width, double height, boolean isHorizontal) {
		super(animId, x, y);
		myHorizontal = isHorizontal;
		init(width, height);
	}
	
	public void init(double width, double height) {
		myWidth = width;
		myHeight = height;
		
		myPolyx = null;
		myPolyy = null;
		
		// make it a rect
		PolygonDef shape = new PolygonDef();
		shape.density = (float) 0;
		shape.setAsBox( (float)width, (float)height );
		createBody( shape );
		setBBox( -(int)width/2, -(int)height/2, (int)width, (int)height );
		
	}
	
	public void paintShape() {
		if( myPolyx == null || myPolyy == null )
		{
			// allocate memory for the polygon
			myPolyx = new double[4];
			myPolyy = new double[4];
		}
		
		myEngine.setColor( myColor );
		double cos = Math.cos( myRotation );
		double sin = Math.sin( myRotation );
		double halfWidth = myWidth/2;
		double halfHeight = myHeight/2;
		myPolyx[0] = (int)( x - halfWidth * cos - halfHeight * sin );
		myPolyy[0] = (int)( y + halfWidth * sin - halfHeight * cos );
		myPolyx[1] = (int)( x + halfWidth * cos - halfHeight * sin );
		myPolyy[1] = (int)( y - halfWidth * sin - halfHeight * cos );
		myPolyx[2] = (int)( x + halfWidth * cos + halfHeight * sin );
		myPolyy[2] = (int)( y - halfWidth * sin + halfHeight * cos );
		myPolyx[3] = (int)( x - halfWidth * cos + halfHeight * sin );
		myPolyy[3] = (int)( y + halfWidth * sin + halfHeight * cos );
		myEngine.drawPolygon( myPolyx, myPolyy, null, 4, true, true );
	}
	
	public void repelMasses(){
		ArrayList<Mass> masses = WorldManager.getWorld().getMasses();
		
		for(Mass M : masses) {
			double distance = 0;
			// If wall is horizontal, only care about X distance from wall
			// If wall is vertical, only care about Y distance from wall
			if(myHorizontal){
				distance = Math.abs(M.getX()-getX());
			}
			else {
				distance = Math.abs(M.getY()-getY());
			}
		}
		
	}

}
