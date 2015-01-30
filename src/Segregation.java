import java.util.ArrayList;

import javafx.scene.paint.Color;


public class Segregation extends MoveCellularAutomata {
	private ArrayList<Cell> emptyCells;
	private Color blue = Color.BLUE;
	private Color red = Color.RED;
	private Color white = Color.WHITE;
	
	public Segregation(int size){
		super("Segregation", size, new int[][] {{0, -1}, {0,1}, {1, 0}, {1, 1}, {-1, 1}},  .75);
		setEmptyColor(white);
		emptyCells = new ArrayList<Cell>();
		setUpInitialConfig();
	}


	public void setUpInitialConfig(){
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				double x = getRandomDouble();
				if (x <0.25){
					addMover(new Mover(cell, red));
				}
				else if(x <0.5){
					addMover(new Mover(cell, blue));
				}
				else{
					cell.setNextState(white);
					emptyCells.add(cell);
				}
				cell.update();
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
		
		if (samePercent < getMyProb()){
			Cell moveTo = getRandomCell(emptyCells);
			move(mover, moveTo);
		}
		
	}
	
	public void move(Mover mover, Cell moveTo){
		emptyCells.add(mover.getCell());
		super.move(mover, moveTo);
		emptyCells.remove(moveTo);
	}
	
	private double getSamePercent(Cell cell) {
		int count = getGrid().findNeighbors(cell, cell.getState()).size();
		int total = getGrid().findNeighbors(cell, white).size() + count;
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
