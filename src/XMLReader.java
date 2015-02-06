import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

public class XMLReader {

	//directly horizontal and vertical cells
	private static final int[][] NEAR_NEIGHBORHOOD = new int[][] { { -1, 0 },
			{ 1, 0 }, { 0, 1 }, { 0, -1 } };
	//all 8 surrounding cells
	private static final int[][] MOORE_NEIGHBORHOOD = new int[][] { { -1, 0 },
			{ 1, 0 }, { 0, 1 }, { 0, -1 }, { -1, -1 }, { -1, 1 }, { 1, -1 },
			{ 1, 1 } };

	private Element docEle;
	private HashMap<String, CellularAutomata> gameTypes;
	private HashMap<String, Grid> gridTypes;
	private HashMap<String, int[][]> nbhoodTypes;

	public XMLReader() {
		setUpTypes();
	}

	public void chooseFile() {
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML",
				"xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}
		parseXML(chooser.getSelectedFile());

	}

	public void parseXML(File f) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(f);
			docEle = doc.getDocumentElement();

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private Grid makeGrid() {
		NodeList nl = docEle.getElementsByTagName("Grid");
		Element grid = (Element) nl.item(0);
		int cellNum = Integer.parseInt(getTextValue(grid, "cellNum"));
		String gridType = getTextValue(grid, "GridType");
		String neighborhood = getTextValue(grid, "neighborhood");

		return gridTypes.get(gridType).init(cellNum,
				nbhoodTypes.get(neighborhood));
	}

	private Map<String, Integer> makeParam() {
		Element number = (Element) docEle.getElementsByTagName("number")
				.item(0);
		NodeList nl = number.getElementsByTagName("*");
		Map<String, Integer> param = new HashMap<>();
		for (int i = 0; i < nl.getLength(); i++) {
			Element el = (Element) nl.item(i);
			param.put(el.getTagName(),
					Integer.parseInt(getTextValue(number, el.getTagName())));
		}
		return param;

	}

	public CellularAutomata makeCA() {
		Element color = (Element) docEle.getElementsByTagName("Colors").item(0);
		String gameType = getTextValue(docEle, "Type");
		//get all elements from xml file
		NodeList nl = color.getElementsByTagName("*");
		String[] colors = new String[nl.getLength()];
		double[] probs = new double[nl.getLength()];
		for (int i = 0; i < nl.getLength(); i++) {
			Element c = (Element) nl.item(i);
			colors[i] = c.getAttribute("color");
			probs[i] = Double.parseDouble(getTextValue(color, c.getTagName()));
		}
		return gameTypes.get(gameType).init(makeGrid(), makeParam(), colors,
				probs);
	}

	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	private void setUpTypes() {
		setUpGameTypes();
		setUpGridTypes();
		setUpNbhoodTypes();
	}

	private void setUpGameTypes() {
		gameTypes = new HashMap<>();
		addGameType(new GameOfLife());
		addGameType(new Fire());
		addGameType(new Segregation());
		addGameType(new Wator());
	}

	private void addGameType(CellularAutomata ca) {
		gameTypes.put(ca.getName(), ca);
	}

	private void setUpGridTypes() {
		gridTypes = new HashMap<>();
		gridTypes.put("default", new Grid());
		gridTypes.put("torus", new TorusGrid());
	}

	private void setUpNbhoodTypes() {
		nbhoodTypes = new HashMap<>();

		nbhoodTypes.put("Moore 8", MOORE_NEIGHBORHOOD);
		nbhoodTypes.put("nearest", NEAR_NEIGHBORHOOD);

	}

}
