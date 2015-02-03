import java.util.ArrayList;

import javafx.scene.paint.Color;

/**
 * Creates rules for Segregation simulation
 */

public class Segregation extends MoveCellularAutomata {
	
	private double percentColorTwo = 0.25;
	private double percentColorOne = 0.25;
	private ArrayList<Cell> emptyCells;
	private Color color1 = Color.RED;
	private Color color2 = Color.BLUE;
	private Color empty = Color.WHITE;
	
	public Segregation(){
		super("Segregation");
		setEmptyColor(empty);
		emptyCells = new ArrayList<Cell>();
	}

	public void setUpInitialConfig(){
		setUpInitialConfig(percentColorOne, percentColorTwo);
	}
	
	public void setUpInitialConfig(double probA, double probB){
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				double x= getRandomDouble();
				if (x < probA){
					addMover(new Mover(cell, color1));
				}
				else if (x< probA + probB){
					addMover(new Mover(cell, color2));
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
