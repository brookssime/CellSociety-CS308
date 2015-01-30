import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class CellSocietyView {
	private CellularAutomata myCA;
	private GridDrawer myGridView;
	private Timeline myAnimation;
	private Scene myScene;
	
	public CellSocietyView(CellularAutomata CA, int width, int height, String shape){
		myCA = CA;
		BorderPane root = new BorderPane();
		myGridView = new GridDrawer(CA.getGrid(), shape);
		root.setCenter(myGridView.makeGrid(width, height));
		root.setRight(makeButtons());
		myGridView.updateNextGen();
		myScene = new Scene(root);
	}
	
	public Scene getScene(){
		return myScene;
	}
	
	//runs next generation
	private void updateView(){
		myCA.updateNextGen();
		myGridView.updateNextGen();
	}
	
	public Timeline makeTimeline(int time){
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Animation.INDEFINITE);
		KeyFrame frame = new KeyFrame(Duration.millis(time), e -> updateView());
		myAnimation.getKeyFrames().add(frame);
		return myAnimation;
	}
	
	private VBox makeButtons(){
		VBox buttons = new VBox();
		Button play = makeUIButton("play", e -> myAnimation.play());
		Button pause = makeUIButton("pause", e -> myAnimation.pause());
		Button fastForward = makeUIButton("Speed Up", e -> myAnimation.setRate(myAnimation.getRate()*1.25));
		Button slowDown = makeUIButton("Slow Down", e-> myAnimation.setRate(myAnimation.getRate()*.8));
		Button step = makeUIButton("Step", e-> updateView());
		buttons.getChildren().addAll(play, pause, fastForward, slowDown, step);
		return buttons;
	}
	
	private Button makeUIButton(String name, EventHandler<MouseEvent> e){
		Button test = new Button(name);
		test.setMaxWidth(Double.MAX_VALUE);
		test.setOnMousePressed(e);
		return test;
	}
}
