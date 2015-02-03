import javafx.scene.paint.Color;


public class Fire extends CellularAutomata {
	private Color onFire = Color.RED;
	private Color tree = Color.GREEN;
	private Color empty = Color.YELLOW;
	
	public Fire(){
		super("Fire");
	}
	
	public void setUpInitialConfig(){
		int x = getGrid().getCells().length/2;
		int y = getGrid().getCells()[0].length/2;
		Cell fire = getGrid().getCellAt(x,y);
		fire.setState(onFire);
		for (Cell[] c: getGrid().getCells()){
			for(Cell cell: c){
				if (cell == fire){
					continue;
				}
				if (getRandomDouble() <.001){
					cell.setState(onFire);
				}
				else{
					cell.setState(tree);
				}
			}
		}	
	}
	

	public void checkRules(Cell cell) {
		rule(cell, 
				c -> c.isState(onFire), 
				c -> c.setNextState(empty));
		rule(cell, 
				c -> c.isState(tree) && checkNextToFire(c) && getRandomDouble()<getProb(),
				c -> c.setNextState(onFire));
		//ruleOne(cell);
		//ruleTwo(cell);
	}
	
	private void ruleOne(Cell cell){
		if (!cell.isState(onFire)){
			return;
		}
		cell.setNextState(empty);
	}
	
	private void ruleTwo(Cell cell){
		if (!cell.isState(tree)){
			return;
		}
		
		boolean nextToFire = checkNextToFire(cell);
		
		if (nextToFire && getRandomDouble() < getProb()){
			cell.setNextState(onFire);
		}
	}

	private boolean checkNextToFire(Cell cell) {
		return !getGrid().findNeighbors(cell, onFire).isEmpty();
	}
}
