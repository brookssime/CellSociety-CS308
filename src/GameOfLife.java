import javafx.scene.paint.Color;


public class GameOfLife extends CellularAutomata{
	private Color alive = Color.BLACK;
	private Color dead = Color.WHITE;

	public GameOfLife(int size){
		super("Game of Life", size, new int[][] {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1,-1}, {1, 0}, {1,1}},0);
		setUpInitialConfig();
	}
	
	public void setUpInitialConfig(){
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				if (getRandomDouble() <0.5){
					cell.setNextState(alive);
				}
				else {
					cell.setNextState(dead);
				}
			}
		}
		handleUpdate();
	}
	
	public void checkRules(){
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				ruleOne(cell);
				ruleTwo(cell);
			}
		}
	}
	
	private void ruleOne(Cell cell){
		if (!cell.isState(dead)){
			return;
		}
		int count = getGrid().findNeighbors(cell, alive).size();
		if (count ==3){
			cell.setNextState(alive);
		}
	}
	
	private void ruleTwo(Cell cell){
		if (!cell.isState(alive)){
			return;
		}
		int count = getGrid().findNeighbors(cell, alive).size();
		if (count != 3 && count !=2){
			cell.setNextState(dead);
		}
	}



	


	
}
