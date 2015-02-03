import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.paint.Paint;


public abstract class MoveCellularAutomata extends CellularAutomata {

	private ArrayList<Mover> allMovers;
	private ArrayList<Mover> toBeRemoved;
	private ArrayList<Mover> toBeAdded;
	private Paint empty;
	
	public MoveCellularAutomata(String name) {
		super(name);
		allMovers = new ArrayList<>();
		toBeRemoved = new ArrayList<>();
		toBeAdded = new ArrayList<>();
	}

	public void setEmptyColor(Paint paint){
		empty = paint;
	}
	
	public void addMover(Mover mover){
		toBeAdded.add(mover);
	}
	
	public void removeMover(Mover mover){
		toBeRemoved.add(mover);
	}

	
	public void move(Mover mover, ArrayList<Cell> possibleMoves){
		if (possibleMoves.isEmpty()){
			return;
		}
		Cell moveTo = getRandomCell(possibleMoves);
		move(mover, moveTo);
	}

	
	private Cell getRandomCell(ArrayList<Cell> list) {
		Collections.shuffle(list);
		return list.get(0);
	}
	
	private void move(Mover mover, Cell to){
		
		mover.getCell().setState(empty);
		mover.move(to);
		to.setState(mover.getState());
	}
	
	public abstract void checkRules(Mover mover);
	
	public void checkRules(){
		for (Mover mover: allMovers){
			checkRules(mover);
		}
	}
	
	@Override
	public void updateNextGen() {
		checkRules();
		
		allMovers.removeAll(toBeRemoved);
		allMovers.addAll(toBeAdded);
		toBeRemoved.clear();
		toBeAdded.clear();
	}



}
