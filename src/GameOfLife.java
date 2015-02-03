import java.util.Map;

import javafx.scene.paint.Color;

/**
 * Creates rules for GameOfLife
 */

public class GameOfLife extends CellularAutomata {

	private Color alive;
	private Color dead;

	public GameOfLife() {
		super("Game of Life");
	}

	public void init(Grid grid, Map<String, Integer> parameters, String[] colors) {
		super.init(grid, parameters, colors);
		alive = Color.web(colors[0]);
		dead = Color.web(colors[1]);
	}

	public void checkRules(Cell cell) {
		int count = getGrid().findNeighbors(cell, alive).size();
		rule(cell, c -> count == 3 && cell.isState(dead),
				c -> c.setNextState(alive));
		rule(cell, c -> count != 2 && count != 3 && cell.isState(alive),
				c -> c.setNextState(dead));
	}

}
