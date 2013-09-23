package springies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jboxGlue.OurWorld;
import jboxGlue.WorldManager;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import org.jbox2d.common.Vec2;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ourObjects.Constants;
import ourObjects.Mass;
import ourObjects.MovingMass;
import ourObjects.Muscle;
import ourObjects.Spring;
import walls.BottomWall;
import walls.LeftWall;
import walls.RightWall;
import walls.TopWall;
import walls.Wall;

public class Parser {

	private Map<String, Mass> massMap;
	private List<Spring> springList;
	private List<Wall> wallList;
	private static final String ID_MASS = "mass", ID_FIXED = "fixed", 
			ID_SPRING = "spring", ID_MUSCLE = "muscle", ID_WALL = "wall"; 
	private int viewWidth, viewHeight;

	public Parser(int w, int h) {
		viewWidth = w;
		viewHeight = h;
		massMap = new HashMap<String, Mass>();
		springList = new ArrayList<Spring>();
		wallList = new ArrayList<Wall>();
	}
	
	public void parseObjects(String path) { //nodes are masses and fixed masses
		massMap.clear();
		springList.clear();
		try{
			DOMParser parser = new DOMParser();
			parser.parse(path);
			NodeList root = parser.getDocument().getChildNodes();
			NodeList model = getNode("model", root).getChildNodes();
			NodeList nodes = getNode("nodes", model).getChildNodes();
			NodeList links = getNode("links", model).getChildNodes();
			constructNodes(nodes);
			constructLinks(links);
			WorldManager.getWorld().addMasses(getMassList());
			WorldManager.getWorld().addSprings(getSpringList());
		}
		catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void parseEnvironment(String path) {
		OurWorld myWorld = WorldManager.getWorld();
		try{
			DOMParser parser = new DOMParser();
			parser.parse(path);
			NodeList root = parser.getDocument().getChildNodes();
			NodeList environment = getNode("environment", root).getChildNodes();
			
			// Handles Gravity
			Node gravity = getNode("gravity", environment);
			double angle = (Double.parseDouble(getNodeAttr("direction", gravity)))*Math.PI/180;
			double magnitude = Double.parseDouble(getNodeAttr("magnitude", gravity));
			myWorld.setGravity(new Vec2((float) (magnitude*Math.cos(angle)) , (float) (magnitude*Math.sin(angle))));
			
			// Handles Viscous Drag
			Node viscosity = getNode("viscosity", environment);
			double vis = Double.parseDouble(getNodeAttr("magnitude", viscosity));
			myWorld.setViscosity(vis);
			
			// Handles CoM Forces
			Node centermass = getNode("centermass", environment);
			double comMag = Double.parseDouble(getNodeAttr("magnitude", centermass));
			double comExp = Double.parseDouble(getNodeAttr("exponent", centermass));
			myWorld.setCenterOfMass(comMag, comExp);
			
			// Handles Wall Generation
			for(int i=0; i<environment.getLength(); i++){
				Node curNode = environment.item(i);
				if(curNode.getNodeName().equals(ID_WALL)) {
					String id = getNodeAttr("id", curNode);
					double repulsion = Double.parseDouble(getNodeAttr("magnitude", curNode));
					double exponent = Double.parseDouble(getNodeAttr("exponent", curNode));
					
					Wall wall = null;
					
					if(id.equals(Constants.ID_TOP_WALL)) {
						double x = viewWidth/2, 
								y = Constants.WALL_THICKNESS/2;
						wall = new TopWall(id, x, y, viewWidth, Constants.WALL_THICKNESS, 
								repulsion, exponent);
					} else if(id.equals(Constants.ID_LEFT_WALL)){
						double x = Constants.WALL_THICKNESS/2, 
								y = viewHeight/2;
						wall = new LeftWall(id, x, y, 
								viewHeight - Constants.WALL_THICKNESS*2, //don't overlap top and bottom 
								Constants.WALL_THICKNESS, repulsion, exponent);
					} else if(id.equals(Constants.ID_BOTTOM_WALL)) {
						double x = viewWidth/2, 
								y = viewHeight-Constants.WALL_THICKNESS/2;
						wall = new BottomWall(id, x, y, viewWidth, Constants.WALL_THICKNESS, repulsion, exponent);
					} else if(id.equals(Constants.ID_RIGHT_WALL)){
						double x = viewWidth-Constants.WALL_THICKNESS/2, 
								y = viewHeight/2;
						wall = new RightWall(id, x, y, 
								viewHeight - Constants.WALL_THICKNESS*2, 
								Constants.WALL_THICKNESS, repulsion, exponent);
					}
					if (wall!=null) {
						wallList.add(wall);
					}
				}
			}
			
			WorldManager.getWorld().setWalls(wallList);
			
		}
		catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public Collection<Spring> getSpringList() {
		return springList;
	}
	
	public Collection<Mass> getMassList() {
		return massMap.values();
	}
	
	private void constructNodes(NodeList nodes) {
		for(int i=0; i<nodes.getLength(); i++) {
			Node curNode = nodes.item(i);
			String nodeType = curNode.getNodeName(); //fixed or mass
			
			if (nodeType.equals(ID_MASS) || nodeType.equals(ID_FIXED)) {
				//Required fields
				String id = getNodeAttr("id", curNode);
				double x = Double.parseDouble(getNodeAttr("x", curNode));
				double y = Double.parseDouble(getNodeAttr("y", curNode));
				Vec2 newXY = getCoordinateCorrections(x, y);
				x = newXY.x;
				y = newXY.y;
				
				//Optional fields
				String strVX = getNodeAttr("vx", curNode);
				double vx = strVX.isEmpty() ? 0 : Double.parseDouble(strVX);
				String strVY = getNodeAttr("vy", curNode);
				double vy = strVY.isEmpty() ? 0 : Double.parseDouble(strVY);
				String strMass = getNodeAttr("mass", curNode);
				//If mass is not provided: return 0 if fixed, else default_mass if mass
				double mass = strMass.isEmpty() ? 
						(nodeType.equals(Parser.ID_MASS) ? Constants.MASS_DEFAULT_MASS : 0) : 
						Double.parseDouble(strMass);
				
				if (nodeType.equals(Parser.ID_FIXED)) {
					massMap.put(id, new Mass("", x, y, mass));
				} else /*if (nodeType.equals(Parser.ID_MASS))*/ {
					massMap.put(id, new MovingMass("", x, y, vx, vy, mass));
				}
			}
		}
	}

	//keep coordinates on the viewing screen
	private Vec2 getCoordinateCorrections(double x, double y) {
		// Check x
		if (x < Constants.WALL_THICKNESS*2) {
			x = Constants.WALL_THICKNESS*2;
		} else if (x > viewWidth - Constants.WALL_THICKNESS*2) {
			x = viewWidth - Constants.WALL_THICKNESS*2;
		}
		
		// Check y
		if (y < Constants.WALL_THICKNESS*2) {
			y = Constants.WALL_THICKNESS*2;
		} else if (y > viewHeight - Constants.WALL_THICKNESS*2) {
			y = viewHeight - Constants.WALL_THICKNESS*2;
		}
		return new Vec2((float)x, (float)y);
	}

	private void constructLinks(NodeList links) {
		for(int i=0; i<links.getLength(); i++) {
			Node curNode = links.item(i);
			String nodeType = curNode.getNodeName(); //fixed or mass
			
			if (nodeType.equals(ID_SPRING) || nodeType.equals(ID_MUSCLE)) {
				//Required fields
				Mass m1 = massMap.get(getNodeAttr("a", curNode));
				Mass m2 = massMap.get(getNodeAttr("b", curNode));
				
				//Optional fields
				String strRestLength = getNodeAttr("restLength", curNode);
				double restLength = strRestLength.isEmpty() ? 0 : Double.parseDouble(strRestLength);
				String strConstant = getNodeAttr("constant", curNode);
				double constant = strConstant.isEmpty() ? 
						Constants.DEFAULT_SPRING_CONSTANT : Double.parseDouble(strConstant);
				String strAmplitude = getNodeAttr("amplitude", curNode);
				double amplitude = strAmplitude.isEmpty() ? 0 : Double.parseDouble(strAmplitude);
				
				if (nodeType.equals(Parser.ID_SPRING)) {
					Spring theSpring = new Spring(Parser.ID_SPRING, m1, m2);
					theSpring.setConstant(constant);
					springList.add(theSpring);
				} else if (nodeType.equals(Parser.ID_MUSCLE)) {
					Muscle theMuscle = new Muscle(Parser.ID_MUSCLE, m1, m2);
					theMuscle.setInitialRestLength(Math.sqrt(Math.pow(m2.getX() - m1.getX(), 2) + Math.pow(m2.getY() - m1.getY(), 2)));
					theMuscle.setConstant(constant);
					theMuscle.setAmplitude(amplitude);
					springList.add(theMuscle);
				}
			}
		}
	}

	/**
	*	Helper Methods for XML Parsing
	*	Thanks to Eric Bruno for the sample code.
	**/
	protected Node getNode(String tagName, NodeList nodes) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            return node;
	        }
	    }
	 
	    return null;
	}
	 
	protected String getNodeValue( Node node ) {
	    NodeList childNodes = node.getChildNodes();
	    for (int x = 0; x < childNodes.getLength(); x++ ) {
	        Node data = childNodes.item(x);
	        if ( data.getNodeType() == Node.TEXT_NODE )
	            return data.getNodeValue();
	    }
	    return "";
	}
	 
	protected String getNodeValue(String tagName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.TEXT_NODE )
	                    return data.getNodeValue();
	            }
	        }
	    }
	    return "";
	}
	 
	protected String getNodeAttr(String attrName, Node node ) {
	    NamedNodeMap attrs = node.getAttributes();
	    for (int y = 0; y < attrs.getLength(); y++ ) {
	        Node attr = attrs.item(y);
	        if (attr.getNodeName().equalsIgnoreCase(attrName)) {
	            return attr.getNodeValue();
	        }
	    }
	    return "";
	}
	 
	protected String getNodeAttr(String tagName, String attrName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.ATTRIBUTE_NODE ) {
	                    if ( data.getNodeName().equalsIgnoreCase(attrName) )
	                        return data.getNodeValue();
	                }
	            }
	        }
	    }
	 
	    return "";
	}
}