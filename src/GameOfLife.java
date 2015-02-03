import javafx.scene.paint.Color;


public class GameOfLife extends CellularAutomata{
	private Color alive = Color.BLACK;
	private Color dead = Color.WHITE;
	
	public GameOfLife(){
		super("Game of Life");
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
	
	public void checkRules(Cell cell){
		int count = getGrid().findNeighbors(cell, alive).size();
		rule(cell,
				c->count==3 && cell.isState(dead),
				c ->c.setNextState(alive));
		rule(cell,
				c -> count !=2 && count !=3 && cell.isState(alive),
				c -> c.setNextState(dead));
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
