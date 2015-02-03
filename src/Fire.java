import javafx.scene.paint.Color;

/**
 * Creates rules for Fire simulation
 */

public class Fire extends CellularAutomata {
	private Color onFire = Color.RED;
	private Color tree = Color.GREEN;
	private Color empty = Color.YELLOW;
	
	public Fire(){
		super("Fire");
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
	

	

	private boolean checkNextToFire(Cell cell) {
		return !getGrid().findNeighbors(cell, onFire).isEmpty();
	}
}
