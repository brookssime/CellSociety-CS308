import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;


public class Wator extends MoveCellularAutomata {
	private Color water = Color.BLUE;
	private Color shark = Color.PURPLE;
	private Color fish = Color.YELLOW;
	private int startEnergy;
	private int fishEnergy;
	private int breedTime;
	
	public Wator(){
		super("Wator");
		setEmptyColor(water);
	}
	
	public CellularAutomata init(Grid grid, Map<String, Integer> parameters){
		init(grid, parameters);
		startEnergy = parameters.get("startEnergy");
		fishEnergy = parameters.get("fishEnergy");
		breedTime = parameters.get("breedTime");
		return this;
	}

	public void ruleOne(Fish f) {
		if (!f.isState(fish)){
			return;
		}
		
		Cell current = f.getCell();
		if (current.getState() != fish){
			removeMover(f);
			return;
		}
		
		f.surviveTurn();
		//finds possible moves
		ArrayList<Cell> possibleMoves = getGrid().findNeighbors(current, water);
		
		move(f, possibleMoves);
		breedIfCan(f, current);
	}

	public void breedIfCan(Fish f, Cell current){
		if (!f.canBreed(breedTime)||f.getCell() == current){
			return;
		}
		Fish born = f.breed(current, startEnergy);
		addMover(born);
	}
	
	public void ruleTwo(Fish f){
		if(!f.isState(shark)){
			return ;
		}
		Cell current = f.getCell();
		f.surviveTurn();
		if (f.checkEnergy()){
			removeMover(f);
			current.setState(water);
			return;
		}
		
		ArrayList<Cell> fishToEat = getGrid().findNeighbors(current, fish);
		ArrayList<Cell> otherMoves = getGrid().findNeighbors(current, water);
		
		move(f, fishToEat);
		if (f.getCell()!= current){
			f.eat(fishEnergy);
		}
		else{
			move(f, otherMoves);
		}
		breedIfCan(f, current);
	}

	@Override
	public void setUpInitialConfig() {
		for (Cell[] c: getGrid().getCells()){
			for (Cell cell: c){
				double x = getRandomDouble();
				if (x <0.25){
					addMover(new Fish(cell, fish, (int) (startEnergy*getRandomDouble()), (int) (5*getRandomDouble())));
				}
				else if(x <0.5){
					addMover(new Fish(cell, shark, (int) (startEnergy*getRandomDouble()), (int) (5*getRandomDouble())));
				}
				else{
					cell.setState(water);
				}
			}
		}
	}

	@Override
	public void checkRules(Mover mover) {
		Fish f = (Fish) mover;
		ruleOne(f);
		ruleTwo(f);
	}
}
