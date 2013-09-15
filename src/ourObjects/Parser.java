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
	private Map<String, Map<String, Double>> FixedMasses;
	private Map<String, Map<String, Double>> Masses;
	private Map<String, Map<String, Double>> Springs;
	private Map<String, Map<String, Double>> Muscles;

	public Parser(String path) {
		myPath = path;
	}
	
	public Map<String, Map<String, Double>> parseAndGenerateMasses() {
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
			Masses = new HashMap<String, Map<String, Double>>();
			for(int i=0; i<nodeChildren.getLength(); i++) {
				if(nodeChildren.item(i).getNodeName().equalsIgnoreCase("mass")) {
					// Movable Masses
					Map<String, Double> attrs = new HashMap<String, Double>();
					String id = getNodeAttr("id", nodeChildren.item(i));
					attrs.put("x",Double.parseDouble(getNodeAttr("x", nodeChildren.item(i))));
					attrs.put("y",Double.parseDouble(getNodeAttr("y", nodeChildren.item(i))));
					attrs.put("vx",Double.parseDouble(getNodeAttr("vx", nodeChildren.item(i))));
					attrs.put("vy",Double.parseDouble(getNodeAttr("vy", nodeChildren.item(i))));

					Masses.put(id, attrs);
				}
			}
			
			return Masses;
		}
		catch(Exception e) {
		    e.printStackTrace();
		    return null;
		}
	}

	public Map<String, Map<String, Double>> parseAndGenerateFixedMasses() {
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
			FixedMasses = new HashMap<String, Map<String, Double>>();
			for(int i=0; i<nodeChildren.getLength(); i++) {
				if(nodeChildren.item(i).getNodeName().equalsIgnoreCase("fixed")) {
					// Fixed Masses
					Map<String, Double> attrs = new HashMap<String, Double>();
					String id = getNodeAttr("id", nodeChildren.item(i));
					double x = Double.parseDouble(getNodeAttr("x", nodeChildren.item(i)));
					double y = Double.parseDouble(getNodeAttr("y", nodeChildren.item(i)));

					attrs.put("x",x);
					attrs.put("y",y);

					FixedMasses.put(id, attrs);
				}
			}
			
			return FixedMasses;
		}
		catch(Exception e) {
		    e.printStackTrace();
		    return null;
		}
	}

	public Map<String, Map<String, Double>> parseAndGenerateSprings() {
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
			Springs = new HashMap<String, Map<String, Double>>();
			int springNum = 0;
			for(int i=0; i<linkChildren.getLength(); i++) {
				if(linkChildren.item(i).getNodeName().equalsIgnoreCase("spring")) {
					// Springs
					springNum++;
					Map<String, Double> attrs = new HashMap<String, Double>();
					String mass1 = getNodeAttr("a", linkChildren.item(i));
					String mass2 = getNodeAttr("b", linkChildren.item(i));
					Double mass1num = Double.parseDouble(mass1.substring(1));
					Double mass2num = Double.parseDouble(mass2.substring(1));
					// check restLength exists?
					Double restLength = Double.parseDouble(getNodeAttr("restlength", linkChildren.item(i)));
					// check constant exists?
					Double constant = Double.parseDouble(getNodeAttr("constant", linkChildren.item(i)));

					attrs.put("mass1", mass1num);
					attrs.put("mass2", mass2num);
					attrs.put("restlength", restLength);
					attrs.put("constant", constant);

					// need to get masses out of engine with objectID
					Springs.put("spring"+springNum,attrs);
				}
			}
			
			return Springs;
		}
		catch ( Exception e ) {
		    e.printStackTrace();
		    return null;
		}
	}

	public Map<String, Map<String, Double>> parseAndGenerateMuscles() {
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
			Muscles = new HashMap<String, Map<String, Double>>();
			int muscleNum = 0;
			for(int i=0; i<linkChildren.getLength(); i++) {
				if(linkChildren.item(i).getNodeName().equalsIgnoreCase("muscle")) {
					// Muscles
					muscleNum++;
					Map<String, Double> attrs = new HashMap<String, Double>();
					String mass1 = getNodeAttr("a", linkChildren.item(i));
					String mass2 = getNodeAttr("b", linkChildren.item(i));
					Double mass1num = Double.parseDouble(mass1.substring(1));
					Double mass2num = Double.parseDouble(mass2.substring(1));
					// check restLength exists?
					Double restLength = Double.parseDouble(getNodeAttr("restlength", linkChildren.item(i)));
					// check constant exists?
					Double constant = Double.parseDouble(getNodeAttr("constant", linkChildren.item(i)));
					// check amplitude exists?
					Double amplitude = Double.parseDouble(getNodeAttr("amplitude", linkChildren.item(i)));

					attrs.put("mass1", mass1num);
					attrs.put("mass2", mass2num);
					attrs.put("restlength", restLength);
					attrs.put("constant", constant);
					attrs.put("amplitude", amplitude);

					// need to get masses out of engine with objectID
					Muscles.put("muscle"+muscleNum,attrs);
				}
			}
			
			return Muscles;
		}
		catch ( Exception e ) {
		    e.printStackTrace();
		    return null;
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