package ourObjects;

import java.util.HashMap;
import java.util.Map;

import jboxGlue.*;
import jgame.JGColor;
import jgame.platform.JGEngine;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {

	private String myPath;
	private Map<String, Mass> Masses = new HashMap<String, Mass>();
	private Map<String, Spring> Springs = new HashMap<String, Spring>();

	public Parser(String path) {
		myPath = path;
	}
	
	public void parseAndGenerateMasses() {
		try{
			DOMParser parser = new DOMParser();
			parser.parse(myPath);
			Document doc = parser.getDocument();

			// Gets root of XML
			NodeList root = doc.getChildNodes();

			// Gets model level nodes
			Node model = getNode("model", root);
			NodeList modelChildren = model.getChildNodes();
			
			// Gets Nodes
			Node nodes = getNode("nodes", modelChildren);
			NodeList nodeChildren = nodes.getChildNodes();

			// Gets Mass objects and constructs them
			for(int i=0; i<nodeChildren.getLength(); i++) {
				if(nodeChildren.item(i).getNodeName().equalsIgnoreCase("mass")) {
					// Movable Masses
					String id = getNodeAttr("id", nodeChildren.item(i));
					Double x = Double.parseDouble(getNodeAttr("x", nodeChildren.item(i)));
					Double y = Double.parseDouble(getNodeAttr("y", nodeChildren.item(i)));
					Double vx = Double.parseDouble(getNodeAttr("vx", nodeChildren.item(i)));
					Double vy = Double.parseDouble(getNodeAttr("vy", nodeChildren.item(i)));

					Mass mass = new MovingMass(id, x, y, vx, vy);
					Masses.put(id, mass);
					
				}
			}
			
		}
		catch(Exception e) {
		    e.printStackTrace();
		}
	}

	public void parseAndGenerateFixedMasses() {
		try{
			DOMParser parser = new DOMParser();
			parser.parse(myPath);
			Document doc = parser.getDocument();

			// Gets root of XML
			NodeList root = doc.getChildNodes();

			// Gets model level nodes
			Node model = getNode("model", root);
			NodeList modelChildren = model.getChildNodes();
			
			// Gets Nodes
			Node nodes = getNode("nodes", modelChildren);
			NodeList nodeChildren = nodes.getChildNodes();

			// Gets Mass objects and constructs them
			for(int i=0; i<nodeChildren.getLength(); i++) {
				if(nodeChildren.item(i).getNodeName().equalsIgnoreCase("fixed")) {
					// Fixed Masses
					String id = getNodeAttr("id", nodeChildren.item(i));
					double x = Double.parseDouble(getNodeAttr("x", nodeChildren.item(i)));
					double y = Double.parseDouble(getNodeAttr("y", nodeChildren.item(i)));

					Mass mass = new Mass(id, x, y);

					Masses.put(id, mass);
				}
			}			
		}
		catch(Exception e) {
		    e.printStackTrace();
		}
	}

	public void parseAndGenerateSprings() {
		try{
			DOMParser parser = new DOMParser();
			parser.parse(myPath);
			Document doc = parser.getDocument();

			// Gets root of XML
			NodeList root = doc.getChildNodes();

			// Gets model level nodes
			Node model = getNode("model", root);
			NodeList modelChildren = model.getChildNodes();
			
			// Gets Links
			Node links = getNode("links", modelChildren);
			NodeList linkChildren = links.getChildNodes();

			// Gets spring and muscle objects and constructs them
			Springs = new HashMap<String, Spring>();
			int springNum = 0;
			for(int i=0; i<linkChildren.getLength(); i++) {
				if(linkChildren.item(i).getNodeName().equalsIgnoreCase("spring")) {
					// Springs
					springNum++;
					Mass mass1 = Masses.get(getNodeAttr("a", linkChildren.item(i)));
					Mass mass2 = Masses.get(getNodeAttr("b", linkChildren.item(i)));

					// check restLength exists?
					Double restLength = Double.parseDouble(getNodeAttr("restlength", linkChildren.item(i)));
					// check constant exists?
					Double constant = Double.parseDouble(getNodeAttr("constant", linkChildren.item(i)));

					Spring spring = new Spring("spring"+springNum, mass1, mass2);
					// need to get masses out of engine with objectID
					Springs.put("spring"+springNum,spring);
				}
			}	
		}
		catch ( Exception e ) {
		    e.printStackTrace();
		}
	}

	public void parseAndGenerateMuscles() {
		try{
			DOMParser parser = new DOMParser();
			parser.parse(myPath);
			Document doc = parser.getDocument();

			// Gets root of XML
			NodeList root = doc.getChildNodes();

			// Gets model level nodes
			Node model = getNode("model", root);
			NodeList modelChildren = model.getChildNodes();
			
			// Gets Links
			Node links = getNode("links", modelChildren);
			NodeList linkChildren = links.getChildNodes();

			// Gets spring and muscle objects and constructs them
			int muscleNum = 0;
			for(int i=0; i<linkChildren.getLength(); i++) {
				if(linkChildren.item(i).getNodeName().equalsIgnoreCase("muscle")) {
					// Muscles
					muscleNum++;
					Mass mass1 = Masses.get(getNodeAttr("a", linkChildren.item(i)));
					Mass mass2 = Masses.get(getNodeAttr("b", linkChildren.item(i)));

					// check restLength exists?
					Double restLength = Double.parseDouble(getNodeAttr("restlength", linkChildren.item(i)));
					// check constant exists?
					Double constant = Double.parseDouble(getNodeAttr("constant", linkChildren.item(i)));
					// check amplitude exists?
					Double amplitude = Double.parseDouble(getNodeAttr("amplitude", linkChildren.item(i)));

					Muscle muscle = new Muscle("muscle"+muscleNum, mass1, mass2);
					// need to get masses out of engine with objectID
					Springs.put("muscle"+muscleNum,muscle);
				}
			}
			
		}
		catch ( Exception e ) {
		    e.printStackTrace();
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