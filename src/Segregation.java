import java.util.ArrayList;

import javafx.scene.paint.Color;

/**
 * Creates rules for Segregation simulation
 */

public class Segregation extends MoveCellularAutomata {
	private ArrayList<Cell> emptyCells;
	private Color blue = Color.BLUE;
	private Color red = Color.RED;
	private Color white = Color.WHITE;
	
	public Segregation(){
		super("Segregation");
		setEmptyColor(white);
		emptyCells = new ArrayList<Cell>();
	}

	public void setUpInitialConfig(){
		setUpInitialConfig(0.25, 0.25);
	}
	
	public void setUpInitialConfig(double probA, double probB){
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				double x= getRandomDouble();
				if (x < probA){
					addMover(new Mover(cell, red));
				}
				else if (x< probA + probB){
					addMover(new Mover(cell, blue));
				}
				else{
					cell.setState(white);
					emptyCells.add(cell);
				}
			}
		}
	}
	
	public void checkRules(Mover mover){
		ruleOne(mover);
	}

	private void ruleOne(Mover mover){
		if (mover.isState(white)){
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
		double total = getGrid().findNeighbors(cell, red).size() + getGrid().findNeighbors(cell, blue).size();
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
