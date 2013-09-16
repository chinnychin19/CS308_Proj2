package ourObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {

	private Map<String, Mass> massMap = new HashMap<String, Mass>();
	private List<Spring> springList = new ArrayList<Spring>();
	private static final String ID_MASS = "mass", ID_FIXED = "fixed", 
			ID_SPRING = "spring", ID_MUSCLE = "muscle"; 

	public void parseXML(String path) { //nodes are masses and fixed masses
		try{
			DOMParser parser = new DOMParser();
			parser.parse(path);
			NodeList root = parser.getDocument().getChildNodes();
			NodeList model = getNode("model", root).getChildNodes();
			NodeList nodes = getNode("nodes", model).getChildNodes();
			NodeList links = getNode("links", model).getChildNodes();
			constructNodes(nodes);
			constructLinks(links);
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
				double constant = strConstant.isEmpty() ? 0 : Double.parseDouble(strConstant);
				String strAmplitude = getNodeAttr("amplitude", curNode);
				double amplitude = strAmplitude.isEmpty() ? 0 : Double.parseDouble(strAmplitude);
				
				if (nodeType.equals(Parser.ID_SPRING)) {
					Spring theSpring = new Spring("", m1, m2);
					theSpring.setRestLength(restLength);
					theSpring.setConstant(constant);
					springList.add(theSpring);
				} else if (nodeType.equals(Parser.ID_MUSCLE)) {
					Muscle theMuscle = new Muscle("", m2, m2);
					theMuscle.setRestLength(restLength);
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