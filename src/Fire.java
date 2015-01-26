import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Fire extends CellularAutomata {
	
	public Fire(){
		super("Fire", new int[][] {{-1, 0}, {1, 0}, {0, 1}, {0, -1}}, .15);
	}

	@Override
	public Scene init(int size, int width, int height) {
		setName("Fire");
		setMyRoot(new Group());
		setMyStates(new HashMap<String, State>());
		VBox buttons= new VBox();
		buttons.setTranslateX(width);
		buttons.getChildren().addAll(pause(), play(), stepForward(), fastForward(), slowDown());
		getMyRoot().getChildren().add(buttons);
		setGrid(new Grid(size, width, height, getMyNeighborhood()));
		
		addState("empty", Color.YELLOW);
		addState("tree", Color.GREEN);
		addState("burning", Color.RED);
		
		setUpInitialConfig();
		
		Scene scene = new Scene(getMyRoot(), width+ buttons.getWidth(), height, Color.WHITE);
		return scene;
	}
	
	public void setUpInitialConfig(){
		int x = getGrid().getCells().length/2;
		int y = getGrid().getCells()[0].length/2;
		Cell fire = getGrid().getCellAt(x,y);
		findState("burning").addToUpdate(fire);
		for (Cell[] c: getGrid().getCells()){
			for(Cell cell: c){
				if (cell == fire){
					continue;
				}
				findState("tree").addToUpdate(cell);
			}
		}
		
		handleUpdate();
	}

	@Override
	public void applyRules() {
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				ruleOne(cell);
				ruleTwo(cell);
			}
		}

	}
	
	public void ruleOne(Cell cell){
		if (!findState("burning").contains(cell)){
			return;
		}
		findState("empty").addToUpdate(cell);
	}
	
	public void ruleTwo(Cell cell){
		if (!findState("tree").contains(cell)){
			return;
		}
		ArrayList<Cell> neighbors = getGrid().getNeighbors(cell);
		boolean nextToFire = false;
		for (Cell neighbor: neighbors){
			if (findState("burning").contains(neighbor)){
				nextToFire = true;
			}
		}
		Random random = new Random();
		if (nextToFire && random.nextDouble() > getMyProb()){
			findState("burning").addToUpdate(cell);
		}
		else{
			findState("tree").addToUpdate(cell);
		}
	}
	

	@Override
	public void handleUpdate() {
		for (State s: getMyStates().values()){
			s.update();
		}
	}
}
