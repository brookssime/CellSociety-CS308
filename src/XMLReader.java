import java.io.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;




public class XMLReader {
	File f = null;
	Document doc = null;
	private static int gameSize;
	private static int cellNumber;
	private static String gridType;
	private static String gameName;
	
	public XMLReader() {
		f = chooseFile();
		parseXML();
		parseDocument();
	}
	
	public File chooseFile(){
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
		chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       f= chooser.getSelectedFile();
	       return f;
	    }
	    return null;
	}

	public void parseXML() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();

			doc = db.parse(f);

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public void parseDocument (){
		Element docEle = doc.getDocumentElement();
		NodeList Scenario = docEle.getElementsByTagName("Parameters");

		if(Scenario != null && Scenario.getLength() > 0) {
			Node param = Scenario.item(0);
			Element el = (Element)param;
			gameSize = Integer.parseInt(getTextValue(el, "GameSize" ));
			cellNumber = Integer.parseInt(getTextValue(el, "cellNum"));
			gridType = getTextValue(el, "GridType");
			gameName = el.getAttribute("name");
						
		}
	}

	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	public static void main(String[] args) {
		XMLReader x = new XMLReader();
		x.parseXML();
		x.parseDocument();
		System.out.println(gameName);
		System.out.println(gridType);
	}

}



