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

/**
 * Loads grid viewer for simulation
 */

import com.sun.prism.paint.Color;

public class CellSocietyView {
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final int FRAME_RATE = 2000;
	private static final double SLOW_RATE = .8;
	private static final double ACCELERATE_RATE = 1.2;		
	private CellularAutomata myCA;
	private GridDrawer myGridView;
	private Timeline myAnimation;
	private Scene myScene;
	private int myWidth;
	private int myHeight;
	private XMLReader myReader;
	private ResourceBundle myButtonNames;
	private BorderPane myRoot;


	public CellSocietyView(int width, int height, String shape) {
		myRoot = new BorderPane();
		myButtonNames = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ "UIButtons");

		myWidth = width;
		myHeight = height;
		myReader = new XMLReader();
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
		//"Moore" neighborhood includeds 8 surrounding cells
		nbhoodTypes.put("Moore", MOORE_NEIGHBORHOOD);
		//"Nearest" neighborhood includes only vert. and horiz. cells
		nbhoodTypes.put("nearest", NEAR_NEIGHBORHOOD);
	}
	
	public Scene getScene() {
		return myScene;
	}

	private void loadNewFile() {
		if (myAnimation != null) {
			myAnimation.stop();
		}
		myReader.chooseFile();
		makeCAGame();
	}


	
 /*private void makeCAGame(String gameName, int cellNum, String neighborhood, String gridType, String[] colors, int[][][] points){
		Grid grid = gridTypes.get(gridType).init(cellNum, nbhoodTypes.get(neighborhood));
		myCA = gameTypes.get(gameName).init(grid, parameters, colors, points);
		myGridView = new GridDrawer(myCA.getGrid(), "square");
		myRoot.setCenter(myGridView.makeGrid(myWidth, myHeight));
		myRoot.setRight(makeButtons());
		myGridView.updateNextGen();
		makeTimeline(FRAME_RATE);
	}*/
	
	private void makeCAGame(){
		myCA = myReader.makeCA();
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
		Button play = makePlayButton();
		Button pause = makePauseButton();
		Button fastForward = makeFFButton();
		Button slowDown = makeSlowButton();
		Button step = makeStepButton();
		Button newFile = makeLoadButton();
		//adds buttons as UI on right side of screen
		buttons.getChildren().addAll(newFile, play, pause, fastForward, slowDown, step);
		return buttons;
	}

	private Button makeStepButton() {
		Button step = makeUIButton(myButtonNames.getString("StepCommand"),
				e -> updateView());
		return step;
	}

	private Button makeSlowButton() {
		Button slowDown = makeUIButton(myButtonNames.getString("SlowCommand"),
				e -> myAnimation.setRate(myAnimation.getRate() * SLOW_RATE));
		return slowDown;
	}

	private Button makeFFButton() {
		Button fastForward = makeUIButton(
				myButtonNames.getString("AccelerateCommand"),
				e -> myAnimation.setRate(myAnimation.getRate() * ACCELERATE_RATE));
		return fastForward;
	}

	private Button makePauseButton() {
		Button pause = makeUIButton(myButtonNames.getString("PauseCommand"),
				e -> myAnimation.pause());
		return pause;
	}

	private Button makePlayButton() {
		Button play = makeUIButton(myButtonNames.getString("PlayCommand"),
				e -> myAnimation.play());
		return play;
	}

	private Button makeUIButton(String name, EventHandler<MouseEvent> e) {
		Button test = new Button(name);
		test.setMaxWidth(Double.MAX_VALUE);
		test.setOnMousePressed(e);
		return test;
	}
}
