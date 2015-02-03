import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.scene.paint.Color;
import javafx.scene.paint.Color;



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
	
	public void init(Grid grid, Map<String, Integer> parameters, String[] colors){
		myProb = parameters.get("prob")*.01;
		myGrid = grid;
	}
	
	public CellularAutomata init(Grid grid, Map<String, Integer> parameters, String[] colors, double[] probs){
		init(grid, parameters, colors);
		setUpInitialConfig(colors, probs);
		return this;
	}
	
	public CellularAutomata init(Grid grid, Map<String, Integer> parameters, String[] colors, int[][][] points){
		init(grid, parameters, colors);
		setUpInitialConfig(colors, points);
		return this;
	}
	
	public void setUpInitialConfig(String[] colors, double[] probs){
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				double x = getRandomDouble();
				double sum = 0;
				for (int i =0; i < colors.length; i ++){
					if (sum <= x && x < sum + probs[i]){
						cell.setState(Color.web(colors[i]));
					}
					sum += probs[i];
				}
			}
		}
	}
	
	public void setUpInitialConfig(String[] colors, int[][][] points){
		for (int i =0; i < colors.length; i ++){
			for (int[] point: points[i]){
				getGrid().getCellAt(point[0], point[1]).setState(Color.web(colors[i]));;
			}
		}
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
