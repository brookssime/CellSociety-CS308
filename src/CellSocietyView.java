import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
import javafx.stage.Stage;
import javafx.util.Duration;

public class CellSocietyView {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	private static final int FRAME_RATE = 2000;

	private CellularAutomata myCA;
	private GridDrawer myGridView;
	private Timeline myAnimation;
	private Scene myScene;
	private ResourceBundle myButtonNames;
	private BorderPane myRoot;

	public CellSocietyView(int width, int height, String shape) {
		myRoot = new BorderPane();
		myButtonNames = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE
				+ "UIButtons");
		myRoot.setCenter(new Rectangle(width, height));
		myRoot.setRight(makeButtons());;
		myScene = new Scene(myRoot);
	}

	public Scene getScene() {
		return myScene;
	}

	public void loadNewFile() {
		if (myAnimation != null) {
			myAnimation.stop();
		}
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt",
				"txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}
		parseFile(chooser.getSelectedFile());
	}

	public void parseFile(File file) {
		int gameSize = 500;
		int cellNum = 20;
		String gameName = "Wator";
		makeCA(gameName, cellNum);
	}
	
	public void makeCA(String gameName, int cellNum){
		myCA = getCAFromString(gameName, cellNum);
		myGridView = new GridDrawer(myCA.getGrid(), "square");
		myRoot.setCenter(myGridView.makeGrid(300, 300));
		myGridView.updateNextGen();
		makeTimeline(FRAME_RATE);
	}
	
	public CellularAutomata getCAFromString(String gameName, int cellNum) {
		try {
			return (CellularAutomata) Class.forName(gameName).getConstructors()[0]
					.newInstance(cellNum);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| SecurityException | ClassNotFoundException e) {
			System.out.println("Not valid game type");
		}
		return null;
	}

	// runs next generation
	private void updateView() {
		myCA.updateNextGen();
		myGridView.updateNextGen();
	}

	public void makeTimeline(int time) {
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Animation.INDEFINITE);
		KeyFrame frame = new KeyFrame(Duration.millis(time), e -> updateView());
		myAnimation.getKeyFrames().add(frame);
		myAnimation.play();
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
		Button newFile = makeUIButton(myButtonNames.getString("LoadCommand"), e -> loadNewFile());
		buttons.getChildren().addAll(play, pause, fastForward, slowDown, step,
				newFile);
		return buttons;
	}

	private Button makeUIButton(String name, EventHandler<MouseEvent> e) {
		Button test = new Button(name);
		test.setMaxWidth(Double.MAX_VALUE);
		test.setOnMousePressed(e);
		return test;
	}
}
