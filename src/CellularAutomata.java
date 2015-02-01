import java.util.Random;


public abstract class CellularAutomata {
	private String myName;
	private int[][] myNeighborhood;
	private double myProb;
	private Grid myGrid;
	private Random myRandom;
	
	public CellularAutomata(String name, int size, int[][] neighborhood, double prob){
		myName = name;
		myNeighborhood = neighborhood;
		myProb = prob;
		myRandom = new Random();
		myGrid = new TorusGrid(size, neighborhood);
	}
	
	
	public abstract void setUpInitialConfig();
	
	public void updateNextGen(){
		checkRules();
		handleUpdate();
	}
	
	public abstract void checkRules();
	
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

	public int[][] getMyNeighborhood() {
		return myNeighborhood;
	}

	public double getMyProb() {
		return myProb;
	}

}
