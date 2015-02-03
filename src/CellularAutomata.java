import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Abstract class for all simulations to be run
 */

public abstract class CellularAutomata {
	private String myName;
	private double myProb;
	private Grid myGrid;
	private Random myRandom;
	
	public CellularAutomata(String name){
		myName = name;
		myRandom = new Random();
	}
	
	public CellularAutomata init(Grid grid, double prob){
		myProb = prob;
		myGrid = grid;
		setUpInitialConfig();	
		return this;
	}

	
	public abstract void setUpInitialConfig();
	
	
	public void setUpInitialConfig(double probA, double probB){
		
	}
	
	public void rule(Cell cell, Predicate<Cell> tester,Consumer<Cell> change){
		if (tester.test(cell)){
			change.accept(cell);
		}	
	}
	
	public void updateNextGen(){
		checkRules();
		handleUpdate();
	}
	
	public void checkRules(Cell cell){
		
	}
	
	public void checkRules(){
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				checkRules(cell);
			}
		}
	}
	
	public void handleUpdate(){
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell : c){
				cell.update();
			}
		}
	}
	
	public double getRandomDouble(){
		return myRandom.nextDouble();
	}
	
	public Grid getGrid() {
		return myGrid;
	}
	
	public String getName(){
		return myName;
	}

	public double getProb() {
		return myProb;
	}

}
