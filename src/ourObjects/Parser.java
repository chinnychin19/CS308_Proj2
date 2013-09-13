package ourObjects;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

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

			Node model = getNode("model", root);
			Node masses = getNode("mass")
		}
	}
}