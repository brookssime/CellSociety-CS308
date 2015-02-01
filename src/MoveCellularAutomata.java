import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.paint.Paint;


public abstract class MoveCellularAutomata extends CellularAutomata {

	private ArrayList<Mover> alreadyMoved;
	private ArrayList<Mover> allMovers;
	private ArrayList<Mover> toBeRemoved;
	private ArrayList<Mover> toBeAdded;
	private Paint empty;
	
	public MoveCellularAutomata(String name, int size, int[][] neighborhood, double prob) {
		super(name, size, neighborhood, prob);
		allMovers = new ArrayList<>();
		toBeRemoved = new ArrayList<>();
		toBeAdded = new ArrayList<>();
	}
	
	public void setEmptyColor(Paint paint){
		empty = paint;
	}
	
	public ArrayList<Mover> getMovers(){
		return allMovers;
	}
	
	public void addToRemove(Mover mover){
		toBeRemoved.add(mover);
	}
	
	public void move(Mover mover, Cell to){
		mover.getCell().setState(empty);
		mover.move(to);
		to.setState(mover.getState());
	}
	
	public void addMover(Mover mover){
		allMovers.add(mover);
	}
	
	public void addNewMover(Mover mover){
		toBeAdded.add(mover);
	}
	
	public abstract void checkRules(Mover mover);
	
	public void checkRules(){
		for (Mover mover: allMovers){
			checkRules(mover);
		}
	}
	
	@Override
	public void updateNextGen() {
		toBeRemoved.clear();
		toBeAdded.clear();
		
		checkRules();
		
		allMovers.removeAll(toBeRemoved);
		allMovers.addAll(toBeAdded);
		
	}

	public Cell getRandomCell(ArrayList<Cell> list) {
		Collections.shuffle(list);
		return list.get(0);
	}

}
