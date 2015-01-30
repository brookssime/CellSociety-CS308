import javafx.scene.paint.Color;


public class Fire extends CellularAutomata {
	private Color onFire = Color.RED;
	private Color tree = Color.GREEN;
	private Color empty = Color.YELLOW;
	
	public Fire(int size){
		super("Fire", size, new int[][] {{-1, 0}, {1, 0}, {0, 1}, {0, -1}}, .5);
		setUpInitialConfig();
	}
	
	public void setUpInitialConfig(){
		int x = getGrid().getCells().length/2;
		int y = getGrid().getCells()[0].length/2;
		Cell fire = getGrid().getCellAt(x,y);
		fire.setNextState(onFire);
		for (Cell[] c: getGrid().getCells()){
			for(Cell cell: c){
				if (cell == fire){
					continue;
				}
				if (getRandomDouble() <.001){
					cell.setNextState(onFire);
				}
				else{
					cell.setNextState(tree);
				}
			}
		}
		
		handleUpdate();
	}
	

	public void checkRules() {
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				ruleOne(cell);
				ruleTwo(cell);
			}
		}

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
		
		if (nextToFire && getRandomDouble() < getMyProb()){
			cell.setNextState(onFire);
		}
	}

	private boolean checkNextToFire(Cell cell) {
		return !getGrid().findNeighbors(cell, onFire).isEmpty();
	}
}
