package ourObjects;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {

	private String myPath;

	public Parser(String path) {
		myPath = path;
	}

	public void parseAndGenerateObjects() {
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
					double x = Double.parseDouble(getNodeAttr("x", nodeChildren.item(i)));
					double y = Double.parseDouble(getNodeAttr("y", nodeChildren.item(i)));
					double vx = Double.parseDouble(getNodeAttr("vx", nodeChildren.item(i)));
					double vy = Double.parseDouble(getNodeAttr("vy", nodeChildren.item(i)));
					new MovingMass(id, x, y, vx, vy);
				}
				else if(nodeChildren.item(i).getNodeName().equalsIgnoreCase("fixed")) {
					// Fixed Masses
					String id = getNodeAttr("id", nodeChildren.item(i));
					double x = Double.parseDouble(getNodeAttr("x", nodeChildren.item(i)));
					double y = Double.parseDouble(getNodeAttr("y", nodeChildren.item(i)));
					new Mass(id, x, y);
				}
			}
			
			// Gets Links
			Node links = getNode("links", modelChildren);
			NodeList linkChildren = links.getChildNodes();

			// Gets spring and muscle objects and constructs them
			for(int i=0; i<linkChildren.getLength(); i++) {
				if(linkChildren.item(i).getNodeName().equalsIgnoreCase("spring")) {
					// Springs
					String mass1 = getNodeAttr("a", linkChildren.item(i));
					String mass2 = getNodeAttr("b", linkChildren.item(i));
					// check restLength exists?
					double restLength = Double.parseDouble(getNodeAttr("restLength", linkChildren.item(i)));

					// need to get masses out of engine with objectID
					new Spring("spring", mass1, mass2);
				}
				else if(linkChildren.item(i).getNodeName().equalsIgnoreCase("fixed")) {
					// Muscles
					String mass1 = getNodeAttr("a", linkChildren.item(i));
					String mass2 = getNodeAttr("b", linkChildren.item(i));
					// check restLength exists?
					double restLength = Double.parseDouble(getNodeAttr("restLength", linkChildren.item(i)));
					// check amplitude?
					double amplitude = Double.parseDouble(getNodeAttr("amplitude", linkChildren.item(i)));					
				}
			}
		}
		catch ( Exception e ) {
		    e.printStackTrace();
		}
	}

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