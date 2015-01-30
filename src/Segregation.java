import java.util.ArrayList;




import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Segregation extends CellularAutomata {

	
	public Segregation(){
		super("Segregation", new int[][] {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1,-1}, {1, 0}, {1,1}}, .3);
	}

	
	@Override
	public Scene init(int size, int width, int height) {
		Group root = new Group();
		setMyStates(new HashMap<String, State>());
		VBox buttons= new VBox();
		buttons.setTranslateX(width);
		buttons.getChildren().addAll(pause(), play(), stepForward(), fastForward(), slowDown());
		root.getChildren().add(buttons);
		setMyRoot(root);

		setGrid(new Grid(size, height, width, getMyNeighborhood()));
		
		addState("red", Color.RED);
		addState("blue", Color.BLUE);
		addState("empty", Color.WHITE);

		return new Scene(getMyRoot(), width+buttons.getWidth(), height, Color.WHITE);
	}

	@Override
	public void applyRules() {
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				ruleOne(cell);
			}
		}
	}
	
	public void ruleOne(Cell cell){
		Random random = new Random();
		if (!findState("empty").contains(cell)){
			return;
		}
		
		ArrayList<Cell> neighbors = getGrid().getNeighbors(cell);
		double count = 0;
		for (Cell neighbor: neighbors){
			if (neighbor.getState() == cell.getState()){
				count ++;
			}
		}
		
		double samePercent = count/8;
		
		if (samePercent > getMyProb()){
			findState("empty").addToUpdate(cell);
			ArrayList<Cell> empty = findState("empty").getMyCurrent();
			Cell randomCell = empty.get(random.nextInt(empty.size()));
			empty.remove(randomCell);
			findState(cell.getState()).addToUpdate(randomCell);
		}
		else{
			findState(cell.getState()).addToUpdate(cell);
		}
	}
	

	

	@Override
	public void handleUpdate() {
		Collections.shuffle(findState("empty").getMyCurrent());
		
	}

}
