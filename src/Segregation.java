import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;

/**
 * Creates rules for Segregation simulation
 */

public class Segregation extends MoveCellularAutomata {
	

	private ArrayList<Cell> emptyCells;
	private Color color1 = Color.RED;
	private Color color2 = Color.BLUE;
	private Color empty = Color.WHITE;
	
	public Segregation(){
		super("Segregation");

		emptyCells = new ArrayList<Cell>();
	}
	
	public void init(Grid grid, Map<String, Integer> parameters, String[] colors){
		super.init(grid, parameters, colors);
		color1 = Color.web(colors[1]);
		color2 = Color.web(colors[0]);
		empty = Color.web(colors[2]);
		setEmptyColor(empty);
		setEmptyColor(empty);
		emptyCells = new ArrayList<Cell>();
	}
	
	public void setUpInitialConfig(String[] colors, double[] probs){
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				double x= getRandomDouble();

				if (x < probs[0]){		
					addMover(new Mover(cell, color2));
				}
				else if (x< probs[0]+ probs[1]){		
					addMover(new Mover(cell, color1));

				}
				else{
					cell.setState(empty);
					emptyCells.add(cell);
				}
			}
		}
	}
	
	public void checkRules(Mover mover){
		ruleOne(mover);
	}

	private void ruleOne(Mover mover){
		if (mover.isState(empty)){
			return;
		}
		
		double samePercent = getSamePercent(mover.getCell());
		
		if (samePercent < getProb()){
			move(mover, emptyCells);
		}
		
	}
	
	public void move(Mover mover, ArrayList<Cell> possibleMoves){
		emptyCells.add(mover.getCell());
		super.move(mover, possibleMoves);
		emptyCells.remove(mover.getCell());
	}
	
	
	private double getSamePercent(Cell cell) {
		double count = getGrid().findNeighbors(cell, cell.getState()).size();
		double total = getGrid().findNeighbors(cell, empty).size() + getGrid().findNeighbors(cell, color2).size();
		double samePercent;
		//avoid division by zero
		if (total ==0){
			samePercent = 1;
		}
		else{
			samePercent = count/total;
		}
		return samePercent;
	}
	
}
