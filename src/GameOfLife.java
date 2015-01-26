import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameOfLife extends CellularAutomata{
	

	public GameOfLife(){
		super("Game of Life", new int[][] {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1,-1}, {1, 0}, {1,1}},0);
	}
	
	public Scene init(int size, int width, int height){
		setName("Game of Life");
		setMyRoot(new Group());
		setMyStates(new HashMap<String, State>());
		VBox buttons= new VBox();
		buttons.setTranslateX(width);
		buttons.getChildren().addAll(pause(), play(), stepForward(), fastForward(), slowDown());
		getMyRoot().getChildren().add(buttons);

		setGrid(new Grid(size, width, height, getMyNeighborhood()));
		
		addState("alive", Color.BLACK);
		addState("dead", Color.WHITE);
		
		setUpInitialConfig(size);
		Scene scene = new Scene(getMyRoot(), width+100, height, Color.WHITE);
		return scene;
	}

	
	public void setUpInitialConfig(int size){
		Random random = new Random();
		for (int i =0; i < size; i ++){
			for (int j =0; j < size; j ++){
				if (random.nextDouble()<0.5){
					findState("alive").addToUpdate(getGrid().getCellAt(i, j));
				}
				else{
					findState("dead").addToUpdate(getGrid().getCellAt(i, j));
				}
			}
		}
		handleUpdate();
	}
	
	public void applyRules(){
		for (State s: getMyStates().values()){
			for (Cell c: s.getMyCurrent()){
				ruleOne(c);
				ruleTwo(c);
			}
		}
	}
	
	public void ruleOne(Cell cell){
		if (!findState("dead").contains(cell)){
			return;
		}
		ArrayList<Cell> neighbors = getGrid().getNeighbors(cell);
		int count =0;
		for (Cell neighbor: neighbors){
			if (findState("alive").contains(neighbor)){
				count ++;
			}
		}
		if (count ==3){
			findState("alive").addToUpdate(cell);
		}
		else{
			findState("dead").addToUpdate(cell);
		}
	}
	
	public void ruleTwo(Cell cell){
		if (!findState("alive").contains(cell)){
			return;
		}
		ArrayList<Cell> neighbors = getGrid().getNeighbors(cell);
		int count =0;
		for (Cell neighbor: neighbors){
			if (findState("alive").contains(neighbor)){
				count++;
			}
		}
		if (count == 3|| count ==2){
			findState("alive").addToUpdate(cell);
		}
		else{
			findState("dead").addToUpdate(cell);
		}
	}
	
	public void handleUpdate(){
		for (State s: getMyStates().values()){
			s.update();
		}
	}
	


	
}
