import java.util.ArrayList;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public abstract class CellularAutomata {
	private String myName;
	private int[][] myNeighborhood;
	private double myProb;
	private Grid myGrid;
	private Map<String, State> myStates;
	private Timeline animation;
	private Group myRoot;
	
	
	public CellularAutomata(String name, int[][] neighborhood, double prob){
		myName = name;
		myNeighborhood = neighborhood;
		setMyProb(prob);
	}
	
	public abstract Scene init(int size, int width, int height);

	public void addState(String name, Color color){
		State s = new State(name, color);
		myStates.put(name, s);
	}
	
	public State findState(String name){
		return myStates.get(name);
	}
	
	public Timeline makeTimeline(int time){
		animation = new Timeline();
		animation.setCycleCount(Animation.INDEFINITE);
		KeyFrame frame = new KeyFrame(Duration.millis(time), e -> update());
		animation.getKeyFrames().add(frame);
		return animation;
	}
	
	public void update(){
		applyRules();
		handleUpdate();
	}
	
	public abstract void applyRules();
	
	public abstract void handleUpdate();

	public Button pause(){
		Button pause = new Button("pause");
		pause.setOnMousePressed(e -> animation.pause());
		return pause;
	}
	
	public Button play(){
		Button play = new Button("play");
		play.setOnMousePressed(e -> animation.play());
		return play;
	}
	
	public Button stepForward(){
		Button stepForward = new Button("Step");
		stepForward.setOnMousePressed(e -> update());
		return stepForward;
	}
	
	public Button fastForward(){
		Button fastForward = new Button("Fast forward");
		fastForward.setOnMousePressed(e -> animation.setRate(animation.getRate()*1.25));
		return fastForward;
	}
	
	public Button slowDown(){
		Button slow = new Button("Slow Down");
		slow.setOnMousePressed(e -> animation.setRate(animation.getRate()*.8));
		return slow;
	}
	
	public Grid getGrid() {
		return myGrid;
	}

	public void setGrid(Grid myGrid) {
		this.myGrid = myGrid;
		myRoot.getChildren().add(myGrid);
	
	}

	public Map<String, State> getMyStates() {
		return myStates;
	}

	public void setMyStates(Map<String, State> myStates) {
		this.myStates = myStates;
	}
	
	public Group getMyRoot() {
		return myRoot;
	}

	public void setMyRoot(Group myRoot) {
		this.myRoot = myRoot;
	}
	
	public String getName(){
		return myName;
	}
	
	public void  setName(String name){
		myName = name;
	}

	public int[][] getMyNeighborhood() {
		return myNeighborhood;
	}

	public void setMyNeighborhood(int[][] myNeighborhood) {
		this.myNeighborhood = myNeighborhood;
	}

	public double getMyProb() {
		return myProb;
	}

	public void setMyProb(double myProb) {
		this.myProb = myProb;
	}
}
