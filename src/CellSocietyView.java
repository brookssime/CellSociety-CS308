import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CellSocietyView {

	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	
	private static final int FRAME_RATE = 2000;
	
	private CellularAutomata myCA;
	private GridDrawer myGridView;
	private Timeline myAnimation;
	private Scene myScene;
	private int myWidth;
	private int myHeight;
	private ResourceBundle myButtonNames;
	private BorderPane myRoot;
	private HashMap<String, CellularAutomata> gameTypes;
	private HashMap<String, Grid> gridTypes;
	private HashMap<String, int[][]> nbhoodTypes;

	public CellSocietyView(int width, int height, String shape) {
		myRoot = new BorderPane();
		myButtonNames = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ "UIButtons");
		setUp();
		myWidth = width;
		myHeight = height;
		myRoot.setCenter(new Rectangle(width, height));
		myRoot.setRight(makeLoadButton());;
		myScene = new Scene(myRoot);
	}
	
	private void setUp(){
		setUpGameTypes();
		setUpGridTypes();
		setUpNbhoodTypes();
	}
	
	private void setUpGameTypes(){
		gameTypes = new HashMap<>();
		addGameType(new GameOfLife());
		addGameType(new Fire());
		addGameType(new Segregation());
		addGameType(new Wator());
	}
	
	private void addGameType(CellularAutomata ca){
		gameTypes.put(ca.getName(), ca);
	}
	
	private void setUpGridTypes(){
		gridTypes = new HashMap<>();
		gridTypes.put("default", new Grid());
		gridTypes.put("torus", new TorusGrid());
	}
	
	private void setUpNbhoodTypes(){
		nbhoodTypes = new HashMap<>();
		nbhoodTypes.put("Moore", new int[][] {{-1, 0}, {1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}});
		nbhoodTypes.put("nearest", new int[][] {{-1, 0}, {1, 0}, {0, 1}, {0, -1}});
	}
	
	public Scene getScene() {
		return myScene;
	}

	private void loadNewFile() {
		if (myAnimation != null) {
			myAnimation.stop();
		}
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		chooser.setFileFilter(new FileNameExtensionFilter("XML","xml"));
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}
		parseFile(chooser.getSelectedFile());
	}

	private void parseFile(File file) {
		int cellNum = 20;
		String neighborhood = "Moore";
		double prob = .3;
		String gridType = "torus";
		String gameName = "Wator";
		makeCAGame(gameName, cellNum, neighborhood, prob, gridType);
	}
	
	private void makeCAGame(String gameName, int cellNum, String neighborhood, double prob, String gridType){
		Grid grid = gridTypes.get(gridType).init(cellNum, nbhoodTypes.get(neighborhood));
		myCA = gameTypes.get(gameName).init(grid, prob);
		myGridView = new GridDrawer(myCA.getGrid(), "square");
		myRoot.setCenter(myGridView.makeGrid(myWidth, myHeight));
		myRoot.setRight(makeButtons());
		myGridView.updateNextGen();
		makeTimeline(FRAME_RATE);
	}

	// runs next generation
	private void updateView() {
		myCA.updateNextGen();
		myGridView.updateNextGen();
	}

	private void makeTimeline(int time) {
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Animation.INDEFINITE);
		KeyFrame frame = new KeyFrame(Duration.millis(time), e -> updateView());
		myAnimation.getKeyFrames().add(frame);
		myAnimation.play();
	}
	
	private Button makeLoadButton(){
		return makeUIButton(myButtonNames.getString("LoadCommand"), e-> loadNewFile());
	}
	
	private VBox makeButtons() {
		VBox buttons = new VBox();
		Button play = makeUIButton(myButtonNames.getString("PlayCommand"),
				e -> myAnimation.play());
		Button pause = makeUIButton(myButtonNames.getString("PauseCommand"),
				e -> myAnimation.pause());
		Button fastForward = makeUIButton(
				myButtonNames.getString("AccelerateCommand"),
				e -> myAnimation.setRate(myAnimation.getRate() * 1.25));
		Button slowDown = makeUIButton(myButtonNames.getString("SlowCommand"),
				e -> myAnimation.setRate(myAnimation.getRate() * .8));
		Button step = makeUIButton(myButtonNames.getString("StepCommand"),
				e -> updateView());
		Button newFile = makeLoadButton();
		buttons.getChildren().addAll(newFile, play, pause, fastForward, slowDown, step);
		return buttons;
	}

	private Button makeUIButton(String name, EventHandler<MouseEvent> e) {
		Button test = new Button(name);
		test.setMaxWidth(Double.MAX_VALUE);
		test.setOnMousePressed(e);
		return test;
	}
}
